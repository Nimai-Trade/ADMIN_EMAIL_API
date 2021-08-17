package com.nimai.admin.specification;

import static org.springframework.data.jpa.domain.Specification.where;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.nimai.admin.model.NimaiMSubscriptionPlan;
import com.nimai.admin.payload.SearchRequest;
@Component
public class SubscriptionSpecification extends BaseSpecification<NimaiMSubscriptionPlan, SearchRequest> {

	@Override
	public Specification<NimaiMSubscriptionPlan> getFilter(SearchRequest request) {

		return (root, query, cb) -> {
			return where(likeValuesContains("countryName", request.getCountry()))
					.and(likeValuesContainsType("customerType", request.getCustomerType())).toPredicate(root, query, cb);
		};
	}

	private Specification<NimaiMSubscriptionPlan> likeValuesContains(String attribute, String value) {
		return (root, query, cb) -> {
			if (value != null) {
				return cb.like(root.get(attribute), cb.literal("%" + value + "%"));
			} else {
				return null;
			}
		};
	}
	
	private Specification<NimaiMSubscriptionPlan> likeValuesContainsType(String attribute, String value) {
		return (root, query, cb) -> {
			if (value != null) {
				return cb.like(root.get(attribute), cb.literal( value ));
			} else {
				return null;
			}
		};
	}

}
