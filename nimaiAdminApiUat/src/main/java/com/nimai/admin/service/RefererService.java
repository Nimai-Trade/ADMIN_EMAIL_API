package com.nimai.admin.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.nimai.admin.payload.CustomerResponse;
import com.nimai.admin.payload.PagedResponse;
import com.nimai.admin.payload.SearchRequest;
/*bashir*/
public interface RefererService {

	PagedResponse<?> getRefDetails(SearchRequest request);

	List<String> userIdSearch(String userId, String data);

//	List<String> emailIdSearch(String emailId, String data);
	List<String> emailIdSearch(String emailId);

	//List<String> mobileNumberSearch(String mobileNo, String data);

	//List<String> companyNameSearch(String companyName, String data);

	PagedResponse<?> getAllReferDetails(SearchRequest request);

	ResponseEntity<CustomerResponse> getReferrerById(Integer id);

	//List<String> bankNameSearch(String bankName, String data);

	List<String> mobileNumberSearch(String mobileNo);

	List<String> companyNameSearch(String companyName);

	List<String> bankNameSearch(String bankName);

}
