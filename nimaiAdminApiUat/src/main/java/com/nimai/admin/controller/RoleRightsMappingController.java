package com.nimai.admin.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nimai.admin.exception.ResourceNotFoundException;
import com.nimai.admin.payload.PagedResponse;
import com.nimai.admin.payload.RightsMappingRequest;
import com.nimai.admin.service.RoleRightsMappingService;

/**
 * 
 * @author sahadeo.naik
 *
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/roleRightsMapping")
public class RoleRightsMappingController {
	
	@Autowired
	RoleRightsMappingService mappingService;
	

	@PostMapping("/list")
	public PagedResponse<?> getAllMapping(@RequestBody Map<String, String> data) {
		return mappingService.getAllMapping(data);
	}
	
		
	@PostMapping("/getRoleList")
	public List<?> getRoleList() throws ResourceNotFoundException {
		return mappingService.getRoleList();
	}
	
	@PostMapping("/getRightsList")
	public List<?> getRightsList() throws ResourceNotFoundException {
		return mappingService.getRightsList();
	}
	
	@PostMapping("/rightsList/{id}")
	public List<?> getRightsListByRoleId (@PathVariable(value = "id") int roleId) throws ResourceNotFoundException {
		return mappingService.getRightsListByRoleId(roleId);
	}

	@PostMapping("/saveMaping")
	public  ResponseEntity<?> saveRoleRights (@Valid @RequestBody RightsMappingRequest detailRequest){
		return mappingService.saveRoleRights(detailRequest);
	}

}
