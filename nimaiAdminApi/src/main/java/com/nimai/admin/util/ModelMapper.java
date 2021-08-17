package com.nimai.admin.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.nimai.admin.model.NimaiFBlkgoods;
import com.nimai.admin.model.NimaiFIntcountry;
import com.nimai.admin.model.NimaiFOwner;
import com.nimai.admin.model.NimaiMCustomer;
import com.nimai.admin.model.NimaiMEmployee;
import com.nimai.admin.model.NimaiMRights;
import com.nimai.admin.model.NimaiMRole;
import com.nimai.admin.model.NimaiMmTransaction;
import com.nimai.admin.model.NimaiMpRoleRights;
import com.nimai.admin.payload.BankDetailsResponse;
import com.nimai.admin.payload.CustomerResponse;
import com.nimai.admin.payload.EmployeeListRequest;
import com.nimai.admin.payload.OwenerBean;
import com.nimai.admin.payload.RightsResponse;
import com.nimai.admin.payload.RoleList;
import com.nimai.admin.payload.RoleResponse;
import com.nimai.admin.payload.RoleRightsMappingResponse;
import com.nimai.admin.payload.TransactionDetails;
import com.nimai.admin.payload.TransactionSearchResponse;
import com.nimai.admin.payload.UserDetailResponse;

public class ModelMapper {

	public static UserDetailResponse mapEntityToEmployeeResponse(NimaiMEmployee employee) {
		UserDetailResponse userDetailResponse = new UserDetailResponse();
		userDetailResponse.setEmpId(employee.getEmpId().toString());
		userDetailResponse.setEmpCode(employee.getEmpCode());
		userDetailResponse.setEmpEmail(employee.getEmpEmail());
		userDetailResponse.setEmpMobile(employee.getEmpMobile());
		userDetailResponse.setEmpName(employee.getEmpName());
		userDetailResponse.setStatus(employee.getStatus());
		userDetailResponse.setAddress1(employee.getAddress1());
		userDetailResponse.setAddress2(employee.getAddress2());
		return userDetailResponse;
	}

	public static EmployeeListRequest mapEmployeesToResponse(NimaiMEmployee employee) {
		EmployeeListRequest employeeListResponse = new EmployeeListRequest();
		employeeListResponse.setEmpId(employee.getEmpId());
		employeeListResponse.setFirstName(employee.getEmpName());
		employeeListResponse.setLastName(employee.getEmpLastName());
		employeeListResponse.setEmpMobile(employee.getEmpMobile());
		employeeListResponse.setCountryExt(employee.getCountryExt());
		employeeListResponse.setEmpEmail(employee.getEmpEmail());
		employeeListResponse.setEmpCode(employee.getEmpCode());
		employeeListResponse.setDepartment(employee.getDepartment());
		employeeListResponse.setDesignation(employee.getDesignation());
		/**
		 * Commented on 31-10-2020 Requirement change from single role to multiple role
		 */
//		employeeListResponse.setEmpRole(employee.getNimaiMpUserRoleList().size() != 0
//				? employee.getNimaiMpUserRoleList().get(0).getRoleId().getRoleId()
//				: 0);
//		employeeListResponse.setEmpRoleName(employee.getNimaiMpUserRoleList().size() != 0
//				? employee.getNimaiMpUserRoleList().get(0).getRoleId().getRoleName()
//				: "");

		List<RoleList> list = employee.getNimaiMpUserRoleList().stream()
				.filter(x -> x.getStatus().equalsIgnoreCase("Active")).map(request -> {
					RoleList roleList = new RoleList();
					roleList.setRoleId(request.getRoleId().getRoleId());
					roleList.setRoleName(request.getRoleId().getRoleName());

					return roleList;
				}).collect(Collectors.toList());
		employeeListResponse.setRoleInfo(list);
		employeeListResponse.setAddress1(employee.getAddress1());
		employeeListResponse.setAddress2(employee.getAddress2());
		employeeListResponse.setCreatedBy(employee.getCreatedBy());
		employeeListResponse.setCreatedDate(employee.getCreatedDate());
		employeeListResponse.setStatus(employee.getStatus());
		employeeListResponse.setReportingManager(employee.getReportingManager());
		employeeListResponse.setCountry(employee.getCountry().split(","));
		employeeListResponse.setLastModifiedBy(employee.getLastModifiedBy());
		return employeeListResponse;
	}

	public static RoleResponse mapRoleToResponse(NimaiMRole role) {
		RoleResponse roleResponse = new RoleResponse();
		roleResponse.setRoleId(role.getRoleId());
		roleResponse.setRoleName(role.getRoleName());
		roleResponse.setRoleStatus(role.getRoleStatus());
		roleResponse.setCreatedBy(role.getCreatedBy());
		roleResponse.setCreatedDate(role.getCreatedDate());
		roleResponse.setRoleShortName(role.getRoleShortName());
		return roleResponse;
	}

	public static RightsResponse mapRightsToResponse(NimaiMRights rights) {
		RightsResponse rightsResponse = new RightsResponse();
		rightsResponse.setRightId(rights.getRightId());
		rightsResponse.setRightName(rights.getRightName());
		rightsResponse.setRightStatus(rights.getRightStatus());
		rightsResponse.setRightShortName(rights.getRightShortName());
		rightsResponse.setCreatedBy(rights.getCreatedBy());
		rightsResponse.setCreatedDate(rights.getCreatedDate());
		return rightsResponse;
	}

	public static RoleRightsMappingResponse mapRoleRightsToResponse(NimaiMpRoleRights map) {
		RoleRightsMappingResponse response = new RoleRightsMappingResponse();
		response.setUserRightsId(map.getUserRightsId());
		response.setRoleName(map.getRoleId().getRoleName());
		response.setRightsName(map.getRightId().getRightName());
		response.setLastModifiedBy(map.getLastModifiedBy());
		response.setLastModifiedDate(map.getLastModifiedDate());
		response.setCreatedBy(map.getCreatedBy());
		response.setCreatedDate(map.getCreatedDate());
		response.setStatus(map.getStatus());
		return response;
	}

	public static CustomerResponse mapCustomerToResponse(NimaiMCustomer cust) {
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
//		response.setOwnerName(cust.getOwnerName());
		response.setRegistrationType(cust.getRegistrationType());
		response.setProvincename(cust.getProvincename());
		response.setAddress1(cust.getAddress1());
		response.setAddress2(cust.getAddress2());
		response.setAddress3(cust.getAddress3());
		response.setCity(cust.getCity());
		response.setPincode(cust.getPincode());
		response.setInsertedDate(cust.getInsertedDate());
		if (cust.getRmStatus().equalsIgnoreCase("Pending")) {
			response.setRelationshipManager(cust.getRmId());
		}
		return response;
	}

	public static TransactionSearchResponse mapTransactionToResponse(NimaiMmTransaction trxn) {
		TransactionSearchResponse response = new TransactionSearchResponse();
		try {
			response.setTransactionId(trxn.getTransactionId());
			response.setUserId(trxn.getUserId().getUserid());
			response.setMobileNo(trxn.getUserId().getMobileNumber());
			response.setEmailId(trxn.getUserId().getEmailAddress());
			response.setBeneficiry(trxn.getBeneName());
			response.setBeneficiryCountry(trxn.getBeneCountry());
			response.setApplicant(trxn.getApplicantName());
			response.setApplicantCountry(trxn.getApplicantCountry());
			response.setInsertedDate(trxn.getInsertedDate());
			if (trxn.getValidity() != null) {
				Date date = new SimpleDateFormat("yyyy-MM-dd").parse(trxn.getValidity());
				response.setTxnValidaty(date);
			}
			response.setLcBank(trxn.getLcIssuanceBank());
			response.setAmount(trxn.getLcValue() + "");
			response.setCcy(trxn.getLcCurrency());
			String requirement = null;
			if (trxn.getRequirementType().equalsIgnoreCase("ConfirmAndDiscount")) {
				requirement = "Confirmation and Discounting";
			} else if (trxn.getRequirementType().equalsIgnoreCase("Banker")) {
				requirement = "Banker's Acceptance";
			} else if (trxn.getRequirementType().equalsIgnoreCase("Refinance")) {
				requirement = "Refinancing";
			} else if (trxn.getRequirementType().equalsIgnoreCase("Discounting")) {
				requirement = "Discounting";
			} else if (trxn.getRequirementType().equalsIgnoreCase("Confirmation")) {
				requirement = "Confirmation";
			}
			response.setRequiredment(requirement);
			response.setTrxnStatus(trxn.getTransactionStatus());
			response.setQuotes(trxn.getNimaiMQuotationList().size() + "");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return response;
	}

	public static TransactionDetails mapTransactionDetails(NimaiMmTransaction transaction, NimaiMCustomer cust) {
		TransactionDetails details = new TransactionDetails();
		details.setTransactionId(transaction.getTransactionId());
		details.setIssuingBank(transaction.getLcIssuanceBank());
		details.setBranch(transaction.getLcIssuanceBranch());
		details.setSwiftCode(transaction.getSwiftCode());
		details.setLcCountry(transaction.getLcIssuanceCountry());
		details.setRequirement(transaction.getRequirementType());
		details.setAmount(transaction.getLcValue());
		details.setIssuingDate(transaction.getLcIssuingDate());
		details.setLastShipmentDate(transaction.getLastShipmentDate());
		details.setNegotiationDate(transaction.getNegotiationDate());
		details.setGoods(transaction.getGoodsType());
		details.setPaymentTerms(transaction.getPaymentTerms());
		details.setRemarks(transaction.getRemarks());
		details.setApplicant(transaction.getApplicantName());
		details.setApplicantCountry(transaction.getApplicantCountry());
		details.setBeneficiary(transaction.getBeneName());
		details.setBeneficiaryBank(transaction.getBeneBankName());
		details.setBeneficiarySwiftCode(transaction.getBeneSwiftCode());
		details.setBeneficiaryBankCountry(transaction.getBeneBankCountry());
		details.setPortofLoading(transaction.getLoadingPort());
		details.setPortofDischarge(transaction.getDischargePort());
		details.setChargesAreOn(transaction.getChargesType());
		details.setValidity(transaction.getValidity());
		details.setProForma(transaction.getLcProForma().substring(transaction.getLcProForma().indexOf("|") + 1,
				transaction.getLcProForma().length()));
		
		
		 details.setApplicantContactPerson(transaction.getApplicantContactPerson());
		details.setApplicantContactPersonEmail(transaction.getApplicantContactPersonEmail());
		details.setBeneContactPerson(transaction.getBeneContactPerson());
		details.setBeneContactPersonEmail(transaction.getBeneContactPersonEmail()); 
		
		
		
		
		details.setUserID(cust.getUserid());
		details.setFirstName(cust.getFirstName());
		details.setLastName(cust.getLastName());
		details.setEmailID(cust.getEmailAddress());
		details.setMobileNo(cust.getMobileNumber());
		details.setCustCountry(cust.getCountryName());
		details.setRegistrationType(cust.getRegistrationType());
		details.setCompanyName(cust.getCountryName());
		details.setAddressCountry(cust.getCountryName());
		details.setStateProvince(cust.getProvincename());
		details.setCity(cust.getCity());
		details.setPin(cust.getPincode());
		details.setAddressline1(cust.getAddress1());
		details.setAddressline2(cust.getAddress2());
		details.setAddressline3(cust.getAddress3());
		details.setTelephone(cust.getTelephone());
		details.setRmFirstName(cust.getRmId());
		details.setRmLastName("");
		details.setRmDesignation("");
		if (transaction.getRequirementType().equalsIgnoreCase("Confirmation")) {
			details.setUsansce(transaction.getConfirmationPeriod());
		} else if (transaction.getRequirementType().equalsIgnoreCase("Discounting")) {
			details.setUsansce(transaction.getDiscountingPeriod());
		} else if (transaction.getRequirementType().equalsIgnoreCase("Refinance")) {
			details.setUsansce(transaction.getRefinancingPeriod());
		} else if (transaction.getRequirementType().equalsIgnoreCase("ConfirmAndDiscount")) {
			details.setUsansce(transaction.getConfirmationPeriod());
		} else if (transaction.getRequirementType().equalsIgnoreCase("Banker")) {
			details.setUsansce(transaction.getDiscountingPeriod());
		} else {
			details.setUsansce("");
		}
		return details;
	}

//	public static QuotationDetailsResponse mapQuotationDetails(NimaiMQuotation quotation) {
//		QuotationDetailsResponse response = new QuotationDetailsResponse();
//		response.setQuotationId(quotation.getQuotationId());
//		response.setCurrency(quotation.getCurrency());
//		response.setBankUserid(customerRepository.findBankName(quotation.getBankUserid()));
//		response.setQuotationStatus(quotation.getQuotationStatus());
//		response.setTotalQuoteValue(quotation.getTotalQuoteValue());
//		response.setTransactionId(quotation.getTransactionId().getTransactionId());
//		response.setValidityDate(quotation.getValidityDate());
//		response.setConfChgsIssuanceToMatur(quotation.getConfChgsIssuanceToMatur());
//		response.setConfChgsIssuanceToNegot(quotation.getConfChgsIssuanceToNegot());
//		response.setConfirmationCharges(quotation.getConfirmationCharges());
//		response.setMinimumTransactionCharges(quotation.getMinimumTransactionCharges());
//		response.setOtherCharges(quotation.getOtherCharges());
//		response.setUserid(quotation.getUserid().getUserid());
//		return response;
//	}

	// bashir
	public static BankDetailsResponse mapBankDetails(NimaiMCustomer cust) {
		BankDetailsResponse response = new BankDetailsResponse();
		response.setSubscriberType(cust.getSubscriberType().toUpperCase());
		response.setUserid(cust.getUserid());
		response.setFirstName(cust.getFirstName());
		response.setLastName(cust.getLastName());
		response.setEmailAddress(cust.getEmailAddress());
		response.setAdditionalEmail1(cust.getEmailAddress1()!= null ? cust.getEmailAddress1():"");
		response.setAdditionalEmail2(cust.getEmailAddress2()!= null ? cust.getEmailAddress2():"");
		response.setAdditionalEmail3(cust.getEmailAddress3()!= null ? cust.getEmailAddress3():"");		
		response.setMobileNumber(cust.getMobileNumber());
		response.setCountryName(cust.getCountryName());
		response.setLandline(cust.getLandline());
		//response.setDesignation(cust.getDesignation());
		//response.setDesignation(nfo.getOwnerDesignation());
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
		StringBuffer sf = new StringBuffer();
		StringBuffer bGoods = new StringBuffer();
		
		try
		{
			if (cust.getRmId() != null && cust.getRmStatus().equalsIgnoreCase("Active"))
				response.setRmFirstName(cust.getRmId());
		}
		catch(Exception e)
		{
			response.setRmFirstName("");
		}
			//---------------Bash ----------------//
		if (cust.getNimaiFIntcountryList().size() != 0) {
			int count = 0;
			List<NimaiFIntcountry> intC = cust.getNimaiFIntcountryList();
			for (NimaiFIntcountry s : intC) {
				if (count != 0) {
					sf.append(", " + s.getCountryName());
				} else {
					sf.append(s.getCountryName());
				}
				count++;				
			}
			response.setIntCountries(sf.toString());
		} else {
			response.setIntCountries("");
		}

		if (cust.getNimaiFBlkgoodsList().size() != 0) {
			int count=0;
			List<NimaiFBlkgoods> intC = cust.getNimaiFBlkgoodsList();
			for(NimaiFBlkgoods goods:intC) {
				if(count!=0) {
					bGoods.append(", "+ goods.getGoodsName());
				}else {
					bGoods.append(goods.getGoodsName());
				}
				count++;
			}
			response.setBlkGoods(bGoods.toString());
		}else {
			response.setBlkGoods("");
		}
		
		// response.setBuisnessCountry(cust.getRegisteredCountry());
		
		//********************************************************************//
		/* if (cust.getNimaiFOwnerList().size() != 0) {
			response.setDesignation(cust.getNimaiFOwnerList().get(0).getOwnerDesignation());
	  		response.setMdFirstName(cust.getNimaiFOwnerList().get(0).getOwnerFname());
			response.setMdLastName(cust.getNimaiFOwnerList().get(0).getOwnerLname());
		}  */
		
		 if (cust.getNimaiFOwnerList().size() != 0) {
			 
			 int count = 0;
			 List<NimaiFOwner> intO=cust.getNimaiFOwnerList();
			 for(NimaiFOwner owner:intO )
			 {
			response.setDesignation(cust.getNimaiFOwnerList().get(0).getOwnerDesignation());
	  		response.setMdFirstName(cust.getNimaiFOwnerList().get(0).getOwnerFname());
			response.setMdLastName(cust.getNimaiFOwnerList().get(0).getOwnerLname());
			 }
			 count++;
			 return response;
		}  

		if (cust.getNimaiMReferList().size() != 0) {
			response.setReferredFirstName(cust.getNimaiMReferList().get(0).getUserid().getFirstName());
			response.setReferredLastName(cust.getNimaiMReferList().get(0).getUserid().getLastName());
			response.setReferredCompanyName(cust.getNimaiMReferList().get(0).getUserid().getCompanyName());
			response.setReferredUserId(cust.getNimaiMReferList().get(0).getUserid().getUserid());
		}
		return response;
	}

}
