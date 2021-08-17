package com.nimai.email.service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManagerFactory;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

import org.springframework.transaction.annotation.Propagation;
//import javax.transaction.Transactional;
import org.springframework.transaction.annotation.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.nimai.email.api.GenericResponse;
import com.nimai.email.bean.AlertToBanksBean;
import com.nimai.email.bean.EligibleEmailBeanResponse;
import com.nimai.email.bean.EligibleEmailList;
import com.nimai.email.bean.EmailSendingDetails;
import com.nimai.email.bean.QuotationAlertRequest;
import com.nimai.email.controller.BanksAlertEmailController;
import com.nimai.email.dao.BanksAlertDao;
import com.nimai.email.entity.NimaiClient;
import com.nimai.email.entity.NimaiEmailSchedulerAlertToBanks;
import com.nimai.email.entity.NimaiLC;
import com.nimai.email.entity.NimaiMBranch;
import com.nimai.email.entity.QuotationMaster;
import com.nimai.email.entity.TransactionSaving;
import com.nimai.email.utility.AppConstants;
import com.nimai.email.utility.EmaiInsert;
import com.nimai.email.utility.EmailErrorCode;
import com.nimai.email.utility.ErrorDescription;
import com.nimai.email.utility.ModelMapper;
import com.nimai.email.utility.ResetUserValidation;

@Service
@Transactional
public class BanksAlertEmailServiceImpl implements BanksALertEmailService {

	private static Logger logger = LoggerFactory.getLogger(BanksAlertEmailController.class);

	@Autowired
	BanksAlertDao userDao;

	@Autowired
	private EmaiInsert emailInsert;

	@Autowired
	EntityManagerFactory em;

	@Autowired
	ResetUserValidation resetUserValidator;

	// @Scheduled(fixedDelay = 50000)
	public void setTransactionEmailInSchTable() {
		logger.info("============Inside scheduler method of setTransactionEmailInSchTable==============");
		/* query to fetch the list of data from nimaiEmailAlertsTobankSchedulerTablw */
		List<NimaiEmailSchedulerAlertToBanks> emailDetailsScheduled = userDao.getTransactionDetailByTrEmailStatus();
		Iterator itr = emailDetailsScheduled.iterator();
		while (itr.hasNext()) {
			NimaiEmailSchedulerAlertToBanks schdulerData = (NimaiEmailSchedulerAlertToBanks) itr.next();
			if (schdulerData.getTransactionEmailStatusToBanks().equalsIgnoreCase("pending")
					&& schdulerData.getCustomerid().substring(0, 2).equalsIgnoreCase("CU")
					&& schdulerData.getEmailEvent().equalsIgnoreCase("LC_UPLOAD(DATA)")) {
				try {
					StoredProcedureQuery getBAnksEmail = em.createEntityManager()
							.createStoredProcedureQuery("get_eligible_banks", NimaiClient.class);
					getBAnksEmail.registerStoredProcedureParameter("inp_customer_userID", String.class,
							ParameterMode.IN);
					getBAnksEmail.registerStoredProcedureParameter("inp_transaction_ID", String.class,
							ParameterMode.IN);

					getBAnksEmail.setParameter("inp_customer_userID", schdulerData.getCustomerid());
					getBAnksEmail.setParameter("inp_transaction_ID", schdulerData.getTransactionid());
					getBAnksEmail.execute();
					ModelMapper modelMapper = new ModelMapper();
					List<NimaiClient> nimaiCust = getBAnksEmail.getResultList();
					EligibleEmailBeanResponse responseBean = new EligibleEmailBeanResponse();
					List<EligibleEmailList> emailIdList = new ArrayList<EligibleEmailList>();

					List<EligibleEmailList> emailId = nimaiCust.stream().map(obj -> {
						EligibleEmailList data = new EligibleEmailList();
						NimaiEmailSchedulerAlertToBanks schedulerEntity = new NimaiEmailSchedulerAlertToBanks();
						Calendar cal = Calendar.getInstance();
						Date insertedDate = cal.getTime();
						schedulerEntity.setInsertedDate(insertedDate);
						schedulerEntity.setCustomerid(schdulerData.getCustomerid());
						schedulerEntity.setTransactionid(schdulerData.getTransactionid());
						schedulerEntity.setEmailEvent("LC_UPLOAD_ALERT_ToBanks");
						schedulerEntity.setBanksEmailID(obj.getEmailAddress());
						schedulerEntity.setBankUserid(obj.getUserid());
						schedulerEntity.setBankUserName(obj.getFirstName());
						int i = schdulerData.getScedulerid();
						String trScheduledId = Integer.toString(i);
						schedulerEntity.setTrScheduledId(trScheduledId);
						schedulerEntity.setEmailFlag("pending");
						// Bank user id transactionEmaiStatus is pending
						schedulerEntity.setTransactionEmailStatusToBanks("pending");
						userDao.saveSchdulerData(schedulerEntity);
						data.setEmailList(obj.getEmailAddress());
						return data;
					}).collect(Collectors.toList());

					// customer id transactioStatus is set as "In-process" to avoid duplicate
					// entry of matching banks while iterating data
					int scedulerid = schdulerData.getScedulerid();
					userDao.updateTREmailStatus(scedulerid);

					logger.info(
							"Customer critria(minLc,blGoods,country) matching banks are not available to send the transaction upload alert");

				} catch (Exception e) {
					logger.info(
							"Customer critria(minLc,blGoods,country) matching banks are not available to send the customer transaction upload alert");

				}
			}
		}
	}

	@Override
//	@Scheduled(fixedDelay = 50000)
//	@Transactional(propagation = Propagation.NESTED)
	public void sendTransactionStatusToBanksByScheduled() {

		// TODO Auto-generated method stub
		logger.info("=====InsidesendTransactionStatusToBanksByScheduled method========= ");
		GenericResponse response = new GenericResponse<>();
		String emailStatus = "";
		/* query to fetch the list of data from nimaiEmailAlertsTobankSchedulerTablw */
		List<NimaiEmailSchedulerAlertToBanks> emailDetailsScheduled = userDao.getTransactionDetail();

		for (NimaiEmailSchedulerAlertToBanks schdulerData : emailDetailsScheduled) {

			if (schdulerData.getEmailEvent().equalsIgnoreCase(AppConstants.QUOTE_ACCEPT)
					|| schdulerData.getEmailEvent().equalsIgnoreCase(AppConstants.QUOTE_REJECTION)) {
				logger.info("============Inside QUOTE_ACCEPT & QUOTE_REJECTION condition==========");
				try {
					NimaiLC custTransactionDetails = userDao
							.getTransactioDetailsByTransId(schdulerData.getTransactionid());
					logger.info("============Inside QUOTE_ACCEPT & QUOTE_REJECTION condition=========="
							+ custTransactionDetails.toString());
					QuotationMaster bnakQuotationDetails = userDao.getDetailsByQuoteId(schdulerData.getQuotationId());

					logger.info("============Inside QUOTE_ACCEPT & QUOTE_REJECTION condition=========="
							+ bnakQuotationDetails.toString());
					NimaiClient customerDetails = userDao.getCustDetailsByUserId(schdulerData.getCustomerid());
					logger.info("============Inside QUOTE_ACCEPT & QUOTE_REJECTION condition=========="
							+ customerDetails.toString());
					NimaiClient bankDetails = userDao.getCustDetailsByUserId(schdulerData.getBankUserid());
					logger.info("============Inside QUOTE_ACCEPT & QUOTE_REJECTION condition=========="
							+ bankDetails.toString());
					if (custTransactionDetails != null && bnakQuotationDetails != null) {

						// to get details of customer Details and customerusrid from nimai_m_quotation
						// as userId
						/* method for email sending to banks */
						emailInsert.sendQuotationStatusEmail(schdulerData.getEmailEvent(), schdulerData,
								schdulerData.getBanksEmailID(), custTransactionDetails, bnakQuotationDetails,
								bankDetails, customerDetails);
						try {
							logger.info(
									"============Inside QUOTE_ACCEPT & QUOTE_REJECTION condition schedulerId:=========="
											+ schdulerData.getScedulerid());
							userDao.updateEmailFlag(schdulerData.getScedulerid());
						} catch (Exception e) {
							e.printStackTrace();
							emailStatus = AppConstants.EMAILSTATUS;
							userDao.updateInvalidIdEmailFlag(schdulerData.getScedulerid(), emailStatus);
							continue;
						}

					} else {

						logger.info("=====Inside QUOTE_ACCEPT or QUOTE_REJECTION quotation id not found======");
						emailStatus = AppConstants.Quote_Id_NOT_Register;
						try {
							userDao.updateInvalidIdEmailFlag(schdulerData.getScedulerid(), emailStatus);
						} catch (Exception e) {
							e.printStackTrace();
							logger.info(
									"=======Inside QUOTE_ACCEPT & QUOTE_REJECTION condition in updateInvalidflag method======");
							continue;
						}

					}

				} catch (Exception e) {
					if (e instanceof NullPointerException) {
						response.setMessage("Email Sending failed");
						EmailErrorCode emailError = new EmailErrorCode("EmailNull", 409);
						logger.info(
								"============email sending failed sendQuotationStatusEmail method of QUOTE_ACCEPT & QUOTE_REJECTION condition catch block========");
						continue;
					}
				}

			} else if (schdulerData.getEmailEvent().equalsIgnoreCase("QUOTE_ACCEPT_CUSTOMER")
					|| schdulerData.getEmailEvent().equalsIgnoreCase("QUOTE_REJECTION_CUSTOMER")) {
				logger.info("============Inside QUOTE_ACCEPT_CUSTOMER & QUOTE_REJECTION_CUSTOMER condition==========");
				try {
					NimaiLC custTransactionDetails = userDao
							.getTransactioDetailsByTransId(schdulerData.getTransactionid());
					logger.info("============Inside QUOTE_ACCEPT & QUOTE_REJECTION condition=========="
							+ custTransactionDetails.toString());
					QuotationMaster bnakQuotationDetails = userDao.getDetailsByQuoteId(schdulerData.getQuotationId());

					logger.info("============Inside QUOTE_ACCEPT & QUOTE_REJECTION condition=========="
							+ bnakQuotationDetails.toString());
					NimaiClient customerDetails = userDao.getCustDetailsByUserId(schdulerData.getCustomerid());
					logger.info("============Inside QUOTE_ACCEPT & QUOTE_REJECTION condition=========="
							+ customerDetails.toString());
					NimaiClient bankDetails = userDao.getCustDetailsByUserId(schdulerData.getBankUserid());
					logger.info("============Inside QUOTE_ACCEPT & QUOTE_REJECTION condition=========="
							+ bankDetails.toString());

					if (custTransactionDetails != null && bnakQuotationDetails != null) {

						try {
							/* method for sending the email to customer */
							logger.info("##########################method for sending the email to customer"
									+ schdulerData.getQuotationId() + "########################");
							QuotationMaster bankQuotationDetails = userDao
									.getDetailsByQuoteId(schdulerData.getQuotationId());

							if (bankQuotationDetails != null) {
								String savingsDetails = "";
								TransactionSaving trSavingDetails = userDao
										.getSavingDetails(schdulerData.getTransactionid());

								if (trSavingDetails == null) {
									savingsDetails = "0";
								} else {
									savingsDetails = String.valueOf(trSavingDetails.getSavings());
								}
								logger.info(
										"============Inside QUOTE_ACCEPT & QUOTE_REJECTION condition savings:=========="
												+ bankDetails.toString());

								NimaiClient custDetails = userDao
										.getcuDetailsByEmail(custTransactionDetails.getBranchUserEmail());
								// if branch userEmail consist parent user email or passcode userEmail
								if (custDetails == null) {
									String passcodeUserEmail = custTransactionDetails.getBranchUserEmail();
									NimaiMBranch branchDetails = userDao.getBrDetailsByEmail(passcodeUserEmail);

									emailInsert.sendQuotationStatusEmailToPassCodeCust(schdulerData.getEmailEvent(),
											schdulerData, passcodeUserEmail, bankQuotationDetails,
											custTransactionDetails, bankDetails, savingsDetails,branchDetails);

								} else {
									emailInsert.sendQuotationStatusEmailToCust(schdulerData.getEmailEvent(),
											schdulerData, schdulerData.getCustomerEmail(), bankQuotationDetails,
											custTransactionDetails, bankDetails, savingsDetails);
								}

								try {
									logger.info(
											"============Inside QUOTE_ACCEPT & QUOTE_REJECTION condition schedulerId:=========="
													+ schdulerData.getScedulerid());
									userDao.updateEmailFlag(schdulerData.getScedulerid());
								} catch (Exception e) {
									e.printStackTrace();
									continue;
								}
								// TODO: handle exception

							} else {
								logger.info("Inside sendTransactionStatusToBanksByScheduled quotation id not found");
								try {
									emailStatus = "Quote_Id_NOT_Register";
									userDao.updateInvalidIdEmailFlag(schdulerData.getScedulerid(), emailStatus);
									response.setMessage("Details not found");
								} catch (Exception e) {
									e.printStackTrace();
									continue;
								}

							}
						} catch (Exception e) {
							if (e instanceof NullPointerException) {
								response.setMessage("Email Sending failed");
								EmailErrorCode emailError = new EmailErrorCode("EmailNull", 409);

								continue;
							}
						}

						logger.info(
								"============Inside QUOTE_ACCEPT & QUOTE_REJECTION condition schdulerData.getScedulerid():=========="
										+ schdulerData.getScedulerid());
					} else {

						logger.info("=====Inside QUOTE_ACCEPT or QUOTE_REJECTION quotation id not found======");
						emailStatus = AppConstants.Quote_Id_NOT_Register;
						try {
							userDao.updateInvalidIdEmailFlag(schdulerData.getScedulerid(), emailStatus);
						} catch (Exception e) {
							e.printStackTrace();
							logger.info(
									"=======Inside QUOTE_ACCEPT & QUOTE_REJECTION condition in updateInvalidflag method======");
							continue;
						}

					}
				} catch (Exception e) {
					if (e instanceof NullPointerException) {
						response.setMessage("Email Sending failed");
						EmailErrorCode emailError = new EmailErrorCode("EmailNull", 409);
						logger.info(
								"============email sending failed sendQuotationStatusEmail method of QUOTE_ACCEPT & QUOTE_REJECTION condition catch block========");
						continue;
					}
				}
			} else if (schdulerData.getEmailEvent().equalsIgnoreCase("Winning_Quote_Data")) {

				NimaiLC custTransactionDetails = userDao.getTransactioDetailsByTransId(schdulerData.getTransactionid());
				logger.info(
						"=====Inside Winning_Quote_Data transaction id not found" + schdulerData.getTransactionid());
				System.out.println("===========Winning_Quote_Data trId:" + schdulerData.getTransactionid());
				if (custTransactionDetails != null) {
					List<QuotationMaster> rejectedBankDetails = userDao
							.getBankQuoteList(schdulerData.getTransactionid());
					System.out.println("reljected details" + rejectedBankDetails.toString());
					NimaiClient customerDetails = userDao.getCustDetailsByUserId(schdulerData.getCustomerid());
					for (QuotationMaster details : rejectedBankDetails) {
						System.out.println("particular data" + details.toString());
						NimaiEmailSchedulerAlertToBanks rejectBankDetails = new NimaiEmailSchedulerAlertToBanks();
						// NimaiEmailSchedulerAlertToBanks schdata=new
						// NimaiEmailSchedulerAlertToBanks();
						NimaiClient bankDetails = userDao.getCustDetailsByUserId(details.getBankUserId());
						rejectBankDetails.setBankUserName(bankDetails.getFirstName());
						rejectBankDetails.setTransactionid(schdulerData.getTransactionid());
						rejectBankDetails.setCustomerCompanyName(customerDetails.getCompanyName());
						rejectBankDetails.setAmount(String.valueOf(custTransactionDetails.getlCValue()));
						rejectBankDetails.setCurrency(custTransactionDetails.getlCCurrency());
						rejectBankDetails.setBanksEmailID(bankDetails.getEmailAddress());
						rejectBankDetails.setEmailEvent("Winning_Quote_Alert_toBanks");
						rejectBankDetails.setEmailFlag("Pending");
						userDao.saveBankSchData(rejectBankDetails);
					}

					logger.info(
							"============Inside Winning_Quote_Data & Winning_Quote_Data condition schdulerData.getScedulerid():=========="
									+ schdulerData.getScedulerid());
					try {
						userDao.updateEmailFlag(schdulerData.getScedulerid());
					} catch (Exception e) {
						e.printStackTrace();
						continue;
					}

				}

				else {
					logger.info("Inside Winning_Quote_Data transaction id not found");
					response.setMessage("Details not found");
					emailStatus = "Tr_Id_NOT_Register";
					try {
						userDao.updateInvalidIdEmailFlag(schdulerData.getScedulerid(), emailStatus);
					} catch (Exception e) {
						e.printStackTrace();
						continue;
					}

				}

			}

			else if (schdulerData.getEmailEvent().equalsIgnoreCase("Winning_Quote_Alert_toBanks")) {
				try {
					emailInsert.sendWinningQuoteToAlertBank(schdulerData.getEmailEvent(), schdulerData);

					logger.info(
							"============Inside Winning_Quote_Alert_toBanks condition schdulerData.getScedulerid():=========="
									+ schdulerData.getScedulerid());
					userDao.updateEmailFlag(schdulerData.getScedulerid());
					try {
						userDao.updateEmailFlag(schdulerData.getScedulerid());
					} catch (Exception e) {
						e.printStackTrace();
						continue;
					}
				} catch (Exception e) {
					e.printStackTrace();
					logger.info(
							"============Inside catch block Winning_Quote_Alert_toBanks condition schdulerData.getScedulerid():=========="
									+ schdulerData.getScedulerid());
					continue;
				}
			}

			else if (schdulerData.getEmailEvent().equalsIgnoreCase("LC_REOPENING_ALERT_ToBanks")) {
				try {
					NimaiLC custTransactionDetails = userDao
							.getTransactioDetailsByTransId(schdulerData.getTransactionid());
					QuotationMaster bnakQuotationDetails = userDao.getDetailsByQuoteId(schdulerData.getQuotationId());
					NimaiClient customerDetails = userDao.getCustDetailsByUserId(schdulerData.getCustomerid());
					logger.info("================LC_REOPENING_ALERT_ToBanks Customer:" + customerDetails.getUserid());
					NimaiClient bankDetails = userDao.getCustDetailsByUserId(schdulerData.getBankUserid());
					logger.info("================LC_REOPENING_ALERT_ToBanks Bank:" + bankDetails.getUserid());

					if (custTransactionDetails != null && bnakQuotationDetails != null && customerDetails != null) {
						// to get details of customer Details and customerusrid from nimai_m_quotation
						// as userId
						/* method for email sending to banks */
						emailInsert.sendLcReopeningToAlertBank(schdulerData.getEmailEvent(), schdulerData,
								schdulerData.getBanksEmailID(), custTransactionDetails, bnakQuotationDetails,
								bankDetails, customerDetails);
					} else {
						logger.info("Inside LC_REOPENING_ALERT_ToBanks quotation id not found");
						response.setMessage("Details not found");
						emailStatus = "Quote_Id_NOT_Register";
						try {
							userDao.updateInvalidIdEmailFlag(schdulerData.getScedulerid(), emailStatus);
						} catch (Exception e) {
							e.printStackTrace();
							continue;
						}

					}

					logger.info(
							"============Inside LC_REOPENING_ALERT_ToBanks Customer condition schdulerData.getScedulerid():=========="
									+ schdulerData.getScedulerid());
					try {
						userDao.updateEmailFlag(schdulerData.getScedulerid());
					} catch (Exception e) {
						e.printStackTrace();
						continue;
					}

				} catch (Exception e) {
					if (e instanceof NullPointerException) {
						response.setMessage("Email Sending failed");
						EmailErrorCode emailError = new EmailErrorCode("EmailNull", 409);
						continue;
					}
				}
			}

			else if (schdulerData.getEmailEvent().equalsIgnoreCase("Bank_Details_tocustomer")) {
				logger.info("============Inside Bank_Details_tocustomer condition==========");

				QuotationMaster bnakQuotationDetails = userDao.getDetailsByQuoteId(schdulerData.getQuotationId());
				if (bnakQuotationDetails != null) {
					try {

						emailInsert.sendBankDetailstoCustomer(schdulerData.getEmailEvent(), schdulerData,
								schdulerData.getCustomerEmail(), bnakQuotationDetails);

						logger.info(
								"============Inside Bank_Details_tocustomer Customer condition schdulerData.getScedulerid():=========="
										+ schdulerData.getScedulerid());
						try {
							userDao.updateEmailFlag(schdulerData.getScedulerid());

						} catch (Exception e) {
							e.printStackTrace();
							continue;
						}

					}

					catch (Exception e) {
						if (e instanceof NullPointerException) {
							response.setMessage("Email Sending failed");
							EmailErrorCode emailError = new EmailErrorCode("EmailNull", 409);
							continue;
						}

					}

				} else {
					logger.info("Inside sendTransactionStatusToBanksByScheduled quotation id not found");
					response.setMessage("Details not found");
					emailStatus = "Quote_Id_NOT_Register";
					try {
						userDao.updateInvalidIdEmailFlag(schdulerData.getScedulerid(), emailStatus);
					} catch (Exception e) {
						e.printStackTrace();
						continue;
					}

				}
			} else if (schdulerData.getEmailEvent().equalsIgnoreCase("QUOTE_PLACE_ALERT_ToBanks")) {
				logger.info("============Inside QUOTE_PLACE_ALERT_ToBanks condition==========");
				logger.info("@@@@@@@@@@@@@@@@##########################Quotatio Id:-" + schdulerData.getQuotationId()
						+ "@@@@@@@@@@@@@@@@@@@@########################");
				QuotationMaster bnakQuotationDetails = userDao.getDetailsByQuoteId(schdulerData.getQuotationId());
				if (bnakQuotationDetails != null) {
					try {
						NimaiLC custTransactionDetails = userDao
								.getTransactioDetailsByTransId(schdulerData.getTransactionid());
						// to get details of customer Details and customerusrid from nimai_m_quotation
						// as userId
						NimaiClient customerDetails = userDao.getCustDetailsByUserId(bnakQuotationDetails.getUserId());

						emailInsert.sendQuotePlaceEmailToBanks(schdulerData.getEmailEvent(), schdulerData,
								schdulerData.getBanksEmailID(), bnakQuotationDetails, customerDetails,
								custTransactionDetails);

//						int scedulerid = schdulerData.getScedulerid();

						logger.info(
								"============Inside QUOTE_PLACE_ALERT_ToBanks Customer condition schdulerData.getScedulerid():=========="
										+ schdulerData.getScedulerid());
						try {
							userDao.updateEmailFlag(schdulerData.getScedulerid());
						} catch (Exception e) {
							e.printStackTrace();
							logger.info(
									"============Inside QUOTE_PLACE_ALERT_ToBanks Customer condition schdulerData.getScedulerid():=========="
											+ schdulerData.getScedulerid());
							continue;
						}

					} catch (Exception e) {
						if (e instanceof NullPointerException) {
							response.setMessage("Email Sending failed");
							EmailErrorCode emailError = new EmailErrorCode("EmailNull", 409);
							response.setData(emailError);
							continue;
						}
					}

				} else {
					logger.info("Inside sendTransactionStatusToBanksByScheduled quotation id not found");
					emailStatus = "Quote_Id_NOT_Register";
					try {
						userDao.updateInvalidIdEmailFlag(schdulerData.getScedulerid(), emailStatus);
					} catch (Exception e) {
						e.printStackTrace();
						continue;
					}

				}
			} else if (schdulerData.getEmailEvent().equalsIgnoreCase("BId_ALERT_ToCustomer")) {
				try {
					/*
					 * method for sending the email to customer tht he received one bid after bank
					 * place a quote against any transactionId
					 */
					// NimaiLCMaster
					// passcodeDetails=lcmasterrepo.findSpecificTransactionById(transactionId);
					String event = "BId_ALERT_ToCustomer";
					NimaiLC custTransactionDetails = userDao
							.getTransactioDetailsByTransId(schdulerData.getTransactionid());
					NimaiClient custDetails = userDao.getcuDetailsByEmail(custTransactionDetails.getBranchUserEmail());
					// if branch userEmail consist parent user email or passcode userEmail
					if (custDetails == null) {
						String passcodeUserEmail = custTransactionDetails.getBranchUserEmail();
						NimaiMBranch branchDetails = userDao.getBrDetailsByEmail(passcodeUserEmail);
						emailInsert.sendBidRecivedEmailToPassCodeCust(event, schdulerData, passcodeUserEmail,
								branchDetails);

					} else {
						emailInsert.sendBidRecivedEmailToCust(event, schdulerData, schdulerData.getCustomerEmail());
					}

					// int scedulerid = schdulerData.getScedulerid();
					logger.info(
							"============Inside BId_ALERT_ToCustomer Customer condition schdulerData.getScedulerid():=========="
									+ schdulerData.getScedulerid());
					try {
						userDao.updateEmailFlag(schdulerData.getScedulerid());
					} catch (Exception e) {
						e.printStackTrace();
						continue;
					}

				} catch (Exception e) {
					if (e instanceof NullPointerException) {
						e.printStackTrace();
						response.setMessage("Email Sending failed");
						EmailErrorCode emailError = new EmailErrorCode("EmailNull", 409);
						continue;
					}
				}
			} else if (schdulerData.getEmailEvent().equalsIgnoreCase("LC_UPLOAD(DATA)")
					|| schdulerData.getEmailEvent().equalsIgnoreCase("LC_UPDATE(DATA)")) {

				logger.info("============Inside LC_UPLOAD(DATA) & LC_UPLOAD(DATA) condition=========="
						+ schdulerData.getTransactionid());

				NimaiLC custTransactionDetails = userDao.getTransactioDetailsByTransId(schdulerData.getTransactionid());
				NimaiClient custDetails = userDao.getCustDetailsByUserId(schdulerData.getCustomerid());
				try {
					if (custTransactionDetails != null && custDetails != null) {

						if (schdulerData.getPasscodeuserEmail() == null
								|| schdulerData.getPasscodeuserEmail().isEmpty()) {
							emailInsert.sendLcStatusEmailData(schdulerData, custTransactionDetails, custDetails);
						} else {
							NimaiMBranch branchDetails = userDao
									.getBrDetailsByEmail(schdulerData.getPasscodeuserEmail());
							emailInsert.sendLcStatusEmailDataToPaUser(schdulerData, custTransactionDetails, custDetails,
									branchDetails);
						}

						logger.info(
								"============Inside LC_UPLOAD Customer condition schdulerData.getScedulerid():=========="
										+ schdulerData.getScedulerid());
						try {
							userDao.updateEmailFlag(schdulerData.getScedulerid());
						} catch (Exception e) {
							logger.info(
									"============Inside LC_UPLOAD Customer condition schdulerData.getScedulerid():=========="
											+ schdulerData.getScedulerid());
							e.printStackTrace();
							continue;
						}

					} else {
						logger.info("Inside sendTransactionStatusToBanksByScheduled transaction id not found");
						emailStatus = "Tr_Id_Not_Register";
						userDao.updateInvalidIdEmailFlag(schdulerData.getScedulerid(), emailStatus);

					}

				} catch (Exception e) {
					if (e instanceof NullPointerException) {
						e.printStackTrace();
						response.setMessage("Email Sending failed");
						EmailErrorCode emailError = new EmailErrorCode("EmailNull", 409);
						continue;
					}
				}
			} else if (schdulerData.getEmailEvent().equalsIgnoreCase("LC_UPLOAD_ALERT_ToBanks")
					|| schdulerData.getEmailEvent().equalsIgnoreCase("LC_UPDATE_ALERT_ToBanks")) {

				logger.info("============Inside LC_UPLOAD_ALERT_ToBanks & LC_UPDATE_ALERT_ToBankscondition=========="
						+ schdulerData.getTransactionid());

				NimaiLC custTransactionDetails = userDao.getTransactioDetailsByTransId(schdulerData.getTransactionid());
				NimaiClient custDetails = userDao.getCustDetailsByUserId(schdulerData.getCustomerid());
				if (custTransactionDetails != null && custDetails != null) {
					if (schdulerData.getTransactionEmailStatusToBanks() == null) {
						try {
							emailInsert.sendTransactionStatusToBanks(schdulerData, custTransactionDetails, custDetails);

							logger.info(
									"============Inside LC_UPDATE_ALERT_ToBankscondition Customer condition schdulerData.getScedulerid():=========="
											+ schdulerData.getScedulerid());
							try {
								userDao.updateEmailFlag(schdulerData.getScedulerid());
							} catch (Exception e) {
								e.printStackTrace();
								continue;
							}

						} catch (Exception e) {
							if (e instanceof NullPointerException) {
								e.printStackTrace();
								response.setMessage("Email Sending failed");
								EmailErrorCode emailError = new EmailErrorCode("EmailNull", 409);
								continue;
							}
						}
					} else {
						try {
							emailInsert.sendTransactionStatusToBanks(schdulerData, custTransactionDetails, custDetails);
							// this scheduler id updating emailTRStatus flag from pending to sent
//							
							try {
								userDao.updateTrStatusEmailFlag(Integer.parseInt(schdulerData.getTrScheduledId()));
								logger.info(
										"============Inside LC_UPDATE_ALERT_ToBankscondition Customer condition schdulerData.getScedulerid():=========="
												+ schdulerData.getScedulerid());
							} catch (Exception e) {
								e.printStackTrace();
								continue;
							}

							try {
								// this scheduler id updating bank email status flag from pending to sent
								userDao.updateBankEmailFlag(schdulerData.getScedulerid());
							} catch (Exception e) {
								e.printStackTrace();
								continue;
							}

						} catch (Exception e) {
							if (e instanceof NullPointerException) {
								response.setMessage("Email Sending failed");
								EmailErrorCode emailError = new EmailErrorCode("EmailNull", 409);
								continue;
							}
						}
					}

				} else {
					logger.info("Inside sendTransactionStatusToBanksByScheduled transaction id not found");
					emailStatus = "Tr_Id_not_Register";
					try {
						logger.info(
								"============Inside LC_UPDATE_ALERT_ToBankscondition Customer condition transaction id not found schdulerData.getScedulerid():=========="
										+ schdulerData.getScedulerid());

						userDao.updateInvalidIdEmailFlag(schdulerData.getScedulerid(), emailStatus);
					} catch (Exception e) {
						e.printStackTrace();
						continue;
					}

				}
				InetAddress ip;
				try {

					ip = InetAddress.getLocalHost();
					System.out.println("=============================Current IP address========== : " + ip.getHostAddress());

				} catch (UnknownHostException e) {

					e.printStackTrace();
					continue;
				}
			}
			
		}

	}

	@Override
	public ResponseEntity<?> sendTransactionStatusToBanks(AlertToBanksBean alertBanksBean) {
		logger.info("=======sendTransactionStatusToBanks method invoked=======");
		GenericResponse response = new GenericResponse<>();
		List<EmailSendingDetails> emailList = alertBanksBean.getBankEmails();
		for (EmailSendingDetails emailIds : emailList) {
			String errorString = this.resetUserValidator.banksEmailValidation(alertBanksBean, emailIds.getEmailId());
			if (errorString.equalsIgnoreCase("Success")) {
				try {
					emailInsert.sendTransactionStatusToBanks(alertBanksBean.getEvent(), alertBanksBean,
							emailIds.getEmailId());
				} catch (Exception e) {
					if (e instanceof NullPointerException) {
						response.setMessage("Email Sending failed");
						EmailErrorCode emailError = new EmailErrorCode("EmailNull", 409);
						response.setData(emailError);
						return new ResponseEntity<Object>(response, HttpStatus.CONFLICT);
					}
				}
			} else {
				response.setErrCode("EX000");
				response.setMessage(ErrorDescription.getDescription("EX000") + errorString.toString());
				return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
			}
		}
		response.setMessage(ErrorDescription.getDescription("ASA002"));
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> sendQuotationStatusToBanks(QuotationAlertRequest quotationReq) {
		logger.info("==========sendQuotationStatusToBanks method invoked=========");
		GenericResponse response = new GenericResponse<>();
		String errorString = this.resetUserValidator.quotationAlertValidation(quotationReq);
		if (errorString.equalsIgnoreCase("Success")) {

			try {
				emailInsert.sendQuotationStatusEmail(quotationReq.getEvent(), quotationReq,
						quotationReq.getBankEmailId());
				response.setMessage(ErrorDescription.getDescription("ASA002"));
				return new ResponseEntity<Object>(response, HttpStatus.OK);
			} catch (Exception e) {
				if (e instanceof NullPointerException) {
					response.setMessage("Email Sending failed");
					EmailErrorCode emailError = new EmailErrorCode("EmailNull", 409);
					response.setData(emailError);
					return new ResponseEntity<Object>(response, HttpStatus.CONFLICT);
				}
			}

		} else {
			response.setErrCode("EX000");
			response.setMessage(ErrorDescription.getDescription("EX000") + errorString.toString());
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

}
