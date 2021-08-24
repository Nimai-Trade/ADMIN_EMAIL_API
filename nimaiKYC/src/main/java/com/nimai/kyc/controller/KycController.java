package com.nimai.kyc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nimai.kyc.payload.BusinessKycList;
import com.nimai.kyc.payload.kycBase64Request;
import com.nimai.kyc.service.KycStorageService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/kyc")
public class KycController {

	private static final Logger logger = LoggerFactory.getLogger(KycController.class);
	@Autowired
	private KycStorageService fileStorageService;

	/*
	 * kyc status is keeping 0,1,2 o is for first time or update purpose, 1 is for
	 * approval of kyc,2 is for rejected by nimai team,while sending the data from
	 * front end documeny list array should contain business data in 0 index and
	 * personal data in 1 index
	 */
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping("/saveKycDoc")
	public ResponseEntity<?> uploadFileBase64(@RequestBody kycBase64Request kycRequest) {
		for (BusinessKycList list : kycRequest.getBusinessDocumentList()) {
			System.out.println(list.getEncodedFileContent());
		}
		return fileStorageService.saveOrUpdatekycDetails(kycRequest);

	}
	
	/**
	 * Getting particular userId KycDetails
	 * 
	 * @param request
	 * @return
	 */
	@GetMapping("kycList/{userId}")
	public ResponseEntity<Object> getKycDetailsById(@PathVariable(value = "userId") String userId) {
		logger.info("======================== Kyc Controller -> getKycDetailsById========================");
		
		return fileStorageService.getKycDetailsByUserId(userId);
	}
	
	@GetMapping("LatestKycList/{userId}")
	public ResponseEntity<Object> getLatestKycDetailsById(@PathVariable(value = "userId") String userId) {
		logger.info("======================== Kyc Controller -> getKycDetailsById========================");
		
		return fileStorageService.getLatestKycDetailsByUserId(userId);
	}

}
