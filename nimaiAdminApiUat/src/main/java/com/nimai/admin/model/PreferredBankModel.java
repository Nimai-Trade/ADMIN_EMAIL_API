package com.nimai.admin.model;

import javax.persistence.Column;
import javax.persistence.Basic;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import javax.persistence.Entity;

@Entity
@Table(name = "customer_preferred_banks")
@EntityListeners({ AuditingEntityListener.class })
@DynamicUpdate
public class PreferredBankModel
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private int id;
    @Column(name = "CUST_USERID")
    private String custUserId;
    @Column(name = "BANK_USERID")
    private String bankUserId;
    @Column(name = "BANK_NAME")
    private String bankName;
    @Column(name = "BANK_COUNTRY")
    private String bankCountry;
    @Column(name = "BANK_CONTACT_PERSON_NAME")
    private String bankContactPersonName;
    @Column(name = "BANK_CONTACT_PERSON_MOB_NO")
    private String bankContactPersonMobNo;
    @Column(name = "BANK_CONTACT_PERSON_EMAIL_ID")
    private String bankContactPersonEmailId;
    @Column(name = "IS_UPLOADED")
    private Integer isUploaded;
    
    public int getId() {
        return this.id;
    }
    
    public void setId(final int id) {
        this.id = id;
    }
    
    public String getCustUserId() {
        return this.custUserId;
    }
    
    public void setCustUserId(final String custUserId) {
        this.custUserId = custUserId;
    }
    
    public String getBankUserId() {
        return this.bankUserId;
    }
    
    public void setBankUserId(final String bankUserId) {
        this.bankUserId = bankUserId;
    }
    
    public String getBankName() {
        return this.bankName;
    }
    
    public void setBankName(final String bankName) {
        this.bankName = bankName;
    }
    
    public String getBankCountry() {
        return this.bankCountry;
    }
    
    public void setBankCountry(final String bankCountry) {
        this.bankCountry = bankCountry;
    }
    
    public String getBankContactPersonName() {
        return this.bankContactPersonName;
    }
    
    public void setBankContactPersonName(final String bankContactPersonName) {
        this.bankContactPersonName = bankContactPersonName;
    }
    
    public String getBankContactPersonMobNo() {
        return this.bankContactPersonMobNo;
    }
    
    public void setBankContactPersonMobNo(final String bankContactPersonMobNo) {
        this.bankContactPersonMobNo = bankContactPersonMobNo;
    }
    
    public String getBankContactPersonEmailId() {
        return this.bankContactPersonEmailId;
    }
    
    public void setBankContactPersonEmailId(final String bankContactPersonEmailId) {
        this.bankContactPersonEmailId = bankContactPersonEmailId;
    }
    
    public Integer getIsUploaded() {
        return this.isUploaded;
    }
    
    public void setIsUploaded(final Integer isUploaded) {
        this.isUploaded = isUploaded;
    }
}