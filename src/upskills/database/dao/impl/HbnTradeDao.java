package upskills.database.dao.impl;

import java.util.Arrays;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.StatelessSession;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.mysql.jdbc.PreparedStatement;

import resources.AutoLogger;
import upskills.database.dao.TradeDao;
import upskills.database.model.Trade;
import upskills.database.model.TradeId;

public class HbnTradeDao extends AbstractHbnDao<Trade> implements TradeDao {

	private static HbnTradeDao instance;

	/**
	 * 
	 */
	private HbnTradeDao() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static HbnTradeDao getInstance() {
		if (instance == null)
			instance = new HbnTradeDao();
		return instance;
	}

	public void closeCurrentSession() {
		closeSession();
	}

	public List<Trade> getTradeByNb(int nb) {
		List<Trade> result = null;
		Session session = getSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			result = (List) session.getNamedQuery("getTradeByNb").setParameter("NB", nb).list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return result;
	}

	public List<Trade> getTradeByCriteria(String query_string) {
		List<Trade> result = null;
		Session session = getSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			result = (List) session.createQuery(query_string).list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
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
			result = (Trade) session.getNamedQuery("getTradeByNbAndField").setParameter("NB", tradeId.getNb())
					.setParameter("field", tradeId.getField().trim()).uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return result;
	}

	/**
	 * Delete a row in trade table Return > 0 : delete sucessful = 0 : delete
	 * fail
	 */
	public Integer deleteTradeByKey(TradeId tradeId) {
		int result = -1;
		Transaction tx = null;
		Session session = getSession();

		try {
			tx = session.beginTransaction();
			result = (Integer) session.getNamedQuery("deleteTradeByKey").setParameter("NB", tradeId.getNb())
					.setParameter("field", tradeId.getField().trim()).executeUpdate();
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			session.close();
		}
		return result;
	}

	public Integer insertTrades(String query_string) throws Exception {
		int result = -1;
		Transaction tx = null;
		StatelessSession session = getStatelessSession();
		try {
			tx = session.beginTransaction();
			result = (Integer) session.createNativeQuery(query_string.trim()).executeUpdate();
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			AutoLogger.getInstance().error4Dev(e.getMessage() + Arrays.toString(e.getStackTrace()) + query_string);

			throw new Exception("Input data invalid");

		} finally {
			session.close();
		}
		return result;
	}

	public Integer insertTrades(List<Trade> trades) {
		int result = -1;
		Transaction tx = null;
		StatelessSession session = getStatelessSession();
		int i = 0;
		try {
			tx = session.beginTransaction();
			for (Trade trade : trades) {
				try {
					System.out.println("Inserting ... " + i++);
					session.insert(trade);
				} catch (Exception e) {
					System.out.println("Trade existed ... ");
				}
			}
			tx.commit();
			if (trades.size() == 0)
				result = 0;
			else
				result = 1;
		} catch (Exception e) {
			e.printStackTrace();
			result = -1;
		} finally {
			session.close();
		}
		return result;
	}

}
