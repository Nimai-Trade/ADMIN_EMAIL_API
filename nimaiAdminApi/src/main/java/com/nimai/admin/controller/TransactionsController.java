package com.nimai.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nimai.admin.payload.PagedResponse;
import com.nimai.admin.payload.QuotationDetailsResponse;
import com.nimai.admin.payload.SearchRequest;
import com.nimai.admin.payload.TransactionDetails;
import com.nimai.admin.service.TransactionsService;

/**
 * 
 * @author sahadeo.naik
 *
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/transaction")
public class TransactionsController {
	
	@Autowired
	TransactionsService transactionsService;
	
	@PostMapping("/list")
	public PagedResponse<?> getAllTransaction(@RequestBody SearchRequest request) {
		return transactionsService.getAllTransaction(request);

	}
	
	@GetMapping("/userIdSearch/{userId}")
	public List<String> userIdSearch(@PathVariable(value = "userId") String userId) {
		System.out.println("userIdSearch search :: " + userId);
		return transactionsService.customerUserIdSearch(userId);
		//return transactionsService.userIdSearch(userId);
	}
	
	@GetMapping("/emailIdSearch/{emailId}")
	public List<String> emailIdSearch(@PathVariable(value = "emailId") String emailId) {
		System.out.println("emailIdSearch search :: " + emailId);
		return transactionsService.emailIdSearch(emailId);
	}
	
	@GetMapping("/mobileNumberSearch/{mobileNo}")
	public List<String> mobileNumberSearch(@PathVariable(value = "mobileNo") String mobileNo) {
		System.out.println("mobileNumberSearch search :: " + mobileNo);
		return transactionsService.mobileNumberSearch(mobileNo);
	}
	
	@GetMapping("/companyNameSearch/{companyName}")
	public List<String> companyNameSearch(@PathVariable(value = "companyName") String companyName) {
		System.out.println("mobileNumberSearch search :: " + companyName);
		return transactionsService.companyNameSearch(companyName);
	}
	
	@GetMapping("/countrySearch/{country}")
	public List<String> countrySearch(@PathVariable(value = "country") String country) {
		System.out.println("countrySearch search :: " + country);
		return transactionsService.countrySearch(country);
	}
	
	@GetMapping("/details/{id}")
	public ResponseEntity<TransactionDetails> getTransactionById(@PathVariable(value = "id") String transactionId) {
		return transactionsService.getTransactionById(transactionId);
	}
	
	@PostMapping("/quotesList")
	public PagedResponse<?> getQuotesDetails(@RequestBody SearchRequest request) {
		return transactionsService.getQuotesDetails(request);
	}
	
	@GetMapping("/quotes/{id}")
	public ResponseEntity<QuotationDetailsResponse> getQuotesDetailsById(@PathVariable(value = "id") Integer quotationId) {
		return transactionsService.getQuotesDetailsById(quotationId);
	}

}
