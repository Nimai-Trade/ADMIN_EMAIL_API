package com.nimai.admin.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nimai.admin.model.NimaiMRights;
import com.nimai.admin.model.NimaiMRole;
import com.nimai.admin.model.NimaiMpRoleRights;
import com.nimai.admin.payload.ApiResponse;
import com.nimai.admin.payload.PagedResponse;
import com.nimai.admin.payload.RightsMappingRequest;
import com.nimai.admin.payload.RightsResponse;
import com.nimai.admin.payload.RoleResponse;
import com.nimai.admin.payload.RoleRightsMappingResponse;
import com.nimai.admin.repository.RightsRepository;
import com.nimai.admin.repository.RoleRepository;
import com.nimai.admin.repository.RoleRightsRepository;
import com.nimai.admin.service.RoleRightsMappingService;
import com.nimai.admin.util.ModelMapper;

/**
 * Created by sahadeo Naik Changes 16 Jun 2020
 */
@Service
public class RoleRightsMappingServiceImpl implements RoleRightsMappingService {

	@Autowired
	RoleRightsRepository roleRightsRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	RightsRepository rightsRepository;

	@Override
	public PagedResponse<?> getAllMapping(Map<String, String> data) {
		Pageable pageable = PageRequest.of(Integer.parseInt(data.get("page")), Integer.parseInt(data.get("size")),
				data.get("direction").equalsIgnoreCase("desc") ? Sort.by(data.get("sortBy")).descending()
						: Sort.by(data.get("sortBy")).ascending());
		Page<NimaiMpRoleRights> mappingList = roleRightsRepository.findAll(pageable);

		List<RoleRightsMappingResponse> responses = mappingList.map(responseData -> {
			return ModelMapper.mapRoleRightsToResponse(responseData);
		}).getContent();

		return new PagedResponse<>(responses, mappingList.getNumber(), mappingList.getSize(),
				mappingList.getTotalElements(), mappingList.getTotalPages(), mappingList.isLast());

	}

	@Override
	public List<?> getRoleList() {
		List<NimaiMRole> list = roleRepository.findAll();
		if (list.size() != 0 && list != null)
			return list.stream().filter(status -> status.getRoleStatus().equals("ACTIVE"))
					.map(role -> new RoleResponse(role.getRoleId(), role.getRoleName(), role.getRoleShortName()))
					.collect(Collectors.toList());
		else
			return null;
	}

	@Override
	public List<?> getRightsList() {
		List<NimaiMRights> list = rightsRepository.findAll();
		if (list.size() != 0 && list != null)
			return list.stream().filter(status -> status.getRightStatus().equals("ACTIVE")).map(
					right -> new RightsResponse(right.getRightId(), right.getRightName(), right.getRightShortName()))
					.collect(Collectors.toList());
		else
			return null;
	}

	@Override
	public List<?> getRightsListByRoleId(int roleId) {
		List<NimaiMpRoleRights> list = roleRightsRepository.findRightsByRoleId(roleId);
		if (list.size() != 0 && list != null)
			return list.stream().filter(status -> status.getStatus().equals("ACTIVE"))
					.map(right -> new Integer(right.getRightId().getRightId())).collect(Collectors.toList());
		else
			return null;
	}

	@Override
	public ResponseEntity<?> saveRoleRights(RightsMappingRequest detailRequest) {
		try {

			List<Integer> list = roleRightsRepository.findRightsIdByRoleId(detailRequest.getRoleId());
//			if (list.size() != 0 && list != null) {
			for (Integer temp : detailRequest.getRightsId()) {
				if (list.contains(temp))
					System.out.println(" Already Rights available to user :: " + temp);
				else {
					System.out.println(" Need to add " + temp);
					NimaiMpRoleRights mpRight = new NimaiMpRoleRights();
					mpRight.setRoleId(new NimaiMRole(detailRequest.getRoleId()));
					mpRight.setRightId(new NimaiMRights(temp));
					mpRight.setStatus("ACTIVE");
					roleRightsRepository.save(mpRight);
				}
			}
			list.removeAll(detailRequest.getRightsId());
			System.out.println("list Contains :" + list);
			if (list.size() > 0) {
				for (Integer rightId : list) {
					NimaiMpRoleRights updateRight = roleRightsRepository.getByRoleAndRight(detailRequest.getRoleId(),
							rightId);
					updateRight.setStatus("INACTIVE");
					roleRightsRepository.save(updateRight);
				}
			}
//			}
			return new ResponseEntity<>(new ApiResponse(true, "Role and Rights Mapping done Successfully."),
					HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse(true, "Error Occurs while Role and Rights Mapping."),
					HttpStatus.BAD_REQUEST);
		}
	}

}
