package com.nimai.email.service;

import java.io.IOException;
import java.lang.reflect.Field;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.persistence.Tuple;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.nimai.email.bean.TupleBean;
import com.nimai.email.dao.DailySchedulerDaoImpl;
import com.nimai.email.dao.UserServiceDao;
import com.nimai.email.entity.AdminDailyCountDetailsBean;
import com.nimai.email.entity.AdminRmWiseCount;
import com.nimai.email.entity.BankMonthlyReport;
import com.nimai.email.entity.CustomerBankMonthlyReort;
import com.nimai.email.entity.EodBankDailyReport;
import com.nimai.email.entity.EodCustomerDailyReort;
import com.nimai.email.entity.NimaiClient;
import com.nimai.email.entity.NimaiLC;
import com.nimai.email.entity.NimaiLCMaster;
import com.nimai.email.entity.NimaiMEmployee;
import com.nimai.email.entity.NimaiMSubscription;
import com.nimai.email.entity.NimaiSubscriptionDetails;
import com.nimai.email.repository.SubscriptionDetailsRepository;
import com.nimai.email.utility.EmaiInsert;
import com.nimai.email.utility.EmailErrorCode;
import com.nimai.email.utility.Utils;

@Service
@Transactional
public class DailySchedulerServiceImpl implements DailySchedulerService {
	@Autowired
	private EmaiInsert emailInsert;

	@Autowired
	DailySchedulerDaoImpl schDao;

	@Autowired
	Utils util;

	@Autowired
	UserServiceDao userDao;

	@Autowired
	SubscriptionDetailsRepository sPlanRepo;

	private static Logger logger = LoggerFactory.getLogger(DailySchedulerServiceImpl.class);

	@Override
	@Scheduled(cron = "${sPLanRenewalcronExpression}")
	//@Scheduled(fixedDelay = 30000)
	public void subScriptionEndReminder() {
		// TODO Auto-generated method stub

		Calendar cal = Calendar.getInstance();
		Date insertedDate = cal.getTime();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date splanEndDate = util.get15daysBeforSPlanEndDate();
		Date endDate = util.get7daysBeforSPlanEndDate();
		Date thirtydaysEndDate = util.getthirtydaysBeforSPlanEndDate();
		dateFormat.format(splanEndDate);
		String renewalStatus = "Pending";
		List<NimaiSubscriptionDetails> fifteendaysBeDetails = schDao.getPlanDetails(splanEndDate, endDate,
				thirtydaysEndDate, renewalStatus);

		System.out.println("=============================" + fifteendaysBeDetails.size() + "=======================");

		if (fifteendaysBeDetails.size() > 0) {
			for (NimaiSubscriptionDetails renewalDetails : fifteendaysBeDetails) {
				NimaiMSubscription currency = schDao.getCurrencyDetails(renewalDetails.getSubscriptionId());
				if (currency != null) {
					try {
						System.out.println("=========" + renewalDetails.toString() + "");
						emailInsert.sendSPlanRenewalEmailToCust(renewalDetails, currency);
						schDao.updaterenewalEmailStatus(renewalDetails.getsPlSerialNUmber());
					} catch (Exception e) {
						if (e instanceof NullPointerException) {
							logger.info("============Inside DailySchedulerServiceImpl class==========");
							System.out.println("===========================DailySchedulerServiceImpl:-"
									+ renewalDetails.toString() + "=======================================");
							logger.info("Email Sending failed");
							EmailErrorCode emailError = new EmailErrorCode("EmailNull", 409);

						}
					}

				}

			}
		}

	}

	@Override
	@Transactional
    @Scheduled(cron = "${consolidatedEmailOn1DayOfmonth}")
	public void consolidated1DayOfMonth() {
		// TODO Auto-generated method stub

		List<Tuple> fifteendaysBeDetails = sPlanRepo.getafter30DaysReferSplanDetils();
		List<TupleBean> beanList = new ArrayList<>();
		Date date = null;
		System.out.println("============================= consolidated1DayOfMonth:" + fifteendaysBeDetails.size()
				+ "=======================");
		for (Tuple renewalDetails : fifteendaysBeDetails) {

			TupleBean bean = new TupleBean();
			bean.setAccoutSource(((String) renewalDetails.get("ACCOUNT_SOURCE") != null
					? (String) renewalDetails.get("ACCOUNT_SOURCE")
					: "null"));
			bean.setUserId(
					((String) renewalDetails.get("USERID") != null ? (String) renewalDetails.get("USERID") : ""));
			bean.setCorporate(
					((String) renewalDetails.get("COMPANY_NAME") != null ? (String) renewalDetails.get("COMPANY_NAME")
							: "null"));
			bean.setsPlanName(((String) renewalDetails.get("SUBSCRIPTION_NAME") != null
					? (String) renewalDetails.get("SUBSCRIPTION_NAME")
					: "null"));
			bean.setCurrency("USD");
			bean.setsPlanFee(String.valueOf(((int) renewalDetails.get("SUBSCRIPTION_AMOUNT"))) != null
					? String.valueOf((int) renewalDetails.get("SUBSCRIPTION_AMOUNT"))
					: "null");
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
			// String splanEndDate = ((Date) renewalDetails.get("SPLAN_END_DATE")!= null ?
			// ((Date) renewalDetails.get("SPLAN_END_DATE") : "");

			bean.setSplanendDate((java.util.Date) renewalDetails.get("SPLAN_END_DATE") != null
					? (java.util.Date) renewalDetails.get("SPLAN_END_DATE")
					: date);

			beanList.add(bean);
		}
		Map<String, List<TupleBean>> groupByAccountSource = new HashMap<>();
		groupByAccountSource = beanList.stream().collect(Collectors.groupingBy(TupleBean::getAccoutSource));
		for (Entry<String, List<TupleBean>> entry : groupByAccountSource.entrySet()) {
			System.out.println("============Inside consolidated1DayOfMonth TupleBean available==========::" + entry.toString());
			NimaiClient clientUseId = userDao.getClientDetailsbyUserId(entry.getKey());
			System.out.println("============Inside consolidated1DayOfMonth eodCusttDailyReport available========== key::" + entry.getKey());

			List<TupleBean> listOfKeys2 = entry.getValue();

			System.out.println("=================================asdfg:" + listOfKeys2.toString());
			if (clientUseId != null) {
				try {
					emailInsert.sendConsolidatedEmail1day(listOfKeys2, clientUseId);
				} catch (Exception e) {
					e.printStackTrace();
					if (e instanceof NullPointerException) {
						logger.info("============Inside catch block of consolidated1DayOfMonth class==========");
						System.out.println("======================catch:" + e + "===============================");
						EmailErrorCode emailError = new EmailErrorCode("EmailNull", 409);
					}
				}
			} else {
				logger.info(
						"============Inside catch block of consolidated1DayOfMonth NimaiClient object not found==========");
				System.out
						.println("============Inside consolidated1DayOfMonth NimaiClient object not available==========::");
			}

		}

	}

	@Override
	@Transactional
    @Scheduled(cron = "${consolidatedEmailOn15DayOfmonth}")
	public void consolidated15DayOfMonth() {
		// TODO Auto-generated method stub

		List<Tuple> fifteendaysBeDetails = sPlanRepo.getafter30DaysReferSplanDetils();
		List<TupleBean> beanList = new ArrayList<>();
		Date date = null;
		System.out.println("============================= consolidated15DayOfMonth:" + fifteendaysBeDetails.size()
				+ "=======================");
		for (Tuple renewalDetails : fifteendaysBeDetails) {

			TupleBean bean = new TupleBean();
			bean.setAccoutSource(((String) renewalDetails.get("ACCOUNT_SOURCE") != null
					? (String) renewalDetails.get("ACCOUNT_SOURCE")
					: "null"));
			bean.setUserId(
					((String) renewalDetails.get("USERID") != null ? (String) renewalDetails.get("USERID") : ""));
			bean.setCorporate(
					((String) renewalDetails.get("COMPANY_NAME") != null ? (String) renewalDetails.get("COMPANY_NAME")
							: "null"));
			bean.setsPlanName(((String) renewalDetails.get("SUBSCRIPTION_NAME") != null
					? (String) renewalDetails.get("SUBSCRIPTION_NAME")
					: "null"));
			bean.setCurrency("USD");
			bean.setsPlanFee(String.valueOf(((int) renewalDetails.get("SUBSCRIPTION_AMOUNT"))) != null
					? String.valueOf((int) renewalDetails.get("SUBSCRIPTION_AMOUNT"))
					: "null");
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
			// String splanEndDate = ((Date) renewalDetails.get("SPLAN_END_DATE")!= null ?
			// ((Date) renewalDetails.get("SPLAN_END_DATE") : "");

			bean.setSplanendDate((java.util.Date) renewalDetails.get("SPLAN_END_DATE") != null
					? (java.util.Date) renewalDetails.get("SPLAN_END_DATE")
					: date);

			beanList.add(bean);
		}
		Map<String, List<TupleBean>> groupByAccountSource = new HashMap<>();
		groupByAccountSource = beanList.stream().collect(Collectors.groupingBy(TupleBean::getAccoutSource));
		for (Entry<String, List<TupleBean>> entry : groupByAccountSource.entrySet()) {
			System.out.println("============Inside consolidated15DayOfMonth TupleBean available==========::" + entry.toString());
			NimaiClient clientUseId = userDao.getClientDetailsbyUserId(entry.getKey());
			System.out.println("============Inside consolidated15DayOfMonth available========== key::" + entry.getKey());

			List<TupleBean> listOfKeys2 = entry.getValue();

			System.out.println("==========consolidated15DayOfMonth asdfg:" + listOfKeys2.toString());
			if (clientUseId != null) {
				try {
					emailInsert.sendConsolidatedEmail1day(listOfKeys2, clientUseId);
				} catch (Exception e) {
					e.printStackTrace();
					if (e instanceof NullPointerException) {
						logger.info("============Inside catch block of consolidated15DayOfMonth class==========");
						System.out.println("======================catch:" + e + "===============================");
						EmailErrorCode emailError = new EmailErrorCode("EmailNull", 409);
					}
				}
			} else {
				logger.info(
						"============Inside catch block of consolidated15DayOfMonth NimaiClient object not found==========");
				System.out
						.println("============Inside consolidated15DayOfMonth NimaiClient object not available==========::");
			}

		}

	}

	// @Scheduled(fixedDelay = 30000)
	@Scheduled(cron = "${eodAdminReportcronExpression}")
	public void managementDailyReport() {

		Date dnow = new Date();
		System.out.println(dnow);
		List<AdminRmWiseCount> counDetails = schDao.getRmCount(dnow);
		if (counDetails.size() > 0) {
			for (AdminRmWiseCount count : counDetails) {
				logger.info("============Inside managementDailyReport class:" + count + "==========");
				System.out.println("======================counDetails:" + count + "===============================");
			}
			AdminDailyCountDetailsBean adminCount = schDao.getDailyCountDetails(dnow);
			if (adminCount != null) {
				System.out
						.println("======================adminCount:" + adminCount + "===============================");

				List<NimaiMEmployee> mgmtEmpList = schDao.findManagementEmailIds();
				if (mgmtEmpList.size() > 0) {
					for (NimaiMEmployee empDetails : mgmtEmpList) {
						logger.info(
								"============Inside managementDailyReport admincount managementEmailList not available=========="
										+ empDetails.toString());
						System.out.println("======================catch:"
								+ "Inside managementDailyReport admincount managementEmailList not available"
								+ "===============================" + empDetails.toString());
						try {
							emailInsert.sendAdminDailyReport(counDetails, adminCount, empDetails);

						} catch (Exception e) {
							e.printStackTrace();
							if (e instanceof NullPointerException) {
								logger.info("============Inside managementDailyReport class==========");
								System.out.println(
										"======================catch:" + e + "===============================");
								EmailErrorCode emailError = new EmailErrorCode("EmailNull", 409);
							}
						}
					}
					schDao.updateAdminReportEmailStatus(adminCount.getReportId());
					for (AdminRmWiseCount rmDetails : counDetails) {
						schDao.updateRmWiseCountReportEmailStatus(rmDetails.getReportId());
						logger.info("============Inside updateRmWiseCountReportEmailStatus==========");
					}
				} else {
					logger.info(
							"============Inside managementDailyReport admincount managementEmailList not available==========");
					System.out.println("======================catch:"
							+ "Inside managementDailyReport admincount managementEmailList not available"
							+ "===============================");
				}

			} else {
				logger.info("============Inside managementDailyReport admincount recoreds not available==========");
				System.out.println("======================catch:"
						+ "Inside managementDailyReport admincount recoreds not available"
						+ "===============================");
			}

		} else {
			logger.info("============Inside managementDailyReport recoreds not available==========");
			System.out.println("======================catch:" + "Inside managementDailyReport recoreds not available"
					+ "===============================");
		}

	}

//@Scheduled(fixedDelay = 30000)
	@Scheduled(cron = "${eodCustomerReportcronExpression}")
	public void sendCustomerDailyReport() {

		List<EodCustomerDailyReort> adminCount = schDao.getCuDailyReport();
		if (adminCount.size() > 0) {
			Map<String, List<EodCustomerDailyReort>> groupByUserId = new HashMap<>();

			groupByUserId = adminCount.stream().collect(Collectors.groupingBy(EodCustomerDailyReort::getUserId));
			for (Entry<String, List<EodCustomerDailyReort>> entry : groupByUserId.entrySet()) {

				System.out.println("============Inside eodCusttDailyReport available==========::" + entry.toString());
				NimaiClient clientUseId = userDao.getClientDetailsbyUserId(entry.getKey());
				System.out.println("============Inside eodCusttDailyReport available========== key::" + entry.getKey());
				if (clientUseId != null) {
					try {
						emailInsert.sendCustEodDailyReport(groupByUserId, clientUseId);
					} catch (Exception e) {
						e.printStackTrace();
						if (e instanceof NullPointerException) {
							logger.info("============Inside catch block of eodCusttDailyReport class==========");
							System.out.println("======================catch:" + e + "===============================");
							EmailErrorCode emailError = new EmailErrorCode("EmailNull", 409);
						}
					}
				} else {
					logger.info(
							"============Inside catch block of eodCusttDailyReport NimaiClient object not found==========");
					System.out.println(
							"============Inside eodCusttDailyReport NimaiClient object not available==========::");
				}

			}
			for (EodCustomerDailyReort cuDetails : adminCount) {
				schDao.updateCuReportEmailStatus(cuDetails.getReportId());
				logger.info("============Inside EodCustomerDailyReort==========");
			}
		} else {
			logger.info("============Inside eodCusttDailyReport recoreds not available==========");
			System.out.println("======================catch:" + "Inside eodCusttDailyReport recoreds not available"

					+ "===============================");
		}
	}

	@Scheduled(cron = "${eodBankReportcronExpression}")
	public void sendBankDailyReport() {

		List<EodBankDailyReport> bankDetails = schDao.getBankDailyReport();
		if (bankDetails.size() > 0) {
			Map<String, List<EodBankDailyReport>> groupByUserId = new HashMap<>();

			groupByUserId = bankDetails.stream().collect(Collectors.groupingBy(EodBankDailyReport::getBankUserId));
			for (Entry<String, List<EodBankDailyReport>> entry : groupByUserId.entrySet()) {
				logger.info("============Inside for loop of eodBAnkDailyReport class==========" + entry.toString());
				System.out.println("============Inside eodBAnkDailyReport available==========::" + entry.toString());
				NimaiClient clientUseId = userDao.getClientDetailsbyUserId(entry.getKey());
				System.out.println("============Inside eodBAnkDailyReport available========== key::" + entry.getKey());
				if (clientUseId != null) {
					try {
						emailInsert.sendBankEodDailyReport(groupByUserId, clientUseId);
					} catch (Exception e) {
						e.printStackTrace();
						if (e instanceof NullPointerException) {
							logger.info("============Inside catch block of eodBAnkDailyReport class==========");
							System.out.println("======================catch:" + e + "===============================");
							EmailErrorCode emailError = new EmailErrorCode("EmailNull", 409);
						}
					}
				} else {
					logger.info(
							"============Inside catch block of eodBAnkDailyReport NimaiClient object not found==========");
					System.out.println(
							"============Inside eodCusttDailyReport NimaiClient object not available==========::");
				}

			}
			for (EodBankDailyReport cuDetails : bankDetails) {
				schDao.updateCuReportEmailStatus(cuDetails.getReportId());
				logger.info("============Inside EodCustomerDailyReort==========");
			}
		} else {
			logger.info("============Inside eodBAnkDailyReport recoreds not available==========");
			System.out.println("======================catch:" + "Inside eodBAnkDailyReport recoreds not available"

					+ "===============================");
		}
	}

	@Scheduled(cron = "${trStatusUpdateScheduler}")
	public void sendAlertToCustForUpdateTrStatus() {

		List<NimaiLCMaster> LcTrDetails = schDao.getAcceptedTrList();
		logger.info("============Inside sendAlertToCustForUpdateTrStatus=========" + LcTrDetails.size());
		System.out.println("======================size:" + "Inside sendAlertToCustForUpdateTrStatus"

				+ "===============================" + LcTrDetails.size());
		if (LcTrDetails.size() > 0) {
			for (NimaiLCMaster trDetails : LcTrDetails) {
				logger.info("============Inside sendAlertToCustForUpdateTrStatus=========" + LcTrDetails.toString());
				System.out.println("======================size:" + "Inside sendAlertToCustForUpdateTrStatus"
						+ LcTrDetails.toString());

				try {
					NimaiClient clientUseId = userDao.getClientDetailsbyUserId(trDetails.getUserId());
					emailInsert.sendTrupdateAlertToCust(trDetails, clientUseId);
				} catch (Exception e) {
					e.printStackTrace();
					if (e instanceof NullPointerException) {
						logger.info("============Inside catch block of eodBAnkDailyReport class==========");
						System.out.println("======================catch:" + e + "===============================");
						EmailErrorCode emailError = new EmailErrorCode("EmailNull", 409);
					}
				}
			}
		} else {
			logger.info("============Inside sendAlertToCustForUpdateTrStatus recoreds not available==========");
			System.out.println(
					"======================catch:" + "Inside sendAlertToCustForUpdateTrStatus recoreds not available"

							+ "===============================");
		}
	}
	
	





	@Scheduled(cron= "${everyMonthReport}")
//	@Scheduled(fixedDelay = 60000L)
	public void sendCustMonthlyReportEveryDay() {
		List<CustomerBankMonthlyReort> monthlyReport = schDao.getMonthlyReport();
		System.out.println(monthlyReport);
		if (monthlyReport.size() > 0) {
			Map<String, List<CustomerBankMonthlyReort>> groupByUserId = new HashMap<>();

			groupByUserId = monthlyReport.stream().collect(Collectors.groupingBy(CustomerBankMonthlyReort::getUserId));
			for (Entry<String, List<CustomerBankMonthlyReort>> entry : groupByUserId.entrySet()) {
				NimaiClient clientUseId = userDao.getClientDetailsbyUserId(entry.getKey());
				if (entry.getKey() != null) {
					try {
						emailInsert.sendCustMonthlyReport(groupByUserId, clientUseId);
					} catch (Exception e) {
						e.printStackTrace();
						if (e instanceof NullPointerException) {
							logger.info("============Inside catch block of eodCusttDailyReport class==========");
							EmailErrorCode emailError = new EmailErrorCode("EmailNull", 409);
						}
					}
				} else {
					logger.info(
							"============Inside catch block of eodCusttDailyReport NimaiClient object not found==========");
				}

			}
		} else {
			logger.info("============Inside eodCusttDailyReport recoreds not available==========");			
		}

	}
	
	@Scheduled(cron= "${everyMonthReport}")
	//@Scheduled(fixedDelay = 60000L)
	public void sendBankMonthlyReportEveryDay() {
		List<BankMonthlyReport> monthlyReport = schDao.getBankMonthlyReport();
		System.out.println(monthlyReport);
		if (monthlyReport.size() > 0) {
			Map<String, List<BankMonthlyReport>> groupByUserId = new HashMap<>();
			groupByUserId = monthlyReport.stream().collect(Collectors.groupingBy(BankMonthlyReport::getUserId));
			for (Entry<String, List<BankMonthlyReport>> entry : groupByUserId.entrySet()) {
				NimaiClient clientUseId = userDao.getClientDetailsbyUserId(entry.getKey());
			
				if (entry.getKey() != null) {
					try {
						emailInsert.sendBankMonthlyReport(groupByUserId, clientUseId);
					} catch (Exception e) {
						e.printStackTrace();
						if (e instanceof NullPointerException) {
							logger.info("============Inside catch block of eodCusttDailyReport class==========");
						}
					}
				} else {
					logger.info(
							"============Inside catch block of eodCusttDailyReport NimaiClient object not found==========");
				}
			}
		} else {
			logger.info("============Inside eodCusttDailyReport recoreds not available==========");			
		}
	}
	
	
	
	
}
