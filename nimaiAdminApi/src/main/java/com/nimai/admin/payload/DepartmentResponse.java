package com.nimai.admin.payload;

public class DepartmentResponse {

	private Integer deptId;
	private String deptName;
	private String deptStatus;

	

	public DepartmentResponse(Integer deptId, String deptName, String deptStatus) {
		super();
		this.deptId = deptId;
		this.deptName = deptName;
		this.deptStatus = deptStatus;
	}

	public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDeptStatus() {
		return deptStatus;
	}

	public void setDeptStatus(String deptStatus) {
		this.deptStatus = deptStatus;
	}
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((deptId == null) ? 0 : deptId.hashCode());
		result = prime * result + ((deptName == null) ? 0 : deptName.hashCode());
		result = prime * result + ((deptStatus == null) ? 0 : deptStatus.hashCode());
//		System.out.println(result);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DepartmentResponse other = (DepartmentResponse) obj;
		if (deptId == null) {
			if (other.deptId != null)
				return false;
		} else if (!deptId.equals(other.deptId))
			return false;
		if (deptName == null) {
			if (other.deptName != null)
				return false;
		} else if (!deptName.equals(other.deptName))
			return false;
		if (deptStatus == null) {
			if (other.deptStatus != null)
				return false;
		} else if (!deptStatus.equals(other.deptStatus))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DepartmentResponse [deptId=" + deptId + ", deptName=" + deptName + ", deptStatus=" + deptStatus + "]";
	}

}
