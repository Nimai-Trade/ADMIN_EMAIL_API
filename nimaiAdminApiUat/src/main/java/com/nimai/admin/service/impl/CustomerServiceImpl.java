package com.nimai.admin.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nimai.admin.model.NimaiEmailScheduler;
import com.nimai.admin.model.NimaiFKyc;
import com.nimai.admin.model.NimaiMCustomer;
import com.nimai.admin.model.NimaiMEmployee;
import com.nimai.admin.model.NimaiMLogin;
import com.nimai.admin.model.NimaiMpUserRole;
import com.nimai.admin.model.NimaiSubscriptionDetails;
import com.nimai.admin.payload.ApiResponse;
import com.nimai.admin.payload.AssignRmResponse;
import com.nimai.admin.payload.CustomerResponse;
import com.nimai.admin.payload.PagedResponse;
import com.nimai.admin.payload.SearchRequest;
import com.nimai.admin.repository.CustomerRepository;
import com.nimai.admin.repository.EmployeeRepository;
import com.nimai.admin.repository.KycRepository;
import com.nimai.admin.repository.NimaiEmailSchedulerRepository;
import com.nimai.admin.repository.TransactionsRepository;
import com.nimai.admin.repository.UserRepository;
import com.nimai.admin.repository.UserRoleRepository;
import com.nimai.admin.service.CustomerService;
import com.nimai.admin.specification.CustomerSearchSpecification;
import com.nimai.admin.specification.CustomerSpecification;
import com.nimai.admin.util.Utility;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	CustomerSpecification customerSpecification;

	@Autowired
	CustomerSearchSpecification customerSearchSpecification;

	@Autowired
	TransactionsRepository transactionsRepository;
	
	@Autowired
	CustomerRepository repo;

	@Autowired
	UserRoleRepository userRoleRepository;

	@Autowired
	UserRepository loginRepository;

	@Autowired
	EmployeeRepository empRepository;

	@Autowired
	NimaiEmailSchedulerRepository schRepo;

	@Autowired
	KycRepository kycRepo;

	private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

	@Override
	public PagedResponse<?> getAllCustomer(Map<String, String> data) {

		Pageable pageable = PageRequest.of(Integer.parseInt(data.get("page")), Integer.parseInt(data.get("size")),
				data.get("direction").equalsIgnoreCase("desc") ? Sort.by(data.get("sortBy")).descending()
						: Sort.by(data.get("sortBy")).ascending());
		// --Bashir changes
		Page<NimaiMCustomer> customerList;
		String countryNames = Utility.getUserCountry();
		if (countryNames != null && countryNames.equalsIgnoreCase("all")) {

		} else if (countryNames != null && !countryNames.equalsIgnoreCase("all")) {
			data.put("countryNames", countryNames);
		} else if (countryNames == null) {
			customerList = null;
		}
		if (data.get("status") != null && data.get("status").equalsIgnoreCase("Pending"))
			customerList = customerRepository.findAll(customerSpecification.getGrantRmFilter(data), pageable);
		else
			customerList = customerRepository.findAll(customerSpecification.getFilter(data), pageable);

		List<CustomerResponse> responses = customerList.map(cust -> {
			CustomerResponse response = new CustomerResponse();
			response.setSubscriberType(cust.getSubscriberType().toUpperCase());
			response.setUserid(cust.getUserid());
			response.setFirstName(cust.getFirstName());
			response.setLastName(cust.getLastName());
			response.setEmailAddress(cust.getEmailAddress());
			response.setMobileNumber(cust.getMobileNumber());
			response.setCountryName(cust.getCountryName());
			response.setLandline(cust.getLandline());
			response.setDesignation(cust.getDesignation());
			response.setCompanyName(cust.getCompanyName());
			response.setBusinessType(cust.getBusinessType());
			response.setBankName(cust.getBankName());
			response.setBranchName(cust.getBranchName());
			response.setSwiftCode(cust.getSwiftCode());
			response.setRegisteredCountry(cust.getRegisteredCountry());
			response.setTelephone(cust.getTelephone());
			response.setMinValueofLc(cust.getMinValueofLc());
			response.setRegistrationType(cust.getRegistrationType());
			response.setProvincename(cust.getProvincename());
			response.setAddress1(cust.getAddress1());
			response.setAddress2(cust.getAddress2());
			response.setAddress3(cust.getAddress3());
			response.setCity(cust.getCity());
			response.setPincode(cust.getPincode());
			response.setInsertedDate(cust.getInsertedDate());
			if (cust.getRmStatus() != null && cust.getRmStatus().equalsIgnoreCase("Pending")) {
				NimaiMEmployee employee = employeeRepository.findByEmpCode(cust.getRmId());
				response.setRelationshipManager(cust.getRmId());
				response.setRmFirstName(employee.getEmpName() + " " + employee.getEmpLastName());
				response.setRmApprovedBy(cust.getApprovedBy());
			}
			return response;
		}).getContent();

		return new PagedResponse<>(responses, customerList.getNumber(), customerList.getSize(),
				customerList.getTotalElements(), customerList.getTotalPages(), customerList.isLast());
	}

	@Override
	public ResponseEntity<?> updateRelationshipManager(Map<String, String> data) {
		NimaiMCustomer customer = customerRepository.getOne(data.get("userId"));
		if (customer.getApprovedBy() != null && customer.getApprovedBy().equalsIgnoreCase(Utility.getUserId())) {
			return new ResponseEntity<>(new ApiResponse(true, "You dont have the authority for this operation!!!"),
					HttpStatus.OK);
		}
		System.out.println(data);
		logger.info("data");
		NimaiMEmployee rmDetails = empRepository.findByEmpCode(data.get("rm"));
		NimaiEmailScheduler schData = new NimaiEmailScheduler();
		customer.setIsRmassigned(true);
		customer.setRmId(data.get("rm"));
		customer.setRmStatus(data.get("status"));
		customer.setApprovedBy(Utility.getUserId());
		customer.setModifiedDate(Utility.getSysDate());
		customerRepository.save(customer);
		if (customer.getRmStatus().equalsIgnoreCase("Active")) {
			/*
			 * changes done by dhiraj to send the notification to rm
			 */
			schData.setrMName(rmDetails.getEmpName());
			schData.setrMemailId(rmDetails.getEmpEmail());
			schData.setUserid(data.get("userId"));
			schData.setsPLanCountry(customer.getCountryName());

			if (data.get("userId").substring(0, 2).equalsIgnoreCase("CU")) {
				schData.setEvent("ASSIGN_NOTIFICATION_TO_RM");
				schData.setUserName(customer.getCompanyName());
			} else if (data.get("userId").substring(0, 2).equalsIgnoreCase("RE")) {
				schData.setEvent("ASSIGN_NOTIFICATION_TO_RM_RE");
				schData.setUserName(customer.getCompanyName());
			} else if (data.get("userId").substring(0, 2).equalsIgnoreCase("BC")) {
				schData.setEvent("ASSIGN_NOTIFICATION_TO_RM_BC");
				schData.setUserName(customer.getCompanyName());
			} else if (data.get("userId").substring(0, 2).equalsIgnoreCase("BA")) {
				schData.setEvent("ASSIGN_NOTIFICATION_TO_RM_BA");
				schData.setUserName(customer.getBankName());
			}

			schData.setEmailStatus("Pending");
			schData.setInsertedDate(Utility.getSysDate());
			schRepo.save(schData);
		}

		return new ResponseEntity<>(
				new ApiResponse(true,
						"Relationship Manager assigned to " + customer.getFirstName() + " " + customer.getLastName()),
				HttpStatus.CREATED);
	}

	@Override
	public Map<String, Map<String, List<AssignRmResponse>>> getRmList() {

		Map<String, Map<String, List<AssignRmResponse>>> value2 = new HashMap<>();
		/**
		 * Old Logic
		 */
//		List<NimaiMEmployee> empList = employeeRepository.findAll();
//		System.out.println(">>> empList >>> " + empList.size());
//		Map<String, List<NimaiMEmployee>> result = empList.stream()
//				.filter(e -> e.getStatus().equalsIgnoreCase("Active")
//						&& e.getNimaiMpUserRoleList().get(0).getRoleId().getRoleShortName().contains("RM"))
//				.collect(Collectors
//						.groupingBy(emp -> emp.getNimaiMpUserRoleList().get(0).getRoleId().getRoleShortName()));
//
//		for (Map.Entry<String, List<NimaiMEmployee>> entry : result.entrySet()) {
//			Map<String, List<AssignRmResponse>> value = new HashMap<String, List<AssignRmResponse>>();
//
//			System.out.println(entry.getKey() + ":" + entry.getValue());
//			for (NimaiMEmployee item : entry.getValue()) {
//				String[] stringArray = item.getCountry().split(",");
//				for (String val : stringArray) {
//					value.computeIfAbsent(val, k -> new ArrayList<AssignRmResponse>()).add(
//							new AssignRmResponse(item.getEmpCode(), item.getEmpName() + " " + item.getEmpLastName()));
//				}
//			}
//			value2.put(entry.getKey().toUpperCase(), value);
//		}

		/**
		 * New Logic
		 */
		List<NimaiMpUserRole> userRole = userRoleRepository.findAll();
		Map<String, List<NimaiMpUserRole>> result = userRole.stream()
				.filter(e -> e.getStatus().equalsIgnoreCase("Active") && e.getRoleId().getRoleShortName().contains("RM")
						&& e.getEmpCode().getStatus().equalsIgnoreCase("Active"))
				.collect(Collectors.groupingBy(emp -> emp.getRoleId().getRoleShortName()));

		for (Map.Entry<String, List<NimaiMpUserRole>> entry : result.entrySet()) {
			Map<String, List<AssignRmResponse>> value = new HashMap<String, List<AssignRmResponse>>();
			for (NimaiMpUserRole item : entry.getValue()) {
				String[] stringArray = item.getEmpCode().getCountry().split(",");
				for (String val : stringArray) {
					value.computeIfAbsent(val, k -> new ArrayList<AssignRmResponse>())
							.add(new AssignRmResponse(item.getEmpCode().getEmpCode(),
									item.getEmpCode().getEmpName() + " " + item.getEmpCode().getEmpLastName()));
				}
			}
			value2.put(entry.getKey().toUpperCase(), value);
		}

		return value2;
	}

	public String collectInPlanName(List<NimaiSubscriptionDetails> subscriptionList) {
		return subscriptionList.stream().filter(plan -> plan.getStatus().equalsIgnoreCase("INACTIVE"))
				.sorted(Comparator.comparingInt(NimaiSubscriptionDetails::getSplSerialNumber).reversed()).findFirst()
				.map(NimaiSubscriptionDetails::getSubscriptionName).get();

	}

	@Override
	public PagedResponse<?> getSearchCustomer(SearchRequest request) {
		System.out.println(" Search Request " + request);
		//request.setBankType("CUSTOMER");
		//request.setSubscriberType("CUSTOMER");
		
		Pageable pageable = null;
		request.setSortBy("inserted_date");
		if ((request.getUserId() != null && request.getTxtStatus() == null)
				|| (request.getEmailId() != null && request.getTxtStatus() == null)
				|| (request.getMobileNo() != null && request.getTxtStatus() == null)) {
			request.setSize(1);
			pageable = PageRequest.of(request.getPage(), request.getSize(),
					request.getDirection().equalsIgnoreCase("desc") ? Sort.by(request.getSortBy()).descending()
							: Sort.by(request.getSortBy()).ascending());
		} else if ((request.getTxtStatus() == null)) {
			pageable = (Pageable) PageRequest.of(request.getPage(), request.getSize(),
					request.getDirection().equalsIgnoreCase("desc")
							? Sort.by(new String[] { request.getSortBy() }).descending()
							: Sort.by(new String[] { request.getSortBy() }).ascending());
		} else if ((request.getTxtStatus().equalsIgnoreCase("Rejected"))) {
			request.setSortBy("nfk.INSERTED_DATE");
			pageable = (Pageable) PageRequest.of(request.getPage(), request.getSize(),
					request.getDirection().equalsIgnoreCase("desc")
							? Sort.by(new String[] { request.getSortBy() }).descending()
							: Sort.by(new String[] { request.getSortBy() }).ascending());

		} else {

			pageable = PageRequest.of(request.getPage(), request.getSize(),
					request.getDirection().equalsIgnoreCase("desc") ? Sort.by(request.getSortBy()).descending()
							: Sort.by(request.getSortBy()).ascending());
		}

		Page<NimaiMCustomer> customerList = null;
		NimaiMCustomer custDetails = new NimaiMCustomer();
		
		String countryNames = Utility.getUserCountry();
		System.out.println("countryNames: " + countryNames);
		if (countryNames != null && countryNames.equalsIgnoreCase("all") && request.getCountry() == null) {
			countryNames = "";
			final List<String> countryList = (List<String>) this.repo.getCountryList();
			for (final String country : countryList) {
				countryNames = countryNames + country + ",";
			}
			System.out.println("Country List: " + countryNames);
			request.setCountryNames(countryNames);
		} else if (countryNames != null && !countryNames.equalsIgnoreCase("all") && request.getCountry() == null) {
			request.setCountryNames(countryNames);
		}else if(countryNames!=null && request.getCountry()!=null) {
			request.setCountryNames(request.getCountry());
		} else if (countryNames == null && request.getCountry() == null) {
		}
		final List<String> value = Stream.of(request.getCountryNames().split(",", -1)).collect(Collectors.toList());
		System.out.println("Values BankService: " + value);
		System.out.println("BankType: "+request.getBankType());
		System.out.println("SubscriberType: "+request.getSubscriberType());
		
		
		if (request.getRole() != null && request.getRole().equalsIgnoreCase("Customer RM")) {
			request.setLoginUserId(Utility.getUserId());
			request.setRmStatus("Active");
		} else {
//			String countryNames = Utility.getUserCountry();
//			if (countryNames != null && countryNames.equalsIgnoreCase("all") && request.getCountry() == null) {
//
//			} else if (countryNames != null && !countryNames.equalsIgnoreCase("all") && request.getCountry() == null) {
//				request.setCountryNames(countryNames);
//			} else if (countryNames == null && request.getCountry() == null) {
//				customerList = null;
//			}
		}

		List<CustomerResponse> responses = new ArrayList<>();

		
		List<NimaiMCustomer> pendingRsponses;
		if (request.getRole().equalsIgnoreCase("Customer RM")) {
			String rmId = Utility.getUserId();
			System.out.println("===========RM userId" + rmId);
		//CustomerServiceImpl custList=new CustomerServiceImpl();
		customerList=retiriveListOnRMId(rmId,request,value,pageable);
		} else {
			if (request.getUserId() != null && request.getTxtStatus() == null) {
				logger.info(
						"************=============inside request.getUserId()============***********" + request.getUserId());
				customerList = customerRepository.getDetailsByUserId(request.getUserId(),value, pageable);
				
			} else if (request.getMobileNo() != null && request.getTxtStatus() == null) {
				System.out.println("========= Searching Customer By Mobile no =========");
				customerList = customerRepository.getDetailsByMobileNumber(request.getMobileNo(),value, pageable);

			}

			else if (request.getEmailId() != null && request.getTxtStatus() == null) {
				System.out.println("========= Searching Customer By Email ID =========");
				customerList = customerRepository.getDetailsByEmailId(request.getEmailId(),value, pageable);
			} else if (request.getCountry() != null && request.getTxtStatus() == null) {
				System.out.println("========= Searching Customer By Country =========");
				customerList = customerRepository.getDetailsByCountry(request.getCountry(),value, pageable);
			} else if (request.getCompanyName() != null && request.getTxtStatus() == null) {
				System.out.println("========= Searching Customer By Company name =========");
				customerList = customerRepository.getDetailsByCompanyName(request.getCompanyName(),value, pageable);
			} else if (request.getTxtStatus() == null || request.getTxtStatus().equalsIgnoreCase("all")) {
				// customerList =
				// customerRepository.findAll(customerSearchSpecification.getFilter(request),
				// pageable);
				System.out.println("========= Searching All Customer =========");
				customerList = customerRepository.getAllCustomerKYC(value,pageable);
			} else if (request.getTxtStatus().equalsIgnoreCase("Pending") && (request.getBankType()==null || request.getBankType().isEmpty()) && (request.getSubscriberType()==null || request.getSubscriberType().isEmpty()) ) {
				System.out.println("========= Searching Pending Customer KYC =========");
				customerList = customerRepository.getPendingCustomerKYC(value,pageable);
			} else if (request.getTxtStatus().equalsIgnoreCase("Approved")) {
				System.out.println("========= Searching Approved Customer KYC =========");
				customerList = customerRepository.getApprovedCustomerKYC(value,pageable);
			} else if (request.getTxtStatus().equalsIgnoreCase("Rejected")) {
				System.out.println("========= Searching Rejected Customer KYC =========");
				customerList = customerRepository.getRejectedCustomerKYC(value,pageable);
			} else if (request.getTxtStatus().equalsIgnoreCase("Not Uploaded")) {
				System.out.println("========= Searching Not Uploaded Customer KYC =========");
				customerList = customerRepository.getNotUploadCustomerKYC(value,pageable);
//				List<NimaiMCustomer> notUploadedRsponses = customerList.stream()
//						.filter(cust -> cust.getNimaiFKycList().size() == 0).collect(Collectors.toList());

			}else if((request.getBankType()==null || request.getBankType().isEmpty()) && request.getSubscriberType().equalsIgnoreCase("Customer")) {
				if(request.getTxtStatus().equalsIgnoreCase("PaymentPending"))
				{
					System.out.println("========= Searching PaymentPending Customer =========");
					customerList = customerRepository.getCuPendingCustomerPayment(value,pageable);
				}
				else if (request.getTxtStatus().equalsIgnoreCase("subexpiry")) {
					customerList = customerRepository.getSubscriptionExpiryCU(value, pageable);
					System.out.println(
							"************=============Subscription Expiry in 30 days============***********");
				}
				else if (request.getTxtStatus().equalsIgnoreCase("PaymentPendingUser")) {
					customerList = customerRepository.getPaymentPendingUserCU(value, pageable);
					System.out.println(
							"************=============PaymentPaymentUser============***********"
									+ request.getUserId());

				}
				else
				{
					System.out.println("========= Searching Customer =========");
					customerList = customerRepository.getCuPendingCustomerKYC(value,pageable);
				}
			}else if(request.getBankType().equalsIgnoreCase("Customer") && request.getSubscriberType().equalsIgnoreCase("Bank")) {
				if(request.getTxtStatus().equalsIgnoreCase("PaymentPending"))
				{
					System.out.println("========= Searching PaymentPending Bank As Customer =========");
					customerList = customerRepository.getBCuPendingCustomerPayment(value,pageable);
				}
				else if (request.getTxtStatus().equalsIgnoreCase("subexpiry")) {
					customerList = customerRepository.getSubscriptionExpiryBC(value, pageable);
					System.out.println(
							"************=============Subscription Expiry in 30 days============***********");
				}
				else if (request.getTxtStatus().equalsIgnoreCase("PaymentPendingUser")) {
					customerList = customerRepository.getPaymentPendingUserBC(value, pageable);
					System.out.println(
							"************=============PaymentPaymentUser============***********"
									+ request.getUserId());

				}
				else
				{
					System.out.println("========= Searching Bank As Customer =========");
					customerList = customerRepository.getBCuPendingCustomerKYC(value,pageable);
				}
			}
			else if (request.getTxtStatus().equalsIgnoreCase("Not Uploaded")) {
				
				if(request.getSubscriberType()==null &&  request.getBankType()==null) {
					 customerList = customerRepository.getNotUploadCustomerKYC(value,pageable);
				}else if(request.getSubscriberType().equals("CUSTOMER") && ( request.getBankType()==null) ) {
					customerList = customerRepository.getNotUploadForCU(value,pageable);
				}else if(request.getSubscriberType().equals("BANK") && request.getBankType().equals("CUSTOMER")) {
					System.out.println("inside khkj");
				     customerList = customerRepository.getNotUploadForBC(value,pageable);
				}
			}

		}

		// else
		responses = customerList.map(cust -> {
			CustomerResponse response = new CustomerResponse();
			System.out.println("request");
			response.setUserid(cust.getUserid());
			response.setFirstName(cust.getFirstName());
			response.setLastName(cust.getLastName());
			response.setMobileNumber(cust.getMobileNumber());
			response.setEmailAddress(cust.getEmailAddress());
			response.setCountryName(cust.getCountryName());
			response.setCompanyName(cust.getCompanyName());
			response.setPlanOfPayments(cust.getNimaiSubscriptionDetailsList().size() != 0
					? collectPlanName(cust.getNimaiSubscriptionDetailsList())
					: "No Active Plan");
			if (response.getPlanOfPayments().isEmpty() || response.getPlanOfPayments() == null) {
				response.setPlanOfPayments(
						("Latest Inactive_").concat(collectInPlanName(cust.getNimaiSubscriptionDetailsList())));
			}
//			response.setTotalTxn(transactionsRepository.countByUserId(cust.getUserid()));
			response.setTotalTxn(
					cust.getNimaiMmTransactionList() != null ? cust.getNimaiMmTransactionList().size() : 0);
			String userId = response.getUserid();
			System.out.println("==============useriD" + userId);
			List<NimaiFKyc> kycdetails = kycRepo.findByUserid(cust);

			if (kycdetails.size() == 0) {
				response.setKyc("Not Uploaded");
			} else {
				response.setKyc(cust.getKycStatus());
			}

			response.setRegisteredCountry(cust.getRegisteredCountry());
			return response;
		}).getContent();

		return new PagedResponse<>(responses, customerList.getNumber(), customerList.getSize(),
				customerList.getTotalElements(), customerList.getTotalPages(), customerList.isLast());

	}

	private Page<NimaiMCustomer> retiriveListOnRMId(String rmId, SearchRequest request, List<String> value, Pageable pageable) {
		Page<NimaiMCustomer> customerList = null;
		//Pageable pageable = null;
		// TODO Auto-generated method stub
		
	
		
		if (request.getUserId() != null && request.getTxtStatus() == null) {
			logger.info(
					"************=============insdie request.getUserId()============***********" + request.getUserId());
			customerList = customerRepository.getDetailsByUserIdRmId(rmId,request.getUserId(),value, pageable);
		
		} else if (request.getMobileNo() != null && request.getTxtStatus() == null) {
			customerList = customerRepository.getDetailsByMobileNumberRmId(request.getMobileNo(),rmId,value, pageable);

		}

		else if (request.getEmailId() != null && request.getTxtStatus() == null) {
			customerList = customerRepository.getDetailsByEmailIdRmId(request.getEmailId(),rmId,value, pageable);
		} else if (request.getCountry() != null && request.getTxtStatus() == null) {
			customerList = customerRepository.getDetailsByCountryRmId(request.getCountry(),rmId,value, pageable);
		} else if (request.getCompanyName() != null && request.getTxtStatus() == null) {
			customerList = customerRepository.getDetailsByCompanyNameRmId(request.getCompanyName(),rmId,value, pageable);
		} else if (request.getTxtStatus() == null || request.getTxtStatus().equalsIgnoreCase("all")) {
			// customerList =
			// customerRepository.findAll(customerSearchSpecification.getFilter(request),
			// pageable);
			System.out.println(rmId);
			System.out.println(pageable);
			try {
				customerList = customerRepository.getAllCustomerKYCRmid(rmId,value,pageable);
			}catch(Exception e){
				e.printStackTrace();
			}
			
			System.out.println(customerList.toString());
		} else if (request.getTxtStatus().equalsIgnoreCase("Pending")) {
			customerList = customerRepository.getPendingCustomerKYCRmId(rmId,value,pageable);
		} else if (request.getTxtStatus().equalsIgnoreCase("Approved")) {
			customerList = customerRepository.getApprovedCustomerKYCRmId(rmId,value,pageable);
		} else if (request.getTxtStatus().equalsIgnoreCase("Rejected")) {
			customerList = customerRepository.getRejectedCustomerKYCRmId(rmId,value,pageable);
		} else if (request.getTxtStatus().equalsIgnoreCase("Not Uploaded")) {
			customerList = customerRepository.getNotUploadCustomerKYCRmId(rmId,value,pageable);
//			List<NimaiMCustomer> notUploadedRsponses = customerList.stream()
//					.filter(cust -> cust.getNimaiFKycList().size() == 0).collect(Collectors.toList());

		}
		return customerList;
	}

	public String collectPlanName(List<NimaiSubscriptionDetails> subscriptionList) {
		return subscriptionList.stream().filter(plan -> plan.getStatus().equalsIgnoreCase("Active"))
				.sorted(Comparator.comparingInt(NimaiSubscriptionDetails::getSplSerialNumber).reversed())
				.map(NimaiSubscriptionDetails::getSubscriptionName).collect(Collectors.joining(" ")).toString();

	}

	@Override
	public ResponseEntity<?> removeSubsidiary(Map<String, String> data) {
		logger.debug(" Remove User ID : " + data.get("userId"));
		try {

			NimaiMLogin login = loginRepository.findByUserId(data.get("userId"));
			login.setStatus(data.get("status"));
			login.setLastModifiedBy(data.get("approverName"));
			login.setLastModifiedDate(new Date());
			NimaiMCustomer customer = login.getUserid();
			customer.setAccountStatus(data.get("status"));
			customer.setAccountRemark(data.get("remark"));
			customer.setModifiedDate(new Date());
			login.setUserid(customer);
			loginRepository.save(login);
			return new ResponseEntity<>(
					new ApiResponse(true, "Removed subsidiary successfully ..." + customer.getUserid()),
					HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("Error while updating kyc status - " + e.getMessage());
			return new ResponseEntity<>(new ApiResponse(true, "Error while updating kyc status."),
					HttpStatus.BAD_REQUEST);
		}
	}

}
