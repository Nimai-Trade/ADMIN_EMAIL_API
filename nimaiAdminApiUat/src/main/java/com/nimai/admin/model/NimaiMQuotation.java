/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nimai.admin.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@Table(name = "nimai_m_quotation")
@NamedQueries({
    @NamedQuery(name = "NimaiMQuotation.findAll", query = "SELECT n FROM NimaiMQuotation n")})
public class NimaiMQuotation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "quotation_id")
    private Integer quotationId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "bank_userid")
    private String bankUserid;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "confirmation_charges")
    private Float confirmationCharges;
    @Size(max = 45)
    @Column(name = "conf_chgs_issuance_to_negot")
    private String confChgsIssuanceToNegot;
    @Size(max = 45)
    @Column(name = "conf_chgs_issuance_to_exp")
    private String confChgsIssuanceToExp;
    @Size(max = 45)
    @Column(name = "conf_chgs_issuance_to_matur")
    private String confChgsIssuanceToMatur;
    @Column(name = "discounting_charges")
    private Float discountingCharges;
    @Column(name = "refinancing_charges")
    private Float refinancingCharges;
    @Column(name = "banker_accept_charges")
    private Float bankerAcceptCharges;
    @Column(name = "applicable_benchmark")
    private Float applicableBenchmark;
    @Size(max = 45)
    @Column(name = "comments_benchmark")
    private String commentsBenchmark;
    @Column(name = "negotiation_charges_fixed")
    private Float negotiationChargesFixed;
    @Column(name = "negotiation_charges_perct")
    private Float negotiationChargesPerct;
    @Column(name = "doc_handling_charges")
    private Float docHandlingCharges;
    @Column(name = "other_charges")
    private Float otherCharges;
    @Size(max = 45)
    @Column(name = "charges_type")
    private String chargesType;
    @Column(name = "minimum_transaction_charges")
    private Float minimumTransactionCharges;
    @Column(name = "inserted_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date insertedDate;
    @Size(max = 50)
    @Column(name = "inserted_by")
    private String insertedBy;
    @Column(name = "modified_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    @Size(max = 50)
    @Column(name = "modified_by")
    private String modifiedBy;
    @Column(name = "min_transaction_charges")
    private Float minTransactionCharges;
    @Column(name = "total_quote_value")
    private Float totalQuoteValue;
    @Column(name = "validity_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date validityDate;
    @Size(max = 50)
    @Column(name = "quotation_status")
    private String quotationStatus;
    @Size(max = 100)
    @Column(name = "bank_name")
    private String bankName;
    @Size(max = 100)
    @Column(name = "branch_name")
    private String branchName;
    @Size(max = 100)
    @Column(name = "swift_code")
    private String swiftCode;
    @Size(max = 100)
    @Column(name = "country_name")
    private String countryName;
    @Size(max = 100)
    @Column(name = "email_address")
    private String emailAddress;
    @Size(max = 100)
    @Column(name = "telephone")
    private String telephone;
    @Size(max = 100)
    @Column(name = "mobile_number")
    private String mobileNumber;
    @Size(max = 100)
    @Column(name = "first_name")
    private String firstName;
    @Size(max = 100)
    @Column(name = "last_name")
    private String lastName;
    @Size(max = 100)
    @Column(name = "currency")
    private String currency;
    @JoinColumn(name = "transaction_id", referencedColumnName = "transaction_id")
    @ManyToOne(optional = false)
    private NimaiMmTransaction transactionId;
    @JoinColumn(name = "userid", referencedColumnName = "USERID")
    @ManyToOne(optional = false)
    private NimaiMCustomer userid;

    public NimaiMQuotation() {
    }

    public NimaiMQuotation(Integer quotationId) {
        this.quotationId = quotationId;
    }

    public NimaiMQuotation(Integer quotationId, String bankUserid) {
        this.quotationId = quotationId;
        this.bankUserid = bankUserid;
    }

    public Integer getQuotationId() {
        return quotationId;
    }

    public void setQuotationId(Integer quotationId) {
        this.quotationId = quotationId;
    }

    public String getBankUserid() {
        return bankUserid;
    }

    public void setBankUserid(String bankUserid) {
        this.bankUserid = bankUserid;
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

    public Float getMinimumTransactionCharges() {
        return minimumTransactionCharges;
    }

    public void setMinimumTransactionCharges(Float minimumTransactionCharges) {
        this.minimumTransactionCharges = minimumTransactionCharges;
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

    public Float getMinTransactionCharges() {
        return minTransactionCharges;
    }

    public void setMinTransactionCharges(Float minTransactionCharges) {
        this.minTransactionCharges = minTransactionCharges;
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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public NimaiMmTransaction getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(NimaiMmTransaction transactionId) {
        this.transactionId = transactionId;
    }

    public NimaiMCustomer getUserid() {
        return userid;
    }

    public void setUserid(NimaiMCustomer userid) {
        this.userid = userid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (quotationId != null ? quotationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NimaiMQuotation)) {
            return false;
        }
        NimaiMQuotation other = (NimaiMQuotation) object;
        if ((this.quotationId == null && other.quotationId != null) || (this.quotationId != null && !this.quotationId.equals(other.quotationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nimai.admin.model.NimaiMQuotation[ quotationId=" + quotationId + " ]";
    }
    
}
