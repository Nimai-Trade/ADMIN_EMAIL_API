package com.nimai.email.utility;

public class EmailErrorCode {
	private String errorName;
	private Integer errorCode;
	
	public EmailErrorCode(String errorName, Integer errorCode) {

		this.errorName = errorName;
		this.errorCode = errorCode;
	}

	/**
	 * @return the errorName
	 */
	public String getErrorName() {
		return errorName;
	}

	/**
	 * @param errorName the errorName to set
	 */
	public void setErrorName(String errorName) {
		this.errorName = errorName;
	}

	/**
	 * @return the errorCode
	 */
	public Integer getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}
	
	
}
