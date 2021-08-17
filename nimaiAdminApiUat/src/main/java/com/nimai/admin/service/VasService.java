package com.nimai.admin.service;

import org.springframework.http.ResponseEntity;

import com.nimai.admin.payload.PagedResponse;
import com.nimai.admin.payload.SearchRequest;
import com.nimai.admin.payload.VasResponse;
import com.nimai.admin.payload.VasUpdateRequestBody;

public interface VasService {

	ResponseEntity<?> saveVasDetails(VasResponse tempVas);

	PagedResponse<?> getAllVasDetails(SearchRequest request);

	int updateVasDetails(VasUpdateRequestBody request);

	ResponseEntity<?> getVasDetailById(Integer vasId);

	ResponseEntity<?> checkerUpdate(VasUpdateRequestBody request);
}
