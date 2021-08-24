package com.nimai.lc.entity;

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
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.nimai.lc.bean.NimaiLCBean;

@Entity
@Table(name="temp_transaction")
@NamedStoredProcedureQuery(name = "move_to_master", procedureName = "move_to_master", parameters = {
		@StoredProcedureParameter(mode = ParameterMode.IN, name = "inp_transaction_id", type = String.class),
		@StoredProcedureParameter(mode = ParameterMode.IN, name = "inp_userid", type = String.class),
		@StoredProcedureParameter(mode = ParameterMode.OUT, name = "validation_message", type = String.class)})
		@NamedStoredProcedureQuery(name = "clone_transaction", procedureName = "clone_transaction", parameters = {
		@StoredProcedureParameter(mode = ParameterMode.IN, name = "inp_transaction_id", type = String.class),
		@StoredProcedureParameter(mode = ParameterMode.IN, name = "updated_transaction_id", type = String.class)})
        
public class NimaiLC {
	
	@Id 
	@Column(name="TRANSACTION_ID") 
	private String transactionId;
	
	//@Column(name="OLD_TRANSACTION_ID")
	//private String OldTransactionId; 
	
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
	private Date lCIssuingDate;
	
	@Column(name="LC_EXPIRY_DATE")
	@Temporal(value=TemporalType.TIMESTAMP)
	private Date lCExpiryDate;
	
	@Column(name="LC_VALUE")
	private Double lCValue;
	
	@Column(name="LC_CURRENCY")
	private String lCCurrency;
	
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
	private Date validity;
	
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
	
	
	
	
	/*public String getOldTransactionId() {
		return OldTransactionId;
	}
	public void setOldTransactionId(String oldTransactionId) {
		OldTransactionId = oldTransactionId;
	}*/
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
        return transactionId ;//The remaining fields
    }
	
}
