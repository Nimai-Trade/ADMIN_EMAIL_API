package com.nimai.admin.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.validation.constraints.NotNull;
import javax.persistence.Basic;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;

@Entity
@Table(name = "nimai_email_scheduler_alerts_tobanks")
public class NimaiEmailSchedulerAlertToBanks
{
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "scheduler_Id")
    private int scedulerid;
    @Column(name = "customer_user_id")
    private String customerid;
    @Column(name = "transaction_id")
    private String transactionid;
    @Column(name = "reason")
    private String reason;
    @Column(name = "banks_email_id")
    private String banksEmailID;
    @Column(name = "inserted_date")
    private Date insertedDate;
    @Column(name = "bank_user_id")
    private String bankUserid;
    @Column(name = "bank_user_name")
    private String bankUserName;
    @Column(name = "email_event")
    private String emailEvent;
    @Column(name = "quotation_id")
    private Integer quotationId;
    @Column(name = "customer_email_id")
    private String customerEmail;
    @Column(name = "passcode_user_email")
    private String passcodeuserEmail;
    @Column(name = "customer_user_name")
    private String customerUserName;
    @Column(name = "Email_Status")
    private String emailStatus;
    @Column(name = "TR_SCHEDULED_ID")
    private String trScheduledId;
    @Column(name = "TR_Email_status_ToBanks")
    private String transactionEmailStatusToBanks;
    
    public String getPasscodeuserEmail() {
        return this.passcodeuserEmail;
    }
    
    public void setPasscodeuserEmail(final String passcodeuserEmail) {
        this.passcodeuserEmail = passcodeuserEmail;
    }
    
    public String getTrScheduledId() {
        return this.trScheduledId;
    }
    
    public void setTrScheduledId(final String trScheduledId) {
        this.trScheduledId = trScheduledId;
    }
    
    public String getTransactionEmailStatusToBanks() {
        return this.transactionEmailStatusToBanks;
    }
    
    public void setTransactionEmailStatusToBanks(final String transactionEmailStatusToBanks) {
        this.transactionEmailStatusToBanks = transactionEmailStatusToBanks;
    }
    
    public void setEmailStatus(final String emailStatus) {
        this.emailStatus = emailStatus;
    }
    
    public String getEmailStatus() {
        return this.emailStatus;
    }
    
    public void setEmailFlag(final String emailStatus) {
        this.emailStatus = emailStatus;
    }
    
    public String getCustomerUserName() {
        return this.customerUserName;
    }
    
    public void setCustomerUserName(final String customerUserName) {
        this.customerUserName = customerUserName;
    }
    
    public String getCustomerEmail() {
        return this.customerEmail;
    }
    
    public void setCustomerEmail(final String customerEmail) {
        this.customerEmail = customerEmail;
    }
    
    public Integer getQuotationId() {
        return this.quotationId;
    }
    
    public void setQuotationId(final Integer quotationId) {
        this.quotationId = quotationId;
    }
    
    public String getEmailEvent() {
        return this.emailEvent;
    }
    
    public void setEmailEvent(final String emailEvent) {
        this.emailEvent = emailEvent;
    }
    
    public String getBankUserid() {
        return this.bankUserid;
    }
    
    public void setBankUserid(final String bankUserid) {
        this.bankUserid = bankUserid;
    }
    
    public String getBankUserName() {
        return this.bankUserName;
    }
    
    public void setBankUserName(final String bankUserName) {
        this.bankUserName = bankUserName;
    }
    
    public String getBanksEmailID() {
        return this.banksEmailID;
    }
    
    public void setBanksEmailID(final String banksEmailID) {
        this.banksEmailID = banksEmailID;
    }
    
    public Date getInsertedDate() {
        return this.insertedDate;
    }
    
    public void setInsertedDate(final Date insertedDate) {
        this.insertedDate = insertedDate;
    }
    
    public int getScedulerid() {
        return this.scedulerid;
    }
    
    public void setScedulerid(final int scedulerid) {
        this.scedulerid = scedulerid;
    }
    
    public String getCustomerid() {
        return this.customerid;
    }
    
    public void setCustomerid(final String customerid) {
        this.customerid = customerid;
    }
    
    public String getTransactionid() {
        return this.transactionid;
    }
    
    public void setTransactionid(final String transactionid) {
        this.transactionid = transactionid;
    }
    
    public String getReason() {
        return this.reason;
    }
    
    public void setReason(final String reason) {
        this.reason = reason;
    }
}