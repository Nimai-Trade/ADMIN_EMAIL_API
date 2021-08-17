package com.nimai.email.dao;

import java.net.SocketTimeoutException;

import java.util.ArrayList;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nimai.email.constants.Constants;
import com.nimai.email.entity.EmailDetails;
import com.nimai.email.repository.EmailDetailsRepository;
import com.nimai.email.service.EmailServiceProviderImpl;
import com.nimai.email.utility.EmaiInsert;
import com.nimai.email.utility.EmailConversionUtil;

@Component
public class EmailProcessImpl {

	private static Logger logger = LoggerFactory.getLogger(EmailProcessImpl.class);

	private byte actionflag = 100;

	@Autowired
	EmailContentProcessImpl emailContentProcessorImpl;

	@Autowired
	EmailServiceProviderImpl emailServiceProviderImpl;

	@Autowired
	EmailDao emailDao;

	@Autowired
	EmailDetailsRepository emailRepo;

	@Autowired
	EmailConversionUtil emailConversionUtil;

	public void sendemail(ArrayList output) throws MessagingException, SocketTimeoutException {
		// System.out.println("email service provider impl size : " + output.size());
		logger.info("=======sendemail details in EmailProcessImpl============" + output.size());
		emailServiceProviderImpl.sendemail(output);

	}

	public void sendemail(ArrayList input, byte emailtype) {

		try {
			ArrayList output = emailContentProcessorImpl.process(input);
			String res = output.get(9).toString();
			// System.out.println("" + res);
			logger.info("" + res);
			if (actionflag == Constants.EMAIL_INSERT_INTO_DB) {

				EmailDetails e = emailConversionUtil.createEmailDBObj(output);
				e.setEmailType((int) emailtype);
				try {
					emailRepo.save(e);
					// emailDao.insertMailDetails(e);
				} catch (Exception ex) {
					throw new RuntimeException("Database exception.");
				}

				// System.out.println("email service provider impl size : " + output.size());
				logger.info("=======sendemail details in EmailProcessImpl of email service provider impl============"
						+ output.size());
			} else {
				// System.out.println("email service provider impl size : " + output.size());
				logger.info("=======sendemail details in EmailProcessImpl of email service provider impl============"
						+ output.size());
				emailServiceProviderImpl.sendemail(output);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void saveEmail(ArrayList input, int emailtype) throws Exception {

		ArrayList output = emailContentProcessorImpl.process(input);
		logger.info(
				"<<<<<<sendemail details in EmailProcessImpl of email service provider impl<<<<<<" + output.toString());
		String res = output.get(6).toString();
		logger.info("<<<<<<sendemail details in EmailProcessImpl of email service provider impl<<<<<<" + res);
		EmailDetails e = emailConversionUtil.createEmailDBObj(output);
		e.setEmailType((int) emailtype);
		//
		try {
			emailRepo.save(e);
			// int x = emailDao.insertMailDetails(e);
			System.out.println("save methos of saveEmail in emailProcessImpl");
		} catch (Exception ex) {
			throw new RuntimeException("Database exception.");
		}

	}

	/**
	 * @return the actionflag
	 */
	public byte getActionflag() {
		return actionflag;
	}

	/**
	 * @param actionflag the actionflag to set
	 */
	public void setActionflag(byte actionflag) {
		this.actionflag = actionflag;
	}

}
