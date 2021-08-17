package com.nimai.admin.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nimai.admin.exception.BadRequestException;
import com.nimai.admin.exception.ResourceNotFoundException;
import com.nimai.admin.model.NimaiMLogin;
import com.nimai.admin.repository.UserRepository;
import com.nimai.admin.util.AppConstants;

/**
 * Created by sahadeo
 */

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String empCode) throws UsernameNotFoundException {
		// Let people login with Employee Code
		NimaiMLogin mLogin = userRepository.findByEmpCode(empCode)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with EMP CODE : " + empCode));

		if (!mLogin.getEmpCode().getStatus().equalsIgnoreCase(AppConstants.LOGIN_STATUS)) {
			throw new BadRequestException("This account is not activated yet. Please contact your Admin.");
		}

		return UserPrincipal.create(mLogin);
	}

	@Transactional
	public UserDetails loadUserById(Long id) {
		NimaiMLogin mLogin = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

		return UserPrincipal.create(mLogin);
	}
}