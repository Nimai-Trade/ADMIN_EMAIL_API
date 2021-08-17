package com.nimai.admin.util;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Random;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.nimai.admin.security.UserPrincipal;

public class Utility {

	public static String randomTokenKeyGenerator() {
		Random objGenerator = new Random(System.currentTimeMillis());
		StringBuilder builder = new StringBuilder();
		int randomNumberLength = 4;
		for (int i = 0; i < randomNumberLength; i++) {
			int digit = objGenerator.nextInt(10);
			builder.append(digit);
		}
		return builder.toString();
	}

	public static Date getSysDate() {
		LocalDate localDate = LocalDate.now();
		return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

	}
	
	public static String getUserCountry() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
		return userPrincipal.getCountry();
	}
	
	public static String getUserId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
		return userPrincipal.getUsername();
	}
public static void main(String[] args) {
	Double x= 355.59;
	int y=144;
	double z=x-y;
	 DecimalFormat f = new DecimalFormat("##.00");
     System.out.println(f.format(z));
	System.out.println(z);
}
}
