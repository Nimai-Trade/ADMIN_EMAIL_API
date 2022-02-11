package com.nimai.splan.utility;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class SPlanUniqueNumber {

	public String uniqueNUmberGenerator() {

		Random randomNUmber = new Random(System.currentTimeMillis());
		StringBuilder builder = new StringBuilder();
		int randomNUmnberLength = 6;
		for (int i = 1; i < randomNUmnberLength; i++) {
			int digit = randomNUmber.nextInt(6);
			builder.append(digit);

		}

		String number = builder.toString();
		return number;
	}

	protected String getSaltString() {
		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		StringBuilder salt = new StringBuilder();
		Random rnd = new Random();
		while (salt.length() < 3) { // length of the random string.
			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
			salt.append(SALTCHARS.charAt(index));
		}
		String saltStr = salt.toString();
		return saltStr;

	}

	public String uniqueNumber() {
		SPlanUniqueNumber nu = new SPlanUniqueNumber();
		String str = (nu.getSaltString().concat(nu.uniqueNUmberGenerator()));

		return str;

	}
	
public int getNoOfyears(int month) {
	return month/12;
	
}
public int getNoOfMonths(int month) {
	return month%12;
}

}
