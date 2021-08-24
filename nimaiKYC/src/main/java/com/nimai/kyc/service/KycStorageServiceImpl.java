package com.nimai.kyc.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.print.DocFlavor.INPUT_STREAM;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nimai.kyc.exception.ErrorDescription;
import com.nimai.kyc.exception.ResourceNotFoundException;
import com.nimai.kyc.model.GenericResponse;
import com.nimai.kyc.model.NimaiEmailScheduler;
import com.nimai.kyc.model.NimaiKyc;
import com.nimai.kyc.model.NimaiMCustomer;
import com.nimai.kyc.model.NimaiMEmployee;
import com.nimai.kyc.payload.BusinessKycList;
import com.nimai.kyc.payload.PersonalKycList;
import com.nimai.kyc.payload.kycBase64Request;
import com.nimai.kyc.repository.NimaiCustomerRepository;
import com.nimai.kyc.repository.NimaiEmailSchedularRepository;
import com.nimai.kyc.repository.NimaiEmployeeRepository;
import com.nimai.kyc.repository.NimaiKycBase64Repository;
import com.nimai.kyc.util.ModelMapper;
import com.nimai.kyc.util.kycValidator;

@Service
public class KycStorageServiceImpl implements KycStorageService {
	private static Logger logger = LoggerFactory.getLogger(KycStorageServiceImpl.class);

	@Autowired
	NimaiCustomerRepository nimaiCustomerRepository;

	@Autowired
	kycValidator kycValidator;

	@Autowired
	NimaiKycBase64Repository kycRepository;

	@Autowired
	NimaiEmployeeRepository employeeRepository;

	@Autowired
	NimaiEmailSchedularRepository schedularRepository;

	/*
	 * while uploading the kyc the kyc status will be 0 in nimaiFKYC as well as it
	 * will update the status on the nimaiCustomer with 0(customer upload the kyc)
	 */
	@Override
	public ResponseEntity<?> saveOrUpdatekycDetails(kycBase64Request kycRequest) {
		GenericResponse response = new GenericResponse();
		String errorString = this.kycValidator.kycRequestValidator(kycRequest);
		if (errorString.equalsIgnoreCase("success")) {
			NimaiMCustomer userId = nimaiCustomerRepository.findByUId(kycRequest.getUserId());

			// NimaiMEmployee

			List<NimaiKyc> kycDetailList = kycRepository.getKycDetailsByUserId(kycRequest.getUserId());
			if (userId != null) {
				String customerUserId = userId.getUserid();
				if (kycRequest.getBusinessDocumentList().size() > 0 || kycRequest.getBusinessDocumentList()==null) {
					List<NimaiKyc> kycList = kycRequest.getBusinessDocumentList().stream().map(mapper -> {
						NimaiKyc nimaiKycDoc = new NimaiKyc();
						nimaiKycDoc.setCustUserId(new NimaiMCustomer(customerUserId));
						nimaiKycDoc.setInsertedBy(userId.getFirstName());
						nimaiKycDoc = ModelMapper.convertEntityKycRequestToDbObj(mapper, nimaiKycDoc);
						return nimaiKycDoc;
					}).collect(Collectors.toList());
					kycRepository.saveAll(kycList);
					response.setMessage("Busines kyc save successfully");
				}
				if (kycRequest.getPersonalDocumentList().size() > 0 || kycRequest.getPersonalDocumentList()==null) {
					List<NimaiKyc> kycList = kycRequest.getPersonalDocumentList().stream().map(mapper -> {
						NimaiKyc nimaiKycDoc = new NimaiKyc();
						nimaiKycDoc.setCustUserId(new NimaiMCustomer(customerUserId));
						nimaiKycDoc.setInsertedBy(userId.getFirstName());
						nimaiKycDoc = ModelMapper.convertEntityKycRequestToDbObj(mapper, nimaiKycDoc);
						return nimaiKycDoc;
					}).collect(Collectors.toList());

					kycRepository.saveAll(kycList);
					response.setErrMessage("Personal kyc save successfully");
				}

				nimaiCustomerRepository.updateKycStatus("Pending", userId.getUserid());
				userId.setKycStatus("Pending");
				
				nimaiCustomerRepository.save(userId);
				NimaiEmailScheduler schedularData = new NimaiEmailScheduler();
				NimaiMEmployee employeeRmDetails = employeeRepository.getDetailsByRmId(userId.getRmId());
				try
				{
				String businessDocName = businessListInStringForm(kycRequest.getBusinessDocumentList())

						.replaceAll("\\[", "").replaceAll("\\]", "");
				if (businessDocName != null) {
					schedularData.setBusinessKycDoc(businessDocName);
				}
				
				String personalDocName = personalListInStringForm(kycRequest.getPersonalDocumentList())
						.replaceAll("\\[", "").replaceAll("\\]", "");
				if (personalDocName != null) {
					schedularData.setPersonalKycDoc(personalDocName);
				}
				}
				catch(Exception e)
				{
					schedularData.setBusinessKycDoc("");
					schedularData.setPersonalKycDoc("");
				}
				if (employeeRmDetails != null) {
					logger.error("RM ID is not assign to the customer");

					schedularData.setEmailId(employeeRmDetails.getEmpEmail());
					String rMId = Integer.toString(employeeRmDetails.getEmpId());
					schedularData.setBranchId(rMId);
					schedularData.setrMName(employeeRmDetails.getEmpName());
					schedularData.setUserid(customerUserId);
					schedularData.setUserName(userId.getFirstName());
					schedularData.setEmailId(userId.getEmailAddress());
					schedularData.setEvent("KYC_UPLOAD");
					schedularData.setEmailStatus("pending");

				} else {
					schedularData.setUserid(customerUserId);
					schedularData.setUserName(userId.getFirstName());
					schedularData.setEmailId(userId.getEmailAddress());
					schedularData.setEvent("KYC_UPLOAD");
					schedularData.setEmailStatus("pending");

				}
				schedularRepository.save(schedularData);

				response.setErrCode("EXE000");
				return new ResponseEntity<Object>(response, HttpStatus.OK);

			}
		} else {
			response.setErrCode("EXE000");
			response.setMessage(ErrorDescription.getDescription("EXE000") + errorString.toString());
			return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
		}

		response.setErrCode("User not registred");
		return new ResponseEntity<Object>(response, HttpStatus.OK);

	}

	@Override
	public ResponseEntity<Object> getKycDetailsByUserId(String userId) {
		logger.info("getKycByUserId is been called");
		List<NimaiKyc> kyc=kycRepository.getKycDetailsByUserId(userId);
	
		/*int kycRejectedBusCount=kycRepository.getRejectedBusinessKycDetailsCountByUserId(userId);
		int kycRejectedPerCount=kycRepository.getRejectedPersonalKycDetailsCountByUserId(userId);
		if(kycRejectedBusCount==1 && kycRejectedPerCount==1)
		{
			kyc = kycRepository.getOneBusinessKycDetailsByUserId(userId);
			kyc.addAll(kycRepository.getOnePersonalKycDetailsByUserId(userId));
		}
		else if(kycRejectedBusCount==0 && kycRejectedPerCount==1)
		{
			kyc = kycRepository.getOnePersonalKycDetailsByUserId(userId);
			//kyc.addAll(kycRepository.getOnePersonalKycDetailsByUserId(userId));
		}
		else if(kycRejectedBusCount==1 && kycRejectedPerCount==0)
		{
			kyc = kycRepository.getOneBusinessKycDetailsByUserId(userId);
			//kyc.addAll(kycRepository.getOnePersonalKycDetailsByUserId(userId));
		}
		else if(kycRejectedBusCount==0 && kycRejectedPerCount==2)
		{
			kyc = kycRepository.getOnePersonalKycDetailsByUserId(userId);
		}
		else 
		{
			kyc = kycRepository.getOneBusinessKycDetailsByUserId(userId);
			kyc.addAll(kycRepository.getOnePersonalKycDetailsByUserId(userId));
		}*/
		/*if(kycCount==1)
		{
			kyc = kycRepository.getOneKycDetailsByUserId(userId);
			kyc.addAll(kycRepository.getOneKycDetailsByUserId(userId));
		}
		else if(kycCount==2)
		{
			kyc = kycRepository.getKycDetailsByUserId(userId);
		}
		else
		{
			kyc = kycRepository.getOneKycDetailsByUserId(userId);
		}*/
		
		if (kyc.size() != 0) {

			Stream<Object> kycResponses = kyc.stream().map(kycdETAILS -> {
				return ModelMapper.mapKycDetailsToKycResponse(kycdETAILS);
			});

			return new ResponseEntity<Object>(kycResponses, HttpStatus.OK);
		} 
		else if(kyc.size() == 0)
		{
			GenericResponse response = new GenericResponse();
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
		else {
			logger.error("User with provided user id is not Present");
			throw new ResourceNotFoundException("No Kyc Details exist for given userId");
		}

	}

	@Override
	public ResponseEntity<Object> getLatestKycDetailsByUserId(String userId) {
		logger.info("getKycByUserId is been called");
		List<NimaiKyc> kyc=kycRepository.getLatestKycDetailsByUserId(userId);
	
		if (kyc.size() != 0) {

			Stream<Object> kycResponses = kyc.stream().map(kycdETAILS -> {
				return ModelMapper.mapKycDetailsToKycResponse(kycdETAILS);
			});

			return new ResponseEntity<Object>(kycResponses, HttpStatus.OK);
		} 
		else if(kyc.size() == 0)
		{
			GenericResponse response = new GenericResponse();
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
		else {
			logger.error("User with provided user id is not Present");
			throw new ResourceNotFoundException("No Kyc Details exist for given userId");
		}

	}
	
	private String businessListInStringForm(List<BusinessKycList> businessList) {
		ArrayList<String> docName = new ArrayList<>();
		if (businessList.size() > 0) {
			for (BusinessKycList businessKycList : businessList) {
				docName.add(businessKycList.getDocumentName());
			}
			return Arrays.asList(docName).toString();
		}
		return null;

	}

	private String personalListInStringForm(List<PersonalKycList> personalKycList) {
		ArrayList<String> docName = new ArrayList<>();
		if (personalKycList.size() > 0) {
			for (PersonalKycList businessKycList : personalKycList) {
				docName.add(businessKycList.getDocumentName());
			}
			return Arrays.asList(docName).toString();
		}
		return null;

	}

}
