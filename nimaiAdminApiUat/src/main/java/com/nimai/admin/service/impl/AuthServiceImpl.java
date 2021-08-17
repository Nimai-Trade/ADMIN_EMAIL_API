package com.nimai.admin.service.impl;

import java.util.Comparator;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nimai.admin.exception.ResourceNotFoundException;
import com.nimai.admin.model.NimaiHistoryLogin;
import com.nimai.admin.model.NimaiMLogin;
import com.nimai.admin.model.NimaiMpRoleRights;
import com.nimai.admin.payload.ApiResponse;
import com.nimai.admin.payload.EmployeeResponse;
import com.nimai.admin.payload.JwtAuthenticationResponse;
import com.nimai.admin.payload.ResetPassword;
import com.nimai.admin.repository.RoleRightsRepository;
import com.nimai.admin.repository.UserRepository;
import com.nimai.admin.repository.UserRoleRepository;
import com.nimai.admin.service.AuthService;
import com.nimai.admin.util.AppConstants;

/**
 * 
 * @author sahadeo.naik
 *
 */
@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	UserRoleRepository userRoleRepository;

	@Autowired
	RoleRightsRepository roleRightsRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Value("${link}")
	private String urllink;
	
	private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

	public ResponseEntity<?> getUserRights(String empCode, String roleName) throws ResourceNotFoundException {
		logger.info("Login User Role :: " + roleName + " <<>> Login Employee Name:: " + empCode);
		List<NimaiMpRoleRights> mpUserRights = roleRightsRepository.findRightsByRoleName(roleName);

		if (!mpUserRights.isEmpty()) {

			List<String> rightList = mpUserRights.stream().map(right -> right.getRightId().getRightShortName())
					.collect(Collectors.toList());
			logger.info("Rights List :: " + rightList.size());
			JwtAuthenticationResponse<Object> responsed = new JwtAuthenticationResponse<>();
			responsed.setTokenType("");
			responsed.setData(rightList);
			return ResponseEntity.ok(responsed);
		} else {
			logger.info("No rights exist for given Employee");
			throw new ResourceNotFoundException("No rights exist for given Employee");
		}

	}

	@Override
	public ResponseEntity<?> changePassword(ResetPassword resetPassword) {
		NimaiMLogin mLogin = userRepository.findByEmpCode(resetPassword.getUsername()).orElseThrow(
				() -> new UsernameNotFoundException("User not found with EMP CODE : " + resetPassword.getUsername()));

		if (mLogin.getToken() != null && mLogin.getTokenExpiryDate() != null) {
			if ((resetPassword.getToken().equals(mLogin.getToken()))
					&& (new Date().before(mLogin.getTokenExpiryDate()))) {

				List<NimaiHistoryLogin> stream1 = mLogin.getNimaiHistoryLoginList().stream()
						.sorted(Comparator.comparing(NimaiHistoryLogin::getLoginHistoryId).reversed())
						.collect(Collectors.toList());

				for (int i = 0; i < stream1.size(); i++) {
					if (passwordEncoder.matches(resetPassword.getNewPassword(), stream1.get(i).getPassword())) {
						return new ResponseEntity<>(
								new ApiResponse(false, "New Password Not same with last 3 password."),
								HttpStatus.BAD_REQUEST);
					}
				}
				if (resetPassword.getNewPassword().equals(resetPassword.getConfirmPassword())) {
					mLogin.setPassword(passwordEncoder.encode(resetPassword.getNewPassword()));
					mLogin.setToken(null);
					mLogin.setTokenExpiryDate(null);
					userRepository.save(mLogin);
					return new ResponseEntity<>(new ApiResponse(true, "Password set successfully."),
							HttpStatus.CREATED);

				} else {
					return new ResponseEntity<>(
							new ApiResponse(false, "New Password and Confirm password does not match."),
							HttpStatus.BAD_REQUEST);
				}

			} else {
				return new ResponseEntity<>(new ApiResponse(false, "Invalid Token or Date"), HttpStatus.BAD_REQUEST);
			}
		} else {
			return new ResponseEntity<>(new ApiResponse(false, "Password already reset"), HttpStatus.ALREADY_REPORTED);
		}
	}

	@Override
	public ResponseEntity<?> forgotPassword(String empCode) {
		String link = "";
		NimaiMLogin mLoginId = userRepository.existsByEmpCode(empCode);
		if(mLoginId!=null) {
			NimaiMLogin mLogin = userRepository.existsByEmailId(mLoginId.getEmpCode().getEmpEmail())
					.orElseThrow(() -> new UsernameNotFoundException("User not found with Email ID : " + mLoginId.getEmpCode().getEmpEmail()));
				
						

				if (mLogin != null) {
					mLogin.setFlag(AppConstants.PASSWORD_RESET_FLAG + "");

					mLogin.setToken(new Random().nextInt(100000) + "");
					Date currentTime = new Date();
					mLogin.setTokenExpiryDate(new Date(currentTime.getTime() + TimeUnit.HOURS.toMillis(4)));
					mLogin = userRepository.save(mLogin);

//					link = "http://203.115.123.93:9090/nimai_admin/#/change-password?userId=" + mLogin.getEmpCode().getEmpCode()
//							+ "&token=" + mLogin.getToken();
					
					//link = "http://nimai-pilot-lb-468660897.me-south-1.elb.amazonaws.com/nimai_admin/#/change-password?userId=" + mLogin.getEmpCode().getEmpCode()
							//+ "&token=" + mLogin.getToken();
							
				//	link = urllink+"/nimai_admin/#/change-password?userId=" + mLogin.getEmpCode().getEmpCode()
					//		+ "&token=" + mLogin.getToken();
							
							//link = "link" + mLogin.getEmpCode().getEmpCode()
							//		+ "&token=" + mLogin.getToken();
											

					return new ResponseEntity<>(new EmployeeResponse(mLogin.getEmpCode().getEmpCode(),
							mLogin.getEmpCode().getEmpName(), mLogin.getEmpCode().getEmpEmail(), "UPDATE", 
							"Password reset link sent on your register email Id"), HttpStatus.CREATED);

				} else {
					return new ResponseEntity<>(new ApiResponse(false, "Password already reset"), HttpStatus.ALREADY_REPORTED);
				}
		}else {
			return new ResponseEntity<>(new ApiResponse(false, "Emp code not registred"), HttpStatus.NOT_FOUND);
			
		}
		
	}

	@Override
	public ResponseEntity<?> changePasswordByUser(ResetPassword resetPassword) {
		NimaiMLogin mLogin = userRepository.findByEmpCode(resetPassword.getUsername()).orElseThrow(
				() -> new UsernameNotFoundException("User not found with EMP CODE : " + resetPassword.getUsername()));

		if (resetPassword.getOldPassword() != null && resetPassword.getNewPassword() != null
				&& resetPassword.getConfirmPassword() != null) {
			if (passwordEncoder.matches(resetPassword.getOldPassword(), mLogin.getPassword())) {

				if (!passwordEncoder.matches(resetPassword.getNewPassword(), mLogin.getPassword())) {

					List<NimaiHistoryLogin> stream1 = mLogin.getNimaiHistoryLoginList().stream()
							.sorted(Comparator.comparing(NimaiHistoryLogin::getLoginHistoryId).reversed())
							.collect(Collectors.toList());

					for (int i = 0; i < stream1.size(); i++) {
						if (passwordEncoder.matches(resetPassword.getNewPassword(), stream1.get(i).getPassword())) {
							return new ResponseEntity<>(
									new ApiResponse(false, "New Password Not same with last 3 password."),
									HttpStatus.OK);
						}
					}
					if (resetPassword.getNewPassword().equals(resetPassword.getConfirmPassword())) {
						mLogin.setPassword(passwordEncoder.encode(resetPassword.getNewPassword()));
						mLogin.setToken(null);
						mLogin.setTokenExpiryDate(null);
						userRepository.save(mLogin);
						return new ResponseEntity<>(new ApiResponse(true, "Password set successfully."),
								HttpStatus.CREATED);

					} else {
						return new ResponseEntity<>(
								new ApiResponse(false, "New Password and Confirm password does not match."),
								HttpStatus.OK);
					}
				} else {
					return new ResponseEntity<>(new ApiResponse(false, "New Password Not same with last 3 password."), HttpStatus.OK);
				}
			} else {
				return new ResponseEntity<>(new ApiResponse(false, "Invalid Old password"), HttpStatus.OK);
			}
		} else {
			return new ResponseEntity<>(
					new ApiResponse(false, "Please enter Old password, New password and Confirm password"),
					HttpStatus.OK);
		}
	}

}
