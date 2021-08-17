package com.nimai.admin.specification;

import static org.springframework.data.jpa.domain.Specification.where;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.nimai.admin.model.NimaiSubscriptionVas;
import com.nimai.admin.payload.SearchRequest;


@Component
public class NimaiSubscriptionVasSpecification extends BaseSpecification<NimaiSubscriptionVas, SearchRequest> {

	
	public Specification<NimaiSubscriptionVas> getWireTransferFilter(SearchRequest request) {
		System.out.println("================country names==============="+request.getCountry());
		System.out.println("================countryName==============="+request.getCountryNames());
		return (root, query, cb) -> {
			return where(likeValuesContains("userId", request.getUserId()))
					.and(likeValuesContains("countryName", request.getCountry()))
					.and(iN("countryName", request.getCountryNames()))
					.and(valuesContains("mode", "Wire"))
					.and(valuesNotContains("paymentSts", "Maker Approved"))
					.or(valuesNotContains("paymentSts", "Pending"))
					.and(valuesNotContains("paymentSts", "Success"))
					
					.toPredicate(root, query, cb);
			
			
		};
	}
	
	// bashir change added 10-10
	private Specification<NimaiSubscriptionVas> iN(String attribute, String inValue) {
		return (root, query, cb) -> {
			if (inValue != null) {
				List<String> value = Stream.of(inValue.split(",", -1)).collect(Collectors.toList());
				return root.get(attribute).in(value);
			} else {
				return null;
			}
		};
	}
	
	
	private Specification<NimaiSubscriptionVas> valuesNotContains(String attribute, String value) {
		return (root, query, cb) -> {			
			return cb.notEqual(root.get(attribute), value);
		};
	}

	

	@Override
	public Specification<NimaiSubscriptionVas> getFilter(SearchRequest request) {
		// TODO Auto-generated method stub
		return null;
	}
	private Specification<NimaiSubscriptionVas> likeValuesContains(String attribute, String value) {
		return (root, query, cb) -> {
			if (value == null) {
				return null;
			} else if (value.equalsIgnoreCase("All")) {
				return null;
			}
			return cb.like(root.get(attribute), cb.literal("%" + value + "%"));
		};
	}
	
	private Specification<NimaiSubscriptionVas> valuesContains(String attribute, String value) {
		return (root, query, cb) -> {
			if (value == null || value.equalsIgnoreCase("all")) {
				return null;
			} else if (value.equalsIgnoreCase("null")) {
				return root.get(attribute).isNull();
			}
			return cb.equal(root.get(attribute), value);
		};
	}
}
