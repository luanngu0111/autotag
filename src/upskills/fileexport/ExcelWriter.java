package upskills.fileexport;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.naming.spi.DirStateFactory.Result;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTTable;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTTableStyleInfo;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import resources.AutoLogger;
import resources.IConstants;
import upskills.tagprocess.ResultObj;

/**
 * @author LuanNgu
 * 
 */
public class ExcelWriter {

	private static String csv_splitter = ";";

	public static void setCsv_splitter(String csv_splitter) {
		ExcelWriter.csv_splitter = csv_splitter;
	}

	/**
	 * @param filename
	 * @param result
	 * @param header
	 * @param csvSplit
	 */
	public static void exportCSVFile(String filename, List<ResultObj> result, List<String> header, String csvSplit) {
		FileWriter writer;
		try {
			writer = new FileWriter(filename);

			StringBuilder sb = new StringBuilder();
			if (csvSplit.trim() != "" && csvSplit.trim() != null) {
				if (header != null && header.size() != 0) {
					
					
					sb.append(String.join(csvSplit, header));
					sb.append("\n");
				}
				for (ResultObj obj : result) {
					sb.append(String.join(csvSplit,obj.convertObj()));
					sb.append("\n");
				}
			} else {
				if (header != null && header.size() != 0) {
					String[] head = (String[]) header.toArray();

					sb.append(String.join(csv_splitter, header));
					sb.append("\n");
				}
				for (ResultObj obj : result) {
					String[] str = (String[]) obj.convertObj().toArray();
					sb.append(String.join(csv_splitter,obj.convertObj()));
					sb.append("\n");
				}
			}
			writer.append(sb.toString());
			writer.flush();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/** Export to csv file 
	 * @param filename
	 * @param result
	 * @param header
	 * @param csvSplit
	 * @param isString
	 */
	public static void exportCSVFile(String filename, List<String[]> result, List<String> header, String csvSplit, boolean isString) {
		FileWriter writer;
		try {
			writer = new FileWriter(filename);

			StringBuilder sb = new StringBuilder();
			if (csvSplit.trim() != "" && csvSplit.trim() != null) {
				if (header != null && header.size() != 0) {
					
					
					sb.append(String.join(csvSplit, header));
					sb.append("\n");
				}
				for (String[] obj : result) {
					sb.append(String.join(csvSplit,obj));
					sb.append("\n");
				}
			} else {
				if (header != null && header.size() != 0) {
					String[] head = (String[]) header.toArray();

					sb.append(String.join(csvSplit, header));
					sb.append("\n");
				}
				for (String[] obj : result) {
					sb.append(String.join(csvSplit,obj));
					sb.append("\n");
				}
			}
			writer.append(sb.toString());
			writer.flush();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

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
	public static void exportXLSXFile(String filename, List<String[]> result, boolean isString, String sheetname,
			List<String> column_header) {
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet(sheetname);
		int rowNum = 0;
		// System.out.println("Creating excel");
		Row headrow = sheet.createRow(rowNum++);
		int colNum = 0;
		for (String header : column_header) {
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

	/**
	 * Format border and color background
	 * 
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

	public static void formatFontCell(XSSFWorkbook wb, CellStyle cell_style) {
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

	/**
	 * @param filename
	 * @param result
	 * @param sheetname
	 * @param column_header
	 */
	public static void exportXLSXFile(String filename, List<ResultObj> result, String sheetname,
			List<String> column_header) {
		AutoLogger log  = AutoLogger.getInstance();
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet(sheetname);
		int rowNum = 0;
		int colNum = 0;
		System.out.println("Creating excel ... XLsX");
		int pivot_start = column_header
				.indexOf("Issue 1st"); /*
										 * the pivot position to specify where
										 * start/end cell is
										 */
		int pivot_end = column_header.indexOf("Issue 10th");
		if (pivot_start != -1) {
			/*
			 * Create header 1
			 */
			CellRangeAddress cra1 = new CellRangeAddress(0, 0, 0, pivot_start - 2);
			sheet.addMergedRegion(cra1);

			CellRangeAddress cra2 = new CellRangeAddress(0, 0, pivot_start - 1, pivot_end);
			sheet.addMergedRegion(cra2);

			CellRangeAddress cra3 = new CellRangeAddress(0, 0, pivot_end + 1, pivot_end + 2);
			sheet.addMergedRegion(cra3);

			Row headrow1 = sheet.createRow(rowNum++);
			Cell cell1 = headrow1.createCell(0);
			CellStyle cell_style1 = workbook.createCellStyle();
			formatCell(workbook, cell_style1, true, true, true, HSSFColor.ORANGE.index);
			formatFontCell(workbook, cell_style1);
			cell1.setCellValue("MisMatch");
			cell1.setCellStyle(cell_style1);
			formatMergedCell(sheet, cell1, cra1, true, false, 0);

			Cell cell2 = headrow1.createCell(pivot_start - 1);
			cell2.setCellValue("Auto-Tagging");
			CellStyle cell_style2 = workbook.createCellStyle();
			formatCell(workbook, cell_style2, true, true, true, HSSFColor.ROYAL_BLUE.index);
			formatFontCell(workbook, cell_style2);
			cell2.setCellStyle(cell_style2);
			formatMergedCell(sheet, cell2, cra2, true, false, 0);

			Cell cell3 = headrow1.createCell(pivot_end + 1);
			cell3.setCellValue("User Input");
			CellStyle cell_style3 = workbook.createCellStyle();
			formatCell(workbook, cell_style3, true, true, true, HSSFColor.SEA_GREEN.index);
			formatFontCell(workbook, cell_style3);
			cell3.setCellStyle(cell_style3);
			formatMergedCell(sheet, cell3, cra3, true, false, 0);

			/*
			 * Create header 2
			 */
			Row headrow = sheet.createRow(rowNum++);
			CellStyle cell_head_style = workbook.createCellStyle();
			formatFontCell(workbook, cell_head_style);
			formatCell(workbook, cell_head_style, true, true, false, (short) 0);
			for (String header : column_header) {
				Cell cell = headrow.createCell(colNum++);
				cell.setCellValue(header);
				cell.setCellStyle(cell_head_style);

			}

		} else {
			/*
			 * Create header
			 */
			Row headrow = sheet.createRow(rowNum++);
			CellStyle cell_head_style = workbook.createCellStyle();
			formatFontCell(workbook, cell_head_style);
			formatCell(workbook, cell_head_style, true, true, false, (short) 0);
			for (String header : column_header) {
				Cell cell = headrow.createCell(colNum++);
				cell.setCellValue(header);
				cell.setCellStyle(cell_head_style);

			}
		}
		// Export content
		CellStyle cell_style = workbook.createCellStyle();
		formatCell(workbook, cell_style, true, true, false, (short) 0);
		List<String> conv_ob = new ArrayList<String>();
		for (ResultObj obj : result) {
			Row row = sheet.createRow(rowNum++);
			colNum = 0;
			conv_ob.clear();
			conv_ob = obj.convertObj();

			for (String field : conv_ob) {

				if (field == null || field.equals("0")) // if this column
														// doesn't contain value
					continue;
				Cell cell = row.createCell(colNum++);
				cell.setCellValue(field);

			}
			// System.out.println("Print row ... " + rowNum);
		}

		// Format exported table
		System.out.println("...Formating content");
		for (int i = 2; i <= sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);
			for (Cell cell : row) {
				cell.setCellStyle(cell_style);
			}
		}

		// Export to file
		try {
			FileOutputStream outputStream = new FileOutputStream(filename);
			workbook.write(outputStream);
			workbook.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			log.error(Arrays.toString(e.getStackTrace()));
		} catch (IOException e) {
			e.printStackTrace();
			log.error(Arrays.toString(e.getStackTrace()));
		}
	}

	public static void exportExcelFile(String filename, List<String[]> result, boolean isString, String sheetname,
			List<String> header) {
		String ext = filename.substring(filename.lastIndexOf("."), filename.length());
		switch (ext) {

		case "xls":
			exportXLSFile(filename, result, true, sheetname);
			break;
		case "xlsx":
			exportXLSXFile(filename, result, true, sheetname, header);
			break;

		default: // Doesn't support file type
			System.out.println("This file type *." + ext + " do not support");
			break;
		}
	}

	public static void exportExcelFile(String filename, List<ResultObj> result, String sheetname, List<String> header) {
		String ext = filename.substring(filename.lastIndexOf(".") + 1, filename.length());
		switch (ext) {
		case "csv":
			exportCSVFile(filename, result, header, csv_splitter);
			break;
		case "xls":
			exportXLSFile(filename, result, sheetname);
			break;
		case "xlsx":
			exportXLSXFile(filename, result, sheetname, header);
			break;

		default: // Doesn't support file type
			System.out.println("This file type *." + ext + " do not support");
			break;
		}
	}
}