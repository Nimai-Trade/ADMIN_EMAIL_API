package com.nimai.admin.model;

import java.sql.Time;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "nimai_m_discount")
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public class NimaiMDiscount {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "DISCOUNT_ID")
	private Integer discountId;
	@Column(name = "DISCOUNT_TYPE")
	private String discountType;
	@Column(name = "AMOUNT")
	private double amount;
	@Column(name = "CURRENCY")
	private String currency;
	@Column(name = "DISCOUNT_PERCENTAGE")
	private double discountPercentage;
	@Column(name = "MAX_DISCOUNT")
	private double maxDiscount;
	@Column(name = "COUPON_FOR")
	private String couponFor;
	@Column(name = "SUBSCRIPTION_PLAN")
	private String subscriptionPlan;
	@Column(name = "COUNTRY")
	private String country;
	@Column(name = "QUANTITY")
	private Integer quantity;
	@Column(name = "CONSUMED_COUPONS")
	private Integer consumedCoupons;
	
	@Column(name="COUPON_TYPE")
	private String couponType;

	@Column(name = "START_DATE")
	private Date startDate;
	@JsonFormat(pattern = "HH:MM:SS")
	@Column(name = "START_TIME")
	private Time startTime;
	@Column(name = "END_DATE")
	private Date endDate;
	@JsonFormat(pattern = "HH:MM:SS")
	@Column(name = "END_TIME")
	private Time endTime;

	@Column(name = "STATUS")
	private String status;

	@Column(name = "COUPON_CODE")
	private String couponCode;
	@Column(name = "APPROVED_BY")
	private String approvedBy;
	@Column(name = "APPROVAL_DATE")
	private Date approvalDate;
	@CreatedBy
	@Column(name = "CREATED_BY")
	private String createdBy;
	@CreatedDate
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	@Column(name = "MODIFIED_BY")
	private String modifiedBy;
	@Column(name = "MODIIFED_DATE")
	private Date modifiedDate;
	
	
	
	
	
	

	public NimaiMDiscount() {
		super();

	}

	public NimaiMDiscount(Integer discountId) {
		this.discountId = discountId;
	}

	public Integer getDiscountId() {
		return discountId;
	}

	public void setDiscountId(Integer discountId) {
		this.discountId = discountId;
	}

	public String getDiscountType() {
		return discountType;
	}

	public void setDiscountType(String discountType) {
		this.discountType = discountType;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public double getDiscountPercentage() {
		return discountPercentage;
	}

	public void setDiscountPercentage(double discountPercentage) {
		this.discountPercentage = discountPercentage;
	}

	public double getMaxDiscount() {
		return maxDiscount;
	}

	public void setMaxDiscount(double maxDiscount) {
		this.maxDiscount = maxDiscount;
	}

	public String getCouponFor() {
		return couponFor;
	}

	public void setCouponFor(String couponFor) {
		this.couponFor = couponFor;
	}

	public String getSubscriptionPlan() {
		return subscriptionPlan;
	}

	public void setSubscriptionPlan(String subscriptionPlan) {
		this.subscriptionPlan = subscriptionPlan;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getConsumedCoupons() {
		return consumedCoupons;
	}

	public void setConsumedCoupons(Integer consumedCoupons) {
		this.consumedCoupons = consumedCoupons;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getCouponType() {
		return couponType;
	}

	public void setCouponType(String couponType) {
		this.couponType = couponType;
	}

	public Time getStartTime() {
		return startTime;
	}

	public void setStartTime(Time startTime) {
		this.startTime = startTime;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Time getEndTime() {
		return endTime;
	}

	public void setEndTime(Time endTime) {
		this.endTime = endTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public Date getApprovalDate() {
		return approvalDate;
	}

	public void setApprovalDate(Date approvalDate) {
		this.approvalDate = approvalDate;
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

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@Override
	public String toString() {
		return "NimaiMDiscount [discountId=" + discountId + ", discountType=" + discountType + ", amount=" + amount
				+ ", currency=" + currency + ", discountPercentage=" + discountPercentage + ", maxDiscount="
				+ maxDiscount + ", couponFor=" + couponFor + ", subscriptionPlan=" + subscriptionPlan + ", country="
				+ country + ", quantity=" + quantity + ", consumedCoupons=" + consumedCoupons + ", couponType="
				+ couponType + ", startDate=" + startDate + ", startTime=" + startTime + ", endDate=" + endDate
				+ ", endTime=" + endTime + ", status=" + status + ", couponCode=" + couponCode + ", approvedBy="
				+ approvedBy + ", approvalDate=" + approvalDate + ", createdBy=" + createdBy + ", createdDate="
				+ createdDate + ", modifiedBy=" + modifiedBy + ", modifiedDate=" + modifiedDate + "]";
	}

}
