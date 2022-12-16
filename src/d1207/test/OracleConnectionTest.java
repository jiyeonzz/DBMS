package d1207.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class OracleConnectionTest {

	public static void main(String[] args) {

		Connection conn = null;	// Connection 인터페이스
		
		// DB접속 정보
		String url = "jdbc:oracle:thin:@192.168.0.158:1521:XE";   //데이터베이스 URL - 디비버에서 확인가능합니다.
		// jdbc:oracle:thin : 지정된 프로토콜 (@뒤에 ip로도 가능)
					//jdbc프로토콜,오라클 지정 ,localhost는 서버에 접속시 ip로 대체 , 1521은 오라클의 포트(서비스 구별값)
		String driver="oracle.jdbc.driver.OracleDriver";     //오라클 드라이버클래스(패키지명.클래스명) - 외부라이브러리 ojdbc6.jar 필요
		String user ="iclass10";			//접속할 계정 정보 - 계정이름
		String password="1234";				//            ㄴ 패스워드
		
		try {	// 오류 처리
	// 1. 메모리에 데이터베이스 드라이버를 로드합니다.(로드-> 적재 forName() 메소드는 throws ClassNotFoundException 처리
			Class.forName(driver);
			
	// 2. DriverManager 클래스는 메모리에 로드된 드라이버 클래스를 관리하고, 드라이버 클래스의 종류(Oracle, MySql 등등)에 따라 알아서 
	// 3. DriverManager 클래스의 메소드 getConnection 으로 데이터베이스(오라클) 드라이버에 대한 Connection 구현객체를 생성
	//                            		ㄴ 인터페이스 Connection 타입으로 참조합니다.
	//									ㄴ new 연산 외에 객체를 생성하는 다른 방법. : 메소드를 실행하여 할 수 있는 디자인패턴	
			conn = DriverManager.getConnection(url,user,password);    
			
			System.out.println(conn);
			if(conn !=null) {
				System.out.println("데이터베이스 연결 성공!!");
			}else {
				System.out.println("데이터베이스 연결 실패!!");
			}
			
		} catch (ClassNotFoundException e) {
			System.out.println("데이터베이스 드라이버 로드에 문제가 생겼습니다. : " + e.getMessage());
		} catch (SQLException e) {
			System.out.println("데이터베이스 연결 및 사용에 문제가 생겼습니다. : " + e.getMessage());
		} finally {	// 최종적으로 지원(리소스)을 해체할 때 finally 안에 작성
			//db 접속 후 모든 db작업을 종료하면 접속도 종료
			try {
				conn.close();
				System.out.println("연결 종료");
			}catch(SQLException e) {
				
			}
		}
	}
}
