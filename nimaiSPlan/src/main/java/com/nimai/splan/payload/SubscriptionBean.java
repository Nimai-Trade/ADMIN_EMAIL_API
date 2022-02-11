package com.nimai.splan.payload;

public class SubscriptionBean {

	private String userId;
	private String subscriptionId;
	private String subscriptionName;
	private int subscriptionAmount;
	private String lcCount;
	private String remark;
	private String status;
	private String insertedBy;
	private String modifiedBy;
	private String insertedDate;
	private String modifiedDate;
	private int subscriptionValidity;
	private String subsidiaries;
	private String relationshipManager;
	private String customerSupport;
	private int year;
	private int month;
	private String eventName;
	private String emailStatus;
	private String emailID;
	private String modeOfPayment;
	private String flag;
	private String coupenCode;
	private int vasAmount;
	private Double discount;
	private Double grandAmount;
	private int discountId;
	private int isVasApplied;
	

	public int getIsVasApplied() {
		return isVasApplied;
	}

	public void setIsVasApplied(int isVasApplied) {
		this.isVasApplied = isVasApplied;
	}

	public int getDiscountId() {
		return discountId;
	}

	public void setDiscountId(int discountId) {
		this.discountId = discountId;
	}

	public int getVasAmount() {
		return vasAmount;
	}

	public void setVasAmount(int vasAmount) {
		this.vasAmount = vasAmount;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public Double getGrandAmount() {
		return grandAmount;
	}

	public void setGrandAmount(Double grandAmount) {
		this.grandAmount = grandAmount;
	}

	public String getCoupenCode() {
		return coupenCode;
	}

	public void setCoupenCode(String coupenCode) {
		this.coupenCode = coupenCode;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getModeOfPayment() {
		return modeOfPayment;
	}

	public void setModeOfPayment(String modeOfPayment) {
		this.modeOfPayment = modeOfPayment;
	}

	/**
	 * @return the emailID
	 */
	public String getEmailID() {
		return emailID;
	}

	/**
	 * @param emailID the emailID to set
	 */
	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}

	/**
	 * @return the eventName
	 */
	public String getEventName() {
		return eventName;
	}

	/**
	 * @param eventName the eventName to set
	 */
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	/**
	 * @return the emailStatus
	 */
	public String getEmailStatus() {
		return emailStatus;
	}

	/**
	 * @param emailStatus the emailStatus to set
	 */
	public void setEmailStatus(String emailStatus) {
		this.emailStatus = emailStatus;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSubsidiaries() {
		return subsidiaries;
	}

	public void setSubsidiaries(String subsidiaries) {
		this.subsidiaries = subsidiaries;
	}

	public String getRelationshipManager() {
		return relationshipManager;
	}

	public void setRelationshipManager(String relationshipManager) {
		this.relationshipManager = relationshipManager;
	}

	public String getCustomerSupport() {
		return customerSupport;
	}

	public void setCustomerSupport(String customerSupport) {
		this.customerSupport = customerSupport;
	}


	public String getSubscriptionId() {
		return subscriptionId;
	}

	public void setSubscriptionId(String subscriptionId) {
		this.subscriptionId = subscriptionId;
	}

	public String getSubscriptionName() {
		return subscriptionName;
	}

	public void setSubscriptionName(String subscriptionName) {
		this.subscriptionName = subscriptionName;
	}

	public int getSubscriptionAmount() {
		return subscriptionAmount;
	}

	public void setSubscriptionAmount(int subscriptionAmount) {
		this.subscriptionAmount = subscriptionAmount;
	}
	

	public int getSubscriptionValidity() {
		return subscriptionValidity;
	}

	public void setSubscriptionValidity(int subscriptionValidity) {
		this.subscriptionValidity = subscriptionValidity;
	}

	public String getLcCount() {
		return lcCount;
	}

	public void setLcCount(String lcCount) {
		this.lcCount = lcCount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getInsertedBy() {
		return insertedBy;
	}

	public void setInsertedBy(String insertedBy) {
		this.insertedBy = insertedBy;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getInsertedDate() {
		return insertedDate;
	}

	public void setInsertedDate(String insertedDate) {
		this.insertedDate = insertedDate;
	}

	public String getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@Override
	public String toString() {
		return "SubscriptionBean [userId=" + userId + ", subscriptionId=" + subscriptionId + ", subscriptionName="
				+ subscriptionName + ", subscriptionAmount=" + subscriptionAmount + ", lcCount=" + lcCount + ", remark="
				+ remark + ", status=" + status + ", insertedBy=" + insertedBy + ", modifiedBy=" + modifiedBy
				+ ", insertedDate=" + insertedDate + ", modifiedDate=" + modifiedDate + ", subscriptionValidity="
				+ subscriptionValidity + ", subsidiaries=" + subsidiaries + ", relationshipManager="
				+ relationshipManager + ", customerSupport=" + customerSupport + ", year=" + year + ", month=" + month
				+ ", eventName=" + eventName + ", emailStatus=" + emailStatus + ", emailID=" + emailID + "]";
	}

}
