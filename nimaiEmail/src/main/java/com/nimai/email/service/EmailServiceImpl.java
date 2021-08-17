package com.nimai.email.service;

import java.util.Date;



import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import com.nimai.email.bean.UserRegistrationBean;

@Service
public class EmailServiceImpl implements EmailService {

	private JavaMailSender javamailsender;
	
//	@Override
//	public void send(String toAddress, String fromAddress, String subject, String Cntent) throws Exception {
//		// TODO Auto-generated method stub
//		
//		MimeMessage mimemessage =javamailsender.createMimeMessage();
//		MimeMessageHelper mimemessageHelper=new MimeMessageHelper(mimemessage, true);
//		mimemessageHelper.setFrom(fromAddress);
//		mimemessageHelper.setTo(toAddress);
//		mimemessageHelper.setText(Cntent);
//		mimemessageHelper.setSubject(subject);
//		mimemessageHelper.setSentDate(new Date());
//		javamailsender.send(mimemessage);
//		
//	}

	@Override
	public void send(UserRegistrationBean useremail) throws Exception {
		// TODO Auto-generated method stub
		
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(useremail.getEmail());
		mail.setSubject("Testing Mail API");
		mail.setText("Hurray ! You have done that dude...");

		/*
		 * This send() contains an Object of SimpleMailMessage as an Parameter
		 */
		javamailsender.send(mail);
	}

}
