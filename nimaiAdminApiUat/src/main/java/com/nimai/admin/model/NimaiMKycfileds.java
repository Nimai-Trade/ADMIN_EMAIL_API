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
@Table(name="nimai_f_kycfields")
public class NimaiMKycfileds {

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
			return insertedBy;
		}
		public void setInsertedBy(String insertedBy) {
			this.insertedBy = insertedBy;
		}
		public String getModifiedBy() {
			return modifiedBy;
		}
		public void setModifiedBy(String modifiedBy) {
			this.modifiedBy = modifiedBy;
		}
	public Date getInsertedDate() {
		return insertedDate;
	}
	public void setInsertedDate(Date insertedDate) {
		this.insertedDate = insertedDate;
	}

	public Date getModifiedDat() {
		return modifiedDat;
	}
	public void setModifiedDat(Date modifiedDat) {
		this.modifiedDat = modifiedDat;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getCustTurnover() {
		return custTurnover;
	}
	public void setCustTurnover(String custTurnover) {
		this.custTurnover = custTurnover;
	}
	public String getImportVolume() {
		return importVolume;
	}
	public void setImportVolume(String importVolume) {
		this.importVolume = importVolume;
	}
	public String getExportVolume() {
		return exportVolume;
	}
	public void setExportVolume(String exportVolume) {
		this.exportVolume = exportVolume;
	}
	public String getYearlyLCVolume() {
		return yearlyLCVolume;
	}
	public void setYearlyLCVolume(String yearlyLCVolume) {
		this.yearlyLCVolume = yearlyLCVolume;
	}
	public String getUsedLCIssuance() {
		return usedLCIssuance;
	}
	public void setUsedLCIssuance(String usedLCIssuance) {
		this.usedLCIssuance = usedLCIssuance;
	}
	
	
}
	
	
	
	

