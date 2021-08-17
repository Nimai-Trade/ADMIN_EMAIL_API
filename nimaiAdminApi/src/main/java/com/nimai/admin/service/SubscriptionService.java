package com.nimai.admin.service;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;

import com.nimai.admin.model.NimaiMSubscriptionPlan;
import com.nimai.admin.payload.PagedResponse;
import com.nimai.admin.payload.SearchRequest;
import com.nimai.admin.payload.SubscriptionMPlanResponse;
import com.nimai.admin.payload.SubscriptionPlanUpdateRequest;

public interface SubscriptionService {

	ResponseEntity<?> updateSubsPlan(@Valid SubscriptionPlanUpdateRequest request);

	PagedResponse<?> getActiveSubsDetails(SearchRequest request);

	ResponseEntity<?> editSubscriptionPlan(NimaiMSubscriptionPlan request);

	ResponseEntity<?> deactivateSubsPlan(@Valid SubscriptionPlanUpdateRequest request);

	ResponseEntity<?> saveSubsPlan(SubscriptionMPlanResponse request);

	int updatePlan(@Valid SubscriptionPlanUpdateRequest request);

	PagedResponse<?> getAllPendingSubsDetails(SearchRequest request);

	PagedResponse<?> getAllSubsDetails(SearchRequest request);

	ResponseEntity<?> getSubscriptonDetailById(Integer subId);

	ResponseEntity<?> getPlanAmount(SearchRequest request);

}
