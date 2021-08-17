package com.nimai.email.entity;


import java.sql.Blob;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;



@Entity
@Table(name="nimai_mm_transaction")
public class NimaiLC {
	
	@Id 
	@Column(name="TRANSACTION_ID") 
	private String transactionId;
	
	@Column(name="USER_ID")
	private String userId; 
	
	@Column(name="REQUIREMENT_TYPE")
	private String requirementType;
	
	@Column(name="LC_ISSUANCE_BANK")
	private String lCIssuanceBank;
	
	@Column(name="LC_ISSUANCE_BRANCH")
	private String lCIssuanceBranch;
	
	@Column(name="SWIFT_CODE")
	private String swiftCode;
	
	@Column(name="LC_ISSUANCE_COUNTRY")
	private String lCIssuanceCountry;
	
	@Column(name="LC_ISSUING_DATE")
	@Temporal(value=TemporalType.TIMESTAMP)
	private Date lCIssuingDate;
	
	@Column(name="LC_EXPIRY_DATE")
	@Temporal(value=TemporalType.TIMESTAMP)
	private Date lCExpiryDate;
	
	@Column(name="LC_VALUE")
	private Double lCValue;
	
	@Column(name="LC_CURRENCY")
	private String lCCurrency;
	
	

	public Double getlCValue() {
		return lCValue;
	}
	public void setlCValue(Double lCValue) {
		this.lCValue = lCValue;
	}

	@Column(name="LAST_SHIPMENT_DATE")
	@Temporal(value=TemporalType.TIMESTAMP)
	private Date lastShipmentDate;
	
	@Column(name="NEGOTIATION_DATE")
	@Temporal(value=TemporalType.TIMESTAMP)
	private Date negotiationDate;
	
	@Column(name="PAYMENT_PERIOD")
	private String paymentPeriod;
	
	@Column(name="PAYMENT_TERMS")
	private String paymentTerms;
	
	@Column(name="TENOR_END_DATE")
	@Temporal(value=TemporalType.TIMESTAMP)
	private Date tenorEndDate;
	
	@Column(name="APPLICANT_NAME")
	private String applicantName;
	
	
	//@OneToMany
	@Column(name="APPLICANT_COUNTRY")
	private String applicantCountry;
	
	@Column(name="BENE_NAME")
	private String beneName;
	
	@Column(name="BENE_BANK_COUNTRY")
	private String beneBankCountry;
	
	@Column(name="BENE_BANK_NAME")
	private String beneBankName;
	
	@Column(name="BENE_SWIFT_CODE")
	private String beneSwiftCode;
	
	@Column(name="BENE_COUNTRY")
	private String beneCountry;
	
	@Column(name="LOADING_COUNTRY")
	private String loadingCountry;
	
	@Column(name="LOADING_PORT")
	private String loadingPort;
	
	@Column(name="DISCHARGE_COUNTRY")
	private String dischargeCountry;
	
	@Column(name="DISCHARGE_PORT")
	private String dischargePort;
	
	@Column(name="CHARGES_TYPE")
	private String chargesType;
	
	@Column(name="VALIDITY")
	private String validity;
	
	@Column(name="INSERTED_DATE")
	@Temporal(value=TemporalType.TIMESTAMP)
	private Date insertedDate;
	
	@Column(name="INSERTED_BY")
	private String insertedBy;
	
	@Column(name="MODIFIED_DATE")
	@Temporal(value=TemporalType.TIMESTAMP)
	private Date modifiedDate;
	
	@Column(name="MODIFIED_BY")
	private String modifiedBy;
	
	@Column(name="TRANSACTION_FLAG")
	private String transactionflag;
	
	@Column(name="TRANSACTION_STATUS")
	private String transactionStatus;
	
	@Column(name="confirmed_flag")
	private Integer confirmedFlag;
	
	
	
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
	public String getTransactionflag() {
		return transactionflag;
	}
	public void setTransactionflag(String transactionflag) {
		this.transactionflag = transactionflag;
	}
	public String getTransactionStatus() {
		return transactionStatus;
	}
	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}
	
	/*New  Fields*/
	@Column(name="branch_userid")
	private String branchUserId;
	
	@Column(name="branch_user_email")
	private String branchUserEmail;
	
	@Column(name="goods_type")
	private String goodsType;
	
	@Column(name="usance_days")
	private Integer usanceDays;
	
	@Column(name="start_date")
	@Temporal(value=TemporalType.TIMESTAMP)
	private Date startDate;
	
	@Column(name="end_date")
	@Temporal(value=TemporalType.TIMESTAMP)
	private Date endDate;
	
	@Column(name="original_tenor_days")
	private Integer originalTenorDays;
	
	@Column(name="refinancing_period")
	private String refinancingPeriod;
	
	@Column(name="lc_maturity_date")
	@Temporal(value=TemporalType.TIMESTAMP)
	private Date lcMaturityDate;
	
	@Column(name="lc_number")
	private String lcNumber;
	
	@Column(name="last_bene_bank")
	private String lastBeneBank;
	
	@Column(name="last_bene_swift_code")
	private String lastBeneSwiftCode;
	
	@Column(name="last_bank_country")
	private String lastBankCountry;
	
	@Column(name="remarks")
	private String remarks;
	
	@Column(name="discounting_period")
	private String discountingPeriod;
	
	@Column(name="confirmation_period")
	private String confirmationPeriod;
	
	@Column(name="financing_period")
	private String financingPeriod;
	
	@Column(name="lc_pro_forma")
	private String lcProForma;
	
	@Column(name="tenor_file")
	private String tenorFile;
	
	@Column(name="user_type")
	private String userType;
	
	@Column(name="applicant_contact_person")
	private String applicantContactPerson;
	
	@Column(name="applicant_contact_person_email")
	private String applicantContactPersonEmail;
	
	@Column(name="bene_contact_person")
	private String beneContactPerson;
	
	@Column(name="bene_contact_person_email")
	private String beneContactPersonEmail;
	
	
	
	
	public Integer getConfirmedFlag() {
		return confirmedFlag;
	}
	public void setConfirmedFlag(Integer confirmedFlag) {
		this.confirmedFlag = confirmedFlag;
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
	
	
	public String getValidity() {
		return validity;
	}
	public void setValidity(String validity) {
		this.validity = validity;
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((applicantContactPerson == null) ? 0 : applicantContactPerson.hashCode());
		result = prime * result + ((applicantContactPersonEmail == null) ? 0 : applicantContactPersonEmail.hashCode());
		result = prime * result + ((applicantCountry == null) ? 0 : applicantCountry.hashCode());
		result = prime * result + ((applicantName == null) ? 0 : applicantName.hashCode());
		result = prime * result + ((beneBankCountry == null) ? 0 : beneBankCountry.hashCode());
		result = prime * result + ((beneBankName == null) ? 0 : beneBankName.hashCode());
		result = prime * result + ((beneContactPerson == null) ? 0 : beneContactPerson.hashCode());
		result = prime * result + ((beneContactPersonEmail == null) ? 0 : beneContactPersonEmail.hashCode());
		result = prime * result + ((beneCountry == null) ? 0 : beneCountry.hashCode());
		result = prime * result + ((beneName == null) ? 0 : beneName.hashCode());
		result = prime * result + ((beneSwiftCode == null) ? 0 : beneSwiftCode.hashCode());
		result = prime * result + ((branchUserEmail == null) ? 0 : branchUserEmail.hashCode());
		result = prime * result + ((branchUserId == null) ? 0 : branchUserId.hashCode());
		result = prime * result + ((chargesType == null) ? 0 : chargesType.hashCode());
		result = prime * result + ((confirmationPeriod == null) ? 0 : confirmationPeriod.hashCode());
		result = prime * result + ((confirmedFlag == null) ? 0 : confirmedFlag.hashCode());
		result = prime * result + ((dischargeCountry == null) ? 0 : dischargeCountry.hashCode());
		result = prime * result + ((dischargePort == null) ? 0 : dischargePort.hashCode());
		result = prime * result + ((discountingPeriod == null) ? 0 : discountingPeriod.hashCode());
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + ((financingPeriod == null) ? 0 : financingPeriod.hashCode());
		result = prime * result + ((goodsType == null) ? 0 : goodsType.hashCode());
		result = prime * result + ((insertedBy == null) ? 0 : insertedBy.hashCode());
		result = prime * result + ((insertedDate == null) ? 0 : insertedDate.hashCode());
		result = prime * result + ((lCCurrency == null) ? 0 : lCCurrency.hashCode());
		result = prime * result + ((lCExpiryDate == null) ? 0 : lCExpiryDate.hashCode());
		result = prime * result + ((lCIssuanceBank == null) ? 0 : lCIssuanceBank.hashCode());
		result = prime * result + ((lCIssuanceBranch == null) ? 0 : lCIssuanceBranch.hashCode());
		result = prime * result + ((lCIssuanceCountry == null) ? 0 : lCIssuanceCountry.hashCode());
		result = prime * result + ((lCIssuingDate == null) ? 0 : lCIssuingDate.hashCode());
		result = prime * result + ((lCValue == null) ? 0 : lCValue.hashCode());
		result = prime * result + ((lastBankCountry == null) ? 0 : lastBankCountry.hashCode());
		result = prime * result + ((lastBeneBank == null) ? 0 : lastBeneBank.hashCode());
		result = prime * result + ((lastBeneSwiftCode == null) ? 0 : lastBeneSwiftCode.hashCode());
		result = prime * result + ((lastShipmentDate == null) ? 0 : lastShipmentDate.hashCode());
		result = prime * result + ((lcMaturityDate == null) ? 0 : lcMaturityDate.hashCode());
		result = prime * result + ((lcNumber == null) ? 0 : lcNumber.hashCode());
		result = prime * result + ((lcProForma == null) ? 0 : lcProForma.hashCode());
		result = prime * result + ((loadingCountry == null) ? 0 : loadingCountry.hashCode());
		result = prime * result + ((loadingPort == null) ? 0 : loadingPort.hashCode());
		result = prime * result + ((modifiedBy == null) ? 0 : modifiedBy.hashCode());
		result = prime * result + ((modifiedDate == null) ? 0 : modifiedDate.hashCode());
		result = prime * result + ((negotiationDate == null) ? 0 : negotiationDate.hashCode());
		result = prime * result + ((originalTenorDays == null) ? 0 : originalTenorDays.hashCode());
		result = prime * result + ((paymentPeriod == null) ? 0 : paymentPeriod.hashCode());
		result = prime * result + ((paymentTerms == null) ? 0 : paymentTerms.hashCode());
		result = prime * result + ((refinancingPeriod == null) ? 0 : refinancingPeriod.hashCode());
		result = prime * result + ((remarks == null) ? 0 : remarks.hashCode());
		result = prime * result + ((requirementType == null) ? 0 : requirementType.hashCode());
		result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
		result = prime * result + ((swiftCode == null) ? 0 : swiftCode.hashCode());
		result = prime * result + ((tenorEndDate == null) ? 0 : tenorEndDate.hashCode());
		result = prime * result + ((tenorFile == null) ? 0 : tenorFile.hashCode());
		result = prime * result + ((transactionId == null) ? 0 : transactionId.hashCode());
		result = prime * result + ((transactionStatus == null) ? 0 : transactionStatus.hashCode());
		result = prime * result + ((transactionflag == null) ? 0 : transactionflag.hashCode());
		result = prime * result + ((usanceDays == null) ? 0 : usanceDays.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		result = prime * result + ((userType == null) ? 0 : userType.hashCode());
		result = prime * result + ((validity == null) ? 0 : validity.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NimaiLC other = (NimaiLC) obj;
		if (applicantContactPerson == null) {
			if (other.applicantContactPerson != null)
				return false;
		} else if (!applicantContactPerson.equals(other.applicantContactPerson))
			return false;
		if (applicantContactPersonEmail == null) {
			if (other.applicantContactPersonEmail != null)
				return false;
		} else if (!applicantContactPersonEmail.equals(other.applicantContactPersonEmail))
			return false;
		if (applicantCountry == null) {
			if (other.applicantCountry != null)
				return false;
		} else if (!applicantCountry.equals(other.applicantCountry))
			return false;
		if (applicantName == null) {
			if (other.applicantName != null)
				return false;
		} else if (!applicantName.equals(other.applicantName))
			return false;
		if (beneBankCountry == null) {
			if (other.beneBankCountry != null)
				return false;
		} else if (!beneBankCountry.equals(other.beneBankCountry))
			return false;
		if (beneBankName == null) {
			if (other.beneBankName != null)
				return false;
		} else if (!beneBankName.equals(other.beneBankName))
			return false;
		if (beneContactPerson == null) {
			if (other.beneContactPerson != null)
				return false;
		} else if (!beneContactPerson.equals(other.beneContactPerson))
			return false;
		if (beneContactPersonEmail == null) {
			if (other.beneContactPersonEmail != null)
				return false;
		} else if (!beneContactPersonEmail.equals(other.beneContactPersonEmail))
			return false;
		if (beneCountry == null) {
			if (other.beneCountry != null)
				return false;
		} else if (!beneCountry.equals(other.beneCountry))
			return false;
		if (beneName == null) {
			if (other.beneName != null)
				return false;
		} else if (!beneName.equals(other.beneName))
			return false;
		if (beneSwiftCode == null) {
			if (other.beneSwiftCode != null)
				return false;
		} else if (!beneSwiftCode.equals(other.beneSwiftCode))
			return false;
		if (branchUserEmail == null) {
			if (other.branchUserEmail != null)
				return false;
		} else if (!branchUserEmail.equals(other.branchUserEmail))
			return false;
		if (branchUserId == null) {
			if (other.branchUserId != null)
				return false;
		} else if (!branchUserId.equals(other.branchUserId))
			return false;
		if (chargesType == null) {
			if (other.chargesType != null)
				return false;
		} else if (!chargesType.equals(other.chargesType))
			return false;
		if (confirmationPeriod == null) {
			if (other.confirmationPeriod != null)
				return false;
		} else if (!confirmationPeriod.equals(other.confirmationPeriod))
			return false;
		if (confirmedFlag == null) {
			if (other.confirmedFlag != null)
				return false;
		} else if (!confirmedFlag.equals(other.confirmedFlag))
			return false;
		if (dischargeCountry == null) {
			if (other.dischargeCountry != null)
				return false;
		} else if (!dischargeCountry.equals(other.dischargeCountry))
			return false;
		if (dischargePort == null) {
			if (other.dischargePort != null)
				return false;
		} else if (!dischargePort.equals(other.dischargePort))
			return false;
		if (discountingPeriod == null) {
			if (other.discountingPeriod != null)
				return false;
		} else if (!discountingPeriod.equals(other.discountingPeriod))
			return false;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (financingPeriod == null) {
			if (other.financingPeriod != null)
				return false;
		} else if (!financingPeriod.equals(other.financingPeriod))
			return false;
		if (goodsType == null) {
			if (other.goodsType != null)
				return false;
		} else if (!goodsType.equals(other.goodsType))
			return false;
		if (insertedBy == null) {
			if (other.insertedBy != null)
				return false;
		} else if (!insertedBy.equals(other.insertedBy))
			return false;
		if (insertedDate == null) {
			if (other.insertedDate != null)
				return false;
		} else if (!insertedDate.equals(other.insertedDate))
			return false;
		if (lCCurrency == null) {
			if (other.lCCurrency != null)
				return false;
		} else if (!lCCurrency.equals(other.lCCurrency))
			return false;
		if (lCExpiryDate == null) {
			if (other.lCExpiryDate != null)
				return false;
		} else if (!lCExpiryDate.equals(other.lCExpiryDate))
			return false;
		if (lCIssuanceBank == null) {
			if (other.lCIssuanceBank != null)
				return false;
		} else if (!lCIssuanceBank.equals(other.lCIssuanceBank))
			return false;
		if (lCIssuanceBranch == null) {
			if (other.lCIssuanceBranch != null)
				return false;
		} else if (!lCIssuanceBranch.equals(other.lCIssuanceBranch))
			return false;
		if (lCIssuanceCountry == null) {
			if (other.lCIssuanceCountry != null)
				return false;
		} else if (!lCIssuanceCountry.equals(other.lCIssuanceCountry))
			return false;
		if (lCIssuingDate == null) {
			if (other.lCIssuingDate != null)
				return false;
		} else if (!lCIssuingDate.equals(other.lCIssuingDate))
			return false;
		if (lCValue == null) {
			if (other.lCValue != null)
				return false;
		} else if (!lCValue.equals(other.lCValue))
			return false;
		if (lastBankCountry == null) {
			if (other.lastBankCountry != null)
				return false;
		} else if (!lastBankCountry.equals(other.lastBankCountry))
			return false;
		if (lastBeneBank == null) {
			if (other.lastBeneBank != null)
				return false;
		} else if (!lastBeneBank.equals(other.lastBeneBank))
			return false;
		if (lastBeneSwiftCode == null) {
			if (other.lastBeneSwiftCode != null)
				return false;
		} else if (!lastBeneSwiftCode.equals(other.lastBeneSwiftCode))
			return false;
		if (lastShipmentDate == null) {
			if (other.lastShipmentDate != null)
				return false;
		} else if (!lastShipmentDate.equals(other.lastShipmentDate))
			return false;
		if (lcMaturityDate == null) {
			if (other.lcMaturityDate != null)
				return false;
		} else if (!lcMaturityDate.equals(other.lcMaturityDate))
			return false;
		if (lcNumber == null) {
			if (other.lcNumber != null)
				return false;
		} else if (!lcNumber.equals(other.lcNumber))
			return false;
		if (lcProForma == null) {
			if (other.lcProForma != null)
				return false;
		} else if (!lcProForma.equals(other.lcProForma))
			return false;
		if (loadingCountry == null) {
			if (other.loadingCountry != null)
				return false;
		} else if (!loadingCountry.equals(other.loadingCountry))
			return false;
		if (loadingPort == null) {
			if (other.loadingPort != null)
				return false;
		} else if (!loadingPort.equals(other.loadingPort))
			return false;
		if (modifiedBy == null) {
			if (other.modifiedBy != null)
				return false;
		} else if (!modifiedBy.equals(other.modifiedBy))
			return false;
		if (modifiedDate == null) {
			if (other.modifiedDate != null)
				return false;
		} else if (!modifiedDate.equals(other.modifiedDate))
			return false;
		if (negotiationDate == null) {
			if (other.negotiationDate != null)
				return false;
		} else if (!negotiationDate.equals(other.negotiationDate))
			return false;
		if (originalTenorDays == null) {
			if (other.originalTenorDays != null)
				return false;
		} else if (!originalTenorDays.equals(other.originalTenorDays))
			return false;
		if (paymentPeriod == null) {
			if (other.paymentPeriod != null)
				return false;
		} else if (!paymentPeriod.equals(other.paymentPeriod))
			return false;
		if (paymentTerms == null) {
			if (other.paymentTerms != null)
				return false;
		} else if (!paymentTerms.equals(other.paymentTerms))
			return false;
		if (refinancingPeriod == null) {
			if (other.refinancingPeriod != null)
				return false;
		} else if (!refinancingPeriod.equals(other.refinancingPeriod))
			return false;
		if (remarks == null) {
			if (other.remarks != null)
				return false;
		} else if (!remarks.equals(other.remarks))
			return false;
		if (requirementType == null) {
			if (other.requirementType != null)
				return false;
		} else if (!requirementType.equals(other.requirementType))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		if (swiftCode == null) {
			if (other.swiftCode != null)
				return false;
		} else if (!swiftCode.equals(other.swiftCode))
			return false;
		if (tenorEndDate == null) {
			if (other.tenorEndDate != null)
				return false;
		} else if (!tenorEndDate.equals(other.tenorEndDate))
			return false;
		if (tenorFile == null) {
			if (other.tenorFile != null)
				return false;
		} else if (!tenorFile.equals(other.tenorFile))
			return false;
		if (transactionId == null) {
			if (other.transactionId != null)
				return false;
		} else if (!transactionId.equals(other.transactionId))
			return false;
		if (transactionStatus == null) {
			if (other.transactionStatus != null)
				return false;
		} else if (!transactionStatus.equals(other.transactionStatus))
			return false;
		if (transactionflag == null) {
			if (other.transactionflag != null)
				return false;
		} else if (!transactionflag.equals(other.transactionflag))
			return false;
		if (usanceDays == null) {
			if (other.usanceDays != null)
				return false;
		} else if (!usanceDays.equals(other.usanceDays))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		if (userType == null) {
			if (other.userType != null)
				return false;
		} else if (!userType.equals(other.userType))
			return false;
		if (validity == null) {
			if (other.validity != null)
				return false;
		} else if (!validity.equals(other.validity))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "NimaiLC [transactionId=" + transactionId + ", userId=" + userId + ", requirementType=" + requirementType
				+ ", lCIssuanceBank=" + lCIssuanceBank + ", lCIssuanceBranch=" + lCIssuanceBranch + ", swiftCode="
				+ swiftCode + ", lCIssuanceCountry=" + lCIssuanceCountry + ", lCIssuingDate=" + lCIssuingDate
				+ ", lCExpiryDate=" + lCExpiryDate + ", lCValue=" + lCValue + ", lCCurrency=" + lCCurrency
				+ ", lastShipmentDate=" + lastShipmentDate + ", negotiationDate=" + negotiationDate + ", paymentPeriod="
				+ paymentPeriod + ", paymentTerms=" + paymentTerms + ", tenorEndDate=" + tenorEndDate
				+ ", applicantName=" + applicantName + ", applicantCountry=" + applicantCountry + ", beneName="
				+ beneName + ", beneBankCountry=" + beneBankCountry + ", beneBankName=" + beneBankName
				+ ", beneSwiftCode=" + beneSwiftCode + ", beneCountry=" + beneCountry + ", loadingCountry="
				+ loadingCountry + ", loadingPort=" + loadingPort + ", dischargeCountry=" + dischargeCountry
				+ ", dischargePort=" + dischargePort + ", chargesType=" + chargesType + ", validity=" + validity
				+ ", insertedDate=" + insertedDate + ", insertedBy=" + insertedBy + ", modifiedDate=" + modifiedDate
				+ ", modifiedBy=" + modifiedBy + ", transactionflag=" + transactionflag + ", transactionStatus="
				+ transactionStatus + ", confirmedFlag=" + confirmedFlag + ", branchUserId=" + branchUserId
				+ ", branchUserEmail=" + branchUserEmail + ", goodsType=" + goodsType + ", usanceDays=" + usanceDays
				+ ", startDate=" + startDate + ", endDate=" + endDate + ", originalTenorDays=" + originalTenorDays
				+ ", refinancingPeriod=" + refinancingPeriod + ", lcMaturityDate=" + lcMaturityDate + ", lcNumber="
				+ lcNumber + ", lastBeneBank=" + lastBeneBank + ", lastBeneSwiftCode=" + lastBeneSwiftCode
				+ ", lastBankCountry=" + lastBankCountry + ", remarks=" + remarks + ", discountingPeriod="
				+ discountingPeriod + ", confirmationPeriod=" + confirmationPeriod + ", financingPeriod="
				+ financingPeriod + ", lcProForma=" + lcProForma + ", tenorFile=" + tenorFile + ", userType=" + userType
				+ ", applicantContactPerson=" + applicantContactPerson + ", applicantContactPersonEmail="
				+ applicantContactPersonEmail + ", beneContactPerson=" + beneContactPerson + ", beneContactPersonEmail="
				+ beneContactPersonEmail + "]";
	}


	
	

	
}
