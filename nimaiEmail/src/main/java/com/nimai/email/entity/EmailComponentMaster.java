package com.nimai.email.entity;

import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * EmailComponentMaster generated
 */


@Entity
@Table(name="NIMAI_EMAIL_COMPONENT")
public class EmailComponentMaster  implements java.io.Serializable {

	
     private Integer eventId;
     private EmailEventMaster emailEventMaster;
     private String emailFrom;
     private String cc;
     private String bcc;
     private String subject;
     private String body;
     private String attachment;
     private String inlineimage;
     private String emailTo;

    public EmailComponentMaster() {
    }

	
    public EmailComponentMaster(Integer eventId) {
        this.eventId = eventId;
    }
    public EmailComponentMaster(Integer eventId, EmailEventMaster emailEventMaster, String emailFrom, String cc, String bcc, String subject, String body, String attachment, String emailTo) {
       this.eventId = eventId;
       this.emailEventMaster = emailEventMaster;
       this.emailFrom = emailFrom;
       this.cc = cc;
       this.bcc = bcc;
       this.subject = subject;
       this.body = body;
       this.attachment = attachment;
       this.emailTo = emailTo;
       

    }
   
    @Id     
    @Column(name="EVENT_ID", unique=true, nullable=false, scale=0)
    public Integer getEventId() {
        return this.eventId;
    }
    
    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "EMAIL_EVENT_ID", referencedColumnName = "EMAIL_EVENT_ID")
    public EmailEventMaster getEmailEventMaster() {
        return this.emailEventMaster;
    }
    
    public void setEmailEventMaster(EmailEventMaster emailEventMaster) {
        this.emailEventMaster = emailEventMaster;
    }

    
    @Column(name="EMAIL_FROM")
    public String getEmailFrom() {
        return this.emailFrom;
    }
    
    public void setEmailFrom(String emailFrom) {
        this.emailFrom = emailFrom;
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

    
    @Column(name="BODY", length=2000)
    public String getBody() {
        return this.body;
    }
    
    public void setBody(String body) {
        this.body = body;
    }

    
    @Column(name="ATTACHMENT")
    public String getAttachment() {
        return this.attachment;
    }
    
    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }
    
    @Column(name="INLINEIMAGE")
    public String getInlineimage() {
        return this.inlineimage;
    }
    
    public void setInlineimage(String inlineimage) {
        this.inlineimage = inlineimage;
    }

    @Column(name="EMAIL_TO")
    public String getEmailTo() {
        return emailTo;
    }

    public void setEmailTo(String emailTo) {
        this.emailTo = emailTo;
    }


    


}




