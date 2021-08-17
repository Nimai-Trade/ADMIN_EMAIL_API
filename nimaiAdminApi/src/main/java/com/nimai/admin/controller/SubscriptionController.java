package com.nimai.admin.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nimai.admin.model.NimaiMSubscriptionPlan;
import com.nimai.admin.payload.PagedResponse;
import com.nimai.admin.payload.SearchRequest;
import com.nimai.admin.payload.SubscriptionMPlanResponse;
import com.nimai.admin.payload.SubscriptionPlanUpdateRequest;
import com.nimai.admin.service.SubscriptionService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/subscription")
public class SubscriptionController {

	private static Logger logger = LoggerFactory.getLogger(SubscriptionController.class);
	
	@Autowired
	SubscriptionService masterSubsService;

	@PostMapping("/list")
	public PagedResponse<?> getAllSubsDetails(@RequestBody SearchRequest request) {
		return masterSubsService.getAllSubsDetails(request);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getSubscriptonDetailById(@PathVariable(value = "id") Integer subId) {
		return masterSubsService.getSubscriptonDetailById(subId);
	}

	@PostMapping("/save")
	public ResponseEntity<?> saveSubsPlanDetails(@RequestBody SubscriptionMPlanResponse request) {
		return masterSubsService.saveSubsPlan(request);
	}

	@PostMapping("/action")
	public ResponseEntity<?> approveSubsPlanDetails(@RequestBody SubscriptionPlanUpdateRequest request) {
		logger.debug("Subscription id"+String.valueOf(request.getSubscriptionPlanId()));
		return masterSubsService.updateSubsPlan(request);
	}

	@PostMapping("/approved/details")
	public PagedResponse<?> getAllActiveSubs(@RequestBody SearchRequest request) {
		return masterSubsService.getActiveSubsDetails(request);
	}

	@PutMapping("/edit")
	public ResponseEntity<?> editSubsPlan(@RequestBody NimaiMSubscriptionPlan request) {
		return masterSubsService.editSubscriptionPlan(request);
	}

	@PostMapping("/pending/details")
	public PagedResponse<?> getAllPendingSubs(@RequestBody SearchRequest request) {
		return masterSubsService.getAllPendingSubsDetails(request);
	}

	@PostMapping("/deactivate")
	public ResponseEntity<?> deactivatePlan(@Valid @RequestBody SubscriptionPlanUpdateRequest request) {
		return masterSubsService.deactivateSubsPlan(request);
	}

}
