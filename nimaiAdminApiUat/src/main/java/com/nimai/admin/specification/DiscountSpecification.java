package com.nimai.admin.specification;

import static org.springframework.data.jpa.domain.Specification.where;

import java.sql.Date;
import java.time.LocalDate;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.nimai.admin.model.NimaiMCustomer;
import com.nimai.admin.model.NimaiMDiscount;
import com.nimai.admin.payload.SearchRequest;

@Component
public class DiscountSpecification extends BaseSpecification<NimaiMDiscount, SearchRequest> {

	@Override
	public Specification<NimaiMDiscount> getFilter(SearchRequest request) {

		return (root, query, cb) -> {
			return where(likeValuesContains("status", request.getStatus()))
					.and(valueBetween("createdDate", request.getDateFrom(), request.getDateTo()))
					.and(valuesContains("couponFor", request.getCustomerType())).toPredicate(root, query, cb);
		};
	}

	private Specification<NimaiMDiscount> likeValuesContains(String attribute, String value) {
		return (root, query, cb) -> {
			if (value != null) {
				return cb.like(root.get(attribute), cb.literal("%" + value + "%"));
			} else {
				return null;
			}
		};
	}

	private Specification<NimaiMDiscount> valueBetween(String attribute, String dateFrom, String dateTo) {
		System.out.println(dateFrom + " " + dateTo);
		return (root, query, cb) -> {
			if (dateFrom != null && dateTo != null) {
				return cb.between(root.get(attribute), Date.valueOf(dateFrom),
						Date.valueOf(LocalDate.parse(dateTo).plusDays(1)));
			} else {
				return null;
			}
		};
	}

	private Specification<NimaiMDiscount> valuesContains(String attribute, String value) {
		return (root, query, cb) -> {
			if (value == null || value.equalsIgnoreCase("all")) {
				return null;
			}
			return cb.equal(root.get(attribute), value);
		};
	}

}
