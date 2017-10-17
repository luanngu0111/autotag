package upskills.fileexport;

import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeUtil;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.sun.prism.paint.Color;

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
	public static void exportXLSXFile(String filename, List<String[]> result, boolean isString, String sheetname) {
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

	/** Format border and color background
	 * @param wb
	 * @param center
	 * @param border
	 * @param color
	 * @param color_val
	 * @return
	 */
	public static void formatCell(XSSFWorkbook wb, CellStyle cell_style, boolean center, boolean border, boolean color,
			short color_val) {
		if (center)
			cell_style.setAlignment(HorizontalAlignment.CENTER);
		if (border) {
			cell_style.setBorderBottom(BorderStyle.MEDIUM);
			cell_style.setBorderTop(BorderStyle.MEDIUM);
			cell_style.setBorderRight(BorderStyle.MEDIUM);
			cell_style.setBorderLeft(BorderStyle.MEDIUM);
		}
		if (color) {
			
			cell_style.setFillForegroundColor(color_val);
			cell_style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			
		}
	}

	public static void formatFontCell(XSSFWorkbook wb,CellStyle cell_style)
	{
		org.apache.poi.ss.usermodel.Font font_style = wb.createFont();
		font_style.setBold(true);
		cell_style.setFont(font_style);
	}
	
	public static void formatMergedCell(XSSFSheet sheet, Cell cell, CellRangeAddress merged_cell, boolean border,
			boolean color, int color_val) {
		if (border) {
			RegionUtil.setBorderBottom(cell.getCellStyle().getBorderRightEnum(), merged_cell, sheet);
			RegionUtil.setBorderLeft(cell.getCellStyle().getBorderRightEnum(), merged_cell, sheet);
			RegionUtil.setBorderRight(cell.getCellStyle().getBorderRightEnum(), merged_cell, sheet);
			RegionUtil.setBorderTop(cell.getCellStyle().getBorderRightEnum(), merged_cell, sheet);
		}
	}

	public static void exportXLSXFile(String filename, List<ResultObj> result, String sheetname) {
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet(sheetname);
		int rowNum = 0;
		System.out.println("Creating excel ... XLsX");
		// Create header 1
		CellRangeAddress cra1 = new CellRangeAddress(0, 0, 0, 6);
		sheet.addMergedRegion(cra1);
		
		CellRangeAddress cra2 = new CellRangeAddress(0, 0, 7, 18);
		sheet.addMergedRegion(cra2);
		
		CellRangeAddress cra3 = new CellRangeAddress(0, 0, 19, 20);
		sheet.addMergedRegion(cra3);

		Row headrow1 = sheet.createRow(rowNum++);
		Cell cell1 = headrow1.createCell(0);
		CellStyle cell_style1 = workbook.createCellStyle();
		formatCell(workbook, cell_style1, true, true, true,  HSSFColor.ORANGE.index);
		formatFontCell(workbook,cell_style1);
		cell1.setCellValue("MisMatch");
		cell1.setCellStyle(cell_style1);
		formatMergedCell(sheet, cell1, cra1, true, false, 0);

		Cell cell2 = headrow1.createCell(7);
		cell2.setCellValue("Auto-Tagging");
		CellStyle cell_style2 = workbook.createCellStyle();
		formatCell(workbook, cell_style2, true, true, true,  HSSFColor.ROYAL_BLUE.index);
		formatFontCell(workbook,cell_style2);
		cell2.setCellStyle(cell_style2);
		formatMergedCell(sheet, cell2, cra2, true, false, 0);

		
		Cell cell3 = headrow1.createCell(19);
		cell3.setCellValue("User Input");
		CellStyle cell_style3 = workbook.createCellStyle();
		formatCell(workbook, cell_style3, true, true, true,  HSSFColor.SEA_GREEN.index);
		formatFontCell(workbook,cell_style3);
		cell3.setCellStyle(cell_style3);
		formatMergedCell(sheet, cell3, cra3, true, false, 0);
		
		
		int colNum = 0;

		// Create header 2
		Row headrow = sheet.createRow(rowNum++);
		CellStyle cell_head_style = workbook.createCellStyle();
		formatFontCell(workbook,cell_head_style);
		formatCell(workbook, cell_head_style, true, true, false,  (short) 0);
		for (String header : IConstants.EXPORT_HEADER) {
			Cell cell = headrow.createCell(colNum++);
			cell.setCellValue(header);
			cell.setCellStyle(cell_head_style);

		}

		CellStyle cell_style = workbook.createCellStyle();
		formatCell(workbook, cell_style, true, true, false,  (short) 0);
		for (ResultObj obj : result) {
			Row row = sheet.createRow(rowNum++);
			colNum = 0;
			List<String> conv_ob = obj.convertObj();
			for (String field : conv_ob) {
				sheet.autoSizeColumn(colNum);
				Cell cell = row.createCell(colNum++);
				cell.setCellValue(field);
				cell.setCellStyle(cell_style);

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
		String ext = filename.substring(filename.lastIndexOf(".") + 1, filename.length());
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