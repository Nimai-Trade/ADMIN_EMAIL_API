package com.nimai.email.utility;

import java.util.ArrayList;
import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nimai.email.constants.Constants;
import com.nimai.email.entity.EmailDetails;
import com.nimai.email.repository.EmailDetailsRepository;

@Component
public class EmailConversionUtil {
	private static Logger logger = LoggerFactory.getLogger(EmailConversionUtil.class);
	
	@Autowired
	EmailDetailsRepository emailRepo;

	public EmailDetails createEmailDBObj(ArrayList output) {
		logger.debug(" createEmailDBObj start || output : " + output + " || emailtype : ");
		EmailDetails details = new EmailDetails();
		String enc = "";
//	            System.out.println("output>"+output);
		//System.out.println("output>" + output.size());
		logger.debug("output>" + output.size());
		details.setEmailFrom((String) output.get(1));
		details.setEmailTo((String) ((ArrayList) output.get(2)).toString());
		details.setEmailTo((String) ((ArrayList) output.get(2)).toString());
		details.setCc((output.get(3) != null) ? ((String) ((ArrayList) output.get(3)).toString()) : null);
		details.setBcc((output.get(4) != null) ? ((String) ((ArrayList) output.get(4)).toString()) : null);
		details.setSubject((String) output.get(5));
		// details.setInlineimage((String) output.get(7));
		// System.out.println("Body Encrypt Flag : " + output.get(8));
		// details.setBodyencrypt((Byte)output.get(8));
		if (output.get(7) != null) {
			String attach = (output.get(7).toString().replace("[", ""));
			details.setAttachments(attach.replace("]", ""));
		//	details.setAttachments((String)output.get(7).toString() );
		}else {details.setAttachments(null);}
		

		// details.setAttachments((output.get(7).toString().replaceAll("[", "").replaceAll("]", "") != null) ? ((String) ((ArrayList)
		//output.get(7)).toString()) : null);
		// long a = (Long)output.get(8);
		// byte b = (byte)(a);
		byte b = 1;
		// if(emailtype == b){
//	            if((Byte)output.get(8) == b){
//	                RC4Util r = RC4Util.getInstance();
//	                    enc = r.encrypt((String)output.get(6));
//	                    details.setBody(enc);
//	            }else{
//	                details.setBody((String)output.get(6));
//	            }

		details.setBody((String) output.get(6));
		// details.setModifiedBy("");
		details.setModifiedOn(Calendar.getInstance().getTime());
		// details.setCreatedBy("");
		details.setCreatedOn(Calendar.getInstance().getTime());
		details.setEmailSendFlg(Constants.EMAIL_NOT_SEND_FLG);
		// details.setBody((String)output.get(6));
		// logger.debug("createEmailDBObj end Email From >> "+details.getEmailFrom()+"
		// Email To >> "+ details.getEmailTo()+" Email CC >> "+ details.getCc()
		// +" Email BCC >> "+ details.getBcc()+" Email Subject >> "+
		// details.getSubject()+" Email Body >> "+ details.getBody());


		return details;
	}

	public EmailDetails createEmailDBObj(ArrayList output, byte emailtype) {

		logger.debug(" createEmailDBObj start || output : " + output + " || emailtype : " + emailtype);
		EmailDetails details = new EmailDetails();
		String enc = "";
//	            System.out.println("output>"+output);
		System.out.println("output>" + output.size());
		details.setEmailFrom((String) output.get(1));
		details.setEmailTo((String) ((ArrayList) output.get(2)).get(0));
		details.setCc((output.get(3) != null) ? ((String) ((ArrayList) output.get(3)).get(0)) : null);
		details.setBcc((output.get(4) != null) ? ((String) ((ArrayList) output.get(4)).get(0)) : null);
		details.setSubject((String) output.get(5));
//	            if(emailtype == Constants.EMP_REG){
//	                RC4Util r = RC4Util.getInstance();
//	                    enc = r.encrypt((String)output.get(6));
//	                    details.setBody(enc);
//	            }else{
//	                details.setBody((String)output.get(6));
//	            }
		// details.setModifiedBy(1l);
		details.setModifiedOn(Calendar.getInstance().getTime());
		// details.setCreatedBy(1l);
		details.setCreatedOn(Calendar.getInstance().getTime());
		details.setEmailSendFlg(Constants.EMAIL_NOT_SEND_FLG);
		// details.setBody((String)output.get(6));
		logger.debug("createEmailDBObj end Email From >> " + details.getEmailFrom() + " Email To >>  "
				+ details.getEmailTo() + " Email CC >>  " + details.getCc() + " Email BCC >>  " + details.getBcc()
				+ " Email Subject >>  " + details.getSubject() + " Email Body >>  " + details.getBody());

		return details;
	}

}
