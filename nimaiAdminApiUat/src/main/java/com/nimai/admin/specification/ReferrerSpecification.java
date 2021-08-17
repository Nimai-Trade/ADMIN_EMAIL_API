package com.nimai.admin.specification;

import static org.springframework.data.jpa.domain.Specification.where;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.nimai.admin.model.NimaiMCustomer;
import com.nimai.admin.model.NimaiMRefer;
import com.nimai.admin.payload.SearchRequest;

@Component
public class ReferrerSpecification extends BaseSpecification<NimaiMRefer, SearchRequest> {
	@Override
	public Specification<NimaiMRefer> getFilter(SearchRequest request) {

		return (root, query, cb) -> {
			return where(valuesContains("userid", 
					request.getUserId() == null ? null : new NimaiMCustomer(request.getUserId())))
//					.and(inUserId("kycStatus", request.getTxtStatus()))
//					.and((valuesContains("subscriberType", request.getSubscriberType())))
					.toPredicate(root, query, cb);
		};
	}

//	private Specification<NimaiMCustomer> valuesContains(String attribute, String value) {
//		return (root, query, cb) -> {
//			if (value == null || value.equalsIgnoreCase("all")) {
//				return null;
//			}
//			return cb.equal(root.get(attribute), value);
//		};
//	}
	
	private Specification<NimaiMRefer> valuesContains(String attribute, NimaiMCustomer value) {
		return (root, query, cb) -> {
			if (value == null) {
				return null;
			}
			return cb.equal(root.get(attribute), value);
		};
	}
	
	public static Specification<NimaiMRefer> inUserId(String attribute, String value) {
		return (root, query, cb) -> {
			if (value == null) {
				return null;
			} else if (value.equalsIgnoreCase("all")) {
				return null;
			}
			final Join<NimaiMRefer, NimaiMCustomer> po = root.join("emailAddress", JoinType.LEFT);
			return cb.like(po.get(attribute), cb.literal("%" + value + "%"));
		};
	}



//	private Specification<NimaiMRefer> likeValuesContains(String attribute, String value) {
//		return (root, query, cb) -> {
//			if (value != null) {
//				return cb.like(root.get(attribute), cb.literal("%" + value + "%"));
//			} else {
//				return null;
//			}
//		};
//	}
}
