package com.nimai.admin.payload;

import java.util.Date;

public class TransactionSearchResponse {	
	   
	    private String transactionId;
	    private String userId;
	    private String mobileNo;
	    private String emailId;
	    private String beneficiry;
	    private String beneficiryCountry;
	    private String applicant;
	    private String applicantCountry;
	    private Date insertedDate;	
	    private Date txnValidaty;	  
	    private String lcBank;	
	    private String amount;	 
	    private String ccy;	    
	    private String requiredment;	 
	    private String trxnStatus;	   
	    private String quotes;
	    
		public String getTransactionId() {
			return transactionId;
		}
		public void setTransactionId(String transactionId) {
			this.transactionId = transactionId;
		}
		public String getUserId() {
			return userId;
		}
		public void setUserId(String userId) {
			this.userId = userId;
		}
		public String getMobileNo() {
			return mobileNo;
		}
		public void setMobileNo(String mobileNo) {
			this.mobileNo = mobileNo;
		}
		public String getEmailId() {
			return emailId;
		}
		public void setEmailId(String emailId) {
			this.emailId = emailId;
		}
		public String getBeneficiry() {
			return beneficiry;
		}
		public void setBeneficiry(String beneficiry) {
			this.beneficiry = beneficiry;
		}
		public String getBeneficiryCountry() {
			return beneficiryCountry;
		}
		public void setBeneficiryCountry(String beneficiryCountry) {
			this.beneficiryCountry = beneficiryCountry;
		}
		public String getApplicant() {
			return applicant;
		}
		public void setApplicant(String applicant) {
			this.applicant = applicant;
		}
		public String getApplicantCountry() {
			return applicantCountry;
		}
		public void setApplicantCountry(String applicantCountry) {
			this.applicantCountry = applicantCountry;
		}
		public Date getInsertedDate() {
			return insertedDate;
		}
		public void setInsertedDate(Date insertedDate) {
			this.insertedDate = insertedDate;
		}
		public Date getTxnValidaty() {
			return txnValidaty;
		}
		public void setTxnValidaty(Date txnValidaty) {
			this.txnValidaty = txnValidaty;
		}
		
		
		public String getLcBank() {
			return lcBank;
		}
		public void setLcBank(String lcBank) {
			this.lcBank = lcBank;
		}
		public String getAmount() {
			return amount;
		}
		public void setAmount(String amount) {
			this.amount = amount;
		}
		public String getCcy() {
			return ccy;
		}
		public void setCcy(String ccy) {
			this.ccy = ccy;
		}
		public String getRequiredment() {
			return requiredment;
		}
		public void setRequiredment(String requiredment) {
			this.requiredment = requiredment;
		}
		public String getTrxnStatus() {
			return trxnStatus;
		}
		public void setTrxnStatus(String trxnStatus) {
			this.trxnStatus = trxnStatus;
		}
		public String getQuotes() {
			return quotes;
		}
		public void setQuotes(String quotes) {
			this.quotes = quotes;
		}
	   
	    
	    
}
