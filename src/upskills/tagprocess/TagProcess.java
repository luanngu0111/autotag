package upskills.tagprocess;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import logger.impl.AutotagLogger;
import resources.AutoLogger;
import resources.DBUtils;
import resources.IConstants;
import resources.Utils;
import upskills.database.dao.impl.HbnIssueDao;
import upskills.database.dao.impl.HbnTradeDao;
import upskills.database.model.Issue;
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
	
	private static List<String>_export_header = new ArrayList<String>();

	
	public static List<String> get_export_header() {
		return _export_header;
	}

	public static void set_export_header(List<String> _export_header) {
		TagProcess._export_header = _export_header;
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
	
	/** This method is to read mismatch result and extract info about trade and wrong column
	 * @param mm_result result which read from mismatch result file
	 * @return list of trade info 
	 */
	public static List<ResultObj> extractMismatchColumn(List<String[]> mm_result) {
		String[] header = HeaderMap.MapHeaderToColumn(mm_result.get(0));
		_export_header.clear();
		_export_header.addAll(Arrays.asList(IConstants.EXPORT_HEADER_NEUTRAL));
		HashMap<Integer,String> flex_head = new HashMap<>();
		int i = 1;
		int nb_ind = Arrays.asList(header).indexOf(IConstants.HEADER_TRADE);
		int fam_ind = Arrays.asList(header).indexOf(IConstants.HEADER_FAMILY);
		int grp_ind = Arrays.asList(header).indexOf(IConstants.HEADER_GROUP);
		int typ_ind = Arrays.asList(header).indexOf(IConstants.HEADER_TYPE);
		int cur_ind = Arrays.asList(header).indexOf(IConstants.HEADER_CURR);
		int port_ind = Arrays.asList(header).indexOf(IConstants.HEADER_PORT);
		int ins_ind = Arrays.asList(header).indexOf(IConstants.HEADER_INS);
		int size = mm_result.size();
		ResultObj result = null;
		List<ResultObj> mm_table = new ArrayList<ResultObj>();
		for (; i < size - 1; i = i + 2) {
			String[] data1 = mm_result.get(i);
			String[] data2 = mm_result.get(i + 1);
			int col_size = data1.length;
			int trade_number = 0;
			
			for (int j = 0; j < col_size; j++) {
				String str1 = data1[j];
				String str2 = data2[j];

				/*
				 * Value is not same and column is not in ignore list
				 */
				if (!str1.equals(str2)
						&& !Arrays.asList(IConstants.IGNORE_COLUMN).contains(
								header[j])) {
					result = new ResultObj();
					
					
					result.setField_name(header[j]);
					result.setSelected(false);
					result.setSystematic(false);
					
					if (cur_ind != -1){
						result.setCurrency(data1[cur_ind]);
						flex_head.put(7, "Currency");
					}
					if (typ_ind != -1){
						result.setTrade_type(data1[typ_ind]);
						flex_head.put(6, "Type");
					}
					if (grp_ind != -1){
						result.setTrade_group(data1[grp_ind]);
						flex_head.put(5, "Group");
					}
					if (fam_ind != -1){
						result.setTrade_family(data1[fam_ind]);
						flex_head.put(4, "Family");
					}
					if (nb_ind != -1){
						result.setTrade_number(Utils.parseTradeNumber(data1[nb_ind]));
						flex_head.put(3, "Trade Number");
					}
					if (ins_ind != -1) {
						result.setInstrument(data1[ins_ind]);
						flex_head.put(2, "Instrument");
					}
					if (port_ind != -1){
						result.setPortfolio(data1[port_ind]);
						flex_head.put(1, "Portfolio");
					}
					mm_table.add(result);
				}

			}
		}
		_export_header.addAll(1, flex_head.values());
		return mm_table;
	}
	
	
	
	public static void main(String[] args){
//			
		try {
			String[] header = new String[]{"NB"};
			MajorProc(Arrays.asList(header));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void PrintScreen(List<ResultObj> test)
	{
		ResultObj obj_result = null;
		for (ResultObj obj : test) {
		
			System.out.println(obj.convertObj());
		}
	}
	
	public static void MajorProc(List<String> header_key) throws IOException {
		
//		GetTagByKeyColumn_v2(header_key);
		if (header_key == null || header_key.size() == 0 
				|| (header_key.size()==1 && HeaderMap.MapHeaderToColumn(header_key.get(0)).equals("NB"))) {
			GetTagByTrade();
		} else
		{
			List<String> mod_header = new ArrayList<String>();
			mod_header = HeaderMap.MapHeaderToColumn(header_key);
			GetTagByKeyColumn(mod_header);
		}
	}
	
	public static void GetTagByTrade() throws IOException {
		
		// Init variable
		AutoLogger log = AutoLogger.getInstance();
		int trade_number = 0;
		String field_name = "";
		
		List<ResultObj> results = new ArrayList<ResultObj>();
		ResultObj obj_result = null;
		List<String[]> mm_result = new ArrayList<String[]>();
		if (IConstants.EXCEL_SHEET_ID != -1)
		{
			mm_result = ExcelReader.readExcelFile(IConstants.FILE_PATH, IConstants.EXCEL_SHEET_NAME);
		} else {
			mm_result = ExcelReader.readExcelFile(IConstants.FILE_PATH, IConstants.EXCEL_SHEET_ID);
		}
		List<ResultObj> trade_field = extractMismatchColumn(mm_result);
		// START loop to read Mismatch file
		System.out.println("Processing .... ");
		log.initLogFile();
		HbnTradeDao hb_trade_dao = HbnTradeDao.getInstance();
		int ind = 0;
		for (ResultObj tf : trade_field) {
			field_name = tf.getField_name();
			trade_number = tf.getTrade_number();
			TradeId tradeid = new TradeId(field_name, trade_number);
			
//			hb_trade_dao.getTradeByNbAndField(tradeid);
			
			// Check Trade NB whether to exists in DB
			boolean is_trade_exist = false;
			List<Trade> obj_trades = new ArrayList<Trade>();
			obj_trades = hb_trade_dao.getTradeByNb(trade_number) ;
			is_trade_exist = (obj_trades != null && obj_trades.size()!=0);

			if (is_trade_exist) { // trade exist in Trade table
				// Initial Trade Object
				Trade obj_trade_field = new Trade();
				tradeid = new TradeId(field_name, trade_number);
				obj_trade_field = hb_trade_dao.getTradeByNbAndField(tradeid);

				if (obj_trade_field != null) { // In case the FIELD exists in
												// Trades
					tf.setSystematic(true);
					tf.addIssue(obj_trade_field.getIssue().getId());
					results.add(tf);

				} else { // In case the field NOT exists in Trades-Issue info

					Set<Integer> issue = new HashSet<>();
					for (Trade trade : obj_trades) {
						issue.add(trade.getIssue().getId());
					}
					tf.addAllIssues(issue);
					results.add(tf);
				}
			} else { // trade NOT exist
				results.add(tf);
			}
			
		}
		// END loop
		
		for (ResultObj obj  : results)
		{
			System.out.println(obj.convertObj());
		}
		ExcelWriter.exportExcelFile(IConstants.EXPORT_EXCEL_FILE, results,
				IConstants.EXCEL_EXPORT_SHEET, _export_header);
		System.out.println("Export completed !");
		// hb_trade_dao.closeCurrentSession();
	}

	public static void GetTagByKeyColumn(List<String> header_key) throws IOException
	{
		//Init variable
		AutoLogger log = AutoLogger.getInstance();
		HashMap<String, String> hashmap = new HashMap<>();
		List<String[]> mm_result = new ArrayList<String[]>();
		List<Trade> obj_trades = new ArrayList<Trade>();
		List<ResultObj> results = new ArrayList<ResultObj>();
		if (IConstants.EXCEL_SHEET_ID != -1)
		{
			mm_result = ExcelReader.readExcelFile(IConstants.FILE_PATH, IConstants.EXCEL_SHEET_NAME);
		} else {
			mm_result = ExcelReader.readExcelFile(IConstants.FILE_PATH, IConstants.EXCEL_SHEET_ID);
		}
		
		List<ResultObj> com_result = extractMismatchColumn(mm_result);
		
		int size = com_result.size();
		
		// START loop to read Mismatch file
		System.out.println("Processing .... ");
		log.initLogFile();
		for (ResultObj item : com_result)
		{
			hashmap.clear();
			for (String header : header_key)
			{
				hashmap.put(header, item.getValueByName(header).toString());
			}
			
			
			// Check Trade NB whether to exists in DB
			boolean is_trade_exist = false;
			obj_trades = DBUtils.GetTradeByCriteria(hashmap, false);
			is_trade_exist = (obj_trades != null && obj_trades.size() != 0);
			if (is_trade_exist)
			{
				hashmap.put("field", item.getField_name());
				List<Trade> obj_trade_field = DBUtils.GetTradeByCriteria(hashmap, false);
				Set<Integer> issue = new HashSet<>();
				/* Check field and key whether to exist */
				if (obj_trade_field != null && obj_trade_field.size()!=0) // exist
				{
					issue.clear();
					for (Trade obj : obj_trade_field)
					{
						issue.add(obj.getIssue().getId());
					}
					item.setSystematic(true);
					item.addAllIssues(issue);
					results.add(item);
				}
				else // not exist
				{
					issue.clear();
					for (Trade obj : obj_trades)
					{
						issue.add(obj.getIssue().getId());
					}
					item.addAllIssues(issue);
					results.add(item);
				}
			}
			else 
			{
				results.add(item);
			}
			
		}
		
		for (ResultObj obj  : results)
		{
			System.out.println(obj.convertObj());
		}
		ExcelWriter.exportExcelFile(IConstants.EXPORT_EXCEL_FILE, results,
				IConstants.EXCEL_EXPORT_SHEET,_export_header);
		System.out.println("Export completed !");
	}
	
	public static void GetTagByKeyColumn_v2(List<String> header_key) throws IOException
	{
		// Init variable
		AutoLogger log = AutoLogger.getInstance();
		HashMap<String, String> hashmap = new HashMap<>();
		List<String[]> mm_result = new ArrayList<String[]>();
		List<Trade> obj_trades = new ArrayList<Trade>();
		List<ResultObj> results = new ArrayList<ResultObj>();
		if (IConstants.EXCEL_SHEET_ID != -1) {
			mm_result = ExcelReader.readExcelFile(IConstants.FILE_PATH, IConstants.EXCEL_SHEET_NAME);
		} else {
			mm_result = ExcelReader.readExcelFile(IConstants.FILE_PATH, IConstants.EXCEL_SHEET_ID);
		}

		List<ResultObj> com_result = extractMismatchColumn(mm_result);
		// START loop to read Mismatch file
		System.out.println("Processing .... ");
		log.initLogFile();
		for (ResultObj item : com_result)
		{
			hashmap.clear();
			for (String header : header_key)
			{
				hashmap.put(header, item.getValueByName(header).toString());
			}
			
			// Check Trade NB whether to exists in DB
			boolean is_trade_exist = false;
			obj_trades = DBUtils.GetTradeByCriteria(hashmap, item.getField_name());
			is_trade_exist = (obj_trades != null && obj_trades.size() != 0);
			if (is_trade_exist)
			{
				Set<Integer> issue = new HashSet<>();
				/* Check field and key whether to exist */
				if (obj_trades.size()== 1) // exists
				{
					issue.clear();
					for (Trade obj : obj_trades)
					{
						issue.add(obj.getIssue().getId());
					}
					item.setSystematic(true);
					item.addAllIssues(issue);
					results.add(item);
				}
				else
				{
					issue.clear();
					for (Trade obj : obj_trades)
					{
						issue.add(obj.getIssue().getId());
					}
					item.addAllIssues(issue);
					results.add(item);
				}
			}
			else 
			{
				results.add(item);
			}
		}
		
		ExcelWriter.exportExcelFile(IConstants.EXPORT_EXCEL_FILE, results,
				IConstants.EXCEL_EXPORT_SHEET,_export_header);
		System.out.println("Export completed !");
		
	}
}
