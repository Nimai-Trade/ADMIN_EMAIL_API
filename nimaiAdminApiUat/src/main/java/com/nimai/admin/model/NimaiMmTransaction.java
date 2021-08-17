/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nimai.admin.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Sahadeo
 */
@Entity
@Table(name = "nimai_mm_transaction")
@NamedQueries({
    @NamedQuery(name = "NimaiMmTransaction.findAll", query = "SELECT n FROM NimaiMmTransaction n")})
public class NimaiMmTransaction implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "transaction_id")
    private String transactionId;
    @Size(max = 45)
    @Column(name = "requirement_type")
    private String requirementType;
    @Size(max = 100)
    @Column(name = "lc_issuance_bank")
    private String lcIssuanceBank;
    @Size(max = 100)
    @Column(name = "lc_issuance_branch")
    private String lcIssuanceBranch;
    @Size(max = 100)
    @Column(name = "swift_code")
    private String swiftCode;
    @Size(max = 100)
    @Column(name = "lc_issuance_country")
    private String lcIssuanceCountry;
    @Column(name = "lc_value")
    private long lcValue;
    @Size(max = 45)
    @Column(name = "lc_currency")
    private String lcCurrency;
    @Column(name = "lc_issuing_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lcIssuingDate;
    @Column(name = "last_shipment_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastShipmentDate;
    @Size(max = 45)
    @Column(name = "goods_type")
    private String goodsType;
    @Column(name = "negotiation_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date negotiationDate;
    @Column(name = "lc_expiry_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lcExpiryDate;
    @Column(name = "usance_days")
    private Integer usanceDays;
    @Size(max = 100)
    @Column(name = "payment_terms")
    private String paymentTerms;
    @Column(name = "start_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    @Column(name = "end_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;
    @Column(name = "original_tenor_days")
    private Integer originalTenorDays;
    @Size(max = 45)
    @Column(name = "refinancing_period")
    private String refinancingPeriod;
    @Column(name = "lc_maturity_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lcMaturityDate;
    @Size(max = 45)
    @Column(name = "lc_number")
    private String lcNumber;
    @Size(max = 100)
    @Column(name = "last_bene_bank")
    private String lastBeneBank;
    @Size(max = 100)
    @Column(name = "last_bene_swift_code")
    private String lastBeneSwiftCode;
    @Size(max = 100)
    @Column(name = "last_bank_country")
    private String lastBankCountry;
    @Size(max = 100)
    @Column(name = "remarks")
    private String remarks;
    @Size(max = 45)
    @Column(name = "discounting_period")
    private String discountingPeriod;
    @Size(max = 45)
    @Column(name = "confirmation_period")
    private String confirmationPeriod;
    @Size(max = 45)
    @Column(name = "financing_period")
    private String financingPeriod;
    @Size(max = 100)
    @Column(name = "user_type")
    private String userType;
    @Size(max = 100)
    @Column(name = "applicant_name")
    private String applicantName;
    @Size(max = 100)
    @Column(name = "applicant_country")
    private String applicantCountry;
    @Size(max = 100)
    @Column(name = "applicant_contact_person")
    private String applicantContactPerson;
    @Size(max = 100)
    @Column(name = "applicant_contact_person_email")
    private String applicantContactPersonEmail;
    @Size(max = 100)
    @Column(name = "bene_name")
    private String beneName;
    @Size(max = 100)
    @Column(name = "bene_country")
    private String beneCountry;
    @Size(max = 100)
    @Column(name = "bene_contact_person")
    private String beneContactPerson;
    @Size(max = 100)
    @Column(name = "bene_contact_person_email")
    private String beneContactPersonEmail;
    @Size(max = 100)
    @Column(name = "bene_bank_name")
    private String beneBankName;
    @Size(max = 100)
    @Column(name = "bene_swift_code")
    private String beneSwiftCode;
    @Size(max = 100)
    @Column(name = "bene_bank_country")
    private String beneBankCountry;
    @Size(max = 100)
    @Column(name = "loading_country")
    private String loadingCountry;
    @Size(max = 100)
    @Column(name = "loading_port")
    private String loadingPort;
    @Size(max = 100)
    @Column(name = "discharge_country")
    private String dischargeCountry;
    @Size(max = 100)
    @Column(name = "discharge_port")
    private String dischargePort;
    @Size(max = 100)
    @Column(name = "charges_type")
    private String chargesType;
    @Size(max = 45)
    @Column(name = "validity")
    private String validity;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "lc_pro_forma")
    private String lcProForma;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "tenor_file")
    private String tenorFile;
    @Basic(optional = false)
    @NotNull
    @Column(name = "inserted_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date insertedDate;
    @Size(max = 45)
    @Column(name = "inserted_by")
    private String insertedBy;
    @Column(name = "modified_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    @Size(max = 45)
    @Column(name = "modified_by")
    private String modifiedBy;
    @Size(max = 45)
    @Column(name = "transaction_flag")
    private String transactionFlag;
    @Size(max = 45)
    @Column(name = "transaction_status")
    private String transactionStatus;
    @Size(max = 100)
    @Column(name = "branch_userid")
    private String branchUserid;
    @Size(max = 100)
    @Column(name = "branch_user_email")
    private String branchUserEmail;
    @Size(max = 255)
    @Column(name = "payment_period")
    private String paymentPeriod;
    @Column(name = "tenor_end_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tenorEndDate;
    @Size(max = 50)
    @Column(name = "quotation_placed")
    private String quotationPlaced;
    @Size(max = 50)
    @Column(name = "quotation_accepted")
    private String quotationAccepted;
    @Column(name = "quotation_received")
    private Integer quotationReceived;
    @Column(name = "confirmed_flag")
    private Integer confirmedFlag;
    @Column(name = "accepted_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date acceptedOn;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "status_reason")
    private String statusReason;
    @JoinColumn(name = "user_id", referencedColumnName = "USERID")
    @ManyToOne
    private NimaiMCustomer userId;
//    @OneToMany(mappedBy = "transactionId1")
//    private List<NimaiFTransgood> nimaiFTransgoodList;
//    @OneToMany(mappedBy = "transactionId")
//    private List<NimaiFTranscountries> nimaiFTranscountriesList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "transactionId")
    private List<NimaiMQuotation> nimaiMQuotationList;
    
    

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public NimaiMmTransaction() {
    }

    public NimaiMmTransaction(String transactionId) {
        this.transactionId = transactionId;
    }

    public NimaiMmTransaction(String transactionId, Date insertedDate) {
        this.transactionId = transactionId;
        this.insertedDate = insertedDate;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getRequirementType() {
        return requirementType;
    }

    public void setRequirementType(String requirementType) {
        this.requirementType = requirementType;
    }

    public String getLcIssuanceBank() {
        return lcIssuanceBank;
    }

    public void setLcIssuanceBank(String lcIssuanceBank) {
        this.lcIssuanceBank = lcIssuanceBank;
    }

    public String getLcIssuanceBranch() {
        return lcIssuanceBranch;
    }

    public void setLcIssuanceBranch(String lcIssuanceBranch) {
        this.lcIssuanceBranch = lcIssuanceBranch;
    }

    public String getSwiftCode() {
        return swiftCode;
    }

    public void setSwiftCode(String swiftCode) {
        this.swiftCode = swiftCode;
    }

    public String getLcIssuanceCountry() {
        return lcIssuanceCountry;
    }

    public void setLcIssuanceCountry(String lcIssuanceCountry) {
        this.lcIssuanceCountry = lcIssuanceCountry;
    }

    public long getLcValue() {
        return lcValue;
    }

    public void setLcValue(long lcValue) {
        this.lcValue = lcValue;
    }

    public String getLcCurrency() {
        return lcCurrency;
    }

    public void setLcCurrency(String lcCurrency) {
        this.lcCurrency = lcCurrency;
    }

    public Date getLcIssuingDate() {
        return lcIssuingDate;
    }

    public void setLcIssuingDate(Date lcIssuingDate) {
        this.lcIssuingDate = lcIssuingDate;
    }

    public Date getLastShipmentDate() {
        return lastShipmentDate;
    }

    public void setLastShipmentDate(Date lastShipmentDate) {
        this.lastShipmentDate = lastShipmentDate;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public Date getNegotiationDate() {
        return negotiationDate;
    }

    public void setNegotiationDate(Date negotiationDate) {
        this.negotiationDate = negotiationDate;
    }

    public Date getLcExpiryDate() {
        return lcExpiryDate;
    }

    public void setLcExpiryDate(Date lcExpiryDate) {
        this.lcExpiryDate = lcExpiryDate;
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

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
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

    public String getBeneName() {
        return beneName;
    }

    public void setBeneName(String beneName) {
        this.beneName = beneName;
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

    public String getChargesType() {
        return chargesType;
    }

    public void setChargesType(String chargesType) {
        this.chargesType = chargesType;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
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

    public String getBranchUserid() {
        return branchUserid;
    }

    public void setBranchUserid(String branchUserid) {
        this.branchUserid = branchUserid;
    }

    public String getBranchUserEmail() {
        return branchUserEmail;
    }

    public void setBranchUserEmail(String branchUserEmail) {
        this.branchUserEmail = branchUserEmail;
    }

    public String getPaymentPeriod() {
        return paymentPeriod;
    }

    public void setPaymentPeriod(String paymentPeriod) {
        this.paymentPeriod = paymentPeriod;
    }

    public Date getTenorEndDate() {
        return tenorEndDate;
    }

    public void setTenorEndDate(Date tenorEndDate) {
        this.tenorEndDate = tenorEndDate;
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

    public Integer getQuotationReceived() {
        return quotationReceived;
    }

    public void setQuotationReceived(Integer quotationReceived) {
        this.quotationReceived = quotationReceived;
    }

    public Integer getConfirmedFlag() {
        return confirmedFlag;
    }

    public void setConfirmedFlag(Integer confirmedFlag) {
        this.confirmedFlag = confirmedFlag;
    }

    public Date getAcceptedOn() {
        return acceptedOn;
    }

    public void setAcceptedOn(Date acceptedOn) {
        this.acceptedOn = acceptedOn;
    }

    public String getStatusReason() {
        return statusReason;
    }

    public void setStatusReason(String statusReason) {
        this.statusReason = statusReason;
    }

    public NimaiMCustomer getUserId() {
        return userId;
    }

    public void setUserId(NimaiMCustomer userId) {
        this.userId = userId;
    }

//    public List<NimaiFTransgood> getNimaiFTransgoodList() {
//        return nimaiFTransgoodList;
//    }
//
//    public void setNimaiFTransgoodList(List<NimaiFTransgood> nimaiFTransgoodList) {
//        this.nimaiFTransgoodList = nimaiFTransgoodList;
//    }
//
//    public List<NimaiFTranscountries> getNimaiFTranscountriesList() {
//        return nimaiFTranscountriesList;
//    }
//
//    public void setNimaiFTranscountriesList(List<NimaiFTranscountries> nimaiFTranscountriesList) {
//        this.nimaiFTranscountriesList = nimaiFTranscountriesList;
//    }

    public List<NimaiMQuotation> getNimaiMQuotationList() {
        return nimaiMQuotationList;
    }

    public void setNimaiMQuotationList(List<NimaiMQuotation> nimaiMQuotationList) {
        this.nimaiMQuotationList = nimaiMQuotationList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (transactionId != null ? transactionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NimaiMmTransaction)) {
            return false;
        }
        NimaiMmTransaction other = (NimaiMmTransaction) object;
        if ((this.transactionId == null && other.transactionId != null) || (this.transactionId != null && !this.transactionId.equals(other.transactionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nimai.admin.model.NimaiMmTransaction[ transactionId=" + transactionId + " ]";
    }
    
}
