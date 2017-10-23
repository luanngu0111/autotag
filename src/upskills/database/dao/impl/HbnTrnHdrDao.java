package upskills.database.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import upskills.database.dao.TrnHdrDao;
import upskills.database.model.TrnHdr;

public class HbnTrnHdrDao extends AbstractHbnDao<TrnHdr> implements TrnHdrDao {

	private static HbnTrnHdrDao _instance;

	/**
	 * 
	 */
	private HbnTrnHdrDao() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static HbnTrnHdrDao getInstance() {
		if (_instance == null)
			_instance = new HbnTrnHdrDao();
		return _instance;
	}

	public void closeCurrentSession()
	{
		closeSession();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TrnHdr> getDataByFmly(String fmly) {
		List<TrnHdr> result = null;
		Session session = getSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			result = (List<TrnHdr>) session.getNamedQuery("getDataByFmly").setParameter("trnFmly", fmly).list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return result;
	}

}
