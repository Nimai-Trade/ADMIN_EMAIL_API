package com.nimai.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nimai.admin.model.NimaiMCustomer;
import com.nimai.admin.model.NimaiSubscriptionVas;
import com.nimai.admin.repository.CustomerRepository;
import com.nimai.admin.repository.SubscriptionVasRepository;
import com.nimai.admin.service.SubscriptionVasService;

@Service
public class SubscriptionVasImpl implements SubscriptionVasService {

	@Autowired
	SubscriptionVasRepository subsVasRepo;
	
	@Autowired
	CustomerRepository repo;

	@Override
	public List<NimaiSubscriptionVas> getByUserIdAndSubsId(String userid, String subs_id) {

		
		return subsVasRepo.findByUserIdAndSubscriptionId(userid, subs_id);

	}

}
