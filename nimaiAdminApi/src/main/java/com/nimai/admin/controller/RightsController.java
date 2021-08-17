package com.nimai.admin.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nimai.admin.payload.PagedResponse;
import com.nimai.admin.payload.RightsRequest;
import com.nimai.admin.payload.RightsResponse;
import com.nimai.admin.service.RightsService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/rights")
public class RightsController {

	@Autowired
	RightsService rightsService;


	@PostMapping("/list")
	public PagedResponse<?> getAllRights(@RequestBody Map<String, String> data) {
		return rightsService.getAllRights(data);
	}

	@GetMapping("/{id}")
	public ResponseEntity<RightsResponse> getRightsById(@PathVariable(value = "id") Integer rightId) {
		return rightsService.getRightsById(rightId);
	}

	@PostMapping("/save")
	public ResponseEntity<?> createOrUpdateRights(@Valid @RequestBody RightsRequest rightsRequest) {
		return rightsService.createOrUpdateRights(rightsRequest);
	}

	@PostMapping("/updateRightStatus")
	public ResponseEntity<?> updateRightStatus(@RequestBody Map<String, String> data) {
		return rightsService.updateRightStatus(data.get("rightId"), data.get("status"));
	}
	
	@GetMapping("/checkShortName/{shortName}")
	public boolean checkShortName(@PathVariable(value = "shortName") String shortName){
		System.out.println("Check checkShortName exits " + shortName);
		return rightsService.checkShortName(shortName);
	}
	
}
