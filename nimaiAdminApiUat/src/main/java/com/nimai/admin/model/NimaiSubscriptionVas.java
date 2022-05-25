package com.nimai.admin.model;

import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "nimai_subscription_vas")
public class NimaiSubscriptionVas {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "id")
  private Integer id;
  
  @Column(name = "userid")
  private String userId;
  
  @Column(name = "subscription_id")
  private String subscriptionId;
  
  @Column(name = "vas_id")
  private Integer vasId;
  
  @Column(name = "country_name")
  private String countryName;
  
  @Column(name = "plan_name")
  private String planName;
  
  @Column(name = "description_1")
  private String description1;
  
  @Column(name = "description_2")
  private String description2;
  
  @Column(name = "description_3")
  private String description3;
  
  @Column(name = "description_4")
  private String description4;
  
  @Column(name = "description_5")
  private String description5;
  
  @Column(name = "currency")
  private String currency;
  
  @Column(name = "pricing")
  private Float pricing;
  
  @Column(name = "status")
  private String status;
  
  @Column(name = "inserted_by")
  private String insertedBy;
  
  @Column(name = "inserted_date")
  private Date insertedDate;
  
  @Column(name = "modified_by")
  private String modifiedBy;
  
  @Column(name = "modified_date")
  private Date modifiedDate;
  
  @Column(name = "PAYMENT_APPROVED_BY")
  private String paymentApprovedBy;
  
  @Column(name = "PAYMENT_APPROVAL_DATE")
  private Date paymentApprovalDate;
  
  @Column(name = "MODE")
  private String mode;
  
  @Column(name = "PAYMENT_STATUS")
  private String paymentSts;
  
  @Column(name = "CHECKER_COMMENT")
  private String checkerComment;
  
  @Column(name = "MAKER_COMMENT")
  private String makerComment;
  
  @Column(name = "PAYMENT_TXN_ID")
  private String paymentTxnId;
  
  @Column(name = "SPLAN_VAS_FLAG")
  private Integer sPlanVasFlag;
  
  @Column(name = "SPL_SERIAL_NUMBER")
  private Integer sPLanSerialNumber;
  
  public Integer getsPLanSerialNumber() {
    return this.sPLanSerialNumber;
  }
  
  public void setsPLanSerialNumber(Integer sPLanSerialNumber) {
    this.sPLanSerialNumber = sPLanSerialNumber;
  }
  
  public Integer getsPlanVasFlag() {
    return this.sPlanVasFlag;
  }
  
  public void setsPlanVasFlag(Integer sPlanVasFlag) {
    this.sPlanVasFlag = sPlanVasFlag;
  }
  
  public String getCheckerComment() {
    return this.checkerComment;
  }
  
  public void setCheckerComment(String checkerComment) {
    this.checkerComment = checkerComment;
  }
  
  public String getMakerComment() {
    return this.makerComment;
  }
  
  public void setMakerComment(String makerComment) {
    this.makerComment = makerComment;
  }
  
  public Date getPaymentApprovalDate() {
    return this.paymentApprovalDate;
  }
  
  public void setPaymentApprovalDate(Date paymentApprovalDate) {
    this.paymentApprovalDate = paymentApprovalDate;
  }
  
  public String getPaymentApprovedBy() {
    return this.paymentApprovedBy;
  }
  
  public void setPaymentApprovedBy(String paymentApprovedBy) {
    this.paymentApprovedBy = paymentApprovedBy;
  }
  
  public String getMode() {
    return this.mode;
  }
  
  public void setMode(String mode) {
    this.mode = mode;
  }
  
  public String getPaymentSts() {
    return this.paymentSts;
  }
  
  public void setPaymentSts(String paymentSts) {
    this.paymentSts = paymentSts;
  }
  
  public String getPaymentTxnId() {
    return this.paymentTxnId;
  }
  
  public void setPaymentTxnId(String paymentTxnId) {
    this.paymentTxnId = paymentTxnId;
  }
  
  public Integer getId() {
    return this.id;
  }
  
  public void setId(Integer id) {
    this.id = id;
  }
  
  public String getUserId() {
    return this.userId;
  }
  
  public void setUserId(String userId) {
    this.userId = userId;
  }
  
  public String getSubscriptionId() {
    return this.subscriptionId;
  }
  
  public void setSubscriptionId(String subscriptionId) {
    this.subscriptionId = subscriptionId;
  }
  
  public Integer getVasId() {
    return this.vasId;
  }
  
  public void setVasId(Integer vasId) {
    this.vasId = vasId;
  }
  
  public String getCountryName() {
    return this.countryName;
  }
  
  public void setCountryName(String countryName) {
    this.countryName = countryName;
  }
  
  public String getPlanName() {
    return this.planName;
  }
  
  public void setPlanName(String planName) {
    this.planName = planName;
  }
  
  public String getDescription1() {
    return this.description1;
  }
  
  public void setDescription1(String description1) {
    this.description1 = description1;
  }
  
  public String getDescription2() {
    return this.description2;
  }
  
  public void setDescription2(String description2) {
    this.description2 = description2;
  }
  
  public String getDescription3() {
    return this.description3;
  }
  
  public void setDescription3(String description3) {
    this.description3 = description3;
  }
  
  public String getDescription4() {
    return this.description4;
  }
  
  public void setDescription4(String description4) {
    this.description4 = description4;
  }
  
  public String getDescription5() {
    return this.description5;
  }
  
  public void setDescription5(String description5) {
    this.description5 = description5;
  }
  
  public String getCurrency() {
    return this.currency;
  }
  
  public void setCurrency(String currency) {
    this.currency = currency;
  }
  
  public Float getPricing() {
    return this.pricing;
  }
  
  public void setPricing(Float pricing) {
    this.pricing = pricing;
  }
  
  public String getStatus() {
    return this.status;
  }
  
  public void setStatus(String status) {
    this.status = status;
  }
  
  public String getInsertedBy() {
    return this.insertedBy;
  }
  
  public void setInsertedBy(String insertedBy) {
    this.insertedBy = insertedBy;
  }
  
  public Date getInsertedDate() {
    return this.insertedDate;
  }
  
  public void setInsertedDate(Date insertedDate) {
    this.insertedDate = insertedDate;
  }
  
  public String getModifiedBy() {
    return this.modifiedBy;
  }
  
  public void setModifiedBy(String modifiedBy) {
    this.modifiedBy = modifiedBy;
  }
  
  public Date getModifiedDate() {
    return this.modifiedDate;
  }
  
  public void setModifiedDate(Date modifiedDate) {
    this.modifiedDate = modifiedDate;
  }
  
  public String toString() {
    return "NimaiSubscriptionVas [id=" + this.id + ", userId=" + this.userId + ", subscriptionId=" + this.subscriptionId + ", vasId=" + this.vasId + ", countryName=" + this.countryName + ", planName=" + this.planName + ", description1=" + this.description1 + ", description2=" + this.description2 + ", description3=" + this.description3 + ", description4=" + this.description4 + ", description5=" + this.description5 + ", currency=" + this.currency + ", pricing=" + this.pricing + ", status=" + this.status + ", insertedBy=" + this.insertedBy + ", insertedDate=" + this.insertedDate + ", modifiedBy=" + this.modifiedBy + ", modifiedDate=" + this.modifiedDate + ", paymentApprovedBy=" + this.paymentApprovedBy + ", paymentApprovalDate=" + this.paymentApprovalDate + ", mode=" + this.mode + ", paymentSts=" + this.paymentSts + ", checkerComment=" + this.checkerComment + ", makerComment=" + this.makerComment + ", paymentTxnId=" + this.paymentTxnId + ", sPlanVasFlag=" + this.sPlanVasFlag + ", sPLanSerialNumber=" + this.sPLanSerialNumber + "]";
  }
}
