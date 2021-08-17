package com.nimai.admin.payload;

import javax.validation.constraints.NotBlank;

public class UserRightsRequest {
	
	@NotBlank
	private String empCode;
	
	@NotBlank
	private String roleName;
	
	
	
	public String getEmpCode() {
		return empCode;
	}
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	

}
