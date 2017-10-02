package upskills.tagprocess;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import resources.IConstants;
import upskills.database.Configs;
import upskills.database.dao.impl.HbnTradeDao;
import upskills.database.model.Trade;
import upskills.database.model.TradeId;
import upskills.fileexport.ExcelWriter;
import upskills.fileimport.ExcelReader;

/**
 * This class present main thread of processing
 * 
 * @author LuanNgu 
 * 1. Read mismatch file 
 * 2. Check Trade NB from file whether to exist in Trades table 
 * 		2.1 If Yes, check name of FIELD whether to exist in Trades table 
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
	 * @return list of trade info 
	 */
	public List<ResultObj> extractMismatchColumn(List<String[]> mm_result) {
		String[] header = mm_result.get(0);
		int i = 1;
		int fam_ind = Arrays.binarySearch(header, 0, header.length, IConstants.HEADER_FAMILY);
		int grp_ind = Arrays.binarySearch(header, 0, header.length, IConstants.HEADER_GROUP);
		int typ_ind = Arrays.binarySearch(header, 0, header.length, IConstants.HEADER_TYPE);
		int cur_ind = Arrays.binarySearch(header, 0, header.length, IConstants.HEADER_CURR);;
		int size = mm_result.size();
		ResultObj result = new ResultObj();
		List<ResultObj> mm_table = new ArrayList<ResultObj>();
		for (; i < size - 1; i = i + 2) {
			String[] data1 = mm_result.get(i);
			String[] data2 = mm_result.get(i + 1);
			int col_size = data1.length;
			for (int j = 0; j < col_size; j++) {
				String str1 = data1[j];
				String str2 = data2[j];
				if (j == 0) {
					result.setTrade_number(Integer.parseInt(str1));					
				} else 
				{
					if (str1 != str2)
					{
						result.setCurrency(data1[cur_ind]);
						result.setField_name(header[j]);
						result.setSelected(false);
						result.setSystematic(false);
						result.setTrade_family(data1[fam_ind]);
						result.setTrade_group(data1[grp_ind]);
						result.setTrade_type(data1[typ_ind]);
					}
				}

			}
			mm_table.add(result);
		}
		return mm_table;
	}
	

	
	@SuppressWarnings("unused")
	public void MajorProc() throws IOException {
		Initialize();
		int trade_number = 0;
		String field_name = "";
		List<ResultObj> results = new ArrayList<ResultObj>();
		ResultObj obj_result = null;

		List<String[]> mm_result = ExcelReader.readExcelFile(IConstants.FILE_PATH, 0);
		List<ResultObj> trade_field = extractMismatchColumn(mm_result);
		// START loop to read Mismatch file
		for (ResultObj tf : trade_field) {
			field_name = tf.getField_name();
			trade_number = tf.getTrade_number();
			TradeId tradeid = new TradeId(field_name, trade_number);
			HbnTradeDao hb_trade_dao = new HbnTradeDao();
			hb_trade_dao.getTradeByNbAndField(tradeid);
			
			// Check Trade NB whether to exists in DB
			boolean is_trade_exist = false;
			Trade obj_trade = new Trade();
			// obj_trade = database.getAllIssueByTrade(trade_number) --TODO
			is_trade_exist = (obj_trade != null);

			if (is_trade_exist) { // trade exist in Trade table
				// Initial Trade Object
				Trade obj_trade_field = new Trade();
				tradeid = new TradeId(field_name, trade_number);
				obj_trade_field = hb_trade_dao.getTradeByNbAndField(tradeid);

				if (obj_trade_field != null) { // In case the FIELD exists in
												// Trades
					obj_result = new ResultObj(false, obj_trade_field.getId().getNb(), obj_trade_field.getTrnFmly(),
							obj_trade_field.getTrnGrp(), obj_trade_field.getTrnType(), obj_trade_field.getCurrency(),
							field_name, true);
					obj_result.addIssue(obj_trade_field.getIssue().getId());
					results.add(obj_result);

				} else { // In case the field NOT exists in Trades-Issue info
					List<String> fields = new ArrayList<String>();
					List<Trade> obj_trades = new ArrayList<Trade>();
					obj_trade = obj_trades.get(0);
					// obj_trades = database.getAllIssueByTrade(trade_number) --TODO
					obj_result = new ResultObj(false, obj_trades.get(0).getId().getNb(), obj_trades.get(0).getTrnFmly(),
							obj_trades.get(0).getTrnGrp(), obj_trades.get(0).getTrnType(),
							obj_trades.get(0).getCurrency(), field_name, false);
					for (Trade trade : obj_trades) {
						obj_result.addIssue(trade.getIssue().getId());
					}
					results.add(obj_result);
				}
			} else { // trade NOT exist
				results.add(tf);
			}
			
		}
		// END loop
		ExcelWriter.exportExcelFile(IConstants.EXPORT_EXCEL_FILE, results, IConstants.EXCEL_SHEET_NAME);
		
	}
}
