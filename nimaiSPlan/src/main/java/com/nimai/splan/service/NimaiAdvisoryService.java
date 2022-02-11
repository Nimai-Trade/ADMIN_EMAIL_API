package com.nimai.splan.service;

import java.util.List;

import com.nimai.splan.model.NimaiAdvisory;
import com.nimai.splan.model.NimaiCustomerSubscriptionGrandAmount;
import com.nimai.splan.model.NimaiSubscriptionVas;


public interface NimaiAdvisoryService {
		public List<NimaiAdvisory> viewAdvisory();
		
		public List<NimaiAdvisory> viewAdvisoryByCountry(String country_name, String userID);

		public String getSubscriptionIdForActive(String userId);

		public void addVasDetails(String userId, String subscriptionId, Integer vasId, String mode, int isSplanWithVasFlag);

		public void inactiveVASStatus(String userId);

		public List<NimaiSubscriptionVas> getActiveVASByUserId(String userId);

		public NimaiAdvisory getVasDetails(String string);

		void addVasDetailsAfterSubscription(String userId, String subscriptionId, String vasId, String mode, Float pricing, String paymentTxnId, String invoiceId);

		Float getVASAmount(String userId, Integer vasId);

		public void addGrandVasDetails(String userId, Double grandAmount);

		public void removeGrandVasDetails(Integer id);

		NimaiCustomerSubscriptionGrandAmount getCustomerVASAmount(String userId);

		public void getLastSerialNoAndUpdate(String userId, String mode);

}
