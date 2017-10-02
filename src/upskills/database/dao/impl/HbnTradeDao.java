package upskills.database.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import upskills.database.dao.TradeDao;
import upskills.database.model.Trade;
import upskills.database.model.TradeId;

public class HbnTradeDao extends AbstractHbnDao<Trade> implements TradeDao {

	public List<Trade> getTradeByNb(int nb) {
		List<Trade> result = null;
		Session session = getSession();
		Transaction tx = null;	
		
		try {
			tx = session.beginTransaction();			
			result = (List)session
					 .getNamedQuery("getTradeByNb")
					 .setParameter("NB", nb)					
					 .list();		
		} catch(Exception e) {
			e.printStackTrace();
		}	
		finally {
			session.close();
		}
		return result;	
	}
	
	public Trade getTradeByNbAndField(TradeId tradeId) {
		Trade result = null;
		Session session = getSession();
		Transaction tx = null;	
		
		try {
			tx = session.beginTransaction();			
			result = (Trade)session
					 .getNamedQuery("getTradeByNbAndField")
					 .setParameter("NB", tradeId.getNb())
					 .setParameter("field", tradeId.getField().trim())
					 .uniqueResult();		
		} catch(Exception e) {
			e.printStackTrace();
		}	
		finally {
			session.close();
		}
		return result;	
	}
	
	/**
	 * Delete a row in trade table
	 * Return
	 * 		 > 0 : delete sucessful
	 * 		 = 0 : delete fail
	 */
	public Integer deleteTradeByKey(TradeId tradeId) {
		int result = -1;
		Transaction tx = null;	
		Session session = getSession();
		
		try {			
			tx = session.beginTransaction();
			result = (Integer)session
					.getNamedQuery("deleteTradeByKey")
					.setParameter("NB", tradeId.getNb())
					.setParameter("field", tradeId.getField().trim())
					.executeUpdate();
			tx.commit();
		} catch(Exception e) {
			e.printStackTrace();
			tx.rollback();
		}	
		finally {
			session.close();
		}
		return result;	
	}
	
}
