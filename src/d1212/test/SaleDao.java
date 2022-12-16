package d1212.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jdbc.util.OracleUtil;

public class SaleDao {
	
// 싱글턴
	private static SaleDao dao = new SaleDao();
	private SaleDao() {	}
	public static SaleDao getSaleDao() {	// 프로젝트 할 때에는 getInstance() 이름으로 진행
		return dao;
	}
	
	public List<SaleDto> salesTotal() throws SQLException {
		Connection conn = OracleUtil.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT mt.custno,custname,decode(grade,'A','VIP','B','일반','C','직원') \"등급\",psum\r\n"
				+ "FROM MEMBER_TBL_02 mt\r\n"
				+ "JOIN\r\n"
				+ "(	\r\n"
				+ "	SELECT custno , sum(price) psum     \r\n"
				+ "	FROM MONEY_TBL_02 \r\n"
				+ "	GROUP BY custno\r\n"
				+ ") sale    \r\n"
				+ "ON mt.custno = sale.custno\r\n"
				+ "ORDER BY psum desc";
		// 조회 되는 결과 컬럼 : custno, custname, agrade, psum 4개는 member와 money 중 한 곳에만 있는 데이터가 아니므로 새로운 DTO를 만듭니다. 
		
		pstmt = conn.prepareStatement(sql);
		rs = pstmt.executeQuery();
		
		List<SaleDto> list = new ArrayList<>();
		
		while(rs.next()) {
			SaleDto sd = new SaleDto(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4));
			list.add(sd);
		}
		conn.close();
		return list;
	}
}
