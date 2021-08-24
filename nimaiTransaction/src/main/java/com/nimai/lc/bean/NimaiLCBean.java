package com.nimai.lc.bean;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;


public class NimaiLCBean implements Serializable {
	private static final long serialVersionUID = 1L;
    private String transactionId;
    //private String OldTransactionId; 
    private String userId; 
    private String requirementType;
    private String lCIssuanceBank;
	private String lCIssuanceBranch;
    private String swiftCode;
	private String lCIssuanceCountry;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date lCIssuingDate;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date lCExpiryDate;
	private Double lCValue;
	private String lCCurrency;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date lastShipmentDate;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date negotiationDate;
	private String paymentPeriod;
	private String paymentTerms;
	private Date tenorEndDate;
	private String applicantName;
	private String applicantCountry;
	private String beneName;
	private String beneBankCountry;
	private String beneBankName;
	private String beneSwiftCode;
	private String beneCountry;
	private String loadingCountry;
	private String loadingPort;
	private String dischargeCountry;
	private String dischargePort;
	private String chargesType;
	private Date validity;
	private Integer confirmedFlag;
	
	
	
	public Integer getConfirmedFlag() {
		return confirmedFlag;
	}
	public void setConfirmedFlag(Integer confirmedFlag) {
		this.confirmedFlag = confirmedFlag;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date insertedDate;
	private String insertedBy;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date modifiedDate;
	private String modifiedBy;
	private String transactionFlag;
	private String transactionStatus;
	
	
	
	/*public String getOldTransactionId() {
		return OldTransactionId;
	}
	public void setOldTransactionId(String oldTransactionId) {
		OldTransactionId = oldTransactionId;
	}*/
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
	public static long getSerialversionuid() {
		return serialVersionUID;
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
	public String getlCIssuanceBranch() {
		return lCIssuanceBranch;
	}
	public void setlCIssuanceBranch(String lCIssuanceBranch) {
		this.lCIssuanceBranch = lCIssuanceBranch;
	}
	public String getSwiftCode() {
		return swiftCode;
	}
	public void setSwiftCode(String swiftCode) {
		this.swiftCode = swiftCode;
	}
	public String getlCIssuanceCountry() {
		return lCIssuanceCountry;
	}
	public void setlCIssuanceCountry(String lCIssuanceCountry) {
		this.lCIssuanceCountry = lCIssuanceCountry;
	}
	public Date getlCIssuingDate() {
		return lCIssuingDate;
	}
	public void setlCIssuingDate(Date lCIssuingDate) {
		this.lCIssuingDate = lCIssuingDate;
	}
	public Date getlCExpiryDate() {
		return lCExpiryDate;
	}
	public void setlCExpiryDate(Date lCExpiryDate) {
		this.lCExpiryDate = lCExpiryDate;
	}
	public Double getlCValue() {
		return lCValue;
	}
	public void setlCValue(Double lCValue) {
		this.lCValue = lCValue;
	}
	public String getlCCurrency() {
		return lCCurrency;
	}
	public void setlCCurrency(String lCCurrency) {
		this.lCCurrency = lCCurrency;
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
	public String getPaymentPeriod() {
		return paymentPeriod;
	}
	public void setPaymentPeriod(String paymentPeriod) {
		this.paymentPeriod = paymentPeriod;
	}
	public String getPaymentTerms() {
		return paymentTerms;
	}
	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
	}
	public Date getTenorEndDate() {
		return tenorEndDate;
	}
	public void setTenorEndDate(Date tenorEndDate) {
		this.tenorEndDate = tenorEndDate;
	}
	public String getApplicantName() {
		return applicantName;
	}
	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}
	public String getApplicantCountry() {
		return applicantCountry;
	}
	public void setApplicantCountry(String applicantCountry) {
		this.applicantCountry = applicantCountry;
	}
	public String getBeneName() {
		return beneName;
	}
	public void setBeneName(String beneName) {
		this.beneName = beneName;
	}
	public String getBeneBankCountry() {
		return beneBankCountry;
	}
	public void setBeneBankCountry(String beneBankCountry) {
		this.beneBankCountry = beneBankCountry;
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
	public String getBeneCountry() {
		return beneCountry;
	}
	public void setBeneCountry(String beneCountry) {
		this.beneCountry = beneCountry;
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
	public String getChargesType() {
		return chargesType;
	}
	public void setChargesType(String chargesType) {
		this.chargesType = chargesType;
	}
	public Date getValidity() {
		return validity;
	}
	public void setValidity(Date validity) {
		this.validity = validity;
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
	public String getTransactionFlag() {
		return transactionFlag;
	}
	public void setTransactionFlag(String transactionFlag) {
		this.transactionFlag = transactionFlag;
	}
	public String getTransactionStatus() {
		return transactionStatus;
	}
	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}
	
	private String branchUserId;
	private String branchUserEmail;
	private String goodsType;
	private Integer usanceDays;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date startDate;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date endDate;
	private Integer originalTenorDays;
	private String refinancingPeriod;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date lcMaturityDate;
	private String lcNumber;
	private String lastBeneBank;
	private String lastBeneSwiftCode;
	private String lastBankCountry;
	private String remarks;
	private String discountingPeriod;
	private String confirmationPeriod;
	private String financingPeriod;
	private String lcProForma;
	private String tenorFile;
	private String userType;
	private String applicantContactPerson;
	private String applicantContactPersonEmail;
	private String beneContactPerson;
	private String beneContactPersonEmail;
	private String quotationPlaced;
	private String quotationAccepted;
	private String statusReason;
	
	
	
	public String getStatusReason() {
		return statusReason;
	}
	public void setStatusReason(String statusReason) {
		this.statusReason = statusReason;
	}
	public String getQuotationPlaced() {
		return quotationPlaced;
	}
	public void setQuotationPlaced(String quotationPlaced) {
		this.quotationPlaced = quotationPlaced;
	}
	public String getQuotationAccepted() {
		return quotationAccepted;
	}
	public void setQuotationAccepted(String quotationAccepted) {
		this.quotationAccepted = quotationAccepted;
	}
	public String getBranchUserId() {
		return branchUserId;
	}
	public void setBranchUserId(String branchUserId) {
		this.branchUserId = branchUserId;
	}
	public String getBranchUserEmail() {
		return branchUserEmail;
	}
	public void setBranchUserEmail(String branchUserEmail) {
		this.branchUserEmail = branchUserEmail;
	}
	public String getGoodsType() {
		return goodsType;
	}
	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}
	public Integer getUsanceDays() {
		return usanceDays;
	}
	public void setUsanceDays(Integer usanceDays) {
		this.usanceDays = usanceDays;
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
	public Date getLcMaturityDate() {
		return lcMaturityDate;
	}
	public void setLcMaturityDate(Date lcMaturityDate) {
		this.lcMaturityDate = lcMaturityDate;
	}
	public String getLcNumber() {
		return lcNumber;
	}
	public void setLcNumber(String lcNumber) {
		this.lcNumber = lcNumber;
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
	public String getLcProForma() {
		return lcProForma;
	}
	public void setLcProForma(String lcProForma) {
		this.lcProForma = lcProForma;
	}
	public String getTenorFile() {
		return tenorFile;
	}
	public void setTenorFile(String tenorFile) {
		this.tenorFile = tenorFile;
	}
	
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
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
	
	@Override
    public String toString() {
        return transactionId + ", " + requirementType + ", " + lCIssuanceBank + ", " + lCIssuanceBranch + ", " +swiftCode;//The remaining fields
    }
}
