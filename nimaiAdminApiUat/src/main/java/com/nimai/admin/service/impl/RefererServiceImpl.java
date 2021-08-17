package com.nimai.admin.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nimai.admin.model.NimaiMCustomer;
import com.nimai.admin.model.NimaiMEmployee;
import com.nimai.admin.model.NimaiMRefer;
import com.nimai.admin.payload.CustomerResponse;
import com.nimai.admin.payload.PagedResponse;
import com.nimai.admin.payload.SearchRequest;
import com.nimai.admin.repository.CustomerRepository;
import com.nimai.admin.repository.EmployeeRepository;
import com.nimai.admin.repository.ReferrerRepository;
import com.nimai.admin.service.RefererService;
import com.nimai.admin.specification.CustomerSearchSpecification;
import com.nimai.admin.specification.ReferrerSpecification;
import com.nimai.admin.util.Utility;

@Service
public class RefererServiceImpl implements RefererService {
	@Autowired
	ReferrerRepository referRepository;

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	CustomerSearchSpecification customerSearchSpecification;

	@Autowired
	ReferrerSpecification referSpecification;

	@Autowired
	EmployeeRepository employeeRepository;

	@Override
	public PagedResponse<?> getRefDetails(SearchRequest request) {
		request.setSubscriberType("REFERRER");
		Pageable pageable = PageRequest.of(request.getPage(), request.getSize(),
				request.getDirection().equalsIgnoreCase("desc") ? Sort.by(request.getSortBy()).descending()
						: Sort.by(request.getSortBy()).ascending());

		Page<NimaiMCustomer> referDetails;
		String countryNames = Utility.getUserCountry();
		if (countryNames != null && countryNames.equalsIgnoreCase("all") && request.getCountry() == null) {

		} else if (countryNames != null && !countryNames.equalsIgnoreCase("all") && request.getCountry() == null) {
			request.setCountryNames(countryNames);
		} else if (countryNames == null && request.getCountry() == null) {
			referDetails = null;
		}

		referDetails = customerRepository.findAll(customerSearchSpecification.getFilter(request), pageable);

		List<CustomerResponse> responses = referDetails.map(ref -> {
			CustomerResponse response = new CustomerResponse();
			response.setUserid(ref.getUserid());
			response.setFirstName(ref.getFirstName());
			response.setLastName(ref.getLastName());
			response.setEmailAddress(ref.getEmailAddress());
			response.setMobileNumber(ref.getMobileNumber());
			response.setCountryName(ref.getCountryName());
			response.setLandline(ref.getLandline());
			response.setDesignation(ref.getDesignation());
			response.setCompanyName(ref.getCompanyName());
			response.setKyc(ref.getKycStatus());
			response.setTotalReference(ref.getNimaiMReferList().size());
			int approve = 0;
			int reject = 0;
			for (int i = 0; i < ref.getNimaiMReferList().size(); i++) {
				NimaiMCustomer data = customerRepository
						.findByEmailAddress(ref.getNimaiMReferList().get(i).getEmailAddress().toLowerCase());
				if (data != null) {
					if (data.getKycStatus() != null && data.getKycStatus().equalsIgnoreCase("Approved")) {
						approve = approve + 1;
					} else if (data.getKycStatus() != null && data.getKycStatus().equalsIgnoreCase("rejected")) {
						reject = reject + 1;
					}
				}
			}

			response.setApprovedReference(approve);
			response.setRejectedReference(reject);
			response.setPendingReference(ref.getNimaiMReferList().size() - approve - reject);
			response.setInsertedDate(ref.getInsertedDate());

			response.setInsertedDate(ref.getInsertedDate());
			return response;
		}).getContent();

		return new PagedResponse<>(responses, referDetails.getNumber(), referDetails.getSize(),
				referDetails.getTotalElements(), referDetails.getTotalPages(), referDetails.isLast());

	}

	@Override
	public List<String> userIdSearch(String userId, String data) {
		String val = Utility.getUserCountry();
		if (!val.equalsIgnoreCase("All")) {
			List<String> list = Stream.of(val.split(",")).collect(Collectors.toList());
			return customerRepository.userIdDataSearchByCountry(userId.toLowerCase(), data, list);
		} else {
			return customerRepository.userIdDataSearch(userId.toLowerCase(), data);
		}
	}

	@Override
	public List<String> emailIdSearch(String emailId) {
//			String data) {
		String val = Utility.getUserCountry();
		if (!val.equalsIgnoreCase("All")) {
			List<String> list = Stream.of(val.split(",")).collect(Collectors.toList());
			// return customerRepository.emailIdDataSearchByCountry(emailId.toLowerCase(),
			// data, list);
			return customerRepository.emailIdDataSearchByCountry(emailId.toLowerCase(), list);
		} else {
//			return customerRepository.emailIdDataSearch(emailId.toLowerCase(), data);
			return customerRepository.emailIdDataSearch(emailId.toLowerCase());
		}
	}

	@Override
	public List<String> mobileNumberSearch(String mobileNo) {
//			, String data) {
		String val = Utility.getUserCountry();
		if (!val.equalsIgnoreCase("All")) {
			List<String> list = Stream.of(val.split(",")).collect(Collectors.toList());
			return customerRepository.mobileNumberDataSearchByCountry(mobileNo.toLowerCase(), list);
			// return
			// customerRepository.mobileNumberDataSearchByCountry(mobileNo.toLowerCase(),
			// data, list);
		} else {
			return customerRepository.mobileNumberDataSearch(mobileNo.toLowerCase());
			// return customerRepository.mobileNumberDataSearch(mobileNo.toLowerCase(),
			// data);
		}
	}

	@Override
	public List<String> companyNameSearch(String companyName) {
//			, String data) {
		String val = Utility.getUserCountry();
		if (!val.equalsIgnoreCase("All")) {
			List<String> list = Stream.of(val.split(",")).collect(Collectors.toList());
			return customerRepository.companyNameDataSearchByCountry(companyName.toLowerCase(), list);
			// return
			// customerRepository.companyNameDataSearchByCountry(companyName.toLowerCase(),
			// data, list);
		} else {
			return customerRepository.companyNameDataSearch(companyName.toLowerCase());
			// return customerRepository.companyNameDataSearch(companyName.toLowerCase(),
			// data);
		}
	}

	@Override
	public PagedResponse<?> getAllReferDetails(SearchRequest request) {
		List<CustomerResponse> responses = null;
		Pageable pageable = PageRequest.of(request.getPage(), request.getSize(),
				request.getDirection().equalsIgnoreCase("desc") ? Sort.by(request.getSortBy()).descending()
						: Sort.by(request.getSortBy()).ascending());

		Page<NimaiMRefer> referDetails = referRepository.findAll(referSpecification.getFilter(request), pageable);

		if (request.getTxtStatus().equalsIgnoreCase("all")) {
			responses = referDetails.map(ref -> {
				CustomerResponse response = new CustomerResponse();
				response.setFirstName(ref.getFirstName());
				response.setLastName(ref.getLastName());
				response.setEmailAddress(ref.getEmailAddress());
				response.setMobileNumber(ref.getMobileNo());
				response.setCountryName(ref.getCountryName());
				response.setCompanyName(ref.getCompanyName());
				response.setInsertedDate(ref.getInsertedDate());
				response.setAccountStatus(ref.getStatus());
				response.setReferId(ref.getId());
				return response;
			}).getContent();
		} else if (request.getTxtStatus().equalsIgnoreCase("approved")
				|| request.getTxtStatus().equalsIgnoreCase("rejected")) {
			responses = referDetails.map(ref -> {
				String customer = customerRepository.findKycByEmailAddress(ref.getEmailAddress().toLowerCase());
				if (customer != null && customer.equalsIgnoreCase(request.getTxtStatus())) {
					CustomerResponse response = new CustomerResponse();
					response.setFirstName(ref.getFirstName());
					response.setLastName(ref.getLastName());
					response.setEmailAddress(ref.getEmailAddress());
					response.setMobileNumber(ref.getMobileNo());
					response.setCountryName(ref.getCountryName());
					response.setCompanyName(ref.getCompanyName());
					response.setInsertedDate(ref.getInsertedDate());
					response.setAccountStatus(ref.getStatus());
					response.setReferId(ref.getId());
					return response;
				}
				return null;
			}).toList();
		} else if (request.getTxtStatus().equalsIgnoreCase("pending")) {
			responses = referDetails.map(ref -> {
				String customer = customerRepository.findKycByEmailAddress(ref.getEmailAddress().toLowerCase());
				if (customer == null || customer.equalsIgnoreCase(request.getTxtStatus())) {
					CustomerResponse response = new CustomerResponse();
					response.setFirstName(ref.getFirstName());
					response.setLastName(ref.getLastName());
					response.setEmailAddress(ref.getEmailAddress());
					response.setMobileNumber(ref.getMobileNo());
					response.setCountryName(ref.getCountryName());
					response.setCompanyName(ref.getCompanyName());
					response.setInsertedDate(ref.getInsertedDate());
					response.setAccountStatus(ref.getStatus());
					response.setReferId(ref.getId());
					return response;
				}
				return null;
			}).toList();
		}

		return new PagedResponse<>(responses, referDetails.getNumber(), referDetails.getSize(),
				referDetails.getTotalElements(), referDetails.getTotalPages(), referDetails.isLast());
	}

	@Override
	public ResponseEntity<CustomerResponse> getReferrerById(Integer id) {
		NimaiMRefer refer = referRepository.getOne(id);
		NimaiMCustomer customer = customerRepository.findByEmailAddress(refer.getEmailAddress().toLowerCase());

		if (refer != null) {
			CustomerResponse response = new CustomerResponse();
			response.setFirstName(refer.getFirstName());
			response.setLastName(refer.getLastName());
			response.setEmailAddress(refer.getEmailAddress());
			response.setMobileNumber(refer.getMobileNo());
			response.setCompanyName(refer.getCompanyName());
			response.setCountryName(refer.getCountryName());
			response.setInsertedDate(refer.getInsertedDate());

			if (customer != null) {
				response.setUserid(customer.getUserid());
				response.setSignUpDate(customer.getInsertedDate());
				response.setAccountStatus(customer.getKycStatus());
				response.setSubscriberType(customer.getSubscriberType());
				if (customer.getRmId() != null) {
					NimaiMEmployee emp = employeeRepository.findByEmpCode(customer.getRmId());
					response.setRmFirstName(emp.getEmpName());
					response.setRmLastName(emp.getEmpLastName());
					response.setRmDesignation(emp.getDesignation());
				}
			}
			return new ResponseEntity<CustomerResponse>(response, HttpStatus.OK);
		}
		return null;
	}

	@Override
	public List<String> bankNameSearch(String bankName) {
//			, String data) {
		String val = Utility.getUserCountry();
		if (!val.equalsIgnoreCase("All")) {
			List<String> list = Stream.of(val.split(",")).collect(Collectors.toList());
			// return customerRepository.bankNameDataSearchByCountry(bankName.toLowerCase(),
			// data, list);
			return customerRepository.bankNameDataSearchByCountry(bankName.toLowerCase(), list);
		} else {
			return customerRepository.bankNameDataSearch(bankName.toLowerCase());
			// return customerRepository.bankNameDataSearch(bankName.toLowerCase(), data);
		}
	}

}
