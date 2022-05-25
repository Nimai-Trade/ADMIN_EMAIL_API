package com.nimai.admin.controller;

import com.nimai.admin.payload.PagedResponse;
import com.nimai.admin.payload.QuotationDetailsResponse;
import com.nimai.admin.payload.SearchRequest;
import com.nimai.admin.payload.TransactionDetails;
import com.nimai.admin.service.TransactionsService;
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

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping({"/api/transaction"})
public class TransactionsController {
  @Autowired
  TransactionsService transactionsService;
  
  @PostMapping({"/list"})
  public PagedResponse<?> getAllTransaction(@RequestBody SearchRequest request) {
    return this.transactionsService.getAllTransaction(request);
  }
  
  @PostMapping({"/secondaryTrlist"})
  public PagedResponse<?> getSecondaryTransaction(@RequestBody SearchRequest request) {
    return this.transactionsService.getsecondaryTransaction(request);
  }
  
  @PostMapping({"/makerApprovedList"})
  public PagedResponse<?> getmakerTransaction(@RequestBody SearchRequest request) {
    return this.transactionsService.getmakerApprovedTransaction(request);
  }
  
  @GetMapping({"/userIdSearch/{userId}"})
  public List<String> userIdSearch(@PathVariable("userId") String userId) {
    System.out.println("userIdSearch search :: " + userId);
    return this.transactionsService.customerUserIdSearch(userId);
  }
  
  @GetMapping({"/emailIdSearch/{emailId}"})
  public List<String> emailIdSearch(@PathVariable("emailId") String emailId) {
    System.out.println("emailIdSearch search :: " + emailId);
    return this.transactionsService.emailIdSearch(emailId);
  }
  
  @GetMapping({"/mobileNumberSearch/{mobileNo}"})
  public List<String> mobileNumberSearch(@PathVariable("mobileNo") String mobileNo) {
    System.out.println("mobileNumberSearch search :: " + mobileNo);
    return this.transactionsService.mobileNumberSearch(mobileNo);
  }
  
  @GetMapping({"/companyNameSearch/{companyName}"})
  public List<String> companyNameSearch(@PathVariable("companyName") String companyName) {
    System.out.println("mobileNumberSearch search :: " + companyName);
    return this.transactionsService.companyNameSearch(companyName);
  }
  
  @GetMapping({"/countrySearch/{country}"})
  public List<String> countrySearch(@PathVariable("country") String country) {
    System.out.println("countrySearch search :: " + country);
    return this.transactionsService.countrySearch(country);
  }
  
  @GetMapping({"/details/{id}"})
  public ResponseEntity<TransactionDetails> getTransactionById(@PathVariable("id") String transactionId) {
    return this.transactionsService.getTransactionById(transactionId);
  }
  
  @PostMapping({"/quotesList"})
  public PagedResponse<?> getQuotesDetails(@RequestBody SearchRequest request) {
    return this.transactionsService.getQuotesDetails(request);
  }
  
  @GetMapping({"/quotes/{id}"})
  public ResponseEntity<QuotationDetailsResponse> getQuotesDetailsById(@PathVariable("id") Integer quotationId) {
    return this.transactionsService.getQuotesDetailsById(quotationId);
  }
}
