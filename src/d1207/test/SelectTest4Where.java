package d1207.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import jdbc.util.OracleUtil;
// 하드코딩 : driver, user, password, 중요한 정보들을 소스에서 직접 설정(값을 변경 하려면 소스코드를 변경( -> 프로퍼티 파일 사용으로 변경
public class SelectTest4Where {

	public static void main(String[] args) {
		
		Connection conn = OracleUtil.getConnection();
		
		PreparedStatement pstmt = null;		// sql 실행할 객체를 참조
		ResultSet rs = null;				// select 쿼리 결과 객체를 참조
//		String sql = "select * from member_tbl_02 where custno = 100002";
		String sql = "select * from member_tbl_02 where custno =   ? ";
//																// ? 는 조건식 값, 인자		
		
		try {
			pstmt = conn.prepareStatement(sql);	// sql 명령을 인자로 받아 실행할 객체를 생성하여 pstmt가 참조변수
			// sql 인자를 전달하는 방법
//			pstmt.setInt(1,100009);		// 1번재 ? 기호 인자에 전달될 값은 100001
			System.out.print("검색할 회원 번호 입력 >>> ");
			Scanner sc = new Scanner(System.in);
			int custno = Integer.parseInt(sc.nextLine());
			pstmt.setInt(1,custno);
			
			rs = pstmt.executeQuery();			// 쿼리 실행하고 그 결과를 객체로 생성하여 rs가 참조변수
			
			System.out.println(custno + "조회 결과");
			System.out.println("::::::::::::::::::::");
			
			int cnt = 0;
			if(rs.next()){	// 조건식의 컬럼 custno 는 Pk : 조회 결과가 0 또는 1개
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
//			System.out.println("조회된 행의 개수 : " + cnt);
			if(cnt==0) {
				System.out.println("조회한 전화번호가 없습니다.");
			}
		}catch(SQLException e) {
			e.printStackTrace();	// 빨간글씨로 오류 확인 해줌
		}

/* 
쿼리 실행하고 결과를 자바 객체에 저장하는 방법
	1. 순수하게 Statement 객체 사용 (외부평가)
	2. 프레임워크 : 코딩 노가다를 최소화 (프로젝트 할 때)
	3. SQL을 이용하지 않고 바로 자바 객체와 대입
*/

	}
}
