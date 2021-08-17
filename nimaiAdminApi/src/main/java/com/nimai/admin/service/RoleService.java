package com.nimai.admin.service;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;

import com.nimai.admin.model.NimaiMRole;
import com.nimai.admin.payload.PagedResponse;
import com.nimai.admin.payload.RoleRequest;
import com.nimai.admin.payload.RoleResponse;

public interface RoleService {

	PagedResponse<?> getAllRole(Map<String, String> data);

	ResponseEntity<RoleResponse> getRoleById(Integer roleId);

	ResponseEntity<?> createOrUpdateRole(@Valid RoleRequest roleRequest);

	ResponseEntity<?> updateRoleStatus(String roleId, String status);

	boolean checkShortName(String shortName);
	
	List<NimaiMRole> getListOfRoles(String empCode);

}
