package com.nimai.email.service;

import java.io.ByteArrayInputStream;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.persistence.EntityManagerFactory;

import org.springframework.transaction.annotation.Propagation;
//import javax.transaction.Transactional;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.nimai.email.api.GenericResponse;
import com.nimai.email.bean.AdminBean;
import com.nimai.email.bean.BankDetailsBean;
import com.nimai.email.bean.BranchUserPassCodeBean;
import com.nimai.email.bean.BranchUserRequest;
import com.nimai.email.bean.EodReport;
import com.nimai.email.bean.KycEmailRequest;
import com.nimai.email.bean.LcUploadBean;
import com.nimai.email.bean.ResetPassBean;
import com.nimai.email.bean.SubsidiaryBean;
import com.nimai.email.bean.UserIdentificationBean;
import com.nimai.email.bean.UserRegistrationBean;
import com.nimai.email.dao.EmailConfigurationdaoImpl;
import com.nimai.email.dao.EmailProcessImpl;
import com.nimai.email.dao.UserServiceDao;
import com.nimai.email.entity.EmailComponentMaster;
import com.nimai.email.entity.NimaiClient;
import com.nimai.email.entity.NimaiEmailScheduler;
import com.nimai.email.entity.NimaiFSubsidiaries;
import com.nimai.email.entity.NimaiLC;
import com.nimai.email.entity.NimaiMBranch;
import com.nimai.email.entity.NimaiMEmployee;
import com.nimai.email.entity.NimaiMLogin;
import com.nimai.email.entity.NimaiMRefer;
import com.nimai.email.entity.NimaiSubscriptionDetails;
import com.nimai.email.entity.NimaiSubscriptionVas;
import com.nimai.email.entity.NimaiSystemConfig;
import com.nimai.email.entity.NimaiToken;
import com.nimai.email.entity.OnlinePayment;
import com.nimai.email.repository.NimaiEmailSchedulerRepo;
import com.nimai.email.repository.NimaiMBranchRepository;
import com.nimai.email.repository.NimaiMCustomerRepo;
import com.nimai.email.repository.NimaiTokenRepository;
import com.nimai.email.repository.OnlinePaymentRepository;
import com.nimai.email.repository.SubscriptionDetailsRepository;
import com.nimai.email.repository.nimaiSystemConfigRepository;
import com.nimai.email.utility.AppConstants;
import com.nimai.email.utility.EmaiInsert;
import com.nimai.email.utility.EmailErrorCode;
import com.nimai.email.utility.EmailSend;
import com.nimai.email.utility.ErrorDescription;
import com.nimai.email.utility.InvoiceTemplate;
import com.nimai.email.utility.ResetUserValidation;
import com.nimai.email.utility.Utils;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	EmailConfigurationdaoImpl emailConfigurationDAO;

	@Autowired
	OnlinePaymentRepository payRepo;

	@Autowired
	UserServiceDao userDao;

	@Autowired
	EntityManagerFactory em;

	@Autowired
	private EmaiInsert emailInsert;

	@Autowired
	EmailProcessImpl emailProcessorImpl;

	@Autowired
	EmailSend emailSend;

	@Autowired
	private Utils utility;

	@Autowired
	EmailConfigurationdaoImpl emailConfigurationDAOImpl;

	@Autowired
	NimaiEmailSchedulerRepo schRepo;

	@Autowired
	SubscriptionDetailsRepository sPlanRepo;

	@Autowired
	nimaiSystemConfigRepository systemConfig;

	@Autowired
	GenericResponse response;

	@Autowired
	ResetUserValidation resetUserValidator;

	@Value("${accountActivation.url}")
	private String accountActivationLink;

	@Value("${forgotPassword.url}")
	private String forgorPasswordLink;

	@Value("${subsidiaryLink.url}")
	private String subAccountActivationLink;

	@Value("${branchUserLink.url}")
	private String branchUserActivationLink;

	@Value("${referUserLink.url}")
	private String referAccountActivationLink;

	@Value("${adminForgotPassLink.url}")
	private String adminForgotPassLink;

	@Autowired
	NimaiMBranchRepository brRepo;

	@Autowired
	NimaiMCustomerRepo custRepo;

	@Autowired
	NimaiTokenRepository tokenRepo;

	@Override
	public boolean checkUserId(String userId) {
		return false;
	}

	@Override
	public boolean checkEmailId(String emailAddress) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ResponseEntity<Object> sendEmail(UserRegistrationBean userRegistratinBean) throws Exception {
		GenericResponse response = new GenericResponse();
		NimaiMLogin nimaiLogin = null;
		logger.info("=======Inside sendEmail method=========" + userRegistratinBean.toString());

		String errorString = this.resetUserValidator.Validation(userRegistratinBean);
		if (errorString.equalsIgnoreCase("success")) {
			try {

				NimaiClient clientUseId = userDao.getcliDetailsByEmailId(userRegistratinBean.getEmail());
				nimaiLogin = userDao.getCustomerDetailsByUserID(clientUseId.getUserid());

				if (nimaiLogin != null && clientUseId != null) {

					// NimaiClient userId = nimaiLogin.getUserid();
					// logger.info(" ========Inside UserServiceImpl==============" + userId);
					Date currentDate = new Date();
					String forgotTokenKey = "";
					String tokenKey = utility.generatePasswordResetToken();
					if (userRegistratinBean.getEvent().equalsIgnoreCase(AppConstants.FORGOT_PASS_EVENT)) {
						Date ForgottokenExpiry = utility.getForgotPassExpiryLink();
						forgotTokenKey = ("FO_").concat(utility.generatePasswordResetToken());
						nimaiLogin.setToken(forgotTokenKey);
						nimaiLogin.setTokenExpiryDate(ForgottokenExpiry);
					} else {
						String email = systemConfig.findByLinkDays();
						Date tokenExpiry = utility.getLinkExpiry(email);
						nimaiLogin.setTokenExpiryDate(tokenExpiry);
						nimaiLogin.setToken(tokenKey);
					}
					nimaiLogin.setInsertedDate(currentDate);
					userDao.update(nimaiLogin);

					try {
						String parentEmailId = "";
						if (clientUseId.getAccountType().equalsIgnoreCase("BANKUSER")) {
							NimaiClient parentClient = userDao.getClientDetailsbyUserId(clientUseId.getAccountSource());
							parentEmailId = parentClient.getEmailAddress();
						}
						String aCLlink = accountActivationLink + tokenKey;
						String fPlink = forgorPasswordLink + forgotTokenKey;
						NimaiClient nimaiClientdetails = userDao.getClientDetailsbyUserId(clientUseId.getUserid());
						if (userRegistratinBean.getEvent().equalsIgnoreCase(AppConstants.ACCOUNT_ACTIVATE_EVENT)) {

							emailInsert.resetPasswordEmail(aCLlink, userRegistratinBean, nimaiLogin, nimaiClientdetails,
									parentEmailId);

						} else if (userRegistratinBean.getEvent().equalsIgnoreCase(AppConstants.FORGOT_PASS_EVENT)) {
							emailInsert.resetForgorPasswordEmail(fPlink, userRegistratinBean,
									userRegistratinBean.getEmail(), clientUseId);
						}
						response.setErrCode("ASA002");
						response.setMessage(ErrorDescription.getDescription("ASA002"));
						return new ResponseEntity<Object>(response, HttpStatus.OK);

					} catch (Exception e) {
						e.printStackTrace();
						if (e instanceof NullPointerException) {
							response.setMessage("No email address provided for User");
							EmailErrorCode emailError = new EmailErrorCode("EmailNull", 409);
							response.setData(emailError);
							return new ResponseEntity<Object>(response, HttpStatus.CONFLICT);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			response.setErrCode("EXE000");
			response.setMessage(ErrorDescription.getDescription("EXE000") + errorString.toString());
			return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
		}
		response.setErrCode("ASA005");
		response.setMessage(ErrorDescription.getDescription("ASA005"));
		return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
	}

	@Override
	// @Scheduled(fixedDelay = 30000L)
	// @Transactional(propagation = Propagation.NESTED)
	public void sendAccountEmail() throws Exception {

		String emailStatus = "";
		List<NimaiEmailScheduler> emailDetailsScheduled = userDao.getSchedulerDetails();
		for (NimaiEmailScheduler schdulerData : emailDetailsScheduled) {

			if (schdulerData.getEvent().equalsIgnoreCase(AppConstants.CUST_SPLAN_EVENT)) {
				try {
					NimaiClient clientUseId = userDao.getClientDetailsbyUserId(schdulerData.getUserid());

					logger.info("============Inside Cust_Splan_email condition==========" + schdulerData.getUserid());

					if (clientUseId != null) {

						NimaiSubscriptionDetails subDetails = userDao.getSplanDetails(schdulerData.getSubscriptionId(),
								schdulerData.getUserid());

						logger.info("===========CUST_SPLAN_EVENT:" + subDetails.getSubscriptionId());
						logger.info("===========CUST_SPLAN_EVENT:" + subDetails.toString());
						if (subDetails.getPaymentMode().equalsIgnoreCase("Credit")) {
							String status = "Success";
							String aStatus = "Approved";
							OnlinePayment paymentDetails = payRepo.findByuserId(schdulerData.getUserid(), status,
									aStatus);
							if (paymentDetails == null) {
								String planFailureName = "SPLAN_FAILURE";
								emailInsert.sendPaymentFailureEmail(schdulerData, clientUseId, subDetails,
										planFailureName);
							} else {
								NimaiSystemConfig configDetails = null;
								configDetails = systemConfig.findBySystemId(1);
								NimaiSystemConfig configDetail = null;
								try {
									configDetail = systemConfig.getOne(14);
									System.out
											.println("configDetail image value" + configDetail.getSystemEntityValue());
									emailInsert.sendCustSPlanEmail(schdulerData, clientUseId, subDetails,
											paymentDetails, configDetails, configDetail.getSystemEntityValue());
								} catch (Exception e) {
									e.printStackTrace();
								}

							}

						}

						try {
							userDao.updateEmailStatus(schdulerData.getAccountSchedulerId());
							logger.info("===========CUST_SPLAN_EVENT  updated id is:"
									+ schdulerData.getAccountSchedulerId());
						} catch (Exception e) {
							e.printStackTrace();
							logger.info("===========CUST_SPLAN_EVENT:" + e.toString());
							logger.info("===========CUST_SPLAN_EVENT not updated id is:"
									+ schdulerData.getAccountSchedulerId());
						}

					} else {
						logger.info("Inside Cust_Splan_email condition user id not found");
						emailStatus = AppConstants.CU_INVALID_FLAG;
						userDao.updateInvalidIdEmailFlag(schdulerData.getAccountSchedulerId(), emailStatus);
						response.setMessage("Details not found");
					}

				} catch (Exception e) {
					if (e instanceof NullPointerException) {

						EmailErrorCode emailError = new EmailErrorCode("EmailNull", 409);

						logger.info("Customer Splan catch block" + emailError);

					}

				}

			} else if (schdulerData.getEvent().equalsIgnoreCase(AppConstants.CUST_SPLAN_EVENT_Wire)) {
				try {
					NimaiClient clientUseId = userDao.getClientDetailsbyUserId(schdulerData.getUserid());

					logger.info("============Inside Cust_Splan_email condition==========" + schdulerData.getUserid());

					if (clientUseId != null && clientUseId.getPaymentStatus().equalsIgnoreCase("Approved")) {

						NimaiSubscriptionDetails subDetails = userDao.getSplanDetails(schdulerData.getSubscriptionId(),
								schdulerData.getUserid());

						logger.info("===========CUST_SPLAN_EVENT:" + subDetails.getSubscriptionId());
						logger.info("===========CUST_SPLAN_EVENT:" + subDetails.toString());
						OnlinePayment paymentDetails = new OnlinePayment();
						NimaiSystemConfig configDetails = null;
						configDetails = systemConfig.findBySystemId(1);
						NimaiSystemConfig configDetail = null;
						configDetail = systemConfig.getOne(14);
						emailInsert.sendCustSPlanEmail(schdulerData, clientUseId, subDetails, paymentDetails,
								configDetails, configDetail.getSystemEntityValue());

						try {
							userDao.updateEmailStatus(schdulerData.getAccountSchedulerId());
							logger.info("===========CUST_SPLAN_EVENT  updated id is:"
									+ schdulerData.getAccountSchedulerId());
						} catch (Exception e) {
							e.printStackTrace();
							logger.info("===========CUST_SPLAN_EVENT:" + e.toString());
							logger.info("===========CUST_SPLAN_EVENT not updated id is:"
									+ schdulerData.getAccountSchedulerId());
						}

					} else {
						logger.info("Inside Cust_Splan_email condition user id not found");
						emailStatus = AppConstants.CU_INVALID_FLAG;
						userDao.updateInvalidIdEmailFlag(schdulerData.getAccountSchedulerId(), emailStatus);
						response.setMessage("Details not found");
					}

				} catch (Exception e) {
					if (e instanceof NullPointerException) {

						EmailErrorCode emailError = new EmailErrorCode("EmailNull", 409);

						logger.info("Custpmer Splan catch block" + emailError);

					}

				}

			} else if (schdulerData.getEvent().equalsIgnoreCase(AppConstants.CUST_SPLAN_Rejected)) {
				try {
					NimaiClient clientUseId = userDao.getClientDetailsbyUserId(schdulerData.getUserid());

					logger.info("============Inside Cust_Splan_email condition==========" + schdulerData.getUserid());

					if (clientUseId != null) {

						NimaiSubscriptionDetails subDetails = userDao.getSplanDetails(schdulerData.getSubscriptionId(),
								schdulerData.getUserid());

						logger.info("===========CUST_SPLAN_EVENT:" + subDetails.getSubscriptionId());
						logger.info("===========CUST_SPLAN_EVENT:" + subDetails.toString());
						OnlinePayment paymentDetails = new OnlinePayment();
						NimaiSystemConfig configDetails = null;
						configDetails = systemConfig.findBySystemId(1);
						NimaiSystemConfig configDetail = null;
						configDetail = systemConfig.getOne(14);
						emailInsert.sendCustSPlanEmail(schdulerData, clientUseId, subDetails, paymentDetails,
								configDetails, configDetail.getSystemEntityValue());

						try {
							userDao.updateEmailStatus(schdulerData.getAccountSchedulerId());
							logger.info("===========CUST_SPLAN_EVENT  updated id is:"
									+ schdulerData.getAccountSchedulerId());
						} catch (Exception e) {
							e.printStackTrace();
							logger.info("===========CUST_SPLAN_EVENT:" + e.toString());
							logger.info("===========CUST_SPLAN_EVENT not updated id is:"
									+ schdulerData.getAccountSchedulerId());
						}

					} else {
						logger.info("Inside Cust_Splan_email condition user id not found");
						emailStatus = AppConstants.CU_INVALID_FLAG;
						userDao.updateInvalidIdEmailFlag(schdulerData.getAccountSchedulerId(), emailStatus);
						response.setMessage("Details not found");
					}

				} catch (Exception e) {
					if (e instanceof NullPointerException) {

						EmailErrorCode emailError = new EmailErrorCode("EmailNull", 409);

						logger.info("Custpmer Splan catch block" + emailError);

					}

				}

			} else if (schdulerData.getEvent().equalsIgnoreCase(AppConstants.ADD_REFER_TO_PARENT)) {
				try {
					if (schdulerData.getUserName() != null && schdulerData.getEmailId() != null) {
						emailInsert.sendReferEmailToParentUser(schdulerData);
						try {
							userDao.updateEmailStatus(schdulerData.getAccountSchedulerId());
						} catch (Exception e) {
							e.printStackTrace();
							continue;
						}
					} else {
						logger.info("Inside ADD_REFER_TO_PARENT condition user id not found");
						emailStatus = AppConstants.Invalid_ParentID;
						userDao.updateInvalidIdEmailFlag(schdulerData.getAccountSchedulerId(), emailStatus);

					}

				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}

			} else if (schdulerData.getEvent().equalsIgnoreCase(AppConstants.VAS_ADDED_EVENT)) {
				try {
					// NimaiClient clientUseId =
					// userDao.getcliDetailsByEmailId(schdulerData.getEmailId());
					NimaiClient clientUseId = userDao.getClientDetailsbyUserId(schdulerData.getUserid());
					logger.info("============Inside VAS_ADDED condition==========");
					logger.info("====================================UserId Id:-" + schdulerData.getUserid()
							+ "====================================");
					if (clientUseId != null) {
						NimaiSubscriptionVas vasDetails = userDao.getVasDetails(schdulerData.getSubscriptionName(),
								clientUseId.getUserid());
						logger.info("====================vasdetails" + vasDetails.getVasId());
						NimaiSubscriptionDetails subDetails = userDao.getSplanDetails(vasDetails.getSubscriptionId(),
								schdulerData.getUserid());

						logger.info("==========================subdetails" + subDetails.getsPlSerialNUmber());
						if (!vasDetails.getMode().equalsIgnoreCase("Wire")) {
							String status = "Success";
							String aStatus = "Approved";
							try {
								OnlinePayment paymentDetails = payRepo.findByuserId(schdulerData.getUserid(), status,
										aStatus);

								if (paymentDetails == null) {
									logger.info("==================null paymentdetails");
									logger.info("=============Inside paymet failure mode===============");
									String planFailureName = "VAS_FAILURE";
									emailInsert.sendPaymentFailureEmail(schdulerData, clientUseId, subDetails,
											planFailureName);
								} else {
									logger.info("================" + paymentDetails.toString());
									logger.info("==================paymentdetails" + paymentDetails.getId());
									logger.info("==============Inside paymet success==============");
									NimaiSystemConfig configDetails = null;
									configDetails = systemConfig.findBySystemId(1);
									NimaiSystemConfig configDetail = null;
									configDetail = systemConfig.getOne(14);
									emailInsert.sendVasEmail(schdulerData, clientUseId, vasDetails, subDetails,
											paymentDetails, configDetails, configDetail.getSystemEntityValue());
								}
								System.out.println("payment dettails outside the codition" + paymentDetails.toString());
								userDao.updateEmailStatus(schdulerData.getAccountSchedulerId());
							} catch (Exception e) {
								e.printStackTrace();
							}
						}

					} else {
						logger.info("Inside Cust_Splan_email condition user id not found");
						emailStatus = AppConstants.CU_INVALID_FLAG;
						userDao.updateInvalidIdEmailFlag(schdulerData.getAccountSchedulerId(), emailStatus);
						response.setMessage("Details not found");

					}

				} catch (Exception e) {
					if (e instanceof NullPointerException) {

						EmailErrorCode emailError = new EmailErrorCode("EmailNull", 409);
						logger.info("Vas added catch block" + emailError);
						continue;
					}
				}
			} else if (schdulerData.getEvent().equalsIgnoreCase("VAS_PLAN_WIRE_APPROVED")) {
				try {
					// NimaiClient clientUseId =
					// userDao.getcliDetailsByEmailId(schdulerData.getEmailId());
					NimaiClient clientUseId = userDao.getClientDetailsbyUserId(schdulerData.getUserid());
					logger.info("============Inside VAS_ADDED condition==========");
					logger.info("====================================UserId Id:-" + schdulerData.getUserid()
							+ "====================================");
					if (clientUseId != null) {
						NimaiSubscriptionVas vasDetails = userDao.getVasDetails(schdulerData.getSubscriptionName(),
								clientUseId.getUserid());
						logger.info("====================vasdetails" + vasDetails.getVasId());
						NimaiSubscriptionDetails subDetails = userDao.getSplanDetails(vasDetails.getSubscriptionId(),
								schdulerData.getUserid());

						logger.info("==========================subdetails" + subDetails.getsPlSerialNUmber());
						String status = "Success";
						String aStatus = "Approved";
						try {
							OnlinePayment paymentDetails = new OnlinePayment();

							logger.info("================" + paymentDetails.toString());
							logger.info("==================paymentdetails" + paymentDetails.getId());
							logger.info("==============Inside paymet success==============");
							NimaiSystemConfig configDetails = null;
							configDetails = systemConfig.findBySystemId(1);
							NimaiSystemConfig configDetail = null;
							configDetail = systemConfig.getOne(14);
							emailInsert.sendVasEmail(schdulerData, clientUseId, vasDetails, subDetails, paymentDetails,
									configDetails, configDetail.getSystemEntityValue());
							System.out.println("payment details outside the codition" + paymentDetails.toString());

							userDao.updateEmailStatus(schdulerData.getAccountSchedulerId());
						} catch (Exception e) {
							e.printStackTrace();
						}

					} else {
						logger.info("Inside Cust_Splan_email condition user id not found");
						emailStatus = AppConstants.CU_INVALID_FLAG;
						userDao.updateInvalidIdEmailFlag(schdulerData.getAccountSchedulerId(), emailStatus);
						response.setMessage("Details not found");

					}

				} catch (Exception e) {
					if (e instanceof NullPointerException) {

						EmailErrorCode emailError = new EmailErrorCode("EmailNull", 409);
						logger.info("Vas added catch block" + emailError);
						continue;
					}
				}
			} else if (schdulerData.getEvent().equalsIgnoreCase(AppConstants.VAS_PLAN_WIRE_REJECTED)) {
				try {

					emailInsert.sendVasRejectedEmail(schdulerData);

					userDao.updateEmailStatus(schdulerData.getAccountSchedulerId());

				} catch (Exception e) {
					if (e instanceof NullPointerException) {

						EmailErrorCode emailError = new EmailErrorCode("EmailNull", 409);
						logger.info("Vas added catch block" + emailError);
						continue;
					}
				}

			}

			else if (schdulerData.getEvent().equalsIgnoreCase(AppConstants.SUBSIDIARY_ADDED_EVENT)) {

				try {
					NimaiClient clientUseId = userDao.getClientDetailsbyUserId(schdulerData.getUserid());
					NimaiClient subDetails = userDao.getClientDetailsbyUserId(schdulerData.getSubUserId());
					logger.info("============Inside SUBSIDIARY_ADDEDED condition==========");
					logger.info("====================================SUBSIDIARY_ADDEDED:-" + schdulerData.getUserid()
							+ "====================================");
					if (clientUseId != null && subDetails != null) {
						emailInsert.sendSubEmailToParentUser(schdulerData, clientUseId, clientUseId.getFirstName(),
								subDetails);

						userDao.updateEmailStatus(schdulerData.getAccountSchedulerId());

					} else {
						logger.info("Inside SUBSIDIARY_ADDEDED condition user id not found");
						emailStatus = AppConstants.INVALID_CU_SUBSIDIARY_ADDED;
						userDao.updateInvalidIdEmailFlag(schdulerData.getAccountSchedulerId(), emailStatus);

					}

				} catch (Exception e) {
					if (e instanceof NullPointerException) {
						response.setMessage("Email Sending failed");
						EmailErrorCode emailError = new EmailErrorCode("EmailNull", 409);

						logger.info("Subsidiary added catch block" + emailError);
						continue;

					}

				}
			} else if (schdulerData.getEvent().equalsIgnoreCase(AppConstants.SUBSIDIARY_ACTIVATION_EVENT)) {

				try {
					NimaiClient clientUseId = userDao.getClientDetailsbyUserId(schdulerData.getSubUserId());
					NimaiClient client = userDao.getClientDetailsbyUserId(schdulerData.getUserid());
					logger.info("============Inside SUBSIDIARY_ACTIVATION_ALERT condition==========");
					System.out.println("====================================SUBSIDIARY_ACTIVATION_ALERT:-"
							+ schdulerData.getUserid() + "====================================");
					if (clientUseId != null && client != null) {
						emailInsert.sendSubEmailToParentUser(schdulerData, client, client.getFirstName(), clientUseId);

						userDao.updateEmailStatus(schdulerData.getAccountSchedulerId());

					} else {
						logger.info("Inside SUBSIDIARY_ACTIVATION_ALERT condition user id not found");
						emailStatus = AppConstants.INVALID_CU_SUBSIDIARY_ACTIVATION_ALERT;
						userDao.updateInvalidIdEmailFlag(schdulerData.getAccountSchedulerId(), emailStatus);

					}

				} catch (Exception e) {
					if (e instanceof NullPointerException) {
						response.setMessage("Email Sending failed");
						EmailErrorCode emailError = new EmailErrorCode("EmailNull", 409);
						logger.info("Subsidiary activation catch block" + emailError);
						continue;

					}

				}

			} else if (schdulerData.getEvent().equalsIgnoreCase(AppConstants.ASSIGN_NOTI_TORM)) {

				logger.info("============Inside ASSIGN_NOTIFICATION_TO_RM condition==========");
				logger.info("====================================ASSIGN_NOTIFICATION_TO_RM:-" + schdulerData.toString()
						+ "====================================");
				try {
					if (schdulerData.getUserid().substring(0, 2).equalsIgnoreCase("CU")) {
						emailInsert.sendEmailToRm(schdulerData);
					}
					userDao.updateEmailStatus(schdulerData.getAccountSchedulerId());

				} catch (Exception e) {
					if (e instanceof NullPointerException) {
						response.setMessage("Email Sending failed");
						EmailErrorCode emailError = new EmailErrorCode("EmailNull", 409);
						logger.info("ASSIGN_NOTI_TORM catch block" + emailError);
						continue;

					}

				}
			} else if (schdulerData.getEvent().equalsIgnoreCase(AppConstants.ASSIGN_NOTIFICATION_TO_RM_RE)) {
				logger.info("============Inside ASSIGN_NOTIFICATION_TO_RM_RE condition==========");
				logger.info("====================================ASSIGN_NOTIFICATION_TO_RM_RE:-"
						+ schdulerData.toString() + "====================================");
				try {
					emailInsert.sendEmailToRmRE(schdulerData);
					userDao.updateEmailStatus(schdulerData.getAccountSchedulerId());
				} catch (Exception e) {
					if (e instanceof NullPointerException) {
						response.setMessage("Email Sending failed");
						EmailErrorCode emailError = new EmailErrorCode("EmailNull", 409);
						logger.info("ASSIGN_NOTIFICATION_TO_RM_RE catch block" + emailError);

					}
				}

			} else if (schdulerData.getEvent().equalsIgnoreCase(AppConstants.ASSIGN_NOTIFICATION_TO_RM_BC)) {
				logger.info("============Inside ASSIGN_NOTIFICATION_TO_RM_BC condition==========");
				logger.info("====================================ASSIGN_NOTIFICATION_TO_RM_BC:-"
						+ schdulerData.toString() + "====================================");
				try {
					emailInsert.sendEmailToRmBC(schdulerData);
					userDao.updateEmailStatus(schdulerData.getAccountSchedulerId());
				} catch (Exception e) {
					if (e instanceof NullPointerException) {
						response.setMessage("Email Sending failed");
						EmailErrorCode emailError = new EmailErrorCode("EmailNull", 409);
						logger.info("ASSIGN_NOTIFICATION_TO_RM_BC catch block" + emailError);
						continue;
					}
				}

			} else if (schdulerData.getEvent().equalsIgnoreCase(AppConstants.ASSIGN_NOTIFICATION_TO_RM_BA)) {

				logger.info("============Inside ASSIGN_NOTIFICATION_TO_RM_BA condition==========");
				logger.info("====================================ASSIGN_NOTIFICATION_TO_RM_BA:-"
						+ schdulerData.toString() + "====================================");
				try {
					emailInsert.sendEmailToRmBA(schdulerData);
					userDao.updateEmailStatus(schdulerData.getAccountSchedulerId());
				} catch (Exception e) {
					if (e instanceof NullPointerException) {
						logger.info("============Inside ASSIGN_NOTIFICATION_TO_RM_BA condition==========");
						logger.info("====================================ASSIGN_NOTIFICATION_TO_RM_BA:-"
								+ schdulerData.toString() + "====================================");
						EmailErrorCode emailError = new EmailErrorCode("EmailNull", 409);
						logger.info("ASSIGN_NOTIFICATION_TO_RM_BA catch block" + emailError);

					}
				}

			} else if (schdulerData.getEvent().equalsIgnoreCase(AppConstants.CU_SPLAN_NOTIFICATON_TORM)) {
				logger.info("============Inside CU_SPLAN_NOTIFICATON_TORM condition==========");
				logger.info("========================CU_SPLAN_NOTIFICATON_TORM:-" + schdulerData.toString()
						+ "========================");
				try {
					emailInsert.sendCuSplanEmailToRm(schdulerData);
					userDao.updateEmailStatus(schdulerData.getAccountSchedulerId());
				} catch (Exception e) {
					if (e instanceof NullPointerException) {
						logger.info("============Inside CU_SPLAN_NOTIFICATON_TORM condition==========");
						logger.info("========================CU_SPLAN_NOTIFICATON_TORM:-" + schdulerData.toString()
								+ "========================");
						EmailErrorCode emailError = new EmailErrorCode("EmailNull", 409);
						logger.info("CU_SPLAN_NOTIFICATON_TORM catch block" + emailError);

					}
				}

			} else if (schdulerData.getEvent().equalsIgnoreCase(AppConstants.BC_SPLAN_NOTIFICATON_TORM)) {
				logger.info("============Inside BC_SPLAN_NOTIFICATON_TORM condition==========");
				logger.info("====================================BC_SPLAN_NOTIFICATON_TORM:-" + schdulerData.toString()
						+ "====================================");
				try {
					emailInsert.sendBCSplanEmailToRm(schdulerData);
					userDao.updateEmailStatus(schdulerData.getAccountSchedulerId());
				} catch (Exception e) {
					if (e instanceof NullPointerException) {
						logger.info("============Inside BC_SPLAN_NOTIFICATON_TORM condition==========");
						logger.info("========================#BC_SPLAN_NOTIFICATON_TORM:-" + schdulerData.toString()
								+ "========================");

						EmailErrorCode emailError = new EmailErrorCode("EmailNull", 409);
						logger.info("BC_SPLAN_NOTIFICATON_TORM catch block" + emailError);

					}
				}

			} else if (schdulerData.getEvent().equalsIgnoreCase(AppConstants.BAU_SPLAN_NOTIFICATON_TORM)) {
				logger.info("============Inside BAU_SPLAN_NOTIFICATON_TORM condition==========");
				logger.info("========================BAU_SPLAN_NOTIFICATON_TORM:-" + schdulerData.toString()
						+ "========================");
				try {
					emailInsert.sendBAUSplanEmailToRm(schdulerData);
					userDao.updateEmailStatus(schdulerData.getAccountSchedulerId());
				} catch (Exception e) {
					if (e instanceof NullPointerException) {
						logger.info("============Inside BAU_SPLAN_NOTIFICATON_TORM condition==========");
						logger.info("========================BAU_SPLAN_NOTIFICATON_TORM:-" + schdulerData.toString()
								+ "========================");
						EmailErrorCode emailError = new EmailErrorCode("EmailNull", 409);
						logger.info("BAU_SPLAN_NOTIFICATON_TORM catch block" + emailError);

					}
				}

			} else if (schdulerData.getEvent().equalsIgnoreCase(AppConstants.FIXED_COUPON_ALERT)) {
				logger.info("============Inside FIXED_COUPON_CODE_CREATED_ALERT condition==========");
				logger.info("====================================FIXED_COUPON_CODE_CREATED_ALERT:-"
						+ schdulerData.toString() + "====================================");
				try {
					emailInsert.sendFixedCDAlertEmailToRm(schdulerData);
					userDao.updateEmailStatus(schdulerData.getAccountSchedulerId());
				} catch (Exception e) {
					logger.info("============Inside FIXED_COUPON_CODE_CREATED_ALERT condition==========");
					logger.info("====================================FIXED_COUPON_CODE_CREATED_ALERT:-"
							+ schdulerData.toString() + "====================================");
					if (e instanceof NullPointerException) {
						EmailErrorCode emailError = new EmailErrorCode("EmailNull", 409);
						logger.info("FIXED_COUPON_ALERT catch block" + emailError);

					}
				}

			} else if (schdulerData.getEvent().equalsIgnoreCase(AppConstants.PERCENT_COUPON_ALERT)) {
				logger.info("============Inside Percent_COUPON_CODE_CREATED_ALERT condition==========");
				logger.info("====================================Percent_COUPON_CODE_CREATED_ALERT:-"
						+ schdulerData.toString() + "====================================");
				try {
					emailInsert.sendPercentCDAlertEmailToRm(schdulerData);
					userDao.updateEmailStatus(schdulerData.getAccountSchedulerId());
				} catch (Exception e) {
					logger.info("============Inside Percent_COUPON_CODE_CREATED_ALERT condition==========");
					System.out.println("====================================Percent_COUPON_CODE_CREATED_ALERT:-"
							+ schdulerData.toString() + "====================================");
					if (e instanceof NullPointerException) {
						EmailErrorCode emailError = new EmailErrorCode("EmailNull", 409);
						logger.info("PERCENT_COUPON_ALERT catch block" + emailError);

					}
					continue;
				}

			} else if (schdulerData.getEvent().equalsIgnoreCase(AppConstants.CU_VAS_TO_RM)) {
				logger.info("============Inside CU_VAS_NOTIFICATION_TORM condition==========");
				logger.info("====================================CU_VAS_NOTIFICATION_TORM:-" + schdulerData.toString()
						+ "====================================");
				try {
					emailInsert.sendCUVASAlertEmailToRm(schdulerData);
					userDao.updateEmailStatus(schdulerData.getAccountSchedulerId());
				} catch (Exception e) {
					logger.info("============Inside CU_VAS_NOTIFICATION_TORM condition==========");
					logger.info("====================================CU_VAS_NOTIFICATION_TORM:-"
							+ schdulerData.toString() + "====================================");
					if (e instanceof NullPointerException) {

						EmailErrorCode emailError = new EmailErrorCode("EmailNull", 409);
						logger.info("CU_VAS_TO_RM catch block" + emailError);

					}
				}

			} else if (schdulerData.getEvent().equalsIgnoreCase(AppConstants.BC_VAS_TO_RM)) {
				logger.info("============Inside BC_VAS_NOTIFICATION_TORM condition==========");
				logger.info("====================================BC_VAS_NOTIFICATION_TORM:-" + schdulerData.toString()
						+ "====================================");
				try {
					emailInsert.sendBCVASAlertEmailToRm(schdulerData);
					userDao.updateEmailStatus(schdulerData.getAccountSchedulerId());
				} catch (Exception e) {
					logger.info("============Inside BC_VAS_NOTIFICATION_TORM condition==========");
					logger.info("====================================BC_VAS_NOTIFICATION_TORM:-"
							+ schdulerData.toString() + "====================================");
					if (e instanceof NullPointerException) {

						EmailErrorCode emailError = new EmailErrorCode("EmailNull", 409);
						logger.info("BC_VAS_NOTIFICATION_TORM condition" + emailError);

					}
					continue;
				}

			} else if (schdulerData.getEvent().equalsIgnoreCase(AppConstants.BAU_VAS_TO_RM)) {
				logger.info("============Inside BAU_VAS_NOTIFICATION_TORM condition==========");
				logger.info("====================================BAU_VAS_NOTIFICATION_TORM-" + schdulerData.toString()
						+ "====================================");
				try {
					emailInsert.sendBAUAlertEmailToRm(schdulerData);
					userDao.updateEmailStatus(schdulerData.getAccountSchedulerId());
				} catch (Exception e) {
					if (e instanceof NullPointerException) {
						EmailErrorCode emailError = new EmailErrorCode("EmailNull", 409);
						logger.info("BAU_VAS_NOTIFICATION_TORM condition" + emailError);

					}
					continue;
				}

			} else if (schdulerData.getEvent().equalsIgnoreCase("KYC_APPROVAL_FROMRMTO_BANk")) {
				logger.info("============Inside KYC_APPROVAL_FROMRMTO_CUSTOMER condition==========");
				logger.info("====================================KYC_APPROVAL_FROMRMTO_CUSTOMER:-"
						+ schdulerData.toString() + "====================================");
				try {
					emailInsert.sendApprovalEmailToBank(schdulerData);
					userDao.updateEmailStatus(schdulerData.getAccountSchedulerId());
				} catch (Exception e) {
					logger.info("============Inside KYC_APPROVAL_FROMRMTO_CUSTOMER condition==========");
					System.out.println("====================================KYC_APPROVAL_FROMRMTO_CUSTOMER:-"
							+ schdulerData.toString() + "====================================");
					if (e instanceof NullPointerException) {
						EmailErrorCode emailError = new EmailErrorCode("EmailNull", 409);
						logger.info("Inside KYC_APPROVAL_FROMRMTO_CUSTOMER condition=" + emailError);

					}
				}

			} else if (schdulerData.getEvent().equalsIgnoreCase(AppConstants.KYC_APP_FROMRM_TO_CU)) {
				logger.info("============Inside KYC_APPROVAL_FROMRMTO_CUSTOMER condition==========");
				logger.info("====================================KYC_APPROVAL_FROMRMTO_CUSTOMER:-"
						+ schdulerData.toString() + "====================================");
				try {
					emailInsert.sendApprovalEmailToCU(schdulerData);
					userDao.updateEmailStatus(schdulerData.getAccountSchedulerId());
				} catch (Exception e) {
					logger.info("============Inside KYC_APPROVAL_FROMRMTO_CUSTOMER condition==========");
					System.out.println("====================================KYC_APPROVAL_FROMRMTO_CUSTOMER:-"
							+ schdulerData.toString() + "====================================");
					if (e instanceof NullPointerException) {
						EmailErrorCode emailError = new EmailErrorCode("EmailNull", 409);
						logger.info("Inside KYC_APPROVAL_FROMRMTO_CUSTOMER condition=" + emailError);

					}
				}

			} else if (schdulerData.getEvent().equalsIgnoreCase(AppConstants.KYC_APP_FROMRM_TO_RE)) {
				logger.info("============Inside KYC_APPROVAL_FROMRMTO_CUSTOMER condition==========");
				logger.info("====================================KYC_APPROVAL_FROMRMTO_CUSTOMER:-"
						+ schdulerData.toString() + "====================================");
				try {
					emailInsert.sendApprovalEmailToRE(schdulerData);
					userDao.updateEmailStatus(schdulerData.getAccountSchedulerId());
				} catch (Exception e) {
					logger.info("============Inside KYC_APPROVAL_FROMRMTO_CUSTOMER condition==========");
					logger.info("====================================KYC_APPROVAL_FROMRMTO_CUSTOMER:-"
							+ schdulerData.toString() + "====================================");
					if (e instanceof NullPointerException) {

						EmailErrorCode emailError = new EmailErrorCode("EmailNull", 409);

						logger.info("Inside KYC_APPROVAL_FROMRMTO_CUSTOMER condition=" + emailError);

					}
					continue;
				}

			} else if (schdulerData.getEvent().equalsIgnoreCase(AppConstants.KYC_REJ_FROMRM_TO_RE_Support)) {
				logger.info("============Inside KYC_REJ_FROMRM_TO_RE_Support condition==========");
				logger.info("====================================KYC_REJ_FROMRM_TO_RE_Support:-"
						+ schdulerData.toString() + "====================================");
				try {
					String CNumber = systemConfig.findByNumber();
					String email = systemConfig.findByEmail();
					emailInsert.sendRejectionEmailFromRmTORe(schdulerData, CNumber, email);
					userDao.updateEmailStatus(schdulerData.getAccountSchedulerId());
				} catch (Exception e) {
					logger.info("============Inside KYC_REJ_FROMRM_TO_RE_Support condition==========");
					logger.info("====================================KYC_APPROVAL_FROMRMTO_CUSTOMER:-"
							+ schdulerData.toString() + "====================================");
					if (e instanceof NullPointerException) {

						EmailErrorCode emailError = new EmailErrorCode("EmailNull", 409);

						logger.info("Inside KYC_REJ_FROMRM_TO_RE_Support condition=" + emailError);

					}
					continue;
				}

			} else if (schdulerData.getEvent().equalsIgnoreCase(AppConstants.KYC_REJ_FROMRM_TO_CU_Support)) {
				logger.info("============Inside KYC_APPROVAL_FROMRMTO_CUSTOMER condition==========");
				logger.info("====================================KYC_APPROVAL_FROMRMTO_CUSTOMER:-"
						+ schdulerData.toString() + "====================================");
				try {
					String CNumber = systemConfig.findByNumber();
					String email = systemConfig.findByEmail();
					emailInsert.sendRejectionEmailFromRmTOCU(schdulerData, CNumber, email);
					userDao.updateEmailStatus(schdulerData.getAccountSchedulerId());
				} catch (Exception e) {
					logger.info("============Inside KYC_REJ_FROMRM_TO_CU_Support condition==========");
					logger.info("====================================KYC_REJ_FROMRM_TO_CU_Support:-"
							+ schdulerData.toString() + "====================================");
					if (e instanceof NullPointerException) {

						EmailErrorCode emailError = new EmailErrorCode("EmailNull", 409);

						logger.info("Inside KYC_APPROVAL_FROMRMTO_CUSTOMER condition=" + emailError);

					}
					continue;
				}

			} else if (schdulerData.getEvent().equalsIgnoreCase(AppConstants.KYC_REJ_FROMRM_TO_BA_Support)) {
				logger.info("============Inside KYC_REJ_FROMRM_TO_BA_Support condition==========");
				logger.info("====================================KYC_REJ_FROMRM_TO_BA_Support:-"
						+ schdulerData.toString() + "====================================");
				try {
					String CNumber = systemConfig.findByNumber();
					String email = systemConfig.findByEmail();
					emailInsert.sendRejectionEmailFromRmTOBA(schdulerData, CNumber, email);
					userDao.updateEmailStatus(schdulerData.getAccountSchedulerId());
				} catch (Exception e) {
					logger.info("============Inside KYC_REJ_FROMRM_TO_BA_Support condition==========");
					logger.info("====================================KYC_REJ_FROMRM_TO_BA_Support:-"
							+ schdulerData.toString() + "====================================");
					if (e instanceof NullPointerException) {

						EmailErrorCode emailError = new EmailErrorCode("EmailNull", 409);

						logger.info("Inside KYC_REJ_FROMRM_TO_BA_Support condition=" + emailError);

					}
					continue;
				}

			} else if (schdulerData.getEvent().equalsIgnoreCase(AppConstants.ACCOUNT_REFER)) {
				logger.info("============Inside ACCOUNT_REFER condition==========");
				logger.info("====================================ACCOUNT_REFER:-" + schdulerData.toString()
						+ "====================================");
				try {
					emailInsert.sendCustAccountReferredEmailToSource(schdulerData);
					userDao.updateEmailStatus(schdulerData.getAccountSchedulerId());
				} catch (Exception e) {
					if (e instanceof NullPointerException) {
						EmailErrorCode emailError = new EmailErrorCode("EmailNull", 409);
						logger.info("Inside ACCOUNT_REFER condition" + emailError);
					}
					continue;
				}

			} else if (schdulerData.getEvent().equalsIgnoreCase(AppConstants.KYC_REJ_FROMRM_TO_CU)) {
				logger.info("============Inside KYC_REJECTION_FROMRMTO_CUSTOMER condition==========");
				logger.info("====================================KYC_REJECTION_FROMRMTO_CUSTOMER:-"
						+ schdulerData.toString() + "====================================");
				try {
					String CNumber = systemConfig.findByNumber();
					String email = systemConfig.findByEmail();
					emailInsert.sendRejectionEmailFromRmTOCU(schdulerData, CNumber, email);
					userDao.updateEmailStatus(schdulerData.getAccountSchedulerId());
				} catch (Exception e) {
					if (e instanceof NullPointerException) {
						EmailErrorCode emailError = new EmailErrorCode("EmailNull", 409);
						logger.info("Inside KYC_REJECTION_FROMRMTO_CUSTOMER condition" + emailError);

					}
				}

			} else if (schdulerData.getEvent().equalsIgnoreCase(AppConstants.KYC_REJ_FROMRM_TO_RE)) {
				logger.info("============Inside KYC_REJECTION_FROMRMTO_CUSTOMER condition==========");
				logger.info("====================================KYC_REJECTION_FROMRMTO_CUSTOMER:-"
						+ schdulerData.toString() + "====================================");
				try {
					String CNumber = systemConfig.findByNumber();

					String email = systemConfig.findByEmail();

					emailInsert.sendRejectionEmailFromRmTORe(schdulerData, CNumber, email);
					userDao.updateEmailStatus(schdulerData.getAccountSchedulerId());
				} catch (Exception e) {
					if (e instanceof NullPointerException) {
						EmailErrorCode emailError = new EmailErrorCode("EmailNull", 409);
						logger.info("Inside KYC_REJECTION_FROMRMTO_CUSTOMER condition" + emailError);

					}
					continue;
				}

			} else if (schdulerData.getEvent().equalsIgnoreCase(AppConstants.KYC_REJ_FROMRM_TO_BA)) {
				logger.info("============Inside KYC_REJ_FROMRM_TO_BA condition==========");
				logger.info("====================================KYC_REJ_FROMRM_TO_BA:-" + schdulerData.toString()
						+ "====================================");
				try {
					String CNumber = systemConfig.findByNumber();
					String email = systemConfig.findByEmail();
					emailInsert.sendRejectionEmailFromRmTOBA(schdulerData, CNumber, email);
					userDao.updateEmailStatus(schdulerData.getAccountSchedulerId());
				} catch (Exception e) {
					if (e instanceof NullPointerException) {
						EmailErrorCode emailError = new EmailErrorCode("EmailNull", 409);
						logger.info("Inside KYC_REJ_FROMRM_TO_BA condition" + emailError);

					}
					continue;
				}

			} else if (schdulerData.getEvent().equalsIgnoreCase(AppConstants.KYC_UPLOAD)) {
				NimaiClient clientUserId = userDao.getClientDetailsbyUserId(schdulerData.getUserid());
				logger.info("============Inside" + schdulerData.getEvent() + "condition==========");
				logger.info("====================================" + schdulerData.getEvent() + ":-"
						+ schdulerData.toString() + "====================================");
				if (clientUserId != null) {
					if (schdulerData.getrMemailId() != null) {
						try {
							/* method for email sending to customer kyc */
							emailInsert.sendKycEmail(schdulerData.getEvent(), clientUserId, schdulerData);
							try {
								/* method for sending the email to rm kyc */
								emailInsert.sendEmailToRm(schdulerData.getEvent(), clientUserId, schdulerData);
							} catch (Exception e) {
								if (e instanceof NullPointerException) {

									EmailErrorCode emailError = new EmailErrorCode("EmailNull", 409);

									logger.info("Inside KYC_UPLOAD" + emailError);

								}
							}

							userDao.updateEmailStatus(schdulerData.getAccountSchedulerId());
							response.setMessage(ErrorDescription.getDescription("ASA002"));

						} catch (Exception e) {
							if (e instanceof NullPointerException) {
								EmailErrorCode emailError = new EmailErrorCode("EmailNull", 409);
								logger.info("Inside KYC_UPLOAD" + emailError);
							}
							continue;
						}
					} else if (schdulerData.getrMemailId() == null) {
						try {

							emailInsert.sendKycEmail(schdulerData.getEvent(), clientUserId, schdulerData);
							int scedulerid = schdulerData.getAccountSchedulerId();
							userDao.updateEmailStatus(scedulerid);

						} catch (Exception e) {
							if (e instanceof NullPointerException) {
								EmailErrorCode emailError = new EmailErrorCode("EmailNull", 409);
								logger.info("Inside KYC_UPLOAD" + emailError);

							}
						}
					}
//					}else {
//						/* method for email sending to customer kyc */
//						emailInsert.sendKycEmailFromRmTOCustomer(schdulerData.getEvent(), clientUserId,
//								schdulerData);
//						int scedulerid = schdulerData.getAccountSchedulerId();
//						userDao.updateEmailStatus(scedulerid);
//					}
				} else {
					logger.info("Inside" + schdulerData.getEvent() + "condition user id not found");
					emailStatus = "UserId_NOT_Registered" + (schdulerData.getEvent());
					userDao.updateInvalidIdEmailFlag(schdulerData.getAccountSchedulerId(), emailStatus);

				}

			}
			InetAddress ip;
			try {

				ip = InetAddress.getLocalHost();
				System.out
						.println("=============================Current IP address========== : " + ip.getHostAddress());

			} catch (UnknownHostException e) {

				e.printStackTrace();
				continue;
			}

		}
	}

	public ResponseEntity<Object> validateResetPasswordLink(String tokenKey) {
		logger.info("======validateResetPasswordLink method invoked=====:" + tokenKey);
		GenericResponse response = new GenericResponse();
		String tokenInitial = tokenKey.substring(0, 2);
		System.out.println(tokenInitial);
		if (tokenInitial.equalsIgnoreCase("RE")) {
			SubsidiaryBean beanResponse = new SubsidiaryBean();
			Date currentDate = new Date();
			NimaiMRefer nimaiRefer = userDao.getReferDetailsByToken(tokenKey);
			System.out.println("nimaiRefer" + nimaiRefer);
			if (nimaiRefer != null && nimaiRefer.getToken().equals(tokenKey)) {
				Date referExpirydate = nimaiRefer.getTokenExpiryTime();
				System.out.println("referExpirydate" + referExpirydate);
				if (currentDate.before(referExpirydate)) {
					beanResponse.setEmailId(nimaiRefer.getEmailAddress());
					beanResponse.setUserId(nimaiRefer.getUserid().getUserid());
					response.setData(beanResponse);
					response.setMessage(tokenKey);
					response.setFlag(1);
					return new ResponseEntity<Object>(response, HttpStatus.OK);
				} else {
					response.setFlag(0);
					response.setErrCode("ASA008");
					response.setMessage(ErrorDescription.getDescription("ASA008"));
					return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
				}

			}

			response.setMessage("Invalid Token");
			return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
		} else if (tokenInitial.equalsIgnoreCase("FO")) {
			try {
				NimaiMLogin nimaiLogin = userDao.getUserDetailsByTokenKey(tokenKey);
				Date dnow = new Date();

				if (nimaiLogin != null && nimaiLogin.getToken().equals(tokenKey)) {
					if (dnow.after(nimaiLogin.getTokenExpiryDate())) {
						response.setFlag(0);
						response.setErrCode("ASA015");
						response.setMessage(ErrorDescription.getDescription("ASA015"));
						return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
					}
					response.setMessage(tokenKey);
					response.setFlag(1);
					return new ResponseEntity<Object>(response, HttpStatus.OK);
				} else {
					response.setFlag(0);
					response.setErrCode("ASA016");
					response.setMessage(ErrorDescription.getDescription("ASA016"));
					return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
				}
			} catch (Exception e) {
				response.setFlag(0);
				response.setErrCode("ASA017");
				response.setMessage(ErrorDescription.getDescription("ASA017"));
				return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
			}

		} else {
			try {
				NimaiMLogin nimaiLogin = userDao.getUserDetailsByTokenKey(tokenKey);
				Date dnow = new Date();
				Date expirydate = nimaiLogin.getTokenExpiryDate();
				if (nimaiLogin != null && nimaiLogin.getToken().equals(tokenKey)) {
					if (dnow.before(expirydate)) {
						if (tokenKey.substring(0, 2).equalsIgnoreCase("CU")) {
							NimaiEmailScheduler schData = new NimaiEmailScheduler();
							schData.setSubUserId(nimaiLogin.getUserid().getUserid());
							schData.setUserid(nimaiLogin.getUserid().getAccountSource());
							schData.setEvent(AppConstants.SUBSIDIARY_ACTIVATION_EVENT);
							schData.setEmailStatus("Sent");
							userDao.saveSubDetails(schData);
						}
						UserIdentificationBean beanResponse = new UserIdentificationBean();
						if (nimaiLogin.getUserid().getAccountType().equalsIgnoreCase("MASTER")) {
							beanResponse.setUserIdentification(nimaiLogin.getUserid().getAccountType());
							response.setData(beanResponse);
						}
						response.setMessage(tokenKey);
						response.setFlag(1);
						return new ResponseEntity<Object>(response, HttpStatus.OK);
					} else {
						response.setFlag(0);
						response.setErrCode("ASA004");
						response.setMessage(ErrorDescription.getDescription("ASA004"));
						return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
					}

				}

			} catch (Exception e) {

				e.printStackTrace();
			}
		}
		response.setMessage("Invalid Token");
		return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
	}

	@Override
	public ResponseEntity<?> sendSubsidiaryEmail(SubsidiaryBean subsidiaryBean) {
		logger.info("=========sendSubsidiaryEmail method invoked======:" + subsidiaryBean.toString());
		GenericResponse response = new GenericResponse();
		String errorString = this.resetUserValidator.subsidiaryValidation(subsidiaryBean);
		if (errorString.equalsIgnoreCase("success")) {
			try {
				NimaiClient clientUseId = userDao.getClientDetailsbyUserId(subsidiaryBean.getUserId());
				NimaiClient subsUserDetails = userDao.getcliDetailsByEmailId(subsidiaryBean.getEmailId());

				if (clientUseId != null) {
					String userId = clientUseId.getUserid();

					String tokenKey = userId.concat("_").concat(utility.generatePasswordResetToken());
					String link = "";

					String clientDomainName = utility.getEmailDomain(clientUseId.getEmailAddress());
//					String subsidiaryDomainName = utility.getEmailDomain(subsidiaryBean.getEmailId());
//					if (clientDomainName.equalsIgnoreCase(subsidiaryDomainName)) {
					NimaiFSubsidiaries subsidiary = new NimaiFSubsidiaries();
					NimaiEmailScheduler schData = new NimaiEmailScheduler();
					// NimaiClient subDetails =
					// userDao.getClientDetailsbyUserId(subsidiaryBean.getSubUserId());
					String token = tokenKey.substring(tokenKey.indexOf("_") + 1);
					Calendar cal = Calendar.getInstance();
					Date insertedDate = cal.getTime();
					Date tokenExpiry = utility.getLinkExpiryDate();
					subsidiary.setTokenExpiryDate(tokenExpiry);
					subsidiary.setSubsidiaryToken(tokenKey);
					subsidiary.setUserid(clientUseId);
					subsidiary.setInsertedDate(insertedDate);
					subsidiary.setSubUserId(subsUserDetails.getUserid());
					subsidiary.setSubsidiaryEmail(subsidiaryBean.getEmailId());
					schData.setUserid(clientUseId.getUserid());
					schData.setSubUserId(subsUserDetails.getUserid());
					schData.setUserName(clientUseId.getFirstName());
					schData.setSubFirstName(subsUserDetails.getFirstName());
					schData.setSubLandLine(subsUserDetails.getLandline());
					schData.setSubLastName(subsUserDetails.getLastName());
					schData.setSubMobile(subsUserDetails.getMobileNumber());
					schData.setSubCountry(subsUserDetails.getCountryName());
					schData.setSubOfficailEmail(subsUserDetails.getEmailAddress());
					schData.setEvent("SUBSIDIARY_ADDEDED");
					schData.setEmailStatus("Pending");
					userDao.saveSubsidiaryDetails(subsidiary);
					response.setFlag(0);
					link = subAccountActivationLink + tokenKey;

					NimaiClient subDet = userDao.getClientDetailsBySubsidiaryId(subsidiary.getSubsidiaryEmail());
					userDao.updateSubsidiaryTokenDetails(tokenExpiry, tokenKey, subDet.getUserid());
					try {
						emailInsert.sendSubAccAcivationLink(link, subsidiaryBean, subsUserDetails);
					} catch (Exception e) {
						e.printStackTrace();
						if (e instanceof NullPointerException) {
							response.setMessage("Email Sending failed");
							EmailErrorCode emailError = new EmailErrorCode("EmailNull", 409);
							response.setData(emailError);
							return new ResponseEntity<Object>(response, HttpStatus.CONFLICT);
						}
					}
					userDao.saveSubDetails(schData);
					response.setMessage(ErrorDescription.getDescription("ASA002"));
					return new ResponseEntity<Object>(response, HttpStatus.OK);
//					} else {
//						response.setErrCode("ASA006");
//						response.setMessage(ErrorDescription.getDescription("ASA006"));
//						return new ResponseEntity<Object>(response, HttpStatus.OK);
//					}

				}

				else {
					response.setErrCode("ASA001");
					response.setMessage(ErrorDescription.getDescription("ASA001"));
					return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
				}

			} catch (Exception e) {
				e.printStackTrace();
				response.setErrCode("ASA001");
				response.setMessage(ErrorDescription.getDescription("ASA001"));
				return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
			}

		} else {
			response.setErrCode("EXE000");
			response.setMessage(ErrorDescription.getDescription("EXE000") + errorString.toString());
			return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
		}

	}

	@Override
	public ResponseEntity<?> sendReferEmail(SubsidiaryBean subsidiaryBean) {
		logger.info("=========sendReferEmail method invoked======:" + subsidiaryBean.toString());
		GenericResponse response = new GenericResponse();
		String errorString = this.resetUserValidator.subsidiaryValidation(subsidiaryBean);
		if (errorString.equalsIgnoreCase("success")) {
			try {
				NimaiClient clientUseId = userDao.getClientDetailsbyUserId(subsidiaryBean.getUserId());
				NimaiClient referUserDetails = userDao.getcliDetailsByEmailId(subsidiaryBean.getEmailId());
				if (clientUseId != null && referUserDetails != null) {
					String userId = clientUseId.getUserid();

					String tokenKey = userId.concat("_").concat(utility.generatePasswordResetToken());

					if (subsidiaryBean.getEvent().equalsIgnoreCase(AppConstants.ADD_REFER)) {

						NimaiMRefer referDetails = userDao.getreferDetails(subsidiaryBean.getReferenceId());
						// System.out.println("UserId" + referDetails.getUserid());
						if (referDetails != null) {
							String refertokenKey = ("RE")
									.concat(userId.concat("_").concat(utility.generatePasswordResetToken()));
							System.out.println(refertokenKey);
							NimaiMRefer referUser = new NimaiMRefer();
							NimaiEmailScheduler schedulerData = new NimaiEmailScheduler();
							schedulerData.setUserid(subsidiaryBean.getUserId());
							schedulerData.setUserName(clientUseId.getFirstName());
							Calendar cal1 = Calendar.getInstance();
							Date addReferdate = cal1.getTime();
							schedulerData.setEmailStatus(AppConstants.STATUS);
							schedulerData.setEvent(AppConstants.ADD_REFER_TO_PARENT);
							schedulerData.setInsertedDate(addReferdate);
							schedulerData.setEmailId(clientUseId.getEmailAddress());
							try {
								userDao.saveSubDetails(schedulerData);
							} catch (Exception e) {
								e.printStackTrace();
								response.setMessage("Exception occure  saving data in schedulerTable");
								return new ResponseEntity<Object>(response, HttpStatus.OK);
							}
							// System.out.println(referUser.getReferenceId());
							String token = tokenKey.substring(tokenKey.indexOf("_") + 1);
							Calendar cal = Calendar.getInstance();
							Date insertedDate = cal.getTime();
							Date tokenExpiry = utility.getLinkExDate();
							try {
								userDao.updateReferTokenDetails(tokenExpiry, refertokenKey, clientUseId, insertedDate,
										subsidiaryBean.getEmailId(), subsidiaryBean.getReferenceId());
							} catch (Exception e) {
								e.printStackTrace();
								response.setMessage(
										"Exception occure while updating referTokenDetails in nimai_m_refer");
								return new ResponseEntity<Object>(response, HttpStatus.OK);
							}
							try {
								System.out.println("Refer User Id: " + referUserDetails.getUserid());
								userDao.updateReTokenLoginTable(refertokenKey, insertedDate, tokenExpiry,
										referUserDetails.getUserid());
							} catch (Exception e) {
								e.printStackTrace();
								response.setMessage(
										"Exception occure while updating referTokenDetails in nimai_m_login");
								return new ResponseEntity<Object>(response, HttpStatus.OK);
							}

							response.setFlag(1);
							String referLink = referAccountActivationLink + refertokenKey;
							System.out.println("Activation link:::" + referLink);
							try {
								emailInsert.sendReferAccAcivationLink(referLink, subsidiaryBean, referUserDetails);

							} catch (Exception e) {
								e.printStackTrace();
								if (e instanceof NullPointerException) {
									response.setMessage("Email Sending failed");
									EmailErrorCode emailError = new EmailErrorCode("EmailNull", 409);
									response.setData(emailError);
									return new ResponseEntity<Object>(response, HttpStatus.CONFLICT);
								}
							}
						}

					} else {
						response.setMessage("Refer Id Entry not Present");
						return new ResponseEntity<Object>(response, HttpStatus.OK);
					}

					response.setErrCode("ASA002");
					response.setMessage(ErrorDescription.getDescription("ASA002"));
					return new ResponseEntity<Object>(response, HttpStatus.OK);

				}

				else {
					response.setErrCode("ASA001");
					response.setMessage(ErrorDescription.getDescription("ASA001"));
					return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
				}

			} catch (Exception e) {
				e.printStackTrace();
				response.setErrCode("ASA001");
				response.setMessage(ErrorDescription.getDescription("ASA001"));
				return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
			}

		} else {
			response.setErrCode("EXE000");
			response.setMessage(ErrorDescription.getDescription("EXE000") + errorString.toString());
			return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
		}
//		response.setErrCode("ASA001");
//		response.setMessage(ErrorDescription.getDescription("ASA001"));
//		return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);

	}

	@Override
	public ResponseEntity<?> validateSubsidiaryLink(String token) {
		logger.info("=======validateSubsidiaryLink method invoked======:" + token);
		GenericResponse response = new GenericResponse();
		String tokenInitial = token.substring(0, 2);
		System.out.println(tokenInitial);
		try {
			SubsidiaryBean beanResponse = new SubsidiaryBean();
			Date dnow = new Date();
			/* for refer token checking,token will start with RE */
			if (tokenInitial.equalsIgnoreCase("RE")) {
				NimaiMRefer nimaiRefer = userDao.getReferDetailsByToken(token);

				Date referExpirydate = nimaiRefer.getTokenExpiryTime();
				if (nimaiRefer != null && nimaiRefer.getToken().equals(token)) {
					if (dnow.before(referExpirydate)) {
						beanResponse.setEmailId(nimaiRefer.getEmailAddress());
						beanResponse.setUserId(nimaiRefer.getUserid().getUserid());
						response.setData(beanResponse);
						response.setMessage(token);
						response.setFlag(1);
						return new ResponseEntity<Object>(response, HttpStatus.OK);
					} else {
						response.setFlag(0);
						response.setErrCode("ASA008");
						response.setMessage(ErrorDescription.getDescription("ASA008"));
						return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
					}

				}
			}
			/* for bank and user token initials will start with BA or Cu */
			else if ((tokenInitial.equalsIgnoreCase("CU") || tokenInitial.equalsIgnoreCase("BA"))) {
				NimaiFSubsidiaries nimaiFSub = userDao.getSubsidiaryDetailsByToken(token);
				Date subExpirydate = nimaiFSub.getTokenExpiryDate();
				if (nimaiFSub != null && nimaiFSub.getSubsidiaryToken().equals(token)) {
					if (dnow.before(subExpirydate)) {
//						NimaiEmailScheduler schData = new NimaiEmailScheduler();
//						schData.setSubUserId(nimaiFSub.getSubUserId());
//						schData.setUserid(nimaiFSub.getUserid().getAccountSource());
//						schData.setEvent(AppConstants.SUBSIDIARY_ACTIVATION_EVENT);
//						schData.setEmailStatus("Pending");
						beanResponse.setEmailId(nimaiFSub.getSubsidiaryEmail());
						beanResponse.setUserId(nimaiFSub.getUserid().getUserid());
						response.setData(beanResponse);
						response.setMessage(token);
						response.setFlag(1);
						return new ResponseEntity<Object>(response, HttpStatus.OK);
					} else {
						response.setFlag(0);
						response.setErrCode("ASA007");
						response.setMessage(ErrorDescription.getDescription("ASA007"));
						return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
					}

				}
			} else {
				/* while sending token from sendsubsidiary api token(token_passcode) */
				String passcode = token.substring(11, 16);
				String branchToken = token.substring(0, 10);
				NimaiMBranch nimaiMbranch = userDao.getbranchDetailsByToken(branchToken);
				Date subExpirydate = nimaiMbranch.getExpryTime();
				if (nimaiMbranch != null && nimaiMbranch.getToken().equals(branchToken)) {
					if (passcode.equals(nimaiMbranch.getPasscodeValue())) {
						if (dnow.before(subExpirydate)) {
							beanResponse.setEmailId(nimaiMbranch.getEmailId());
							beanResponse.setUserId(nimaiMbranch.getUserid());
							response.setData(beanResponse);
							response.setMessage(token);
							response.setFlag(1);
							return new ResponseEntity<Object>(response, HttpStatus.OK);
						} else {
							response.setFlag(0);
							response.setErrCode("ASA009");
							response.setMessage(ErrorDescription.getDescription("ASA009"));
							return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
						}
					} else {
						response.setMessage("Passcode does not match");
						return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
					}

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		response.setMessage("Invalid Token");
		return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
	}

	/* Resending the email within expiry time */
	@Override
	public ResponseEntity<?> sendbranchUserLink(BranchUserRequest branchUserLink) {
		logger.info("=====sendbranchUserLink=========:" + branchUserLink.toString());
		String errorString = this.resetUserValidator.buPassCodeValidator(branchUserLink);
		if (errorString.equalsIgnoreCase("success")) {
			Date currentDateTime = new Date();
			NimaiMBranch branchUser = new NimaiMBranch();
			NimaiClient clientUseId = custRepo.getOne(branchUserLink.getUserId());
			if (clientUseId.getKycStatus() == null) {
				return new ResponseEntity<>("KYC status not maintain", HttpStatus.OK);
			}
			if (clientUseId.getKycStatus().equalsIgnoreCase("Approved")) {
				branchUser = brRepo.getOne(Integer.parseInt(branchUserLink.getBranchId()));
				if (branchUser.getPasscodeCount() == null) {
					try {
						int count = 0;
						// userDao.updatePassCount(currentDateTime, count, branchUser.getBrId());
						branchUser.setInsertTime(currentDateTime);
						branchUser.setPasscodeCount(count);
						branchUser.setBrId(branchUser.getBrId());
						brRepo.save(branchUser);

					} catch (Exception e) {
						e.printStackTrace();
						return new ResponseEntity<>("Passcode Count is null", HttpStatus.OK);
					}

				}

				if (branchUser.getPasscodeCount() < 3) {
					try {
						String urlToken = sendBranchUserLink(branchUserLink, clientUseId, response, branchUser);
						if (urlToken.equalsIgnoreCase("Email sending failed")) {
							response.setErrCode("ASA014");
						} else {
							response.setErrCode("ASA002");
							response.setData(urlToken);
							response.setId(Integer.parseInt(branchUserLink.getBranchId()));
						}
						response.setMessage(ErrorDescription.getDescription("ASA002"));
						return new ResponseEntity<Object>(response, HttpStatus.OK);
					} catch (Exception e) {
						logger.info(
								"=========Catch block of send branch User Link (branchUser.getPasscodeCount() < 3)======:");
						e.printStackTrace();
						response.setMessage("Something went wrong");
						return new ResponseEntity<>(response, HttpStatus.OK);
					}

				} else if (branchUser.getPasscodeCount() >= 3) {

					long diff = currentDateTime.getTime() - branchUser.getInsertTime().getTime();
					long differenceMinutes = diff / (60 * 1000) % 60;
					// System.out.println("=============currentTime" + differenceMinutes);
					logger.info("=============currentTime" + differenceMinutes);
					// System.out.println("=============currentTime" +
					// branchUser.getInsertTime().getTime());
					logger.info("=============currentTime" + branchUser.getInsertTime().getTime());
					Calendar calendar = Calendar.getInstance();
					System.out.println("=============difference in minutes" + differenceMinutes);
					logger.info("=============difference in minutes" + differenceMinutes);

					if (differenceMinutes <= 10) {
						response.setMessage(
								"You have excedded maximum attempts to login into system,Yoour account is temprorarily blocked,try again after 10 minutes.");
						response.setErrCode("EX003");
						response.setId(Integer.parseInt(branchUserLink.getBranchId()));

						return new ResponseEntity<>(response, HttpStatus.OK);
					} else if (differenceMinutes > 10) {
						try {
							String urlToken = sendBranchUserLink(branchUserLink, clientUseId, response, branchUser);
							int passcount = 0;
							userDao.updateBranchUserDetails(branchUser.getToken(), currentDateTime, passcount);
							if (urlToken.equalsIgnoreCase("Email sending failed")) {
								response.setErrCode("ASA014");
							} else {
								response.setErrCode("ASA002");
								response.setData(urlToken);
								response.setId(Integer.parseInt(branchUserLink.getBranchId()));
							}
							response.setMessage(ErrorDescription.getDescription("ASA002"));
							return new ResponseEntity<Object>(response, HttpStatus.OK);
						} catch (Exception e) {
							logger.info(
									"=========Catch block of send branch User Link else if (differenceMinutes > 10) method======:");
							e.printStackTrace();
							response.setMessage("Something went wrong");
							return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
						}

					}
				}

			} else {
				response.setErrCode("ASA013");
				response.setMessage(ErrorDescription.getDescription("ASA013") + errorString.toString());
				return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
			}
		} else {
			response.setErrCode("EX000");
			response.setMessage(ErrorDescription.getDescription("EXE000") + errorString.toString());
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("Somethig went wrong", HttpStatus.BAD_REQUEST);
	}

	public String sendBranchUserLink(BranchUserRequest branchUserLink, NimaiClient clientUseId,
			GenericResponse response, NimaiMBranch branchUser) {
		NimaiMBranch branchUserDetails = new NimaiMBranch();
		String errorMsg = "Email sending failed";
		try {
			int id = Integer.parseInt(branchUserLink.getBranchId());
			logger.info("=====================branchId:" + id);
			String link = "";
			String passcode = "";
			String bUlink = "";
			String urltoken = "";
			String token = "";
			String employeeId = branchUser.getEmployeeId();
			if (employeeId == null || employeeId.isEmpty()) {
				employeeId = "null";
			} else {
				employeeId = branchUser.getEmployeeId();
			}
			if (branchUser != null && branchUser.getToken() != null) {
				Date dnow = new Date();
				System.out.println(dnow);
				logger.info("=====================String sendBranchUserLink:" + dnow);
				token = utility.generatePasswordResetToken();
				if (branchUser.getExpryTime() == null || branchUser.getToken() == null) {

					passcode = utility.passcodeValue();
					// passcode = "1234";
					String userId = branchUserLink.getUserId();
					String tokenKey = utility.generateLinkToken(clientUseId.getSubscriberType(),
							branchUserLink.getUserId());
					Date tokenExpiry = utility.getLinkExpiryDate();
					Calendar cal = Calendar.getInstance();
					Date insertedDate = cal.getTime();
					branchUserDetails.setEmailId(branchUserLink.getEmailId());
					try {
//						userDao.updateBranchUser(passcode, tokenKey, insertedDate, branchUserLink.getEmailId(), id,
//								tokenExpiry);
						branchUserDetails.setPasscodeValue(passcode);
						branchUserDetails.setToken(tokenKey);
						branchUserDetails.setUserid(branchUserLink.getUserId());
						branchUserDetails.setEmployeeId(branchUser.getEmployeeId());
						branchUserDetails.setInsertTime(insertedDate);
						branchUserDetails.setEmailId(branchUserLink.getEmailId());
						branchUserDetails.setEmployeeName(branchUserLink.getEmployeeName());
						branchUserDetails.setBrId(id);
						branchUserDetails.setExpryTime(tokenExpiry);
						brRepo.save(branchUserDetails);
					} catch (Exception e) {
						e.printStackTrace();
						response.setMessage("Email Sending failed");
						EmailErrorCode emailError = new EmailErrorCode("EmailNull", 409);
						response.setData(emailError);
						return errorMsg;
					}

					urltoken = tokenKey.concat("_").concat(passcode);
					link = subAccountActivationLink + urltoken;
					try {

						emailInsert.sendBranchUserActivationLink(link, branchUserLink, passcode, clientUseId);
						return urltoken;
					} catch (Exception e) {
						if (e instanceof NullPointerException) {
							response.setMessage("Email Sending failed");
							EmailErrorCode emailError = new EmailErrorCode("EmailNull", 409);
							response.setData(emailError);
							return errorMsg;
						}
					}
				}
				if (token.equalsIgnoreCase(branchUser.getToken())) {
					token = utility.generateLinkToken(clientUseId.getSubscriberType(), branchUserLink.getUserId());
				}

				if (dnow.before(branchUser.getExpryTime())) {
					String passcodeValue = utility.passcodeValue();
					try {
						// userDao.updateBranchUser(branchUserLink.getEmailId(), dnow, passcodeValue,
						// token);
						Date tokExpiry = utility.getLinkExpiryDate();
						branchUserDetails.setEmailId(branchUserLink.getEmailId());
						branchUserDetails.setInsertTime(dnow);
						branchUserDetails.setPasscodeValue(passcodeValue);
						branchUserDetails.setUserid(branchUserLink.getUserId());
						branchUserDetails.setEmployeeId(branchUser.getEmployeeId());
						branchUserDetails.setEmployeeName(branchUserLink.getEmployeeName());
						branchUserDetails.setToken(token);
						branchUserDetails.setExpryTime(tokExpiry);
						branchUserDetails.setBrId(id);
						brRepo.save(branchUserDetails);
					} catch (Exception e) {
						e.printStackTrace();
						response.setMessage("Email Sending failed");
						EmailErrorCode emailError = new EmailErrorCode("EmailNull", 409);
						response.setData(emailError);
						return errorMsg;
					}
					urltoken = token.concat("_").concat(passcodeValue);
					bUlink = subAccountActivationLink + urltoken;
					try {
						emailInsert.sendBranchUserActivationLink(bUlink, branchUserLink, passcodeValue, clientUseId);
						return urltoken;
					} catch (Exception e) {
						if (e instanceof NullPointerException) {
							response.setMessage("Email Sending failed");
							EmailErrorCode emailError = new EmailErrorCode("EmailNull", 409);
							response.setData(emailError);
							return errorMsg;
						}
					}
				} else {

					passcode = utility.passcodeValue();
					// passcode = "1234";
					String userId = branchUserLink.getUserId();
					try {
						String tokenKey = utility.generateLinkToken(clientUseId.getSubscriberType(),
								branchUserLink.getUserId());

						Date tokenExpiry = utility.getLinkExpiryDate();
						Calendar cal = Calendar.getInstance();
						Date insertedDate = cal.getTime();
						try {
//						userDao.updateBranchUser(passcode, tokenKey, insertedDate, branchUserLink.getEmailId(), id,
//								tokenExpiry);
							branchUserDetails.setPasscodeValue(passcode);
							branchUserDetails.setToken(tokenKey);
							branchUserDetails.setInsertTime(insertedDate);
							branchUserDetails.setEmailId(branchUserLink.getEmailId());
							branchUserDetails.setUserid(branchUserLink.getUserId());
							branchUserDetails.setEmployeeId(branchUser.getEmployeeId());
							branchUserDetails.setEmployeeName(branchUserLink.getEmployeeName());
							branchUserDetails.setExpryTime(tokenExpiry);
							branchUserDetails.setBrId(id);
							brRepo.save(branchUserDetails);

						}

						catch (Exception e) {
							e.printStackTrace();
							response.setMessage("Email Sending failed");
							EmailErrorCode emailError = new EmailErrorCode("EmailNull", 409);
							response.setData(emailError);
							return errorMsg;
						}

						// userDao.updateBranchUser(branchUserDetails);
						urltoken = tokenKey.concat("_").concat(passcode);
						bUlink = subAccountActivationLink + urltoken;

						try {
							emailInsert.sendBranchUserActivationLink(bUlink, branchUserLink, passcode, clientUseId);
							return urltoken;
						} catch (Exception e) {
							if (e instanceof NullPointerException) {
								response.setMessage("Email Sending failed");
								EmailErrorCode emailError = new EmailErrorCode("EmailNull", 409);
								response.setData(emailError);
								return errorMsg;

							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			} else {

				passcode = utility.passcodeValue();
				// passcode = "1234";
				String userId = branchUserLink.getUserId();
				String tokenKey = utility.generatePasswordResetToken();
				Date tokenExpiry = utility.getLinkExpiryDate();
				Calendar cal = Calendar.getInstance();
				Date insertedDate = cal.getTime();
				branchUserDetails.setEmailId(branchUserLink.getEmailId());
				try {
//					userDao.updateBranchUser(passcode, tokenKey, insertedDate, branchUserLink.getEmailId(), id,
//							tokenExpiry);
					branchUserDetails.setPasscodeValue(passcode);
					branchUserDetails.setToken(tokenKey);
					branchUserDetails.setInsertTime(insertedDate);
					branchUserDetails.setEmailId(branchUserLink.getEmailId());
					branchUserDetails.setUserid(branchUserLink.getUserId());
					branchUserDetails.setEmployeeId(branchUser.getEmployeeId());
					branchUserDetails.setEmployeeName(branchUserLink.getEmployeeName());
					branchUserDetails.setBrId(id);
					branchUserDetails.setExpryTime(tokenExpiry);
					brRepo.save(branchUserDetails);
				} catch (Exception e) {
					e.printStackTrace();
					response.setMessage("Email Sending failed");
					EmailErrorCode emailError = new EmailErrorCode("EmailNull", 409);
					response.setData(emailError);
					return errorMsg;
				}

				urltoken = tokenKey.concat("_").concat(passcode);
				link = subAccountActivationLink + urltoken;
				try {

					emailInsert.sendBranchUserActivationLink(link, branchUserLink, passcode, clientUseId);
					return urltoken;
				} catch (Exception e) {
					if (e instanceof NullPointerException) {
						response.setMessage("Email Sending failed");
						EmailErrorCode emailError = new EmailErrorCode("EmailNull", 409);
						response.setData(emailError);
						return errorMsg;
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return errorMsg;
	}

	@Override
	public ResponseEntity<?> validatePassCodeValue(BranchUserPassCodeBean passCodeBean) {
		logger.info("=========validatePassCodeValue method invoked========");
		String errorString = this.resetUserValidator.passcodeValidation(passCodeBean);
		logger.info("pascodevalue before logic" + passCodeBean.getPasscodeValue());
		logger.info("token before logic" + passCodeBean.getToken());
		if (errorString.equalsIgnoreCase("success")) {
			try {
				NimaiMBranch branchDetails = brRepo.getOne(passCodeBean.getId());
				String employeeId = branchDetails.getEmployeeId();
				if (employeeId == null || employeeId.isEmpty()) {
					employeeId = "null";
				} else {
					employeeId = branchDetails.getEmployeeId();
				}

				logger.info("============passCodeBean.getId():" + passCodeBean.getId());
				NimaiToken token = tokenRepo.getOne(passCodeBean.getUserId());
				String flag = "";
				Date dnow = new Date();
				if (branchDetails != null) {
					Date subExpirydate = branchDetails.getExpryTime();
					if (branchDetails.getToken().equals(passCodeBean.getToken())) {
						if (passCodeBean.getPasscodeValue().equals(branchDetails.getPasscodeValue())) {
							if (dnow.before(subExpirydate)) {
								SubsidiaryBean beanResponse = new SubsidiaryBean();

								if (employeeId.substring(0, 2).equalsIgnoreCase("BA")
										|| employeeId.substring(0, 2).equalsIgnoreCase("BC")
										|| employeeId.substring(0, 2).equalsIgnoreCase("CU")
										|| employeeId.substring(0, 2).equalsIgnoreCase("RE")) {
									beanResponse.setUserId(branchDetails.getEmployeeId());
								} else {
									beanResponse.setUserId(branchDetails.getUserid());
								}
								int passcodecount = 0;
								branchDetails.setPasscodeCount(passcodecount);
								branchDetails.setInsertTime(dnow);
								branchDetails.setBrId(branchDetails.getBrId());
								brRepo.save(branchDetails);

								response.setData(beanResponse);
								response.setMessage(passCodeBean.getToken());
								response.setFlag(1);
								response.setErrCode("ASA001");
								return new ResponseEntity<Object>(response, HttpStatus.OK);
							} else {
								response.setFlag(0);
								response.setErrCode("ASA009");
								response.setMessage(ErrorDescription.getDescription("ASA009"));
								return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
							}
						} else {
							if (branchDetails.getPasscodeCount() == 3) {
								int passcodecount = 3;
								branchDetails.setPasscodeCount(passcodecount);
								branchDetails.setInsertTime(dnow);
								branchDetails.setBrId(branchDetails.getBrId());
								brRepo.save(branchDetails);
								response.setMessage("Attempt excedded");
								try {
									flag = "attempt_true";
									token.setIsInvalidCaptcha(flag);
									tokenRepo.save(token);

									System.out.println("Inside true comdition for invalidCaptchaFlag" + flag);
								} catch (Exception e) {
									e.printStackTrace();
									response.setErrCode("EX001");
									response.setMessage("Error while updating invalid captcha details");

								}
								return new ResponseEntity<>(response, HttpStatus.OK);
							}
							int passcodeCOunt = branchDetails.getPasscodeCount() + 1;
							branchDetails.setPasscodeCount(passcodeCOunt);
							branchDetails.setInsertTime(dnow);
							branchDetails.setBrId(branchDetails.getBrId());
							brRepo.save(branchDetails);
							response.setMessage("Passcode does not match");
							try {
								flag = "passcode_true";
								token.setIsInvalidCaptcha(flag);
								tokenRepo.save(token);
								System.out.println("Inside true condition for invalid passcode condition" + flag);
							} catch (Exception e) {
								e.printStackTrace();
								response.setErrCode("EX001");
								response.setMessage("Error while updating invalid captcha details");

							}
							return new ResponseEntity<Object>(response, HttpStatus.OK);
						}

					} else {
						response.setErrCode("EX001");
						response.setMessage("Token not found");
						try {
							flag = "token_true";
							token.setIsInvalidCaptcha(flag);
							tokenRepo.save(token);
							System.out.println("Inside true comdition for invalidToken condition" + flag);
						} catch (Exception e) {
							e.printStackTrace();
							response.setErrCode("EX001");
							response.setMessage("Error while updating invalid captcha details");

						}
						return new ResponseEntity<Object>(response, HttpStatus.OK);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				response.setMessage("Branch Id not found");
				return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
			}
		}
		response.setErrCode("EXE000");
		response.setMessage(ErrorDescription.getDescription("EXE000") + errorString.toString());
		return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
	}

	@Override
	public ResponseEntity<Object> sendAdminEmail(AdminBean userRegistratinBean) throws Exception {

		logger.info("=======Inside sendAdminEmail method====" + userRegistratinBean.toString());

		NimaiMLogin mLoginId = userDao.existsByEmpCode(userRegistratinBean.getEmpCode());

		try {

			if (mLoginId == null) {
				System.out.println("==========================EMpDetaila:" + mLoginId.toString());
				response.setErrCode("ASA010");
				response.setMessage(ErrorDescription.getDescription("ASA010"));
				return new ResponseEntity<Object>(response, HttpStatus.OK);

			} else {
				String link = adminForgotPassLink + mLoginId.getEmpCode().getEmpCode() + "&token="
						+ mLoginId.getToken();
				System.out.println("==========================EMpDetaila:" + mLoginId.toString());
				if (userRegistratinBean.getEvent().equalsIgnoreCase(AppConstants.FORGOT_PASS_EVENT)) {
					logger.info("=================link" + link);
					try {
						emailInsert.AdminForgotPassEmail(userRegistratinBean, mLoginId, link);
					} catch (Exception e) {
						e.printStackTrace();
						response.setErrCode("ASA018");
						response.setMessage(ErrorDescription.getDescription("ASA018"));
						return new ResponseEntity<Object>(response, HttpStatus.OK);
					}

				} else if (userRegistratinBean.getEvent().equalsIgnoreCase(AppConstants.ACCOUNT_ACTIVATE_EVENT)) {
					logger.info("=================link" + link);
					try {
						emailInsert.AdminEmail(userRegistratinBean, mLoginId, link);
					} catch (Exception e) {
						e.printStackTrace();
						response.setErrCode("ASA018");
						response.setMessage(ErrorDescription.getDescription("ASA018"));
						return new ResponseEntity<Object>(response, HttpStatus.OK);
					}

				}

			}

			response.setErrCode("ASA002");
			response.setMessage(ErrorDescription.getDescription("ASA002"));
			return new ResponseEntity<Object>(response, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			response.setErrCode("ASA011");
			response.setMessage(ErrorDescription.getDescription("ASA011"));
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
	}

	public void AdminEmail(AdminBean resetpassword) throws Exception {

		EmailComponentMaster emailconfigurationBean = null;
		try {

			emailconfigurationBean = emailConfigurationDAOImpl.findEventConfiguration(resetpassword.getEvent());

			System.out.println(" Fetching Configuration for Reset Password Policy " + emailconfigurationBean);
			Map<String, String> subject = new HashMap<String, String>();
			Map<String, String> body = new HashMap<String, String>();

			subject.put("Subject", emailconfigurationBean.getSubject());

			// ArrayList attachements = new ArrayList();
			// attachements.add(new File("D:\\BDO1.jpg"));
			body.put("link", resetpassword.getLink());
			body.put("username", resetpassword.getUserName());
			body.put("userId", resetpassword.getUserId());
			String toMail = resetpassword.getEmail();

			System.out.println(" Fetching Configuration for Reset Password Policy "
					+ emailconfigurationBean.getSubject() + " :: " + emailconfigurationBean.getEventId());

			ArrayList details = new ArrayList();
			details.add(emailconfigurationBean.getEventId());// 0
			details.add(toMail);// 1
			details.add(subject);// 2
			details.add(body);// 3
			// details.add(attachements);
			System.out.println("details" + details);
			emailProcessorImpl.saveEmail(details, emailconfigurationBean.getEventId());

			emailSend.getDetailsEmail();

		} catch (Exception e) {

			System.out.println("Exception" + e);
			e.printStackTrace();
		}

	}

	@Override
//@Scheduled(cron = "0 0 23 0 0 MON-SAT")
	public ByteArrayInputStream generateEodReport() {
		// TODO Auto-generated method stub

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		java.sql.Date todaysdate = new java.sql.Date(new java.util.Date().getTime());
		System.out.println(todaysdate);
		List<NimaiLC> customerList = userDao.getCustTransactionList(todaysdate);
		System.out.println(customerList.toString());
		Map<String, List<NimaiLC>> groupByUserId = customerList.stream()
				.collect(Collectors.groupingBy(NimaiLC::getUserId));
		System.out.println("New generated methods inside the generate Eod Report" + groupByUserId.toString());
		ArrayList<String> fileLocationArrayList = new ArrayList<>();
		for (Map.Entry<String, List<NimaiLC>> entry : groupByUserId.entrySet()) {
			try {
				String fileLocation = com.nimai.email.utility.ExcelUtility.generateReportToExcel(entry.getKey(),
						entry.getValue());
				System.out.println("filelocation name in the middle" + fileLocation);
				for (String filePresent : fileLocationArrayList)
					if (!filePresent.equalsIgnoreCase(fileLocation) && filePresent != null && !filePresent.isEmpty()) {
						try {
							boolean emailStatus = emailInsert.sendEodReport(fileLocation, entry.getKey());
							if (emailStatus != false) {
								System.out.println("false conditions");
								fileLocationArrayList.add(fileLocation);
							}

						} catch (Exception e) {

						}
					}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return null;

	}

	@Override
	public ResponseEntity<?> sendDmmyAccEmail(UserRegistrationBean userBean) {
		// TODO Auto-generated method stub
		NimaiClient nimaiClientdetails = userDao.getClientDetailsbyUserId(userBean.getUserId());
		emailInsert.sendAccEmail(userBean, nimaiClientdetails);

		return null;
	}

	@Override
	public ResponseEntity<?> sendbranchUserPasscode(BranchUserRequest branchUserLink) {
		logger.info("=====sendbranchUserLink=========:" + branchUserLink.toString());
		GenericResponse response = new GenericResponse<>();
		NimaiMBranch branchUserDetails = new NimaiMBranch();
		Date currentTime = new Date();
		Date currentDateTime = new Date();
		String errorString = this.resetUserValidator.buPassCodeValidator(branchUserLink);
		if (errorString.equalsIgnoreCase("success")) {

			try {
				NimaiClient clientUseId = custRepo.getOne(branchUserLink.getUserId());

				if (clientUseId != null) {

//					NimaiMBranch branchUser = userDao.getBranchUserDetails(clientUseId.getEmailAddress(),
//							clientUseId.getUserid());
					NimaiMBranch branchUser = userDao.getBranchUserDetailsByEMpId(clientUseId.getEmailAddress(),
							clientUseId.getUserid());

					if (branchUser == null) {

						String urlToken = sendbrPaasFor1Time(clientUseId, branchUserLink);
						int index = urlToken.indexOf("@");
						int BrID = Integer.parseInt(urlToken.substring(index + 1, urlToken.length()));
						if (urlToken.equalsIgnoreCase("Email sending failed")) {
							response.setErrCode("ASA014");
							response.setMessage(ErrorDescription.getDescription("ASA014"));
						} else {
							response.setErrCode("ASA002");
							response.setMessage(ErrorDescription.getDescription("ASA002"));
							response.setId((BrID));
							response.setData(urlToken);
						}

						return new ResponseEntity<Object>(response, HttpStatus.OK);
					}
					if (branchUser.getPasscodeCount() == null) {

						try {
							int count = 0;
							// userDao.updatePassCount(currentDateTime, count, branchUser.getBrId());
							branchUserDetails.setPasscodeCount(count);
							branchUserDetails.setInsertTime(currentDateTime);
							branchUserDetails.setBrId(branchUser.getBrId());
							brRepo.save(branchUserDetails);

						} catch (Exception e) {
							e.printStackTrace();
							return new ResponseEntity<>("Passcode Count is null", HttpStatus.CONFLICT);
						}

					}
					if (branchUser.getPasscodeCount() < 3) {
						String urlToken = sendPasscodeMethod(branchUserLink, clientUseId, response, branchUser);
						if (urlToken.equalsIgnoreCase("Email sending failed")) {
							response.setErrCode("ASA014");
							response.setMessage(ErrorDescription.getDescription("ASA014"));
						} else {
							response.setErrCode("ASA002");
							response.setMessage(ErrorDescription.getDescription("ASA002"));
							response.setId(branchUser.getBrId());
							response.setData(urlToken);
						}

						return new ResponseEntity<Object>(response, HttpStatus.OK);
					} else if (branchUser.getPasscodeCount() >= 3) {

						long diff = currentTime.getTime() - branchUser.getInsertTime().getTime();

						long differenceMinutes = diff / (60 * 1000) % 60;

						System.out.println("=============currentTime" + differenceMinutes);
						logger.info("=============currentTime" + differenceMinutes);
						System.out.println("=============currentTime" + branchUser.getInsertTime().getTime());
						logger.info("=============currentTime" + branchUser.getInsertTime().getTime());
						Calendar calendar = Calendar.getInstance();
						// calendar.setTime(diff);
						// int minutes = calendar.get(Calendar.MINUTE);
						System.out.println("=============difference in minutes" + differenceMinutes);

						if (differenceMinutes <= 10) {
							response.setMessage(
									"You have excedded maximum attempts to login into system,Yoour account is temprorarily blocked,try again after 10 minutes.");
							response.setErrCode("EX003");
							response.setId(branchUser.getBrId());
							return new ResponseEntity<>(response, HttpStatus.OK);
						} else if (differenceMinutes > 10) {
							String urlToken = sendPasscodeMethod(branchUserLink, clientUseId, response, branchUser);
							int count = 0;
							// userDao.updateBranchUserDetails(branchUser.getToken(), currentDateTime,
							// count);
							branchUserDetails.setToken(branchUser.getToken());
							branchUserDetails.setInsertTime(currentDateTime);
							branchUserDetails.setPasscodeCount(count);
							branchUserDetails.setBrId(branchUser.getBrId());
							brRepo.save(branchUserDetails);

							if (urlToken.equalsIgnoreCase("Email sending failed")) {
								response.setErrCode("ASA014");
								response.setMessage(ErrorDescription.getDescription("ASA014"));
							} else {
								response.setErrCode("ASA002");
								response.setMessage(ErrorDescription.getDescription("ASA002"));
								response.setId(branchUser.getBrId());
								response.setData(urlToken);
							}

							return new ResponseEntity<Object>(response, HttpStatus.OK);
						}
					}
				} else {
					response.setMessage("User Not registred");
					return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			response.setErrCode("EX000");
			response.setMessage(ErrorDescription.getDescription("EXE000") + errorString.toString());
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		response.setMessage("Branch Id not found");
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

	}

	private String sendbrPaasFor1Time(NimaiClient clientUseId, BranchUserRequest branchUserLink) {
		// TODO Auto-generated method stub
		NimaiMBranch branchUserDetails = new NimaiMBranch();
		String link = "";
		String passcode = "";
		String bUlink = "";
		String urltoken = "";
		String errorMsg = "Email Sending failed";
		passcode = utility.passcodeValue();
		// passcode = "1234";
		String userId = branchUserLink.getUserId();
		String tokenKey = utility.generateLinkToken(clientUseId.getUserid(), branchUserLink.getUserId());
		Date tokenExpiry = utility.getLinkExpiryDate();
		Calendar cal = Calendar.getInstance();
		Date insertedDate = cal.getTime();
		branchUserDetails.setEmailId(clientUseId.getEmailAddress());
		branchUserDetails.setPasscodeValue(passcode);
		branchUserDetails.setToken(tokenKey);
		branchUserDetails.setInsertTime(insertedDate);
		branchUserDetails.setEmployeeId(clientUseId.getUserid());
		branchUserDetails.setEmployeeName(clientUseId.getFirstName());
		branchUserDetails.setExpryTime(tokenExpiry);
		branchUserDetails.setPasscodeCount(0);

		// userDao.saveBranchUser(branchUserDetails);
		brRepo.save(branchUserDetails);
		urltoken = tokenKey.concat("_").concat(passcode);
		link = subAccountActivationLink + urltoken;
		try {

			emailInsert.sendBranchUserPasscodeLink(link, branchUserLink, clientUseId, passcode);
			// return urltoken;
		} catch (Exception e) {
			if (e instanceof NullPointerException) {

				return errorMsg;
			}
		}
		return urltoken.concat("@").concat(String.valueOf(branchUserDetails.getBrId()));

	}

	public String sendPasscodeMethod(BranchUserRequest branchUserLink, NimaiClient clientUseId,
			GenericResponse response, NimaiMBranch branchUser) {
		NimaiMBranch branchUserDetails = brRepo.getOne(branchUser.getBrId());
		String link = "";
		String passcode = "";
		String bUlink = "";
		Date dnow = new Date();
		String urltoken = "";
		String errorMsg = "Email Sending failed";
		logger.debug("===============sendPasscodeMethod===========:");
		String token = utility.generatePasswordResetToken();
		Date expirydate = branchUser.getExpryTime();
		if (dnow.before(expirydate)) {
			String passcodeValue = utility.passcodeValue();
			// String passcodeValue = "1234";
			try {
				// userDao.updateBranchUserDetails(clientUseId.getEmailAddress(), dnow,
				// passcodeValue, token);
				branchUserDetails.setEmailId(clientUseId.getEmailAddress());
				branchUserDetails.setInsertTime(dnow);
				branchUserDetails.setPasscodeValue(passcodeValue);
				branchUserDetails.setToken(token);
				branchUserDetails.setBrId(branchUser.getBrId());
				brRepo.save(branchUserDetails);
			} catch (Exception e) {
				e.printStackTrace();
				response.setMessage("Email Sending failed");
				EmailErrorCode emailError = new EmailErrorCode("EmailNull", 409);
				response.setData(emailError);
				return errorMsg;
			}

			urltoken = token.concat("_").concat(passcodeValue);
			bUlink = subAccountActivationLink + urltoken;
			try {
				emailInsert.sendBranchUserPasscodeLink(bUlink, branchUserLink, clientUseId, passcodeValue);
				return urltoken;
			} catch (Exception e) {
				if (e instanceof NullPointerException) {
					response.setMessage("Email Sending failed");
					EmailErrorCode emailError = new EmailErrorCode("EmailNull", 409);
					response.setData(emailError);
					return errorMsg;
				}
			}
		} else {

			passcode = utility.passcodeValue();
			// passcode = "1234";
			String userId = branchUserLink.getUserId();
			String tokenKey = utility.generatePasswordResetToken();
			Date tokenExpiry = utility.getLinkExpiryDate();
//				Calendar cal = Calendar.getInstance();
//				Date insertedDate = cal.getTime();
			try {
//					userDao.updateBranchUser(passcode, tokenKey, dnow, clientUseId.getEmailAddress(),
//							branchUser.getBrId(), tokenExpiry);
				branchUserDetails.setEmailId(clientUseId.getEmailAddress());
				branchUserDetails.setInsertTime(dnow);
				branchUserDetails.setPasscodeValue(passcode);
				branchUserDetails.setToken(tokenKey);
				branchUserDetails.setExpryTime(tokenExpiry);
				branchUserDetails.setBrId(branchUser.getBrId());
				brRepo.save(branchUserDetails);

			} catch (Exception e) {
				e.printStackTrace();
				response.setMessage("Email Sending failed");
				EmailErrorCode emailError = new EmailErrorCode("EmailNull", 409);
				response.setData(emailError);
				return errorMsg;
			}

			urltoken = tokenKey.concat("_").concat(passcode);
			bUlink = subAccountActivationLink + urltoken;
			try {
				emailInsert.sendBranchUserPasscodeLink(bUlink, branchUserLink, clientUseId, passcode);
				return urltoken;
			} catch (Exception e) {
				if (e instanceof NullPointerException) {
					response.setMessage("Email Sending failed");
					EmailErrorCode emailError = new EmailErrorCode("EmailNull", 409);
					response.setData(emailError);
					return errorMsg;
				}
			}
		}

		return errorMsg;
	}

	@Override
	public ResponseEntity<?> reSendReferEmail(SubsidiaryBean registerLink) {
		logger.info("=========sendReferEmail method invoked======:" + registerLink.toString());
		GenericResponse response = new GenericResponse();
		String errorString = this.resetUserValidator.reSendValidation(registerLink);
		if (errorString.equalsIgnoreCase("success")) {
			try {
				NimaiClient clientUseId = userDao.getClientDetailsbyUserId(registerLink.getUserId());
				NimaiMRefer referDetails = userDao.getreferDetailsByUserDetails(clientUseId.getEmailAddress());
				if (clientUseId != null && referDetails != null) {
					String userId = clientUseId.getUserid();
					String tokenKey = userId.concat("_").concat(utility.generatePasswordResetToken());
					if (registerLink.getEvent().equalsIgnoreCase("ADD_REFER")) {

						String refertokenKey = ("RE")
								.concat(userId.concat("_").concat(utility.generatePasswordResetToken()));
						System.out.println(refertokenKey);
						NimaiMRefer referUser = new NimaiMRefer();
						String token = tokenKey.substring(tokenKey.indexOf("_") + 1);
						Calendar cal = Calendar.getInstance();
						Date insertedDate = cal.getTime();
						Date tokenExpiry = utility.getLinkExDate();
						userDao.updateReferTokenDetails(tokenExpiry, refertokenKey, clientUseId, insertedDate,
								clientUseId.getEmailAddress(), referDetails.getId());
						System.out.println("Refer User Id: " + referDetails.getId());
						userDao.updateReTokenLoginTable(refertokenKey, insertedDate, tokenExpiry,
								clientUseId.getUserid());
						response.setFlag(1);
						String referLink = referAccountActivationLink + refertokenKey;
						System.out.println("Activation link:::" + referLink);
						try {
							emailInsert.reSendReferAccAcivationLink(referLink, registerLink, clientUseId);

						} catch (Exception e) {
							e.printStackTrace();
							if (e instanceof NullPointerException) {
								response.setMessage("Email Sending failed");
								EmailErrorCode emailError = new EmailErrorCode("EmailNull", 409);
								response.setData(emailError);
								return new ResponseEntity<Object>(response, HttpStatus.CONFLICT);
							}
						}
					}

					response.setErrCode("ASA002");
					response.setMessage(ErrorDescription.getDescription("ASA002"));
					return new ResponseEntity<Object>(response, HttpStatus.OK);

				}

				else {
					response.setErrCode("ASA001");
					response.setMessage(ErrorDescription.getDescription("ASA001"));
					return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
				}

			} catch (Exception e) {
				e.printStackTrace();
				response.setErrCode("ASA001");
				response.setMessage(ErrorDescription.getDescription("ASA001"));
				return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
			}
		}
		response.setErrCode("ASA002");
		response.setMessage(ErrorDescription.getDescription("ASA002"));
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> restSuccessEmail(ResetPassBean resetBean) {
		GenericResponse response = new GenericResponse();

		logger.info("=======Inside restSuccessEmail method====" + resetBean.toString());

		if (resetBean.getEvent().equalsIgnoreCase("RESET_SUCCESS")) {

			NimaiClient clientUseId = userDao.getClientDetailsbyUserId(resetBean.getUserId());
			try {
				emailInsert.resetSuccessEmail(resetBean, clientUseId);
			} catch (Exception e) {
				e.printStackTrace();
				response.setErrCode("ASA011");
				response.setMessage(ErrorDescription.getDescription("ASA011"));
				return new ResponseEntity<Object>(response, HttpStatus.OK);
			}
		} else {
			response.setErrCode("ASA012");
			response.setMessage(ErrorDescription.getDescription("ASA012"));
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}

		response.setErrCode("ASA002");
		response.setMessage(ErrorDescription.getDescription("ASA002"));
		return new ResponseEntity<Object>(response, HttpStatus.OK);

	}

	@Override
	public boolean validateToken(String userId, String token) {
		// TODO Auto-generated method stub

		// NimaiToken nt = userDao.isTokenExists(userId, token);
		NimaiToken nt = tokenRepo.isTokenExists(userId, token);
		if (nt != null)
			return true;
		else
			return false;
	}

	@Override
	public ResponseEntity<?> sendBankDetails(BankDetailsBean bdBean) {
		// TODO Auto-generated method stub
		GenericResponse response = new GenericResponse();
		NimaiClient clientUseId = userDao.getClientDetailsbyUserId(bdBean.getUserId());
		logger.info("=======Inside sendBankDetails method====" + bdBean.toString());
		try {

			if (clientUseId != null) {

				try {

					emailInsert.BankDetailstoCustomer(bdBean, clientUseId);
				} catch (Exception e) {
					e.printStackTrace();
					response.setErrCode("ASA018");
					response.setMessage(ErrorDescription.getDescription("ASA018"));
					return new ResponseEntity<Object>(response, HttpStatus.OK);
				}

			} else {
				logger.info("=======Inside sendBankDetails method====" + bdBean.toString());
				response.setErrCode("ASA010");
				response.setMessage(ErrorDescription.getDescription("ASA010"));
				return new ResponseEntity<Object>(response, HttpStatus.OK);
			}
			if (bdBean.getWireTransfer().equalsIgnoreCase("wire_CLickHere")) {
				response.setErrCode("wire_ClickHere");
			} else {
				response.setErrCode("wire_SendRequest");
			}

			response.setMessage(ErrorDescription.getDescription("Email Send Succefully"));
			return new ResponseEntity<Object>(response, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			response.setErrCode("ASA011");
			response.setMessage(ErrorDescription.getDescription("ASA011"));
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}

	}

	@Override
	public void saveInvalidCaptchaFlag(BranchUserPassCodeBean passCodeBean, String flag) {
		// TODO Auto-generated method stub

		userDao.updateInvalidCaptcha(passCodeBean, flag);

	}

	@Override
	public String InvalidCaptchaStatus(BranchUserPassCodeBean passCodeBean) {
		// TODO Auto-generated method stub

		NimaiMBranch branchDetails = userDao.getbranchDetailsById(passCodeBean.getId());
		if (branchDetails != null) {
			if (branchDetails.getInvalidCaptcha() == null) {

				return "String null";
			} else if (branchDetails.getInvalidCaptcha().equalsIgnoreCase("true")) {

				return "true";
			} else {
				return "false";
			}
		}

		return "object null";
	}

	@Override
	public ResponseEntity<?> validateCaptcha(String userId) {
		// TODO Auto-generated method stubtry
		GenericResponse response = new GenericResponse();
		String tokenUserEmail = "";
		NimaiToken nt;
		try {
			if (userId.toString().substring(0, 2).equalsIgnoreCase("CU")
					|| userId.toString().substring(0, 2).equalsIgnoreCase("BC")
					|| userId.toString().substring(0, 2).equalsIgnoreCase("RE")) {
				NimaiClient client = userDao.getClientDetailsbyUserId(userId);
				tokenUserEmail = userId + "-" + client.getEmailAddress();
				nt = tokenRepo.getOne(tokenUserEmail);
			} else {
				nt = userDao.isTokenExists(userId);
				tokenUserEmail = userId;
				nt = tokenRepo.getOne(tokenUserEmail);
			}
			String captchFlag = nt.getIsInvalidCaptcha();
			if (captchFlag == null) {
				response.setMessage("Authorise Access");

			} else if (captchFlag.equalsIgnoreCase("false")) {
				response.setMessage("Authorise Access");

			} else if (captchFlag.equalsIgnoreCase("passcode_true")) {
				response.setMessage("Passcode does not match");
				String flag = "false";
				try {
					userDao.updateInvalCaptcha(tokenUserEmail, flag);
				} catch (Exception e) {
					e.printStackTrace();
					response.setErrCode("EX001");
					response.setMessage("Exception while updating flag");
				}
			} else if (captchFlag.equalsIgnoreCase("token_true")) {
				response.setMessage("Token not found");
				String flag = "false";
				try {
					userDao.updateInvalCaptcha(tokenUserEmail, flag);
				} catch (Exception e) {
					e.printStackTrace();
					response.setErrCode("EX001");
					response.setMessage("Exception while updating flag");
				}
			} else if (captchFlag.equalsIgnoreCase("attempt_true")) {
				response.setMessage("Attempt exceedded");
				String flag = "false";
				try {
					userDao.updateInvalCaptcha(tokenUserEmail, flag);
				} catch (Exception e) {
					e.printStackTrace();
					response.setErrCode("EX001");
					response.setMessage("Exception while updating flag");
				}
			} else if (captchFlag.equalsIgnoreCase("true")) {
				response.setErrCode("EX001");
				String flag = "false";
				try {
					userDao.updateInvalCaptcha(tokenUserEmail, flag);
				} catch (Exception e) {
					e.printStackTrace();
					response.setErrCode("EX001");
					response.setMessage("Exception while updating flag");
				}
				response.setMessage("Unauthorise Access");
			}
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			response.setErrCode("EX001");
			response.setMessage("Unauthorise Access");
			return new ResponseEntity<Object>(response, HttpStatus.OK);

		}

	}

}
