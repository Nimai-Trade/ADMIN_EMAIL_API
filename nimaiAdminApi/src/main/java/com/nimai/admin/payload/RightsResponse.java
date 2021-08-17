package com.nimai.admin.payload;

import java.util.Date;

public class RightsResponse {
	
	private Integer rightId;
	private String rightName;
	private String rightStatus;
	private String createdBy;
	private Date createdDate;
	private String rightShortName;

	
	public RightsResponse() {
		super();
	}
	
	
	public RightsResponse(Integer rightId) {
		super();
		this.rightId = rightId;
	}



	public RightsResponse(Integer rightId, String rightName, String rightShortName) {
		super();
		this.rightId = rightId;
		this.rightName = rightName;
		this.rightShortName = rightShortName;
	}


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
	public String getRightShortName() {
		return rightShortName;
	}
	public void setRightShortName(String rightShortName) {
		this.rightShortName = rightShortName;
	}

	
}
