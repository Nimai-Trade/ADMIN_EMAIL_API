package com.nimai.email.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="NIMAI_EMAIL_DETAILS")
//@SequenceGenerator(sequenceName = "SEQ_EMAIL_DETAILS_ID", name = "SEQ_EMAIL_DETAILS_ID", allocationSize = 1)
public class EmailDetails {
	
	  private Integer emailDetailsId;
	     private Integer emailType;
	     private String emailFrom;
	     private String emailTo;
	     private String cc;
	     private String bcc;
	     private String subject;
	     private String body;
	     private Integer emailSendFlg;
	     private Date createdOn;
	     private Date modifiedOn;
	     private String attachments;
	     private String inlineimage;
	     
	     public EmailDetails() {
	     }
	     
	     public EmailDetails(Integer emailDetailsId) {
	         this.emailDetailsId = emailDetailsId;
	     }
	     
	     public EmailDetails(Integer emailDetailsId, Integer emailType, String emailFrom, String emailTo, String cc, String bcc, String subject, String body, Integer emailSendFlg, Date createdOn, Date modifiedOn) {
	         this.emailDetailsId = emailDetailsId;
	         this.emailType = emailType;
	         this.emailFrom = emailFrom;
	         this.emailTo = emailTo;
	         this.cc = cc;
	         this.bcc = bcc;
	         this.subject = subject;
	         this.body = body;
	         this.emailSendFlg = emailSendFlg;
	         this.createdOn = createdOn;
	         this.modifiedOn = modifiedOn;
	      }
	     
	     @Id 
	     @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name="EMAIL_DETAILS_ID", unique=true, nullable=false, scale=0)
	    public Integer getEmailDetailsId() {
	        return this.emailDetailsId;
	    }
	    
	    public void setEmailDetailsId(Integer emailDetailsId) {
	        this.emailDetailsId = emailDetailsId;
	    }

	    
	    @Column(name="EMAIL_TYPE", precision=1, scale=0)
	    public Integer getEmailType() {
	        return this.emailType;
	    }
	    
	    public void setEmailType(Integer emailType) {
	        this.emailType = emailType;
	    }

	    
	    @Column(name="EMAIL_FROM")
	    public String getEmailFrom() {
	        return this.emailFrom;
	    }
	    
	    public void setEmailFrom(String emailFrom) {
	        this.emailFrom = emailFrom;
	    }

	    
	    @Column(name="EMAIL_TO")
	    public String getEmailTo() {
	        return this.emailTo;
	    }
	    
	    public void setEmailTo(String emailTo) {
	        this.emailTo = emailTo;
	    }

	    
	    @Column(name="CC")
	    public String getCc() {
	        return this.cc;
	    }
	    
	    public void setCc(String cc) {
	        this.cc = cc;
	    }

	    
	    @Column(name="BCC")
	    public String getBcc() {
	        return this.bcc;
	    }
	    
	    public void setBcc(String bcc) {
	        this.bcc = bcc;
	    }

	    
	    @Column(name="SUBJECT")
	    public String getSubject() {
	        return this.subject;
	    }
	    
	    public void setSubject(String subject) {
	        this.subject = subject;
	    }

	    
	    @Column(name="BODY")
	    public String getBody() {
	        return this.body;
	    }
	    
	    public void setBody(String body) {
	        this.body = body;
	    }

	    
	    @Column(name="EMAIL_SEND_FLG", precision=1, scale=0)
	    public Integer getEmailSendFlg() {
	        return this.emailSendFlg;
	    }
	    
	    public void setEmailSendFlg(Integer emailSendFlg) {
	        this.emailSendFlg = emailSendFlg;
	    }

	    
	    @Column(name="CREATED_ON")
	     @Temporal(TemporalType.TIMESTAMP)
	    public Date getCreatedOn() {
	        return this.createdOn;
	    }
	    
	    public void setCreatedOn(Date createdOn) {
	        this.createdOn = createdOn;
	    }

	    
	    @Column(name="MODIFIED_ON")
	    @Temporal(TemporalType.TIMESTAMP)
	    public Date getModifiedOn() {
	        return this.modifiedOn;
	    }
	    
	    public void setModifiedOn(Date modifiedOn) {
	        this.modifiedOn = modifiedOn;
	    }

	 @Column(name="ATTACHMENT", length=200)
	    public String getAttachments() {
	        return this.attachments;
	    }
	    
	    public void setAttachments(String attachments) {
	        this.attachments = attachments;
	    }

	     @Column(name="INLINEIMAGE")
	    public String getInlineimage() {
	        return inlineimage;
	    }

	    public void setInlineimage(String inlineimage) {
	        this.inlineimage = inlineimage;
	    }



	}

