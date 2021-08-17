package com.nimai.email.service;

import java.io.ByteArrayInputStream;

import org.springframework.http.ResponseEntity;

import com.nimai.email.bean.UserRegistrationBean;
import com.nimai.email.bean.AdminBean;
import com.nimai.email.bean.AlertToBanksBean;
import com.nimai.email.bean.BankDetailsBean;
import com.nimai.email.bean.BranchUserBean;
import com.nimai.email.bean.BranchUserPassCodeBean;
import com.nimai.email.bean.BranchUserRequest;
import com.nimai.email.bean.KycEmailRequest;
import com.nimai.email.bean.LcUploadBean;
import com.nimai.email.bean.QuotationAlertRequest;
import com.nimai.email.bean.ResetPassBean;
import com.nimai.email.bean.SubsidiaryBean;
import com.nimai.email.entity.NimaiClient;

public interface UserService {
	boolean checkUserId(String userId);

	boolean checkEmailId(String emailAddress);

	ResponseEntity<Object> sendEmail(UserRegistrationBean userRegistrationBean) throws Exception;

	ResponseEntity<?> validateResetPasswordLink(String token);

	ResponseEntity<?> sendSubsidiaryEmail(SubsidiaryBean subsidiaryBean);

	ResponseEntity<?> validateSubsidiaryLink(String token);

	ResponseEntity<?> sendbranchUserLink(BranchUserRequest branchUserLink);

	ResponseEntity<?> validatePassCodeValue(BranchUserPassCodeBean passCodeBean);

	ResponseEntity<Object> sendAdminEmail(AdminBean userRegistratinBean) throws Exception;

	public void sendAccountEmail() throws Exception;
	
	public ByteArrayInputStream generateEodReport();

	ResponseEntity<?> sendDmmyAccEmail(UserRegistrationBean userBean);

	ResponseEntity<?> sendReferEmail(SubsidiaryBean registerLink);

	ResponseEntity<?> sendbranchUserPasscode(BranchUserRequest branchUserLink);

	ResponseEntity<?> reSendReferEmail(SubsidiaryBean registerLink);

	ResponseEntity<?> restSuccessEmail(ResetPassBean resetBean);

	ResponseEntity<?> sendBankDetails(BankDetailsBean bdBean);

	boolean validateToken(String userId, String substring);

	void saveInvalidCaptchaFlag(BranchUserPassCodeBean passCodeBean, String flag);

	String InvalidCaptchaStatus(BranchUserPassCodeBean passCodeBean);

	ResponseEntity<?> validateCaptcha(String userId);

}
