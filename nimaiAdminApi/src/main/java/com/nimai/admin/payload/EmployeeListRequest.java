package com.nimai.admin.payload;

import java.util.Date;
import java.util.List;

public class EmployeeListRequest {

	private Integer empId;
	private String empCode;
	private String firstName;
	private String lastName;
	private String empMobile;

	private String empEmail;
	private String empRole[];
	private String status;
	private String empRoleName;

	private String department;
	private String designation;
	private String reportingManager;
	private String country[];

	private String address1;
	private String address2;
	private String createdBy;
	private Date createdDate;
	private String countryExt;
	private String lastModifiedBy;

	private List<RoleList> roleInfo;

	public Integer getEmpId() {
		return empId;
	}

	public void setEmpId(Integer empId) {
		this.empId = empId;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
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

	public String[] getEmpRole() {
		return empRole;
	}

	public void setEmpRole(String[] empRole) {
		this.empRole = empRole;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getReportingManager() {
		return reportingManager;
	}

	public void setReportingManager(String reportingManager) {
		this.reportingManager = reportingManager;
	}

	public String[] getCountry() {
		return country;
	}

	public void setCountry(String[] country) {
		this.country = country;
	}

	public String getEmpRoleName() {
		return empRoleName;
	}

	public void setEmpRoleName(String empRoleName) {
		this.empRoleName = empRoleName;
	}

	public String getCountryExt() {
		return countryExt;
	}

	public void setCountryExt(String countryExt) {
		this.countryExt = countryExt;
	}

	public List<RoleList> getRoleInfo() {
		return roleInfo;
	}

	public void setRoleInfo(List<RoleList> roleInfo) {
		this.roleInfo = roleInfo;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

}
