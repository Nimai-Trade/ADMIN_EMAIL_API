package com.nimai.admin.payload;

public class AssignRmResponse {

	private String code;
	private String value;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public AssignRmResponse(String code, String value) {
		super();
		this.code = code;
		this.value = value;
	}
	
	

}
