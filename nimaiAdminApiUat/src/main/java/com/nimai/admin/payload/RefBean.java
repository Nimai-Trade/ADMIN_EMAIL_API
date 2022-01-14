package com.nimai.admin.payload;

import java.util.List;



public class RefBean {
	private Float totalEarning;
	List<ReferrerBean> rfb;
	
	
	public Float getTotalEarning() {
		return totalEarning;
	}
	public void setTotalEarning(Float totalEarning) {
		this.totalEarning = totalEarning;
	}
	public List<ReferrerBean> getRfb() {
		return rfb;
	}
	public void setRfb(List<ReferrerBean> rfb) {
		this.rfb = rfb;
	}
}
