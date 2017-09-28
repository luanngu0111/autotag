package upskills.database.dao.impl;

import org.hibernate.Transaction;

import upskills.database.dao.TradeDao;
import upskills.database.model.*;

public class HbnTradeDao extends AbstractHbnDao<Trade> implements TradeDao {

	public Trade getTradeByNbAndField(TradeId tradeId) {
		Trade result = null;
		result = (Trade) getSession().getNamedQuery("getTradeByNbAndField")
				.setParameter(0, tradeId.getNb())
				// .setInteger("NB", tradeId.getNb()) //Deprecated
				// .setString("field", tradeId.getField().trim()) //Deprecated
				.setParameter(1, tradeId.getField()).uniqueResult();
		return result;
	}

	/**
	 * Delete a row in trade table Return > 0 : delete sucessful = 0 : delete
	 * fail
	 */
	public Integer deleteTradeByKey(TradeId tradeId) {
		int result = -1;
		Transaction tx = getSession().beginTransaction();

		try {
			result = (Integer) getSession().getNamedQuery("deleteTradeByKey")
					.setParameter(0, tradeId.getNb())
					.setParameter(1, tradeId.getField().trim()).executeUpdate();
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			getSession().close();
		}
		return result;
	}

	@Override
	public void create(Trade t) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Trade t) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Trade t) {
		// TODO Auto-generated method stub

	}

	
}
