package com.nimai.lc.service;

import java.util.HashMap;
import java.util.List;


import com.nimai.lc.entity.MainDashboard;



public interface CustomerDashboardService {

	 // public List<CustomerDashboardBean> customerashboard();
	 public MainDashboard customerdashboard(String year,String userId,String emailId,String startDate,String endDate);
}
