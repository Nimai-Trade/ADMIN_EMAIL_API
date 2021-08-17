package com.nimai.admin.service.impl;


import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.nimai.admin.model.NimaiMDisplayFeatures;
import com.nimai.admin.payload.DisplayFeatureRequest;
import com.nimai.admin.payload.DisplayFeatureResponse;
import com.nimai.admin.payload.PagedResponse;
import com.nimai.admin.payload.SearchRequest;
import com.nimai.admin.repository.DisplayFeatureRepository;
import com.nimai.admin.service.DisplayFeatureService;
import com.nimai.admin.specification.DisplayFeatureSpecification;

@Service
public class DisplayFeatureServiceImpl implements DisplayFeatureService {

	@Autowired
	DisplayFeatureRepository dispFeatureRepo;

	@Autowired
	DisplayFeatureSpecification dispSpecification;

	@Override
	@Transactional
	public List<NimaiMDisplayFeatures> saveDispFeat(List<DisplayFeatureRequest> request) {

		System.out.println("Inside save : " + request.size());
		List<NimaiMDisplayFeatures> featList = new ArrayList<NimaiMDisplayFeatures>();
		for (DisplayFeatureRequest dispFeature : request) {
			System.out.println("Contry :" + dispFeature.getCountry() + " " + dispFeature.getAvgAmount());
			NimaiMDisplayFeatures mDisp = new NimaiMDisplayFeatures();
			if (dispFeatureRepo.getCountryCcy(dispFeature.getCountry(), dispFeature.getCcy()) > 0) {
				int flag = dispFeatureRepo.updateDispFeature("Inactive", dispFeature.getCountry());
				if (flag > 0) {
					System.out.println("Successfully updated!!!!");
				}
			}
			mDisp.setCountry(dispFeature.getCountry());
			mDisp.setAvgAmount(dispFeature.getAvgAmount());
			mDisp.setCcy(dispFeature.getCcy());
			mDisp.setStatus("Active");

			NimaiMDisplayFeatures f = dispFeatureRepo.save(mDisp);
			featList.add(f);
		}
		return featList;

	}

	@Override
	public PagedResponse<?> listDisplayFeatures(SearchRequest request) {

		Pageable pageable = PageRequest.of(request.getPage(), request.getSize(),
				request.getDirection().equalsIgnoreCase("desc") ? Sort.by(request.getSortBy()).descending()
						: Sort.by(request.getSortBy()).ascending());
		Page<NimaiMDisplayFeatures> featureList = dispFeatureRepo.findAll(dispSpecification.getFilter(request),
				pageable);

		List<DisplayFeatureResponse> responses = featureList.map(sub -> {
			DisplayFeatureResponse response = new DisplayFeatureResponse();
			response.setCountry(sub.getCountry());
			response.setAvgAmount(sub.getAvgAmount());
			response.setCreatedDate(sub.getCreatedDate());
			response.setCcy(sub.getCcy());
			return response;
		}).getContent();

		return new PagedResponse<>(responses, featureList.getNumber(), featureList.getSize(),
				featureList.getTotalElements(), featureList.getTotalPages(), featureList.isLast());
	}

	@Override
	public List<String> countryList() {
		return dispFeatureRepo.findNonReferencedCountry();
	}

}
