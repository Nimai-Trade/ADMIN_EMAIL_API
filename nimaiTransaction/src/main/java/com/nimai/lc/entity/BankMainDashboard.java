package com.nimai.lc.entity;

import java.util.List;

public class BankMainDashboard {
	private List<BankDashBoardCount> bankdashbrdcount;
	private List<BankLatestAcceptedTransaction> banklatestaccepttrxn;
	private List<BankDashboardBarChart> bankBarChart;
	public List<BankLatestAcceptedTransaction> getBanklatestaccepttrxn() {
		return banklatestaccepttrxn;
	}
	public void setBanklatestaccepttrxn(List<BankLatestAcceptedTransaction> banklatestaccepttrxn) {
		this.banklatestaccepttrxn = banklatestaccepttrxn;
	}
	public List<BankDashBoardCount> getBankdashbrdcount() {
		return bankdashbrdcount;
	}
	public void setBankdashbrdcount(List<BankDashBoardCount> bankdashbrdcount) {
		this.bankdashbrdcount = bankdashbrdcount;
	}
	public List<BankDashboardBarChart> getBankBarChart() {
		return bankBarChart;
	}
	public void setBankBarChart(List<BankDashboardBarChart> bankBarChart) {
		this.bankBarChart = bankBarChart;
	}
	

}
