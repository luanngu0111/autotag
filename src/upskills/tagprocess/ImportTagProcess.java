package upskills.tagprocess;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import resources.AutoLogger;
import resources.DBUtils;
import resources.IConstants;
import resources.Utils;
import upskills.database.dao.impl.HbnIssueDao;
import upskills.database.model.Issue;
import upskills.database.model.Trade;
import upskills.database.model.TradeId;
import upskills.fileimport.ExcelReader;

/**
 * @author LuanNgu {@docRoot} This class present Importing Tag result from User
 *         to DB. Base on format of result file of TagProcess.
 */
public class ImportTagProcess {

	public ImportTagProcess() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		try {
			ImportToDb(IConstants.EXPORT_EXCEL_FILE);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void ImportToDb_Old(String filename) throws Exception {
		AutoLogger log = AutoLogger.getInstance();
		List<String[]> tag_result = null;
		List<Trade> inserted_trades = new ArrayList<Trade>();
		HashMap<String, Trade> trade_record = new HashMap<String, Trade>();
		try {
			System.out.println("Reading ... ");
			tag_result = ExcelReader.readXLSXFile(filename, 0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.writeInLog(Arrays.toString(e.getStackTrace()));
		}

		/*
		 * Determine header location
		 */
		String[] header = tag_result.get(1);
		int nb_ind = Arrays.asList(header).indexOf("Trade Number");
		int fam_ind = Arrays.asList(header).indexOf("Family");
		int grp_ind = Arrays.asList(header).indexOf("Group");
		int typ_ind = Arrays.asList(header).indexOf("Type");
		int cur_ind = Arrays.asList(header).indexOf("Currency");
		int port_ind = Arrays.asList(header).indexOf("Portfolio");
		int ins_ind = Arrays.asList(header).indexOf("Instrument");
		int sts_ind = Arrays.asList(header).indexOf("Status");
		int field_ind = Arrays.asList(header).indexOf(IConstants.EXPORT_HEADER_NEUTRAL[1]);

		/*
		 * Remove header
		 */
		tag_result.remove(0);
		tag_result.remove(0);

		if (tag_result != null && tag_result.size() != 0) {
			System.out.println("Start importing to DB ... ");

			HbnIssueDao hbn_issue = HbnIssueDao.getInstance();

			int size = tag_result.size();
			int step = 0;

			for (String[] row : tag_result) {
				System.out.println("Fetching ... " + ++step * 100 / size + "% of " + size);
				log.writeInLog("Fetching ... " + step * 100 / size + "% of " + size);
				Trade trade = new Trade();
				if (row[0].trim() != "" && (row[0].trim().equals("X") || row[0].trim().equals("Y"))) {

					TradeId trade_id = new TradeId();
					if (nb_ind != -1 && field_ind != -1) {
						trade_id = new TradeId(row[field_ind], Utils.parseTradeNumber(row[nb_ind]));
						trade.setId(trade_id);
					}
					if (cur_ind != -1)
						trade.setCurrency(row[cur_ind]);

					if (ins_ind != -1) {
						trade.setInstrument(row[ins_ind]);
					}
					if (port_ind != -1) {
						trade.setPortfolio(row[port_ind]);
					}
					if (fam_ind != -1) {
						trade.setTrnFmly(row[fam_ind]);
					}
					if (grp_ind != -1) {
						trade.setTrnGrp(row[grp_ind]);
					}
					if (typ_ind != -1) {
						trade.setTrnType(row[typ_ind]);
					}
					if (sts_ind != -1) {
						trade.setTrnStatus(row[sts_ind]);
					} else {
						trade.setTrnStatus("LIVE");
					}
					int i = 0;
					int issue_col = Arrays.asList(header).indexOf("Issue 1st"); // Get
																				// start
																				// index
																				// of
																				// issue
																				// columns
					for (String col : row) {
						if (i >= issue_col && col.trim() != "" && col.trim() != null) {
							int id = Utils.parseTradeNumber(col);
							Issue issue = new Issue();
							issue = hbn_issue.getIssueById(id);
							trade.setIssue(issue);
							trade_record.put(trade_id.getField() + "-" + trade_id.getNb(), trade);
						}
						i++;
					}

				}

			}
			step = 0;
			size = trade_record.size();
			if (size > 0) {
				List<Trade> trades = new ArrayList<>(trade_record.values());
				int result = DBUtils.insertTrades(trades);
				if (result != -1) {
					System.out.println("Importing is successful !");
					log.endProg("Importing successful !");
				} else {
					System.out.println("Importing failed !");
					log.error("Importing failed !");
				}
			}

		} else {
			System.out.println("Nothing to import");
		}

	}

	public static void ImportToDb(String filename) throws Exception {
		AutoLogger log = AutoLogger.getInstance();
		List<String[]> tag_result = null;
		List<Integer> selectedIndex = new ArrayList<Integer>();
		try {
			System.out.println("Reading ... ");
			tag_result = ExcelReader.readXLSXFile(filename, 0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.writeInLog(Arrays.toString(e.getStackTrace()));
		}
		String[] header = tag_result.get(1);
		int nb_ind = Arrays.asList(header).indexOf("Trade Number");
		int fam_ind = Arrays.asList(header).indexOf("Family");
		int grp_ind = Arrays.asList(header).indexOf("Group");
		int typ_ind = Arrays.asList(header).indexOf("Type");
		int cur_ind = Arrays.asList(header).indexOf("Currency");
		int port_ind = Arrays.asList(header).indexOf("Portfolio");
		int ins_ind = Arrays.asList(header).indexOf("Instrument");
		int sts_ind = Arrays.asList(header).indexOf("Status");
		int field_ind = Arrays.asList(header).indexOf(IConstants.EXPORT_HEADER_NEUTRAL[1]);
		int issue_col = Arrays.asList(header)
				.indexOf("Issue 1st"); /*
										 * Get start index of issue columns
										 */
		int size_of_file = 0;
		HbnIssueDao hbn_issue = HbnIssueDao.getInstance();

		List<Trade> selected_trades = new ArrayList<Trade>();
		/*
		 * Remove header
		 */
		tag_result.remove(0);
		tag_result.remove(0);

		size_of_file = tag_result.size();
		int step = 0;
		if (tag_result != null && tag_result.size() != 0) {
			for (int i = 0; i < size_of_file; i++) {
				String[] row = tag_result.get(i);
				if (row[0].trim() != "" && (row[0].trim().equals("X") || row[0].trim().equals("Y"))) {
					selectedIndex.add(i);
				}
			}

			for (int index : selectedIndex) {
				System.out.println("Fetching ... " + ++step * 100 / size_of_file + "% of " + size_of_file);
				String[] row = tag_result.get(index);
				Trade trade = new Trade();
				TradeId trade_id = new TradeId();
				if (nb_ind != -1 && field_ind != -1) {
					trade_id = new TradeId(row[field_ind], Utils.parseTradeNumber(row[nb_ind]));
					trade.setId(trade_id);
				}
				if (cur_ind != -1)
					trade.setCurrency(row[cur_ind]);

				if (ins_ind != -1) {
					trade.setInstrument(row[ins_ind]);
				}
				if (port_ind != -1) {
					trade.setPortfolio(row[port_ind]);
				}
				if (fam_ind != -1) {
					trade.setTrnFmly(row[fam_ind]);
				}
				if (grp_ind != -1) {
					trade.setTrnGrp(row[grp_ind]);
				}
				if (typ_ind != -1) {
					trade.setTrnType(row[typ_ind]);
				}
				if (sts_ind != -1) {
					trade.setTrnStatus(row[sts_ind]);
				} else {
					trade.setTrnStatus("LIVE");
				}

				for (int i = issue_col; i < row.length; i++) {
					if (row[i].trim() != "" && row[i].trim() != null) {
						int id = Utils.parseTradeNumber(row[i]);
						Issue issue = new Issue();
						issue = hbn_issue.getIssueById(id);
						trade.setIssue(issue);
					}
				}
				selected_trades.add(trade);
			}

			int result = DBUtils.insertMultipleTrade(selected_trades);
			if (result == -1) {
				System.out.println("Import failed");
			} else if (result == 0) {
				System.out.println("Nothing imported");
			} else {
				System.out.println("Import successful");
			}
		} else {
			System.out.println("Nothing imported");
		}
	}
}
