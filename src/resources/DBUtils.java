package resources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import com.mysql.jdbc.PreparedStatement;

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
	}
	
	public static void InsertNewIssue(Issue issue)
	{
		HbnIssueDao hbn = HbnIssueDao.getInstance();
		hbn.create(issue);
	}
	
	public static Issue GetIssueById(int id)
	{
		HbnIssueDao hbn = HbnIssueDao.getInstance();
		Issue issue= hbn.getIssueById(id);
		if (issue==null)
		{
			AutoLogger.getInstance().messageBox("Issue not found");
		}
		return issue;
		
	}
	public static Trade GetTradeByNbAndField(int nb, String field)
	{
		HbnTradeDao hbn = HbnTradeDao.getInstance();
		Trade trade = hbn.getTradeByNbAndField(new TradeId(field, nb));
		return trade;
	}
	
	public static List<Trade> GetTradeByNb(int nb)
	{
		HbnTradeDao hbn = HbnTradeDao.getInstance();
		List<Trade> trades = hbn.getTradeByNb(nb);
		return trades;
		
	}
	
	public static List<Trade> GetAllTrade()
	{
		HbnTradeDao hbn = HbnTradeDao.getInstance();
		List<Trade> trades = hbn.getAll();
		return trades;
		
	}

	/**
	 * @param criteria : pair data of column and value
	 * @param is_union : if yes, using OR operator. Otherwise, using AND operator in WHERE clause
	 * @return
	 */
	public static List<Trade> GetTradeByCriteria(HashMap<String,String> criteria, boolean is_union)
	{
		Set<Entry<String, String>> entrySet = criteria.entrySet();
		int size = entrySet.size();
		int i = 1;
		StringBuilder sb = new StringBuilder("from Trade where ");
		for (Entry entry : entrySet) {
			
			
			if (i++ < size)
				sb.append(entry.getKey() + " = '" + entry.getValue() + ((is_union==true)?"' OR ":"' AND "));
			else
				sb.append(entry.getKey() + " = '" + entry.getValue()+"'");
			
		}
		
		HbnTradeDao hbn = HbnTradeDao.getInstance();
		List<Trade> trades = hbn.getTradeByCriteria(sb.toString());

		return trades;
		
	}
	
	public static void CloseSession()
	{
		HbnTradeDao hbn_trade = HbnTradeDao.getInstance();
		HbnIssueDao hbn_issue = HbnIssueDao.getInstance();
		HbnTrnHdrDao hbn_hdr = HbnTrnHdrDao.getInstance();
		hbn_trade.closeCurrentSession();
		hbn_issue.closeCurrentSession();
		hbn_hdr.closeCurrentSession();
	}
	/**
	 * @return List of Family, Group, Type
	 */
	public static List<TrnHdr> GetListFGT()
	{
		HbnTrnHdrDao hbn = HbnTrnHdrDao.getInstance() ;
		List<TrnHdr> list = hbn.getAll();
		return list;
	}
	
	/**
	 * @return List of Family, Group, Type
	 */
	public static List<TrnHdr> GetDataByFmly(String family)
	{
		HbnTrnHdrDao hbn = HbnTrnHdrDao.getInstance() ;
		List<TrnHdr> list = hbn.getDataByFmly(family);
		return list;
	}
	
	public static int insertTrades(List<Trade> trades) throws Exception
	{
		int result = -1;
		if (trades==null || trades.size()==0)
		{
			AutoLogger.getInstance().error("No trade imported");
			return -1;
		}
		HbnTradeDao hbn = HbnTradeDao.getInstance();
		int size = trades.size();
		StringBuilder sb = new StringBuilder();
		String query = "INSERT INTO trade (NB,instrument,currency,portfolio,trn_fmly,trn_grp,trn_type,trn_status,field,issue_id) VALUES ";
		List<String> queries = new ArrayList<String>(); 
		for (int i = 0 ; i < size; i++){
			Trade trade  = trades.get(i);
			sb.append(String.format("(%d, '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', %d), \n",
					trade.getId().getNb(),
					trade.getInstrument(),
					trade.getCurrency(),
					trade.getPortfolio(),
					trade.getTrnFmly(),
					trade.getTrnGrp(),
					trade.getTrnType(),
					trade.getTrnStatus(),
					trade.getId().getField(),
					trade.getIssue().getId()));
			if ((i > 0 && i%10000==0) || i==size-1){
				
				sb.insert(0, query);
				sb.deleteCharAt(sb.lastIndexOf(",")).append("ON DUPLICATE KEY UPDATE NB=VALUES(NB), field=VALUES(field)");
//				result = hbn.insertTrades(sb.toString());
				queries.add(sb.toString());
				sb.delete(0, sb.length());
				System.out.println("Import data "+ i);
			}
		}
		
		for (String q : queries)
		{
			result = hbn.insertTrades(q);
		}
		return result;
	}
	public static int insertMultipleTrade(List<Trade> trades){
		int result = -1;
		HbnTradeDao hbn = HbnTradeDao.getInstance();
		result = hbn.insertTrades(trades);
		return result;
	}
}
