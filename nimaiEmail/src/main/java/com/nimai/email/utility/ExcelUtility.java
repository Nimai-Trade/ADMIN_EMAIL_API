package com.nimai.email.utility;

import java.io.ByteArrayInputStream;
import static java.nio.file.StandardOpenOption.CREATE_NEW;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.nimai.email.bean.LcUploadBean;
import com.nimai.email.emailproperties.FileStorageException;
import com.nimai.email.entity.NimaiLC;

public class ExcelUtility {

	public static String generateReportToExcel(String key, List<NimaiLC> value) throws IOException {
		String str1 = "d:/nimai/";
		// TODO Auto-generated method stub
		ArrayList<String> cloumns = new ArrayList<>();
		cloumns.add("TRANSACTION_ID");
		cloumns.add("USER_ID");
		cloumns.add("LC_VALUE");
		cloumns.add("LC_CURRENCY");
		cloumns.add("LC_ISSUING_DATE");
		cloumns.add("LAST_SHIPMENT_DATE");
		cloumns.add("GOODS_TYPE");
		cloumns.add("LC_MATURITY_DATE");
		cloumns.add("LOADING_COUNTRY");
		cloumns.add("DISCHARGE_COUNTRY");
		cloumns.add("DISCHARGE_PORT");
		cloumns.add("VALIDITY");
		cloumns.add("TRANSACTION_STATUS");

		Workbook workbook = new HSSFWorkbook();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		CreationHelper createHelper = workbook.getCreationHelper();

		Sheet sheet = workbook.createSheet("sheet1");

		Font headerFont = workbook.createFont();
		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFont(headerFont);

		// Row for Header
		Row headerRow = sheet.createRow(0);

		// Header
		for (int col = 0; col < cloumns.size(); col++) {
			Cell cell = headerRow.createCell(col);
			cell.setCellValue(cloumns.get(col));
			cell.setCellStyle(headerCellStyle);
		}
		int rowIdx = 1;
		for (NimaiLC lcDetails : value) {
			Row row = sheet.createRow(rowIdx++);
			row.createCell(0).setCellValue(lcDetails.getTransactionId());
			row.createCell(1).setCellValue(lcDetails.getUserId());
			Double lcValuefrmDb = lcDetails.getlCValue();
			if (lcDetails.getlCValue() != null) {
				String lcValue = String.valueOf(lcValuefrmDb);
				row.createCell(2).setCellValue(lcValue);
			} else {
				row.createCell(2).setCellValue("NA");
			}

			row.createCell(3).setCellValue(lcDetails.getlCCurrency());
			String lcIssuingdate = convertDateToString(lcDetails.getlCIssuingDate());
			row.createCell(4).setCellValue(lcIssuingdate);
			String lastShipDate = convertDateToString(lcDetails.getLastShipmentDate());
			row.createCell(5).setCellValue(lastShipDate);
			row.createCell(6).setCellValue(lcDetails.getGoodsType());
			String lcMaturityDate = convertDateToString(lcDetails.getLcMaturityDate());
			row.createCell(7).setCellValue(lcMaturityDate);
			row.createCell(8).setCellValue(lcDetails.getLoadingCountry());
			row.createCell(9).setCellValue(lcDetails.getDischargeCountry());
			row.createCell(10).setCellValue(lcDetails.getDischargePort());
			String validity = (lcDetails.getValidity());
			row.createCell(11).setCellValue(validity);
			row.createCell(12).setCellValue(lcDetails.getTransactionStatus());
		}
		try {
			Files.createDirectories(Paths.get(str1));
		} catch (Exception ex) {
			// LOGGER.error("Unable to find the directory proivded for the uploaded files");
			throw new FileStorageException("Could not create the directory where the uploaded files will be stored.",
					ex);
		}

		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy_MM_dd");

		String currentDateTime = format.format(date);
		String folderName = str1 + currentDateTime;
		try {
			String fileName = Files.createDirectories(Paths.get(folderName)) + File.separator + key + "_"
					+ new SimpleDateFormat("dd_MMM_yyyy").format(Calendar.getInstance().getTime()) + ".xls";
			File newFile = new File(fileName);
			System.out.println("file path of the user"+fileName);
			FileOutputStream outFile = new FileOutputStream(fileName);
			workbook.write(outFile);
			return fileName;
		} catch (Exception ex) {
			// LOGGER.error("Unable to find the directory proivded for the uploaded files");
			throw new FileStorageException("Could not create the directory where the uploaded files will be stored.",
					ex);
		}
	}

	public static String convertDateToString(Date date) {
		if (date != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		}
		return "NA";
	}

	public static String convertDateToString(Integer lcValue) {
		if (lcValue != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		}
		return null;
	}

}
