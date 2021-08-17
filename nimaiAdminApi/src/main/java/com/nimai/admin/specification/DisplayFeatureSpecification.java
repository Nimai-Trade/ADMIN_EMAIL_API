package com.nimai.admin.specification;

import static org.springframework.data.jpa.domain.Specification.where;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.nimai.admin.model.NimaiMDisplayFeatures;
import com.nimai.admin.payload.SearchRequest;

@Component
public class DisplayFeatureSpecification extends BaseSpecification<NimaiMDisplayFeatures, SearchRequest> {

	@Override
	public Specification<NimaiMDisplayFeatures> getFilter(SearchRequest request) {

		return (root, query, cb) -> {
			return where(likeValuesContains("status", request.getStatus()))
					.and(valuesContains("country", request.getCountry()))
					.toPredicate(root, query, cb);
		};
	}

	private Specification<NimaiMDisplayFeatures> likeValuesContains(String attribute, String value) {
		return (root, query, cb) -> {
			if (value != null) {
				return cb.like(root.get(attribute), cb.literal(value));
			} else {
				return null;
			}
		};
	}
	
	private Specification<NimaiMDisplayFeatures> valuesContains(String attribute, String value) {
		return (root, query, cb) -> {
			if (value == null || value.equalsIgnoreCase("all")) {
				return null;
			}
			return cb.equal(root.get(attribute), value);
		};
	}
}
