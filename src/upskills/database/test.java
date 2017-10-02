package upskills.database;

import upskills.database.dao.impl.HbnTradeDao;
import upskills.database.model.Trade;
import upskills.database.model.TradeId;

public class test {

	public static void main(String[] args) {
		System.out.println("-------------- begin -----------------");

		Trade trade = new Trade();
		TradeId tradeId = new TradeId("PC", 12351);

		HbnTradeDao hbnTradeDao = new HbnTradeDao();

		trade = hbnTradeDao.getTradeByNbAndField(tradeId);

		System.out.println(trade.getId().getNb());

		System.out.println("-------------- end -------------------");

	}
}
