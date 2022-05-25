package com.nimai.admin.service;

import com.nimai.admin.payload.PagedResponse;
import com.nimai.admin.payload.QuotationDetailsResponse;
import com.nimai.admin.payload.SearchRequest;
import com.nimai.admin.payload.TransactionDetails;
import java.util.List;
import org.springframework.http.ResponseEntity;

public interface TransactionsService {
  PagedResponse<?> getAllTransaction(SearchRequest paramSearchRequest);
  
  PagedResponse<?> getmakerApprovedTransaction(SearchRequest paramSearchRequest);
  
  List<String> userIdSearch(String paramString);
  
  List<String> emailIdSearch(String paramString);
  
  List<String> mobileNumberSearch(String paramString);
  
  List<String> companyNameSearch(String paramString);
  
  List<String> countrySearch(String paramString);
  
  ResponseEntity<TransactionDetails> getTransactionById(String paramString);
  
  PagedResponse<?> getQuotesDetails(SearchRequest paramSearchRequest);
  
  ResponseEntity<QuotationDetailsResponse> getQuotesDetailsById(Integer paramInteger);
  
  List<String> customerUserIdSearch(String paramString);
  
  PagedResponse<?> getsecondaryTransaction(SearchRequest paramSearchRequest);
}
