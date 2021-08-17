package com.nimai.admin.specification;

import static org.springframework.data.jpa.domain.Specification.where;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.nimai.admin.model.NimaiMVas;
import com.nimai.admin.payload.SearchRequest;

@Component
public class VasSpecification extends BaseSpecification<NimaiMVas, SearchRequest> {
	@Override
	public Specification<NimaiMVas> getFilter(SearchRequest request) {

		return (root, query, cb) -> {
			return where(likeValuesContains("countryName", request.getCountry())).toPredicate(root, query, cb);
		};
	}

	private Specification<NimaiMVas> likeValuesContains(String attribute, String value) {
		return (root, query, cb) -> {
			if (value != null) {
				return cb.like(root.get(attribute), cb.literal("%" + value + "%"));
			} else {
				return null;
			}
		};
	}

}
