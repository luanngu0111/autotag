package upskills.tagprocess;

import java.util.ArrayList;
import java.util.List;

/** This class present main thread of processing
 * @author LuanNgu
 * 1. Read mismatch file
 * 2. Check Trade NB from file whether to exist in Trades table
 * 		2.1 If Yes, check name of field whether to exist in Trades table
 * 			2.1.1 Yes, Set systematic = Y, put value from Issue column into Result.
 * 			2.1.2 Not exist, set systematic = N, put all issues of current trade nb into Result -> This result will be verify manually
 * 		2.2 If no, start "Tag new mismatch" process : put mismatch info to Result file and let issue info blank. After user verify trade issue manually
 * 3. Export Result
 */
public class MainProcess {
	public void MajorProc()
	{
		int trade_number = 0;
		List<ResultObj> results = new ArrayList<ResultObj>();
		boolean is_trade_exist = false;
		ResultObj obj_result = null;
		// Check Trade NB whether to exists in DB
		/*
		 * is_trade_exist = database.checkTradenb(trade_number)
		 */
		
		if (is_trade_exist)
		{
			//Initial Trade Object Trades obj_trade = new Trades();
			
			/*
			 * obj_trade = database.searchMismatchFieldbyTrade(trade_number, field_name);
			 * if (obj_trade != null)
			 * {
			 * 		obj_result = new ResultObj(false, obj_trade.getTrade_number(), obj_trade.getTradefamily(), obj_trade.getTradegroup()
			 * 									, obj_trade.getTradetype(), obj_trade.getCurrency(), obj_trade.getFieldname(), true);
			 * } 
			 * else 
			 * {
			 * 	obj_result = new ResultObj(false, obj_trade.getTrade_number(), obj_trade.getTradefamily(), obj_trade.getTradegroup()
			 * 									, obj_trade.getTradetype(), obj_trade.getCurrency(), obj_trade.getFieldname(), false);
			 * }
			 */
			
			
			
			
		}
		else 
		{
			
		}
	}
}
