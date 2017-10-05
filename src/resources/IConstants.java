package resources;

public class IConstants {
	public static String HOST_NAME = "localhost";
	public static String PORT = "3306";
	public static String USER_NAME = "root";
	public static String PASSWORD = "";
	public static String DB_NAME = "recondb";
	public static String FILE_PATH = "TestResult.csv";
	public static String CSV_SPLIT = "_;_";
	public static String HEADER_TRADE = "TRADE_NB";
	public static String HEADER_FAMILY = "FAM";
	public static String HEADER_GROUP = "GROUP";
	public static String HEADER_TYPE = "TYPE";
	public static String HEADER_CURR = "CURRENCY";
	public static String HEADER_KEY_VAL = "KeyValue";
	public static String EXPORT_EXCEL_FILE = "results_autotag/ExportTest.xlsx";
	public static String EXCEL_SHEET_NAME = "Result 0";
	public static String EXCEL_EXPORT_SHEET = "TAGGING";
	public static int EXCEL_SHEET_ID = 2;
	public static String[] IGNORE_COLUMN = new String[] { "SourceName", "LineNumber" };
	public static String[] EXPORT_HEADER = new String[] { "Selected", "Trade Number", "Trade Family", "Trade Group", "Trade Type",
			"Currency", "Field", "Systematic", "Issue 1st", "Issue 2nd", "Issue 3rd", "Issue 4th", "Issue 5th",
			"Issue 6th", "Issue 7th", "Issue 8th" ,"Issue 8th" ,"Issue 9th" ,"Issue 10th" };
	public String getHOST_NAME() {
		return HOST_NAME;
	}
	public void setHOST_NAME(String hOST_NAME) {
		HOST_NAME = hOST_NAME;
	}
	public String getPORT() {
		return PORT;
	}
	public void setPORT(String pORT) {
		PORT = pORT;
	}
	public String getUSER_NAME() {
		return USER_NAME;
	}
	public void setUSER_NAME(String uSER_NAME) {
		USER_NAME = uSER_NAME;
	}
	public String getPASSWORD() {
		return PASSWORD;
	}
	public void setPASSWORD(String pASSWORD) {
		PASSWORD = pASSWORD;
	}
	public String getDB_NAME() {
		return DB_NAME;
	}
	public void setDB_NAME(String dB_NAME) {
		DB_NAME = dB_NAME;
	}
	public String getFILE_PATH() {
		return FILE_PATH;
	}
	public void setFILE_PATH(String fILE_PATH) {
		FILE_PATH = fILE_PATH;
	}
	public String getHEADER_TRADE() {
		return HEADER_TRADE;
	}
	public void setHEADER_TRADE(String hEADER_TRADE) {
		HEADER_TRADE = hEADER_TRADE;
	}
	public String getHEADER_FAMILY() {
		return HEADER_FAMILY;
	}
	public void setHEADER_FAMILY(String hEADER_FAMILY) {
		HEADER_FAMILY = hEADER_FAMILY;
	}
	public String getHEADER_GROUP() {
		return HEADER_GROUP;
	}
	public void setHEADER_GROUP(String hEADER_GROUP) {
		HEADER_GROUP = hEADER_GROUP;
	}
	public String getHEADER_TYPE() {
		return HEADER_TYPE;
	}
	public void setHEADER_TYPE(String hEADER_TYPE) {
		HEADER_TYPE = hEADER_TYPE;
	}
	public String getHEADER_CURR() {
		return HEADER_CURR;
	}
	public void setHEADER_CURR(String hEADER_CURR) {
		HEADER_CURR = hEADER_CURR;
	}
	public String getHEADER_KEY_VAL() {
		return HEADER_KEY_VAL;
	}
	public void setHEADER_KEY_VAL(String hEADER_KEY_VAL) {
		HEADER_KEY_VAL = hEADER_KEY_VAL;
	}
	public String getEXPORT_EXCEL_FILE() {
		return EXPORT_EXCEL_FILE;
	}
	public void setEXPORT_EXCEL_FILE(String eXPORT_EXCEL_FILE) {
		EXPORT_EXCEL_FILE = eXPORT_EXCEL_FILE;
	}
	public String getEXCEL_SHEET_NAME() {
		return EXCEL_SHEET_NAME;
	}
	public void setEXCEL_SHEET_NAME(String eXCEL_SHEET_NAME) {
		EXCEL_SHEET_NAME = eXCEL_SHEET_NAME;
	}
	public String getEXCEL_EXPORT_SHEET() {
		return EXCEL_EXPORT_SHEET;
	}
	public void setEXCEL_EXPORT_SHEET(String eXCEL_EXPORT_SHEET) {
		EXCEL_EXPORT_SHEET = eXCEL_EXPORT_SHEET;
	}
	public int getEXCEL_SHEET_ID() {
		return EXCEL_SHEET_ID;
	}
	public void setEXCEL_SHEET_ID(int eXCEL_SHEET_ID) {
		EXCEL_SHEET_ID = eXCEL_SHEET_ID;
	}
	public String[] getIGNORE_COLUMN() {
		return IGNORE_COLUMN;
	}
	public void setIGNORE_COLUMN(String[] iGNORE_COLUMN) {
		IGNORE_COLUMN = iGNORE_COLUMN;
	}
	public String[] getEXPORT_HEADER() {
		return EXPORT_HEADER;
	}
	public void setEXPORT_HEADER(String[] eXPORT_HEADER) {
		EXPORT_HEADER = eXPORT_HEADER;
	}
	public static String getCSV_SPLIT() {
		return CSV_SPLIT;
	}
	public static void setCSV_SPLIT(String cSV_SPLIT) {
		CSV_SPLIT = cSV_SPLIT;
	}
	
}
