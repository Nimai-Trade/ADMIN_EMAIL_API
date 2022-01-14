package com.nimai.admin.util;

import java.math.BigDecimal;
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
	 BigDecimal b1, b2;
	Double x= 355.59;
	int y=144;
	b1 = new BigDecimal(67891.000012);
    b2 = new BigDecimal("67891.000");
    
    System.out.println("max value"+b1.max(b2));
    if (b1.compareTo(b2) == 0) {
        System.out.println(b1 + " and " + b2 + " are equal.");
    }
    else if (b1.compareTo(b2) == 1) {
        System.out.println(b1 + " is greater than " + b2 + ".");
    }
    else {
        System.out.println(b1 + " is lesser than " + b2 + ".");
    }
}
}
