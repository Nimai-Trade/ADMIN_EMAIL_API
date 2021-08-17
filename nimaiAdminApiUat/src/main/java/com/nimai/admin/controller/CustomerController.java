package com.nimai.admin.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nimai.admin.payload.AssignRmResponse;
import com.nimai.admin.payload.PagedResponse;
import com.nimai.admin.payload.SearchRequest;
import com.nimai.admin.service.CustomerService;

/**
 * 
 * @author sahadeo.naik
 *
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/customer")
public class CustomerController {

	@Autowired
	CustomerService customerService;

	@PostMapping("/list")
	public PagedResponse<?> getAllCustomer(@RequestBody Map<String, String> data) {
		System.out.println(data);
		return customerService.getAllCustomer(data);

	}

	@PostMapping("/updateRelationshipManager")
	public ResponseEntity<?> updateRelationshipManager(@RequestBody Map<String, String> data) {
		System.out.println(data);
		return customerService.updateRelationshipManager(data);

	}

	@GetMapping("/getRmList")
	public Map<String, Map<String, List<AssignRmResponse>>> getRmList() {
		System.out.println("getRmList ");
		return customerService.getRmList();
	}

	@PostMapping("/searchCustomerList")
	public PagedResponse<?> getSearchCustomer(@RequestBody SearchRequest request) {
		return customerService.getSearchCustomer(request);

	}

	@PostMapping("/removeSubsidiary")
	public ResponseEntity<?> removeSubsidiary(@RequestBody Map<String, String> data) {
		return customerService.removeSubsidiary(data);

	}

}
