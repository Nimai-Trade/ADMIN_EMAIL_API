package com.nimai.admin.util;


import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.nimai.admin.model.NimaiMEmployee;
import com.nimai.admin.model.NimaiMRights;
import com.nimai.admin.model.NimaiMRole;
import com.nimai.admin.payload.EmployeeListRequest;
import com.nimai.admin.payload.RightsRequest;
import com.nimai.admin.payload.RoleRequest;

@Component
public class ConverterService {


	ModelMapper modelMapper ;

	// convert entity to dto
	public RoleRequest convertToDto(NimaiMRole nimaiMRole) {
		return modelMapper.map(nimaiMRole, RoleRequest.class);
	}

	// convert dto to entity
	public NimaiMRole convertToEntity(RoleRequest roleRequest) {
		return modelMapper.map(roleRequest, NimaiMRole.class);
	}

	// convert entity to dto
	public EmployeeListRequest convertToDto(NimaiMEmployee nimaiMEmployee) {
		return modelMapper.map(nimaiMEmployee, EmployeeListRequest.class);
	}

	// convert dto to entity
	public NimaiMEmployee convertToEntity(EmployeeListRequest employeeListRequest) {
		return modelMapper.map(employeeListRequest, NimaiMEmployee.class);
	}

	// convert entity to dto
	public RightsRequest convertToDto(NimaiMRights rightObject) {
		return modelMapper.map(rightObject, RightsRequest.class);
	}

	// convert dto to entity
	public NimaiMRights convertToEntity(RightsRequest rightRequest) {
		return modelMapper.map(rightRequest, NimaiMRights.class);
	}

}
