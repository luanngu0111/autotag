package upskills.fileimport;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import jxl.read.biff.BiffException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {
	private String csvFile;
	private BufferedReader br = null;
	private String line = "";
	private static String csvSplitBy = "_;_";
	private String irrelevantColumns;
	private String filePath;

	/** Read CSV file
	 * @param filename path of name 
	 * @return Array list of data 
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static List<String[]> readCSVFile(String filename)
			throws FileNotFoundException, IOException {
		List<String[]> lines = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			String line = br.readLine();
			while (line != null) {
				String[] cols = line.split(csvSplitBy);
				lines.add(cols);
				line = br.readLine();
			}
		}
		return lines;
	}

	/** Read data in sheet of XLSX file
	 * @param datatypeSheet data type of sheet
	 * @return Array list of data in sheet
	 */
	private static List<String[]> readSheetXLSX(Sheet datatypeSheet) {
		Iterator<Row> iterator = datatypeSheet.iterator();
		List<String[]> lines = new ArrayList<>();
		while (iterator.hasNext()) {

			Row currentRow = iterator.next();
			Iterator<Cell> cellIterator = currentRow.iterator();
			StringBuilder str = new StringBuilder();
			while (cellIterator.hasNext()) {

				Cell currentCell = cellIterator.next();
				// getCellTypeEnum shown as deprecated for version 3.15
				// getCellTypeEnum ill be renamed to getCellType starting
				// from version 4.0
				if (currentCell.getCellTypeEnum() == CellType.STRING) {
					str.append(currentCell.getStringCellValue()).append(
							csvSplitBy);
				} else if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
					str.append(currentCell.getNumericCellValue()).append(
							csvSplitBy);
				}

			}
			lines.add(str.toString().split(csvSplitBy));
		}
		return lines;
	}

	/** Read a sheet in XLSX file by sheet name
	 * @param filename path of file
	 * @param sheetname name of sheet
	 * @return Array list of data table
	 * @throws IOException
	 */
	public static List<String[]> readXLSXFile(String filename, String sheetname)
			throws IOException {
		List<String[]> lines = new ArrayList<>();
		Workbook workbook = null;
		try {

			FileInputStream excelFile = new FileInputStream(new File(filename));
			workbook = new XSSFWorkbook(excelFile);

			// Get data sheet by sheet name or index of sheet.
			Sheet datatypeSheet = workbook.getSheet(sheetname);
			lines = readSheetXLSX(datatypeSheet);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (workbook != null) {
				workbook.close();
			}
		}

		return lines;

	}

	/** Read a sheet in XLSX file by index of sheet
	 * @param filename path of file
	 * @param sheet_id index of file
	 * @return Array list of data table
	 * @throws IOException
	 */
	public static List<String[]> readXLSXFile(String filename, int sheet_id)
			throws IOException {
		List<String[]> lines = new ArrayList<>();
		Workbook workbook = null;
		try {

			FileInputStream excelFile = new FileInputStream(new File(filename));
			workbook = new XSSFWorkbook(excelFile);

			// Get data sheet by sheet name or index of sheet.
			Sheet datatypeSheet = workbook.getSheetAt(sheet_id);
			lines = readSheetXLSX(datatypeSheet);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (workbook != null) {
				workbook.close();
			}
		}

		return lines;

	}

	/** Read data in a sheet of XLS file
	 * @param sheet: data type of sheet
	 * @return Array LIst of data in sheet
	 */
	private static List<String[]> readSheetXLS(jxl.Sheet sheet)
	{
		List<String[]> lines = new ArrayList<>();
		int row_num = sheet.getRows();
		int col_num = sheet.getColumns();

		for (int i = 0; i < row_num; i++) {
			String[] row = new String[col_num];
			for (int j = 0; j < col_num; j++) {
				jxl.Cell cell = sheet.getCell(j, i);
				row[j] = cell.getContents();
			}
			lines.add(row);
		}
		return lines;
	}
	/** Read a sheet in XLS file by index of sheet
	 * @param filename
	 * @param sheet_id
	 * @return Array list of data table
	 */
	public static List<String[]> readXLSFile(String filename, int sheet_id) {
		jxl.Workbook workbook = null;
		List<String[]> lines = new ArrayList<>();
		try {
			workbook = jxl.Workbook.getWorkbook(new File(filename));
			jxl.Sheet sheet = workbook.getSheet(sheet_id);
			lines = readSheetXLS(sheet);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (BiffException e) {
			e.printStackTrace();
		} finally {
			if (workbook != null) {
				workbook.close();
			}
		}

		return lines;
	}
	
	/** Read a sheet in XLS file by sheet name
	 * @param filename: path of file
	 * @param sheet_name: name of sheet
	 * @return Array list of data table
	 */
	public static List<String[]> readXLSFile(String filename, String sheet_name) {
		jxl.Workbook workbook = null;
		List<String[]> lines = new ArrayList<>();
		try {
			workbook = jxl.Workbook.getWorkbook(new File(filename));
			jxl.Sheet sheet = workbook.getSheet(sheet_name);
			lines = readSheetXLS(sheet);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (BiffException e) {
			e.printStackTrace();
		} finally {
			if (workbook != null) {
				workbook.close();
			}
		}

		return lines;
	}

	/** Read all type of excel file: xlsx, xls, csv
	 * @param filename: path of file
	 * @param sheetname: name of sheet 
	 * @return Array list of data table
	 * @throws IOException
	 */
	public static List<String[]> readExcelFile(String filename,
			String sheetname) throws IOException {
		String ext = filename.substring(filename.lastIndexOf(".")+1,
				filename.length());
		List<String[]> lines = new ArrayList<>();
		switch (ext) {
		case "csv":
			lines = readCSVFile(filename);
			break;
		case "xls":
			lines = readXLSFile(filename, sheetname);
			break;
		case "xlsx":
			lines = readXLSXFile(filename, sheetname);
			break;

		default:
			break;
		}
		return lines;

	}

	/** Read all type of excel file : xlsx, xls, csv
	 * @param filename: path of file
	 * @param sheet_id: index of sheet
	 * @return: Array list of data table
	 * @throws IOException
	 */
	public static List<String[]> readExcelFile(String filename,
			int sheet_id) throws IOException {
		String ext = filename.substring(filename.lastIndexOf("."),
				filename.length());
		List<String[]> lines = new ArrayList<>();
		switch (ext) {
		case "csv":
			lines = readCSVFile(filename);
			break;
		case "xls":
			lines = readXLSFile(filename, sheet_id);
			break;
		case "xlsx":
			lines = readXLSXFile(filename, sheet_id);
			break;

		default:
			break;
		}
		return lines;

	}

}
