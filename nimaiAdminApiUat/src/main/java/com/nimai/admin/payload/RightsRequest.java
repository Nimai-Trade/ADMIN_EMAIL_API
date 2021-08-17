package com.nimai.admin.payload;

public class RightsRequest {

	private Integer rightId;
	private String rightName;
	private String rightStatus;
	private String rightShortName;

	public Integer getRightId() {
		return rightId;
	}
	public void setRightId(Integer rightId) {
		this.rightId = rightId;
	}
	public String getRightName() {
		return rightName;
	}
	public void setRightName(String rightName) {
		this.rightName = rightName;
	}
	public String getRightStatus() {
		return rightStatus;
	}
	public void setRightStatus(String rightStatus) {
		this.rightStatus = rightStatus;
	}
	public String getRightShortName() {
		return rightShortName;
	}
	public void setRightShortName(String rightShortName) {
		this.rightShortName = rightShortName;
	}
	
	
}
