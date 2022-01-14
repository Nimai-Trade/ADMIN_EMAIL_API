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
	return splanSerialNumber;
}
public void setSplanSerialNumber(int splanSerialNumber) {
	this.splanSerialNumber = splanSerialNumber;
}
public String getVasStatus() {
	return vasStatus;
}
public void setVasStatus(String vasStatus) {
	this.vasStatus = vasStatus;
}
public String getVasDiscount() {
	return vasDiscount;
}
public void setVasDiscount(String vasDiscount) {
	this.vasDiscount = vasDiscount;
}
public String getsPlanAmount() {
	return sPlanAmount;
}
public void setsPlanAmount(String sPlanAmount) {
	this.sPlanAmount = sPlanAmount;
}
public String getGst() {
	return gst;
}
public void setGst(String gst) {
	this.gst = gst;
}
public String getGrandTotal() {
	return grandTotal;
}
public void setGrandTotal(String grandTotal) {
	this.grandTotal = grandTotal;
}
public String getCalculatedGstValue() {
	return calculatedGstValue;
}
public void setCalculatedGstValue(String calculatedGstValue) {
	this.calculatedGstValue = calculatedGstValue;
}
public String getCustomerId() {
	return customerId;
}
public void setCustomerId(String customerId) {
	this.customerId = customerId;
}
public String getInvoiceNumber() {
	return invoiceNumber;
}
public void setInvoiceNumber(String invoiceNumber) {
	this.invoiceNumber = invoiceNumber;
}
public String getContactPersonName() {
	return contactPersonName;
}
public void setContactPersonName(String contactPersonName) {
	this.contactPersonName = contactPersonName;
}
public String getInvoiceDate() {
	return invoiceDate;
}
public void setInvoiceDate(String invoiceDate) {
	this.invoiceDate = invoiceDate;
}
public String getCountry() {
	return country;
}
public void setCountry(String country) {
	this.country = country;
}
public String getReferrenceNumber() {
	return referrenceNumber;
}
public void setReferrenceNumber(String referrenceNumber) {
	this.referrenceNumber = referrenceNumber;
}
public String getCompanyName() {
	return companyName;
}
public void setCompanyName(String companyName) {
	this.companyName = companyName;
}
public String getSubscriptionAmount() {
	return subscriptionAmount;
}
public void setSubscriptionAmount(String subscriptionAmount) {
	this.subscriptionAmount = subscriptionAmount;
}
public String getVasAmount() {
	return vasAmount;
}
public void setVasAmount(String vasAmount) {
	this.vasAmount = vasAmount;
}
public String getTotalAmount() {
	return totalAmount;
}
public void setTotalAmount(String totalAmount) {
	this.totalAmount = totalAmount;
}
public String getAmountInWords() {
	return amountInWords;
}
public void setAmountInWords(String amountInWords) {
	this.amountInWords = amountInWords;
}
@Override
public String toString() {
	return "InvoiceBeanResponse [customerId=" + customerId + ", invoiceNumber=" + invoiceNumber + ", contactPersonName="
			+ contactPersonName + ", invoiceDate=" + invoiceDate + ", country=" + country + ", referrenceNumber="
			+ referrenceNumber + ", companyName=" + companyName + ", subscriptionAmount=" + subscriptionAmount
			+ ", vasAmount=" + vasAmount + ", totalAmount=" + totalAmount + ", amountInWords=" + amountInWords + "]";
}



}
