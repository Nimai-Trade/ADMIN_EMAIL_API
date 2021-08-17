package com.nimai.email.service;

import com.nimai.email.bean.UserRegistrationBean;

public interface EmailService {

	
	public  void send(UserRegistrationBean userEmail) throws Exception; 
}
