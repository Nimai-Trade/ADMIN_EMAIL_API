package com.nimai.lc.entity;

import java.util.List;

public class MainDashboard {
	
	
	
	private List<PieChartCountry> piechartcountry;
	private List<PieChartGoods> piechartgoods;
	private List<CumulativeTransactionAmount> cumulativetrxnAmnt;
	private List<TransactionBifurcation> transactionbifurcation;
	private List<LatestAcceptedTransaction> latestacceptedtrxn;
	private List<CustomerDashBoardCount> custmrdasbrdcount;;
	private List<LifeTimeSaving> lifetimesaving;
	
	public List<PieChartCountry> getPiechartcountry() {
		return piechartcountry;
	}
	public void setPiechartcountry(List<PieChartCountry> piechartcountry) {
		this.piechartcountry = piechartcountry;
	}
	public List<PieChartGoods> getPiechartgoods() {
		return piechartgoods;
	}
	public void setPiechartgoods(List<PieChartGoods> piechartgoods) {
		this.piechartgoods = piechartgoods;
	}
	public List<CumulativeTransactionAmount> getCumulativetrxnAmnt() {
		return cumulativetrxnAmnt;
	}
	public void setCumulativetrxnAmnt(List<CumulativeTransactionAmount> cumulativetrxnAmnt) {
		this.cumulativetrxnAmnt = cumulativetrxnAmnt;
	}
	public List<TransactionBifurcation> getTransactionbifurcation() {
		return transactionbifurcation;
	}
	public void setTransactionbifurcation(List<TransactionBifurcation> transactionbifurcation) {
		this.transactionbifurcation = transactionbifurcation;
	}
	public List<LatestAcceptedTransaction> getLatestacceptedtrxn() {
		return latestacceptedtrxn;
	}
	public void setLatestacceptedtrxn(List<LatestAcceptedTransaction> latestacceptedtrxn) {
		this.latestacceptedtrxn = latestacceptedtrxn;
	}
	public List<CustomerDashBoardCount> getCustmrdasbrdcount() {
		return custmrdasbrdcount;
	}
	public void setCustmrdasbrdcount(List<CustomerDashBoardCount> custmrdasbrdcount) {
		this.custmrdasbrdcount = custmrdasbrdcount;
	}
	public List<LifeTimeSaving> getLifetimesaving() {
		return lifetimesaving;
	}
	public void setLifetimesaving(List<LifeTimeSaving> lifetimesaving) {
		this.lifetimesaving = lifetimesaving;
	}
	
	
	
	
}
