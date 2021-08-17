package com.nimai.admin.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nimai.admin.model.GenericResponse;
import com.nimai.admin.model.NimaiMEmployee;
import com.nimai.admin.payload.EmployeeListRequest;
import com.nimai.admin.payload.PagedResponse;
import com.nimai.admin.payload.PasswordDetails;
import com.nimai.admin.payload.SearchRequest;
import com.nimai.admin.repository.EmployeeRepository;
import com.nimai.admin.service.EmployeeService;
import com.nimai.admin.service.TokenService;
import com.nimai.admin.util.EmailSender;

/**
 * 
 * @author sahadeo.naik
 *
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

	private static Logger logger = LoggerFactory.getLogger(EmployeeController.class);
	@Autowired
	private EmployeeService employeeService;

	@Autowired
	EmailSender email;
	
	@Autowired
	EmployeeRepository empRepo;
	
	@Autowired
	TokenService tokenService;

	@PostMapping("/list")
	public PagedResponse<?> getAllEmployee(@RequestBody SearchRequest data) {

		System.out.println(data);
		return employeeService.getAllEmployee(data);

	}

	
	
	
	@GetMapping("/{id}")
	public ResponseEntity<EmployeeListRequest> getEmployeesById(@PathVariable(value = "id") Integer empId) {
		return employeeService.getEmployeesById(empId);
	}

	@PostMapping("/save")
	public ResponseEntity<?> createOrUpdateEmployees(@Valid @RequestBody EmployeeListRequest request) {
		return employeeService.createOrUpdateEmployees(request);
	}

	@PostMapping("/updateEmployeeStatus")
	public ResponseEntity<?> updateEmployeeStatus(@RequestBody Map<String, String> data) {
		return employeeService.updateEmployeeStatus(data.get("empId"), data.get("status"));

	}

	@PatchMapping("/updateUser/{empCode}")
	public ResponseEntity<?> updateUser(@PathVariable(value = "empCode") String empCode,
			@Valid @RequestBody PasswordDetails passwordDetails) {
		return employeeService.updateUser(passwordDetails, empCode);
	}
	
	@GetMapping("/checkEmployeeCode/{empCode}")
	public boolean checkEmployeeCode(@PathVariable(value = "empCode") String empCode){
		System.out.println("Check EmpCode exits " + empCode);
		return employeeService.checkEmployeeCode(empCode);
	}
	
	
	@GetMapping("/countryList")
	public List<?> countryList() {
		return employeeService.countryList();
	}
	
	@PostMapping("/approvalPendingList")
	public PagedResponse<?> approvalPendingList(@RequestBody SearchRequest request) {
		
		return employeeService.approvalPendingList(request);

	}

}
