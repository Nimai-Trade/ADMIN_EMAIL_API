package com.nimai.admin.payload;

public class KycFiledBean
{
    private String custUserId;
    private String userId;
    private int id;
    private String custTurnover;
    private String importVolume;
    private String exportVolume;
    private String yearlyLCVolume;
    private String usedLCIssuance;
    private String insertedBy;
    private String modifiedby;
 
    
    
    public String getUserId() {
        return this.userId;
    }
    
    public void setUserId(final String userId) {
        this.userId = userId;
    }
    
    public String getCustUserId() {
        return this.custUserId;
    }
    
    public void setCustUserId(final String custUserId) {
        this.custUserId = custUserId;
    }
    
    public String getInsertedBy() {
        return this.insertedBy;
    }
    
    public void setInsertedBy(final String insertedBy) {
        this.insertedBy = insertedBy;
    }
    
    public String getModifiedby() {
        return this.modifiedby;
    }
    
    public void setModifiedby(final String modifiedby) {
        this.modifiedby = modifiedby;
    }
    
    public int getId() {
        return this.id;
    }
    
    public void setId(final int id) {
        this.id = id;
    }
    
    public String getCustTurnover() {
        return this.custTurnover;
    }
    
    public void setCustTurnover(final String custTurnover) {
        this.custTurnover = custTurnover;
    }
    
    public String getImportVolume() {
        return this.importVolume;
    }
    
    public void setImportVolume(final String importVolume) {
        this.importVolume = importVolume;
    }
    
    public String getExportVolume() {
        return this.exportVolume;
    }
    
    public void setExportVolume(final String exportVolume) {
        this.exportVolume = exportVolume;
    }
    
    public String getYearlyLCVolume() {
        return this.yearlyLCVolume;
    }
    
    public void setYearlyLCVolume(final String yearlyLCVolume) {
        this.yearlyLCVolume = yearlyLCVolume;
    }
    
    public String getUsedLCIssuance() {
        return this.usedLCIssuance;
    }
    
    public void setUsedLCIssuance(final String usedLCIssuance) {
        this.usedLCIssuance = usedLCIssuance;
    }
}