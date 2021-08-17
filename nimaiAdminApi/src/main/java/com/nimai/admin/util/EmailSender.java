
package com.nimai.admin.util;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.nimai.admin.model.NimaiMEmployee;
import com.nimai.admin.model.NimaiMLogin;
import com.nimai.admin.repository.UserRepository;

@Component
public class EmailSender {
	
	@Autowired
	UserRepository userRepository;

	private JavaMailSender javaMailSender;

	public EmailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

//	public void sendEmail(NimaiMEmployee employee) throws MailException {
//
//		SimpleMailMessage mail = new SimpleMailMessage();
//
//		mail.setTo(employee.getEmpEmail());
//		mail.setSubject("Testing Mail API");		
//		String link = ServletUriComponentsBuilder.fromCurrentContextPath().path("http://localhost:8085/api/employee/updateUser/empCode="+employee.getEmpCode()+"&token="+employee.getNimaiMLoginList().get(0).getToken()).toUriString();		
//		mail.setText(link);
//		javaMailSender.send(mail);
//	}
	
	public void sendmail(NimaiMEmployee employee) throws AddressException, MessagingException, IOException {
		   Properties props = new Properties();
		   props.put("mail.smtp.auth", "true");
		   props.put("mail.smtp.starttls.enable", "true");
		   props.put("mail.smtp.host", "smtp.gmail.com");
		   props.put("mail.smtp.port", "587");
		   
		   NimaiMLogin login = userRepository.findByEmpCode(employee.getEmpCode())
					.orElseThrow(() -> new UsernameNotFoundException("User not found with EMP CODE : " + employee.getEmpCode()));
		   
		   Session session = Session.getInstance(props, new javax.mail.Authenticator() {
		      protected PasswordAuthentication getPasswordAuthentication() {
		         return new PasswordAuthentication("naiksahadev06@gmail.com", "Admin@1234");
		      }
		   });
		   Message msg = new MimeMessage(session);
		   msg.setFrom(new InternetAddress("naiksahadev06@gmail.com", false));

		   msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse("naiksahadeo06@gmail.com"));
		   msg.setSubject("Nimai Account Password Generation");
		   msg.setContent("Nimai Account Password Generation", "text/html");
		   msg.setSentDate(new Date());

		   MimeBodyPart messageBodyPart = new MimeBodyPart();
		   String link = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/employee/updateUser/empCode="+login.getEmpCode()+"&token="+login.getToken()).toUriString();		
			
		   messageBodyPart.setContent("Go to the below link and set password to the your Nimai account:: \n" +link, "text/html");


		   Multipart multipart = new MimeMultipart();
		   multipart.addBodyPart(messageBodyPart);
		   MimeBodyPart attachPart = new MimeBodyPart();

		   attachPart.attachFile("C:\\Users\\Sahadeo\\Pictures\\pikachu-6.png");
		   multipart.addBodyPart(attachPart);
		   msg.setContent(multipart);
		   Transport.send(msg);   
		}

}







