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
import com.nimai.admin.model.NimaiMRights;
import com.nimai.admin.payload.ApiResponse;
import com.nimai.admin.payload.PagedResponse;
import com.nimai.admin.payload.RightsRequest;
import com.nimai.admin.payload.RightsResponse;
import com.nimai.admin.repository.RightsRepository;
import com.nimai.admin.service.RightsService;
import com.nimai.admin.util.ModelMapper;

@Service
public class RightsServiceImpl implements RightsService {

	@Autowired
	RightsRepository rightsRepository;

	@Override
	public PagedResponse<?> getAllRights(Map<String, String> data) {
		Pageable pageable = PageRequest.of(Integer.parseInt(data.get("page")), Integer.parseInt(data.get("size")),
				data.get("direction").equalsIgnoreCase("desc") ? Sort.by(data.get("sortBy")).descending()
						: Sort.by(data.get("sortBy")).ascending());
		Page<NimaiMRights> rightsList = rightsRepository.findAll(pageable);

		List<RightsResponse> responses = rightsList.map(right -> {
			return ModelMapper.mapRightsToResponse(right);
		}).getContent();

//		return new PagedResponse<>(responses);
		return new PagedResponse<>(responses, rightsList.getNumber(), rightsList.getSize(),
				rightsList.getTotalElements(), rightsList.getTotalPages(), rightsList.isLast());
	}

	@Override
	public ResponseEntity<RightsResponse> getRightsById(Integer rightId) {
		NimaiMRights rights = rightsRepository.getOne(rightId);

		if (rights != null) {
			RightsResponse rightsResponse = ModelMapper.mapRightsToResponse(rights);

			return new ResponseEntity<RightsResponse>(rightsResponse, HttpStatus.OK);
		} else {
			throw new ResourceNotFoundException("No Rights Details exist for given id");
		}
	}

	@Override
	public ResponseEntity<?> createOrUpdateRights(@Valid RightsRequest rightsRequest) {
		try {
			NimaiMRights rights = null;
			String msg = "";
			System.out.println("Right ID :: " + rightsRequest.getRightId());
			if (rightsRequest.getRightId() != null) {
				rights = rightsRepository.getOne(rightsRequest.getRightId());
				msg = "Right updated successfully";
			} else {
				rights = new NimaiMRights();
				msg = "Right created successfully";
			}

			rights.setRightName(rightsRequest.getRightName());
			rights.setRightShortName(rightsRequest.getRightShortName());
			rights.setRightStatus(rightsRequest.getRightStatus());
			rightsRepository.save(rights);

			return new ResponseEntity<>(new ApiResponse(true, msg), HttpStatus.CREATED);
		} catch (Exception e) {
			System.out.println("Exception in Rights :" + e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<>(new ApiResponse(true, "Error due to some technical issue"),
					HttpStatus.EXPECTATION_FAILED);
		}
	}

	@Override
	public ResponseEntity<?> updateRightStatus(String rightId, String status) {

		NimaiMRights right = rightsRepository.getOne(Integer.parseInt(rightId));
		right.setRightStatus(status);
		rightsRepository.save(right);

		return new ResponseEntity<>(new ApiResponse(true, "Right status updated successfully..."), HttpStatus.CREATED);
	}

	@Override
	public boolean checkShortName(String shortName) {
		return rightsRepository.existsByRightShortName(shortName);
	}

}
