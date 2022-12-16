package d1208.test;

// 인스턴스 필드 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jdbc.util.OracleUtil;

//DAO 클래스 : Data Access Object - SQL 실행하는 메소드를 모아놓은 클래스
//				ㄴ DAO 클래스는 '객체를 1개만 생성되도록 하는 디자인패턴' - 싱글턴(singleton)으로 만듭니다. 
//				ㄴ 1개의 객체로 여러 프로그램에서 사용 하도록 합니다. static 안쓰고 싱글턴으로 만듭니다. 
//														ㄴ static은 재정의 불가능하므로 사용 안함
//				ㄴ 인스턴스 메소드로 만들어야 인터페이스 적용과 상속에서 메소드 재정의가 가능
	public class MemberDao {

		// 싱글턴
		private static MemberDao dao = new MemberDao();	// private static 멤버변수가 참조하는 것은 자기 자신 객체
		private MemberDao() { };// 생성자 private(이 클래스 외에는 사용 불가
		public static MemberDao getMemberDao() { // 생성된 객체를 리턴해 주는 public 매소드 - 항상 동일한 객체를 리턴
			return dao;
		}
		
		public void delete(int custno) throws SQLException {
			String sql = "delete from member_tbl_02 where custno = ?";
			Connection conn = OracleUtil.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, custno);
			pstmt.execute();
			pstmt.close();
			conn.close();
		}
		
		public void update(Member member) throws SQLException {
			String sql = "update member_tbl_02\r\n"
					+ "set phone = ?, address = ?, city = ? \r\n"
					+ "where custno = ?";
			Connection conn = OracleUtil.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getPhone());
			pstmt.setString(2, member.getAddress());
			pstmt.setString(3, member.getCity());
			pstmt.setInt(4, member.getCustno());
			
			pstmt.execute();
			pstmt.close();	// pstmt에 지정된 sql 실행 종료(close : 자원 해제)
//			conn.commit();	// autocommit 이 false(OFF) 일 때는 필요
//			conn.rollback();	// 하나의 트랜잭션을 구성하는 여러 SQL 중 일부 오류일 때 이미 실행된 SqL은 취소
			conn.close();
			
		}

	public void insert(Member member) throws SQLException {
		// Member 객체를 인자로 받아서 insert 할 값으로 SQL에 전달
		Connection conn = OracleUtil.getConnection();
		String sql = "INSERT INTO MEMBER_TBL_02 \r\n"
				+ "VALUES (seq_custno.nextval, ?, ?, ?, sysdate, ?, ?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);

		pstmt.setString(1, member.getCustname());
		pstmt.setString(2, member.getPhone());
		pstmt.setString(3, member.getAddress());
		pstmt.setString(4, member.getGrade());
		pstmt.setString(5, member.getCity());

		pstmt.execute();
		conn.close();
	}

	public List<Member> selectList() throws SQLException { // Member 객체를 저장한 리스트를 리턴
		List<Member> list = null;

		Connection conn = OracleUtil.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from member_tbl_02";

		pstmt = conn.prepareStatement(sql); // Exception 처리는 jvm에게 ghrow(위임. 떠넘기기)
		rs = pstmt.executeQuery();
		List<Member> memberList = new ArrayList<>(); // select 조회 결과를 저장

		while (rs.next()) {
			Member m = new Member(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getDate(5),
									rs.getString(6), rs.getString(7));
			memberList.add(m);
		}
		conn.close();
		return memberList;
	}

	public Member selectOne(int custno) throws SQLException {
		// pk값 custno 를 인자로 전달 받아서 해당되는 조회결과를 Member객체로 리턴
		Member member = null;

		Connection conn = OracleUtil.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from member_tbl_02 where custno = ?";

		pstmt = conn.prepareStatement(sql); // Exception 처리는 jvm에게 ghrow(위임. 떠넘기기)
		pstmt.setInt(1, custno);// ****** 메소드에서 SLQ 전달할 값을 인자로 받았습니다.
		rs = pstmt.executeQuery();

		if (rs.next()) {
			member = new Member(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getDate(5),
					rs.getString(6), rs.getString(7));
		}
		return member;
	}

}
