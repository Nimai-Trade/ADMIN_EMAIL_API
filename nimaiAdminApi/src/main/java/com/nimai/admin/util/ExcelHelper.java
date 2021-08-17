package com.nimai.admin.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.nimai.admin.model.NimaiMCustomer;
import com.nimai.admin.payload.DiscountCouponProjection;
import com.nimai.admin.payload.DiscountMpCouponRequest;

public class ExcelHelper {
	public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	static String[] HEADERs = { "UserId", "First Name", "Last Name", "Country", "Company Name", "VAS",
			"Subscription Plan", "Current Status", "Start Date", "End Date", "Credits Remaining", "Coupon" };
	static String SHEET = "Personalized_Coupons";

	
	public static ByteArrayInputStream userDetailsToExcel(List<DiscountCouponProjection> couponList,List<String> coupons) {
		Date date3 = null;
		Name namedRange;
		   String colLetter;
		   String reference;
		   
		try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
			Sheet sheet = workbook.createSheet(SHEET);

			CellStyle cellStyle = workbook.createCellStyle();
			CreationHelper creationHelper = workbook.getCreationHelper();
			cellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("yyyy-MM-dd"));
			// Header
			Row headerRow = sheet.createRow(0);

			for (int col = 0; col < HEADERs.length; col++) {

				Cell cell = headerRow.createCell(col);
				cell.setCellValue(HEADERs[col]);
				sheet.autoSizeColumn(col);
			}
			//for dropdown
			Row rowdrop; 
			int c = 39;
			   //put the data in
			for (String key : coupons) {
				int r = 0;
				rowdrop = sheet.getRow(r); if (rowdrop == null) rowdrop = sheet.createRow(r); r++;
				rowdrop.createCell(c).setCellValue(key);
			    c++;
			   }
			int rowIdx = 1;
			System.out.println("List of Coupon: "+coupons);
			//List<String> couponList=
			colLetter = CellReference.convertNumToColString((c-1));
			   namedRange = workbook.createName();
			   namedRange.setNameName("Coupons");
			   reference = SHEET+"!$AN$1:$" + colLetter + "$1";
			   namedRange.setRefersToFormula(reference);
			   
			for (DiscountCouponProjection cp : couponList) {
				//System.out.println("Coupon: "+cp.getCoupon().get(1));
				Row row = sheet.createRow(rowIdx++);

				row.createCell(0).setCellValue((String) cp.getUserid());
				row.createCell(1).setCellValue((String) cp.getFirstName() != null ? cp.getFirstName() : "");
				row.createCell(2).setCellValue((String) cp.getLastName() != null ? cp.getLastName() : "");
				row.createCell(3).setCellValue((String) cp.getCountryName() != null ? cp.getCountryName() : "");
				row.createCell(4).setCellValue((String) cp.getCompanyName() != null ? cp.getCompanyName() : "");
				row.createCell(5).setCellValue((Integer) cp.getIsVasApplied() != null ? cp.getIsVasApplied() : 0);
				row.createCell(6)
						.setCellValue((String) cp.getSubscriptionName() != null ? cp.getSubscriptionName() : "");
				row.createCell(7).setCellValue((String) cp.getStatus() != null ? cp.getStatus() : "");
				row.createCell(8).setCellValue(cp.getStartDate() != null ? cp.getStartDate() : date3);
				row.getCell(8).setCellStyle(cellStyle);
				row.createCell(9).setCellValue(cp.getEndDate() != null ? cp.getEndDate() : date3);
				row.getCell(9).setCellStyle(cellStyle);
				row.createCell(10)
						.setCellValue((Double) cp.getCreditsRemaining() != null ? cp.getCreditsRemaining() : 0);
				//row.createCell(11).setCellValue((String) cp.getCoupon() != null ? cp.getCoupon() : "");

				for (int i = 0; i < 13; i++) {
					sheet.autoSizeColumn(i);
				}
			}
			
			 DataValidationHelper dvHelper = sheet.getDataValidationHelper();
			   //data validation for categories in A2:
			 DataValidationConstraint dvConstraint = dvHelper.createFormulaListConstraint("Coupons");
			 CellRangeAddressList addressList = new CellRangeAddressList(1, rowIdx-1, 11, 11);            
			 DataValidation validation = dvHelper.createValidation(dvConstraint, addressList);
			 sheet.addValidationData(validation);
			 //sheet.autoSizeColumn(11);
			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		} catch (IOException e) {
			throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
		}
	}

	public static boolean hasExcelFormat(MultipartFile excelFile) {
		if (!TYPE.equals(excelFile.getContentType())) {
			return false;
		}
		return true;
	}

	public static List<DiscountMpCouponRequest> excelToMpDiscount(InputStream inputStream) {

		Date date3 = null;
		try {
			Workbook workbook = new XSSFWorkbook(inputStream);

			Sheet sheet = workbook.getSheet(SHEET);
			Iterator<Row> rows = sheet.iterator();

			List<DiscountMpCouponRequest> mpCoupons = new ArrayList<DiscountMpCouponRequest>();

			int rowNumber = 0;
			while (rows.hasNext()) {
				Row currentRow = rows.next();

				// skip header
				if (rowNumber == 0) {
					rowNumber++;
					continue;
				}

				Iterator<Cell> cellsInRow = currentRow.iterator();

				DiscountMpCouponRequest mDiscount = new DiscountMpCouponRequest();

				int cellIdx = 0;
				while (cellsInRow.hasNext()) {
					Cell currentCell = cellsInRow.next();

					switch (cellIdx) {
					case 0:
						mDiscount.setUserid(new NimaiMCustomer(currentCell.getStringCellValue()));
						break;

					case 1:
						mDiscount.setFirstName(
								currentCell.getStringCellValue() != null ? currentCell.getStringCellValue() : "");
						break;

					case 2:
						mDiscount.setLastName(
								currentCell.getStringCellValue() != null ? currentCell.getStringCellValue() : "");
						break;

					case 3:
						mDiscount.setCountry(
								currentCell.getStringCellValue() != null ? currentCell.getStringCellValue() : "");
						break;
					case 4:
						mDiscount.setCompanyName(
								currentCell.getStringCellValue() != null ? currentCell.getStringCellValue() : "");
						break;
					case 5:
						mDiscount.setVas((int) currentCell.getNumericCellValue());
						break;
					case 6:
						mDiscount.setSubscriptionPlan(
								currentCell.getStringCellValue() != null ? currentCell.getStringCellValue() : "");
						break;
					case 7:
						mDiscount.setCurrentStatus(
								currentCell.getStringCellValue() != null ? currentCell.getStringCellValue() : "");
						break;
					case 8:
						mDiscount.setStartDate(
								currentCell.getDateCellValue() != null ? currentCell.getDateCellValue() : date3);
						break;
					case 9:
						mDiscount.setEndDate(
								currentCell.getDateCellValue() != null ? currentCell.getDateCellValue() : date3);
					case 10:
						mDiscount.setCreditsRemaining((int) currentCell.getNumericCellValue());
						break;
					case 11:
						mDiscount.setCouponCode(
								currentCell.getStringCellValue() != null ? currentCell.getStringCellValue() : "");
						break;
					default:
						break;
					}

					cellIdx++;
				}

				mpCoupons.add(mDiscount);
			}

			workbook.close();

			return mpCoupons;
		} catch (IOException e) {
			throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
		}
	}

}
