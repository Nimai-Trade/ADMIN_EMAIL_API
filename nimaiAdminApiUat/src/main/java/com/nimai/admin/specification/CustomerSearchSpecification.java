package com.nimai.admin.specification;

import static org.springframework.data.jpa.domain.Specification.where;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.nimai.admin.model.NimaiMCustomer;
import com.nimai.admin.model.NimaiMRefer;
import com.nimai.admin.payload.SearchRequest;

@Component
public class CustomerSearchSpecification extends BaseSpecification<NimaiMCustomer, SearchRequest> {

	@Override
	public Specification<NimaiMCustomer> getFilter(SearchRequest request) {
		return (root, query, cb) -> {
			return where(likeValuesContains("userid", request.getUserId()))
					.and(likeValuesContains("emailAddress", request.getEmailId()))
					.and(likeValuesContains("mobileNumber", request.getMobileNo()))
					.and(likeValuesContains("registeredCountry", request.getCountry()))
					.and(likeValuesContains("companyName", request.getCompanyName()))
					.and(valuesContains("kycStatus", request.getTxtStatus()))
					.and(valuesContains("rmId", request.getLoginUserId()))
					.and(valuesContains("rmStatus", request.getRmStatus()))
					.and(iN("countryName", request.getCountryNames()))
					.and(valueBetween("insertedDate", request.getDateFrom(), request.getDateTo()))
					.and((valuesContains("subscriberType", request.getSubscriberType()))
							.or(valuesContains("bankType", request.getBankType())))
					.toPredicate(root, query, cb);
		};
	}
	private Specification<NimaiMCustomer> notEqualToValue(String attribute, String value) {
		return (root, query, cb) -> {
			if (value != null) {
				return cb.notEqual(root.get(attribute), cb.literal(value));
			} else {
				return null;
			}
		};
	}
	private Specification<NimaiMCustomer> valueBetween(String attribute, String dateFrom, String dateTo) {
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

	private Specification<NimaiMCustomer> valuesContains(String attribute, String value) {
		return (root, query, cb) -> {
			if (value == null || value.equalsIgnoreCase("all")) {
				return null;
			} else if (value.equalsIgnoreCase("null")) {
				return root.get(attribute).isNull();
			}
			return cb.equal(root.get(attribute), value);
		};
	}

	private Specification<NimaiMCustomer> likeValuesContains(String attribute, String value) {
		return (root, query, cb) -> {
			if (value == null) {
				return null;
			} else if (value.equalsIgnoreCase("All")) {
				return null;
			}
			return cb.like(root.get(attribute), cb.literal("%" + value + "%"));
		};
	}

	public Specification<NimaiMCustomer> getBankFilter(SearchRequest request) {
		return (root, query, cb) -> {
			return where(likeValuesContains("userid", request.getUserId()))
					.and(likeValuesContains("emailAddress", request.getEmailId()))
					.and(likeValuesContains("mobileNumber", request.getMobileNo()))
					.and(likeValuesContains("registeredCountry", request.getCountry()))
					.and(likeValuesContains("bankName", request.getBankName()))
					.and(valuesContains("kycStatus", request.getTxtStatus()))
					.and(valuesContains("rmId", request.getLoginUserId()))
					.and(valuesContains("rmStatus", request.getRmStatus()))
					.and(valuesContains("subscriberType", request.getSubscriberType()))
					.and(iN("registeredCountry", request.getCountryNames()))
					.and(valuesContains("bankType", request.getBankType())).toPredicate(root, query, cb);
		};
	}

	public Specification<NimaiMCustomer> getReferrerFilter(SearchRequest request) {

		return (root, query, cb) -> {
			return where(valuesContains("userid", request.getUserId()))
					.and(inUserId("kycStatus", request.getTxtStatus()))
//					.and((valuesContains("subscriberType", request.getSubscriberType())))
					.toPredicate(root, query, cb);
		};
	}

	public static Specification<NimaiMCustomer> inUserId(String attribute, String value) {
		return (root, query, cb) -> {
			if (value == null) {
				return null;
			} else if (value.equalsIgnoreCase("all")) {
				return null;
			}
			final Join<NimaiMRefer, NimaiMCustomer> po = root.join("userid", JoinType.RIGHT);
			return cb.like(po.get(attribute), cb.literal("%" + value + "%"));
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
	
	public Specification<NimaiMCustomer> getWireTransferFilter(SearchRequest request) {
		System.out.println("==============countrynames"+request.getCountry());
		System.out.println("==============countrynames"+request.getCountryNames());
		return (root, query, cb) -> {
			return where(likeValuesContains("userid", request.getUserId()))
					//.and(likeValuesContains("registeredCountry", request.getCountry()))
					.and(likeValuesContains("registeredCountry", request.getCountryNames()))
					.and(iN("registeredCountry", request.getCountryNames()))
					.and(valuesContains("subscriberType", "REFERRER"))
					.and(valuesNotContains("modeOfPayment", "Wire"))
					.and(valuesNotContains("paymentStatus", "Pending"))
					.or(valuesNotContains("paymentStatus", "Maker Approved"))
					
					
					.toPredicate(root, query, cb);
			
			
		};
	}
	
	private Specification<NimaiMCustomer> valuesNotContains(String attribute, String value) {
		return (root, query, cb) -> {			
			return cb.notEqual(root.get(attribute), value);
		};
	}

}
