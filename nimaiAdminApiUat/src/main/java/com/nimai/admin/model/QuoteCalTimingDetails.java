package com.nimai.admin.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "quote_cal_timing_details")
@EntityListeners({AuditingEntityListener.class})
@DynamicUpdate
public class QuoteCalTimingDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "Quote_cal_id")
  private int quoteTImingId;
  
  @Column(name = "transaction_id")
  private String transactionId;
  
  @Column(name = "quotation_id")
  private String quotationId;
  
  @Column(name = "actual_time_in_milseconds")
  private long timeInmilsecond;
  
  public long getTimeInmilsecond() {
    return this.timeInmilsecond;
  }
  
  public void setTimeInmilsecond(long timeInmilsecond) {
    this.timeInmilsecond = timeInmilsecond;
  }
  
  public int getQuoteTImingId() {
    return this.quoteTImingId;
  }
  
  public void setQuoteTImingId(int quoteTImingId) {
    this.quoteTImingId = quoteTImingId;
  }
  
  public String getTransactionId() {
    return this.transactionId;
  }
  
  public void setTransactionId(String transactionId) {
    this.transactionId = transactionId;
  }
  
  public String getQuotationId() {
    return this.quotationId;
  }
  
  public void setQuotationId(String quotationId) {
    this.quotationId = quotationId;
  }
}
