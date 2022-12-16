package d1208.test;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class MemberApp {

	public static void main(String[] args) throws SQLException {
		
		boolean run = true;
		Scanner sc = new Scanner(System.in);
		// SQL 실행할 Dao 객체 생성
		MemberDao dao = MemberDao.getMemberDao();
		while(run) {
			System.out.println("[1] insert	[s] select 전체	[p] select PK	[e] 종료 ");
			System.out.print("메뉴 선택 >>> ");
			String sel = sc.nextLine();
		
			switch(sel) {
			case"i":
				System.out.print("이름 입력 >>> ");
				String name = sc.nextLine();
				System.out.print("주소 입력 >>> ");
				String addr = sc.nextLine();
				Member member = new Member(0, name, null, addr, null, null, null);
				dao.insert(member);
				break;
			case"s":
				List<Member> list = dao.selectList();
				for(Member m : list) {
					System.out.println(m.getCustno() + "\t" 
							+ m.getCustname() + "\t" 
							+ m.getAddress() + "\t"
							+ m.getPhone() + "\t"
							+ m.getJoindate() + "\t"
							+ m.getGrade() + "\t"
							+ m.getCity() + "\t");
				}
				break;
			case"p":
				// 조회할 pk 컬럼값 입력 받고 메소드 실행
				System.out.print("검색할 회원 번호 입력 >>> ");
				int custno = Integer.parseInt(sc.nextLine());
				Member m = dao.selectOne(custno);
				System.out.println("조회 결과 " + m);
				System.out.println(":::::::::::::::::::::::");
				if(m != null) {
					System.out.println(m.getCustno() + "\t"
							+ m.getCustname() + "\t"
							+ m.getAddress() + "\t"
							+ m.getPhone() + "\t"
							+ m.getJoindate() + "\t"
							+ m.getGrade() + "\t"
							+ m.getCity() + "\t"
							);
				}
				break;
			case"e":
				run = false;
				System.out.println("프로그램을 종료합니다.");
				break;
			default:
				break;
			}
			
			
		}
			
			
			
			
	}
}
