package com.nimai.admin.service.impl;

import com.nimai.admin.exception.ResourceNotFoundException;
import com.nimai.admin.model.NimaiEmailScheduler;
import com.nimai.admin.model.NimaiMRole;
import com.nimai.admin.model.NimaiMVas;
import com.nimai.admin.model.NimaiMVasCountry;
import com.nimai.admin.payload.ApiResponse;
import com.nimai.admin.payload.CountryList;
import com.nimai.admin.payload.PagedResponse;
import com.nimai.admin.payload.SearchRequest;
import com.nimai.admin.payload.VasResponse;
import com.nimai.admin.payload.VasUpdateRequestBody;
import com.nimai.admin.repository.CustomerRepository;
import com.nimai.admin.repository.EmployeeRepository;
import com.nimai.admin.repository.MasterSubsPlanRepository;
import com.nimai.admin.repository.NimaiEmailSchedulerRepository;
import com.nimai.admin.repository.NimaiVasCountryRepository;
import com.nimai.admin.repository.RoleRepository;
import com.nimai.admin.repository.VasRepository;
import com.nimai.admin.service.VasService;
import com.nimai.admin.specification.VasSpecification;
import com.nimai.admin.util.Utility;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.persistence.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class VasServiceImpl implements VasService {
  @Autowired
  VasRepository vasRepository;
  
  @Autowired
  VasSpecification vasSpecification;
  
  @Autowired
  EmployeeRepository employeeRepo;
  
  @Autowired
  CustomerRepository repo;
  
  @Autowired
  NimaiEmailSchedulerRepository schRepo;
  
  @Autowired
  RoleRepository roleRepo;
  
  @Autowired
  MasterSubsPlanRepository sub;
  
  @Autowired
  NimaiVasCountryRepository vasCountryRepo;
  
  public ResponseEntity<?> saveVasDetails(VasResponse tempVas) {
    try {
      NimaiMVas nimaiTempVas = null;
      String msg = "";
      System.out.println("VAS ID :: " + tempVas.getVasid());
      Calendar cal = Calendar.getInstance();
      Date today = cal.getTime();
      if (tempVas.getVasid() != null) {
        nimaiTempVas = (NimaiMVas)this.vasRepository.getOne(tempVas.getVasid());
        nimaiTempVas.setModifiedBy(tempVas.getModifiedBy());
        nimaiTempVas.setModifiedDate(today);
        this.vasCountryRepo.deleteSPLanById(nimaiTempVas.getVasid());
        msg = "VAS Plan updated successfully";
      } else {
        nimaiTempVas = new NimaiMVas();
        msg = "VAS plan created successfully";
      } 
      nimaiTempVas.setCustomerType(tempVas.getCustomerType());
      Arrays.sort((Object[])tempVas.getCountry());
      StringBuilder stringBuilder = new StringBuilder();
      int i;
      for (i = 0; i < (tempVas.getCountry()).length; i++)
        stringBuilder.append(tempVas.getCountry()[i] + ","); 
      nimaiTempVas.setCountryName(stringBuilder.toString().substring(0, stringBuilder.length() - 1));
      nimaiTempVas.setPlanName(tempVas.getPlanName());
      nimaiTempVas.setDescription1(tempVas.getDescription1());
      nimaiTempVas.setDescription2(tempVas.getDescription2());
      nimaiTempVas.setDescription3(tempVas.getDescription3());
      nimaiTempVas.setDescription4(tempVas.getDescription4());
      nimaiTempVas.setDescription5(tempVas.getDescription5());
      nimaiTempVas.setPricing(tempVas.getPricing());
      nimaiTempVas.setCurrency(tempVas.getCountryCurrency());
      nimaiTempVas.setCreatedDate(today);
      nimaiTempVas.setStatus("Pending");
      this.vasRepository.save(nimaiTempVas);
      for (i = 0; i < (tempVas.getCountry()).length; i++) {
        NimaiMVasCountry vasCoutry = new NimaiMVasCountry();
        vasCoutry.setVasCountry(tempVas.getCountry()[i]);
        vasCoutry.setVasId(nimaiTempVas.getVasid());
        this.vasCountryRepo.save(vasCoutry);
      } 
      return new ResponseEntity(new ApiResponse(Boolean.valueOf(true), msg), HttpStatus.CREATED);
    } catch (Exception e) {
      System.out.println("Exception in VAS :" + e.getMessage());
      e.printStackTrace();
      return new ResponseEntity(new ApiResponse(Boolean.valueOf(true), "Error due to some technical issue"), HttpStatus.EXPECTATION_FAILED);
    } 
  }
  
  public PagedResponse<?> getAllVasDetails(SearchRequest request) {
    request.setSortBy("CREATED_DATE");
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
    System.out.println("Countries: " + value);
    Page<NimaiMVas> vasList = this.vasRepository.getAllVasPlan(value, (Pageable)pageRequest);
    List<VasResponse> responses = vasList.map(vas -> {
          VasResponse response = new VasResponse();
          response.setVasid(Integer.valueOf(vas.getVasid()));
          response.setCustomerType(vas.getCustomerType());
          String countryName = "";
          List<String> myList = new ArrayList<>(Arrays.asList(vas.getCountryName().split(",")));
          if (myList.size() <= 1) {
            response.setCountryName(vas.getCountryName());
          } else {
            response.setCountryName("Multiple Countries");
          } 
          response.setCountry(vas.getCountryName().split(","));
          response.setPlanName(vas.getPlanName());
          response.setDescription1(vas.getDescription1());
          response.setDescription2(vas.getDescription2());
          response.setDescription3(vas.getDescription3());
          response.setDescription4(vas.getDescription4());
          response.setDescription5(vas.getDescription5());
          response.setPricing(vas.getPricing());
          response.setStatus(vas.getStatus());
          response.setCreatedDate(vas.getCreatedDate());
          response.setCountryCurrency(vas.getCurrency());
          response.setCreatedBy(vas.getCreatedBy());
          response.setModifiedBy(vas.getModifiedBy());
          return response;
        }).getContent();
    return new PagedResponse(responses, vasList.getNumber(), vasList.getSize(), vasList.getTotalElements(), vasList
        .getTotalPages(), vasList.isLast());
  }
  
  public ResponseEntity<?> getVasDetailById(Integer vasId) {
    NimaiMVas vas = (NimaiMVas)this.vasRepository.getOne(vasId);
    if (vas != null) {
      VasResponse response = new VasResponse();
      response.setVasid(Integer.valueOf(vas.getVasid()));
      response.setCustomerType(vas.getCustomerType());
      List<CountryList> countryList = new ArrayList<>();
      List<String> actualCountryList = this.vasCountryRepo.getVasCountryList(vasId);
      for (String country : actualCountryList) {
        CountryList listCountry = new CountryList();
        listCountry.setCountry(country);
        countryList.add(listCountry);
      } 
      response.setCountryList(countryList);
      response.setCountryName(vas.getCountryName());
      response.setPlanName(vas.getPlanName());
      response.setDescription1(vas.getDescription1());
      response.setDescription2(vas.getDescription2());
      response.setDescription3(vas.getDescription3());
      response.setDescription4(vas.getDescription4());
      response.setDescription5(vas.getDescription5());
      response.setPricing(vas.getPricing());
      response.setStatus(vas.getStatus());
      response.setCreatedBy(vas.getCreatedBy());
      response.setCreatedDate(vas.getCreatedDate());
      response.setModifiedBy(vas.getModifiedBy());
      response.setModifiedDate(vas.getModifiedDate());
      response.setCountryCurrency(vas.getCurrency());
      return new ResponseEntity(response, HttpStatus.OK);
    } 
    throw new ResourceNotFoundException("No VAS Details exist...");
  }
  
  public ResponseEntity<?> checkerUpdate(VasUpdateRequestBody request) {
    NimaiMVas chck = (NimaiMVas)this.vasRepository.getOne(Integer.valueOf(request.getVasid()));
    System.out.println("checker update quantity" + this.vasRepository.checkAvailability(request.getCountryName(), request.getCustomerType()));
    if (chck.getCreatedBy().equalsIgnoreCase(Utility.getUserId()))
      return new ResponseEntity(new ApiResponse(Boolean.valueOf(false), "You dont have the authority for this operation!!!"), HttpStatus.OK); 
    if (request.getStatus().equalsIgnoreCase("Active")) {
      NimaiMVas nimaiMVas = (NimaiMVas)this.vasRepository.getOne(Integer.valueOf(request.getVasid()));
      nimaiMVas.setStatus(request.getStatus());
      String designation = "RM";
      String accountStatus = "ACTIVE";
      int roleId = getEmpRoleId(request.getCustomerType());
      System.out.println("========== role id:" + roleId + "======================");
      if (roleId != 0) {
        List<Tuple> performanceList = this.employeeRepo.getEMpList(roleId, request.getCountryName());
        System.out.println("==================" + performanceList.size() + "=================");
        if (performanceList.size() > 0)
          for (Tuple emp : performanceList) {
            NimaiEmailScheduler schData = new NimaiEmailScheduler();
            System.out.println("==============================" + performanceList.toString() + "==================");
            if (request.getCustomerType().equalsIgnoreCase("CUSTOMER")) {
              schData.setEvent("CU_VAS_NOTIFICATION_TORM");
              schData.setCustomerType("CUSTOMER");
            } else if (request.getCustomerType().equalsIgnoreCase("BANK as Customer")) {
              schData.setEvent("BC_VAS_NOTIFICATION_TORM");
              schData.setCustomerType("BANK as Customer");
            } else if (request.getCustomerType().equalsIgnoreCase("Bank as underwriter")) {
              schData.setEvent("BAU_VAS_NOTIFICATION_TORM");
              schData.setCustomerType("BANK as Customer");
            } 
            schData.setSubscriptionName(nimaiMVas.getPlanName());
            schData.setSubscriptionAmount(String.valueOf(nimaiMVas.getPricing()));
            schData.setDescription1(nimaiMVas.getDescription1());
            schData.setDescription2(nimaiMVas.getDescription2());
            schData.setDescription3(nimaiMVas.getDescription3());
            schData.setDescription4(nimaiMVas.getDescription4());
            schData.setDescription5(nimaiMVas.getDescription5());
            schData.setrMName(
                ((String)emp.get("EMP_FIRST_NAME") != null) ? (String)emp.get("EMP_FIRST_NAME") : "");
            schData.setrMemailId(
                ((String)emp.get("EMP_EMAIL") != null) ? (String)emp.get("EMP_EMAIL") : "");
            schData.setsPlanCurrency(nimaiMVas.getCurrency());
            schData.setsPLanCountry(nimaiMVas.getCountryName());
            Calendar cal = Calendar.getInstance();
            Date today = cal.getTime();
            schData.setInsertedDate(today);
            schData.setEmailStatus("Pending");
            this.schRepo.save(schData);
          }  
      } else {
        System.out.println("============role Id is not in vasServiceImpl present");
      } 
      this.vasRepository.save(nimaiMVas);
      return new ResponseEntity(new ApiResponse(
            Boolean.valueOf(true), "Vas plan activated for country " + request.getCountryName()), HttpStatus.CREATED);
    } 
    if (request.getStatus().equalsIgnoreCase("Rejected")) {
      NimaiMVas nimaiMVas = (NimaiMVas)this.vasRepository.getOne(Integer.valueOf(request.getVasid()));
      nimaiMVas.setStatus("Rejected");
      this.vasRepository.save(nimaiMVas);
      return new ResponseEntity(new ApiResponse(
            Boolean.valueOf(true), "Vas plan rejected for country " + request.getCountryName()), HttpStatus.CREATED);
    } 
    NimaiMVas vas = (NimaiMVas)this.vasRepository.getOne(Integer.valueOf(request.getVasid()));
    vas.setStatus("Inactive");
    this.vasRepository.save(vas);
    return new ResponseEntity(new ApiResponse(
          Boolean.valueOf(true), "Vas plan Inactivate for country " + request.getCountryName()), HttpStatus.CREATED);
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
  
  public int updateVasDetails(VasUpdateRequestBody request) {
    return 0;
  }
}
