package com.nimai.admin.payload;

public class PreferredBankListResponse
{
    private String userid;
    private String custUserid;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String bankName;
    private String registeredCountry;
    private String country;
    private String contactPersonName;
    private String contactPersonContactNo;
    private String contactPersonEmailId;
    private Integer isUploaded;
    
    public String getContactPersonEmailId() {
        return this.contactPersonEmailId;
    }
    
    public void setContactPersonEmailId(final String contactPersonEmailId) {
        this.contactPersonEmailId = contactPersonEmailId;
    }
    
    public String getRegisteredCountry() {
        return this.registeredCountry;
    }
    
    public void setRegisteredCountry(final String registeredCountry) {
        this.registeredCountry = registeredCountry;
    }
    
    public PreferredBankListResponse() {
    }
    
    public PreferredBankListResponse(final String userid, final String firstName, final String lastName, final String emailAddress, final String bankName) {
        this.userid = userid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.bankName = bankName;
    }
    
    public PreferredBankListResponse(final String userid, final String custUserid, final String bankName) {
        this.userid = userid;
        this.custUserid = custUserid;
        this.bankName = bankName;
    }
    
    public String getCustUserid() {
        return this.custUserid;
    }
    
    public void setCustUserid(final String custUserid) {
        this.custUserid = custUserid;
    }
    
    public String getUserid() {
        return this.userid;
    }
    
    public void setUserid(final String userid) {
        this.userid = userid;
    }
    
    public String getFirstName() {
        return this.firstName;
    }
    
    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return this.lastName;
    }
    
    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }
    
    public String getEmailAddress() {
        return this.emailAddress;
    }
    
    public void setEmailAddress(final String emailAddress) {
        this.emailAddress = emailAddress;
    }
    
    public String getBankName() {
        return this.bankName;
    }
    
    public void setBankName(final String bankName) {
        this.bankName = bankName;
    }
    
    public String getCountry() {
        return this.country;
    }
    
    public void setCountry(final String country) {
        this.country = country;
    }
    
    public String getContactPersonName() {
        return this.contactPersonName;
    }
    
    public void setContactPersonName(final String contactPersonName) {
        this.contactPersonName = contactPersonName;
    }
    
    public String getContactPersonContactNo() {
        return this.contactPersonContactNo;
    }
    
    public void setContactPersonContactNo(final String contactPersonContactNo) {
        this.contactPersonContactNo = contactPersonContactNo;
    }
    
    public Integer getIsUploaded() {
        return this.isUploaded;
    }
    
    public void setIsUploaded(final Integer isUploaded) {
        this.isUploaded = isUploaded;
    }
}