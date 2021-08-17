package com.nimai.email.utility;

import java.util.regex.Pattern;


import org.hibernate.query.criteria.internal.predicate.IsEmptyPredicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nimai.email.bean.UserRegistrationBean;
import com.nimai.email.bean.AlertToBanksBean;
import com.nimai.email.bean.BranchUserPassCodeBean;
import com.nimai.email.bean.BranchUserRequest;
import com.nimai.email.bean.KycEmailRequest;
import com.nimai.email.bean.LcUploadBean;
import com.nimai.email.bean.QuotationAlertRequest;
import com.nimai.email.bean.SubsidiaryBean;
import com.nimai.email.dao.EmailConfigurationdaoImpl;
import com.nimai.email.dao.UserServiceDao;
import com.nimai.email.entity.EmailComponentMaster;
import com.nimai.email.entity.NimaiClient;
import com.nimai.email.entity.NimaiEmailScheduler;
import com.nimai.email.entity.NimaiEmailSchedulerAlertToBanks;
import com.nimai.email.repository.EmailComponentRepository;
import com.nimai.email.service.UserServiceImpl;

@Component
public class ResetUserValidation {

	private static Logger logger = LoggerFactory.getLogger(ResetUserValidation.class);

	@Autowired
	UserServiceDao userDao;

	@Autowired
	EmailConfigurationdaoImpl emailconfigurationBean;

	@Autowired
	EmailConfigurationdaoImpl emailConfigurationDAOImpl;
	
	@Autowired
	EmailComponentRepository emailComRepo;

	public String Validation(UserRegistrationBean resetPasswordBean) {
		logger.info(" ================ Validation utility invoked================" + resetPasswordBean.toString());
		EmailComponentMaster emailconfigurationBean = null;
		final Pattern EMAILPATTERN = Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
		emailconfigurationBean=emailComRepo.findEventConfiguration(resetPasswordBean.getEvent());
		logger.info(" ================ Send resetPaasword Link API is  Invoked ================"
				+ emailconfigurationBean.getEmailEventMaster().getEmailEventName());

		try {

			if (!EMAILPATTERN.matcher(resetPasswordBean.getEmail()).matches()) {
				return "Email id invalid";
			}
			if (!resetPasswordBean.getEvent().trim().isEmpty()) {
				emailconfigurationBean = emailConfigurationDAOImpl.findEventConfiguration(resetPasswordBean.getEvent());
				if (emailconfigurationBean == null) {
					return "Email Event not Exists";
				}

			} else {
				return "Email Event should not be empty.";
			}

			if (!EMAILPATTERN.matcher(resetPasswordBean.getEmail()).matches()) {
				return "Email id invalid";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "Failed : " + e.getMessage();
		}
		return "success";

	}

	public String subsidiaryValidation(SubsidiaryBean subsidiaryBean) {
		logger.info(
				" ================ subsidiaryValidation utility invoked================" + subsidiaryBean.toString());
		EmailComponentMaster emailconfigurationBean = null;
		final Pattern EMAILPATTERN = Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");

		try {
			if (!subsidiaryBean.getUserId().trim().isEmpty()) {
				boolean isUserIdExisist = userDao.isUserIdExisist(subsidiaryBean.getUserId());

				if (isUserIdExisist != true) {
					return "Invalid userId";
				}
			} else {
				return "User Id should not be empty";
			}

			if (!subsidiaryBean.getEvent().trim().isEmpty()) {
				emailconfigurationBean=emailComRepo.findEventConfiguration(subsidiaryBean.getEvent());
				if (emailconfigurationBean == null) {
					return "Email Event not Exists";
				}

			} else {
				return "Email Event should not be empty.";
			}

			if (!EMAILPATTERN.matcher(subsidiaryBean.getEmailId()).matches()) {
				return "Email id invalid";
			}

		} catch (Exception e) {
			e.printStackTrace();
			return "Failed : " + e.getMessage();
		}
		return "success";

	}

	public String forgotPassValidation(String emailId) {
		logger.info(" ================ forgotPassValidation utility invoked================" + emailId);
		NimaiClient client = userDao.getcliDetailsByEmailId(emailId);
		String returnStr = "";
		try {
			if (!client.getEmailAddress().equals(emailId)) {
				return "Invalid Email Address";
			}
			returnStr = "success";
		} catch (Exception e) {
			e.printStackTrace();
			returnStr = "Failed : " + e.getMessage();
		}
		return returnStr;

	}

	public String lcValidation(LcUploadBean lcUploadBean) {
		logger.info(" ================ lcValidation utility invoked================" + lcUploadBean.toString());
		EmailComponentMaster emailconfigurationBean = null;
		final Pattern EMAILPATTERN = Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");

		try {

			if (!(lcUploadBean.getEvent().equalsIgnoreCase("LC_UPLOAD"))
					|| (lcUploadBean.getEvent().equalsIgnoreCase("LC_REJECT"))
					|| (lcUploadBean.getEvent().equalsIgnoreCase("LC_UPDATE"))
					|| (lcUploadBean.getEvent().equalsIgnoreCase("LC_UPLOAD(DATA)")
					|| lcUploadBean.getEvent().equalsIgnoreCase("LC_REJECT(DATA)")
					|| lcUploadBean.getEvent().equalsIgnoreCase("LC_UPDATE(DATA)")
					|| lcUploadBean.getEvent().equalsIgnoreCase("LC_REOPEINING(DATA)"))) {
				return "Invalid Event";
			}
			if (!lcUploadBean.getUserId().trim().isEmpty()) {
				boolean isUserIdExisist = userDao.isUserIdExisist(lcUploadBean.getUserId());

				if (isUserIdExisist != true) {
					return "Invalid userId";
				}
			} else {
				return "User Id should not be empty";
			}

			if (!lcUploadBean.getEvent().trim().isEmpty()) {
				emailconfigurationBean=emailComRepo.findEventConfiguration(lcUploadBean.getEvent());
				if (emailconfigurationBean == null) {
					return "Email Event not Exists";
				}

			} else {
				return "Email Event should not be empty.";
			}

			if (!lcUploadBean.getEvent().trim().isEmpty() && lcUploadBean.getTransactionid() == null) {
				return "Transaction id should not be empty";
			}

		} catch (Exception e) {
			e.printStackTrace();
			return "Failed : " + e.getMessage();
		}
		return "success";
	}

	public String banksEmailValidation(AlertToBanksBean alertBanksBean, String emailId) {
		logger.info(
				" ================ banksEmailValidation utility invoked================" + alertBanksBean.toString(),
				emailId);
		EmailComponentMaster emailconfigurationBean = null;
		final Pattern EMAILPATTERN = Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");

		try {

			if (!((alertBanksBean.getEvent().equalsIgnoreCase("LC_UPLOAD_ALERT_ToBanks")
					|| alertBanksBean.getEvent().equalsIgnoreCase("LC_REJECT_ALERT_ToBanks")
					|| alertBanksBean.getEvent().equalsIgnoreCase("LC_UPDATE_ALERT_ToBanks")
					|| alertBanksBean.getEvent().equalsIgnoreCase("LC_Reopening_ALERT_ToBanks")
					|| alertBanksBean.getEvent().equalsIgnoreCase("QUOTE_ACCEPT")
					||alertBanksBean.getEvent().equalsIgnoreCase("BId_ALERT_ToCustomer")
					||alertBanksBean.getEvent().equalsIgnoreCase("Bank_Details_tocustomer")
					|| alertBanksBean.getEvent().equalsIgnoreCase("QUOTE_REJECTION"))
					|| alertBanksBean.getEvent().equalsIgnoreCase("QUOTE_ACCEPT_ALERT_ToBanks"))) {
				return "Invalid Event";
			}

			if (!alertBanksBean.getEvent().trim().isEmpty()) {
				emailconfigurationBean=emailComRepo.findEventConfiguration(alertBanksBean.getEvent());
				if (emailconfigurationBean == null) {
					return "Email Event not Exists";
				}

			} else {
				return "Email Event should not be empty.";
			}


		} catch (Exception e) {
			e.printStackTrace();
			return "Failed : " + e.getMessage();
		}
		return "success";
	}

	public String quotationAlertValidation(QuotationAlertRequest quotationReq) {
		logger.info("====quotationAlert Validation utility invoked======" + quotationReq.toString());
		EmailComponentMaster emailconfigurationBean = null;
		final Pattern EMAILPATTERN = Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");

		try {

			if (!(quotationReq.getEvent().equalsIgnoreCase("QUOTE_ACCEPT")
					|| quotationReq.getEvent().equalsIgnoreCase("QUOTE_REJECTION"))) {
				return "Invalid Event";
			}

			if (!quotationReq.getEvent().trim().isEmpty()) {
				emailconfigurationBean=emailComRepo.findEventConfiguration(quotationReq.getEvent());
				if (emailconfigurationBean == null) {
					return "Email Event not Exists";
				}

			} else {
				return "Email Event should not be empty.";
			}

			if (!quotationReq.getTransactionId().trim().isEmpty() && quotationReq.getTransactionId() == null) {
				return "Transaction id should not be empty";
			}
			if (!quotationReq.getQuotationId().trim().isEmpty() && quotationReq.getQuotationId() == null) {
				return "Transaction id should not be empty";
			}
			if (!EMAILPATTERN.matcher(quotationReq.getBankEmailId()).matches()) {
				return "Email id invalid";
			}

		} catch (Exception e) {
			e.printStackTrace();
			return "Failed : " + e.getMessage();
		}
		return "success";
	}

	public String kycValidation(KycEmailRequest kycReq) {
		logger.info("====kycValidation Invoked" + kycReq.toString());
		EmailComponentMaster emailconfigurationBean = null;
		final Pattern EMAILPATTERN = Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");

		try {

			if (!(kycReq.getEvent().equalsIgnoreCase("KYC_UPLOAD") || kycReq.getEvent().equalsIgnoreCase("KYC_UPDATE")
					|| kycReq.getEvent().equalsIgnoreCase("KYC_REJECT"))) {
				return "Invalid Event";
			}
			if (!kycReq.getUserId().trim().isEmpty()) {
				boolean isUserIdExisist = userDao.isUserIdExisist(kycReq.getUserId());

				if (isUserIdExisist != true) {
					return "Invalid userId";
				}
			} else {
				return "User Id should not be empty";
			}

			if (!kycReq.getEvent().trim().isEmpty()) {
				emailconfigurationBean=emailComRepo.findEventConfiguration(kycReq.getEvent());
				if (emailconfigurationBean == null) {
					return "Email Event not Exists";
				}

			} else {
				return "Email Event should not be empty.";
			}

			if (!kycReq.getEvent().trim().isEmpty() && kycReq.getEvent() == null) {
				return "Transaction id should not be empty";
			}

		} catch (Exception e) {
			e.printStackTrace();
			return "Failed : " + e.getMessage();
		}
		return "success";
	}

	public String branchUserValidation(BranchUserRequest branchUserLink) {
		logger.info("==========branchUserValidation==========" + branchUserLink.toString());
		EmailComponentMaster emailconfigurationBean = null;
		final Pattern EMAILPATTERN = Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");

		try {
			if (!branchUserLink.getBranchId().trim().isEmpty() && branchUserLink.getBranchId() != null) {
				int id = Integer.parseInt(branchUserLink.getBranchId());
				boolean isUserIdExisist = userDao.isEntryPresent(id);

				if (isUserIdExisist != true) {
					return "branchId not present";
				}
			} else {
				return "branchId should not be empty";
			}
			if (branchUserLink.getUserId().trim().isEmpty() && branchUserLink.getUserId() == null) {
				return "User Id should not be empty";
			}

			if (!branchUserLink.getEvent().trim().isEmpty()) {
				emailconfigurationBean=emailComRepo.findEventConfiguration(branchUserLink.getEvent());
				if (emailconfigurationBean == null) {
					return "Email Event not Exists";
				}

			} else {
				return "Email Event should not be empty.";
			}

			if (!EMAILPATTERN.matcher(branchUserLink.getEmailId()).matches()) {
				return "Email id invalid";
			}

		} catch (Exception e) {
			e.printStackTrace();
			return "Failed : " + e.getMessage();
		}
		return "success";

	}

	public String passcodeValidation(BranchUserPassCodeBean passCodeBean) {
		logger.info("=======passcodeValidation utility invoked======" + passCodeBean.toString());
		try {

			if ((passCodeBean.getPasscodeValue().trim().isEmpty()) || (passCodeBean.getPasscodeValue() == null)) {
				return "Passcode value should not be empty";
			}

			if ((passCodeBean.getToken().trim().isEmpty()) || (passCodeBean.getToken() == null)) {
				return "Token should not be null";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "Failed : " + e.getMessage();
		}
		return "success";

	}

	public String Validation(NimaiEmailScheduler schdulerData) {
		logger.info(" ================ Validation utility invoked for scheduler method================" + schdulerData.toString());
		EmailComponentMaster emailconfigurationBean = new EmailComponentMaster();
		final Pattern EMAILPATTERN = Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
		//emailconfigurationBean = emailConfigurationDAOImpl.findEventConfiguration(schdulerData.getEvent());   
		logger.info(" ================ Send resetPaasword Link API is  Invoked ================"
				+ emailconfigurationBean.getEmailEventMaster().getEmailEventName());

		try {

			if (!EMAILPATTERN.matcher(schdulerData.getEmailId()).matches()) {
				return "Email id invalid";
			}
			if (!schdulerData.getEvent().trim().isEmpty()) {
				emailconfigurationBean=emailComRepo.findEventConfiguration(schdulerData.getEvent());
				if (emailconfigurationBean == null) {
					return "Email Event not Exists";
				}

			} else {
				return "Email Event should not be empty.";
			}

			if (!EMAILPATTERN.matcher(schdulerData.getEmailId()).matches()) {
				return "Email id invalid";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "Failed : " + e.getMessage();
		}
		return "success";

	}

	public String subsidiaryValidation(NimaiEmailScheduler schdulerData) {
		logger.info(" ================scheduler subsidiaryValidation utility invoked================" + schdulerData.toString());
		EmailComponentMaster emailconfigurationBean = null;
		final Pattern EMAILPATTERN = Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");

		try {
			if (!schdulerData.getUserid().trim().isEmpty()) {
				boolean isUserIdExisist = userDao.isUserIdExisist(schdulerData.getUserid());

				if (isUserIdExisist != true) {
					return "Invalid userId";
				}
			} else {
				return "User Id should not be empty";
			}

			if (!schdulerData.getEvent().trim().isEmpty()) {
				emailconfigurationBean=emailComRepo.findEventConfiguration(schdulerData.getEvent());
				if (emailconfigurationBean == null) {
					return "Email Event not Exists";
				}

			} else {
				return "Email Event should not be empty.";
			}

			if (!EMAILPATTERN.matcher(schdulerData.getEmailId()).matches()) {
				return "Email id invalid";
			}

		} catch (Exception e) {
			e.printStackTrace();
			return "Failed : " + e.getMessage();
		}
		return "success";

	}

	public String branchUserValidation(NimaiEmailScheduler schdulerData) {
		logger.info("==========branchUserValidation==========" + schdulerData.toString());
		EmailComponentMaster emailconfigurationBean = null;
		final Pattern EMAILPATTERN = Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");

		try {
		
			if (schdulerData.getUserid().trim().isEmpty() && schdulerData.getUserid() == null) {
				return "User Id should not be empty";
			}

			if (!schdulerData.getEvent().trim().isEmpty()) {
				emailconfigurationBean=emailComRepo.findEventConfiguration(schdulerData.getEvent());
				if (emailconfigurationBean == null) {
					return "Email Event not Exists";
				}

			} else {
				return "Email Event should not be empty.";
			}

			if (!EMAILPATTERN.matcher(schdulerData.getEmailId()).matches()) {
				return "Email id invalid";
			}

		} catch (Exception e) {
			e.printStackTrace();
			return "Failed : " + e.getMessage();
		}
		return "success";

	}

	public String bankEmailValidation(NimaiEmailSchedulerAlertToBanks schdulerData, String banksEmailID) {
		logger.info(
				" ================ banksEmailValidation utility invoked================" + schdulerData.toString(),
				banksEmailID);
		EmailComponentMaster emailconfigurationBean = null;
		final Pattern EMAILPATTERN = Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");

		try {

			if (!((schdulerData.getEmailEvent().equalsIgnoreCase("LC_UPLOAD_ALERT_ToBanks")
					|| schdulerData.getEmailEvent().equalsIgnoreCase("LC_REJECT_ALERT_ToBanks")
					|| schdulerData.getEmailEvent().equalsIgnoreCase("LC_UPLOAD(DATA)")
					|| schdulerData.getEmailEvent().equalsIgnoreCase("LC_UPDATE(DATA)")
					|| schdulerData.getEmailEvent().equalsIgnoreCase("LC_UPDATE_ALERT_ToBanks")
					|| schdulerData.getEmailEvent().equalsIgnoreCase("LC_Reopening_ALERT_ToBanks")
					|| schdulerData.getEmailEvent().equalsIgnoreCase("QUOTE_ACCEPT")
					||schdulerData.getEmailEvent().equalsIgnoreCase("BId_ALERT_ToCustomer")
					||schdulerData.getEmailEvent().equalsIgnoreCase("Bank_Details_tocustomer")
					|| schdulerData.getEmailEvent().equalsIgnoreCase("QUOTE_REJECTION"))
					|| schdulerData.getEmailEvent().equalsIgnoreCase("QUOTE_PLACE_ALERT_ToBanks"))) {
				return "Invalid Event";
			}

			if (!schdulerData.getEmailEvent().trim().isEmpty()) {
				emailconfigurationBean = emailConfigurationDAOImpl.findEventConfiguration(schdulerData.getEmailEvent());
				if (emailconfigurationBean == null) {
					return "Email Event not Exists";
				}

			} else {
				return "Email Event should not be empty.";
			}


		} catch (Exception e) {
			e.printStackTrace();
			return "Failed : " + e.getMessage();
		}
		return "success";

	}

	public String buPassCodeValidator(BranchUserRequest branchUserLink) {
		logger.info("==========branchUserValidation==========" + branchUserLink.toString());
	
		try {

			if (branchUserLink.getUserId().trim().isEmpty() && branchUserLink.getUserId() == null) {
				return "User Id should not be empty";
			}

			if (!branchUserLink.getEvent().trim().isEmpty() && branchUserLink.getUserId() == null) {
				return "Email Event should not be empty.";
				}

			

			

		} catch (Exception e) {
			e.printStackTrace();
			return "Failed : " + e.getMessage();
		}
		return "success";

	}

	public String reSendValidation(SubsidiaryBean registerLink) {
		// TODO Auto-generated method stub
		logger.info(
				" ================ reSendValidation utility invoked================" + registerLink.toString());
		EmailComponentMaster emailconfigurationBean = null;
		final Pattern EMAILPATTERN = Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");

		try {
			if (!registerLink.getUserId().trim().isEmpty()) {
				boolean isUserIdExisist = userDao.isUserIdExisist(registerLink.getUserId());

				if (isUserIdExisist != true) {
					return "Invalid userId";
				}
			} else {
				return "User Id should not be empty";
			}

			if (!registerLink.getEvent().trim().isEmpty()) {
				emailconfigurationBean = emailConfigurationDAOImpl.findEventConfiguration(registerLink.getEvent());
				if (emailconfigurationBean == null) {
					return "Email Event not Exists";
				}

			} else {
				return "Email Event should not be empty.";
			}

			

		} catch (Exception e) {
			e.printStackTrace();
			return "Failed : " + e.getMessage();
		}
		return "success";

}

}