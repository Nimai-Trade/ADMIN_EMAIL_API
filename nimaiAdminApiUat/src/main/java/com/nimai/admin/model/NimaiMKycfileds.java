package com.nimai.admin.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Basic;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;

@Entity
@Table(name = "nimai_f_kycfields")
public class NimaiMKycfileds
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "FIELD_ID")
    private int id;
    @Column(name = "USER_ID")
    private String userId;
    @Column(name = "CUST_TURNOVER")
    private String custTurnover;
    @Column(name = "IMPORT_VOL")
    private String importVolume;
    @Column(name = "EXPORT_VOL")
    private String exportVolume;
    @Column(name = "YEARLY_LC_VOLUME")
    private String yearlyLCVolume;
    @Column(name = "USED_LC_ISSUANCE")
    private String usedLCIssuance;
    @Column(name = "INSERTED_DATE")
    private Date insertedDate;
    @Column(name = "MODIFIED_DATE")
    private Date modifiedDat;
    @Column(name = "INSERTED_BY")
    private String insertedBy;
    @Column(name = "MODIFIED_BY")
    private String modifiedBy;
    
    public String getInsertedBy() {
        return this.insertedBy;
    }
    
    public void setInsertedBy(final String insertedBy) {
        this.insertedBy = insertedBy;
    }
    
    public String getModifiedBy() {
        return this.modifiedBy;
    }
    
    public void setModifiedBy(final String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }
    
    public Date getInsertedDate() {
        return this.insertedDate;
    }
    
    public void setInsertedDate(final Date insertedDate) {
        this.insertedDate = insertedDate;
    }
    
    public Date getModifiedDat() {
        return this.modifiedDat;
    }
    
    public void setModifiedDat(final Date modifiedDat) {
        this.modifiedDat = modifiedDat;
    }
    
    public int getId() {
        return this.id;
    }
    
    public void setId(final int id) {
        this.id = id;
    }
    
    public String getUserId() {
        return this.userId;
    }
    
    public void setUserId(final String userId) {
        this.userId = userId;
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