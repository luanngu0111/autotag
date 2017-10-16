package upskills.fileexport;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
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
	public static void exportXLSFile(String filename, List<String[]> result,
			boolean isString, String sheetname) {
		int col_size = 0;
		// 1. Create an Excel file
		WritableWorkbook Wbook = null;
		try {
			Wbook = Workbook.createWorkbook(new File(filename));
			// create an Excel sheet
			WritableSheet excelSheet = Wbook.createSheet(sheetname, 0);
			Label label = null;
			String[] str = null;
			for (String header : IConstants.EXPORT_HEADER) {
				label = new Label(col_size++, 0, header);
				excelSheet.addCell(label);
			}
			col_size = 0;

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
	public static void exportXLSFile(String filename, List<ResultObj> result,
			String sheetname) {
		int col_size = 0;
		// 1. Create an Excel file
		WritableWorkbook Wbook = null;
		try {
			Wbook = Workbook.createWorkbook(new File(filename));
			// create an Excel sheet
			WritableSheet excelSheet = Wbook.createSheet(sheetname, 0);
			Label label = null;
			ResultObj obj = null;
			for (String header : IConstants.EXPORT_HEADER) {
				label = new Label(col_size++, 0, header);
				excelSheet.addCell(label);
			}
			col_size = 0;
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
	public static void exportXLSXFile(String filename, List<String[]> result,
			boolean isString, String sheetname) {
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet(sheetname);
		int rowNum = 0;
		// System.out.println("Creating excel");
		Row headrow = sheet.createRow(rowNum++);
		int colNum = 0;
		for (String header : IConstants.EXPORT_HEADER) {
			Cell cell = headrow.createCell(colNum++);
			cell.setCellValue(header);

		}
		for (String[] str : result) {
			Row row = sheet.createRow(rowNum++);
			colNum = 0;
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

	public static CellStyle formatCell(XSSFWorkbook wb, boolean center,
			boolean border, boolean color, int color_val) {
		CellStyle cell_style = wb.createCellStyle();
		if (center)
			cell_style.setAlignment(HorizontalAlignment.CENTER);
		if (border) {
			cell_style.setBorderBottom(BorderStyle.MEDIUM);
			cell_style.setBorderTop(BorderStyle.MEDIUM);
			cell_style.setBorderRight(BorderStyle.MEDIUM);
			cell_style.setBorderLeft(BorderStyle.MEDIUM);
		}
		return cell_style;
	}

	public static void exportXLSXFile(String filename, List<ResultObj> result,
			String sheetname) {
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet(sheetname);
		int rowNum = 0;
		System.out.println("Creating excel ... XLsX");
		// Create header 1
		CellRangeAddress cra1 = new CellRangeAddress(0, 0, 0, 6);
		sheet.addMergedRegion(cra1);
		CellRangeAddress cra2 = new CellRangeAddress(0, 0, 7, 17);
		sheet.addMergedRegion(cra2);
		Row headrow1 = sheet.createRow(rowNum++);
		Cell cell1 = headrow1.createCell(0);
		cell1.setCellValue("MisMatch");
		cell1.setCellStyle(formatCell(workbook, true, true, false, 0));
		Cell cell2 = headrow1.createCell(7);
		cell2.setCellValue("Auto-Tagging");
		cell2.setCellStyle(formatCell(workbook, true, true, false, 0));

		int colNum = 0;

		// Create header 2
		Row headrow = sheet.createRow(rowNum++);
		for (String header : IConstants.EXPORT_HEADER) {
			Cell cell = headrow.createCell(colNum++);
			cell.setCellValue(header);
			cell.setCellStyle(formatCell(workbook, false, true, false, 0));

		}

		for (ResultObj obj : result) {
			Row row = sheet.createRow(rowNum++);
			colNum = 0;
			List<String> conv_ob = obj.convertObj();
			for (String field : conv_ob) {
				sheet.autoSizeColumn(colNum);
				Cell cell = row.createCell(colNum++);
				cell.setCellValue(field);
				cell.setCellStyle(formatCell(workbook, false, true, false, 0));
				
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

	public static void exportExcelFile(String filename, List<String[]> result,
			boolean isString, String sheetname) {
		String ext = filename.substring(filename.lastIndexOf("."),
				filename.length());
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

	public static void exportExcelFile(String filename, List<ResultObj> result,
			String sheetname) {
		String ext = filename.substring(filename.lastIndexOf(".") + 1,
				filename.length());
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
