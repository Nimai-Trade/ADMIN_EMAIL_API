package com.nimai.splan.payload;

import java.util.List;

public class SPlanResponseBean {
	private List<customerSPlansResponse> customerSplans;
	private List<banksSplansReponse> banksSplans;

	public List<customerSPlansResponse> getCustomerSplans() {
		return customerSplans;
	}

	public void setCustomerSplans(List<customerSPlansResponse> customerSplans) {
		this.customerSplans = customerSplans;
	}

	public List<banksSplansReponse> getBanksSplans() {
		return banksSplans;
	}

	public void setBanksSplans(List<banksSplansReponse> banksSplans) {
		this.banksSplans = banksSplans;
	}

	@Override
	public String toString() {
		return "SPlanResponseBean [customerSplans=" + customerSplans + ", banksSplans=" + banksSplans + "]";
	}

}
