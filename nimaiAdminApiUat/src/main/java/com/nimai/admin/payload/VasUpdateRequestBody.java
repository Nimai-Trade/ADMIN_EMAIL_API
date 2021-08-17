package com.nimai.admin.payload;

public class VasUpdateRequestBody {

	private String countryName;
	private int vasid;
	private String status;
	private String userId;
	private String makerComment;
	private String checkerComment;
	private int subcriptionId;
	private String customerType;
	private String vasMakerComment;
	private String vasCheckerComment;

	public String getVasMakerComment() {
		return vasMakerComment;
	}

	public void setVasMakerComment(String vasMakerComment) {
		this.vasMakerComment = vasMakerComment;
	}

	public String getVasCheckerComment() {
		return vasCheckerComment;
	}

	public void setVasCheckerComment(String vasCheckerComment) {
		this.vasCheckerComment = vasCheckerComment;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public int getVasid() {
		return vasid;
	}

	public void setVasid(int vasid) {
		this.vasid = vasid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public VasUpdateRequestBody() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMakerComment() {
		return makerComment;
	}

	public void setMakerComment(String makerComment) {
		this.makerComment = makerComment;
	}

	public String getCheckerComment() {
		return checkerComment;
	}

	public void setCheckerComment(String checkerComment) {
		this.checkerComment = checkerComment;
	}

	public int getSubcriptionId() {
		return subcriptionId;
	}

	public void setSubcriptionId(int subcriptionId) {
		this.subcriptionId = subcriptionId;
	}

}
