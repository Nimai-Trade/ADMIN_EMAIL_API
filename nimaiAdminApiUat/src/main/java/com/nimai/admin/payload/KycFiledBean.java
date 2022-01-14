package com.nimai.admin.payload;

public class KycFiledBean {
	
	private String userId;
	
	private int id;
	
	private String custTurnover;
	
	private String importVolume;
	
	private String exportVolume;
	
	private String yearlyLCVolume;
	
	private String usedLCIssuance;
	
	private String insertedBy;
	
	private String modifiedby;
	
	
	
	
	

	public String getInsertedBy() {
		return insertedBy;
	}

	public void setInsertedBy(String insertedBy) {
		this.insertedBy = insertedBy;
	}

	public String getModifiedby() {
		return modifiedby;
	}

	public void setModifiedby(String modifiedby) {
		this.modifiedby = modifiedby;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
