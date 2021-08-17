package com.nimai.admin.specification;

import static org.springframework.data.jpa.domain.Specification.where;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.nimai.admin.model.NimaiMCustomer;

@Component
public class CustomerSpecification extends BaseSpecification<NimaiMCustomer, Map<String, String>> {

	@Override
	public Specification<NimaiMCustomer> getFilter(Map<String, String> request) {
		return (root, query, cb) -> {
			return where(custContains("rmId"))
					.and(likeValuesContains("userid", request.get("userType")))
					.and(iN("countryName", request.get("countryNames")))
					.and(likeValuesContains("rmStatus", request.get("status")))
					.toPredicate(root, query, cb);
		};
	}

	private Specification<NimaiMCustomer> custContains(String attribute) {
		return (root, query, cb) -> {
//			if (value == null) {
//				return null;
//			}
			return cb.isNull(root.get(attribute));
		};
	}

	private Specification<NimaiMCustomer> valuesContains(String attribute, String value) {
		return (root, query, cb) -> {
			return cb.equal(root.get(attribute), value);
		};
	}

	private Specification<NimaiMCustomer> likeValuesContains(String attribute, String value) {
		return (root, query, cb) -> {
			if (value != null) {
				return cb.like(root.get(attribute), cb.literal(value + "%"));
			} else {
				return null;
			}
		};
	}

	// bashir change added 10-10
	private Specification<NimaiMCustomer> iN(String attribute, String inValue) {
		return (root, query, cb) -> {
			if (inValue != null) {
				List<String> value = Stream.of(inValue.split(",", -1)).collect(Collectors.toList());
				return root.get(attribute).in(value);
			} else {
				return null;
			}
		};
	}
	
	
	public Specification<NimaiMCustomer> getGrantRmFilter(Map<String, String> request) {
		return (root, query, cb) -> {
			return where(iN("countryName", request.get("countryNames")))
					.and(likeValuesContains("userid", request.get("userType")))
					.and(likeValuesContains("rmStatus", request.get("status")))
					.toPredicate(root, query, cb);
		};
	}

}
