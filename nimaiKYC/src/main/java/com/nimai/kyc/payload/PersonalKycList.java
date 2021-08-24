package com.nimai.kyc.payload;

public class PersonalKycList {
	private String documentName;
	private String title;
	private String reason;
	private String country;
	private String encodedFileContent;
	private String documentType;
	/**
	 * @return the documentName
	 */
	public String getDocumentName() {
		return documentName;
	}
	/**
	 * @param documentName the documentName to set
	 */
	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the reason
	 */
	public String getReason() {
		return reason;
	}
	/**
	 * @param reason the reason to set
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}
	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}
	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}
	/**
	 * @return the encodedFileContent
	 */
	public String getEncodedFileContent() {
		return encodedFileContent;
	}
	/**
	 * @param encodedFileContent the encodedFileContent to set
	 */
	public void setEncodedFileContent(String encodedFileContent) {
		this.encodedFileContent = encodedFileContent;
	}
	/**
	 * @return the documentType
	 */
	public String getDocumentType() {
		return documentType;
	}
	/**
	 * @param documentType the documentType to set
	 */
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}
	
	
}
