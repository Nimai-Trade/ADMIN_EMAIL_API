package com.nimai.admin.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nimai.admin.exception.ResourceNotFoundException;
import com.nimai.admin.model.NimaiMCurrency;
import com.nimai.admin.model.NimaiMEmployee;
import com.nimai.admin.model.NimaiMLogin;
import com.nimai.admin.model.NimaiMRole;
import com.nimai.admin.model.NimaiMpUserRole;
import com.nimai.admin.payload.ApiResponse;
import com.nimai.admin.payload.CurrencyResponce;
import com.nimai.admin.payload.EmployeeListRequest;
import com.nimai.admin.payload.EmployeeResponse;
import com.nimai.admin.payload.PagedResponse;
import com.nimai.admin.payload.PasswordDetails;
import com.nimai.admin.payload.SearchRequest;
import com.nimai.admin.repository.CurrencyRepository;
import com.nimai.admin.repository.EmployeeRepository;
import com.nimai.admin.repository.RoleRepository;
import com.nimai.admin.repository.UserRepository;
import com.nimai.admin.repository.UserRoleRepository;
import com.nimai.admin.repository.nimaiSystemConfigRepository;
import com.nimai.admin.service.EmployeeService;
import com.nimai.admin.specification.EmployeeSpecification;
import com.nimai.admin.util.AppConstants;
import com.nimai.admin.util.ModelMapper;
import com.nimai.admin.util.Utility;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	UserRoleRepository userRoleRepository;

	@Autowired
	CurrencyRepository currencyRepository;

    private JavaMailSender javaMailSender;
	
	@Autowired
	EmployeeSpecification employeeSpecification;
	
	@Autowired
	nimaiSystemConfigRepository systemConfig;
	
	
	@Value("${link}")
	private String urllink;
	
	private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

	@Override
	public PagedResponse<?> getAllEmployee(SearchRequest data) {
		Pageable pageable = PageRequest.of(data.getPage(),data.getSize(),
				data.getDirection().equalsIgnoreCase("desc") ? Sort.by(data.getSortBy()).descending()
						: Sort.by(data.getSortBy()).ascending());

//		Page<NimaiMEmployee> empList = employeeRepository.findAll(pageable);
		Page<NimaiMEmployee> empList = employeeRepository.findAll(employeeSpecification.getFilter(data), pageable);

		List<EmployeeListRequest> responses = empList.map(emp -> {
			return ModelMapper.mapEmployeesToResponse(emp);
		}).getContent();

		return new PagedResponse<>(responses, empList.getNumber(), empList.getSize(), empList.getTotalElements(),
				empList.getTotalPages(), empList.isLast());
	}

	@Override
	public ResponseEntity<?>createOrUpdateEmployees(EmployeeListRequest request) {
		try {
			NimaiMEmployee employee = null;
			String msg = "";
			String link = "";
			String flag = "";
			if (request.getEmpId() != null) {
				employee = employeeRepository.getOne(request.getEmpId());
				msg = "Employee updated successfully";
				employee.setStatus(request.getStatus());
				flag = "UPDATE";
			} else {
				employee = new NimaiMEmployee();
				employee.setEmpCode(request.getEmpCode());				
				msg = "Employee created successfully";
				flag = "NEW";
			}

			employee.setStatus("Pending");
			employee.setEmpName(request.getFirstName());
			employee.setEmpLastName(request.getLastName());
			employee.setEmpEmail(request.getEmpEmail());
			employee.setEmpMobile(request.getEmpMobile());
			employee.setCountryExt(request.getCountryExt());
			employee.setDepartment(request.getDepartment());
			employee.setDesignation(request.getDesignation());
			employee.setReportingManager(request.getReportingManager());
			StringBuilder stringBuilder = new StringBuilder();
			for (int i = 0; i < request.getCountry().length; i++) {
				stringBuilder.append(request.getCountry()[i] + ",");
			}
			employee.setCountry(stringBuilder.toString().substring(0, stringBuilder.length() - 1));
			employee.setAddress1(request.getAddress1());
			employee.setAddress2(request.getAddress2());
			

			employee = employeeRepository.save(employee);
			System.out.println(employee.getEmpId() + " >>>> Employeee save" + employee.getEmpCode());

			Optional<NimaiMLogin> login = userRepository.findByEmpCode(request.getEmpCode());

			if (login.isPresent()) {
				NimaiMLogin loginUser = login.get();
				loginUser.setStatus(request.getStatus());
				userRepository.save(loginUser);
			} else {
				NimaiMLogin nimaiLogin = new NimaiMLogin();
				nimaiLogin.setPassword(passwordEncoder.encode("Nimai@123"));
				nimaiLogin.setStatus(request.getStatus());
				nimaiLogin.setFlag(AppConstants.PASSWORD_RESET_FLAG + "");
				nimaiLogin.setEmpCode(employee);

				nimaiLogin.setToken(new Random().nextInt(100000) + "");
				Date currentTime = new Date();
				String adminLinkEpiresDays = systemConfig.findByLinkDays();
				//nimaiLogin.setTokenExpiryDate(new Date(currentTime.getTime() + TimeUnit.DAYS.toDays(Integer.parseInt(adminLinkEpiresDays))));
				
				nimaiLogin.setTokenExpiryDate(getLinkExpiry(adminLinkEpiresDays));
				nimaiLogin = userRepository.save(nimaiLogin);

//				link = ServletUriComponentsBuilder
//						.fromCurrentContextPath().path("/#/change-password?userId="
//								+ nimaiLogin.getEmpCode().getEmpCode() + "&token=" + nimaiLogin.getToken())
//						.toUriString();
				//link = urllink+"/nimai_admin/#/change-password?userId="
					//	+ nimaiLogin.getEmpCode().getEmpCode() + "&token=" + nimaiLogin.getToken();

//				link = "http://203.115.123.93:9090/nimai_admin/#/change-password?userId="
//						+ nimaiLogin.getEmpCode().getEmpCode() + "&token=" + nimaiLogin.getToken();
				
//				link = "http://nimai-pilot-lb-468660897.me-south-1.elb.amazonaws.com/nimai_admin/#/change-password?userId="
//						+ nimaiLogin.getEmpCode().getEmpCode() + "&token=" + nimaiLogin.getToken();

				
			}

			/**
			 * commented on 31-10-2020
			 * Requirement change from single Role to multiple role
			 */
//			Optional<NimaiMpUserRole> userData = userRoleRepository.findByEmpCode(request.getEmpCode());
//			if (userData.isPresent()) {
//				NimaiMpUserRole user = userData.get();
//				user.setRoleId(new NimaiMRole(request.getEmpRole()));
//				user.setStatus(request.getStatus());
//				userRoleRepository.save(user);
//			} else {
//				NimaiMpUserRole userRole = new NimaiMpUserRole();
//				userRole.setEmpCode(employee);
//				userRole.setRoleId(new NimaiMRole(request.getEmpRole()));
//				userRole.setStatus(request.getStatus());
//				userRoleRepository.save(userRole);
//			}
			
			/**
			 * Code Added on 31-10-2020
			 * Single user have muliple roles
			 */
			List<NimaiMpUserRole> userData = userRoleRepository.findByEmpCode(request.getEmpCode());		
			for (NimaiMpUserRole userRole : userData) {
				userRole.setStatus("INACTIVE");
				userRoleRepository.save(userRole);
			}
			List<NimaiMpUserRole> roleList = new ArrayList<>();

			for (int i = 0; i < request.getEmpRole().length; i++) {				
				NimaiMpUserRole userRole = new NimaiMpUserRole();
				userRole.setEmpCode(employee);
				userRole.setRoleId(new NimaiMRole(Integer.parseInt(request.getEmpRole()[i])));
				userRole.setStatus("ACTIVE");
				roleList.add(userRole);
			}
			
			System.out.println(">>>> Update User Role List");
			userRoleRepository.saveAll(roleList);	
			
//			if(!link.isEmpty()) {
//				sendEmail(request.getFirstName(),request.getLastName(), link,request.getEmpEmail() );				
//			}

			return new ResponseEntity<>(new EmployeeResponse(employee.getEmpCode(), employee.getEmpName(),
					employee.getEmpEmail(), flag, msg), HttpStatus.CREATED);
		} catch (Exception e) {
			System.out.println("Exception in employee :" + e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<>(new EmployeeResponse(), HttpStatus.EXPECTATION_FAILED);
		}

	}

	@Override
	public ResponseEntity<EmployeeListRequest> getEmployeesById(Integer empId) {

		NimaiMEmployee employee = employeeRepository.getOne(empId);

		if (employee != null) {
			EmployeeListRequest employeeListResponse = ModelMapper.mapEmployeesToResponse(employee);

			return new ResponseEntity<EmployeeListRequest>(employeeListResponse, HttpStatus.OK);
		} else {
			logger.info("No Employee Details exist for given id");
			throw new ResourceNotFoundException("No Employee Details exist for given id");
		}
	}

	@Override
	public ResponseEntity<?> updateEmployeeStatus(String empId, String status) {
		NimaiMEmployee employee = employeeRepository.getOne(Integer.parseInt(empId));
		if(employee.getCreatedBy().equalsIgnoreCase(Utility.getUserId())) {
			return new ResponseEntity<>(new ApiResponse(true, "Unauthorized Operation !!!!"),
					HttpStatus.OK);
		}
		employee.setStatus(status);
		Optional<NimaiMLogin> empLogin=userRepository.findByEmpCode(employee.getEmpCode());
		if(empLogin.isPresent())
		{
			//employeeRepository.updateLoginStatus(employee.getEmpCode(), "INACTIVE");
			employeeRepository.updateLoginStatus(employee.getEmpCode(), status);
		}
		employeeRepository.save(employee);

		logger.info("Employee status updated successfully... " + empId);
		return new ResponseEntity<>(new ApiResponse(true, "Employee status updated successfully..."),
				HttpStatus.CREATED);

	}

	private String encodePassword(String password) {
		return passwordEncoder.encode(password);
	}

	@Override
	public ResponseEntity<?> updateUser(@Valid PasswordDetails passwordDetails, String empCode) {
		NimaiMLogin nimaiMLogin = userRepository.findByEmpCode(empCode)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with EMP CODE : " + empCode));

		nimaiMLogin.setPassword(encodePassword(passwordDetails.getPassword()));
		System.out.println(nimaiMLogin.getPassword());
		System.out.println(nimaiMLogin.getEmpCode());
		userRepository.save(nimaiMLogin);
		logger.info("Employee Password updated successfully... " + nimaiMLogin.getEmpCode());
		return new ResponseEntity<>(new ApiResponse(true, "Employee Password updated successfully..."),
				HttpStatus.CREATED);
	}

	@Override
	public boolean checkEmployeeCode(String empCode) {
		return employeeRepository.existsByEmpCode(empCode);
	}

	@Override
	public List<CurrencyResponce> countryList() {
		List<NimaiMCurrency> currency = currencyRepository.findAll();

		if (currency.size() != 0 && currency != null)
			return currency.stream().map(
					res -> new CurrencyResponce(res.getCountry(), res.getCode(), res.getCountryId(), res.getCurrency()))
					.collect(Collectors.toList());
		else
			return null;
	}

	void sendEmail(String fName, String lName, String link, String email) {

		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(email);

		msg.setSubject("Nimai Admin Set Password Link ");
		msg.setText("Dear " + fName + " " + lName + " \n Please click below link to set password \n " + link);

		javaMailSender.send(msg);

	}

	@Override
	public PagedResponse<?> approvalPendingList(SearchRequest request) {
		
		Pageable pageable = PageRequest.of(request.getPage(), request.getSize(),
				request.getDirection().equalsIgnoreCase("desc") ? Sort.by(request.getSortBy()).descending()
						: Sort.by(request.getSortBy()).ascending());

		Page<NimaiMEmployee> empList = employeeRepository.findAll(employeeSpecification.getFilter(request), pageable);

		List<EmployeeListRequest> responses = empList.map(emp -> {
			return ModelMapper.mapEmployeesToResponse(emp);
		}).getContent();

		return new PagedResponse<>(responses, empList.getNumber(), empList.getSize(), empList.getTotalElements(),
				empList.getTotalPages(), empList.isLast());
	}
	public Date getLinkExpiry(String days) {
		
		Date dNow = new Date();
		Date dafter = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(dNow);
		cal.add(Calendar.DATE, Integer.parseInt(days));
		dafter = cal.getTime();
		System.out.println(dafter);
		return dafter;
	}
}
