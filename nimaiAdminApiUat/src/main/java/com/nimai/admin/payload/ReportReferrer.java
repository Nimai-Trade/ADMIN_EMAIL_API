package com.nimai.admin.payload;

public class ReportReferrer {
  private String country;
  
  private String referrer_userID;
  
  private String referrer_Company;
  
  private String first_Name;
  
  private String last_Name;
  
  private int total_References;
  
  private int approved_References;
  
  private int rejected_References;
  
  private int pending_References;
  
  private double earning;
  
  private String ccy;
  
  private String rm;
  
  private String referrerEmailId;
  
  private String account_status;
  
  public String getAccount_status() {
    return this.account_status;
  }
  
  public void setAccount_status(String account_status) {
    this.account_status = account_status;
  }
  
  public String getReferrerEmailId() {
    return this.referrerEmailId;
  }
  
  public void setReferrerEmailId(String referrerEmailId) {
    this.referrerEmailId = referrerEmailId;
  }
  
  public String getReferrer_userID() {
    return this.referrer_userID;
  }
  
  public void setReferrer_userID(String referrer_userID) {
    this.referrer_userID = referrer_userID;
  }
  
  public String getCountry() {
    return this.country;
  }
  
  public void setCountry(String country) {
    this.country = country;
  }
  
  public String getReferrer_Company() {
    return this.referrer_Company;
  }
  
  public void setReferrer_Company(String referrer_Company) {
    this.referrer_Company = referrer_Company;
  }
  
  public String getFirst_Name() {
    return this.first_Name;
  }
  
  public void setFirst_Name(String first_Name) {
    this.first_Name = first_Name;
  }
  
  public String getLast_Name() {
    return this.last_Name;
  }
  
  public void setLast_Name(String last_Name) {
    this.last_Name = last_Name;
  }
  
  public int getTotal_References() {
    return this.total_References;
  }
  
  public void setTotal_References(int total_References) {
    this.total_References = total_References;
  }
  
  public int getApproved_References() {
    return this.approved_References;
  }
  
  public void setApproved_References(int approved_References) {
    this.approved_References = approved_References;
  }
  
  public int getRejected_References() {
    return this.rejected_References;
  }
  
  public void setRejected_References(int rejected_References) {
    this.rejected_References = rejected_References;
  }
  
  public int getPending_References() {
    return this.pending_References;
  }
  
  public void setPending_References(int pending_References) {
    this.pending_References = pending_References;
  }
  
  public double getEarning() {
    return this.earning;
  }
  
  public void setEarning(double earning) {
    this.earning = earning;
  }
  
  public String getCcy() {
    return this.ccy;
  }
  
  public void setCcy(String ccy) {
    this.ccy = ccy;
  }
  
  public String getRm() {
    return this.rm;
  }
  
  public void setRm(String rm) {
    this.rm = rm;
  }
}
