package d1208.test;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// lombok 사용해봅시다 : 롬북 라이브러리로 자주 사용되는 getter, setter 등등 코딩을 직접 안해도 됩니다. 
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MemberDto {
	
	private int custno;
	private String custname;
	private String phone;
	private String address;
	private Date joindate;
	private String grade;
	private String city;

	}
