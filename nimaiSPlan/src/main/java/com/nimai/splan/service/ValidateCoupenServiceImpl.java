package com.nimai.splan.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nimai.splan.NimaiSPlanApplication;
import com.nimai.splan.model.NimaiCustomerSubscriptionGrandAmount;
import com.nimai.splan.model.NimaiMCustomer;
import com.nimai.splan.model.NimaiMMCoupen;
import com.nimai.splan.payload.GenericResponse;
import com.nimai.splan.repository.NimaiCustomerGrandAmountRepo;
import com.nimai.splan.repository.NimaiMCustomerRepository;
import com.nimai.splan.repository.NimaiMMCoupenRepo;
import com.nimai.splan.repository.SubscriptionPlanRepository;

@Service
public class ValidateCoupenServiceImpl implements ValidateCoupenService{
	
	private static final Logger logger = LoggerFactory.getLogger(NimaiSPlanApplication.class);
	
	@Autowired
	EntityManagerFactory emFactory;
	
	@Autowired
	NimaiCustomerGrandAmountRepo nimaiCustomerGrandAmtRepository;
	
	@Autowired
	NimaiMMCoupenRepo nimaimmRepo;
	
	@Autowired
	NimaiMCustomerRepository userRepository;
	
	@Autowired
	SubscriptionPlanRepository subscriptionDetailsRepository;

	@Override
	public HashMap<String, String> validateCoupen(String coupenId,String countryName,String subscriptionPlan,String coupenfor) {
		EntityManager entityManager = emFactory.createEntityManager();
		StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("VALIDATE_COUPEN",NimaiMMCoupen.class);

		storedProcedureQuery.registerStoredProcedureParameter("inp_coupen_id", String.class, ParameterMode.IN);
		storedProcedureQuery.registerStoredProcedureParameter("inp_country_name", String.class, ParameterMode.IN);
		storedProcedureQuery.registerStoredProcedureParameter("inp_subsciption_plan", String.class, ParameterMode.IN);
		storedProcedureQuery.registerStoredProcedureParameter("inp_user_type", String.class, ParameterMode.IN);
		storedProcedureQuery.registerStoredProcedureParameter("out_coupen_status", String.class, ParameterMode.OUT);
		storedProcedureQuery.registerStoredProcedureParameter("out_total_amount", float.class, ParameterMode.OUT);
		
		storedProcedureQuery.setParameter("inp_coupen_id", coupenId);
		storedProcedureQuery.setParameter("inp_country_name",countryName );
		storedProcedureQuery.setParameter("inp_subsciption_plan",subscriptionPlan );
		storedProcedureQuery.setParameter("inp_user_type",coupenfor );
		storedProcedureQuery.execute();
		
		String out_coupen_status = (String) storedProcedureQuery.getOutputParameterValue("out_coupen_status");
		Float out_total_amount = (Float) storedProcedureQuery.getOutputParameterValue("out_total_amount");
		
		HashMap<String, String> outputdata=new HashMap<String, String>();
		outputdata.put("coupenstatus", out_coupen_status);
		outputdata.put("totalamount", out_total_amount.toString());
		return outputdata;
		
	}

	@Override
	public ResponseEntity<?> applyForCoupen(String userId, String subscriptionId, String subscriptionName,String coupenCode,Integer subscriptionAmount) {
		// TODO Auto-generated method stub
		GenericResponse response = new GenericResponse<>();
		
		String coupenFor=null;
		if(userId.substring(0, 2).equalsIgnoreCase("BA"))
			coupenFor="Bank";
		if(userId.substring(0, 2).equalsIgnoreCase("BC"))
			coupenFor="Bank as Customer";
		if(userId.substring(0, 2).equalsIgnoreCase("CU"))
			coupenFor="Customer";
		try
		{
			//String businesscountry="";
			Optional<NimaiMCustomer> custDet=userRepository.findByUserId(userId);
			String businessCountry=custDet.get().getRegistredCountry();
			System.out.println("Business Country: "+businessCountry);
			String promoCode="",emailID="";
			String couponType="";
			int couponCount;
			Integer leadID=nimaimmRepo.getLeadId(userId);
			
			//FIEO Changes - Adil - 23 AUG 2021
			logger.info("Lead ID: "+leadID);
			System.out.println("Lead ID: "+leadID);
			emailID=nimaimmRepo.getEmailId(userId);
			promoCode=nimaimmRepo.getPromoCode(emailID);
			if(promoCode==null)
				promoCode="";
			
			System.out.println("emailID: "+emailID);
			System.out.println("promoCode: "+promoCode);
			if(leadID>0 && !promoCode.equalsIgnoreCase(""))
			{
				System.out.println("In lead section of coupon");
				couponType= nimaimmRepo.getCouponTypeByCoupenCode(coupenCode);
				System.out.println("CouponType: "+couponType);
			}
			else
				couponType= nimaimmRepo.getCouponTypeByCoupenCodeSubscriptionNameStatusAndConsumption(coupenCode,subscriptionName,coupenFor,businessCountry);
			System.out.println("Coupon Type: "+couponType);
			logger.info("Coupon Type: "+couponType);
			logger.info("Coupon For: "+coupenFor);
			logger.info("Coupon Code: "+coupenCode);
			logger.info("Subscription Name: "+subscriptionName);
			logger.info("Subscription Id: "+subscriptionId);
			couponCount=nimaimmRepo.getCountForValidCoupon(coupenCode);
			System.out.println("Coupon count :"+couponCount);
			if(couponType.equalsIgnoreCase("nc") && couponCount>0)
			{
					return proceedForDiscountProcess(userId,subscriptionId,coupenCode,businessCountry,subscriptionName,coupenFor,subscriptionAmount,leadID);
			}
			else if(couponType.equalsIgnoreCase("pc") && couponCount>0)
			{
				List validUser=nimaimmRepo.getDataByUserIdAndStatus(userId);
				if(validUser.size()==0)
				{
					response.setStatus("Failure");
					response.setErrCode("Coupon is Invalid");
					return new ResponseEntity<Object>(response, HttpStatus.OK);
				}
				else
				{
					return proceedForDiscountProcess(userId,subscriptionId,coupenCode,businessCountry,subscriptionName,coupenFor,subscriptionAmount,0);
				}
			}
			else
			{
				response.setStatus("Failure");
				response.setErrCode("Coupon is Invalid");
				return new ResponseEntity<Object>(response, HttpStatus.OK);
			}
			
			
		}
		catch(Exception e)
		{
			response.setStatus("Failure");
			response.setErrCode("Coupon is Invalid");
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
		
	}

	@Override
	public ResponseEntity<?> removeFromCoupen(String userId,int discountId,NimaiCustomerSubscriptionGrandAmount ncsga) {
		// TODO Auto-generated method stub
		GenericResponse response = new GenericResponse<>();
		
		try
		{
			nimaimmRepo.decrementConsumption(discountId);
			nimaiCustomerGrandAmtRepository.deleteById(ncsga.getId());
			response.setStatus("Coupon Removed Successfully");
			response.setData("0");
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
		catch(Exception e)
		{
			response.setStatus("Failure");
			response.setErrCode("Unable to remove Coupon");
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
		
	}
	
	private ResponseEntity<?> proceedForDiscountProcess(String userId,String subscriptionId,String coupenCode, String businessCountry, String subscriptionName,
			String coupenFor,Integer subscriptionAmount,Integer leadID)
	{
		GenericResponse response = new GenericResponse<>();
		Date today=new Date();
		Double finalAmount,discountId;;
		String discountType,emailID="",promoCode="";
		
		emailID=nimaimmRepo.getEmailId(userId);
		promoCode=nimaimmRepo.getPromoCode(emailID);
		if(promoCode==null)
			promoCode="";
		
		if(leadID>0 && !promoCode.equalsIgnoreCase(""))
		{
			discountType= nimaimmRepo.getDiscountTypeByCoupenCodeStatusAndConsumption(coupenCode);
			discountId=nimaimmRepo.getDiscountIdByCouponCode(coupenCode);
		}
		else
		{
			discountType= nimaimmRepo.getDiscountTypeByCoupenCodeSubscriptionNameStatusAndConsumption(coupenCode,subscriptionName,coupenFor,businessCountry);
			discountId=nimaimmRepo.getDiscountId(coupenCode,businessCountry,subscriptionName,coupenFor);
		}
		System.out.println("Current Date: "+today);
		System.out.println("Discount Type: "+discountType);
		
		Integer subsAmount=subscriptionDetailsRepository.getSubscriptionAmt(subscriptionId);
		
		if(discountType.equalsIgnoreCase("Fixed"))
		{
			Double discAmount=nimaimmRepo.getDiscAmountByDiscId(discountId);
			//Double discountedAmount=nimaimmRepo.getAmountByCoupenCode(coupenFor,coupenCode,businessCountry,subscriptionName);
			Double discountedAmount=nimaimmRepo.getAmountByDiscId(discountId);
			finalAmount=subscriptionAmount-discountedAmount;
			if(finalAmount>=0)
			{
				System.out.println("Original Diff: "+(subsAmount-discAmount));
				logger.info("Original Diff: "+(subsAmount-discAmount));
				System.out.println("final Diff: "+finalAmount);
				logger.info("final Diff: "+finalAmount);
				//if(Double.compare((subsAmount-discAmount),finalAmount)==0 && Double.compare(subscriptionAmount,subsAmount)==0)
				//{	
				logger.info("Updating Consumption for discount id: "+discountId);
					nimaimmRepo.updateConsumption(discountId);
					NimaiCustomerSubscriptionGrandAmount ncsgm=new NimaiCustomerSubscriptionGrandAmount();
					ncsgm.setUserId(userId);
					ncsgm.setGrandAmount(finalAmount);
					ncsgm.setDiscountApplied("Yes");
					ncsgm.setInsertedDate(new Date());
					nimaiCustomerGrandAmtRepository.save(ncsgm);
					HashMap<String,Double> data=new HashMap<>();
					data.put("discountId", discountId);
					data.put("discount", discountedAmount);
					data.put("grandAmount", finalAmount);
					subscriptionDetailsRepository.updateDiscountId(userId,discountId);
					response.setStatus("Coupon Applied Successfully");
					response.setData(data);
					return new ResponseEntity<Object>(response, HttpStatus.OK);
				/*}
				else
				{
					response.setStatus("Failure");
					response.setData("OOPs! Something went wrong. Please Apply Coupon again");
					return new ResponseEntity<Object>(response, HttpStatus.OK);
				}*/
			}
			else
			{
				response.setStatus("Failure");
				response.setData("0");
				return new ResponseEntity<Object>(response, HttpStatus.OK);
			}
		}
		else if(discountType.equalsIgnoreCase("Percentage") || discountType.equalsIgnoreCase("%"))
		{
			Double discountPercentage=nimaimmRepo.getDiscPercByDiscountId(discountId);
			Double maxDiscount=nimaimmRepo.getMaxDiscByDiscountId(discountId);
			Double subsValue=subscriptionAmount*(discountPercentage/100);
			if(subsValue<=maxDiscount)
			{
				finalAmount=subscriptionAmount-subsValue;
				if(finalAmount>=0)
				{
					logger.info("Updating Consumption for discount id: "+discountId);
					nimaimmRepo.updateConsumption(discountId);
					NimaiCustomerSubscriptionGrandAmount ncsgm=new NimaiCustomerSubscriptionGrandAmount();
					ncsgm.setUserId(userId);
					ncsgm.setGrandAmount(finalAmount);
					ncsgm.setDiscountApplied("Yes");
					ncsgm.setInsertedDate(new Date());
					nimaiCustomerGrandAmtRepository.save(ncsgm);
					HashMap<String,Double> data1=new HashMap<>();
					data1.put("discountId", discountId);
					data1.put("discount", subsValue);
					data1.put("grandAmount", finalAmount);
					//subscriptionDetailsRepository.updateDiscountId(userId,discountId);
					response.setStatus("Coupon Applied Successfully");
					response.setData(data1);
					return new ResponseEntity<Object>(response, HttpStatus.OK);
				}
				else
				{
					logger.info("final amount is -ve");
					response.setStatus("Failure");
					response.setData("0");
					return new ResponseEntity<Object>(response, HttpStatus.OK);
				}
			}
			else
			{
				finalAmount=subscriptionAmount-maxDiscount;
				if(finalAmount>=0)
				{
					logger.info("Updating Consumption for discount id: "+discountId);
					nimaimmRepo.updateConsumption(discountId);
					NimaiCustomerSubscriptionGrandAmount ncsgm=new NimaiCustomerSubscriptionGrandAmount();
					ncsgm.setUserId(userId);
					ncsgm.setGrandAmount(finalAmount);
					ncsgm.setDiscountApplied("Yes");
					ncsgm.setInsertedDate(new Date());
					nimaiCustomerGrandAmtRepository.save(ncsgm);
					HashMap<String,Double> data1=new HashMap<>();
					data1.put("discountId", discountId);
					data1.put("discount", maxDiscount);
					data1.put("grandAmount", finalAmount);
					//subscriptionDetailsRepository.updateDiscountId(userId,discountId);
					response.setStatus("Coupon Applied Successfully");
					response.setData(data1);
					return new ResponseEntity<Object>(response, HttpStatus.OK);
				}
				else
				{
					logger.info("final amount is -ve");
					response.setStatus("Failure");
					response.setData("0");
					return new ResponseEntity<Object>(response, HttpStatus.OK);
				}
			}
			
		}
		else
		{
			response.setStatus("Failure");
			response.setData("0");
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
			
	}
	
	@Override
	public HashMap<String,Double> discountCalculate(Double discountId,String subscriptionId)
	{
		logger.info("Discount Calculation");
		GenericResponse response = new GenericResponse<>();
		Date today=new Date();
		Double finalAmount;
		String discountType= nimaimmRepo.getDiscountTypeByDiscountId(discountId);
		System.out.println("Current Date: "+today);
		System.out.println("Discount Type: "+discountType);
		logger.info("Discount Type: "+discountType);
		logger.info("discount id: "+discountId);
		//Double discountId=nimaimmRepo.getDiscountId(coupenCode,businessCountry,subscriptionName,coupenFor);
		Integer subsAmount=subscriptionDetailsRepository.getSubscriptionAmt(subscriptionId);
		logger.info("Subscription Amount: "+subsAmount);
		if(discountType.equalsIgnoreCase("Fixed"))
		{
			Double discAmount=nimaimmRepo.getDiscAmountByDiscId(discountId);
			Double discountedAmount=nimaimmRepo.getAmountByDiscountId(discountId);
			finalAmount=subsAmount-discountedAmount;
			logger.info("Discount Amount: "+discAmount);
			logger.info("Discounted Amount: "+discountedAmount);
			logger.info("Final Amount: "+finalAmount);
			if(finalAmount>=0)
			{
				//return discountedAmount;
				HashMap<String,Double> data=new HashMap<>();
				data.put("discountId", discountId);
				data.put("discount", discountedAmount);
				data.put("grandAmount", finalAmount);
				return data;
			//	response.setStatus("Coupon Applied Successfully");
			//	response.setData(data);
			//	return new ResponseEntity<Object>(response, HttpStatus.OK);
			}
			else
			{
				HashMap<String,Double> data=new HashMap<>();
				data.put("discountId", discountId);
				data.put("discount", 0.0);
				data.put("grandAmount", finalAmount);
				return data;
			//	response.setStatus("Failure");
			//	response.setData("0");
			//	return new ResponseEntity<Object>(response, HttpStatus.OK);
			}
		}
		else if(discountType.equalsIgnoreCase("Percentage") || discountType.equalsIgnoreCase("%"))
		{
			Double discountPercentage=nimaimmRepo.getDiscPercByDiscountId(discountId);
			Double maxDiscount=nimaimmRepo.getMaxDiscByDiscountId(discountId);
			Double subsValue=subsAmount*(discountPercentage/100);
			logger.info("Discount Percentage: "+discountPercentage);
			logger.info("Max Discount: "+maxDiscount);
			logger.info("subsValue: "+subsValue);
			if(subsValue<=maxDiscount)
			{
				finalAmount=subsAmount-subsValue;
				logger.info("Final Amount: "+finalAmount);
				if(finalAmount>=0)
				{
					//return subsValue;
					HashMap<String,Double> data1=new HashMap<>();
					data1.put("discountId", discountId);
					data1.put("discount", subsValue);
					data1.put("grandAmount", finalAmount);
					return data1;
					//subscriptionDetailsRepository.updateDiscountId(userId,discountId);
					//response.setStatus("Coupon Applied Successfully");
					//response.setData(data1);
					//return new ResponseEntity<Object>(response, HttpStatus.OK);
				}
				else
				{
					//return 0.0;
					HashMap<String,Double> data1=new HashMap<>();
					data1.put("discountId", discountId);
					data1.put("discount", 0.0);
					data1.put("grandAmount", finalAmount);
					return data1;
				}
			}
			else
			{
				finalAmount=subsAmount-maxDiscount;
				logger.info("Final Amount: "+finalAmount);
				if(finalAmount>=0)
				{
					//return maxDiscount;
					HashMap<String,Double> data1=new HashMap<>();
					data1.put("discountId", discountId);
					data1.put("discount", maxDiscount);
					data1.put("grandAmount", finalAmount);
					return data1;
					//subscriptionDetailsRepository.updateDiscountId(userId,discountId);
				//	response.setStatus("Coupon Applied Successfully");
				//	response.setData(data1);
				//	return new ResponseEntity<Object>(response, HttpStatus.OK);
				}
				else
				{
					//return 0.0;
					HashMap<String,Double> data1=new HashMap<>();
					data1.put("discountId", discountId);
					data1.put("discount", 0.0);
					data1.put("grandAmount", finalAmount);
					return data1;
				}
			}
			
		}
		else
		{
			//return 0.0;
			HashMap<String,Double> data1=new HashMap<>();
			data1.put("discountId", discountId);
			data1.put("discount", 0.0);
			data1.put("grandAmount", 0.0);
			return data1;
		}
			
	}
	
}	
	
	


