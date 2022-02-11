package com.nimai.admin.service.impl;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.Tuple;
import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nimai.admin.controller.SubscriptionController;
import com.nimai.admin.exception.ResourceNotFoundException;
import com.nimai.admin.model.NimaiEmailScheduler;
import com.nimai.admin.model.NimaiMEmployee;
import com.nimai.admin.model.NimaiMRole;
import com.nimai.admin.model.NimaiMSubscriptionCountry;
import com.nimai.admin.model.NimaiMSubscriptionPlan;
import com.nimai.admin.payload.ApiResponse;
import com.nimai.admin.payload.CountryList;
import com.nimai.admin.payload.DiscountPlanResponse;
import com.nimai.admin.payload.PagedResponse;
import com.nimai.admin.payload.SearchRequest;
import com.nimai.admin.payload.SubscriptionMPlanResponse;
import com.nimai.admin.payload.SubscriptionPlanUpdateRequest;
import com.nimai.admin.repository.CustomerRepository;
import com.nimai.admin.repository.EmployeeRepository;
import com.nimai.admin.repository.MasterSubsPlanRepository;
import com.nimai.admin.repository.NimaiEmailSchedulerRepository;
import com.nimai.admin.repository.NimaiMSubscriptionCountryRepository;
import com.nimai.admin.repository.RoleRepository;
import com.nimai.admin.service.SubscriptionService;
import com.nimai.admin.specification.SubscriptionSpecification;
import com.nimai.admin.util.Utility;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {
	private static Logger logger = LoggerFactory.getLogger(SubscriptionServiceImpl.class);

	@Autowired
	SubscriptionSpecification subsspecification;

	@Autowired
	MasterSubsPlanRepository masterRepo;

	@Autowired
	EmployeeRepository employeeRepo;

	@Autowired
	NimaiEmailSchedulerRepository schRepo;
	
	@Autowired
	CustomerRepository repo;

	@Autowired
	RoleRepository roleRepo;
	
	@Autowired
	NimaiMSubscriptionCountryRepository sPlanCountryRepo;

	/**
	 * To fetch list of data
	 */
	@Override
	public PagedResponse<?> getAllSubsDetails(SearchRequest request) {
		
		request.setSortBy("inserted_date");
		Pageable pageable = PageRequest.of(request.getPage(), request.getSize(),
				request.getDirection().equalsIgnoreCase("desc") ? Sort.by(request.getSortBy()).descending()
						: Sort.by(request.getSortBy()).ascending());
		String countryNames = Utility.getUserCountry();
		System.out.println("CountryNames: "+countryNames);
		if (countryNames != null && countryNames.equalsIgnoreCase("all") && request.getCountry() == null) {
			countryNames = "";
			final List<String> countryList = (List<String>) this.repo.getCountryList();
			for (final String country : countryList) {
				countryNames = countryNames + country + ",";
			}
			System.out.println("Country List: " + countryNames);
			request.setCountryNames(countryNames);
		}else if(countryNames!=null && request.getCountry()!=null) {
			request.setCountryNames(request.getCountry());
		} else if (countryNames != null && !countryNames.equalsIgnoreCase("all") && request.getCountry() == null) {
			request.setCountryNames(countryNames);
		}
		final List<String> value = Stream.of(request.getCountryNames().split(",", -1)).collect(Collectors.toList());
		System.out.println("Countries:" + value);
		
		String query=null;
		List<NimaiMSubscriptionPlan> queryList=new ArrayList<>();
		List<SubscriptionMPlanResponse> allList=new ArrayList<>();
		Page<NimaiMSubscriptionPlan> subsList;
		subsList = masterRepo.getAllSubscriptionPlan(value,request.getCustomerType(), pageable);
	
 
	//	Page<NimaiMSubscriptionPlan> subsList = masterRepo.findAll(subsspecification.getFilter(request), pageable);
		List<SubscriptionMPlanResponse> responses = subsList.map(sub -> {
			SubscriptionMPlanResponse response = new SubscriptionMPlanResponse();
			response.setSubscriptionPlanId(sub.getSubscriptionPlanId());
			
			String countryName="";
			List<String> myList = new ArrayList<String>(Arrays.asList(sub.getCountryName().split(",")));
			
			 
		            if(myList.size()<=1) {
		            	response.setCountryName(sub.getCountryName());
		            }else {
		            	response.setCountryName("Multiple Countries");
		            }
		     
			
			
			response.setCountry(sub.getCountryName().split(","));
		//	response.setCountryName(sub.getCountryName());
			response.setCustomerType(sub.getCustomerType());
			response.setPlanName(sub.getPlanName());
			response.setCredits(sub.getCredits());
		response.setRm(sub.getRm());
			response.setSubsidiaries(sub.getSubsidiaries());
			response.setPricing(sub.getPricing());
			response.setValidity(sub.getValidity() + "");
			response.setStatus(sub.getStatus());
			response.setCreatedBy(sub.getCreatedBy());
			return response;
		}).getContent();
		
		
		return new PagedResponse<>(responses, subsList.getNumber(), subsList.getSize(), subsList.getTotalElements(),
				subsList.getTotalPages(), subsList.isLast());
	}

	/**
	 * Fetch details by ID
	 */
	@Override
	public ResponseEntity<?> getSubscriptonDetailById(Integer subscriptionPlanId) {
		NimaiMSubscriptionPlan sub = masterRepo.getOne(subscriptionPlanId);
		if (sub != null) {

			SubscriptionMPlanResponse response = new SubscriptionMPlanResponse();
			response.setSubscriptionPlanId(sub.getSubscriptionPlanId());
			response.setCountryName(sub.getCountryName());
			response.setCustomerType(sub.getCustomerType());
			response.setPlanName(sub.getPlanName());
			response.setCredits(sub.getCredits());
			List<CountryList> countryList=new ArrayList<CountryList>();
			List<NimaiMSubscriptionCountry> actualCountryList=sub.getSubscriptionCountry();
			for(NimaiMSubscriptionCountry country:actualCountryList) {
				CountryList listCountry=new CountryList();
				listCountry.setCountry(country.getCountry());
				countryList.add(listCountry);
			}
			response.setCountryList(countryList);
			response.setSubsidiaries(sub.getSubsidiaries());
			response.setRm(sub.getRm());
			response.setPricing(sub.getPricing());
			response.setValidity(sub.getValidity() + "");
			response.setStatus(sub.getStatus());
			response.setCreatedDate(sub.getCreatedDate());
			response.setCreatedBy(sub.getCreatedBy());
			response.setCustomerSupport(sub.getCustomerSupport());
			response.setRemark(sub.getRemark());
			response.setCountryCurrency(sub.getCurrency());

			return new ResponseEntity<SubscriptionMPlanResponse>(response, HttpStatus.OK);
		} else {
			throw new ResourceNotFoundException("No Subscription Details exist...");
		}
	}

	/**
	 * Save or update subscription plan
	 */
	@Override
	public ResponseEntity<?> saveSubsPlan(SubscriptionMPlanResponse request) {
		try {
			System.out.println("request created by" + request.getCreatedBy());
			NimaiMSubscriptionPlan plan = null;

			// List<NimaiMEmployee>
			// empDetails=employeeRepo.findEmpListByCountry(request.getCountryName(),accountStatus,
			// roleId);

			String msg = "";

			System.out.println("Sub ID :: " + request.getSubscriptionPlanId());
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat simpleformat = new SimpleDateFormat("ddMMyyHHmmss");
			String id=request.getCountry().toString();
			String subscriptionId = id.substring(1, 3) + simpleformat.format(cal.getTime());
			//String subscriptionId =request.getCountryName().substring(0, 3) + simpleformat.format(cal.getTime());
			if (request.getSubscriptionPlanId() != null) {
				plan = masterRepo.getOne(request.getSubscriptionPlanId());
				// plan.setCreatedBy(plan.getCreatedBy());
				plan.setModifiedBy(request.getModifiedBy());
				msg = "Subscription Plan updated successfully";
			} else {
				plan = new NimaiMSubscriptionPlan();
				plan.setSubscriptionId(subscriptionId);
				msg = "Subscription plan created successfully";

			}
			System.out.println(request.getCountry());
			 Arrays.sort(request.getCountry());
//			 List<String> arrCOuntry=Arrays.asList(request.getCountry());
//			   Collections.sort(arrCOuntry);
//			 
//                       
//			   String[] arr = arrCOuntry.toArray(new String[0]);
			 StringBuilder stringBuilder = new StringBuilder();
			for (int i = 0; i < request.getCountry().length; i++) {
				stringBuilder.append(request.getCountry()[i] + ",");
			}
			plan.setCountryName(stringBuilder.toString().substring(0, stringBuilder.length() - 1));
			
			//plan.setCountryName(request.getCountryName());
			plan.setCreatedBy(request.getCreatedBy());
			plan.setCustomerType(request.getCustomerType());
			plan.setPlanName(request.getPlanName());
			plan.setCredits(request.getCredits());
			plan.setSubsidiaries(request.getSubsidiaries());
			plan.setRm(request.getRm());
			plan.setPricing(request.getPricing());
			plan.setValidity(Integer.parseInt(request.getValidity()));
			plan.setStatus(request.getStatus());
			plan.setCreatedDate(request.getCreatedDate());
			plan.setCustomerSupport(request.getCustomerSupport());
			plan.setRemark(request.getRemark());
			plan.setCurrency(request.getCountryCurrency());
			plan.setStatus("Pending");
			masterRepo.save(plan);
			
			for (int i = 0; i < request.getCountry().length; i++) {
				System.out.println("subscription plan Id in integer"+plan.getSubscriptionPlanId());
				NimaiMSubscriptionCountry subCountry=new NimaiMSubscriptionCountry();
				subCountry.setCountry(request.getCountry()[i]);
				subCountry.setSubscriptionId(plan.getSubscriptionId());
				//subCountry.setSubscriptionPlanId(plan.getSubscriptionPlanId());
				subCountry.setsPLanId(plan);
				sPlanCountryRepo.save(subCountry);
			}
			
			
			
			
			System.out.println("insertedby" + plan.getCreatedBy());

			/*
			 * RMs entery will be in NimaiEmailScheduler table to send the email
			 * notification
			 */

			return new ResponseEntity<>(new ApiResponse(true, msg), HttpStatus.CREATED);
		} catch (Exception e) {
			System.out.println("Exception in Subscription Plan :" + e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<>(new ApiResponse(true, "Error due to some technical issue"),
					HttpStatus.EXPECTATION_FAILED);
		}
	}

	private int getEmpRoleId(String customerType) {
		// TODO Auto-generated method stub
		int roleId = 0;
		if (customerType.equalsIgnoreCase("CUSTOMER")) {
			String role = "Bank RM";
			NimaiMRole bankRoleDtails = roleRepo.getBankRoleId(role);
			roleId = bankRoleDtails.getRoleId();
			return roleId;
		} else if (customerType.equalsIgnoreCase("BANK") || customerType.equalsIgnoreCase("BANK AS CUSTOMER")) {
			String role = "Customer RM";
			NimaiMRole cuRoleDtails = roleRepo.getBankRoleId(role);
			roleId = cuRoleDtails.getRoleId();
			return roleId;
		}
		return 0;

	}

	/**
	 * Approve / Reject / Active / Inactive subscription plan
	 */
	@Override
	public ResponseEntity<?> updateSubsPlan(SubscriptionPlanUpdateRequest request) {
		logger.info("Inside the subscription service impl");
		try {
			String msg = "";
			NimaiMSubscriptionPlan plan = masterRepo.getOne(request.getSubscriptionPlanId());
			logger.info("plan details" + plan.toString());
			if (plan.getCreatedBy().equalsIgnoreCase(Utility.getUserId())) {
				return new ResponseEntity<>(new ApiResponse(false, "You dont have the authority for this operation!!!"),
						HttpStatus.OK);
			}
			logger.info("Admin user details" + Utility.getUserId());
			if (request.getStatus().equalsIgnoreCase("Approved")) {
				logger.info("INSIDE approved condition" + Utility.getUserId());
				plan.setStatus("Active");
				plan.setApprovedBy(request.getModifiedBy());
				plan.setApprovedDate(new Date());
				String designation = "RM";
				String accountStatus = "ACTIVE";
				int roleId = getEmpRoleId(request.getCustomerType());
				logger.info("=====roleId=====:" + roleId);
				if (roleId != 0) {
					List<Tuple> performanceList = employeeRepo.getEMpList(roleId, request.getCountryName());

					if (performanceList.size() > 0) {
						for (Tuple emp : performanceList) {
							System.out.println("====empDetails======== ::" + emp.toString());
							NimaiEmailScheduler schdata = new NimaiEmailScheduler();
							schdata.setSubscriptionName(plan.getPlanName());
							schdata.setSubscriptionAmount(String.valueOf(plan.getPricing()));
							schdata.setRelationshipManager(plan.getRm());
							schdata.setSubsidiaryUsers(String.valueOf(plan.getSubsidiaries()));
							schdata.setSubscriptionValidity(String.valueOf(plan.getValidity()));
							schdata.setsPLanLcCount(String.valueOf(plan.getCredits()));
							schdata.setrMName(
									(String) emp.get("EMP_FIRST_NAME") != null ? (String) emp.get("EMP_FIRST_NAME")
											: "");
							schdata.setrMemailId(
									(String) emp.get("EMP_EMAIL") != null ? (String) emp.get("EMP_EMAIL") : "");
							schdata.setsPLanCountry(request.getCountryName());
							schdata.setCustomerSupport(plan.getCustomerSupport());
							schdata.setSubscriptionId(plan.getSubscriptionId());
							schdata.setsPlanCurrency(plan.getCurrency());
							Calendar calnew = Calendar.getInstance();
							Date today = calnew.getTime();
							schdata.setInsertedDate(today);
							if (request.getCustomerType().equalsIgnoreCase("Customer")) {
								schdata.setEvent("CU_SPLAN_NOTIFICATON_TORM");
							} else if (request.getCustomerType().equalsIgnoreCase("BANK AS CUSTOMER")) {
								schdata.setEvent("BC_SPLAN_NOTIFICATON_TORM");
							} else if (request.getCustomerType().equalsIgnoreCase("BANK")) {
								schdata.setEvent("BAU_SPLAN_NOTIFICATON_TORM");
							}
//							if (request.getSubscriptionPlanId() != null) {
//								if (request.getCustomerType().equalsIgnoreCase("Customer")) {
//									schdata.setEvent("UPDATED_CU_SPLAN_NOTIFICATON_TORM");
//								} else if (request.getCustomerType().equalsIgnoreCase("BANK AS CUSTOMER")) {
//									schdata.setEvent("UPDATED_BC_SPLAN_NOTIFICATON_TORM");
//								} else if (request.getCustomerType().equalsIgnoreCase("BANK")) {
//									schdata.setEvent("UPDATED_BAU_SPLAN_NOTIFICATON_TORM");
//								}
//							}
							schdata.setEmailStatus("Pending");
							schRepo.save(schdata);

						}

					}
				} else {
					System.out.println("============role Id is not present");
				}

				msg = "Subscription Plan Approved for " + plan.getCountryName();
			} else if (request.getStatus().equalsIgnoreCase("Rejected")) {
				logger.info("INSIDE rejected condition" + Utility.getUserId());
				plan.setStatus(request.getStatus());
				plan.setApprovedBy(request.getModifiedBy());
				plan.setApprovedDate(new Date());
				msg = "Subscription Plan Rejected for " + plan.getCountryName();
			} else if (request.getStatus().equalsIgnoreCase("Active")) {
				logger.info("INSIDE Active condition" + Utility.getUserId());
				plan.setStatus(request.getStatus());
				msg = "Subscription Plan Active for " + plan.getCountryName();
			} else {
				logger.info("INSIDE else condition" + Utility.getUserId());
				plan.setStatus(request.getStatus());
				msg = "Subscription Plan Inactive for " + plan.getCountryName();
			}
			masterRepo.save(plan);
			return new ResponseEntity<>(new ApiResponse(true, msg), HttpStatus.CREATED);
		} catch (Exception e) {
			System.out.println("Exception in Subscription Plan update :" + e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<>(new ApiResponse(true, "Error due to some technical issue"),
					HttpStatus.EXPECTATION_FAILED);

		}
	}

	/**
	 * Need to check with bashir - below code required or not
	 */

	@Override
	public PagedResponse<?> getActiveSubsDetails(SearchRequest request) {
		Pageable pageable = PageRequest.of(request.getPage(), request.getSize(),
				request.getDirection().equalsIgnoreCase("desc") ? Sort.by(request.getSortBy()).descending()
						: Sort.by(request.getSortBy()).ascending());
		Page<NimaiMSubscriptionPlan> subsList = masterRepo.findAll(subsspecification.getFilter(request), pageable);

		List<SubscriptionMPlanResponse> responses = subsList.map(sub -> {
			SubscriptionMPlanResponse response = new SubscriptionMPlanResponse();
			response.setSubscriptionPlanId(sub.getSubscriptionPlanId());
			response.setCountryName(sub.getCountryName());
			response.setCustomerType(sub.getCustomerType());
			response.setPlanName(sub.getPlanName());
			response.setCredits(sub.getCredits());
			response.setSubsidiaries(sub.getSubsidiaries());
			response.setRm(sub.getRm());
			response.setPricing(sub.getPricing());
			response.setValidity(sub.getValidity() + "");
			response.setStatus(sub.getStatus());
			return response;
		}).getContent();

		return new PagedResponse<>(responses, subsList.getNumber(), subsList.getSize(), subsList.getTotalElements(),
				subsList.getTotalPages(), subsList.isLast());
	}

	public ResponseEntity<?> editSubscriptionPlan(NimaiMSubscriptionPlan request) throws ResourceNotFoundException {
		NimaiMSubscriptionPlan subscription = masterRepo.findById(request.getSubscriptionPlanId())
				.orElseThrow(() -> new ResourceNotFoundException(
						"Subscription Plan not found for this id :: " + request.getSubscriptionId()));
		subscription.setCountryName(request.getCountryName());
		subscription.setPlanName(request.getPlanName());
		subscription.setCredits(request.getCredits());
		subscription.setSubsidiaries(request.getSubsidiaries());
		subscription.setRm(request.getRm());
		subscription.setPricing(request.getPricing());
		subscription.setValidity(request.getValidity());

		final NimaiMSubscriptionPlan response = masterRepo.save(subscription);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Override
	@Transactional
	public ResponseEntity<?> deactivateSubsPlan(@Valid SubscriptionPlanUpdateRequest request) {
		if (masterRepo.deactivateSubsPlan(request.getSubscriptionPlanId(), request.getModifiedBy(),
				Utility.getSysDate()) > 0) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Error Occured While Deactivating plan", HttpStatus.EXPECTATION_FAILED);
		}
	}

	@Override
	public int updatePlan(@Valid SubscriptionPlanUpdateRequest request) {

		return masterRepo.updateTemp(request.getSubscriptionPlanId(), request.getStatus(), request.getModifiedBy(),
				request.getModifiedDate());
	}

	@Override
	public PagedResponse<?> getAllPendingSubsDetails(SearchRequest request) {
		Pageable pageable = PageRequest.of(request.getPage(), request.getSize(),
				request.getDirection().equalsIgnoreCase("desc") ? Sort.by(request.getSortBy()).descending()
						: Sort.by(request.getSortBy()).ascending());
		Page<NimaiMSubscriptionPlan> subsList = masterRepo.findByStatus(request.getStatus(), pageable);

		List<SubscriptionMPlanResponse> responses = subsList.map(sub -> {
			SubscriptionMPlanResponse response = new SubscriptionMPlanResponse();
			response.setSubscriptionPlanId(sub.getSubscriptionPlanId());
			response.setCountryName(sub.getCountryName());
			response.setCustomerType(sub.getCustomerType());
			response.setPlanName(sub.getPlanName());
			response.setCredits(sub.getCredits());
			response.setRm(sub.getRm());
			response.setSubsidiaries(sub.getSubsidiaries());
			response.setPricing(sub.getPricing());
			response.setValidity(sub.getValidity() + "");
			response.setStatus(sub.getStatus());
			return response;
		}).getContent();

		return new PagedResponse<>(responses, subsList.getNumber(), subsList.getSize(), subsList.getTotalElements(),
				subsList.getTotalPages(), subsList.isLast());
	}

	// returns PlanName and Amount to DiscountManagementApi
	@Override
	public ResponseEntity<?> getPlanAmount(SearchRequest request) {
		String[] arrayCOuntry=request.getDiscountCountry();
		StringBuilder stringBuilder = new StringBuilder();
		Arrays.sort(arrayCOuntry);
		for(int i=0;i<arrayCOuntry.length;i++) {
			stringBuilder.append(arrayCOuntry[i]+",");
		}
		
		String countryNames=(stringBuilder.toString().substring(0, stringBuilder.length() - 1));
		
		System.out.println("countryName"+countryNames);
		List<NimaiMSubscriptionPlan> subList = masterRepo.getPlanAmount(request.getCustomerType(),
				countryNames);		
		List<DiscountPlanResponse> dResp = new ArrayList<DiscountPlanResponse>();

		for (NimaiMSubscriptionPlan o : subList) {
			DiscountPlanResponse sp = new DiscountPlanResponse();
			sp.setPlanName(o.getPlanName());
			sp.setPricing(o.getPricing());
			dResp.add(sp);
		}
		return new ResponseEntity<>(dResp, HttpStatus.OK);
	}
	/* CUstomise pagination logic
	 * String query=null;
		List<NimaiMSubscriptionPlan> queryList=new ArrayList<>();
		List<SubscriptionMPlanResponse> allList=new ArrayList<>();
		Page<NimaiMSubscriptionPlan> subsList;
	//	subsList = masterRepo.getAllSubscriptionPlan(value,request.getCustomerType(), pageable);
		Page<SubscriptionMPlanResponse> pages = null;

	for(String countryValues:value) {
		System.out.println("country value"+countryValues);
		queryList = masterRepo.getAllSubscriptionPlanNew(countryValues,request.getCustomerType(),pageable);
	

		for(NimaiMSubscriptionPlan sub:queryList) 
	{
		SubscriptionMPlanResponse response = new SubscriptionMPlanResponse();
		response.setSubscriptionPlanId(sub.getSubscriptionPlanId());
		response.setCountry(sub.getCountryName().split(","));
	//	response.setCountryName(sub.getCountryName());
		response.setCustomerType(sub.getCustomerType());
		response.setPlanName(sub.getPlanName());
		response.setCredits(sub.getCredits());
		response.setRm(sub.getRm());
		response.setSubsidiaries(sub.getSubsidiaries());
		response.setPricing(sub.getPricing());
		response.setValidity(sub.getValidity() + "");
		response.setStatus(sub.getStatus());
		response.setCreatedBy(sub.getCreatedBy());
		allList.add( response);
		
	}
	}

 pages=new PageImpl<>(allList);


 Integer pagesize=request.getSize();
 Integer pageNUmber=request.getPage();

 if (pagesize==null || pagesize <= 0 || pagesize > allList.size())
	 pagesize = allList.size();
 int numPages = (int) Math.ceil((double)allList.size() / (double)pagesize);
 List<List<SubscriptionMPlanResponse>> pagesNew = new ArrayList<List<SubscriptionMPlanResponse>>(numPages);
 for (int pageNum = 0; pageNum < numPages;)
	 pagesNew.add(allList.subList(pageNum * pagesize, Math.min(++pageNum * pagesize, allList.size())));
 
 List<SubscriptionMPlanResponse> responseList=new ArrayList<SubscriptionMPlanResponse>();

 for(int i=0;i<pagesNew.size();i++) {
	
	 List<SubscriptionMPlanResponse> responseListNew=pagesNew.get(i);
	 for(SubscriptionMPlanResponse sub:responseListNew) {
		 SubscriptionMPlanResponse response = new SubscriptionMPlanResponse();
			response.setSubscriptionPlanId(sub.getSubscriptionPlanId());
			 StringJoiner joiner = new StringJoiner("");
			  for(int j = 0; i < sub.getCountry().length; j++) {
			         joiner.add(sub.getCountry()[i]);
			      }
			      String str = joiner.toString();
			//response.setCountry(str);
			response.setCountryName(str);
			response.setCustomerType(sub.getCustomerType());
			response.setPlanName(sub.getPlanName());
			response.setCredits(sub.getCredits());
			response.setRm(sub.getRm());
			response.setSubsidiaries(sub.getSubsidiaries());
			response.setPricing(sub.getPricing());
			response.setValidity(sub.getValidity() + "");
			response.setStatus(sub.getStatus());
			response.setCreatedBy(sub.getCreatedBy());
			responseList.add(response);
	 }

 }
 	return new PagedResponse<>(responseList, pages.getNumber(), pages.getSize(), pages.getTotalElements(),
				pages.getTotalPages(), pages.isLast());
 
	 *  
	 *  
	 *  
	 *  
	 *  */
}
