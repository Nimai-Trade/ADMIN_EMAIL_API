package com.nimai.admin.specification;

import static org.springframework.data.jpa.domain.Specification.where;

import java.sql.Date;
import java.time.LocalDate;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.nimai.admin.model.NimaiMCustomer;
import com.nimai.admin.model.NimaiSubscriptionDetails;
import com.nimai.admin.payload.SearchRequest;

@Component
public class SubscriptionDetailsSpecification extends BaseSpecification<NimaiSubscriptionDetails, SearchRequest> {

	@Override
	public Specification<NimaiSubscriptionDetails> getFilter(SearchRequest request) {
		return (root, query, cb) -> {
			return valueBetween("splanEndDate", request.getDateFrom(), request.getDateTo()).toPredicate(root, query,
					cb);
		};
	}

	private Specification<NimaiSubscriptionDetails> valueBetween(String attribute, String dateFrom, String dateTo) {
		return (root, query, cb) -> {
			if (dateFrom != null && dateTo != null) {
				return cb.between(root.get(attribute), Date.valueOf(dateFrom),
						Date.valueOf(LocalDate.parse(dateTo).plusDays(1)));
			} else {
				return null;
			}
		};
	}
	
	
	
}
