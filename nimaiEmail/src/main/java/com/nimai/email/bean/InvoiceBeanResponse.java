package com.nimai.email.bean;


public class InvoiceBeanResponse {
  private String customerId;
  
  private String invoiceNumber;
  
  private String contactPersonName;
  
  private String invoiceDate;
  
  private String country;
  
  private String referrenceNumber;
  
  private String companyName;
  
  private String subscriptionAmount;
  
  private String vasAmount;
  
  private String totalAmount;
  
  private String amountInWords;
  
  private String grandTotal;
  
  private String calculatedGstValue;
  
  private String gst;
  
  private String sPlanAmount;
  
  private String vasDiscount;
  
  private String vasStatus;
  
  private int splanSerialNumber;
  
  public int getSplanSerialNumber() {
    return this.splanSerialNumber;
  }
  
  public void setSplanSerialNumber(int splanSerialNumber) {
    this.splanSerialNumber = splanSerialNumber;
  }
  
  public String getVasStatus() {
    return this.vasStatus;
  }
  
  public void setVasStatus(String vasStatus) {
    this.vasStatus = vasStatus;
  }
  
  public String getVasDiscount() {
    return this.vasDiscount;
  }
  
  public void setVasDiscount(String vasDiscount) {
    this.vasDiscount = vasDiscount;
  }
  
  public String getsPlanAmount() {
    return this.sPlanAmount;
  }
  
  public void setsPlanAmount(String sPlanAmount) {
    this.sPlanAmount = sPlanAmount;
  }
  
  public String getGst() {
    return this.gst;
  }
  
  public void setGst(String gst) {
    this.gst = gst;
  }
  
  public String getGrandTotal() {
    return this.grandTotal;
  }
  
  public void setGrandTotal(String grandTotal) {
    this.grandTotal = grandTotal;
  }
  
  public String getCalculatedGstValue() {
    return this.calculatedGstValue;
  }
  
  public void setCalculatedGstValue(String calculatedGstValue) {
    this.calculatedGstValue = calculatedGstValue;
  }
  
  public String getCustomerId() {
    return this.customerId;
  }
  
  public void setCustomerId(String customerId) {
    this.customerId = customerId;
  }
  
  public String getInvoiceNumber() {
    return this.invoiceNumber;
  }
  
  public void setInvoiceNumber(String invoiceNumber) {
    this.invoiceNumber = invoiceNumber;
  }
  
  public String getContactPersonName() {
    return this.contactPersonName;
  }
  
  public void setContactPersonName(String contactPersonName) {
    this.contactPersonName = contactPersonName;
  }
  
  public String getInvoiceDate() {
    return this.invoiceDate;
  }
  
  public void setInvoiceDate(String invoiceDate) {
    this.invoiceDate = invoiceDate;
  }
  
  public String getCountry() {
    return this.country;
  }
  
  public void setCountry(String country) {
    this.country = country;
  }
  
  public String getReferrenceNumber() {
    return this.referrenceNumber;
  }
  
  public void setReferrenceNumber(String referrenceNumber) {
    this.referrenceNumber = referrenceNumber;
  }
  
  public String getCompanyName() {
    return this.companyName;
  }
  
  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }
  
  public String getSubscriptionAmount() {
    return this.subscriptionAmount;
  }
  
  public void setSubscriptionAmount(String subscriptionAmount) {
    this.subscriptionAmount = subscriptionAmount;
  }
  
  public String getVasAmount() {
    return this.vasAmount;
  }
  
  public void setVasAmount(String vasAmount) {
    this.vasAmount = vasAmount;
  }
  
  public String getTotalAmount() {
    return this.totalAmount;
  }
  
  public void setTotalAmount(String totalAmount) {
    this.totalAmount = totalAmount;
  }
  
  public String getAmountInWords() {
    return this.amountInWords;
  }
  
  public void setAmountInWords(String amountInWords) {
    this.amountInWords = amountInWords;
  }
  
  public String toString() {
    return "InvoiceBeanResponse [customerId=" + this.customerId + ", invoiceNumber=" + this.invoiceNumber + ", contactPersonName=" + this.contactPersonName + ", invoiceDate=" + this.invoiceDate + ", country=" + this.country + ", referrenceNumber=" + this.referrenceNumber + ", companyName=" + this.companyName + ", subscriptionAmount=" + this.subscriptionAmount + ", vasAmount=" + this.vasAmount + ", totalAmount=" + this.totalAmount + ", amountInWords=" + this.amountInWords + "]";
  }
}




