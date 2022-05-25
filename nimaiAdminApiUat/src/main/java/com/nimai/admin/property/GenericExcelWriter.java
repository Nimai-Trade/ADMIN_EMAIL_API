package com.nimai.admin.property;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

public class GenericExcelWriter {
  public static <T> ByteArrayInputStream writeToExcel(String fileName, List<T> data) {
    SXSSFWorkbook workbook = null;
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    try {
      workbook = new SXSSFWorkbook(500);
      SXSSFSheet sXSSFSheet = workbook.createSheet(processNames(fileName));
      CellStyle cellStyle = workbook.createCellStyle();
      cellStyle.setBorderBottom(BorderStyle.THIN);
      cellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
      cellStyle.setBorderRight(BorderStyle.THIN);
      cellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
      cellStyle.setBorderTop(BorderStyle.THIN);
      cellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
      List<String> fieldNames = getFieldNamesForClass(data.get(0).getClass());
      int rowCount = 0;
      int columnCount = 0;
      Row row = sXSSFSheet.createRow(rowCount++);
      for (String fieldName : fieldNames) {
        Cell cell = row.createCell(columnCount++);
        cell.setCellStyle(cellStyle);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cell.setCellValue(processNames(fieldName));
        cell.setCellValue((fieldName == null) ? "" : fieldName);
      } 
      Class<? extends Object> classz = (Class)data.get(0).getClass();
      for (T t : data) {
        row = sXSSFSheet.createRow(rowCount++);
        columnCount = 0;
        for (String fieldName : fieldNames) {
          Cell cell = row.createCell(columnCount);
          CellStyle rowcellStyle = workbook.createCellStyle();
          rowcellStyle.setBorderBottom(BorderStyle.THIN);
          rowcellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
          rowcellStyle.setBorderRight(BorderStyle.THIN);
          rowcellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
          rowcellStyle.setBorderTop(BorderStyle.THIN);
          rowcellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
          cell.setCellStyle(rowcellStyle);
          CreationHelper creationHelper = workbook.getCreationHelper();
          cellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("yyyy-MM-dd"));
          Method method = null;
          try {
            method = classz.getMethod("get" + capitalize(fieldName), new Class[0]);
          } catch (NoSuchMethodException nme) {
            method = classz.getMethod("get" + fieldName, new Class[0]);
          } 
          Object value = method.invoke(t, (Object[])null);
          if (value != null)
            if (value instanceof String) {
              cell.setCellValue((String)value);
            } else if (value instanceof Long) {
              cell.setCellValue(((Long)value).longValue());
            } else if (value instanceof Integer) {
              cell.setCellValue(((Integer)value).intValue());
            } else if (value instanceof Double) {
              cell.setCellValue(((Double)value).doubleValue());
            } else if (value instanceof Float) {
              cell.setCellValue(((Float)value).floatValue());
            } else if (value instanceof Date) {
              cell.setCellStyle(cellStyle);
              cell.setCellValue((Date)value);
            } else if (value instanceof Date) {
              cell.setCellStyle(cellStyle);
              cell.setCellValue((Date)value);
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
  
  public static String processNames(String s) {
    return s.substring(0, 1).toUpperCase() + s.substring(1).replace("_", " ").replace("$", "/").replace("2", "%")
      .replace("3", "&").replace("1", "(").replace("0", ")");
  }
  
  private static List<String> getFieldNamesForClass(Class<?> clazz) throws Exception {
    List<String> fieldNames = new ArrayList<>();
    Field[] fields = clazz.getDeclaredFields();
    for (int i = 0; i < fields.length; i++)
      fieldNames.add(fields[i].getName()); 
    return fieldNames;
  }
  
  private static String capitalize(String s) {
    if (s.length() == 0)
      return s; 
    return s.substring(0, 1).toUpperCase() + s.substring(1);
  }
}
