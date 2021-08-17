package com.nimai.admin.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.nimai.admin.payload.PagedResponse;
import com.nimai.admin.payload.QuotationDetailsResponse;
import com.nimai.admin.payload.SearchRequest;
import com.nimai.admin.payload.TransactionDetails;

public interface TransactionsService {

	PagedResponse<?> getAllTransaction(SearchRequest request);

	List<String> userIdSearch(String userId);

	List<String> emailIdSearch(String emailId);

	List<String> mobileNumberSearch(String mobileNo);

	List<String> companyNameSearch(String companyName);

	List<String> countrySearch(String country);

	ResponseEntity<TransactionDetails> getTransactionById(String transactionId);

	PagedResponse<?> getQuotesDetails(SearchRequest request);

	ResponseEntity<QuotationDetailsResponse> getQuotesDetailsById(Integer quotationId);

	List<String> customerUserIdSearch(String userId);

	
	
}
