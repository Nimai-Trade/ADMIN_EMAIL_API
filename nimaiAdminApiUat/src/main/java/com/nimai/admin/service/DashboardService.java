package com.nimai.admin.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.nimai.admin.payload.DashCountryAnalysisResponse;
import com.nimai.admin.payload.DashNewUserStat;
import com.nimai.admin.payload.DashTransStat;
import com.nimai.admin.payload.PagedResponse;
import com.nimai.admin.payload.SearchRequest;

public interface DashboardService {

	int getConfAwaited();

	int getpayApproval();

	int getAssignRmCount();

	int getPendingAssignRmCount();

	int getUserCount();

	int getKycApprovalCount();

	int getGrantKycCount();

	int getPendingKycCount(SearchRequest request);

	int getSubsExpCount(Optional<SearchRequest> request);

	int getPayPendingCount(SearchRequest request);
	
	int getSubsGrantCount();

	int getVasGrantCount();

	int getDiscountCouponCount();

	Map<String, Object> getCustomerRevenue(SearchRequest request);

	Map<String, Object> getBankCustomerRevenue(SearchRequest request);

	Map<String, Object> getBankUwRevenue(SearchRequest request);

	// --------------------->>>>>>>>>>>>>>>>>>>>>

	List<DashCountryAnalysisResponse> getCountryAnalysis();

	List<DashNewUserStat> getNewUserStat(SearchRequest request);

	List<DashNewUserStat> getActiveUserStat(SearchRequest request);

	// --------------------------->>>>>>>>>>>>>>>>>>>>>>>>>
	List<DashTransStat> getTransactionStat(SearchRequest request);

	List<Map<String, Object>> getQuotesStat(SearchRequest request);

	int getOverallCustomers(SearchRequest request);
	
	//new
	int getOverallCustomersDate(SearchRequest request);

	int getOverallBankUw(SearchRequest request);

	int getCustomerStatusTransc(SearchRequest request);

	// ------------------dash 2 Customer---------------

	int getCustTrxn(SearchRequest request);

	int getCustActiveTrxnCount();

	int getCustTrxnEXp(SearchRequest request);
	
	int getCustBankPayPendingCount(SearchRequest request);
	
	int getBankAndCustPendingKycCount(SearchRequest request);
	
	int getSubsExpBankAndCustCount(SearchRequest request);


	// -------------------bank >>>>>>>>>>

	List<Map<String, Object>> getBankAvgStat(SearchRequest request);
	
	int getCustTrxnBank(Optional<SearchRequest> request);
   
	
	int getTotalCustomers(Optional<SearchRequest> request);

	int getBankQuotesCount(Optional<SearchRequest> request);

	PagedResponse<?> getCustDiscardO(Optional<SearchRequest> request);

	PagedResponse<?> getCustStat(SearchRequest request);

	PagedResponse<?> getSubStat(SearchRequest request);

	int getCustTran(SearchRequest request);

	List<DashTransStat> getCustTransactionStat(SearchRequest request);

	int getBankAs(SearchRequest request);

	int getBankAwaitedQuotes();

	 List<Map<String, Object>> getTransactionComp(SearchRequest request);

	int getReferrers(SearchRequest request);

	List<DashCountryAnalysisResponse> getLatestCountryAnalysis();

}
