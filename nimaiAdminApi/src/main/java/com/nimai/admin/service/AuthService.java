package com.nimai.admin.service;

import org.springframework.http.ResponseEntity;

import com.nimai.admin.payload.ResetPassword;

public interface AuthService {

	ResponseEntity<?> getUserRights(String empCode, String roleName);

	ResponseEntity<?> changePassword(ResetPassword resetPassword);

	ResponseEntity<?> forgotPassword(String emailId);

	ResponseEntity<?> changePasswordByUser(ResetPassword resetPassword);

}
