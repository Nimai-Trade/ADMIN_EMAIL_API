package com.nimai.admin.payload;

import java.util.Date;

public class RoleRightsMappingResponse {

	private Integer userRightsId;
	private String roleName;
	private String rightsName;	
	private String createdBy;
	private Date createdDate;
	private String lastModifiedBy;
	private Date lastModifiedDate;
	private String status;

	public Integer getUserRightsId() {
		return userRightsId;
	}

	public void setUserRightsId(Integer userRightsId) {
		this.userRightsId = userRightsId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRightsName() {
		return rightsName;
	}

	public void setRightsName(String rightsName) {
		this.rightsName = rightsName;
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

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	@Override
	public String toString() {
		return "RoleRightsMappingResponse [userRightsId=" + userRightsId + ", roleName=" + roleName + ", rightsName="
				+ rightsName + ", status=" + status + ", createdBy=" + createdBy + ", createdDate=" + createdDate
				+ ", lastModifiedBy=" + lastModifiedBy + ", lastModifiedDate=" + lastModifiedDate + "]";
	}

}
