package d1213.book;

import java.sql.Date;

import lombok.Getter;

@Getter
public class DelayDto {
	private String bcode;
	private String title;
	private Date rent_date;
	
	@Override
	public String toString() {
		return  bcode + "\t"
				+ title + "\t" 
				+ rent_date ;
	}

	public DelayDto(String string, String string2, Date date) {
		// TODO Auto-generated constructor stub
	}


}