package com.nimai.email.utility;

import java.io.File;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.nimai.email.api.GenericResponse;
import com.nimai.email.bean.AdminBean;

import com.nimai.email.bean.AlertToBanksBean;
import com.nimai.email.bean.BankDetailsBean;
import com.nimai.email.bean.BranchUserRequest;
import com.nimai.email.bean.KycEmailRequest;
import com.nimai.email.bean.LcUploadBean;
import com.nimai.email.bean.QuotationAlertRequest;
import com.nimai.email.bean.ResetPassBean;
import com.nimai.email.bean.SubsidiaryBean;
import com.nimai.email.bean.TupleBean;
import com.nimai.email.bean.UserRegistrationBean;
import com.nimai.email.dao.EmailConfigurationdaoImpl;
import com.nimai.email.dao.EmailProcessImpl;
import com.nimai.email.entity.AdminDailyCountDetailsBean;
import com.nimai.email.entity.AdminRmWiseCount;
import com.nimai.email.entity.BankMonthlyReport;
import com.nimai.email.entity.CustomerBankMonthlyReort;
import com.nimai.email.entity.EmailComponentMaster;
import com.nimai.email.entity.EodBankDailyReport;
import com.nimai.email.entity.EodCustomerDailyReort;
import com.nimai.email.entity.NimaiClient;
import com.nimai.email.entity.NimaiEmailScheduler;
import com.nimai.email.entity.NimaiEmailSchedulerAlertToBanks;
import com.nimai.email.entity.NimaiLC;
import com.nimai.email.entity.NimaiLCMaster;
import com.nimai.email.entity.NimaiMBranch;
import com.nimai.email.entity.NimaiMEmployee;
import com.nimai.email.entity.NimaiMLogin;
import com.nimai.email.entity.NimaiMSubscription;
import com.nimai.email.entity.NimaiSubscriptionDetails;
import com.nimai.email.entity.NimaiSubscriptionVas;
import com.nimai.email.entity.NimaiSystemConfig;
import com.nimai.email.entity.OnlinePayment;
import com.nimai.email.entity.QuotationMaster;
import com.nimai.email.repository.EmailComponentRepository;
import com.nimai.email.repository.OnlinePaymentRepository;
import com.nimai.email.repository.nimaiSystemConfigRepository;
import com.sun.mail.smtp.SMTPAddressFailedException;

@Component
public class EmaiInsert {

	private static Logger logger = LoggerFactory.getLogger(EmaiInsert.class);

	@Autowired
	EmailConfigurationdaoImpl emailConfigurationDAOImpl;

	@Autowired
	EmailProcessImpl emailProcessorImpl;

	@Autowired
	EmailComponentRepository emailComRepo;

	@Autowired
	OnlinePaymentRepository payRepo;

	@Autowired
	nimaiSystemConfigRepository systemConfig;

	@Autowired
	EmailSend emailSend;

	@Value("${updateTrlink.url}")
	private String updateTrlink;

	@Value("${ccEMail.email}")
	private String uatCcEMail;

	@Value("${quoteDetailsLink.url}")
	private String quoteDetailsLink;

	@Value("${new-request.url}")
	private String bankPlaceQuote;

	public EmailConfigurationdaoImpl getEmailConfigurationDAOImpl() {
		return emailConfigurationDAOImpl;
	}

	public void setEmailConfigurationDAOImpl(EmailConfigurationdaoImpl emailConfigurationDAOImpl) {
		this.emailConfigurationDAOImpl = emailConfigurationDAOImpl;
	}

	public void resetPasswordEmail(String link, UserRegistrationBean resetpassword, NimaiMLogin nimaiLogin,
			NimaiClient clientDetails, String ccEmail) throws Exception {

		EmailComponentMaster emailconfigurationBean = null;
		try {
			emailconfigurationBean = emailComRepo.findEventConfiguration(resetpassword.getEvent());
			System.out.println(" Fetching Configuration for Reset Password Policy " + emailconfigurationBean);
			Map<String, String> subject = new HashMap<String, String>();
			Map<String, String> body = new HashMap<String, String>();
			subject.put("Subject", emailconfigurationBean.getSubject());
			ArrayList attachements = new ArrayList();
			// attachements.add(new File("D:\\BDO1.jpg"));
			body.put("link", link);
			body.put("username", clientDetails.getFirstName());
			body.put("userId", clientDetails.getUserid());
			body.put("password", nimaiLogin.getPassword());
			String toMail = resetpassword.getEmail();

			System.out.println(" Fetching Configuration for Reset Password Policy "
					+ emailconfigurationBean.getSubject() + " :: " + emailconfigurationBean.getEventId());
			ArrayList details = new ArrayList();
//
//			if (clientDetails.getAccountType().equalsIgnoreCase("BANKUSER")) {
//				String ccEmailId = ccEmail;

			emailConfigurationDAOImpl.saveBCCEmails(uatCcEMail, emailconfigurationBean.getEventId());
			// emailConfigurationDAOImpl.
			// }

			// emailConfigurationDAOImpl.saveCCEmails(uatCcEMail,
			// emailconfigurationBean.getEventId());

			details.add(emailconfigurationBean.getEventId());// 0
			details.add(toMail);// 1
			details.add(subject);// 2
			details.add(body);// 3
			details.add(attachements);

			System.out.println("details" + details);
			emailProcessorImpl.saveEmail(details, emailconfigurationBean.getEventId());

			emailSend.getDetailsEmail();

		} catch (SMTPAddressFailedException e) {
			logger.info("=======SMTP ADDRESS Failed Exception============" + e);

			System.out.println("Exception" + e);
			e.printStackTrace();
		} catch (Exception e) {

			System.out.println("resetPasswordEmail Exception" + e);
			e.printStackTrace();
		}

	}

	public void resetForgorPasswordEmail(String link, UserRegistrationBean forgotPassWord, String emailId,
			NimaiClient nimaiClientDetails) throws Exception {

		EmailComponentMaster emailconfigurationBean = null;
		ArrayList details1 = null;
		try {
			emailconfigurationBean = emailComRepo.findEventConfiguration(forgotPassWord.getEvent());

			System.out.println("emailconfigurationBean output " + emailconfigurationBean);
			Map<String, String> subject = new HashMap<String, String>();
			Map<String, String> body1 = new HashMap<String, String>();
			subject.put("Subject", emailconfigurationBean.getSubject());
			// ArrayList attachements = new ArrayList();
			// attachements.add(new File("D:\\BDO1.jpg"));
			body1.put("link", link);
			body1.put("username", nimaiClientDetails.getFirstName());
			body1.put("userId", nimaiClientDetails.getUserid());
			// String toMail = emailconfigurationBean.getEmailTo();
			// System.out.println("TO mail.."+toMail);
			String toMail = emailId;

			System.out.println("emailconfigurationBean.getEventid" + emailconfigurationBean.getEventId());

			// emailConfigurationDAOImpl.saveCCEmails(uatCcEMail,
			// emailconfigurationBean.getEventId());
			emailConfigurationDAOImpl.saveBCCEmails(uatCcEMail, emailconfigurationBean.getEventId());
			/**
			 *
			 * input ArrayList 0 - eventId 1 - to email addresss 2 - Arraylist of parameters
			 * for subject 3 - Arraylist of parameters for body 4 - Arraylist of files for
			 * attachement
			 *
			 */
			ArrayList attachements = new ArrayList();
			ArrayList details = new ArrayList();
			details.add(emailconfigurationBean.getEventId());// 0
			details.add(toMail);// 1
			details.add(subject);// 2
			details.add(body1);// 3
			details.add(attachements);
			System.out.println("details" + details);
			emailProcessorImpl.saveEmail(details, emailconfigurationBean.getEventId());

			emailSend.getDetailsEmail();
		} catch (SMTPAddressFailedException e) {
			logger.info("=======SMTP ADDRESS Failed Exception============" + e);

			System.out.println("Exception" + e);
			e.printStackTrace();
		} catch (Exception e) {

			System.out.println("Exception" + e);
			e.printStackTrace();
		}

	}

	public void sendSubAccAcivationLink(String Link, SubsidiaryBean subsidiaryBean, NimaiClient subsUserDetails) {
		EmailComponentMaster emailconfigurationBean = null;
		try {

			emailconfigurationBean = emailComRepo.findEventConfiguration(subsidiaryBean.getEvent());

			System.out.println(" Fetching Configuration for Reset Password Policy " + emailconfigurationBean);
			Map<String, String> subject = new HashMap<String, String>();
			Map<String, String> body = new HashMap<String, String>();

			subject.put("Subject", emailconfigurationBean.getSubject());

			// ArrayList attachements = new ArrayList();
			// attachements.add(new File("D:\\EmailSplan_changes_Checklist.xlsx"));
			body.put("link", Link);
			body.put("emailId", subsidiaryBean.getEmailId());
			body.put("subsUserId", subsUserDetails.getUserid());
			body.put("username", subsUserDetails.getFirstName());

			if (subsidiaryBean.getEvent().equalsIgnoreCase("ADD_REFER")) {

				body.put("username", subsidiaryBean.getUserId());
			}
			String toMail = subsidiaryBean.getEmailId();

			// emailConfigurationDAOImpl.saveCCEmails(uatCcEMail,
			// emailconfigurationBean.getEventId());
			emailConfigurationDAOImpl.saveBCCEmails(uatCcEMail, emailconfigurationBean.getEventId());
			System.out.println(" Fetching Configuration for Reset Password Policy "
					+ emailconfigurationBean.getSubject() + " :: " + emailconfigurationBean.getEventId());
			ArrayList attachements = new ArrayList();
			ArrayList details = new ArrayList();
			details.add(emailconfigurationBean.getEventId());// 0
			details.add(toMail);// 1
			details.add(subject);// 2
			details.add(body);// 3
			details.add(attachements);
			System.out.println("details" + details);
			emailProcessorImpl.saveEmail(details, emailconfigurationBean.getEventId());

			emailSend.getDetailsEmail();

		} catch (SMTPAddressFailedException e) {
			logger.info("=======SMTP ADDRESS Failed Exception============" + e);

			System.out.println("Exception" + e);
			e.printStackTrace();
		} catch (Exception e) {

			System.out.println("sendSubAccAcivationLink Exception" + e);
			e.printStackTrace();
		}

	}

	public void sendBranchUserActivationLink(String bUlink, BranchUserRequest branchUserLink, String passcode,
			NimaiClient clientUseId) {
		EmailComponentMaster emailconfigurationBean = null;
		try {
			emailconfigurationBean = emailComRepo.findEventConfiguration(branchUserLink.getEvent());
			// emailconfigurationBean=emailComRepo.findEventConfiguration(branchUserLink.getEvent());

			// System.out.println(" Fetching Configuration for Reset Password Policy " +
			// emailconfigurationBean);
			Map<String, String> subject = new HashMap<String, String>();
			Map<String, String> body = new HashMap<String, String>();

			subject.put("Subject", emailconfigurationBean.getSubject());

			// ArrayList attachements = new ArrayList();
			// attachements.add(new File("D:\\EmailSplan_changes_Checklist.xlsx"));
			body.put("link", bUlink);
			body.put("passcode", passcode);
			body.put("username", clientUseId.getFirstName());

			body.put("emailId", branchUserLink.getEmailId());

			String toMail = branchUserLink.getEmailId();

			/// emailConfigurationDAOImpl.saveCCEmails(uatCcEMail,
			/// emailconfigurationBean.getEventId());
			emailConfigurationDAOImpl.saveBCCEmails(uatCcEMail, emailconfigurationBean.getEventId());
			ArrayList attachements = new ArrayList();
			ArrayList details = new ArrayList();
			details.add(emailconfigurationBean.getEventId());// 0
			details.add(toMail);// 1
			details.add(subject);// 2
			details.add(body);// 3
			details.add(attachements);
			// System.out.println("details" + details);
			logger.info("=======details============" + details);
			emailProcessorImpl.saveEmail(details, emailconfigurationBean.getEventId());

			emailSend.getDetailsEmail();

		} catch (SMTPAddressFailedException e) {
			logger.info("=======SMTP ADDRESS Failed Exception============" + e);

			System.out.println("Exception" + e);
			e.printStackTrace();
		} catch (Exception e) {

			System.out.println("sendBranchUserActivationLink Exception" + e);
			e.printStackTrace();
		}

	}

	public void sendTransactionStatusEmail(String event, LcUploadBean lcUploadBean, String emailAddress) {
		EmailComponentMaster emailconfigurationBean = null;
		try {
			emailconfigurationBean = emailComRepo.findEventConfiguration(lcUploadBean.getEvent());

			System.out.println(" Fetching Configuration for Reset Password Policy " + emailconfigurationBean);
			Map<String, String> subject = new HashMap<String, String>();
			Map<String, String> body = new HashMap<String, String>();

			subject.put("Subject", emailconfigurationBean.getSubject());

			body.put("transactionId", lcUploadBean.getTransactionid());

			String toMail = emailAddress;

			// emailConfigurationDAOImpl.saveCCEmails(uatCcEMail,
			// emailconfigurationBean.getEventId());
			emailConfigurationDAOImpl.saveBCCEmails(uatCcEMail, emailconfigurationBean.getEventId());
			System.out.println("Fetching Configuration for transaction status" + emailconfigurationBean.getSubject()
					+ " :: " + emailconfigurationBean.getEventId());
			ArrayList attachements = new ArrayList();
			ArrayList details = new ArrayList();
			details.add(emailconfigurationBean.getEventId());// 0
			details.add(toMail);// 1
			details.add(subject);// 2
			details.add(body);// 3
			details.add(attachements);

			System.out.println("details" + details);
			emailProcessorImpl.saveEmail(details, emailconfigurationBean.getEventId());

			emailSend.getDetailsEmail();

		} catch (SMTPAddressFailedException e) {
			logger.info("=======SMTP ADDRESS Failed Exception============" + e);

			System.out.println("Exception" + e);
			e.printStackTrace();
		} catch (Exception e) {

			System.out.println("sendTransactionStatusEmail Exception" + e);
			e.printStackTrace();
		}

	}

	public void sendLcStatusEmailWithData(String event, LcUploadBean lcUploadBean, String username, String emailAddress,
			NimaiLC transaction) {
		EmailComponentMaster emailconfigurationBean = null;
		GenericResponse response = new GenericResponse();
		try {
			emailconfigurationBean = emailComRepo.findEventConfiguration(lcUploadBean.getEvent());

			System.out.println("========useraname=======:::" + username);
			System.out.println(" Fetching Configuration for Reset Password Policy " + emailconfigurationBean);
			Map<String, String> subject = new HashMap<String, String>();
			Map<String, String> body = new HashMap<String, String>();
			// String lcValue = Double.toString(transaction.getlCValue());
			String lcValue = new BigDecimal(transaction.getlCValue()).toEngineeringString();
			String lcIssuingDate = convertStringToDate(transaction.getlCIssuingDate());
			String lcExpiryDate = (transaction.getValidity().trim().substring(0, 10).replaceAll("-", "/"));
			subject.put("Subject", emailconfigurationBean.getSubject());

			body.put("transactionId", lcUploadBean.getTransactionid());
			body.put("username", username);
			body.put("userId", lcUploadBean.getUserId());
			body.put("lcIssuingValue", lcValue);
			body.put("lcIssuingDate", lcIssuingDate);
			body.put("lcExpiryDate", lcExpiryDate);

			String toMail = emailAddress;

			// emailConfigurationDAOImpl.saveCCEmails(uatCcEMail,
			// emailconfigurationBean.getEventId());
			emailConfigurationDAOImpl.saveBCCEmails(uatCcEMail, emailconfigurationBean.getEventId());
			System.out.println("Fetching Configuration for transaction status" + emailconfigurationBean.getSubject()
					+ " :: " + emailconfigurationBean.getEventId());
			ArrayList attachements = new ArrayList();
			ArrayList details = new ArrayList();
			details.add(emailconfigurationBean.getEventId());// 0
			details.add(toMail);// 1
			details.add(subject);// 2
			details.add(body);// 3
			details.add(attachements);
			System.out.println("details" + details);

			emailProcessorImpl.saveEmail(details, emailconfigurationBean.getEventId());

			emailSend.getDetailsEmail();

		} catch (SMTPAddressFailedException e) {
			logger.info("=======SMTP ADDRESS Failed Exception============" + e);
			response.setMessage("SMTP Address Failed Exception");

			System.out.println("Exception" + e);
			e.printStackTrace();
		} catch (Exception e) {
			logger.info("=======sendLcStatusEmailWithData Exception============" + e);
			e.printStackTrace();
		}

	}

	public String convertSToDate(Date indate) {
		String dateString = null;
		String lcDate = "";
		SimpleDateFormat sdfr = new SimpleDateFormat("dd/MMM/yyyy");
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(indate);
			int month = cal.get(Calendar.MONTH) + 1;
			int day = cal.get(Calendar.DATE) + 1;
			int year = cal.get(Calendar.YEAR);
			lcDate = day + "/" + month + "/" + year;
		} catch (Exception ex) {
			System.out.println(ex);
		}
		return lcDate;
	}

	public String convertStringToDate(Date indate) {
		String dateString = null;

		SimpleDateFormat sdfr = new SimpleDateFormat("dd/MM/yyyy");
		try {
			dateString = sdfr.format(indate);
		} catch (Exception ex) {
			System.out.println(ex);
		}
		return dateString;
	}

	public String convertStringLocalToDate(LocalDateTime ldtForCurrentDate) {
		String dateString = null;
		SimpleDateFormat sdfr = new SimpleDateFormat("dd/MMM/yyyy");
		try {
			dateString = sdfr.format(ldtForCurrentDate);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println(ex);
		}
		return dateString;
	}

	public void sendTransactionStatusToBanks(NimaiEmailSchedulerAlertToBanks alertBanksBean,
			NimaiLC custTransactionDetails, NimaiClient custDetails) {
		EmailComponentMaster emailconfigurationBean = null;
		NimaiSystemConfig configDetails = null;
		// configDetails= systemConfig.findBySystemId(13);
		configDetails = systemConfig.findBySystemValue("link");
		System.out.println("system value" + configDetails.getSystemEntityValue());
		try {
			// emailconfigurationBean =
			// emailConfigurationDAOImpl.findEventConfiguration(alertBanksBean.getEmailEvent());
			emailconfigurationBean = emailComRepo.findEventConfiguration(alertBanksBean.getEmailEvent());
			logger.info(" Fetching Configuration for Reset Password Policy " + emailconfigurationBean);
			Map<String, String> subject = new HashMap<String, String>();
			Map<String, String> body = new HashMap<String, String>();
			String lcValue = new BigDecimal(custTransactionDetails.getlCValue()).toEngineeringString();
			// String lcValue = Double.toString(custTransactionDetails.getlCValue());
			String requiredPage = "new-request";
			String quotePlaceLink = bankPlaceQuote + requiredPage;
			String lcIssuingDate = convertSToDate(custTransactionDetails.getlCIssuingDate());
			// String lcExpiryDate = convertSToDate(custTransactionDetails.getValidity());
			String lcExpiryDate = custTransactionDetails.getValidity().trim().substring(0, 10).replaceAll("-", "/");
			subject.put("Subject", emailconfigurationBean.getSubject());
			body.put("transactionId", alertBanksBean.getTransactionid());
			body.put("username", alertBanksBean.getBankUserName());
			System.out.println("=============" + alertBanksBean.getBankUserName());
			body.put("userId", custTransactionDetails.getUserId());
			body.put("customerName", custDetails.getFirstName());
			body.put("companyname", custDetails.getCompanyName());
			body.put("lcIssuingValue", lcValue);
			body.put("lcIssuingDate", lcIssuingDate);
			body.put("lcExpiryDate", lcExpiryDate);
			body.put("currency", custTransactionDetails.getlCCurrency());
			body.put("currency", custTransactionDetails.getlCCurrency());
			body.put("link", configDetails.getSystemEntityValue());
			if (alertBanksBean.getEmailEvent().equalsIgnoreCase("LC_REJECT_ALERT_ToBanks")) {
				body.put("reason", alertBanksBean.getReason());
			}
			String toMail = alertBanksBean.getBanksEmailID();
			// emailConfigurationDAOImpl.saveCCEmails(uatCcEMail,
			// emailconfigurationBean.getEventId());
			emailConfigurationDAOImpl.saveBCCEmails(uatCcEMail, emailconfigurationBean.getEventId());
			ArrayList attachements = new ArrayList();
			ArrayList details = new ArrayList();
			details.add(emailconfigurationBean.getEventId());// 0
			details.add(toMail);// 1
			details.add(subject);// 2
			details.add(body);// 3
			details.add(attachements);

			logger.info("details" + details);
			emailProcessorImpl.saveEmail(details, emailconfigurationBean.getEventId());

			emailSend.getDetailsEmail();

		} catch (SMTPAddressFailedException e) {
			logger.info("=======SMTP ADDRESS Failed Exception============" + e);
			logger.info("Exception" + e);
			e.printStackTrace();
		} catch (Exception e) {
			logger.info("sendTransactionStatusToBanks Exception" + e);
			e.printStackTrace();
		}

	}

	public void sendQuotationStatusEmail(String event, QuotationAlertRequest quotationReq, String bankEmailId) {
		EmailComponentMaster emailconfigurationBean = null;
		try {
			// emailconfigurationBean =
			// emailConfigurationDAOImpl.findEventConfiguration(quotationReq.getEvent());
			emailconfigurationBean = emailComRepo.findEventConfiguration(quotationReq.getEvent());

			System.out.println(" Fetching Configuration for Reset Password Policy " + emailconfigurationBean);
			Map<String, String> subject = new HashMap<String, String>();
			Map<String, String> body = new HashMap<String, String>();

			subject.put("Subject", emailconfigurationBean.getSubject());

			body.put("transactionId", quotationReq.getTransactionId());

			body.put("quotationId", quotationReq.getQuotationId());

			if (quotationReq.getEvent().equalsIgnoreCase("QUOTE_REJECTION")) {
				body.put("reason", quotationReq.getReason());
				body.put("quotationId", quotationReq.getQuotationId());
			}

			String toMail = bankEmailId;

			// emailConfigurationDAOImpl.saveCCEmails(uatCcEMail,
			// emailconfigurationBean.getEventId());
			emailConfigurationDAOImpl.saveBCCEmails(uatCcEMail, emailconfigurationBean.getEventId());
			System.out.println("Fetching Configuration for transaction status" + emailconfigurationBean.getSubject()
					+ " :: " + emailconfigurationBean.getEventId());
			ArrayList attachements = new ArrayList();
			ArrayList details = new ArrayList();
			details.add(emailconfigurationBean.getEventId());// 0
			details.add(toMail);// 1
			details.add(subject);// 2
			details.add(body);// 3
			details.add(attachements);
			System.out.println("details" + details);
			emailProcessorImpl.saveEmail(details, emailconfigurationBean.getEventId());

			emailSend.getDetailsEmail();

		} catch (SMTPAddressFailedException e) {
			logger.info("=======sendQuotationStatusEmail SMTP ADDRESS Failed Exception============" + e);

			System.out.println("sendQuotationStatusEmail Exception" + e);
			e.printStackTrace();
		} catch (Exception e) {

			System.out.println("sendQuotationStatusEmail Exception" + e);
			e.printStackTrace();
		}

	}

	public void sendTransactionStatusToBanks(String event, AlertToBanksBean alertBanksBean, String emailId) {
		EmailComponentMaster emailconfigurationBean = null;
		try {
			// emailconfigurationBean =
			// emailConfigurationDAOImpl.findEventConfiguration(event);
			emailconfigurationBean = emailComRepo.findEventConfiguration(event);

			System.out.println(" Fetching Configuration for Reset Password Policy " + emailconfigurationBean);
			Map<String, String> subject = new HashMap<String, String>();
			Map<String, String> body = new HashMap<String, String>();

			subject.put("Subject", emailconfigurationBean.getSubject());
			body.put("transactionId", alertBanksBean.getTransactionId());

			if (event.equalsIgnoreCase("LC_REJECT_ALERT_ToBanks")) {
				body.put("reason", alertBanksBean.getReason());
			}

			String toMail = emailId;

			// emailConfigurationDAOImpl.saveCCEmails(uatCcEMail,
			// emailconfigurationBean.getEventId());
			emailConfigurationDAOImpl.saveBCCEmails(uatCcEMail, emailconfigurationBean.getEventId());
			System.out.println("Fetching Configuration for transaction status" + emailconfigurationBean.getSubject()
					+ " :: " + emailconfigurationBean.getEventId());
			ArrayList attachements = new ArrayList();
			ArrayList details = new ArrayList();
			details.add(emailconfigurationBean.getEventId());// 0
			details.add(toMail);// 1
			details.add(subject);// 2
			details.add(body);// 3
			details.add(attachements);
			System.out.println("details" + details);
			emailProcessorImpl.saveEmail(details, emailconfigurationBean.getEventId());

			emailSend.getDetailsEmail();

		} catch (SMTPAddressFailedException e) {
			logger.info("=======sendTransactionStatusToBanks SMTP ADDRESS Failed Exception============" + e);
			System.out.println("sendTransactionStatusToBanks Exception" + e);
			e.printStackTrace();
		} catch (Exception e) {

			System.out.println("sendTransactionStatusToBanks Exception" + e);
			e.printStackTrace();
		}

	}

	public void sendQuotePlaceEmailToBanks(String emailEvent, NimaiEmailSchedulerAlertToBanks schdulerData,
			String banksEmailID, QuotationMaster bankQuotationDetails, NimaiClient customerDetails,
			NimaiLC custTransactionDetails) {
		EmailComponentMaster emailconfigurationBean = null;
		try {
			// emailconfigurationBean =
			// emailConfigurationDAOImpl.findEventConfiguration(emailEvent);
			emailconfigurationBean = emailComRepo.findEventConfiguration(emailEvent);

			System.out.println(" Fetching Configuration for Reset Password Policy " + emailconfigurationBean);
			Map<String, String> subject = new HashMap<String, String>();
			Map<String, String> body = new HashMap<String, String>();
			String validaityDate = convertSToDate(bankQuotationDetails.getValidityDate());
			String totalQuoteValue = String.valueOf(bankQuotationDetails.getTotalQuoteValue());
			String quotationId = Float.toString(schdulerData.getQuotationId());
			subject.put("Subject", emailconfigurationBean.getSubject());
			body.put("transactionId", schdulerData.getTransactionid());
			body.put("username", schdulerData.getBankUserName());
			body.put("userId", schdulerData.getCustomerid());
			body.put("quotationId", quotationId);
			body.put("validatyDate", validaityDate);
			body.put("totalQuoteValue", totalQuoteValue);
			body.put("companyname", customerDetails.getCompanyName());
			body.put("currency", custTransactionDetails.getlCCurrency());
			body.put("amount", String.valueOf(custTransactionDetails.getlCValue()));

			String toMail = schdulerData.getBanksEmailID();

			// emailConfigurationDAOImpl.saveCCEmails(uatCcEMail,
			// emailconfigurationBean.getEventId());
			emailConfigurationDAOImpl.saveBCCEmails(uatCcEMail, emailconfigurationBean.getEventId());
			System.out.println("Fetching Configuration for transaction status" + emailconfigurationBean.getSubject()
					+ " :: " + emailconfigurationBean.getEventId());
			ArrayList attachements = new ArrayList();
			ArrayList details = new ArrayList();
			details.add(emailconfigurationBean.getEventId());// 0
			details.add(toMail);// 1
			details.add(subject);// 2
			details.add(body);// 3
			details.add(attachements);
			System.out.println("details" + details);
			emailProcessorImpl.saveEmail(details, emailconfigurationBean.getEventId());

			emailSend.getDetailsEmail();

		} catch (SMTPAddressFailedException e) {
			logger.info("=======sendQuotePlaceEmailToBanks SMTP ADDRESS Failed Exception============" + e);
			System.out.println("sendQuotePlaceEmailToBanks Exception" + e);
			e.printStackTrace();
		} catch (Exception e) {

			System.out.println("sendQuotePlaceEmailToBanks Exception" + e);
			e.printStackTrace();
		}

	}

	public void sendQuotationStatusEmail(String emailEvent, NimaiEmailSchedulerAlertToBanks schdulerData,
			String banksEmailID, NimaiLC custTransactionDetails, QuotationMaster bnakQuotationDetails,
			NimaiClient bankDetails, NimaiClient customerDetails) {
		logger.info("=========Inside sendQuotationStatusEmail method with schedulerData from database==========");
		EmailComponentMaster emailconfigurationBean = null;
		try {
			// emailconfigurationBean =
			// emailConfigurationDAOImpl.findEventConfiguration(schdulerData.getEmailEvent());
			emailconfigurationBean = emailComRepo.findEventConfiguration(schdulerData.getEmailEvent());

			logger.info("Fetching Configuration for Reset Password Policy " + emailconfigurationBean);
			Map<String, String> subject = new HashMap<String, String>();
			Map<String, String> body = new HashMap<String, String>();
			String quoteId = String.valueOf(schdulerData.getQuotationId());
			String validaityDate = convertSToDate(bnakQuotationDetails.getValidityDate());
			String subjectSValue = "Quote accepted by the corporate";
			subject.put("Subject", subjectSValue);
			emailConfigurationDAOImpl.updateSubject(subjectSValue, emailconfigurationBean.getEventId());
			String bankUserName = schdulerData.getBankUserName();
			body.put("htmlBody", "<br>Dear" + " " + bankUserName + ", " + "<br>" + "<br>Congratulations!<br>\r\n"
					+ "<br><br>" + customerDetails.getCompanyName() + " " + "has accepted the quote.<br><br>\r\n"
					+ "Quotation placed details against customer are as follows:<br><br>\r\n"
					+ "<br><table style=\"border: 1px solid black!important; border-collapse: collapse;\">\r\n"
					+ "          <thead>\r\n" + "              <tr>\r\n"
					+ "                  <th style=\"background-color:#98AFC7;border: 1px solid black!important; border-collapse: collapse;\">Transaction ID </th>\r\n"
					+ "   <th style=\"background-color:#98AFC7;border: 1px solid black!important; border-collapse: collapse;\">Currency</th>\r\n"
					+ "                  <th style=\"background-color:#98AFC7;border: 1px solid black!important; border-collapse: collapse;\">Amount</th>\r\n"
					+ "  <th style=\"background-color:#98AFC7;border: 1px solid black!important; border-collapse: collapse;\">Total Quote Value</th>\r\n"
					+ "                  <th style=\"background-color:#98AFC7;border: 1px solid black!important; border-collapse: collapse;\">Validity Date</th>\r\n"
					+ " \r\n" + "              </tr>\r\n" + "          </thead>\r\n" + "          <tbody>\r\n"
					+ "              <tr>\r\n"
					+ "                  <td style=\"background-color:#ADD8E6;border: 1px solid black!important; border-collapse: collapse;\">"
					+ schdulerData.getTransactionid() + "</td>\r\n"
					+ "                  <td style=\"background-color:#ADD8E6;border: 1px solid black!important; border-collapse: collapse;\">"
					+ custTransactionDetails.getlCCurrency() + "</td>\r\n"
					+ "<td style=\"background-color:#ADD8E6;border: 1px solid black!important; border-collapse: collapse;\">"
					+ String.valueOf(custTransactionDetails.getlCValue()) + "</td>\r\n"
					+ "                  <td style=\"background-color:#ADD8E6;border: 1px solid black!important; border-collapse: collapse;\">"
					+ String.valueOf(bnakQuotationDetails.getTotalQuoteValue()) + "</td>\r\n"
					+ "                  <td style=\"background-color:#ADD8E6;border: 1px solid black!important; border-collapse: collapse;\">"
					+ validaityDate + "</td>\r\n" + "\r\n" + "              </tr>           \r\n"
					+ "          </tbody>\r\n" + "      </table>\r\n" + "   <br><br>  " + "<br>Warm Regards,<br>\r\n"
					+ "Team 360tf<br>  \r\n"
					+ "-------------------------------------------------------------------  \r\n"
					+ "<br>Copyright & Disclaimer | Privacy Policy<br><br>  \r\n"
					+ "Please do not reply to this mail as this is automated mail service<br></body>");

			if (schdulerData.getEmailEvent().equalsIgnoreCase("QUOTE_REJECTION")) {
				if (bnakQuotationDetails.getRejectedBy().equalsIgnoreCase("BANK")) {
					String subjectRejection = "Quote rejected by the bank";
					subject.put("Subject", subjectRejection);
					body.put("reason", schdulerData.getReason());
					emailConfigurationDAOImpl.updateSubject(subjectRejection, emailconfigurationBean.getEventId());
					body.put("quotationId", quoteId);
					body.put("htmlBody", "<br>Dear" + " " + bankDetails.getFirstName() + ", <br><br>"
							+ "You have rejected the quote.<br><br>\r\n"
							+ "Quotation place details against customer are as follows:<br><br>\r\n"
							+ "<table style=\"border: 1px solid black!important; border-collapse: collapse;\">\r\n"
							+ "          <thead>\r\n" + "              <tr>\r\n"
							+ "                  <th style=\"background-color:#98AFC7;border: 1px solid black!important; border-collapse: collapse;\">TransactionId</th>\r\n"
							+ "   <th style=\"background-color:#98AFC7;border: 1px solid black!important; border-collapse: collapse;\">Currency</th>\r\n"
							+ "                  <th style=\"background-color:#98AFC7;border: 1px solid black!important; border-collapse: collapse;\">Amount</th>\r\n"
							+ "              </tr>\r\n" + "          </thead>\r\n" + "          <tbody>\r\n"
							+ "              <tr>\r\n"
							+ "                  <td style=\"background-color:#ADD8E6;border: 1px solid black!important; border-collapse: collapse;\">"
							+ schdulerData.getTransactionid() + "</td>\r\n"
							+ "                  <td style=\"background-color:#ADD8E6;border: 1px solid black!important; border-collapse: collapse;\">"
							+ custTransactionDetails.getlCCurrency() + "</td>\r\n"
							+ "<td style=\"background-color:#ADD8E6;border: 1px solid black!important; border-collapse: collapse;\">"
							+ String.valueOf(custTransactionDetails.getlCValue()) + "</td>\r\n" +

							"              </tr>           \r\n" + "          </tbody>\r\n" + "      </table>\r\n"
							+ "<br>For further details, open Transaction Details -> Rejected Transaction\r\n" + "<br>"
							+ " <br> <br>\r\n" + " <br>Warm Regards,<br>\r\n" + "Team 360tf<br>  \r\n"
							+ "-------------------------------------------------------------------  \r\n"
							+ "<br>Copyright & Disclaimer | Privacy Policy<br><br>  \r\n"
							+ "Please do not reply to this mail as this is automated mail service<br>");
				} else {
					String subjectRejection = "Quote rejected by corporate";
					subject.put("Subject", subjectRejection);
					body.put("reason", schdulerData.getReason());
					emailConfigurationDAOImpl.updateSubject(subjectRejection, emailconfigurationBean.getEventId());
					body.put("quotationId", quoteId);
					body.put("htmlBody", "<br>Dear" + " " + bankDetails.getFirstName() + ", <br><br>"
							+ customerDetails.getCompanyName() + " " + "has rejected the accepted quote.<br><br>\r\n"
							+ "Quotation place details against customer are as follows:<br><br>\r\n"
							+ "<table style=\"border: 1px solid black!important; border-collapse: collapse;\">\r\n"
							+ "          <thead>\r\n" + "              <tr>\r\n"
							+ "                  <th style=\"background-color:#98AFC7;border: 1px solid black!important; border-collapse: collapse;\">TransactionId</th>\r\n"
							+ "   <th style=\"background-color:#98AFC7;border: 1px solid black!important; border-collapse: collapse;\">Currency</th>\r\n"
							+ "                  <th style=\"background-color:#98AFC7;border: 1px solid black!important; border-collapse: collapse;\">Amount</th>\r\n"
							+ "              </tr>\r\n" + "          </thead>\r\n" + "          <tbody>\r\n"
							+ "              <tr>\r\n"
							+ "                  <td style=\"background-color:#ADD8E6;border: 1px solid black!important; border-collapse: collapse;\">"
							+ schdulerData.getTransactionid() + "</td>\r\n"
							+ "                  <td style=\"background-color:#ADD8E6;border: 1px solid black!important; border-collapse: collapse;\">"
							+ custTransactionDetails.getlCCurrency() + "</td>\r\n"
							+ "<td style=\"background-color:#ADD8E6;border: 1px solid black!important; border-collapse: collapse;\">"
							+ String.valueOf(custTransactionDetails.getlCValue()) + "</td>\r\n" +

							"              </tr>           \r\n" + "          </tbody>\r\n" + "      </table>\r\n"
							+ "<br>For further details, open Transaction Details -> Rejected Transaction\r\n" + "<br>"
							+ " <br> <br>\r\n" + " <br>Warm Regards,<br>\r\n" + "Team 360tf<br>  \r\n"
							+ "-------------------------------------------------------------------  \r\n"
							+ "<br>Copyright & Disclaimer | Privacy Policy<br><br>  \r\n"
							+ "Please do not reply to this mail as this is automated mail service<br>");
				}

			}

			String toMail = schdulerData.getBanksEmailID();

			// emailConfigurationDAOImpl.saveCCEmails(uatCcEMail,
			// emailconfigurationBean.getEventId());
			emailConfigurationDAOImpl.saveBCCEmails(uatCcEMail, emailconfigurationBean.getEventId());
			System.out.println("Fetching Configuration for transaction status" + emailconfigurationBean.getSubject()
					+ " :: " + emailconfigurationBean.getEventId());
			ArrayList attachements = new ArrayList();
			ArrayList details = new ArrayList();
			details.add(emailconfigurationBean.getEventId());// 0
			details.add(toMail);// 1
			details.add(subject);// 2
			details.add(body);// 3
			details.add(attachements);
			logger.info("details" + details);
			emailProcessorImpl.saveEmail(details, emailconfigurationBean.getEventId());

			emailSend.getDetailsEmail();

		} catch (SMTPAddressFailedException e) {
			logger.info("=======sendQuotationStatusEmail SMTP ADDRESS Failed Exception============" + e);
			logger.info("sendQuotationStatusEmail Exception" + e);
			e.printStackTrace();
		} catch (Exception e) {
			logger.info("sendQuotationStatusEmail Exception" + e);
			e.printStackTrace();
		}

	}

	public void sendLcReopeningToAlertBank(String emailEvent, NimaiEmailSchedulerAlertToBanks schdulerData,
			String banksEmailID, NimaiLC custTransactionDetails, QuotationMaster bnakQuotationDetails,
			NimaiClient bankDetails, NimaiClient customerDetails) {
		logger.info("=========Inside sendLcReopeningToAlertBank method with schedulerData from database==========");
		EmailComponentMaster emailconfigurationBean = null;
		try {
			// emailconfigurationBean =
			// emailConfigurationDAOImpl.findEventConfiguration(schdulerData.getEmailEvent());
			emailconfigurationBean = emailComRepo.findEventConfiguration(schdulerData.getEmailEvent());

			System.out.println("Fetching Configuration for Reset Password Policy " + emailconfigurationBean);
			Map<String, String> subject = new HashMap<String, String>();
			Map<String, String> body = new HashMap<String, String>();
			String quoteId = String.valueOf(schdulerData.getQuotationId());
			String validaityDate = convertSToDate(bnakQuotationDetails.getValidityDate());
			String subjectSValue = "Transaction reopened by the corporate";
			subject.put("Subject", subjectSValue);
			emailConfigurationDAOImpl.updateSubject(subjectSValue, emailconfigurationBean.getEventId());
			String bankUserName = schdulerData.getBankUserName();
			System.out.println("================Company Name" + customerDetails.getCompanyName());
			body.put("htmlBody", "<br>Dear" + " " + bankDetails.getFirstName() + ", " + "<br><br>"
					+ customerDetails.getCompanyName() + " "
					+ "has reopened the transaction. Your quote still stand a chance to be accepted by the corporate.<br><br>\r\n"
					+ "Transaction Details:<br>\r\n" + "<br><br>" + "<table>\r\n" + "          <thead>\r\n"
					+ "              <tr>\r\n"
					+ "                  <th style=\"background-color:#98AFC7;border: 1px solid black!important; border-collapse: collapse;\">Transaction ID</th>\r\n"
					+ "   <th style=\"background-color:#98AFC7;border: 1px solid black!important; border-collapse: collapse;\">Currency</th>\r\n"
					+ "                  <th style=\"background-color:#98AFC7;border: 1px solid black!important; border-collapse: collapse;\">Amount</th>\r\n"
					+ "  <th style=\"background-color:#98AFC7;border: 1px solid black!important; border-collapse: collapse;\">Total Quote Value</th>\r\n"
					+ "                  <th>Validity Date</th>\r\n" + " \r\n" + "              </tr>\r\n"
					+ "          </thead>\r\n" + "          <tbody>\r\n" + "              <tr>\r\n"
					+ "                  <td style=\"background-color:#ADD8E6;border: 1px solid black!important; border-collapse: collapse;\">"
					+ schdulerData.getTransactionid() + "</td>\r\n"
					+ "                  <td style=\"background-color:#ADD8E6;border: 1px solid black!important; border-collapse: collapse;\">"
					+ custTransactionDetails.getlCCurrency() + "</td>\r\n"
					+ "<td style=\"background-color:#ADD8E6;border: 1px solid black!important; border-collapse: collapse;\">"
					+ String.valueOf(custTransactionDetails.getlCValue()) + "</td>\r\n"
					+ "                  <td style=\"background-color:#ADD8E6;border: 1px solid black!important; border-collapse: collapse;\">"
					+ String.valueOf(bnakQuotationDetails.getTotalQuoteValue()) + "</td>\r\n"
					+ "                  <td style=\"background-color:#ADD8E6;border: 1px solid black!important; border-collapse: collapse;\">"
					+ validaityDate + "</td>\r\n" + "\r\n" + "              </tr>           \r\n"
					+ "          </tbody>\r\n" + "      </table>\r\n" + "   <br><br> " + "\r\n"
					+ "<br>Warm Regards,<br>\r\n" + "Team 360tf<br>\r\n"
					+ "-------------------------------------------------------------------\r\n"
					+ "<br>Copyright & Disclaimer | Privacy Policy<br><br>\r\n"
					+ "Please do not reply to this mail as this is automated mail service\r\n" + "\r\n" + "\r\n"
					+ "</body>");

			String toMail = schdulerData.getBanksEmailID();

			// emailConfigurationDAOImpl.saveCCEmails(uatCcEMail,
			// emailconfigurationBean.getEventId());
			emailConfigurationDAOImpl.saveBCCEmails(uatCcEMail, emailconfigurationBean.getEventId());
			System.out.println("Fetching Configuration for transaction status" + emailconfigurationBean.getSubject()
					+ " :: " + emailconfigurationBean.getEventId());
			ArrayList attachements = new ArrayList();
			ArrayList details = new ArrayList();
			details.add(emailconfigurationBean.getEventId());// 0
			details.add(toMail);// 1
			details.add(subject);// 2
			details.add(body);// 3
			details.add(attachements);
			System.out.println("details" + details);
			emailProcessorImpl.saveEmail(details, emailconfigurationBean.getEventId());

			emailSend.getDetailsEmail();

		} catch (SMTPAddressFailedException e) {
			logger.info("=======sendQuotationStatusEmail SMTP ADDRESS Failed Exception============" + e);
			System.out.println("sendQuotationStatusEmail Exception" + e);
			e.printStackTrace();
		} catch (Exception e) {

			System.out.println("sendQuotationStatusEmail Exception" + e);
			e.printStackTrace();
		}

	}

	public void sendQuotationStatusEmailToCust(String emailEvent, NimaiEmailSchedulerAlertToBanks schdulerData,
			String custEmailID, QuotationMaster quotationDetails, NimaiLC custTransactionDetails,
			NimaiClient bankDetails, String savingsDetails) {
		logger.info("=========Inside sendQuotationStatusEmailToCust method with schedulerData from database==========");
		EmailComponentMaster emailconfigurationBean = null;

		try {
			// emailconfigurationBean =
			// emailConfigurationDAOImpl.findEventConfiguration(schdulerData.getEmailEvent());
			emailconfigurationBean = emailComRepo.findEventConfiguration("QUOTE_ACCEPT");

			System.out.println("Fetching Configuration for Reset Password Policy " + emailconfigurationBean);
			Map<String, String> subject = new HashMap<String, String>();
			Map<String, String> body = new HashMap<String, String>();
			String quoteId = String.valueOf(schdulerData.getQuotationId());
			String Subject = "Quote acceptance confirmation";
			emailConfigurationDAOImpl.updateSubject(Subject, emailconfigurationBean.getEventId());
			String custName = schdulerData.getCustomerUserName();
			String bankName = bankDetails.getBankNbfcName();
			String country = bankDetails.getCountryName();
			String savings = "";
			if (savingsDetails.equalsIgnoreCase("0")) {
				savings = "| Savings on this transaction" + " " + custTransactionDetails.getlCCurrency() + " "
						+ savingsDetails + "<br>\n";
			} else {
				savings = "";
			}
			String contacatPerson = bankDetails.getFirstName() + " " + bankDetails.getLastName();
			String emailAddress = bankDetails.getEmailAddress();
			String phone = bankDetails.getMobileNumber();
			Float amount = quotationDetails.getTotalQuoteValue();
			String telePhone = bankDetails.getTelephone();
			String quoteValue = String.valueOf(amount);
			String htmlValue = "bank's";
			subject.put("Subject", Subject);

			body.put("htmlBody",
					"<br>Dear" + " " + custName + ", " + "<br><br>Congratulations! <br><br>"
							+ "You have accepted quote from" + " " + bankName + " " + "against the transaction ID" + " "
							+ schdulerData.getTransactionid() + "<br><br>\r\n" + "<br>Accepted Quote Details" + " "
							+ custTransactionDetails.getlCCurrency() + " " + amount + savings
							+ "<br>Please find below the underwriting" + " " + htmlValue + " "
							+ "contact details:<br>\n" + "<br>Bank :" + " " + bankName + "<br>\n" + "Country :" + " "
							+ country + "<br>\n" + "Contact Person :" + " " + contacatPerson + "<br>\n" + "Email :"
							+ " " + emailAddress + "<br>\n" + "Phone :" + " " + telePhone + "<br>\n" + "Mobile :" + " "
							+ phone + "<br>\n" + "<br>\n" + "<br>Warm Regards,<br>\n" + "Team 360tf<br>\n"
							+ "-------------------------------------------------------------------\n"
							+ "<br>Copyright & Disclaimer | Privacy Policy<br><br>\n"
							+ "Please do not reply to this mail as this is automated mail service\r\n" + "<br>");

			if (schdulerData.getEmailEvent().equalsIgnoreCase("QUOTE_REJECTION_CUSTOMER")) {  
				emailconfigurationBean = emailComRepo.findEventConfiguration("QUOTE_REJECTION");
				if (quotationDetails.getRejectedBy().equalsIgnoreCase("BANK")) {
					String subjectRejection = "Quote rejected by bank";
					subject.put("Subject", subjectRejection);
					body.put("reason", schdulerData.getReason());
					emailConfigurationDAOImpl.updateSubject(subjectRejection, emailconfigurationBean.getEventId());
					body.put("quotationId", quoteId);
					body.put("htmlBody", "<br>Dear" + " " + custName + ", <br><br>" + bankDetails.getBankNbfcName()
							+ " " + "has rejected the quote.<br><br>\r\n"
							+ "Quotation place details against customer are as follows:<br><br>\r\n"
							+ "<table style=\"border: 1px solid black!important; border-collapse: collapse;\">\r\n"
							+ "          <thead>\r\n" + "              <tr>\r\n"
							+ "                  <th style=\"background-color:#98AFC7;border: 1px solid black!important; border-collapse: collapse;\">TransactionId</th>\r\n"
							+ "   <th style=\"background-color:#98AFC7;border: 1px solid black!important; border-collapse: collapse;\">Currency</th>\r\n"
							+ "                  <th style=\"background-color:#98AFC7;border: 1px solid black!important; border-collapse: collapse;\">Amount</th>\r\n"
							+ "              </tr>\r\n" + "          </thead>\r\n" + "          <tbody>\r\n"
							+ "              <tr>\r\n"
							+ "                  <td style=\"background-color:#ADD8E6;border: 1px solid black!important; border-collapse: collapse;\">"
							+ schdulerData.getTransactionid() + "</td>\r\n"
							+ "                  <td style=\"background-color:#ADD8E6;border: 1px solid black!important; border-collapse: collapse;\">"
							+ custTransactionDetails.getlCCurrency() + "</td>\r\n"
							+ "<td style=\"background-color:#ADD8E6;border: 1px solid black!important; border-collapse: collapse;\">"
							+ String.valueOf(custTransactionDetails.getlCValue()) + "</td>\r\n" +

							"              </tr>           \r\n" + "          </tbody>\r\n" + "      </table>\r\n"
							+ "<br>For further details, open Transaction Details -> Rejected Transaction\r\n" + "<br>"
							+ " <br> <br>\r\n" + " <br>Warm Regards,<br>\r\n" + "Team 360tf<br>  \r\n"
							+ "-------------------------------------------------------------------  \r\n"
							+ "<br>Copyright & Disclaimer | Privacy Policy<br><br>  \r\n"
							+ "Please do not reply to this mail as this is automated mail service<br>");
				} else {
					String subjectRejection = "Quote Rejected";
					String baName = quotationDetails.getBankName();
					emailConfigurationDAOImpl.updateSubject(subjectRejection, emailconfigurationBean.getEventId());
					subject.put("Subject", subjectRejection);
					body.put("reason", schdulerData.getReason());
					body.put("htmlBody", "<br>Dear" + " " + custName
							+ "<br><br>You have rejected the quote for transaction ID" + " " + "<b>"
							+ schdulerData.getTransactionid() + "</b>" + " " + "received from" + " " + "<b>" + baName
							+ "</b>.<br>\r\n"
							+ "<br>You can reopen the transaction from Rejected Transaction section. Upon reopening the transaction you can accept available quotes or wait for new quotes"
							+ ".<br>\r\n" + "<br>Warm Regards,<br>\n" + "Team 360tf<br>\n"
							+ "-------------------------------------------------------------------\n"
							+ "<br>Copyright & Disclaimer | Privacy Policy<br><br>\n"
							+ "Please do not reply to this mail as this is automated mail service\r\n" + "<br>");

				}

			}

			String toMail = custEmailID;

			// emailConfigurationDAOImpl.saveCCEmails(uatCcEMail,
			// emailconfigurationBean.getEventId());
			emailConfigurationDAOImpl.saveBCCEmails(uatCcEMail, emailconfigurationBean.getEventId());
			System.out.println("Fetching Configuration for transaction status" + emailconfigurationBean.getSubject()
					+ " :: " + emailconfigurationBean.getEventId());
			ArrayList attachements = new ArrayList();
			ArrayList details = new ArrayList();
			details.add(emailconfigurationBean.getEventId());// 0
			details.add(toMail);// 1
			details.add(subject);// 2
			details.add(body);// 3
			details.add(attachements);

			logger.info("details" + details);
			emailProcessorImpl.saveEmail(details, emailconfigurationBean.getEventId());

			emailSend.getDetailsEmail();

		} catch (SMTPAddressFailedException e) {
			logger.info("=======sendQuotationStatusEmailToCust SMTP ADDRESS Failed Exception============" + e);
			logger.info("sendQuotationStatusEmailToCust Exception" + e);
			e.printStackTrace();
		} catch (Exception e) {
			logger.info("sendQuotationStatusEmailToCust Exception" + e);
			e.printStackTrace();
		}

	}

	public void sendBidRecivedEmailToCust(String emailEvent, NimaiEmailSchedulerAlertToBanks schdulerData,
			String customerEmail) {
		// TODO Auto-generated method stub
		EmailComponentMaster emailconfigurationBean = null;
		NimaiSystemConfig configDetails = null;
		// configDetails= systemConfig.findBySystemId(13);
		configDetails = systemConfig.findBySystemValue("link");
		System.out.println("system value" + configDetails.getSystemEntityValue());
		try {
			// emailconfigurationBean =
			// emailConfigurationDAOImpl.findEventConfiguration(emailEvent);
			emailconfigurationBean = emailComRepo.findEventConfiguration(emailEvent);

			System.out.println(" Fetching Configuration for Reset Password Policy " + emailconfigurationBean);
			String requiredPage = "quoteDetails";
			String quoteLlink = quoteDetailsLink + requiredPage;
			Map<String, String> subject = new HashMap<String, String>();
			Map<String, String> body = new HashMap<String, String>();
			subject.put("Subject", emailconfigurationBean.getSubject());
			body.put("transactionId", schdulerData.getTransactionid());
			body.put("customerName", schdulerData.getCustomerUserName());
			body.put("link", configDetails.getSystemEntityValue());
			String toMail = customerEmail;

//			emailConfigurationDAOImpl.saveCCEmails(uatCcEMail, emailconfigurationBean.getEventId());
			emailConfigurationDAOImpl.saveBCCEmails(uatCcEMail, emailconfigurationBean.getEventId());
			System.out.println("Fetching Configuration for transaction status" + emailconfigurationBean.getSubject()
					+ " :: " + emailconfigurationBean.getEventId());
			ArrayList attachements = new ArrayList();
			ArrayList details = new ArrayList();
			details.add(emailconfigurationBean.getEventId());// 0
			details.add(toMail);// 1
			details.add(subject);// 2
			details.add(body);// 3
			details.add(attachements);
			System.out.println("details" + details);
			emailProcessorImpl.saveEmail(details, emailconfigurationBean.getEventId());

			emailSend.getDetailsEmail();

		} catch (SMTPAddressFailedException e) {
			logger.info("=======sendBidRecivedEmailToCust SMTP ADDRESS Failed Exception============" + e);
			System.out.println("sendBidRecivedEmailToCust Exception" + e);
			e.printStackTrace();
		} catch (Exception e) {

			System.out.println("sendBidRecivedEmailToCust Exception" + e);
			e.printStackTrace();
		}

	}

	public void sendBankDetailstoCustomer(String emailEvent, NimaiEmailSchedulerAlertToBanks schdulerData,
			String customerEmail, QuotationMaster bnakQuotationDetails) {
		logger.info("Inside EmailInsert sendBankDetailstoCustomer method");
		// TODO Auto-generated method stub
		EmailComponentMaster emailconfigurationBean = null;
		try {
			// emailconfigurationBean =
			// emailConfigurationDAOImpl.findEventConfiguration(emailEvent);
			emailconfigurationBean = emailComRepo.findEventConfiguration(emailEvent);

			System.out.println(" Fetching Configuration for Reset Password Policy " + emailconfigurationBean);
			Map<String, String> subject = new HashMap<String, String>();
			Map<String, String> body = new HashMap<String, String>();
			String validaityDate = convertStringToDate(bnakQuotationDetails.getValidityDate());
			String totalQuoteValue = String.valueOf(bnakQuotationDetails.getTotalQuoteValue());
			String quotationId = Float.toString(schdulerData.getQuotationId());
			subject.put("Subject", emailconfigurationBean.getSubject());
			body.put("transactionId", schdulerData.getTransactionid());
			body.put("customerName", schdulerData.getCustomerUserName());
			body.put("bankName", bnakQuotationDetails.getBankName());
			body.put("branchName", bnakQuotationDetails.getBranchName());
			body.put("userId", schdulerData.getCustomerid());
			body.put("quotationId", quotationId);
			body.put("validatyDate", validaityDate);
			body.put("totalQuoteValue", totalQuoteValue);

			String toMail = schdulerData.getCustomerEmail();

			// emailConfigurationDAOImpl.saveCCEmails(uatCcEMail,
			// emailconfigurationBean.getEventId());
			emailConfigurationDAOImpl.saveBCCEmails(uatCcEMail, emailconfigurationBean.getEventId());

			System.out.println("Fetching Configuration for transaction status" + emailconfigurationBean.getSubject()
					+ " :: " + emailconfigurationBean.getEventId());
			ArrayList attachements = new ArrayList();
			ArrayList details = new ArrayList();
			details.add(emailconfigurationBean.getEventId());// 0
			details.add(toMail);// 1
			details.add(subject);// 2
			details.add(body);// 3
			details.add(attachements);

			System.out.println("details" + details);
			emailProcessorImpl.saveEmail(details, emailconfigurationBean.getEventId());

			emailSend.getDetailsEmail();

		} catch (SMTPAddressFailedException e) {
			logger.info("=======sendBankDetailstoCustomer SMTP ADDRESS Failed Exception============" + e);
			System.out.println("sendBankDetailstoCustomer Exception" + e);
			e.printStackTrace();
		} catch (Exception e) {

			System.out.println("sendBankDetailstoCustomer Exception" + e);
			e.printStackTrace();
		}

	}

	public void AdminEmail(AdminBean resetpassword, NimaiMLogin employeeDetails, String link) throws Exception {

		EmailComponentMaster emailconfigurationBean = null;
		try {

			// emailconfigurationBean =
			// emailConfigurationDAOImpl.findEventConfiguration(resetpassword.getEvent());
			emailconfigurationBean = emailComRepo.findEventConfiguration(resetpassword.getEvent());

			System.out.println(" Fetching Configuration for Reset Password Policy " + emailconfigurationBean);
			Map<String, String> subject = new HashMap<String, String>();
			Map<String, String> body = new HashMap<String, String>();

			subject.put("Subject", emailconfigurationBean.getSubject());

			// ArrayList attachements = new ArrayList();
			// attachements.add(new File("D:\\BDO1.jpg"));
			body.put("link", link);
			body.put("username", employeeDetails.getEmpCode().getEmpName());
			body.put("userId", employeeDetails.getEmpCode().getEmpCode());
			String toMail = employeeDetails.getEmpCode().getEmpEmail();

			// emailConfigurationDAOImpl.saveCCEmails(uatCcEMail,
			// emailconfigurationBean.getEventId());
			emailConfigurationDAOImpl.saveBCCEmails(uatCcEMail, emailconfigurationBean.getEventId());

			System.out.println(" Fetching Configuration for Reset Password Policy "
					+ emailconfigurationBean.getSubject() + " :: " + emailconfigurationBean.getEventId());
			ArrayList attachements = new ArrayList();
			ArrayList details = new ArrayList();
			details.add(emailconfigurationBean.getEventId());// 0
			details.add(toMail);// 1
			details.add(subject);// 2
			details.add(body);// 3
			details.add(attachements);
			System.out.println("details" + details);
			emailProcessorImpl.saveEmail(details, emailconfigurationBean.getEventId());

			emailSend.getDetailsEmail();

		} catch (SMTPAddressFailedException e) {
			logger.info("=======AdminEmail SMTP ADDRESS Failed Exception============" + e);
			System.out.println("AdminEmail Exception" + e);
			e.printStackTrace();
		} catch (Exception e) {

			System.out.println("AdminEmail Exception" + e);
			e.printStackTrace();
		}

	}

	public void sendLcStatusEmailData(NimaiEmailSchedulerAlertToBanks schdulerData, NimaiLC transactionDetails,
			NimaiClient clientUserId) {
		// TODO Auto-generated method stub
		EmailComponentMaster emailconfigurationBean = null;
		GenericResponse response = new GenericResponse();
		try {
			// emailconfigurationBean =
			// emailConfigurationDAOImpl.findEventConfiguration(schdulerData.getEmailEvent());
			emailconfigurationBean = emailComRepo.findEventConfiguration(schdulerData.getEmailEvent());
			logger.info("========useraname=======:::" + schdulerData.getCustomerUserName());
			logger.info(" Fetching Configuration for Reset Password Policy " + emailconfigurationBean);
			// String ccEmail = "";
			Map<String, String> subject = new HashMap<String, String>();
			Map<String, String> body = new HashMap<String, String>();
			// String lcValue = Double.toString(transactionDetails.getlCValue());
			String lcValue = new BigDecimal(transactionDetails.getlCValue()).toEngineeringString();

			String lcIssuingDate = convertSToDate(transactionDetails.getlCIssuingDate());
			// String lcExpiryDate = convertSToDate(transactionDetails.getValidity());
			String lcExpiryDate = transactionDetails.getValidity().trim().substring(0, 10).replaceAll("-", "/");
			subject.put("Subject", emailconfigurationBean.getSubject());
			body.put("transactionId", schdulerData.getTransactionid());
			body.put("username", clientUserId.getFirstName());
			body.put("companyname", clientUserId.getCompanyName());
			body.put("userId", schdulerData.getCustomerid());
			body.put("lcIssuingValue", lcValue);
			body.put("lcIssuingDate", lcIssuingDate);
			body.put("lcExpiryDate", lcExpiryDate);

			if (transactionDetails.getRequirementType().equalsIgnoreCase("ConfirmAndDiscount")) {

				body.put("productRequirementName", "Confirmation & Discounting");
			} else if (transactionDetails.getRequirementType().equalsIgnoreCase("Refinance")) {
				body.put("productRequirementName", " Refinancing");
			} else if (transactionDetails.getRequirementType().equalsIgnoreCase("Banker")) {
				body.put("productRequirementName", " Bankers Acceptance ");
			} else {
				body.put("productRequirementName", transactionDetails.getRequirementType());
			}

			body.put("currency", transactionDetails.getlCCurrency());

			String toMail = schdulerData.getCustomerEmail();
			logger.info("Fetching Configuration for transaction status" + emailconfigurationBean.getSubject() + " :: "
					+ emailconfigurationBean.getEventId());

			String emailId = uatCcEMail;

			System.out.println("EmailID frm presented" + emailId);

			emailConfigurationDAOImpl.saveBCCEmails(uatCcEMail, emailconfigurationBean.getEventId());

			ArrayList attachements = new ArrayList();
			ArrayList details = new ArrayList();
			details.add(emailconfigurationBean.getEventId());// 0
			details.add(toMail);// 1
			details.add(subject);// 2
			details.add(body);// 3
			details.add(attachements);

			logger.info("details" + details);

			emailProcessorImpl.saveEmail(details, emailconfigurationBean.getEventId());

			emailSend.getDetailsEmail();

		} catch (SMTPAddressFailedException e) {
			logger.info("=======SMTP ADDRESS Failed Exception============" + e);
			logger.info("Exception" + e);
			e.printStackTrace();
		} catch (Exception e) {
			logger.info("=======Exception============" + e);
			e.printStackTrace();
		}

	}

	public void sendCustSPlanEmail(NimaiEmailScheduler schdulerData, NimaiClient clientUseId,
			NimaiSubscriptionDetails subDetails, OnlinePayment paymentDetails, NimaiSystemConfig configDetails,
			String imagePath) {
		// TODO Auto-generated method stub
		EmailComponentMaster emailconfigurationBean = null;
		logger.info("=======Inside sendCustSPlanEmail============" + schdulerData.toString());
		GenericResponse response = new GenericResponse();
		try {
			System.out.println("initial date" + schdulerData.getSubscriptionStartDate());
			InvoiceTemplate link = new InvoiceTemplate();
			ArrayList attachements = new ArrayList();
			if (subDetails.getPaymentMode().equalsIgnoreCase("Wire")
					&& !clientUseId.getPaymentStatus().equalsIgnoreCase("Rejected")) {
				System.out.println("Inside Wire mode");
				emailconfigurationBean = emailComRepo.findEventConfiguration(AppConstants.CUST_SPLAN_EVENT_Wire);
				attachements.add(new File(
						link.generateSplanInvoiceTemplate(subDetails, paymentDetails, configDetails, imagePath)));
			} else if (subDetails.getPaymentMode().equalsIgnoreCase("Credit")) {
				System.out.println("Inside credit mode");
				emailconfigurationBean = emailComRepo.findEventConfiguration(schdulerData.getEvent());
				attachements.add(new File(
						link.generateSplanInvoiceTemplate(subDetails, paymentDetails, configDetails, imagePath)));
			} else if (clientUseId.getPaymentStatus().equalsIgnoreCase("Rejected")) {
				System.out.println("Inside Rejected mode");
				emailconfigurationBean = emailComRepo.findEventConfiguration(AppConstants.CUST_SPLAN_Rejected);
			}

			// emailconfigurationBean=emailComRepo.findEventConfiguration(schdulerData.getEvent());
			logger.info("========useraname=======:::" + schdulerData.getUserName());
			logger.info(" Fetching Configuration for sendCustSPlanEmail " + emailconfigurationBean);

			String ccEmail = "";
			/*
			 * invoice code if not completed please commnet dhiraj
			 */
			Date dnow = new Date();
			String sPLanStartdate = new SimpleDateFormat("dd/MM/yyyy").format(dnow);
			System.out.println("initial date" + schdulerData.getSubscriptionStartDate());
			Map<String, String> subject = new HashMap<String, String>();
			Map<String, String> body = new HashMap<String, String>();
			// String sPlanStartDate = convertStringLocalToDate(ldtForCurrentDate);

			// String sPlanStartDate = convertStringToDate
			// (subDetails.getSubscriptionStartDate());
			System.out.println("plan start date======== " + sPLanStartdate);
			Utils endDate = new Utils();
			int year = endDate.getNoOfyears(subDetails.getSubscriptionValidity());
			int month = endDate.getNoOfMonths(subDetails.getSubscriptionValidity());
			System.out.println(year);
			System.out.println(month);

			Calendar cal = Calendar.getInstance();
			Date today = cal.getTime();
			cal.add(Calendar.YEAR, year);
			cal.add(Calendar.MONTH, month);
			Date sPlanEndDate = cal.getTime();
			logger.debug("===========SPLANENDDATE" + sPlanEndDate);
			// String sPLanEndDate =
			// convertStringToDate(subDetails.getSubscriptionEndDate());
			subject.put("Subject", emailconfigurationBean.getSubject());

			logger.info("==========link:"
					+ link.generateSplanInvoiceTemplate(subDetails, paymentDetails, configDetails, imagePath));

			body.put("username", schdulerData.getUserName());
			body.put("suscriptionId", schdulerData.getSubscriptionId());
			body.put("subscriptionName", schdulerData.getSubscriptionName());
			body.put("relationshipManager", schdulerData.getRelationshipManager());
			body.put("customerSupport", schdulerData.getCustomerSupport());
			body.put("splanStartDate", sPLanStartdate);
			body.put("splanEndDate", convertStringToDate(sPlanEndDate));
			body.put("splanValidity", schdulerData.getSubscriptionValidity());
			body.put("splanAmount", schdulerData.getSubscriptionAmount());

			String toMail = schdulerData.getEmailId();
			System.out.println("Fetching Configuration for transaction status" + emailconfigurationBean.getSubject()
					+ " :: " + emailconfigurationBean.getEventId());
			String emailId = presentEmailID(clientUseId);
			emailId = emailId;
			System.out.println("================UATCCEMMAIL" + uatCcEMail);
			System.out.println("EmailID frm presented" + emailId);
			if (emailId != null && !emailId.trim().isEmpty()) {
				System.out.println("CC email Details inside sendCustSPlanEmail details" + emailId);
				emailConfigurationDAOImpl.saveCCEmails(emailId, emailconfigurationBean.getEventId());
				// emailConfigurationDAOImpl.saveBCCEmails(emailId,
				// emailconfigurationBean.getEventId());
			}
			emailConfigurationDAOImpl.saveBCCEmails(uatCcEMail, emailconfigurationBean.getEventId());

			ArrayList details = new ArrayList();
			details.add(emailconfigurationBean.getEventId());// 0
			details.add(toMail);// 1
			details.add(subject);// 2
			details.add(body);// 3
			details.add(attachements);// 4
			System.out.println("details" + details);
			logger.info("=======sendCustSPlanEmail details to send============" + details);
			emailProcessorImpl.saveEmail(details, emailconfigurationBean.getEventId());

			emailSend.getDetailsEmail();

		} catch (SMTPAddressFailedException e) {
			logger.info("=======sendCustSPlanEmail SMTP ADDRESS Failed Exception============" + e);
			response.setMessage("sendCustSPlanEmail SMTP Address Failed Exception");

			System.out.println("Exception" + e);
			e.printStackTrace();
		} catch (Exception e) {
			logger.info("=======sendCustSPlanEmail Exception============" + e);
			e.printStackTrace();
		}

	}

	public String presentEmailID(NimaiClient client) {
		String ccEmails = "";
		if (client.getEmailAddress1() == null && client.getEmailAddress2() != null
				&& client.getEmailAddress3() != null) {
			ccEmails = client.getEmailAddress2() + "," + client.getEmailAddress3();
		} else if (client.getEmailAddress1() != null && client.getEmailAddress2() == null
				&& client.getEmailAddress3() == null) {
			ccEmails = client.getEmailAddress1();
		} else if (client.getEmailAddress2() == null && client.getEmailAddress1() != null
				&& client.getEmailAddress3() != null) {
			ccEmails = client.getEmailAddress1() + "," + client.getEmailAddress3();
		} else if (client.getEmailAddress2() != null && client.getEmailAddress1() == null
				&& client.getEmailAddress3() == null) {
			ccEmails = client.getEmailAddress2();
		} else if (client.getEmailAddress3() == null && client.getEmailAddress1() != null
				&& client.getEmailAddress2() != null) {
			ccEmails = client.getEmailAddress1() + "," + client.getEmailAddress2();
		} else if (client.getEmailAddress3() != null && client.getEmailAddress1() == null
				&& client.getEmailAddress2() == null) {
			ccEmails = client.getEmailAddress3();

		} else if (client.getEmailAddress1() != null && client.getEmailAddress2() != null
				&& client.getEmailAddress3() != null) {
			ccEmails = client.getEmailAddress1() + "," + client.getEmailAddress2() + "," + client.getEmailAddress3();
		} else if (client.getEmailAddress1() != null && client.getEmailAddress2() == null
				&& client.getEmailAddress3() == null) {
			ccEmails = client.getEmailAddress1();
		} else if (client.getEmailAddress2() != null && client.getEmailAddress1() == null
				&& client.getEmailAddress3() == null) {
			ccEmails = client.getEmailAddress2();
		} else if (client.getEmailAddress3() != null && client.getEmailAddress1() == null
				&& client.getEmailAddress2() == null) {
			ccEmails = client.getEmailAddress3();
		} else if (client.getEmailAddress3() == null && client.getEmailAddress1() == null
				&& client.getEmailAddress2() == null) {
			System.out.println("inside present emailId nll method");
			return null;
		}
		System.out.println("ccEmails from present emailId method:" + ccEmails);
		return ccEmails;

	}

	public void sendKycEmail(String event, NimaiClient clientUserId, NimaiEmailScheduler schdulerData) {
		EmailComponentMaster emailconfigurationBean = null;
		try {
			emailconfigurationBean = emailComRepo.findEventConfiguration(schdulerData.getEvent());

			System.out.println(" Fetching Configuration for Reset Password Policy " + emailconfigurationBean);
			Map<String, String> subject = new HashMap<String, String>();
			Map<String, String> body = new HashMap<String, String>();

			subject.put("Subject", emailconfigurationBean.getSubject());

			String userID = schdulerData.getUserid();
			String custName = schdulerData.getUserName();
			String rmname = schdulerData.getrMName();
			String toMail = schdulerData.getEmailId();
			String rmEmail = schdulerData.getrMemailId();
			ArrayList attachements = new ArrayList();

			body.put("htmlBody", "<br>Dear" + " " + custName + ", <br><br>"
					+ "Your kyc document received successfully,<br>360tf Team will update the approval status of kyc document within two working days"
					+ "<br><br>\r\n" + "<br>");

			if (schdulerData.getEvent().equalsIgnoreCase("KYC_REJECT")) {
				body.put("htmlBody", "<br>Dear" + " " + custName + ", <br><br>"
						+ "Your kyc has been rejected by 360tf admin team,<br>Please contact to your relationship manager for more details"
						+ " and resubmit document to avoid delay." + "<br><br>\r\n" + "<br>");
			}
			if (schdulerData.getEvent().equalsIgnoreCase("KYC_APPROVED")) {
				body.put("htmlBody", "<br>Dear" + " " + custName + ", <br><br>"
						+ "Your kyc has been approved by 360tf admin team,<br>You are ready to go for the transaction placement."
						+ "<br><br>\r\n" + "<br>");
			}

			// String emailId = presentEmailID(clientUserId);
			String emailId = uatCcEMail;

//			System.out.println("EmailID frm presented" + emailId);
//			if (emailId != null && !emailId.trim().isEmpty()) {
			System.out.println("CC email Details inside sendCustSPlanEmail details" + emailId);
			// emailConfigurationDAOImpl.saveCCEmails(emailId,
			// emailconfigurationBean.getEventId());
			emailConfigurationDAOImpl.saveBCCEmails(emailId, emailconfigurationBean.getEventId());
//			}

			System.out.println("Fetching Configuration for sendKycEmail" + emailconfigurationBean.getSubject() + " :: "
					+ emailconfigurationBean.getEventId());

			ArrayList details = new ArrayList();
			details.add(emailconfigurationBean.getEventId());// 0
			details.add(toMail);// 1
			details.add(subject);// 2
			details.add(body);// 3
			details.add(attachements);
			System.out.println("details" + details);
			emailProcessorImpl.saveEmail(details, emailconfigurationBean.getEventId());
			emailSend.getDetailsEmail();

		} catch (SMTPAddressFailedException e) {
			logger.info("=======sendKycEmail SMTP ADDRESS Failed Exception============" + e);
			System.out.println("sendKycEmail Exception" + e);
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("sendKycStatusEmail Exception" + e);
			e.printStackTrace();
		}
	}

	public void sendEmailToRm(String event, NimaiClient clientUserId, NimaiEmailScheduler schdulerData) {
		// TODO Auto-generated method stub
		EmailComponentMaster emailconfigurationBean = null;
		try {
			emailconfigurationBean = emailComRepo.findEventConfiguration(schdulerData.getEvent());

			System.out.println(" Fetching Configuration for Reset Password Policy " + emailconfigurationBean);

			Map<String, String> subject = new HashMap<String, String>();
			Map<String, String> body = new HashMap<String, String>();
			ArrayList attachements = new ArrayList();
			subject.put("Subject", emailconfigurationBean.getSubject());
			String rmname = schdulerData.getrMName();
			String toMail = schdulerData.getrMemailId();
			// emailConfigurationDAOImpl.saveCCEmails(uatCcEMail,
			// emailconfigurationBean.getEventId());
			emailConfigurationDAOImpl.saveBCCEmails(uatCcEMail, emailconfigurationBean.getEventId());
			String reason = schdulerData.getReason();
			body.put("htmlBody",
					"<br>Dear" + " " + rmname + ", <br><br>" + "Kyc has upload against userId is" + " "
							+ schdulerData.getUserid() + " " + ",<br>NIMAI Team please verify the customer kyc"
							+ "<br><br>\r\n" + "<br>");

			if (schdulerData.getEvent().equalsIgnoreCase("KYC_REJECT")) {
				body.put("htmlBody", "<br>Dear" + " " + rmname + ", <br><br>" + "You have reject kyc whose userId is,"
						+ " " + schdulerData.getUserid() + "<br><br>Reason for rejecting is" + " " + reason + "<br>\r\n"
						+ "<br>Please notify customer to resubmitt of kyc document." + "<br><br>\r\n" + "<br>");
			}
			if (schdulerData.getEvent().equalsIgnoreCase("KYC_APPROVED")) {
				body.put("htmlBody", "<br>Dear" + " " + rmname + ", <br><br>"
						+ "You have approve the kyc whose userId is," + " " + schdulerData.getUserid() + "<br>");
			}

			ArrayList details = new ArrayList();
			details.add(emailconfigurationBean.getEventId());// 0
			details.add(toMail);// 1
			details.add(subject);// 2
			details.add(body);// 3
			details.add(attachements);
			System.out.println("details" + details);
			emailProcessorImpl.saveEmail(details, emailconfigurationBean.getEventId());
			emailSend.getDetailsEmail();
		} catch (SMTPAddressFailedException e) {
			logger.info("=======sendEmailToRm SMTP ADDRESS Failed Exception============" + e);
			System.out.println("sendEmailToRm Exception" + e);
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("sendEmailToRm Exception" + e);
			e.printStackTrace();
		}

	}

	public boolean sendEodReport(String fileLocation, String key) {
		// TODO Auto-generated method stub
		EmailComponentMaster emailconfigurationBean = null;
		logger.info("=======Inside sendEodReport============");
		GenericResponse response = new GenericResponse();
		try {
			// emailconfigurationBean =
			// emailConfigurationDAOImpl.findEventConfiguration("EOD_REPORT");
			emailconfigurationBean = emailComRepo.findEventConfiguration("EOD_REPORT");

			System.out.println(" Fetching Configuration for sendCustSPlanEmail " + emailconfigurationBean);
			String ccEmail = "";
			Map<String, String> subject = new HashMap<String, String>();
			Map<String, String> body = new HashMap<String, String>();
			subject.put("Subject", emailconfigurationBean.getSubject());

			ArrayList attachements = new ArrayList();
			attachements.add(fileLocation);

//			body.put("username", schdulerData.getUserName());
//			body.put("suscriptionId", schdulerData.getSubscriptionId());
//			body.put("subscriptionName", schdulerData.getSubscriptionName());
//			body.put("relationshipManager", schdulerData.getRelationshipManager());
//			body.put("customerSupport", schdulerData.getCustomerSupport());
//			body.put("splanStartDate", sPlanStartDate);
//			body.put("splanEndDate", sPLanEndDate);
//			body.put("splanValidity", schdulerData.getSubscriptionValidity());
//			body.put("splanAmount", schdulerData.getSubscriptionAmount());

			String toMail = "djjagtap123@gmail.com";
			System.out.println("Fetching Configuration for transaction status" + emailconfigurationBean.getSubject()
					+ " :: " + emailconfigurationBean.getEventId());
			// String emailId = presentEmailID(clientUseId);
			// System.out.println("EmailID frm presented" + emailId);
//			if (emailId != null && !emailId.trim().isEmpty()) {
//				System.out.println("CC email Details inside sendCustSPlanEmail details" + emailId);
//				emailConfigurationDAOImpl.saveCCEmails(emailId, emailconfigurationBean.getEventId());
//			}

			ArrayList details = new ArrayList();
			details.add(emailconfigurationBean.getEventId());// 0
			details.add(toMail);// 1
			details.add(subject);// 2
			details.add(body);// 3
			details.add(attachements);
			System.out.println("details" + details);

			emailProcessorImpl.saveEmail(details, emailconfigurationBean.getEventId());

			emailSend.getDetailsEmail();
			return true;
		} catch (SMTPAddressFailedException e) {
			logger.info("=======sendCustSPlanEmail SMTP ADDRESS Failed Exception============" + e);
			response.setMessage("sendCustSPlanEmail SMTP Address Failed Exception");

			System.out.println("Exception" + e);
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			logger.info("=======sendCustSPlanEmail Exception============" + e);
			e.printStackTrace();
			return false;
		}

	}

	public void sendAccEmail(UserRegistrationBean userBean, NimaiClient nimaiClientdetails) {
		EmailComponentMaster emailconfigurationBean = null;
		try {

			// emailconfigurationBean =
			// emailConfigurationDAOImpl.findEventConfiguration(userBean.getEvent());
			emailconfigurationBean = emailComRepo.findEventConfiguration(userBean.getEvent());

			System.out.println(" Fetching Configuration for Reset Password Policy " + emailconfigurationBean);
			Map<String, String> subject = new HashMap<String, String>();
			Map<String, String> body = new HashMap<String, String>();

			subject.put("Subject", emailconfigurationBean.getSubject());
			body.put("username", nimaiClientdetails.getFirstName());
			body.put("accountnumber", "123456789");
			body.put("ifsccode", "5678435");
			body.put("bankname", "Axis bank");
//			body.put("splanStartDate", sPlanStartDate);
//			body.put("splanEndDate", sPLanEndDate);

			// ArrayList attachements = new ArrayList();
			// attachements.add(new File("D:\\EmailSplan_changes_Checklist.xlsx"));
			// body.put("link", Link);
//			body.put("emailId", userBean.getEmailId());
//			if (subsidiaryBean.getEvent().equalsIgnoreCase("ADD_REFER")) {
//				body.put("username", subsidiaryBean.getUserId());
//			}
			String toMail = nimaiClientdetails.getEmailAddress();

			// emailConfigurationDAOImpl.saveCCEmails(uatCcEMail,
			// emailconfigurationBean.getEventId());

			emailConfigurationDAOImpl.saveBCCEmails(uatCcEMail, emailconfigurationBean.getEventId());
			System.out.println(" Fetching Configuration for Reset Password Policy "
					+ emailconfigurationBean.getSubject() + " :: " + emailconfigurationBean.getEventId());
			ArrayList attachements = new ArrayList();
			ArrayList details = new ArrayList();
			details.add(emailconfigurationBean.getEventId());// 0
			details.add(toMail);// 1
			details.add(subject);// 2
			details.add(body);// 3
			details.add(attachements);
			System.out.println("details" + details);
			emailProcessorImpl.saveEmail(details, emailconfigurationBean.getEventId());

			emailSend.getDetailsEmail();

		} catch (SMTPAddressFailedException e) {
			logger.info("=======SMTP ADDRESS Failed Exception============" + e);

			System.out.println("Exception" + e);
			e.printStackTrace();
		} catch (Exception e) {

			System.out.println("sendSubAccAcivationLink Exception" + e);
			e.printStackTrace();
		}

	}

	public void sendReferAccAcivationLink(String Link, SubsidiaryBean subsidiaryBean, NimaiClient referUsrIdDetails) {
		EmailComponentMaster emailconfigurationBean = null;
		try {
			System.out.println("Link: " + Link);
			System.out.println("SubsidiaryBean: " + subsidiaryBean);
//System.out.println("NimaiClient: "+referUsrIdDetails);
			// emailconfigurationBean =
			// emailConfigurationDAOImpl.findEventConfiguration(subsidiaryBean.getEvent());

			emailconfigurationBean = emailComRepo.findEventConfiguration(subsidiaryBean.getEvent());

			System.out.println(" Fetching Configuration for Reset Password Policy " + emailconfigurationBean);
			Map<String, String> subject = new HashMap<String, String>();
			Map<String, String> body = new HashMap<String, String>();

			subject.put("Subject", emailconfigurationBean.getSubject());

			// ArrayList attachements = new ArrayList();
			// attachements.add(new File("D:\\EmailSplan_changes_Checklist.xlsx"));
			body.put("link", Link);
			body.put("emailId", subsidiaryBean.getEmailId());
			if (subsidiaryBean.getEvent().equalsIgnoreCase("ADD_REFER")) {
				body.put("referUserId", referUsrIdDetails.getUserid());
				body.put("productRequirementName", referUsrIdDetails.getFirstName());
				System.out.println("Refer user id:" + referUsrIdDetails.getUserid());
				body.put("username", subsidiaryBean.getUserId());
			}
			String toMail = subsidiaryBean.getEmailId();

			// emailConfigurationDAOImpl.saveCCEmails(uatCcEMail,
			// emailconfigurationBean.getEventId());
			emailConfigurationDAOImpl.saveBCCEmails(uatCcEMail, emailconfigurationBean.getEventId());

			System.out.println(" Fetching Configuration for Reset Password Policy "
					+ emailconfigurationBean.getSubject() + " :: " + emailconfigurationBean.getEventId());
			ArrayList attachements = new ArrayList();
			ArrayList details = new ArrayList();
			details.add(emailconfigurationBean.getEventId());// 0
			details.add(toMail);// 1
			details.add(subject);// 2
			details.add(body);// 3
			details.add(attachements);
			System.out.println("details" + details);
			emailProcessorImpl.saveEmail(details, emailconfigurationBean.getEventId());

			emailSend.getDetailsEmail();

		} catch (SMTPAddressFailedException e) {
			logger.info("=======SMTP ADDRESS Failed Exception============" + e);

			System.out.println("Exception" + e);
			e.printStackTrace();
		} catch (Exception e) {

			System.out.println("sendSubAccAcivationLink Exception" + e);
			e.printStackTrace();
		}

	}

	public void sendBranchUserPasscodeLink(String bUlink, BranchUserRequest branchUserLink, NimaiClient clientDetails,
			String passcode) {
		EmailComponentMaster emailconfigurationBean = null;
		try {
			// emailconfigurationBean=emailComRepo.findEventConfiguration(branchUserLink.getEvent());

			emailconfigurationBean = emailComRepo.findEventConfiguration(branchUserLink.getEvent());

			System.out.println(" Fetching Configuration for Reset Password Policy " + emailconfigurationBean);
			Map<String, String> subject = new HashMap<String, String>();
			Map<String, String> body = new HashMap<String, String>();

			subject.put("Subject", emailconfigurationBean.getSubject());

			// ArrayList attachements = new ArrayList();
			// attachements.add(new File("D:\\EmailSplan_changes_Checklist.xlsx"));
			body.put("passcode", passcode);
			body.put("link", bUlink);
			body.put("username", clientDetails.getFirstName());

			body.put("emailId", clientDetails.getEmailAddress());

			String toMail = clientDetails.getEmailAddress();

			// emailConfigurationDAOImpl.saveCCEmails(uatCcEMail,
			// emailconfigurationBean.getEventId());
			emailConfigurationDAOImpl.saveBCCEmails(uatCcEMail, emailconfigurationBean.getEventId());

			System.out.println("Fetching Configuration for Reset Password Policy " + emailconfigurationBean.getSubject()
					+ " :: " + emailconfigurationBean.getEventId());
			ArrayList attachements = new ArrayList();
			ArrayList details = new ArrayList();
			details.add(emailconfigurationBean.getEventId());// 0
			details.add(toMail);// 1
			details.add(subject);// 2
			details.add(body);// 3
			details.add(attachements);
			System.out.println("details" + details);
			emailProcessorImpl.saveEmail(details, emailconfigurationBean.getEventId());

			emailSend.getDetailsEmail();

		} catch (SMTPAddressFailedException e) {
			logger.info("=======SMTP ADDRESS Failed Exception============" + e);

			System.out.println("Exception" + e);
			e.printStackTrace();
		} catch (Exception e) {

			System.out.println("sendBranchUserActivationLink Exception" + e);
			e.printStackTrace();
		}

	}

	public void reSendReferAccAcivationLink(String referLink, SubsidiaryBean registerLink, NimaiClient clientUseId) {
		// TODO Auto-generated method stub
		EmailComponentMaster emailconfigurationBean = null;
		try {
			System.out.println("Link: " + referLink);
			System.out.println("SubsidiaryBean: " + clientUseId);
//System.out.println("NimaiClient: "+referUsrIdDetails);
			/// emailconfigurationBean =
			/// emailConfigurationDAOImpl.findEventConfiguration(registerLink.getEvent());
			emailconfigurationBean = emailComRepo.findEventConfiguration(registerLink.getEvent());

			System.out.println(" Fetching Configuration for Reset Password Policy " + emailconfigurationBean);
			Map<String, String> subject = new HashMap<String, String>();
			Map<String, String> body = new HashMap<String, String>();

			subject.put("Subject", emailconfigurationBean.getSubject());

			// ArrayList attachements = new ArrayList();
			// attachements.add(new File("D:\\EmailSplan_changes_Checklist.xlsx"));
			body.put("link", referLink);
			body.put("emailId", clientUseId.getEmailAddress());
			if (registerLink.getEvent().equalsIgnoreCase("ADD_REFER")) {
				body.put("referUserId", clientUseId.getUserid());
				System.out.println("Refer user id:" + clientUseId.getUserid());
				body.put("username", clientUseId.getCompanyName());
			}
			String toMail = clientUseId.getEmailAddress();

			// emailConfigurationDAOImpl.saveCCEmails(uatCcEMail,
			// emailconfigurationBean.getEventId());
			emailConfigurationDAOImpl.saveBCCEmails(uatCcEMail, emailconfigurationBean.getEventId());

			System.out.println(" Fetching Configuration for Reset Password Policy "
					+ emailconfigurationBean.getSubject() + " :: " + emailconfigurationBean.getEventId());
			ArrayList attachements = new ArrayList();
			ArrayList details = new ArrayList();
			details.add(emailconfigurationBean.getEventId());// 0
			details.add(toMail);// 1
			details.add(subject);// 2
			details.add(body);// 3
			details.add(attachements);
			System.out.println("details" + details);
			emailProcessorImpl.saveEmail(details, emailconfigurationBean.getEventId());

			emailSend.getDetailsEmail();

		} catch (SMTPAddressFailedException e) {
			logger.info("=======SMTP ADDRESS Failed Exception============" + e);

			System.out.println("Exception" + e);
			e.printStackTrace();
		} catch (Exception e) {

			System.out.println("sendSubAccAcivationLink Exception" + e);
			e.printStackTrace();
		}

	}

	public void sendSubEmailToParentUser(NimaiEmailScheduler schdulerData, NimaiClient clientUseId, String parentName,
			NimaiClient subDetails) {
		// TODO Auto-generated method stub
		EmailComponentMaster emailconfigurationBean = null;
		try {

			// emailconfigurationBean=emailComRepo.findEventConfiguration(schdulerData.getEvent());
			emailconfigurationBean = emailComRepo.findEventConfiguration(schdulerData.getEvent());

			System.out.println(" Fetching Configuration for Reset Password Policy " + emailconfigurationBean);
			Map<String, String> subject = new HashMap<String, String>();
			Map<String, String> body = new HashMap<String, String>();

			subject.put("Subject", emailconfigurationBean.getSubject());

			// ArrayList attachements = new ArrayList();
			// attachements.add(new File("D:\\EmailSplan_changes_Checklist.xlsx"));

			body.put("firstname", schdulerData.getSubFirstName());
			body.put("lastname", schdulerData.getSubLastName());
			body.put("country", schdulerData.getSubCountry());
			body.put("mobileNo", schdulerData.getSubMobile());
			body.put("landline", schdulerData.getSubLandLine());
			body.put("subEmail", schdulerData.getSubOfficailEmail());
			body.put("username", schdulerData.getUserName());

			if (schdulerData.getEvent().equalsIgnoreCase("SUBSIDIARY_ACTIVATION_ALERT")) {
				body.put("firstname", subDetails.getFirstName());
				body.put("lastname", subDetails.getLastName());
				body.put("country", subDetails.getCountryName());
				body.put("mobileNo", subDetails.getMobileNumber());
				body.put("landline", subDetails.getLandline());
				body.put("subEmail", subDetails.getEmailAddress());
				body.put("username", parentName);

			}
			String toMail = clientUseId.getEmailAddress();

			// emailConfigurationDAOImpl.saveCCEmails(uatCcEMail,
			// emailconfigurationBean.getEventId());
			emailConfigurationDAOImpl.saveBCCEmails(uatCcEMail, emailconfigurationBean.getEventId());

			System.out.println(" Fetching Configuration for Reset Password Policy "
					+ emailconfigurationBean.getSubject() + " :: " + emailconfigurationBean.getEventId());
			ArrayList attachements = new ArrayList();
			ArrayList details = new ArrayList();
			details.add(emailconfigurationBean.getEventId());// 0
			details.add(toMail);// 1
			details.add(subject);// 2
			details.add(body);// 3
			details.add(attachements);
			System.out.println("details" + details);
			emailProcessorImpl.saveEmail(details, emailconfigurationBean.getEventId());

			emailSend.getDetailsEmail();

		} catch (SMTPAddressFailedException e) {
			logger.info("=======SMTP ADDRESS Failed Exception============" + e);

			System.out.println("Exception" + e);
			e.printStackTrace();
		} catch (Exception e) {

			System.out.println("sendSubAccAcivationLink Exception" + e);
			e.printStackTrace();
		}

	}

	public void sendEmailToRm(NimaiEmailScheduler schdulerData) {
		// TODO Auto-generated method stub
		EmailComponentMaster emailconfigurationBean = null;
		try {

			// emailconfigurationBean=emailComRepo.findEventConfiguration(schdulerData.getEvent());
			emailconfigurationBean = emailComRepo.findEventConfiguration(schdulerData.getEvent());

			System.out.println(" Fetching Configuration for Reset Password Policy " + emailconfigurationBean);
			Map<String, String> subject = new HashMap<String, String>();
			Map<String, String> body = new HashMap<String, String>();

			subject.put("Subject", emailconfigurationBean.getSubject());

			// ArrayList attachements = new ArrayList();
			// attachements.add(new File("D:\\BDO1.jpg"));
			body.put("username", schdulerData.getrMName());
			body.put("userId", schdulerData.getUserid());
			body.put("companyname", schdulerData.getUserName());
			body.put("country", schdulerData.getsPLanCountry());
			String toMail = schdulerData.getrMemailId();

			// emailConfigurationDAOImpl.saveCCEmails(uatCcEMail,
			// emailconfigurationBean.getEventId());
			emailConfigurationDAOImpl.saveBCCEmails(uatCcEMail, emailconfigurationBean.getEventId());

			System.out.println(" Fetching Configuration for Reset Password Policy "
					+ emailconfigurationBean.getSubject() + " :: " + emailconfigurationBean.getEventId());
			ArrayList attachements = new ArrayList();
			ArrayList details = new ArrayList();
			details.add(emailconfigurationBean.getEventId());// 0
			details.add(toMail);// 1
			details.add(subject);// 2
			details.add(body);// 3
			details.add(attachements);
			System.out.println("details" + details);
			emailProcessorImpl.saveEmail(details, emailconfigurationBean.getEventId());

			emailSend.getDetailsEmail();

		} catch (SMTPAddressFailedException e) {
			logger.info("=======AdminEmail SMTP ADDRESS Failed Exception============" + e);
			System.out.println("AdminEmail Exception" + e);
			e.printStackTrace();
		} catch (Exception e) {

			System.out.println("AdminEmail Exception" + e);
			e.printStackTrace();
		}

	}

	public void sendEmailToRmRE(NimaiEmailScheduler schdulerData) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		EmailComponentMaster emailconfigurationBean = null;
		try {

			// emailconfigurationBean=emailComRepo.findEventConfiguration(schdulerData.getEvent());
			emailconfigurationBean = emailComRepo.findEventConfiguration(schdulerData.getEvent());

			System.out.println(" Fetching Configuration for Reset Password Policy " + emailconfigurationBean);
			Map<String, String> subject = new HashMap<String, String>();
			Map<String, String> body = new HashMap<String, String>();

			subject.put("Subject", emailconfigurationBean.getSubject());

			// ArrayList attachements = new ArrayList();
			// attachements.add(new File("D:\\BDO1.jpg"));
			body.put("username", schdulerData.getrMName());
			body.put("userId", schdulerData.getUserid());
			body.put("companyname", schdulerData.getUserName());
			body.put("country", schdulerData.getsPLanCountry());
			String toMail = schdulerData.getrMemailId();

			// emailConfigurationDAOImpl.saveCCEmails(uatCcEMail,
			// emailconfigurationBean.getEventId());
			emailConfigurationDAOImpl.saveBCCEmails(uatCcEMail, emailconfigurationBean.getEventId());

			System.out.println(" Fetching Configuration for Reset Password Policy "
					+ emailconfigurationBean.getSubject() + " :: " + emailconfigurationBean.getEventId());
			ArrayList attachements = new ArrayList();
			ArrayList details = new ArrayList();
			details.add(emailconfigurationBean.getEventId());// 0
			details.add(toMail);// 1
			details.add(subject);// 2
			details.add(body);// 3
			details.add(attachements);
			System.out.println("details" + details);
			emailProcessorImpl.saveEmail(details, emailconfigurationBean.getEventId());

			emailSend.getDetailsEmail();

		} catch (SMTPAddressFailedException e) {
			logger.info("=======AdminEmail SMTP ADDRESS Failed Exception============" + e);
			System.out.println("AdminEmail Exception" + e);
			e.printStackTrace();
		} catch (Exception e) {

			System.out.println("AdminEmail Exception" + e);
			e.printStackTrace();
		}

	}

	public void sendEmailToRmBC(NimaiEmailScheduler schdulerData) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		EmailComponentMaster emailconfigurationBean = null;
		try {

			emailconfigurationBean = emailComRepo.findEventConfiguration(schdulerData.getEvent());

			System.out.println(" Fetching Configuration for Reset Password Policy " + emailconfigurationBean);
			Map<String, String> subject = new HashMap<String, String>();
			Map<String, String> body = new HashMap<String, String>();

			subject.put("Subject", emailconfigurationBean.getSubject());

			// ArrayList attachements = new ArrayList();
			// attachements.add(new File("D:\\BDO1.jpg"));
			body.put("username", schdulerData.getrMName());
			body.put("userId", schdulerData.getUserid());
			body.put("companyname", schdulerData.getUserName());
			body.put("country", schdulerData.getsPLanCountry());
			String toMail = schdulerData.getrMemailId();

			// emailConfigurationDAOImpl.saveCCEmails(uatCcEMail,
			// emailconfigurationBean.getEventId());
			emailConfigurationDAOImpl.saveBCCEmails(uatCcEMail, emailconfigurationBean.getEventId());

			System.out.println(" Fetching Configuration for Reset Password Policy "
					+ emailconfigurationBean.getSubject() + " :: " + emailconfigurationBean.getEventId());
			ArrayList attachements = new ArrayList();
			ArrayList details = new ArrayList();
			details.add(emailconfigurationBean.getEventId());// 0
			details.add(toMail);// 1
			details.add(subject);// 2
			details.add(body);// 3
			details.add(attachements);
			System.out.println("details" + details);
			emailProcessorImpl.saveEmail(details, emailconfigurationBean.getEventId());

			emailSend.getDetailsEmail();

		} catch (SMTPAddressFailedException e) {
			logger.info("=======AdminEmail SMTP ADDRESS Failed Exception============" + e);
			System.out.println("AdminEmail Exception" + e);
			e.printStackTrace();
		} catch (Exception e) {

			System.out.println("AdminEmail Exception" + e);
			e.printStackTrace();
		}

	}

	public void sendEmailToRmBA(NimaiEmailScheduler schdulerData) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		EmailComponentMaster emailconfigurationBean = null;
		try {

			emailconfigurationBean = emailComRepo.findEventConfiguration(schdulerData.getEvent());

			System.out.println(" Fetching Configuration for Reset Password Policy " + emailconfigurationBean);
			Map<String, String> subject = new HashMap<String, String>();
			Map<String, String> body = new HashMap<String, String>();

			subject.put("Subject", emailconfigurationBean.getSubject());

			// ArrayList attachements = new ArrayList();
			// attachements.add(new File("D:\\BDO1.jpg"));
			body.put("username", schdulerData.getrMName());
			body.put("userId", schdulerData.getUserid());
			body.put("companyname", schdulerData.getUserName());
			body.put("country", schdulerData.getsPLanCountry());
			String toMail = schdulerData.getrMemailId();

			// emailConfigurationDAOImpl.saveCCEmails(uatCcEMail,
			// emailconfigurationBean.getEventId());
			emailConfigurationDAOImpl.saveBCCEmails(uatCcEMail, emailconfigurationBean.getEventId());

			System.out.println(" Fetching Configuration for Reset Password Policy "
					+ emailconfigurationBean.getSubject() + " :: " + emailconfigurationBean.getEventId());
			ArrayList attachements = new ArrayList();
			ArrayList details = new ArrayList();
			details.add(emailconfigurationBean.getEventId());// 0
			details.add(toMail);// 1
			details.add(subject);// 2
			details.add(body);// 3
			details.add(attachements);
			System.out.println("details" + details);
			emailProcessorImpl.saveEmail(details, emailconfigurationBean.getEventId());

			emailSend.getDetailsEmail();

		} catch (SMTPAddressFailedException e) {
			logger.info("=======AdminEmail SMTP ADDRESS Failed Exception============" + e);
			System.out.println("AdminEmail Exception" + e);
			e.printStackTrace();
		} catch (Exception e) {

			System.out.println("AdminEmail Exception" + e);
			e.printStackTrace();
		}

	}

	public void sendBAUSplanEmailToRm(NimaiEmailScheduler schdata) {
		// TODO Auto-generated method stub
		EmailComponentMaster emailconfigurationBean = null;
		try {

			// emailconfigurationBean =
			// emailConfigurationDAOImpl.findEventConfiguration(schdata.getEvent());
			emailconfigurationBean = emailComRepo.findEventConfiguration(schdata.getEvent());

			System.out.println(" Fetching Configuration for Reset Password Policy " + emailconfigurationBean);
			Map<String, String> subject = new HashMap<String, String>();
			Map<String, String> body = new HashMap<String, String>();

			subject.put("Subject", emailconfigurationBean.getSubject());

			// ArrayList attachements = new ArrayList();
			// attachements.add(new File("D:\\BDO1.jpg"));
			body.put("username", schdata.getrMName());
			body.put("SPLANNAme", schdata.getSubscriptionName());
			body.put("Credits", schdata.getsPLanLcCount());
			body.put("Subsidiaries", schdata.getSubsidiaryUsers());
			body.put("RM", schdata.getRelationshipManager());
			body.put("Price", schdata.getSubscriptionAmount());
			body.put("Validity", schdata.getSubscriptionValidity());
			body.put("country", schdata.getsPLanCountry());
			body.put("currency", schdata.getsPlanCurrency());

			String toMail = schdata.getrMemailId();

			// emailConfigurationDAOImpl.saveCCEmails(uatCcEMail,
			// emailconfigurationBean.getEventId());
			emailConfigurationDAOImpl.saveBCCEmails(uatCcEMail, emailconfigurationBean.getEventId());

			System.out.println(" Fetching Configuration for Reset Password Policy "
					+ emailconfigurationBean.getSubject() + " :: " + emailconfigurationBean.getEventId());
			ArrayList attachements = new ArrayList();
			ArrayList details = new ArrayList();
			details.add(emailconfigurationBean.getEventId());// 0
			details.add(toMail);// 1
			details.add(subject);// 2
			details.add(body);// 3
			details.add(attachements);
			System.out.println("details" + details);
			emailProcessorImpl.saveEmail(details, emailconfigurationBean.getEventId());

			emailSend.getDetailsEmail();

		} catch (SMTPAddressFailedException e) {
			logger.info("=======AdminEmail SMTP ADDRESS Failed Exception============" + e);
			System.out.println("AdminEmail Exception" + e);
			e.printStackTrace();
		} catch (Exception e) {

			System.out.println("AdminEmail Exception" + e);
			e.printStackTrace();
		}

	}

	public void sendBCSplanEmailToRm(NimaiEmailScheduler schdata) {
		// TODO Auto-generated method stub
		EmailComponentMaster emailconfigurationBean = null;
		try {

			// emailconfigurationBean =
			// emailConfigurationDAOImpl.findEventConfiguration(schdata.getEvent());
			emailconfigurationBean = emailComRepo.findEventConfiguration(schdata.getEvent());

			System.out.println(" Fetching Configuration for Reset Password Policy " + emailconfigurationBean);
			Map<String, String> subject = new HashMap<String, String>();
			Map<String, String> body = new HashMap<String, String>();

			subject.put("Subject", emailconfigurationBean.getSubject());

			// ArrayList attachements = new ArrayList();
			// attachements.add(new File("D:\\BDO1.jpg"));
			body.put("username", schdata.getrMName());
			body.put("SPLANNAme", schdata.getSubscriptionName());
			body.put("Credits", schdata.getsPLanLcCount());
			body.put("Subsidiaries", schdata.getSubsidiaryUsers());
			body.put("RM", schdata.getRelationshipManager());
			body.put("Price", schdata.getSubscriptionAmount());
			body.put("Validity", schdata.getSubscriptionValidity());
			body.put("country", schdata.getsPLanCountry());
			body.put("currency", schdata.getsPlanCurrency());

			String toMail = schdata.getrMemailId();

			// emailConfigurationDAOImpl.saveCCEmails(uatCcEMail,
			// emailconfigurationBean.getEventId());
			emailConfigurationDAOImpl.saveBCCEmails(uatCcEMail, emailconfigurationBean.getEventId());

			System.out.println(" Fetching Configuration for Reset Password Policy "
					+ emailconfigurationBean.getSubject() + " :: " + emailconfigurationBean.getEventId());
			ArrayList attachements = new ArrayList();
			ArrayList details = new ArrayList();
			details.add(emailconfigurationBean.getEventId());// 0
			details.add(toMail);// 1
			details.add(subject);// 2
			details.add(body);// 3
			details.add(attachements);
			System.out.println("details" + details);
			emailProcessorImpl.saveEmail(details, emailconfigurationBean.getEventId());

			emailSend.getDetailsEmail();

		} catch (SMTPAddressFailedException e) {
			logger.info("=======AdminEmail SMTP ADDRESS Failed Exception============" + e);
			System.out.println("AdminEmail Exception" + e);
			e.printStackTrace();
		} catch (Exception e) {

			System.out.println("AdminEmail Exception" + e);
			e.printStackTrace();
		}

	}

	public void sendCuSplanEmailToRm(NimaiEmailScheduler schdata) {
		// TODO Auto-generated method stub
		EmailComponentMaster emailconfigurationBean = null;
		try {

			// emailconfigurationBean =
			// emailConfigurationDAOImpl.findEventConfiguration(schdata.getEvent());
			emailconfigurationBean = emailComRepo.findEventConfiguration(schdata.getEvent());

			System.out.println(" Fetching Configuration for Reset Password Policy " + emailconfigurationBean);
			Map<String, String> subject = new HashMap<String, String>();
			Map<String, String> body = new HashMap<String, String>();

			subject.put("Subject", emailconfigurationBean.getSubject());

			// ArrayList attachements = new ArrayList();
			// attachements.add(new File("D:\\BDO1.jpg"));
			body.put("username", schdata.getrMName());
			body.put("SPLANNAme", schdata.getSubscriptionName());
			body.put("Credits", schdata.getsPLanLcCount());
			body.put("Subsidiaries", schdata.getSubsidiaryUsers());
			body.put("RM", schdata.getRelationshipManager());
			body.put("Price", schdata.getSubscriptionAmount());
			body.put("Validity", schdata.getSubscriptionValidity());
			body.put("country", schdata.getsPLanCountry());
			body.put("currency", schdata.getsPlanCurrency());

			String toMail = schdata.getrMemailId();

			// emailConfigurationDAOImpl.saveCCEmails(uatCcEMail,
			// emailconfigurationBean.getEventId());
			emailConfigurationDAOImpl.saveBCCEmails(uatCcEMail, emailconfigurationBean.getEventId());
			System.out.println(" Fetching Configuration for Reset Password Policy "
					+ emailconfigurationBean.getSubject() + " :: " + emailconfigurationBean.getEventId());
			ArrayList attachements = new ArrayList();
			ArrayList details = new ArrayList();
			details.add(emailconfigurationBean.getEventId());// 0
			details.add(toMail);// 1
			details.add(subject);// 2
			details.add(body);// 3
			details.add(attachements);
			System.out.println("details" + details);
			emailProcessorImpl.saveEmail(details, emailconfigurationBean.getEventId());

			emailSend.getDetailsEmail();

		} catch (SMTPAddressFailedException e) {
			logger.info("=======AdminEmail SMTP ADDRESS Failed Exception============" + e);
			System.out.println("AdminEmail Exception" + e);
			e.printStackTrace();
		} catch (Exception e) {

			System.out.println("AdminEmail Exception" + e);
			e.printStackTrace();
		}

	}

	public void sendFixedCDAlertEmailToRm(NimaiEmailScheduler schdata) {
		// TODO Auto-generated method stub
		EmailComponentMaster emailconfigurationBean = null;
		try {

			// emailconfigurationBean =
			// emailConfigurationDAOImpl.findEventConfiguration(schdata.getEvent());
			emailconfigurationBean = emailComRepo.findEventConfiguration(schdata.getEvent());

			System.out.println(" Fetching Configuration for Reset Password Policy " + emailconfigurationBean);
			Map<String, String> subject = new HashMap<String, String>();
			Map<String, String> body = new HashMap<String, String>();

			subject.put("Subject", emailconfigurationBean.getSubject());

			// ArrayList attachements = new ArrayList();
			// attachements.add(new File("D:\\BDO1.jpg"));
			body.put("username", schdata.getrMName());
			body.put("subscriptionName", schdata.getSubscriptionName());
			body.put("couponCode", schdata.getCouponCode());
			body.put("discountType", schdata.getDiscountType());
			body.put("startdate", schdata.getStartDate());
			body.put("startTime", schdata.getStartTime());
			body.put("endDate", schdata.getEndDate());
			body.put("endTime", schdata.getEndTime());
			body.put("quantity", schdata.getQuantity());
			body.put("customerType", schdata.getCustomerType());
			body.put("country", schdata.getsPLanCountry());
			String toMail = schdata.getrMemailId();

			// emailConfigurationDAOImpl.saveCCEmails(uatCcEMail,
			// emailconfigurationBean.getEventId());
			emailConfigurationDAOImpl.saveBCCEmails(uatCcEMail, emailconfigurationBean.getEventId());

			System.out.println(" Fetching Configuration for Reset Password Policy "
					+ emailconfigurationBean.getSubject() + " :: " + emailconfigurationBean.getEventId());
			ArrayList attachements = new ArrayList();
			ArrayList details = new ArrayList();
			details.add(emailconfigurationBean.getEventId());// 0
			details.add(toMail);// 1
			details.add(subject);// 2
			details.add(body);// 3
			details.add(attachements);
			System.out.println("details" + details);
			emailProcessorImpl.saveEmail(details, emailconfigurationBean.getEventId());

			emailSend.getDetailsEmail();

		} catch (SMTPAddressFailedException e) {
			logger.info("=======AdminEmail SMTP ADDRESS Failed Exception============" + e);
			System.out.println("AdminEmail Exception" + e);
			e.printStackTrace();
		} catch (Exception e) {

			System.out.println("AdminEmail Exception" + e);
			e.printStackTrace();
		}

	}

	public void sendPercentCDAlertEmailToRm(NimaiEmailScheduler schdata) {
		// TODO Auto-generated method stub
		EmailComponentMaster emailconfigurationBean = null;
		try {

			// emailconfigurationBean =
			// emailConfigurationDAOImpl.findEventConfiguration(schdata.getEvent());
			emailconfigurationBean = emailComRepo.findEventConfiguration(schdata.getEvent());

			System.out.println(" Fetching Configuration for Reset Password Policy " + emailconfigurationBean);
			Map<String, String> subject = new HashMap<String, String>();
			Map<String, String> body = new HashMap<String, String>();

			subject.put("Subject", emailconfigurationBean.getSubject());

			// ArrayList attachements = new ArrayList();

			body.put("username", schdata.getrMName());
			body.put("subscriptionName", schdata.getSubscriptionName());
			body.put("couponCode", schdata.getCouponCode());
			body.put("discountType", schdata.getDiscountType());
			body.put("startdate", schdata.getStartDate());
			body.put("startTime", schdata.getStartTime());
			body.put("endDate", schdata.getEndDate());

			body.put("endTime", schdata.getEndTime());
			body.put("quantity", schdata.getQuantity());
			body.put("customerType", schdata.getCustomerType());
			body.put("country", schdata.getsPLanCountry());
			body.put("discountPercentage", schdata.getDiscountPercentage());

			String toMail = schdata.getrMemailId();

			// emailConfigurationDAOImpl.saveCCEmails(uatCcEMail,
			// emailconfigurationBean.getEventId());
			emailConfigurationDAOImpl.saveBCCEmails(uatCcEMail, emailconfigurationBean.getEventId());

			System.out.println(" Fetching Configuration for Reset Password Policy "
					+ emailconfigurationBean.getSubject() + " :: " + emailconfigurationBean.getEventId());
			ArrayList attachements = new ArrayList();
			ArrayList details = new ArrayList();
			details.add(emailconfigurationBean.getEventId());// 0
			details.add(toMail);// 1
			details.add(subject);// 2
			details.add(body);// 3
			details.add(attachements);
			System.out.println("details" + details);
			emailProcessorImpl.saveEmail(details, emailconfigurationBean.getEventId());

			emailSend.getDetailsEmail();

		} catch (SMTPAddressFailedException e) {
			logger.info("=======AdminEmail SMTP ADDRESS Failed Exception============" + e);
			System.out.println("AdminEmail Exception" + e);
			e.printStackTrace();
		} catch (Exception e) {

			System.out.println("AdminEmail Exception" + e);
			e.printStackTrace();
		}

	}

	public void sendBAUAlertEmailToRm(NimaiEmailScheduler schdata) {
		// TODO Auto-generated method stub
		EmailComponentMaster emailconfigurationBean = null;
		try {

			// emailconfigurationBean =
			// emailConfigurationDAOImpl.findEventConfiguration(schdata.getEvent());
			emailconfigurationBean = emailComRepo.findEventConfiguration(schdata.getEvent());

			System.out.println(" Fetching Configuration for Reset Password Policy " + emailconfigurationBean);
			Map<String, String> subject = new HashMap<String, String>();
			Map<String, String> body = new HashMap<String, String>();

			subject.put("Subject", emailconfigurationBean.getSubject());

			// ArrayList attachements = new ArrayList();

			body.put("username", schdata.getrMName());
			body.put("subscriptionName", schdata.getSubscriptionName());
			body.put("Price", schdata.getSubscriptionAmount());
			body.put("description1", schdata.getDescription1());
			body.put("description2", schdata.getDescription2());
			body.put("description3", schdata.getDescription3());
			body.put("description4", schdata.getDescription4());

			body.put("description5", schdata.getDescription5());

			body.put("customerType", schdata.getCustomerType());
			body.put("country", schdata.getsPLanCountry());
			body.put("currency", schdata.getsPlanCurrency());

			String toMail = schdata.getrMemailId();

			// emailConfigurationDAOImpl.saveCCEmails(uatCcEMail,
			// emailconfigurationBean.getEventId());
			emailConfigurationDAOImpl.saveBCCEmails(uatCcEMail, emailconfigurationBean.getEventId());

			System.out.println(" Fetching Configuration for Reset Password Policy "
					+ emailconfigurationBean.getSubject() + " :: " + emailconfigurationBean.getEventId());
			ArrayList attachements = new ArrayList();
			ArrayList details = new ArrayList();
			details.add(emailconfigurationBean.getEventId());// 0
			details.add(toMail);// 1
			details.add(subject);// 2
			details.add(body);// 3
			details.add(attachements);
			System.out.println("details" + details);
			emailProcessorImpl.saveEmail(details, emailconfigurationBean.getEventId());

			emailSend.getDetailsEmail();

		} catch (SMTPAddressFailedException e) {
			logger.info("=======AdminEmail SMTP ADDRESS Failed Exception============" + e);
			System.out.println("AdminEmail Exception" + e);
			e.printStackTrace();
		} catch (Exception e) {

			System.out.println("AdminEmail Exception" + e);
			e.printStackTrace();
		}

	}

	public void sendBCVASAlertEmailToRm(NimaiEmailScheduler schdata) {
// TODO Auto-generated method stub
		EmailComponentMaster emailconfigurationBean = null;
		try {

			// emailconfigurationBean =
			// emailConfigurationDAOImpl.findEventConfiguration(schdata.getEvent());

			emailconfigurationBean = emailComRepo.findEventConfiguration(schdata.getEvent());

			System.out.println(" Fetching Configuration for Reset Password Policy " + emailconfigurationBean);
			Map<String, String> subject = new HashMap<String, String>();
			Map<String, String> body = new HashMap<String, String>();

			subject.put("Subject", emailconfigurationBean.getSubject());

			// ArrayList attachements = new ArrayList();

			body.put("username", schdata.getrMName());
			body.put("subscriptionName", schdata.getSubscriptionName());
			body.put("Price", schdata.getSubscriptionAmount());
			body.put("description1", schdata.getDescription1());
			body.put("description2", schdata.getDescription2());
			body.put("description3", schdata.getDescription3());
			body.put("description4", schdata.getDescription4());

			body.put("description5", schdata.getDescription5());

			body.put("customerType", schdata.getCustomerType());
			body.put("country", schdata.getsPLanCountry());
			body.put("currency", schdata.getsPlanCurrency());

			String toMail = schdata.getrMemailId();

			// emailConfigurationDAOImpl.saveCCEmails(uatCcEMail,
			// emailconfigurationBean.getEventId());
			emailConfigurationDAOImpl.saveBCCEmails(uatCcEMail, emailconfigurationBean.getEventId());

			System.out.println(" Fetching Configuration for Reset Password Policy "
					+ emailconfigurationBean.getSubject() + " :: " + emailconfigurationBean.getEventId());
			ArrayList attachements = new ArrayList();
			ArrayList details = new ArrayList();
			details.add(emailconfigurationBean.getEventId());// 0
			details.add(toMail);// 1
			details.add(subject);// 2
			details.add(body);// 3
			details.add(attachements);
			System.out.println("details" + details);
			emailProcessorImpl.saveEmail(details, emailconfigurationBean.getEventId());

			emailSend.getDetailsEmail();

		} catch (SMTPAddressFailedException e) {
			logger.info("=======AdminEmail SMTP ADDRESS Failed Exception============" + e);
			System.out.println("AdminEmail Exception" + e);
			e.printStackTrace();
		} catch (Exception e) {

			System.out.println("AdminEmail Exception" + e);
			e.printStackTrace();
		}

	}

	public void sendCUVASAlertEmailToRm(NimaiEmailScheduler schdata) {
		// TODO Auto-generated method stub
		EmailComponentMaster emailconfigurationBean = null;
		try {

			// emailconfigurationBean =
			// emailConfigurationDAOImpl.findEventConfiguration(schdata.getEvent());

			emailconfigurationBean = emailComRepo.findEventConfiguration(schdata.getEvent());

			System.out.println(" Fetching Configuration for Reset Password Policy " + emailconfigurationBean);
			Map<String, String> subject = new HashMap<String, String>();
			Map<String, String> body = new HashMap<String, String>();

			subject.put("Subject", emailconfigurationBean.getSubject());

			// ArrayList attachements = new ArrayList();

			body.put("username", schdata.getrMName());
			body.put("subscriptionName", schdata.getSubscriptionName());
			body.put("Price", schdata.getSubscriptionAmount());
			body.put("description1", schdata.getDescription1());
			body.put("description2", schdata.getDescription2());
			body.put("description3", schdata.getDescription3());
			body.put("description4", schdata.getDescription4());

			body.put("description5", schdata.getDescription5());

			body.put("customerType", schdata.getCustomerType());
			body.put("country", schdata.getsPLanCountry());
			body.put("currency", schdata.getsPlanCurrency());

			String toMail = schdata.getrMemailId();

			// emailConfigurationDAOImpl.saveCCEmails(uatCcEMail,
			// emailconfigurationBean.getEventId());
			emailConfigurationDAOImpl.saveBCCEmails(uatCcEMail, emailconfigurationBean.getEventId());

			System.out.println("Fetching Configuration for Reset Password Policy " + emailconfigurationBean.getSubject()
					+ " :: " + emailconfigurationBean.getEventId());
			ArrayList attachements = new ArrayList();
			ArrayList details = new ArrayList();
			details.add(emailconfigurationBean.getEventId());// 0
			details.add(toMail);// 1
			details.add(subject);// 2
			details.add(body);// 3
			details.add(attachements);
			System.out.println("details" + details);
			emailProcessorImpl.saveEmail(details, emailconfigurationBean.getEventId());

			emailSend.getDetailsEmail();

		} catch (SMTPAddressFailedException e) {
			logger.info("=======AdminEmail SMTP ADDRESS Failed Exception============" + e);
			System.out.println("AdminEmail Exception" + e);
			e.printStackTrace();
		} catch (Exception e) {

			System.out.println("AdminEmail Exception" + e);
			e.printStackTrace();
		}

	}

	public void sendRejectionEmailFromRmTOCU(NimaiEmailScheduler schdulerData, String cNumber, String email) {
		// TODO Auto-generated method stub
		EmailComponentMaster emailconfigurationBean = null;
		try {
			Map<String, String> body = new HashMap<String, String>();
			if (schdulerData.getEvent().equalsIgnoreCase(AppConstants.KYC_REJ_FROMRM_TO_CU_Support)) {
				emailconfigurationBean = emailComRepo.findEventConfiguration(AppConstants.KYC_REJ_FROMRM_TO_CU_Support);
			} else {
				emailconfigurationBean = emailComRepo.findEventConfiguration(schdulerData.getEvent());
				body.put("rmName", schdulerData.getrMName());
				body.put("rmContactNumber", schdulerData.getSubMobile());
				body.put("rmEmailId", schdulerData.getrMemailId());
			}

			System.out.println(" Fetching Configuration for Reset Password Policy " + emailconfigurationBean);
			Map<String, String> subject = new HashMap<String, String>();

			subject.put("Subject", emailconfigurationBean.getSubject());

			// ArrayList attachements = new ArrayList();
			NimaiSystemConfig configDetails = null;

			body.put("username", schdulerData.getUserName());

			body.put("customerSupportNumber", cNumber);

			body.put("customerEmailId", email);
			body.put("kycType", schdulerData.getKycDocName());
			body.put("businessName", schdulerData.getDescription1());
			body.put("customerSupportNumber", cNumber);

			body.put("customerEmailId", email);

			configDetails = systemConfig.findBySystemValue("link");
			body.put("link", configDetails.getSystemEntityValue());

			String toMail = schdulerData.getEmailId();

			// emailConfigurationDAOImpl.saveCCEmails(uatCcEMail,
			// emailconfigurationBean.getEventId());
			emailConfigurationDAOImpl.saveBCCEmails(uatCcEMail, emailconfigurationBean.getEventId());

			System.out.println("Fetching Configuration for Reset Password Policy " + emailconfigurationBean.getSubject()
					+ " :: " + emailconfigurationBean.getEventId());
			ArrayList attachements = new ArrayList();
			ArrayList details = new ArrayList();
			details.add(emailconfigurationBean.getEventId());// 0
			details.add(toMail);// 1
			details.add(subject);// 2
			details.add(body);// 3
			details.add(attachements);
			System.out.println("details" + details);
			emailProcessorImpl.saveEmail(details, emailconfigurationBean.getEventId());

			emailSend.getDetailsEmail();

		} catch (SMTPAddressFailedException e) {
			logger.info("=======AdminEmail SMTP ADDRESS Failed Exception============" + e);
			System.out.println("AdminEmail Exception" + e);
			e.printStackTrace();
		} catch (Exception e) {

			System.out.println("AdminEmail Exception" + e);
			e.printStackTrace();
		}
	}

	public void sendApprovalEmailToCU(NimaiEmailScheduler schdata) {
		// TODO Auto-generated method stub
		EmailComponentMaster emailconfigurationBean = null;
		try {

			// emailconfigurationBean =
			// emailConfigurationDAOImpl.findEventConfiguration(schdata.getEvent());

			emailconfigurationBean = emailComRepo.findEventConfiguration(schdata.getEvent());

			System.out.println(" Fetching Configuration for Reset Password Policy " + emailconfigurationBean);
			Map<String, String> subject = new HashMap<String, String>();
			Map<String, String> body = new HashMap<String, String>();

			subject.put("Subject", emailconfigurationBean.getSubject());

			// ArrayList attachements = new ArrayList();

			body.put("username", schdata.getUserName());

			body.put("businessName", schdata.getDescription1());
			String toMail = schdata.getEmailId();

			// emailConfigurationDAOImpl.saveCCEmails(uatCcEMail,
			// emailconfigurationBean.getEventId());
			emailConfigurationDAOImpl.saveBCCEmails(uatCcEMail, emailconfigurationBean.getEventId());

			System.out.println("Fetching Configuration for Reset Password Policy " + emailconfigurationBean.getSubject()
					+ " :: " + emailconfigurationBean.getEventId());
			ArrayList attachements = new ArrayList();
			ArrayList details = new ArrayList();
			details.add(emailconfigurationBean.getEventId());// 0
			details.add(toMail);// 1
			details.add(subject);// 2
			details.add(body);// 3
			details.add(attachements);
			System.out.println("details" + details);
			emailProcessorImpl.saveEmail(details, emailconfigurationBean.getEventId());

			emailSend.getDetailsEmail();

		} catch (SMTPAddressFailedException e) {
			logger.info("=======AdminEmail SMTP ADDRESS Failed Exception============" + e);
			System.out.println("AdminEmail Exception" + e);
			e.printStackTrace();
		} catch (Exception e) {

			System.out.println("AdminEmail Exception" + e);
			e.printStackTrace();
		}

	}

	public void sendKycEmailFromRmTOCustomer(String event, NimaiClient clientUserId, NimaiEmailScheduler schdulerData) {
		// TODO Auto-generated method stub

	}

	public void sendSPlanRenewalEmailToCust(NimaiSubscriptionDetails schdata, NimaiMSubscription currencyDetails) {
		// TODO Auto-generated method stub
		EmailComponentMaster emailconfigurationBean = null;
		try {
			String event = "SPLAN_RENEWAL";
			// emailconfigurationBean =
			// emailConfigurationDAOImpl.findEventConfiguration(event);

			emailconfigurationBean = emailComRepo.findEventConfiguration(event);

			System.out.println(" Fetching Configuration for Reset Password Policy " + emailconfigurationBean);
			Map<String, String> subject = new HashMap<String, String>();
			Map<String, String> body = new HashMap<String, String>();

			subject.put("Subject", emailconfigurationBean.getSubject());

			// ArrayList attachements = new ArrayList();
			// attachements.add(new File("D:\\BDO1.jpg"));
			body.put("username", schdata.getUserid().getFirstName());
			body.put("SPLANNAme", schdata.getSubscriptionName());
			body.put("Credits", schdata.getlCount());
			body.put("Subsidiaries", schdata.getSubsidiaries());
			body.put("RM", schdata.getRelationshipManager());
			body.put("Price", String.valueOf(schdata.getSubscriptionAmount()));
			body.put("Validity", String.valueOf(schdata.getSubscriptionValidity()));
			body.put("country", schdata.getsPLanCountry());
			body.put("currency", currencyDetails.getCurrency());
			String dateString = null;
			SimpleDateFormat sdfr = new SimpleDateFormat("yyyy/MMM/dd");
			dateString = sdfr.format(schdata.getSubscriptionEndDate());
			body.put("splanEndDate", dateString);

			String toMail = schdata.getUserid().getEmailAddress();
			String emailId = presentEmailID(schdata.getUserid());
			emailId = emailId + "," + uatCcEMail;

			System.out.println("EmailID frm presented" + emailId);
			if (emailId != null && !emailId.trim().isEmpty()) {
				System.out.println("CC email Details inside sendLcStatusEmailData details" + emailId);
				emailConfigurationDAOImpl.saveCCEmails(emailId, emailconfigurationBean.getEventId());
			}
			// emailConfigurationDAOImpl.saveBCCEmails(uatCcEMail,
			// emailconfigurationBean.getEventId());
			System.out.println(" Fetching Configuration for Reset Password Policy "
					+ emailconfigurationBean.getSubject() + " :: " + emailconfigurationBean.getEventId());
			ArrayList attachements = new ArrayList();
			ArrayList details = new ArrayList();
			details.add(emailconfigurationBean.getEventId());// 0
			details.add(toMail);// 1
			details.add(subject);// 2
			details.add(body);// 3
			details.add(attachements);
			System.out.println("details" + details);
			emailProcessorImpl.saveEmail(details, emailconfigurationBean.getEventId());

			emailSend.getDetailsEmail();

		} catch (SMTPAddressFailedException e) {
			logger.info("=======AdminEmail SMTP ADDRESS Failed Exception============" + e);
			System.out.println("AdminEmail Exception" + e);
			e.printStackTrace();
		} catch (Exception e) {

			System.out.println("AdminEmail Exception" + e);
			e.printStackTrace();
		}

	}

	public void sendAdminDailyReport(List<AdminRmWiseCount> counDetails, AdminDailyCountDetailsBean adminCount,
			NimaiMEmployee employeeDetails) {
		// TODO Auto-generated method stub
		logger.info("=========Inside sendQuotationStatusEmail method with schedulerData from database==========");
		EmailComponentMaster emailconfigurationBean = null;
		try {
			String event = "ADMIN_DAILY_REPORT";
			// emailconfigurationBean =
			// emailConfigurationDAOImpl.findEventConfiguration(event);
			emailconfigurationBean = emailComRepo.findEventConfiguration(event);
			HtmlCreationUtility util = new HtmlCreationUtility();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -1);
			String html = util.generateAdminHtmlTemplateReport(counDetails, adminCount, employeeDetails);
			emailConfigurationDAOImpl.updateSubject(
					emailconfigurationBean.getSubject().concat(" ").concat(dateFormat.format(cal.getTime())),
					emailconfigurationBean.getEventId());
			System.out.println("Fetching Configuration for Reset Password Policy " + emailconfigurationBean);
			Map<String, String> subject = new HashMap<String, String>();
			Map<String, String> body = new HashMap<String, String>();

			body.put("htmlBody", html);

			String toMail = employeeDetails.getEmpEmail();

			// emailConfigurationDAOImpl.saveCCEmails(uatCcEMail,
			// emailconfigurationBean.getEventId());
			emailConfigurationDAOImpl.saveBCCEmails(uatCcEMail, emailconfigurationBean.getEventId());
			System.out.println("Fetching Configuration for transaction status" + emailconfigurationBean.getSubject()
					+ " :: " + emailconfigurationBean.getEventId());
			ArrayList attachements = new ArrayList();
			ArrayList details = new ArrayList();
			details.add(emailconfigurationBean.getEventId());// 0
			details.add(toMail);// 1
			details.add(subject);// 2
			details.add(body);// 3
			details.add(attachements);
			System.out.println("details" + details);
			emailProcessorImpl.saveEmail(details, emailconfigurationBean.getEventId());

			emailSend.getDetailsEmail();

		} catch (SMTPAddressFailedException e) {
			logger.info("=======sendAdminDailyReport SMTP ADDRESS Failed Exception============" + e);
			System.out.println("sendAdminDailyReport Exception" + e);
			e.printStackTrace();
		} catch (Exception e) {

			System.out.println("sendAdminDailyReport Exception" + e);
			e.printStackTrace();
		}

	}

	public void sendCustEodDailyReport(Map<String, List<EodCustomerDailyReort>> groupByUserId,
			NimaiClient clientUseId) {
		// TODO Auto-generated method stub
		logger.info("=========Inside sendCustEodDailyReport method with schedulerData from database==========");
		EmailComponentMaster emailconfigurationBean = null;
		try {
			String event = "CUSTOMER_DAILY_REPORT";
			// emailconfigurationBean =
			// emailConfigurationDAOImpl.findEventConfiguration(event);
			emailconfigurationBean = emailComRepo.findEventConfiguration(event);
			HtmlCreationUtility util = new HtmlCreationUtility();

			String html = util.generateCustEodHtmlTemplateReport(groupByUserId, clientUseId);

			System.out.println("Fetching Configuration for Reset Password Policy " + emailconfigurationBean);
			Map<String, String> subject = new HashMap<String, String>();
			Map<String, String> body = new HashMap<String, String>();

			body.put("htmlBody", html);
			String toMail = clientUseId.getEmailAddress();

			// emailConfigurationDAOImpl.saveCCEmails(uatCcEMail,
			// emailconfigurationBean.getEventId());
			emailConfigurationDAOImpl.saveBCCEmails(uatCcEMail, emailconfigurationBean.getEventId());
			System.out.println("Fetching Configuration for transaction status" + emailconfigurationBean.getSubject()
					+ " :: " + emailconfigurationBean.getEventId());
			ArrayList attachements = new ArrayList();
			ArrayList details = new ArrayList();
			details.add(emailconfigurationBean.getEventId());// 0
			details.add(toMail);// 1
			details.add(subject);// 2
			details.add(body);// 3
			details.add(attachements);
			System.out.println("details" + details);
			emailProcessorImpl.saveEmail(details, emailconfigurationBean.getEventId());

			emailSend.getDetailsEmail();

		} catch (SMTPAddressFailedException e) {
			logger.info("=======sendCustEodDailyReport SMTP ADDRESS Failed Exception============" + e);
			System.out.println("sendCustEodDailyReport Exception" + e);
			e.printStackTrace();
		} catch (Exception e) {

			System.out.println("sendCustEodDailyReport Exception" + e);
			e.printStackTrace();
		}

	}

	public void sendBankEodDailyReport(Map<String, List<EodBankDailyReport>> groupByUserId, NimaiClient clientUseId) {
		// TODO Auto-generated method stub
		logger.info("=========Inside sendCustEodDailyReport method with schedulerData from database==========");
		EmailComponentMaster emailconfigurationBean = null;
		try {
			String event = "CUSTOMER_DAILY_REPORT";
			// emailconfigurationBean =
			// emailConfigurationDAOImpl.findEventConfiguration(event);
			emailconfigurationBean = emailComRepo.findEventConfiguration(event);
			HtmlCreationUtility util = new HtmlCreationUtility();

			String html = util.generateBankEodHtmlTemplateReport(groupByUserId, clientUseId);

			System.out.println("Fetching Configuration for Reset Password Policy " + emailconfigurationBean);
			Map<String, String> subject = new HashMap<String, String>();
			Map<String, String> body = new HashMap<String, String>();

			body.put("htmlBody", html);

			String toMail = clientUseId.getEmailAddress();

			// emailConfigurationDAOImpl.saveCCEmails(uatCcEMail,
			// emailconfigurationBean.getEventId());
			emailConfigurationDAOImpl.saveBCCEmails(uatCcEMail, emailconfigurationBean.getEventId());
			System.out.println("Fetching Configuration for transaction status" + emailconfigurationBean.getSubject()
					+ " :: " + emailconfigurationBean.getEventId());
			ArrayList attachements = new ArrayList();
			ArrayList details = new ArrayList();
			details.add(emailconfigurationBean.getEventId());// 0
			details.add(toMail);// 1
			details.add(subject);// 2
			details.add(body);// 3
			details.add(attachements);
			System.out.println("details" + details);
			emailProcessorImpl.saveEmail(details, emailconfigurationBean.getEventId());

			emailSend.getDetailsEmail();

		} catch (SMTPAddressFailedException e) {
			logger.info("=======sendBankEodDailyReport SMTP ADDRESS Failed Exception============" + e);
			System.out.println("sendBankEodDailyReport Exception" + e);
			e.printStackTrace();
		} catch (Exception e) {

			System.out.println("sendBankEodDailyReport Exception" + e);
			e.printStackTrace();
		}

	}

	public void sendTrupdateAlertToCust(NimaiLCMaster trDetails, NimaiClient clientUseId) {
		// TODO Auto-generated method stub
		EmailComponentMaster emailconfigurationBean = null;
		NimaiSystemConfig configDetails = null;

		configDetails = systemConfig.findBySystemValue("link");
		try {
			logger.info("============Inside EmailInsert sendTrupdateAlertToCust=========");
			System.out.println("======================size:" + "Inside EmailInsert sendTrupdateAlertToCust"

					+ "===============================");
			String event = "UPDATE_TR_STATUS";
			// emailconfigurationBean =
			// emailConfigurationDAOImpl.findEventConfiguration(event);
			emailconfigurationBean = emailComRepo.findEventConfiguration(event);
			HtmlCreationUtility util = new HtmlCreationUtility();
			String requiredPage = "transactionDetails";
			String updateTransactionLlink = updateTrlink + requiredPage;
			System.out.println("Fetching Configuration for Reset Password Policy " + emailconfigurationBean);
			Map<String, String> subject = new HashMap<String, String>();
			Map<String, String> body = new HashMap<String, String>();

			body.put("username ", clientUseId.getFirstName());
			body.put("bankName", trDetails.getBeneBankName());
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -1);
			body.put("acceptedDate", dateFormat.format(trDetails.getAcceptedOn()));
			body.put("transactionId", trDetails.getTransactionId());
			body.put("link", configDetails.getSystemEntityValue());
			System.out.println("system valu for the link" + configDetails.getSystemEntityValue());
			String toMail = clientUseId.getEmailAddress();

			// emailConfigurationDAOImpl.saveCCEmails(uatCcEMail,
			// emailconfigurationBean.getEventId());
			emailConfigurationDAOImpl.saveBCCEmails(uatCcEMail, emailconfigurationBean.getEventId());
			System.out.println("Fetching Configuration for transaction status" + emailconfigurationBean.getSubject()
					+ " :: " + emailconfigurationBean.getEventId());
			ArrayList attachements = new ArrayList();
			ArrayList details = new ArrayList();
			details.add(emailconfigurationBean.getEventId());// 0
			details.add(toMail);// 1
			details.add(subject);// 2
			details.add(body);// 3
			details.add(attachements);
			System.out.println("details" + details);
			emailProcessorImpl.saveEmail(details, emailconfigurationBean.getEventId());

			emailSend.getDetailsEmail();

		} catch (SMTPAddressFailedException e) {
			logger.info("=======sendTrupdateAlertToCust SMTP ADDRESS Failed Exception============" + e);
			System.out.println("sendTrupdateAlertToCust Exception" + e);
			e.printStackTrace();
		} catch (Exception e) {

			System.out.println("sendTrupdateAlertToCust Exception" + e);
			e.printStackTrace();
		}
	}

	public void resetSuccessEmail(ResetPassBean resetBean, NimaiClient clientUseId) {
		// TODO Auto-generated method stub
		EmailComponentMaster emailconfigurationBean = null;
		try {

			// emailconfigurationBean =
			// emailConfigurationDAOImpl.findEventConfiguration(resetBean.getEvent());

			emailconfigurationBean = emailComRepo.findEventConfiguration(resetBean.getEvent());

			System.out.println(" Fetching Configuration for Reset Password Policy " + emailconfigurationBean);
			Map<String, String> subject = new HashMap<String, String>();
			Map<String, String> body = new HashMap<String, String>();

			subject.put("Subject", emailconfigurationBean.getSubject());

			// ArrayList attachements = new ArrayList();
			// attachements.add(new File("D:\\BDO1.jpg"));

			body.put("username", clientUseId.getFirstName());
			body.put("userId", resetBean.getUserId());
			String toMail = clientUseId.getEmailAddress();

			// emailConfigurationDAOImpl.saveCCEmails(uatCcEMail,
			// emailconfigurationBean.getEventId());
			emailConfigurationDAOImpl.saveBCCEmails(uatCcEMail, emailconfigurationBean.getEventId());
			System.out.println(" Fetching Configuration for Reset Password Policy "
					+ emailconfigurationBean.getSubject() + " :: " + emailconfigurationBean.getEventId());
			ArrayList attachements = new ArrayList();
			ArrayList details = new ArrayList();
			details.add(emailconfigurationBean.getEventId());// 0
			details.add(toMail);// 1
			details.add(subject);// 2
			details.add(body);// 3
			details.add(attachements);
			System.out.println("details" + details);
			emailProcessorImpl.saveEmail(details, emailconfigurationBean.getEventId());

			emailSend.getDetailsEmail();

		} catch (SMTPAddressFailedException e) {
			logger.info("=======resetSuccessEmail SMTP ADDRESS Failed Exception============" + e);
			System.out.println("resetSuccessEmail Exception" + e);
			e.printStackTrace();
		} catch (Exception e) {

			System.out.println("AdminEmail Exception" + e);
			e.printStackTrace();
		}
	}

	public void sendVasEmail(NimaiEmailScheduler schdulerData, NimaiClient clientUseId, NimaiSubscriptionVas vasDetails,
			NimaiSubscriptionDetails subDetails, OnlinePayment paymentDetails, NimaiSystemConfig configDetails,
			String imagePath) {
		// TODO Auto-generated method stub
		EmailComponentMaster emailconfigurationBean = null;
		logger.info("=======Inside sendCustSPlanEmail============" + schdulerData.toString());
		GenericResponse response = new GenericResponse();
		try {
			// emailconfigurationBean=emailComRepo.findEventConfiguration(schdulerData.getEvent());
			emailconfigurationBean = emailComRepo.findEventConfiguration(schdulerData.getEvent());
			System.out.println("========useraname=======:::" + schdulerData.getUserName());
			System.out.println(" Fetching Configuration for sendCustSPlanEmail " + emailconfigurationBean);
			String ccEmail = "";
			Map<String, String> subject = new HashMap<String, String>();
			Map<String, String> body = new HashMap<String, String>();
			Date dnow = new Date();
			String sPLanStartdate = new SimpleDateFormat("dd/MM/yyyy").format(dnow);
			// String sPlanStartDate =
			// convertStringToDate(subDetails.getSubscriptionStartDate());
			String sPLanEndDate = convertStringToDate(subDetails.getSubscriptionEndDate());
			subject.put("Subject", emailconfigurationBean.getSubject());
//			subDetails.setVasAmount(100);  
//			subDetails.setDiscount(10);
			ArrayList attachements = new ArrayList();
			InvoiceTemplate link = new InvoiceTemplate();
			attachements.add(new File(
					link.generateVasInvoiceTemplate(vasDetails, subDetails, paymentDetails, configDetails, imagePath)));
			// attachements.add(new File("D:\\EmailSplan_changes_Checklist.xlsx"));

			body.put("username", clientUseId.getFirstName());

			body.put("subscriptionName", schdulerData.getSubscriptionName());

			String toMail = clientUseId.getEmailAddress();

			// emailConfigurationDAOImpl.saveCCEmails(uatCcEMail,
			// emailconfigurationBean.getEventId());
			emailConfigurationDAOImpl.saveBCCEmails(uatCcEMail, emailconfigurationBean.getEventId());
			System.out.println("Fetching Configuration for transaction status" + emailconfigurationBean.getSubject()
					+ " :: " + emailconfigurationBean.getEventId());
			String emailId = presentEmailID(clientUseId);
			System.out.println("EmailID frm presented" + emailId);
			if (emailId != null && !emailId.trim().isEmpty()) {
				System.out.println("CC email Details inside sendCustSPlanEmail details" + emailId);
				emailConfigurationDAOImpl.saveCCEmails(emailId, emailconfigurationBean.getEventId());
			}
			emailConfigurationDAOImpl.saveBCCEmails(emailId, emailconfigurationBean.getEventId());
			ArrayList details = new ArrayList();
			details.add(emailconfigurationBean.getEventId());// 0
			details.add(toMail);// 1
			details.add(subject);// 2
			details.add(body);// 3
			details.add(attachements);
			System.out.println("details" + details);

			emailProcessorImpl.saveEmail(details, emailconfigurationBean.getEventId());

			emailSend.getDetailsEmail();

		} catch (SMTPAddressFailedException e) {
			logger.info("=======sendCustSPlanEmail SMTP ADDRESS Failed Exception============" + e);
			response.setMessage("sendCustSPlanEmail SMTP Address Failed Exception");

			System.out.println("Exception" + e);
			e.printStackTrace();
		} catch (Exception e) {
			logger.info("=======sendCustSPlanEmail Exception============" + e);
			e.printStackTrace();
		}

	}

	public void sendWinningQuoteToAlertBank(String emailEvent, NimaiEmailSchedulerAlertToBanks schdulerData) {
		// TODO Auto-generated method stub
		EmailComponentMaster emailconfigurationBean = null;
		try {
			// emailconfigurationBean =
			// emailConfigurationDAOImpl.findEventConfiguration(emailEvent);

			emailconfigurationBean = emailComRepo.findEventConfiguration(emailEvent);

			System.out.println(" sendWinningQuoteToAlertBank " + emailconfigurationBean);
			String requiredPage = "quoteDetails";
			String quoteLlink = quoteDetailsLink + requiredPage;
			Map<String, String> subject = new HashMap<String, String>();
			Map<String, String> body = new HashMap<String, String>();
			body.put("username", schdulerData.getBankUserName());
			body.put("transactionId", schdulerData.getTransactionid());
			body.put("companyname", schdulerData.getCustomerCompanyName());
			body.put("currency", schdulerData.getCurrency());

			body.put("amount", schdulerData.getAmount());

			// emailConfigurationDAOImpl.saveCCEmails(uatCcEMail,
			// emailconfigurationBean.getEventId());
			emailConfigurationDAOImpl.saveBCCEmails(uatCcEMail, emailconfigurationBean.getEventId());
			ArrayList attachements = new ArrayList();
			ArrayList details = new ArrayList();
			details.add(emailconfigurationBean.getEventId());// 0
			details.add(schdulerData.getBanksEmailID());// 1
			details.add(subject);// 2
			details.add(body);// 3
			details.add(attachements);
			System.out.println("details" + details);
			emailProcessorImpl.saveEmail(details, emailconfigurationBean.getEventId());

			emailSend.getDetailsEmail();

		} catch (SMTPAddressFailedException e) {
			logger.info("=======sendWinningQuoteToAlertBank SMTP ADDRESS Failed Exception============" + e);
			System.out.println("sendWinningQuoteToAlertBank Exception" + e);
			e.printStackTrace();
		} catch (Exception e) {

			System.out.println("sendBidRecivedEmailToCust Exception" + e);
			e.printStackTrace();
		}

	}

	public void AdminForgotPassEmail(AdminBean userRegistratinBean, NimaiMLogin mLoginId, String link) {
		// TODO Auto-generated method stub
		EmailComponentMaster emailconfigurationBean = null;
		ArrayList details1 = null;
		try {

			// emailconfigurationBean =
			// emailConfigurationDAOImpl.findEventConfiguration(userRegistratinBean.getEvent());

			emailconfigurationBean = emailComRepo.findEventConfiguration(userRegistratinBean.getEvent());
			System.out.println("emailconfigurationBean output " + emailconfigurationBean);
			Map<String, String> subject = new HashMap<String, String>();
			Map<String, String> body = new HashMap<String, String>();
			subject.put("Subject", emailconfigurationBean.getSubject());
			// ArrayList attachements = new ArrayList();
			// attachements.add(new File("D:\\BDO1.jpg"));
			body.put("link", link);
			body.put("username", mLoginId.getEmpCode().getEmpName());
			body.put("userId", mLoginId.getEmpCode().getEmpCode());
			String toMail = mLoginId.getEmpCode().getEmpEmail();

			// emailConfigurationDAOImpl.saveCCEmails(uatCcEMail,
			// emailconfigurationBean.getEventId());
			emailConfigurationDAOImpl.saveBCCEmails(uatCcEMail, emailconfigurationBean.getEventId());
			System.out.println("emailconfigurationBean.getEventid" + emailconfigurationBean.getEventId());

			/**
			 *
			 * input ArrayList 0 - eventId 1 - to email addresss 2 - Arraylist of parameters
			 * for subject 3 - Arraylist of parameters for body 4 - Arraylist of files for
			 * attachement
			 *
			 */
			ArrayList attachements = new ArrayList();
			ArrayList details = new ArrayList();
			details.add(emailconfigurationBean.getEventId());// 0
			details.add(toMail);// 1
			details.add(subject);// 2
			details.add(body);// 3
			details.add(attachements);
			System.out.println("details" + details);
			emailProcessorImpl.saveEmail(details, emailconfigurationBean.getEventId());

			emailSend.getDetailsEmail();
		} catch (SMTPAddressFailedException e) {
			logger.info("=======SMTP ADDRESS Failed Exception============" + e);

			System.out.println("Exception" + e);
			e.printStackTrace();
		} catch (Exception e) {

			System.out.println("Exception" + e);
			e.printStackTrace();
		}

	}

	public void sendReferEmailToParentUser(NimaiEmailScheduler schdulerData) {
		// TODO Auto-generated method stub
		EmailComponentMaster emailconfigurationBean = null;
		try {

			emailconfigurationBean = emailComRepo.findEventConfiguration(schdulerData.getEvent());

			System.out.println(" Fetching Configuration for Reset Password Policy " + emailconfigurationBean);
			Map<String, String> subject = new HashMap<String, String>();
			Map<String, String> body = new HashMap<String, String>();

			subject.put("Subject", emailconfigurationBean.getSubject());

			body.put("username", schdulerData.getUserName());
			String toMail = schdulerData.getEmailId();

			// emailConfigurationDAOImpl.saveCCEmails(uatCcEMail,
			// emailconfigurationBean.getEventId());
			emailConfigurationDAOImpl.saveBCCEmails(uatCcEMail, emailconfigurationBean.getEventId());
			System.out.println(" Fetching Configuration for Reset Password Policy "
					+ emailconfigurationBean.getSubject() + " :: " + emailconfigurationBean.getEventId());
			ArrayList attachements = new ArrayList();
			ArrayList details = new ArrayList();
			details.add(emailconfigurationBean.getEventId());// 0
			details.add(toMail);// 1
			details.add(subject);// 2
			details.add(body);// 3
			details.add(attachements);
			System.out.println("details" + details);
			emailProcessorImpl.saveEmail(details, emailconfigurationBean.getEventId());

			emailSend.getDetailsEmail();

		} catch (SMTPAddressFailedException e) {
			logger.info("=======SMTP ADDRESS Failed Exception============" + e);

			System.out.println("Exception" + e);
			e.printStackTrace();
		} catch (Exception e) {

			System.out.println("sendSubAccAcivationLink Exception" + e);
			e.printStackTrace();
		}

	}

	public void BankDetailstoCustomer(BankDetailsBean bdBean, NimaiClient clientUseId) {
		// TODO Auto-generated method stub
		EmailComponentMaster emailconfigurationBean = null;
		try {

			// emailconfigurationBean =
			// emailConfigurationDAOImpl.findEventConfiguration(AppConstants.BDETAILS);
			emailconfigurationBean = emailComRepo.findEventConfiguration(AppConstants.BDETAILS);

			System.out.println(" Fetching Configuration for Reset Password Policy " + emailconfigurationBean);
			Map<String, String> subject = new HashMap<String, String>();
			Map<String, String> body = new HashMap<String, String>();
			subject.put("Subject", emailconfigurationBean.getSubject());
			body.put("username", clientUseId.getFirstName());
			body.put("accountName", AppConstants.ACCNAME);
			body.put("CompanyAddress", AppConstants.COMPANYADDRESS1);
			body.put("CompanyAddress2", AppConstants.COMPANYADDRESS2);
			body.put("CompanyAddress3", AppConstants.COMPANYADDRESS3);
			body.put("bankAccNumber", AppConstants.BAN);
			body.put("bankName", AppConstants.BANKNAME);
			body.put("bankAddress", AppConstants.BANKADDRESS1);
			body.put("bankAddress2", AppConstants.BANKADDRESS2);
			body.put("bankAddress3", AppConstants.BANKADDRESS3);
			body.put("bankSwiftCode", AppConstants.BSD);
			body.put("intermeditaryBankName", AppConstants.IBN);
			body.put("intermeditaryBankSC", AppConstants.IBNBSD);
			body.put("accountType", AppConstants.ACCTYPE);
			body.put("bankBranch", AppConstants.BBRANCH);

			String toMail = clientUseId.getEmailAddress();

			// emailConfigurationDAOImpl.saveCCEmails(uatCcEMail,
			// emailconfigurationBean.getEventId());
			emailConfigurationDAOImpl.saveBCCEmails(uatCcEMail, emailconfigurationBean.getEventId());
			System.out.println(" Fetching Configuration for Reset Password Policy "
					+ emailconfigurationBean.getSubject() + " :: " + emailconfigurationBean.getEventId());
			ArrayList attachements = new ArrayList();
			ArrayList details = new ArrayList();
			details.add(emailconfigurationBean.getEventId());// 0
			details.add(toMail);// 1
			details.add(subject);// 2
			details.add(body);// 3
			details.add(attachements);
			System.out.println("details" + details);
			emailProcessorImpl.saveEmail(details, emailconfigurationBean.getEventId());

			emailSend.getDetailsEmail();

		} catch (SMTPAddressFailedException e) {
			logger.info("=======SMTP ADDRESS Failed Exception============" + e);

			System.out.println("Exception" + e);
			e.printStackTrace();
		} catch (Exception e) {

			System.out.println("sendSubAccAcivationLink Exception" + e);
			e.printStackTrace();
		}
	}

	public void sendApprovalEmailToRE(NimaiEmailScheduler schdulerData) {
		// TODO Auto-generated method stub
		EmailComponentMaster emailconfigurationBean = null;
		try {

			// emailconfigurationBean=emailComRepo.findEventConfiguration(schdulerData.getEvent());
			emailconfigurationBean = emailComRepo.findEventConfiguration(schdulerData.getEvent());

			System.out.println(" Fetching Configuration for Reset Password Policy " + emailconfigurationBean);
			Map<String, String> subject = new HashMap<String, String>();
			Map<String, String> body = new HashMap<String, String>();

			subject.put("Subject", emailconfigurationBean.getSubject());

			// ArrayList attachements = new ArrayList();

			body.put("username", schdulerData.getUserName());
			body.put("businessName", schdulerData.getDescription1());

			String toMail = schdulerData.getEmailId();

			// emailConfigurationDAOImpl.saveCCEmails(uatCcEMail,
			// emailconfigurationBean.getEventId());
			emailConfigurationDAOImpl.saveBCCEmails(uatCcEMail, emailconfigurationBean.getEventId());
			System.out.println("Fetching Configuration for Reset Password Policy " + emailconfigurationBean.getSubject()
					+ " :: " + emailconfigurationBean.getEventId());
			ArrayList attachements = new ArrayList();
			ArrayList details = new ArrayList();
			details.add(emailconfigurationBean.getEventId());// 0
			details.add(toMail);// 1
			details.add(subject);// 2
			details.add(body);// 3
			details.add(attachements);
			System.out.println("details" + details);
			emailProcessorImpl.saveEmail(details, emailconfigurationBean.getEventId());

			emailSend.getDetailsEmail();

		} catch (SMTPAddressFailedException e) {
			logger.info("=======AdminEmail SMTP ADDRESS Failed Exception============" + e);
			System.out.println("AdminEmail Exception" + e);
			e.printStackTrace();
		} catch (Exception e) {

			System.out.println("AdminEmail Exception" + e);
			e.printStackTrace();
		}

	}

	public void sendRejectionEmailFromRmTORe(NimaiEmailScheduler schdulerData, String cNumber2, String email2) {
		// TODO Auto-generated method stub
		EmailComponentMaster emailconfigurationBean = null;
		try {

			// emailconfigurationBean=emailComRepo.findEventConfiguration(schdulerData.getEvent());
			Map<String, String> body = new HashMap<String, String>();
			if (schdulerData.getEvent().equalsIgnoreCase(AppConstants.KYC_REJ_FROMRM_TO_RE_Support)) {
				emailconfigurationBean = emailComRepo.findEventConfiguration(AppConstants.KYC_REJ_FROMRM_TO_RE_Support);
				body.put("businessName", schdulerData.getDescription2());
			} else {
				emailconfigurationBean = emailComRepo.findEventConfiguration(schdulerData.getEvent());
				body.put("rmName", schdulerData.getrMName());
				body.put("rmContactNumber", schdulerData.getSubMobile());
				body.put("rmEmailId", schdulerData.getrMemailId());
				body.put("businessName", schdulerData.getDescription1());
			}

			System.out.println(" Fetching Configuration for Reset Password Policy " + emailconfigurationBean);
			Map<String, String> subject = new HashMap<String, String>();

			subject.put("Subject", emailconfigurationBean.getSubject());

			// ArrayList attachements = new ArrayList();
			NimaiSystemConfig configDetails = null;
			// configDetails= systemConfig.findBySystemId(13);
			configDetails = systemConfig.findBySystemValue("link");
			System.out.println("system value" + configDetails.getSystemEntityValue());
			body.put("username", schdulerData.getUserName());

			NimaiSystemConfig configDetail = null;

			body.put("customerSupportNumber", cNumber2);

			body.put("customerEmailId", email2);

			body.put("kycType", schdulerData.getKycDocName());
			body.put("link", configDetails.getSystemEntityValue());
			System.out.println("system value" + configDetails.getSystemEntityValue());
			String toMail = schdulerData.getEmailId();

			// emailConfigurationDAOImpl.saveCCEmails(uatCcEMail,
			// emailconfigurationBean.getEventId());
			emailConfigurationDAOImpl.saveBCCEmails(uatCcEMail, emailconfigurationBean.getEventId());
			System.out.println("Fetching Configuration for Reset Password Policy " + emailconfigurationBean.getSubject()
					+ " :: " + emailconfigurationBean.getEventId());
			ArrayList attachements = new ArrayList();
			ArrayList details = new ArrayList();
			details.add(emailconfigurationBean.getEventId());// 0
			details.add(toMail);// 1
			details.add(subject);// 2
			details.add(body);// 3
			details.add(attachements);
			System.out.println("details" + details);
			emailProcessorImpl.saveEmail(details, emailconfigurationBean.getEventId());

			emailSend.getDetailsEmail();

		} catch (SMTPAddressFailedException e) {
			logger.info("=======AdminEmail SMTP ADDRESS Failed Exception============" + e);
			System.out.println("AdminEmail Exception" + e);
			e.printStackTrace();
		} catch (Exception e) {

			System.out.println("AdminEmail Exception" + e);
			e.printStackTrace();
		}
	}

	public void sendCustAccountReferredEmailToSource(NimaiEmailScheduler schdulerData) {
		// TODO Auto-generated method stub
		EmailComponentMaster emailconfigurationBean = null;
		try {

			emailconfigurationBean = emailComRepo.findEventConfiguration(schdulerData.getEvent());
			System.out.println(" Fetching Configuration for Reset Password Policy " + emailconfigurationBean);
			Map<String, String> subject = new HashMap<String, String>();
			Map<String, String> body = new HashMap<String, String>();

			subject.put("Subject", emailconfigurationBean.getSubject());
			Utils util = new Utils();
			// ArrayList attachements = new ArrayList();
			String amount = String.valueOf(util.referrerAmount(Integer.parseInt(schdulerData.getSubscriptionAmount())));
			body.put("username", schdulerData.getUserName());
			body.put("companyname", schdulerData.getDescription1());
			body.put("splanAmount", amount);

			String toMail = schdulerData.getEmailId();

			// emailConfigurationDAOImpl.saveCCEmails(uatCcEMail,
			// emailconfigurationBean.getEventId());
			emailConfigurationDAOImpl.saveBCCEmails(uatCcEMail, emailconfigurationBean.getEventId());
			System.out.println("Fetching Configuration for Reset Password Policy " + emailconfigurationBean.getSubject()
					+ " :: " + emailconfigurationBean.getEventId());
			ArrayList attachements = new ArrayList();
			ArrayList details = new ArrayList();
			details.add(emailconfigurationBean.getEventId());// 0
			details.add(toMail);// 1
			details.add(subject);// 2
			details.add(body);// 3
			details.add(attachements);
			System.out.println("details" + details);
			emailProcessorImpl.saveEmail(details, emailconfigurationBean.getEventId());

			emailSend.getDetailsEmail();

		} catch (SMTPAddressFailedException e) {
			logger.info("=======AdminEmail SMTP ADDRESS Failed Exception============" + e);
			System.out.println("AdminEmail Exception" + e);
			e.printStackTrace();
		} catch (Exception e) {

			System.out.println("AdminEmail Exception" + e);
			e.printStackTrace();
		}
	}

	public void sendConsolidatedEmail1day(List<TupleBean> listOfKeys2, NimaiClient clientUseId) {
		// TODO Auto-generated method stub
		EmailComponentMaster emailconfigurationBean = null;
		try {
			String event = "CONSOLIDATD_1_DAYOFMONTH";
			emailconfigurationBean = emailConfigurationDAOImpl.findEventConfiguration(event);

			emailconfigurationBean = emailComRepo.findEventConfiguration(event);
			HtmlCreationUtility util = new HtmlCreationUtility();

			String html = util.generateConsolidatedHtmlTemplateReport(listOfKeys2, clientUseId);

			System.out.println("Fetching Configuration for Reset Password Policy " + emailconfigurationBean);
			Map<String, String> subject = new HashMap<String, String>();
			Map<String, String> body = new HashMap<String, String>();

			body.put("htmlBody", html);
			String toMail = clientUseId.getEmailAddress();

			// emailConfigurationDAOImpl.saveCCEmails(uatCcEMail,
			// emailconfigurationBean.getEventId());
			emailConfigurationDAOImpl.saveBCCEmails(uatCcEMail, emailconfigurationBean.getEventId());
			System.out.println("Fetching Configuration for transaction status" + emailconfigurationBean.getSubject()
					+ " :: " + emailconfigurationBean.getEventId());
			ArrayList attachements = new ArrayList();
			ArrayList details = new ArrayList();
			details.add(emailconfigurationBean.getEventId());// 0
			details.add(toMail);// 1
			details.add(subject);// 2
			details.add(body);// 3
			details.add(attachements);
			System.out.println("details" + details);
			emailProcessorImpl.saveEmail(details, emailconfigurationBean.getEventId());

			emailSend.getDetailsEmail();

		} catch (SMTPAddressFailedException e) {
			logger.info("=======sendCustEodDailyReport SMTP ADDRESS Failed Exception============" + e);
			System.out.println("sendCustEodDailyReport Exception" + e);
			e.printStackTrace();
		} catch (Exception e) {

			System.out.println("sendCustEodDailyReport Exception" + e);
			e.printStackTrace();
		}

	}

	public void sendLcStatusEmailDataToPaUser(NimaiEmailSchedulerAlertToBanks schdulerData,
			NimaiLC custTransactionDetails, NimaiClient custDetails, NimaiMBranch branchDetails) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		EmailComponentMaster emailconfigurationBean = null;
		GenericResponse response = new GenericResponse();
		try {
			// emailconfigurationBean =
			// emailConfigurationDAOImpl.findEventConfiguration(schdulerData.getEmailEvent());
			emailconfigurationBean = emailComRepo.findEventConfiguration("Passcode_LC_UPLOAD(DATA)");
			logger.info("========useraname=======:::" + schdulerData.getCustomerUserName());
			logger.info(" Fetching Configuration for Reset Password Policy " + emailconfigurationBean);

			Map<String, String> subject = new HashMap<String, String>();
			Map<String, String> body = new HashMap<String, String>();
			// String lcValue = Double.toString(transactionDetails.getlCValue());
			String lcValue = new BigDecimal(custTransactionDetails.getlCValue()).toEngineeringString();

			String lcIssuingDate = convertSToDate(custTransactionDetails.getlCIssuingDate());
			// String lcExpiryDate = convertSToDate(custTransactionDetails.getValidity());
			subject.put("Subject", emailconfigurationBean.getSubject());
			body.put("transactionId", schdulerData.getTransactionid());
			body.put("username", branchDetails.getEmployeeName());
			body.put("companyname", custDetails.getCompanyName());
			body.put("userId", schdulerData.getCustomerid());
			body.put("lcIssuingValue", lcValue);
			body.put("lcIssuingDate", lcIssuingDate);
			body.put("passcodeUserEmail", schdulerData.getPasscodeuserEmail());
			body.put("lcExpiryDate", custTransactionDetails.getValidity().trim().substring(0, 10).replaceAll("-", "/"));
			if (custTransactionDetails.getRequirementType().equalsIgnoreCase("ConfirmAndDiscount")) {
				body.put("productRequirementName", "Confirmation & Discounting");
			} else if (custTransactionDetails.getRequirementType().equalsIgnoreCase("Refinance")) {
				body.put("productRequirementName", " Refinancing");
			} else if (custTransactionDetails.getRequirementType().equalsIgnoreCase("Banker")) {
				body.put("productRequirementName", " Bankers Acceptance");
			} else {
				body.put("productRequirementName", custTransactionDetails.getRequirementType());
			}
			body.put("currency", custTransactionDetails.getlCCurrency());
			sendPasscodeUserLcnotificationToCust(schdulerData, custTransactionDetails, custDetails, branchDetails);
			String passCodeEamil = schdulerData.getPasscodeuserEmail();
			emailConfigurationDAOImpl.saveBCCEmails(uatCcEMail, emailconfigurationBean.getEventId());
			logger.info("Fetching Configuration for transaction status" + emailconfigurationBean.getSubject() + " :: "
					+ emailconfigurationBean.getEventId());

			ArrayList attachements = new ArrayList();
			ArrayList details = new ArrayList();
			details.add(emailconfigurationBean.getEventId());// 0
			details.add(passCodeEamil);// 1
			details.add(subject);// 2
			details.add(body);// 3
			details.add(attachements);

			logger.info("details" + details);

			emailProcessorImpl.saveEmail(details, emailconfigurationBean.getEventId());

			emailSend.getDetailsEmail();

		} catch (SMTPAddressFailedException e) {
			logger.info("=======SMTP ADDRESS Failed Exception============" + e);
			logger.info("Exception" + e);
			e.printStackTrace();
		} catch (Exception e) {
			logger.info("=======Exception============" + e);
			e.printStackTrace();
		}
	}

	private void sendPasscodeUserLcnotificationToCust(NimaiEmailSchedulerAlertToBanks schdulerData,
			NimaiLC custTransactionDetails, NimaiClient custDetails, NimaiMBranch branchDetails) {
		// TODO Auto-generated method stub
		EmailComponentMaster emailconfigurationBean = null;
		GenericResponse response = new GenericResponse();
		try {
			// emailconfigurationBean =
			// emailConfigurationDAOImpl.findEventConfiguration(schdulerData.getEmailEvent());
			emailconfigurationBean = emailComRepo.findEventConfiguration("Passcode_LC_UPLOAD(DATA)_Alert_TO_Parent");
			logger.info("========useraname=======:::" + schdulerData.getCustomerUserName());
			logger.info(" Fetching Configuration for Reset Password Policy " + emailconfigurationBean);
			String ccEmail = "";
			Map<String, String> subject = new HashMap<String, String>();
			Map<String, String> body = new HashMap<String, String>();
			// String lcValue = Double.toString(transactionDetails.getlCValue());
			String lcValue = new BigDecimal(custTransactionDetails.getlCValue()).toEngineeringString();

			String lcIssuingDate = convertStringToDate(custTransactionDetails.getlCIssuingDate());
			// String lcExpiryDate =
			// convertStringToDate(custTransactionDetails.getValidity());
			String lcExpiryDate = custTransactionDetails.getValidity().trim().substring(0, 10).replaceAll("-", "/");
			subject.put("Subject", emailconfigurationBean.getSubject());
			body.put("transactionId", schdulerData.getTransactionid());
			body.put("username", custDetails.getFirstName());
			body.put("userId", schdulerData.getCustomerid());
			body.put("lcIssuingValue", lcValue);
			body.put("lcIssuingDate", lcIssuingDate);
			body.put("lcExpiryDate", lcExpiryDate);
			// body.put("productRequirementName",
			// custTransactionDetails.getRequirementType());
			body.put("currency", custTransactionDetails.getlCCurrency());
			body.put("passcodeUserEmail", schdulerData.getPasscodeuserEmail());
			if (custTransactionDetails.getRequirementType().equalsIgnoreCase("ConfirmAndDiscount")) {
				body.put("productRequirementName", "Confirmation & Discounting");
			} else if (custTransactionDetails.getRequirementType().equalsIgnoreCase("Refinance")) {
				body.put("productRequirementName", " Refinancing");
			} else if (custTransactionDetails.getRequirementType().equalsIgnoreCase("Banker")) {
				body.put("productRequirementName", " Bankers Acceptance");
			} else {
				body.put("productRequirementName", custTransactionDetails.getRequirementType());
			}
			String toMail = schdulerData.getCustomerEmail();
			emailConfigurationDAOImpl.saveBCCEmails(uatCcEMail, emailconfigurationBean.getEventId());
//		
			ArrayList attachements = new ArrayList();
			ArrayList details = new ArrayList();
			details.add(emailconfigurationBean.getEventId());// 0
			details.add(toMail);// 1
			details.add(subject);// 2
			details.add(body);// 3
			details.add(attachements);

			logger.info("details" + details);

			emailProcessorImpl.saveEmail(details, emailconfigurationBean.getEventId());

			emailSend.getDetailsEmail();

		} catch (SMTPAddressFailedException e) {
			logger.info("=======SMTP ADDRESS Failed Exception============" + e);
			logger.info("Exception" + e);
			e.printStackTrace();
		} catch (Exception e) {
			logger.info("=======Exception============" + e);
			e.printStackTrace();
		}
	}

	public void sendPaymentFailureEmail(NimaiEmailScheduler schdulerData, NimaiClient clientUseId,
			NimaiSubscriptionDetails subDetails, String planFailureName) {
		// TODO Auto-generated method stub

		OnlinePayment paymentDetails = payRepo.findByUId(schdulerData.getUserid());
		EmailComponentMaster emailconfigurationBean = null;
		GenericResponse response = new GenericResponse();
		try {
			// emailconfigurationBean =
			// emailConfigurationDAOImpl.findEventConfiguration(schdulerData.getEmailEvent());
			emailconfigurationBean = emailComRepo.findEventConfiguration("CUST_SPLAN_CREDIT_PAYMENT_FAILURE");
			logger.info("========useraname=======:::" + clientUseId.getFirstName());
			logger.info(" Fetching Configuration for Reset Password Policy " + emailconfigurationBean);
			String ccEmail = "";
			Map<String, String> subject = new HashMap<String, String>();
			Map<String, String> body = new HashMap<String, String>();
			// String lcValue = Double.toString(transactionDetails.getlCValue());
			body.put("username", clientUseId.getFirstName());
			body.put("orderId", paymentDetails.getOrderId());

			String toMail = clientUseId.getEmailAddress();
//		
			ArrayList attachements = new ArrayList();
			ArrayList details = new ArrayList();
			details.add(emailconfigurationBean.getEventId());// 0
			details.add(toMail);// 1
			details.add(subject);// 2
			details.add(body);// 3
			details.add(attachements);

			logger.info("details" + details);

			emailProcessorImpl.saveEmail(details, emailconfigurationBean.getEventId());

			emailSend.getDetailsEmail();

		} catch (SMTPAddressFailedException e) {
			logger.info("=======SMTP ADDRESS Failed Exception============" + e);
			logger.info("Exception" + e);
			e.printStackTrace();
		} catch (Exception e) {
			logger.info("=======Exception============" + e);
			e.printStackTrace();
		}

	}

	public void sendVasRejectedEmail(NimaiEmailScheduler schdulerData) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub

		EmailComponentMaster emailconfigurationBean = null;
		GenericResponse response = new GenericResponse();
		try {
			// emailconfigurationBean =
			// emailConfigurationDAOImpl.findEventConfiguration(schdulerData.getEmailEvent());
			emailconfigurationBean = emailComRepo.findEventConfiguration(schdulerData.getEvent());
			logger.info("========useraname=======:::" + schdulerData.getUserName());
			logger.info(" Fetching Configuration for Reset Password Policy " + emailconfigurationBean);
			String ccEmail = "";
			Map<String, String> subject = new HashMap<String, String>();
			Map<String, String> body = new HashMap<String, String>();
			// String lcValue = Double.toString(transactionDetails.getlCValue());
			body.put("username", schdulerData.getUserName());

			String toMail = schdulerData.getEmailId();
//				
			ArrayList attachements = new ArrayList();
			ArrayList details = new ArrayList();
			details.add(emailconfigurationBean.getEventId());// 0
			details.add(toMail);// 1
			details.add(subject);// 2
			details.add(body);// 3
			details.add(attachements);

			logger.info("details" + details);

			emailProcessorImpl.saveEmail(details, emailconfigurationBean.getEventId());

			emailSend.getDetailsEmail();

		} catch (SMTPAddressFailedException e) {
			logger.info("=======SMTP ADDRESS Failed Exception============" + e);
			logger.info("Exception" + e);
			e.printStackTrace();
		} catch (Exception e) {
			logger.info("=======Exception============" + e);
			e.printStackTrace();
		}
	}

	public void sendVasPaymentFailureEmail(NimaiEmailScheduler schdulerData, NimaiClient clientUseId,
			NimaiSubscriptionDetails subDetails) {
		// TODO Auto-generated method stub

		OnlinePayment paymentDetails = payRepo.findByUId(schdulerData.getUserid());
		EmailComponentMaster emailconfigurationBean = null;
		GenericResponse response = new GenericResponse();
		try {
			// emailconfigurationBean =
			// emailConfigurationDAOImpl.findEventConfiguration(schdulerData.getEmailEvent());
			emailconfigurationBean = emailComRepo.findEventConfiguration("CUST_SPLAN_CREDIT_PAYMENT_FAILURE");
			logger.info("========useraname=======:::" + clientUseId.getFirstName());
			logger.info(" Fetching Configuration for Reset Password Policy " + emailconfigurationBean);
			String ccEmail = "";
			Map<String, String> subject = new HashMap<String, String>();
			Map<String, String> body = new HashMap<String, String>();
			// String lcValue = Double.toString(transactionDetails.getlCValue());
			body.put("username", clientUseId.getFirstName());
			body.put("orderId", paymentDetails.getOrderId());

			String toMail = clientUseId.getEmailAddress();
//		
			ArrayList attachements = new ArrayList();
			ArrayList details = new ArrayList();
			details.add(emailconfigurationBean.getEventId());// 0
			details.add(toMail);// 1
			details.add(subject);// 2
			details.add(body);// 3
			details.add(attachements);

			logger.info("details" + details);

			emailProcessorImpl.saveEmail(details, emailconfigurationBean.getEventId());

			emailSend.getDetailsEmail();

		} catch (SMTPAddressFailedException e) {
			logger.info("=======SMTP ADDRESS Failed Exception============" + e);
			logger.info("Exception" + e);
			e.printStackTrace();
		} catch (Exception e) {
			logger.info("=======Exception============" + e);
			e.printStackTrace();
		}

	}

	public void sendApprovalEmailToBank(NimaiEmailScheduler schdulerData) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		EmailComponentMaster emailconfigurationBean = null;
		try {

			// emailconfigurationBean =
			// emailConfigurationDAOImpl.findEventConfiguration(schdata.getEvent());

			emailconfigurationBean = emailComRepo.findEventConfiguration(schdulerData.getEvent());

			System.out.println(" Fetching Configuration for Reset Password Policy " + emailconfigurationBean);
			Map<String, String> subject = new HashMap<String, String>();
			Map<String, String> body = new HashMap<String, String>();

			subject.put("Subject", emailconfigurationBean.getSubject());

			// ArrayList attachements = new ArrayList();

			body.put("username", schdulerData.getUserName());
			body.put("businessName", schdulerData.getDescription1());

			String toMail = schdulerData.getEmailId();

			// emailConfigurationDAOImpl.saveCCEmails(uatCcEMail,
			// emailconfigurationBean.getEventId());
			emailConfigurationDAOImpl.saveBCCEmails(uatCcEMail, emailconfigurationBean.getEventId());
			System.out.println("Fetching Configuration for Reset Password Policy " + emailconfigurationBean.getSubject()
					+ " :: " + emailconfigurationBean.getEventId());
			ArrayList attachements = new ArrayList();
			ArrayList details = new ArrayList();
			details.add(emailconfigurationBean.getEventId());// 0
			details.add(toMail);// 1
			details.add(subject);// 2
			details.add(body);// 3
			details.add(attachements);
			System.out.println("details" + details);
			emailProcessorImpl.saveEmail(details, emailconfigurationBean.getEventId());

			emailSend.getDetailsEmail();

		} catch (SMTPAddressFailedException e) {
			logger.info("=======AdminEmail SMTP ADDRESS Failed Exception============" + e);
			System.out.println("AdminEmail Exception" + e);
			e.printStackTrace();
		} catch (Exception e) {

			System.out.println("AdminEmail Exception" + e);
			e.printStackTrace();
		}
	}

	public void sendRejectionEmailFromRmTOBA(NimaiEmailScheduler schdulerData, String cNumber, String email) {
		// TODO Auto-generated method stub
		EmailComponentMaster emailconfigurationBean = null;
		try {

			// emailconfigurationBean=emailComRepo.findEventConfiguration(schdulerData.getEvent());
			Map<String, String> body = new HashMap<String, String>();
			if (schdulerData.getEvent().equalsIgnoreCase(AppConstants.KYC_REJ_FROMRM_TO_BA_Support)) {
				emailconfigurationBean = emailComRepo.findEventConfiguration(AppConstants.KYC_REJ_FROMRM_TO_BA_Support);
				body.put("businessName", schdulerData.getDescription2());
			} else {
				emailconfigurationBean = emailComRepo.findEventConfiguration(schdulerData.getEvent());

				body.put("rmName", schdulerData.getrMName());
				body.put("rmContactNumber", schdulerData.getSubMobile());
				body.put("rmEmailId", schdulerData.getrMemailId());
				body.put("businessName", schdulerData.getDescription1());
			}

			System.out.println(" Fetching Configuration for Reset Password Policy " + emailconfigurationBean);
			Map<String, String> subject = new HashMap<String, String>();

			subject.put("Subject", emailconfigurationBean.getSubject());

			// ArrayList attachements = new ArrayList();
			NimaiSystemConfig configDetails = null;
			// configDetails= systemConfig.findBySystemId(13);
			configDetails = systemConfig.findBySystemValue("link");
			System.out.println("system value" + configDetails.getSystemEntityValue());
			body.put("username", schdulerData.getUserName());
			body.put("customerSupportNumber", cNumber);

			body.put("customerEmailId", email);

			body.put("kycType", schdulerData.getKycDocName());
			body.put("link", configDetails.getSystemEntityValue());
			System.out.println("system value" + configDetails.getSystemEntityValue());
			String toMail = schdulerData.getEmailId();

			// emailConfigurationDAOImpl.saveCCEmails(uatCcEMail,
			// emailconfigurationBean.getEventId());
			emailConfigurationDAOImpl.saveBCCEmails(uatCcEMail, emailconfigurationBean.getEventId());
			System.out.println("Fetching Configuration for Reset Password Policy " + emailconfigurationBean.getSubject()
					+ " :: " + emailconfigurationBean.getEventId());
			ArrayList attachements = new ArrayList();
			ArrayList details = new ArrayList();
			details.add(emailconfigurationBean.getEventId());// 0
			details.add(toMail);// 1
			details.add(subject);// 2
			details.add(body);// 3
			details.add(attachements);
			System.out.println("details" + details);
			emailProcessorImpl.saveEmail(details, emailconfigurationBean.getEventId());

			emailSend.getDetailsEmail();

		} catch (SMTPAddressFailedException e) {
			logger.info("=======AdminEmail SMTP ADDRESS Failed Exception============" + e);
			System.out.println("AdminEmail Exception" + e);
			e.printStackTrace();
		} catch (Exception e) {

			System.out.println("AdminEmail Exception" + e);
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		EmaiInsert em = new EmaiInsert();
		Date date = new Date();
		em.convertSToDate(date);
		System.out.println("COnverted Date" + em.convertSToDate(date));
		String datenew = "  2021-07-07 00:00:00";
		String newDate = datenew.trim().substring(0, 10).replaceAll("-", "/");
		// newDate.format("dd/mm/yyyy", newDate);
		System.out.println(newDate);

	}

	public void sendBidRecivedEmailToPassCodeCust(String event, NimaiEmailSchedulerAlertToBanks schdulerData,
			String passcodeEmail, NimaiMBranch branchDetails) {
		// TODO Auto-generated method stub
		EmailComponentMaster emailconfigurationBean = null;
		NimaiSystemConfig configDetails = null;
		// configDetails= systemConfig.findBySystemId(13);
		configDetails = systemConfig.findBySystemValue("link");
		System.out.println("system value" + configDetails.getSystemEntityValue());
		try {
			// emailconfigurationBean =
			// emailConfigurationDAOImpl.findEventConfiguration(emailEvent);
			emailconfigurationBean = emailComRepo.findEventConfiguration(event);

			System.out.println(" Fetching Configuration for Reset Password Policy " + emailconfigurationBean);
			String requiredPage = "quoteDetails";
			String quoteLlink = quoteDetailsLink + requiredPage;
			Map<String, String> subject = new HashMap<String, String>();
			Map<String, String> body = new HashMap<String, String>();
			subject.put("Subject", emailconfigurationBean.getSubject());
			body.put("transactionId", schdulerData.getTransactionid());
			body.put("customerName", branchDetails.getEmployeeName());
			body.put("link", configDetails.getSystemEntityValue());
			String toMail = passcodeEmail;
//			emailConfigurationDAOImpl.saveCCEmails(schdulerData.getCustomerEmail(),
//					emailconfigurationBean.getEventId());
			emailConfigurationDAOImpl.saveBCCEmails(uatCcEMail, emailconfigurationBean.getEventId());
			System.out.println("Fetching Configuration for transaction status" + emailconfigurationBean.getSubject()
					+ " :: " + emailconfigurationBean.getEventId());
			ArrayList attachements = new ArrayList();
			ArrayList details = new ArrayList();
			details.add(emailconfigurationBean.getEventId());// 0
			details.add(toMail);// 1
			details.add(subject);// 2
			details.add(body);// 3
			details.add(attachements);
			System.out.println("details" + details);
			emailProcessorImpl.saveEmail(details, emailconfigurationBean.getEventId());

			emailSend.getDetailsEmail();

		} catch (SMTPAddressFailedException e) {
			logger.info("=======sendBidRecivedEmailToCust SMTP ADDRESS Failed Exception============" + e);
			System.out.println("sendBidRecivedEmailToCust Exception" + e);
			e.printStackTrace();
		} catch (Exception e) {

			System.out.println("sendBidRecivedEmailToCust Exception" + e);
			e.printStackTrace();
		}

	}

	public void sendQuotationStatusEmailToPassCodeCust(String emailEvent, NimaiEmailSchedulerAlertToBanks schdulerData,
			String passcodeUserEmail, QuotationMaster bankQuotationDetails, NimaiLC custTransactionDetails,
			NimaiClient bankDetails, String savingsDetails, NimaiMBranch branchDetails) {
		// TODO Auto-generated method stub
		logger.info("=========Inside sendQuotationStatusEmailToCust method with schedulerData from database==========");
		EmailComponentMaster emailconfigurationBean = null;

		try {
			// emailconfigurationBean =
			// emailConfigurationDAOImpl.findEventConfiguration(schdulerData.getEmailEvent());
			emailconfigurationBean = emailComRepo.findEventConfiguration("QUOTE_ACCEPT");

			System.out.println("Fetching Configuration for Reset Password Policy " + emailconfigurationBean);
			Map<String, String> subject = new HashMap<String, String>();
			Map<String, String> body = new HashMap<String, String>();
			String quoteId = String.valueOf(schdulerData.getQuotationId());
			String Subject = "Quote acceptance confirmation";
			emailConfigurationDAOImpl.updateSubject(Subject, emailconfigurationBean.getEventId());
			String custName = branchDetails.getEmployeeName();
			String bankName = bankDetails.getBankNbfcName();
			String country = bankDetails.getCountryName();
			String contacatPerson = bankDetails.getFirstName() + " " + bankDetails.getLastName();
			String emailAddress = bankDetails.getEmailAddress();
			String phone = bankDetails.getMobileNumber();
			Float amount = bankQuotationDetails.getTotalQuoteValue();
			String telePhone = bankDetails.getTelephone();
			String quoteValue = String.valueOf(amount);
			String htmlValue = "bank's";
			subject.put("Subject", Subject);

			body.put("htmlBody",
					"<br>Dear" + " " + custName + ", " + "<br><br>Congratulations! <br><br>"
							+ "You have accepted quote from" + " " + bankName + " " + "against the transaction ID" + " "
							+ schdulerData.getTransactionid() + "<br><br>\r\n" + "<br>Accepted Quote Details" + " "
							+ custTransactionDetails.getlCCurrency() + " " + amount + "| Savings on this transaction"
							+ " " + custTransactionDetails.getlCCurrency() + " " + savingsDetails + "<br>\n"
							+ "<br>Please find below the underwriting" + " " + htmlValue + " "
							+ "contact details:<br>\n" + "<br>Bank :" + " " + bankName + "<br>\n" + "Country :" + " "
							+ country + "<br>\n" + "Contact Person :" + " " + contacatPerson + "<br>\n" + "Email :"
							+ " " + emailAddress + "<br>\n" + "Phone :" + " " + telePhone + "<br>\n" + "Mobile :" + " "
							+ phone + "<br>\n" + "<br>\n" + "<br>Warm Regards,<br>\n" + "Team 360tf<br>\n"
							+ "-------------------------------------------------------------------\n"
							+ "<br>Copyright & Disclaimer | Privacy Policy<br><br>\n"
							+ "Please do not reply to this mail as this is automated mail service\r\n" + "<br>");

			if (schdulerData.getEmailEvent().equalsIgnoreCase("QUOTE_REJECTION_CUSTOMER")) {
				emailconfigurationBean = emailComRepo.findEventConfiguration("QUOTE_REJECTION");
				if (bankQuotationDetails.getRejectedBy().equalsIgnoreCase("BANK")) {
					String subjectRejection = "Quote rejected by bank";
					subject.put("Subject", subjectRejection);
					body.put("reason", schdulerData.getReason());
					emailConfigurationDAOImpl.updateSubject(subjectRejection, emailconfigurationBean.getEventId());
					body.put("quotationId", quoteId);
					body.put("htmlBody", "<br>Dear" + " " + custName + ", <br><br>" + bankDetails.getBankNbfcName()
							+ " " + "has rejected the quote.<br><br>\r\n"
							+ "Quotation place details against customer are as follows:<br><br>\r\n"
							+ "<table style=\"border: 1px solid black!important; border-collapse: collapse;\">\r\n"
							+ "          <thead>\r\n" + "              <tr>\r\n"
							+ "                  <th style=\"background-color:#98AFC7;border: 1px solid black!important; border-collapse: collapse;\">TransactionId</th>\r\n"
							+ "   <th style=\"background-color:#98AFC7;border: 1px solid black!important; border-collapse: collapse;\">Currency</th>\r\n"
							+ "                  <th style=\"background-color:#98AFC7;border: 1px solid black!important; border-collapse: collapse;\">Amount</th>\r\n"
							+ "              </tr>\r\n" + "          </thead>\r\n" + "          <tbody>\r\n"
							+ "              <tr>\r\n"
							+ "                  <td style=\"background-color:#ADD8E6;border: 1px solid black!important; border-collapse: collapse;\">"
							+ schdulerData.getTransactionid() + "</td>\r\n"
							+ "                  <td style=\"background-color:#ADD8E6;border: 1px solid black!important; border-collapse: collapse;\">"
							+ custTransactionDetails.getlCCurrency() + "</td>\r\n"
							+ "<td style=\"background-color:#ADD8E6;border: 1px solid black!important; border-collapse: collapse;\">"
							+ String.valueOf(custTransactionDetails.getlCValue()) + "</td>\r\n" +

							"              </tr>           \r\n" + "          </tbody>\r\n" + "      </table>\r\n"
							+ "<br>For further details, open Transaction Details -> Rejected Transaction\r\n" + "<br>"
							+ " <br> <br>\r\n" + " <br>Warm Regards,<br>\r\n" + "Team 360tf<br>  \r\n"
							+ "-------------------------------------------------------------------  \r\n"
							+ "<br>Copyright & Disclaimer | Privacy Policy<br><br>  \r\n"
							+ "Please do not reply to this mail as this is automated mail service<br>");
				} else {
					String subjectRejection = "Quote Rejected";

					String baName = bankQuotationDetails.getBankName();
					emailConfigurationDAOImpl.updateSubject(subjectRejection, emailconfigurationBean.getEventId());
					subject.put("Subject", subjectRejection);
					body.put("reason", schdulerData.getReason());
					body.put("htmlBody", "<br>Dear" + " " + custName
							+ "<br><br>You have rejected the quote for transaction ID" + " " + "<b>"
							+ schdulerData.getTransactionid() + "</b>" + " " + "received from" + " " + "<b>" + baName
							+ "</b>.<br>\r\n"
							+ "<br>You can reopen the transaction from Rejected Transaction section. Upon reopening the transaction you can accept available quotes or wait for new quotes"
							+ ".<br>\r\n" + "<br>Warm Regards,<br>\n" + "Team 360tf<br>\n"
							+ "-------------------------------------------------------------------\n"
							+ "<br>Copyright & Disclaimer | Privacy Policy<br><br>\n"
							+ "Please do not reply to this mail as this is automated mail service\r\n" + "<br>");

				}

			}

			String toMail = passcodeUserEmail;

//			emailConfigurationDAOImpl.saveCCEmails(schdulerData.getCustomerEmail(),
//					emailconfigurationBean.getEventId());
			emailConfigurationDAOImpl.saveBCCEmails(uatCcEMail, emailconfigurationBean.getEventId());
			System.out.println("Fetching Configuration for transaction status" + emailconfigurationBean.getSubject()
					+ " :: " + emailconfigurationBean.getEventId());
			ArrayList attachements = new ArrayList();
			ArrayList details = new ArrayList();
			details.add(emailconfigurationBean.getEventId());// 0
			details.add(toMail);// 1
			details.add(subject);// 2
			details.add(body);// 3
			details.add(attachements);

			logger.info("details" + details);
			emailProcessorImpl.saveEmail(details, emailconfigurationBean.getEventId());

			emailSend.getDetailsEmail();

		} catch (SMTPAddressFailedException e) {
			logger.info("=======sendQuotationStatusEmailToCust SMTP ADDRESS Failed Exception============" + e);
			logger.info("sendQuotationStatusEmailToCust Exception" + e);
			e.printStackTrace();
		} catch (Exception e) {
			logger.info("sendQuotationStatusEmailToCust Exception" + e);
			e.printStackTrace();
		}

	}
	


	public void sendCustMonthlyReport(Map<String, List<CustomerBankMonthlyReort>> groupByUserId,
			NimaiClient clientUseId) {
		// TODO Auto-generated method stub
		logger.info("=========Inside sendCustEodDailyReport method with schedulerData from database==========");
		EmailComponentMaster emailconfigurationBean = null;
		try {
			String event = "CU_BC_MONTHLY_REPORT";
			//emailconfigurationBean = emailConfigurationDAOImpl.findEventConfiguration(event);
			emailconfigurationBean=emailComRepo.findEventConfiguration(event);
			HtmlCreationUtility util = new HtmlCreationUtility();

			String html = util.generateCustMonthlyHtmlTemplateReport(groupByUserId, clientUseId);
			Map<String, String> subject = new HashMap<String, String>();
			Map<String, String> body = new HashMap<String, String>();
			body.put("htmlBody", html);
			String toMail = clientUseId.getEmailAddress();	
			ArrayList attachements = new ArrayList();
			ArrayList details = new ArrayList();
			details.add(emailconfigurationBean.getEventId());// 0
			details.add(toMail);// 1
			details.add(subject);// 2
			details.add(body);// 3
			details.add(attachements);
			emailProcessorImpl.saveEmail(details, emailconfigurationBean.getEventId());
			emailSend.getDetailsEmail();

		} catch (SMTPAddressFailedException e) {
			logger.info("=======sendCustEodDailyReport SMTP ADDRESS Failed Exception============" + e);
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("sendCustEodDailyReport Exception" + e);
			e.printStackTrace();
		}
	}
	
	
	
	
	public void sendBankMonthlyReport(Map<String, List<BankMonthlyReport>> groupByUserId,
			NimaiClient clientUseId) {
		// TODO Auto-generated method stub
		logger.info("=========Inside sendCustEodDailyReport method with schedulerData from database==========");
		EmailComponentMaster emailconfigurationBean = null;
		try {
			String event = "BA_MONTHLY_REPORT";
			//emailconfigurationBean = emailConfigurationDAOImpl.findEventConfiguration(event);
			emailconfigurationBean=emailComRepo.findEventConfiguration(event);
			HtmlCreationUtility util = new HtmlCreationUtility();

			String html = util.generateBankMonthlyHtmlTemplateReport(groupByUserId, clientUseId);
			Map<String, String> subject = new HashMap<String, String>();
			Map<String, String> body = new HashMap<String, String>();
			body.put("htmlBody", html);
			String toMail = clientUseId.getEmailAddress();	
			ArrayList attachements = new ArrayList();
			ArrayList details = new ArrayList();
			details.add(emailconfigurationBean.getEventId());// 0
			details.add(toMail);// 1
			details.add(subject);// 2
			details.add(body);// 3
			details.add(attachements);
			emailProcessorImpl.saveEmail(details, emailconfigurationBean.getEventId());
			emailSend.getDetailsEmail();

		} catch (SMTPAddressFailedException e) {
			logger.info("=======sendCustEodDailyReport SMTP ADDRESS Failed Exception============" + e);
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("sendCustEodDailyReport Exception" + e);
			e.printStackTrace();
		}
	}

}
