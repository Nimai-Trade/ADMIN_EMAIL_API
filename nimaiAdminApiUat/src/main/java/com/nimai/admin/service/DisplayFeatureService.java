package com.nimai.admin.service;

import java.util.List;

import com.nimai.admin.model.NimaiMDisplayFeatures;
import com.nimai.admin.payload.DisplayFeatureRequest;
import com.nimai.admin.payload.PagedResponse;
import com.nimai.admin.payload.SearchRequest;

public interface DisplayFeatureService {

	public List<NimaiMDisplayFeatures> saveDispFeat(List<DisplayFeatureRequest> request);

	public PagedResponse<?> listDisplayFeatures(SearchRequest request);

	public List<String> countryList();
}
