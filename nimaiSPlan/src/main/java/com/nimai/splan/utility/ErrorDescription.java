package com.nimai.splan.utility;

import java.util.HashMap;
import java.util.Map;

public class ErrorDescription {

	private static Map<String, String> codeToDescriptionMap = new HashMap<String, String>();

	static {
		codeToDescriptionMap.put("NIM000", "SUCCESS");
		codeToDescriptionMap.put("NIM001", "FAILURE");

		codeToDescriptionMap.put("EXE000", "Exception Occurred : ");

		// For Personal Details and Business Details Sign Up Process
		codeToDescriptionMap.put("ASA001", "Subscription Plan Purchased Successfully.");
		codeToDescriptionMap.put("ASA002", "Subscription Plan is not Available.");
		codeToDescriptionMap.put("ASA003", "User is not Present.");
		codeToDescriptionMap.put("ASA004", "User Kyc not approved.");
		codeToDescriptionMap.put("ASA005", "Subscription Plan Deleted successfully.");
		codeToDescriptionMap.put("ASA006", "Subscription Plan is not Present.");
		codeToDescriptionMap.put("ASA007", "Subscription Plan is updated successfully.");
		codeToDescriptionMap.put("ASA008", "Subscription Plan is not Active for user.");
		codeToDescriptionMap.put("ASA009", "Subscription Id is required.");
		codeToDescriptionMap.put("ASA010", "Invalid SubscriptionId");

	}

	public static String getDescription(String code) {
		String description = codeToDescriptionMap.get(code);
		if (description == null) {
			description = "Invalid Error Code!";
		}
		return description;
	}

}
