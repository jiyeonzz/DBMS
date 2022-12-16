package d1208.test;

import java.sql.Date;

public class Money {

	private int custno;
	private int salenol;
	private int pcost;
	private int amouunt;
	private int price;
	private String pcode;
	private Date sdate;
	
	public Money(int custno, int salenol, int pcost, int amouunt, int price, String pcode, Date sdate) {
		super();
		this.custno = custno;
		this.salenol = salenol;
		this.pcost = pcost;
		this.amouunt = amouunt;
		this.price = price;
		this.pcode = pcode;
		this.sdate = sdate;
	}

	@Override
	public String toString() {
		return "Money [custno=" + custno + ", salenol=" + salenol + ", pcost=" + pcost + ", amouunt=" + amouunt
				+ ", price=" + price + ", pcode=" + pcode + ", sdate=" + sdate + "]";
	}

	public int getSalenol() {
		return salenol;
	}

	public int getPcost() {
		return pcost;
	}

	public int getAmouunt() {
		return amouunt;
	}

	public int getPrice() {
		return price;
	}

	public String getPcode() {
		return pcode;
	}

	public Date getSdate() {
		return sdate;
	}
	
	
	
	
	
	
	
	
}
