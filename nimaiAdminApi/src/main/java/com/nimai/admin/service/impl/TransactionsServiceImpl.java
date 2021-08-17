package com.nimai.admin.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nimai.admin.model.NimaiMCustomer;
import com.nimai.admin.model.NimaiMEmployee;
import com.nimai.admin.model.NimaiMQuotation;
import com.nimai.admin.model.NimaiMmTransaction;
import com.nimai.admin.payload.PagedResponse;
import com.nimai.admin.payload.QuotationDetailsResponse;
import com.nimai.admin.payload.QuotationListResponse;
import com.nimai.admin.payload.SearchRequest;
import com.nimai.admin.payload.TransactionDetails;
import com.nimai.admin.payload.TransactionSearchResponse;
import com.nimai.admin.repository.CurrencyRepository;
import com.nimai.admin.repository.CustomerRepository;
import com.nimai.admin.repository.EmployeeRepository;
import com.nimai.admin.repository.QuotationRepository;
import com.nimai.admin.repository.TransactionsRepository;
import com.nimai.admin.service.TransactionsService;
import com.nimai.admin.specification.QuotationSpecification;
import com.nimai.admin.specification.TransactionsSpecification;
import com.nimai.admin.util.ModelMapper;
import com.nimai.admin.util.Utility;

@Service
public class TransactionsServiceImpl implements TransactionsService {

	@Autowired
	TransactionsRepository transactionsRepository;

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	CurrencyRepository currencyRepository;

	@Autowired
	TransactionsSpecification transactionsSpecification;

	@Autowired
	QuotationRepository quotationRepository;

	@Autowired
	QuotationSpecification quotationSpecification;

	@Autowired
	EmployeeRepository employeeRepository;

	private static final Logger logger = LoggerFactory.getLogger(TransactionsServiceImpl.class);

	@Override
	public PagedResponse<?> getAllTransaction(SearchRequest request) {
//		List<String> userIdList = null;
//		if (request.getEmailId() != null || request.getMobileNo() != null || request.getCompanyName() != null) {
//
//			userIdList = customerRepository.getUserIdOnEmailMobileNumberCompanyName(request.getEmailId(),
//					request.getMobileNo(), request.getCompanyName());
//		}

		Pageable pageable = PageRequest.of(request.getPage(), request.getSize(),
				request.getDirection().equalsIgnoreCase("desc") ? Sort.by(request.getSortBy()).descending()
						: Sort.by(request.getSortBy()).ascending());
		Page<NimaiMmTransaction> transactionList;
		String countryNames = Utility.getUserCountry();
		if (request.getCountry() == null) {

			if (countryNames != null && countryNames.equalsIgnoreCase("all")) {

			} else if (countryNames != null && !countryNames.equalsIgnoreCase("all")) {
				request.setCountryNames(countryNames);
			}
		} else if (request.getCountry() == null && countryNames == null) {
			transactionList = null;
		}

		transactionList = transactionsRepository.findAll(transactionsSpecification.getFilter(request), pageable);

		List<TransactionSearchResponse> responses = transactionList.map(cust -> {
			return ModelMapper.mapTransactionToResponse(cust);
		}).getContent();

		return new PagedResponse<>(responses, transactionList.getNumber(), transactionList.getSize(),
				transactionList.getTotalElements(), transactionList.getTotalPages(), transactionList.isLast());

	}

	@Override
	public List<String> userIdSearch(String userId) {
		String val = Utility.getUserCountry();
		if (!val.equalsIgnoreCase("All")) {
			List<String> list = Stream.of(val.split(",")).collect(Collectors.toList());
			return customerRepository.userIdSearchByCountry(userId.toLowerCase(), list);
		} else {
			return customerRepository.userIdSearch(userId.toLowerCase());
		}
	}

	@Override
	public List<String> emailIdSearch(String emailId) {
		String val = Utility.getUserCountry();
		if (!val.equalsIgnoreCase("All")) {
			List<String> list = Stream.of(val.split(",")).collect(Collectors.toList());
			return customerRepository.emailIdSearchByCountry(emailId.toLowerCase(), list);
		} else {
			return customerRepository.emailIdSearch(emailId.toLowerCase());
		}
	}

	@Override
	public List<String> mobileNumberSearch(String mobileNo) {
		String val = Utility.getUserCountry();
		if (!val.equalsIgnoreCase("All")) {
			List<String> list = Stream.of(val.split(",")).collect(Collectors.toList());
			return customerRepository.mobileNumberSearchByCountry(mobileNo.toLowerCase(), list);
		} else {
			return customerRepository.mobileNumberSearch(mobileNo.toLowerCase());
		}
	}

	@Override
	public List<String> companyNameSearch(String companyName) {
		String val = Utility.getUserCountry();
		if (!val.equalsIgnoreCase("All")) {
			List<String> list = Stream.of(val.split(",")).collect(Collectors.toList());
			return customerRepository.companyNameSearchByCountry(companyName.toLowerCase(), list);
		} else {
			return customerRepository.companyNameSearch(companyName.toLowerCase());
		}
	}

	@Override
	public List<String> countrySearch(String country) {
		String val = Utility.getUserCountry();
		if (!val.equalsIgnoreCase("All")) {
			List<String> nameList = Stream.of(val.split(",")).collect(Collectors.toList());
			return nameList.stream().filter(x -> x.toLowerCase().contains(country.toLowerCase()))
					.collect(Collectors.toList());
		} else
			return currencyRepository.countrySearch(country);
	}

	@Override
	public ResponseEntity<TransactionDetails> getTransactionById(String transactionId) {
		try {
			NimaiMmTransaction trasaction = transactionsRepository.getOne(transactionId);
			NimaiMCustomer customer = customerRepository.getOne(trasaction.getUserId().getUserid());

			TransactionDetails employeeListResponse = ModelMapper.mapTransactionDetails(trasaction, customer);
			if (employeeListResponse.getRmFirstName() != null) {
				NimaiMEmployee emp = employeeRepository.findByEmpCode(employeeListResponse.getRmFirstName());
				employeeListResponse.setRmFirstName(emp.getEmpName());
				employeeListResponse.setRmLastName(emp.getEmpLastName());
				employeeListResponse.setRmDesignation(emp.getDesignation());
			}
			return new ResponseEntity<TransactionDetails>(employeeListResponse, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<TransactionDetails>(HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public PagedResponse<?> getQuotesDetails(SearchRequest request) {
		Pageable pageable = PageRequest.of(request.getPage(), request.getSize(),
				request.getDirection().equalsIgnoreCase("desc") ? Sort.by(request.getSortBy()).descending()
						: Sort.by(request.getSortBy()).ascending());
		Page<NimaiMQuotation> quotationList = quotationRepository.findAll(quotationSpecification.getFilter(request),
				pageable);

		List<QuotationListResponse> responses = quotationList.stream()
				.map(quote -> new QuotationListResponse(quote.getQuotationId(),
						quote.getTransactionId().getTransactionId(),
						customerRepository.findBankName(quote.getBankUserid()), quote.getTotalQuoteValue(),
						quote.getValidityDate(), quote.getQuotationStatus(), quote.getTransactionId().getLcCurrency()))
				.collect(Collectors.toList());

		return new PagedResponse<>(responses, quotationList.getNumber(), quotationList.getSize(),
				quotationList.getTotalElements(), quotationList.getTotalPages(), quotationList.isLast());

	}

	@Override
	public ResponseEntity<QuotationDetailsResponse> getQuotesDetailsById(Integer quotationId) {
		NimaiMQuotation quotation = quotationRepository.getOne(quotationId);
		if (quotation != null) {

			QuotationDetailsResponse response = new QuotationDetailsResponse();
			response.setUserid(quotation.getUserid().getUserid());
			response.setQuotationId(quotation.getQuotationId());
			response.setBankUserid(customerRepository.findBankName(quotation.getBankUserid()));
			response.setCurrency(quotation.getTransactionId().getLcCurrency());
			response.setQuotationStatus(quotation.getQuotationStatus());

			response.setConfirmationCharges(quotation.getConfirmationCharges());
			response.setConfChgsIssuanceToNegot(quotation.getConfChgsIssuanceToNegot() != null
					? quotation.getConfChgsIssuanceToNegot().toUpperCase()
					: "");
			response.setConfChgsIssuanceToMatur(quotation.getConfChgsIssuanceToMatur() != null
					? quotation.getConfChgsIssuanceToMatur().toUpperCase()
					: "");
			if (quotation.getApplicableBenchmark() != null)
				response.setApplicableBenchmark(quotation.getApplicableBenchmark());
			if (quotation.getCommentsBenchmark() != null)
				response.setCommentsBenchmark(quotation.getCommentsBenchmark());
			if (quotation.getDiscountingCharges() != null)
				response.setDiscountingChargesPA(quotation.getDiscountingCharges());
			if (quotation.getRefinancingCharges() != null)
				response.setRefinancingCharges(quotation.getRefinancingCharges());
			if (quotation.getBankerAcceptCharges() != null)
				response.setBankerAcceptCharges(quotation.getBankerAcceptCharges());
			if (quotation.getNegotiationChargesFixed() != null)
				response.setNegotiationChargesFixed(quotation.getNegotiationChargesFixed());
			if (quotation.getNegotiationChargesPerct() != null)
				response.setNegotiationChargesPerct(quotation.getNegotiationChargesPerct());
			if (quotation.getDocHandlingCharges() != null)
				response.setDocHandlingCharges(quotation.getDocHandlingCharges());
			if (quotation.getOtherCharges() != null)
				response.setOtherCharges(quotation.getOtherCharges());
			if (quotation.getChargesType() != null)
				response.setSpecifyTypeOfCharges(quotation.getChargesType());
			if (quotation.getMinTransactionCharges() != null)
				response.setMinimumTransactionCharges(quotation.getMinTransactionCharges());
			response.setTotalQuoteValue(quotation.getTotalQuoteValue());
			response.setValidityDate(quotation.getValidityDate());

			response.setTransactionId(quotation.getTransactionId().getTransactionId());
			response.setRequirementType(quotation.getTransactionId().getRequirementType());
			response.setIb(quotation.getTransactionId().getLcIssuanceBank());
			response.setAmount(quotation.getTransactionId().getLcValue() + "");
			if (quotation.getTransactionId().getRequirementType().equalsIgnoreCase("Confirmation")) {
				response.setTanor(quotation.getTransactionId().getConfirmationPeriod());
			} else if (quotation.getTransactionId().getRequirementType().equalsIgnoreCase("Discounting")) {
				response.setTanor(quotation.getTransactionId().getDiscountingPeriod());
			} else if (quotation.getTransactionId().getRequirementType().equalsIgnoreCase("Refinance")) {
				response.setTanor(quotation.getTransactionId().getRefinancingPeriod());
			} else if (quotation.getTransactionId().getRequirementType().equalsIgnoreCase("ConfirmAndDiscount")) {
				response.setTanor(quotation.getTransactionId().getConfirmationPeriod());
			} else if (quotation.getTransactionId().getRequirementType().equalsIgnoreCase("Banker")) {
				response.setTanor(quotation.getTransactionId().getDiscountingPeriod());
			} else {
				response.setTanor("");
			}

			return new ResponseEntity<QuotationDetailsResponse>(response, HttpStatus.OK);
		}
		return null;
	}
	
	@Override
	public List<String> customerUserIdSearch(String userId) {
		String val = Utility.getUserCountry();
		if (!val.equalsIgnoreCase("All")) {
			List<String> list = Stream.of(val.split(",")).collect(Collectors.toList());
			return customerRepository.userIdSearchByCountry(userId.toLowerCase(), list);
		} else {
			return customerRepository.userIdSearchForCustomer(userId.toLowerCase());
		}
	}

}
