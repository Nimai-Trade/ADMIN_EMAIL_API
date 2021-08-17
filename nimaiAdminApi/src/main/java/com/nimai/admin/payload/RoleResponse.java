package com.nimai.admin.payload;

import java.util.Date;

public class RoleResponse {

	private Integer roleId;
	private String roleName;
	private String roleStatus;
	private String createdBy;
	private Date createdDate;
	private String roleShortName;	
	
	public RoleResponse() {
		super();
	}

	public RoleResponse(Integer roleId, String roleName, String roleShortName) {
		super();
		this.roleId = roleId;
		this.roleName = roleName;
		this.roleShortName = roleShortName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleStatus() {
		return roleStatus;
	}

	public void setRoleStatus(String roleStatus) {
		this.roleStatus = roleStatus;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
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

	public String getRoleShortName() {
		return roleShortName;
	}

	public void setRoleShortName(String roleShortName) {
		this.roleShortName = roleShortName;
	}

}
