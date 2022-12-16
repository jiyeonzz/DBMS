package d1207.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jdbc.util.OracleUtil;

public class SelectTest3 {

	public static void main(String[] args) {
		
		Connection conn = OracleUtil.getConnection();
		System.out.print("연결 확인 : ");
		System.out.println(conn);
		
		// \r\n 는 줄바꿈(엔터), sql 자바 문자열에서는 꼭 필요한 것 아닙니다. (있어도 되고 없어도 됩니다.) 
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
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(sql);	
			rs = pstmt.executeQuery();	
			
			while(rs.next()) {
				System.out.print(rs.getInt(1)+"\t");
				System.out.print(rs.getNString(2)+"\t");
				System.out.print(rs.getNString(3)+"\t");
				System.out.print(rs.getNString(4)+"\t");
				System.out.println();
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		
	}
}
