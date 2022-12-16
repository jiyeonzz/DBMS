package d1213.book;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@TestMethodOrder(value=MethodOrderer.OrderAnnotation.class)
class BookRentUnitTest {
	
	// 아래의 DAO 클래스 메소드를 테스트 하는 것이 목적
	private BookRentDao dao = BookRentDao.getInstance();
	
	@Order(1)
	@DisplayName("대여중인 도서 목록의 크기는 count 함수값과 동일 해야함")
	@Test
	void selectRentBookTest() throws SQLException {
		// 출력문 사용 가능하지만, 출력문이 핵심은 아님
		List<BookRentDto> list = dao.selectRentBook();
		for(BookRentDto br : list)
			System.out.println(br);
		
		assertEquals(1, list.size());	
		// assertXXX은 검증 메소드 : 기대가 일치하면 성공, 그렇지 않으면 실패
		// JUnit 실행 결과 창에 Failures(실패) 숫자가 1이면 테스트 메소드 오류
//		fail("Not yet implemented");	// fail : 메소드 검증 실패
		
	}
	
	@Order(2)
	@DisplayName("회원번호 10004가 도서 대여 가능 여부 - 기대값 false")
	@Test
	void isAvailabeMemberTest() throws SQLException{
		boolean result = dao.isAvailableMember(10004);
		assertEquals(false, result);
//		assertFalse(result):	// 위와 동일한 메소드
		
	}
	
	@Order(3)
	@DisplayName("도서코드 여러 개 데이터에대해 대여 가능 여부 - 기대값 false")
	@ParameterizedTest
	@ValueSource(strings = {"C1101"})
//	@Test
	void isAvailableBookTest(String bcode) throws SQLException {	// 여러 개 도서코드
		boolean result = dao.isAvailableBook(bcode);
		assertFalse(result);
		/*
		  주의할 점 : ' 정확한 데이터와 정확한 기대값으로 테스트 했을 때' 실패한다면 실행코드(메소드)가 오류
		 */
	}
	
	@Order(4)
	@DisplayName("회원 여러 개 데이터에대해 대여 기능 여부 - 기대값 true")
	@ParameterizedTest
	@ValueSource(ints = {10001, 10002, 10003})
	void isAvailableMemberTest2(int mem_idx) throws SQLException {
		boolean result = dao.isAvailableMember(mem_idx);
		assertTrue(result);
	}
	
	@Order(5)
	@DisplayName("회원 10004의 대여 정보가 있는지 - 기대값 not null")
	@Test
	void rentMemberTest() throws SQLException {
		BookRentDto dto = dao.selectRentByMember(10004);
		assertNotNull(dto);
	}
	
	@Order(6)
	@DisplayName("회원 10009, 도서 C1101, 대여 실행 - 기대값 0")
	@Test
	void rentTest() throws SQLException{
		try {
			int cnt = dao.rentBook(10009, "C1101");
			assertEquals(0, cnt);
		} catch (SQLException e) {
			System.out.println("대여 오류 : 잘못된 회원 또는 도서 입니다. ");
		}
	}
	
	@Order(7)
	@DisplayName("회원 10003, 도서 A1102, 대여 실행 - 기대값 1")
	@Test
	void rentTest2(){
		try {
			int cnt = dao.rentBook(10003, "A1102");
			assertEquals(1, cnt);
		} catch (SQLException e) {
			System.out.println("대여 오류 : 잘못된 회원 또는 도서 입니다. ");
		}
	}
}
	
	
/*

JUnit : 단위테스트 (사용자가 만든 메소드 중심)를 위한 도구(외부 라이브러리 추가)
	단위테스트는 데이터가 준비(given)되면 어떤 메소드를 실행(when) 시켰을 때 결과가 기대하는(then) 값이 나와야하는 것을 검증하는 과정
	@Test 어노테이션이 표시된 메소드를 실행하고 검증

*/