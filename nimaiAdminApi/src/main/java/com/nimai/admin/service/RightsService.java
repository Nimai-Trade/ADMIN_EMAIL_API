package com.nimai.admin.service;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;

import com.nimai.admin.payload.PagedResponse;
import com.nimai.admin.payload.RightsRequest;
import com.nimai.admin.payload.RightsResponse;

public interface RightsService {

	PagedResponse<?> getAllRights(Map<String, String> data);

	ResponseEntity<RightsResponse> getRightsById(Integer rightId);

	ResponseEntity<?> createOrUpdateRights(@Valid RightsRequest rightsRequest);
	
	ResponseEntity<?> updateRightStatus(String rightId, String status);

	boolean checkShortName(String shortName);


}
