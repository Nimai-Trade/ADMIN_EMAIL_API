package com.nimai.splan.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nimai.splan.model.NimaiMCustomer;
import com.nimai.splan.repository.NimaiMCustomerRepository;
import com.nimai.splan.repository.NimaiSubscriptionDetailProcRepo;

@Service
@Transactional
public class GetCountServiceImpl implements GetCountService{
	
	@Autowired
	NimaiSubscriptionDetailProcRepo getcount; 
	
	@Autowired
	NimaiMCustomerRepository custRepo;
	
	@Autowired
	EntityManagerFactory emFactory;
	
	private static final Logger logger = LoggerFactory.getLogger(GetCountServiceImpl.class);
	
	@Override
	public HashMap<String, Object> getCount(String userid,String emailAddress) {
		EntityManager entityManager = emFactory.createEntityManager();
		
		try {
		StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("GET_DETAILS_FOR_DASHBOARD",NimaiMCustomer.class);
		storedProcedureQuery.registerStoredProcedureParameter("i_user_id", String.class, ParameterMode.IN);
		storedProcedureQuery.registerStoredProcedureParameter("i_email_id", String.class, ParameterMode.IN);
		storedProcedureQuery.registerStoredProcedureParameter("OUT_LC_COUNT", Integer.class, ParameterMode.OUT);
		storedProcedureQuery.registerStoredProcedureParameter("OUT_SUBSIDIARIES_COUNT",Integer.class, ParameterMode.OUT);
		storedProcedureQuery.registerStoredProcedureParameter("OUT_LC_Utilized_Count", Integer.class, ParameterMode.OUT);
		storedProcedureQuery.registerStoredProcedureParameter("OUT_Subsidiary_Utilized_Count", Integer.class, ParameterMode.OUT);
		storedProcedureQuery.registerStoredProcedureParameter("OUT_KYC_count", Integer.class, ParameterMode.OUT);
		storedProcedureQuery.registerStoredProcedureParameter("OUT_isRegister", boolean.class, ParameterMode.OUT);
		storedProcedureQuery.registerStoredProcedureParameter("OUT_isBDetailsFilled", boolean.class, ParameterMode.OUT);
		storedProcedureQuery.registerStoredProcedureParameter("OUT_isSPlanPurchased", String.class, ParameterMode.OUT);
		storedProcedureQuery.registerStoredProcedureParameter("OUT_ModeofPayment", String.class, ParameterMode.OUT);
		storedProcedureQuery.registerStoredProcedureParameter("OUT_paymentStatus", String.class, ParameterMode.OUT);
		storedProcedureQuery.registerStoredProcedureParameter("OUT_paymentDate", Date.class, ParameterMode.OUT);
		storedProcedureQuery.registerStoredProcedureParameter("OUT_paymentTransId", String.class, ParameterMode.OUT);
		storedProcedureQuery.registerStoredProcedureParameter("OUT_KycStatus", String.class, ParameterMode.OUT);
		storedProcedureQuery.registerStoredProcedureParameter("OUT_kycApprovalDate", Date.class, ParameterMode.OUT);
		storedProcedureQuery.registerStoredProcedureParameter("OUT_REFER_COUNT", Integer.class, ParameterMode.OUT);
		storedProcedureQuery.registerStoredProcedureParameter("OUT_SUBSCRIBER_TYPE", String.class, ParameterMode.OUT);
		storedProcedureQuery.registerStoredProcedureParameter("OUT_USERID", String.class, ParameterMode.OUT);
		storedProcedureQuery.registerStoredProcedureParameter("OUT_BANK_TYPE", String.class, ParameterMode.OUT);
		storedProcedureQuery.registerStoredProcedureParameter("OUT_FIRST_NAME", String.class, ParameterMode.OUT);
		storedProcedureQuery.registerStoredProcedureParameter("OUT_LAST_NAME", String.class, ParameterMode.OUT);
		storedProcedureQuery.registerStoredProcedureParameter("OUT_EMAIL_ADDRESS", String.class, ParameterMode.OUT);
		storedProcedureQuery.registerStoredProcedureParameter("OUT_MOBILE_NUMBER", String.class, ParameterMode.OUT);
		storedProcedureQuery.registerStoredProcedureParameter("OUT_COUNTRY_NAME", String.class, ParameterMode.OUT);
		storedProcedureQuery.registerStoredProcedureParameter("OUT_LANDLINE", String.class, ParameterMode.OUT);
		storedProcedureQuery.registerStoredProcedureParameter("OUT_DESIGNATION", String.class, ParameterMode.OUT);
		storedProcedureQuery.registerStoredProcedureParameter("OUT_COMPANY_NAME", String.class, ParameterMode.OUT);
		storedProcedureQuery.registerStoredProcedureParameter("OUT_BUSINESS_TYPE", String.class, ParameterMode.OUT);
		storedProcedureQuery.registerStoredProcedureParameter("OUT_BANK_NAME", String.class, ParameterMode.OUT);
		storedProcedureQuery.registerStoredProcedureParameter("OUT_BRANCH_NAME", String.class, ParameterMode.OUT);
		storedProcedureQuery.registerStoredProcedureParameter("OUT_SWIFT_CODE", String.class, ParameterMode.OUT);
		storedProcedureQuery.registerStoredProcedureParameter("OUT_TELEPHONE",String.class, ParameterMode.OUT);
		storedProcedureQuery.registerStoredProcedureParameter("OUT_MIN_VALUEOF_LC", String.class, ParameterMode.OUT);
		storedProcedureQuery.registerStoredProcedureParameter("OUT_REGISTRATION_TYPE", String.class, ParameterMode.OUT);
		storedProcedureQuery.registerStoredProcedureParameter("OUT_PROVINCENAME", String.class, ParameterMode.OUT);
		storedProcedureQuery.registerStoredProcedureParameter("OUT_ADDRESS1", String.class, ParameterMode.OUT);
		storedProcedureQuery.registerStoredProcedureParameter("OUT_ADDRESS2", String.class, ParameterMode.OUT);
		storedProcedureQuery.registerStoredProcedureParameter("OUT_ADDRESS3", String.class, ParameterMode.OUT);
		storedProcedureQuery.registerStoredProcedureParameter("OUT_CITY", String.class, ParameterMode.OUT);
		storedProcedureQuery.registerStoredProcedureParameter("OUT_PINCODE", String.class, ParameterMode.OUT);
		storedProcedureQuery.registerStoredProcedureParameter("OUT_REGISTERED_COUNTRY", String.class, ParameterMode.OUT);
		storedProcedureQuery.registerStoredProcedureParameter("OUT_IS_RMASSIGNED", boolean.class, ParameterMode.OUT);
		storedProcedureQuery.registerStoredProcedureParameter("OUT_RM_ID", String.class, ParameterMode.OUT);
		storedProcedureQuery.registerStoredProcedureParameter("OUT_DRAFT_COUNT", Integer.class, ParameterMode.OUT);
		storedProcedureQuery.registerStoredProcedureParameter("OUT_ACCOUNT_CREATED_DATE", Date.class, ParameterMode.OUT);
		storedProcedureQuery.registerStoredProcedureParameter("OUT_ACCOUNT_SOURCE", String.class, ParameterMode.OUT);
		storedProcedureQuery.registerStoredProcedureParameter("OUT_ACCOUNT_STATUS", String.class, ParameterMode.OUT);
		storedProcedureQuery.registerStoredProcedureParameter("OUT_ACCOUNT_TYPE", String.class, ParameterMode.OUT);
		storedProcedureQuery.registerStoredProcedureParameter("OUT_CURRENCY_CODE", String.class, ParameterMode.OUT);
		storedProcedureQuery.registerStoredProcedureParameter("OUT_SPL_SERIAL_NUMBER", Integer.class, ParameterMode.OUT);
		storedProcedureQuery.registerStoredProcedureParameter("OUT_SUBSCRIPTION_ID", String.class, ParameterMode.OUT);
		storedProcedureQuery.registerStoredProcedureParameter("OUT_SUBSCRIPTION_NAME", String.class, ParameterMode.OUT);
		storedProcedureQuery.registerStoredProcedureParameter("OUT_SUBSCRIPTION_AMOUNT", Integer.class, ParameterMode.OUT);
		storedProcedureQuery.registerStoredProcedureParameter("OUT_SPLAN_START_DATE", Date.class, ParameterMode.OUT);
		storedProcedureQuery.registerStoredProcedureParameter("OUT_SPLAN_END_DATE", Date.class, ParameterMode.OUT);
		storedProcedureQuery.registerStoredProcedureParameter("OUT_FLAG", Integer.class, ParameterMode.OUT);
		storedProcedureQuery.registerStoredProcedureParameter("OUT_RELATIONSHIP_MANAGER", String.class, ParameterMode.OUT);
		storedProcedureQuery.registerStoredProcedureParameter("OUT_CUSTOMER_SUPPORT", String.class, ParameterMode.OUT);
		storedProcedureQuery.registerStoredProcedureParameter("OUT_REMARK", String.class, ParameterMode.OUT);
		storedProcedureQuery.registerStoredProcedureParameter("OUT_STATUS", String.class, ParameterMode.OUT);
		storedProcedureQuery.registerStoredProcedureParameter("OUT_SUBSCRIPTION_VALIDITY", String.class, ParameterMode.OUT);
		storedProcedureQuery.registerStoredProcedureParameter("OUT_INSERTED_BY", String.class, ParameterMode.OUT);
		storedProcedureQuery.registerStoredProcedureParameter("OUT_MODIFIED_BY", String.class, ParameterMode.OUT);
		storedProcedureQuery.registerStoredProcedureParameter("OUT_is_val_applied", boolean.class, ParameterMode.OUT);
		storedProcedureQuery.registerStoredProcedureParameter("OUT_vas_amount", Integer.class, ParameterMode.OUT);
		storedProcedureQuery.registerStoredProcedureParameter("OUT_grand_amount", Integer.class, ParameterMode.OUT);
		storedProcedureQuery.registerStoredProcedureParameter("OUT_discount_id", Integer.class, ParameterMode.OUT);
		storedProcedureQuery.registerStoredProcedureParameter("OUT_discount_amount", Double.class, ParameterMode.OUT);
		storedProcedureQuery.registerStoredProcedureParameter("OUT_TNC_DATE", Date.class, ParameterMode.OUT);
		
		storedProcedureQuery.setParameter("i_user_id", userid);
        storedProcedureQuery.setParameter("i_email_id",emailAddress );
		storedProcedureQuery.execute();
		
		Integer OUT_LC_COUNT = (Integer) storedProcedureQuery.getOutputParameterValue("OUT_LC_COUNT");
		Integer OUT_SUBSIDIARIES_COUNT = (Integer) storedProcedureQuery.getOutputParameterValue("OUT_SUBSIDIARIES_COUNT");
		Integer OUT_LC_Utilized_Count = (Integer) storedProcedureQuery.getOutputParameterValue("OUT_LC_Utilized_Count");
		Integer OUT_Subsisiary_Utilized_Count = (Integer) storedProcedureQuery.getOutputParameterValue("OUT_Subsidiary_Utilized_Count");
		Integer OUT_KYC_count = (Integer) storedProcedureQuery.getOutputParameterValue("OUT_KYC_count");
		Boolean OUT_isRegister = (Boolean) storedProcedureQuery.getOutputParameterValue("OUT_isRegister");
		Boolean OUT_isBDetailsFilled = (Boolean) storedProcedureQuery.getOutputParameterValue("OUT_isBDetailsFilled");
		String OUT_isSPlanPurchased = (String) storedProcedureQuery.getOutputParameterValue("OUT_isSPlanPurchased");
		String OUT_ModeofPayment = (String) storedProcedureQuery.getOutputParameterValue("OUT_ModeofPayment");
		String OUT_paymentStatus = (String) storedProcedureQuery.getOutputParameterValue("OUT_paymentStatus");
		Date OUT_paymentDate = (Date) storedProcedureQuery.getOutputParameterValue("OUT_paymentDate");
		String OUT_paymentTransId = (String) storedProcedureQuery.getOutputParameterValue("OUT_paymentTransId");
		
		String OUT_KycStatus = (String) storedProcedureQuery.getOutputParameterValue("OUT_KycStatus");
		Date OUT_kycApprovalDate = (Date) storedProcedureQuery.getOutputParameterValue("OUT_kycApprovalDate");
        Integer OUT_REFER_COUNT = (Integer) storedProcedureQuery.getOutputParameterValue("OUT_REFER_COUNT");
        
        String OUT_SUBSCRIBER_TYPE = (String) storedProcedureQuery.getOutputParameterValue("OUT_SUBSCRIBER_TYPE");
        String OUT_USERID = (String) storedProcedureQuery.getOutputParameterValue("OUT_USERID");
        String OUT_BANK_TYPE = (String) storedProcedureQuery.getOutputParameterValue("OUT_BANK_TYPE");
        String OUT_FIRST_NAME = (String) storedProcedureQuery.getOutputParameterValue("OUT_FIRST_NAME");
        String OUT_LAST_NAME = (String) storedProcedureQuery.getOutputParameterValue("OUT_LAST_NAME");
        String OUT_EMAIL_ADDRESS = (String) storedProcedureQuery.getOutputParameterValue("OUT_EMAIL_ADDRESS");
        String OUT_MOBILE_NUMBER = (String) storedProcedureQuery.getOutputParameterValue("OUT_MOBILE_NUMBER");
        String OUT_COUNTRY_NAME = (String) storedProcedureQuery.getOutputParameterValue("OUT_COUNTRY_NAME");
        String OUT_LANDLINE = (String) storedProcedureQuery.getOutputParameterValue("OUT_LANDLINE");
        String OUT_DESIGNATION = (String) storedProcedureQuery.getOutputParameterValue("OUT_DESIGNATION");
        String OUT_COMPANY_NAME = (String) storedProcedureQuery.getOutputParameterValue("OUT_COMPANY_NAME");
        String OUT_BUSINESS_TYPE = (String) storedProcedureQuery.getOutputParameterValue("OUT_BUSINESS_TYPE");
        String OUT_BANK_NAME = (String) storedProcedureQuery.getOutputParameterValue("OUT_BANK_NAME");
        String OUT_BRANCH_NAME = (String) storedProcedureQuery.getOutputParameterValue("OUT_BRANCH_NAME");
        String OUT_SWIFT_CODE = (String) storedProcedureQuery.getOutputParameterValue("OUT_SWIFT_CODE");
        String OUT_TELEPHONE = (String) storedProcedureQuery.getOutputParameterValue("OUT_TELEPHONE");
        String OUT_MIN_VALUEOF_LC = (String) storedProcedureQuery.getOutputParameterValue("OUT_MIN_VALUEOF_LC");
        String OUT_REGISTRATION_TYPE = (String) storedProcedureQuery.getOutputParameterValue("OUT_REGISTRATION_TYPE");
        String OUT_PROVINCENAME = (String) storedProcedureQuery.getOutputParameterValue("OUT_PROVINCENAME");
        String OUT_ADDRESS1 = (String) storedProcedureQuery.getOutputParameterValue("OUT_ADDRESS1");
        String OUT_ADDRESS2 = (String) storedProcedureQuery.getOutputParameterValue("OUT_ADDRESS2");
        String OUT_ADDRESS3 = (String) storedProcedureQuery.getOutputParameterValue("OUT_ADDRESS3");
        String OUT_CITY = (String) storedProcedureQuery.getOutputParameterValue("OUT_CITY");
        String OUT_PINCODE = (String) storedProcedureQuery.getOutputParameterValue("OUT_PINCODE");
        String OUT_REGISTERED_COUNTRY = (String) storedProcedureQuery.getOutputParameterValue("OUT_REGISTERED_COUNTRY");
        Boolean OUT_IS_RMASSIGNED = (Boolean) storedProcedureQuery.getOutputParameterValue("OUT_IS_RMASSIGNED");
        String OUT_RM_ID = (String) storedProcedureQuery.getOutputParameterValue("OUT_RM_ID");
        Integer OUT_DRAFT_COUNT=(Integer) storedProcedureQuery.getOutputParameterValue("OUT_DRAFT_COUNT");
        Date OUT_ACCOUNT_CREATED_DATE = (Date) storedProcedureQuery.getOutputParameterValue("OUT_ACCOUNT_CREATED_DATE");
        String OUT_ACCOUNT_SOURCE = (String) storedProcedureQuery.getOutputParameterValue("OUT_ACCOUNT_SOURCE");
        String OUT_ACCOUNT_STATUS = (String) storedProcedureQuery.getOutputParameterValue("OUT_ACCOUNT_STATUS");
        String OUT_ACCOUNT_TYPE = (String) storedProcedureQuery.getOutputParameterValue("OUT_ACCOUNT_TYPE");
        String OUT_CURRENCY_CODE = (String) storedProcedureQuery.getOutputParameterValue("OUT_CURRENCY_CODE");
        Integer OUT_SPL_SERIAL_NUMBER = (Integer) storedProcedureQuery.getOutputParameterValue("OUT_SPL_SERIAL_NUMBER");
        String OUT_SUBSCRIPTION_ID = (String) storedProcedureQuery.getOutputParameterValue("OUT_SUBSCRIPTION_ID");
        String OUT_SUBSCRIPTION_NAME = (String) storedProcedureQuery.getOutputParameterValue("OUT_SUBSCRIPTION_NAME");
        Integer OUT_SUBSCRIPTION_AMOUNT = (Integer) storedProcedureQuery.getOutputParameterValue("OUT_SUBSCRIPTION_AMOUNT");
        Date OUT_SPLAN_START_DATE = (Date) storedProcedureQuery.getOutputParameterValue("OUT_SPLAN_START_DATE");
        Date OUT_SPLAN_END_DATE = (Date) storedProcedureQuery.getOutputParameterValue("OUT_SPLAN_END_DATE");
        Integer OUT_FLAG = (Integer) storedProcedureQuery.getOutputParameterValue("OUT_FLAG");
        String OUT_RELATIONSHIP_MANAGER = (String) storedProcedureQuery.getOutputParameterValue("OUT_RELATIONSHIP_MANAGER");
        String OUT_CUSTOMER_SUPPORT = (String) storedProcedureQuery.getOutputParameterValue("OUT_CUSTOMER_SUPPORT");
        String OUT_REMARK= (String) storedProcedureQuery.getOutputParameterValue("OUT_REMARK");
        String OUT_STATUS = (String) storedProcedureQuery.getOutputParameterValue("OUT_STATUS");
        String OUT_SUBSCRIPTION_VALIDITY = (String) storedProcedureQuery.getOutputParameterValue("OUT_SUBSCRIPTION_VALIDITY");
        String OUT_INSERTED_BY= (String) storedProcedureQuery.getOutputParameterValue("OUT_INSERTED_BY");
        String OUT_MODIFIED_BY= (String) storedProcedureQuery.getOutputParameterValue("OUT_MODIFIED_BY");
        Boolean OUT_is_vas_applied= (Boolean) storedProcedureQuery.getOutputParameterValue("OUT_is_val_applied");
        Integer OUT_vas_amount= (Integer) storedProcedureQuery.getOutputParameterValue("OUT_vas_amount");
        Integer OUT_grand_amount= (Integer) storedProcedureQuery.getOutputParameterValue("OUT_grand_amount");
        Integer OUT_discount_id= (Integer) storedProcedureQuery.getOutputParameterValue("OUT_discount_id");
        Double OUT_discount_amount= (Double) storedProcedureQuery.getOutputParameterValue("OUT_discount_amount");
        Date OUT_TNC_DATE = (Date) storedProcedureQuery.getOutputParameterValue("OUT_TNC_DATE");
        
        
		HashMap<String,Object> outputdata1=new HashMap<String,Object>();
		outputdata1.put("lc_count", OUT_LC_COUNT);
		outputdata1.put("subsidiries",OUT_SUBSIDIARIES_COUNT);
		outputdata1.put("lcutilizedcount",OUT_LC_Utilized_Count);
		outputdata1.put("subuticount",OUT_Subsisiary_Utilized_Count);
		outputdata1.put("kyccount",OUT_KYC_count);
		outputdata1.put("isreg",OUT_isRegister);
		outputdata1.put("isbdetailfilled",OUT_isBDetailsFilled);
		outputdata1.put("issplanpurchased",OUT_isSPlanPurchased);
		outputdata1.put("modeofpayment",OUT_ModeofPayment);
		outputdata1.put("paymentstatus",OUT_paymentStatus);
		outputdata1.put("paymentdate",OUT_paymentDate);
		outputdata1.put("paymentTransId",OUT_paymentTransId);
		outputdata1.put("kycstatus",OUT_KycStatus);
		outputdata1.put("kycapprovaldate",OUT_kycApprovalDate);
		outputdata1.put("refercount",OUT_REFER_COUNT);
		outputdata1.put("subscribertype",OUT_SUBSCRIBER_TYPE);
		outputdata1.put("userId",OUT_USERID);
		outputdata1.put("banktype",OUT_BANK_TYPE);
		outputdata1.put("firstname",OUT_FIRST_NAME);
		outputdata1.put("lastname",OUT_LAST_NAME);
		outputdata1.put("emailaddress",OUT_EMAIL_ADDRESS);
		outputdata1.put("mobilenumber",OUT_MOBILE_NUMBER);
		outputdata1.put("countryname",OUT_COUNTRY_NAME);
		outputdata1.put("landline",OUT_LANDLINE);
		outputdata1.put("designation",OUT_DESIGNATION);
		outputdata1.put("companyname",OUT_COMPANY_NAME);
		outputdata1.put("businesstype",OUT_BUSINESS_TYPE);
		outputdata1.put("bankname",OUT_BANK_NAME);
		outputdata1.put("branchname",OUT_BRANCH_NAME);
		outputdata1.put("swiftcode",OUT_SWIFT_CODE);
		outputdata1.put("telephone",OUT_TELEPHONE);
		outputdata1.put("minvalueoflc",OUT_MIN_VALUEOF_LC);
		outputdata1.put("registrationtype",OUT_REGISTRATION_TYPE);
		outputdata1.put("provincename",OUT_PROVINCENAME);
		outputdata1.put("address1",OUT_ADDRESS1);
		outputdata1.put("address2",OUT_ADDRESS2);
		outputdata1.put("address3",OUT_ADDRESS3);
		outputdata1.put("city",OUT_CITY);
		outputdata1.put("pincode",OUT_PINCODE);
		outputdata1.put("registeredcountry",OUT_REGISTERED_COUNTRY);
		outputdata1.put("rmassigned",OUT_IS_RMASSIGNED);
		outputdata1.put("rmId",OUT_RM_ID);
		outputdata1.put("draftcount", OUT_DRAFT_COUNT);
		outputdata1.put("accountcreateddate", OUT_ACCOUNT_CREATED_DATE);
		outputdata1.put("accountsource", OUT_ACCOUNT_SOURCE);
		outputdata1.put("accountstatus", OUT_ACCOUNT_STATUS );
		outputdata1.put("accounttype", OUT_ACCOUNT_TYPE);
		outputdata1.put("currencycode", OUT_CURRENCY_CODE );
		outputdata1.put("splserialnumber", OUT_SPL_SERIAL_NUMBER);
		outputdata1.put("subscriptionid", OUT_SUBSCRIPTION_ID);
		outputdata1.put("subscriptionname", OUT_SUBSCRIPTION_NAME);
		outputdata1.put("subscriptionamount", OUT_SUBSCRIPTION_AMOUNT);
		outputdata1.put("splanstartdate", OUT_SPLAN_START_DATE);
		outputdata1.put("splanenddate", OUT_SPLAN_END_DATE);
		outputdata1.put("flag", OUT_FLAG);
		outputdata1.put("relationshipmanager", OUT_RELATIONSHIP_MANAGER);
		outputdata1.put("customersupport", OUT_CUSTOMER_SUPPORT);
		outputdata1.put("remark", OUT_REMARK);
		outputdata1.put("status", OUT_STATUS);
		outputdata1.put("subscriptionvaldity", OUT_SUBSCRIPTION_VALIDITY);
		outputdata1.put("insertedby", OUT_INSERTED_BY);
		outputdata1.put("modifiedby", OUT_MODIFIED_BY);
		outputdata1.put("isvasapplied", OUT_is_vas_applied);
		outputdata1.put("vasamount", OUT_vas_amount);
		outputdata1.put("grandamount", OUT_grand_amount);
		outputdata1.put("discountId", OUT_discount_id);
		outputdata1.put("discountAmount", OUT_discount_amount);
		outputdata1.put("tncDate", OUT_TNC_DATE);
		return outputdata1;
	}
    catch(Exception e) {
	 System.out.println("Exception Occur:"+e);
	 }
		
		  finally { entityManager.close();
		  
		  }
		return null;
		
		
}

	@Override
	public String getAccountType(String userid) {
		// TODO Auto-generated method stub
		return custRepo.getAccountTypeByUserId(userid);
	}

	@Override
	public String getAccountSource(String userid) {
		// TODO Auto-generated method stub
		return custRepo.getAccountSourceByUserId(userid);
	}
	
	@Override
	public NimaiMCustomer getEmailAddress(String userid) {
		// TODO Auto-generated method stub
		return custRepo.findCustomerDetailsByUserId(userid);
	}
}
