package d1212.login;

import java.sql.SQLException;
import java.util.Scanner;

public class Test {
	
	public static void main(String[] args) throws SQLException {
		
		BookMemberDao bmdao = BookMemberDao.getBookMemberDao();
		
		Scanner sc = new Scanner(System.in);
//		System.out.print("아이디(이메일) 입력 >>> ");
//		String id = sc.nextLine();
//		
//		System.out.print("패스워드 입력 >>> ");
//		String password = sc.nextLine();
		
		// 1. login 메소드 
//		boolean bb = bmdao.login(id, password);
//		
//		if(bb) {
//			System.out.println(bb);
//		}else
//			System.out.println(bb);
		
		
		// 2. updatePassword 메소드
//		bmdao.updatePassword(id, password);
		
		
		// 3. insert 메소드
		System.out.print("회원번호 입력 >>> ");
		int mem_idx = Integer.parseInt(sc.nextLine());
		System.out.print(" 이름 입력 >>> ");
		String name = sc.nextLine();
		System.out.print("이메일 입력 >>> ");
		String email = sc.nextLine();
		System.out.print("비밀번호 입력 >>> ");
		String pw = sc.nextLine();
		
		bmdao.insert(mem_idx, name, email, pw);
		
	
	}
	
}
