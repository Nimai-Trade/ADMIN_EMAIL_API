package com.nimai.admin.payload;

public class VasUpdateRequestBody {
  private String countryName;
  
  private String vasNUmber;
  
  private int vasid;
  
  private String status;
  
  private String userId;
  
  private String makerComment;
  
  private String checkerComment;
  
  private int subcriptionId;
  
  private String customerType;
  
  private String vasMakerComment;
  
  private String vasCheckerComment;
  
  private String sPLanSerialNumber;
  
  public String getsPLanSerialNumber() {
    return this.sPLanSerialNumber;
  }
  
  public void setsPLanSerialNumber(String sPLanSerialNumber) {
    this.sPLanSerialNumber = sPLanSerialNumber;
  }
  
  public String getVasNUmber() {
    return this.vasNUmber;
  }
  
  public void setVasNUmber(String vasNUmber) {
    this.vasNUmber = vasNUmber;
  }
  
  public int getVasid() {
    return this.vasid;
  }
  
  public void setVasid(int vasid) {
    this.vasid = vasid;
  }
  
  public String getVasMakerComment() {
    return this.vasMakerComment;
  }
  
  public void setVasMakerComment(String vasMakerComment) {
    this.vasMakerComment = vasMakerComment;
  }
  
  public String getVasCheckerComment() {
    return this.vasCheckerComment;
  }
  
  public void setVasCheckerComment(String vasCheckerComment) {
    this.vasCheckerComment = vasCheckerComment;
  }
  
  public String getCustomerType() {
    return this.customerType;
  }
  
  public void setCustomerType(String customerType) {
    this.customerType = customerType;
  }
  
  public String getCountryName() {
    return this.countryName;
  }
  
  public void setCountryName(String countryName) {
    this.countryName = countryName;
  }
  
  public String getStatus() {
    return this.status;
  }
  
  public void setStatus(String status) {
    this.status = status;
  }
  
  public String getUserId() {
    return this.userId;
  }
  
  public void setUserId(String userId) {
    this.userId = userId;
  }
  
  public String getMakerComment() {
    return this.makerComment;
  }
  
  public void setMakerComment(String makerComment) {
    this.makerComment = makerComment;
  }
  
  public String getCheckerComment() {
    return this.checkerComment;
  }
  
  public void setCheckerComment(String checkerComment) {
    this.checkerComment = checkerComment;
  }
  
  public int getSubcriptionId() {
    return this.subcriptionId;
  }
  
  public void setSubcriptionId(int subcriptionId) {
    this.subcriptionId = subcriptionId;
  }
}
