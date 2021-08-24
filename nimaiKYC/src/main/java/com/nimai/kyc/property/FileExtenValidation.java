package com.nimai.kyc.property;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileExtenValidation {

	private static Pattern fileExtnPtrn = Pattern.compile("([^\\s]+(\\.(?i)(pdf|jpg|jpeg|gif|png))$)");

	public static boolean validateFileExtn(String userName) {

		Matcher mtch = fileExtnPtrn.matcher(userName);
		if (mtch.matches()) {
			return true;
		}
		return false;
	}
}