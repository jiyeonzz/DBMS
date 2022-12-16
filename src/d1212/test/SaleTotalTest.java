package d1212.test;

import java.sql.SQLException;
import java.util.List;

public class SaleTotalTest {

	public static void main(String[] args) throws SQLException {
		
		SaleDao dao = SaleDao.getSaleDao();
		
		List<SaleDto> list = dao.salesTotal();
		
		for(SaleDto sale : list)
			System.out.println(sale.getCustno() + "\t"
					+ sale.getCustname() + "\t"
					+ sale.getAgrade() + "\t"
					+ sale.getPsum() + "\t");
	}
}
