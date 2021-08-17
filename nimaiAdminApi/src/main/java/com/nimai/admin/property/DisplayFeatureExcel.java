package com.nimai.admin.property;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.nimai.admin.payload.DisplayFeatureRequest;

public class DisplayFeatureExcel {
	static String SHEET = "displaycountry";
	static String[] HEADERs = { "Country", "Average Amount", "Ccy" };

	public static List<DisplayFeatureRequest> excelToDisplayReq(InputStream inputStream) {
		System.out.println("Inside Conversion");
		try {
			Workbook workbook = new XSSFWorkbook(inputStream);

			Sheet sheet = workbook.getSheet(SHEET);
			Iterator<Row> rows = sheet.iterator();

			List<DisplayFeatureRequest> disFeatureList = new ArrayList<DisplayFeatureRequest>();
			System.out.println("Feta Request :" + disFeatureList.size());

			int rowNumber = 0;
			while (rows.hasNext()) {
				Row currentRow = rows.next();

				// skip header
				if (rowNumber == 0) {
					rowNumber++;
					continue;
				}

				Iterator<Cell> cellsInRow = currentRow.iterator();

				DisplayFeatureRequest dispFeature = new DisplayFeatureRequest();

				int cellIdx = 0;
				while (cellsInRow.hasNext()) {
					Cell currentCell = cellsInRow.next();

					switch (cellIdx) {
					case 0:
						dispFeature.setCountry(
								currentCell.getStringCellValue() != null ? currentCell.getStringCellValue() : "");
						break;

					case 1:
						dispFeature.setAvgAmount(
								(Double) currentCell.getNumericCellValue() != null ? currentCell.getNumericCellValue()
										: 0);
						break;
					case 2:
						dispFeature.setCcy(
								currentCell.getStringCellValue() != null ? currentCell.getStringCellValue() : "");
						break;
					default:
						break;
					}

					cellIdx++;
				}

				disFeatureList.add(dispFeature);
			}

			workbook.close();

			return disFeatureList;
		} catch (IOException e) {
			throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
		}
	}

	public static InputStream loadExampleExcel() {
		try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
			Sheet sheet = workbook.createSheet(SHEET);
			CellStyle cellStyle = workbook.createCellStyle();
			XSSFFont defaultFont = (XSSFFont) workbook.createFont();
			defaultFont.setBold(true);

			// Header
			Row headerRow = sheet.createRow(0);

			for (int col = 0; col < HEADERs.length; col++) {
				Cell cell = headerRow.createCell(col);
				cellStyle.setFont(defaultFont);
				cellStyle.setAlignment(HorizontalAlignment.CENTER);
				cell.setCellValue(HEADERs[col]);
				sheet.autoSizeColumn(col);
			}

			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		} catch (IOException e) {
			throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
		}

	}

}
