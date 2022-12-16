package d1213.book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import jdbc.util.OracleUtil;

public class BookRentDao {
	private static BookRentDao dao = new BookRentDao();
	private BookRentDao() {   };
	public static BookRentDao getInstance() {
		return dao;
	}

	public List<BookRentDto> selectRentBook() throws SQLException {
		List<BookRentDto> list = new ArrayList<>();
		String sql = "SELECT tb.* , TRUNC(sysdate) - EXP_DATE delays \r\n"
				+ "FROM TBL_BOOKRENT tb \r\n"
				+ "WHERE RETURN_DATE IS NULL";
		Connection conn = OracleUtil.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		while(rs.next()) {
			list.add(new BookRentDto(rs.getInt(1), 
					rs.getInt(2), 
					rs.getString(3), 
					rs.getDate(4), 
					rs.getDate(5), 
					rs.getDate(6), 
					rs.getInt(8)));
		}
		pstmt.close();
		conn.close();
		return list;
	}
	
	public List<BookRentDto> selectDelay() throws SQLException {
		List<BookRentDto> list = new ArrayList<>();
		String sql = "SELECT tb.BCODE ,title , rent_date\r\n"
				+ "FROM TBL_BOOK tb \r\n"
				+ "JOIN TBL_BOOKRENT tb2 \r\n"
				+ "ON tb.BCODE = tb2.BCODE AND EXP_DATE < SYSDATE "
				+ "AND RETURN_DATE IS NULL";
		Connection conn = OracleUtil.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		while(rs.next()) {
			list.add(new BookRentDto(rs.getInt(1),
					rs.getInt(2),
					rs.getString(3),
					rs.getDate(4),
					rs.getDate(5),
					rs.getDate(6),
					rs.getInt(8)));
			
		}
		pstmt.close();
		conn.close();
		return list;
	}
	
	public BookRentDto selectRentByMember(int mem_idx) throws SQLException {
		BookRentDto dto = null;
		String sql = "select * from tbl_bookrent "
				+ "where RETURN_DATE IS NULL and mem_idx = ? ";
		Connection conn = OracleUtil.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, mem_idx);
		ResultSet rs = pstmt.executeQuery();
		if(rs.next())
			dto = new BookRentDto(rs.getInt(1), 
					rs.getInt(2), 
					rs.getString(3), 
					rs.getDate(4), 
					rs.getDate(5), 
					rs.getDate(6), 
					rs.getInt(7));
		pstmt.close();
		conn.close();
		return dto;
	}
	
	public boolean isRent(int mem_idx,String bcode) throws SQLException {
		boolean result = false;
		if(isAvailableBook(bcode) && isAvailableMember(mem_idx)) result=true;
		return result;
	}
	
	// Builder 테스트를 위한 메소드 오버로딩
	public int rentBook(BookRentDto bookRentDto) throws SQLException {
		String sql = "UPDATE TBL_BOOKRENT \r\n"
				+ "SET RETURN_DATE = sysdate , DELAY_DAYS = TRUNC(sysdate) - EXP_DATE\r\n"
				+ "WHERE RENT_NO = ?";
		Connection conn = OracleUtil.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, bookRentDto.getMem_idx());
		pstmt.setString(2, bookRentDto.getBcode());
//		pstmt.execute();	// 리턴타입이 boolean : ResultSet 결과가 있는지 여부
		pstmt.executeUpdate();	// 리턴타입이 int : (I,U,D반영된 행의 개수)
		int result = pstmt.executeUpdate();					// insert, update, delet
		pstmt.close();
		conn.close();
		return result;
	}
	
	
//	public void rentBook(BookRentDto bookRentDto) throws SQLException {
	public int rentBook(int mem_idx,String bcode) throws SQLException {
		String sql = "INSERT INTO TBL_BOOKRENT (rent_no,MEM_IDX,BCODE,RENT_DATE,EXP_DATE) \r\n"
				+ "values(bookrent_seq.nextval,?,?,sysdate,sysdate+14)";
		Connection conn = OracleUtil.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, mem_idx);
		pstmt.setString(2, bcode);
//		pstmt.execute();
		int result = pstmt.executeUpdate();
		pstmt.close();
		conn.close();
		return result;
	}
	
	
	public boolean isAvailableBook(String bcode) throws SQLException {
		boolean result = false;
		String sql = "select count(*) from tbl_bookrent \r\n"
				+ "where RETURN_DATE IS NULL and bcode = ? ";
		Connection conn = OracleUtil.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, bcode);
		ResultSet rs = pstmt.executeQuery();
		rs.next();
		if(rs.getInt(1)==0) result=true;   //count(*) 가 1일 때는 bcode 는 대여 중 
		pstmt.close();
		conn.close();
		return result;
	}
	
	public boolean isAvailableMember(int mem_idx) throws SQLException {
		boolean result = false;
		//if(selectRentByMember(mem_idx)!= null) result=true;
		String sql = "select count(*) from tbl_bookrent \r\n"
				+ "where RETURN_DATE IS NULL and mem_idx = ? ";
		Connection conn = OracleUtil.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, mem_idx);
		ResultSet rs = pstmt.executeQuery();
		rs.next();
		if(rs.getInt(1)==0) result=true; //count(*) 가 1일 때는 mem_idx 는 대여 중
		pstmt.close();
		conn.close();
		
		return result;
	}
	
	//모든 회원번호 리스트
	public List<Integer> selectAllMember() throws SQLException {
		List<Integer> list = new ArrayList<>();
		String sql = "select mem_idx from book_member";
		Connection conn = OracleUtil.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		while(rs.next())
			list.add(rs.getInt(1));
		
		pstmt.close();
		conn.close();
		return list;
	}
	
	
	//모든 도서코드 리스트
	public List<String> selectAllBook() throws SQLException{
		List<String> list = new ArrayList<>();
		String sql = "select bcode from tbl_book";
		Connection conn = OracleUtil.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		while(rs.next())
			list.add(rs.getString(1));
		
		pstmt.close();
		conn.close();
		
		return list;
		
	}
	
}
	
