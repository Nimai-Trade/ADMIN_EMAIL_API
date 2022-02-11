package com.nimai.splan.controller;

import java.util.List;

import javax.persistence.Column;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nimai.splan.model.NimaiAdvisory;
import com.nimai.splan.model.NimaiCustomerSubscriptionGrandAmount;
import com.nimai.splan.model.NimaiSubscriptionVas;
import com.nimai.splan.payload.NimaiSubscriptionVasBean;
import com.nimai.splan.repository.NimaiAdvisoryRepo;
import com.nimai.splan.repository.SubscriptionPlanRepository;
import com.nimai.splan.payload.CustomerSubscriptionGrandAmountBean;
import com.nimai.splan.payload.GenericResponse;
import com.nimai.splan.payload.NimaiAfterVasSubscriptionBean;
import com.nimai.splan.service.NimaiAdvisoryService;
import com.nimai.splan.service.SubscriptionPlanService;
import com.nimai.splan.utility.ErrorDescription;



@CrossOrigin(origins = "*")
@RestController
public class AdvisoryController {
	
	private static final String randomString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	
	@Autowired   
	NimaiAdvisoryService advisoryService;
	
	@Autowired
	private SubscriptionPlanService sPlanService;
	
	@Autowired
	SubscriptionPlanRepository splanDetRepo;
	
	@Autowired
	NimaiAdvisoryRepo nimaiAdvisoryRepo;
	
	GenericResponse response = new GenericResponse<>();
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@RequestMapping(value = "/viewAdvisory", produces = "application/json", method = RequestMethod.GET)
	public ResponseEntity<?> viewAdvisory()
	{
			List<NimaiAdvisory> nadvisory= advisoryService.viewAdvisory();
			if(nadvisory.isEmpty())
			{
				response.setErrMessage("No Records Found");;
				response.setStatus("Failure");
				return new ResponseEntity<Object>(response, HttpStatus.OK);
			}
			else
			{
				response.setData(nadvisory);
				response.setStatus("Success");
				return new ResponseEntity<Object>(response, HttpStatus.OK);
			}
    }
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping(value = "/getAdvisoryListByCountry/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAdvisoryByCountryName(@RequestBody NimaiAdvisory nimaiAdvisory,
			@PathVariable("userId") String userID) {
		GenericResponse response = new GenericResponse<>();
		try {
			String country_name=nimaiAdvisory.getCountry_name();
			List<NimaiAdvisory> outdata1 =  (List<NimaiAdvisory>) advisoryService.viewAdvisoryByCountry(country_name,userID);
			if(!outdata1.isEmpty())
			{
				response.setData(outdata1);
				response.setStatus("Success");
				return new ResponseEntity<Object>(response, HttpStatus.OK);
			}
			else
			{
				response.setData(null);
				response.setStatus("Success");
				return new ResponseEntity<Object>(response, HttpStatus.OK);
			}
		}
		catch(Exception e) {
			response.setStatus("Failure");
			response.setErrCode("EXE000");
			System.out.println(""+e);
			response.setErrMessage(ErrorDescription.getDescription("EXE000"));
			return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
		}
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping(value = "/addVAS", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> addVAS(@RequestBody NimaiSubscriptionVasBean nimaiSubsciptionVas) {
		GenericResponse response = new GenericResponse<>();
		String userId=nimaiSubsciptionVas.getUserId();
		//int vasId=nimaiSubsciptionVas.getVasId();
		String subscriptionId=nimaiSubsciptionVas.getSubscriptionId();
		String mode=nimaiSubsciptionVas.getMode();
		int isSplanWithVasFlag=nimaiSubsciptionVas.getIsSplanWithVasFlag();
		String vasPurchased=nimaiSubsciptionVas.getVasPurchased();
		
		try
		{
			int i;
			int vasCount = StringUtils.countOccurrencesOf(vasPurchased, "-");
			Integer totalVAS=vasCount;
			System.out.println("Total VAS: "+totalVAS);
			
			advisoryService.inactiveVASStatus(userId);
			//int vasWithDisc=vasCount+1;
			String vasSplitted[] =vasPurchased.split("-",totalVAS);
			
			for(i=0;i<totalVAS;i++)
			{
				System.out.println("vasSplitted: "+vasSplitted[i]);
				if(i==totalVAS-1)
					vasSplitted[i]=vasSplitted[i].replace("-", "");
				int vasId=Integer.valueOf(vasSplitted[i]);
				System.out.println("VAS ID: "+vasId);
				advisoryService.addVasDetails(userId,subscriptionId,vasId,mode,isSplanWithVasFlag);
				
			}
			advisoryService.getLastSerialNoAndUpdate(userId,mode);
			
			response.setStatus("Success");
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
		catch(Exception e)
		{
			System.out.println("Error: "+e);
			response.setStatus("Failure");
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
		
		//response.setData(outdata1);
		
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping(value = "/getVASByUserId", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getVASByUserId(@RequestBody NimaiSubscriptionVas nimaiSubsciptionVas) {
		GenericResponse response = new GenericResponse<>();
		List<NimaiSubscriptionVas> vasDetails= advisoryService.getActiveVASByUserId(nimaiSubsciptionVas.getUserId());
		if(vasDetails.isEmpty())
		{
			response.setErrMessage("No Records Found");
			response.setStatus("Failure");
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
		else
		{
			response.setData(vasDetails);
			response.setStatus("Success");
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping(value = "/addVASAfterSubscription", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> addVASAfterSubscription(@RequestBody NimaiAfterVasSubscriptionBean nimaiSubsciptionVas) {
		GenericResponse response = new GenericResponse<>();
		String userId=nimaiSubsciptionVas.getUserId();
		String vasId=nimaiSubsciptionVas.getVasId();
		String subscriptionId=nimaiSubsciptionVas.getSubscriptionId();
		String mode=nimaiSubsciptionVas.getMode();
		Float pricing;//=nimaiSubsciptionVas.getPricing();
		
		String paymentTxnId="",invoiceId="";
		if(mode.equalsIgnoreCase("Wire"))
		{
			paymentTxnId=generatePaymentTtransactionID(15);
			invoiceId=generatePaymentTtransactionID(10);
		}
		else
		{
			paymentTxnId=nimaiSubsciptionVas.getPaymentTxnId();
			invoiceId=nimaiSubsciptionVas.getInvoiceId();
		}
		try
		{
			
			advisoryService.inactiveVASStatus(userId);
		}
		catch(Exception e)
		{
			System.out.println("Error: "+e);
			response.setStatus("Failure");
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
		pricing=0.0f;
		advisoryService.addVasDetailsAfterSubscription(userId,subscriptionId,vasId,mode,pricing,paymentTxnId,invoiceId);
		//response.setData(outdata1);
		response.setErrMessage("VAS Plan Purchased Successfully");
		response.setStatus("Success");
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping(value = "/getFinalVASAmount", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getFinalVASAmount(@RequestBody NimaiSubscriptionVas nimaiSubsciptionVas) {
		GenericResponse response = new GenericResponse<>();
		String userId=nimaiSubsciptionVas.getUserId();
		int vasId=nimaiSubsciptionVas.getVasId();
		try
		{
			Float vasAmount=advisoryService.getVASAmount(userId,vasId);
			response.setData(vasAmount);
			response.setStatus("Success");
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
		catch(Exception e)
		{
			System.out.println("Error: "+e);
			response.setStatus("Failure");
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
		
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping(value = "/addVASToGrand", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> addVASToGrand(@RequestBody CustomerSubscriptionGrandAmountBean nimaiSubsciptionVas) {
		GenericResponse response = new GenericResponse<>();
		String userId=nimaiSubsciptionVas.getUserId();
		Double grandAmount=nimaiSubsciptionVas.getGrandAmount();
		//int vasId=nimaiSubsciptionVas.get.getVasId();
		//String subscriptionId=nimaiSubsciptionVas.getSubscriptionId();
		
		advisoryService.addGrandVasDetails(userId,grandAmount);
		//response.setData(outdata1);
		response.setStatus("Success");
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping(value = "/removeVASFromGrand", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> removeVASFromGrand(@RequestBody CustomerSubscriptionGrandAmountBean nimaiSubsciptionVas) {
		GenericResponse response = new GenericResponse<>();
		String userId=nimaiSubsciptionVas.getUserId();
		NimaiCustomerSubscriptionGrandAmount ncsga=advisoryService.getCustomerVASAmount(userId);
		
		advisoryService.removeGrandVasDetails(ncsga.getId());
		//response.setData(outdata1);
		response.setStatus("Success");
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}
	
	public static String generatePaymentTtransactionID(int count) {
		StringBuilder sb = new StringBuilder();
		while (count-- != 0) {
			int character = (int) (Math.random() * randomString.length());
			sb.append(randomString.charAt(character));
		}
		return sb.toString();
	}

}
