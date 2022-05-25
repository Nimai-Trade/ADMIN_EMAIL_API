package com.nimai.admin.service.impl;

import com.nimai.admin.exception.ResourceNotFoundException;
import com.nimai.admin.model.NimaiEmailScheduler;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.persistence.Tuple;
import javax.transaction.Transactional;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
  
  public PagedResponse<?> getAllSubsDetails(SearchRequest request) {
    request.setSortBy("inserted_date");
    PageRequest pageRequest = PageRequest.of(request.getPage(), request.getSize(), 
        request.getDirection().equalsIgnoreCase("desc") ? Sort.by(new String[] { request.getSortBy() }).descending() : 
        Sort.by(new String[] { request.getSortBy() }).ascending());
    String countryNames = Utility.getUserCountry();
    System.out.println("CountryNames: " + countryNames);
    if (countryNames != null && countryNames.equalsIgnoreCase("all") && request.getCountry() == null) {
      countryNames = "";
      List<String> countryList = this.repo.getCountryList();
      for (String country : countryList)
        countryNames = countryNames + country + ","; 
      System.out.println("Country List: " + countryNames);
      request.setCountryNames(countryNames);
    } else if (countryNames != null && request.getCountry() != null) {
      String countryWiseFilter = "";
      if (!countryNames.equalsIgnoreCase("All") && request.getCountry() != null) {
        List<String> countryList = new ArrayList<>(Arrays.asList(countryNames.split(",")));
        for (String country : countryList) {
          System.out.println("CountryNames: countryNames != null && request.getCountry() != null" + country);
          System.out.println("CountryNames:1" + country);
          System.out.println("CountryNames:2" + request.getCountry());
          if (country.equalsIgnoreCase(request.getCountry())) {
            System.out.println("CountryNames:2A" + request.getCountry());
            countryWiseFilter = country;
            break;
          } 
          System.out.println("CountryNames NA:3" + request.getCountry());
          countryWiseFilter = "NA";
        } 
        request.setCountryNames(countryWiseFilter);
      } else if (countryNames.equalsIgnoreCase("All") && request.getCountry() != null) {
        System.out.println("INSIDE second condition else if " + countryNames);
        request.setCountryNames(request.getCountry());
      } else {
        countryNames = "";
        List<String> countryList = this.repo.getCountryList();
        for (String country : countryList)
          countryNames = countryNames + country + ","; 
        System.out.println("Country List: " + countryNames);
        request.setCountryNames(countryNames);
      } 
    } else if (countryNames != null && !countryNames.equalsIgnoreCase("all") && request.getCountry() == null) {
      request.setCountryNames(countryNames);
    } 
    List<String> value = (List<String>)Stream.<String>of(request.getCountryNames().split(",", -1)).collect(Collectors.toList());
    System.out.println("Countries:" + value);
    String query = null;
    List<NimaiMSubscriptionPlan> queryList = new ArrayList<>();
    List<SubscriptionMPlanResponse> allList = new ArrayList<>();
    Page<NimaiMSubscriptionPlan> subsList = this.masterRepo.getAllSubscriptionPlan(value, request.getCustomerType(), (Pageable)pageRequest);
    System.out.println(subsList.toString());
    List<SubscriptionMPlanResponse> responses = subsList.map(sub -> {
          SubscriptionMPlanResponse response = new SubscriptionMPlanResponse();
          response.setSubscriptionPlanId(Integer.valueOf(sub.getSubscriptionPlanId()));
          String countryName = "";
          List<String> myList = new ArrayList<>(Arrays.asList(sub.getCountryName().split(",")));
          if (sub.getCountryName().equalsIgnoreCase("All")) {
            response.setCountryName("All");
          } else if (myList.size() <= 1) {
            response.setCountryName(sub.getCountryName());
          } else {
            response.setCountryName("Multiple Countries");
          } 
          response.setCountry(sub.getCountryName().split(","));
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
    return new PagedResponse(responses, subsList.getNumber(), subsList.getSize(), subsList.getTotalElements(), subsList
        .getTotalPages(), subsList.isLast());
  }
  
  public ResponseEntity<?> getSubscriptonDetailById(Integer subscriptionPlanId) {
    NimaiMSubscriptionPlan sub = (NimaiMSubscriptionPlan)this.masterRepo.getOne(subscriptionPlanId);
    if (sub != null) {
      SubscriptionMPlanResponse response = new SubscriptionMPlanResponse();
      response.setSubscriptionPlanId(Integer.valueOf(sub.getSubscriptionPlanId()));
      response.setCountryName(sub.getCountryName());
      response.setCustomerType(sub.getCustomerType());
      response.setPlanName(sub.getPlanName());
      response.setCredits(sub.getCredits());
      List<CountryList> countryList = new ArrayList<>();
      List<NimaiMSubscriptionCountry> actualCountryList = sub.getSubscriptionCountry();
      actualCountryList.stream().distinct().collect(Collectors.toList());
      for (NimaiMSubscriptionCountry country : actualCountryList) {
        CountryList listCountry = new CountryList();
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
      return new ResponseEntity(response, HttpStatus.OK);
    } 
    throw new ResourceNotFoundException("No Subscription Details exist...");
  }
  
  public ResponseEntity<?> saveSubsPlan(SubscriptionMPlanResponse request) {
    try {
      System.out.println("request created by" + request.getCreatedBy());
      NimaiMSubscriptionPlan plan = null;
      String msg = "";
      System.out.println("Sub ID :: " + request.getSubscriptionPlanId());
      Calendar cal = Calendar.getInstance();
      SimpleDateFormat simpleformat = new SimpleDateFormat("ddMMyyHHmmss");
      String id = request.getCountry().toString();
      String subscriptionId = id.substring(1, 3) + simpleformat.format(cal.getTime());
      if (request.getSubscriptionPlanId() != null) {
        plan = (NimaiMSubscriptionPlan)this.masterRepo.getOne(request.getSubscriptionPlanId());
        plan.setModifiedBy(request.getModifiedBy());
        this.sPlanCountryRepo.deleteSplanCountryById(plan.getSubscriptionPlanId());
        msg = "Subscription Plan updated successfully";
      } else {
        plan = new NimaiMSubscriptionPlan();
        plan.setSubscriptionId(subscriptionId);
        msg = "Subscription plan created successfully";
      } 
      System.out.println(request.getCountry());
      Arrays.sort((Object[])request.getCountry());
      StringBuilder stringBuilder = new StringBuilder();
      int i;
      for (i = 0; i < (request.getCountry()).length; i++)
        stringBuilder.append(request.getCountry()[i] + ","); 
      plan.setCountryName(stringBuilder.toString().substring(0, stringBuilder.length() - 1));
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
      this.masterRepo.save(plan);
      for (i = 0; i < (request.getCountry()).length; i++) {
        System.out.println("subscription plan Id in integer" + plan.getSubscriptionPlanId());
        NimaiMSubscriptionCountry subCountry = new NimaiMSubscriptionCountry();
        subCountry.setCountry(request.getCountry()[i]);
        subCountry.setSubscriptionId(plan.getSubscriptionId());
        subCountry.setsPLanId(plan);
        this.sPlanCountryRepo.save(subCountry);
      } 
      System.out.println("insertedby" + plan.getCreatedBy());
      return new ResponseEntity(new ApiResponse(Boolean.valueOf(true), msg), HttpStatus.CREATED);
    } catch (Exception e) {
      System.out.println("Exception in Subscription Plan :" + e.getMessage());
      e.printStackTrace();
      return new ResponseEntity(new ApiResponse(Boolean.valueOf(true), "Error due to some technical issue"), HttpStatus.EXPECTATION_FAILED);
    } 
  }
  
  private int getEmpRoleId(String customerType) {
    int roleId = 0;
    if (customerType.equalsIgnoreCase("CUSTOMER")) {
      String role = "Bank RM";
      NimaiMRole bankRoleDtails = this.roleRepo.getBankRoleId(role);
      roleId = bankRoleDtails.getRoleId().intValue();
      return roleId;
    } 
    if (customerType.equalsIgnoreCase("BANK") || customerType.equalsIgnoreCase("BANK AS CUSTOMER")) {
      String role = "Customer RM";
      NimaiMRole cuRoleDtails = this.roleRepo.getBankRoleId(role);
      roleId = cuRoleDtails.getRoleId().intValue();
      return roleId;
    } 
    return 0;
  }
  
  public ResponseEntity<?> updateSubsPlan(SubscriptionPlanUpdateRequest request) {
    logger.info("Inside the subscription service impl");
    try {
      String msg = "";
      NimaiMSubscriptionPlan plan = (NimaiMSubscriptionPlan)this.masterRepo.getOne(Integer.valueOf(request.getSubscriptionPlanId()));
      logger.info("plan details" + plan.toString());
      if (plan.getCreatedBy().equalsIgnoreCase(Utility.getUserId()))
        return new ResponseEntity(new ApiResponse(Boolean.valueOf(false), "You dont have the authority for this operation!!!"), HttpStatus.OK); 
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
          List<Tuple> performanceList = this.employeeRepo.getEMpList(roleId, request.getCountryName());
          if (performanceList.size() > 0)
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
                  ((String)emp.get("EMP_FIRST_NAME") != null) ? (String)emp.get("EMP_FIRST_NAME") : "");
              schdata.setrMemailId(
                  ((String)emp.get("EMP_EMAIL") != null) ? (String)emp.get("EMP_EMAIL") : "");
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
              schdata.setEmailStatus("Pending");
              this.schRepo.save(schdata);
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
      this.masterRepo.save(plan);
      return new ResponseEntity(new ApiResponse(Boolean.valueOf(true), msg), HttpStatus.CREATED);
    } catch (Exception e) {
      System.out.println("Exception in Subscription Plan update :" + e.getMessage());
      e.printStackTrace();
      return new ResponseEntity(new ApiResponse(Boolean.valueOf(true), "Error due to some technical issue"), HttpStatus.EXPECTATION_FAILED);
    } 
  }
  
  public PagedResponse<?> getActiveSubsDetails(SearchRequest request) {
    PageRequest pageRequest = PageRequest.of(request.getPage(), request.getSize(), 
        request.getDirection().equalsIgnoreCase("desc") ? Sort.by(new String[] { request.getSortBy() }).descending() : 
        Sort.by(new String[] { request.getSortBy() }).ascending());
    Page<NimaiMSubscriptionPlan> subsList = this.masterRepo.findAll(this.subsspecification.getFilter(request), (Pageable)pageRequest);
    List<SubscriptionMPlanResponse> responses = subsList.map(sub -> {
          SubscriptionMPlanResponse response = new SubscriptionMPlanResponse();
          response.setSubscriptionPlanId(Integer.valueOf(sub.getSubscriptionPlanId()));
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
    return new PagedResponse(responses, subsList.getNumber(), subsList.getSize(), subsList.getTotalElements(), subsList
        .getTotalPages(), subsList.isLast());
  }
  
  public ResponseEntity<?> editSubscriptionPlan(NimaiMSubscriptionPlan request) throws ResourceNotFoundException {
    NimaiMSubscriptionPlan subscription = (NimaiMSubscriptionPlan)this.masterRepo.findById(Integer.valueOf(request.getSubscriptionPlanId())).orElseThrow(() -> new ResourceNotFoundException("Subscription Plan not found for this id :: " + request.getSubscriptionId()));
    subscription.setCountryName(request.getCountryName());
    subscription.setPlanName(request.getPlanName());
    subscription.setCredits(request.getCredits());
    subscription.setSubsidiaries(request.getSubsidiaries());
    subscription.setRm(request.getRm());
    subscription.setPricing(request.getPricing());
    subscription.setValidity(request.getValidity());
    NimaiMSubscriptionPlan response = (NimaiMSubscriptionPlan)this.masterRepo.save(subscription);
    return new ResponseEntity(response, HttpStatus.OK);
  }
  
  @Transactional
  public ResponseEntity<?> deactivateSubsPlan(@Valid SubscriptionPlanUpdateRequest request) {
    if (this.masterRepo.deactivateSubsPlan(request.getSubscriptionPlanId(), request.getModifiedBy(), 
        Utility.getSysDate()) > 0)
      return new ResponseEntity(HttpStatus.OK); 
    return new ResponseEntity("Error Occured While Deactivating plan", HttpStatus.EXPECTATION_FAILED);
  }
  
  public int updatePlan(@Valid SubscriptionPlanUpdateRequest request) {
    return this.masterRepo.updateTemp(request.getSubscriptionPlanId(), request.getStatus(), request.getModifiedBy(), request
        .getModifiedDate());
  }
  
  public PagedResponse<?> getAllPendingSubsDetails(SearchRequest request) {
    PageRequest pageRequest = PageRequest.of(request.getPage(), request.getSize(), 
        request.getDirection().equalsIgnoreCase("desc") ? Sort.by(new String[] { request.getSortBy() }).descending() : 
        Sort.by(new String[] { request.getSortBy() }).ascending());
    Page<NimaiMSubscriptionPlan> subsList = this.masterRepo.findByStatus(request.getStatus(), (Pageable)pageRequest);
    List<SubscriptionMPlanResponse> responses = subsList.map(sub -> {
          SubscriptionMPlanResponse response = new SubscriptionMPlanResponse();
          response.setSubscriptionPlanId(Integer.valueOf(sub.getSubscriptionPlanId()));
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
    return new PagedResponse(responses, subsList.getNumber(), subsList.getSize(), subsList.getTotalElements(), subsList
        .getTotalPages(), subsList.isLast());
  }
  
  public ResponseEntity<?> getPlanAmount(SearchRequest request) {
    String[] arrayCOuntry = request.getDiscountCountry();
    StringBuilder stringBuilder = new StringBuilder();
    Arrays.sort((Object[])arrayCOuntry);
    for (int i = 0; i < arrayCOuntry.length; i++)
      stringBuilder.append(arrayCOuntry[i] + ","); 
    String countryNames = stringBuilder.toString().substring(0, stringBuilder.length() - 1);
    String countryNamesList = "";
    String quotes = "'";
    List<String> myList = new ArrayList<>(Arrays.asList(countryNames.split(",")));
    for (String country : myList)
      countryNamesList = countryNames + country + ","; 
    System.out.println("Country List: " + countryNames);
    System.out.println("Country List: " + countryNamesList);
    request.setCountryNames(countryNamesList);
    List<String> value = (List<String>)Stream.<String>of(countryNames.split(",", -1)).collect(Collectors.toList());
    System.out.println("value List: " + value);
    System.out.println("countryName" + countryNames);
    List<NimaiMSubscriptionPlan> subList = this.masterRepo.getPlanAmount(request.getCustomerType(), value);
    List<DiscountPlanResponse> dResp = new ArrayList<>();
    for (NimaiMSubscriptionPlan o : subList) {
      DiscountPlanResponse sp = new DiscountPlanResponse();
      sp.setPlanName(o.getPlanName());
      sp.setPricing(o.getPricing());
      dResp.add(sp);
    } 
    return new ResponseEntity(dResp, HttpStatus.OK);
  }
}
