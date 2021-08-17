package com.nimai.admin.payload;

import java.util.List;

public class RightsMappingRequest {
	
	private Integer roleId;
	private List<Integer> rightsId;
	
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public List<Integer> getRightsId() {
		return rightsId;
	}
	public void setRightsId(List<Integer> rightsId) {
		this.rightsId = rightsId;
	}
	
	
	

}
