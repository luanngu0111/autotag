package upskills.fileexport;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import resources.IConstants;
import upskills.tagprocess.ResultObj;

/**
 * @author LuanNgu
 *
 */
public class ExcelWriter {

	/**
	 * Export to xls file
	 * 
	 * @param result
	 *            result data with header
	 */
	public static void exportXLSFile(String filename, List<String[]> result, boolean isString, String sheetname) {
		int col_size = 0;
		// 1. Create an Excel file
		WritableWorkbook Wbook = null;
		try {
			Wbook = Workbook.createWorkbook(new File(filename));
			// create an Excel sheet
			WritableSheet excelSheet = Wbook.createSheet(sheetname, 0);
			Label label = null;
			String[] str = null;
			for (int i = 0; i < result.size(); i++) {
				str = result.get(i);
				col_size = str.length;
				for (int j = 0; j < col_size; j++) {
					label = new Label(j, i, str[j]);
					excelSheet.addCell(label);
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		} finally {
			if (Wbook != null) {
				try {
					Wbook.close();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (WriteException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * @param result
	 * @param sheetname
	 */
	public static void exportXLSFile(String filename, List<ResultObj> result, String sheetname) {
		int col_size = 0;
		// 1. Create an Excel file
		WritableWorkbook Wbook = null;
		try {
			Wbook = Workbook.createWorkbook(new File(filename));
			// create an Excel sheet
			WritableSheet excelSheet = Wbook.createSheet(sheetname, 0);
			Label label = null;
			ResultObj obj = null;
			for (int i = 0; i < result.size(); i++) {
				obj = result.get(i);
				List<String> conv_ob = obj.convertObj();
				for (String prop : conv_ob) {
					label = new Label(col_size++, i, prop);
					excelSheet.addCell(label);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		} finally {
			if (Wbook != null) {
				try {
					Wbook.close();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (WriteException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Export to xlsx file
	 * 
	 * @param result
	 *            result data with header. Datatype List of list of string
	 */
	public static void exportXLSXFile(String filename, List<String[]> result, boolean isString, String sheetname) {
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet(sheetname);
		int rowNum = 0;
		// System.out.println("Creating excel");
		for (String[] str : result) {
			Row row = sheet.createRow(rowNum++);
			int colNum = 0;
			for (String field : str) {
				Cell cell = row.createCell(colNum++);
				cell.setCellValue(field);

			}
		}
		try {
			FileOutputStream outputStream = new FileOutputStream(filename);
			workbook.write(outputStream);
			workbook.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void exportXLSXFile(String filename, List<ResultObj> result, String sheetname) {
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet(sheetname);
		int rowNum = 0;
		// System.out.println("Creating excel");
		for (ResultObj obj : result) {
			Row row = sheet.createRow(rowNum++);
			int colNum = 0;
			List<String> conv_ob = obj.convertObj();
			for (String field : conv_ob) {
				Cell cell = row.createCell(colNum++);
				cell.setCellValue(field);

			}
		}
		try {
			FileOutputStream outputStream = new FileOutputStream(filename);
			workbook.write(outputStream);
			workbook.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void exportExcelFile(String filename, List<String[]> result, boolean isString, String sheetname) {
		String ext = filename.substring(filename.lastIndexOf("."), filename.length());
		switch (ext) {

		case "xls":
			exportXLSFile(filename, result, true, sheetname);
			break;
		case "xlsx":
			exportXLSXFile(filename, result, true, sheetname);
			break;

		default: // Doesn't support file type
			System.out.println("This file type *." + ext + " do not support");
			break;
		}
	}

	public static void exportExcelFile(String filename, List<ResultObj> result, String sheetname) {
		String ext = filename.substring(filename.lastIndexOf("."), filename.length());
		switch (ext) {

		case "xls":
			exportXLSFile(filename, result, sheetname);
			break;
		case "xlsx":
			exportXLSXFile(filename, result, sheetname);
			break;

		default: // Doesn't support file type
			System.out.println("This file type *." + ext + " do not support");
			break;
		}
	}
}
