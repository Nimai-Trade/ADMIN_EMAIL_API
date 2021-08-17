package com.nimai.admin.property;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class GenericExcelWriter {

	public static <T> ByteArrayInputStream writeToExcel(String fileName, List<T> data) {

		XSSFWorkbook workbook = null;
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		try {

			workbook = new XSSFWorkbook();
			Sheet sheet = workbook.createSheet(processNames(fileName));
			CellStyle cellStyle = workbook.createCellStyle();

			List<String> fieldNames = getFieldNamesForClass(data.get(0).getClass());
			int rowCount = 0;
			int columnCount = 0;
			Row row = sheet.createRow(rowCount++);
			for (String fieldName : fieldNames) {
				Cell cell = row.createCell(columnCount++);
				cell.setCellStyle(cellStyle);
				cellStyle.setAlignment(HorizontalAlignment.CENTER);
				cell.setCellValue(processNames(fieldName));
				sheet.autoSizeColumn(columnCount);
			}
			Class<? extends Object> classz = data.get(0).getClass();
			for (T t : data) {
				row = sheet.createRow(rowCount++);
				columnCount = 0;
				for (String fieldName : fieldNames) {
					Cell cell = row.createCell(columnCount);

					CreationHelper creationHelper = workbook.getCreationHelper();

					cellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("yyyy-MM-dd"));

					Method method = null;
					try {
						method = classz.getMethod("get" + capitalize(fieldName));
					} catch (NoSuchMethodException nme) {
						method = classz.getMethod("get" + fieldName);
					}
					Object value = method.invoke(t, (Object[]) null);
					if (value != null) {
						if (value instanceof String) {
							cell.setCellValue((String) value);
						} else if (value instanceof Long) {
							cell.setCellValue((Long) value);
						} else if (value instanceof Integer) {
							cell.setCellValue((Integer) value);
						} else if (value instanceof Double) {
							cell.setCellValue((Double) value);
						} else if (value instanceof Float) {
							cell.setCellValue((Float) value);
						} else if (value instanceof Date) {
							cell.setCellStyle(cellStyle);
							cell.setCellValue((Date) value);

						} else if (value instanceof java.util.Date) {
							cell.setCellStyle(cellStyle);
							cell.setCellValue((java.util.Date) value);
						}
						sheet.autoSizeColumn(columnCount);
					}
					columnCount++;
				}
			}

			workbook.write(out);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return new ByteArrayInputStream(out.toByteArray());

	}

	private static String processNames(String s) {

		return s.substring(0, 1).toUpperCase() + s.substring(1).replace("_", " ").replace("$", "/").replace("2", "%")
				.replace("3", "&").replace("1", "(").replace("0", ")");
	}

	// retrieve field names from a POJO class
	private static List<String> getFieldNamesForClass(Class<?> clazz) throws Exception {
		List<String> fieldNames = new ArrayList<String>();
		Field[] fields = clazz.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			fieldNames.add(fields[i].getName());
		}
		return fieldNames;
	}

	// capitalize the first letter of the field name for retriving value of the
	// field later
	private static String capitalize(String s) {
		if (s.length() == 0)
			return s;
		return s.substring(0, 1).toUpperCase() + s.substring(1);
	}

}
