package com.nimai.admin.payload;

public class UserDetailRequest {
	
	private String empId;
	private String empCode;
	private String empName;
	private String empMobile;
	private String empEmail;
	private String empRole;
	private String empDept [];
	private String loginRequired;
	private String address1;
	private String address2;
	
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getEmpMobile() {
		return empMobile;
	}
	public void setEmpMobile(String empMobile) {
		this.empMobile = empMobile;
	}
	public String getEmpEmail() {
		return empEmail;
	}
	public void setEmpEmail(String empEmail) {
		this.empEmail = empEmail;
	}
	public String getEmpRole() {
		return empRole;
	}
	public void setEmpRole(String empRole) {
		this.empRole = empRole;
	}
	
	public String[] getEmpDept() {
		return empDept;
	}
	public void setEmpDept(String[] empDept) {
		this.empDept = empDept;
	}
	public String getLoginRequired() {
		return loginRequired;
	}
	public void setLoginRequired(String loginRequired) {
		this.loginRequired = loginRequired;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getEmpCode() {
		return empCode;
	}
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	
	
	
	

}
