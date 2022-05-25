package com.nimai.admin.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.nimai.admin.model.PreferredBankModel;

public class UploadedPreferredBankExcelHelper 
{
	public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	  static String[] HEADERs = { "Bank Name", "Country", "Contact Person Name", "Contact Person Mobile No." };
	  static String SHEET = "PreferredBanks";
	  
	  public static boolean hasExcelFormat(MultipartFile file) 
	  {
	    if (!TYPE.equals(file.getContentType())) {
	      return false;
	    }
	    return true;
	  }
	  public static List<PreferredBankModel> excelToPreferredBanks(InputStream is) 
	  {
	    try {
	      Workbook workbook = new XSSFWorkbook(is);
	      workbook.setMissingCellPolicy(MissingCellPolicy.RETURN_BLANK_AS_NULL);
	      DataFormatter formatter = new DataFormatter();
	      Sheet sheet = workbook.getSheet(SHEET);
	      
	      Iterator<Row> rows = sheet.iterator();
	      List<PreferredBankModel> preferredBanks = new ArrayList<PreferredBankModel>();
	      int rowNumber = 0;
	      while (rows.hasNext()) {
	        Row currentRow = rows.next();
	        // skip header
	        if (rowNumber == 0) {
	          rowNumber++;
	          continue;
	        }
	       for (int colNum = 0; colNum < currentRow.getLastCellNum(); colNum++) {

	        	   Cell cell = currentRow.getCell(colNum, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
	        	   String cellValue = null;
	        	   if(cell.getCellType()==CellType.BLANK)
	        		   currentRow.createCell(colNum, CellType.BLANK);
	        	   //cell.setBlank();
	        	// do whatever you want to do with the cell or its value

	        	}
	        Iterator<Cell> cellsInRow = currentRow.iterator();
	        PreferredBankModel preferredBank = new PreferredBankModel();
	        int cellIdx = 0;
	        
	        while (cellsInRow.hasNext()) {
	          Cell currentCell = cellsInRow.next();
	          if(currentCell==null)
	        	  cellIdx=3;
	          System.out.println("Cell: "+cellIdx);
	          System.out.println("Row: "+rowNumber);
	         
	          switch (cellIdx) {
	          case 0:
	        	  preferredBank.setBankName(currentCell.getStringCellValue());
	            break;
	          case 1:
	        	  preferredBank.setBankCountry(currentCell.getStringCellValue());
	            break;
	          case 2:
	        	  preferredBank.setBankContactPersonName(currentCell.getStringCellValue());
	            break;
	          case 3:
	        	  Cell MobNoCell=sheet.getRow(rowNumber++).getCell(3);
	        	  
	        	  //if(MobNoCell == null || MobNoCell.getCellType() == CellType.BLANK)
	        	//	  continue;
	        	  String mobNo=formatter.formatCellValue(MobNoCell);
	        	  System.out.println("Mobile no: "+mobNo);
	        	  preferredBank.setBankContactPersonMobNo(mobNo);
	            break;
	          default:  
	            break;
	          }
	    
        	  
	          cellIdx++;
	        }
	        
	        preferredBank.setIsUploaded(1);
	        preferredBanks.add(preferredBank);
	      }
	      workbook.close();
	      return preferredBanks;
	    } catch (IOException e) {
	      throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
	    }
	  }
	  
	  
	  public static List<PreferredBankModel> extractInfo(String userId,InputStream is) {
			List<PreferredBankModel> infoList = new ArrayList<PreferredBankModel>();
			Workbook wb = null;

			try {
				wb = new XSSFWorkbook(is);
				Sheet sheet = wb.getSheetAt(0);
				
				boolean skipHeader = true;
				
				for (Row row : sheet) {
					if (skipHeader) {
						skipHeader = false;
						continue;
					}

					List<Cell> cells = new ArrayList<Cell>();

					int lastColumn = Math.max(row.getLastCellNum(), 5);// because my
																		// excel
																		// sheet has
																		// max 5
																		// columns,
																		// in case
																		// last
																		// column is
																		// empty
																		// then
																		// row.getLastCellNum()
																		// will
																		// return 4
					for (int cn = 0; cn < lastColumn; cn++) {
						Cell c = row.getCell(cn, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
						cells.add(c);
					}

					PreferredBankModel info = extractInfoFromCell(userId,cells);
					infoList.add(info);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (wb != null) {
					try {
						wb.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

			return infoList;
		}

		private static PreferredBankModel extractInfoFromCell(String userId,List<Cell> cells) {
			PreferredBankModel info = new PreferredBankModel();
			
			info.setCustUserId(userId);
			Cell bankName = cells.get(0);
			if (bankName != null) {
				switch (bankName.getCellType()) {
				case NUMERIC:
					info.setBankName(NumberToTextConverter.toText(bankName.getNumericCellValue()));
					break;
				case STRING:
					info.setBankName(bankName.getStringCellValue());
					break;
				default:
					info.setBankName(bankName.getStringCellValue());
					break;
				//info.setBankName(bankName.getStringCellValue());
			}
			}
			Cell bankCountry = cells.get(1);
			if (bankCountry != null) {
				switch (bankCountry.getCellType()) {
				case NUMERIC:
					info.setBankCountry(NumberToTextConverter.toText(bankCountry.getNumericCellValue()));
					break;
				case STRING:
					info.setBankCountry(bankCountry.getStringCellValue());
					break;
				default:
					info.setBankCountry(bankCountry.getStringCellValue());
					break;
				//info.setBankName(bankName.getStringCellValue());
			}
				//info.setBankCountry(bankCountry.getStringCellValue());
			}
			
			Cell bankContactPerson = cells.get(2);
			if (bankContactPerson != null) {
				info.setBankContactPersonName(bankContactPerson.getStringCellValue());
			}
			
			Cell mobileCell = cells.get(3);
			if (mobileCell != null) {
				switch (mobileCell.getCellType()) {
				case NUMERIC:
					info.setBankContactPersonMobNo(NumberToTextConverter.toText(mobileCell.getNumericCellValue()));
					break;
				case BLANK:
					break;
				default:
					break;
				}
			}
			
			Cell bankContactPersonEmail = cells.get(4);
			if (bankContactPersonEmail != null) {
				info.setBankContactPersonEmailId(bankContactPersonEmail.getStringCellValue());
			}
			info.setIsUploaded(1);
			return info;
		}
		
		public static boolean validateExcel(InputStream is)
		{
			Workbook wb = null;
			String[] headerNames={ "Bank Name", "Country", "Contact Person Name", "Contact Person Mobile No.", "Email ID" };
			int i=0,flag=0;
			try 
			{
				wb = new XSSFWorkbook(is);
				Sheet sheet = wb.getSheetAt(0);
				System.out.println("Sheet name:"+sheet.getSheetName());
				Row row = sheet.getRow(0); //Get first row
				//following is boilerplate from the java doc
				short minColIx = row.getFirstCellNum(); //get the first column index for a row
				short maxColIx = row.getLastCellNum();
				for(short colIx=minColIx; colIx<maxColIx; colIx++) 
				{ //loop from first to last index
					Cell cell = row.getCell(colIx); //get the cell
					System.out.println("Header: "+cell.getStringCellValue());
					System.out.println("String Header: "+headerNames[colIx]);
					if(!headerNames[colIx].equalsIgnoreCase(cell.getStringCellValue()))
					{
						flag=1;
						break;
					}
				}
				System.out.println("flag: "+flag);
				if(sheet.getSheetName().equalsIgnoreCase("PreferredBanks") || flag==0)
					return true;
				else
					return false;
			}
			catch (IOException e) 
			{
				e.printStackTrace();
				return false;
			} 
			finally 
			{
				if (wb != null) 
				{
					try 
					{
						wb.close();
					} 
					catch (IOException e) 
					{
						e.printStackTrace();
						return false;
					}
				}
			}
		}
}
