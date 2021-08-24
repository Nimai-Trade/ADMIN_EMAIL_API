package com.nimai.lc.utility;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;

import com.lowagie.text.Phrase;

import com.nimai.lc.bean.TransactionQuotationBean;
import com.nimai.lc.entity.NimaiClient;
import com.nimai.lc.entity.NimaiLCMaster;

public class CSVHelper {

	static String[] headers= {"Transaction ID","User ID","Product Type","Transaction Status","Applicant Contact Person","Applicant Contact Person Email",
			"Applicant Country","Beneficiary Bank Country","Beneficiary Contact Person","Beneficiary Contact Person Email","Beneficiary Country",
			"Beneficiary Name","Beneficiary Swift Code","Branch User ID","Charges Type","Discharge Country","Discharge Port","Confirmation Period","Discounting Period",
                       "Refinancing Period","Financing Period","Start Date","End Date","Goods Type","Inserted By","Inserted Date","Last Bank Country","Last Beneficiary Bank",
                	"Last Beneficiary Swift Code","Last Shipment Date","lC Currency",
			"lC Expiry Date","lC Issuance Bank", "lC Issuance Country", "LC Issuing Branch","lC Issuing Date","Lc Maturity Date",
			"Lc Number",  "lC Value",  "Loading Country", "Loading Port", "Modified By", "Modified Date",
			"Negotiation Date", "Original Tenor Days","Payment Period","Payment Terms","Quotation Received",
			 "Rejected On","Remarks","Status Reason","Swift Code","Tenor End Date","User Type","Validity"};
	
	
  public static ByteArrayInputStream putDataToCSV(List<NimaiLCMaster> masterLCData) {
    final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);

        
    try (ByteArrayOutputStream out = new ByteArrayOutputStream();
        CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {
    	//for(String header:headers)
    	//{
    		List<String> headerData = Arrays.asList(headers);
    		csvPrinter.printRecord(headerData);
    	//}
      for (NimaiLCMaster lc : masterLCData) {
        List<Object> data = Arrays.asList(
        		lc.getTransactionId(),
        		lc.getUserId(),
        		lc.getRequirementType(),
        		lc.getTransactionStatus(),
        		lc.getApplicantContactPerson(),
        		lc.getApplicantContactPersonEmail(),
        		lc.getApplicantCountry(),
        		lc.getBeneBankCountry(),
        		lc.getBeneContactPerson(),
        		lc.getBeneContactPersonEmail(),
        		lc.getBeneCountry(),
        		lc.getBeneName(),
        		lc.getBeneSwiftCode(),
        		lc.getBranchUserEmail(),
        		lc.getChargesType(),
        		lc.getDischargeCountry(),
                lc.getDischargePort(),
        		lc.getConfirmationPeriod(),
                lc.getDiscountingPeriod(),
                lc.getRefinancingPeriod(),
                lc.getFinancingPeriod(),
                lc.getStartDate(),
                lc.getEndDate(),
                lc.getGoodsType(),
                lc.getInsertedBy(),
                lc.getInsertedDate(),
                lc.getLastBankCountry(),
                lc.getLastBeneBank(),
                lc.getLastBeneSwiftCode(),
                lc.getLastShipmentDate(),
                lc.getlCCurrency(),
                lc.getlCExpiryDate(),
                lc.getlCIssuanceBank(),
                lc.getlCIssuanceCountry(),
                lc.getlCIssuanceBranch(),
                lc.getlCIssuingDate(),
                lc.getLcMaturityDate(),
                lc.getLcNumber(),
                lc.getlCValue(),
                lc.getLoadingCountry(),
                lc.getLoadingPort(),
                lc.getModifiedBy(),
                lc.getModifiedDate(),
                lc.getNegotiationDate(),
                lc.getOriginalTenorDays(),
                lc.getPaymentPeriod(),
                lc.getPaymentTerms(),
                lc.getQuotationReceived(),
                lc.getRejectedOn(),
                lc.getRemarks(),
                lc.getStatusReason(),
                lc.getSwiftCode(),
                lc.getTenorEndDate(),                
               // lc.getUsanceDays(),
                lc.getUserType(),
                lc.getValidity()                          
               
               
        		);

        csvPrinter.printRecord(data);
      }

      csvPrinter.flush();
      return new ByteArrayInputStream(out.toByteArray());
    } catch (IOException e) {
      throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
    }
  }


  static String[] headersForBank={"User ID","Transaction ID","Product Type","Transaction Status","Applicant Contact Person","Applicant Contact Person Email",
			"Applicant Country","Beneficiary Bank Country","Beneficiary Contact Person","Beneficiary Contact Person Email","Beneficiary Country",
			"Beneficiary Name","Beneficiary Swift Code",
			"Banker Accept Charges","Applicable Benchmark","Comments Benchmark","Min Transaction Charges",
			"Charges Type","Discharge Port","Discharge Country","Start Date","End Date","Confirmation period","Refinancing Period",
			"Discounting Period","Financing Period","Goods Type","Inserted Date", "Modified Date","Last Bank Country","Last Beneficiary Bank","Last Beneficiary Swift Code","Last Shipment Date","lC Currency",
			"lC Expiry Date","lC Issuance Bank", "lC Issuance Country", "lC Issuing Date","LC Issuing Branch","Lc Maturity Date",
			"Lc Number",  "lC Value",  "Loading Country", "Loading Port","Negotiation Date", "Original Tenor Days","Payment Period","Payment Terms","Quotation Placed","Quotation Id",
			"Rejected On","Accepted On","Accepted Quote Value","Rejected Reason","Rejected By","Rejected On","Remarks","Term Condition Comments","Usance Days",
			"User Id","User Type","Validity"};
  public static ByteArrayInputStream putDataToCSVForBank(List<TransactionQuotationBean> masterLCData) {
	    final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);

	        
	    try (ByteArrayOutputStream out = new ByteArrayOutputStream();
	        CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {
	    	//for(String header:headers)
	    	//{
	    		List<String> headerData = Arrays.asList(headersForBank);
	    		csvPrinter.printRecord(headerData);
	    	//}
	      for (TransactionQuotationBean lc : masterLCData) {   	  
	    	
	    	    
	        List<Object> data = Arrays.asList(
	        		lc.getBankUserId(),
	        		lc.getTransactionId(),
	        		lc.getRequirementType(),
	                lc.getTransactionStatus(),
	        		lc.getApplicantContactPerson(),
	        		lc.getApplicantContactPersonEmail(),
	        		lc.getApplicantCountry(),
	        		lc.getBeneBankCountry(),
	        		lc.getBeneContactPerson(),
	        		lc.getBeneContactPersonEmail(),
	        		lc.getBeneCountry(),
	        		lc.getBeneName(),
	        		lc.getBeneSwiftCode(),
	        		lc.getBankerAcceptCharges(),
	        		lc.getApplicableBenchmark(),
	        		lc.getCommentsBenchmark(),
	        		lc.getMinTransactionCharges(),
	        		lc.getChargesType(),
	        		lc.getDischargePort(),
	        		lc.getDischargeCountry(),	        		
	        		lc.getStartDate(),
	                lc.getEndDate(),
	        		lc.getConfirmationPeriod(),
	                lc.getRefinancingPeriod(),
	                lc.getDiscountingPeriod(),
	                lc.getFinancingPeriod(),
	                lc.getGoodsType(),
	                lc.getInsertedDate(),
	                lc.getModifiedDate(),
	                lc.getLastBankCountry(),
	                lc.getLastBeneBank(),
	                lc.getLastBeneSwiftCode(),
	                lc.getLastShipmentDate(),
	                lc.getlCCurrency(),
	                lc.getlCExpiryDate(),
	                lc.getlCIssuanceBank(),
	                lc.getlCIssuanceCountry(),
	                lc.getlCIssuingDate(),
	        		lc.getlCIssuanceBranch(),
	                lc.getlCMaturityDate(),
	                lc.getlCNumber(),
	                lc.getlCValue(),
	                lc.getLoadingCountry(),
	                lc.getLoadingPort(),
                    lc.getNegotiationDate(),
	                lc.getOriginalTenorDays(),	                
	                lc.getPaymentPeriod(),
	                lc.getPaymentTerms(),
	                lc.getQuotationPlaced(),
	                lc.getQuotationId(),
	                lc.getRejectedOn(),
	                lc.getAcceptedOn(),
	                lc.getAcceptedQuoteValue(),
	                lc.getRejectedReason(),	     
	                lc.getRejectedBy(),
	                lc.getRejectedOn(),
	                lc.getRemarks(),
	                lc.getTermConditionComments(),	                
	                lc.getUsanceDays(),
	                lc.getUserId(),
	                lc.getUserType(),
	                lc.getValidity()                               
	               
	                
	                
	                
	        		);

	        csvPrinter.printRecord(data);
	      }

	      csvPrinter.flush();
	      return new ByteArrayInputStream(out.toByteArray());
	    } catch (IOException e) {
	      throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
	    }
	  }



}
