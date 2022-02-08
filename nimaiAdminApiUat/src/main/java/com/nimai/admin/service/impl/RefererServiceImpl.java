package com.nimai.admin.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;

import java.util.List;

import java.util.stream.Collectors;
import java.util.stream.Stream;

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
import com.nimai.admin.model.NimaiMRefer;
import com.nimai.admin.model.NimaiSubscriptionDetails;
import com.nimai.admin.payload.CustomerResponse;
import com.nimai.admin.payload.PagedResponse;
import com.nimai.admin.payload.RefBean;
import com.nimai.admin.payload.ReferrerBean;
import com.nimai.admin.payload.SearchRequest;
import com.nimai.admin.repository.CustomerRepository;
import com.nimai.admin.repository.EmployeeRepository;
import com.nimai.admin.repository.ReferrerRepository;
import com.nimai.admin.repository.SubscriptionDetailsRepository;
import com.nimai.admin.repository.nimaiSystemConfigRepository;
import com.nimai.admin.service.RefererService;
import com.nimai.admin.specification.CustomerSearchSpecification;
import com.nimai.admin.specification.ReferrerSpecification;
import com.nimai.admin.util.Utility;

@Service
public class RefererServiceImpl implements RefererService {
	@Autowired
	ReferrerRepository referRepository;

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	CustomerSearchSpecification customerSearchSpecification;

	@Autowired
	ReferrerSpecification referSpecification;

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	SubscriptionDetailsRepository subRepo;

	@Autowired
	nimaiSystemConfigRepository systemConfig;

	@Override
	public PagedResponse<?> getRefDetails(SearchRequest request) {
		request.setSubscriberType("REFERRER");
		// Pageable pageable = PageRequest.of(request.getPage(), request.getSize(),
		// request.getDirection().equalsIgnoreCase("desc") ?
		// Sort.by(request.getSortBy()).descending()
		// : Sort.by(request.getSortBy()).ascending());
		Pageable pageable;
		Page<NimaiMCustomer> referDetails;
//		String countryNames = Utility.getUserCountry();
//		if (countryNames != null && countryNames.equalsIgnoreCase("all") && request.getCountry() == null) {
//
//		} else if (countryNames != null && !countryNames.equalsIgnoreCase("all") && request.getCountry() == null) {
//			request.setCountryNames(countryNames);
//		} else if (countryNames == null && request.getCountry() == null) {
//			referDetails = null;
//		}

		if (request.getRole() != null && ((request.getRole().equalsIgnoreCase("Bank RM"))
				|| (request.getRole().equalsIgnoreCase("Customer RM")))) {
			request.setLoginUserId(Utility.getUserId());
			request.setRmStatus("Active");
		}
		String countryNames = Utility.getUserCountry();
		System.out.println("countryNames: " + countryNames);
		if (countryNames != null && countryNames.equalsIgnoreCase("all") && request.getCountry() == null) {
			countryNames = "";
			final List<String> countryList = (List<String>) this.customerRepository.getCountryList();
			for (final String country : countryList) {
				countryNames = countryNames + country + ",";
				request.setCountryNames(countryNames);
			}
			System.out.println("Country List: " + countryNames);
			request.setCountryNames(countryNames);
		} else if (countryNames != null && !countryNames.equalsIgnoreCase("all") && request.getCountry() == null) {
			request.setCountryNames(countryNames);
		} else if (countryNames == null && request.getCountry() == null) {
		}
		final List<String> countryValue = Stream.of(request.getCountryNames().split(",", -1))
				.collect(Collectors.toList());
		// final List<String> value = Stream.of(request.getCountryNames().split(",",
		// -1)).collect(Collectors.toList());
		// System.out.println("Values BankService: " + value);
		System.out.println("countryValue: " + countryValue);
		System.out.println("REquest--Txn Status: " + request.getTxtStatus());
		try {
			if (request.getTxtStatus().equalsIgnoreCase("null") || request.getTxtStatus() == null) {
				System.out.println("======= creating pageable (txn sts=null)=======");
				pageable = PageRequest.of(request.getPage(), request.getSize(),
						request.getDirection().equalsIgnoreCase("desc") ? Sort.by(request.getSortBy()).descending()
								: Sort.by(request.getSortBy()).ascending());
				System.out.println("======= Getting Referrer Data =======");
				referDetails = customerRepository.findAll(customerSearchSpecification.getFilter(request), pageable);

			} else if (request.getTxtStatus().equalsIgnoreCase("Not Uploaded")) {
				System.out.println("======= creating pageable (txn sts=not uploaded)=======");
				request.setSortBy("inserted_date");
				pageable = PageRequest.of(request.getPage(), request.getSize(),
						request.getDirection().equalsIgnoreCase("desc") ? Sort.by(request.getSortBy()).descending()
								: Sort.by(request.getSortBy()).ascending());
				System.out.println("======= Getting Not Uploaded KYC of Referrer =======");
				referDetails = customerRepository.getNotUploadForRE(countryValue, pageable);
			} 
			else if(request.getTxtStatus().equalsIgnoreCase("Pending"))
			{
				System.out.println("======= creating pageable (txn sts=pending)=======");
				request.setSortBy("inserted_date");
				pageable=PageRequest.of(request.getPage(), request.getSize(),
						request.getDirection().equalsIgnoreCase("desc") ? Sort.by(request.getSortBy()).descending()
						: Sort.by(request.getSortBy()).ascending());
				System.out.println("======= Getting Not Uploaded KYC of Referrer =======");
				referDetails = customerRepository.getPendingForRE(countryValue, pageable);
			}
			else {
				System.out.println("======= creating pageable else (txn sts=null)=======");
				pageable = PageRequest.of(request.getPage(), request.getSize(),
						request.getDirection().equalsIgnoreCase("desc") ? Sort.by(request.getSortBy()).descending()
								: Sort.by(request.getSortBy()).ascending());
				System.out.println("======= Getting Referrer Data =======");
				referDetails = customerRepository.findAll(customerSearchSpecification.getFilter(request), pageable);

			}
		} catch (Exception e) {
			System.out.println("In Exception block -- Referrer");
			System.out.println("======= creating pageable else (txn sts=null)=======");
			pageable = PageRequest.of(request.getPage(), request.getSize(),
					request.getDirection().equalsIgnoreCase("desc") ? Sort.by(request.getSortBy()).descending()
							: Sort.by(request.getSortBy()).ascending());
			System.out.println("======= Getting Referrer Data =======");
			referDetails = customerRepository.findAll(customerSearchSpecification.getFilter(request), pageable);

		}
		// for (NimaiMCustomer ref : referDetails) {
//
//		}

		List<CustomerResponse> responses = referDetails.map(ref -> {
			CustomerResponse response = new CustomerResponse();
			response.setUserid(ref.getUserid());
			System.out.println("==========================referreer userId===================="+ref.getUserid());
			response.setFirstName(ref.getFirstName());
			response.setLastName(ref.getLastName());
			response.setEmailAddress(ref.getEmailAddress());
			response.setMobileNumber(ref.getMobileNumber());
			response.setCountryName(ref.getCountryName());
			response.setLandline(ref.getLandline());
			response.setDesignation(ref.getDesignation());
			response.setCompanyName(ref.getCompanyName());
			if(ref.getKycStatus()==null)
				response.setKyc("Not Uploaded");
			else if (ref.getKycStatus().equals("PENDING"))
				response.setKyc("Not Uploaded");
			else
				response.setKyc(ref.getKycStatus());
			// response.setTotalReference(ref.getNimaiMReferList().size());
//			List<NimaiMCustomer> data = customerRepository.findReferListByReferrerUsrId(ref.getUserid());
//			response.setEarning(earning);

			// if(!ref.getAccountSource().equalsIgnoreCase("WEBSITE")) {
			List<NimaiMRefer> refer = referRepository.findReferByUserId(ref.getUserid());

			RefBean referBean = new RefBean();
			List<ReferrerBean> rfb = new ArrayList<>();
			Float totalEarning = (float) 0.0;
			for (NimaiMRefer rf : refer) {
				NimaiMCustomer customer = new NimaiMCustomer();
				ReferrerBean rb = new ReferrerBean();
				try {
					customer = customerRepository.getUserIdByEmailId(rf.getEmailAddress());
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("Customer id which is not present :" + rf.getEmailAddress());
					continue;
				}

				List<NimaiSubscriptionDetails> details = new ArrayList<>();

				try {
					details = subRepo.finSplanByReferId(customer.getUserid());
					Float referEarning = Float.valueOf(systemConfig.earningPercentage());
					Float actualREarning = (Float) (referEarning / 100);
					Float userTotalEarning = (float) 0.0;
					if (details == null) {
						userTotalEarning = (float) 0.0;
					} else if (details.size() == 1) {
						if ((customer.getPaymentStatus().equalsIgnoreCase("Approved")
								|| customer.getPaymentStatus().equalsIgnoreCase("Success"))
								&& customer.getKycStatus().equalsIgnoreCase("Approved")) {
							Integer totalEarn = customerRepository.findTotalEarning(customer.getUserid());
							if (totalEarn == null) {
								userTotalEarning = (float) 0.0;
							} else {
								Float value = Float
										.parseFloat(new DecimalFormat("##.##").format(totalEarn * actualREarning));
								// ncb.setTotalEarning(String.valueOf(value));
								userTotalEarning = value;
								// ncb.setTotalEarning(currency + " " + (int) ((int) Math.round(totalEarn *
								// 0.07)));
							}
						} else {
							userTotalEarning = (float) 0.0;
						}
					} else {
						Integer totalEarn = customerRepository.findTotalEarning(customer.getUserid());
						if (totalEarn == null) {
							userTotalEarning = (float) 0.0;
						} else {
							Float value = Float
									.parseFloat(new DecimalFormat("##.##").format(totalEarn * actualREarning));
							// ncb.setTotalEarning(String.valueOf(value));
							userTotalEarning = value;
							// ncb.setTotalEarning(currency + " " + (int) ((int) Math.round(totalEarn *
							// 0.07)));
						}
					}
					rb.setUserWiseTotalEarning(userTotalEarning);
					rb.setUserId(customer.getUserid());
					rfb.add(rb);
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}

			}
			referBean.setRfb(rfb);
			/*
			 * code commented done on 3/01/2021
			 */
//			for (ReferrerBean rEarn : rfb) {
//				totalEarning += rEarn.getUserWiseTotalEarning();
//			}
//			referBean.setTotalEarning(Float.parseFloat(new DecimalFormat("##.##").format(totalEarning)));
//
//			float Earning = Float.parseFloat(new DecimalFormat("##.##").format(totalEarning));
//			String earning = String.valueOf(Earning);

			// }

//			int approve = 0;
//			int reject = 0;
//			for (int i = 0; i < ref.getNimaiMReferList().size(); i++) {
//				NimaiMCustomer data = customerRepository
//						.findByEmailAddress(ref.getNimaiMReferList().get(i).getEmailAddress().toLowerCase());
//
//				if (data != null) {
//					if (data.getKycStatus() != null && data.getKycStatus().equalsIgnoreCase("Approved")) {
//						approve = approve + 1;
//					} else if (data.getKycStatus() != null && data.getKycStatus().equalsIgnoreCase("rejected")) {
//						reject = reject + 1;
//					}
//				}
//			}
			/* Changes done on 3/01/2021 */

			Integer approvedReference = customerRepository.getApprovedReferrence(ref.getUserid());
			if (approvedReference.equals(null)) {
				response.setApprovedReference(0);
			} else {
				response.setApprovedReference(approvedReference);
			}
			try {
				Integer totalReference = customerRepository.getTotareference(ref.getUserid());
				if (totalReference.equals(null)) {
					response.setTotalReference(0);
				} else {
					response.setTotalReference(totalReference);
				}
			} catch (Exception e) {
				e.printStackTrace();

			}

			try {
				Integer pendingReference = customerRepository.getpendingReference(ref.getUserid());
				if (pendingReference.equals(null)) {
					response.setPendingReference(0);
				} else {
					response.setPendingReference(pendingReference);
				}
			} catch (Exception e) {
				e.printStackTrace();

			}
			try {
				Integer rejectedReference = customerRepository.getRejectedReference(ref.getUserid());
				if (rejectedReference.equals(null)) {
					response.setRejectedReference(0);
				} else {
					response.setRejectedReference(rejectedReference);
				}
			} catch (Exception e) {
				e.printStackTrace();

			}

			NimaiMCustomer referrDetails = customerRepository.getOne(ref.getUserid());

			String accountStatus = checkAccStatus(referrDetails);

			if (accountStatus == null) {
				// response.setAccount_status("INACTIVE");
				response.setEarning(String.valueOf(0));
			} else if (accountStatus.equalsIgnoreCase("ACTIVE")) {
				// response.setAccount_status("ACTIVE");
				try {
					Double value = customerRepository.getEarning(request.getUserId());
					if (value==null|| value.equals(null)) {
						value=(double) 0;
					} else {
						value= value;
					}
					Float referEarning = Float.valueOf(systemConfig.earningPercentage());
					Float actualREarning = (Float) (referEarning / 100);

					Float earning = Float.parseFloat(new DecimalFormat("##.##").format(value * actualREarning));
					if (earning.equals(null)) {
						response.setEarning(String.valueOf(0));
					} else {
						response.setEarning(String.valueOf(earning));
					}
				} catch (Exception e) {
					e.printStackTrace();

				}
			} else {
				// response.setAccount_status("INACTIVE");
				response.setEarning(String.valueOf(0));
			}
			/* Changes done on 3/01/2021 */
			// response.setApprovedReference(approve);
			// response.setRejectedReference(reject);
			// response.setPendingReference(ref.getNimaiMReferList().size() - approve -
			// reject);
			response.setInsertedDate(ref.getInsertedDate());
			response.setInsertedDate(ref.getInsertedDate());
			// response.setEarning(earning);
			return response;
		}).getContent();

		return new PagedResponse<>(responses, referDetails.getNumber(), referDetails.getSize(),
				referDetails.getTotalElements(), referDetails.getTotalPages(), referDetails.isLast());

	}

	public String checkAccStatus(NimaiMCustomer custDetails) {
		String accountStatus = "";
		if (custDetails.getUserid().substring(0, 2).equalsIgnoreCase("BA")
				|| custDetails.getUserid().substring(0, 2).equalsIgnoreCase("BC")
				|| custDetails.getUserid().substring(0, 2).equalsIgnoreCase("CU")) {

			if (custDetails.getPaymentStatus() == null || custDetails.getKycStatus() == null) {
				accountStatus = "INACTIVE";
			} else if (custDetails.getPaymentStatus().equalsIgnoreCase("Approved")
					&& custDetails.getKycStatus().equalsIgnoreCase("Approved")) {
				accountStatus = "ACTIVE";
			} else {
				accountStatus = "INACTIVE";
			}
		} else {
			if (custDetails.getKycStatus() == null) {
				accountStatus = "INACTIVE";
			} else if (custDetails.getKycStatus().equalsIgnoreCase("Approved")) {
				accountStatus = "ACTIVE";
			} else {
				accountStatus = "INACTIVE";
			}
		}
		return accountStatus;

	}

	public int approvededReferenc(String userId) {
		Integer approvededReference;
		try {
			approvededReference = customerRepository.getApprovedReferrence(userId);
			if (approvededReference.equals(null)) {
				approvededReference = approvededReference;
			} else {
				approvededReference = 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
			approvededReference = 0;
			return approvededReference;
		}
		return approvededReference;

	}

	public int totalReferensece(String userId) {
		Integer totalReference;
		try {
			totalReference = customerRepository.getTotareference(userId);
			if (totalReference.equals(null)) {
				totalReference = totalReference;
			} else {
				totalReference = 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
			totalReference = 0;
			return totalReference;
		}
		return totalReference;
	}

	public int pendingReference(String userId) {
		Integer pendingReference;
		try {
			pendingReference = customerRepository.getpendingReference(userId);
			if (pendingReference.equals(null)) {
				pendingReference = pendingReference;
			} else {
				pendingReference = 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
			pendingReference = 0;
			return pendingReference;
		}
		return pendingReference;
	}

	public int rejectedReference(String userId) {
		Integer rejectedReference;
		try {
			rejectedReference = customerRepository.getRejectedReference(userId);
			if (rejectedReference.equals(null)) {
				rejectedReference = rejectedReference;
			} else {
				rejectedReference = 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
			rejectedReference = 0;
			return rejectedReference;
		}
		return rejectedReference;
	}

	@Override
	public List<String> userIdSearch(String userId, String data) {
		String val = Utility.getUserCountry();
		if (!val.equalsIgnoreCase("All")) {
			List<String> list = Stream.of(val.split(",")).collect(Collectors.toList());
			return customerRepository.userIdDataSearchByCountry(userId.toLowerCase(), data, list);
		} else {
			return customerRepository.userIdDataSearch(userId.toLowerCase(), data);
		}
	}

	@Override
	public List<String> emailIdSearch(String emailId) {
//			String data) {
		String val = Utility.getUserCountry();
		if (!val.equalsIgnoreCase("All")) {
			List<String> list = Stream.of(val.split(",")).collect(Collectors.toList());
			// return customerRepository.emailIdDataSearchByCountry(emailId.toLowerCase(),
			// data, list);
			return customerRepository.emailIdDataSearchByCountry(emailId.toLowerCase(), list);
		} else {
//			return customerRepository.emailIdDataSearch(emailId.toLowerCase(), data);
			return customerRepository.emailIdDataSearch(emailId.toLowerCase());
		}
	}

	@Override
	public List<String> mobileNumberSearch(String mobileNo) {
//			, String data) {
		String val = Utility.getUserCountry();
		if (!val.equalsIgnoreCase("All")) {
			List<String> list = Stream.of(val.split(",")).collect(Collectors.toList());
			return customerRepository.mobileNumberDataSearchByCountry(mobileNo.toLowerCase(), list);
			// return
			// customerRepository.mobileNumberDataSearchByCountry(mobileNo.toLowerCase(),
			// data, list);
		} else {
			return customerRepository.mobileNumberDataSearch(mobileNo.toLowerCase());
			// return customerRepository.mobileNumberDataSearch(mobileNo.toLowerCase(),
			// data);
		}
	}

	@Override
	public List<String> companyNameSearch(String companyName) {
//			, String data) {
		String val = Utility.getUserCountry();
		if (!val.equalsIgnoreCase("All")) {
			List<String> list = Stream.of(val.split(",")).collect(Collectors.toList());
			return customerRepository.companyNameDataSearchByCountry(companyName.toLowerCase(), list);
			// return
			// customerRepository.companyNameDataSearchByCountry(companyName.toLowerCase(),
			// data, list);
		} else {
			return customerRepository.companyNameDataSearch(companyName.toLowerCase());
			// return customerRepository.companyNameDataSearch(companyName.toLowerCase(),
			// data);
		}
	}

	@Override
	public PagedResponse<?> getAllReferDetails(SearchRequest request) {
		List<CustomerResponse> responses = null;
		Pageable pageable = PageRequest.of(request.getPage(), request.getSize(),
				request.getDirection().equalsIgnoreCase("desc") ? Sort.by(request.getSortBy()).descending()
						: Sort.by(request.getSortBy()).ascending());

		Page<NimaiMRefer> referDetails = referRepository.findAll(referSpecification.getFilter(request), pageable);

		if (request.getTxtStatus().equalsIgnoreCase("all")) {
			responses = referDetails.map(ref -> {
				System.out.println("");
				CustomerResponse response = new CustomerResponse();
				response.setFirstName(ref.getFirstName());

				NimaiMCustomer customer = customerRepository.findByEmailAddress(ref.getEmailAddress());
				if (customer == null) {
					response.setAccountStatus("Pending");
				} else {
					String paymentSts = customer.getPaymentStatus();
					String kycSts = customer.getKycStatus();
					if (paymentSts == null || kycSts == null) {
						response.setAccountStatus("Pending");
					} else if ((paymentSts.equalsIgnoreCase("Approved") || paymentSts.equalsIgnoreCase("Success"))
							&& kycSts.equalsIgnoreCase("Approved")) {
						response.setAccountStatus("Active");
					} else {
						response.setAccountStatus("Pending");
					}
				}

				response.setLastName(ref.getLastName());
				response.setEmailAddress(ref.getEmailAddress());
				response.setMobileNumber(ref.getMobileNo());
				response.setCountryName(ref.getCountryName());
				response.setCompanyName(ref.getCompanyName());
				response.setInsertedDate(ref.getInsertedDate());
				response.setReferId(ref.getId());
				return response;
			}).getContent();
		} else if (request.getTxtStatus().equalsIgnoreCase("approved")
				|| request.getTxtStatus().equalsIgnoreCase("rejected")) {
			responses = referDetails.map(ref -> {
				String customer = customerRepository.findKycByEmailAddress(ref.getEmailAddress().toLowerCase());
				if (customer != null && customer.equalsIgnoreCase(request.getTxtStatus())) {
					CustomerResponse response = new CustomerResponse();

					response.setFirstName(ref.getFirstName());
					response.setLastName(ref.getLastName());
					response.setEmailAddress(ref.getEmailAddress());
					response.setMobileNumber(ref.getMobileNo());
					response.setCountryName(ref.getCountryName());
					response.setCompanyName(ref.getCompanyName());
					response.setInsertedDate(ref.getInsertedDate());
					NimaiMCustomer customer1 = customerRepository.findByEmailAddress(ref.getEmailAddress());
					if (customer1 == null) {
						response.setAccountStatus("Pending");
					} else {
						String paymentSts = customer1.getPaymentStatus();

						String kycSts = customer1.getKycStatus();

						if (paymentSts == null || kycSts == null) {
							response.setAccountStatus("Pending");
						} else if ((paymentSts.equalsIgnoreCase("Approved") || paymentSts.equalsIgnoreCase("Success"))
								&& kycSts.equalsIgnoreCase("Approved")) {
							response.setAccountStatus("Active");
						} else {
							response.setAccountStatus("Pending");
						}
					}

					// response.setAccountStatus(ref.getStatus());
					response.setReferId(ref.getId());
					return response;
				}
				return null;
			}).toList();
		} else if (request.getTxtStatus().equalsIgnoreCase("pending")) {
			responses = referDetails.map(ref -> {
				String customer = customerRepository.findKycByEmailAddress(ref.getEmailAddress().toLowerCase());
				if (customer == null || customer.equalsIgnoreCase(request.getTxtStatus())) {
					CustomerResponse response = new CustomerResponse();
					response.setFirstName(ref.getFirstName());
					response.setLastName(ref.getLastName());
					response.setEmailAddress(ref.getEmailAddress());
					response.setMobileNumber(ref.getMobileNo());
					response.setCountryName(ref.getCountryName());
					response.setCompanyName(ref.getCompanyName());
					response.setInsertedDate(ref.getInsertedDate());
					NimaiMCustomer customer1 = customerRepository.findByEmailAddress(ref.getEmailAddress());

					if (customer1 == null) {
						response.setAccountStatus("Pending");
					} else {
						String paymentSts = customer1.getPaymentStatus();
						String kycSts = customer1.getKycStatus();
						if (paymentSts == null || kycSts == null) {
							response.setAccountStatus("Pending");
						} else if ((paymentSts.equalsIgnoreCase("Approved") || paymentSts.equalsIgnoreCase("Success"))
								&& kycSts.equalsIgnoreCase("Approved")) {
							response.setAccountStatus("Active");
						} else {
							response.setAccountStatus("Pending");
						}
					}

					response.setReferId(ref.getId());
					return response;
				}
				return null;
			}).toList();
		}

		return new PagedResponse<>(responses, referDetails.getNumber(), referDetails.getSize(),
				referDetails.getTotalElements(), referDetails.getTotalPages(), referDetails.isLast());
	}

	@Override
	public ResponseEntity<CustomerResponse> getReferrerById(Integer id) {
		NimaiMRefer refer = referRepository.getOne(id);
		NimaiMCustomer customer = customerRepository.findByEmailAddress(refer.getEmailAddress().toLowerCase());

		if (refer != null) {
			CustomerResponse response = new CustomerResponse();
			response.setFirstName(refer.getFirstName());
			response.setLastName(refer.getLastName());
			response.setEmailAddress(refer.getEmailAddress());
			response.setMobileNumber(refer.getMobileNo());
			response.setCompanyName(refer.getCompanyName());
			response.setCountryName(refer.getCountryName());
			response.setInsertedDate(refer.getInsertedDate());

			String Status = "Active";
			NimaiSubscriptionDetails detail = subRepo.getplanByUserID(customer.getUserid(), Status);
			if (detail == null) {
				response.setSubscriptionValidity(" ");
				response.setPlanName(" ");
				response.setPlanAmount(" ");
			} else {
				response.setSubscriptionValidity(detail.getSubscriptionValidity());
				response.setPlanName(detail.getSubscriptionName());
				response.setPlanAmount(String.valueOf(detail.getGrandAmount()));
				List<NimaiSubscriptionDetails> details = new ArrayList<>();
				details = subRepo.finSplanByReferId(customer.getUserid());
				Float referEarning = Float.valueOf(systemConfig.earningPercentage());
				Float actualREarning = (Float) (referEarning / 100);
				Float userTotalEarning = (float) 0.0;
				if (details.size() == 0) {
					response.setUserWiseTotalEarning(0);
				} else if (details.size() == 1) {
					if ((customer.getPaymentStatus().equalsIgnoreCase("Approved")
							|| customer.getPaymentStatus().equalsIgnoreCase("Success"))
							&& customer.getKycStatus().equalsIgnoreCase("Approved")) {
						Integer totalEarn = customerRepository.findTotalEarning(customer.getUserid());
						if (totalEarn == null) {
							userTotalEarning = (float) 0.0;
						} else {
							Float value = Float
									.parseFloat(new DecimalFormat("##.##").format(totalEarn * actualREarning));
							// ncb.setTotalEarning(String.valueOf(value));
							userTotalEarning = value;
							// ncb.setTotalEarning(currency + " " + (int) ((int) Math.round(totalEarn *
							// 0.07)));
						}
					} else {
						userTotalEarning = (float) 0.0;
					}
				} else {
					Integer totalEarn = customerRepository.findTotalEarning(customer.getUserid());
					if (totalEarn == null) {
						userTotalEarning = (float) 0.0;
					} else {
						Float value = Float.parseFloat(new DecimalFormat("##.##").format(totalEarn * actualREarning));
						// ncb.setTotalEarning(String.valueOf(value));
						userTotalEarning = value;
						// ncb.setTotalEarning(currency + " " + (int) ((int) Math.round(totalEarn *
						// 0.07)));
					}
				}
				response.setUserWiseTotalEarning(userTotalEarning);
			}

			if (customer != null) {
				response.setUserid(customer.getUserid());
				response.setSignUpDate(customer.getInsertedDate());

				if ((customer.getPaymentStatus().equalsIgnoreCase("Approved")
						|| customer.getPaymentStatus().equalsIgnoreCase("Success"))
						&& customer.getKycStatus().equalsIgnoreCase("Approved")) {
					response.setAccountStatus("Active");
				} else {
					response.setAccountStatus("Pending");
				}

				response.setSubscriberType(customer.getSubscriberType());
				if (customer.getRmId() != null) {
					NimaiMEmployee emp = employeeRepository.findByEmpCode(customer.getRmId());
					response.setRmFirstName(emp.getEmpName());
					response.setRmLastName(emp.getEmpLastName());
					response.setRmDesignation(emp.getDesignation());
				}
			}
			return new ResponseEntity<CustomerResponse>(response, HttpStatus.OK);
		}
		return null;
	}

	@Override
	public List<String> bankNameSearch(String bankName) {
//			, String data) {
		String val = Utility.getUserCountry();
		if (!val.equalsIgnoreCase("All")) {
			List<String> list = Stream.of(val.split(",")).collect(Collectors.toList());
			// return customerRepository.bankNameDataSearchByCountry(bankName.toLowerCase(),
			// data, list);
			return customerRepository.bankNameDataSearchByCountry(bankName.toLowerCase(), list);
		} else {
			return customerRepository.bankNameDataSearch(bankName.toLowerCase());
			// return customerRepository.bankNameDataSearch(bankName.toLowerCase(), data);
		}
	}

}
