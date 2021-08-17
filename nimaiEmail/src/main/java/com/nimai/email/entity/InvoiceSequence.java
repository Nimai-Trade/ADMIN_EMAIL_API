package com.nimai.email.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="invoice_sequence")
public class InvoiceSequence {

	@Id
	@Basic(optional = false)
	@NotNull
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "invoice_id")
	private int invoiceId;
	
	@Column(name="invoice_seq")
	private int invoiceSeq;

	public int getInvoiceSeq() {
		return invoiceSeq;
	}

	public void setInvoiceSeq(int invoiceSeq) {
		this.invoiceSeq = invoiceSeq;
	}
	
	
	
	
	
}
