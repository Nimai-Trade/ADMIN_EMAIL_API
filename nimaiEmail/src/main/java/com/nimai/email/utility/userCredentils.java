package com.nimai.email.utility;

import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;

public class userCredentils {
	String customerUserName="CU";
	String bankName="BA";
	
	
	public String username(String subscribeType) {
		
		Random randomNumber=new Random(System.currentTimeMillis());
	int number=randomNumber.nextInt(900) + 1000;
String snumber=String.valueOf(number);
	if(subscribeType=="CUSTOMER") {
		String initilals=subscribeType.substring(0, 2);
		String username=initilals.concat(snumber);
		System.out.println(username);
		return username;
	}else if(subscribeType=="BANK"){
		String initilals=subscribeType.substring(0, 2);
		String username=initilals.concat(snumber);
		System.out.println(username);
		return username;
	}else if(subscribeType=="REFERRER"){
		String initilals=subscribeType.substring(0, 2);
		String username=initilals.concat(snumber);
		System.out.println(username);
		return username;
		
	}
	return "invalid SubscriberType";
	}
	
	public String password() {
		int leftLimit=97;
		int rightLimit=122;
		int targetStrnglength=10;
		Random random=new Random();
		String password = random.ints(leftLimit, rightLimit + 1)
			      .limit(targetStrnglength)
			      .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
			      .toString();
		System.out.println(password);
		return password;
		
	}
	public void givenUsingJava8_whenGeneratingRandomAlphanumericString_thenCorrect() {
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~`!@#$%^&*()-_=+[{]}\\|;:\'\",<.>/?";
		String pwd = RandomStringUtils.random( 9, characters );
		System.out.println( pwd ); 
	}
	
	public static void main(String[] args) {
		userCredentils u=new userCredentils();
		u.username("BANK");
		u.password();
	}
	

}
