package com.nimai.splan.payload;

public class banksSplansReponse {
	private String subscriptionId;
	private String subscriptionName;
	private int subscriptionAmount;
	private String subscriptionCurrency;
	private String lcCount;
	private int subscriptionValidity;
	private String subsidiaries;
	private String relationshipManager;
	private String customerSupport;
	private String customerType;
	private String sPLanCountry;
	
	
	public String getSubscriptionCurrency() {
		return subscriptionCurrency;
	}

	public void setSubscriptionCurrency(String subscriptionCurrency) {
		this.subscriptionCurrency = subscriptionCurrency;
	}

	public String getsPLanCountry() {
		return sPLanCountry;
	}
	
	public void setsPLanCountry(String sPLanCountry) {
		this.sPLanCountry = sPLanCountry;
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
	public String getLcCount() {
		return lcCount;
	}
	public void setLcCount(String lcCount) {
		this.lcCount = lcCount;
	}
	public int getSubscriptionValidity() {
		return subscriptionValidity;
	}
	public void setSubscriptionValidity(int subscriptionValidity) {
		this.subscriptionValidity = subscriptionValidity;
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
	public String getCustomerType() {
		return customerType;
	}
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	@Override
	public String toString() {
		return "banksSplansReponse [subscriptionId=" + subscriptionId + ", subscriptionName=" + subscriptionName
				+ ", subscriptionAmount=" + subscriptionAmount + ", lcCount=" + lcCount + ", subscriptionValidity="
				+ subscriptionValidity + ", subsidiaries=" + subsidiaries + ", relationshipManager="
				+ relationshipManager + ", customerSupport=" + customerSupport + ", customerType=" + customerType
				+ ", sPLanCountry=" + sPLanCountry + "]";
	}
	
	
}
