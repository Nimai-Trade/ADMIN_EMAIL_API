package com.nimai.kyc.util;

import java.util.Base64;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.nimai.kyc.model.NimaiEmailScheduler;
import com.nimai.kyc.model.NimaiKyc;
import com.nimai.kyc.payload.BusinessKycList;
import com.nimai.kyc.payload.PersonalKycList;
import com.nimai.kyc.payload.kycResponseList;
import com.nimai.kyc.repository.NimaiCustomerRepository;
import com.nimai.kyc.repository.NimaiKycBase64Repository;

public class ModelMapper {

	@Autowired
	NimaiCustomerRepository nimaiCustomerRepository;

	@Autowired
	NimaiKycBase64Repository kycRepository;

	public static NimaiKyc convertEntityKycRequestToDbObj(BusinessKycList mapper, NimaiKyc nimaiKycDoc) {
		Calendar cal = Calendar.getInstance();
		Date today = cal.getTime();
		nimaiKycDoc.setCountry(mapper.getCountry());
		nimaiKycDoc.setTitle("Business");
		nimaiKycDoc.setEncodedFileContent(mapper.getEncodedFileContent());
		nimaiKycDoc.setInsertedDate(today);
		nimaiKycDoc.setDocumentName(mapper.getDocumentName());
		nimaiKycDoc.setDocumentType(mapper.getDocumentType());
		nimaiKycDoc.setKycStatus("Pending");

		return nimaiKycDoc;

	}

	public String EncrtyptedData(String text) {
		text = Base64.getEncoder().encodeToString(text.getBytes()).toString();
		return text;
	}

	public static NimaiKyc convertEntityKycRequestToDbObj(PersonalKycList mapper, NimaiKyc nimaiKycDoc) {
		Calendar cal = Calendar.getInstance();
		Date today = cal.getTime();
		nimaiKycDoc.setCountry(mapper.getCountry());
		nimaiKycDoc.setTitle("Personal");
		nimaiKycDoc.setEncodedFileContent(mapper.getEncodedFileContent());
		nimaiKycDoc.setInsertedDate(today);
		nimaiKycDoc.setDocumentName(mapper.getDocumentName());
		nimaiKycDoc.setDocumentType(mapper.getDocumentType());
		nimaiKycDoc.setKycStatus("Pending");

		return nimaiKycDoc;
	}

	public static Object mapKycDetailsToKycResponse(NimaiKyc kycDetails) {
		kycResponseList kycResponse = new kycResponseList();
		kycResponse.setKycStatus(kycDetails.getKycStatus());
		kycResponse.setApprovedBy(kycDetails.getApprovedBy());
		kycResponse.setApprovedDate(kycDetails.getApprovedDate());
		kycResponse.setCheckerComment(kycDetails.getCheckerComment());
		kycResponse.setReason(kycDetails.getReason());
		kycResponse.setCustUserId(kycDetails.getCustUserId().getUserid());
		kycResponse.setDocumentName(kycDetails.getDocumentName());
		kycResponse.setDocumentType(kycDetails.getDocumentType());
		kycResponse.setEncodedFileContent(kycDetails.getEncodedFileContent());
		kycResponse.setCountry(kycDetails.getCountry());
		kycResponse.setTitle(kycDetails.getTitle());
		kycResponse.setInsertedDate(kycDetails.getInsertedDate());
		kycResponse.setModifiedDate(kycDetails.getModifiedDate());
		kycResponse.setComment(kycDetails.getComment().substring(0,kycDetails.getComment().indexOf("_")));
		return kycResponse;
	}

	
	
	
	
	}


