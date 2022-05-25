package com.nimai.admin.payload;

import java.util.Date;
import java.util.List;

public class VasResponse {
  private Integer vasid;
  
  private String customerType;
  
  private String countryName;
  
  private String planName;
  
  private String description1;
  
  private String description2;
  
  private String description3;
  
  private String description4;
  
  private String description5;
  
  private float pricing;
  
  private Boolean flag;
  
  private String status;
  
  private String createdBy;
  
  private Date createdDate;
  
  private String modifiedBy;
  
  private Date modifiedDate;
  
  private String countryCurrency;
  
  private String[] country;
  
  private List<CountryList> countryList;
  
  public List<CountryList> getCountryList() {
    return this.countryList;
  }
  
  public void setCountryList(List<CountryList> countryList) {
    this.countryList = countryList;
  }
  
  public String[] getCountry() {
    return this.country;
  }
  
  public void setCountry(String[] country) {
    this.country = country;
  }
  
  public String getCustomerType() {
    return this.customerType;
  }
  
  public void setCustomerType(String customerType) {
    this.customerType = customerType;
  }
  
  public Integer getVasid() {
    return this.vasid;
  }
  
  public void setVasid(Integer vasid) {
    this.vasid = vasid;
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
  
  public float getPricing() {
    return this.pricing;
  }
  
  public void setPricing(float pricing) {
    this.pricing = pricing;
  }
  
  public Boolean getFlag() {
    return this.flag;
  }
  
  public void setFlag(Boolean flag) {
    this.flag = flag;
  }
  
  public String getStatus() {
    return this.status;
  }
  
  public void setStatus(String status) {
    this.status = status;
  }
  
  public String getCreatedBy() {
    return this.createdBy;
  }
  
  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }
  
  public Date getCreatedDate() {
    return this.createdDate;
  }
  
  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
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
  
  public String getCountryCurrency() {
    return this.countryCurrency;
  }
  
  public void setCountryCurrency(String countryCurrency) {
    this.countryCurrency = countryCurrency;
  }
}
