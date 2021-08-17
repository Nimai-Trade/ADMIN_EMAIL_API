package com.nimai.admin.specification;

import static org.springframework.data.jpa.domain.Specification.where;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.nimai.admin.model.NimaiMQuotation;
import com.nimai.admin.model.NimaiMmTransaction;
import com.nimai.admin.payload.SearchRequest;

@Component
public class QuotationSpecification extends BaseSpecification<NimaiMQuotation, SearchRequest> {

	@Override
	public Specification<NimaiMQuotation> getFilter(SearchRequest request) {
		return (root, query, cb) -> {
			return where(valuesContains("transactionId",
					request.getTransactionId() == null ? null : new NimaiMmTransaction(request.getTransactionId())))
							.toPredicate(root, query, cb);
		};
	}

	private Specification<NimaiMQuotation> valuesContains(String attribute, NimaiMmTransaction value) {
		return (root, query, cb) -> {
			if (value == null) {
				return null;
			}
			return cb.equal(root.get(attribute), value);
		};
	}

	public Specification<NimaiMQuotation> quotationFilter(SearchRequest request) {	
		return (root, query, cb) -> {
			return where(contains("bankUserid", request.getUserId())).
					and(contains("quotationStatus", request.getStatus())).
					toPredicate(root, query, cb);
		};
	}

	private Specification<NimaiMQuotation> contains(String attribute, String value) {
		return (root, query, cb) -> {
			if (value == null) {
				return null;
			}
			return cb.equal(root.get(attribute), value);
		};
	}

}
