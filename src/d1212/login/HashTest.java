package d1212.login;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import com.google.common.hash.Hashing;

import jdbc.util.OracleUtil;

public class HashTest {

	public static void main(String[] args) throws SQLException {
		
		Connection conn = OracleUtil.getConnection();
		String sql = "update book_member set password64=?\r\n"
				+ "where mem_idx=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		Scanner sc = new Scanner(System.in);

		System.out.print("회원 번호 입력 >>> ");
		pstmt.setInt(2, Integer.parseInt(sc.nextLine()));
		System.out.print("비밀번호 입력(password64 해시코드) >>> ");
		String pass = sc.nextLine();
		
		String hval = Hashing.sha256().hashString(pass, StandardCharsets.UTF_8).toString();	// 첫번째 인자값의 인코딩형식
		pstmt.setString(1, hval);
		
		System.out.println("입력한 패스워드를 sha256함수로 해시코드 값 변경 ");
		System.out.println(hval);
		
		pstmt.execute();
		pstmt.close();
		conn.close();
		System.out.println("패스워드가 변경 되었습니다.");
		
	}
}
