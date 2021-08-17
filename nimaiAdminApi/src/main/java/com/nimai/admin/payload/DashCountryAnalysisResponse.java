package com.nimai.admin.payload;

public class DashCountryAnalysisResponse {
	private String countryName;
	private Integer totalCustomers;
	private Integer totalUnderwriters;
	private Integer totalTrxn;
	private Double cumulativeLcValue;

	public DashCountryAnalysisResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public Integer getTotalCustomers() {
		return totalCustomers;
	}

	public void setTotalCustomers(Integer totalCustomers) {
		this.totalCustomers = totalCustomers;
	}

	public Integer getTotalUnderwriters() {
		return totalUnderwriters;
	}

	public void setTotalUnderwriters(Integer totalUnderwriters) {
		this.totalUnderwriters = totalUnderwriters;
	}

	public Integer getTotalTrxn() {
		return totalTrxn;
	}

	public void setTotalTrxn(Integer totalTrxn) {
		this.totalTrxn = totalTrxn;
	}

	public Double getCumulativeLcValue() {
		return cumulativeLcValue;
	}

	public void setCumulativeLcValue(Double cumulativeLcValue) {
		this.cumulativeLcValue = cumulativeLcValue;
	}

}
