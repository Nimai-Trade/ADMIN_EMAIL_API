package com.nimai.admin.util;

import java.util.Comparator;

import org.springframework.stereotype.Component;

import com.nimai.admin.model.NimaiSubscriptionDetails;

@Component
public class SPlanSerialComparator implements Comparator<NimaiSubscriptionDetails> {

	@Override
	public int compare(NimaiSubscriptionDetails details1, NimaiSubscriptionDetails details2) {
		// TODO Auto-generated method stub
		return details2.getSplSerialNumber()-details1.getSplSerialNumber();
	}

}
