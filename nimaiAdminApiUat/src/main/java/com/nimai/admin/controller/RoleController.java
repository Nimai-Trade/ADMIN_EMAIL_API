package com.nimai.admin.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nimai.admin.payload.PagedResponse;
import com.nimai.admin.payload.RoleRequest;
import com.nimai.admin.payload.RoleResponse;
import com.nimai.admin.service.RoleService;

/**
 * 
 * @author sahadeo.naik
 *
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/role")
public class RoleController {
	
	@Autowired
	RoleService roleService;
	

	@PostMapping("/list")
	public PagedResponse<?> getAllRole(@RequestBody Map<String, String> data) {
		return roleService.getAllRole(data);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<RoleResponse> getRoleById(@PathVariable(value = "id") Integer roleId) {
		return roleService.getRoleById(roleId);
	}
	
	@PostMapping("/save")
	public ResponseEntity<?> createOrUpdateRole(@Valid @RequestBody RoleRequest roleRequest) {
		return roleService.createOrUpdateRole(roleRequest);
	}
	
	@PostMapping("/updateRoleStatus")
	public ResponseEntity<?> updateRoleStatus(@RequestBody Map<String, String> data) {
		return roleService.updateRoleStatus(data.get("roleId"), data.get("status"));
	}
	
	@GetMapping("/checkShortName/{shortName}")
	public boolean checkShortName(@PathVariable(value = "shortName") String shortName){
		System.out.println("Check checkShortName exits " + shortName);
		return roleService.checkShortName(shortName);
	}

}
