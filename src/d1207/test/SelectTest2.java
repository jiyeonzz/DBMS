package d1207.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import jdbc.util.OracleUtil;

public class SelectTest2 {

	public static void main(String[] args) {
		// java.sql 패키지의 인터페이스는 자바 API에 구현클래스는 없습니다. 연관된 다른 객체를 통해서 객체가 생성
		Connection conn = OracleUtil.getConnection();
		System.out.print("연결 확인 : ");	
		System.out.println(conn);	// null이 출력되면 연결 실패
		
		
		// SQL select 실행해봅시다 - 새로운 객체 2가지 -> 인터페이스를 통해서 DBMS 종류에 따라 구현체 생성
		PreparedStatement pstmt = null;		// sql 실행할 객체를 참조
		ResultSet rs = null;				// select 쿼리 결과 객체를 참조
		String sql = "select * from member_tbl_02";
		
		try {
			pstmt = conn.prepareStatement(sql);	// sql 명령을 인자로 받아 실행할 객체를 생성하여 pstmt가 참조변수
			rs = pstmt.executeQuery();			// 쿼리 실행하고 그 결과를 객체로 생성하여 rs가 참조변수
			
			int cnt = 0;
			while(rs.next()){
				System.out.println("첫 번째 컬럼의 값 : " + rs.getInt(1));
				System.out.println("두 번째 컬럼의 값 : " + rs.getNString(2));
				System.out.println("세 번째 컬럼의 값 : " + rs.getNString(3));
				System.out.println("네 번째 컬럼의 값 : " + rs.getNString(4));
				System.out.println("다섯 번째 컬럼의 값 : " + rs.getDate(5));
				System.out.println("여섯 번째 컬럼의 값 : " + rs.getNString(6));
				System.out.println("일곱 번째 컬럼의 값 : " + rs.getNString(7));		
				System.out.println(rs.getInt(1) + "\t" 
								+ rs.getNString(2) + "\t"
								+ rs.getNString(3) + "\t"
								+ rs.getNString(4) + "\t"
								+ rs.getNString(5) + "\t"
								+ rs.getNString(6) + "\t"
								+ rs.getNString(7));
				cnt ++;
				System.out.println("-------------------------------------------");
			}
			System.out.println("조회된 행의 개수 : " + cnt);
		}catch(SQLException e) {
			e.printStackTrace();
		}

/* 
쿼리 실행하고 결과를 자바 객체에 저장하는 방법
	1. 순수하게 Statement 객체 사용 (외부평가)
	2. 프레임워크 : 코딩 노가다를 최소화 (프로젝트 할 때)
	3. SQL을 이용하지 않고 바로 자바 객체와 대입
*/

	}
}
