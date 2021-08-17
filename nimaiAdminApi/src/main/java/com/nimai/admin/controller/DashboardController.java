package com.nimai.admin.controller;

import java.time.Year;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nimai.admin.payload.PagedResponse;
import com.nimai.admin.payload.SearchRequest;
import com.nimai.admin.service.DashboardService;

/**
 * Created by Bashir
 */

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {
	
	private static Logger logger = LoggerFactory.getLogger(DashboardController.class);


	@Autowired
	DashboardService dashService;

	@GetMapping("approvals/payConfAwaited")
	public int getConfCount() {
		return dashService.getConfAwaited();
	}

	@GetMapping("approvals/payApproval")
	public int getAppCount() {
		return dashService.getpayApproval();
	}

	@GetMapping("approvals/assignRM")
	public int getPendingAssignCount() {
		return dashService.getAssignRmCount();
	}

	@GetMapping("approvals/assignRM/pending")
	public int getPending() {
		return dashService.getPendingAssignRmCount();
	}

	@GetMapping("approvals/grantUsers")
	public int getUserCount() {
		return dashService.getUserCount();
	}

	@GetMapping("approvals/pendingKYCApproval")
	public int getPendingCount() {
		return dashService.getKycApprovalCount();
	}

	@GetMapping("approvals/grantKyc")
	public int getGrantCount() {
		return dashService.getGrantKycCount();
	}

	@PostMapping("approvals/pendingKyc")
	public int getPendingKycCount(@RequestBody SearchRequest request) {
		return dashService.getPendingKycCount(request);
	}

	@PostMapping("approvals/subsExpiry")
	public int getExpCount(@RequestBody Optional<SearchRequest> request) {
		return dashService.getSubsExpCount(request);
	}

	@PostMapping("approvals/paymentPending")
	public int getPendingCount(@RequestBody SearchRequest request) {
		return dashService.getPayPendingCount(request);
	}

	@GetMapping("approvalsEx/subscription")
	public int getGrantSubs() {
		return dashService.getSubsGrantCount();
	}

	@GetMapping("approvalsEx/vas")
	public int getGrantVas() {
		return dashService.getVasGrantCount();
	}

	@GetMapping("approvalsEx/discountCoupon")
	public int getGrantDiscount() {
		return dashService.getDiscountCouponCount();
	}

	// ---->>>>>>>>>>>>>>>----------------

	@PostMapping("revenue/customer")
	public ResponseEntity<?> getCustRevenue(@RequestBody SearchRequest request) {
		logger.info("==========revenue/customer====================");
		return new ResponseEntity<>(dashService.getCustomerRevenue(request), HttpStatus.OK);
	}

	@PostMapping("revenue/bankAsCust")
	public ResponseEntity<?> getBankCustRevenue(@RequestBody SearchRequest request) {
		logger.info("==========INside revenue/bankAsCust====================");
		return new ResponseEntity<>(dashService.getBankCustomerRevenue(request), HttpStatus.OK);
	}

	@PostMapping("revenue/bankAsUw")
	public ResponseEntity<?> getBankUwRevenue(@RequestBody SearchRequest request) {
		logger.info("==========INside revenue/bankAsUw====================");
		return new ResponseEntity<>(dashService.getBankUwRevenue(request), HttpStatus.OK);
	}

	// ----->>>>>>>>>>>>>>>----------

	@GetMapping("/countryAnalysis")
	public ResponseEntity<?> getList() {
		return new ResponseEntity<>(dashService.getLatestCountryAnalysis(), HttpStatus.OK);
		//return new ResponseEntity<>(dashService.getCountryAnalysis(), HttpStatus.OK);
	}

	@PostMapping("/newUserStats")
	public ResponseEntity<?> getStat(@RequestBody SearchRequest request) {
		if (request.getDateFrom() == null) {
			request.setDateFrom(Year.now().toString());
		}
		return new ResponseEntity<>(dashService.getNewUserStat(request), HttpStatus.OK);
	}

	@PostMapping("/activeUserStats")
	public ResponseEntity<?> getActiveStat(@RequestBody SearchRequest request) {
		if (request.getDateFrom() == null) {
			request.setDateFrom(Year.now().toString());
		}
		return new ResponseEntity<>(dashService.getActiveUserStat(request), HttpStatus.OK);
	}

	// ------------>>>>>>>>>>>>>>>>>>>>>>-----------

	@PostMapping("/transactionStats")
	public ResponseEntity<?> getTransactStat(@RequestBody SearchRequest request) {
		return new ResponseEntity<>(dashService.getTransactionStat(request), HttpStatus.OK);
	}

	@PostMapping("/quotesPerTransaction")
	public ResponseEntity<?> getQuotesStat(@RequestBody SearchRequest request) {
		return new ResponseEntity<>(dashService.getQuotesStat(request), HttpStatus.OK);
	}

	@PostMapping("/overall/customers")
	public int getOvrCust(@RequestBody SearchRequest request) {
		return dashService.getOverallCustomers(request);
	}

	@PostMapping("/overall/bank")
	public int getOvrBankUw(@RequestBody SearchRequest request) {
		return dashService.getOverallBankUw(request);
	}

	@PostMapping("/overall/custTransactStat")
	public int getCustRej(@RequestBody SearchRequest request) {
		return dashService.getCustomerStatusTransc(request);
	}

	// ------------Dashboard 2 >>> Cust-------------------//

	@PostMapping("/customer/transStats")
	public ResponseEntity<?> getCustTransactStat(@RequestBody SearchRequest request) {
		return new ResponseEntity<>(dashService.getCustTransactionStat(request), HttpStatus.OK);
	}

	@PostMapping("/customer/tranComparision")
	public ResponseEntity<?> getCustTranComp(@RequestBody SearchRequest request){
		return new ResponseEntity<>(dashService.getTransactionComp(request), HttpStatus.OK);
	}
	@PostMapping("/customer/totalTrxn")
	public int getTransCount(@RequestBody SearchRequest request) {
		return dashService.getCustTran(request);
	}

	@PostMapping("/customer/acceptedTrans")
	public int getCustAccepted(@RequestBody SearchRequest request) {
		return dashService.getCustTrxn(request);
	}

	@PostMapping("/customer/rejected")
	public int getCustRejected(@RequestBody SearchRequest request) {
		return dashService.getCustTrxn(request);
	}

	@PostMapping("/customer/expired")
	public int getCustTrxnExpired(@RequestBody SearchRequest request) {
		return dashService.getCustTrxn(request);
	}

	@PostMapping("/customer/customers")
	public int getCustomers(@RequestBody SearchRequest request) {
		return dashService.getOverallCustomersDate(request);
	}

	@GetMapping("/customer/activeCustTrxn")
	public int getActTrxn() {
		return dashService.getCustActiveTrxnCount();
	}

	@PostMapping("/customer/trxnExpiry")
	public int getCustExpCount(@RequestBody SearchRequest request) {
		return dashService.getCustTrxnEXp(request);
	}

	@PostMapping("/customer/paymentPending")//new case created in db wrt case 11
	public int getPayPending(@RequestBody SearchRequest request) {
		return dashService.getCustBankPayPendingCount(request);
	}

	@PostMapping("/customer/kycPending")//created case 25
	public int getKycPen(@RequestBody SearchRequest request) {
		return dashService.getBankAndCustPendingKycCount(request);
	}
	@PostMapping("/customer/subscriptionExpiry")//new case created in db wrt case 9
	public int getCustSubsEx(@RequestBody SearchRequest request) {
		return dashService.getSubsExpBankAndCustCount(request);
	}
	
	@PostMapping("/customer/referrer")
	public int getRef(@RequestBody SearchRequest request) {
		return dashService.getReferrers(request);
	}
	
	// ------>>>>>> DashBoard Bank>>>>>>>>>>	
	
	@PostMapping("/bank/transactionStats")
	public ResponseEntity<?> getBankTransactStat(@RequestBody SearchRequest request) {
		return new ResponseEntity<>(dashService.getCustTransactionStat(request), HttpStatus.OK);
	}
	
	@PostMapping("/bank/transactionComparision")
	public ResponseEntity<?> getCustTransactionStat(@RequestBody SearchRequest request) {
		return new ResponseEntity<>(dashService.getTransactionComp(request), HttpStatus.OK);
	}
	
	@PostMapping("/bank/avgQuotes")
	public ResponseEntity<?> getAvgQuotes(@RequestBody SearchRequest request){
		return new ResponseEntity<>(dashService.getBankAvgStat(request),HttpStatus.OK);
	}

	@PostMapping("/bank/bankTrans")
	public int getAllBankCustTran(@RequestBody SearchRequest request) {
		return dashService.getCustTran(request);
	}

	@PostMapping("/bank/custStatusTrxn")
	public int getCustTrxn(@RequestBody Optional<SearchRequest> request) {// need to pass status as Accepted & Dates
		return dashService.getCustTrxnBank(request);
	}

	// bank and underwriter
	@PostMapping("/bank/totalCustomer")
	public int getBankTotalCust(@RequestBody Optional<SearchRequest> request) {
		return dashService.getTotalCustomers(request);
	}

	@PostMapping("/bank/totalQuotes")
	public int getBankTotalQuotes(@RequestBody Optional<SearchRequest> request) {
		return dashService.getBankQuotesCount(request);
	}

	@PostMapping("/bank/statusQuotes")
	public int getBankAccptdQuotes(@RequestBody Optional<SearchRequest> request) {
		return dashService.getBankQuotesCount(request);
	}

	// trans expin 3 days call /dashboard/customer/trxnExpiry

	// call cust/payment pending 

	// call customer/kycPending

	// call subs expiry customer 

	// // //
	@PostMapping("/bank/custOruw")
	public int getBank(@RequestBody SearchRequest request) {
		return dashService.getBankAs(request);
	}

	@GetMapping("/bank/quotesAwaitedFromBank")
	public int getQuotesAwaited() {
		return dashService.getBankAwaitedQuotes();
	}

	// --->>>>>>>> discard 1
	@PostMapping("customer/transactionStats")
	public PagedResponse<?> getList(@RequestBody Optional<SearchRequest> request) {
		return dashService.getCustDiscardO(request);
	}

	@PostMapping("customer/customerStats")
	public PagedResponse<?> getCustStats(@RequestBody SearchRequest request) {
		return dashService.getCustStat(request);
	}

	@PostMapping("customer/subscriptionStats")
	public PagedResponse<?> getSubsStat(@RequestBody SearchRequest request) {
		return dashService.getSubStat(request);
	}
}
