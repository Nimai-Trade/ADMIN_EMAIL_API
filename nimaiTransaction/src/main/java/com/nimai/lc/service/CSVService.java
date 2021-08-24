package com.nimai.lc.service;

import java.io.ByteArrayInputStream;
import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nimai.lc.bean.CustomerTransactionBean;
import com.nimai.lc.bean.TransactionQuotationBean;
import com.nimai.lc.entity.NimaiClient;
import com.nimai.lc.entity.NimaiLCMaster;
import com.nimai.lc.repository.LCMasterRepository;
import com.nimai.lc.repository.QuotationMasterRepository;
import com.nimai.lc.utility.CSVHelper;

@Service
public class CSVService {

  @Autowired
  LCMasterRepository lcRepo;

  @Autowired
	QuotationService quotationService;
  
  public ByteArrayInputStream loadDataForBank(String bankUserId,String quotationStatus) throws ParseException {
	  
		List<TransactionQuotationBean> quotations = quotationService.getTransactionQuotationDetailByBankUserIdAndStatus(bankUserId,quotationStatus);
		
		  ByteArrayInputStream in = CSVHelper.putDataToCSVForBank(quotations);
		    return in;
		
 
}
  
  
  public ByteArrayInputStream loadDataForCustomer(String userId, String txnStatus, String branchUserEmail) {
	  
		if(userId!=null && userId!="" )
		{
			if(userId.substring(0, 2).equalsIgnoreCase("Al"))//parent
			{
				System.out.println("Removing All from userid: "+userId.replaceFirst("All", ""));
				  List<NimaiLCMaster> lcData = lcRepo.findTransactionReportForCustByUserIdAndStatus(userId.replaceFirst("All", ""),txnStatus);
				  ByteArrayInputStream in = CSVHelper.putDataToCSV(lcData);
				    return in;
			}
			else//subsidiary
			{
				 List<NimaiLCMaster> lcData = lcRepo.findTransactionReportForCustByUserIdAndStatus(userId,txnStatus);
				  ByteArrayInputStream in = CSVHelper.putDataToCSV(lcData);
				    return in;
				
			}
		}
		else//passcode user
		{
			System.out.println(branchUserEmail);
			 List<NimaiLCMaster> lcData = lcRepo.findTransactionReportForCustByUserIdAndStatusAndEmail(txnStatus,branchUserEmail);
			  ByteArrayInputStream in = CSVHelper.putDataToCSV(lcData);
			    return in;
			
		}
	  
   
  }
}
