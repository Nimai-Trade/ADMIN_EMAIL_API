package com.nimai.admin.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nimai.admin.model.GenericResponse;
import com.nimai.admin.model.NimaiEmployeeLoginAttempt;
import com.nimai.admin.model.NimaiMEmployee;
import com.nimai.admin.model.NimaiMLogin;
import com.nimai.admin.model.NimaiMRights;
import com.nimai.admin.model.NimaiMRole;
import com.nimai.admin.model.NimaiMpRoleRights;
import com.nimai.admin.model.NimaiMpUserRole;
import com.nimai.admin.model.NimaiToken;
import com.nimai.admin.model.Tokens;
import com.nimai.admin.payload.JwtAuthenticationResponse;
import com.nimai.admin.payload.LoginRequest;
import com.nimai.admin.payload.NimaiMpRoleRightsBean;
import com.nimai.admin.payload.NimaiMpUserRoleBean;
import com.nimai.admin.payload.ResetPassword;
import com.nimai.admin.payload.UserRightsRequest;
import com.nimai.admin.payload.loginBeanResponse;
import com.nimai.admin.repository.EmployeeRepository;
import com.nimai.admin.repository.NimaiEmployeeLoginAttemptRepository;
import com.nimai.admin.repository.RightsRepository;
import com.nimai.admin.repository.RoleRepository;
import com.nimai.admin.repository.RoleRightsRepository;
import com.nimai.admin.repository.UserRepository;
import com.nimai.admin.repository.UserRoleRepository;
import com.nimai.admin.repository.UserTokenRepository;
import com.nimai.admin.security.JwtTokenProvider;
import com.nimai.admin.service.AuthService;
import com.nimai.admin.service.RoleService;
import com.nimai.admin.service.TokenService;
import com.nimai.admin.service.impl.CaptchService;

/**
 * Created by sahadeo Naik Changes 13 Jan 2020
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	private static Logger logger = LoggerFactory.getLogger(AuthController.class);
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	EmployeeRepository empRepo;

	@Autowired
	UserTokenRepository tokenRepo;

	@Autowired
	RoleRightsRepository rightsRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	JwtTokenProvider tokenProvider;

	@Autowired
	UserRoleRepository roleRepo;

	@Autowired
	AuthService authService;

	@Autowired
	RightsRepository rightsRepo;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	RoleService roleService;

	@Autowired
	NimaiEmployeeLoginAttemptRepository attemptRepo;

	@Autowired
	CaptchService captchaService;

	@Autowired
	TokenService tokenService;

//	@Autowired
//	RedisService redisService;

	/**
	 * Login Functionality for all employees.
	 * 
	 * @param loginRequest
	 * @return
	 */
	@CrossOrigin("*")
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		InetAddress ip;
		try {
			ip = InetAddress.getLocalHost();
			System.out.println("=============================Current IP address========== : " + ip.getHostAddress());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		logger.info("====== Sign In ======" + loginRequest.getRecaptchaResponse());
		boolean captchaVerified = captchaService.verify(loginRequest.getRecaptchaResponse());
		System.out.println("Peersonal Details recaptchResponse" + loginRequest.getRecaptchaResponse());
		if (captchaVerified) {
			NimaiMEmployee employee = empRepo.findByEmpCode(loginRequest.getUsername());
			NimaiEmployeeLoginAttempt attempt = new NimaiEmployeeLoginAttempt();
			Date currentTime = new Date();
			Date currentDateTime = new Date();
			NimaiEmployeeLoginAttempt countDetails = attemptRepo.getcountDetailsByEmpCode(loginRequest.getUsername());
			if (countDetails == null) {

				return signUser(employee, loginRequest, attempt, countDetails);

			}
			if (countDetails.getCount() < 3) {
				return signUser(employee, loginRequest, attempt, countDetails);
			} else if (countDetails.getCount() >= 3) {
				long diff = currentTime.getTime() - countDetails.getInsertedDate().getTime();
				long differenceMinutes = diff / (60 * 1000) % 60;
				logger.info("=============currentTime" + differenceMinutes);
				logger.info("=============currentTime" + countDetails.getInsertedDate().getTime());
				Calendar calendar = Calendar.getInstance();
				System.out.println("=============difference in minutes" + differenceMinutes);
				if (differenceMinutes <= 10) {
					return new ResponseEntity<>("You have excedded maximum attempts to login into the system."
							+ "Your account is temporarily blocked.Please try again after 10 ", HttpStatus.OK);
				} else if (differenceMinutes > 10) {
					NimaiEmployeeLoginAttempt countDetail = attemptRepo.getOne(countDetails.getId());
					countDetail.setCount(0);
					countDetail.setEmpCode(loginRequest.getUsername());
					countDetail.setInsertedDate(currentDateTime);
					attemptRepo.save(countDetail);
					return signUser(employee, loginRequest, attempt, countDetails);
				}
			}
		} else {
			System.out.println("INSIDE ELSE CONDITION OF CAPTCH IN authenticateUser CONTROLLER"
					+ loginRequest.getRecaptchaResponse());
			System.out.println("INSIDE ELSE CONDITION OF CAPTCH IN authenticateUser CONTROLLER" + captchaVerified);
			return new ResponseEntity<>("Invalid Captcha", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("Bad Credentials", HttpStatus.BAD_REQUEST);

	}

	private ResponseEntity<?> signUser(NimaiMEmployee employee, LoginRequest loginRequest,
			NimaiEmployeeLoginAttempt attempt, NimaiEmployeeLoginAttempt countDetails) {
		try {
			if (employee.getEmpCode().equals(loginRequest.getUsername())) {
				Authentication authentication = authenticationManager
						.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
								loginRequest.getPassword()));

				logger.info("===============Authentaction:" + authentication.isAuthenticated());
				SecurityContextHolder.getContext().setAuthentication(authentication);
				String jwt = tokenProvider.generateToken(authentication);
				JwtAuthenticationResponse<Object> responsed = new JwtAuthenticationResponse<>();
				Date currentDateTime = new Date();
				NimaiToken token = new NimaiToken();
				NimaiToken brDetails = tokenRepo.getTokenByEmpCode(loginRequest.getUsername());
				NimaiEmployeeLoginAttempt countDetail = attemptRepo
						.getcountDetailsByEmpCode(loginRequest.getUsername());

				if (countDetail == null) {
					int count = 1;
					attempt.setCount(count);
					attempt.setEmpCode(loginRequest.getUsername());
					attempt.setInsertedDate(currentDateTime);
					attemptRepo.save(attempt);
				} else {
					NimaiEmployeeLoginAttempt countDet = attemptRepo.getOne(countDetail.getId());
					int newCount = 1;
					countDet.setEmpCode(loginRequest.getUsername());
					countDet.setInsertedDate(currentDateTime);
					attemptRepo.save(countDet);

				}
				if (brDetails != null) {
					NimaiToken brUserToupdate = tokenRepo.getOne(brDetails.getUserId());
					token.setToken(jwt);
					token.setUserId(loginRequest.getUsername());
					token.setInsertedDate(currentDateTime);
					tokenRepo.save(token);
				} else {
					token.setToken(jwt);
					token.setUserId(loginRequest.getUsername());
					token.setInsertedDate(currentDateTime);
					tokenRepo.save(token);
				}

				responsed.setAccessToken(jwt);
				return ResponseEntity.ok(responsed);
			} else {
				return new ResponseEntity<>("User not register", HttpStatus.UNAUTHORIZED);
			}
		} catch (BadCredentialsException e) {
			e.printStackTrace();
			NimaiEmployeeLoginAttempt countDetail = attemptRepo.getcountDetailsByEmpCode(loginRequest.getUsername());
			Date currentDateTime = new Date();
			if (countDetail == null) {
				int count = 1;
				attempt.setCount(count);
				attempt.setEmpCode(loginRequest.getUsername());
				attempt.setInsertedDate(currentDateTime);
				attemptRepo.save(attempt);
			} else {
				NimaiEmployeeLoginAttempt countDet = attemptRepo.getOne(countDetail.getId());
				if (countDet.getCount() == 0) {
					countDet.setCount(0);
				} else {
					countDet.setCount(countDetails.getCount() + 1);
				}

				countDet.setEmpCode(loginRequest.getUsername());
				countDet.setInsertedDate(currentDateTime);
				attemptRepo.save(countDet);

			}
			return new ResponseEntity<>("Bad credentials", HttpStatus.BAD_REQUEST);
		}

	}

	@CrossOrigin("*")
	@PostMapping("/validateUser/{userId}")
	public ResponseEntity<?> validUser(@PathVariable String userId, HttpServletRequest request) {
		System.out.println("====== Sign In ======");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String token = request.getHeader("Authorization");
		System.out.println("Authorization: " + token);
		Date currentDateTime = new Date();
		GenericResponse<Object> response = new GenericResponse();
		boolean validToken = tokenService.validateToken(userId, token.substring(7));
		if (validToken) {
			NimaiMEmployee employee = empRepo.findByEmpCode(userId);

			if (employee.getEmpCode().equals(userId)) {
				Optional<NimaiMLogin> loginDetails = userRepository.findByEmpCode(userId);
				loginBeanResponse signResponse = new loginBeanResponse();
				List<NimaiMpUserRole> roleList = roleRepo.findUserRoleId(userId);
//				List<String> authority = new ArrayList<>();
				List<String> rol = new ArrayList<>();

				for (NimaiMpUserRole roleid : roleList) {

					NimaiMpUserRoleBean roleById = new NimaiMpUserRoleBean();
					NimaiMRole role = roleRepository.getOne(roleid.getRoleId().getRoleId());
					List<NimaiMpRoleRights> rightsList = rightsRepository
							.findRightsByRoleId(roleid.getRoleId().getRoleId());
//					for (NimaiMpRoleRights rights : rightsList) {
//						NimaiMpRoleRightsBean rightsResponse = new NimaiMpRoleRightsBean();
//						NimaiMRights rightsID = rightsRepo.getOne(rights.getRightId().getRightId());
//						rightsResponse.setRightName(rightsID.getRightName());
//
//						authority.add(rightsID.getRightName());
//					}
					roleById.setRoleName(role.getRoleName());

					rol.add(role.getRoleName());

				}

				signResponse.setRole(rol);
				// signResponse.setAuthorities(authority);
				signResponse.setId(loginDetails.get().getLoginId());
				signResponse.setCountry(employee.getCountry());
				signResponse.setFlag(loginDetails.get().getFlag());
				signResponse.setName(employee.getEmpLastName());
				signResponse.setAccountNonExpired("true");
				signResponse.setAccountNonLocked("true");
				signResponse.setCredentialsNonExpired("true");
				signResponse.setEnabled("true");
				signResponse.setUsername(employee.getEmpCode());
				response.setMessage("Valid user");
				response.setData(signResponse);
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
		} else {
			return new ResponseEntity<>("Unauthorise Access", HttpStatus.UNAUTHORIZED);
		}
		return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);

	}

	@CrossOrigin("*")
	@GetMapping(value = "/signOut")
	public ResponseEntity<?> logoutPage(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			// saving token into blacklist
			// redisService.save(new Tokens(getJwtFromRequest(request)));
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return ResponseEntity.ok(HttpStatus.OK);
	}

	@CrossOrigin("*")
	@PostMapping("/userRights")
	public ResponseEntity<?> getUserRights(@Valid @RequestBody UserRightsRequest rightsRequest) {
		List<NimaiMRole> roleList = roleService.getListOfRoles(rightsRequest.getEmpCode());
		boolean status = false;
		for (NimaiMRole role : roleList) {

			if (role.getRoleName().equals(rightsRequest.getRoleName())) {
				status = true;
			}
		}
		if (status == false)

			return new ResponseEntity<>("Selected role is not associated with the current user",
					HttpStatus.BAD_REQUEST);
		else
			return authService.getUserRights(rightsRequest.getEmpCode(), rightsRequest.getRoleName());
	}

	/**
	 * User change password / Force to change password after 1st time login
	 * 
	 * @param resetPassword
	 * @return
	 * 
	 *         It must be between 8 and 30 characters long as defined by the
	 *         LengthRule. It must have at least 1 lowercase character as defined by
	 *         the CharacterRule It must have at least 1 uppercase character as
	 *         defined by the CharacterRule It must have at least 1 special
	 *         character as defined by the CharacterRule It must have at least 1
	 *         digit character as defined by the CharacterRule It must not contain
	 *         whitespaces as defined by the WhitespaceRule
	 */
	@CrossOrigin("*")
	@PostMapping("/changePassword")
	public ResponseEntity<?> changePassword(@RequestBody ResetPassword resetPassword) {
		return authService.changePassword(resetPassword);
	}

	/* To get token from Request and save it into blacklist at the time of logout */
	private String getJwtFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7, bearerToken.length());
		}
		return null;
	}

	@CrossOrigin("*")
	@PostMapping("/forgotPassword")
	public ResponseEntity<?> forgotPassword(@RequestBody ResetPassword resetPassword) {
		System.out.println("email Id : " + resetPassword.getEmpCode());
		boolean captchaVerified = captchaService.verify(resetPassword.getRecaptchaResponse());
		System.out.println("Peersonal Details recaptchResponse" + resetPassword.getRecaptchaResponse());
		if (captchaVerified) {
			return authService.forgotPassword(resetPassword.getEmpCode());
		} else {
			System.out.println("INSIDE ELSE CONDITION OF CAPTCH IN forgotPassword CONTROLLER"
					+ resetPassword.getRecaptchaResponse());
			System.out.println("INSIDE ELSE CONDITION OF CAPTCH IN Auth CONTROLLER" + captchaVerified);
			return new ResponseEntity<>("Invalid Captcha", HttpStatus.BAD_REQUEST);
		}
	}

	@CrossOrigin("*")
	@PostMapping("/changePasswordByUser")
	public ResponseEntity<?> changePasswordByUser(@RequestBody ResetPassword resetPassword) {
		return authService.changePasswordByUser(resetPassword);
	}

}
