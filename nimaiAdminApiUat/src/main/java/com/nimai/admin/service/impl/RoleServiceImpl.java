package com.nimai.admin.service.impl;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nimai.admin.exception.ResourceNotFoundException;
import com.nimai.admin.model.NimaiMRole;
import com.nimai.admin.payload.ApiResponse;
import com.nimai.admin.payload.PagedResponse;
import com.nimai.admin.payload.RoleRequest;
import com.nimai.admin.payload.RoleResponse;
import com.nimai.admin.repository.RoleRepository;
import com.nimai.admin.service.RoleService;
import com.nimai.admin.util.ModelMapper;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	RoleRepository roleRepository;

	@Override
	public PagedResponse<?> getAllRole(Map<String, String> data) {

		Pageable pageable = PageRequest.of(Integer.parseInt(data.get("page")), Integer.parseInt(data.get("size")),
				data.get("direction").equalsIgnoreCase("desc") ? Sort.by(data.get("sortBy")).descending()
						: Sort.by(data.get("sortBy")).ascending());
		Page<NimaiMRole> roleList = roleRepository.findAll(pageable);

		List<RoleResponse> responses = roleList.map(role -> {
			return ModelMapper.mapRoleToResponse(role);
		}).getContent();

//		return new PagedResponse<>(responses);
		return new PagedResponse<>(responses, roleList.getNumber(), roleList.getSize(), roleList.getTotalElements(),
				roleList.getTotalPages(), roleList.isLast());
	}

	@Override
	public ResponseEntity<RoleResponse> getRoleById(Integer roleId) {
		NimaiMRole role = roleRepository.getOne(roleId);

		if (role != null) {
			RoleResponse roleResponse = ModelMapper.mapRoleToResponse(role);

			return new ResponseEntity<RoleResponse>(roleResponse, HttpStatus.OK);
		} else {
			throw new ResourceNotFoundException("No Mould Details exist for given id");
		}
	}

	@Override
	public ResponseEntity<?> createOrUpdateRole(@Valid RoleRequest roleRequest) {
		try {

			NimaiMRole role = null;
			String msg = "";
			System.out.println("Role ID :: " + roleRequest.getRoleId());

			if (roleRequest.getRoleId() != null) {
				role = roleRepository.getOne(roleRequest.getRoleId());
				msg = "Role updated successfully";
			} else {
				role = new NimaiMRole();
				msg = "Role created successfully";
			}
			role.setRoleName(roleRequest.getRoleName());
			role.setRoleShortName(roleRequest.getRoleShortName());
			role.setRoleStatus(roleRequest.getRoleStatus());
			roleRepository.save(role);

			return new ResponseEntity<>(new ApiResponse(true, msg), HttpStatus.CREATED);
		} catch (Exception e) {
			System.out.println("Exception in Role :" + e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<>(new ApiResponse(true, "Error due to some technical issue"),
					HttpStatus.EXPECTATION_FAILED);
		}

	}

	@Override
	public ResponseEntity<?> updateRoleStatus(String roleId, String status) {
		NimaiMRole role = roleRepository.getOne(Integer.parseInt(roleId));
		role.setRoleStatus(status);
		roleRepository.save(role);
		return new ResponseEntity<>(new ApiResponse(true, "Role status updated successfully..."), HttpStatus.CREATED);

	}

	@Override
	public boolean checkShortName(String shortName) {
		return roleRepository.existsByRoleShortName(shortName);
	}
	
	@Override
	public List<NimaiMRole> getListOfRoles(String empCode) {
		// TODO Auto-generated method stub
		return roleRepository.getDataByEmpCode(empCode);
	}

}
