package com.nimai.admin.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Tuple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nimai.admin.exception.ResourceNotFoundException;
import com.nimai.admin.model.NimaiEmailScheduler;
import com.nimai.admin.model.NimaiMCustomer;
import com.nimai.admin.model.NimaiMEmployee;
import com.nimai.admin.model.NimaiMRole;
import com.nimai.admin.model.NimaiMVas;
import com.nimai.admin.payload.ApiResponse;
import com.nimai.admin.payload.PagedResponse;
import com.nimai.admin.payload.SearchRequest;
import com.nimai.admin.payload.VasResponse;
import com.nimai.admin.payload.VasUpdateRequestBody;
import com.nimai.admin.repository.EmployeeRepository;
import com.nimai.admin.repository.NimaiEmailSchedulerRepository;
import com.nimai.admin.repository.RoleRepository;
import com.nimai.admin.repository.VasRepository;
import com.nimai.admin.service.VasService;
import com.nimai.admin.specification.VasSpecification;
import com.nimai.admin.util.Utility;

@Service
public class VasServiceImpl implements VasService {

	@Autowired
	VasRepository vasRepository;

	@Autowired
	VasSpecification vasSpecification;

	@Autowired
	EmployeeRepository employeeRepo;

	@Autowired
	NimaiEmailSchedulerRepository schRepo;

	@Autowired
	RoleRepository roleRepo;

	/**
	 * Maker Create a Vas Plan
	 */
	@Override
	public ResponseEntity<?> saveVasDetails(VasResponse tempVas) {
		try {
			NimaiMVas nimaiTempVas = null;

			// List<NimaiMEmployee> empDetails =
			// employeeRepo.findEmpListByCountry(tempVas.getCountryName(),
			// designation,accountStatus);

			String msg = "";
			System.out.println("VAS ID :: " + tempVas.getVasid());
			Calendar cal = Calendar.getInstance();
			Date today = cal.getTime();

			if (tempVas.getVasid() != null) {
				nimaiTempVas = vasRepository.getOne(tempVas.getVasid());
				nimaiTempVas.setModifiedBy(tempVas.getModifiedBy());
				nimaiTempVas.setModifiedDate(today);
				msg = "VAS Plan updated successfully";
			} else {

				List<NimaiMVas> nimaiVas = vasRepository.getVasDetails(tempVas.getCountryName());
				if (nimaiVas.size() >= 1) {
					msg = "VAS Plan already active for " + tempVas.getCountryName();
					return new ResponseEntity<>(new ApiResponse(true, msg), HttpStatus.OK);
				}
				nimaiTempVas = new NimaiMVas();
				msg = "VAS plan created successfully";
			}
			nimaiTempVas.setCustomerType(tempVas.getCustomerType());
			nimaiTempVas.setCountryName(tempVas.getCountryName());
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
			vasRepository.save(nimaiTempVas);

			return new ResponseEntity<>(new ApiResponse(true, msg), HttpStatus.CREATED);
		} catch (Exception e) {
			System.out.println("Exception in VAS :" + e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<>(new ApiResponse(true, "Error due to some technical issue"),
					HttpStatus.EXPECTATION_FAILED);
		}
	}

	/**
	 * List all Vas data
	 */
	@Override
	public PagedResponse<?> getAllVasDetails(SearchRequest request) {
		Pageable pageable = PageRequest.of(request.getPage(), request.getSize(),
				request.getDirection().equalsIgnoreCase("desc") ? Sort.by(request.getSortBy()).descending()
						: Sort.by(request.getSortBy()).ascending());

		Page<NimaiMVas> vasList = vasRepository.findAll(vasSpecification.getFilter(request), pageable);

		List<VasResponse> responses = vasList.map(vas -> {
			VasResponse response = new VasResponse();
			response.setVasid(vas.getVasid());
			response.setCustomerType(vas.getCustomerType());
			response.setCountryName(vas.getCountryName());
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

		return new PagedResponse<>(responses, vasList.getNumber(), vasList.getSize(), vasList.getTotalElements(),
				vasList.getTotalPages(), vasList.isLast());
	}

//	@Override
//	public int updateVasDetails(VasUpdateRequestBody request) {
//		if (request.getStatus().equalsIgnoreCase("Active")) {
//			return makerRepo.updateByVasidAndFlag(request.getVasid(), "Approved", request.getModifiedBy(),
//					request.getModifiedDate());
//
//		} else {
//			return makerRepo.updateByVasidAndFlag(request.getVasid(), request.getStatus(), request.getModifiedBy(),
//					request.getModifiedDate());
//
//		}
//	}

	/**
	 * Get Vas detail By ID
	 */
	@Override
	public ResponseEntity<?> getVasDetailById(Integer vasId) {
		NimaiMVas vas = vasRepository.getOne(vasId);
		if (vas != null) {
			VasResponse response = new VasResponse();
			response.setVasid(vas.getVasid());
			response.setCustomerType(vas.getCustomerType());
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
			return new ResponseEntity<VasResponse>(response, HttpStatus.OK);
		} else {
			throw new ResourceNotFoundException("No VAS Details exist...");
		}
	}

	@Override
	public ResponseEntity<?> checkerUpdate(VasUpdateRequestBody request) {
		NimaiMVas chck = vasRepository.getOne(request.getVasid());

		System.out.println("checker update quantity" + vasRepository.checkAvailability(request.getCountryName()));
		if (chck.getCreatedBy().equalsIgnoreCase(Utility.getUserId())) {

			return new ResponseEntity<>(new ApiResponse(false, "You dont have the authority for this operation!!!"),
					HttpStatus.OK);
		}

		if (request.getStatus().equalsIgnoreCase("Active")) {

			if (vasRepository.checkAvailability(request.getCountryName()) >= 1) {

				return new ResponseEntity<>(
						new ApiResponse(true, "VAS Plan already active for " + request.getCountryName()),
						HttpStatus.OK);
			} else {
				NimaiMVas vas = vasRepository.getOne(request.getVasid());

				vas.setStatus(request.getStatus());
				String designation = "RM";
				String accountStatus = "ACTIVE";
				int roleId = getEmpRoleId(request.getCustomerType());
				System.out.println("========== role id:" + roleId + "======================");
				if (roleId != 0) {
					List<Tuple> performanceList = employeeRepo.getEMpList(roleId, request.getCountryName());
					System.out.println("==================" + performanceList.size() + "=================");
					if (performanceList.size() > 0) {
						for (Tuple emp : performanceList) {
							NimaiEmailScheduler schData = new NimaiEmailScheduler();
							System.out.println("==============================" + performanceList.toString()
									+ "==================");
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
//							if (tempVas.getVasid() != null) {
//								if (tempVas.getCustomerType().equalsIgnoreCase("CUSTOMER")) {
//									schData.setEvent("UPDATED_CU_VAS_NOTIFICATION_TORM");
//									schData.setCustomerType("CUSTOMER");
//								} else if (tempVas.getCustomerType().equalsIgnoreCase("BANK as Customer")) {
//									schData.setEvent("UPDATED_BC_VAS_NOTIFICATION_TORM");
//									schData.setCustomerType("BANK as Customer");
//								} else if (tempVas.getCustomerType().equalsIgnoreCase("Bank as underwriter")) {
//									schData.setEvent("UPDATED_BAU_VAS_NOTIFICATION_TORM");
//									schData.setCustomerType("Bank as underwriter");
//								}
//							}
							schData.setSubscriptionName(vas.getPlanName());
							schData.setSubscriptionAmount(String.valueOf(vas.getPricing()));
							schData.setDescription1(vas.getDescription1());
							schData.setDescription2(vas.getDescription2());
							schData.setDescription3(vas.getDescription3());
							schData.setDescription4(vas.getDescription4());
							schData.setDescription5(vas.getDescription5());
							schData.setrMName(
									(String) emp.get("EMP_FIRST_NAME") != null ? (String) emp.get("EMP_FIRST_NAME")
											: "");
							schData.setrMemailId(
									(String) emp.get("EMP_EMAIL") != null ? (String) emp.get("EMP_EMAIL") : "");
							schData.setsPlanCurrency(vas.getCurrency());
							schData.setsPLanCountry(vas.getCountryName());
							Calendar cal = Calendar.getInstance();
							Date today = cal.getTime();
							schData.setInsertedDate(today);
							schData.setEmailStatus("Pending");
							schRepo.save(schData);

						}
					}

				} else {
					System.out.println("============role Id is not in vasServiceImpl present");
				}

				vasRepository.save(vas);
				return new ResponseEntity<>(
						new ApiResponse(true, "Vas plan activated for country " + request.getCountryName()),
						HttpStatus.CREATED);
			}
		} else if (request.getStatus().equalsIgnoreCase("Rejected")) {
			NimaiMVas vas = vasRepository.getOne(request.getVasid());
			vas.setStatus("Rejected");
			vasRepository.save(vas);
			return new ResponseEntity<>(
					new ApiResponse(true, "Vas plan rejected for country " + request.getCountryName()),
					HttpStatus.CREATED);
		} else {
			NimaiMVas vas = vasRepository.getOne(request.getVasid());
			vas.setStatus("Inactive");
			vasRepository.save(vas);
			return new ResponseEntity<>(
					new ApiResponse(true, "Vas plan Inactivate for country " + request.getCountryName()),
					HttpStatus.CREATED);
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

	@Override
	public int updateVasDetails(VasUpdateRequestBody request) {
		// TODO Auto-generated method stub
		return 0;
	}
}
