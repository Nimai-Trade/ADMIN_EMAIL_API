package com.nimai.admin.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.nimai.admin.payload.AssignRmResponse;
import com.nimai.admin.payload.PagedResponse;
import com.nimai.admin.payload.SearchRequest;

public interface CustomerService {

	PagedResponse<?> getAllCustomer(Map<String, String> data);

	ResponseEntity<?> updateRelationshipManager(Map<String, String> data);

	Map<String, Map<String, List<AssignRmResponse>>> getRmList();

	PagedResponse<?> getSearchCustomer(SearchRequest request);

	ResponseEntity<?> removeSubsidiary(Map<String, String> data);

}
