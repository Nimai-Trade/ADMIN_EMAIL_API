package com.nimai.admin.payload;

import java.util.Date;

public class QuotationDetailsResponse {
	private Integer quotationId;
	private String userid;
	private String bankUserid;
	private String transactionId;
	private String requirementType;

	private Float confirmationCharges;
	private String confChgsIssuanceToNegot;
	private String confChgsIssuanceToMatur;
	private Float applicableBenchmark;
	private String commentsBenchmark;
	private Float bankerAcceptCharges;
	private Float refinancingCharges;
	private Float discountingChargesPA;
	private Float NegotiationChargesPA;

	private Float negotiationChargesFixed;
	private Float negotiationChargesPerct;

	private Float docHandlingCharges;
	private Float otherCharges;
	private String specifyTypeOfCharges;

	private Float minimumTransactionCharges;
	private Float totalQuoteValue;
	private Date validityDate;
	private String quotationStatus;
	private String currency;

	private String ib;
	private String tanor;
	private String amount;
	private String termCondition;

	public QuotationDetailsResponse() {
		super();
	}

	public QuotationDetailsResponse(Integer quotationId, String userid, String bankUserid, String transactionId,
			Float confirmationCharges, String confChgsIssuanceToNegot, String confChgsIssuanceToMatur,
			Float minimumTransactionCharges, Float otherCharges, Date validityDate, Float totalQuoteValue,
			String currency, String quotationStatus) {
		super();
		this.quotationId = quotationId;
		this.userid = userid;
		this.bankUserid = bankUserid;
		this.transactionId = transactionId;
		this.confirmationCharges = confirmationCharges;
		this.confChgsIssuanceToNegot = confChgsIssuanceToNegot;
		this.confChgsIssuanceToMatur = confChgsIssuanceToMatur;
		this.minimumTransactionCharges = minimumTransactionCharges;
		this.otherCharges = otherCharges;
		this.validityDate = validityDate;
		this.totalQuoteValue = totalQuoteValue;
		this.currency = currency;
		this.quotationStatus = quotationStatus;
	}

	public Integer getQuotationId() {
		return quotationId;
	}

	public void setQuotationId(Integer quotationId) {
		this.quotationId = quotationId;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getBankUserid() {
		return bankUserid;
	}

	public void setBankUserid(String bankUserid) {
		this.bankUserid = bankUserid;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
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

	public String getConfChgsIssuanceToMatur() {
		return confChgsIssuanceToMatur;
	}

	public void setConfChgsIssuanceToMatur(String confChgsIssuanceToMatur) {
		this.confChgsIssuanceToMatur = confChgsIssuanceToMatur;
	}

	public Float getMinimumTransactionCharges() {
		return minimumTransactionCharges;
	}

	public void setMinimumTransactionCharges(Float minimumTransactionCharges) {
		this.minimumTransactionCharges = minimumTransactionCharges;
	}

	public Float getOtherCharges() {
		return otherCharges;
	}

	public void setOtherCharges(Float otherCharges) {
		this.otherCharges = otherCharges;
	}

	public Date getValidityDate() {
		return validityDate;
	}

	public void setValidityDate(Date validityDate) {
		this.validityDate = validityDate;
	}

	public Float getTotalQuoteValue() {
		return totalQuoteValue;
	}

	public void setTotalQuoteValue(Float totalQuoteValue) {
		this.totalQuoteValue = totalQuoteValue;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getQuotationStatus() {
		return quotationStatus;
	}

	public void setQuotationStatus(String quotationStatus) {
		this.quotationStatus = quotationStatus;
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

	public Float getBankerAcceptCharges() {
		return bankerAcceptCharges;
	}

	public void setBankerAcceptCharges(Float bankerAcceptCharges) {
		this.bankerAcceptCharges = bankerAcceptCharges;
	}

	public Float getRefinancingCharges() {
		return refinancingCharges;
	}

	public void setRefinancingCharges(Float refinancingCharges) {
		this.refinancingCharges = refinancingCharges;
	}

	public Float getDiscountingChargesPA() {
		return discountingChargesPA;
	}

	public void setDiscountingChargesPA(Float discountingChargesPA) {
		this.discountingChargesPA = discountingChargesPA;
	}

	public Float getNegotiationChargesPA() {
		return NegotiationChargesPA;
	}

	public void setNegotiationChargesPA(Float negotiationChargesPA) {
		NegotiationChargesPA = negotiationChargesPA;
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

	public String getSpecifyTypeOfCharges() {
		return specifyTypeOfCharges;
	}

	public void setSpecifyTypeOfCharges(String specifyTypeOfCharges) {
		this.specifyTypeOfCharges = specifyTypeOfCharges;
	}

	public String getRequirementType() {
		return requirementType;
	}

	public void setRequirementType(String requirementType) {
		this.requirementType = requirementType;
	}

	public String getIb() {
		return ib;
	}

	public void setIb(String ib) {
		this.ib = ib;
	}

	public String getTanor() {
		return tanor;
	}

	public void setTanor(String tanor) {
		this.tanor = tanor;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getTermCondition() {
		return termCondition;
	}

	public void setTermCondition(String termCondition) {
		this.termCondition = termCondition;
	}

}
