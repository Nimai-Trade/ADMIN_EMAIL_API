package com.nimai.admin.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;

import com.nimai.admin.payload.EmployeeListRequest;
import com.nimai.admin.payload.PagedResponse;
import com.nimai.admin.payload.PasswordDetails;
import com.nimai.admin.payload.SearchRequest;

public interface EmployeeService {

	PagedResponse<?> getAllEmployee(SearchRequest data);

	ResponseEntity<EmployeeListRequest> getEmployeesById(Integer empId);

	ResponseEntity<?> createOrUpdateEmployees(@Valid EmployeeListRequest request);

	ResponseEntity<?> updateEmployeeStatus(String empId, String status);

	ResponseEntity<?>  updateUser(@Valid PasswordDetails passwordDetails, String empCode);

	boolean checkEmployeeCode(String empCode);

	List<?> countryList();

	PagedResponse<?> approvalPendingList(SearchRequest request);



}
