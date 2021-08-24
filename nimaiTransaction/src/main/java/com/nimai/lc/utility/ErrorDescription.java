package com.nimai.lc.utility;

import java.util.HashMap;
import java.util.Map;

public class ErrorDescription {

	private static Map<String, String> codeToDescriptionMap = new HashMap<String, String>();

	static {
		codeToDescriptionMap.put("NIM000", "SUCCESS");
		codeToDescriptionMap.put("NIM001", "FAILURE");

		codeToDescriptionMap.put("EXE000", "Exception Occurred : ");

		codeToDescriptionMap.put("ASA001", "Transaction saved successfully.");
		codeToDescriptionMap.put("ASA002", "Transaction is not Available.");
		
		codeToDescriptionMap.put("ASA003", "Quotation Placed successfully.");
		codeToDescriptionMap.put("ASA004", "Quotation is not Available.");

	}

	public static String getDescription(String code) {
		String description = codeToDescriptionMap.get(code);
		if (description == null) {
			description = "Invalid Error Code!";
		}
		return description;
	}

}