package com.nimai.email.utility;

import java.util.ArrayList;

import java.util.List;
import java.util.Properties;
import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.transaction.Transactional;

import java.io.File;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.nimai.email.constants.ConfigurationValues;
import com.nimai.email.dao.EmailDao;
import com.nimai.email.dao.EmailProcessImpl;
import com.nimai.email.entity.EmailDetails;
import com.nimai.email.repository.EmailDetailsRepository;

/**
 *
 * @author Administrator
 */
@Component
public class EmailSend {

	private static Logger logger = LoggerFactory.getLogger(EmailSend.class);

	@Autowired
	private ConfigurationValues configurationValues;

	@Autowired
	EmailProcessImpl emailProcessorImpl;

	@Autowired
	EmailDetailsRepository emailRepo;

	@Autowired
	EmailDao emailDao;

	@Value("${mail.host}")
	private String host;

	@Value("${mail.smtp.port}")
	private String port;

	@Transactional
	public void getDetailsEmail() throws Exception {
		try {
			List<EmailDetails> results = new ArrayList<EmailDetails>();
			results = emailRepo.findAllByEmailFlag(com.nimai.email.constants.Constants.EMAIL_NOT_SEND_FLG);
			int emailID;
			String dec = null;
			logger.debug("Email sending to following Employee : " + results.size() + "");
			if (results.size() > 0) {

				for (EmailDetails e : results) {
					emailID = e.getEmailDetailsId();
					logger.debug("EMAIL DETAILS ID = " + emailID);
					ArrayList input = createArraylistfromDBObj(e);
					logger.debug("........Sinput.........." + input.toString());
					emailProcessorImpl.sendemail(input);
					emailDao.updateMailDetails(emailID);
					logger.debug("EmailDetails Table is updated for emaildetailid = " + emailID);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Exception-1" + e.getMessage());
			logger.debug("before sending email-1" + e.getMessage());
		}
	}

	public boolean confirmSMTP() {
		boolean result = false;
		try {
			// System.out.println("In confirmSMTP");
			logger.debug("In confirmSMTP");
			String accontEmailid = configurationValues.getStringValues("EMAIL", "EMAIL.SEND_EMAIL_ADDRESS");
			// String pwd = configurationValues.getEncryptedValues("EMAIL",
			// "EMAIL.SEND_EMAIL_PWD");
			String pwd = configurationValues.getStringValues("EMAIL", "EMAIL.SEND_EMAIL_PWD");
			// System.out.println("pwd" + pwd);
			logger.debug("pwd" + pwd);
			Properties props = new Properties();
			Session session = Session.getInstance(props, null);
			Transport transport = session.getTransport("smtp");
			// transport.connect(test.getHost(), Integer.parseInt(test.getPort()),
			// test.getEmailaddress(), test.getPwd());
			transport.connect(host, Integer.parseInt(port), accontEmailid, pwd);
			transport.close();
			result = true;
			logger.debug("SMTP Server Alive...!");
			// System.out.println("SMTP Server Alive...!");
		} catch (AuthenticationFailedException e) {
			logger.debug(e.getMessage());
			// System.out.println(e.getMessage());
		} catch (MessagingException e) {
			logger.debug(e.toString());
			System.out.println(e.getMessage());
		} catch (Exception e) {
			logger.debug(e.toString());
			// System.out.println(e.getMessage());
		}

		return result;
	}

	public EmailSend() {

	}

	/*
	 * **************************************************************** PARAMETER
	 * STRUTURE TO BE FOLLOWED for ARRAYLIST to be passed in METHOD sendemail
	 * ****************************************************************** input
	 * ArrayList 0 - eventId 1 - from email address 2 - Arraylist of TO email
	 * addresses 3 - Arraylist of CC email addresses 4 - Arraylist of BCC email
	 * addresses 5 - String of Subject 6 - String of Body 7 - ArrayList of File
	 * attachements 8 - ArrayList of Inline Images
	 * ******************************************************************
	 */
	public ArrayList createArraylistfromDBObj(EmailDetails details) {
		ArrayList output = new ArrayList();
		output.add(1);
		output.add(details.getEmailFrom());

		ArrayList<String> to = new ArrayList<String>();
		to.add(details.getEmailTo());
		output.add(to);

		ArrayList<String> cc = new ArrayList<String>();
		if (details.getCc() == null) {
			output.add(null);
		} else {
			cc.add(details.getCc());
			output.add(cc);
		}

		ArrayList<String> bcc = new ArrayList<String>();
		if (details.getBcc() == null) {
			output.add(null);
		} else {
			bcc.add(details.getBcc());
			output.add(bcc);
		}
		output.add(details.getSubject());
		output.add(details.getBody());
		output.add(null);
		output.add(null);

		if (details.getAttachments() == null) {
			output.add(null);
		} else {
			logger.debug("" + details.getAttachments());
			String attach1 = details.getAttachments().replace("[", "");
			attach1 = details.getAttachments().replace("]", "");
			ArrayList<String> list1 = new ArrayList<String>(Arrays.asList(details.getAttachments().split(",")));
			logger.debug("" + list1.size());
			ArrayList<File> myList = new ArrayList<File>();

			if (list1.size() > 0) {
				for (String temp : list1) {
					System.out.println(temp);
					myList.add(new File(temp.toString().trim()));
				}
			}

			output.add(myList);
		}

		// details.setBody((String)output.get(6));
		System.out.println("createArraylistfromDBObj end || Email From >> " + details.getEmailFrom() + " Email To >>  "
				+ details.getEmailTo() + " Email CC >>  " + details.getCc() + " Email BCC >>  " + details.getBcc()
				+ " Email Subject >>  " + details.getSubject() + " Email Body >>  " + details.getBody());

		return output;
	}

	private ArrayList<InlineImage> getInlineImages(EmailDetails details) {
		ArrayList<InlineImage> listOfInlineImages = null;

		String inlineImages = details.getInlineimage();
		if (inlineImages != null && inlineImages.trim().length() > 0) {
			// System.out.println("inlineImages=" + inlineImages);
			logger.debug("inlineImages=" + inlineImages);
			listOfInlineImages = new ArrayList<InlineImage>();
			String array[] = inlineImages.split(",");
			for (String inlineImageDetails : array) {
				String imageDetails[] = inlineImageDetails.split(":");
				InlineImage inlineImage = new InlineImage();
				inlineImage.setFileType(imageDetails[0]);
				inlineImage.setTagName(imageDetails[1]);
				inlineImage.setFileName(imageDetails[2]);
				inlineImage.process();
				listOfInlineImages.add(inlineImage);
			}

		} else {
			listOfInlineImages = null;
		}

		return listOfInlineImages;
	}
//  public static void main(String[] args) throws Exception {
//  boolean status;
//  EmailSend email = new EmailSend();
// // TestEmailSend email = new EmailSend();
//  //DCBEmailProcessorEmail email = new DCBEmailProcessorEmail();
//  status = email.confirmSMTP();
//  System.out.println(">>>>>>>>"+status);
//  if (status) {
//      //loggerEmail.info("Email Sending Start..........");
//      System.out.println("Email Sending Start..........");
//      email.getDetailsEmail();
//      //loggerEmail.info("Email Sending Done..........");
//      System.out.println("Email Sending Done..........");
//
//      // loggerEmail.info("SMS Sending Start..........");
//      System.out.println("SMS Sending Start..........");
//      //email.getLeadDetailsSms();
//      logger.info("SMS Sending Done..........");
//      System.out.println("SMS Sending Done..........");
//  } else {
//      logger.info("Email & SMS Sending Fail");
//      System.out.println("Email & SMS Sending Fail");
//      logger.info("SMTP Server is not Alive...!");
//      System.out.println("SMTP Server is not Alive...!");
//  }
//  //  System.exit(0);
//}
}
