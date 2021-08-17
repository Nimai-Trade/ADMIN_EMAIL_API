package com.nimai.admin.payload;

public class OwenerBean {
	
	private String mdFirstName;
	private String mdLastName;
	private String designation;
	public String getMdFirstName() {
		return mdFirstName;
	}
	public void setMdFirstName(String mdFirstName) {
		this.mdFirstName = mdFirstName;
	}
	public String getMdLastName() {
		return mdLastName;
	}
	public void setMdLastName(String mdLastName) {
		this.mdLastName = mdLastName;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	
	public OwenerBean(String mdFirstName, String mdLastName, String designation) {
		this.mdFirstName = mdFirstName;
		this.mdLastName = mdLastName;
		this.designation = designation;
		
	}

}
