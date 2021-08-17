package com.nimai.admin.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.nimai.admin.payload.PagedResponse;
import com.nimai.admin.payload.RightsMappingRequest;

public interface RoleRightsMappingService {

	PagedResponse<?> getAllMapping(Map<String, String> data);

	List<?> getRoleList();

	List<?> getRightsList();

	List<?> getRightsListByRoleId(int roleId);

	ResponseEntity<?> saveRoleRights(RightsMappingRequest detailRequest);

}
