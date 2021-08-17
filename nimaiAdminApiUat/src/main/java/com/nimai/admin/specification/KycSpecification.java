package com.nimai.admin.specification;

import static org.springframework.data.jpa.domain.Specification.where;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.nimai.admin.model.NimaiFKyc;
import com.nimai.admin.model.NimaiMCustomer;
import com.nimai.admin.model.NimaiMRefer;
import com.nimai.admin.payload.SearchRequest;

@Component
public class KycSpecification extends BaseSpecification<NimaiFKyc, SearchRequest> {

	@Override
	public Specification<NimaiFKyc> getFilter(SearchRequest request) {
		return (root, query, cb) -> {
			return where(valContains("kycStatus", request.getStatus()))
					//.and(inCountry("registeredCountry", request.getCountryNames()))
					.toPredicate(root, query, cb);
		};
	}

	private Specification<NimaiFKyc> valContains(String attribute, String value) {
		return (root, query, cb) -> {
			if (value == null) {
				return null;
			}

			return cb.equal(root.get(attribute), value);
		};
	}

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

	public static Specification<NimaiFKyc> inCountry(String attribute, String inValue) {
		return (root, query, cb) -> {
			if (inValue != null) {
				List<String> value = Stream.of(inValue.split(",", -1)).collect(Collectors.toList());
				System.out.println("value: "+value);
				System.out.println("Attribute: "+attribute);
				Join<NimaiFKyc, NimaiMCustomer> po = root.join("userid", JoinType.LEFT);
				System.out.println("po: "+po.get(attribute).in(value).toString());
				return po.get(attribute).in(value);
			} else {
				return null;
			}
		};
		//System.out.println("Query:"+query);
	}

}
