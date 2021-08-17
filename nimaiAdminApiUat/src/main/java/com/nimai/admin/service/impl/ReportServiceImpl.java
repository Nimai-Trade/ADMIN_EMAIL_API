package com.nimai.admin.service.impl;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.Tuple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nimai.admin.model.NimaiLCMaster;
import com.nimai.admin.model.NimaiMCustomer;
import com.nimai.admin.model.NimaiMDiscount;
import com.nimai.admin.model.NimaiMRefer;
import com.nimai.admin.model.NimaiMmTransaction;
import com.nimai.admin.model.NimaiSubscriptionDetails;
import com.nimai.admin.payload.ReportBankRmPerformance;
import com.nimai.admin.payload.ReportBankRmUwPerformance;
import com.nimai.admin.payload.ReportBankTransaction;
import com.nimai.admin.payload.ReportCountryWise;
import com.nimai.admin.payload.ReportCustomerRmPerformance;
import com.nimai.admin.payload.ReportCustomerTransaction;
import com.nimai.admin.payload.ReportDiscountCoupon;
import com.nimai.admin.payload.ReportNewUserStatus;
import com.nimai.admin.payload.ReportPaymentAndSubscription;
import com.nimai.admin.payload.ReportProductRequirement;
import com.nimai.admin.payload.ReportReferrer;
import com.nimai.admin.payload.ReportUserSubscriptionRenewal;
import com.nimai.admin.payload.ReportsTxnExpiry;
import com.nimai.admin.payload.SearchRequest;
import com.nimai.admin.property.GenericExcelWriter;
import com.nimai.admin.repository.CustomerRepository;
import com.nimai.admin.repository.DiscountRepository;
import com.nimai.admin.repository.EmployeeRepository;
import com.nimai.admin.repository.ReferrerRepository;
import com.nimai.admin.repository.SubscriptionDetailsRepository;
import com.nimai.admin.repository.TransactionsRepository;
import com.nimai.admin.service.ReportService;
import com.nimai.admin.specification.CustomerSearchSpecification;
import com.nimai.admin.specification.DiscountSpecification;
import com.nimai.admin.specification.TransactionsSpecification;

@Service
public class ReportServiceImpl implements ReportService {

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	EntityManagerFactory em;

	@Autowired
	TransactionsRepository transactionRepository;

	@Autowired
	TransactionsSpecification transactionSpecification;

	@Autowired
	CustomerSearchSpecification customerSearchSpecification;

	@Autowired
	SubscriptionDetailsRepository subsDetailsRepo;

	@Autowired
	DiscountRepository discountRepo;

	@Autowired
	DiscountSpecification discSpecification;

	@Autowired
	SubscriptionDetailsRepository sPLanRepo;

	@Autowired
	ReferrerRepository referRepo;

	Date date = null;

	// Bank Transaction Report-done
	@Override
	public ByteArrayInputStream getAllBankTransactionDetails(SearchRequest request, String fileName) {
		List<ReportBankTransaction> transaction = new ArrayList<ReportBankTransaction>();
		List<Tuple> bankProjection;

		
		if (request.getUserId() != null && !request.getUserId().equals("")) {
			System.out.println("userid:" + request.getUserId());
			System.out.println("from date:" + request.getDateFrom());
			System.out.println("to date:" + request.getDateTo());
			bankProjection = customerRepository.getAllTransactionDetailsUserId(request.getDateFrom(),
					java.sql.Date.valueOf(LocalDate.parse(request.getDateTo()).plusDays(1)), request.getUserId());

			System.out.println("===========BankProjection list for user" + bankProjection.size());
			// return GenericExcelWriter.writeToExcel(fileName,
			// processBankProhectList(bankProjection,request.getUserId()));
		} else {
			bankProjection = customerRepository.getAllTransactionDetails(java.sql.Date.valueOf(request.getDateFrom()),
					java.sql.Date.valueOf(LocalDate.parse(request.getDateTo()).plusDays(1)));
			System.out.println("===============BankProjection" + bankProjection.size());
		}
		

		for (Tuple details : bankProjection) {
			HashMap<String, Integer> getData=calculateQuote((Integer)details.get("quotation_id"),(String)details.get("transaction_id"),"");
			ReportBankTransaction bank = new ReportBankTransaction();
			bank.setUser_ID((String) details.get("bank_userid"));
			bank.setMobile((String) details.get("mobile_number") != null ? (String) details.get("mobile_number") : "");
			bank.setEmail((String) details.get("email_address") != null ? (String) details.get("email_address") : "");
			bank.setDate_3_Time((java.util.Date) details.get("inserted_date") != null
					? (java.util.Date) details.get("inserted_date")
					: date);
			bank.setBank_Name((String) details.get("bank_name") != null ? (String) details.get("bank_name") : "");
			bank.setBranch_Name((String) details.get("branch_name") != null ? (String) details.get("branch_name") : "");
			bank.setCountry((String) details.get("country_name") != null ? (String) details.get("country_name") : "");
			bank.setTransaction_Id(
					(String) details.get("transaction_id") != null ? (String) details.get("transaction_id") : "");
			bank.setRequirement(
					(String) details.get("requirement_type") != null ? (String) details.get("requirement_type") : "");
			bank.setiB(
					(String) details.get("lc_issuance_bank") != null ? (String) details.get("lc_issuance_bank") : "");
			bank.setAmount((Double) details.get("lc_value") != null ? (Double) details.get("lc_value") : 0);
			bank.setCcy((String) details.get("lc_currency") != null ? (String) details.get("lc_currency") : "");
			bank.setTenor(
					(Integer) details.get("original_tenor_days") != null ? (Integer) details.get("original_tenor_days")
							: 0);
			bank.setApplicable_benchmark(
			    	(Float) details.get("applicable_benchmark") != null ? (Float) details.get("applicable_benchmark")
								: 0); 
			bank.setConfirmation_charges_p_a(
					(Float) details.get("confirmation_charges") != null ? (Float) details.get("confirmation_charges")
							: 0); 
			bank.setDiscounting_charges_p_a(
					(Float) details.get("discounting_charges") != null ? (Float) details.get("discounting_charges")
							: 0);
			bank.setRefinancing_charges_p_a(
					(Float) details.get("refinancing_charges") != null ? (Float) details.get("refinancing_charges")
							: 0);
			bank.setBanker_accept_charges_p_a(
					(Float) details.get("banker_accept_charges") != null ? (Float) details.get("banker_accept_charges")
							: 0);
		
			bank.setConfirmation_charges_from_date_of_issuance_till_negotiation_date(
					String.valueOf(getData.get("confChgsNegot"))!= null? String.valueOf(getData.get("confChgsNegot")): "0");
			
			bank.setConfirmation_charges_from_date_of_issuance_till_maturity_date(
					String.valueOf(getData.get("confChgsMatur"))!= null ? String.valueOf(getData.get("confChgsMatur")): "0");
			bank.setNegotiation_charges_in_fixed(
					(Float) details.get("negotiation_charges_fixed") != null
							? (Float) details.get("negotiation_charges_fixed")
							: 0);
			bank.setNegotiation_charges_in_percentage(
					(Float) details.get("negotiation_charges_perct") != null
							? (Float) details.get("negotiation_charges_perct")
							: 0);
			bank.setOther_Charges(
					(Float) details.get("other_charges") != null ? (Float) details.get("other_charges") : 0);
			bank.setMin_Trxn_Charges((Float) details.get("minimum_transaction_charges") != null
					? (Float) details.get("minimum_transaction_charges")
					: 0);
			//bank.setTotal_Quote((Float)getData.get("TotalQuote"));
			
			bank.setTotal_Quote((Float) ((Integer) getData.get("TotalQuote")).floatValue() != null
					? (Float) ((Integer) getData.get("TotalQuote")).floatValue()
					: 0);
			bank.setValidity(((String) details.get("validity")).replace("-", "/").replaceAll(" 00:00:00", "") != null
					? (String) details.get("validity")
					: "");

			transaction.add(bank);

		}
		ByteArrayInputStream excel = GenericExcelWriter.writeToExcel(fileName, transaction);
		if (excel == null) {
			return null;
		}
		return excel;
	}

	private List<ReportBankTransaction> processBankProhectList(List<Tuple> bankProjection, String string) {
		// TODO Auto-generated method stub
		List<ReportBankTransaction> transaction = new ArrayList<ReportBankTransaction>();
		for (Tuple details : bankProjection) {
			System.out.println("INside process for loop" + details.get("bank_userid"));
			ReportBankTransaction bank = new ReportBankTransaction();
			if (details.get("bank_userid") == string) {
				bank.setUser_ID((String) details.get("bank_userid"));
				bank.setMobile(
						(String) details.get("mobile_number") != null ? (String) details.get("mobile_number") : "");
				bank.setEmail(
						(String) details.get("email_address") != null ? (String) details.get("email_address") : "");
				bank.setDate_3_Time((java.util.Date) details.get("inserted_date") != null
						? (java.util.Date) details.get("inserted_date")
						: date);
				bank.setBank_Name((String) details.get("bank_name") != null ? (String) details.get("bank_name") : "");
				bank.setBranch_Name(
						(String) details.get("branch_name") != null ? (String) details.get("branch_name") : "");
				bank.setCountry(
						(String) details.get("country_name") != null ? (String) details.get("country_name") : "");
				bank.setTransaction_Id(
						(String) details.get("transaction_id") != null ? (String) details.get("transaction_id") : "");
				bank.setRequirement(
						(String) details.get("requirement_type") != null ? (String) details.get("requirement_type")
								: "");
				bank.setiB((String) details.get("lc_issuance_bank") != null ? (String) details.get("lc_issuance_bank")
						: "");
				bank.setAmount((Double) details.get("lc_value") != null ? (Double) details.get("lc_value") : 0);
				bank.setCcy((String) details.get("lc_currency") != null ? (String) details.get("lc_currency") : "");
				bank.setTenor((Integer) details.get("original_tenor_days") != null
						? (Integer) details.get("original_tenor_days")
						: 0);
				bank.setConfirmation_charges_p_a((Float) details.get("confirmation_charges") != null
						? (Float) details.get("confirmation_charges")
						: 0);
//				bank.setConfirmation_charges_from_date_of_issuance_till_negotiation_date(
//						(String) details.get("conf_chgs_issuance_to_negot") != null
//								? (String) details.get("conf_chgs_issuance_to_negot")
//								: "");
//				bank.setConfirmation_charges_from_date_of_issuance_till_maturity_date(
//						(String) details.get("conf_chgs_issuance_to_matur") != null
//								? (String) details.get("conf_chgs_issuance_to_matur")
//								: "");
				bank.setOther_Charges(
						(Float) details.get("other_charges") != null ? (Float) details.get("other_charges") : 0);
				bank.setMin_Trxn_Charges((Float) details.get("minimum_transaction_charges") != null
						? (Float) details.get("minimum_transaction_charges")
						: 0);
				bank.setTotal_Quote((Float) ((BigInteger) details.get("total_quotes")).floatValue() != null
						? (Float) ((BigInteger) details.get("total_quotes")).floatValue()
						: 0);
				bank.setValidity(
						((String) details.get("validity")).replace("-", "/").replaceAll(" 00:00:00", "") != null
								? (String) details.get("validity")
								: "");

				transaction.add(bank);
			}

		}
		return transaction;

	}

	// Trxn Expiry Reports-done
	@Override
	public ByteArrayInputStream getTransactioReports(SearchRequest request, String filename) {
		List<ReportsTxnExpiry> trans = new ArrayList<ReportsTxnExpiry>();
		List<Tuple> tupleTxn;
		if (request.getUserId() != null && !request.getUserId().equals("")) {
			tupleTxn = transactionRepository.getAllTransDetailsUserId(java.sql.Date.valueOf(request.getDateFrom()),
					java.sql.Date.valueOf(LocalDate.parse(request.getDateTo()).plusDays(1)), request.getUserId());
		} else {
			tupleTxn = transactionRepository.getAllTransDetails(java.sql.Date.valueOf(request.getDateFrom()),
					java.sql.Date.valueOf(LocalDate.parse(request.getDateTo()).plusDays(1)));

		}

		for (Tuple t : tupleTxn) {
			ReportsTxnExpiry repTran = new ReportsTxnExpiry();
			repTran.setTrxn_ID((String) t.get("txnId"));
			repTran.setDate_Time(
					(java.util.Date) t.get("date_time") != null ? (java.util.Date) t.get("date_time") : date);
			repTran.setApplicant((String) t.get("applicant") != null ? (String) t.get("applicant") : "");
			repTran.setA_Country((String) t.get("a_country") != null ? (String) t.get("a_country") : "");
			repTran.setBeneficiary((String) t.get("beneficiary") != null ? (String) t.get("beneficiary") : "");
			repTran.setB_country((String) t.get("b_country") != null ? (String) t.get("b_country") : "");
			repTran.setRequirement((String) t.get("requirement") != null ? (String) t.get("requirement") : "");
			repTran.setAmount((Long) ((Double) t.get("amount")).longValue() != null
					? (Long) ((Double) t.get("amount")).longValue()
					: 0);
			repTran.setCcy((String) t.get("ccy") != null ? (String) t.get("ccy") : "");
			repTran.setCustomer((String) t.get("customer") != null ? (String) t.get("customer") : "");
			repTran.setValidity(((String) t.get("validity")).replace("-", "/").replaceAll(" 00:00:00", "") != null
					? (String) t.get("validity")
					: "");

			if (((BigInteger) t.get("expires_in")).intValue() < 0) {
				repTran.setExpired_in("expired");
			} else if (((BigInteger) t.get("expires_in")).intValue() == 0) {
				repTran.setExpired_in("Expiring Today");
			} else {
				repTran.setExpired_in((String) t.get("expires_in").toString() + " days");
			}
			repTran.setRm((String) t.get("rm") != null ? (String) t.get("rm") : "");

			trans.add(repTran);
		}

		return GenericExcelWriter.writeToExcel(filename, trans);
	}

	// New User Report-done
	@Override
	public ByteArrayInputStream getNewUserReport(SearchRequest request, String filename) {

		List<ReportNewUserStatus> newUser = new ArrayList<ReportNewUserStatus>();
		List<Tuple> tuple;
		if (request.getUserId() != null && !request.getUserId().equals("")) {
			tuple = customerRepository.getNewUserIdReports(java.sql.Date.valueOf(request.getDateFrom()),
					java.sql.Date.valueOf(LocalDate.parse(request.getDateTo()).plusDays(1)), request.getUserId());
		} else {
			tuple = customerRepository.getNewUserReports(java.sql.Date.valueOf(request.getDateFrom()),
					java.sql.Date.valueOf(LocalDate.parse(request.getDateTo()).plusDays(1)));
		}
		for (Tuple details : tuple) {
			//String bankName=customerRepository.getBankName((String) details.get("userid")); //parent bankname
			
			ReportNewUserStatus newReport = new ReportNewUserStatus();
			newReport.setUser_ID((String) details.get("userid"));
			newReport.setUser_Type(
					(String) details.get("customer_type") != null ? (String) details.get("customer_type") : "");
			
			newReport.setCustomer$Bank$Bank_as_Customer_Name(
					(String) details.get("customer_Name") != null ? (String) details.get("customer_Name") : "");
			newReport.setSubscription(
					(String) details.get("subs_plan") != null ? (String) details.get("subs_plan") : "");
		
			if (details.get("vas")!=null){
				if(details.get("vas").equals(0)) {
					newReport.setvAS("No");
				}else if(details.get("vas").equals(1)) {
					newReport.setvAS("Yes");
				}
				
			}
			//newReport.setvAS((Integer) details.get("vas") != null ? (Integer) details.get("vas") : 0);
			newReport.setFees_Paid_$USD$((Double) details.get("fee") != null ? (Double) details.get("fee") : 0);
		//	newReport.setCcy((String) details.get("ccy") != null ? (String) details.get("ccy") : "");
			newReport.setPayment_Mode(
					(String) details.get("mode_of_payment") != null ? (String) details.get("mode_of_payment") : "");
			newReport.setPayment_Status(
					(String) details.get("payment_status") != null ? (String) details.get("payment_status") : "");
			newReport.setkYC_Status(
					(String) details.get("kyc_status") != null ? (String) details.get("kyc_status") : "");
			newReport.setRm((String) details.get("rm") != null ? (String) details.get("rm") : "");

			newUser.add(newReport);

		}
		return GenericExcelWriter.writeToExcel(filename, newUser);
	}

//Subscription renewal report-done
	@Override
	public ByteArrayInputStream getUSubsRenewal(SearchRequest request, String filename) {
		List<ReportUserSubscriptionRenewal> renewal = new ArrayList<ReportUserSubscriptionRenewal>();
		List<Tuple> reports;
		System.out.println("----------------"+request.getDateFrom());
		if (request.getUserId() != null && !request.getUserId().equals("")) {
//			reports = subsDetailsRepo.getUserSubsRenewalUserId(java.sql.Date.valueOf(request.getDateFrom()),
//					java.sql.Date.valueOf(LocalDate.parse(request.getDateTo()).plusDays(1)), request.getUserId());
			
			reports = subsDetailsRepo.getUserSubsRenewalUserId(request.getDateFrom(),
					java.sql.Date.valueOf(LocalDate.parse(request.getDateTo()).plusDays(0)), request.getUserId());
			
		} else {
//			reports = subsDetailsRepo.getUserSubsRenewal(java.sql.Date.valueOf(request.getDateFrom()),
//					java.sql.Date.valueOf(LocalDate.parse(request.getDateTo()).plusDays(1)));
			
			reports = subsDetailsRepo.getUserSubsRenewal(request.getDateFrom(),
					java.sql.Date.valueOf(LocalDate.parse(request.getDateTo()).plusDays(1)));
		}
		for (Tuple response : reports) {
			ReportUserSubscriptionRenewal responses = new ReportUserSubscriptionRenewal();
			responses.setUser_ID((String) response.get("userid") != null ? (String) response.get("userid") : "");
			responses.setUser_Type(
					(String) response.get("subscriber_type") != null ? (String) response.get("subscriber_type") : "");
			responses.setCustomer$Bank$Bank_as_Customer_Name(
					(String) response.get("company_name") != null ? (String) response.get("company_name") : "");
			responses.setSubscription(
					(String) response.get("subscription_name") != null ? (String) response.get("subscription_name")
							: "");
			if ((Integer) response.get("is_vas_applied") == 0) {
				responses.setVas("No");
			} else if ((Integer) response.get("is_vas_applied") == 1) {
				responses.setVas("Yes");
			}
			responses.setFees_Paid((Integer) response.get("subscription_amount") != null
					? (Integer) response.get("subscription_amount")
					: 0);
			responses.setActivation_Date(
					(Date) response.get("splan_start_date") != null ? (Date) response.get("splan_start_date") : date);
			responses.setExpiry_Date(
					(Date) response.get("splan_end_date") != null ? (Date) response.get("splan_end_date") : date);
			responses.setCredits_Available(
					(Double) response.get("credits_available") != null ? (Double) response.get("credits_available")
							: 0);
			responses.setSubsidiaries$Additional(
					(String) response.get("subsidiaries") != null ? (String) response.get("subsidiaries") : "");

			renewal.add(responses);
		}
		return GenericExcelWriter.writeToExcel(filename, renewal);

	}

	// Discount Report-done
	@Override
	public ByteArrayInputStream getDiscountReport(SearchRequest request, String filename) {
		List<NimaiMDiscount> mDiscount = discountRepo.findAll(discSpecification.getFilter(request));
		List<ReportDiscountCoupon> disCoupon = new ArrayList<ReportDiscountCoupon>();
		
		for (NimaiMDiscount disc : mDiscount) {
			ReportDiscountCoupon response = new ReportDiscountCoupon();
			response.setDiscount_Type(disc.getDiscountType() != null ? disc.getDiscountType() : "");
			response.setDiscount_amount((Double) disc.getAmount() != null ? disc.getAmount() : 0);
			response.setCcy(disc.getCurrency() != null ? disc.getCurrency() : "");
			response.setDiscount_2((Double) disc.getDiscountPercentage() != null ? disc.getDiscountPercentage() : 0);
			response.setMax_Discount((Double) disc.getMaxDiscount() != null ? disc.getMaxDiscount() : 0);
			response.setCcy(disc.getCurrency() != null ? disc.getCurrency() : "");
			response.setCoupon_For(disc.getCouponFor() != null ? disc.getCouponFor() : "");
			response.setSubscripton_Plan(disc.getSubscriptionPlan() != null ? disc.getSubscriptionPlan() : "");
			response.setCountry(disc.getCountry() != null ? disc.getCountry() : "");
			response.setQuantity(disc.getQuantity() != null ? disc.getQuantity() : 0);
			response.setStart_Date(disc.getStartDate() != null ? disc.getStartDate() : date);
			response.setEnd_Date(disc.getEndDate() != null ? disc.getEndDate() : date);
			response.setCoupon_Code(disc.getCouponCode() != null ? disc.getCouponCode() : "");
			response.setConsumed(disc.getConsumedCoupons() != null ? disc.getConsumedCoupons() : 0);

disCoupon.add(response);
		}
	
		return GenericExcelWriter.writeToExcel(filename, disCoupon);

	}

	// product requirement//done
	@Override
	public ByteArrayInputStream getProdReqReport(SearchRequest request, String filename) {
		List<Tuple> reqReport = transactionRepository.getProductReqRep(java.sql.Date.valueOf(request.getDateFrom()),
				java.sql.Date.valueOf(LocalDate.parse(request.getDateTo()).plusDays(1)));

		List<ReportProductRequirement> product = new ArrayList<ReportProductRequirement>();
		for (Tuple tuple : reqReport) {
			ReportProductRequirement response = new ReportProductRequirement();
			response.setCountry(
					(String) tuple.get("lc_issuance_country") != null ? (String) tuple.get("lc_issuance_country") : "");
			response.setCcy((String) tuple.get("lc_currency") != null ? (String) tuple.get("lc_currency") : "");
			response.setProduct_Type(
					(String) tuple.get("requirement_type") != null ? (String) tuple.get("requirement_type") : "");
			response.setTxn_Placed(((BigInteger) tuple.get("txn_Placed")).intValue());

			if (tuple.get("lc_value") == null) {
				response.setCumulative_LC_Value(0.0);
			} else {
				response.setCumulative_LC_Value((Double) tuple.get("lc_value"));
			}

//			response.setCumulative_LC_Value((Double) ((BigDecimal) tuple.get("lc_value")).doubleValue() != null
//					? (Double) ((BigDecimal) tuple.get("lc_value")).doubleValue()
//					: 0);
			response.setQuotes_Received(((BigInteger) tuple.get("total_quotes")).intValue());
			response.setCumulative_Quote_Value(
					(Double) tuple.get("quote_value") != null ? (Double) tuple.get("quote_value") : 0);

			product.add(response);

		}

		return GenericExcelWriter.writeToExcel(filename, product);

	}

	// Referrer Report
	// will be provided by sahadeo sir
	@Override
	public ByteArrayInputStream getReffReport(SearchRequest request, String filename) throws ParseException {
		// List<NimaiMCustomer> referDetails =
		// customerRepository.findAll(customerSearchSpecification.getFilter(request));

//		List<NimaiMCustomer> reDetails=customerRepository.findByDates(request.getDateFrom(),request.getDateTo());
//		
		DateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
		java.util.Date fromDate = formatter.parse(request.getDateFrom());
		java.util.Date toDate = formatter.parse(request.getDateTo());
		List<ReportReferrer> referrer = new ArrayList<ReportReferrer>();
		if (request.getUserId() != null && !request.getUserId().equals("")) {
			NimaiMCustomer referrDetails = customerRepository.getOne(request.getUserId());
			ReportReferrer response = new ReportReferrer();
			response.setCountry(referrDetails.getCountryName());
			response.setReferrer_userID(referrDetails.getUserid());
			response.setFirst_Name(referrDetails.getFirstName());
			response.setLast_Name(referrDetails.getLastName());
			response.setReferrerEmailId(referrDetails.getEmailAddress());
			response.setRm(referrDetails.getRmId());

			Integer approvedReference = customerRepository.getApprovedReferrence(request.getUserId());
			if (approvedReference.equals(null)) {
				response.setApproved_References(0);
			} else {
				response.setApproved_References(approvedReference);
			}
			try {
				Integer totalReference = customerRepository.getTotareference(request.getUserId());
				if (totalReference.equals(null)) {
					response.setTotal_References(0);
				} else {
					response.setTotal_References(totalReference);
				}
			} catch (Exception e) {
				e.printStackTrace();

			}

			try {
				Integer pendingReference = customerRepository.getpendingReference(request.getUserId());
				if (pendingReference.equals(null)) {
					response.setPending_References(0);
				} else {
					response.setPending_References(pendingReference);
				}
			} catch (Exception e) {
				e.printStackTrace();

			}
			try {
				Integer rejectedReference = customerRepository.getRejectedReference(request.getUserId());
				if (rejectedReference.equals(null)) {
					response.setRejected_References(0);
				} else {
					response.setRejected_References(rejectedReference);
				}
			} catch (Exception e) {
				e.printStackTrace();

			}

			try {
				Double earning = customerRepository.getEarning(request.getUserId());
				if (earning.equals(null)) {
					response.setEarning(0);
				} else {
					response.setEarning(earning);
				}
			} catch (Exception e) {
				e.printStackTrace();

			}

			response.setCcy(referrDetails.getCurrencyCode());

			response.setRm(referrDetails.getRmId());

			referrer.add(response);

		} else {
			List<NimaiMRefer> referList = referRepo.finBydates(fromDate, toDate);

			for (NimaiMRefer cust : referList) {
				NimaiMCustomer referrDetails = customerRepository.getOne(cust.getUserid().getUserid());
				ReportReferrer response = new ReportReferrer();
				response.setCountry(cust.getCountryName());
				response.setReferrer_userID(cust.getUserid().getUserid());
				response.setFirst_Name(cust.getFirstName());
				response.setLast_Name(cust.getLastName());
				response.setReferrerEmailId(cust.getUserid().getUserid());
				Integer approvedReference = customerRepository.getApprovedReferrence(cust.getUserid().getUserid());
				if (approvedReference.equals(null)) {
					response.setApproved_References(0);
				} else {
					response.setApproved_References(approvedReference);
				}
				try {
					Integer totalReference = customerRepository.getTotareference(cust.getUserid().getUserid());
					if (totalReference.equals(null)) {
						response.setTotal_References(0);
					} else {
						response.setTotal_References(totalReference);
					}
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}

				try {
					Integer pendingReference = customerRepository.getpendingReference(cust.getUserid().getUserid());
					if (pendingReference.equals(null)) {
						response.setPending_References(0);
					} else {
						response.setPending_References(pendingReference);
					}
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
				try {
					Integer rejectedReference = customerRepository.getRejectedReference(cust.getUserid().getUserid());
					if (rejectedReference.equals(null)) {
						response.setRejected_References(0);
					} else {
						response.setRejected_References(rejectedReference);
					}
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}

				try {
					Double earning = customerRepository.getEarning(cust.getUserid().getUserid());
					if (earning.equals(null)) {
						response.setEarning(0);
					} else {
						response.setEarning(earning);
					}
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}

				response.setCcy(cust.getUserid().getCurrencyCode());

				response.setRm(cust.getUserid().getRmId());
				referrer.add(response);

			}
		}

		return GenericExcelWriter.writeToExcel(filename, referrer);
	}

	// Country Wise Reports-data duplicate issuewill be checked by sahadeoSir
	@Override
	public ByteArrayInputStream getCountryWiseReport(SearchRequest request, String filename) {
//		List<NimaiMmTransaction> transaction = transactionRepository
//				.findAll(transactionSpecification.getFilter(request));

List<NimaiMmTransaction> transaction = transactionRepository
.findCountryWiseDataByDates(request.getDateFrom(),request.getDateTo());
System.out.println(transaction.size());
		List<ReportCountryWise> country = new ArrayList<ReportCountryWise>();

		for (NimaiMmTransaction details : transaction) {
			ReportCountryWise countReport = new ReportCountryWise();
			
			Integer txnCount=transactionRepository.getTxnCount(details.getLcIssuanceCountry(),details.getLcCurrency());
			if(txnCount==null) {
				countReport.setTrxn_Count(0);
			}else {
				countReport.setTrxn_Count(txnCount);
			}
			
			Double getcumulativeTxnAMount=transactionRepository.getcumulativeTxnAMount(details.getLcIssuanceCountry(),details.getLcCurrency());
			if(getcumulativeTxnAMount==null) {
				countReport.setCumulative_Trxn_Amount("0");
			}else {
				String cumulativeTxnAMount = new BigDecimal(getcumulativeTxnAMount).toEngineeringString();
				countReport.setCumulative_Trxn_Amount(cumulativeTxnAMount);
			}
			
			Integer gettxnAccepted=transactionRepository.gettxnAccepted(details.getLcIssuanceCountry(),details.getLcCurrency());
			if(gettxnAccepted==null) {
				countReport.setTrxn_Accepted(0);
			}else {
				countReport.setTrxn_Accepted(gettxnAccepted);
			}
			
			Integer gettxnRejected=transactionRepository.gettxnRejected(details.getLcIssuanceCountry(),details.getLcCurrency());
			if(gettxnRejected==null) {
				countReport.setTrxn_Rejected(0);
			}else {
				countReport.setTrxn_Rejected(gettxnRejected);
			}
			Integer gettxnExpired=transactionRepository.gettxnExpired(details.getLcIssuanceCountry(),details.getLcCurrency());
			if(gettxnExpired==null) {
				countReport.setTrxn_Expired(0);
			}else {
				countReport.setTrxn_Expired(gettxnExpired);
			}
			Integer gettxnclosedTxn=transactionRepository.gettxnclosedTxn(details.getLcIssuanceCountry(),details.getLcCurrency());
			if(gettxnclosedTxn==null) {
				countReport.setTrxn_Closed(0);
			}else {
				countReport.setTrxn_Closed(gettxnclosedTxn);
			}
			countReport.setCountry1of_registration0(details.getLcIssuanceCountry());
			countReport.setCcy(details.getLcCurrency());
			
		
			
			
			
			
//			List<NimaiMmTransaction> count = transactionRepository
//					.findByLcIssuanceCountryAndLcCurrency(details.getLcIssuanceCountry(), details.getLcCurrency());
//			countReport.setTrxn_Count(count.size());
//			int expired = 0;
//			int rejected = 0;
//			int accepted = 0;
//			int closed = 0;
//			double totalAmount = 0;
//			for (int i = 0; i < count.size(); i++) {
//				NimaiMmTransaction t = transactionRepository.getOne(count.get(i).getTransactionId());
//				totalAmount = totalAmount + t.getLcValue();
//				if (t != null) {
//					if (t.getTransactionStatus() != null && t.getTransactionStatus().equalsIgnoreCase("Expired")) {
//						expired = expired + 1;
//					} else if (t.getTransactionStatus() != null
//							&& t.getTransactionStatus().equalsIgnoreCase("Rejected")) {
//						rejected = rejected + 1;
//					} else if (t.getTransactionStatus() != null
//							&& t.getTransactionStatus().equalsIgnoreCase("Active")) {
//						accepted = accepted + 1;
//					} else if (t.getTransactionStatus().equalsIgnoreCase("Closed")) {
//						closed = closed + 1;
//					}
//				}
//			//	countReport.setCumulative_Trxn_Amount(totalAmount);
//				countReport.setTrxn_Accepted(accepted);
//				countReport.setTrxn_Closed(closed);
//				countReport.setTrxn_Rejected(rejected);
//				countReport.setTrxn_Expired(expired);
//			}

			country.add(countReport);
		}

		return GenericExcelWriter.writeToExcel(filename, country);
	}

	// Payment and Subcription-done
	@Override
	public ByteArrayInputStream getPaymentSubscriptionReport(SearchRequest request, String filename)
			throws ParseException {
		List<ReportPaymentAndSubscription> payReport = new ArrayList<ReportPaymentAndSubscription>();
		List<NimaiSubscriptionDetails> customerList;
		List<Tuple> tuple;
		DateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
		java.util.Date fromDate = formatter.parse(request.getDateFrom());
		java.util.Date toDate = formatter.parse(request.getDateTo());
		
		if (request.getUserId() != null && !request.getUserId().equals("")) {
		
			tuple = customerRepository.getPaymentSubUserIdReport(request.getDateFrom(),
					java.sql.Date.valueOf(LocalDate.parse(request.getDateTo()).plusDays(1)), request.getUserId());
		} else {
			tuple = customerRepository.getPaymentSubReport(request.getDateFrom(),
					java.sql.Date.valueOf(LocalDate.parse(request.getDateTo()).plusDays(1)));
		}		
		
	//	if (request.getUserId() != null) {
//			tuple = customerRepository.getPaymentSubUserIdReport(java.sql.Date.valueOf(request.getDateFrom()),
//					java.sql.Date.valueOf(LocalDate.parse(request.getDateTo()).plusDays(2)), request.getUserId());
			for (Tuple report : tuple) {
				ReportPaymentAndSubscription response = new ReportPaymentAndSubscription();
				response.setUser_ID((String) report.get("userid") != null ? (String) report.get("userid") : "");
				response.setUser_Type(
						(String) report.get("subscriber_type") != null ? (String) report.get("subscriber_type") : "");
				response.setOrganization(
						(String) report.get("company_name") != null ? (String) report.get("company_name") : "");
				response.setMobile(
						(String) report.get("mobile_number") != null ? (String) report.get("mobile_number") : "");
				response.setLandline((String) report.get("landline") != null ? (String) report.get("landline") : "");
				response.setCountry(
						(String) report.get("country_name") != null ? (String) report.get("country_name") : "");
				response.setEmail(
						(String) report.get("email_address") != null ? (String) report.get("email_address") : "");
				response.setFirst_Name(
						(String) report.get("first_name") != null ? (String) report.get("first_name") : "");
				response.setLast_Name((String) report.get("last_name") != null ? (String) report.get("last_name") : "");
				response.setPlan(
						(String) report.get("subscription_name") != null ? (String) report.get("subscription_name")
								: "");
				if ((Integer) report.get("is_vas_applied") == 0) {
					response.setvAS("No");
				} else if ((Integer) report.get("is_vas_applied") == 1) {
					response.setvAS("Yes");
				}
				response.setCoupon_Code("");
				response.setCoupon_Discount(0);
				response.setDiscount_Ccy("");
				response.setFee_Paid(Double.valueOf((Integer) report.get("subscription_amount")) != null
						? Double.valueOf((Integer) report.get("subscription_amount"))
						: 0);
				response.setCcy(
						(String) report.get("currency_code") != null ? (String) report.get("currency_code") : "");
				response.setMode_of_Payment(
						(String) report.get("mode_of_payment") != null ? (String) report.get("mode_of_payment") : "");
				response.setDate_3_Time((java.util.Date) report.get("inserted_date") != null
						? (java.util.Date) report.get("inserted_date")
						: date);
				response.setPayment_ID("");
				response.setPlan_activation_Date(
						(Date) report.get("splan_start_date") != null ? (Date) report.get("splan_start_date") : date);
				response.setPlan_expiry_Date(
						(Date) report.get("splan_end_date") != null ? (Date) report.get("splan_end_date") : date);

				payReport.add(response);

		//	}

//		} else {
//			customerList = sPLanRepo.getCustomerDetail(fromDate, toDate);
//			// System.out.println("============list" + customerList.size());
//			processCUstomerDetailsPayment(customerList, request);
//			for (NimaiSubscriptionDetails report : customerList) {
//				// System.out.println("Process ========================" + report.toString());
//				ReportPaymentAndSubscription response = new ReportPaymentAndSubscription();
//				if (report.getUserid() == null) {
//					response.setUser_ID("");
//					response.setUser_Type("");
//					response.setOrganization("");
//					response.setMobile("");
//					response.setLandline("");
//					response.setCountry("");
//					response.setEmail("");
//					response.setFirst_Name("");
//					response.setLast_Name("");
//				} else {
//					response.setUser_ID(
//							(String) report.getUserid().getUserid() != null ? (String) report.getUserid().getUserid()
//									: "");
//					response.setUser_Type((String) report.getUserid().getSubscriberType() != null
//							? (String) report.getUserid().getSubscriberType()
//							: "");
//					response.setOrganization((String) report.getUserid().getCompanyName() != null
//							? (String) report.getUserid().getCompanyName()
//							: "");
//					response.setMobile((String) report.getUserid().getMobileNumber() != null
//							? (String) report.getUserid().getMobileNumber()
//							: "");
//					response.setLandline((String) report.getUserid().getLandline() != null
//							? (String) report.getUserid().getLandline()
//							: "");
//					response.setCountry((String) report.getUserid().getCountryName() != null
//							? (String) report.getUserid().getCountryName()
//							: "");
//					response.setEmail(
//							(String) report.getUserid().getEmailAddress() != null ? report.getUserid().getEmailAddress()
//									: "");
//					response.setFirst_Name(
//							(String) report.getUserid().getFirstName() != null ? report.getUserid().getFirstName()
//									: "");
//					response.setLast_Name(
//							(String) report.getUserid().getLastName() != null ? report.getUserid().getLastName() : "");
//
//				}
//
//				response.setPlan(
//						(String) report.getSubscriptionName() != null ? (String) report.getSubscriptionName() : "");
//				if ((Integer) report.getIsVasApplied() == 0) {
//					response.setvAS("No");
//				} else if ((Integer) report.getIsVasApplied() == 1) {
//					response.setvAS("Yes");
//				}
//				response.setCoupon_Code("");
//				response.setCoupon_Discount(0);
//				response.setDiscount_Ccy("");
//				if (report.getSubscriptionAmount() == null) {
//					response.setFee_Paid(0);
//				} else {
//					response.setFee_Paid(Double.valueOf((Integer) report.getSubscriptionAmount()) != null
//							? Double.valueOf((Integer) report.getSubscriptionAmount())
//							: 0);
//				}
//
//				// response.setCcy((String) report.get("currency_code") != null ? (String)
//				// report.get("currency_code") : "");
//				// response.setMode_of_Payment(
//				// (String) report.get("mode_of_payment") != null ? (String)
//				// report.get("mode_of_payment") : "");
//				response.setDate_3_Time(
//						(java.util.Date) report.getInsertedDate() != null ? (java.util.Date) report.getInsertedDate()
//								: date);
//				response.setPayment_ID("");
//				response.setPlan_activation_Date(
//						(Date) report.getSplanStartDate() != null ? (Date) report.getSplanStartDate() : date);
//				response.setPlan_expiry_Date(
//						(Date) report.getSplanEndDate() != null ? (Date) report.getSplanEndDate() : date);
//
//				payReport.add(response);
//				// return response;
//
//			}
//
		}

		return GenericExcelWriter.writeToExcel(filename, payReport);

	}

	private List<ReportPaymentAndSubscription> processCUstomerDetailsPayment(
			List<NimaiSubscriptionDetails> customerList, SearchRequest request) {
		// TODO Auto-generated method stub

		List<ReportPaymentAndSubscription> payReport = new ArrayList<ReportPaymentAndSubscription>();

		for (NimaiSubscriptionDetails report : customerList) {
			System.out.println("Process ========================" + report.toString());
			ReportPaymentAndSubscription response = new ReportPaymentAndSubscription();
			if (report.getUserid() == null) {
				response.setUser_ID("");
				response.setUser_Type("");
				response.setOrganization("");
				response.setMobile("");
				response.setLandline("");
				response.setCountry("");
				response.setEmail("");
				response.setFirst_Name("");
				response.setLast_Name("");
			} else {
				response.setUser_ID(
						(String) report.getUserid().getUserid() != null ? (String) report.getUserid().getUserid() : "");
				response.setUser_Type((String) report.getUserid().getSubscriberType() != null
						? (String) report.getUserid().getSubscriberType()
						: "");
				response.setOrganization((String) report.getUserid().getCompanyName() != null
						? (String) report.getUserid().getCompanyName()
						: "");
				response.setMobile((String) report.getUserid().getMobileNumber() != null
						? (String) report.getUserid().getMobileNumber()
						: "");
				response.setLandline(
						(String) report.getUserid().getLandline() != null ? (String) report.getUserid().getLandline()
								: "");
				response.setCountry((String) report.getUserid().getCountryName() != null
						? (String) report.getUserid().getCountryName()
						: "");
				response.setEmail(
						(String) report.getUserid().getEmailAddress() != null ? report.getUserid().getEmailAddress()
								: "");
				response.setFirst_Name(
						(String) report.getUserid().getFirstName() != null ? report.getUserid().getFirstName() : "");
				response.setLast_Name(
						(String) report.getUserid().getLastName() != null ? report.getUserid().getLastName() : "");

			}

			response.setPlan(
					(String) report.getSubscriptionName() != null ? (String) report.getSubscriptionName() : "");
			if ((Integer) report.getIsVasApplied() == 0) {
				response.setvAS("No");
			} else if ((Integer) report.getIsVasApplied() == 1) {
				response.setvAS("Yes");
			}
			response.setCoupon_Code("");
			response.setCoupon_Discount(0);
			response.setDiscount_Ccy("");
			if (report.getSubscriptionAmount() == null) {
				response.setFee_Paid(0);
			} else {
				response.setFee_Paid(Double.valueOf((Integer) report.getSubscriptionAmount()) != null
						? Double.valueOf((Integer) report.getSubscriptionAmount())
						: 0);
			}

			// response.setCcy((String) report.get("currency_code") != null ? (String)
			// report.get("currency_code") : "");
			// response.setMode_of_Payment(
			// (String) report.get("mode_of_payment") != null ? (String)
			// report.get("mode_of_payment") : "");
			response.setDate_3_Time(
					(java.util.Date) report.getInsertedDate() != null ? (java.util.Date) report.getInsertedDate()
							: date);
			response.setPayment_ID("");
			response.setPlan_activation_Date(
					(Date) report.getSplanStartDate() != null ? (Date) report.getSplanStartDate() : date);
			response.setPlan_expiry_Date(
					(Date) report.getSplanEndDate() != null ? (Date) report.getSplanEndDate() : date);

			payReport.add(response);
			// return response;

		}

		return payReport;
	}

	@Override
	public ByteArrayInputStream getCustRmPerfReport(SearchRequest request, String filename) {
		List<ReportCustomerRmPerformance> custRm = new ArrayList<ReportCustomerRmPerformance>();
		List<Tuple> performanceList;
		if (request.getUserId() != null && !request.getUserId().equals("")) {
			performanceList = employeeRepository.getCustRmReportByEmpCode(request.getDateFrom(),
					java.sql.Date.valueOf(LocalDate.parse(request.getDateTo()).plusDays(1)),
					request.getUserId());
		} else {
			performanceList = employeeRepository.getCustRmReport(request.getDateFrom(), 
					java.sql.Date.valueOf(LocalDate.parse(request.getDateTo()).plusDays(1)));
		}
		for (Tuple report : performanceList) {
			BigInteger b1;
			BigDecimal b2;
			 b1 = BigInteger.valueOf(0);
			 b2 = BigDecimal.valueOf(0);
			ReportCustomerRmPerformance response = new ReportCustomerRmPerformance();
			response.setCountry((String) report.get("country") != null ? (String) report.get("country") : "");
			response.setFirst_Name(
					(String) report.get("emp_first_name") != null ? (String) report.get("emp_first_name") : "");
			response.setLast_Name(
					(String) report.get("emp_last_name") != null ? (String) report.get("emp_last_name") : "");
			
			if(report.get("customer_accounts") != null) {
				BigInteger bi1= (BigInteger) report.get("customer_accounts");
				int ccCount = bi1.intValue();
				response.setCustomer_accounts(ccCount);
			}else {
				response.setCustomer_accounts(0);
			}
			if(report.get("trxn_count") != null) {
				BigInteger bi2= (BigInteger) report.get("trxn_count");
				int ccAmtCount = bi2.intValue();
				response.setTrxn_Count(ccAmtCount);
			}else {
				response.setTrxn_Count(0);
			}
			
//			response.setCustomer_accounts(
//					(BigInteger) report.get("customer_accounts") != null ? (BigInteger) report.get("customer_accounts")
//							: b1);
//			response.setTrxn_Count((BigInteger) report.get("trxn_count") != null ? (BigInteger) report.get("trxn_count")
//					: b1);
			response.setCumulative_LC_Amount(
					(Double) report.get("cumulative_LC_Amount") != null ? (Double) report.get("cumulative_LC_Amount")
							: 0);
			
			if(report.get("trxn_Accepted") != null) {
				BigDecimal bd1= (BigDecimal) report.get("trxn_Accepted");
				double acceptedCount = bd1.doubleValue();
				response.setTrxn_Accepted(acceptedCount);
			}else {
				response.setTrxn_Accepted(0.0);
			}
			if(report.get("trxn_Rejected") != null) {
				BigDecimal bd2= (BigDecimal) report.get("trxn_Rejected");
				double rejectedCount = bd2.doubleValue();
				response.setTrxn_Rejected(rejectedCount);
			}else {
				response.setTrxn_Rejected(0.0);
			}
			if(report.get("trxn_Expired") != null) {
				BigDecimal bd3= (BigDecimal) report.get("trxn_Expired");
				double expiredCount = bd3.doubleValue();
				response.setTrxn_Expired(expiredCount);
			}else {
				response.setTrxn_Expired(0.0);
			}
			
			
//			response.setTrxn_Accepted(
//					(BigDecimal) report.get("trxn_Accepted") != null ? (BigDecimal) report.get("trxn_Accepted")
//							: b2);
//			response.setTrxn_Rejected(
//					(BigDecimal) report.get("trxn_Rejected") != null ? (BigDecimal) report.get("trxn_Rejected")
//							: b2);
//			response.setTrxn_Expired(
//					(BigDecimal) report.get("trxn_Expired") != null ? (BigDecimal) report.get("trxn_Expired")
//							: b2);

			custRm.add(response);
			System.out.println(response);
		}
		System.out.println(custRm);
		return GenericExcelWriter.writeToExcel(filename, custRm);
	}

	@Override
	public ByteArrayInputStream getBankRmPerfReport(SearchRequest request, String filename) {
		List<ReportBankRmPerformance> bankRm = new ArrayList<ReportBankRmPerformance>();
		List<Tuple> performanceList;
		if(request.getUserId()!=null && !request.getUserId().equals("")) {
			performanceList = employeeRepository.getBankRmReportByEmpCode(request.getDateFrom(),
					java.sql.Date.valueOf(LocalDate.parse(request.getDateTo()).plusDays(1)),request.getUserId());
		}else {
			performanceList = employeeRepository.getBankRmReport(request.getDateFrom(),
					java.sql.Date.valueOf(LocalDate.parse(request.getDateTo()).plusDays(1)));
		}
		 
		for (Tuple report : performanceList) {
			BigInteger b1;
			BigDecimal b2;
			 b1 = BigInteger.valueOf(0);
			 b2 = BigDecimal.valueOf(0);
			
			ReportBankRmPerformance response = new ReportBankRmPerformance();
			response.setCountry((String) report.get("country") != null ? (String) report.get("country") : "");
			response.setFirst_Name(
					(String) report.get("emp_first_name") != null ? (String) report.get("emp_first_name") : "");
			response.setLast_Name(
					(String) report.get("emp_last_name") != null ? (String) report.get("emp_last_name") : "");
			
			if(report.get("customer_accounts") != null) {
				BigInteger bi1= (BigInteger) report.get("customer_accounts");
				int ccCount = bi1.intValue();
				response.setBank_As_Customer_accounts(ccCount);
			}else {
				response.setBank_As_Customer_accounts(0);
			}
			if(report.get("trxn_count") != null) {
				BigInteger bi2= (BigInteger) report.get("trxn_count");
				int ccAmtCount = bi2.intValue();
				response.setTrxn_Count(ccAmtCount);
			}else {
				response.setTrxn_Count(0);
			}
//			response.setBank_As_Customer_accounts(
//					(BigInteger) report.get("customer_accounts") != null ? (BigInteger) report.get("customer_accounts")
//							: b1);
//			response.setTrxn_Count((BigInteger) report.get("trxn_count") != null ? (BigInteger) report.get("trxn_count")
//					: b1);
			response.setCumulative_LC_Amount(
					(Double) report.get("cumulative_LC_Amount") != null ? (Double) report.get("cumulative_LC_Amount")
							: 0);
		
			
			if(report.get("trxn_Accepted") != null) {
				BigDecimal bd1= (BigDecimal) report.get("trxn_Accepted");
				double acceptedCount = bd1.doubleValue();
				response.setTrxn_Accepted(acceptedCount);
			}else {
				response.setTrxn_Accepted(0.0);
			}
			if(report.get("trxn_Rejected") != null) {
				BigDecimal bd2= (BigDecimal) report.get("trxn_Rejected");
				double rejectedCount = bd2.doubleValue();
				response.setTrxn_Rejected(rejectedCount);
			}else {
				response.setTrxn_Rejected(0.0);
			}
			if(report.get("trxn_Expired") != null) {
				BigDecimal bd3= (BigDecimal) report.get("trxn_Expired");
				double expiredCount = bd3.doubleValue();
				response.setTrxn_Expired(expiredCount);
			}else {
				response.setTrxn_Expired(0.0);
			}
//			response.setTrxn_Accepted(
//				(BigDecimal) report.get("trxn_Accepted") != null ? (BigDecimal) report.get("trxn_Accepted")
//							: b2);
//			response.setTrxn_Rejected(
//					(BigDecimal) report.get("trxn_Rejected") != null ? (BigDecimal) report.get("trxn_Rejected")
//							: b2);
//			response.setTrxn_Expired(
//					(BigDecimal) report.get("trxn_Expired") != null ? (BigDecimal) report.get("trxn_Expired")
//							: b2);
			
			bankRm.add(response);
			 System.out.println(response);
		}
		 System.out.println(bankRm);
		
		return GenericExcelWriter.writeToExcel(filename, bankRm);
	}

	@Override
	public ByteArrayInputStream getBankRmPerfUwReport(SearchRequest request, String filename) {
		List<ReportBankRmUwPerformance> uwPerfReport = new ArrayList<ReportBankRmUwPerformance>();
		List<Tuple> reports;
		if(request.getUserId()!=null && !request.getUserId().equals("")) {
			reports = employeeRepository.getBankRmUwReportbyEmpCode(request.getDateFrom(),
					java.sql.Date.valueOf(LocalDate.parse(request.getDateTo()).plusDays(1)),request.getUserId());
		}else {
			reports = employeeRepository.getBankRmUwReport(request.getDateFrom(),
					java.sql.Date.valueOf(LocalDate.parse(request.getDateTo()).plusDays(1)));
		}
		 
		for (Tuple report : reports) {
			ReportBankRmUwPerformance response = new ReportBankRmUwPerformance();
			BigInteger b1;
			BigDecimal b2;
			 b1 = BigInteger.valueOf(0);
			 b2 = BigDecimal.valueOf(0);
		       
			response.setCountry((String) report.get("country") != null ? (String) report.get("country") : "");
			response.setFirst_Name((String) report.get("emp_first_name") != null ? (String) report.get("emp_first_name") : "");
			response.setLast_Name((String) report.get("emp_last_name") != null ? (String) report.get("emp_last_name") : "");
//			response.setBank_Accounts((BigInteger) report.get("customer_accounts") != null ? (BigInteger) report.get("customer_accounts") : b1);
//			response.setQuote_Count((BigInteger) report.get("trxn_count") != null ? (BigInteger) report.get("trxn_count") : b1);
			if(report.get("customer_accounts") != null) {
				BigInteger bi11= (BigInteger) report.get("customer_accounts");
				int ccCount = bi11.intValue();
				response.setBank_Accounts(ccCount);
			}else {
				response.setBank_Accounts(0);
			}
			if(report.get("trxn_count") != null) {
				BigInteger bi12= (BigInteger) report.get("trxn_count");
				int ccAmtCount = bi12.intValue();
				response.setQuote_Count(ccAmtCount);
			}else {
				response.setQuote_Count(0);
			}
			
			response.setCumulative_Quote_Amount((Double) report.get("cumulative_LC_Amount") != null ? (Double) report.get("cumulative_LC_Amount") : 0);
//			response.setAccepted_Quote((BigDecimal) report.get("trxn_Accepted") != null ? (BigDecimal) report.get("trxn_Accepted") : b2);
//			response.setRejected_Quote((BigDecimal) report.get("trxn_Rejected") != null ? (BigDecimal) report.get("trxn_Rejected") : b2);
//			response.setExpired_Quote((BigDecimal) report.get("trxn_Expired") != null ? (BigDecimal) report.get("trxn_Expired") : b2);
			if(report.get("trxn_Accepted") != null) {
				BigDecimal bd1= (BigDecimal) report.get("trxn_Accepted");
				double acceptedCount = bd1.doubleValue();
				response.setAccepted_Quote(acceptedCount);
			}else {
				response.setAccepted_Quote(0.0);
			}
			if(report.get("trxn_Rejected") != null) {
				BigDecimal bd2= (BigDecimal) report.get("trxn_Rejected");
				double rejectedCount = bd2.doubleValue();
				response.setRejected_Quote(rejectedCount);
			}else {
				response.setRejected_Quote(0.0);
			}
			if(report.get("trxn_Expired") != null) {
				BigDecimal bd3= (BigDecimal) report.get("trxn_Expired");
				double expiredCount = bd3.doubleValue();
				response.setExpired_Quote(expiredCount);
			}else {
				response.setExpired_Quote(0.0);
			}
			uwPerfReport.add(response);
		}
		return GenericExcelWriter.writeToExcel(filename, uwPerfReport);
	}

	@Override
	public ByteArrayInputStream getAllCustomerTransactionDetails(SearchRequest request, String filename) {
		List<ReportCustomerTransaction> custTransaction = new ArrayList<ReportCustomerTransaction>();

		NimaiMCustomer custDetails = new NimaiMCustomer();
		List<Tuple> reports = customerRepository.getCustomerTransactionReportByDates(request.getDateFrom(),
				request.getDateTo());

		for (Tuple report : reports) {
			ReportCustomerTransaction response = new ReportCustomerTransaction();
			response.setUser_ID((String) report.get("user_id") != null ? (String) report.get("user_id") : "");
			try {
				custDetails = customerRepository.getOne((String) report.get("user_id"));
				response.setUser_Type(custDetails.getSubscriberType() != null ? custDetails.getSubscriberType() : "");
				response.setMobile(custDetails.getMobileNumber() != null ? custDetails.getMobileNumber() : "");
				response.setEmail(custDetails.getEmailAddress() != null ? custDetails.getEmailAddress() : "");
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Unfind userId===:" + (String) report.get("user_id"));
				continue;
			}

			response.setDate_3_Time(
					(java.util.Date) report.get("inserted_date") != null ? (java.util.Date) report.get("inserted_date")
							: date);
			response.setTrxn_ID(
					(String) report.get("transaction_id") != null ? (String) report.get("transaction_id") : "");
			response.setApplicant(
					(String) report.get("applicant_name") != null ? (String) report.get("applicant_name") : "");
			response.setCountry(
					(String) report.get("applicant_country") != null ? (String) report.get("applicant_country") : "");
			response.setApplicant_Contact_Person((String) report.get("applicant_contact_person") != null ? (String) report.get("applicant_contact_person")
					: "");
	response.setApplicant_contact_Person_Email((String) report.get("applicant_contact_person_email") != null ? (String) report.get("applicant_contact_person_email")
			: "");
			response.setBeneficiary((String) report.get("bene_name") != null ? (String) report.get("bene_name") : "");
			response.setB_Country(
					(String) report.get("bene_country") != null ? (String) report.get("bene_country") : "");
			response.setContact_Person(
					(String) report.get("bene_contact_person") != null ? (String) report.get("bene_contact_person")
							: "");
			response.setContact_Persons_Email((String) report.get("bene_contact_person_email") != null
					? (String) report.get("bene_contact_person_email")
					: "");
			response.setBank_Country(
					(String) report.get("bene_bank_country") != null ? (String) report.get("bene_bank_country") : "");
			response.setBank_swift_Code(
					(String) report.get("bene_swift_code") != null ? (String) report.get("bene_swift_code") : "");
			response.setBank_Name(
					(String) report.get("bene_bank_name") != null ? (String) report.get("bene_bank_name") : "");
			response.setiB(
					(String) report.get("lc_issuance_bank") != null ? (String) report.get("lc_issuance_bank") : "");
			response.setBranch(
					(String) report.get("lc_issuance_branch") != null ? (String) report.get("lc_issuance_branch") : "");
			response.setSwift_Code((String) report.get("swift_code") != null ? (String) report.get("swift_code") : "");
			response.setO_Country(
					(String) report.get("lc_issuance_country") != null ? (String) report.get("lc_issuance_country")
							: "");
			response.setRequirement(
					(String) report.get("requirement_type") != null ? (String) report.get("requirement_type") : "");
			response.setAmount((Double) report.get("lc_value") != null ? (Double) report.get("lc_value") : 0);
			response.setCcy((String) report.get("lc_currency") != null ? (String) report.get("lc_currency") : "");
			response.setIssuing_Date((java.util.Date) report.get("lc_issuing_date") != null
					? (java.util.Date) report.get("lc_issuing_date")
					: date);
			response.setLsd((java.util.Date) report.get("last_shipment_date") != null
					? (java.util.Date) report.get("last_shipment_date")
					: date);
			response.setNegotiation_Date((java.util.Date) report.get("negotiation_date") != null
					? (java.util.Date) report.get("negotiation_date")
					: date);
			response.setGoods((String) report.get("goods_type") != null ? (String) report.get("goods_type") : "");
			response.setUsance((Integer) report.get("usance_days") != null ? (Integer) report.get("usance_days") : 0);
			
			
			response.setOrignal_tenor_of_LC(
					(Integer) report.get("original_tenor_days") != null ? (Integer) report.get("original_tenor_days")
							: 0);
			response.setRefinancing_Period(
					(String) report.get("refinancing_period") != null ? (String) report.get("refinancing_period") : "");
			response.setLc_Maturity_Date((java.util.Date) report.get("lc_maturity_date") != null
					? (java.util.Date) report.get("lc_maturity_date")
					: date);
			response.setLc_Number((String) report.get("lc_number") != null ? (String) report.get("lc_number")
					: "");
			
			response.setBen_trxn_bank_Name(
					(String) report.get("last_bene_bank") != null ? (String) report.get("last_bene_bank") : "");
			response.setBen_trxn_swiftCode(
					(String) report.get("last_bene_swift_code") != null ? (String) report.get("last_bene_swift_code")
							: "");
			response.setBen_trxn_Country(
					(String) report.get("last_bank_country") != null ? (String) report.get("last_bank_country") : "");
			response.setPort_of_Loading(
					(String) report.get("loading_port") != null ? (String) report.get("loading_port") : "");
			response.setPort_Country(
					(String) report.get("loading_country") != null ? (String) report.get("loading_country") : "");
			response.setPort_of_Discharge(
					(String) report.get("discharge_port") != null ? (String) report.get("discharge_port") : "");
			response.setDischarge_Country(
					(String) report.get("discharge_country") != null ? (String) report.get("discharge_country") : "");
			response.setCharges_are_on(
					(String) report.get("charges_type") != null ? (String) report.get("charges_type") : "");
//			response.setValidity(((String) report.get("validity")).replace("-", "/").replaceAll(" 00:00:00", "") != null
			response.setValidity(report.get("validity") != null ? (String) report.get("validity") : "");
//			response.setPro_Forma_Uploaded(
//					(String) report.get("lc_pro_forma") != null ? (String) report.get("lc_pro_forma") : "");
			response.setQuotes_Received(
					(Integer) report.get("quotation_received") != null ? (Integer) report.get("quotation_received")
							: 0);

			custTransaction.add(response);
		}
		return GenericExcelWriter.writeToExcel(filename, custTransaction);
	}

	@Override
	public ByteArrayInputStream getAllCustomerTransactionDetailsByUserId(SearchRequest request, String filename)
			throws ParseException {
		// TODO Auto-generated method stub
		DateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
		java.util.Date fromDate = formatter.parse(request.getDateFrom());
		java.util.Date toDate = formatter.parse(request.getDateTo());

//			List<Tuple> cuTrDetails = transactionRepository.findByUsrIdDates(request.getUserId(), request.getDateFrom(),
//					request.getDateTo());
			   List<Tuple> cuTrDetails ;
				try {
					
					if (request.getUserId() != null && !request.getUserId().equals("")) {			
						 cuTrDetails = transactionRepository.findByUsrIdDates(request.getUserId(), request.getDateFrom(),
								java.sql.Date.valueOf(LocalDate.parse(request.getDateTo()).plusDays(1)));
					} else {
					     cuTrDetails = transactionRepository.findByDates(request.getDateFrom(),
								java.sql.Date.valueOf(LocalDate.parse(request.getDateTo()).plusDays(1)));
						
					}
			NimaiMCustomer custDetails = new NimaiMCustomer();
			System.out.println("==================" + cuTrDetails.size());
			List<ReportCustomerTransaction> custTransaction = new ArrayList<>();
			for (Tuple report : cuTrDetails) {
				ReportCustomerTransaction response = new ReportCustomerTransaction();
				response.setUser_ID((String) report.get("user_id") != null ? (String)  report.get("user_id") : "");
				try {
					custDetails = customerRepository.getOne((String) report.get("user_id"));
					response.setUser_Type(
							custDetails.getSubscriberType() != null ? custDetails.getSubscriberType() : "");
					response.setMobile(custDetails.getMobileNumber() != null ? custDetails.getMobileNumber() : "");
					response.setEmail(custDetails.getEmailAddress() != null ? custDetails.getEmailAddress() : "");
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("Unfind userId===:" + (String) report.get("user_id"));
					continue;
				}
				response.setDate_3_Time((java.util.Date) report.get("inserted_date") != null
						? (java.util.Date) report.get("inserted_date")
						: date);
				response.setTrxn_ID(
						(String) report.get("transaction_id") != null ? (String) report.get("transaction_id") : "");
				response.setApplicant(
						(String) report.get("applicant_name") != null ? (String) report.get("applicant_name") : "");
				response.setCountry(
						(String) report.get("applicant_country") != null ? (String) report.get("applicant_country")
								: "");
				response.setApplicant_Contact_Person((String) report.get("applicant_contact_person") != null ? (String) report.get("applicant_contact_person")
								: "");
				response.setApplicant_contact_Person_Email((String) report.get("applicant_contact_person_email") != null ? (String) report.get("applicant_contact_person_email")
						: "");
				
				response.setBeneficiary(
						(String) report.get("bene_name") != null ? (String) report.get("bene_name") : "");
				response.setB_Country(
						(String) report.get("bene_country") != null ? (String) report.get("bene_country") : "");
				response.setContact_Person(
						(String) report.get("bene_contact_person") != null ? (String) report.get("bene_contact_person")
								: "");
				response.setContact_Persons_Email((String) report.get("bene_contact_person_email") != null
						? (String) report.get("bene_contact_person_email")
						: "");
				response.setBank_Country(
						(String) report.get("bene_bank_country") != null ? (String) report.get("bene_bank_country")
								: "");
				response.setBank_swift_Code(
						(String) report.get("bene_swift_code") != null ? (String) report.get("bene_swift_code") : "");
				response.setBank_Name(
						(String) report.get("bene_bank_name") != null ? (String) report.get("bene_bank_name") : "");
				response.setiB(
						(String) report.get("lc_issuance_bank") != null ? (String) report.get("lc_issuance_bank") : "");
				response.setBranch(
						(String) report.get("lc_issuance_branch") != null ? (String) report.get("lc_issuance_branch")
								: "");
				response.setSwift_Code(
						(String) report.get("swift_code") != null ? (String) report.get("swift_code") : "");
				response.setO_Country(
						(String) report.get("lc_issuance_country") != null ? (String) report.get("lc_issuance_country")
								: "");
				response.setRequirement(
						(String) report.get("requirement_type") != null ? (String) report.get("requirement_type") : "");
				response.setAmount((Double) report.get("lc_value") != null ? (Double) report.get("lc_value") : 0);
				response.setCcy((String) report.get("lc_currency") != null ? (String) report.get("lc_currency") : "");
				response.setIssuing_Date((java.util.Date) report.get("lc_issuing_date") != null
						? (java.util.Date) report.get("lc_issuing_date")
						: date);
				response.setLsd((java.util.Date) report.get("last_shipment_date") != null
						? (java.util.Date) report.get("last_shipment_date")
						: date);
				response.setNegotiation_Date((java.util.Date) report.get("negotiation_date") != null
						? (java.util.Date) report.get("negotiation_date")
						: date);
				response.setGoods((String) report.get("goods_type") != null ? (String) report.get("goods_type") : "");
				response.setUsance(
						(Integer) report.get("usance_days") != null ? (Integer) report.get("usance_days") : 0);
				
				
				response.setOrignal_tenor_of_LC((Integer) report.get("original_tenor_days") != null
						? (Integer) report.get("original_tenor_days")
						: 0);
				response.setRefinancing_Period(
						(String) report.get("refinancing_period") != null ? (String) report.get("refinancing_period")
								: "");
				response.setLc_Maturity_Date((java.util.Date) report.get("lc_maturity_date") != null
						? (java.util.Date) report.get("lc_maturity_date")
						: date);
				response.setLc_Number((String) report.get("lc_number") != null ? (String) report.get("lc_number")
						: "");
				response.setBen_trxn_bank_Name(
						(String) report.get("last_bene_bank") != null ? (String) report.get("last_bene_bank") : "");
				response.setBen_trxn_swiftCode((String) report.get("last_bene_swift_code") != null
						? (String) report.get("last_bene_swift_code")
						: "");
				response.setBen_trxn_Country(
						(String) report.get("last_bank_country") != null ? (String) report.get("last_bank_country")
								: "");
				response.setPort_of_Loading(
						(String) report.get("loading_port") != null ? (String) report.get("loading_port") : "");
				response.setPort_Country(
						(String) report.get("loading_country") != null ? (String) report.get("loading_country") : "");
				response.setPort_of_Discharge(
						(String) report.get("discharge_port") != null ? (String) report.get("discharge_port") : "");
				response.setDischarge_Country(
						(String) report.get("discharge_country") != null ? (String) report.get("discharge_country")
								: "");
				response.setCharges_are_on(
						(String) report.get("charges_type") != null ? (String) report.get("charges_type") : "");
//				response.setValidity(((String) report.get("validity")).replace("-", "/").replaceAll(" 00:00:00", "") != null
				response.setValidity(report.get("validity") != null ? (String) report.get("validity") : "");

				response.setQuotes_Received(
						(Integer) report.get("quotation_received") != null ? (Integer) report.get("quotation_received")
								: 0);
				custTransaction.add(response);
			}

			return GenericExcelWriter.writeToExcel(filename, custTransaction);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
	
	public HashMap<String, Integer> calculateQuote(Integer quotationId,String transId, String tableType) {
		// TODO Auto-generated method stub
		// String transactionId="4028870370f1880f0170f1899aec0001";
		EntityManager entityManager = em.createEntityManager();
		try {
		StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("quote_calculation",
				NimaiLCMaster.class);
		// set parameters
		System.out.println("Calculating Quote for: "+quotationId);
		storedProcedure.registerStoredProcedureParameter("inp_quotation_id", Integer.class, ParameterMode.IN);
		storedProcedure.registerStoredProcedureParameter("inp_transaction_id", String.class, ParameterMode.IN);
		storedProcedure.registerStoredProcedureParameter("inp_table_type", String.class, ParameterMode.IN);
		storedProcedure.registerStoredProcedureParameter("negoDays", Integer.class, ParameterMode.OUT);
		storedProcedure.registerStoredProcedureParameter("expDays", Integer.class, ParameterMode.OUT);
		storedProcedure.registerStoredProcedureParameter("matDays", Integer.class, ParameterMode.OUT);
		storedProcedure.registerStoredProcedureParameter("confChgsNegot", Float.class, ParameterMode.OUT);
		storedProcedure.registerStoredProcedureParameter("confChgsMatur", Float.class, ParameterMode.OUT);
		storedProcedure.registerStoredProcedureParameter("sumOfQuote", Integer.class, ParameterMode.OUT);
		storedProcedure.registerStoredProcedureParameter("totalQuote", Integer.class, ParameterMode.OUT);
		storedProcedure.setParameter("inp_quotation_id", quotationId);
		storedProcedure.setParameter("inp_transaction_id", transId);
		storedProcedure.setParameter("inp_table_type", tableType);

		storedProcedure.execute();

		int negoDays = (int) storedProcedure.getOutputParameterValue("negoDays");
		int expDays = (int) storedProcedure.getOutputParameterValue("expDays");
		int matDays = (int) storedProcedure.getOutputParameterValue("matDays");
		float confChgsNegot = (float) storedProcedure.getOutputParameterValue("confChgsNegot");
		float confChgsMatur = (float) storedProcedure.getOutputParameterValue("confChgsMatur");
		int sumOfQuote = (int) storedProcedure.getOutputParameterValue("sumOfQuote");
		int totalQuote = (int) storedProcedure.getOutputParameterValue("totalQuote");

		System.out.println(negoDays + " " + expDays + " " + matDays + " " + sumOfQuote + " " + totalQuote);
		HashMap outputData = new HashMap();

		outputData.put("negotiationDays", negoDays);
		outputData.put("expiryDays", expDays);
		outputData.put("maturityDays", matDays);
		outputData.put("confChgsNegot", confChgsNegot);
		outputData.put("confChgsMatur", confChgsMatur);
		outputData.put("sumOfQuote", sumOfQuote);
		outputData.put("TotalQuote", totalQuote);
		

		return outputData;
	} catch (Exception e) {
		System.out.println(e);

	} finally {
		entityManager.close();

	}
	return null;

	}
	
	
}
