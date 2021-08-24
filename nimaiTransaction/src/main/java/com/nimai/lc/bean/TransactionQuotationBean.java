package com.nimai.lc.bean;

import java.util.Date;

import javax.persistence.Column;

public class TransactionQuotationBean {
	private String transactionId;
	private String userId;
	private String requirementType;
	private String lCIssuanceBank;
	private String lCIssuanceBranch;
	private String lCSwiftCode;
	private String lCIssuanceCountry;
	private Double lCValue;
	private String lCCurrency;
	private Date lCIssuingDate;
	private Date lastShipmentDate;
	private String goodsType;
	private Date negotiationDate;
	private Date lCExpiryDate;
	private Integer usanceDays;
	private String paymentTerms;
	private Date startDate;
	private Date endDate;
	private Integer originalTenorDays;
	private String refinancingPeriod;
	private Date lCMaturityDate;
	private String lCNumber;
	private String lastBeneBank;
	private String lastBeneSwiftCode;
	private String lastBankCountry;
	private String remarks;
	private String discountingPeriod;
	private String confirmationPeriod;
	private String financingPeriod;
	private String userType;
	private String applicantName;
	private String applicantCountry;
	private String applicantContactPerson;
	private String applicantContactPersonEmail;
	private String beneName;
	private String beneCountry;
	private String beneContactPerson;
	private String beneContactPersonEmail;
	private String beneBankName;
	private String beneSwiftCode;
	private String beneBankCountry;
	private String loadingCountry;
	private String loadingPort;
	private String dischargeCountry;
	private String dischargePort;
	private String lcChargesType;
	private String lcProforma;
	private String tenorFile;
	private Date validity;
	private String paymentPeriod;

	private String quotationPlaced;
	private String transactionStatus;
	private Date acceptedOn;
	private Integer quotationId;
	private String bankUserId;

	private Float confirmationCharges;
	private String confChgsIssuanceToNegot;
	private String confChgsIssuanceToExp;
	private String confChgsIssuanceToMatur;
	private Float discountingCharges;
	private Float refinancingCharges;
	private Float bankerAcceptCharges;
	private Float applicableBenchmark;
	private String commentsBenchmark;
	private Float negotiationChargesFixed;
	private Float negotiationChargesPerct;
	private Float docHandlingCharges;
	private Float otherCharges;
	private String chargesType;
	private Float minTransactionCharges;

	private Float totalQuoteValue;
	private Date validityDate;
	private String quotationStatus;
	private Integer quoteRank;
	private Date insertedDate;
	private Date modifiedDate;
	private String rejectedBy;
	private String termConditionComments;
	private Float acceptedQuoteValue;
	private Date rejectedOn;
	private String transactionDaysCountdown;
	private String quoteDaysCountdown;
	private Date expiredOn;
	private String rejectedReason;
	
	
	
	public String getTenorFile() {
		return tenorFile;
	}

	public void setTenorFile(String tenorFile) {
		this.tenorFile = tenorFile;
	}

	public String getRejectedReason() {
		return rejectedReason;
	}

	public void setRejectedReason(String rejectedReason) {
		this.rejectedReason = rejectedReason;
	}

	public Date getExpiredOn() {
		return expiredOn;
	}

	public void setExpiredOn(Date expiredOn) {
		this.expiredOn = expiredOn;
	}

	public Date getRejectedOn() {
		return rejectedOn;
	}

	public void setRejectedOn(Date rejectedOn) {
		this.rejectedOn = rejectedOn;
	}

	public String getTransactionDaysCountdown() {
		return transactionDaysCountdown;
	}

	public void setTransactionDaysCountdown(String transactionDaysCountdown) {
		this.transactionDaysCountdown = transactionDaysCountdown;
	}

	public String getQuoteDaysCountdown() {
		return quoteDaysCountdown;
	}

	public void setQuoteDaysCountdown(String quoteDaysCountdown) {
		this.quoteDaysCountdown = quoteDaysCountdown;
	}

	public Float getAcceptedQuoteValue() {
		return acceptedQuoteValue;
	}

	public void setAcceptedQuoteValue(Float acceptedQuoteValue) {
		this.acceptedQuoteValue = acceptedQuoteValue;
	}

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

	public Date getInsertedDate() {
		return insertedDate;
	}

	public void setInsertedDate(Date insertedDate) {
		this.insertedDate = insertedDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Integer getQuoteRank() {
		return quoteRank;
	}

	public void setQuoteRank(Integer quoteRank) {
		this.quoteRank = quoteRank;
	}

	public String getQuotationStatus() {
		return quotationStatus;
	}

	public void setQuotationStatus(String quotationStatus) {
		this.quotationStatus = quotationStatus;
	}

	public Integer getQuotationId() {
		return quotationId;
	}

	public void setQuotationId(Integer quotationId) {
		this.quotationId = quotationId;
	}

	public String getBankUserId() {
		return bankUserId;
	}

	public void setBankUserId(String bankUserId) {
		this.bankUserId = bankUserId;
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

	public String getRequirementType() {
		return requirementType;
	}

	public void setRequirementType(String requirementType) {
		this.requirementType = requirementType;
	}

	public String getlCIssuanceBank() {
		return lCIssuanceBank;
	}

	public void setlCIssuanceBank(String lCIssuanceBank) {
		this.lCIssuanceBank = lCIssuanceBank;
	}

	public String getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}

	public String getApplicantName() {
		return applicantName;
	}

	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}

	public String getBeneName() {
		return beneName;
	}

	public void setBeneName(String beneName) {
		this.beneName = beneName;
	}

	public Double getlCValue() {
		return lCValue;
	}

	public void setlCValue(Double lCValue) {
		this.lCValue = lCValue;
	}

	public Date getValidity() {
		return validity;
	}

	public void setValidity(Date validity) {
		this.validity = validity;
	}

	public String getQuotationPlaced() {
		return quotationPlaced;
	}

	public void setQuotationPlaced(String quotationPlaced) {
		this.quotationPlaced = quotationPlaced;
	}

	public String getTransactionStatus() {
		return transactionStatus;
	}

	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	public Date getAcceptedOn() {
		return acceptedOn;
	}

	public void setAcceptedOn(Date acceptedOn) {
		this.acceptedOn = acceptedOn;
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

	public String getConfChgsIssuanceToExp() {
		return confChgsIssuanceToExp;
	}

	public void setConfChgsIssuanceToExp(String confChgsIssuanceToExp) {
		this.confChgsIssuanceToExp = confChgsIssuanceToExp;
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

	public Float getBankerAcceptCharges() {
		return bankerAcceptCharges;
	}

	public void setBankerAcceptCharges(Float bankerAcceptCharges) {
		this.bankerAcceptCharges = bankerAcceptCharges;
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

	public Float getMinTransactionCharges() {
		return minTransactionCharges;
	}

	public void setMinTransactionCharges(Float minTransactionCharges) {
		this.minTransactionCharges = minTransactionCharges;
	}

	public String getlCIssuanceBranch() {
		return lCIssuanceBranch;
	}

	public void setlCIssuanceBranch(String lCIssuanceBranch) {
		this.lCIssuanceBranch = lCIssuanceBranch;
	}

	public String getlCSwiftCode() {
		return lCSwiftCode;
	}

	public void setlCSwiftCode(String lCSwiftCode) {
		this.lCSwiftCode = lCSwiftCode;
	}

	public String getlCIssuanceCountry() {
		return lCIssuanceCountry;
	}

	public void setlCIssuanceCountry(String lCIssuanceCountry) {
		this.lCIssuanceCountry = lCIssuanceCountry;
	}

	public String getlCCurrency() {
		return lCCurrency;
	}

	public void setlCCurrency(String lCCurrency) {
		this.lCCurrency = lCCurrency;
	}

	public Date getlCIssuingDate() {
		return lCIssuingDate;
	}

	public void setlCIssuingDate(Date lCIssuingDate) {
		this.lCIssuingDate = lCIssuingDate;
	}

	public Date getLastShipmentDate() {
		return lastShipmentDate;
	}

	public void setLastShipmentDate(Date lastShipmentDate) {
		this.lastShipmentDate = lastShipmentDate;
	}

	public Date getNegotiationDate() {
		return negotiationDate;
	}

	public void setNegotiationDate(Date negotiationDate) {
		this.negotiationDate = negotiationDate;
	}

	public Date getlCExpiryDate() {
		return lCExpiryDate;
	}

	public void setlCExpiryDate(Date lCExpiryDate) {
		this.lCExpiryDate = lCExpiryDate;
	}

	public Integer getUsanceDays() {
		return usanceDays;
	}

	public void setUsanceDays(Integer usanceDays) {
		this.usanceDays = usanceDays;
	}

	public String getPaymentTerms() {
		return paymentTerms;
	}

	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getOriginalTenorDays() {
		return originalTenorDays;
	}

	public void setOriginalTenorDays(Integer originalTenorDays) {
		this.originalTenorDays = originalTenorDays;
	}

	public String getRefinancingPeriod() {
		return refinancingPeriod;
	}

	public void setRefinancingPeriod(String refinancingPeriod) {
		this.refinancingPeriod = refinancingPeriod;
	}

	public Date getlCMaturityDate() {
		return lCMaturityDate;
	}

	public void setlCMaturityDate(Date lCMaturityDate) {
		this.lCMaturityDate = lCMaturityDate;
	}

	public String getlCNumber() {
		return lCNumber;
	}

	public void setlCNumber(String lCNumber) {
		this.lCNumber = lCNumber;
	}

	public String getLastBeneBank() {
		return lastBeneBank;
	}

	public void setLastBeneBank(String lastBeneBank) {
		this.lastBeneBank = lastBeneBank;
	}

	public String getLastBeneSwiftCode() {
		return lastBeneSwiftCode;
	}

	public void setLastBeneSwiftCode(String lastBeneSwiftCode) {
		this.lastBeneSwiftCode = lastBeneSwiftCode;
	}

	public String getLastBankCountry() {
		return lastBankCountry;
	}

	public void setLastBankCountry(String lastBankCountry) {
		this.lastBankCountry = lastBankCountry;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getDiscountingPeriod() {
		return discountingPeriod;
	}

	public void setDiscountingPeriod(String discountingPeriod) {
		this.discountingPeriod = discountingPeriod;
	}

	public String getConfirmationPeriod() {
		return confirmationPeriod;
	}

	public void setConfirmationPeriod(String confirmationPeriod) {
		this.confirmationPeriod = confirmationPeriod;
	}

	public String getFinancingPeriod() {
		return financingPeriod;
	}

	public void setFinancingPeriod(String financingPeriod) {
		this.financingPeriod = financingPeriod;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getApplicantCountry() {
		return applicantCountry;
	}

	public void setApplicantCountry(String applicantCountry) {
		this.applicantCountry = applicantCountry;
	}

	public String getApplicantContactPerson() {
		return applicantContactPerson;
	}

	public void setApplicantContactPerson(String applicantContactPerson) {
		this.applicantContactPerson = applicantContactPerson;
	}

	public String getApplicantContactPersonEmail() {
		return applicantContactPersonEmail;
	}

	public void setApplicantContactPersonEmail(String applicantContactPersonEmail) {
		this.applicantContactPersonEmail = applicantContactPersonEmail;
	}

	public String getBeneCountry() {
		return beneCountry;
	}

	public void setBeneCountry(String beneCountry) {
		this.beneCountry = beneCountry;
	}

	public String getBeneContactPerson() {
		return beneContactPerson;
	}

	public void setBeneContactPerson(String beneContactPerson) {
		this.beneContactPerson = beneContactPerson;
	}

	public String getBeneContactPersonEmail() {
		return beneContactPersonEmail;
	}

	public void setBeneContactPersonEmail(String beneContactPersonEmail) {
		this.beneContactPersonEmail = beneContactPersonEmail;
	}

	public String getBeneBankName() {
		return beneBankName;
	}

	public void setBeneBankName(String beneBankName) {
		this.beneBankName = beneBankName;
	}

	public String getBeneSwiftCode() {
		return beneSwiftCode;
	}

	public void setBeneSwiftCode(String beneSwiftCode) {
		this.beneSwiftCode = beneSwiftCode;
	}

	public String getBeneBankCountry() {
		return beneBankCountry;
	}

	public void setBeneBankCountry(String beneBankCountry) {
		this.beneBankCountry = beneBankCountry;
	}

	public String getLoadingCountry() {
		return loadingCountry;
	}

	public void setLoadingCountry(String loadingCountry) {
		this.loadingCountry = loadingCountry;
	}

	public String getLoadingPort() {
		return loadingPort;
	}

	public void setLoadingPort(String loadingPort) {
		this.loadingPort = loadingPort;
	}

	public String getDischargeCountry() {
		return dischargeCountry;
	}

	public void setDischargeCountry(String dischargeCountry) {
		this.dischargeCountry = dischargeCountry;
	}

	public String getDischargePort() {
		return dischargePort;
	}

	public void setDischargePort(String dischargePort) {
		this.dischargePort = dischargePort;
	}

	public String getLcChargesType() {
		return lcChargesType;
	}

	public void setLcChargesType(String lcChargesType) {
		this.lcChargesType = lcChargesType;
	}

	public String getLcProforma() {
		return lcProforma;
	}

	public void setLcProforma(String lcProforma) {
		this.lcProforma = lcProforma;
	}

	public String getPaymentPeriod() {
		return paymentPeriod;
	}

	public void setPaymentPeriod(String paymentPeriod) {
		this.paymentPeriod = paymentPeriod;
	}

}
