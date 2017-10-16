package resources;

public interface IConstants {
	public static String HOST_NAME = "localhost";
	public static String PORT = "3306";
	public static String USER_NAME = "root";
	public static String PASSWORD = "root";
	public static String DB_NAME = "recondb";
	public static String FILE_PATH = "TestResult.xlsx";
	public static String HEADER_TRADE = "TRADE_NB";
	public static String HEADER_FAMILY = "FAM";
	public static String HEADER_GROUP = "GROUP";
	public static String HEADER_TYPE = "TYPE";
	public static String HEADER_CURR = "CURRENCY";
	public static String HEADER_KEY_VAL = "KeyValue";
	public static String EXPORT_EXCEL_FILE = "export/ExportTest.xlsx";
	public static String EXCEL_SHEET_NAME = "Result 0";
	public static String EXCEL_EXPORT_SHEET = "TAGGING";
	public static int EXCEL_SHEET_ID = 2;
	public static String[] IGNORE_COLUMN = new String[] { "SourceName", "LineNumber" };
	public static String[] EXPORT_HEADER = new String[] { "Selected", "Trade Number", "Trade Family", "Trade Group", "Trade Type",
			"Currency", "Field", "Systematic", "Issue 1st", "Issue 2nd", "Issue 3rd", "Issue 4th", "Issue 5th",
			"Issue 6th", "Issue 7th", "Issue 8th" ,"Issue 8th" ,"Issue 9th" ,"Issue 10th" };
}
