package d1213.book;

import java.sql.SQLException;
import java.util.List;

public class BookRentTest {

	public static void main(String[] args) {
		BookRentDao dao = BookRentDao.getInstance();
		
		try {
			List<BookRentDto> list = dao.selectRentBook();
			for(BookRentDto br : list)
				System.out.println(br);			//toString() 재정의 직접 하기 
			
		} catch (SQLException e) {
			System.out.println("대여중인 도서 조회 오류!");
		}
		
	}
}