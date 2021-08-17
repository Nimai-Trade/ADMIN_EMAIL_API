package com.nimai.email.service;

import java.net.SocketTimeoutException;
import java.util.ArrayList;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nimai.email.bean.EmailContentBean;
import com.nimai.email.dao.EmailProcessImpl;

@Component
public class EmailServiceProviderImpl {
	
	private static Logger logger = LoggerFactory.getLogger(EmailServiceProviderImpl.class);

	@Autowired    
	EmailContentBean emailContentBean;
	
	
	public void sendemail(ArrayList  input) throws MessagingException, SocketTimeoutException
	 {
		//System.out.println("in EmailServiceProviderImpl");
		logger.info("=======in EmailServiceProviderImpl============");

		 emailContentBean.setFromEmailAddress((String)input.get(1));
		 emailContentBean.setToEmailAddress((ArrayList)input.get(2));
		 emailContentBean.setCcEmailAddress((ArrayList)input.get(3));
		 emailContentBean.setBccEmailAddress((ArrayList)input.get(4));
		 emailContentBean.setSubject((String)input.get(5));
		 emailContentBean.setEmailBody((String)input.get(6));
		 
		 
		 emailContentBean.setListOfAttachements((ArrayList)input.get(9));
	   
		// System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
		 //System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
		 logger.info("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
		 logger.info("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
		 
		emailContentBean.sendEmail();
		 
	 }
}
