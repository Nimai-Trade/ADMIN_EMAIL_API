
package com.nimai.admin.service.impl;

import org.slf4j.LoggerFactory;

import com.nimai.admin.payload.SPlanBean;
import com.nimai.admin.payload.CouponBean;
import com.nimai.admin.payload.CurrencyResponce;
import com.nimai.admin.payload.EligibleEmailList;
import com.nimai.admin.payload.VasUpdateRequestBody;
import java.util.stream.Stream;
import com.nimai.admin.payload.QuotationListResponse;
import java.util.HashSet;
import java.util.Set;
import java.util.Map;
import com.nimai.admin.model.NimaiKyc;
import com.nimai.admin.model.NimaiLCMaster;

import java.util.Calendar;

import com.nimai.admin.model.BankRatingModel;
import com.nimai.admin.model.GenericResponse;
import com.nimai.admin.model.NimaiEmailScheduler;
import com.nimai.admin.model.NimaiEmailSchedulerAlertToBanks;

import java.util.Date;
import com.nimai.admin.payload.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import com.nimai.admin.util.Utility;

import io.jsonwebtoken.lang.Arrays;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import com.nimai.admin.payload.PagedResponse;
import com.nimai.admin.payload.SearchRequest;
import com.nimai.admin.payload.TransactionRequestBody;
import com.nimai.admin.payload.VasDetails;

import java.util.Iterator;
import com.nimai.admin.model.NimaiMDiscount;
import com.nimai.admin.model.NimaiSubscriptionVas;
import com.nimai.admin.model.PreferredBankModel;

import java.text.DecimalFormat;
import java.util.ArrayList;
import com.nimai.admin.model.NimaiSubscriptionDetails;
import com.nimai.admin.payload.PlanOfPaymentDetailsResponse;
import com.nimai.admin.payload.PreferredBankListResponse;
import com.nimai.admin.payload.PreferredBankRequest;

import java.util.function.Function;
import java.util.Comparator;
import com.nimai.admin.model.NimaiFKyc;
import com.nimai.admin.payload.KycBDetailResponse;
import com.nimai.admin.payload.KycFiledBean;
import com.nimai.admin.model.NimaiMQuotation;
import com.nimai.admin.payload.QuotationDetailsResponse;
import com.nimai.admin.model.NimaiFOwner;
import com.nimai.admin.model.NimaiMEmployee;
import com.nimai.admin.model.NimaiMKycfileds;
import com.nimai.admin.model.NimaiMCustomer;
import com.nimai.admin.controller.BankController;
import com.nimai.admin.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import com.nimai.admin.payload.OwenerBean;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import com.nimai.admin.payload.AssociatedAccountsDetails;
import java.util.List;
import com.nimai.admin.util.ModelMapper;
import com.nimai.admin.payload.BankDetailsResponse;
import com.nimai.admin.payload.BankRatingRequest;

import org.springframework.http.ResponseEntity;
import com.nimai.admin.util.SPlanSerialComparator;
import org.slf4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import javax.validation.Valid;

import com.nimai.admin.repository.NimaiEmailSchedulerRepository;
import com.nimai.admin.specification.KycSpecification;
import com.nimai.admin.specification.QuotationSpecification;
import com.nimai.admin.repository.EmployeeRepository;
import com.nimai.admin.repository.KycFiledRepository;
import com.nimai.admin.repository.DiscountRepository;
import com.nimai.admin.repository.TransactionsRepository;
import com.nimai.admin.specification.NimaiSubscriptionVasSpecification;
import com.nimai.admin.specification.CustomerSearchSpecification;
import com.nimai.admin.repository.ReferrerRepository;
import com.nimai.admin.repository.SubscriptionDetailsRepository;
import com.nimai.admin.repository.KycRepository;
import com.nimai.admin.repository.NimaiKycRepository;
import com.nimai.admin.repository.NimaiLcMasterRepository;
import com.nimai.admin.repository.QuotationRepository;
import com.nimai.admin.repository.SubscriptionVasRepository;
import com.nimai.admin.repository.BankRatingRepository;
import com.nimai.admin.repository.CustomerRepository;
import com.nimai.admin.repository.MasterSubsPlanRepository;
import com.nimai.admin.repository.NimaiEmailSchedulerAlertToBanksRepository;

import org.springframework.beans.factory.annotation.Autowired;
import com.nimai.admin.repository.OwnerMasterRepository;
import com.nimai.admin.repository.PreferredBankRepository;

import org.springframework.stereotype.Service;
import com.nimai.admin.service.BankService;

@Service
public class BankServiceImpl implements BankService {

	private static Logger logger = LoggerFactory.getLogger(BankServiceImpl.class);

	@Autowired
	OwnerMasterRepository owrepo;
	@Autowired
	MasterSubsPlanRepository sPlanMasterRepo;
	@Autowired
	QuotationRepository quotemasterrepo;
	@Autowired
	SubscriptionDetailsRepository sPlanRepo;
	@Autowired
	CustomerRepository repo;
	@Autowired
	NimaiLcMasterRepository lcMasterrepo;
	@Autowired
	SubscriptionVasRepository vasRep;
	@Autowired
	QuotationRepository quoteRepo;
	@Autowired
	NimaiKycRepository kycNRepo;
	@Autowired
	KycRepository kycRepo;
	@Autowired
	SubscriptionDetailsRepository planRepo;
	@Autowired
	ReferrerRepository referrerRepo;
	@Autowired
	KycFiledRepository fieldRepo;
	@Autowired
	CustomerSearchSpecification customerSearchSpecification;
	@Autowired
	NimaiSubscriptionVasSpecification vasSearchSpecification;
	@Autowired
	TransactionsRepository transactionsRepository;
	@Autowired
	DiscountRepository discRepo;
	@Autowired
	EmployeeRepository employeeRepository;
	@Autowired
	QuotationSpecification quotationSpecification;
	@Autowired
	KycSpecification kycSpecification;
	@Autowired
	NimaiEmailSchedulerRepository schRepo;
	@Autowired
	SubscriptionVasRepository vasRepo;
	@Autowired
	PreferredBankRepository preferBankRepo;
	@Autowired
	BankRatingRepository bankratingRepo;
	@Autowired
	EntityManagerFactory em;
	@Autowired
	NimaiEmailSchedulerAlertToBanksRepository userDao;
//	private static final Logger logger;
	@Autowired
	SPlanSerialComparator comparatorSplan;

	public ResponseEntity<BankDetailsResponse> getBankDetailUserId(final String userid) {
		final NimaiMCustomer cust = this.repo.findByUserid(userid);
		if (cust != null) {
			final BankDetailsResponse response = ModelMapper.mapBankDetails(cust);
			response.setCurrencyCode(cust.getCurrencyCode());
			response.setDesignation(cust.getDesignation());
			System.out.println("Email ID: " + cust.getEmailAddress());
			NimaiMCustomer referredcust = null;
			try {
				final String referredUserId = this.referrerRepo.findReferredUserByEmailId(cust.getEmailAddress());
				System.out.println("Referred User ID: " + referredUserId);
				referredcust = this.repo.findByUserid(referredUserId);
			} catch (Exception e) {
				response.setReferredUserId("");
				response.setReferredFirstName("");
				response.setReferredLastName("");
				response.setReferredCompanyName("");
			}
			if (referredcust != null) {
				response.setReferredUserId(referredcust.getUserid());
				response.setReferredFirstName(referredcust.getFirstName());
				response.setReferredLastName(referredcust.getLastName());
				response.setReferredCompanyName(referredcust.getCompanyName());
			} else {
				response.setReferredUserId("");
				response.setReferredFirstName("");
				response.setReferredLastName("");
				response.setReferredCompanyName("");
			}
			if (response.getRmFirstName() != null) {
				final NimaiMEmployee emp = this.employeeRepository.findByEmpCode(response.getRmFirstName());
				response.setRmFirstName(emp.getEmpName());
				response.setRmLastName(emp.getEmpLastName());
				response.setRmDesignation(emp.getDesignation());
			}
			if (userid.substring(0, 2).equalsIgnoreCase("BA")) {
				final List<NimaiMCustomer> subsidiaryList = (List<NimaiMCustomer>) this.repo
						.findAdditionalUserByUserId(userid);
				if (subsidiaryList != null && subsidiaryList.size() != 0) {
					AssociatedAccountsDetails associatedAccountsDetails = null;
					List<AssociatedAccountsDetails> data = new ArrayList<>();
					for (NimaiMCustomer sub : subsidiaryList) {
						AssociatedAccountsDetails subdetails = new AssociatedAccountsDetails();
						subdetails.setUserId(sub.getUserid());
						subdetails.setName(sub.getFirstName());
						subdetails.setStatus(sub.getAccountStatus());
						subdetails.setEmailId(sub.getEmailAddress());
						subdetails.setCountry(sub.getRegisteredCountry());
						subdetails.setLandline(sub.getLandline());
						data.add(subdetails);
					}
					response.setSubsidiary(data);
				}
			} else {
				final List<NimaiMCustomer> subsidiaryList = (List<NimaiMCustomer>) this.repo
						.findSubsidiaryByUserId(userid);
				if (subsidiaryList != null && subsidiaryList.size() != 0) {
					List<AssociatedAccountsDetails> data = new ArrayList<>();
					for (NimaiMCustomer sub : subsidiaryList) {
						AssociatedAccountsDetails subdetails = new AssociatedAccountsDetails();
						subdetails.setUserId(sub.getUserid());
						subdetails.setName(sub.getFirstName());
						subdetails.setStatus(sub.getAccountStatus());
						subdetails.setEmailId(sub.getEmailAddress());
						subdetails.setCountry(sub.getRegisteredCountry());
						subdetails.setLandline(sub.getLandline());
						data.add(subdetails);
					}

					response.setSubsidiary(data);
				}
			}
			final List<NimaiFOwner> owener = (List<NimaiFOwner>) this.owrepo.findDataByUserId(userid);
			System.out.println("Owner List: " + owener);
			if (owener != null && owener.size() != 0) {
				final List<OwenerBean> data2 = owener.stream().filter(d1 -> d1.getUserid().equals(d1.getUserid()))
						.map(owe -> new OwenerBean(owe.getOwnerFname(), owe.getOwnerLname(), owe.getOwnerDesignation()))
						.collect(Collectors.toList());
				response.setOwner((List) data2);
			}
			return (ResponseEntity<BankDetailsResponse>) new ResponseEntity(response, HttpStatus.OK);
		}
		BankServiceImpl.logger.info("No Bank Details exist for given id");
		throw new ResourceNotFoundException("No Bank Details exist for given id");
	}

	public List<QuotationDetailsResponse> getQuotesUserId(final String userId) {
		final List<NimaiMQuotation> quotes = (List<NimaiMQuotation>) this.quoteRepo
				.findByUserid(new NimaiMCustomer(userId));
		final List<QuotationDetailsResponse> response = quotes.stream()
				.map(emp -> new QuotationDetailsResponse(emp.getQuotationId(), emp.getUserid().getUserid(),
						emp.getTransactionId().getTransactionId(), emp.getBankUserid(), emp.getConfirmationCharges(),
						emp.getConfChgsIssuanceToNegot(), emp.getConfChgsIssuanceToMatur(),
						emp.getMinimumTransactionCharges(), emp.getOtherCharges(), emp.getValidityDate(),
						emp.getTotalQuoteValue(), emp.getCurrency(), emp.getQuotationStatus()))
				.collect(Collectors.toList());
		return response;
	}

	@Override
	public List<KycBDetailResponse> getKycDetailsUserId(NimaiMCustomer userId) {
		List<NimaiFKyc> fkyc = kycRepo.findKycByUserid(userId.getUserid());
//		String country = Utility.getUserCountry();
//		List<String> cNames = Stream.of(country.split(",", -1)).collect(Collectors.toList());
//		if (country != null && country.equalsIgnoreCase("all")) {
//			fkyc = kycRepo.findByUserid(userId);
//		} else if (country != null && !country.equalsIgnoreCase("all")) {
//			fkyc = kycRepo.findByUseridAndCountryIn(userId, cNames);
//		}
		List<KycBDetailResponse> kycResp = fkyc.stream().sorted(Comparator.comparing(NimaiFKyc::getId).reversed())
				.map(kyc -> new KycBDetailResponse(kyc.getId(), kyc.getDocumentName(), kyc.getCountry(),
						kyc.getKycType(), kyc.getDocumentType(), kyc.getComment(), kyc.getKycStatus(),
						kyc.getCheckerComment(), kyc.getEncodedFileContent().substring(
								kyc.getEncodedFileContent().indexOf("|") + 1, kyc.getEncodedFileContent().length())))
				.collect(Collectors.toList());

		return kycResp;

	}

	public List<PlanOfPaymentDetailsResponse> getPlanOPayDetails(final String userId) {
		final NimaiMCustomer cust = (NimaiMCustomer) this.repo.getOne(userId);
		final List<NimaiSubscriptionDetails> subs = (List<NimaiSubscriptionDetails>) this.planRepo.findByUserid(cust)
				.stream().sorted(Comparator.comparingInt(NimaiSubscriptionDetails::getSplSerialNumber).reversed())
				.limit(5L).collect(Collectors.toList());
		final List<PlanOfPaymentDetailsResponse> data = new ArrayList<PlanOfPaymentDetailsResponse>();
		for (final NimaiSubscriptionDetails pay : subs) {
			final PlanOfPaymentDetailsResponse value = new PlanOfPaymentDetailsResponse();
			value.setSubscriptionId(pay.getSubscriptionId());
			value.setSplSerialNumber(pay.getSplSerialNumber());
			value.setUserid(pay.getUserid().getUserid());
			value.setSubscriptionName(pay.getSubscriptionName());
			value.setSubscriptionAmount(
					(pay.getSubscriptionAmount() != null) ? ("USD " + pay.getSubscriptionAmount()) : "");
			value.setLcCount(pay.getLcCount() + " Trxn Credits");
			value.setSplanStartDate(pay.getSplanStartDate());
			value.setSplanEndDate(pay.getSplanEndDate());
			value.setSubsidiaries(pay.getSubsidiaries() + " Subsidiaries");
			value.setRelationshipManager(pay.getRelationshipManager() + ", Relationship Manager");
			value.setCustomerSupport(pay.getCustomerSupport() + " Customer Support");
			value.setRemark(pay.getRemark());

			value.setIsVasAppliedWithSPlan(pay.getIsVasApplied());
			if (pay.getStatus().equalsIgnoreCase("ACTIVE")) {
				value.setStatus("Active");
				value.setMakerComment(pay.getMakerComment());
				value.setCheckerComment(pay.getCheckerComment());
			} else {
				value.setStatus(pay.getStatus());
			}
			value.setSubscriptionValidity(pay.getSubscriptionValidity() + " Months Validity");
			value.setDiscount((pay.getDiscount() == null) ? "" : ("USD " + pay.getDiscount()));
			value.setPaymentStatus(pay.getPaymentStatus());
			value.setInsertedDate(pay.getInsertedDate());
			value.setPaymentMode(pay.getPaymentMode());
			final List<NimaiSubscriptionVas> subsVas = (List<NimaiSubscriptionVas>) this.vasRep
					.findByUserIdAndSubscriptionIdAndSerialNo(userId, pay.getSubscriptionId(),
							pay.getSplSerialNumber());

			VasDetails vasDetailsVa = new VasDetails();
			List<VasDetails> details = new ArrayList<>();
			if (subsVas == null) {
				vasDetailsVa.setVasPlan("");
				vasDetailsVa.setVasStatus("");
				vasDetailsVa.setVasBenefits("");
				vasDetailsVa.setVasAmount("");
				vasDetailsVa.setTotalAmount("");
				details.add(vasDetailsVa);
			}
			List admins = subsVas.stream().filter((subsVa) -> subsVa.getPaymentSts().equalsIgnoreCase("Pending")
					&& subsVa.getStatus().equalsIgnoreCase("ACTIVE")).collect(Collectors.toList());
			if (admins.size() >= 1) {
				value.setIsMultipleVasApplied("1");
			} else {
				value.setIsMultipleVasApplied("0");
			}

			for (final NimaiSubscriptionVas vasDetailsNew : subsVas) {
				VasDetails vasDetailsValue = new VasDetails();
				System.out.println("subscriptionid" + pay.getSplSerialNumber());
				System.out.println("" + vasDetailsNew.toString());
				if (pay.getStatus().equalsIgnoreCase("ACTIVE") || pay.getStatus().equalsIgnoreCase("INACTIVE")) {
					if ((vasDetailsNew.getStatus().equalsIgnoreCase("Active")
							|| vasDetailsNew.getStatus().equalsIgnoreCase("Inactive")) && pay.getVasAmount() != 0
							&& !vasDetailsNew.getStatus().equalsIgnoreCase("Rejected")) {

						vasDetailsValue.setVasPlan(vasDetailsNew.getPlanName());
						vasDetailsValue.setVasStatus(vasDetailsNew.getStatus());
						vasDetailsValue.setVasBenefits(vasDetailsNew.getDescription1() + ", "
								+ vasDetailsNew.getDescription2() + ", " + vasDetailsNew.getDescription3());
						vasDetailsValue.setVasAmount("USD " + vasDetailsNew.getPricing());
						vasDetailsValue
								.setTotalAmount("USD " + (pay.getSubscriptionAmount() + vasDetailsNew.getPricing()));
						vasDetailsValue.setVasMakerComment(vasDetailsNew.getMakerComment());
						vasDetailsValue.setVasCheckerComment(vasDetailsNew.getCheckerComment());
						vasDetailsValue.setVasPaymentStatus(vasDetailsNew.getPaymentSts());
						vasDetailsValue.setVasPlanPaymentMode(vasDetailsNew.getMode());
						vasDetailsValue.setVasId(vasDetailsNew.getId());
						if (vasDetailsNew.getsPlanVasFlag() == null) {
							vasDetailsValue.setIsSplanWithVasFlag(2);
						} else if (vasDetailsNew.getsPlanVasFlag() == 0) {
							value.setIsSplanWithVasFlag((int) vasDetailsNew.getsPlanVasFlag());
						} else {
							if (vasDetailsNew.getsPlanVasFlag() != 1) {
								continue;
							}
							vasDetailsValue.setIsSplanWithVasFlag((int) vasDetailsNew.getsPlanVasFlag());
						}
					}
					details.add(vasDetailsValue);
				} else {
					if (vasDetailsNew.getStatus().equalsIgnoreCase("Inactive") && pay.getVasAmount() != 0) {
						vasDetailsValue.setVasPlan(vasDetailsNew.getPlanName());
						vasDetailsValue.setVasStatus(vasDetailsNew.getStatus());
						vasDetailsValue.setVasBenefits(vasDetailsNew.getDescription1() + ", "
								+ vasDetailsNew.getDescription2() + ", " + vasDetailsNew.getDescription3());
						vasDetailsValue.setVasAmount("USD " + vasDetailsNew.getPricing());
						vasDetailsValue
								.setTotalAmount("USD " + (pay.getSubscriptionAmount() + vasDetailsNew.getPricing()));
						vasDetailsValue.setVasMakerComment(vasDetailsNew.getMakerComment());
						vasDetailsValue.setVasCheckerComment(vasDetailsNew.getCheckerComment());
						vasDetailsValue.setVasPaymentStatus(vasDetailsNew.getPaymentSts());
						vasDetailsValue.setVasPlanPaymentMode(vasDetailsNew.getMode());
						vasDetailsValue.setVasId(vasDetailsNew.getId());
						if (vasDetailsNew.getsPlanVasFlag() == null) {
							vasDetailsValue.setIsSplanWithVasFlag(2);
						} else if (vasDetailsNew.getsPlanVasFlag() == 0) {
							vasDetailsValue.setIsSplanWithVasFlag((int) vasDetailsNew.getsPlanVasFlag());
						} else {
							if (vasDetailsNew.getsPlanVasFlag() != 1) {
								continue;
							}
							vasDetailsValue.setIsSplanWithVasFlag((int) vasDetailsNew.getsPlanVasFlag());
						}
					}
					details.add(vasDetailsValue);
				}
				value.setVasList(details);
				System.out.println("added list" + value.getVasList().toString());
			}
			// System.out.println("outside for loop details"+details.toString());
			// value.setVasList(details);
			if (pay.getUserid() != null) {
				if (pay.getInvoiceId() == null) {
					value.setTransactionId("");
				} else {
					value.setTransactionId(pay.getInvoiceId());
				}
			} else {
				value.setTransactionId("");
			}
			if (pay.getDiscountId() > 0) {
				final NimaiMDiscount dis = (NimaiMDiscount) this.discRepo.getOne(pay.getDiscountId());
				value.setCouponCode(dis.getCouponCode());
			} else {
				value.setCouponCode("");
			}
			value.setTotalAmount("USD " + (pay.getSubscriptionAmount() + pay.getVasAmount()));
			value.setAmountPaid("USD " + (pay.getSubscriptionAmount() + pay.getVasAmount()
					- ((pay.getDiscount() == null) ? 0.0 : pay.getDiscount())));
			data.add(value);
		}
		return data;
	}

	public PagedResponse<?> getSearchBankDetail(final SearchRequest request) {
		request.setSubscriberType("BANK");
		request.setBankType("UNDERWRITER");
		Pageable pageable = null;
		request.setSortBy("inserted_date");
		if ((request.getUserId() != null && request.getTxtStatus() == null)
				|| (request.getEmailId() != null && request.getTxtStatus() == null)
				|| (request.getMobileNo() != null && request.getTxtStatus() == null)) {
			request.setSize(1);
			pageable = (Pageable) PageRequest.of(request.getPage(), request.getSize(),
					request.getDirection().equalsIgnoreCase("desc")
							? Sort.by(new String[] { request.getSortBy() }).descending()
							: Sort.by(new String[] { request.getSortBy() }).ascending());
		} else if ((request.getTxtStatus() == null)) {
			pageable = (Pageable) PageRequest.of(request.getPage(), request.getSize(),
					request.getDirection().equalsIgnoreCase("desc")
							? Sort.by(new String[] { request.getSortBy() }).descending()
							: Sort.by(new String[] { request.getSortBy() }).ascending());
		} else if ((request.getTxtStatus().equalsIgnoreCase("Pending"))) {
			request.setSortBy("nfk.INSERTED_DATE");
			pageable = (Pageable) PageRequest.of(request.getPage(), request.getSize(),
					request.getDirection().equalsIgnoreCase("desc")
							? Sort.by(new String[] { request.getSortBy() }).descending()
							: Sort.by(new String[] { request.getSortBy() }).ascending());

		} else if ((request.getTxtStatus().equalsIgnoreCase("Rejected"))) {
			request.setSortBy("nfk.INSERTED_DATE");
			pageable = (Pageable) PageRequest.of(request.getPage(), request.getSize(),
					request.getDirection().equalsIgnoreCase("desc")
							? Sort.by(new String[] { request.getSortBy() }).descending()
							: Sort.by(new String[] { request.getSortBy() }).ascending());

		}else if(request.getTxtStatus().equalsIgnoreCase("PaymentPendingUser")) {
			request.setSortBy("inserted_date");
			pageable = (Pageable) PageRequest.of(request.getPage(), request.getSize(),
					request.getDirection().equalsIgnoreCase("desc")
							? Sort.by(new String[] { request.getSortBy() }).descending()
							: Sort.by(new String[] { request.getSortBy() }).ascending());
		} else {

			pageable = (Pageable) PageRequest.of(request.getPage(), request.getSize(),
					request.getDirection().equalsIgnoreCase("desc")
							? Sort.by(new String[] { request.getSortBy() }).descending()
							: Sort.by(new String[] { request.getSortBy() }).ascending());
		}

		String countryNames = Utility.getUserCountry();
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
		} else if (countryNames == null && request.getCountry() == null) {
		}
		final List<String> value = Stream.of(request.getCountryNames().split(",", -1)).collect(Collectors.toList());
		System.out.println("Values BankService: " + value);

		if (request.getRole() != null && request.getRole().equalsIgnoreCase("Bank RM")) {
			request.setLoginUserId(Utility.getUserId());
			request.setRmStatus("Active");
		} else {
//			final String countryNames = Utility.getUserCountry();
//			if (countryNames == null || !countryNames.equalsIgnoreCase("all") || request.getCountry() != null) {
//				if (countryNames != null && !countryNames.equalsIgnoreCase("all") && request.getCountry() == null) {
//					request.setCountryNames(countryNames);
//				} else if (countryNames == null && request.getCountry() == null) {
//				}
//			}
		}
		Page<NimaiMCustomer> bankDetails = null;
		if (request.getRole().equalsIgnoreCase("Bank RM")) {
			String userId = Utility.getUserId();
			System.out.println("===========RM userId" + userId);
			// CustomerServiceImpl custList=new CustomerServiceImpl();
			bankDetails = retiriveListOnRMId(userId, request, value, pageable);
		} else {
			// (Page<NimaiMCustomer>) this.repo
//			.findAll(this.customerSearchSpecification.getBankFilter(request), pageable);
			if (request.getUserId() != null && request.getTxtStatus() == null) {
				System.out.println("************=============1.insdie request.getUserId()============***********"
						+ request.getUserId());
				bankDetails = repo.getBankDetailsByUserId(request.getUserId(), value, pageable);

			}

			else if (request.getMobileNo() != null && request.getTxtStatus() == null) {
				System.out.println("************=============2.request.getMobileNo()============***********"
						+ request.getUserId());
				bankDetails = repo.getBankDetailsByMobileNo(request.getMobileNo(), value, pageable);

			}

			else if (request.getCountry() != null && request.getTxtStatus() == null) {
				System.out.println(
						"************=============3.request.getCountry()============***********" + request.getUserId());
				bankDetails = repo.getBankDetailsByCountry(request.getCountry(), value, pageable);

			}

			else if (request.getBankName() != null && request.getTxtStatus() == null) {
				System.out.println("************=============4.request.getCompanyName()============***********"
						+ request.getUserId());
				bankDetails = repo.getBankDetailsByCompanyName(request.getBankName(), value, pageable);

			}

			else if (request.getEmailId() != null && request.getTxtStatus() == null) {
				System.out.println(
						"************=============5.request.getEmailId()============***********" + request.getUserId());
				bankDetails = repo.getBankDetailsByEmailId(request.getEmailId(), value, pageable);

			}

			else if (request.getTxtStatus() == null || request.getTxtStatus().equalsIgnoreCase("all")) {
				bankDetails = repo.getAllBankKYC(value, pageable);
				System.out.println("************=============6.request.getTxtStatus() == null============***********"
						+ request.getUserId());

			}

			else if (request.getTxtStatus().equalsIgnoreCase("Pending")) {
				bankDetails = repo.getPendingBankKYC(value, pageable);
				System.out.println(
						"************=============7.request.getTxtStatus().equalsIgnoreCase(\"Pending\") == null============***********"
								+ request.getUserId());

			}

			else if (request.getTxtStatus().equalsIgnoreCase("PaymentPending")) {
				bankDetails = repo.getPaymentPendingBank(value, pageable);
				System.out.println(
						"************=============7.request.getTxtStatus().equalsIgnoreCase(\"PaymentPending\") ============***********"
								+ request.getUserId());

			}

			else if (request.getTxtStatus().equalsIgnoreCase("subexpiry")) {
				bankDetails = repo.getSubscriptionExpiryBank(value, pageable);
				System.out.println("************=============Subscription Expiry in 30 days============***********");

			}

			else if (request.getTxtStatus().equalsIgnoreCase("PaymentPendingUser")) {
				bankDetails = repo.getPaymentPendingUserBank(value, pageable);
				System.out.println(
						"************=============PaymentPaymentUser============***********" + request.getUserId());

			}

			else if (request.getTxtStatus().equalsIgnoreCase("Approved")) {
				bankDetails = repo.getApprovedBankKYC(value, pageable);
				System.out.println(
						"************=============8.request.getTxtStatus().equalsIgnoreCase(\"Approved\")============***********"
								+ request.getUserId());

			}

			else if (request.getTxtStatus().equalsIgnoreCase("Rejected")) {
				bankDetails = repo.getRejectedBankKYC(value, pageable);
				System.out.println(
						"************=============10.request.getTxtStatus().equalsIgnoreCase(\"Rejected\")============***********"
								+ request.getUserId());

			}

			else if (request.getTxtStatus().equalsIgnoreCase("Not Uploaded")) {
				bankDetails = repo.getNotUploadBankKYC(value, pageable);
				System.out.println(
						"************=============11.request.getTxtStatus().equalsIgnoreCase(\"Not Uploaded\")============***********"
								+ request.getUserId());

			}
		}

		// final BankDetailsResponse response;
		final List<BankDetailsResponse> responses = (List<BankDetailsResponse>) bankDetails.map(cust -> {
			BankDetailsResponse response = new BankDetailsResponse();

			// System.out.println(bankDetails.toString());
			response.setUserid(cust.getUserid());
			response.setFirstName(cust.getFirstName());
			response.setLastName(cust.getLastName());
			response.setEmailAddress(cust.getEmailAddress());
			response.setMobileNumber(cust.getMobileNumber());
			response.setLandline(cust.getLandline());
			response.setCountryName(cust.getCountryName());
			response.setBankName(cust.getBankName());
//			response.setPlanOfPayments((cust.getNimaiSubscriptionDetailsList().size() != 0)
//					? this.collectPlanName(cust.getNimaiSubscriptionDetailsList())
//					: "No Active Plan");
//			if (response.getPlanOfPayments().isEmpty() || response.getPlanOfPayments() == null) {
//				response.setPlanOfPayments(
//						"Latest Inactive_".concat(this.collectInPlanName(cust.getNimaiSubscriptionDetailsList())));
//			}
			if(request.getTxtStatus()==null) {
				response.setPlanOfPayments(cust.getNimaiSubscriptionDetailsList().size() != 0
						? collectPlanName(cust.getNimaiSubscriptionDetailsList())
						: "No Active Plan");
				if (response.getPlanOfPayments().isEmpty() || response.getPlanOfPayments() == null) {
					response.setPlanOfPayments(
							("Latest Inactive_").concat(collectInPlanName(cust.getNimaiSubscriptionDetailsList())));
				}
			}
			
			
			else if (request.getTxtStatus().equalsIgnoreCase("PaymentPendingUser")) {
				if(cust.getNimaiSubscriptionDetailsList().size()!=0) {
					response.setPlanOfPayments(
							("Latest Inactive_").concat(collectInPlanName(cust.getNimaiSubscriptionDetailsList())));
				}else {
					response.setPlanOfPayments(cust.getNimaiSubscriptionDetailsList().size() != 0
							? collectPlanName(cust.getNimaiSubscriptionDetailsList())
							: "No Active Plan");
				}
				
			}else{
				response.setPlanOfPayments(cust.getNimaiSubscriptionDetailsList().size() != 0
						? collectPlanName(cust.getNimaiSubscriptionDetailsList())
						: "No Active Plan");
				if (response.getPlanOfPayments().isEmpty() || response.getPlanOfPayments() == null) {
					response.setPlanOfPayments(
							("Latest Inactive_").concat(collectInPlanName(cust.getNimaiSubscriptionDetailsList())));
				}
			}
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			response.setTotalQuotes(this.quoteRepo.quoteCout(cust.getUserid()));
			// response.setKyc(cust.getKycStatus());
			// System.out.println("==============useriD"+userId);
			List<NimaiFKyc> kycdetails = kycRepo.findByUserid(cust);
			if (kycdetails.size() == 0) {
				response.setKyc("Not Uploaded");
			} else {
				response.setKyc(cust.getKycStatus());
			}
			response.setRegisteredCountry(cust.getRegisteredCountry());
			return response;
		}).getContent();
		return (PagedResponse<?>) new PagedResponse((List) responses, bankDetails.getNumber(), bankDetails.getSize(),
				bankDetails.getTotalElements(), bankDetails.getTotalPages(), bankDetails.isLast());

	}

	private Page<NimaiMCustomer> retiriveListOnRMId(String rmId, SearchRequest request, List<String> value,
			Pageable pageable) {
		// TODO Auto-generated method stub
		Page<NimaiMCustomer> bankDetails = null;
		if (request.getUserId() != null && request.getTxtStatus() == null) {
			System.out.println("************=============1.insdie request.getUserId()============***********"
					+ request.getUserId());
			bankDetails = repo.getBankDetailsByUserIdRmId(request.getUserId(), rmId, value, pageable);

		}

		else if (request.getMobileNo() != null && request.getTxtStatus() == null) {
			System.out.println(
					"************=============2.request.getMobileNo()============***********" + request.getUserId());
			bankDetails = repo.getBankDetailsByMobileNoRmId(request.getMobileNo(), rmId, value, pageable);

		}

		else if (request.getCountry() != null && request.getTxtStatus() == null) {
			System.out.println(
					"************=============3.request.getCountry()============***********" + request.getUserId());
			bankDetails = repo.getBankDetailsByCountryRmId(request.getCountry(), rmId, value, pageable);

		}

		else if (request.getBankName() != null && request.getTxtStatus() == null) {
			System.out.println(
					"************=============4.request.getCompanyName()============***********" + request.getUserId());
			bankDetails = repo.getBankDetailsByCompanyNameRmId(request.getBankName(), rmId, value, pageable);

		}

		else if (request.getEmailId() != null && request.getTxtStatus() == null) {
			System.out.println(
					"************=============5.request.getEmailId()============***********" + request.getUserId());
			bankDetails = repo.getBankDetailsByEmailIdRmId(request.getEmailId(), rmId, value, pageable);

		}

		else if (request.getTxtStatus() == null || request.getTxtStatus().equalsIgnoreCase("all")) {
			bankDetails = repo.getAllBankKYCRmId(rmId, value, pageable);
			System.out.println("************=============6.request.getTxtStatus() == null============***********"
					+ request.getUserId());

		}

		else if (request.getTxtStatus().equalsIgnoreCase("Pending")) {
			bankDetails = repo.getPendingBankKYCRmId(rmId, value, pageable);
			System.out.println(
					"************=============7.request.getTxtStatus().equalsIgnoreCase(\"Pending\") == null============***********"
							+ request.getUserId());

		}

		else if (request.getTxtStatus().equalsIgnoreCase("Approved")) {
			bankDetails = repo.getApprovedBankKYCRmId(rmId, value, pageable);
			System.out.println(
					"************=============8.request.getTxtStatus().equalsIgnoreCase(\"Approved\")============***********"
							+ request.getUserId());

		}

		else if (request.getTxtStatus().equalsIgnoreCase("Rejected")) {
			bankDetails = repo.getRejectedBankKYCRmId(rmId, value, pageable);
			System.out.println(
					"************=============10.request.getTxtStatus().equalsIgnoreCase(\"Rejected\")============***********"
							+ request.getUserId());

		}

		else if (request.getTxtStatus().equalsIgnoreCase("Not Uploaded")) {
			bankDetails = repo.getNotUploadBankKYCRmId(rmId, value, pageable);
			System.out.println(
					"************=============11.request.getTxtStatus().equalsIgnoreCase(\"Not Uploaded\")============***********"
							+ request.getUserId());

		}
		return bankDetails;
	}

	public ResponseEntity<?> kycStatusUpdate(final KycBDetailResponse data) {
		final NimaiFKyc chck = (NimaiFKyc) this.kycRepo.getOne(data.getKycid());
		if (chck.getApprovedMaker().equalsIgnoreCase(Utility.getUserId())) {
			return (ResponseEntity<?>) new ResponseEntity(
					new ApiResponse(Boolean.valueOf(false), "You dont have the authority for this operation!!!"),
					HttpStatus.OK);
		}
		BankServiceImpl.logger.debug("updating kyc status-");
		try {
			final NimaiFKyc fkyc = (NimaiFKyc) this.kycRepo.getOne(data.getKycid());
			fkyc.setKycStatus(data.getKycStatus());
			fkyc.setApprovedBy(data.getApproverName());
			fkyc.setApprovedDate(new Date());
			fkyc.setCheckerComment(data.getApprovalReason().concat(" " + "-" + " " + Utility.getUserId()));
			// fkyc.setComment(data.getComment());
			this.kycRepo.save(fkyc);
			final NimaiMCustomer customer = (NimaiMCustomer) this.repo.getOne(fkyc.getUserid().getUserid());
			final NimaiMEmployee emp = this.employeeRepository.findByEmpCode(customer.getRmId());
			if (data.getKycStatus().equalsIgnoreCase("Rejected")) {
				final NimaiEmailScheduler schdata = new NimaiEmailScheduler();
				if (emp != null && customer.getRmStatus().equalsIgnoreCase("Active")) {
					schdata.setrMName(emp.getEmpName());
					schdata.setUserid(customer.getUserid());
					schdata.setrMemailId(emp.getEmpEmail());
					schdata.setSubMobile(emp.getEmpMobile());
					schdata.setSubLandLine(emp.getEmpMobile());
					schdata.setUserName(customer.getFirstName());
					if (customer.getUserid().substring(0, 2).equalsIgnoreCase("RE")) {
						schdata.setEvent("KYC_REJECTION_FROMRMTO_REFERRER");
						schdata.setDescription1(customer.getBusinessType());
					} else if (customer.getUserid().substring(0, 2).equalsIgnoreCase("BA")) {
						schdata.setEvent("KYC_REJECTION_FROMRMTO_BANk");
						schdata.setDescription1(customer.getBankName());
					} else {
						schdata.setEvent("KYC_REJECTION_FROMRMTO_CUSTOMER");
					}
				} else {
					schdata.setrMemailId("nimaitradesupport@360tf.com");
					if (customer.getUserid().substring(0, 2).equalsIgnoreCase("RE")) {
						schdata.setEvent("KYC_REJECTION_FROMRMTO_REFERRER_Support");
						schdata.setDescription1(customer.getBusinessType());
					} else if (customer.getUserid().substring(0, 2).equalsIgnoreCase("BA")) {
						schdata.setEvent("KYC_REJECTION_FROMRMTO_BANk_Support");
						schdata.setDescription1(customer.getBankName());
					} else {
						schdata.setEvent("KYC_REJECTION_FROMRMTO_CUSTOMER_Support");
					}
				}
				schdata.setKycDocName(fkyc.getKycType());
				schdata.setEmailStatus("Pending");
				schdata.setEmailId(customer.getEmailAddress());
				final Calendar cal = Calendar.getInstance();
				final Date today = cal.getTime();
				schdata.setInsertedDate(today);
				this.schRepo.save(schdata);
			}
			String kycStatus = "";
			String companyName = "";
			if (customer.getKycStatus() == null) {
				kycStatus = "null";
			} else {
				kycStatus = customer.getKycStatus();
			}
			if (customer.getCompanyName() == null || customer.getCompanyName().isEmpty()) {
				companyName = "NA";
			} else {
				companyName = customer.getCompanyName();
			}
			final String kycSts = "Maker Approved";
			final List<NimaiKyc> listData = (List<NimaiKyc>) this.kycNRepo.findByUserid(fkyc.getUserid().getUserid());
			for (final NimaiKyc kycData : listData) {
				System.out.println("===============kyc data id" + kycData.getId());
			}
			Map<String, Set<String>> result = listData.stream().collect(Collectors.groupingBy(NimaiKyc::getKycType,
					Collectors.mapping(NimaiKyc::getKycStatus, Collectors.toSet())));
			String status = "";
			Set<String> bstat = new HashSet<String>();
			Set<String> cstat = new HashSet<String>();
			for (final Map.Entry<String, Set<String>> entry : result.entrySet()) {
				BankServiceImpl.logger.debug("Key = " + entry.getKey() + ", Value = " + entry.getValue());
				if (entry.getKey().equalsIgnoreCase("Personal")) {
					cstat = entry.getValue();
				}
				if (entry.getKey().equalsIgnoreCase("Business")) {
					bstat = entry.getValue();
				}
				if (entry.getValue().contains("Approved") && !status.equals("Pending")) {
					status = "Approved";
					System.out.println("inside approved !pending condition" + entry.getValue());
				} else if (bstat.contains("Rejected") && cstat.contains("Rejected")) {
					status = "Rejected";
					System.out.println("inside Rejected Rejected condition" + entry.getValue());
				} else if (bstat.contains("Rejected") && cstat.contains("Approved")) {
					status = "Rejected";
					System.out.println("inside Rejected Approved condition" + entry.getValue());
				} else if (bstat.contains("Approved") && cstat.contains("Rejected")) {
					status = "Rejected";
					System.out.println("inside Approved Rejected condition" + entry.getValue());
				} else {
					status = "Pending";
					System.out.println("inside else condition" + entry.getValue());
				}
			}
			if (status.equals("Approved")) {
				customer.setKycStatus("Approved");
				customer.setKycApprovaldate(new Date());
				final NimaiEmailScheduler schdata2 = new NimaiEmailScheduler();
				schdata2.setUserid(customer.getUserid());
				schdata2.setUserName(customer.getFirstName());
				if (customer.getUserid().substring(0, 2).equalsIgnoreCase("RE")) {
					schdata2.setEvent("KYC_APPROVAL_FROMRMTO_REFERRER");
					schdata2.setDescription1(customer.getCompanyName());
				} else if (customer.getUserid().substring(0, 2).equalsIgnoreCase("BC")
						|| customer.getUserid().substring(0, 2).equalsIgnoreCase("CU")) {
					schdata2.setEvent("KYC_APPROVAL_FROMRMTO_CUSTOMER");
					if (customer.getUserid().substring(0, 2).equalsIgnoreCase("BC")) {
						schdata2.setDescription1(customer.getCompanyName());
					} else {
						schdata2.setDescription1(customer.getCompanyName());
					}
				} else if (customer.getUserid().substring(0, 2).equalsIgnoreCase("BA")) {
					schdata2.setEvent("KYC_APPROVAL_FROMRMTO_BANk");
					schdata2.setDescription1(customer.getBankName());
				}
				schdata2.setEmailStatus("Pending");
				schdata2.setEmailId(customer.getEmailAddress());
				final String planStatus = "ACTIVE";

				if ((!customer.getUserid().substring(0, 2).equalsIgnoreCase("RE"))
						&& ((!customer.getAccountType().equalsIgnoreCase("SUBSIDIARY"))
								&& (!customer.getAccountType().equalsIgnoreCase("BANKUSER")))) {
					final Calendar cal2 = Calendar.getInstance();
					final Date today2 = cal2.getTime();
					schdata2.setInsertedDate(today2);
					this.schRepo.save(schdata2);
					NimaiSubscriptionDetails details = this.planRepo.getplanByUserID(customer.getUserid(), planStatus);
					final int noOfdays = 30;
					final int validityInNumber = Integer.valueOf(details.getSubscriptionValidity());
					final int actualEndDaysOfPLan = validityInNumber * noOfdays - 1;
					System.out.println(actualEndDaysOfPLan);
					final Calendar calforEndDate = Calendar.getInstance();
					calforEndDate.setTime(today2);
					System.out.println(today2);
					calforEndDate.add(5, actualEndDaysOfPLan);
					final Date endDate = calforEndDate.getTime();
					System.out.println(endDate);
					details.setSplanStartDate(today2);
					calforEndDate.add(5, actualEndDaysOfPLan);
					details.setSplanEndDate(endDate);
					this.planRepo.save(details);
				}

				if (customer.getAccountType().equalsIgnoreCase("SUBSIDIARY")) {
					final NimaiEmailScheduler schData = new NimaiEmailScheduler();
					schData.setSubUserId(customer.getUserid());
					schData.setUserid(customer.getAccountSource());
					schData.setEvent("SUBSIDIARY_ACTIVATION_ALERT");
					schData.setEmailStatus("Pending");
					this.schRepo.save(schData);
				}
				if (!customer.getAccountSource().equalsIgnoreCase("WEBSITE")
						&& customer.getAccountType().equalsIgnoreCase("REFER") && status.equalsIgnoreCase("Approved")) {
					final NimaiEmailScheduler accountReferEmail = new NimaiEmailScheduler();
					accountReferEmail.setSubUserId(customer.getUserid());
					final NimaiSubscriptionDetails details = this.planRepo.getplanByUserID(customer.getUserid(),
							planStatus);
					accountReferEmail.setSubscriptionAmount(String.valueOf(details.getSubscriptionAmount()));
					if (customer.getCompanyName() == null || customer.getCompanyName().isEmpty())
						accountReferEmail.setDescription1("NA");
					else
						accountReferEmail.setDescription1(customer.getCompanyName());
					final NimaiMCustomer accounSourceDetails = (NimaiMCustomer) this.repo
							.getOne(customer.getAccountSource());
					accountReferEmail.setUserid(customer.getAccountSource());
					accountReferEmail.setUserName(accounSourceDetails.getFirstName());
					accountReferEmail.setEmailId(accounSourceDetails.getEmailAddress());
					accountReferEmail.setEmailStatus("Pending");
					accountReferEmail.setEvent("CUSTOMER_ACCOUNT_REFERRED");
					this.schRepo.save(accountReferEmail);
				}
			} else if (status.equals("Rejected")) {
				customer.setKycStatus("Rejected");
			} else if (customer.getKycStatus().equalsIgnoreCase("Rejected")) {
				customer.setKycStatus("Rejected");
			} else {
				customer.setKycStatus("Pending");
			}
			if (fkyc.getKycStatus().equalsIgnoreCase("Rejected")) {
				customer.setKycStatus("Rejected");
			}
			this.repo.save(customer);
			return (ResponseEntity<?>) new ResponseEntity(
					new ApiResponse(Boolean.valueOf(true), "Kyc Status updated successfully..."), HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			BankServiceImpl.logger.debug("Error while updating kyc status - " + e);
			return (ResponseEntity<?>) new ResponseEntity(
					new ApiResponse(Boolean.valueOf(true), "Error while updating kyc status."), HttpStatus.BAD_REQUEST);
		}
	}

	public PagedResponse<?> getBankQuoteList(final SearchRequest request) {
		final Pageable pageable = (Pageable) PageRequest.of(request.getPage(), request.getSize(),
				request.getDirection().equalsIgnoreCase("desc")
						? Sort.by(new String[] { request.getSortBy() }).descending()
						: Sort.by(new String[] { request.getSortBy() }).ascending());
		final Page<NimaiMQuotation> quoteDetails = (Page<NimaiMQuotation>) this.quoteRepo
				.findAll(this.quotationSpecification.quotationFilter(request), pageable);
		// final QuotationListResponse response;
		final List<QuotationListResponse> responses = (List<QuotationListResponse>) quoteDetails.map(quote -> {
			QuotationListResponse response = new QuotationListResponse();
			response.setQuotationId(quote.getQuotationId());
			response.setBankUserid(quote.getBankUserid());
			response.setUserId(quote.getUserid().getUserid());
			response.setMobile(quote.getMobileNumber());
			response.setEmail(quote.getEmailAddress());
			response.setBeneficiary(quote.getTransactionId().getBeneBankName());
			response.setCountry(quote.getTransactionId().getBeneCountry());
			response.setTransactionId(quote.getTransactionId().getTransactionId());
			response.setInsertedDate(quote.getInsertedDate());
			response.setValidityDate(quote.getValidityDate());
			response.setIb(quote.getBankName());
			response.setAmount(quote.getTransactionId().getLcValue() + "");
			response.setCcy(quote.getTransactionId().getLcCurrency());
			response.setTotalQuoteValue(quote.getTotalQuoteValue());
			response.setQuoteCcy(quote.getTransactionId().getLcCurrency());
			response.setRequirement(quote.getTransactionId().getRequirementType());
			response.setTrxnStatus(quote.getQuotationStatus());
			return response;
		}).getContent();
		return (PagedResponse<?>) new PagedResponse((List) responses, quoteDetails.getNumber(), quoteDetails.getSize(),
				quoteDetails.getTotalElements(), quoteDetails.getTotalPages(), quoteDetails.isLast());
	}

	public String collectPlanName(final List<NimaiSubscriptionDetails> subscriptionList) {
		return subscriptionList.stream().filter(plan -> plan.getStatus().equalsIgnoreCase("Active"))
				.sorted(Comparator.comparingInt(NimaiSubscriptionDetails::getSplSerialNumber).reversed())
				.map(NimaiSubscriptionDetails::getSubscriptionName).collect(Collectors.joining(" ")).toString();
	}

	public String collectVasPlanName(final List<NimaiSubscriptionVas> subscriptionList) {
		return subscriptionList.stream().filter(plan -> plan.getStatus().equalsIgnoreCase("Active"))
				.sorted(Comparator.comparingInt(NimaiSubscriptionVas::getVasId).reversed())
				.map(NimaiSubscriptionVas::getPlanName).collect(Collectors.joining(" ")).toString();
	}

	public String collectInPlanName(final List<NimaiSubscriptionDetails> subscriptionList) {
		return subscriptionList.stream().filter(plan -> plan.getStatus().equalsIgnoreCase("INACTIVE"))
				.sorted(Comparator.comparingInt(NimaiSubscriptionDetails::getSplSerialNumber).reversed()).findFirst()
				.map(NimaiSubscriptionDetails::getSubscriptionName).get();
	}

	public ResponseEntity<?> makerKycStatusUpdate(final KycBDetailResponse data) {
		BankServiceImpl.logger.debug("updating kyc status - Maker " + data.getApproverName());
		try {
			final NimaiEmailScheduler schdata = new NimaiEmailScheduler();
			final NimaiFKyc fkyc = (NimaiFKyc) this.kycRepo.getOne(data.getKycid());
			final NimaiMCustomer customer = (NimaiMCustomer) this.repo.getOne(fkyc.getUserid().getUserid());
			if (data.getKycStatus().equalsIgnoreCase("Maker Rejected")) {
				final NimaiMCustomer cust = (NimaiMCustomer) this.repo.getOne(fkyc.getUserid().getUserid());
				final NimaiMEmployee emp = this.employeeRepository.findByEmpCode(cust.getRmId());
				if (emp == null || cust.getRmStatus().equalsIgnoreCase("Pending")) {
					schdata.setrMemailId("nimaitradesupport@360tf.com");
					schdata.setDescription1("ContactPersonDetails");
					if (cust.getUserid().substring(0, 2).equalsIgnoreCase("RE")) {
						schdata.setEvent("KYC_REJECTION_FROMRMTO_REFERRER_Support");
						schdata.setDescription2(cust.getBusinessType());
					} else if (cust.getUserid().substring(0, 2).equalsIgnoreCase("BA")) {
						schdata.setEvent("KYC_REJECTION_FROMRMTO_BANk_Support");
						schdata.setDescription2(cust.getBankName());
					} else {
						schdata.setEvent("KYC_REJECTION_FROMRMTO_CUSTOMER_Support");
					}
				} else {
					schdata.setrMName(emp.getEmpName());
					schdata.setUserid(cust.getUserid());
					schdata.setrMemailId(emp.getEmpEmail());
					schdata.setSubMobile(emp.getEmpMobile());
					schdata.setSubLandLine(emp.getEmpMobile());
					if (cust.getUserid().substring(0, 2).equalsIgnoreCase("RE")) {
						schdata.setEvent("KYC_REJECTION_FROMRMTO_REFERRER");
						schdata.setDescription1(cust.getBusinessType());
					} else if (cust.getUserid().substring(0, 2).equalsIgnoreCase("BA")) {
						schdata.setEvent("KYC_REJECTION_FROMRMTO_BANk");
						schdata.setDescription1(cust.getBankName());
					} else {
						schdata.setEvent("KYC_REJECTION_FROMRMTO_CUSTOMER");
					}
				}
				schdata.setUserName(cust.getFirstName());
				schdata.setKycDocName(fkyc.getKycType());
				schdata.setEmailStatus("Pending");
				schdata.setEmailId(cust.getEmailAddress());
				final Calendar cal = Calendar.getInstance();
				final Date today = cal.getTime();
				schdata.setInsertedDate(today);
				this.schRepo.save(schdata);
				cust.setKycStatus("Rejected");
				this.repo.save(cust);
			}
			fkyc.setComment(data.getComment().concat(" " + "-" + "  " + data.getApproverName()));
			fkyc.setReason(data.getReason());
			fkyc.setKycStatus(data.getKycStatus());
			fkyc.setApprovedMaker(data.getApproverName());
			fkyc.setMakerApprovedDate(new Date());
			this.kycRepo.save(fkyc);
			String kycStatus = "";
			if (customer.getKycStatus() == null) {
				kycStatus = "null";
			} else {
				kycStatus = customer.getKycStatus();
			}
			final List<NimaiKyc> listData = (List<NimaiKyc>) this.kycNRepo
					.findByUseridDesc(fkyc.getUserid().getUserid());
			System.out.println("=============result in makerKycStatus update==========" + listData.toString());
			final Map<String, Set<String>> result = listData.stream().collect(Collectors
					.groupingBy(NimaiKyc::getKycType, Collectors.mapping(NimaiKyc::getKycStatus, Collectors.toSet())));
			String status = "";
			Set<String> bstat = new HashSet<String>();
			Set<String> cstat = new HashSet<String>();
			for (final Map.Entry<String, Set<String>> entry : result.entrySet()) {
				BankServiceImpl.logger.debug("Key = " + entry.getKey() + ", Value = " + entry.getValue());
				if (entry.getKey().equalsIgnoreCase("Personal")) {
					cstat = entry.getValue();
				}
				if (entry.getKey().equalsIgnoreCase("Business")) {
					bstat = entry.getValue();
				}
				if (entry.getValue().contains("Maker Approved") && !status.equals("Pending")) {
					status = "Pending";
				} else if (bstat.contains("Maker Rejected") && cstat.contains("Maker Rejected")) {
					status = "Rejected";
				} else if (bstat.contains("Maker Rejected") && cstat.contains("Maker Approved")) {
					status = "Rejected";
				} else if (bstat.contains("Maker Approved") && cstat.contains("Maker Rejected")) {
					status = "Rejected";
				} else {
					status = "Pending";
				}
			}
			if (status.equals("Approved")) {
				customer.setKycStatus("Approved");
				customer.setKycApprovaldate(new Date());
			} else if (status.equals("Rejected")) {
				customer.setKycStatus("Rejected");
			} else {
				customer.setKycStatus("Pending");
			}
			if (fkyc.getKycStatus().equalsIgnoreCase("Maker Rejected")) {
				customer.setKycStatus("Rejected");
			}
			this.repo.save(customer);
			return (ResponseEntity<?>) new ResponseEntity(
					new ApiResponse(Boolean.valueOf(true), "Kyc Status updated successfully..."), HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			BankServiceImpl.logger.debug("Error while updating kyc status - " + e);
			return (ResponseEntity<?>) new ResponseEntity(
					new ApiResponse(Boolean.valueOf(false), "Error while updating kyc status."),
					HttpStatus.BAD_REQUEST);
		}
	}

	@SuppressWarnings("unchecked")
	public PagedResponse<?> getMakerApprovedKyc(final SearchRequest request) {
		final Pageable pageable = (Pageable) PageRequest.of(request.getPage(), request.getSize(),
				request.getDirection().equalsIgnoreCase("desc")
						? Sort.by(new String[] { request.getSortBy() }).descending()
						: Sort.by(new String[] { request.getSortBy() }).ascending());
		String countryNames = Utility.getUserCountry();
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
		} else if (countryNames == null && request.getCountry() == null) {
		}
		final List<String> value = Stream.of(request.getCountryNames().split(",", -1)).collect(Collectors.toList());
		System.out.println("Values BankService: " + value);
		System.out.println("subsType: " + request.getSubscriberType());
		System.out.println("bankType: " + request.getBankType());
		Page<NimaiFKyc> kycDetails;
		try {
			if ((request.getSubscriberType().equalsIgnoreCase("CUSTOMER")
					|| request.getSubscriberType().equalsIgnoreCase("REFERRER")) && request.getBankType() == null)
				kycDetails = (Page<NimaiFKyc>) this.kycRepo.findCustomerReferrerMakerApprovedKycByCountries(
						(List) value, request.getSubscriberType(), pageable);
			else if ((request.getSubscriberType().equalsIgnoreCase("BANK")
					&& request.getBankType().equalsIgnoreCase("UNDERWRITER"))
					|| (request.getSubscriberType().equalsIgnoreCase("BANK")
							&& request.getBankType().equalsIgnoreCase("CUSTOMER")))
				kycDetails = (Page<NimaiFKyc>) this.kycRepo.findMakerApprovedKycByCountriesSubsTypeBankType(
						(List) value, request.getSubscriberType(), request.getBankType(), pageable);
			else if ((request.getSubscriberType() == null && request.getBankType() == null)
					|| (request.getSubscriberType().isEmpty() && request.getBankType().isEmpty())
					|| (request.getSubscriberType().equalsIgnoreCase("null")
							&& request.getBankType().equalsIgnoreCase("null"))) {
				System.out.println("Getting value by clicking on TAB");
				kycDetails = (Page<NimaiFKyc>) this.kycRepo.findMakerApprovedKycByCountries((List) value, pageable);
			} else {
				System.out.println("Getting value by clicking on TAB");
				kycDetails = (Page<NimaiFKyc>) this.kycRepo.findMakerApprovedKycByCountries((List) value, pageable);
			}
		} catch (Exception e) {
			System.out.println("=========== In CATCH ============");
			kycDetails = (Page<NimaiFKyc>) this.kycRepo.findMakerApprovedKycByCountries((List) value, pageable);
		}

		// final KycBDetailResponse response;
		final List<KycBDetailResponse> responses = (List<KycBDetailResponse>) kycDetails.map(kyc -> {
			KycBDetailResponse response = new KycBDetailResponse();
			response.setKycid(kyc.getId());
			response.setDocName(kyc.getDocumentName());
			response.setCountry(kyc.getCountry());
			response.setKycType(kyc.getKycType());
			response.setDocType(kyc.getDocumentType());
			// response.setReason(kyc.getReason());
			response.setReason(kyc.getComment());
			response.setKycStatus(kyc.getKycStatus());
			response.setUserid(kyc.getUserid().getUserid());
			response.setApproverName(kyc.getApprovedMaker());
			return response;
		}).getContent();
		return (PagedResponse<?>) new PagedResponse((List) responses, kycDetails.getNumber(), kycDetails.getSize(),
				kycDetails.getTotalElements(), kycDetails.getTotalPages(), kycDetails.isLast());
	}

	@SuppressWarnings("unchecked")
	public ResponseEntity<?> wireTranferStatusUpdate(final VasUpdateRequestBody request) {
		final NimaiMCustomer customer2 = this.repo.findByUserid(request.getUserId());
		if (customer2.getPaymentApprovedBy() != null
				&& customer2.getPaymentApprovedBy().equalsIgnoreCase(request.getUserId())) {
			return (ResponseEntity<?>) new ResponseEntity(
					new ApiResponse(Boolean.valueOf(false), "You dont have the authority for this operation!!!"),
					HttpStatus.OK);
		}

		try {
			if ((request.getVasNUmber() == null) || (request.getVasNUmber().isEmpty())) {

				customer2.setPaymentApprovedBy(Utility.getUserId());
				customer2.setPaymentStatus(request.getStatus());
				customer2.setPaymentDate(new Date());
				this.repo.save(customer2);
				final NimaiEmailScheduler schedulerData = new NimaiEmailScheduler();
				final NimaiSubscriptionDetails details = (NimaiSubscriptionDetails) this.planRepo
						.getOne(request.getSubcriptionId());
				if (request.getMakerComment() != null) {
					details.setMakerComment(request.getMakerComment().concat("_" + Utility.getUserId()));
				}
				if (request.getCheckerComment() != null) {
					details.setCheckerComment(request.getCheckerComment().concat("_" + Utility.getUserId()));
				}
				details.setPaymentStatus(request.getStatus());

				if (details.getVasAmount() != 0) {

					List< NimaiSubscriptionVas> vasUpdate = this.vasRep
							.getVasListDetailsBySplanId(details.getSubscriptionId(), details.getUserid().getUserid());
					for(NimaiSubscriptionVas  vasDetails2:vasUpdate) {
						vasDetails2.setPaymentSts(request.getStatus());
						if (request.getMakerComment() != null) {
							vasDetails2.setMakerComment(
									request.getMakerComment().concat(" " + "-" + "  " + Utility.getUserId()));
						}
						if (request.getCheckerComment() != null) {
							vasDetails2.setCheckerComment(
									request.getCheckerComment().concat(" " + "-" + "  " + Utility.getUserId()));
						}
						this.vasRep.save(vasDetails2);
					}
					
				}

				if (request.getStatus().equalsIgnoreCase("Rejected")) {

					schedulerData.setUserid(customer2.getUserid());
					schedulerData.setUserName(customer2.getFirstName());
					schedulerData.setEmailId(customer2.getEmailAddress());
					schedulerData.setEvent("Cust_Splan_email_Wire_Rejected");
					schedulerData.setSubscriptionId(details.getSubscriptionId());
					schedulerData.setSubscriptionAmount(String.valueOf(details.getSubscriptionAmount()));
					schedulerData.setSubscriptionName(details.getSubscriptionName());
					schedulerData.setSubscriptionValidity(details.getSubscriptionValidity());
					schedulerData.setRelationshipManager(details.getRelationshipManager());
					schedulerData.setCustomerSupport(details.getCustomerSupport());
					schedulerData.setSubscriptionStartDate(details.getSplanStartDate());
					schedulerData.setSubscriptionEndDate(details.getSplanEndDate());
					schedulerData.setEmailStatus("Pending");
					schedulerData.setInsertedDate(new Date());
					this.schRepo.save(schedulerData);
				}
				if (request.getStatus().equalsIgnoreCase("Approved")
						&& details.getUserid().getPaymentStatus().equalsIgnoreCase("Approved")) {
					logger.debug("Inside subscriptionplan approved status");
					logger.debug("subscriptionplan:" + request.getStatus());
					schedulerData.setUserid(customer2.getUserid());
					schedulerData.setUserName(customer2.getFirstName());
					schedulerData.setEmailId(customer2.getEmailAddress());
					schedulerData.setEvent("Cust_Splan_email_Wire");
					schedulerData.setSubscriptionId(details.getSubscriptionId());
					schedulerData.setSubscriptionAmount(String.valueOf(details.getSubscriptionAmount()));
					schedulerData.setSubscriptionName(details.getSubscriptionName());
					schedulerData.setSubscriptionValidity(details.getSubscriptionValidity());
					schedulerData.setRelationshipManager(details.getRelationshipManager());
					schedulerData.setCustomerSupport(details.getCustomerSupport());
					schedulerData.setSubscriptionStartDate(details.getSplanStartDate());
					schedulerData.setSubscriptionEndDate(details.getSplanEndDate());
					final Calendar cal = Calendar.getInstance();
					final Date today = cal.getTime();
					final int noOfdays = 30;
					final int validityInNumber = Integer.valueOf(details.getSubscriptionValidity());
					final int actualEndDaysOfPLan = validityInNumber * noOfdays - 1;
					logger.debug(String.valueOf(actualEndDaysOfPLan));
					final Calendar calforEndDate = Calendar.getInstance();
					calforEndDate.setTime(today);
//					logger.info(today);
					calforEndDate.add(5, actualEndDaysOfPLan);
					final Date endDate = calforEndDate.getTime();
//					logger.debug(endDate);
					details.setSplanStartDate(today);
					calforEndDate.add(5, actualEndDaysOfPLan);
					details.setSplanEndDate(endDate);
					this.planRepo.save(details);
					schedulerData.setEmailStatus("Pending");
					schedulerData.setInsertedDate(new Date());
					this.schRepo.save(schedulerData);
					logger.debug("details to be save of subscription plan" + details.toString());
				}
				this.planRepo.save(details);

			} else if (request.getVasNUmber() != null) {
				String vasId = request.getVasNUmber();
				String[] vasIdArray = vasId.split("-");
				for (String str : vasIdArray) {
					System.out.println(str);
					logger.debug("VASID:" + Integer.parseInt(str));
					NimaiSubscriptionVas vasDetails = (NimaiSubscriptionVas) this.vasRep.getOne(Integer.parseInt(str));

					final NimaiSubscriptionDetails details = (NimaiSubscriptionDetails) this.planRepo
							.getOne(vasDetails.getsPLanSerialNumber());
					if (!details.getPaymentStatus().equalsIgnoreCase("Approved")) {
						updateSPlanAndVasStatus(request, customer2, details);
					}

					logger.debug("vasDetails:" + vasDetails.toString());
					if (vasDetails.getPaymentApprovedBy() != null
							&& vasDetails.getPaymentApprovedBy().equalsIgnoreCase(request.getUserId())) {
						return (ResponseEntity<?>) new ResponseEntity(new ApiResponse(Boolean.valueOf(false),
								"You dont have the authority for this operation!!!"), HttpStatus.OK);
					}
					if (request.getVasMakerComment() != null) {
						vasDetails.setMakerComment(
								request.getVasMakerComment().concat(" " + "-" + "  " + Utility.getUserId()));
					}
					if (request.getVasCheckerComment() != null) {
						vasDetails.setCheckerComment(
								request.getVasCheckerComment().concat(" " + "-" + "  " + Utility.getUserId()));
					}
					vasDetails.setPaymentApprovalDate(new Date());
					vasDetails.setPaymentSts(request.getStatus());
					vasDetails.setPaymentApprovedBy(Utility.getUserId());
					this.vasRep.save(vasDetails);
					if ((request.getStatus().equalsIgnoreCase("Maker Rejected")
							|| request.getStatus().equalsIgnoreCase("Rejected"))) {
						String planstatus = "ACTIVE";
						NimaiSubscriptionDetails sDetails = planRepo.getplanByUserIDAndSID(vasDetails.getUserId(),
								planstatus, vasDetails.getSubscriptionId());
						Double vasDeductAmt = sDetails.getGrandAmount() - sDetails.getVasAmount();
						DecimalFormat f = new DecimalFormat("##.00");
						System.out.println(f.format(vasDeductAmt));
						sDetails.setGrandAmount(Double.valueOf(f.format(vasDeductAmt)));
						planRepo.save(sDetails);
					}

					NimaiEmailScheduler vaSchData = new NimaiEmailScheduler();
					NimaiMCustomer customer = this.repo.findByUserid(vasDetails.getUserId());
					if (request.getStatus().equalsIgnoreCase("Rejected")) {
						vaSchData.setUserid(vasDetails.getUserId());
						vaSchData.setUserName(customer.getFirstName());
						vaSchData.setDescription1(vasDetails.getPlanName());
						vaSchData.setSubscriptionId(vasDetails.getSubscriptionId());
						vaSchData.setEmailId(customer.getEmailAddress());
						vaSchData.setEmailStatus("Pending");
						vaSchData.setEvent("VAS_PLAN_WIRE_REJECTED");

					}
					if (request.getStatus().equalsIgnoreCase("Approved")) {
						vaSchData.setUserid(vasDetails.getUserId());
						vaSchData.setUserName(customer.getFirstName());
						vaSchData.setEmailId(customer.getEmailAddress());
						vaSchData.setSubscriptionId(vaSchData.getSubscriptionId());
						vaSchData.setSubscriptionName(vasDetails.getPlanName());
						vaSchData.setEmailStatus("Pending");
						vaSchData.setEvent("VAS_PLAN_WIRE_APPROVED");
						vaSchData.setSubscriptionAmount(String.valueOf(vasDetails.getPricing()));
					}
					this.schRepo.save(vaSchData);

				}
			}

//			} else if (request.getVasNUmber() != null && request.getsPLanSerialNumber() != null) {
//				updateSPlanAndVasStatus(request, customer2);
//			}
			return (ResponseEntity<?>) new ResponseEntity(
					new ApiResponse(Boolean.valueOf(true), "Payment status updated successfully... "), HttpStatus.OK);

		} catch (Exception e) {
			System.out.println("Exception in Payment status update :" + e.getMessage());
			e.printStackTrace();
			return (ResponseEntity<?>) new ResponseEntity(
					new ApiResponse(Boolean.valueOf(true), "Error due to some technical issue"),
					HttpStatus.EXPECTATION_FAILED);
		}
	}

	private void updateSPlanAndVasStatus(VasUpdateRequestBody request, NimaiMCustomer customer2,
			NimaiSubscriptionDetails details) {
		// TODO Auto-generated method stub

		customer2.setPaymentApprovedBy(Utility.getUserId());
		customer2.setPaymentStatus(request.getStatus());
		customer2.setPaymentDate(new Date());
		this.repo.save(customer2);
		final NimaiEmailScheduler schedulerData = new NimaiEmailScheduler();
//		final NimaiSubscriptionDetails details = (NimaiSubscriptionDetails) this.planRepo
//				.getOne(request.getSubcriptionId());
		if (request.getMakerComment() != null) {
			details.setMakerComment(request.getMakerComment().concat("_" + Utility.getUserId()));
		}
		if (request.getCheckerComment() != null) {
			details.setCheckerComment(request.getCheckerComment().concat("_" + Utility.getUserId()));
		}
		details.setPaymentStatus(request.getStatus());
		// updateVasDetails(request.getVasNUmber(), request);
		if (request.getStatus().equalsIgnoreCase("Rejected")) {
			schedulerData.setUserid(customer2.getUserid());
			schedulerData.setUserName(customer2.getFirstName());
			schedulerData.setEmailId(customer2.getEmailAddress());
			schedulerData.setEvent("Cust_Splan_email_Wire_Rejected");
			schedulerData.setSubscriptionId(details.getSubscriptionId());
			schedulerData.setSubscriptionAmount(String.valueOf(details.getSubscriptionAmount()));
			schedulerData.setSubscriptionName(details.getSubscriptionName());
			schedulerData.setSubscriptionValidity(details.getSubscriptionValidity());
			schedulerData.setRelationshipManager(details.getRelationshipManager());
			schedulerData.setCustomerSupport(details.getCustomerSupport());
			schedulerData.setSubscriptionStartDate(details.getSplanStartDate());
			schedulerData.setSubscriptionEndDate(details.getSplanEndDate());
			schedulerData.setEmailStatus("Pending");
			schedulerData.setInsertedDate(new Date());
			this.schRepo.save(schedulerData);
		}
		if (request.getStatus().equalsIgnoreCase("Approved")
				&& details.getUserid().getPaymentStatus().equalsIgnoreCase("Approved")) {
			logger.debug("Inside subscriptionplan approved status");
			logger.debug("subscriptionplan:" + request.getStatus());
			schedulerData.setUserid(customer2.getUserid());
			schedulerData.setUserName(customer2.getFirstName());
			schedulerData.setEmailId(customer2.getEmailAddress());
			schedulerData.setEvent("Cust_Splan_email_Wire");
			schedulerData.setSubscriptionId(details.getSubscriptionId());
			schedulerData.setSubscriptionAmount(String.valueOf(details.getSubscriptionAmount()));
			schedulerData.setSubscriptionName(details.getSubscriptionName());
			schedulerData.setSubscriptionValidity(details.getSubscriptionValidity());
			schedulerData.setRelationshipManager(details.getRelationshipManager());
			schedulerData.setCustomerSupport(details.getCustomerSupport());
			schedulerData.setSubscriptionStartDate(details.getSplanStartDate());
			schedulerData.setSubscriptionEndDate(details.getSplanEndDate());
			final Calendar cal = Calendar.getInstance();
			final Date today = cal.getTime();
			final int noOfdays = 30;
			final int validityInNumber = Integer.valueOf(details.getSubscriptionValidity());
			final int actualEndDaysOfPLan = validityInNumber * noOfdays - 1;
			logger.debug(String.valueOf(actualEndDaysOfPLan));
			final Calendar calforEndDate = Calendar.getInstance();
			calforEndDate.setTime(today);
//			logger.info(today);
			calforEndDate.add(5, actualEndDaysOfPLan);
			final Date endDate = calforEndDate.getTime();
//			logger.debug(endDate);
			details.setSplanStartDate(today);
			calforEndDate.add(5, actualEndDaysOfPLan);
			details.setSplanEndDate(endDate);
			this.planRepo.save(details);
			schedulerData.setEmailStatus("Pending");
			schedulerData.setInsertedDate(new Date());
			this.schRepo.save(schedulerData);
			logger.debug("details to be save of subscription plan" + details.toString());
		}
		this.planRepo.save(details);

	}

	private void updateVasDetails(String vasNUmber, VasUpdateRequestBody request) {
		// TODO Auto-generated method stub
		String vasId = request.getVasNUmber();
		String[] vasIdArray = vasId.split("-");
		for (String str : vasIdArray) {
			System.out.println(str);
			logger.debug("VASID:" + Integer.parseInt(str));
			NimaiSubscriptionVas vasDetails = (NimaiSubscriptionVas) this.vasRep.getOne(Integer.parseInt(str));
			logger.debug("vasDetails:" + vasDetails.toString());

			if (request.getVasMakerComment() != null) {
				vasDetails.setMakerComment(request.getVasMakerComment().concat(" " + "-" + "  " + Utility.getUserId()));
			}
			if (request.getVasCheckerComment() != null) {
				vasDetails.setCheckerComment(
						request.getVasCheckerComment().concat(" " + "-" + "  " + Utility.getUserId()));
			}
			vasDetails.setPaymentApprovalDate(new Date());
			vasDetails.setPaymentSts(request.getStatus());
			vasDetails.setPaymentApprovedBy(Utility.getUserId());
			this.vasRep.save(vasDetails);
			if ((request.getStatus().equalsIgnoreCase("Maker Rejected")
					|| request.getStatus().equalsIgnoreCase("Rejected"))) {
				String planstatus = "ACTIVE";
				NimaiSubscriptionDetails sDetails = planRepo.getplanByUserIDAndSID(vasDetails.getUserId(), planstatus,
						vasDetails.getSubscriptionId());
				Double vasDeductAmt = sDetails.getGrandAmount() - sDetails.getVasAmount();
				DecimalFormat f = new DecimalFormat("##.00");
				System.out.println(f.format(vasDeductAmt));
				sDetails.setGrandAmount(Double.valueOf(f.format(vasDeductAmt)));
				planRepo.save(sDetails);
			}

			NimaiEmailScheduler vaSchData = new NimaiEmailScheduler();
			NimaiMCustomer customer = this.repo.findByUserid(vasDetails.getUserId());
			if (request.getStatus().equalsIgnoreCase("Rejected")) {
				vaSchData.setUserid(vasDetails.getUserId());
				vaSchData.setUserName(customer.getFirstName());
				vaSchData.setDescription1(vasDetails.getPlanName());
				vaSchData.setSubscriptionId(vasDetails.getSubscriptionId());
				vaSchData.setEmailId(customer.getEmailAddress());
				vaSchData.setEmailStatus("Pending");
				vaSchData.setEvent("VAS_PLAN_WIRE_REJECTED");

			}
			if (request.getStatus().equalsIgnoreCase("Approved")) {
				vaSchData.setUserid(vasDetails.getUserId());
				vaSchData.setUserName(customer.getFirstName());
				vaSchData.setEmailId(customer.getEmailAddress());
				vaSchData.setSubscriptionId(vaSchData.getSubscriptionId());
				vaSchData.setSubscriptionName(vasDetails.getPlanName());
				vaSchData.setEmailStatus("Pending");
				vaSchData.setEvent("VAS_PLAN_WIRE_APPROVED");
				vaSchData.setSubscriptionAmount(String.valueOf(vasDetails.getPricing()));
			}
			this.schRepo.save(vaSchData);
		}

	}

	@SuppressWarnings("unchecked")
	public PagedResponse<?> getWireTransferList(final SearchRequest request) {

		final Pageable pageable = (Pageable) PageRequest.of(request.getPage(), request.getSize(),
				request.getDirection().equalsIgnoreCase("desc") ? Sort.by(new String[] { "PAYMENT_DATE" }).descending()
						: Sort.by(new String[] { "PAYMENT_DATE" }).ascending());
		final String countryNames = Utility.getUserCountry();
		Page<NimaiMCustomer> paymentDetails;
		List<NimaiMCustomer> cuList;
		int pageSize = request.getSize();
		if (request.getSubscriberType() != null || request.getBankType() != null) {
			if (request.getCountry() == null || request.getCountry().isEmpty()) {
				request.setCountryNames(countryNames);

				if (request.getCountryNames().equalsIgnoreCase("all")) {
					if (request.getSubscriberType().equalsIgnoreCase("CUSTOMER") && request.getBankType() == null)
						paymentDetails = (Page<NimaiMCustomer>) this.repo
								.findAllMakerApprovedPaymentDetailsSubsType(request.getSubscriberType(), pageable);
					else
						paymentDetails = (Page<NimaiMCustomer>) this.repo
								.findAllMakerApprovedPaymentDetailsSubsTypeBankType(request.getSubscriberType(),
										request.getBankType(), pageable);
				} else {
					final List<String> value = Stream.of(request.getCountryNames().split(",", -1))
							.collect(Collectors.toList());
					if (request.getSubscriberType().equalsIgnoreCase("CUSTOMER") && request.getBankType() == null)
						paymentDetails = (Page<NimaiMCustomer>) this.repo
								.findMakerApprovedPaymentDetailsSubsType(request.getSubscriberType(), value, pageable);
					else
						paymentDetails = (Page<NimaiMCustomer>) this.repo
								.findMakerApprovedPaymentDetailsSubsTypeBankType(request.getSubscriberType(),
										request.getBankType(), value, pageable);
				}

			} else {
				if (request.getCountry().equalsIgnoreCase("all")) {
					request.setCountryNames(countryNames);

					if (request.getCountryNames().equalsIgnoreCase("all")) {

						paymentDetails = (Page<NimaiMCustomer>) this.repo
								.findAllMakerApprovedPaymentDetailsSubsTypeBankType(request.getSubscriberType(),
										request.getBankType(), pageable);
					} else {
						final List<String> value = Stream.of(request.getCountryNames().split(",", -1))
								.collect(Collectors.toList());

						paymentDetails = (Page<NimaiMCustomer>) this.repo
								.findMakerApprovedPaymentDetailsSubsTypeBankType(request.getSubscriberType(),
										request.getBankType(), value, pageable);

					}

				} else {
					// cuList=repo.getListByCountryname(request.getCountry(),pageSize);
					paymentDetails = (Page<NimaiMCustomer>) this.repo.getListByCountryname(request.getCountry(),
							pageable);
					// paymentDetails = new PageImpl<>(cuList,pageable,cuList.size());
				}
				request.setCountryNames(request.getCountry());
			}
		} else {
			if (request.getCountry() == null || request.getCountry().isEmpty()) {
				request.setCountryNames(countryNames);

				if (request.getCountryNames().equalsIgnoreCase("all")) {

					paymentDetails = (Page<NimaiMCustomer>) this.repo.findAllMakerApprovedPaymentDetails(pageable);
				} else {
					final List<String> value = Stream.of(request.getCountryNames().split(",", -1))
							.collect(Collectors.toList());

					paymentDetails = (Page<NimaiMCustomer>) this.repo.findMakerApprovedPaymentDetails(value, pageable);
				}

			} else {
				if (request.getCountry().equalsIgnoreCase("all")) {
					request.setCountryNames(countryNames);

					if (request.getCountryNames().equalsIgnoreCase("all")) {

						paymentDetails = (Page<NimaiMCustomer>) this.repo.findAllMakerApprovedPaymentDetails(pageable);
					} else {
						final List<String> value = Stream.of(request.getCountryNames().split(",", -1))
								.collect(Collectors.toList());

						paymentDetails = (Page<NimaiMCustomer>) this.repo.findMakerApprovedPaymentDetails(value,
								pageable);

					}

				} else {
					// cuList=repo.getListByCountryname(request.getCountry(),pageSize);
					paymentDetails = (Page<NimaiMCustomer>) this.repo.getListByCountryname(request.getCountry(),
							pageable);
					// paymentDetails = new PageImpl<>(cuList,pageable,cuList.size());
				}
				request.setCountryNames(request.getCountry());
			}
		}
//		if (countryNames == null || !countryNames.equalsIgnoreCase("all") || request.getCountry() != null) {
//			if (countryNames != null && !countryNames.equalsIgnoreCase("all") && request.getCountry() == null) {
//				request.setCountryNames(countryNames);
//				System.out.println("==============countrynames 1" + countryNames);
//			} else if (countryNames != null || request.getCountry() == null) {
//			}
//		}
		System.out.println("==============countrynames 2" + countryNames);
		System.out.println("==============countrynames 2" + request.getCountryNames());

		System.out.println("payment detials size" + paymentDetails.getSize());
		// final BankDetailsResponse response;
		// final NimaiSubscriptionDetails sub;
		// NimaiSubscriptionVas vasDetails;

		final List<BankDetailsResponse> responses = (List<BankDetailsResponse>) paymentDetails.map(cust -> {
			BankDetailsResponse response = new BankDetailsResponse();

			NimaiSubscriptionDetails sub = (NimaiSubscriptionDetails) cust.getNimaiSubscriptionDetailsList().stream()
					.filter(e -> e.getStatus().equalsIgnoreCase("Active")).findFirst().orElse(null);
			if (sub != null) {
				response.setUserid(cust.getUserid());
				response.setFirstName(cust.getFirstName());
				response.setLastName(cust.getLastName());
				response.setEmailAddress(cust.getEmailAddress());
				response.setMobileNumber(cust.getMobileNumber());
//			response.setPlanOfPayments((cust.getNimaiSubscriptionDetailsList().size() != 0)
//					? this.collectPlanName(cust.getNimaiSubscriptionDetailsList())
//					: "No Active Plan");
				response.setRegisteredCountry(cust.getRegisteredCountry());
				response.setStatus(cust.getPaymentStatus());
				response.setPaymentApprovedBy(cust.getPaymentApprovedBy());
//			NimaiSubscriptionDetails sub = (NimaiSubscriptionDetails) cust.getNimaiSubscriptionDetailsList().stream()
//					.filter(e -> e.getStatus().equalsIgnoreCase("Active")).findFirst().orElse(null);
//			if (sub != null) {
				response.setPlanOfPayments((cust.getNimaiSubscriptionDetailsList().size() != 0)
						? this.collectPlanName(cust.getNimaiSubscriptionDetailsList())
						: "No Active Plan");
				response.setPlanId((int) sub.getSplSerialNumber());
				response.setMakerComment(sub.getMakerComment());
				response.setCheckerComment(sub.getCheckerComment());
				response.setPaymentMode(sub.getPaymentMode());
				if (sub.getVasAmount() != 0) {
					NimaiSubscriptionVas vasDetails = this.vasRep.getVasDetailsBySplanId(sub.getSubscriptionId(),
							sub.getUserid().getUserid());
					if (vasDetails == null) {
						response.setVasMakerComment((String) null);
						response.setVasCheckerComment((String) null);
						response.setVasStatus((String) null);
					} else {
						response.setVasMakerComment(sub.getMakerComment());
						response.setVasCheckerComment(sub.getCheckerComment());
						response.setVasStatus(vasDetails.getPaymentSts());
					}
				}
			}
			return response;
		}).getContent();
		return (PagedResponse<?>) new PagedResponse((List) responses, paymentDetails.getNumber(),
				paymentDetails.getSize(), paymentDetails.getTotalElements(), paymentDetails.getTotalPages(),
				paymentDetails.isLast());
	}

	public KycBDetailResponse getMakerApprovedKycByKycId(final SearchRequest request) {
		final NimaiFKyc kyc = (NimaiFKyc) this.kycRepo.getOne(request.getKycId());
		final KycBDetailResponse response = new KycBDetailResponse();
		response.setKycid(kyc.getId());
		response.setDocName(kyc.getDocumentName());
		response.setCountry(kyc.getCountry());
		response.setKycType(kyc.getKycType());
		response.setDocType(kyc.getDocumentType());
		response.setReason(kyc.getReason());
		response.setKycStatus(kyc.getKycStatus());
		response.setEncodedFileContent(
				kyc.getEncodedFileContent().substring(kyc.getEncodedFileContent().indexOf("|") + 1));
		response.setUserid(kyc.getUserid().getUserid());
		response.setApproverName(kyc.getApprovedMaker());
		return response;
	}

	@SuppressWarnings("unchecked")
	public ResponseEntity<?> checkDuplicateCouponCode(final CouponBean request) {
		final NimaiMDiscount discountDetails = this.discRepo.getDetailsByCoupon(request.getCouponCode(),
				request.getCountryName(), "ACTIVE", request.getCustomerType());
		if (discountDetails != null) {
			return (ResponseEntity<?>) new ResponseEntity(
					new ApiResponse(Boolean.valueOf(true), "Coupon already exist"), HttpStatus.OK);
		}
		return (ResponseEntity<?>) new ResponseEntity(new ApiResponse(Boolean.valueOf(true), "Success"), HttpStatus.OK);
	}

	public ResponseEntity<?> checkDuplicateSPLan(final SPlanBean request) {
		return null;
	}

	@SuppressWarnings("unchecked")
	public PagedResponse<?> getVasWireTransferList(final SearchRequest request) {
		final Pageable pageable = (Pageable) PageRequest.of(request.getPage(), request.getSize(),
				request.getDirection().equalsIgnoreCase("desc")
						? Sort.by(new String[] { request.getSortBy() }).descending()
						: Sort.by(new String[] { request.getSortBy() }).ascending());
		final String countryNames = Utility.getUserCountry();

		final Page<NimaiSubscriptionVas> vasDetails;
		if (request.getCountry() == null || request.getCountry().isEmpty()) {
			request.setCountryNames(countryNames);

			if (request.getCountryNames().equalsIgnoreCase("all")) {
				vasDetails = (Page<NimaiSubscriptionVas>) this.vasRep.getAllVasListByCountryname(countryNames,
						pageable);

			} else {
				final List<String> value = Stream.of(request.getCountryNames().split(",", -1))
						.collect(Collectors.toList());
				vasDetails = (Page<NimaiSubscriptionVas>) this.vasRep.findMakerApprovedVasDetails(value, pageable);
			}
		} else {
			if (request.getCountry().equalsIgnoreCase("all")) {
				request.setCountryNames(countryNames);

				if (request.getCountryNames().equalsIgnoreCase("all")) {
					vasDetails = (Page<NimaiSubscriptionVas>) this.vasRep.getAllVasListByCountryname(countryNames,
							pageable);

				} else {
					final List<String> value = Stream.of(request.getCountryNames().split(",", -1))
							.collect(Collectors.toList());
					vasDetails = (Page<NimaiSubscriptionVas>) this.vasRep.findMakerApprovedVasDetails(value, pageable);
				}

			} else {
				vasDetails = this.vasRep.getVasListByCountryname(request.getCountry(), pageable);
			}
			request.setCountryNames(request.getCountry());
		}

//		if (countryNames == null || !countryNames.equalsIgnoreCase("all") || request.getCountry() != null) {
//			if (countryNames != null && !countryNames.equalsIgnoreCase("all") && request.getCountry() == null) {
//				request.setCountryNames(countryNames);
//				System.out.println("======================countryNames=======" + countryNames);
//			} else if (countryNames != null || request.getCountry() == null) {
//			}
//		}
		System.out.println("======================countryNames=======" + countryNames);

		for (final NimaiSubscriptionVas vas : vasDetails) {
			if (vas.getPricing() == null) {
				vas.setPricing(Float.valueOf(0.0f));
			}
		}
		
		final List<BankDetailsResponse> varesponses = (List<BankDetailsResponse>) vasDetails.map(vascust -> {
			BankDetailsResponse response = new BankDetailsResponse();
			List<NimaiSubscriptionVas> vasList = (List<NimaiSubscriptionVas>) this.vasRep
					.findVasByUserId(vascust.getUserId());
List<BankDetailsResponse> responseList=new ArrayList<>();
NimaiSubscriptionVas vasSingle=vasRep.getVasDetailsBySplanId(vascust.getSubscriptionId(), vascust.getUserId());

//for (NimaiSubscriptionVas vas : vasList) {

			String vasIdResponse = "";
			List<String> vasIdString=new ArrayList<String>();
			for (NimaiSubscriptionVas vasNew : vasList) {
vasIdString.add(String.valueOf(vasNew.getId()).concat("-"));
				//vasIdResponse = (String.valueOf(vas.getId(-).concat("-"));

			}
			
			String[] array = new String[vasIdString.size()];
			//String[] strArr = new String[] {"1","2","3"};
			vasIdString.toArray(array);
			StringBuilder sb=new StringBuilder();
			for(String str:vasIdString.toArray(array)) {
				sb.append(str);
				sb.substring(0, sb.length()-1);
			}
			StringBuilder vasNumberString=sb;
			
				response.setUserid(vascust.getUserId());
				System.out.println("user id for nullpointer exception"+vascust.getUserId());
				try {
					NimaiMCustomer customerDetails = this.repo.findByUserid(vascust.getUserId());
					if(customerDetails==null) {
						response.setFirstName("");
						response.setLastName("");
						response.setEmailAddress("");
						response.setMobileNumber("");
					}else {
						response.setFirstName(customerDetails.getFirstName());
						response.setLastName(customerDetails.getLastName());
						response.setEmailAddress(customerDetails.getEmailAddress());
						response.setMobileNumber(customerDetails.getMobileNumber());
					}
					
				}catch(Exception e) {
					e.printStackTrace();
				}
			
				
			
				response.setVasMakerComment(vasSingle.getMakerComment());
				response.setVasCheckerComment(vasSingle.getCheckerComment());
				response.setPlanOfPayments((vasList.size() != 0) ? this.collectVasPlanName(vasList) : "No Active Plan");
				response.setRegisteredCountry(vasSingle.getCountryName());
				response.setPlanId((int) vasSingle.getId());
				response.setStatus(vasSingle.getPaymentSts());
				response.setPaymentApprovedBy(vasSingle.getPaymentApprovedBy());
				response.setVasNUmber(vasNumberString.toString());
				responseList.add(response);
			
			
			//}

			
			
			return response;
		}).getContent();
		return (PagedResponse<?>) new PagedResponse((List) varesponses, vasDetails.getNumber(), vasDetails.getSize(),
				vasDetails.getTotalElements(), vasDetails.getTotalPages(), vasDetails.isLast());
	}

	static {
		logger = LoggerFactory.getLogger((Class) BankServiceImpl.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResponseEntity<?> transactionStatusUpdate(TransactionRequestBody request) {
		// TODO Auto-generated method stub
		NimaiLCMaster lcDetais = lcMasterrepo.getOne(request.getTransactionId());
		if (lcDetais.getTransactionApprovedBy() != null
				&& lcDetais.getTransactionApprovedBy().equalsIgnoreCase(Utility.getUserId())) {
			return (ResponseEntity<?>) new ResponseEntity(
					new ApiResponse(Boolean.valueOf(false), "You dont have the authority for this operation!!!"),
					HttpStatus.OK);
		}

		lcDetais.setTrApprovaldate(new Date());
		if (request.getStatus().equalsIgnoreCase("Approved")) {
			System.out.println("INside approved condition");
			lcDetais.setTrApprovaldate(new Date());
			lcDetais.setTransactionApprovedBy(Utility.getUserId());
			lcDetais.setTransactionStatus("Active");

			getAlleligibleBAnksEmail(lcDetais.getUserId(), request.getTransactionId(), 0, "LC_UPLOAD_ALERT_ToBanks",
					"LC_UPLOAD(DATA)");

		} else {
			lcDetais.setTransactionRejected(Utility.getUserId());
			lcDetais.setTrRejectedDate(new Date());
			lcDetais.setTransactionStatus(request.getStatus());
			try {
				NimaiSubscriptionDetails details = sPlanRepo.getplanByUserID(lcDetais.getUserId(), "ACTIVE");
				int count = (details.getLcUtilizedCount() - 1);
				details.setLcUtilizedCount(count);
				sPlanRepo.save(details);
			} catch (Exception e) {
				e.printStackTrace();
				return (ResponseEntity<?>) new ResponseEntity(
						new ApiResponse(Boolean.valueOf(true), "Error while updating Transaction status... "),
						HttpStatus.OK);

			}

		}

		if (request.getMakerComment() != null) {
			lcDetais.setMakerComment(request.getMakerComment().concat("_" + Utility.getUserId()));
			lcDetais.setStatusReason(request.getMakerComment());
		}
		if (request.getCheckerComment() != null) {
			lcDetais.setCheckerComment(request.getCheckerComment().concat("_" + Utility.getUserId()));
			lcDetais.setStatusReason(request.getCheckerComment());
		}
//		lcDetais.setTransactionStatus(request.getStatus());

		lcMasterrepo.save(lcDetais);

		return (ResponseEntity<?>) new ResponseEntity(
				new ApiResponse(Boolean.valueOf(true), "Transaction status updated successfully... "), HttpStatus.OK);

	}

	@SuppressWarnings("rawtypes")
	@Override
	public ResponseEntity<?> makerTransactionStatusUpdate(TransactionRequestBody request) {
		// TODO Auto-generated method stub
		NimaiLCMaster lcDetais = lcMasterrepo.getOne(request.getTransactionId());

		lcDetais.setTransactionStatus(request.getStatus());
		lcDetais.setTrApprovaldate(new Date());
		if (request.getStatus().equalsIgnoreCase("Maker Approved")) {
			lcDetais.setTrApprovaldate(new Date());
			lcDetais.setTransactionApprovedBy(Utility.getUserId());
		} else {
			lcDetais.setTransactionRejected(Utility.getUserId());
			lcDetais.setTrRejectedDate(new Date());
			try {
				NimaiSubscriptionDetails details = sPlanRepo.getplanByUserID(lcDetais.getUserId(), "ACTIVE");
				int count = (details.getLcUtilizedCount() - 1);
				details.setLcUtilizedCount(count);
				sPlanRepo.save(details);
			} catch (Exception e) {
				e.printStackTrace();
				return (ResponseEntity<?>) new ResponseEntity(
						new ApiResponse(Boolean.valueOf(true), "Error while updating Transaction status... "),
						HttpStatus.OK);

			}

		}

		if (request.getMakerComment() != null) {
			lcDetais.setMakerComment(request.getMakerComment().concat("_" + Utility.getUserId()));
			lcDetais.setStatusReason(request.getMakerComment());
		}
		if (request.getCheckerComment() != null) {
			lcDetais.setCheckerComment(request.getCheckerComment().concat("_" + Utility.getUserId()));
			lcDetais.setStatusReason(request.getCheckerComment());
		}
		lcDetais.setTransactionStatus(request.getStatus());

		lcMasterrepo.save(lcDetais);

		return (ResponseEntity<?>) new ResponseEntity(
				new ApiResponse(Boolean.valueOf(true), "Transaction status updated successfully... "), HttpStatus.OK);

	}

	public void getAlleligibleBAnksEmail(String userId, String transactionId, int quoteId, String bankEmailEvent,
			String custEmailEvent) {

		System.out.println("======== Getting eligible bank ========");
		EntityManager entityManager = em.createEntityManager();
		try {

			StoredProcedureQuery getBAnksEmail = entityManager.createStoredProcedureQuery("get_eligible_banks",
					NimaiMCustomer.class);
			getBAnksEmail.registerStoredProcedureParameter("inp_customer_userID", String.class, ParameterMode.IN);
			getBAnksEmail.registerStoredProcedureParameter("inp_transaction_ID", String.class, ParameterMode.IN);

			getBAnksEmail.setParameter("inp_customer_userID", userId);
			getBAnksEmail.setParameter("inp_transaction_ID", transactionId);
			getBAnksEmail.execute();
			// ModelMapperUtil modelMapper = new ModelMapperUtil();
			List<NimaiMCustomer> nimaiCust = getBAnksEmail.getResultList();

			// EligibleEmailBeanResponse responseBean = new EligibleEmailBeanResponse();
			// String custEmailId="";
			List<EligibleEmailList> emailId = nimaiCust.stream().map(obj -> {
				EligibleEmailList data = new EligibleEmailList();
				NimaiEmailSchedulerAlertToBanks schedulerEntity = new NimaiEmailSchedulerAlertToBanks();
				Calendar cal = Calendar.getInstance();
				System.out.println("=========Bank list to send emil" + nimaiCust.toString());
				Date insertedDate = cal.getTime();
				schedulerEntity.setInsertedDate(insertedDate);
				schedulerEntity.setCustomerid(userId);
				System.out.println("Userid:" + userId);
				schedulerEntity.setTransactionid(transactionId);
				schedulerEntity.setEmailEvent(bankEmailEvent);
				/* while updating set event as */
				// schedulerEntity.setEmailEvent("LC_UPDATE_ALERT_ToBanks");
				schedulerEntity.setBanksEmailID(obj.getEmailAddress());
				schedulerEntity.setBankUserid(obj.getUserid());
				schedulerEntity.setBankUserName(obj.getFirstName());
				schedulerEntity.setEmailFlag("Pending");
				userDao.save(schedulerEntity);
				data.setEmailList(obj.getEmailAddress());
				return data;
			}).collect(Collectors.toList());

			if (nimaiCust.isEmpty()) {
				System.out.println("No Banks Eligible");

			}

			Calendar cal = Calendar.getInstance();
			Date insertedDate = cal.getTime();
			NimaiEmailSchedulerAlertToBanks schedulerEntityCust = new NimaiEmailSchedulerAlertToBanks();
			NimaiLCMaster passcodeDetails = lcMasterrepo.findSpecificTransactionById(transactionId);

			NimaiMCustomer custDetails = lcMasterrepo.getCustomerDetais(passcodeDetails.getBranchUserEmail());
			// if branch userEmail consist parent user email or passcode userEmail
			if (custDetails == null) {
				schedulerEntityCust.setPasscodeuserEmail(passcodeDetails.getBranchUserEmail());

			}
			String custUserName = lcMasterrepo.getCustomerName(userId);
			String custEmailId = lcMasterrepo.getCustomerEmailId(userId);
			schedulerEntityCust.setInsertedDate(insertedDate);
			schedulerEntityCust.setQuotationId(quoteId);
			schedulerEntityCust.setCustomerid(userId);
			schedulerEntityCust.setCustomerUserName(custUserName == null ? "" : custUserName);
			schedulerEntityCust.setCustomerEmail(custEmailId == null ? "" : custEmailId);
			schedulerEntityCust.setTransactionid(transactionId);
			schedulerEntityCust.setEmailEvent(custEmailEvent);
			if (nimaiCust.isEmpty()) {
				System.out.println("No Banks Eligible");
				schedulerEntityCust.setTransactionEmailStatusToBanks("Pending");
			}

			schedulerEntityCust.setEmailFlag("Pending");
			userDao.save(schedulerEntityCust);

		} catch (Exception e) {
			System.out.println("" + e.getMessage());
		} finally {
			entityManager.close();

		}

	}

	@Override
	public ResponseEntity<?> kycFiledSave(KycFiledBean data) {
		// TODO Auto-generated method stub
		NimaiMKycfileds fields = new NimaiMKycfileds();
		final Calendar cal2 = Calendar.getInstance();
		final Date today2 = cal2.getTime();
		String response;
		System.out.println("Field ID: " + data.getId());
		if (data.getId() != 0) {

			fields = fieldRepo.getOne(data.getId());
			fields.setModifiedDat(today2);
			fields.setModifiedBy(Utility.getUserId());
			response = "fieldData  updated successfully... ";

		} else {
			fields.setInsertedDate(today2);
			fields.setInsertedBy(Utility.getUserId());
			response = "fieldData  save successfully... ";

		}
		fields.setCustTurnover(data.getCustTurnover());
		fields.setImportVolume(data.getImportVolume());
		fields.setExportVolume(data.getExportVolume());
		fields.setYearlyLCVolume(data.getYearlyLCVolume());
		fields.setUsedLCIssuance(data.getUsedLCIssuance());
		fields.setUserId(data.getUserId());

		fieldRepo.save(fields);

		return (ResponseEntity<?>) new ResponseEntity(new ApiResponse(Boolean.valueOf(true), response, fields.getId()),
				HttpStatus.OK);

	}

	@Override
	public ResponseEntity<?> kycViewFieldData(KycFiledBean data) {
		// TODO Auto-generated method stub
		GenericResponse response = new GenericResponse();
		KycFiledBean dataResponse = new KycFiledBean();

		NimaiMKycfileds fields = fieldRepo.getFiledData(data.getUserId());
		if (fields == null) {
			response.setMessage("Field Data not available");
		}
		data.setCustTurnover(fields.getCustTurnover());
		data.setImportVolume(fields.getImportVolume());
		data.setExportVolume(fields.getExportVolume());
		data.setYearlyLCVolume(fields.getYearlyLCVolume());
		data.setUsedLCIssuance(fields.getUsedLCIssuance());
		data.setUserId(fields.getUserId());
		data.setInsertedBy(fields.getInsertedBy());
		data.setModifiedby(fields.getModifiedBy());
		data.setId(fields.getId());

		response.setData(fields);

		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	public static void main(String[] args) {
		String number = "23_24_26_";
		String[] arr = number.split("_");
		for (String str : arr) {
			System.out.println(str);
		}
		System.out.println(arr.toString());
		// List<String> parts = Arrays.asList(number.split(","));

	}

	@Override
	public List<PreferredBankListResponse> getBankList() {
		// TODO Auto-generated method stub
		final List<NimaiMCustomer> cust = this.repo.findBankDetails();
		
	if (cust.size() != 0 && cust != null)
		return cust.stream().map(
				res -> new PreferredBankListResponse(res.getUserid(), res.getFirstName(), res.getLastName(),res.getEmailAddress(), res.getBankName()))
				.collect(Collectors.toList());
	else
		return null;
	}

	@Override
	public ResponseEntity<?> createOrUpdatePreferredBank(@Valid PreferredBankRequest request) {
		// TODO Auto-generated method stub
		preferBankRepo.deletePreferredBank(request.getCustUserId());
		for (int i = 0; i < request.getBanks().length; i++) {
			PreferredBankModel preferBank = new PreferredBankModel();
			preferBank.setCustUserId(request.getCustUserId());
			preferBank.setBankUserId(request.getBanks()[i]);
			preferBankRepo.save(preferBank);
		}
		
		return new ResponseEntity<>(new ApiResponse(true, "Preferred Bank Added Successfully..."),
				HttpStatus.CREATED);
	}

	@Override
	public List<PreferredBankListResponse> viewPreferredBanks(@Valid PreferredBankRequest request) {
		// TODO Auto-generated method stub
		List<PreferredBankListResponse> prBankList = new ArrayList<>();
		final List<PreferredBankModel> cust = preferBankRepo.findBankDetails(request.getCustUserId());
		for(PreferredBankModel pm:cust)
		{
			String bankNm = this.repo.findBankName(pm.getBankUserId());
			PreferredBankListResponse pr=new PreferredBankListResponse();
			pr.setUserid(pm.getBankUserId());
			pr.setCustUserid(pm.getCustUserId());
			pr.setBankName(bankNm);
			prBankList.add(pr);
		}
		return prBankList;
		/*if (cust.size() != 0 && cust != null)
			return cust.stream().map(
					res -> new PreferredBankListResponse(res.getBankUserId(),res.getCustUserId(),""))
					.collect(Collectors.toList());
		else
			return null;*/
	}

	@Override
	public ResponseEntity<?> createOrUpdateBankRating(@Valid BankRatingRequest request) {
		// TODO Auto-generated method stub
		BankRatingModel br=bankratingRepo.getDetailsByBankUserId(request.getBankUserid());
		if(br==null)
		{
			BankRatingModel brNew=new BankRatingModel();
			brNew.setBankUserid(request.getBankUserid());
			brNew.setRating(request.getRating());
			bankratingRepo.save(brNew);
		}
		else
		{
			BankRatingModel brOld=bankratingRepo.getOne(br.getId());
			brOld.setBankUserid(request.getBankUserid());
			brOld.setRating(request.getRating());
			bankratingRepo.save(brOld);
		}
		return new ResponseEntity<>(new ApiResponse(true, "Bank Rated Successfully..."),
				HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<?> viewBankRatingDetails(@Valid BankRatingRequest request) {
		// TODO Auto-generated method stub
		
		GenericResponse response = new GenericResponse();
		BankRatingModel brm=bankratingRepo.getDetailsByBankUserId(request.getBankUserid());
		response.setData(brm);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
