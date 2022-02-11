package com.nimai.splan.service;

import java.util.HashMap;
import java.util.Optional;

import com.nimai.splan.model.NimaiMCustomer;

public interface GetCountService {
	
	public HashMap<String, Object> getCount(String userid,String emailAddress);

	public String getAccountType(String userid);

	public String getAccountSource(String userid);

	NimaiMCustomer getEmailAddress(String userid);  

}
