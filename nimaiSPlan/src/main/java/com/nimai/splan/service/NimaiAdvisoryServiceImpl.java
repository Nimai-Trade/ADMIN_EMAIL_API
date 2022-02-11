package com.nimai.splan.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.nimai.splan.model.NimaiAdvisory;
import com.nimai.splan.model.NimaiCustomerSubscriptionGrandAmount;
import com.nimai.splan.model.NimaiEmailScheduler;
import com.nimai.splan.model.NimaiMCustomer;
import com.nimai.splan.model.NimaiSubscriptionDetails;
import com.nimai.splan.model.NimaiSubscriptionVas;
import com.nimai.splan.repository.NimaiAdvisoryRepo;
import com.nimai.splan.repository.NimaiCustomerGrandAmountRepo;
import com.nimai.splan.repository.NimaiEmailSchedulerRepository;
import com.nimai.splan.repository.NimaiMCustomerRepository;
import com.nimai.splan.repository.NimaiMSPlanRepository;
import com.nimai.splan.repository.NimaiSubscriptionVasRepo;
import com.nimai.splan.repository.SubscriptionPlanRepository;

@Service
public class NimaiAdvisoryServiceImpl implements NimaiAdvisoryService {

	@Autowired
	NimaiMCustomerRepository userRepository;
	
	@Autowired
	NimaiAdvisoryRepo nimaiAdvisoryRepo;

	@Autowired
	NimaiMSPlanRepository subscriptionRepo;
	
	@Autowired
	SubscriptionPlanRepository splanDetRepo;
	
	@Autowired
	NimaiCustomerGrandAmountRepo nimaiCustomerGrandAmtRepository;
	
	@Autowired
	NimaiSubscriptionVasRepo nsvRepo;
	
	@Autowired
	NimaiEmailSchedulerRepository emailDetailsRepository;

	
	@Override
	public List<NimaiAdvisory> viewAdvisory() {
		// TODO Auto-generated method stub
		return nimaiAdvisoryRepo.findAll();
	}

	@Override
	public List<NimaiAdvisory> viewAdvisoryByCountry(String country_name,String userId) {

		List<NimaiAdvisory> vasList=null;
		if (userId.substring(0, 2).equalsIgnoreCase("CU"))
			vasList = nimaiAdvisoryRepo.findByCountryName(country_name,"Customer");
		if (userId.substring(0, 2).equalsIgnoreCase("BC"))
			vasList = nimaiAdvisoryRepo.findByCountryName(country_name,"Bank As Customer");
		
		return vasList;

	}

	@Override
	public String getSubscriptionIdForActive(String userId) {
		// TODO Auto-generated method stub
		String obtainedSubscriptionId="";
		try
		{
			NimaiSubscriptionDetails nmd=splanDetRepo.findByUserId(userId);
			//obtainedSubscriptionId=splanDetRepo.findSubscriptionIdById(userId);
			obtainedSubscriptionId=nmd.getSubscriptionId();
		}
		catch(Exception e)
		{
			System.out.println("No Active Subscription Available");
			obtainedSubscriptionId="";
		}
		return obtainedSubscriptionId;
	}

	@Override
	public void addVasDetails(String userId, String subscriptionId, Integer vasId, String mode, int isSplanWithvasFlag) {
		// TODO Auto-generated method stub
		
		NimaiAdvisory vasAdvisory=nimaiAdvisoryRepo.getDataByVasId(vasId);
		NimaiSubscriptionVas nsv=new NimaiSubscriptionVas();
		nsv.setUserId(userId);
		nsv.setSubscriptionId(subscriptionId);
		nsv.setVasId(vasId);
		nsv.setCountryName(vasAdvisory.getCountry_name());
		nsv.setPlanName(vasAdvisory.getPlan_name());
		nsv.setDescription_1(vasAdvisory.getDescription_1());
		nsv.setDescription_2(vasAdvisory.getDescription_2());
		nsv.setDescription_3(vasAdvisory.getDescription_3());
		nsv.setDescription_4(vasAdvisory.getDescription_4());
		nsv.setDescription_5(vasAdvisory.getDescription_5());
		nsv.setCurrency(vasAdvisory.getCurrency());
		nsv.setPricing(vasAdvisory.getPricing());
		nsv.setIsSplanWithVasFlag(isSplanWithvasFlag);
		nsv.setStatus("Active");
		nsv.setInsertedBy(userId);
		nsv.setInsertedDate(new Date());
		nsv.setModifiedBy(userId);
		nsv.setModifiedDate(new Date());
		
		if(mode.equalsIgnoreCase("Wire"))
		{
			nsv.setPaymentSts("Pending");
			nsv.setMode("Wire");
		}
		else
		{
			nsv.setPaymentSts("Approved");
			nsv.setMode("Credit");
		}
		nsvRepo.save(nsv);
		
		/*NimaiEmailScheduler schedularData = new NimaiEmailScheduler();
		schedularData.setSubscriptionName(vasAdvisory.getPlan_name());
		schedularData.setEvent("VAS_ADDED");
		schedularData.setEmailStatus("Pending");
		schedularData.setUserid(userId);
		emailDetailsRepository.save(schedularData);*/
		splanDetRepo.updateIsVASApplied(userId);
	}
	
	@Override
	public void addVasDetailsAfterSubscription(String userId, String subscriptionId, String vasIdString, String mode, Float pricing, String paymentTxnId, String invoiceId) {
		// TODO Auto-generated method stub
		Double subscriptionTotalMonth,differenceInSubsStartAndCurrent;
		Float vasFinalAmount = null;
		int vasCount = StringUtils.countOccurrencesOf(vasIdString, "-");
		System.out.println("Total VAS: "+vasCount);
		int i,vasId;
		//int vasWithDisc=vasCount+1;
		String vasSplitted[] =vasIdString.split("-",vasCount+1);
		for(i=0;i<vasCount;i++)
		{
			System.out.println("Iteration: "+i);
			vasId=Integer.valueOf(vasSplitted[i]);
			System.out.println("VASID: "+vasId);
			NimaiAdvisory vasAdvisory=nimaiAdvisoryRepo.getDataByVasId(vasId);
			NimaiSubscriptionDetails nsd=splanDetRepo.findByUserId(userId);
			try
			{
				subscriptionTotalMonth=splanDetRepo.findNoOfMonthOfSubscriptionByUserId(userId);
				System.out.println("Subscription of Month: "+subscriptionTotalMonth);
				differenceInSubsStartAndCurrent=splanDetRepo.findDiffInSubscriptionStartAndCurrentByUserId(userId);
				System.out.println("Subscription of Month: "+differenceInSubsStartAndCurrent);
				Double halfSubscriptionTotalMonth=subscriptionTotalMonth/2;
				System.out.println("Half of SubscriptionTotalMonth: "+halfSubscriptionTotalMonth);
				System.out.println("VAS Price: "+vasAdvisory.getPricing());
				if(differenceInSubsStartAndCurrent>halfSubscriptionTotalMonth)
					vasFinalAmount=vasAdvisory.getPricing()/2;
				else
					vasFinalAmount=vasAdvisory.getPricing();
			}
			catch(Exception e)
			{
				subscriptionTotalMonth=0.0;
				differenceInSubsStartAndCurrent=0.0;
				vasFinalAmount=0f;
			}
			Double gstValue=nimaiAdvisoryRepo.getGSTValue()/100;
			//System.out.println(""+nsv.getPricing());
			Double planPriceGST=vasFinalAmount+(vasFinalAmount*gstValue);
			System.out.println("gstValue: "+gstValue);
			System.out.println("planPriceGST: "+planPriceGST);
			String finalPrice = String.format("%.2f", planPriceGST);
			String vasfinalPrice = String.format("%.2f", vasFinalAmount);
			System.out.println("Final Amount: "+vasFinalAmount);
			NimaiSubscriptionVas nsv=new NimaiSubscriptionVas();
			nsv.setUserId(userId);
			nsv.setSubscriptionId(subscriptionId);
			nsv.setVasId(vasId);
			nsv.setCountryName(vasAdvisory.getCountry_name());
			nsv.setPlanName(vasAdvisory.getPlan_name());
			nsv.setDescription_1(vasAdvisory.getDescription_1());
			nsv.setDescription_2(vasAdvisory.getDescription_2());
			nsv.setDescription_3(vasAdvisory.getDescription_3());
			nsv.setDescription_4(vasAdvisory.getDescription_4());
			nsv.setDescription_5(vasAdvisory.getDescription_5());
			nsv.setCurrency(vasAdvisory.getCurrency());
			nsv.setSplSerialNumber(nsd.getsPlSerialNUmber());
			nsv.setStatus("Active");
			nsv.setMode(mode);
			nsv.setPaymentTxnId(paymentTxnId);
			nsv.setInvoiceId(invoiceId);
			if(mode.equalsIgnoreCase("Wire"))
			{
				nsv.setPricing(Float.valueOf(finalPrice));
				nsv.setPaymentSts("Pending");
			}
			else
			{
				nsv.setPricing(Float.valueOf(finalPrice));
				nsv.setPaymentSts("Approved");
			}
			nsv.setInsertedBy(userId);
			nsv.setInsertedDate(new Date());
			nsv.setModifiedBy(userId);
			nsv.setModifiedDate(new Date());
			nsvRepo.save(nsv);
			if(mode.equalsIgnoreCase("Wire"))
			{
				
				splanDetRepo.updateVASDetailsAppliedWire(userId,vasfinalPrice,finalPrice);
				userRepository.updatePaymentTxnId("Wire",invoiceId, userId);
			}
			else
			{
				splanDetRepo.updateVASDetailsApplied(userId,vasFinalAmount,Float.valueOf(finalPrice));
				userRepository.updatePaymentTxnId("Credit",invoiceId, userId);
			}
			/*if(mode.equalsIgnoreCase("wire"))
			{
				userRepository.updatePaymentMode("Wire", userId);
				userRepository.updatePaymentStatus(userId);
			}*/
		
		NimaiEmailScheduler schedularData = new NimaiEmailScheduler();
		schedularData.setUserid(nsd.getUserid().getUserid());
		schedularData.setDescription1(vasAdvisory.getDescription_1());
		schedularData.setDescription2(vasAdvisory.getDescription_2());
		schedularData.setDescription3(vasAdvisory.getDescription_3());
		schedularData.setDescription4(vasAdvisory.getDescription_4());
		schedularData.setDescription5(vasAdvisory.getDescription_5());
		schedularData.setSubscriptionName(vasAdvisory.getPlan_name());
		schedularData.setEmailId(nsd.getUserid().getEmailAddress());
		schedularData.setSubscriptionAmount(String.valueOf(vasFinalAmount));
		schedularData.setEvent("VAS_ADDED");
		schedularData.setEmailStatus("Pending");
		schedularData.setSubscriptionId(nsd.getSubscriptionId());
		emailDetailsRepository.save(schedularData);
		}
	}

	@Override
	public void inactiveVASStatus(String userId) {
		// TODO Auto-generated method stub
		
			List<NimaiSubscriptionVas> vasEntity = nsvRepo
					.findAllByUserId(userId);
			if (!vasEntity.isEmpty()) {
				for (NimaiSubscriptionVas vas : vasEntity) {
					vas.setStatus("Inactive");
					nsvRepo.save(vas);
				}
			}
	}

	@Override
	public List<NimaiSubscriptionVas> getActiveVASByUserId(String userId) {
		// TODO Auto-generated method stub
		return nsvRepo.findActiveVASByUserId(userId);
	}

	@Override
	public NimaiAdvisory getVasDetails(String string) {
		// TODO Auto-generated method stub
		return nimaiAdvisoryRepo.getVASDetByVasId(Integer.valueOf(string));
	}
	
	@Override
	public Float getVASAmount(String userId, Integer vasId) {
		// TODO Auto-generated method stub
		Double subscriptionTotalMonth,differenceInSubsStartAndCurrent;
		Float vasFinalAmount = null;
		NimaiAdvisory vasAdvisory=nimaiAdvisoryRepo.getDataByVasId(vasId);
		NimaiSubscriptionDetails nsd=splanDetRepo.findByUserId(userId);
		try
		{
			subscriptionTotalMonth=splanDetRepo.findNoOfMonthOfSubscriptionByUserId(userId);
			System.out.println("Subscription of Month: "+subscriptionTotalMonth);
			differenceInSubsStartAndCurrent=splanDetRepo.findDiffInSubscriptionStartAndCurrentByUserId(userId);
			System.out.println("Subscription of Month: "+differenceInSubsStartAndCurrent);
			Double halfSubscriptionTotalMonth=subscriptionTotalMonth/2;
			System.out.println("Half of SubscriptionTotalMonth: "+halfSubscriptionTotalMonth);
			System.out.println("VAS Price: "+vasAdvisory.getPricing());
			if(differenceInSubsStartAndCurrent>halfSubscriptionTotalMonth)
				vasFinalAmount=vasAdvisory.getPricing()/2;
			else
				vasFinalAmount=vasAdvisory.getPricing();
		}
		catch(Exception e)
		{
			subscriptionTotalMonth=0.0;
			differenceInSubsStartAndCurrent=0.0;
			vasFinalAmount=0f;
		}
		System.out.println("Final Amount: "+vasFinalAmount);
		return vasFinalAmount;
	}

	@Override
	public void addGrandVasDetails(String userId, Double grandAmount) {
		// TODO Auto-generated method stub
		NimaiCustomerSubscriptionGrandAmount ncsgm=new NimaiCustomerSubscriptionGrandAmount();
		ncsgm.setUserId(userId);
		ncsgm.setGrandAmount(grandAmount);
		ncsgm.setVasApplied("Yes");
		ncsgm.setInsertedDate(new Date());
		nimaiCustomerGrandAmtRepository.save(ncsgm);
	}

	@Override
	public void removeGrandVasDetails(Integer id) {
		// TODO Auto-generated method stub
		nimaiCustomerGrandAmtRepository.deleteById(id);
		
	}
	
	@Override
	public NimaiCustomerSubscriptionGrandAmount getCustomerVASAmount(String userId) {
		// TODO Auto-generated method stub
		NimaiCustomerSubscriptionGrandAmount userDet=nimaiCustomerGrandAmtRepository.getVASDetByUserId(userId);
		return userDet;
	}

	
	@Override
	public void getLastSerialNoAndUpdate(String userId, String mode) {
		// TODO Auto-generated method stub
		Integer serialNo=splanDetRepo.findLastSerialNo();
		System.out.println("SerialNo: "+serialNo);
		if(mode.equalsIgnoreCase("Wire"))
		{
			nimaiAdvisoryRepo.updateSplSerialNo(userId, serialNo);
		}
		else
		{
			nimaiAdvisoryRepo.updateSplSerialNo(userId, serialNo);
		}
	}

}
