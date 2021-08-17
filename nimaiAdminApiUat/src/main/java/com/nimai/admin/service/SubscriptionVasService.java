package com.nimai.admin.service;

import java.util.List;

import com.nimai.admin.model.NimaiSubscriptionVas;

public interface SubscriptionVasService {
	
	List<NimaiSubscriptionVas> getByUserIdAndSubsId(String userid,String subs_id);

}
