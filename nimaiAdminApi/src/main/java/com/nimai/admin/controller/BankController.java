package com.nimai.admin.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nimai.admin.model.NimaiMCustomer;
import com.nimai.admin.payload.BankDetailsResponse;
import com.nimai.admin.payload.CouponBean;
import com.nimai.admin.payload.KycBDetailResponse;
import com.nimai.admin.payload.PagedResponse;
import com.nimai.admin.payload.PlanOfPaymentDetailsResponse;
import com.nimai.admin.payload.QuotationDetailsResponse;
import com.nimai.admin.payload.SPlanBean;
import com.nimai.admin.payload.SearchRequest;
import com.nimai.admin.payload.VasUpdateRequestBody;
import com.nimai.admin.service.BankService;

/**
 * 
 * @author bashir.khan
 *
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/bank")
public class BankController {
	
	private static Logger logger = LoggerFactory.getLogger(BankController.class);

	@Autowired
	BankService bankService;

	@PostMapping("/searchList")
	public PagedResponse<?> getSearchCustomer(@RequestBody SearchRequest request) {
		return bankService.getSearchBankDetail(request);
	}

	@GetMapping("/details/{userid}")
	public ResponseEntity<BankDetailsResponse> getBankDetailByUserId(@PathVariable(value = "userid") String userid) {
		System.out.println("user :: " + userid);
		return bankService.getBankDetailUserId(userid);
	}

	//parameter did not match as user id req is NimaiCustomer and we were passing String
	@GetMapping("/quotes/{userId}")
	public List<QuotationDetailsResponse> getQuotesDetailsByUserId(@PathVariable("userId") String userId) {
		return bankService.getQuotesUserId(userId);
	}

	@GetMapping("/kyc/{userId}")
	public List<KycBDetailResponse> grtKycDetailByUserId(@PathVariable("userId") String userId) {
		NimaiMCustomer c = new NimaiMCustomer();
		c.setUserid(userId);
		return bankService.getKycDetailsUserId(c);
	}

	@GetMapping("/planOfPayment/{userId}")
	public List<PlanOfPaymentDetailsResponse> getPlanDetails(@PathVariable("userId") String userId) {
//		NimaiMCustomer cust = new NimaiMCustomer();
//		cust.setUserid(userId);
		return bankService.getPlanOPayDetails(userId);
	}
	
	@PostMapping("/kycStatusUpdate")
	public ResponseEntity<?> kycStatusUpdate(@RequestBody KycBDetailResponse data) {
		return bankService.kycStatusUpdate(data);
	}

	@PostMapping("/quoteList")
	public PagedResponse<?> getBankQuoteList(@RequestBody SearchRequest request) {
		return bankService.getBankQuoteList(request);
	}
	
	@PostMapping("/makerKycStatusUpdate")
	public ResponseEntity<?> makerKycStatusUpdate(@RequestBody KycBDetailResponse data) {
		return bankService.makerKycStatusUpdate(data);
	}
	
	@PostMapping("/makerApprovedKyc")
	public PagedResponse<?> getMakerApprovedKyc(@RequestBody SearchRequest request) {
		System.out.println("Request: "+request);
		return bankService.getMakerApprovedKyc(request);
	}
	
	@PostMapping("/viewMakerApprovedKycByKycId")
	public KycBDetailResponse getMakerApprovedKycbyKycId(@RequestBody SearchRequest request) {
		System.out.println("Request: "+request);
		KycBDetailResponse response = bankService.getMakerApprovedKycByKycId(request);
		return response;
	}
	
	@PostMapping("/wireTranferStatusUpdate")
	public ResponseEntity<?> wireTranferStatusUpdate(@RequestBody VasUpdateRequestBody request) {
		return bankService.wireTranferStatusUpdate(request);
	}
	
	@PostMapping("/wireTransferList")
	public PagedResponse<?> getWireTransferList(@RequestBody SearchRequest request) {
		return bankService.getWireTransferList(request);
	}
	
	
	
	@PostMapping("/vasWireTransferList")
	public PagedResponse<?> getVasWireTransferList(@RequestBody SearchRequest request) {
		return bankService.getVasWireTransferList(request);
	}
	
	@PostMapping("/checkDuplicateCouponCode")
	public ResponseEntity<?> checkDuplicateCouponCode(@RequestBody CouponBean request) {
		return bankService.checkDuplicateCouponCode(request);
	}
	  
	@PostMapping("/checkDuplicateSPLan")
	public ResponseEntity<?> checkDuplicateSPLan(@RequestBody SPlanBean request) {
		return bankService.checkDuplicateSPLan(request);
	}

}
