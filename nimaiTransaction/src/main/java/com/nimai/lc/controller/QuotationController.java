package com.nimai.lc.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nimai.lc.NimaiTransactionApplication;
import com.nimai.lc.bean.BankDetailsBean;
import com.nimai.lc.bean.NimaiLCBean;
import com.nimai.lc.bean.NimaiLCMasterBean;
import com.nimai.lc.bean.QuotationBean;
import com.nimai.lc.bean.QuotationMasterBean;
import com.nimai.lc.bean.TransactionQuotationBean;
import com.nimai.lc.entity.NimaiLC;
import com.nimai.lc.entity.NimaiLCMaster;
import com.nimai.lc.entity.Quotation;
import com.nimai.lc.entity.QuotationMaster;
import com.nimai.lc.entity.TransactionSaving;
import com.nimai.lc.payload.GenericResponse;
import com.nimai.lc.repository.TransactionSavingRepo;
import com.nimai.lc.service.CSVService;
import com.nimai.lc.service.LCService;
import com.nimai.lc.service.QuotationService;
import com.nimai.lc.utility.ErrorDescription;

@CrossOrigin(origins = "*")
@RestController
public class QuotationController 
{
	@Autowired
	QuotationService quotationService;
	

	@Autowired
	CSVService csvFileService;
	
	@Autowired
	LCService lcservice;
	
	@Autowired
	TransactionSavingRepo trSavingRepo;
	
	private static final Logger logger = LoggerFactory.getLogger(NimaiTransactionApplication.class);

	@CrossOrigin(value = "*", allowedHeaders = "*")
	@RequestMapping(value = "/checkQuotationPlaced", produces = "application/json", method = RequestMethod.POST)
	public ResponseEntity<?> checkQuotation(@RequestBody QuotationBean quotationBean) 
	{
		logger.info("=========== Check Quotation ===========");
		GenericResponse response = new GenericResponse<>();
		String transactionId=quotationBean.getTransactionId();
		String bankUserId=quotationBean.getBankUserId();
		List<QuotationMaster> data=quotationService.checkQuotationPlacedOrNot(transactionId,bankUserId);
		if(data.isEmpty())
		{
			response.setStatus("Success");
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
		else
		{
			response.setStatus("Failure");
			response.setErrMessage("The Quotation has been already Placed");
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
	}
	
	@CrossOrigin(value = "*", allowedHeaders = "*")
	@RequestMapping(value = "/saveQuotationToDraft", produces = "application/json", method = RequestMethod.POST)
	public ResponseEntity<?> placeQuotation(@RequestBody QuotationBean quotationBean) {
		logger.info("=========== Save Quotation To Draft ===========");
		GenericResponse response = new GenericResponse<>();
		NimaiLCMaster nmlc=lcservice.getTransactionForAcceptCheck(quotationBean.getTransactionId());
		if(nmlc==null)
		{
			try 
			{	
				String transId = quotationBean.getTransactionId();
				NimaiLCMaster transDetails=lcservice.checkTransaction(transId);
				System.out.println("transId:"+transId);
				if(transDetails==null)
				{
					response.setStatus("Failure");
					response.setErrCode("ASA002");
					response.setErrMessage(ErrorDescription.getDescription("ASA002"));
					return new ResponseEntity<Object>(response, HttpStatus.OK);
				}
				else
				{
					Quotation quoteDetails=quotationService.findDraftQuotation(transId,quotationBean.getUserId(),quotationBean.getBankUserId());
					if(quoteDetails==null)
					{
						Integer qid=quotationService.saveQuotationdetails(quotationBean);
						System.out.println("Quotation Id "+qid+" Saved");
						HashMap<String, Integer> getData=quotationService.calculateQuote(qid,quotationBean.getTransactionId(),"Draft");
						//quotationService.quotePlace(transId);
						getData.put("quotationId", qid);
						response.setData(getData);
						response.setStatus("Success");
						return new ResponseEntity<Object>(response, HttpStatus.OK);
					}
					else
					{
						//ModelMapper modelMapper = new ModelMapper();
						//QuotationBean quoteBean = modelMapper.map(quoteDetails, QuotationBean.class);
						Integer qidForUpdate=quotationService.findQuotationId(transId, quotationBean.getUserId(), quotationBean.getBankUserId());
						System.out.println("Qid For Update= "+qidForUpdate);
						quotationBean.setQuotationId(qidForUpdate);
						quotationService.updateDraftQuotationDet(quotationBean);
						HashMap<String, Integer> getData=quotationService.calculateQuote(quotationBean.getQuotationId(),quotationBean.getTransactionId(),"Draft");
						getData.put("quotationId", qidForUpdate);
						response.setData(getData);
						response.setStatus("Success");
						return new ResponseEntity<Object>(response, HttpStatus.OK);
					}
				}
				// System.out.println("nimaibean: "+nimailcBean.toString());
				// System.out.println("nimaiEntity: "+nimailcDetails.toString());
				
			} catch (Exception e) {
				response.setStatus("Failure");
				response.setErrCode("EXE000");
				response.setErrMessage(ErrorDescription.getDescription("EXE000")+" "+e);
				return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
			}
		}
		else
		{
			response.setStatus("Failure");
			System.out.println("Quotation has already Accepted by the Customer for the transaction:"+quotationBean.getTransactionId());
			response.setErrMessage("Quotation has already Accepted by the Customer for the transaction:"+quotationBean.getTransactionId());
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}

	}
	
	

	@CrossOrigin(value = "*", allowedHeaders = "*")
	@RequestMapping(value = "/confirmQuotation", produces = "application/json", method = RequestMethod.POST)
	public ResponseEntity<?> confirmQuotation(@RequestBody QuotationBean quotationBean) {
		logger.info("=========== Confirm Quotation ===========");
		Integer lcCount=0,utilizedLcCount=0;
		GenericResponse response = new GenericResponse<>();
		try {
			Integer quotationId=quotationBean.getQuotationId();
			String transId = quotationBean.getTransactionId();
			//String userId = quotationBean.getUserId();
			
			System.out.println("Confirming Quotation Id:"+quotationId+" for transId:"+transId);
			
			String bankUserId = quotationService.getBankUserIdByQId(quotationId);
			
			//Code 15 Jan 2021
			String obtainUserId=lcservice.checkMasterForSubsidiary(bankUserId);
			System.out.println("Master UserId : "+obtainUserId);
			//
			
			lcCount = lcservice.getLcCount(obtainUserId);
			utilizedLcCount = lcservice.getUtilizedLcCount(obtainUserId);
		
			System.out.println("Counts for Bank User: " + obtainUserId);
			System.out.println("LC Count: " + lcCount);
			System.out.println("LC Utilzed Count: " + utilizedLcCount);
			if (lcCount - utilizedLcCount == 0) 
			{
				response.setStatus("Failure");
				response.setErrMessage("Your Credit has been exhaust. Please Select Subscription Plan.");
				return new ResponseEntity<Object>(response, HttpStatus.OK);
			}
			else
			{
				quotationService.confirmQuotationdetails(quotationBean);
				//quotationService.sendMailToBank(quotationBean,"QUOTE_PLACE_ALERT_ToBanks");
				//quotationService.quotePlace(transId);
				lcservice.getAlleligibleBAnksEmail(quotationBean.getUserId(), transId, quotationId,"QUOTE_PLACE_ALERT_ToBanks","BId_ALERT_ToCustomer");
			
				response.setStatus("Success");
				return new ResponseEntity<Object>(response, HttpStatus.OK);
			}
		}
		
			// System.out.println("nimaibean: "+nimailcBean.toString());
			// System.out.println("nimaiEntity: "+nimailcDetails.toString());
			
		catch (Exception e) {
			response.setStatus("Failure");
			response.setErrCode("EXE000");
			response.setErrMessage(ErrorDescription.getDescription("EXE000")+" "+e);
			return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
		}

	}
	
	@CrossOrigin(value = "*", allowedHeaders = "*")
	@RequestMapping(value = "/calculateQuote", produces = "application/json", method = RequestMethod.POST)
	public ResponseEntity<?> calculateQuote(@RequestBody QuotationMasterBean quotebean) 
	{
		logger.info("=========== Calculating Quote ===========");
		GenericResponse response = new GenericResponse<>();
		Integer quotationId=quotebean.getQuotationId();
		String transId=quotebean.getTransactionId();
		try
		{
			HashMap<String, Integer> outputFields=quotationService.calculateQuote(quotationId,transId,"Master");
			response.setStatus("Success");
			response.setData(outputFields);
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
		catch (Exception e)
		{
			response.setStatus("Failure");
			response.setErrCode("EXE000");
			response.setErrMessage("Exception: "+e);
			return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
		}
	}
	
	/*@CrossOrigin(value = "*", allowedHeaders = "*")
	@RequestMapping(value = "/getQuotationCount", produces = "application/json", method = RequestMethod.POST)
	public ResponseEntity<?> getQuotationCountAgainstTransId(@RequestBody NimaiLCMasterBean nimailc) {
		GenericResponse response = new GenericResponse<>();
		String transId=nimailc.getTransactionId();
		//String userId = nimailc.getUserId();
		//String status=nimailc.getTransactionStatus();
		int count=0;
		try
		{
		 count = quotationService.getQuotationdetailsToCount(transId);
		
			response.setData(count);
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
		catch(Exception e)
		{
			response.setData(count);
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}

	}*/
	
	@CrossOrigin(value = "*", allowedHeaders = "*")
	@RequestMapping(value = "/getDraftQuotationByUserId", produces = "application/json", method = RequestMethod.POST)
	public ResponseEntity<?> getDraftQuotation(@RequestBody QuotationBean quotationBean) {
		logger.info("=========== Get Draft Quotation By UserId ===========");
		GenericResponse response = new GenericResponse<>();
		String userId = quotationBean.getUserId();
		List<Quotation> draftQuotations = quotationService.getAllDraftQuotationDetails(userId);
		
		if (draftQuotations.isEmpty()) {
			response.setStatus("Failure");
			response.setErrCode("ASA004");
			response.setErrMessage(ErrorDescription.getDescription("ASA004"));
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		} else {
			// lcservice.setDataToList(transactions);
			response.setData(draftQuotations);
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}

	}
	
	@CrossOrigin(value = "*", allowedHeaders = "*")
	@RequestMapping(value = "/getAllQuotationByUserId", produces = "application/json", method = RequestMethod.POST)
	public ResponseEntity<?> getQuotation(@RequestBody QuotationMasterBean quotationBean) {
		logger.info("=========== Get all Quotation By UserId ===========");
		GenericResponse response = new GenericResponse<>();
		String userId = quotationBean.getUserId();
		List<QuotationMaster> quotations = quotationService.getAllQuotationDetails(userId);
		
		if (quotations.isEmpty()) {
			response.setStatus("Failure");
			response.setErrCode("ASA004");
			response.setErrMessage(ErrorDescription.getDescription("ASA004"));
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		} else {
			// lcservice.setDataToList(transactions);
			response.setData(quotations);
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}

	}
	
	@CrossOrigin(value = "*", allowedHeaders = "*")
	@RequestMapping(value = "/getQuotationDetailByUserIdAndStatus", produces = "application/json", method = RequestMethod.POST)
	public ResponseEntity<?> getQuotationByUserIdAndStatus(@RequestBody QuotationMasterBean quotationBean) {
		logger.info("=========== Get Quotation By UserId and Status ===========");
		GenericResponse response = new GenericResponse<>();
		String userId = quotationBean.getUserId();
		String status=quotationBean.getQuotationStatus();
		List<QuotationMaster> quotations = quotationService.getQuotationDetailByUserIdAndStatus(userId,status);
		if (quotations.isEmpty()) {
			response.setStatus("Failure");
			response.setErrCode("ASA004");
			response.setErrMessage(ErrorDescription.getDescription("ASA004"));
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		} else {
			response.setData(quotations);
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
 
	}
	
	@CrossOrigin( value = "*",allowedHeaders = "*")
	@PostMapping(value = "/updateDraftQuotation", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> updateDraftQuotation(@RequestBody QuotationBean quotationbean) {
		logger.info("=========== Update Draft Quotation ==========="); 
		GenericResponse response = new GenericResponse<>();
		 try 
			 {
				 
				 quotationService.updateDraftQuotationDet(quotationbean);
				 //HashMap<String, Integer> getData=generateQuote(quotationbean.getTransactionId(),"Draft");
				 HashMap<String, Integer> getData=quotationService.calculateQuote(quotationbean.getQuotationId(),quotationbean.getTransactionId(),"Draft");
					
				 response.setData(getData);
				 response.setStatus("Success");
				 return new ResponseEntity<Object>(response, HttpStatus.OK);
			 } 
			 catch (Exception e) 
			 {
				 response.setStatus("Failure");
				 response.setErrCode("EXE000");
				 response.setErrMessage(ErrorDescription.getDescription("EXE000")+" "+e);
				 return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
			 }
		
	}
	
	@CrossOrigin( value = "*",allowedHeaders = "*")
	@PostMapping(value = "/updateMasterQuotation", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> updateQuotationDetails(@RequestBody QuotationMasterBean quotationbean) {
		logger.info("=========== Update Master Quotation ===========");
		 GenericResponse response = new GenericResponse<>();
		 Integer quotationId=quotationbean.getQuotationId();
		 String transId=quotationbean.getTransactionId();
		 
		 String userId=quotationbean.getUserId();
		 try 
			 {
				 
				 quotationService.moveQuoteToHistory(quotationId,transId, userId);
				 quotationService.updateQuotationMasterDetails(quotationbean);
				 //HashMap<String, Integer> getData=generateQuote(quotationbean.getTransactionId(),"Master");
				 HashMap<String, Integer> getData=quotationService.calculateQuote(quotationId,transId,"Master");
					
				 response.setData(getData);
				 response.setStatus("Success");
				 return new ResponseEntity<Object>(response, HttpStatus.OK);
			 } 
			 catch (Exception e) 
			 {
				 response.setStatus("Failure");
				 response.setErrCode("EXE000");
				 response.setErrMessage(ErrorDescription.getDescription("EXE000")+" "+e);
				 return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
			 }
		
	}
	
	@CrossOrigin(value = "*", allowedHeaders = "*")
	@RequestMapping(value = "/getSpecificDraftQuotationDetailByQuotationId", produces = "application/json", method = RequestMethod.POST)
	public ResponseEntity<?> getSpecificDraftQuotation(@RequestBody QuotationBean quotationbean) {
		logger.info("=========== Get Specific Draft Quotation By QuotationId ===========");
		GenericResponse response = new GenericResponse<>();
		Integer quotationId = quotationbean.getQuotationId();
		System.out.println(""+quotationId);
		Quotation quote = quotationService.getSpecificDraftQuotationDetail(quotationId);
		if (quote == null) {
			response.setStatus("Failure");
			response.setErrCode("ASA004");
			response.setErrMessage(ErrorDescription.getDescription("ASA004"));
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		} else {
			response.setData(quote);
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
	}
	
	@CrossOrigin(value = "*", allowedHeaders = "*")
	@RequestMapping(value = "/getAllQRByUserIdTxnId", produces = "application/json", method = RequestMethod.POST)
	public ResponseEntity<?> getAllDetailByUserIdAndTransactionId(@RequestBody QuotationBean quotationbean) {
		logger.info("=========== Get all Quotation Received By UserId and TransactionId ===========");
		GenericResponse response = new GenericResponse<>();
		String transactionId = quotationbean.getTransactionId();
		String userId=quotationbean.getUserId();
		
		System.out.println(""+transactionId);
		System.out.println(""+userId);
		List<QuotationMasterBean> quotations = quotationService.getQuotationDetailByUserIdAndTransactionId(userId,transactionId);
		
		if (quotations == null || quotations.isEmpty()) {
			response.setStatus("Failure");
			response.setErrCode("ASA004");
			response.setErrMessage(ErrorDescription.getDescription("ASA004"));
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		} else {
			response.setData(quotations);
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
	}
	
	@CrossOrigin(value = "*", allowedHeaders = "*")
	@RequestMapping(value = "/getQuoteByUserIdTxnIdStatus", produces = "application/json", method = RequestMethod.POST)
	public ResponseEntity<?> getQuoteDetailByUserIdAndTransactionId(@RequestBody QuotationBean quotationbean) {
		logger.info("=========== Get all Quotation Received By UserId and TransactionId ===========");
		GenericResponse response = new GenericResponse<>();
		String transactionId = quotationbean.getTransactionId();
		String userId=quotationbean.getUserId();
		String status=quotationbean.getQuotationStatus();
		System.out.println(""+transactionId);
		System.out.println(""+userId);
		List<QuotationMaster> quotations = quotationService.getQuotationDetailByUserIdAndTransactionIdStatus(userId,transactionId,status);
		Integer quotationId=quotationService.getQuotationIdByTransIdUserId(transactionId,userId,status);
		HashMap<String, Integer> getData=quotationService.calculateQuote(quotationId, transactionId, "");
		System.out.println("Quote Conf: "+getData);
		//Integer confChgsNegot = getData.get("confChgsNegot");
		//Integer confChgsMatur = getData.get("confChgsMatur");
		//System.out.println("confChgsNegot: "+getData.get("confChgsNegot")+", confChgsMatur"+getData.get("confChgsMatur"));
		if (quotations == null || quotations.isEmpty()) {
			response.setStatus("Failure");
			response.setErrCode("ASA004");
			response.setErrMessage(ErrorDescription.getDescription("ASA004"));
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		} else {
			response.setData(quotations);
			response.setStatus("confChgsNegot:"+getData.get("confChgsNegot")+", confChgsMatur:"+getData.get("confChgsMatur"));
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
	}
	
	@CrossOrigin(value = "*", allowedHeaders = "*")
	@RequestMapping(value = "/getQuotationDtlByQId", produces = "application/json", method = RequestMethod.POST)
	public ResponseEntity<?> getAllQuotationByQuotationId(@RequestBody QuotationBean quotationbean) {
		logger.info("=========== Get Quotation By QuotationId ===========");
		GenericResponse response = new GenericResponse<>();
		Integer quotationId = quotationbean.getQuotationId();
		
		System.out.println(""+quotationId);
		
		List<QuotationMaster> quotations = quotationService.getQuotationDetailByQuotationId(quotationId);
		
		if (quotations == null || quotations.isEmpty()) {
			response.setStatus("Failure");
			response.setErrCode("ASA004");
			response.setErrMessage(ErrorDescription.getDescription("ASA004"));
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		} else {
			response.setData(quotations);
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
	}
	
	@CrossOrigin(value = "*", allowedHeaders = "*")
	@RequestMapping(value = "/rejectQuote/{id}", produces = "application/json", method = RequestMethod.POST)
	public ResponseEntity<?> rejectQuotation(@PathVariable("id") Integer quotationId,@RequestBody NimaiLCMasterBean nimailcmasterbean) {
		logger.info("=========== Reject Quotation ===========");
		GenericResponse response = new GenericResponse<>();
		String userId=nimailcmasterbean.getUserId();
		String statusReason= nimailcmasterbean.getStatusReason();
		System.out.println("Quotation Id: "+quotationId);
		try
		{
			quotationService.updateQuotationForReject(quotationId,userId,statusReason);
			String transId=quotationService.getTransactionId(quotationId);
			String userIdByQid=quotationService.getUserId(quotationId);
			lcservice.getAlleligibleBAnksEmail(userIdByQid, transId, quotationId,"QUOTE_REJECTION","");
			
			response.setStatus("Quote Rejected Successfully");
			return new ResponseEntity<Object>(response, HttpStatus.OK);
			
		}
		catch(Exception e)
		{
			response.setStatus("Failure");
			response.setErrCode("EXE000");
			response.setErrMessage(ErrorDescription.getDescription("EXE000")+" "+e);
			return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
		}
	}
	
	@CrossOrigin(value = "*", allowedHeaders = "*")
	@RequestMapping(value = "/acceptQuote", produces = "application/json", method = RequestMethod.POST)
	public ResponseEntity<?> acceptQuotation(@RequestBody QuotationBean quotationbean) {
		logger.info("=========== Accept Quotation ===========");
		GenericResponse response = new GenericResponse<>();
		
		Integer quotationId = quotationbean.getQuotationId();
		String transId = quotationbean.getTransactionId();
		String userId= quotationbean.getUserId();
		System.out.println("Quotation Id: "+quotationId);
		QuotationMaster qmData=quotationService.getDetailsOfAcceptedTrans(transId);
		if(qmData!=null)
		{
			response.setStatus("Failure");
			System.out.println("Quote has already been Accepted for trans:"+quotationbean.getTransactionId());
			response.setErrMessage("Quote has already been Accepted");
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
		QuotationMaster qm=quotationService.getDetailsOfAcceptedTrans(transId,userId);
		if(qm==null)
		{
			try
			{
				quotationService.updateQuotationForAccept(quotationId,transId);
				String bankUserId=quotationService.findBankUserIdByQuotationId(quotationId);
				List<BankDetailsBean> bdb=quotationService.getBankDet(bankUserId);
				response.setData(bdb);
				String userIdByQid=quotationService.getUserId(quotationId);
				String lcCountry=lcservice.getLCIssuingCountryByTransId(transId);
				String lcCurrency=lcservice.getLCCurrencyByTransId(transId);
				boolean b=quotationService.checkDataForSaving(lcCountry,lcCurrency);
				if(b==true)
				{
				String savingPercentValue=quotationService.calculateSavingPercent(transId,quotationId);
				String splitted[]=savingPercentValue.split(",", 2);
				Double savingPercent=Double.valueOf(splitted[0]);
				DecimalFormat df=new DecimalFormat("#######0.00");
				String savingValue=df.format(Double.valueOf(splitted[1]));
				if(savingPercent>0)
				{
					TransactionSaving tsr=new TransactionSaving();
					tsr.setTransactionid(transId);
					tsr.setUserid(userIdByQid);
					tsr.setSavings(Double.valueOf(savingValue));
					trSavingRepo.save(tsr);
					response.setStatus("You've saved "+lcCurrency+" "+savingValue+" by accepting this quote.");
				}
				else
				{
					response.setStatus("");
				}
				}
				/*System.out.println("Average Amount: "+avgAmount);
				float quoteValue=quotationService.getQuoteValueByQid(quotationId);
				System.out.println("Quote Amount: "+quoteValue);
				if(avgAmount==null)
				{
					response.setStatus("");
				//response.setStatus("Quote Accepted Successfully of Bank: "+bankUserId);
				}
				else
				{
					int saving=(int) (avgAmount-quoteValue);
					System.out.println("Saving: "+saving);
					if(saving>0)
						response.setStatus("You've saved "+lcCurrency+" "+saving+" by accepting this quote.");
					else
						response.setStatus("");
				}*/
				lcservice.getAlleligibleBAnksEmail(userIdByQid, transId, quotationId,"QUOTE_ACCEPT","Bank_Details_tocustomer");
				return new ResponseEntity<Object>(response, HttpStatus.OK);
				
			}
			catch(Exception e)
			{
				response.setStatus("Failure");
				response.setErrCode("EXE000");
				System.out.println(""+e);
				response.setErrMessage(ErrorDescription.getDescription("EXE000")+" "+e);
				return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
			}
		}
		else
		{
			response.setStatus("Failure");
			System.out.println("You cant accept the quote of bank for the transaction:"+quotationbean.getTransactionId());
			response.setErrMessage("You cant accept the quote of bank for the transaction:"+quotationbean.getTransactionId());
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
		
	}
	

	@CrossOrigin(value = "*", allowedHeaders = "*")
	@RequestMapping(value = "/getSavings/{ccy}/{userid}", produces = "application/json", method = RequestMethod.POST)
	public ResponseEntity<?> getTotalSavings(@PathVariable("ccy") String ccy,@PathVariable("userid") String userid) {
		logger.info("=========== Get Quotation By Bank UserId ===========");
		GenericResponse response = new GenericResponse<>();
		List<String> totalSavings = null;
		List<String> ccyList = null ;		
	//	if(ccy.equalsIgnoreCase("all")) {
			 totalSavings = quotationService.getTotalSavings(userid);	
			 ccyList = quotationService.getSavingsByUserId(userid);      
		if (ccyList == null || ccyList.isEmpty() || totalSavings.isEmpty()) {
			response.setStatus("Failure");
			response.setErrCode("ASA004");
			response.setErrMessage(ErrorDescription.getDescription("ASA004"));
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		} else {
			response.setData(totalSavings);
			response.setList(ccyList);
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
	}
	
	
	@CrossOrigin(value = "*", allowedHeaders = "*")
	@RequestMapping(value = "/getSavingsByUserId/{ccy}/{userid}", produces = "application/json", method = RequestMethod.POST)
	public ResponseEntity<?> getSavingsUserId(@PathVariable("ccy") String ccy,@PathVariable("userid") String userid) {
		logger.info("=========== Get Quotation By Bank UserId ===========");
		GenericResponse response = new GenericResponse<>();
		List<String> totalSavings = null;

		totalSavings = quotationService.getTotalSavingsUserId(ccy,userid);	
		if (totalSavings.isEmpty() || totalSavings.get(0)==null || totalSavings.get(0).equals(null)) {
			 response.setData(0.0);
			response.setStatus("Failure");
			response.setErrCode("ASA004");
			response.setErrMessage(ErrorDescription.getDescription("ASA004"));
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		} else {
			Double value =Double.parseDouble(totalSavings.get(0));			
			  BigDecimal bd=new BigDecimal(value).setScale(2,RoundingMode.HALF_DOWN);
		        System.out.println("Double upto 2 decimal places: "+bd.doubleValue());	
		        response.setData(bd.doubleValue());
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
	}
	
	@CrossOrigin(value = "*", allowedHeaders = "*")
	@RequestMapping(value = "/getQuotationDtlByBankUserId", produces = "application/json", method = RequestMethod.POST)
	public ResponseEntity<?> getAllQuotationByBankUserId(@RequestBody QuotationBean quotationbean) {
		logger.info("=========== Get Quotation By Bank UserId ===========");
		GenericResponse response = new GenericResponse<>();
		String bankUserId = quotationbean.getBankUserId();
		List<QuotationMaster> quotations = quotationService.getQuotationDetailByBankUserId(bankUserId);
		
		if (quotations == null || quotations.isEmpty()) {
			response.setStatus("Failure");
			response.setErrCode("ASA004");
			response.setErrMessage(ErrorDescription.getDescription("ASA004"));
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		} else {
			response.setData(quotations);
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
	}
	
	@CrossOrigin(value = "*", allowedHeaders = "*")
	@RequestMapping(value = "/getDraftQuotationByBankUserId", produces = "application/json", method = RequestMethod.POST)
	public ResponseEntity<?> getDraftQuotationByBankUserId(@RequestBody QuotationBean quotationBean) throws NumberFormatException, ParseException {
		logger.info("=========== Get Draft Quotation By Bank UserId ===========");
		GenericResponse response = new GenericResponse<>();
		String bankUserId = quotationBean.getBankUserId();
		//List<Quotation> draftQuotations = quotationService.getAllDraftQuotationDetailsByBankUserId(bankUserId);
		List<TransactionQuotationBean> draftQuotations = quotationService.getAllDraftTransQuotationDetailsByBankUserId(bankUserId);
		
		if (draftQuotations.isEmpty()) {
			response.setStatus("Failure");
			response.setErrCode("ASA004");
			response.setErrMessage(ErrorDescription.getDescription("ASA004"));
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		} else {
			// lcservice.setDataToList(transactions);
			response.setData(draftQuotations);
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}

	}
	
	@CrossOrigin(value = "*", allowedHeaders = "*")
	@RequestMapping(value = "/getTransQuotationDtlByBankUserIdAndStatus", produces = "application/json", method = RequestMethod.POST)
	public ResponseEntity<?> getAllTransQuotationByBankUserIdAndStatus(@RequestBody TransactionQuotationBean trquotationbean) throws ParseException {
		logger.info("=========== Get Transaction Quotation Details By Bank UserId and Status ===========");
		GenericResponse response = new GenericResponse<>();
		
		String bankUserId = trquotationbean.getBankUserId();
		//String quotationPlaced = trquotationbean.getQuotationPlaced();
		//String transactionStatus = trquotationbean.getTransactionStatus();
		String quotationStatus = trquotationbean.getQuotationStatus();
		System.out.println(""+bankUserId);
		//List<TransactionQuotationBean> quotations = quotationService.getTransactionQuotationDetailByBankUserIdAndStatus(bankUserId,quotationPlaced,transactionStatus);
		List<TransactionQuotationBean> quotations = quotationService.getTransactionQuotationDetailByBankUserIdAndStatus(bankUserId,quotationStatus);
		if (quotations.isEmpty()) {
			response.setStatus("Failure");
			response.setErrCode("ASA004");
			response.setErrMessage(ErrorDescription.getDescription("ASA004"));
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		} else {
			response.setData(quotations);
			
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
	}
	
	@CrossOrigin(value = "*", allowedHeaders = "*")
	@RequestMapping(value = "/downloadExcelReportForBankTransaction", produces = "application/json", method = RequestMethod.POST)
	public ResponseEntity<?> downloadExcelReportForTxnByUserId(@RequestBody TransactionQuotationBean nimailc) throws ParseException 
	{
		Date d=new Date();
		String filename = "TrasanctionDetail_"+nimailc.getUserId()+"_"+d+".csv";
		
	    InputStreamResource file = new InputStreamResource(csvFileService.loadDataForBank(nimailc.getBankUserId(),nimailc.getQuotationStatus()));
	    return ResponseEntity.ok()
	        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
	        .contentType(MediaType.parseMediaType("application/csv"))
	        .body(file);
	}
	
	@CrossOrigin(value = "*", allowedHeaders = "*")
	@RequestMapping(value = "/getTransQuotationDtlByQuotationId", produces = "application/json", method = RequestMethod.POST)
	public ResponseEntity<?> getTransQuotationByQId(@RequestBody TransactionQuotationBean trquotationbean) throws ParseException {
		logger.info("=========== Get Transaction Quotation Details By Bank UserId and Status ===========");
		GenericResponse response = new GenericResponse<>();
		
		int qId=trquotationbean.getQuotationId();
		String transactionId=trquotationbean.getTransactionId();
		System.out.println(""+qId);
		//List<TransactionQuotationBean> quotations = quotationService.getTransactionQuotationDetailByBankUserIdAndStatus(bankUserId,quotationPlaced,transactionStatus);
		List<TransactionQuotationBean> quotations = quotationService.getTransactionQuotationDetailByQId(qId);
		HashMap<String, Integer> getData=quotationService.calculateQuote(qId,transactionId, "");
		System.out.println("Quote Conf: "+getData);
		if (quotations.isEmpty()) {
			response.setStatus("Failure");
			response.setErrCode("ASA004");
			response.setErrMessage(ErrorDescription.getDescription("ASA004"));
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		} else {
			response.setData(quotations);
			response.setStatus("confChgsNegot:"+getData.get("confChgsNegot")+", confChgsMatur:"+getData.get("confChgsMatur"));
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
	}
	
	@CrossOrigin(value = "*", allowedHeaders = "*")
	@RequestMapping(value = "/getQuotationOfAcceptedQuote", produces = "application/json", method = RequestMethod.POST)
	public ResponseEntity<?> getQuotationOfAcceptedQuote(@RequestBody QuotationMasterBean quotationbean) throws ParseException {
		logger.info("=========== Get Transaction Quotation Details By Bank UserId and Status ===========");
		GenericResponse response = new GenericResponse<>();
		
		String transactionId = quotationbean.getTransactionId();
		//String quotationPlaced = trquotationbean.getQuotationPlaced();
		//String transactionStatus = trquotationbean.getTransactionStatus();
		//String quotationStatus = trquotationbean.getQuotationStatus();
		//System.out.println(""+bankUserId);
		//List<TransactionQuotationBean> quotations = quotationService.getTransactionQuotationDetailByBankUserIdAndStatus(bankUserId,quotationPlaced,transactionStatus);
		QuotationMaster quotations = quotationService.getQuotationOfAcceptedQuote(transactionId);
		HashMap<String, Integer> getData=quotationService.calculateQuote(quotations.getQuotationId(), transactionId, "");
		System.out.println("Quote Conf: "+getData);
		if (quotations==null) {
			response.setStatus("Failure");
			response.setErrCode("ASA004");
			response.setErrMessage(ErrorDescription.getDescription("ASA004"));
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		} else {
			response.setData(quotations);
			response.setStatus("confChgsNegot:"+getData.get("confChgsNegot")+", confChgsMatur:"+getData.get("confChgsMatur"));
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
	}
	
	@CrossOrigin(value = "*", allowedHeaders = "*")
	@RequestMapping(value = "/deleteDraftQuotationByQuotationId", produces = "application/json", method = RequestMethod.POST)
	public ResponseEntity<?> deleteDraftQuotation(@RequestBody QuotationBean quotationbean) {
		logger.info("=========== Deleting Draft Quotation By QuotationId ===========");
		GenericResponse response = new GenericResponse<>();
		Integer quotationId = quotationbean.getQuotationId();
		Quotation draftQuotation = quotationService.getDraftQuotationDetails(quotationId);
		if (draftQuotation==null) {
			response.setStatus("Failure");
			response.setErrCode("ASA004");
			response.setErrMessage(ErrorDescription.getDescription("ASA004"));
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		} else {
			// lcservice.setDataToList(transactions);
			quotationService.deleteDraftQuotation(quotationId);
			response.setData("Quotation "+quotationId+" Deleted Successfully");
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}

	}
	/*@CrossOrigin(value = "*", allowedHeaders = "*")
	@RequestMapping(value = "/getConfirmationCharges", produces = "application/json", method = RequestMethod.POST)
	public ResponseEntity<?> getConfirmationCharges(@RequestBody QuotationMasterBean quotebean) throws ParseException {
		logger.info("=========== Get Transaction Quotation Details By Bank UserId and Status ===========");
		GenericResponse response = new GenericResponse<>();
		
		String bankUserId = trquotationbean.getBankUserId();
		String quotationPlaced = trquotationbean.getQuotationPlaced();
		String transactionStatus = trquotationbean.getTransactionStatus();
		System.out.println(""+bankUserId);
		
		List<TransactionQuotationBean> quotations = quotationService.getTransactionQuotationDetailByBankUserIdAndStatus(bankUserId,quotationPlaced,transactionStatus);
		
		if (quotations == null) {
			response.setStatus("Failure");
			response.setErrCode("ASA004");
			response.setErrMessage(ErrorDescription.getDescription("ASA004"));
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		} else {
			response.setData(quotations);
			
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
	}*/
	
	@CrossOrigin(value = "*", allowedHeaders = "*")
	@RequestMapping(value = "/getConfChargesForQuoteAmount", produces = "application/json", method = RequestMethod.POST)
	public ResponseEntity<?> getConfChargesForQuoteAmount(@RequestBody QuotationBean quotationbean) {
		logger.info("=========== Get Confirmation Charges ===========");
		GenericResponse response = new GenericResponse<>();
		int quotationId = quotationbean.getQuotationId();
		String transactionId=quotationbean.getTransactionId();
		String userId=quotationbean.getUserId();
		System.out.println(""+transactionId);
		System.out.println(""+quotationId);
		List<QuotationMasterBean> quotations = quotationService.getQuotationDetailByUserIdAndTransactionId(userId,transactionId);
		HashMap<String, Integer> getData=quotationService.calculateQuote(quotationId, transactionId, "");
		//System.out.println("Quote Conf: "+getData);
		//Integer confChgsNegot = getData.get("confChgsNegot");
		//Integer confChgsMatur = getData.get("confChgsMatur");
		//System.out.println("confChgsNegot: "+getData.get("confChgsNegot")+", confChgsMatur"+getData.get("confChgsMatur"));
		if (quotations == null || quotations.isEmpty()) {
			response.setStatus("Failure");
			response.setErrCode("ASA004");
			response.setErrMessage(ErrorDescription.getDescription("ASA004"));
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		} else {
			response.setData(quotations);
			response.setStatus("confChgsNegot:"+getData.get("confChgsNegot")+", confChgsMatur:"+getData.get("confChgsMatur"));
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
	}

	@CrossOrigin(value = "*", allowedHeaders = "*")
	@RequestMapping(value = "/checkAcceptedExpiredTransaction", produces = "application/json", method = RequestMethod.POST)
	public ResponseEntity<?> checkAcceptedExpiredTransaction(@RequestBody QuotationBean quotationbean) {
		logger.info("=========== Checking Accepted or Expired Transaction for Draft===========");
		GenericResponse response = new GenericResponse<>();
		String transactionId=quotationbean.getTransactionId();
		String userId=quotationbean.getUserId();
		System.out.println(""+transactionId);
		System.out.println(""+userId);
		NimaiLCMaster data = lcservice.getAcceptedorExpiredTransaction(transactionId,userId);
		if (data == null) {
			response.setStatus("Success");
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		} else {
			response.setStatus("Failure");
			response.setErrMessage("The transaction is already closed by the customer. You cannot place a quote for this.");
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
	}
	
	@CrossOrigin(value = "*", allowedHeaders = "*")
	@RequestMapping(value = "/withdrawQuote", produces = "application/json", method = RequestMethod.POST)
	public ResponseEntity<?> withdrawQuote(@RequestBody QuotationBean quotationbean) {
		logger.info("=========== Withdrawing Quote ===========");
		GenericResponse response = new GenericResponse<>();
		QuotationMaster qm=quotationService.getQuotationDetailByAcceptedQuotationId(quotationbean.getQuotationId());
		if(qm==null)
		{
		boolean sts=quotationService.withdrawQuoteByQid(quotationbean);
		if (sts == true) {
			response.setStatus("Success");
			response.setData("Quote Withdrawn Successfully");
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		} else {
			response.setStatus("Failure");
			response.setErrMessage("Could not able to withdraw Quote");
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
		}
		else
		{
			response.setStatus("Failure");
			response.setErrMessage("You can't withdraw the quote as it has been already Accepted");
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
	}
}
