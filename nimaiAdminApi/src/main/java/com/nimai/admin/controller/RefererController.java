package com.nimai.admin.controller;

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

import com.nimai.admin.payload.CustomerResponse;
import com.nimai.admin.payload.PagedResponse;
import com.nimai.admin.payload.SearchRequest;
import com.nimai.admin.service.RefererService;

/*bashir*/
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/referer") 
public class RefererController {
	@Autowired
	RefererService refService;

	@PostMapping("/list")
	public PagedResponse<?> getAllRefererDetails(@RequestBody SearchRequest request) {
		return refService.getRefDetails(request);
	}

	@GetMapping("/userIdSearch/{userId}/{data}")
	public List<String> userIdSearch(@PathVariable(value = "userId") String userId,
			@PathVariable(value = "data") String data) {
		System.out.println("userIdSearch search :: " + userId);
		return refService.userIdSearch(userId, data);
	}

	@GetMapping("/emailIdSearch/{emailId}")
	public List<String> emailIdSearch(@PathVariable(value = "emailId") String emailId){
//			@PathVariable(value = "data") String data) {
		System.out.println("emailIdSearch search :: " + emailId);
		return refService.emailIdSearch(emailId);
	}

	@GetMapping("/mobileNumberSearch/{mobileNo}")
	public List<String> mobileNumberSearch(@PathVariable(value = "mobileNo") String mobileNo){
//			,
//			@PathVariable(value = "data") String data) {
		System.out.println("mobileNumberSearch search :: " + mobileNo);
		return refService.mobileNumberSearch(mobileNo);
//				data);
	}

	@GetMapping("/companyNameSearch/{companyName}")
	public List<String> companyNameSearch(@PathVariable(value = "companyName") String companyName){
//			@PathVariable(value = "data") String data) {
		System.out.println("companyNameSearch search :: " + companyName);
		//return refService.companyNameSearch(companyName, data);
		return refService.companyNameSearch(companyName);
	}
	
	@GetMapping("/bankNameSearch/{bankName}")
//			+ "/{data}")
	public List<String> bankNameSearch(@PathVariable(value = "bankName") String bankName){
//			,
//			@PathVariable(value = "data") String data) {
		System.out.println("bankNameSearch search :: " + bankName);
		//return refService.bankNameSearch(bankName, data);
		return refService.bankNameSearch(bankName);
	}

	@PostMapping("/referList")
	public PagedResponse<?> getAllReferDetails(@RequestBody SearchRequest request) {
		return refService.getAllReferDetails(request);
	}

	@GetMapping("/{id}")
	public ResponseEntity<CustomerResponse> getReferrerById(@PathVariable(value = "id") Integer id) {
		return refService.getReferrerById(id);
	}
}
