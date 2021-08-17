package com.nimai.admin.specification;

import static org.springframework.data.jpa.domain.Specification.where;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.nimai.admin.model.NimaiMmTransaction;
import com.nimai.admin.payload.SearchRequest;

@Component
public class TransactionDashboardSpecification extends BaseSpecification<NimaiMmTransaction, SearchRequest> {

	@Override
	public Specification<NimaiMmTransaction> getFilter(SearchRequest request) {
		return (root, query, cb) -> {
			return where(valueBetween("validity", request.getDateFrom(), request.getDateTo())).toPredicate(root, query,
					cb);
		};
	}

	private Specification<NimaiMmTransaction> valueBetween(String attribute, String dateFrom, String dateTo) {
		System.out.println(dateFrom + " " + dateTo);
		return (root, query, cb) -> {
			if (dateFrom != null && dateTo != null) {
				return cb.between(root.get(attribute), dateFrom, (dateTo));
			} else {
				return null;
			}
		};
	}
}
