package d1212.test;

import java.sql.SQLException;

import d1208.test.MemberDao;

public class MemberDeleteTest {

	public static void main(String[] args) throws SQLException {
		// 트래잭션 관리를 위해서는 try~catch 를 이용하여 try안에는 commit, catch 안에는 rollback
		// -> Dao 메소드에서 합니다. (메소드는 하나의 트랜잭션이 실행되도록 작성)
		
//		MemberDao dao2 = new MemberDao();	// 생성자가 private 이므로 오류
		
		MemberDao dao = MemberDao.getMemberDao();
		dao.delete(100022);
	}
}
