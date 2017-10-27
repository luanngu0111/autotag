package upskills.tagprocess;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import resources.IConstants;
import upskills.database.dao.impl.HbnIssueDao;
import upskills.database.dao.impl.HbnTradeDao;
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
		ImportToDb(IConstants.EXPORT_EXCEL_FILE);

	}

	public static void ImportToDb(String filename) {
		List<String[]> tag_result = null;
		try {
			System.out.println("Reading ... ");
			tag_result = ExcelReader.readXLSXFile(filename, 0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		int field_ind = Arrays.asList(header).indexOf(IConstants.EXPORT_HEADER_NEUTRAL[1]);

		/*
		 * Remove header
		 */
		tag_result.remove(0);
		tag_result.remove(0);

		if (tag_result != null && tag_result.size() != 0) {
			System.out.println("Start importing to DB ... ");
			Trade trade = new Trade();
			Issue issue = new Issue();
			HbnIssueDao hbn_issue = HbnIssueDao.getInstance();
			HbnTradeDao hbn_trade = HbnTradeDao.getInstance();
			int size = tag_result.size();
			int step = 0;
			for (String[] row : tag_result) {
				// System.out.println("Importing ... " + step * 100 / size +
				// "%");
				if (row[0].trim() != "" && (row[0].trim().equals("X") || row[0].trim().equals("Y"))) {

					TradeId trade_id = null;
					if (nb_ind != -1 && field_ind != -1) {
						trade_id = new TradeId(row[field_ind], Integer.parseInt(row[nb_ind]));
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
					trade.setTrnStatus("LIVE");
					int i = 0;
					int issue_col = Arrays.asList(header).indexOf("Issue 1st"); // Get
																									// start
																									// index
																									// of
																									// issue
																									// columns
					for (String col : row) {
						if (i >= issue_col && col.trim() != "" && col.trim() != null) {
							int id = (int) Double.parseDouble(col);
							issue = hbn_issue.getIssueById(id);
							trade.setIssue(issue);
							if (hbn_trade.getTradeByNbAndField(trade_id) == null) {
								hbn_trade.create(trade);
								System.out.println(String.format("%s and %s imported successfully ! ", row[nb_ind],
										row[field_ind]));
							} else {
								System.out.println(String.format("%s and %s have been existed already ", row[nb_ind],
										row[field_ind]));
							}
						}
						i++;
					}

				}
				step++;

			}
		} else {
			System.out.println("Nothing to import");
		}

	}

}
