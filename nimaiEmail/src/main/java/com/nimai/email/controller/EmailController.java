
package com.nimai.email.controller;

import java.io.BufferedInputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.io.codec.Base64.OutputStream;
import com.itextpdf.text.DocumentException;
import com.nimai.email.api.GenericResponse;
import com.nimai.email.bean.AdminBean;
import com.nimai.email.bean.BankDetailsBean;
import com.nimai.email.bean.BranchUserPassCodeBean;
import com.nimai.email.bean.BranchUserRequest;
import com.nimai.email.bean.InvoiceBeanResponse;
import com.nimai.email.bean.KycEmailRequest;
import com.nimai.email.bean.LcUploadBean;
import com.nimai.email.bean.PdfBean;
import com.nimai.email.bean.ResetPassBean;
import com.nimai.email.bean.SubsidiaryBean;
import com.nimai.email.bean.UserRegistrationBean;
import com.nimai.email.dao.UserServiceDao;
import com.nimai.email.entity.EmailComponentMaster;
import com.nimai.email.entity.NimaiClient;
import com.nimai.email.entity.NimaiMBranch;
import com.nimai.email.entity.NimaiSubscriptionDetails;
import com.nimai.email.entity.NimaiToken;
import com.nimai.email.repository.NimaiTokenRepository;
import com.nimai.email.repository.SubscriptionDetailsRepository;
import com.nimai.email.repository.nimaiSystemConfigRepository;
import com.nimai.email.service.CaptchService;
import com.nimai.email.service.UserService;
import com.nimai.email.utility.AppConstants;
import com.nimai.email.utility.EmaiInsert;
import com.nimai.email.utility.ErrorDescription;
import com.nimai.email.utility.FileAttachment;
import com.nimai.email.utility.Utils;


/**
 * @author Dhiraj
 *
 */
/**
 * @param branchUserLink
 * @return
 */
@RestController
public class EmailController {

	private static Logger logger = LoggerFactory.getLogger(EmailController.class);
	@Autowired
	EmaiInsert emailInsert;

	@Autowired
	UserServiceDao userDao;

	@Autowired
	UserService userEmailService;

	@Autowired
	CaptchService captchaService;

	@Autowired
	NimaiTokenRepository tokenRepo;

	@Autowired
	private Utils utility;

	@Autowired
	GenericResponse response;
	
	@Autowired
	SubscriptionDetailsRepository sPlanRepo;
	
	@Autowired
	nimaiSystemConfigRepository systemConfig;

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@RequestMapping("/sendSetPasswordLink")
	public ResponseEntity<?> sendResetPasswordLink(@RequestBody UserRegistrationBean registerLink) throws Exception {
		logger.info(
				" ================ Send resetPassword Link API is  Invoked ================" + registerLink.toString());
		GenericResponse<Object> response = new GenericResponse<Object>();
		if (registerLink.getEvent().equalsIgnoreCase(AppConstants.FORGOT_PASS_EVENT)) {
			boolean captchaVerified = captchaService.forgotVerify(registerLink.getRecaptchaResponse());
			System.out.println("Peersonal Details recaptchResponse" + registerLink.getRecaptchaResponse());
			if (!captchaVerified) {
			System.out.println(
						"INSIDE ELSE CONDITION OF sendResetPasswordLink" + registerLink.getRecaptchaResponse());
				System.out.println("INSIDE ELSE CONDITION OF sendResetPasswordLink" + captchaVerified);
				response.setMessage("Invalid Captcha");
		return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
}
		}

		return userEmailService.sendEmail(registerLink);
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@RequestMapping("/sendAccDetails")
	public ResponseEntity<?> sendAccDetails(@RequestBody UserRegistrationBean userBean) throws Exception {
		// logger.info(
		// " ================ Send resetPassword Link API is Invoked ================" +
		// registerLink.toString());
		return userEmailService.sendDmmyAccEmail(userBean);
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping("/validatePasswordLink/{token}")
	public ResponseEntity<?> getEmployeeById(@PathVariable(value = "token") String token) {
		logger.info(" ================ Send getEmployeeById API is  Invoked ================:" + token);
		return userEmailService.validateResetPasswordLink(token);
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping("/passwordChangeSuccess")
	public ResponseEntity<?> getEmployeeById(@RequestBody ResetPassBean resetBean) {
		logger.info(" ================ Send getEmployeeById API is  Invoked ================:" + resetBean.getUserId());
		return userEmailService.restSuccessEmail(resetBean);
	}

	/*
	 * email sending api for refer and subsidiary user In refer angular end should
	 * send referenceId
	 */
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@RequestMapping("/sendSubsidiaryAcivationLink")
	public ResponseEntity<?> sendSubsidiaryActivarionLink(@RequestBody SubsidiaryBean registerLink) throws Exception {
		logger.info(" ================ Send sendSubsidiaryActivarionLink API is  Invoked ================:"
				+ registerLink.toString());
		GenericResponse response = new GenericResponse();
		if ((registerLink.getEvent().equalsIgnoreCase("ADD_SUBSIDIARY"))) {
			return userEmailService.sendSubsidiaryEmail(registerLink);
		} else if (registerLink.getEvent().equalsIgnoreCase("ADD_REFER")) {
			return userEmailService.sendReferEmail(registerLink);
		} else {
			response.setMessage("Invalid Event");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}

	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@RequestMapping("/reSendSubsidiaryAcivationLink")
	public ResponseEntity<?> reSendSubActivarionLink(@RequestBody SubsidiaryBean registerLink) throws Exception {
		logger.info(" ================ Send sendSubsidiaryActivarionLink API is  Invoked ================:"
				+ registerLink.toString());
		GenericResponse response = new GenericResponse();
		if ((registerLink.getEvent().equalsIgnoreCase("ADD_SUBSIDIARY"))) {
			return userEmailService.sendSubsidiaryEmail(registerLink);
		} else if (registerLink.getEvent().equalsIgnoreCase("ADD_REFER")) {
			return userEmailService.reSendReferEmail(registerLink);
		} else {
			response.setMessage("Invalid Event");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}

	}

	/*
	 * Validating api for subsidiary, refer
	 */
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping("/validateAccLink/{token}")
	public ResponseEntity<?> getSubsidiaryDetails(@PathVariable(value = "token") String token) {
		logger.info(" ================ Send getSubsidiaryDetails API is  Invoked ================:" + token);
		return userEmailService.validateSubsidiaryLink(token);
	}

	
	/*
	 * Validating branch user passcodeVlue
	 */
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping("/validatePasscode")
	public ResponseEntity<?> checBranchUserkPassCode(@RequestBody BranchUserPassCodeBean passCodeBean,
			HttpServletRequest request) {
		GenericResponse<Object> response = new GenericResponse<>();
		String flag = "";
		String token = request.getHeader("Authorization");
		String userid=passCodeBean.getUserId();
		String userID="";
		logger.info("validatePasscode Authorization: " + token.substring(7));

		logger.info("================ checBranchUserkPassCode API Invoked ================:" + passCodeBean.toString());
	boolean captchaVerified = captchaService.verify(passCodeBean.getRecaptchaResponse());
System.out.println("Peersonal Details recaptchResponse" + passCodeBean.getRecaptchaResponse());
		try {
			if(userid.toString().substring(0, 2).equalsIgnoreCase("CU") || 
					userid.toString().substring(0, 2).equalsIgnoreCase("BC") ||
					userid.toString().substring(0, 2).equalsIgnoreCase("RE")) {
				if(passCodeBean.getEmailid()==null || passCodeBean.getEmailid().isEmpty()) {
					NimaiClient client = userDao.getClientDetailsbyUserId(passCodeBean.getUserId());
					userID=userid+"-"+client.getEmailAddress();
				}else {
					userID=userid+"-"+passCodeBean.getEmailid();
					
				}
				
			}else {
				userID=userid;
	}
			
			NimaiToken tokenDetails = tokenRepo.isTokenExists(userID, token.substring(7));
			if (tokenDetails == null) {
				response.setMessage("Unauthorized Access.");
				return new ResponseEntity<Object>(response, HttpStatus.UNAUTHORIZED);
			}
			if (!captchaVerified) {

				logger.info("INSIDE ELSE CONDITION OF CAPTCH IN authenticateUser CONTROLLER"
						+ passCodeBean.getRecaptchaResponse());
				logger.info("INSIDE ELSE CONDITION OF CAPTCH IN authenticateUser CONTROLLER" + captchaVerified);
				response.setMessage("Invalid captcha");
			try {
					flag = "true";
					tokenDetails.setIsInvalidCaptcha(flag);
					tokenRepo.save(tokenDetails);
					System.out.println("Inside true comdition for invalidCaptchaFlag" + flag);
				} catch (Exception e) {
					e.printStackTrace();
					response.setErrCode("EX001");
					response.setMessage("Error while updating invalid captcha details");
					return new ResponseEntity<>(response, HttpStatus.OK);
				}

			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
			}

			return userEmailService.validatePassCodeValue(passCodeBean);
		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage("OOPS! Something went wrong");
			return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
		}
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@RequestMapping("/validateInvalidCaptcha/{userId}")
	public ResponseEntity<?> validateService(@PathVariable(value = "userId") String userId) {
		logger.info(" ================ validateInvalidCaptcha API Invoked ================:" + userId);
		GenericResponse response = new GenericResponse();

		return userEmailService.validateCaptcha(userId);

	}

	/*
	 * branch user email sending api this api when customer will sign in as customer
	 * or bank as customer
	 */
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@RequestMapping("/sendBranchUserLink")
	public ResponseEntity<?> sendBranchUserLink(@RequestBody BranchUserRequest branchUserLink) {
		logger.info(" ================ sendBranchUserLink API Invoked ================:" + branchUserLink.toString());

		if (!branchUserLink.getEvent().equalsIgnoreCase("ADD_BRANCH_USER")) {
			response.setMessage("Invalid Event");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		NimaiClient clientUseId = userDao.getClientDetailsbyUserId(branchUserLink.getEmailId());
		if (clientUseId == null) {
			NimaiClient client = userDao.getClientDetailsbyUserId(branchUserLink.getUserId());
			if(client==null) {
				response.setErrCode("ASA001");
				response.setMessage(ErrorDescription.getDescription("ASA001"));
				return new ResponseEntity<Object>(response, HttpStatus.OK);
			}else {
				String clientDomainName = utility.getEmailDomain(client.getEmailAddress());
				String passCodeDomainName = utility.getEmailDomain(branchUserLink.getEmailId());
				if (clientDomainName.equalsIgnoreCase(passCodeDomainName)) {
					return userEmailService.sendbranchUserLink(branchUserLink);
				} else {
					response.setErrCode("ASA006");
					response.setMessage(ErrorDescription.getDescription("ASA006"));
					return new ResponseEntity<Object>(response, HttpStatus.OK);
				}
			}
		
	
		} else if(clientUseId.getAccountType().equalsIgnoreCase("SUBSIDIARY")||
				clientUseId.getAccountType().equalsIgnoreCase("MASTER")) {
			return userEmailService.sendbranchUserLink(branchUserLink);
		}else {
			response.setErrCode("ASA019");
			response.setMessage(ErrorDescription.getDescription("ASA019"));
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}

	}

	/* branch user email sending api */
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@RequestMapping("/sendBranchUserPasscode")
	public ResponseEntity<?> sendBranchUserPasscode(@RequestBody BranchUserRequest branchUserLink) {
		logger.info(
				" ================ sendBranchUserPasscode API Invoked ================:" + branchUserLink.toString());
		GenericResponse response = new GenericResponse();

		if (!branchUserLink.getEvent().equalsIgnoreCase("ADD_BRANCH_USER")) {
			response.setMessage("Invalid Event");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		} else if (!branchUserLink.getUserId().substring(0, 2).equalsIgnoreCase("BA")
				&& !branchUserLink.getUserId().substring(0, 2).equalsIgnoreCase("BC")
				&& !branchUserLink.getUserId().substring(0, 2).equalsIgnoreCase("CU")
				&& !branchUserLink.getUserId().substring(0, 2).equalsIgnoreCase("RE")) {
			response.setMessage("Invalid User");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

		}
		return userEmailService.sendbranchUserPasscode(branchUserLink);

	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@RequestMapping("/sendAdminSetPasswordLink")
	public ResponseEntity<?> sendAdminResetPasswordLink(@RequestBody AdminBean adminBean) throws Exception {
		logger.info(
				" ================ Send resetPaasword Link API is  Invoked ================" + adminBean.toString());
		return userEmailService.sendAdminEmail(adminBean);
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@RequestMapping("/sendBankDetails")
	public ResponseEntity<?> sendBAnkDetails(@RequestBody BankDetailsBean BdBean) throws Exception {
		logger.info(" ================ Send resetPaasword Link API is  Invoked ================" + BdBean.toString());
		return userEmailService.sendBankDetails(BdBean);
	}
	
	
//	@CrossOrigin(origins = "*", allowedHeaders = "*")
//	@RequestMapping("/getInvoice/{userId}")
//	public ResponseEntity<?> getInvoice(@PathVariable(value = "userId") String userId) throws Exception {
//		logger.info(" ================ Send resetPaasword Link API is  Invoked ================" + userId);
//		return userEmailService.getSplanInvoice(userId);
//	}
	
	@CrossOrigin(value = "*", allowedHeaders = "*")
	@RequestMapping(value = "/downloadInvoice/{userId}/{invoiceId}", produces ="application/json", method = RequestMethod.POST)
	public ResponseEntity<?> downloadExcelReportForTxnByUserId(@PathVariable(value = "userId") String userId,
			@PathVariable(value = "invoiceId") String invoiceId) throws ParseException, IOException 
	{
		GenericResponse respons=new GenericResponse<>();
		System.out.println("userId"+userId);
		System.out.println("invoiceId"+invoiceId);
		try {
			int subDetails=userEmailService.chkForInvoiceId(userId,invoiceId);
			if(subDetails==0) {
				InvoiceBeanResponse response=userEmailService.getVasplanInvoiceString(userId,invoiceId);
				
				if(!response.getVasStatus().equalsIgnoreCase("Approved")) {
					System.out.println("Inside the if condtionns");
					System.out.println("Inside the if condtionns"+response.toString());
					System.out.println("Inside the if condtionns"+response.getSplanSerialNumber());
					NimaiSubscriptionDetails subDetail = userEmailService.getSubDetails(response.getSplanSerialNumber());
					 response=userEmailService.getSplanInvoiceString(subDetail.getUserid().getUserid(),subDetail.getInvoiceId());
					respons.setData(response);
					respons.setMessage("Success");
				}else {
					System.out.println("Inside the else condtionns");
					respons.setData(response);
					respons.setMessage("Success");
				}
				
			}else {
				InvoiceBeanResponse response=userEmailService.getSplanInvoiceString(userId,invoiceId);
				respons.setData(response);
				respons.setMessage("Success");
				
			}
			
		}catch(Exception e) {
			logger.info("Excption Inside downloadInvoice checking invoiceId controller method");
			e.printStackTrace();
		}
		
		

		

		
		return new ResponseEntity<Object>(respons, HttpStatus.OK);
		
	}
	
	@CrossOrigin(value = "*", allowedHeaders = "*")
	@RequestMapping(value = "/downloadVasInvoice/{userId}/{invoiceId}", produces ="application/json", method = RequestMethod.POST)
	public ResponseEntity<?> downloadVasByUserId(@PathVariable(value = "userId") String userId,
			@PathVariable(value = "invoiceId") String invoiceId) throws ParseException, IOException 
	{
 
		System.out.println("userId"+userId);
		System.out.println("invoiceId"+invoiceId);
		
	
		
	
		String  pdfPath =systemConfig.getPdfPath();
		PdfBean bean =new PdfBean();
		GenericResponse respons=new GenericResponse<>();  
		respons.setData(response);
		respons.setMessage("Success");
		return new ResponseEntity<Object>(respons, HttpStatus.OK);
		
	}
	
	
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static ByteArrayInputStream retrieveByteArrayInputStream(File file) throws IOException {
	    return new ByteArrayInputStream(FileUtils.readFileToByteArray(file));
	}
}