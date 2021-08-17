package com.nimai.admin.payload;

import javax.validation.constraints.NotBlank;

/**
 * Created by sahadeo
 */
public class LoginRequest {

	@NotBlank
	private String username;

	@NotBlank
	private String password;

	@NotBlank
	private String recaptchaResponse;

	public String getRecaptchaResponse() {
		return recaptchaResponse;
	}

	public void setRecaptchaResponse(String recaptchaResponse) {
		this.recaptchaResponse = recaptchaResponse;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
