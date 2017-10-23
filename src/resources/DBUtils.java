package resources;

import java.util.List;

import upskills.database.dao.impl.HbnIssueDao;
import upskills.database.dao.impl.HbnTradeDao;
import upskills.database.dao.impl.HbnTrnHdrDao;
import upskills.database.model.Issue;
import upskills.database.model.Trade;
import upskills.database.model.TradeId;
import upskills.database.model.TrnHdr;

public class DBUtils {

	public DBUtils() {
		// TODO Auto-generated constructor stub
		
	}
	public static void InsertNewTradeIssue(Trade trade)
	{
		HbnTradeDao hbn = HbnTradeDao.getInstance();
		hbn.create(trade);
		hbn.closeCurrentSession();
	}
	
	public static void InsertNewIssue(Issue issue)
	{
		HbnIssueDao hbn = HbnIssueDao.getInstance();
		hbn.create(issue);
		hbn.closeCurrentSession();
	}
	
	public static Issue GetIssueById(int id)
	{
		HbnIssueDao hbn = HbnIssueDao.getInstance();
		Issue issue= hbn.getIssueById(id);
		hbn.closeCurrentSession();
		return issue;
		
	}
	public static Trade GetTradeByNbAndField(int nb, String field)
	{
		HbnTradeDao hbn = HbnTradeDao.getInstance();
		Trade trade = hbn.getTradeByNbAndField(new TradeId(field, nb));
		hbn.closeCurrentSession();
		return trade;
	}
	
	public static List<Trade> GetTradeByNb(int nb)
	{
		HbnTradeDao hbn = HbnTradeDao.getInstance();
		List<Trade> trades = hbn.getTradeByNb(nb);
		hbn.closeCurrentSession();
		return trades;
		
	}

	/**
	 * @return List of Family, Group, Type
	 */
	public static List<TrnHdr> GetListFGT()
	{
		HbnTrnHdrDao hbn = HbnTrnHdrDao.getInstance() ;
		List<TrnHdr> list = hbn.getAll();
		hbn.closeCurrentSession();
		return list;
	}
}