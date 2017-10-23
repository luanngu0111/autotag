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
 * @author LuanNgu
 * This class present Importing Tag result from User to DB. Base on format of result file of TagProcess. 
 */
public class ImportTagProcess {

	public ImportTagProcess() {
		// TODO Auto-generated constructor stub
	}
	public static void main(String[] args)
	{
		ImportToDb(IConstants.EXPORT_EXCEL_FILE);
		
	}
	public static void ImportToDb(String filename) 
	{
		List<String[]> tag_result = null;
		try {
			System.out.println("Reading ... ");
			tag_result = ExcelReader.readXLSXFile(filename, 0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*
		 * Remove header
		 */
		tag_result.remove(1);
		tag_result.remove(2);
		
		if (tag_result != null && tag_result.size()!=0)
		{
			System.out.println("Start importing to DB ... ");
			Trade trade =new Trade();
			Issue issue = new Issue();
			HbnIssueDao hbn_issue = HbnIssueDao.getInstance();
			HbnTradeDao hbn_trade = HbnTradeDao.getInstance();
			int size = tag_result.size();
			int step = 0;
			for (String[] row : tag_result) {
				System.out.println("Importing ... " + step * 100 / size);
				if (row[0].trim() != "" && (row[0].trim().equals("X") || row[0].trim().equals("Y"))) {

					TradeId trade_id = new TradeId(row[6], Integer.parseInt(row[1]));
					trade.setId(trade_id);
					trade.setCurrency(row[5]);
					trade.setInstrument("");
					trade.setPortfolio("");
					trade.setTrnFmly(row[2]);
					trade.setTrnGrp(row[3]);
					trade.setTrnType(row[4]);
					trade.setTrnStatus("LIVE");
					int i = 0;
					for (String col : row) {
						if (i >= 8 && col.trim() != "" && col.trim() != null) {
							int id = (int) Double.parseDouble(col);
							issue = hbn_issue.getIssueById(id);
							trade.setIssue(issue);
							if (hbn_trade.getTradeByNbAndField(trade_id) == null){
								hbn_trade.create(trade);
								System.out.println("Trade and issue imported successfully ! ");
							}
							else {
								System.out.println("Trade and issue have been existed already ");
							}
						}
						i++;
					}

				}
				step++;

			}
			System.out.println("Import successfully ! ");
			hbn_issue.closeCurrentSession();
			hbn_trade.closeCurrentSession();
		}
		else {
			System.out.println("Nothing to import");
		}
		
	}

}
