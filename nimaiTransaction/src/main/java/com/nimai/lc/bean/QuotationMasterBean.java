package com.nimai.lc.bean;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class QuotationMasterBean implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private Integer quotationId;
	private String transactionId;
	private String userId;
	private String bankUserId;
	private Float confirmationCharges;
	private String confChgsIssuanceToNegot;
	private String confChgsIssuanceToexp;
	private String confChgsIssuanceToMatur;
	private Float discountingCharges;
    private Float refinancingCharges;
	private Float bankAcceptCharges;
	private Float applicableBenchmark;
	private String commentsBenchmark;
	private Float negotiationChargesFixed;
	private Float negotiationChargesPerct;
	private Float docHandlingCharges;
	private Float otherCharges;
	private String chargesType;
	private double minTransactionCharges;
	//@JsonFormat(pattern = "yyyy-MM-dd")
	private Date insertedDate;
	private String insertedBy;
	//@JsonFormat(pattern = "yyyy-MM-dd")
	private Date modifiedDate;
	private String modifiedBy;
	private String quotationStatus;
	private Float totalQuoteValue;
	private Date validityDate;
	private String termConditionComments;
	private String bankName;
	private String branchName;
	private String swiftCode;
	private String countryName;
	private String emailAddress;
	private String telephone;
	private String mobileNumber;
	private String firstName;
	private String lastName;
	private Integer noOfQuotesByBank;
	private String goods;
	private Integer noOfGoodsByBank;
	private String rejectedBy;
	
	
	
	public String getTermConditionComments() {
		return termConditionComments;
	}
	public void setTermConditionComments(String termConditionComments) {
		this.termConditionComments = termConditionComments;
	}
	public String getRejectedBy() {
		return rejectedBy;
	}
	public void setRejectedBy(String rejectedBy) {
		this.rejectedBy = rejectedBy;
	}
	public Integer getNoOfQuotesByBank() {
		return noOfQuotesByBank;
	}
	public void setNoOfQuotesByBank(Integer noOfQuotesByBank) {
		this.noOfQuotesByBank = noOfQuotesByBank;
	}
	
	public String getGoods() {
		return goods;
	}
	public void setGoods(String goods) {
		this.goods = goods;
	}
	public Integer getNoOfGoodsByBank() {
		return noOfGoodsByBank;
	}
	public void setNoOfGoodsByBank(Integer noOfGoodsByBank) {
		this.noOfGoodsByBank = noOfGoodsByBank;
	}
	public Float getTotalQuoteValue() {
		return totalQuoteValue;
	}
	public void setTotalQuoteValue(Float totalQuoteValue) {
		this.totalQuoteValue = totalQuoteValue;
	}
	public Date getValidityDate() {
		return validityDate;
	}
	public void setValidityDate(Date validityDate) {
		this.validityDate = validityDate;
	}
	public Integer getQuotationId() {
		return quotationId;
	}
	public void setQuotationId(Integer quotationId) {
		this.quotationId = quotationId;
	}
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
	
	
	public String getBankUserId() {
		return bankUserId;
	}
	public void setBankUserId(String bankUserId) {
		this.bankUserId = bankUserId;
	}
	public Float getConfirmationCharges() {
		return confirmationCharges;
	}
	public void setConfirmationCharges(Float confirmationCharges) {
		this.confirmationCharges = confirmationCharges;
	}
	public String getConfChgsIssuanceToNegot() {
		return confChgsIssuanceToNegot;
	}
	public void setConfChgsIssuanceToNegot(String confChgsIssuanceToNegot) {
		this.confChgsIssuanceToNegot = confChgsIssuanceToNegot;
	}
	public String getConfChgsIssuanceToexp() {
		return confChgsIssuanceToexp;
	}
	public void setConfChgsIssuanceToexp(String confChgsIssuanceToexp) {
		this.confChgsIssuanceToexp = confChgsIssuanceToexp;
	}
	public String getConfChgsIssuanceToMatur() {
		return confChgsIssuanceToMatur;
	}
	public void setConfChgsIssuanceToMatur(String confChgsIssuanceToMatur) {
		this.confChgsIssuanceToMatur = confChgsIssuanceToMatur;
	}
	public Float getDiscountingCharges() {
		return discountingCharges;
	}
	public void setDiscountingCharges(Float discountingCharges) {
		this.discountingCharges = discountingCharges;
	}
	
	public Float getRefinancingCharges() {
		return refinancingCharges;
	}
	public void setRefinancingCharges(Float refinancingCharges) {
		this.refinancingCharges = refinancingCharges;
	}
	public Float getBankAcceptCharges() {
		return bankAcceptCharges;
	}
	public void setBankAcceptCharges(Float bankAcceptCharges) {
		this.bankAcceptCharges = bankAcceptCharges;
	}
	public Float getApplicableBenchmark() {
		return applicableBenchmark;
	}
	public void setApplicableBenchmark(Float applicableBenchmark) {
		this.applicableBenchmark = applicableBenchmark;
	}
	public String getCommentsBenchmark() {
		return commentsBenchmark;
	}
	public void setCommentsBenchmark(String commentsBenchmark) {
		this.commentsBenchmark = commentsBenchmark;
	}
	public Float getNegotiationChargesFixed() {
		return negotiationChargesFixed;
	}
	public void setNegotiationChargesFixed(Float negotiationChargesFixed) {
		this.negotiationChargesFixed = negotiationChargesFixed;
	}
	public Float getNegotiationChargesPerct() {
		return negotiationChargesPerct;
	}
	public void setNegotiationChargesPerct(Float negotiationChargesPerct) {
		this.negotiationChargesPerct = negotiationChargesPerct;
	}
	public Float getDocHandlingCharges() {
		return docHandlingCharges;
	}
	public void setDocHandlingCharges(Float docHandlingCharges) {
		this.docHandlingCharges = docHandlingCharges;
	}
	public Float getOtherCharges() {
		return otherCharges;
	}
	public void setOtherCharges(Float otherCharges) {
		this.otherCharges = otherCharges;
	}
	public String getChargesType() {
		return chargesType;
	}
	public void setChargesType(String chargesType) {
		this.chargesType = chargesType;
	}
	public double getMinTransactionCharges() {
		return minTransactionCharges;
	}
	public void setMinTransactionCharges(double minTransactionCharges) {
		this.minTransactionCharges = minTransactionCharges;
	}
	public Date getInsertedDate() {
		return insertedDate;
	}
	public void setInsertedDate(Date insertedDate) {
		this.insertedDate = insertedDate;
	}
	public String getInsertedBy() {
		return insertedBy;
	}
	public void setInsertedBy(String insertedBy) {
		this.insertedBy = insertedBy;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public String getQuotationStatus() {
		return quotationStatus;
	}
	public void setQuotationStatus(String quotationStatus) {
		this.quotationStatus = quotationStatus;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getSwiftCode() {
		return swiftCode;
	}
	public void setSwiftCode(String swiftCode) {
		this.swiftCode = swiftCode;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	
}
