package com.nimai.splan.controller;

import java.io.IOException;
import java.lang.management.GarbageCollectorMXBean;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nimai.splan.model.NimaiAdvisory;
import com.nimai.splan.model.NimaiCustomerSubscriptionGrandAmount;
import com.nimai.splan.model.NimaiMCustomer;
import com.nimai.splan.model.NimaiMSubscription;
import com.nimai.splan.model.OnlinePayment;
import com.nimai.splan.payload.CustomerSubscriptionGrandAmountBean;
import com.nimai.splan.payload.GenericResponse;
import com.nimai.splan.payload.PaypalPaymentRequest;
import com.nimai.splan.payload.SplanRequest;
import com.nimai.splan.payload.SubscriptionAndPaymentBean;
import com.nimai.splan.payload.SubscriptionBean;
import com.nimai.splan.payload.SubscriptionPaymentBean;
import com.nimai.splan.payload.SubscriptionPlanResponse;
import com.nimai.splan.repository.OnlinePaymentRepo;
import com.nimai.splan.service.NimaiAdvisoryService;
import com.nimai.splan.service.SubscriptionPlanService;
import com.nimai.splan.service.ValidateCoupenService;
import com.nimai.splan.utility.Credentials;
import com.nimai.splan.utility.ErrorDescription;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.paypal.http.HttpResponse;
import com.paypal.http.exceptions.HttpException;
import com.paypal.orders.LinkDescription;
import com.paypal.orders.OrdersCaptureRequest;
import com.paypal.orders.OrdersGetRequest;
import com.nimai.splan.config.PaypalPaymentIntent;
import com.nimai.splan.config.PaypalPaymentMethod;
import com.nimai.splan.service.PaypalService;

@CrossOrigin(origins = "*")
@RestController
public class SubscriptionPlanController {

	private static Logger logger = LoggerFactory.getLogger(SubscriptionPlanController.class);

	@Autowired
	private SubscriptionPlanService sPlanService;
	
	@Autowired
	private NimaiAdvisoryService advService;
	
	@Autowired
	private ValidateCoupenService couponService;
	
	@Autowired
	OnlinePaymentRepo onlinePaymentRepo;

	@Value("${payment.redirect.url}")
	private String redirectFromPaymentLink;
	
	//Paypal
	public static final String PAYPAL_SUCCESS_URL = "pay/success";
	public static final String PAYPAL_CANCEL_URL = "pay/cancel";
	
	@Autowired
	private PaypalService paypalService;
	
	/**
	 * Dhiraj Code
	 *  on the basis of customer type and country splan is view to user
	 * @param userID
	 * @param subscriptionRequest
	 * @return
	 */
//	@CrossOrigin("*")
//	@PostMapping("/viewCustomerSPlan")
//	public ResponseEntity<?> ViewCustomerSPlans(@RequestBody SplanRequest sPLanRequest) {
//		logger.info(" ================ Send ViewCustomerSPlans API is Invoked ================:"
//				+ sPLanRequest.getUserId());
//		GenericResponse response = new GenericResponse<>();
//		if (!sPLanRequest.getUserId().substring(0, 2).equalsIgnoreCase("RE")) {
//			return sPlanService.findCustomerSPlanDetails(sPLanRequest);
//		}
//		response.setErrCode("ASA014");
//		response.setErrMessage(ErrorDescription.getDescription("ASA014"));
//		return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
//
//	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping("/saveUserSubscriptionPlan/{userId}")
	public ResponseEntity<?> saveCustomerSPlan(@PathVariable("userId") String userID,
			@RequestBody SubscriptionBean subscriptionRequest) {
		GenericResponse response = new GenericResponse<>();
		String flag=subscriptionRequest.getFlag();
		if("new".equalsIgnoreCase(flag))
		{
			logger.info(" ================ Send saveCustomerSPlan API is Invoked ================:" + userID);
			
			if (!userID.substring(0, 2).equalsIgnoreCase("RE")) {
				System.out.println(subscriptionRequest.toString());
				return sPlanService.saveUserSubscriptionPlan(subscriptionRequest, userID);
			
			}
			response.setErrCode("ASA014");
			response.setErrMessage(ErrorDescription.getDescription("ASA014"));
			return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
		}
		else
		{
			logger.info(" ================ renewCustomerSPlan API is Invoked ================:" + userID);
			if (!userID.substring(0, 2).equalsIgnoreCase("RE")) 
			{
				System.out.println(subscriptionRequest.toString());
				return sPlanService.renewSubscriptionPlan(subscriptionRequest, userID);
			}
			response.setErrCode("ASA014");
			response.setErrMessage(ErrorDescription.getDescription("ASA014"));
			return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);

		}
		
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping("/renewSubscriptionPlan/{userId}")
	public ResponseEntity<?> renewCustomerSPlan(@PathVariable("userId") String userID,
			@RequestBody SubscriptionBean subscriptionRequest) {
		logger.info(" ================ renewCustomerSPlan API is Invoked ================:" + userID);
		GenericResponse response = new GenericResponse<>();
		if (!userID.substring(0, 2).equalsIgnoreCase("RE")) 
		{
			System.out.println(subscriptionRequest.toString());
			return sPlanService.renewSubscriptionPlan(subscriptionRequest, userID);
		}
		response.setErrCode("ASA014");
		response.setErrMessage(ErrorDescription.getDescription("ASA014"));
		return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);

	}

	@CrossOrigin("*")
	@GetMapping("/viewSPlanToUser/{userId}")
	public ResponseEntity<?> viewSPlanToCustomer(@PathVariable("userId") String userId) {
		logger.info(" ================ Send viewSPlanToCustomer API is Invoked ================:" + userId);
		return sPlanService.findMSPlanDetails(userId);
	}

	@CrossOrigin("*")
	@GetMapping("/list/{userId}")
	public ResponseEntity<?> findAllSPlanDetailsByUserId(@PathVariable("userId") String userId) {
		logger.info(" ================ Send findAllSPlanDetailsByUserId API is Invoked ================:" + userId);
		return sPlanService.findSPlanDetailsByUserId(userId);
	}

	@CrossOrigin("*")
	@GetMapping("/getSPlan/{userId}")
	public ResponseEntity<?> getSPlanByUserId(@PathVariable("userId") String userId) {
		logger.info(" ================ Send getSPlanByUserId API is Invoked ================:" + userId);
		return sPlanService.getSPlanByUserId(userId);
	}
	
	@CrossOrigin("*")
	@GetMapping("/getInactiveSPlan/{userId}")
	public ResponseEntity<?> getInactiveSPlanByUserId(@PathVariable("userId") String userId) {
		logger.info(" ================ Send getInactiveSPlanByUserId API is Invoked ================:" + userId);
		return sPlanService.getInactiveSPlanByUserId(userId);
	}
		
	@CrossOrigin("*")
	@PostMapping("/viewCustomerSPlan")
	public ResponseEntity<?> ViewCustomerSPlans(@RequestBody SplanRequest sPLanRequest) {
		logger.info(" ================ Send ViewCustomerSPlans API is Invoked ================:"
				+ sPLanRequest.getUserId());
		GenericResponse response = new GenericResponse<>();
		if (!sPLanRequest.getUserId().substring(0, 2).equalsIgnoreCase("RE")) {
			return sPlanService.findCustomerSPlanDetails(sPLanRequest);
		}
		response.setErrCode("ASA014");
		response.setErrMessage(ErrorDescription.getDescription("ASA014"));
		return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);

	}
	
	@CrossOrigin(value = "*", allowedHeaders = "*")
	@GetMapping("/viewAllCustomerSPlan/{userId}")
	public ResponseEntity<?> viewAllCustomerSPlans(@PathVariable("userId") String userID) {
		logger.info(" ================ Send ViewAllCustomerSPlans API is Invoked ================:");
		GenericResponse response = new GenericResponse<>();
		
			return sPlanService.findAllSPlanDetailsForCustomer(userID);
		

	}
	
	@CrossOrigin(value = "*", allowedHeaders = "*")
	@PostMapping("/viewSubscriptionBySubscriptionId")
	public ResponseEntity<?> viewSPlanBySPlanId(@RequestBody SubscriptionPlanResponse srb) {
		logger.info(" ================ Send ViewSPlans By SPlanID API is Invoked ================:");
		GenericResponse response = new GenericResponse<>();
		
		NimaiMSubscription subData= sPlanService.getPlanDetailsBySubscriptionId(srb.getSubscriptionId());
		response.setData(subData);
		response.setStatus("Success");
		return new ResponseEntity<Object>(response, HttpStatus.OK);

	}
	
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping("/initiatePG")
	public ResponseEntity<?> initiatePG(@RequestBody SubscriptionPaymentBean sPymentRequest) {
		logger.info(" ================ Send Payment Request ================:"
				+ sPymentRequest.getUserId());
		GenericResponse response = new GenericResponse<>();
		Double subsAmt,vasAmt=0.0,discAmt,grandAmt;
		String subsCurrency;
		try
		{	
			NimaiCustomerSubscriptionGrandAmount ncsga=sPlanService.getCustomerAmount(sPymentRequest.getUserId());
			
			try
			{
				NimaiMSubscription subsDet=sPlanService.getPlanDetailsBySubscriptionId(sPymentRequest.getMerchantParam2());
				subsCurrency=subsDet.getCurrency();
				subsAmt=(double) subsDet.getSubscriptionAmount();
				int i;
				int vasCount = StringUtils.countOccurrencesOf(sPymentRequest.getMerchantParam4(), "-");
				System.out.println("Total VAS: "+vasCount);
				
				//int vasWithDisc=vasCount+1;
				//String vasSplitted[] =merchantParam4.split("-",vasCount);
				String vasSplitted[] =sPymentRequest.getMerchantParam4().split("-",vasCount+1);
				
				for(i=0;i<vasCount;i++)
				{
					System.out.println("Iteration: "+i);
					System.out.println("VAS: "+vasSplitted[i]);
					NimaiAdvisory vasDet=advService.getVasDetails(vasSplitted[i]);
					//String vasSplitted[] =sPymentRequest.getMerchantParam5().split("-",2);
					if(vasDet==null)
						vasAmt=0.0;
					else
						vasAmt=vasAmt+(double) vasDet.getPricing();//Double.valueOf(vasSplitted[0]);
				}
				System.out.println("VAS Amount: "+vasAmt);
				System.out.println("Discount Amt: "+vasSplitted[vasCount]);
				discAmt=Double.valueOf(vasSplitted[vasCount]);
				
				grandAmt=(subsAmt+vasAmt)-discAmt;
				System.out.println("Grand Amount: "+grandAmt);
			}
			catch(Exception e)
			{
				response.setStatus("Failure");
				response.setData("OOPs! Something went wrong. Please Buy Plan again");
				return new ResponseEntity<Object>(response, HttpStatus.OK);
			}
			
			//System.out.println("Amount found: "+ncsga.getGrandAmount());
			System.out.println("Amount Calculated: "+grandAmt);
			if(sPymentRequest.getMerchantParam3().equalsIgnoreCase("renew"))
			{
				if(Double.compare(ncsga.getGrandAmount(), grandAmt)==0)
				{
					Map<String, Object> spb=sPlanService.initiatePayment(sPymentRequest,ncsga.getGrandAmount(),subsCurrency);
					response.setStatus("Success");
					response.setData(spb);
					return new ResponseEntity<Object>(response, HttpStatus.OK);
				}
				else
				{
					response.setStatus("Failure");
					response.setData("OOPs! Something went wrong. Please Buy Plan again");
					return new ResponseEntity<Object>(response, HttpStatus.OK);
				}
			}
			else
			{
				Map<String, Object> spb=sPlanService.initiatePayment(sPymentRequest,grandAmt,subsCurrency);
				response.setStatus("Success");
				response.setData(spb);
				return new ResponseEntity<Object>(response, HttpStatus.OK);
			}
			
		}
		catch(Exception e)
		{
			response.setErrCode("ASA014");
			System.out.println("Error: "+e);
			response.setErrMessage(ErrorDescription.getDescription("ASA014"));
			return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
		}
	}
	
	/*
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping("/initiatePG")
	public ResponseEntity<?> initiatePG(@RequestBody SubscriptionPaymentBean sPymentRequest,HttpServletResponse servResponse) {
		logger.info(" ================ Send Payment Request ================:"
				+ sPymentRequest.getUserId());
		GenericResponse response = new GenericResponse<>();
		Double subsAmt,vasAmt,discAmt,grandAmt;
		try
		{	
			NimaiCustomerSubscriptionGrandAmount ncsga=sPlanService.getCustomerAmount(sPymentRequest.getUserId());
			
			try
			{
				NimaiMSubscription subsDet=sPlanService.getPlanDetailsBySubscriptionId(sPymentRequest.getMerchantParam2());
				subsAmt=(double) subsDet.getSubscriptionAmount();
				
				String vasSplitted[] =sPymentRequest.getMerchantParam4().split("-",2);
				
				NimaiAdvisory vasDet=advService.getVasDetails(vasSplitted[0]);
				//String vasSplitted[] =sPymentRequest.getMerchantParam5().split("-",2);
				if(vasDet==null)
					vasAmt=0.0;
				else
					vasAmt=(double) vasDet.getPricing();//Double.valueOf(vasSplitted[0]);
				
				discAmt=Double.valueOf(vasSplitted[1]);
				
				grandAmt=(subsAmt+vasAmt)-discAmt;
			}
			catch(Exception e)
			{
				response.setStatus("Failure");
				response.setData("OOPs! Something went wrong. Please Buy Plan again");
				return new ResponseEntity<Object>(response, HttpStatus.OK);
			}
			
			System.out.println("Amount found: "+ncsga.getGrandAmount());
			System.out.println("Amount Calculated: "+grandAmt);
			if(Double.compare(ncsga.getGrandAmount(), grandAmt)==0)
			{
				try {
					String prodDescription="merchantParam2="+sPymentRequest.getMerchantParam2()
										  +",merchantParam3="+sPymentRequest.getMerchantParam3()
										  +",merchantParam4="+sPymentRequest.getMerchantParam4()
										  +",merchantParam5="+sPymentRequest.getMerchantParam5();
					Payment payment = paypalService.createPayment(
							sPymentRequest.getMerchantParam1(),
							grandAmt, 
							"USD", 
							PaypalPaymentMethod.paypal, 
							PaypalPaymentIntent.sale,
							prodDescription, 
							sPymentRequest.getCancelURL(), 
							sPymentRequest.getRedirectURL());
					for(Links links : payment.getLinks()){
						if(links.getRel().equals("approval_url")){
							//servResponse.addHeader("Access-Control-Allow-Origin", "*");
							servResponse.sendRedirect(links.getHref());
						}
					}
				} catch (PayPalRESTException e) {
					logger.error(e.getMessage());
				}
				logger.info("Success");
			}
			else
			{
				response.setStatus("Failure");
				response.setData("OOPs! Something went wrong. Please Buy Plan again");
				return new ResponseEntity<Object>(response, HttpStatus.OK);
			}
		}
		catch(Exception e)
		{
			System.out.println("Error: "+e);
			
		}
		response.setStatus("Success");
		//response.setData("OOPs! Something went wrong. Please Buy Plan again");
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}
*/
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping("/PGResponse")
	public ResponseEntity<?> pgResponse(@RequestBody PaypalPaymentRequest ppRequest) throws IOException, ServletException {
		logger.info(" ================ Getting Payent Response ================");
		GenericResponse response = new GenericResponse<>();
		
		String userId=ppRequest.getUserId();
		//String paymentId=ppRequest.getPaymentId();
		String payerId=ppRequest.getPayerId();
		String orderId=ppRequest.getOrderId();
		
		try {
			System.out.println("OrderId: "+orderId);
			System.out.println("PayerId: "+payerId);
			if(payerId==null)
			{
				response.setStatus("Cancelled");
				return new ResponseEntity<Object>(response, HttpStatus.OK);
			}
			try {
				
				//Map<String, Object> spb=sPlanService.executePayment(orderId, payerId);
				Map<String, Object> spb=sPlanService.executePayment(orderId);
				String sts=spb.get("OrderStatus").toString();
				System.out.println("Order Status: "+sts);
				com.paypal.orders.Order order = null;
				OrdersCaptureRequest request = new OrdersCaptureRequest(orderId);
				HttpResponse<com.paypal.orders.Order> responseCapture = Credentials.client.execute(request);
				
				order=responseCapture.result();
				System.out.println("Order Capture Status: "+order.status());
				//transaction
				String paymentStatus=order.purchaseUnits().get(0).payments().captures().get(0).status();
				System.out.println("Payment Status: "+paymentStatus);
				System.out.println("Payment Txn Id: "+order.purchaseUnits().get(0).payments().captures().get(0).id());
				logger.info("Order Id: "+orderId);
				logger.info("Payment Txn Id: "+order.purchaseUnits().get(0).payments().captures().get(0).id());
				logger.info("Order Status: "+sts);
				logger.info("Order Capture Status: "+order.status());
				logger.info("Payment Status: "+paymentStatus);
				if(sts.equalsIgnoreCase("Approved") && order.status().equalsIgnoreCase("Completed") && (paymentStatus.equalsIgnoreCase("Completed"))) 
						//|| paymentStatus.equalsIgnoreCase("Pending")))
				{
					logger.info("Order Id: "+orderId);
					logger.info("Payment Txn Id: "+order.purchaseUnits().get(0).payments().captures().get(0).id());
					logger.info("Order Status: "+sts);
					logger.info("Order Capture Status: "+order.status());
					logger.info("Payment Status: "+paymentStatus);
					sPlanService.saveData(orderId,"");
					response.setStatus("Success");
					response.setData(spb);
					return new ResponseEntity<Object>(response, HttpStatus.OK);
				}
				if(sts.equalsIgnoreCase("Approved"))
				{
					sPlanService.saveData(orderId,"Failed");
					response.setStatus("Failed");
					response.setData("Transaction Failed");
					return new ResponseEntity<Object>(response, HttpStatus.OK);
				}
			}
			catch (PayPalRESTException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				sPlanService.saveData(orderId,"Failed");
				response.setStatus("Failed");
				response.setData("Transaction Failed");
				return new ResponseEntity<Object>(response, HttpStatus.OK);
			}
			catch(HttpException he)
			{
				System.out.println("Exception Decline: "+he.statusCode());
				if(he.statusCode()==422)
				{
					com.paypal.orders.Order order1 = null;
					OrdersGetRequest request = new OrdersGetRequest(orderId);	
					HttpResponse<com.paypal.orders.Order> responsePaypal = Credentials.client.execute(request);
					order1 = responsePaypal.result();
					for(LinkDescription o:order1.links())
					{
						System.out.println("---"+o.rel()+"---"+o.href());
					}
					System.out.println("Link: "+order1.links().get(0).href());
					
					response.setStatus("Declined");
					response.setData("Declined");
					return new ResponseEntity<Object>(response, HttpStatus.OK);
				}
				sPlanService.saveData(orderId,"Failed");
				response.setStatus("Failed");
				response.setData("Transaction Failed");
				return new ResponseEntity<Object>(response, HttpStatus.OK);
			}
		//	Payment payment = sPlanService.executePayment(paymentId, payerId);
			
			
			//sPlanService.saveData(payment,paymentId);
			
			/*if(payment.getState().equals("approved")){
				Map<String, Object> spb=sPlanService.completePayment(payment,paymentId);
				response.setStatus("Success");
				response.setData(spb);
				return new ResponseEntity<Object>(response, HttpStatus.OK);
			}*/
			/*if(payerId==null || payerId.equalsIgnoreCase("") || paymentId==null || paymentId.equalsIgnoreCase(""))
			{
				response.setStatus("Cancelled");
				return new ResponseEntity<Object>(response, HttpStatus.OK);
			}*/
		} 
		catch(IllegalArgumentException e)
		{
			response.setStatus("Cancelled");
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
		/*catch (PayPalRESTException e) 
		{
			logger.error(e.getMessage());
			System.out.println("Payment status: "+e.getDetails());
			response.setStatus("Failed");
			OnlinePayment op = new OnlinePayment();
			op.setUserId(userId);
			//op.setOrderId(paymentId.substring(paymentId.lastIndexOf("-") + 1));
			op.setStatus("Failed");
			op.setInsertedBy(userId);
			Date now = new Date();
			op.setInsertedDate(now);
			op.setModifiedBy(userId);

			onlinePaymentRepo.save(op);
			response.setData("Transaction Failed");
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}*/
		response.setStatus("OK");
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}
	
	/*CCAvenue
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping("/PGResponse")
	public void pgResponse(@RequestParam("encResp") String encResp,HttpServletRequest servRequest,
			HttpServletResponse servResponse) throws IOException, ServletException {
		logger.info(" ================ Getting Payent Response ================"+encResp);
		GenericResponse response = new GenericResponse<>();
		
		HashMap<String,String> finalData=sPlanService.getPaymentResponse(encResp);
		System.out.println("finalData: "+finalData);
		String sts=finalData.get("order_status");
		System.out.println("Status: "+sts);
		//response.setErrCode("ASA014");
		//response.setErrMessage(ErrorDescription.getDescription("ASA014"));
		//if(sts.equalsIgnoreCase("Success"))
		//{
		//HttpSession httpSession = servRequest.getSession(true);
		//httpSession.setAttribute("userId", finalData.get("merchant_param1"));
		//httpSession.setAttribute("status", finalData.get("order_status"));
		//httpSession.setAttribute("orderId", finalData.get("order_id"));
		//System.out.println("Session created.......");
		//System.out.println("UserId before redirect: "+httpSession.getAttribute("userId"));
		//RequestDispatcher rd=servRequest.getRequestDispatcher("http://136.232.244.190:8081/nimai-uat/#/cst/dsb/subscription");  
		//rd.forward(servRequest, servResponse);
		//redirAttr.addFlashAttribute("pgResp", finalData);
		if(sts.equalsIgnoreCase("Success"))
		{
			Cookie cSuccess=new Cookie("status", "Success");
			Cookie cPaymentTxId=new Cookie("orderId", finalData.get("order_id"));
			NimaiMSubscription subsDetail=sPlanService.getPlanDetailsBySubscriptionId(finalData.get("merchant_param2"));
			Cookie cCustSupport=new Cookie("custSupport", subsDetail.getCustomerSupport());
			Cookie cLcCount=new Cookie("lcCount", subsDetail.getlCount());
			Cookie cRelManager=new Cookie("relManager", subsDetail.getRelationshipManager());
			Cookie cSubAmount=new Cookie("subsAmount", finalData.get("amount"));
			Cookie cSubName=new Cookie("subsName", subsDetail.getSubscriptionName());
			Cookie cSubVal=new Cookie("subsValidity", String.valueOf(subsDetail.getSubscriptionValidity()));
			Cookie cSubsidiaries=new Cookie("subsidiaries", subsDetail.getSubsidiaries());
			Cookie cUserId=new Cookie("userId", finalData.get("merchant_param1"));
			Cookie cFlag=new Cookie("subsflag", finalData.get("merchant_param3"));
			Cookie cActualAmt=new Cookie("actualAmt", finalData.get("merchant_param5"));
			Cookie cPaymentMode=new Cookie("paymentMode", "Credit");
			Cookie cVasAmount;
			try
			{
				String vasSplitted[] =finalData.get("merchant_param4").split("-",2);
				NimaiAdvisory vasDet=advService.getVasDetails(vasSplitted[0]);
				cVasAmount=new Cookie("vasAmount", String.valueOf(vasDet.getPricing()));
			}
			catch(Exception e)
			{
				cVasAmount=new Cookie("vasAmount", "");
			}
			
			//cSuccess.setMaxAge(2*24*60*60);
			cSuccess.setPath("/");
			cPaymentTxId.setPath("/");
			cCustSupport.setPath("/");
			cLcCount.setPath("/");
			cRelManager.setPath("/");
			cSubAmount.setPath("/");
			cSubName.setPath("/");
			cSubVal.setPath("/");
			cSubsidiaries.setPath("/");
			cUserId.setPath("/");
			cFlag.setPath("/");
			cPaymentMode.setPath("/");
			cActualAmt.setPath("/");
			cVasAmount.setPath("/");
			
			
			servResponse.addCookie(cSuccess);
			servResponse.addCookie(cPaymentTxId);
			servResponse.addCookie(cCustSupport);
			servResponse.addCookie(cLcCount);
			servResponse.addCookie(cRelManager);
			servResponse.addCookie(cSubAmount);
			servResponse.addCookie(cSubName);
			servResponse.addCookie(cSubVal);
			servResponse.addCookie(cSubsidiaries);
			servResponse.addCookie(cUserId);
			servResponse.addCookie(cFlag);
			servResponse.addCookie(cPaymentMode);
			servResponse.addCookie(cVasAmount);
			servResponse.addCookie(cActualAmt);
		}
		else
		{
			Cookie cFailure=new Cookie("status", "Failure");
			//cFailure.setMaxAge(2*24*60*60);
			cFailure.setPath("/");
			servResponse.addCookie(cFailure);
		}
		//servResponse.setHeader("status", "Success");
		
		//http://136.232.244.190:8081/
		String userIdForRedirect=finalData.get("merchant_param1");
		if(userIdForRedirect.substring(0, 2).equalsIgnoreCase("CU") || userIdForRedirect.substring(0, 2).equalsIgnoreCase("BC"))
			servResponse.sendRedirect(redirectFromPaymentLink+"nimai-uat/#/cst/dsb/subscription");
		else
			servResponse.sendRedirect(redirectFromPaymentLink+"nimai-uat/#/bcst/dsb/subscription");
			//System.out.println("UserId after redirect: "+httpSession.getAttribute("userId"));
			//return "redirect:http://136.232.244.190:8081/nimai-uat/#/cst/dsb/subscription";
			//response.setData(finalData);
			//response.setStatus("Success");	
			//return new ResponseEntity<Object>(response, HttpStatus.OK);
		//}
		//return "";
	//	else
	//	{
	//		response.setStatus("Failure");
	//		return new ResponseEntity<Object>(response, HttpStatus.OK);
		//}
	}
	*/
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping("/checkPaymentStatus")
	public ResponseEntity<?> checkPGStatus(@RequestBody SubscriptionPaymentBean sPymentRequest) {
		logger.info(" ================ Check Payment Status ================:"
				+ sPymentRequest.getUserId());
		GenericResponse response = new GenericResponse<>();
		
		try
		{
			OnlinePayment spb=sPlanService.checkPayment(sPymentRequest);
			if(spb.getStatus().equalsIgnoreCase("Success"))
			{
				response.setStatus("Success");
				response.setData(spb);
				return new ResponseEntity<Object>(response, HttpStatus.OK);
			}
			else
			{
				response.setStatus("Failure");
				response.setData(spb);
				return new ResponseEntity<Object>(response, HttpStatus.OK);
			}
		}
		catch(Exception e)
		{
			response.setErrCode("ASA014");
			System.out.println("Error: "+e);
			response.setErrMessage("Something Went Wrong!");
			return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
		}
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping("/checkSubsidiary")
	public ResponseEntity<?> saveCustomerSPlan(@RequestBody SubscriptionBean subscriptionRequest) 
	{
		GenericResponse response = new GenericResponse<>();
		return sPlanService.checkForSubsidiary(subscriptionRequest);
		
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping("/continueBuy")
	public ResponseEntity<?> saveGrandAmount(@RequestBody CustomerSubscriptionGrandAmountBean subscriptionRequest) 
	{
		System.out.println("======= Continue Buy =======");
		Double subsAmt,vasAmt=0.0,discAmt,calculatedAmt;
		NimaiCustomerSubscriptionGrandAmount ncsga;
		GenericResponse response = new GenericResponse<>();
		
		NimaiMSubscription subsDet=sPlanService.getPlanDetailsBySubscriptionId(subscriptionRequest.getSubscriptionId());
		subsAmt=(double) subsDet.getSubscriptionAmount();
		
		if(subscriptionRequest.getDiscountId()==0)
			discAmt=0.0;
		else
		{
			HashMap<String,Double> discData=couponService.discountCalculate(subscriptionRequest.getDiscountId(), subscriptionRequest.getSubscriptionId());
			discAmt=discData.get("discount");
		}
		if(subscriptionRequest.getVasId().equalsIgnoreCase("0"))
			vasAmt=0.0;
		else
		{
			System.out.println("VAS Purchased: "+subscriptionRequest.getVasId());
			int i;
			Double vasPrice=0.0;
			int vasCount = StringUtils.countOccurrencesOf(subscriptionRequest.getVasId(), "-");
			System.out.println("Total VAS: "+vasCount+1);
			
			String vasSplitted[] =subscriptionRequest.getVasId().split("-",vasCount+1);
			for(i=0;i<vasCount+1;i++)
			{
				System.out.println("Iteration: "+i);
				NimaiAdvisory vasDet=advService.getVasDetails(vasSplitted[i]);
				vasPrice=(double) vasDet.getPricing();
				vasAmt=vasAmt+vasPrice;
			}
		}
		calculatedAmt=(subsAmt+vasAmt)-discAmt;
		
		if(Double.compare(calculatedAmt,subscriptionRequest.getGrandAmount())!=0)
		{
			response.setStatus("Failure");
			response.setData("OOPs! Something Went Wrong. Please Buy Plan Again.");
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
		try
		{
			ncsga=sPlanService.getCustomerAmount(subscriptionRequest.getUserId());
			System.out.println("Data: "+ncsga.getDiscountApplied());
		}
		catch(Exception e)
		{
		//if(ncsga.getDiscountApplied().equalsIgnoreCase("") || ncsga.getDiscountApplied()==null)
			return sPlanService.insertGrandAmountData(subscriptionRequest);
		}
		if(ncsga.getDiscountApplied()==null || ncsga.getVasApplied()==null)
		{
			return sPlanService.insertGrandAmountData(subscriptionRequest);
		}
		else
		{
			if(Double.compare(ncsga.getGrandAmount(),subscriptionRequest.getGrandAmount())==0)
			{
				response.setStatus("Success");
				return new ResponseEntity<Object>(response, HttpStatus.OK);
			}
			else
			{
				response.setStatus("Failure");
				response.setData("OOPs! Something Went Wrong. Please Apply Discount Again");
				return new ResponseEntity<Object>(response, HttpStatus.OK);
			}
		}
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping("/verifyPayment")
	public ResponseEntity<?> verifyPayment(@RequestBody CustomerSubscriptionGrandAmountBean subscriptionRequest) 
	{
		GenericResponse response = new GenericResponse<>();
		int id=subscriptionRequest.getId();
		Double amt=subscriptionRequest.getGrandAmount();
		boolean verify=sPlanService.checkPaymentData(id,amt);
		if(verify)
		{
			response.setStatus("Success");
			response.setData("Verified");
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
		else
		{
			response.setStatus("Failure");
			response.setData("OOPs! Something Went Wrong. Please Apply Discount Again");
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
	}
	
	@CrossOrigin(value = "*", allowedHeaders = "*")
	@RequestMapping(value = "/getPreviousPlans", produces = "application/json", method = RequestMethod.POST)
	public ResponseEntity<Object> getCount(@RequestBody NimaiMCustomer nimaicustomer,HttpServletRequest request) {
		logger.info("======== Getting previously purchased plan =========");
		GenericResponse response = new GenericResponse<>();
		try {

			String userId = nimaicustomer.getUserid();

			List<SubscriptionAndPaymentBean> spb=sPlanService.getLastPurchasedPlan(userId);
				response.setData(spb);
				response.setStatus("Success");
				return new ResponseEntity<Object>(response, HttpStatus.OK);
			
			
		} catch (Exception e) {
			response.setStatus("Failure");
			response.setErrCode("EXE000");
			response.setErrMessage(ErrorDescription.getDescription("EXE000"));
			return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
		}
	}
}
