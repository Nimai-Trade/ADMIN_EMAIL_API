package com.nimai.splan.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.StringTokenizer;

import javax.persistence.EntityManagerFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.nimai.splan.model.NimaiAdvisory;
import com.nimai.splan.model.NimaiCustomerSubscriptionGrandAmount;
import com.nimai.splan.model.NimaiEmailScheduler;
import com.nimai.splan.model.NimaiMCustomer;
import com.nimai.splan.model.NimaiMSubscription;
import com.nimai.splan.model.NimaiSubscriptionDetails;
import com.nimai.splan.model.OnlinePayment;
import com.nimai.splan.payload.CustomerSubscriptionGrandAmountBean;
import com.nimai.splan.payload.GenericResponse;
import com.nimai.splan.payload.SPlanApprovalBean;
import com.nimai.splan.payload.SPlanResponseBean;
import com.nimai.splan.payload.SplanRequest;
import com.nimai.splan.payload.SubscriptionAndPaymentBean;
import com.nimai.splan.payload.SubscriptionBean;
import com.nimai.splan.payload.SubscriptionPaymentBean;
import com.nimai.splan.payload.SubscriptionPlanResponse;
import com.nimai.splan.payload.banksSplansReponse;
import com.nimai.splan.payload.customerSPlansResponse;
import com.nimai.splan.repository.NimaiAdvisoryRepo;
import com.nimai.splan.repository.NimaiCustomerGrandAmountRepo;
import com.nimai.splan.repository.NimaiEmailSchedulerRepository;
import com.nimai.splan.repository.NimaiMCustomerRepository;
import com.nimai.splan.repository.NimaiMSPlanRepository;
import com.nimai.splan.repository.OnlinePaymentRepo;
import com.nimai.splan.repository.SubscriptionPlanRepository;
import com.nimai.splan.utility.ErrorDescription;
import com.nimai.splan.utility.ModelMapper;
import com.nimai.splan.utility.SPlanUniqueNumber;
import com.nimai.splan.utility.SubscriptionPlanValidation;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import com.paypal.http.HttpResponse;
import com.paypal.http.exceptions.HttpException;
import com.paypal.orders.AddressPortable;
import com.paypal.orders.AmountBreakdown;
import com.paypal.orders.AmountWithBreakdown;
import com.paypal.orders.ApplicationContext;
import com.paypal.orders.LinkDescription;
import com.paypal.orders.Money;
import com.paypal.orders.Name;
import com.paypal.orders.OrderRequest;
import com.paypal.orders.OrdersCaptureRequest;
import com.paypal.orders.OrdersCreateRequest;
import com.paypal.orders.OrdersGetRequest;
import com.paypal.orders.PaymentMethod;
import com.paypal.orders.PurchaseUnitRequest;
import com.nimai.splan.utility.Credentials;

@Service
@Transactional
public class SubscriptionPlanServiceImpl implements SubscriptionPlanService {

	private static Logger logger = LoggerFactory.getLogger(SubscriptionPlanServiceImpl.class);

	private static final String randomString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

	@Autowired
	SubscriptionPlanValidation validationDao;

	@Autowired
	NimaiMSPlanRepository subscriptionRepo;

	@Autowired
	NimaiMCustomerRepository userRepository;

	@Autowired
	private NimaiAdvisoryService advService;
	
	@Autowired
	SubscriptionPlanRepository subscriptionDetailsRepository;
	
	@Autowired
	NimaiAdvisoryRepo advRepo;

	@Autowired
	NimaiEmailSchedulerRepository emailDetailsRepository;
	
	@Autowired
	NimaiCustomerGrandAmountRepo nimaiCustomerGrandAmtRepository;

	@Autowired
	OnlinePaymentRepo onlinePaymentRepo;

	@Autowired
	EntityManagerFactory em;

	@Autowired
	NimaiMSPlanRepository masterSPlanRepo;

	@Value("${payment.ccavenue.workingkey}")
	private String paymentWorkingKey;

	/*@Value("${payment.ccavenue.accessCode}")
	private String paymentAccessCode;

	@Value("${payment.ccavenue.merchantId}")
	private String paymentMerchantId;
	*/
	@Value("${paypal.mode}")
	private String mode;
	
	@Value("${paypal.client.app}")
	private String clientId;// = "AYSq3RDGsmBLJE-otTkBtM-jBRd1TCQwFf9RGfwddNXWz0uFU9ztymylOhRS";
    
	@Value("${paypal.client.secret}")
	private String clientSecret;// = "EGnHDxD_qRPdaLdZz8iCr8N7_MzF-YHPTkjs6NKYQvQSBngp4PTTVWkPZRbL";
    
	@Value("${payment.redirect.url}")
	private String redirectFromPaymentLink;
	
	String ccavenueParameterNames[] = { "merchant_id", "order_id", "currency", "amount", "redirect_url", "cancel_url",
			"language", "billing_name", "billing_address", "billing_city", "billing_state", "billing_zip",
			"billing_country", "billing_tel", "billing_email", "delivery_name", "delivery_address", "delivery_city",
			"delivery_state", "delivery_zip", "delivery_country", "delivery_tel", "merchant_param1", "merchant_param2",
			"merchant_param3", "merchant_param4", "merchant_param5", "promo_code", "customer_identifier" };

	@Override
	public ResponseEntity<?> saveUserSubscriptionPlan(SubscriptionBean subscriptionRequest, String userId) {
		GenericResponse response = new GenericResponse<>();
		String paymentTrId = "";
		logger.info(" ================ Send saveUserSubscriptionPlan method Invoked ================");
		try {
			if (subscriptionRequest.getSubscriptionId() != null) {

				Optional<NimaiMCustomer> mCustomer = userRepository.findByUserId(userId);
				if (mCustomer.isPresent()) {
					List<NimaiSubscriptionDetails> subscriptionEntity = subscriptionDetailsRepository
							.findAllByUserId(userId);
					if (!subscriptionEntity.isEmpty()) {
						for (NimaiSubscriptionDetails plan : subscriptionEntity) {
							plan.setStatus("Inactive");
							subscriptionDetailsRepository.save(plan);
						}
					}
					NimaiSubscriptionDetails subScriptionDetails = new NimaiSubscriptionDetails();
					NimaiEmailScheduler schedularData = new NimaiEmailScheduler();

					subScriptionDetails.setSubscriptionName(subscriptionRequest.getSubscriptionName());
					subScriptionDetails.setUserid(mCustomer.get());
					subScriptionDetails.setSubscriptionValidity(subscriptionRequest.getSubscriptionValidity());
					subScriptionDetails.setSubscriptionId(subscriptionRequest.getSubscriptionId());
					subScriptionDetails.setRemark(subscriptionRequest.getRemark());
					subScriptionDetails.setSubscriptionAmount(subscriptionRequest.getSubscriptionAmount());
					subScriptionDetails.setlCount(subscriptionRequest.getLcCount());
					subScriptionDetails.setSubsidiaries(subscriptionRequest.getSubsidiaries());
					subScriptionDetails.setRelationshipManager(subscriptionRequest.getRelationshipManager());
					subScriptionDetails.setCustomerSupport(subscriptionRequest.getCustomerSupport());
					subScriptionDetails.setIsVasApplied(subscriptionRequest.getIsVasApplied());
					subScriptionDetails.setVasAmount(subscriptionRequest.getVasAmount());
					subScriptionDetails.setDiscountId(subscriptionRequest.getDiscountId());
					subScriptionDetails.setDiscount(subscriptionRequest.getDiscount());
					subScriptionDetails.setGrandAmount(subscriptionRequest.getGrandAmount());
					subScriptionDetails.setInsertedBy(mCustomer.get().getFirstName());
					subScriptionDetails.setsPLanCountry(mCustomer.get().getAddress3());
					subScriptionDetails.setInsertedDate(new Date());
					String customerType = subscriptionRequest.getSubscriptionId().substring(0, 2);
					if (customerType.equalsIgnoreCase("BA")) {
						subScriptionDetails.setCustomerType("Bank");
					} else {
						subScriptionDetails.setCustomerType("Customer");
					}
					SPlanUniqueNumber endDate = new SPlanUniqueNumber();
					int year = endDate.getNoOfyears(subScriptionDetails.getSubscriptionValidity());
					int month = endDate.getNoOfMonths(subScriptionDetails.getSubscriptionValidity());
					System.out.println(year);
					System.out.println(month);
					subScriptionDetails.setStatus("ACTIVE");

					Calendar cal = Calendar.getInstance();
					Date today = cal.getTime();
					cal.add(Calendar.YEAR, year);
					cal.add(Calendar.MONTH, month);
					Date sPlanEndDate = cal.getTime();
					subScriptionDetails.setSubscriptionStartDate(today);
					subScriptionDetails.setSubscriptionEndDate(sPlanEndDate);
					subScriptionDetails.setRenewalEmailStatus("Pending");
					if (subscriptionRequest.getModeOfPayment().equalsIgnoreCase("Wire"))
					{
						subScriptionDetails.setPaymentMode("Wire");
						subScriptionDetails.setPaymentStatus("Pending");
					}
					else
					{
						subScriptionDetails.setPaymentMode("Credit");
						subScriptionDetails.setPaymentStatus("Approved");
					}
					NimaiSubscriptionDetails subScription = subscriptionDetailsRepository.save(subScriptionDetails);

					if (subscriptionRequest.getModeOfPayment().equalsIgnoreCase("Wire")) {
						userRepository.updatePaymentStatus(mCustomer.get().getUserid());
						userRepository.updatePlanPurchasedStatus(mCustomer.get().getUserid());
						String invoiceId= generatePaymentTtransactionID(10);
						paymentTrId = generatePaymentTtransactionID(15);
						userRepository.updatePaymentTransactionId(mCustomer.get().getUserid(), invoiceId);
						userRepository.updatePaymentMode(subscriptionRequest.getModeOfPayment(),
								mCustomer.get().getUserid());
						Double gstValue=subscriptionRepo.getGSTValue()/100;
						Double planPriceGST=subScription.getGrandAmount()+(subScription.getGrandAmount()*gstValue);
						System.out.println("gstValue: "+gstValue);
						System.out.println("planPriceGST: "+planPriceGST);
						String finalPrice = String.format("%.2f", planPriceGST);
						subscriptionDetailsRepository.updatePaymentTxnIdForWire(mCustomer.get().getUserid(), paymentTrId, invoiceId, finalPrice);
					} else {
						userRepository.updatePaymentMode(subscriptionRequest.getModeOfPayment(),
								mCustomer.get().getUserid());
						userRepository.updatePlanPurchasedStatus(mCustomer.get().getUserid());
						userRepository.updatePaymentStatusForCredit(mCustomer.get().getUserid());
						OnlinePayment paymentDet=onlinePaymentRepo.getDetailsByUserId(mCustomer.get().getUserid());
						if(subscriptionRequest.getGrandAmount()==0)
						{
							String invoiceId=generatePaymentTtransactionID(10);
							userRepository.updatePaymentTransactionId(mCustomer.get().getUserid(), invoiceId);
							subscriptionDetailsRepository.updateInvId(mCustomer.get().getUserid(), invoiceId);
						}
						else
						{
							userRepository.updatePaymentTransactionId(mCustomer.get().getUserid(), paymentDet.getInvoiceId());
							subscriptionDetailsRepository.updatePaymentTxnIdInvId(mCustomer.get().getUserid(), paymentDet.getOrderId(), paymentDet.getInvoiceId());
						}
						
					}
					//userRepository.updateKycStatus(true, mCustomer.get().getUserid());
					// userRepository.updatePaymentMode(subscriptionRequest.getModeOfPayment(),
					// mCustomer.get().getUserid());
					// paymentTrId=generatePaymentTtransactionID(15);
					// userRepository.updatePaymentTransactionId(mCustomer.get().getUserid(),paymentTrId);
					/*
					 * String
					 * paymentMode=userRepository.getModeOfPayment(mCustomer.get().getUserid());
					 * System.out.println("Mode Of Payment: "+paymentMode);
					 * if(paymentMode.equalsIgnoreCase("CreditPending")) {
					 * subScriptionDetails.setStatus("INACTIVE"); } else {
					 * 
					 * }
					 */

					/* schedular data requirement Code */
					schedularData.setUserid(mCustomer.get().getUserid());
					String sPlanValidity = Integer.toString(subscriptionRequest.getSubscriptionValidity());
					String sPlanAmount = Integer.toString(subscriptionRequest.getSubscriptionAmount());
					schedularData.setSubscriptionId(subscriptionRequest.getSubscriptionId());
					schedularData.setCustomerSupport(subscriptionRequest.getCustomerSupport());
					schedularData.setRelationshipManager(subscriptionRequest.getRelationshipManager());
					schedularData.setSubscriptionAmount(sPlanAmount);
					if (subscriptionRequest.getUserId().substring(0, 2).equalsIgnoreCase("BA")) {
						schedularData.setUserName(mCustomer.get().getFirstName());
						schedularData.setEmailId(mCustomer.get().getEmailAddress());
					} else if (subscriptionRequest.getUserId().substring(0, 2).equalsIgnoreCase("CU")
							|| subscriptionRequest.getUserId().substring(0, 2).equalsIgnoreCase("BC")) {
						String emailId = "";
						if (subscriptionRequest.getEmailID() != null) {
							emailId = subscriptionRequest.getEmailID() + "," + mCustomer.get().getEmailAddress();
						} else {
							emailId = mCustomer.get().getEmailAddress();
						}
						schedularData.setUserName(mCustomer.get().getFirstName());

						schedularData.setEmailId(emailId);
					}
					schedularData.setSubscriptionStartDate(today);
					schedularData.setSubscriptionEndDate(sPlanEndDate);
					schedularData.setSubscriptionName(subscriptionRequest.getSubscriptionName());
					schedularData.setSubscriptionValidity(sPlanValidity);
					schedularData.setEmailStatus("pending");
					schedularData.setEvent("Cust_Splan_email");
					schedularData.setInsertedDate(today);
					NimaiEmailScheduler emailData = emailDetailsRepository.save(schedularData);
					/* ----------------sceduler Data--------------------- */
					response.setErrCode("ASA001");
					response.setData(paymentTrId);
					response.setErrMessage(ErrorDescription.getDescription("ASA001"));
					return new ResponseEntity<Object>(response, HttpStatus.OK);
				} else {
					response.setStatus("Failure");
					response.setErrCode("ASA003");
					response.setErrMessage(ErrorDescription.getDescription("ASA003"));
					return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
				}
			} else {
				response.setStatus("Failure");
				response.setErrCode("ASA009");
				response.setErrMessage(ErrorDescription.getDescription("ASA009"));
				return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus("Failure");
			response.setErrCode("EXE000");
			response.setErrMessage(ErrorDescription.getDescription("EXE000") + e.getMessage());
			return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
		}

	}

	@Override
	public ResponseEntity<?> renewSubscriptionPlan(SubscriptionBean subscriptionRequest, String userId) {
		GenericResponse response = new GenericResponse<>();
		String paymentTrId = "";
		logger.info(" ================ renewSubscriptionPlan method Invoked ================");
		Calendar cal = Calendar.getInstance();
		Date today = cal.getTime();
		int addOnCredit = 0, utilzedLcCount=0, days = 0;
		try {
			if (subscriptionRequest.getSubscriptionId() != null) {
				Optional<NimaiMCustomer> mCustomer = userRepository.findByUserId(userId);
				NimaiSubscriptionDetails details=subscriptionDetailsRepository.findByUserId(mCustomer.get().getUserid());

				if (mCustomer.isPresent()) {
					List<NimaiSubscriptionDetails> subscriptionEntity = subscriptionDetailsRepository
							.findAllByUserId(userId);
					if (!subscriptionEntity.isEmpty()) {
						for (NimaiSubscriptionDetails plan : subscriptionEntity) {
							if (plan.getSubsidiaryUtilizedCount() > Integer
									.valueOf(subscriptionRequest.getSubsidiaries())) {
								if (userId.substring(0, 2).equalsIgnoreCase("CU") ) {//|| userId.substring(0, 2).equalsIgnoreCase("BC") ) {

									response.setStatus("Failure");
									response.setErrMessage(
											"You had already Active Subsidiary. Kindly select appropriate Plan.");
									return new ResponseEntity<Object>(response, HttpStatus.OK);
								} /*if (userId.substring(0, 2).equalsIgnoreCase("BA")) {
									response.setStatus("Failure");
									response.setErrMessage(
											"You had already Active Additional user. Kindly select appropriate Plan.");
									return new ResponseEntity<Object>(response, HttpStatus.OK);
								} */
								/*
								else {
									response.setStatus("Failure");
									response.setErrMessage(
											"You had already Active Subsidiary/Additional user. Kindly select appropriate Plan.");
									return new ResponseEntity<Object>(response, HttpStatus.OK);
								}*/

							}
							if ((plan.getSubscriptionEndDate().after(today)
									|| plan.getSubscriptionEndDate().compareTo(today) <= 0)
									&& (Integer.valueOf(plan.getlCount()) - plan.getLcUtilizedCount()) > 0) {
								// System.out.println("Subscription End date: "+plan.getSubscriptionEndDate());
								// System.out.println("Comparision between date:
								// "+plan.getSubscriptionEndDate().compareTo(today));
								// System.out.println("LC Count:"+Integer.valueOf(plan.getlCount()));
								// System.out.println("LC Utilized Count:"+plan.getLcUtilizedCount());
								if (plan.getSubscriptionEndDate().compareTo(today) <= 0)
									days = (int) ((plan.getSubscriptionEndDate().getTime() - today.getTime())
											/ (1000 * 60 * 60 * 24));
								else
									days = (int) ((plan.getSubscriptionEndDate().getTime() - today.getTime())
											/ (1000 * 60 * 60 * 24)) + 1;
								if(!plan.getPaymentStatus().equalsIgnoreCase("Rejected"))
									addOnCredit = (Integer.valueOf(plan.getlCount()) - plan.getLcUtilizedCount());
								else
									addOnCredit = 0;
								System.out.println("addOnCredit:" + addOnCredit);

							}
							// userRepository.updatePaymentStatus(mCustomer.get().getUserid());
							plan.setStatus("Inactive");
							subscriptionDetailsRepository.save(plan);
						}
					} else {
						NimaiSubscriptionDetails inactiveSubscriptionEntity = subscriptionDetailsRepository
								.findOnlyLatestInactiveSubscriptionByUserId(userId);
						int noOfDays = (int) ((today.getTime()
								- inactiveSubscriptionEntity.getSubscriptionEndDate().getTime())
								/ (1000 * 60 * 60 * 24));
						System.out.println("Diff between exp and current date: " + noOfDays);
						if (inactiveSubscriptionEntity.getSubsidiaryUtilizedCount() >= Integer
								.valueOf(subscriptionRequest.getSubsidiaries()) && (userId.substring(0, 2).equalsIgnoreCase("CU"))) {
							response.setStatus("Failure");
							response.setErrMessage(
									"You had already Active Subsidiary. Kindly select appropriate Plan.");
							return new ResponseEntity<Object>(response, HttpStatus.OK);
						}
						if (noOfDays < 60 && (Integer.valueOf(inactiveSubscriptionEntity.getlCount())
								- inactiveSubscriptionEntity.getLcUtilizedCount()) > 0) {
							if(!inactiveSubscriptionEntity.getPaymentStatus().equalsIgnoreCase("Rejected"))
								addOnCredit = (Integer.valueOf(inactiveSubscriptionEntity.getlCount())
									- inactiveSubscriptionEntity.getLcUtilizedCount());
							else
								addOnCredit=0;
						}
						
					}
					
					System.out.println("AddOnCredit: "+addOnCredit);
					System.out.println("UtilizedLcCount: "+utilzedLcCount);
					NimaiSubscriptionDetails subScriptionDetails = new NimaiSubscriptionDetails();
					NimaiEmailScheduler schedularData = new NimaiEmailScheduler();
					
					subScriptionDetails.setSubscriptionName(subscriptionRequest.getSubscriptionName());
					subScriptionDetails.setUserid(mCustomer.get());
					subScriptionDetails.setSubscriptionValidity(subscriptionRequest.getSubscriptionValidity());
					subScriptionDetails.setSubscriptionId(subscriptionRequest.getSubscriptionId());
					subScriptionDetails.setRemark(subscriptionRequest.getRemark());
					subScriptionDetails.setSubscriptionAmount(subscriptionRequest.getSubscriptionAmount());
					subScriptionDetails
							.setlCount(String.valueOf(Integer.valueOf(subscriptionRequest.getLcCount()) + addOnCredit));
					subScriptionDetails.setLcUtilizedCount(utilzedLcCount);
					subScriptionDetails.setSubsidiaries(subscriptionRequest.getSubsidiaries());
					subScriptionDetails.setIsVasApplied(subscriptionRequest.getIsVasApplied());
					subScriptionDetails.setRelationshipManager(subscriptionRequest.getRelationshipManager());
					subScriptionDetails.setVasAmount(subscriptionRequest.getVasAmount());
					subScriptionDetails.setDiscountId(subscriptionRequest.getDiscountId());
					subScriptionDetails.setDiscount(subscriptionRequest.getDiscount());
					System.out.println("Grand Amount: "+subscriptionRequest.getGrandAmount());
					Double toBeTruncated = new Double(subscriptionRequest.getGrandAmount());
					Double truncatedDouble = BigDecimal.valueOf(toBeTruncated)
					    .setScale(2, RoundingMode.HALF_UP)
					    .doubleValue();
					subScriptionDetails.setGrandAmount(truncatedDouble);
					subScriptionDetails.setCustomerSupport(subscriptionRequest.getCustomerSupport());
					subScriptionDetails.setInsertedBy(mCustomer.get().getFirstName());
					subScriptionDetails.setsPLanCountry(mCustomer.get().getAddress3());
					subScriptionDetails.setInsertedDate(new Date());
				//Change on 22 Mar 2021 - findLatestIn....	
					NimaiSubscriptionDetails inactiveSubscriptionEntity2 = subscriptionDetailsRepository
							.findOnlyLatestInactiveSubscriptionByUserId(userId);
					System.out.println(""+inactiveSubscriptionEntity2.getSubsidiaryUtilizedCount());
					System.out.println(""+subscriptionRequest.getSubsidiaries());
					if(inactiveSubscriptionEntity2.getSubsidiaryUtilizedCount() == Integer.valueOf(subscriptionRequest.getSubsidiaries())) {
						subScriptionDetails.setSubsidiaryUtilizedCount(inactiveSubscriptionEntity2.getSubsidiaryUtilizedCount());
					}else {
						if(inactiveSubscriptionEntity2.getSubsidiaryUtilizedCount()==0)
						{
							subScriptionDetails.setSubsidiaryUtilizedCount(0);
						}
						else
						{
						//subScriptionDetails.setSubsidiaryUtilizedCount(Integer.valueOf(subscriptionRequest.getSubsidiaries())-inactiveSubscriptionEntity2.getSubsidiaryUtilizedCount());
							subScriptionDetails.setSubsidiaryUtilizedCount(Integer.valueOf(inactiveSubscriptionEntity2.getSubsidiaryUtilizedCount()));
						}
					}
					
					String customerType = subscriptionRequest.getSubscriptionId().substring(0, 2);
					if (customerType.equalsIgnoreCase("BA")) {
						subScriptionDetails.setCustomerType("Bank");
					} else {
						subScriptionDetails.setCustomerType("Customer");
					}
					SPlanUniqueNumber endDate = new SPlanUniqueNumber();
					int year = endDate.getNoOfyears(subScriptionDetails.getSubscriptionValidity());
					int month = endDate.getNoOfMonths(subScriptionDetails.getSubscriptionValidity());
					System.out.println(year);
					System.out.println(month);
					subScriptionDetails.setStatus("ACTIVE");
					cal.add(Calendar.DATE, days);
					cal.add(Calendar.YEAR, year);
					cal.add(Calendar.MONTH, month);
					Date sPlanEndDate = cal.getTime();
					subScriptionDetails.setSubscriptionStartDate(today);
					subScriptionDetails.setSubscriptionEndDate(sPlanEndDate);
					subScriptionDetails.setRenewalEmailStatus("Pending");
					System.out.println("Current Date: " + today);
					if (subscriptionRequest.getModeOfPayment().equalsIgnoreCase("Wire"))
					{
						subScriptionDetails.setPaymentMode("Wire");
						subScriptionDetails.setPaymentStatus("Pending");
					}
					else
					{
						subScriptionDetails.setPaymentMode("Credit");
						subScriptionDetails.setPaymentStatus("Approved");
					}
					NimaiSubscriptionDetails subScription = subscriptionDetailsRepository.save(subScriptionDetails);
					System.out.println("Grand Amount after save: "+subScription.getGrandAmount());
					//userRepository.updateKycStatus(true, mCustomer.get().getUserid());
					if (subscriptionRequest.getModeOfPayment().equalsIgnoreCase("Wire")) {
						advService.inactiveVASStatus(userId);
						userRepository.updatePaymentStatus(mCustomer.get().getUserid());
						userRepository.updatePlanPurchasedStatus(mCustomer.get().getUserid());
						String invoiceId=generatePaymentTtransactionID(10);
						paymentTrId = generatePaymentTtransactionID(15);
						userRepository.updatePaymentTransactionId(mCustomer.get().getUserid(), invoiceId);
						userRepository.updatePaymentMode(subscriptionRequest.getModeOfPayment(),
								mCustomer.get().getUserid());
						subscriptionDetailsRepository.updatePaymentTxnIdInvId(mCustomer.get().getUserid(), paymentTrId, invoiceId);
						Double gstValue=subscriptionRepo.getGSTValue()/100;
						Double planPriceGST=subScription.getGrandAmount()+(subScription.getGrandAmount()*gstValue);
						System.out.println("gstValue: "+gstValue);
						System.out.println("planPriceGST: "+planPriceGST);
						String finalPrice = String.format("%.2f", planPriceGST);
						subscriptionDetailsRepository.updatePaymentTxnIdForWire(mCustomer.get().getUserid(), paymentTrId, invoiceId, finalPrice);
						if(Integer.valueOf(inactiveSubscriptionEntity2.getlCount())<Integer.valueOf(inactiveSubscriptionEntity2.getLcUtilizedCount()))
						{
							utilzedLcCount=Integer.valueOf(inactiveSubscriptionEntity2.getLcUtilizedCount())
									-Integer.valueOf(inactiveSubscriptionEntity2.getlCount());
							subscriptionDetailsRepository.updateLCUtilzed(mCustomer.get().getUserid(),utilzedLcCount);
						}
							

					} else {
						userRepository.updatePaymentMode(subscriptionRequest.getModeOfPayment(),
								mCustomer.get().getUserid());
						userRepository.updatePlanPurchasedStatus(mCustomer.get().getUserid());
						userRepository.updatePaymentStatusForCredit(mCustomer.get().getUserid());
						OnlinePayment paymentDet=onlinePaymentRepo.getDetailsByUserId(mCustomer.get().getUserid());
						if(subscriptionRequest.getGrandAmount()==0)
						{
							String invoiceId=generatePaymentTtransactionID(10);
							userRepository.updatePaymentTransactionId(mCustomer.get().getUserid(), invoiceId);
							subscriptionDetailsRepository.updateInvId(mCustomer.get().getUserid(), invoiceId);
						}
						else
						{
							userRepository.updatePaymentTransactionId(mCustomer.get().getUserid(), paymentDet.getInvoiceId());
							subscriptionDetailsRepository.updatePaymentTxnIdInvId(mCustomer.get().getUserid(), paymentDet.getOrderId(), paymentDet.getInvoiceId());
						}
						if(Integer.valueOf(inactiveSubscriptionEntity2.getlCount())<Integer.valueOf(inactiveSubscriptionEntity2.getLcUtilizedCount()))
						{
							utilzedLcCount=Integer.valueOf(inactiveSubscriptionEntity2.getLcUtilizedCount())
									-Integer.valueOf(inactiveSubscriptionEntity2.getlCount());
							subscriptionDetailsRepository.updateLCUtilzed(mCustomer.get().getUserid(),utilzedLcCount);
						}
					}
					/* schedular data requirement Code */
					schedularData.setUserid(mCustomer.get().getUserid());
					String sPlanValidity = Integer.toString(subscriptionRequest.getSubscriptionValidity());
					String sPlanAmount = Integer.toString(subscriptionRequest.getSubscriptionAmount());
					schedularData.setSubscriptionId(subscriptionRequest.getSubscriptionId());
					schedularData.setCustomerSupport(subscriptionRequest.getCustomerSupport());
					schedularData.setRelationshipManager(subscriptionRequest.getRelationshipManager());
					schedularData.setSubscriptionAmount(sPlanAmount);
					if (subscriptionRequest.getUserId().substring(0, 2).equalsIgnoreCase("BA")) {
						schedularData.setUserName(mCustomer.get().getFirstName());
						
							
							schedularData.setEmailId(mCustomer.get().getEmailAddress());
					
						
					} else if (subscriptionRequest.getUserId().substring(0, 2).equalsIgnoreCase("CU")
							|| subscriptionRequest.getUserId().substring(0, 2).equalsIgnoreCase("BC")) {
						String emailId = "";
						if (subscriptionRequest.getEmailID() != null) {
							emailId = subscriptionRequest.getEmailID() + "," + mCustomer.get().getEmailAddress();
						} else {
							emailId = mCustomer.get().getEmailAddress();
						}
						schedularData.setUserName(mCustomer.get().getFirstName());

						schedularData.setEmailId(emailId);
					}
					schedularData.setSubscriptionEndDate(sPlanEndDate);
					schedularData.setSubscriptionStartDate(today);
					schedularData.setSubscriptionName(subscriptionRequest.getSubscriptionName());
					schedularData.setSubscriptionValidity(sPlanValidity);
					schedularData.setEmailStatus("pending");
					schedularData.setEvent("Cust_Splan_email");
					schedularData.setInsertedDate(today);
					NimaiEmailScheduler emailData = emailDetailsRepository.save(schedularData);
					/* ----------------sceduler Data--------------------- */
					response.setErrCode("ASA001");
					response.setErrMessage("Subscription Plan Renewed Successfully.");
					response.setData(paymentTrId);
					return new ResponseEntity<Object>(response, HttpStatus.OK);
				} else {
					response.setStatus("Failure");
					response.setErrCode("ASA003");
					response.setErrMessage(ErrorDescription.getDescription("ASA003"));
					return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
				}
			} else {
				response.setStatus("Failure");
				response.setErrCode("ASA009");
				response.setErrMessage(ErrorDescription.getDescription("ASA009"));
				return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus("Failure");
			response.setErrCode("EXE000");
			response.setErrMessage(ErrorDescription.getDescription("EXE000") + e.getMessage());
			return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
		}

	}

	@Override
	public ResponseEntity<?> findSPlanDetailsByUserId(String userId) {
		logger.info(" ================ Send findSPlanDetailsByUserId method Invoked ================");
		GenericResponse response = new GenericResponse<>();
		try {
			List<NimaiSubscriptionDetails> subscriptionEntity = subscriptionDetailsRepository.findAllByUserId(userId);
			if (subscriptionEntity.isEmpty()) {
				response.setStatus("Failure");
				response.setErrCode("ASA002");
				response.setErrMessage(ErrorDescription.getDescription("ASA002"));
				return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
			} else {
				List<SubscriptionPlanResponse> subscriptionBean = new ArrayList<SubscriptionPlanResponse>();
				for (NimaiSubscriptionDetails temp : subscriptionEntity) {
					SubscriptionPlanResponse responseBean = new SubscriptionPlanResponse();
					responseBean.setSubscriptionAmount(temp.getSubscriptionAmount());
					responseBean.setSubscriptionName(temp.getSubscriptionName());
					responseBean.setSubscriptionId(temp.getSubscriptionId());
					responseBean.setSubscriptionValidity(temp.getSubscriptionValidity());
					responseBean.setLcCount(temp.getlCount());
					responseBean.setRemark(temp.getRemark());
					responseBean.setUserId(temp.getUserid().getUserid());
					responseBean.setStatus(temp.getStatus());
					responseBean.setSubsidiaries(temp.getSubsidiaries());
					responseBean.setRelationshipManager(temp.getRelationshipManager());
					responseBean.setCustomerSupport(temp.getCustomerSupport());
					responseBean.setIsVasApplied(temp.getIsVasApplied());
					responseBean.setVasAmount(temp.getVasAmount());
					responseBean.setDiscount(temp.getDiscount());
					responseBean.setDiscountId(temp.getDiscountId());
					responseBean.setGrandAmount(temp.getGrandAmount());
					subscriptionBean.add(responseBean);
					response.setData(subscriptionBean);

				}
				return new ResponseEntity<Object>(response, HttpStatus.OK);
			}
		} catch (Exception e) {
			response.setStatus("Failure");
			response.setErrCode("EXE000");
			response.setErrMessage(ErrorDescription.getDescription("EXE000") + e.getMessage());
			return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
		}
	}

	private Date getDate(String pattern) throws ParseException {
		return new SimpleDateFormat(pattern).parse(new SimpleDateFormat(pattern).format(new Date()));
	}

	@Override
	public ResponseEntity<?> getSPlanByUserId(String userId) {
		GenericResponse response = new GenericResponse<>();
		logger.info(" ================ getSPlanByUserId method Invoked ================");
		try {
			List<NimaiSubscriptionDetails> subscriptionEntity = subscriptionDetailsRepository.findAllByUserId(userId);
			if (subscriptionEntity.isEmpty()) {
				response.setStatus("Failure");
				response.setErrCode("ASA002");
				response.setErrMessage(ErrorDescription.getDescription("ASA002"));
				return new ResponseEntity<Object>(response, HttpStatus.OK);
			} else {
				//SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");  
				List<SubscriptionPlanResponse> subscriptionBean = new ArrayList<SubscriptionPlanResponse>();
				//OnlinePayment onlinePayDet=onlinePaymentRepo.getDetailsByUserId(userId);
				for (NimaiSubscriptionDetails temp : subscriptionEntity) {
					SubscriptionPlanResponse responseBean = new SubscriptionPlanResponse();
					
					if (temp.getStatus().equals("ACTIVE") && temp.getFlag() == 0) {

						responseBean.setSubscriptionAmount(temp.getSubscriptionAmount());
						responseBean.setSubscriptionName(temp.getSubscriptionName());
						responseBean.setSubscriptionId(temp.getSubscriptionId());
						responseBean.setSubscriptionValidity(temp.getSubscriptionValidity());
						responseBean.setLcCount(temp.getlCount());
						responseBean.setRemark(temp.getRemark());
						responseBean.setUserId(temp.getUserid().getUserid());
						responseBean.setStatus(temp.getStatus());
						
						responseBean.setSubsidiaries(temp.getSubsidiaries());
						responseBean.setRelationshipManager(temp.getRelationshipManager());
						responseBean.setCustomerSupport(temp.getCustomerSupport());
						responseBean.setIsVasApplied(temp.getIsVasApplied());
						responseBean.setVasAmount(temp.getVasAmount());
						responseBean.setDiscountId(temp.getDiscountId());
						responseBean.setDiscount(temp.getDiscount());
						responseBean.setGrandAmount(temp.getGrandAmount());
						responseBean.setSubsStartDate(temp.getInsertedDate());
						responseBean.setInvoiceId(temp.getInvoiceId());
						subscriptionBean.add(responseBean);
						response.setData(subscriptionBean);

						return new ResponseEntity<Object>(response, HttpStatus.OK);
					} else {
						response.setStatus("Failure");
						response.setErrCode("ASA008");
						response.setErrMessage(ErrorDescription.getDescription("ASA008"));
						return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
					}
				}
			}
		} catch (Exception e) {
			response.setStatus("Failure");
			response.setErrCode("EXE000");
			response.setErrMessage(ErrorDescription.getDescription("EXE000") + e.getMessage());
			return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
	}

	@Override
	public ResponseEntity<?> getInactiveSPlanByUserId(String userId) {
		GenericResponse response = new GenericResponse<>();
		logger.info(" ================ getInactiveSPlanByUserId method Invoked ================");
		try {
			List<NimaiSubscriptionDetails> subscriptionEntity = subscriptionDetailsRepository.findAllInactivePlanByUserId(userId);
			if (subscriptionEntity.isEmpty()) {
				response.setStatus("Failure");
				response.setErrCode("ASA002");
				response.setErrMessage(ErrorDescription.getDescription("ASA002"));
				return new ResponseEntity<Object>(response, HttpStatus.OK);
			} else {
				//SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");  
				List<SubscriptionPlanResponse> subscriptionBean = new ArrayList<SubscriptionPlanResponse>();
				//OnlinePayment onlinePayDet=onlinePaymentRepo.getDetailsByUserId(userId);
				for (NimaiSubscriptionDetails temp : subscriptionEntity) {
					SubscriptionPlanResponse responseBean = new SubscriptionPlanResponse();
					
					if (temp.getStatus().equalsIgnoreCase("INACTIVE") && temp.getFlag() == 0) {

						responseBean.setSubscriptionAmount(temp.getSubscriptionAmount());
						responseBean.setSubscriptionName(temp.getSubscriptionName());
						responseBean.setSubscriptionId(temp.getSubscriptionId());
						responseBean.setSubscriptionValidity(temp.getSubscriptionValidity());
						responseBean.setLcCount(temp.getlCount());
						responseBean.setRemark(temp.getRemark());
						responseBean.setUserId(temp.getUserid().getUserid());
						responseBean.setStatus(temp.getStatus());
						
						responseBean.setSubsidiaries(temp.getSubsidiaries());
						responseBean.setRelationshipManager(temp.getRelationshipManager());
						responseBean.setCustomerSupport(temp.getCustomerSupport());
						responseBean.setIsVasApplied(temp.getIsVasApplied());
						responseBean.setVasAmount(temp.getVasAmount());
						responseBean.setDiscountId(temp.getDiscountId());
						responseBean.setDiscount(temp.getDiscount());
						responseBean.setGrandAmount(temp.getGrandAmount());
						responseBean.setSubsStartDate(temp.getInsertedDate());
						responseBean.setInvoiceId(temp.getInvoiceId());
						subscriptionBean.add(responseBean);
						response.setData(subscriptionBean);

						return new ResponseEntity<Object>(response, HttpStatus.OK);
					} else {
						response.setStatus("Failure");
						response.setErrCode("ASA008");
						response.setErrMessage(ErrorDescription.getDescription("ASA008"));
						return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
					}
				}
			}
		} catch (Exception e) {
			response.setStatus("Failure");
			response.setErrCode("EXE000");
			response.setErrMessage(ErrorDescription.getDescription("EXE000") + e.getMessage());
			return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
	}
	
	@Override
	public ResponseEntity<?> findMSPlanDetails(String userId) {
		GenericResponse response = new GenericResponse<>();
		logger.info("======findMSplanDetails method invoked===========");
		try {
			Optional<NimaiMCustomer> user = userRepository.findById(userId);
			if (user.isPresent()) {
				List<NimaiSubscriptionDetails> sPlanEntity = subscriptionDetailsRepository.findAllByUserId(userId);
				if (!sPlanEntity.isEmpty()) {
					for (NimaiSubscriptionDetails temp : sPlanEntity) {
						if (temp.getStatus().equalsIgnoreCase("Active")) {
							SubscriptionPlanResponse responseBean = new SubscriptionPlanResponse();
							responseBean.setSubscriptionAmount(temp.getSubscriptionAmount());
							responseBean.setSubscriptionName(temp.getSubscriptionName());
							responseBean.setSubscriptionId(temp.getSubscriptionId());
							responseBean.setSubscriptionValidity(temp.getSubscriptionValidity());
							responseBean.setLcCount(temp.getlCount());
							responseBean.setRemark(temp.getRemark());
							responseBean.setUserId(temp.getUserid().getUserid());
							responseBean.setStatus(temp.getStatus());
							responseBean.setSubsidiaries(temp.getSubsidiaries());
							responseBean.setRelationshipManager(temp.getRelationshipManager());
							responseBean.setCustomerSupport(temp.getCustomerSupport());
							responseBean.setIsVasApplied(temp.getIsVasApplied());
							responseBean.setVasAmount(temp.getVasAmount());
							responseBean.setDiscountId(temp.getDiscountId());
							responseBean.setDiscount(temp.getDiscount());
							responseBean.setGrandAmount(temp.getGrandAmount());
							response.setData(responseBean);
						} else {
							response.setErrMessage("SubscriptionPlan is not Activated on this userId");
						}
					}
				} else {
					List<SubscriptionPlanResponse> subscriptionBean = new ArrayList<SubscriptionPlanResponse>();
					List<NimaiMSubscription> subscriptionEntity = masterSPlanRepo.findAll();
					for (NimaiMSubscription mSPLan : subscriptionEntity) {
						SubscriptionPlanResponse responseBean = new SubscriptionPlanResponse();
						responseBean.setSubscriptionAmount(mSPLan.getSubscriptionAmount());
						responseBean.setSubscriptionName(mSPLan.getSubscriptionName());
						responseBean.setSubscriptionId(mSPLan.getSubscriptionId());
						responseBean.setSubscriptionValidity(mSPLan.getSubscriptionValidity());
						responseBean.setLcCount(mSPLan.getlCount());
						responseBean.setRemark(mSPLan.getRemark());
						responseBean.setStatus(mSPLan.getStatus());
						responseBean.setSubsidiaries(mSPLan.getSubsidiaries());
						responseBean.setRelationshipManager(mSPLan.getRelationshipManager());
						responseBean.setCustomerSupport(mSPLan.getCustomerSupport());

						subscriptionBean.add(responseBean);
						response.setData(subscriptionBean);

					}

				}

			} else {
				response.setErrMessage("Invalid UserId");
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus("Failure");
			response.setErrCode("EXE000");
			response.setErrMessage(ErrorDescription.getDescription("EXE000") + e.getMessage());
		}
		return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
	}

//	@Override
//	public ResponseEntity<?> findCustomerSPlanDetails(SplanRequest sPlanRequest) {
//		GenericResponse response = new GenericResponse<>();
//		logger.info("======findCustomerSPlanDetails method invoked===========");
//		try {
//			Optional<NimaiMCustomer> user = userRepository.findById(sPlanRequest.getUserId());
//			SPlanResponseBean sPlanResponseBean = new SPlanResponseBean();
//
//			if (user.isPresent()) {
//				List<NimaiSubscriptionDetails> sPlanEntity = subscriptionDetailsRepository.findAllByUserId(sPlanRequest.getUserId());
//
//				if (!sPlanEntity.isEmpty()) {
//					for (NimaiSubscriptionDetails temp : sPlanEntity) {
//						if (temp.getStatus().equalsIgnoreCase("Active")
//								|| temp.getStatus().equalsIgnoreCase("Pending")) {
//
//							SubscriptionPlanResponse detailResponse = ModelMapper.mapEntityToEntityResponse(temp);
//							response.setData(detailResponse);
//							return new ResponseEntity<>(response, HttpStatus.OK);
//						} else {
//							sPlanResponseBean = sPlanMasterlist(user.get());
//							if (sPlanResponseBean != null) {
//								response.setData(sPlanResponseBean);
//								return new ResponseEntity<>(response, HttpStatus.OK);
//							} else {
//								response.setErrCode("ASA012");
//								response.setErrMessage(ErrorDescription.getDescription("ASA012"));
//								return new ResponseEntity<>(response, HttpStatus.OK);
//							}
//						}
//
//					}
//				} else {
//					sPlanResponseBean = sPlanMasterlist(user.get());
//					if (sPlanResponseBean != null) {
//						response.setData(sPlanResponseBean);
//						return new ResponseEntity<>(response, HttpStatus.OK);
//					} else {
//						response.setErrCode("ASA012");
//						response.setErrMessage(ErrorDescription.getDescription("ASA012"));
//						return new ResponseEntity<>(response, HttpStatus.OK);
//					}
//
//				}
//			} else {
//				response.setErrCode("ASA003");
//				response.setErrMessage(ErrorDescription.getDescription("ASA003"));
//				return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
//			}
//
//		}
//
//		catch (Exception e) {
//			response.setErrMessage("No entity Found");
//			return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
//		}
//		response.setErrMessage("No response");
//		return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
//	}

	public SPlanResponseBean sPlanMasterlist(NimaiMCustomer user) {
		logger.info("======SPlanResponseBean method invoked===========");
		SPlanResponseBean sPlanResponseBean = new SPlanResponseBean();

		String customerType = user.getUserid().substring(0, 2);
		logger.info("CountryName:" + user.getCountryName());
		if (customerType.equalsIgnoreCase("Cu")) {
			String custType = "Customer";
			List<NimaiMSubscription> custSPlanList = masterSPlanRepo.findByCountry(custType, user.getCountryName());
			System.out.println(custSPlanList.toString());
			if (!custSPlanList.isEmpty()) {
				List<customerSPlansResponse> custSubscriptionBean = ModelMapper
						.mapCustSplanListToSBeanRsponse(custSPlanList);
				sPlanResponseBean.setCustomerSplans(custSubscriptionBean);
				return sPlanResponseBean;

			} else {
				return null;
			}

		} else {
			String custType = "Bank";
			List<NimaiMSubscription> banksSPlanList = masterSPlanRepo.findByCountry(custType, user.getCountryName());

			if (!banksSPlanList.isEmpty()) {
				List<banksSplansReponse> banksubscriptionBean = ModelMapper
						.mapBankSplanListToSBeanRsponse(banksSPlanList);
				sPlanResponseBean.setBanksSplans(banksubscriptionBean);
				return sPlanResponseBean;

			} else {
				return null;
			}
		}

	}

	@Override
	public ResponseEntity<?> findCustomerSPlanDetails(SplanRequest sPlanRequest) {
		GenericResponse response = new GenericResponse<>();
		logger.info("======findCustomerSPlanDetails method invoked===========");
		try {
			Optional<NimaiMCustomer> user = userRepository.findById(sPlanRequest.getUserId());
			SPlanResponseBean sPlanResponseBean = new SPlanResponseBean();

			if (user.isPresent()) {
				String customerType = user.get().getUserid().substring(0, 2);
				logger.info("CountryName:" + user.get().getRegistredCountry());
				if (customerType.equalsIgnoreCase("CU")) {
					List<NimaiMSubscription> custSPlanList = masterSPlanRepo.findByCountry("Customer",
							user.get().getRegistredCountry());
					System.out.println(custSPlanList.toString());
					if (!custSPlanList.isEmpty()) {
						List<customerSPlansResponse> custSubscriptionBean = ModelMapper
								.mapCustSplanListToSBeanRsponse(custSPlanList);
						sPlanResponseBean.setCustomerSplans(custSubscriptionBean);
					} else {
						sPlanResponseBean = null;
					}
				} else if (customerType.equalsIgnoreCase("BC")) {
					List<NimaiMSubscription> custSPlanList = masterSPlanRepo.findByCountry("Bank As Customer",
							user.get().getRegistredCountry());
					System.out.println(custSPlanList.toString());
					if (!custSPlanList.isEmpty()) {
						List<customerSPlansResponse> custSubscriptionBean = ModelMapper
								.mapCustSplanListToSBeanRsponse(custSPlanList);
						sPlanResponseBean.setCustomerSplans(custSubscriptionBean);
					} else {
						sPlanResponseBean = null;
					}
				} else {
					List<NimaiMSubscription> banksSPlanList = masterSPlanRepo.findByCountry("Bank",
							user.get().getRegistredCountry());
					if (!banksSPlanList.isEmpty()) {
						List<banksSplansReponse> banksubscriptionBean = ModelMapper
								.mapBankSplanListToSBeanRsponse(banksSPlanList);
						sPlanResponseBean.setBanksSplans(banksubscriptionBean);
					} else {
						sPlanResponseBean = null;
					}
				}

				if (sPlanResponseBean != null) {
					response.setData(sPlanResponseBean);
					return new ResponseEntity<>(response, HttpStatus.OK);
				} else {
					response.setErrCode("ASA012");
					response.setErrMessage(ErrorDescription.getDescription("ASA012"));
					return new ResponseEntity<>(response, HttpStatus.OK);
				}

			} else {
				response.setErrCode("ASA003");
				response.setErrMessage(ErrorDescription.getDescription("ASA003"));
				return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {
			response.setErrMessage("No entity Found");
			return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
		}
	}

	public static String generatePaymentTtransactionID(int count) {
		StringBuilder sb = new StringBuilder();
		while (count-- != 0) {
			int character = (int) (Math.random() * randomString.length());
			sb.append(randomString.charAt(character));
		}
		return sb.toString();
	}

	 @Override
		public Map<String, Object> initiatePayment(SubscriptionPaymentBean sPymentRequest, Double grndAmt, String subsCurency) throws PayPalRESTException {
			// TODO Auto-generated method stub
		 //APIContext context = new APIContext(clientId, clientSecret, mode);

			NimaiCustomerSubscriptionGrandAmount nc=getCustomerAmount(sPymentRequest.getUserId());
			String userId = sPymentRequest.getUserId();
			String merchantId = sPymentRequest.getMerchantId();
			String orderId = sPymentRequest.getOrderId();
			Double amount = grndAmt;
			/*if(grndAmt==0.0)
				amount = nc.getGrandAmount();
			else
				amount=sPymentRequest.getAmount();
				*/	//sPymentRequest.getAmount();
			String currency = sPymentRequest.getCurrency();
			String redirectURL = sPymentRequest.getRedirectURL();
			String cancelURL = sPymentRequest.getCancelURL();
			/*
			 * String language=sPymentRequest.getLanguage(); String
			 * billingName=sPymentRequest.getBillingName(); String
			 * billingAddress=sPymentRequest.getBillingAddress(); String
			 * billingCity=sPymentRequest.getBillingCity(); String
			 * billingState=sPymentRequest.getBillingState(); String
			 * billingZip=sPymentRequest.getBillingZip(); String
			 * billingCountry=sPymentRequest.getBillingCountry(); String
			 * billingTel=sPymentRequest.getBillingTel(); String
			 * billingEmail=sPymentRequest.getBillingEmail(); String
			 * shippingName=sPymentRequest.getShippingName(); String
			 * shippingAddress=sPymentRequest.getShippingAddress(); String
			 * shippingCity=sPymentRequest.getShippingCity(); String
			 * shippingState=sPymentRequest.getShippingState(); String
			 * shippingZip=sPymentRequest.getShippingZip(); String
			 * shippingCountry=sPymentRequest.getShippingCountry(); String
			 * shippingTel=sPymentRequest.getShippingTel();
			 */
			String merchantParam1 = sPymentRequest.getMerchantParam1();
			String merchantParam2 = sPymentRequest.getMerchantParam2();//subid
			String merchantParam3 = sPymentRequest.getMerchantParam3();
			String merchantParam4 = sPymentRequest.getMerchantParam4();//vasid-discamt
			String merchantParam5 = sPymentRequest.getMerchantParam5();
			String merchantParam6 = sPymentRequest.getMerchantParam6();//discountId
			
			Optional<NimaiMCustomer> mCustomer = userRepository.findByUserId(merchantParam1);
			
			String productDescription="merchantParam1="+merchantParam1
									 +",merchantParam2="+merchantParam2
									 +",merchantParam3="+merchantParam3
									 +",merchantParam4="+merchantParam4
									 +",merchantParam5="+merchantParam5
									 +",merchantParam6="+merchantParam6;
			
					System.out.println("Product description: "+productDescription);
			Map<String, Object> response = new HashMap<String, Object>();
			
			int vasCount = StringUtils.countOccurrencesOf(merchantParam4, "-");
			System.out.println("Total VAS: "+vasCount);
			
			//int vasWithDisc=vasCount+1;
			String vasSplitted[] =merchantParam4.split("-",vasCount+1);
			Double vasAmount = null;
			Double vasFinalAmount=0.0;
			int i;
			try
			{
				for(i=0;i<vasCount;i++)
				{
					System.out.println("Iteration: "+i);
					System.out.println("VAS: "+vasSplitted[i]);
					vasAmount=advRepo.findPricingByVASId(Integer.valueOf(vasSplitted[i]));
					if(vasAmount==null)
					{
						vasFinalAmount=0.0;
					}
					else
					{
						vasFinalAmount=vasFinalAmount+vasAmount;
					}
				}
			}
			catch(Exception e)
			{
				vasAmount=0.0;
			}
			final Double vasAmt=vasFinalAmount;
			com.paypal.orders.Order order = null;
			// Construct a request object and set desired parameters
			// Here, OrdersCreateRequest() creates a POST request to /v2/checkout/orders
			OrderRequest orderRequest = new OrderRequest();
			orderRequest.checkoutPaymentIntent("CAPTURE");
			
			ApplicationContext ac=new ApplicationContext();
			if(merchantParam1.substring(0, 2).equalsIgnoreCase("BA"))
	        {
				ac.cancelUrl(redirectFromPaymentLink+"#/bcst/dsb/subscription");
				ac.returnUrl(redirectFromPaymentLink+"#/bcst/dsb/subscription");
	        }
	        else
	        {
	        	ac.cancelUrl(redirectFromPaymentLink+"#/cst/dsb/subscription");
				ac.returnUrl(redirectFromPaymentLink+"#/cst/dsb/subscription");
	        }
			
			ac.shippingPreference("NO_SHIPPING");
			ac.userAction("PAY_NOW");
			PaymentMethod pm=new PaymentMethod();
			pm.payerSelected("PAYPAL");
			pm.payeePreferred("IMMEDIATE_PAYMENT_REQUIRED");
			ac.paymentMethod(pm);
			
			orderRequest.applicationContext(ac);
			
			com.paypal.orders.Payer payer=new com.paypal.orders.Payer();
			Name name=new Name();
			name.givenName(mCustomer.get().getFirstName());
			name.surname(mCustomer.get().getLastName());
			payer.name(name);
			payer.email(mCustomer.get().getEmailAddress());
			/*PhoneWithType phoneWithType=new PhoneWithType();
			Phone phoneNumber=new Phone();
			phoneNumber.nationalNumber(mCustomer.get().getMobileNumber());
			phoneWithType.phoneNumber(phoneNumber);
			payer.phoneWithType(phoneWithType);
			AddressPortable addressPortable=new AddressPortable();
			addressPortable.addressLine1(mCustomer.get().getAddress1());
			addressPortable.addressLine2(mCustomer.get().getProvincename());
			addressPortable.addressLine3(mCustomer.get().getCountryName());
			addressPortable.postalCode(mCustomer.get().getPincode());
			payer.addressPortable(addressPortable);
			*/
			orderRequest.payer(payer);
			
			//List<PurchaseUnitRequest> purchaseUnits = new ArrayList<>();
			System.out.println("Subscription Amount:"+merchantParam5);
			System.out.println("VAS Amount:"+String.format("%.2f", vasAmt));
			Double itemGst,amountWithGST,disc;
			Double gst=subscriptionRepo.getGSTValue()/100;
			System.out.println("GST Value from DB: "+gst);
			//System.out.println("");
			if(merchantParam3.equalsIgnoreCase("renew-vas"))
			{
				itemGst=vasAmt*gst;
				amountWithGST=vasAmt+itemGst;
			}
	        else
	        {
	        	itemGst=amount*gst;
	        	amountWithGST=amount+itemGst;
	        }
			if(!vasSplitted[vasCount].equalsIgnoreCase("0"))
				disc=Double.valueOf(vasSplitted[vasCount]);
			else
				disc=0.0;
			Double subVas=Double.valueOf(merchantParam5)+vasAmt;
			System.out.println("Subsc + VAS: "+subVas);
			System.out.println("Discount: "+disc);
			System.out.println("Amount without GST: "+amount);
			System.out.println("GST: "+itemGst);
			System.out.println("Amount with GST: "+String.format("%.2f", amountWithGST));
			System.out.println("Amount: "+(amount));
			String invoiceId=generatePaymentTtransactionID(10);
			List<PurchaseUnitRequest> purchaseUnitRequests = new ArrayList<PurchaseUnitRequest>();
			PurchaseUnitRequest purchaseUnitRequest = null;
			if(merchantParam3.equalsIgnoreCase("renew") || merchantParam3.equalsIgnoreCase("new"))
			{
			purchaseUnitRequest = new PurchaseUnitRequest().referenceId(merchantParam1+"-"+merchantParam2+"-"+merchantParam3+"-"+merchantParam6)
				.invoiceId(invoiceId)
				
				//.customId("MDNM-DND-234323-1222")
			    //.description("Sporting Goods").customId("CUST-HighFashions").softDescriptor("HighFashions")
			    .amountWithBreakdown(new AmountWithBreakdown().currencyCode(subsCurency).value(""+String.format("%.2f", amountWithGST))
			        .amountBreakdown(new AmountBreakdown().itemTotal(new Money().currencyCode(subsCurency).value(""+subVas))
			            //.shipping(new Money().currencyCode("USD").value("30.00"))
			            //.handling(new Money().currencyCode("USD").value("10.00"))
			        	.discount(new Money().currencyCode(subsCurency).value(""+String.format("%.2f",disc)))
			            .taxTotal(new Money().currencyCode(subsCurency).value(""+String.format("%.2f",itemGst)))
			            //.shippingDiscount(new Money().currencyCode("USD").value("10.00"))
			        		)
			    )
			    .items(new ArrayList<com.paypal.orders.Item>() {
			      {
			    	  if(merchantParam3.equalsIgnoreCase("renew") || merchantParam3.equalsIgnoreCase("new"))
			    	  {
			        add(new com.paypal.orders.Item().name("Subscription")
			            .unitAmount(new Money().currencyCode(subsCurency).value(""+String.format("%.2f",Double.valueOf(merchantParam5))))
			            .quantity("1")
			            //.tax(new Money().currencyCode("USD").value("10.00")).quantity("1")
			           .category("DIGITAL_GOODS")
			            );
			    	  }
			    	  
			    	  if(vasAmt>0.0)
			    	  {
			        add(new com.paypal.orders.Item().name("VAS")
			            .unitAmount(new Money().currencyCode(subsCurency).value(""+String.format("%.2f", vasAmt)))
			            .quantity("1")
			            //.tax(new Money().currencyCode("USD").value("5.00")).quantity("2")
			            .category("DIGITAL_GOODS")
			            );
			    	  }
			    	  
			      }
			    })
			    ;
			}
			else
			{
				purchaseUnitRequest = new PurchaseUnitRequest().referenceId(merchantParam1+"-"+merchantParam2+"-"+merchantParam3+"-"+merchantParam6)
						.invoiceId(invoiceId)	
						//.customId("MDNM-DND-234323-1222")
				    //.description("Sporting Goods").customId("CUST-HighFashions").softDescriptor("HighFashions")
				    .amountWithBreakdown(new AmountWithBreakdown().currencyCode(subsCurency).value(""+String.format("%.2f", amountWithGST))
				        .amountBreakdown(new AmountBreakdown().itemTotal(new Money().currencyCode(subsCurency).value(""+String.format("%.2f", vasAmt)))
				            //.shipping(new Money().currencyCode("USD").value("30.00"))
				            //.handling(new Money().currencyCode("USD").value("10.00"))
				        	.discount(new Money().currencyCode(subsCurency).value(""+String.format("%.2f",disc)))
				            .taxTotal(new Money().currencyCode(subsCurency).value(""+String.format("%.2f",itemGst)))
				            //.shippingDiscount(new Money().currencyCode("USD").value("10.00"))
				        		)
				    )
				    .items(new ArrayList<com.paypal.orders.Item>() {
				      {
				    	  if(merchantParam3.equalsIgnoreCase("renew") || merchantParam3.equalsIgnoreCase("new"))
				    	  {
				        add(new com.paypal.orders.Item().name("Subscription")
				            .unitAmount(new Money().currencyCode(subsCurency).value(merchantParam5))
				            .quantity("1")
				            //.tax(new Money().currencyCode("USD").value("10.00")).quantity("1")
				            .category("DIGITAL_GOODS")
				            );
				    	  }
				    	  
				    	  if(vasAmt>0.0)
				    	  {
				        add(new com.paypal.orders.Item().name("VAS")
				            .unitAmount(new Money().currencyCode(subsCurency).value(""+String.format("%.2f", vasAmt)))
				            .quantity("1")
				            //.tax(new Money().currencyCode("USD").value("5.00")).quantity("2")
				            .category("DIGITAL_GOODS")
				            );
				    	  }
				    	  
				      }
				    })
				    ;
				
			}
			purchaseUnitRequests.add(purchaseUnitRequest);
			
			
			//purchaseUnits.add(new PurchaseUnitRequest());
			//purchaseUnits.add(new PurchaseUnitRequest().items(itemList));
			orderRequest.purchaseUnits(purchaseUnitRequests);
			OrdersCreateRequest request = new OrdersCreateRequest().requestBody(orderRequest);
			
			try 
			{
				// Call API with your client and get a response for your call
				HttpResponse<com.paypal.orders.Order> OrderResponse = Credentials.client.execute(request);

				// If call returns body in response, you can get the de-serialized version by
				// calling result() on the response
				order = OrderResponse.result();
				System.out.println("Order ID: " + order.id());
				String redirectLink="";
				for(LinkDescription o:order.links())
				{
					System.out.println("---"+o.href());
					if(o.rel().equalsIgnoreCase("approve"))
					{
						redirectLink=o.href();
						break;
					}
					
				}
				response.put("status", "success");
                response.put("redirect_url", redirectLink);
			} 
			catch (IOException ioe) 
			{
				if (ioe instanceof HttpException) 
				{
					// Something went wrong server-side
					HttpException he = (HttpException) ioe;
					System.out.println(he.getMessage());
					he.headers().forEach(x -> System.out.println(x + " :" + he.headers().header(x)));
				} 
				else 
				{
					// Something went wrong client-side
				}
			}
			return response;
		}
	
	 @Override
	 public Map<String, Object> executePayment(String orderId) throws PayPalRESTException{
		 Map<String, Object> responseData = new HashMap<String, Object>();
			
		 //Map<String, Object> response = new HashMap<String, Object>();
		 com.paypal.orders.Order order = null;
		 OrdersGetRequest request = new OrdersGetRequest(orderId);	
		 //OrdersCaptureRequest request = new OrdersCaptureRequest(orderId);

			try {
				// Call API with your client and get a response for your call
				HttpResponse<com.paypal.orders.Order> responsePaypal = Credentials.client.execute(request);

				// If call returns body in response, you can get the de-serialized version by
				// calling result() on the response
				order = responsePaypal.result();
				//System.out.println("Capture ID: " + order.purchaseUnits().get(0).payments().captures().get(0).id());
				//order.purchaseUnits().get(0).payments().captures().get(0).links()
				//		.forEach(link -> System.out.println(link.rel() + " => " + link.method() + ":" + link.href()));
				//System.out.println("Order Status: "+order.status());
				System.out.println("Details: "+order.purchaseUnits().get(0).referenceId());
				
				String planSplit[]=order.purchaseUnits().get(0).referenceId().split("-", 5);
				System.out.println("Length: "+planSplit.length);
				System.out.println("planSplit[0] :"+planSplit[0]);
				System.out.println("planSplit[1] :"+planSplit[1]);
				System.out.println("planSplit[2] :"+planSplit[2]);
				String discId = "";
					
				if(planSplit.length==4)
				{
					discId=planSplit[3];
					System.out.println("planSplit[3] :"+planSplit[3]);
				}
				if(planSplit.length==5)
				{
					discId=planSplit[4];
					System.out.println("planSplit[4] :"+planSplit[4]);
				}
				
				String vasPrice="",discountPrice="";
				/*try
				{
					vasPrice=order.purchaseUnits().get(0).items().get(1).unitAmount().value();
				}
				catch(NullPointerException ne)
				{
					vasPrice="0";
				}
				try
				{
					discountPrice=order.purchaseUnits().get(0).amountWithBreakdown().amountBreakdown().discount().value();
				}
				catch(NullPointerException ne)
				{
					discountPrice="";
				}*/
				
				System.out.println("Purchase Unit Size: "+order.purchaseUnits().get(0).items().size());
				if(order.purchaseUnits().get(0).items().size()==2)
					vasPrice=order.purchaseUnits().get(0).items().get(1).unitAmount().value();
				else
						vasPrice="0";
				
				System.out.println("VAS Price: "+vasPrice);
				
				try
			    {
					if(order.purchaseUnits().get(0).amountWithBreakdown().amountBreakdown().discount().value().equalsIgnoreCase("0") 
							|| order.purchaseUnits().get(0).amountWithBreakdown().amountBreakdown().discount().value()==null)	
						discountPrice="0";
					else
						discountPrice=order.purchaseUnits().get(0).amountWithBreakdown().amountBreakdown().discount().value();
				}
				catch(NullPointerException e)
				{
					discountPrice="0";
				}
								
				String merchantP1=planSplit[0];
				String merchantP2=planSplit[1];
				String merchantP3=planSplit[2];
				//String merchantP4=payment.getTransactions().get(0).getItemList().getItems().get(1).getPrice()+"-"+
				//		payment.getTransactions().get(0).getItemList().getItems().get(2).getPrice().substring(1);
				String merchantP4=vasPrice+"-"+discountPrice;
				String merchantP5=order.purchaseUnits().get(0).items().get(0).unitAmount().value();
				System.out.println("Merchant Param= "+merchantP1+"--"+merchantP2
						+"--"+merchantP3+"--"+merchantP4+"--"+merchantP5);
				
				System.out.println("Amount with GST --> execute= "+order.purchaseUnits().get(0).amountWithBreakdown().value());
				NimaiMSubscription subsDetail=getPlanDetailsBySubscriptionId(merchantP2);
				
				responseData.put("subscriptionId", merchantP2);
				responseData.put("orderId", orderId);
				responseData.put("custSupport", subsDetail.getCustomerSupport());
				responseData.put("lcCount", subsDetail.getlCount());
				responseData.put("relManager", subsDetail.getRelationshipManager());
				responseData.put("subsAmount",""+order.purchaseUnits().get(0).amountWithBreakdown().value());//subsDetail.getSubscriptionAmount());
				responseData.put("subsName", subsDetail.getSubscriptionName());
				responseData.put("subsValidity", String.valueOf(subsDetail.getSubscriptionValidity()));
				responseData.put("subsidiaries", subsDetail.getSubsidiaries());
				responseData.put("userId", merchantP1);
				responseData.put("subsflag", merchantP3);
				responseData.put("actualAmt", merchantP5);
				responseData.put("paymentMode", "Credit");
				responseData.put("userId",merchantP1);
				responseData.put("discAmount",discountPrice);
				responseData.put("discId",discId);
				responseData.put("vasAmount",vasPrice);
				responseData.put("OrderStatus",order.status());		
				responseData.put("invoiceId", order.purchaseUnits().get(0).invoiceId());
			} catch (IOException ioe) {
				if (ioe instanceof HttpException) {
					// Something went wrong server-side
					System.out.println("Issue On Server Side");
					HttpException he = (HttpException) ioe;
					System.out.println(he.getMessage());
					he.headers().forEach(x -> System.out.println(x + " :" + he.headers().header(x)));
				} else {
					// Something went wrong client-side
				}
			}
			return responseData;
		}
	/* CCAvenue
	 @Override
	public SubscriptionPaymentBean initiatePayment(SubscriptionPaymentBean sPymentRequest) {
		// TODO Auto-generated method stub
		NimaiCustomerSubscriptionGrandAmount nc=getCustomerAmount(sPymentRequest.getUserId());
		String userId = sPymentRequest.getUserId();
		String merchantId = sPymentRequest.getMerchantId();
		String orderId = sPymentRequest.getOrderId();
		Double amount = nc.getGrandAmount();
				//sPymentRequest.getAmount();
		String currency = sPymentRequest.getCurrency();
		String redirectURL = sPymentRequest.getRedirectURL();
		String cancelURL = sPymentRequest.getCancelURL();
		
		String merchantParam1 = sPymentRequest.getMerchantParam1();
		String merchantParam2 = sPymentRequest.getMerchantParam2();//subid
		String merchantParam3 = sPymentRequest.getMerchantParam3();
		String merchantParam4 = sPymentRequest.getMerchantParam4();//vasid
		String merchantParam5 = sPymentRequest.getMerchantParam5();
		//String language = sPymentRequest.getLanguage();
		
		System.out.println("Working Key: " + paymentWorkingKey);
		System.out.println("Access Code: " + paymentAccessCode);
		System.out.println("Merchant Id: " + paymentMerchantId);

		String requestValues[] = { merchantId, orderId, currency, amount.toString(), redirectURL, cancelURL, 
				merchantParam1, merchantParam2, merchantParam3, merchantParam4,  merchantParam5};
		System.out.println("Values=" + requestValues[0]);
		// Enumeration enumeration= {""};
		String ccaRequest = mapParameterNameAndValues(ccavenueParameterNames, requestValues);

		AesCryptUtil aesUtil = new AesCryptUtil(paymentWorkingKey);
		String encRequest = aesUtil.encrypt(ccaRequest);

		SubscriptionPaymentBean spb = new SubscriptionPaymentBean();
		spb.setMerchantId(paymentMerchantId);
		spb.setAccessCode(paymentAccessCode);
		spb.setRequestDump(encRequest);

		return spb;
	}
	*/
	private String mapParameterNameAndValues(String[] ccavenueParameterNames2, String[] requestValues) {
		// TODO Auto-generated method stub
		String ccaRequest = "", pname = "", pvalue = "";
		for (int i = 0; i < ccavenueParameterNames2.length; i++) {
			if (ccavenueParameterNames2[i].equalsIgnoreCase("merchant_id")) {
				pname = "" + ccavenueParameterNames2[0];
				pvalue = requestValues[0];
				ccaRequest = ccaRequest + pname + "=" + pvalue + "&";
			}
			if (ccavenueParameterNames2[i].equalsIgnoreCase("order_id")) {
				pname = "" + ccavenueParameterNames2[1];
				pvalue = requestValues[1];
				ccaRequest = ccaRequest + pname + "=" + pvalue + "&";
			}
			if (ccavenueParameterNames2[i].equalsIgnoreCase("currency")) {
				pname = "" + ccavenueParameterNames2[2];
				pvalue = requestValues[2];
				ccaRequest = ccaRequest + pname + "=" + pvalue + "&";
			}
			if (ccavenueParameterNames2[i].equalsIgnoreCase("amount")) {
				pname = "" + ccavenueParameterNames2[3];
				pvalue = requestValues[3];
				ccaRequest = ccaRequest + pname + "=" + pvalue + "&";
			}
			if (ccavenueParameterNames2[i].equalsIgnoreCase("redirect_url")) {
				pname = "" + ccavenueParameterNames2[4];
				pvalue = requestValues[4];
				ccaRequest = ccaRequest + pname + "=" + pvalue + "&";
			}
			if (ccavenueParameterNames2[i].equalsIgnoreCase("cancel_url")) {
				pname = "" + ccavenueParameterNames2[5];
				pvalue = requestValues[5];
				ccaRequest = ccaRequest + pname + "=" + pvalue + "&";
			}
			
			if (ccavenueParameterNames2[i].equalsIgnoreCase("merchant_param1")) {
				pname = "" + ccavenueParameterNames2[22];
				pvalue = requestValues[6];
				ccaRequest = ccaRequest + pname + "=" + pvalue + "&";
			}
			if (ccavenueParameterNames2[i].equalsIgnoreCase("merchant_param2")) {
				pname = "" + ccavenueParameterNames2[23];
				pvalue = requestValues[7];
				ccaRequest = ccaRequest + pname + "=" + pvalue + "&";
			}
			if (ccavenueParameterNames2[i].equalsIgnoreCase("merchant_param3")) {
				pname = "" + ccavenueParameterNames2[24];
				pvalue = requestValues[8];
				ccaRequest = ccaRequest + pname + "=" + pvalue + "&";
			}
			if (ccavenueParameterNames2[i].equalsIgnoreCase("merchant_param4")) {
				pname = "" + ccavenueParameterNames2[25];
				pvalue = requestValues[9];
				ccaRequest = ccaRequest + pname + "=" + pvalue + "&";
			}
			if (ccavenueParameterNames2[i].equalsIgnoreCase("merchant_param5")) {
				pname = "" + ccavenueParameterNames2[26];
				pvalue = requestValues[10];
				ccaRequest = ccaRequest + pname + "=" + pvalue + "&";
			}
			

			// pname = ""+enumeration.nextElement();
			// pvalue = request.getParameter(pname);
			// ccaRequest = ccaRequest + pname + "=" + pvalue + "&";
			// System.out.println(""+ccavenueParameterNames[i]);
		}
		System.out.println("ccaRequest==" + ccaRequest);
		return ccaRequest;
	}

/*	@Override
	public HashMap<String, String> getPaymentResponse(String encResp) {
		// TODO Auto-generated method stub
		AesCryptUtil aesUtil = new AesCryptUtil(paymentWorkingKey);
		String decResp = aesUtil.decrypt(encResp);
		StringTokenizer tokenizer = new StringTokenizer(decResp, "&");
		Hashtable hs = new Hashtable();
		String pair = null, pname = null, pvalue = null;
		while (tokenizer.hasMoreTokens()) {
			pair = (String) tokenizer.nextToken();
			if (pair != null) {
				StringTokenizer strTok = new StringTokenizer(pair, "=");
				pname = "";
				pvalue = "";
				if (strTok.hasMoreTokens()) {
					pname = (String) strTok.nextToken();
					if (strTok.hasMoreTokens())
						pvalue = (String) strTok.nextToken();
					hs.put(pname, pvalue);
				}
			}
		}
		System.out.println("Decrypted Data: " + hs);
		Enumeration enumeration = hs.keys();
		HashMap<String, String> finalData = new HashMap<>();
		while (enumeration.hasMoreElements()) {
			pname = "" + enumeration.nextElement();
			pvalue = "" + hs.get(pname);
			finalData.put(pname, pvalue);
		}
		System.out.println("Enumerated Data: " + finalData);

		OnlinePayment op = new OnlinePayment();
		op.setUserId(finalData.get("merchant_param1"));
		op.setOrderId(finalData.get("order_id"));
		op.setAmount(Double.valueOf(finalData.get("amount")));
		op.setCurrency(finalData.get("currency"));
		op.setBankReceiptNo(finalData.get("bank_receipt_no"));
		op.setTrackingId(finalData.get("tracking_id"));
		op.setStatusMessage(finalData.get("status_message"));
		op.setStatus(finalData.get("order_status"));
		op.setFailureMessage(finalData.get("failure_message"));
		op.setRequestDump(finalData.get("order_id"));
		op.setResponseDump(finalData.toString());
		op.setInsertedBy(finalData.get("merchant_param1"));
		Date now = new Date();
		op.setInsertedDate(now);
		op.setModifiedBy(finalData.get("merchant_param1"));

		onlinePaymentRepo.save(op);

		if (op.getStatus().equalsIgnoreCase("Success")) {
			userRepository.updatePaymentStatus("Success", op.getOrderId(), op.getUserId());
			userRepository.updatePaymentTransactionId(op.getUserId(), op.getOrderId());
		}
		return finalData;

	}
*/
	@Override
	public OnlinePayment checkPayment(SubscriptionPaymentBean sPymentRequest) {
		// TODO Auto-generated method stub
		OnlinePayment op = onlinePaymentRepo.getDetailsByUserId(sPymentRequest.getUserId());
		System.out.println("Data of User: " + sPymentRequest.getUserId());
		System.out.println("Data: " + op);
		return op;
	}

	@Override
	public NimaiMSubscription getPlanDetailsBySubscriptionId(String string) {
		// TODO Auto-generated method stub
		return subscriptionRepo.findDetailBySubscriptionId(string);
	}

	@Override
	public ResponseEntity<?> findAllSPlanDetailsForCustomer(String userId) {
		GenericResponse response = new GenericResponse<>();
		logger.info("======findCustomerSPlanDetails method invoked===========");
		try {
			SPlanResponseBean sPlanResponseBean = new SPlanResponseBean();
			
			String countryName=masterSPlanRepo.getBusinessCountry(userId);
			logger.info("======findCustomerSPlanDetails method invoked for country:==========="+countryName);
			List<NimaiMSubscription> custSPlanList = masterSPlanRepo.findByCustomerType("Customer",countryName);
			System.out.println(custSPlanList.toString());
			if (!custSPlanList.isEmpty()) {
				List<customerSPlansResponse> custSubscriptionBean = ModelMapper
						.mapCustSplanListToSBeanRsponse(custSPlanList);
				sPlanResponseBean.setCustomerSplans(custSubscriptionBean);
			} else {
				sPlanResponseBean = null;
			}

			if (sPlanResponseBean != null) {
				response.setData(sPlanResponseBean);
				return new ResponseEntity<>(response, HttpStatus.OK);
			} else {
				response.setErrCode("ASA012");
				response.setErrMessage(ErrorDescription.getDescription("ASA012"));
				return new ResponseEntity<>(response, HttpStatus.OK);
			}

		} catch (Exception e) {
			response.setErrMessage("No entity Found");
			return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public ResponseEntity<?> checkForSubsidiary(SubscriptionBean subscriptionRequest) 
	{
		// TODO Auto-generated method stub
		GenericResponse response = new GenericResponse<>();
		String userId=subscriptionRequest.getUserId();
		Calendar cal = Calendar.getInstance();
		Date today = cal.getTime();
		int addOnCredit = 0, days = 0;
		try {
			if (subscriptionRequest.getSubscriptionId() != null) {
				Optional<NimaiMCustomer> mCustomer = userRepository.findByUserId(userId);
				NimaiSubscriptionDetails details=subscriptionDetailsRepository.findByUserId(mCustomer.get().getUserid());

				if (mCustomer.isPresent()) {
					List<NimaiSubscriptionDetails> subscriptionEntity = subscriptionDetailsRepository
							.findAllByUserId(userId);
					if (!subscriptionEntity.isEmpty()) {
						for (NimaiSubscriptionDetails plan : subscriptionEntity) {
							if (plan.getSubsidiaryUtilizedCount() > Integer
									.valueOf(subscriptionRequest.getSubsidiaries())) {
								if (userId.substring(0, 2).equalsIgnoreCase("CU") ) {

									response.setStatus("Failure");
									response.setErrMessage(
											"You had already Active Subsidiary. Kindly select appropriate Plan.");
									return new ResponseEntity<Object>(response, HttpStatus.OK);
								} /*else //if (userId.substring(0, 2).equalsIgnoreCase("BA")) 
								{
									response.setStatus("Failure");
									response.setErrMessage(
											"You had already Active Additional user. Kindly select appropriate Plan.");
									return new ResponseEntity<Object>(response, HttpStatus.OK);
								} */
								/*else {
									response.setStatus("Failure");
									response.setErrMessage(
											"You had already Active Subsidiary/Additional user. Kindly select appropriate Plan.");
									return new ResponseEntity<Object>(response, HttpStatus.OK);
								}*/

							}
							if ((plan.getSubscriptionEndDate().after(today)
									|| plan.getSubscriptionEndDate().compareTo(today) <= 0)
									&& (Integer.valueOf(plan.getlCount()) - plan.getLcUtilizedCount()) > 0) {
								if (plan.getSubscriptionEndDate().compareTo(today) <= 0)
									days = (int) ((plan.getSubscriptionEndDate().getTime() - today.getTime())
											/ (1000 * 60 * 60 * 24));
								else
									days = (int) ((plan.getSubscriptionEndDate().getTime() - today.getTime())
											/ (1000 * 60 * 60 * 24)) + 1;

								addOnCredit = (Integer.valueOf(plan.getlCount()) - plan.getLcUtilizedCount());
								System.out.println("addOnCredit:" + addOnCredit);

							}
							// userRepository.updatePaymentStatus(mCustomer.get().getUserid());
						}
					} else {
						NimaiSubscriptionDetails inactiveSubscriptionEntity = subscriptionDetailsRepository
								.findOnlyLatestInactiveSubscriptionByUserId(userId);
						if(inactiveSubscriptionEntity==null)
						{
							response.setStatus("Success");
							return new ResponseEntity<Object>(response, HttpStatus.OK);
						}
						int noOfDays = (int) ((today.getTime()
								- inactiveSubscriptionEntity.getSubscriptionEndDate().getTime())
								/ (1000 * 60 * 60 * 24));
						System.out.println("Diff between exp and current date: " + noOfDays);
						if (inactiveSubscriptionEntity.getSubsidiaryUtilizedCount() >= Integer
								.valueOf(subscriptionRequest.getSubsidiaries()) && userId.substring(0, 2).equalsIgnoreCase("CU")) {
							response.setStatus("Failure");
							response.setErrMessage(
									"You had already Active Subsidiary. Kindly select appropriate Plan.");
							return new ResponseEntity<Object>(response, HttpStatus.OK);
						}
						if (noOfDays < 60 && (Integer.valueOf(inactiveSubscriptionEntity.getlCount())
								- inactiveSubscriptionEntity.getLcUtilizedCount()) > 0) {
							addOnCredit = (Integer.valueOf(inactiveSubscriptionEntity.getlCount())
									- inactiveSubscriptionEntity.getLcUtilizedCount());
						}
					}
					} else {
					response.setStatus("Failure");
					response.setErrCode("ASA003");
					response.setErrMessage(ErrorDescription.getDescription("ASA003"));
					return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
				}
			} else {
				response.setStatus("Failure");
				response.setErrCode("ASA009");
				response.setErrMessage(ErrorDescription.getDescription("ASA009"));
				return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus("Failure");
			response.setErrCode("EXE000");
			response.setErrMessage(ErrorDescription.getDescription("EXE000") + e.getMessage());
			return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
		}
		response.setStatus("Success");
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> insertGrandAmountData(CustomerSubscriptionGrandAmountBean subscriptionRequest) {
		// TODO Auto-generated method stub
		GenericResponse response = new GenericResponse<>();
		NimaiCustomerSubscriptionGrandAmount ncsgm=new NimaiCustomerSubscriptionGrandAmount();
		ncsgm.setUserId(subscriptionRequest.getUserId());
		ncsgm.setGrandAmount(subscriptionRequest.getGrandAmount());
		ncsgm.setDiscountApplied("");
		ncsgm.setInsertedDate(new Date());
		nimaiCustomerGrandAmtRepository.save(ncsgm);
		response.setStatus("Success");
		response.setData(ncsgm.getId());
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}

	@Override
	public NimaiCustomerSubscriptionGrandAmount getCustomerAmount(String userId) {
		// TODO Auto-generated method stub
		NimaiCustomerSubscriptionGrandAmount userDet=nimaiCustomerGrandAmtRepository.getDetByUserId(userId);
		return userDet;
	}

	@Override
	public boolean checkPaymentData(int id, Double amt) {
		// TODO Auto-generated method stub
		NimaiCustomerSubscriptionGrandAmount userDet=nimaiCustomerGrandAmtRepository.getDetByIdAndAmt(id,amt);
		if(userDet!=null)
			return true;
		else
			return false;
	}

	@Override
	public Map<String, Object> completePayment(Payment payment,String paymentId) {
		// TODO Auto-generated method stub
		String planSplit[]=payment.getTransactions().get(0).getCustom().split("-", 5);
		System.out.println("Length: "+planSplit.length);
		System.out.println("planSplit[0] :"+planSplit[0]);
		System.out.println("planSplit[1] :"+planSplit[1]);
		System.out.println("planSplit[2] :"+planSplit[2]);
		String discId = "";
			
		if(planSplit.length==4)
		{
			discId=planSplit[3];
			System.out.println("planSplit[3] :"+planSplit[3]);
		}
		if(planSplit.length==5)
		{
			discId=planSplit[4];
			System.out.println("planSplit[4] :"+planSplit[4]);
		}
		
		String vasPrice="",discountPrice="";
		if(payment.getTransactions().get(0).getItemList().getItems().size()==2)
		{
			if(payment.getTransactions().get(0).getItemList().getItems().get(1).getName().equalsIgnoreCase("VAS"))
				vasPrice=payment.getTransactions().get(0).getItemList().getItems().get(1).getPrice();
			else
				vasPrice="0";
			if(payment.getTransactions().get(0).getItemList().getItems().get(1).getName().equalsIgnoreCase("Discount"))	
				discountPrice=payment.getTransactions().get(0).getItemList().getItems().get(1).getPrice().substring(1);
			else
				discountPrice="0";
		}
		if(payment.getTransactions().get(0).getItemList().getItems().size()==3)
		{
			if(payment.getTransactions().get(0).getItemList().getItems().get(1).getName().equalsIgnoreCase("VAS"))
				vasPrice=payment.getTransactions().get(0).getItemList().getItems().get(1).getPrice();
			else
				vasPrice="0";
			if(payment.getTransactions().get(0).getItemList().getItems().get(2).getName().equalsIgnoreCase("Discount"))
				discountPrice=payment.getTransactions().get(0).getItemList().getItems().get(2).getPrice().substring(1);
			else
				discountPrice="0";
		}
		
		String merchantP1=planSplit[0];
		String merchantP2=planSplit[1];
		String merchantP3=planSplit[2];
		//String merchantP4=payment.getTransactions().get(0).getItemList().getItems().get(1).getPrice()+"-"+
		//		payment.getTransactions().get(0).getItemList().getItems().get(2).getPrice().substring(1);
		String merchantP4=vasPrice+"-"+discountPrice;
		String merchantP5=payment.getTransactions().get(0).getItemList().getItems().get(0).getPrice();
		
		//System.out.println("Product 1: "+payment.getTransactions().get(0).getItemList().getItems().get(0));
		//System.out.println("Product 2: "+payment.getTransactions().get(0).getItemList().getItems().get(1));
		//System.out.println("Product 3: "+payment.getTransactions().get(0).getItemList().getItems().get(2));
		
		//String productDesc=payment.getTransactions().get(0).getDescription();
		//System.out.println("Description: "+productDesc);
				
		//String[] productDiscList = productDesc.split(",");
		
		/*String merchantParam1=productDiscList[0];
		String merchantParam2=productDiscList[1];
		String merchantParam3=productDiscList[2];
		String merchantParam4=productDiscList[3];
		String merchantParam5=productDiscList[4];
		
		String[] merchant1 = merchantParam1.split("=");
		String merchantP1=merchant1[1];
		
		String[] merchant2 = merchantParam2.split("=");
		String merchantP2=merchant2[1];
		
		String[] merchant3 = merchantParam3.split("=");
		String merchantP3=merchant3[1];
		
		String[] merchant4 = merchantParam4.split("=");
		String merchantP4=merchant4[1];
		
		String[] merchant5 = merchantParam5.split("=");
		String merchantP5=merchant5[1];
		
		System.out.println("Merchant Param= "+merchantParam1+"--"+merchantParam2
				+"--"+merchantParam3+"--"+merchantParam4+"--"+merchantParam5);
		
		System.out.println("Merchant Param Values= "+merchantP1+"--"+merchantP2
				+"--"+merchantP3+"--"+merchantP4+"--"+merchantP5);
		*/
		NimaiMSubscription subsDetail=getPlanDetailsBySubscriptionId(merchantP2);
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("subscriptionId", merchantP2);
		response.put("orderId", paymentId.substring(paymentId.lastIndexOf("-") + 1));
		response.put("custSupport", subsDetail.getCustomerSupport());
		response.put("lcCount", subsDetail.getlCount());
		response.put("relManager", subsDetail.getRelationshipManager());
		response.put("subsAmount",""+payment.getTransactions().get(0).getAmount().getTotal());//subsDetail.getSubscriptionAmount());
		response.put("subsName", subsDetail.getSubscriptionName());
		response.put("subsValidity", String.valueOf(subsDetail.getSubscriptionValidity()));
		response.put("subsidiaries", subsDetail.getSubsidiaries());
		response.put("userId", merchantP1);
		response.put("subsflag", merchantP3);
		response.put("actualAmt", merchantP5);
		response.put("paymentMode", "Credit");
		response.put("userId",merchantP1);
		response.put("discAmount",discountPrice);
		response.put("discId",discId);
		
		//response.put("merchantParam2",merchantP2);
		//response.put("subsflag",merchantP3);
		/*try
		{
			String vasSplitted[] =merchantP4.split("-",2);
			NimaiAdvisory vasDet=advService.getVasDetails(vasSplitted[0]);*/
			response.put("vasAmount",vasPrice);
		/*}
		catch(Exception e)
		{
			response.put("vasAmount", "");
		}*/
		
		response.put("actualAmt",merchantP5);
		
		return response;
	}

	@Override
	public void saveData(String orderId, String sts) throws IOException {
		// TODO Auto-generated method stub
		
		 com.paypal.orders.Order order1 = null;
		 OrdersGetRequest request = new OrdersGetRequest(orderId);	

		
				HttpResponse<com.paypal.orders.Order> responsePaypal = Credentials.client.execute(request);
				order1 = responsePaypal.result();
				System.out.println("Details: "+order1.purchaseUnits().get(0).referenceId());
				
				String planSplit[]=order1.purchaseUnits().get(0).referenceId().split("-", 5);
				System.out.println("Length: "+planSplit.length);
				System.out.println("planSplit[0] :"+planSplit[0]);
				System.out.println("planSplit[1] :"+planSplit[1]);
				System.out.println("planSplit[2] :"+planSplit[2]);
				String merchantP1=planSplit[0];
				
		OnlinePayment op = new OnlinePayment();
		op.setUserId(merchantP1);
		op.setOrderId(orderId);
		op.setAmount(Double.valueOf(order1.purchaseUnits().get(0).amountWithBreakdown().value()));
		op.setCurrency(order1.purchaseUnits().get(0).amountWithBreakdown().currencyCode());
		op.setInvoiceId(order1.purchaseUnits().get(0).invoiceId());
		op.setTransactionId(order1.purchaseUnits().get(0).payments().captures().get(0).id());
		//op.setBankReceiptNo(finalData.get("bank_receipt_no"));
		//op.setTrackingId(payment.getTransactions().get(0).getRelatedResources().get(0).getSale().getId());
		//op.setStatusMessage(finalData.get("status_message"));
		if(sts.equalsIgnoreCase("Failed"))
			op.setStatus("Failed");
		else
			op.setStatus("Approved");
		//op.setFailureMessage(finalData.get("failure_message"));
		//op.setRequestDump(finalData.get("order_id"));
		//op.setResponseDump(finalData.toString());
		op.setInsertedBy(merchantP1);
		Date now = new Date();
		op.setInsertedDate(now);
		op.setModifiedBy(merchantP1);

		onlinePaymentRepo.save(op);

		if (op.getStatus().equalsIgnoreCase("Approved")) {
			userRepository.updatePaymentStatus("Approved", op.getOrderId(), op.getUserId());
		//	if(planSplit[3].equalsIgnoreCase("vas") || !payment.getTransactions().get(0).getItemList().getItems().get(1).getPrice().equalsIgnoreCase("0"))
		//		userRepository.updatePaymentStatus("Approved", op.getOrderId(), op.getUserId());
			//userRepository.updatePaymentTransactionId(op.getUserId(), op.getOrderId());
		}
	}

	@Override
	public List<SubscriptionAndPaymentBean> getLastPurchasedPlan(String userId) throws ParseException {
		// TODO Auto-generated method stub
		List<SubscriptionAndPaymentBean> sp=new ArrayList<SubscriptionAndPaymentBean>();
		DateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try
		{
			List<SubscriptionAndPaymentBean> spb=subscriptionDetailsRepository.getPreviousSubscription(userId);
			System.out.println("list: "+spb);
			for(Object d:spb)
			{
				System.out.println("============= "+d);
				SubscriptionAndPaymentBean s=new SubscriptionAndPaymentBean();
				s.setInvoiceId(((Object[])d)[0]==null?"null":((Object[])d)[0].toString());
				s.setInsertedDate(((Object[])d)[1]==null?new Date(0):(Date)simpleDateFormat.parse(((Object[])d)[1].toString()));
				s.setPaymentStatus(((Object[])d)[2]==null?"null":((Object[])d)[2].toString());
				s.setSplSerialNo(((Object[])d)[3]==null?0:Integer.valueOf(((Object[])d)[3].toString()));
				sp.add(s);
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return sp;
	}
}