package com.nimai.admin.specification;

import static org.springframework.data.jpa.domain.Specification.where;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.nimai.admin.model.NimaiMEmployee;
import com.nimai.admin.payload.SearchRequest;

@Component
public class EmployeeSpecification extends BaseSpecification<NimaiMEmployee, SearchRequest> {

	@Override
	public Specification<NimaiMEmployee> getFilter(SearchRequest request) {
		return (root, query, cb) -> {
			return where(empContains("status", request.getStatus())).toPredicate(root, query, cb);
		};
	}

	private Specification<NimaiMEmployee> empContains(String attribute, String value) {
		return (root, query, cb) -> {
			if (value == null) {
				return null;
			}

			return cb.equal(root.get(attribute), value);
		};
	}

}
