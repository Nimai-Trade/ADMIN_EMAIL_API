package com.nimai.splan.payload;

import java.util.Date;

public class SubscriptionAndPaymentBean {
	
	private String invoiceId;
	
	private Date insertedDate;

	private String paymentStatus;
	
	private Integer splSerialNo;
	
	
	
	public Integer getSplSerialNo() {
		return splSerialNo;
	}

	public void setSplSerialNo(Integer splSerialNo) {
		this.splSerialNo = splSerialNo;
	}

	public String getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}

	public Date getInsertedDate() {
		return insertedDate;
	}

	public void setInsertedDate(Date insertedDate) {
		this.insertedDate = insertedDate;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	
	
}
