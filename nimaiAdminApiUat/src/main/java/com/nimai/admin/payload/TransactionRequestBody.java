package com.nimai.admin.payload;

public class TransactionRequestBody
{
    private String countryName;
    private String transactionId;
    private String status;
    private String userId;
    private String makerComment;
    private String checkerComment;
    private String customerType;
    
    public String getCountryName() {
        return this.countryName;
    }
    
    public void setCountryName(final String countryName) {
        this.countryName = countryName;
    }
    
    public String getTransactionId() {
        return this.transactionId;
    }
    
    public void setTransactionId(final String transactionId) {
        this.transactionId = transactionId;
    }
    
    public String getStatus() {
        return this.status;
    }
    
    public void setStatus(final String status) {
        this.status = status;
    }
    
    public String getUserId() {
        return this.userId;
    }
    
    public void setUserId(final String userId) {
        this.userId = userId;
    }
    
    public String getMakerComment() {
        return this.makerComment;
    }
    
    public void setMakerComment(final String makerComment) {
        this.makerComment = makerComment;
    }
    
    public String getCheckerComment() {
        return this.checkerComment;
    }
    
    public void setCheckerComment(final String checkerComment) {
        this.checkerComment = checkerComment;
    }
    
    public String getCustomerType() {
        return this.customerType;
    }
    
    public void setCustomerType(final String customerType) {
        this.customerType = customerType;
    }
}