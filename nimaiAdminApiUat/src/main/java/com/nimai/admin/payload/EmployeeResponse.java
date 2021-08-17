package com.nimai.admin.payload;

public class EmployeeResponse {

	private String empCode;
	private String empName;
	private String empEmail;
	private String flag;
	private String link;
	private String message;
	
	

	public EmployeeResponse() {
		super();
	}

	public EmployeeResponse(String empCode, String empName, String empEmail, String flag, String message) {
		super();
		this.empCode = empCode;
		this.empName = empName;
		this.empEmail = empEmail;
		this.flag = flag;
		//this.link = link;
		this.message = message;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getEmpEmail() {
		return empEmail;
	}

	public void setEmpEmail(String empEmail) {
		this.empEmail = empEmail;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	

}
