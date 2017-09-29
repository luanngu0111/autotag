package upskills.tagprocess;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import resources.IConstants;
import upskills.database.Configs;
import upskills.database.dao.impl.HbnTradeDao;
import upskills.database.model.Trade;
import upskills.database.model.TradeId;
import upskills.fileimport.ExcelReader;

/**
 * This class present main thread of processing
 * 
 * @author LuanNgu 
 * 1. Read mismatch file 
 * 2. Check Trade NB from file whether to exist in Trades table 
 * 		2.1 If Yes, check name of field whether to exist in Trades table 
 * 			2.1.1 Yes, Set systematic = Y, put value from Issue column into Result. 
 * 			2.1.2 Not exist, set systematic = N, put all issues of current trade nb into Result -> This result will be verify manually 
 * 		2.2 If no, start "Tag new mismatch" process : put mismatch info to Result file and let issue info blank. After user verify trade issue manually 
 * 3. Return step 1
 * 4. If EOF, Export Result
 */
public class TagProcess {
	
	public void Initialize()
	{
		Configs.setProperties(IConstants.HOST_NAME, IConstants.PORT, IConstants.USER_NAME, IConstants.PASSWORD, IConstants.DB_NAME);
	}
	
	/** This method is to read mismatch result and extract info about trade and wrong column
	 * @param mm_result result which read from mismatch result file
	 * @return list of trade and wrong column
	 */
	public List<String[]> extractMismatchColumn(List<String[]> mm_result)
	{
		return null;
	}
	@SuppressWarnings("unused")
	public void MajorProc() throws IOException {
		Initialize();
		int trade_number = 0;
		String field_name = "";
		List<ResultObj> results = new ArrayList<ResultObj>();
		ResultObj obj_result = null;

		List<String[]> mm_result = ExcelReader.readExcelFile(IConstants.FILE_PATH, 0);
		List<String[]> trade_field = extractMismatchColumn(mm_result);
		// START loop to read Mismatch file
		for (String[] row : trade_field) {
			TradeId trade_id = new TradeId();
			trade_id.setNb(trade_number);
			trade_id.setField(field_name);
			HbnTradeDao hb_trade_dao = new HbnTradeDao();
			hb_trade_dao.getTradeByNbAndField(trade_id);
			
			// Check Trade NB whether to exists in DB
			boolean is_trade_exist = false;
			Trade obj_trade = new Trade();
			// obj_trade = database.getAllIssueByTrade(trade_number)
			// is_trade_exists = (obj_trade != null); 
			
			if (is_trade_exist) {  // trade exist in Trade table
				// Initial Trade Object
				Trade obj_trade_field = new Trade();
				obj_trade_field = hb_trade_dao.getTradeByNbAndField(trade_id);

				if (obj_trade_field != null) { // In case the field exists in Trades
					obj_result = new ResultObj(false, obj_trade_field.getId().getNb(), obj_trade_field.getTrnFmly(),
							obj_trade_field.getTrnGrp(), obj_trade_field.getTrnType(), obj_trade_field.getCurrency(),
							field_name, true);

				} else { // In case the field NOT exists in Trades-Issue info
					List<String> fields = new ArrayList<String>();
					List<Trade> obj_trades = new ArrayList<Trade>();
					// obj_trades = database.getAllIssueByTrade(trade_number)
					for (Trade trade : obj_trades) {
//						 obj_result = new ResultObj(false,
//						 trade.getId().getNb(), trade.getTrnFmly(),
//						 trade.getTrnGrp(), trade.getTrnType(),
//						 trade.getCurrency(), trade., false);
					}
				}
			} else { // trade NOT exist
				
			}
			results.add(obj_result);
		}
		// END loop
	}
}
