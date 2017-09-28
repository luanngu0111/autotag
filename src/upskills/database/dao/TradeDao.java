package upskills.database.dao;

import upskills.database.model.Trade;
import upskills.database.model.TradeId;


public interface TradeDao extends Dao<Trade> {
	public Trade getTradeByNbAndField(TradeId tradeId);
	public Integer deleteTradeByKey(TradeId tradeId);
}
