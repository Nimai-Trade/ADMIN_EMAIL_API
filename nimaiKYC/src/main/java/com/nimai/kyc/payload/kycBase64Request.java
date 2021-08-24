package com.nimai.kyc.payload;

import java.util.List;

public class kycBase64Request {
	private String userId;
	private List<BusinessKycList> businessDocumentList;
	private List<PersonalKycList> personalDocumentList;
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * @return the businessDocumentList
	 */
	public List<BusinessKycList> getBusinessDocumentList() {
		return businessDocumentList;
	}
	/**
	 * @param businessDocumentList the businessDocumentList to set
	 */
	public void setBusinessDocumentList(List<BusinessKycList> businessDocumentList) {
		this.businessDocumentList = businessDocumentList;
	}
	/**
	 * @return the personalDocumentList
	 */
	public List<PersonalKycList> getPersonalDocumentList() {
		return personalDocumentList;
	}
	/**
	 * @param personalDocumentList the personalDocumentList to set
	 */
	public void setPersonalDocumentList(List<PersonalKycList> personalDocumentList) {
		this.personalDocumentList = personalDocumentList;
	}


	
	
	


}
