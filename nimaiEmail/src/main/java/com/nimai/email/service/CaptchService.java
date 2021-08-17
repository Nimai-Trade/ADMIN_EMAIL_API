package com.nimai.email.service;


import java.io.BufferedReader;


import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.net.ssl.HttpsURLConnection;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.nimai.email.bean.RecaptchaResponse;





@PropertySource("classpath:application.properties")
@Service
public class CaptchService {

	private final RestTemplate restTemplate;

	public CaptchService(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
	}

	@Value("${google.recaptcha.secret.key}")
	public String recaptchaSecret;
	
	@Value("${google.recaptcha.secret}")
	public String forRecaptchaSecret;

	@Value("${google.recaptcha.verify.url}")
	public String recaptchaVerifyUrl;

	private final static String USER_AGENT = "Mozilla/5.0";

	public boolean verify(String response) {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
		System.out.println("Inside captchaservice" + response);
		param.add("secret", recaptchaSecret);
		param.add("response", response);

		HttpEntity<MultiValueMap<String, String>> requst = new HttpEntity<>(param, headers);

		RecaptchaResponse recaptResponse = null;
		try {
			recaptResponse = this.restTemplate.postForObject(recaptchaVerifyUrl, requst, RecaptchaResponse.class);

			System.out.println("Inside captchaservice" + recaptResponse.toString());

			System.out.println("Success:" + recaptResponse.isSuccess());
			System.out.println("Hostname:" + recaptResponse.getHostname());
			System.out.println("Challenge Timestamp:" + recaptResponse.getChallengeTs());

		} catch (RestClientException e) {
			System.out.print(e.getMessage());
		}
		if (recaptResponse.isSuccess()) {
			return true;
		} else {
			return false;
		}
	}
	public boolean forgotVerify(String response) {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
		System.out.println("Inside captchaservice" + response);
		param.add("secret", forRecaptchaSecret);
		param.add("response", response);

		HttpEntity<MultiValueMap<String, String>> requst = new HttpEntity<>(param, headers);

		RecaptchaResponse recaptResponse = null;
		try {
			recaptResponse = this.restTemplate.postForObject(recaptchaVerifyUrl, requst, RecaptchaResponse.class);

			System.out.println("Inside captchaservice" + recaptResponse.toString());

			System.out.println("Success:" + recaptResponse.isSuccess());
			System.out.println("Hostname:" + recaptResponse.getHostname());
			System.out.println("Challenge Timestamp:" + recaptResponse.getChallengeTs());

		} catch (RestClientException e) {
			System.out.print(e.getMessage());
		}
		if (recaptResponse.isSuccess()) {
			return true;
		} else {
			return false;
		}
	}


}

