package com.nimai.admin.service.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.WeakHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.Tuple;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nimai.admin.controller.DashboardController;
import com.nimai.admin.model.NimaiMCustomer;
import com.nimai.admin.model.NimaiMEmployee;
import com.nimai.admin.model.NimaiMmTransaction;
import com.nimai.admin.model.NimaiSubscriptionDetails;
import com.nimai.admin.payload.CustomerStatResponse;
import com.nimai.admin.payload.DashCountryAnalysisResponse;
import com.nimai.admin.payload.DashNewUserStat;
import com.nimai.admin.payload.DashTransStat;
import com.nimai.admin.payload.DiscardResponse;
import com.nimai.admin.payload.PagedResponse;
import com.nimai.admin.payload.PendingRequestBean;
import com.nimai.admin.payload.SearchRequest;
import com.nimai.admin.payload.SubscriptionRenewalResponse;
import com.nimai.admin.repository.CustomerRepository;
import com.nimai.admin.repository.EmployeeRepository;
import com.nimai.admin.repository.SubscriptionDetailsRepository;
import com.nimai.admin.repository.TransactionsRepository;
import com.nimai.admin.service.DashboardService;
import com.nimai.admin.specification.CustomerSearchSpecification;
import com.nimai.admin.specification.SubscriptionDetailsSpecification;
import com.nimai.admin.specification.TransactionDashboardSpecification;
import com.nimai.admin.util.Utility;

@Service
public class DashboardServiceImpl implements DashboardService {

	private static Logger logger = LoggerFactory.getLogger(DashboardServiceImpl.class);

	@Autowired
	CustomerRepository custRepo;

	@Autowired
	CustomerSearchSpecification custSpecs;

	@Autowired
	CustomerRepository repo;

	@Autowired
	EmployeeRepository empRepo;

	@Autowired
	SubscriptionDetailsRepository subsRepo;

	@Autowired
	SubscriptionDetailsSpecification subSpecification;
	@Autowired
	TransactionDashboardSpecification transactionsSpecification;

	@Autowired
	TransactionsRepository transaRepo;

	// (IN `query_no` INT, IN `subscriberType` VARCHAR(80), IN `bankType`
	// VARCHAR(80), IN `status` VARCHAR(50), IN `exp_day` INT, IN `dateFrom`
	// VARCHAR(50), IN `dateTo` VARCHbAR(50), IN `cases` VARCHAR(20))

	@Override
	public int getConfAwaited() {
		System.out.println(Utility.getUserCountry());

		return custRepo.getDashboardCount(1, "", "", "", 0, "", "", "", Utility.getUserCountry());

	}

	@Override
	public int getpayApproval() {
		return custRepo.getDashboardCount(2, "", "", "", 0, "", "", "", Utility.getUserCountry());
	}

	@Override
	public int getAssignRmCount() {
		return custRepo.getDashboardCount(3, "", "", "", 0, "", "", "", Utility.getUserCountry());
	}

	@Override
	public int getPendingAssignRmCount() {
		return custRepo.getDashboardCount(4, "", "", "", 0, "", "", "", Utility.getUserCountry());
	}

	@Override
	public int getUserCount() {
		return custRepo.getDashboardCount(5, "", "", "", 0, "", "", "", Utility.getUserCountry());
	}

	@Override
	public int getKycApprovalCount() {
		return custRepo.getDashboardCount(6, "", "", "", 0, "", "", "", Utility.getUserCountry());
	}

	@Override
	public int getGrantKycCount() {
		return custRepo.getDashboardCount(7, "", "", "", 0, "", "", "", Utility.getUserCountry());
	}

	@Override
	public int getPendingKycCount(SearchRequest request) {
		if (request.getSubscriberType().equalsIgnoreCase("all")) {
			return custRepo.getDashboardCount(8, "", "", "", 0, "", "", "", Utility.getUserCountry());
		} else if (!request.getSubscriberType().equalsIgnoreCase("all") && request.getBankType() == null) {
			return custRepo.getDashboardCount(8, request.getSubscriberType(), "", "", 1, "", "", "A",
					Utility.getUserCountry());// used 1 to loop
			// inside
		} else {
			return custRepo.getDashboardCount(8, request.getSubscriberType(), request.getBankType(), "", 0, "", "", "A",
					Utility.getUserCountry());
		}

	}

	// for bankk & cust kyc pending
	public int getBankAndCustPendingKycCount(SearchRequest request) {
		if (request.getBankType() == null) {
			return custRepo.getDashboardCount(25, request.getSubscriberType(), "", "", 0, "", "", "A",
					Utility.getUserId());
		} else {
			return custRepo.getDashboardCount(25, request.getSubscriberType(), request.getBankType(), "", 0, "", "",
					"A", Utility.getUserId());
		}
	}

	@Override
	public int getSubsExpCount(Optional<SearchRequest> requests) {
		SearchRequest request = requests.get();
		System.out.println(request.getSubscriberType());
		if (request.getSubscriberType().equalsIgnoreCase("all")) {
			return custRepo.getDashboardCount(9, "", "", "", 0, "", "", "", Utility.getUserCountry());
		} else if (!request.getSubscriberType().equalsIgnoreCase("all") && request.getBankType() == null) {
			return custRepo.getDashboardCount(9, request.getSubscriberType(), "", "", 0, "", "", "A",
					Utility.getUserCountry());
		} else {
			return custRepo.getDashboardCount(10, request.getSubscriberType(), request.getBankType(), "", 0, "", "", "",
					Utility.getUserCountry());
		}
	}

	@Override
	public int getPayPendingCount(SearchRequest request) {
		System.out.println(Utility.getUserCountry());
		if (request.getSubscriberType().equalsIgnoreCase("all")) {
			return custRepo.getDashboardCount(11, "", "", "", 0, "", "", "", Utility.getUserCountry());
		} else if (!request.getSubscriberType().equalsIgnoreCase("All") && request.getBankType() == null) {
			return custRepo.getDashboardCount(11, request.getSubscriberType(), "", "", 0, "", "", "A",
					Utility.getUserCountry());
		} else {
			return custRepo.getDashboardCount(12, request.getSubscriberType(), request.getBankType(), "", 0, "", "", "",
					Utility.getUserCountry());
		}
	}

	// new for customer pay pending
	public int getCustBankPayPendingCount(SearchRequest request) {
		return custRepo.getDashboardCount(24, request.getSubscriberType(), "", "", 0, "", "", "", Utility.getUserId());

	}

	@Override
	public int getSubsGrantCount() {
		return custRepo.getDashboardCount(13, "", "", "", 0, "", "", "", Utility.getUserCountry());
	}

	@Override
	public int getVasGrantCount() {
		return custRepo.getDashboardCount(14, "", "", "", 0, "", "", "", Utility.getUserCountry());
	}

	@Override
	public int getDiscountCouponCount() {
		return custRepo.getDashboardCount(15, "", "", "", 0, "", "", "", Utility.getUserCountry());
	}

	@Override
	public Map<String, Object> getCustomerRevenue(SearchRequest request) {
		if (request.getDateFrom() == null && request.getDateTo() == null) {
			request.setDateFrom(LocalDate.now().minusDays(30).toString());
			request.setDateTo(LocalDate.now().toString());
			logger.info("Fromdate minus 30 days in getCustomerRevenue " + LocalDate.now().minusDays(30).toString());
			logger.info("Todate getCustomerRevenue" + LocalDate.now().toString());
		}
		logger.info("Fromdate minus in getCustomerRevenue from request " + request.getDateFrom());
		logger.info("Todate getCustomerRevenue from request" + request.getDateTo());
		logger.info(
				"User countries in getCustomerRevenue" + Utility.getUserCountry() + "_userId:_" + Utility.getUserId());
		return subsRepo.getRevenues(1, java.sql.Date.valueOf(request.getDateFrom()),
				java.sql.Date.valueOf(request.getDateTo()), Utility.getUserCountry());
	}

	@Override
	public Object totalQuoteReceived(SearchRequest request) {
		// TODO Auto-generated method stub
		Double amount = (double) 0;
		if (request.getDateFrom() == null && request.getDateTo() == null) {
			request.setDateFrom(LocalDate.now().minusDays(30).toString());
			request.setDateTo(LocalDate.now().toString());
			logger.info("Fromdate minus 30 days in getCustomerRevenue " + LocalDate.now().minusDays(30).toString());
			logger.info("Todate getCustomerRevenue" + LocalDate.now().toString());
		}
		logger.info("Fromdate minus in getCustomerRevenue from request " + request.getDateFrom());
		logger.info("Todate getCustomerRevenue from request" + request.getDateTo());
		logger.info(
				"User countries in getCustomerRevenue" + Utility.getUserCountry() + "_userId:_" + Utility.getUserId());
		if (Utility.getUserCountry().equalsIgnoreCase("ALL")) {
			amount = subsRepo.gettotalQuoteReceived(java.sql.Date.valueOf(request.getDateFrom()),
					java.sql.Date.valueOf(request.getDateTo()));
		} else {
			amount = subsRepo.gettotalQuoteReceivedCountryNames(java.sql.Date.valueOf(request.getDateFrom()),
					java.sql.Date.valueOf(request.getDateTo()), Utility.getUserCountry());
		}

		if (amount == null) {
			amount = 0.0;
		}

		Double amountInUsd = Double.parseDouble(new DecimalFormat("##.##").format(amount));
		return amountInUsd;
	}

	@Override
	public Map<String, Object> getBankCustomerRevenue(SearchRequest request) {
		if (request.getDateFrom() == null && request.getDateTo() == null) {
			request.setDateFrom(LocalDate.now().minusDays(30).toString());
			request.setDateTo(LocalDate.now().toString());
			logger.info("Fromdate minus 30 days in getBankCustomerRevenue " + LocalDate.now().minusDays(30).toString());
			logger.info("Todate getBankCustomerRevenue" + LocalDate.now().toString());
		}
		logger.info("Fromdate minus in getBankCustomerRevenue from request " + request.getDateFrom());
		logger.info("Todate getBankCustomerRevenue from request" + request.getDateTo());
		logger.info("User countries in getBankCustomerRevenue" + Utility.getUserCountry() + "_userId:_"
				+ Utility.getUserId());

		return subsRepo.getRevenues(2, java.sql.Date.valueOf(request.getDateFrom()),
				java.sql.Date.valueOf(request.getDateTo()), Utility.getUserCountry());
	}

	@Override
	public Map<String, Object> getBankUwRevenue(SearchRequest request) {
		if (request.getDateFrom() == null && request.getDateTo() == null) {
			request.setDateFrom(LocalDate.now().minusDays(30).toString());
			request.setDateTo(LocalDate.now().toString());
			logger.info("Fromdate minus 30 days in getBankUwRevenue " + LocalDate.now().minusDays(30).toString());
			logger.info("Todate getBankUwRevenue" + LocalDate.now().toString());
		}
		logger.info("Fromdate minus in getBankUwRevenue from request " + request.getDateFrom());
		logger.info("Todate getBankUwRevenue from request" + request.getDateTo());
		logger.info(
				"User countries in getBankUwRevenue" + Utility.getUserCountry() + "_userId:_" + Utility.getUserId());

		return subsRepo.getRevenues(3, java.sql.Date.valueOf(request.getDateFrom()),
				java.sql.Date.valueOf(request.getDateTo()), Utility.getUserCountry());
	}

	// ------------->>>>>>>>>>>-------------------------
	@Override
	public List<DashCountryAnalysisResponse> getCountryAnalysis() {
		List<Tuple> responses;
		if (Utility.getUserCountry().equalsIgnoreCase("all")) {
			responses = custRepo.getCountryDetailsAnalysis();
		} else {
			final List<String> value = Stream.of(Utility.getUserCountry().split(",", -1)).collect(Collectors.toList());
			responses = custRepo.getCountryFilteredDetails(value);

		}
		List<DashCountryAnalysisResponse> resp = responses.stream().map(res -> {
			DashCountryAnalysisResponse analysis = new DashCountryAnalysisResponse();
			analysis.setCountryName((String) res.get("country_name") != null ? (String) res.get("country_name") : "");
			analysis.setTotalCustomers((Integer) ((BigInteger) res.get("total_customers")).intValue() != null
					? (Integer) ((BigInteger) res.get("total_customers")).intValue()
					: 0);
			analysis.setTotalUnderwriters((Integer) ((BigInteger) res.get("total_underwriter")).intValue() != null
					? (Integer) ((BigInteger) res.get("total_underwriter")).intValue()
					: 0);
			analysis.setTotalTrxn((Integer) ((BigInteger) res.get("total_trxn")).intValue() != null
					? (Integer) ((BigInteger) res.get("total_trxn")).intValue()
					: 0);
			analysis.setCumulativeLcValue(
					(Double) res.get("cumulative_lc_amount") != null ? (Double) res.get("cumulative_lc_amount") : 0);

			return analysis;
		}).collect(Collectors.toList());

		return resp;
	}

	@Override
	public List<DashNewUserStat> getNewUserStat(SearchRequest request) {
		List<Tuple> userStat;
		if (request.getBankType() == null) {
			if (Utility.getUserCountry().equalsIgnoreCase("all")) {
				userStat = custRepo.getDashboardUserStat(request.getDateFrom());
			} else {
				userStat = custRepo.getDashboardCountryrUserStat(request.getDateFrom(), Utility.getUserCountry());
			}
		} else {
			if (Utility.getUserCountry().equalsIgnoreCase("all")) {
				userStat = custRepo.getDashboardBankStat(request.getDateFrom(), request.getBankType());
			} else {
				userStat = custRepo.getDashboardBankCountryStat(request.getDateFrom(), request.getBankType(),
						Utility.getUserCountry());
			}
		}
		List<DashNewUserStat> resp = userStat.stream().map(res -> {
			DashNewUserStat response = new DashNewUserStat();
			response.setMonth((String) res.get("month"));
			response.setCustomers((Integer) ((BigInteger) res.get("customers")).intValue());
			response.setSubs_rate((Double) ((BigDecimal) res.get("subscription_rate")).doubleValue());
			return response;
		}).collect(Collectors.toList());

		return resp;
	}

	// here in date From we will have to pass year as String parameter
	@Override
	public List<DashNewUserStat> getActiveUserStat(SearchRequest request) {

		List<Tuple> activeUserStat;
		if (request.getBankType() == null) {
			if (Utility.getUserCountry().equalsIgnoreCase("All")) {
				activeUserStat = subsRepo.getDashboardActiveCustStat(request.getDateFrom());
			} else {
				activeUserStat = subsRepo.getDashboardActiveCustCountryStat(request.getDateFrom(),
						Utility.getUserCountry());
			}
		} else {
			if (Utility.getUserCountry().equalsIgnoreCase("all")) {
				activeUserStat = subsRepo.getDashboardActiveBankStat(request.getBankType(), request.getDateFrom());
			} else {
				activeUserStat = subsRepo.getDashboardActiveBankCountryStat(request.getBankType(),
						request.getDateFrom(), Utility.getUserCountry());
			}
		}
		List<DashNewUserStat> resp = activeUserStat.stream().map(res -> {
			DashNewUserStat response = new DashNewUserStat();
			response.setMonth((String) res.get("month"));
			response.setCustomers((Integer) ((BigInteger) res.get("customers")).intValue());
			return response;
		}).collect(Collectors.toList());

		return resp;
	}

	// ---------------->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	// this is all transaction method

	@Override
	public List<DashTransStat> getTransactionStat(SearchRequest request) {
		if (request.getDateFrom() == null && request.getDateTo() == null) {
			request.setDateFrom(LocalDate.now().minusDays(30).toString());
			request.setDateTo(LocalDate.now().toString());
		}
		if (request.getBankType() == null) {
			request.setBankType("");
		}
		List<Tuple> graph;
		if (Utility.getUserCountry().equalsIgnoreCase("all")) {
			graph = transaRepo.getGenCumulativeTrxn(Date.valueOf(LocalDate.parse(request.getDateFrom())),
					Date.valueOf(LocalDate.parse(request.getDateTo()).plusDays(1)));
		} else {
			graph = transaRepo.getGenCumulativeCountryTrxn(Date.valueOf(LocalDate.parse(request.getDateFrom())),
					Date.valueOf(LocalDate.parse(request.getDateTo()).plusDays(1)), Utility.getUserCountry());
		}
		List<DashTransStat> responses = graph.stream().map(res -> {
			DashTransStat response = new DashTransStat();
			response.setMonth((String) res.get("month"));
			response.setDay((Integer) res.get("weekDay"));
			response.setTrxn_count((Integer) ((BigInteger) res.get("trxn_count")).intValue());
			response.setCumulative_amount((long) ((Double) res.get("cumulative_amount")).longValue());
			return response;
		}).collect(Collectors.toList());
		return responses;
	}

	@Override
	public List<Map<String, Object>> getQuotesStat(SearchRequest request) {
		if (request.getDateFrom() == null && request.getDateTo() == null) {
			request.setDateFrom(LocalDate.now().minusDays(30).toString());
			request.setDateTo(LocalDate.now().toString());
		}
		List<Tuple> quotesList;
		if (Utility.getUserCountry().equalsIgnoreCase("all")) {
			quotesList = transaRepo.getAvgQuote(java.sql.Date.valueOf(request.getDateFrom()),
					java.sql.Date.valueOf((LocalDate.parse(request.getDateTo()).plusDays(1))));
		} else {
			quotesList = transaRepo.getAvgCountryQuote(java.sql.Date.valueOf(request.getDateFrom()),
					java.sql.Date.valueOf((LocalDate.parse(request.getDateTo()).plusDays(1))),
					Utility.getUserCountry());
		}

		List<Map<String, Object>> qList = quotesList.stream().map(res -> {
			Map<String, Object> response = new HashMap<String, Object>();
			response.put("month", (String) res.get("month") != null ? (String) res.get("month") : "");
			response.put("transaction_count", (BigInteger) res.get("transaction_count"));
			response.put("day", (Integer) res.get("day"));
			response.put("total_quotes", (BigInteger) res.get("total_quotes"));
			return response;
		}).collect(Collectors.toList());
		return qList;
	}

	@Override
	public int getOverallCustomers(SearchRequest request) {
		if (request.getSubscriberType().equalsIgnoreCase("Referrer")) {
			return custRepo.getDashboardCount(27, request.getSubscriberType(), "", "", 0, "", "", "",
					Utility.getUserCountry());
		} else {

			return custRepo.getDashboardCount(16, request.getSubscriberType(), "", "", 0, "", "", "",
					Utility.getUserCountry());

		}
	}

	public int getOverallCustomersDate(SearchRequest request) {
		if (request.getDateFrom() == null && request.getDateTo() == null) {
			request.setDateFrom(LocalDate.now().minusDays(30).toString());
			request.setDateTo(LocalDate.now().toString());
		}

		return custRepo.getDashboardCount(16, request.getSubscriberType(), "", "", 0, request.getDateFrom(),
				request.getDateTo(), "A", Utility.getUserId());

	}

	@Override
	public int getOverallBankUw(SearchRequest request) {
		return custRepo.getDashboardCount(17, "Bank", request.getBankType(), "", 0, "", "", "",
				Utility.getUserCountry());
	}

	@Override
	public int getCustomerStatusTransc(SearchRequest request) {

		logger.info("Coutry names" + Utility.getUserCountry());
		if (Utility.getUserCountry().equalsIgnoreCase("All")) {
			logger.info("Country all country" + Utility.getUserCountry());
			return custRepo.getDashboardCount(18, "", "", request.getStatus(), 7, "", "", "", Utility.getUserCountry());
		} else {
			final List<String> value = Stream.of(Utility.getUserCountry().split(",", -1)).collect(Collectors.toList());
			if (request.getStatus().equalsIgnoreCase("Expired")) {
				logger.info("Country Expired condition country" + Utility.getUserCountry());
				return custRepo.getDashboardCountByCountryWise(value);
			} else {
				logger.info("Country Rejected condition country" + Utility.getUserCountry());
				return custRepo.getDashboardRejectedCountByCountryWise(value);
			}

		}

	}

	// -------->>>>>>>>>> Dashboard Customer>>>>>>>>>----
	// need to add date from and to
	@Override
	public int getCustTran(SearchRequest request) {
		if (request.getDateFrom() == null && request.getDateTo() == null) {
			request.setDateFrom(LocalDate.now().minusDays(30).toString());
			request.setDateTo(LocalDate.now().toString());
		}
		if (request.getBankType() == null) {
			return custRepo.getDashboardCount(22, request.getSubscriberType(), "", "", 0, request.getDateFrom(),
					request.getDateTo(), "A", Utility.getUserId());
		} else {
			return custRepo.getDashboardCount(22, request.getSubscriberType(), request.getBankType(), "", 0,
					request.getDateFrom(), request.getDateTo(), "B", Utility.getUserId());// B added just to loop
		}
	}

	@Override
	public int getCustTrxn(SearchRequest request) {
		if (request.getDateFrom() == null && request.getDateTo() == null) {
			request.setDateFrom(LocalDate.now().minusDays(30).toString());
			request.setDateTo(LocalDate.now().toString());
		}
		return custRepo.getDashboardCount(18, "", "", request.getStatus(), 0, request.getDateFrom(),
				request.getDateTo(), "", Utility.getUserId());
	}

	@Override
	public int getCustActiveTrxnCount() {
		return custRepo.getDashboardCount(19, "Customer", "", "", 0, "", "", "", Utility.getUserId());
	}

	@Override
	public int getCustTrxnEXp(SearchRequest request) {
		if (request.getBankType() != null) {
			return custRepo.getDashboardCount(20, request.getSubscriberType(), "Customer", "", 3, "", "", "A",
					Utility.getUserId());
		} else {
			return custRepo.getDashboardCount(20, request.getSubscriberType(), "", "", 3, "", "", "",
					Utility.getUserId());// 3
		} // is
			// expiry
			// in
			// 3
			// days
	}

	@Override
	public int getSubsExpBankAndCustCount(SearchRequest request) {
		if (request.getBankType() == null) {
			return custRepo.getDashboardCount(26, request.getSubscriberType(), "", "", 0, "", "", "A",
					Utility.getUserId());
		} else {
			return custRepo.getDashboardCount(26, request.getSubscriberType(), request.getBankType(), "", 0, "", "", "",
					Utility.getUserId());
		}
	}

	// >>>>>>>>>>>>>>>>>>>>>>>>>>--Bank
	@Override
	public int getCustTrxnBank(Optional<SearchRequest> request) {
		if (request.get().getDateFrom() == null && request.get().getDateTo() == null) {
			request.get().setDateFrom(LocalDate.now().minusDays(30).toString());
			request.get().setDateTo(LocalDate.now().toString());
		}
		return custRepo.getDashboardCount(18, request.get().getSubscriberType(), request.get().getBankType(),
				request.get().getStatus(), 0, request.get().getDateFrom(), request.get().getDateTo(), "A",
				Utility.getUserId());// A is for
		// looping
		// inside if
		// else procedure
	}

	@Override
	public int getTotalCustomers(Optional<SearchRequest> request) {
		if (request.get().getDateFrom() == null && request.get().getDateTo() == null) {
			request.get().setDateFrom(LocalDate.now().minusDays(30).toString());
			request.get().setDateTo(LocalDate.now().toString());
		}
		return custRepo.getDashboardCount(17, "Bank", request.get().getBankType(), "", 0, request.get().getDateFrom(),
				request.get().getDateTo(), "A", Utility.getUserId());
	}

//without dates
	@Override
	public int getBankAs(SearchRequest request) {

		return custRepo.getDashboardCount(17, "Bank", request.getBankType(), "", 1, "", "", "A", Utility.getUserId());
	}

	@Override
	public int getBankQuotesCount(Optional<SearchRequest> request) {

		if (request.get().getDateFrom() == null && request.get().getDateTo() == null) {
			request.get().setDateFrom(LocalDate.now().minusDays(30).toString());
			request.get().setDateTo(LocalDate.now().toString());
		}
		if (request.get().getStatus() != null & request.get().getSubscriberType().equalsIgnoreCase("Customer")) {
			return custRepo.getDashboardCount(21, request.get().getSubscriberType(), "", request.get().getStatus(), 0,
					request.get().getDateFrom(), request.get().getDateTo(), "A", Utility.getUserId());
		} else if (request.get().getStatus() != null & request.get().getSubscriberType().equalsIgnoreCase("Bank")) {
			return custRepo.getDashboardCount(21, request.get().getSubscriberType(), "", request.get().getStatus(), 0,
					request.get().getDateFrom(), request.get().getDateTo(), "B", Utility.getUserId());
		} else {
			return custRepo.getDashboardCount(21, request.get().getSubscriberType(), "", "", 0,
					request.get().getDateFrom(), request.get().getDateTo(), "", Utility.getUserId());
		}
	}

	// pass day in dateFrom parameter
	@Override
	public PagedResponse<?> getCustDiscardO(Optional<SearchRequest> request) {
		if (request.get().getDateFrom() == null) {
			request.get().setDateFrom("7");
		}
		Integer duration = (Integer.parseInt(request.get().getDateFrom())) + 1;
		SearchRequest requests = request.get();
		requests.setDateTo(LocalDate.parse(LocalDate.now().toString()).plusDays(duration).toString());
		requests.setDateFrom(LocalDate.now().toString());
		Pageable pageable = PageRequest.of(requests.getPage(), requests.getSize(),
				requests.getDirection().equalsIgnoreCase("desc") ? Sort.by(requests.getSortBy()).descending()
						: Sort.by(requests.getSortBy()).ascending());

		Page<NimaiMmTransaction> transList = transaRepo.findAll(transactionsSpecification.getFilter(requests),
				pageable);

		List<DiscardResponse> resp = transList.stream().map(res -> {
			Period period = Period.between(LocalDate.parse(LocalDate.now().toString()),
					LocalDate.parse(res.getValidity().substring(0, 10)));
			DiscardResponse response = new DiscardResponse();
			response.setTrxnId(res.getTransactionId());
			response.setCustomer(res.getApplicantName());
			response.setExpiresIn(String.valueOf(period.getDays()) + " days");
			return response;
		}).collect(Collectors.toList());

		return new PagedResponse<>(resp, transList.getNumber(), transList.getSize(), transList.getTotalElements(),
				transList.getTotalPages(), transList.isLast());

	}

	@Override
	public PagedResponse<?> getCustStat(SearchRequest requests) {
		Pageable pageable = PageRequest.of(requests.getPage(), requests.getSize(),
				requests.getDirection().equalsIgnoreCase("desc") ? Sort.by(requests.getSortBy()).descending()
						: Sort.by(requests.getSortBy()).ascending());

		Page<NimaiMCustomer> custStatList = custRepo.findAll(custSpecs.getFilter(requests), pageable);
		List<CustomerStatResponse> responses = custStatList.stream().map(res -> {
			CustomerStatResponse response = new CustomerStatResponse();
			response.setUserid(res.getUserid());
			response.setCustomer(res.getCompanyName());
			response.setPayment(res.getModeOfPayment());
			response.setKyc(res.getKycStatus());
			return response;
		}).collect(Collectors.toList());
		return new PagedResponse<>(responses, custStatList.getNumber(), custStatList.getSize(),
				custStatList.getTotalElements(), custStatList.getTotalPages(), custStatList.isLast());

	}

	@Override
	public PagedResponse<?> getSubStat(SearchRequest request) {
		if (request.getDateFrom() == null) {
			request.setDateFrom("7");
		}
		Integer duration = (Integer.parseInt(request.getDateFrom())) + 1;
		request.setDateTo(LocalDate.parse(LocalDate.now().toString()).plusDays(duration).toString());
		request.setDateFrom(LocalDate.now().toString());
		Pageable pageable = PageRequest.of(request.getPage(), request.getSize(),
				request.getDirection().equalsIgnoreCase("desc") ? Sort.by(request.getSortBy()).descending()
						: Sort.by(request.getSortBy()).ascending());

		Page<NimaiSubscriptionDetails> subsDetails = subsRepo.findAll(subSpecification.getFilter(request), pageable);
		List<SubscriptionRenewalResponse> response = subsDetails.stream().map(res -> {
			long exp = ChronoUnit.DAYS.between(LocalDate.now(), LocalDate.parse(res.getSplanEndDate().toString()));
			SubscriptionRenewalResponse resp = new SubscriptionRenewalResponse();
			resp.setUserid(res.getUserid().getUserid());
			resp.setCustomer(res.getUserid().getCompanyName());
			resp.setExpiryDate(res.getSplanEndDate());
			if (exp <= 0) {
				resp.setExpiresIn("Expired");
			} else {
				resp.setExpiresIn(exp + " days");
			}
			return resp;

		}).collect(Collectors.toList());
		return new PagedResponse<>(response, subsDetails.getNumber(), subsDetails.getSize(),
				subsDetails.getTotalElements(), subsDetails.getTotalPages(), subsDetails.isLast());
	}

	@Override
	public List<DashTransStat> getCustTransactionStat(SearchRequest request) {
		if (request.getDateFrom() == null && request.getDateTo() == null) {
			request.setDateFrom(LocalDate.now().minusDays(30).toString());
			request.setDateTo(LocalDate.now().toString());
		}
		List<Tuple> graph;
		if (request.getSubscriberType().equalsIgnoreCase("Bank")) {
			graph = transaRepo.getCumulativeBankTrxn(request.getSubscriberType(),
					Date.valueOf(LocalDate.parse(request.getDateFrom())),
					Date.valueOf(LocalDate.parse(request.getDateTo()).plusDays(1)), Utility.getUserId());
		}
		graph = transaRepo.getCumulativeCustTrxn(request.getSubscriberType(),
				Date.valueOf(LocalDate.parse(request.getDateFrom())),
				Date.valueOf(LocalDate.parse(request.getDateTo()).plusDays(1)), Utility.getUserId());
		System.out.println(graph.isEmpty());
		List<DashTransStat> responses = graph.stream().map(res -> {
			DashTransStat response = new DashTransStat();
			response.setMonth((String) res.get("month"));
			response.setDay((Integer) res.get("weekDay"));
			response.setTrxn_count((Integer) ((BigInteger) res.get("trxn_count")).intValue());
			response.setCumulative_amount((long) ((Double) res.get("cumulative_amount")).longValue());
			return response;
		}).collect(Collectors.toList());
		return responses;
	}

	@Override
	public int getBankAwaitedQuotes() {
		return custRepo.getDashboardCount(23, "", "", "", 0, "", "", "", Utility.getUserId());
	}

	@Override
	public List<Map<String, Object>> getBankAvgStat(SearchRequest request) {
		if (request.getDateFrom() == null && request.getDateTo() == null) {
			request.setDateFrom(LocalDate.now().minusDays(30).toString());
			request.setDateTo(LocalDate.now().toString());
		}
		List<Tuple> quotesList = transaRepo.getAvgBankQuote(java.sql.Date.valueOf(request.getDateFrom()),
				java.sql.Date.valueOf((LocalDate.parse(request.getDateTo()).plusDays(1))), Utility.getUserId());

		List<Map<String, Object>> qList = quotesList.stream().map(res -> {
			Map<String, Object> response = new HashMap<String, Object>();
			response.put("month", (String) res.get("month") != null ? (String) res.get("month") : "");
			response.put("transaction_count", (BigInteger) res.get("transaction_count"));
			response.put("day", (Integer) res.get("day"));
			response.put("total_quotes", (BigInteger) res.get("total_quotes"));
			return response;
		}).collect(Collectors.toList());
		return qList;
	}

	@Override
	public List<Map<String, Object>> getTransactionComp(SearchRequest request) {
		if (request.getDateFrom() == null && request.getDateTo() == null) {
			request.setDateFrom(LocalDate.now().minusDays(30).toString());
			request.setDateTo(LocalDate.now().toString());
		}
		List<Tuple> compList = transaRepo.getCustTransComparision(java.sql.Date.valueOf(request.getDateFrom()),
				java.sql.Date.valueOf((LocalDate.parse(request.getDateTo()).plusDays(1))), Utility.getUserId());

		List<Map<String, Object>> qList = compList.stream().map(res -> {
			Map<String, Object> response = new WeakHashMap<String, Object>();
			response.put("month", (String) res.get("Month") != null ? (String) res.get("Month") : 0);
			response.put("day", (Integer) res.get("day") != null ? (Integer) res.get("day") : 0);
			response.put("Confirmation",
					(BigInteger) res.get("Confirmation") != null ? (BigInteger) res.get("Confirmation") : 0);
			response.put("Discounting",
					(BigInteger) res.get("Discounting") != null ? (BigInteger) res.get("Discounting") : 0);
			response.put("Refinance",
					(BigInteger) res.get("Refinance") != null ? (BigInteger) res.get("Refinance") : 0);
			response.put("ConfirmAndDiscount",
					(BigInteger) res.get("Confirm And Discount") != null ? (BigInteger) res.get("Confirm And Discount")
							: 0);
			response.put("Banker", (BigInteger) res.get("Banker") != null ? (BigInteger) res.get("Banker") : 0);
//			response.put("day", (Integer) res.get("day"));
//			response.put("transactions", (BigInteger) res.get("transactions"));
			return response;
		}).collect(Collectors.toList());
		return qList;
	}

	@Override
	public int getReferrers(SearchRequest request) {

		return custRepo.getDashboardCount(27, request.getSubscriberType(), "", "", 0, "", "", "",
				Utility.getUserCountry());
	}

	@Override
	public List<DashCountryAnalysisResponse> getLatestCountryAnalysis() {
		// TODO Auto-generated method stub
		// Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
		// int limit=request.getSize();
		// int page=request.getPage();
		// int offsetData=limit*page;
		List<Tuple> responses;
		if (Utility.getUserCountry().equalsIgnoreCase("all")) {
			responses = custRepo.getCountryDetailsAnalysis();
		} else {
			final List<String> value = Stream.of(Utility.getUserCountry().split(",", -1)).collect(Collectors.toList());
			responses = custRepo.getCountryFilteredDetails(value);

		}
		List<DashCountryAnalysisResponse> resp = responses.stream().map(res -> {
			DashCountryAnalysisResponse analysis = new DashCountryAnalysisResponse();
			analysis.setCountryName(
					(String) res.get("REGISTERED_COUNTRY") != null ? (String) res.get("REGISTERED_COUNTRY") : "");
			analysis.setTotalCustomers((Integer) ((BigInteger) res.get("total_customers")).intValue() != null
					? (Integer) ((BigInteger) res.get("total_customers")).intValue()
					: 0);
			analysis.setTotalUnderwriters((Integer) ((BigInteger) res.get("total_underwriter")).intValue() != null
					? (Integer) ((BigInteger) res.get("total_underwriter")).intValue()
					: 0);
			analysis.setTotalTrxn((Integer) ((BigInteger) res.get("total_trxn")).intValue() != null
					? (Integer) ((BigInteger) res.get("total_trxn")).intValue()
					: 0);
			analysis.setCumulativeLcValue(
					(Double) res.get("cumulative_lc_amount") != null ? (Double) res.get("cumulative_lc_amount") : 0);

			return analysis;
		}).collect(Collectors.toList());

		return resp;
	}

	@Override
	public Object totalQuoteAccepted(SearchRequest request) {
		Double amount = 0.0;
		if (request.getDateFrom() == null && request.getDateTo() == null) {
			request.setDateFrom(LocalDate.now().minusDays(30).toString());
			request.setDateTo(LocalDate.now().toString());
			logger.info("Fromdate minus 30 days in getCustomerRevenue " + LocalDate.now().minusDays(30).toString());
			logger.info("Todate getCustomerRevenue" + LocalDate.now().toString());
		}
		logger.info("Fromdate minus in getCustomerRevenue from request " + request.getDateFrom());
		logger.info("Todate getCustomerRevenue from request" + request.getDateTo());
		logger.info(
				"User countries in getCustomerRevenue" + Utility.getUserCountry() + "_userId:_" + Utility.getUserId());

		if (Utility.getUserCountry().equalsIgnoreCase("ALL")) {
			amount = subsRepo.gettotalQuoteAccepted(java.sql.Date.valueOf(request.getDateFrom()),
					java.sql.Date.valueOf(request.getDateTo()));
		} else {
			amount = subsRepo.gettotalQuoteAcceptedCountryNames(java.sql.Date.valueOf(request.getDateFrom()),
					java.sql.Date.valueOf(request.getDateTo()), Utility.getUserCountry());
		}
		if (amount == null) {
			amount = 0.0;
		}
		Double amountInUsd = Double.parseDouble(new DecimalFormat("##.##").format(amount));
		return amountInUsd;

	}

	@Override
	public Object totalQuoteClosed(SearchRequest request) {
		Double amount = 0.0;
		if (request.getDateFrom() == null && request.getDateTo() == null) {
			request.setDateFrom(LocalDate.now().minusDays(30).toString());
			request.setDateTo(LocalDate.now().toString());
			logger.info("Fromdate minus 30 days in getCustomerRevenue " + LocalDate.now().minusDays(30).toString());
			logger.info("Todate getCustomerRevenue" + LocalDate.now().toString());
		}
		logger.info("Fromdate minus in getCustomerRevenue from request " + request.getDateFrom());
		logger.info("Todate getCustomerRevenue from request" + request.getDateTo());
		logger.info(
				"User countries in getCustomerRevenue" + Utility.getUserCountry() + "_userId:_" + Utility.getUserId());

		if (Utility.getUserCountry().equalsIgnoreCase("ALL")) {
			amount = subsRepo.gettotalQuoteClosed(java.sql.Date.valueOf(request.getDateFrom()),
					java.sql.Date.valueOf(request.getDateTo()));
		} else {
			amount = subsRepo.gettotalQuoteClosedCountryNames(java.sql.Date.valueOf(request.getDateFrom()),
					java.sql.Date.valueOf(request.getDateTo()), Utility.getUserCountry());
		}

		if (amount == null) {
			amount = 0.0;
		}

		Double amountInUsd = Double.parseDouble(new DecimalFormat("##.##").format(amount));
		return amountInUsd;

	}

	@Override
	public PagedResponse<?> getPendingWireTfList(SearchRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PendingRequestBean getPendingRequests(SearchRequest request) {
		// TODO Auto-generated method stub
		PendingRequestBean response = new PendingRequestBean();
		String countryNames = Utility.getUserCountry();
		String rmId = Utility.getUserId();
		System.out.println("countryNames: " + countryNames);
		if (countryNames != null && countryNames.equalsIgnoreCase("all") && request.getCountry() == null) {
			countryNames = "";
			final List<String> countryList = (List<String>) this.repo.getCountryList();
			for (final String country : countryList) {
				countryNames = countryNames + country + ",";
			}
			System.out.println("Country List: " + countryNames);
			request.setCountryNames(countryNames);
		} else if (countryNames != null && !countryNames.equalsIgnoreCase("all") && request.getCountry() == null) {
			request.setCountryNames(countryNames);
		} else if (countryNames != null && request.getCountry() != null) {
			request.setCountryNames(request.getCountry());
		} else if (countryNames == null && request.getCountry() == null) {
		}
		final List<String> value = Stream.of(request.getCountryNames().split(",", -1)).collect(Collectors.toList());
		System.out.println("Values BankService: " + value);

		
		if ((request.getBankType() == null || request.getBankType().isEmpty()||
				request.getRole().equalsIgnoreCase("Management")) && request.getSubscriberType().equalsIgnoreCase("All")) {
			response = getAllData(countryNames, request, response, value, rmId);
		} else if ((request.getBankType() == null || request.getBankType().isEmpty()) && request.getSubscriberType().equalsIgnoreCase("Customer")) {
			response = getCustomerData(countryNames, request, response, value, rmId);
		} else if ((request.getBankType() == null || request.getBankType().isEmpty()) && request.getSubscriberType().equalsIgnoreCase("Referrer")) {
			response = getReferrerdata(countryNames, request,value, response);
		} else if (request.getBankType().equalsIgnoreCase("Customer")
				&& request.getSubscriberType().equalsIgnoreCase("Bank")) {
			response = getBankAsCustomerdata(countryNames, request, response, value, rmId);
		} else if (request.getBankType().equalsIgnoreCase("Underwriter")
				&& request.getSubscriberType().equalsIgnoreCase("Bank")) {
			response = getBankAsUnderwriterdata(countryNames, request,value, response);
		} 

		return response;
	}

	private PendingRequestBean getReferrerdata(String countryNames, SearchRequest request,
			List<String> value, PendingRequestBean response) {
		long paymentApprovalList;
		long grantPayment;
		long grantRm;
		long kycApproval;
		long grntkycApproval;
		
			 kycApproval = custRepo.getRefPendingAllKYC();
			 grntkycApproval = custRepo.getRefCustGrantKYC();
			paymentApprovalList = 0;//custRepo.getDashboardCount(34, "", "", "", 0, "", "", "", Utility.getUserCountry());
			grantRm = custRepo.getDashboardCount(4, request.getSubscriberType(), request.getBankType(), "", 0, "", "", "", Utility.getUserCountry());
			grantPayment = 0;//custRepo.getDashboardCount(35, "", "", "", 0, "", "", "", Utility.getUserCountry());	
		long assignRm = custRepo.getDashboardCount(3, request.getSubscriberType(), request.getBankType(), "", 0, "", "", "", Utility.getUserCountry());
		long grantUser =custRepo.getDashboardCount(5, "", "", "", 0, "", "", "", Utility.getUserCountry());
		
		long subExp30Days = custRepo.getDashboardCount(9, "Referrer", "", "", 0, "", "", "A", Utility.getUserCountry());
		
		long pendingKyc =  custRepo.getRefpendingKycNullNew();
				
				//custRepo.getDashboardCount(8, "Referrer","", "", 1, "", "", "A",
				//Utility.getUserCountry());
		
		
		long paymentPending = 0;//custRepo.getDashboardCount(11, "Referrer", "", "", 0, "", "", "A",
				//Utility.getUserCountry());

		response.setPaymentApproval(paymentApprovalList);
		response.setGrantPayment(grantPayment);
		response.setAssignRm(assignRm);
		response.setGrantRM(grantRm);
		response.setGrantUser(grantUser);
		response.setKycApproval(kycApproval);
		response.setGrantKyc(grntkycApproval);
		response.setSubPlanExpiring30Days(subExp30Days);
		response.setKycPendingUser(pendingKyc);
		response.setPaymentPendingUser(paymentPending);
		return response;
	}

	private PendingRequestBean getBankAsUnderwriterdata(String countryNames, SearchRequest request,List<String> value,
			PendingRequestBean response) {
		// TODO Auto-generated method stub
		long paymentApprovalList;
		long grantPayment;
		long grantRm;
		long kycApproval;
		long grntkycApproval;
		long pendingKyc;
		
			 kycApproval = custRepo.getBankAsUnderPendingAllKYC();
			 grntkycApproval = custRepo.getBankAsUnderGrantKYC();
			paymentApprovalList = custRepo.getDashboardCount(30, "", "", "", 0, "", "", "", Utility.getUserCountry());
			grantRm = custRepo.getDashboardCount(4,request.getSubscriberType(), request.getBankType(), "", 0, "", "", "", Utility.getUserCountry());
			grantPayment = custRepo.getDashboardCount(33, "", "", "", 0, "", "", "", Utility.getUserCountry());	
		long assignRm = custRepo.getDashboardCount(3, request.getSubscriberType(), request.getBankType(), "", 0, "", "", "", Utility.getUserCountry());
		long grantUser =custRepo.getDashboardCount(5, "", "", "", 0, "", "", "", Utility.getUserCountry());
		
		custRepo.getDashboardCount(12, request.getSubscriberType(), request.getBankType(), "", 0, "", "", "",
				Utility.getUserCountry());
		
		
		long subExp30Days = custRepo.getDashboardCount(10, request.getSubscriberType(),request.getBankType(), "", 0, "", "", "", Utility.getUserCountry());
		
	
				
		if(Utility.getUserCountry().equalsIgnoreCase("All")) {
			pendingKyc = custRepo.getBankAsUndependingKycNullNew();
		}else {
			pendingKyc = custRepo.getCountryBankAsUndependingKycNullNew(value);
		}
		
				//custRepo.getDashboardCount(8, "Bank", "Underwriter", "", 1, "", "", "A",
				//Utility.getUserCountry());
		
		
		long paymentPending = custRepo.getDashboardCount(12,request.getSubscriberType(),request.getBankType(), "", 0, "", "", "",
				Utility.getUserCountry());

		response.setPaymentApproval(paymentApprovalList);
		response.setGrantPayment(grantPayment);
		response.setAssignRm(assignRm);
		response.setGrantRM(grantRm);
		response.setGrantUser(grantUser);
		response.setKycApproval(kycApproval);
		response.setGrantKyc(grntkycApproval);
		response.setSubPlanExpiring30Days(subExp30Days);
		response.setKycPendingUser(pendingKyc);
		response.setPaymentPendingUser(paymentPending);
		return response;
	}

	private PendingRequestBean getBankAsCustomerdata(String countryNames, SearchRequest request,
			PendingRequestBean response, List<String> value, String rmId) {
		// TODO Auto-generated method stub
		
		long paymentApprovalList;
		long grantPayment;
		long grantRm;
		long kycApproval;
		long grntkycApproval;
		long pendingKyc;
		
			 kycApproval = custRepo.getBankAsCustPendingAllKYC();
			 grntkycApproval = custRepo.getBankAsCustGrantKYC();
			paymentApprovalList = custRepo.getDashboardCount(29, "", "", "", 0, "", "", "", Utility.getUserCountry());
			grantRm = custRepo.getDashboardCount(4, request.getSubscriberType(), request.getBankType(), "", 0, "", "", "", Utility.getUserCountry());
			grantPayment = custRepo.getDashboardCount(32, "", "", "", 0, "", "", "", Utility.getUserCountry());	
		long assignRm = custRepo.getDashboardCount(3, request.getSubscriberType(), request.getBankType(), "", 0, "", "", "", Utility.getUserCountry());
		long grantUser =custRepo.getDashboardCount(5, "", "", "", 0, "", "", "", Utility.getUserCountry());
		
		long subExp30Days = custRepo.getDashboardCount(10, request.getSubscriberType(), request.getBankType(), "", 0, "", "", "", Utility.getUserCountry());
		
		
		if(Utility.getUserCountry().equalsIgnoreCase("All")) {
			pendingKyc =custRepo.getBankAsCustpendingKycNullNew(); 
		}else {
			pendingKyc =custRepo.getCountryBankAsCustpendingKycNullNew(value);
		}
				
				//custRepo.getDashboardCount(8, "Bank", "Customer", "", 1, "", "", "A",
				//Utility.getUserCountry());
		
		
		long paymentPending = custRepo.getDashboardCount(12, request.getSubscriberType(), request.getBankType(), "", 0, "", "", "",
				Utility.getUserCountry());

		response.setPaymentApproval(paymentApprovalList);
		response.setGrantPayment(grantPayment);
		response.setAssignRm(assignRm);
		response.setGrantRM(grantRm);
		response.setGrantUser(grantUser);
		response.setKycApproval(kycApproval);
		response.setGrantKyc(grntkycApproval);
		response.setSubPlanExpiring30Days(subExp30Days);
		response.setKycPendingUser(pendingKyc);
		response.setPaymentPendingUser(paymentPending);
		return response;
	}

	private PendingRequestBean getCustomerData(String countryNames, SearchRequest request,
			PendingRequestBean response, List<String> value, String rmId) {
		
		long paymentApprovalList;
		long grantPayment;
		long grantRm;
		long kycApproval;
		long grntkycApproval;
		long pendingKyc;
		
			 kycApproval = custRepo.getCustPendingAllKYC();
			 grntkycApproval = custRepo.getCustGrantKYC();
			paymentApprovalList = custRepo.getDashboardCount(28, "", "", "", 0, "", "", "", Utility.getUserCountry());
			grantRm = custRepo.getDashboardCount(4, request.getSubscriberType(), request.getBankType(), "", 0, "", "", "", Utility.getUserCountry());
			grantPayment = custRepo.getDashboardCount(31, "", "", "", 0, "", "", "", Utility.getUserCountry());	
		long assignRm = custRepo.getDashboardCount(3, request.getSubscriberType(), request.getBankType(), "", 0, "", "", "", Utility.getUserCountry());
		long grantUser =custRepo.getDashboardCount(5, "", "", "", 0, "", "", "", Utility.getUserCountry());
		
		long subExp30Days = custRepo.getDashboardCount(9, request.getSubscriberType(), "", "", 0, "", "", "A", Utility.getUserCountry());
		
		
		
		if(Utility.getUserCountry().equalsIgnoreCase("All")) {
			 pendingKyc = custRepo.getCustpendingKycNullNew();
		}else {
			System.out.println("Countrynames inside the conditions"+countryNames);
			pendingKyc = custRepo.getCountryCustpendingKycNullNew(value);
		}
				
				
				
				
				//custRepo.getDashboardCount(8, "Customer","", "", 1, "", "", "A",
			//	Utility.getUserCountry());
		
		
		long paymentPending = custRepo.getDashboardCount(11, "Customer", "", "", 0, "", "", "A",
				Utility.getUserCountry());

		response.setPaymentApproval(paymentApprovalList);
		response.setGrantPayment(grantPayment);
		response.setAssignRm(assignRm);
		response.setGrantRM(grantRm);
		response.setGrantUser(grantUser);
		response.setKycApproval(kycApproval);
		response.setGrantKyc(grntkycApproval);
		response.setSubPlanExpiring30Days(subExp30Days);
		response.setKycPendingUser(pendingKyc);
		response.setPaymentPendingUser(paymentPending);
		return response;
		// TODO Auto-generated method stub

	}

	private PendingRequestBean getAllData(String countryNames, SearchRequest request, PendingRequestBean response,
			List<String> value, String rmId) {
	System.out.println("===========Inside  all condition=========");
		long paymentApprovalList;
		long grantPayment;
		long grantRm;
		long kycApproval;
		long grntkycApproval;
		long pendingKyc;
		
			 kycApproval = custRepo.getPendingAllKYC();
			 grntkycApproval = custRepo.getGrantKYC();
			paymentApprovalList = custRepo.getDashboardCount(1, "", "", "", 0, "", "", "", Utility.getUserCountry());
			grantRm = custRepo.getDashboardCount(37, "", "", "", 0, "", "", "", Utility.getUserCountry());
			grantPayment = custRepo.getDashboardCount(2, "", "", "", 0, "", "", "", Utility.getUserCountry());	
			// kycApproval = custRepo.getPendingAllKYC();
			 grntkycApproval = custRepo.getGrantKYC();
		long assignRm = custRepo.getDashboardCount(36, "", "", "", 0, "", "", "", Utility.getUserCountry());
		long grantUser =custRepo.getDashboardCount(5, "", "", "", 0, "", "", "", Utility.getUserCountry());
		
		long subExp30Days = custRepo.getDashboardCount(9, "", "", "", 0, "", "", "", Utility.getUserCountry());
		if(Utility.getUserCountry().equalsIgnoreCase("All")) {
			 pendingKyc = custRepo.getpendingKycNullNew();
		}else {
			 pendingKyc = custRepo.getCountrypendingKycNullNew(value);
		}
		
				
				
				
				//custRepo.getDashboardCount(8, "", "", "", 0, "", "", "", Utility.getUserCountry());
		
		
		
		
		long paymentPending = custRepo.getDashboardCount(11, "", "", "", 0, "", "", "", Utility.getUserCountry());

		response.setPaymentApproval(paymentApprovalList);
		response.setGrantPayment(grantPayment);
		response.setAssignRm(assignRm);
		response.setGrantRM(grantRm);
		response.setGrantUser(grantUser);
		response.setKycApproval(kycApproval);
		response.setGrantKyc(grntkycApproval);
		response.setSubPlanExpiring30Days(subExp30Days);
		response.setKycPendingUser(pendingKyc);
		response.setPaymentPendingUser(paymentPending);

		return response;
		// TODO Auto-generated method stub

	}
}
