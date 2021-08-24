package com.nimai.kyc.exception;

import java.util.HashMap;
import java.util.Map;

public class ErrorDescription {

	private static Map<String, String> codeToDescriptionMap = new HashMap<String, String>();

	static {
		codeToDescriptionMap.put("NIM000", "SUCCESS");
		codeToDescriptionMap.put("NIM001", "FAILURE");
		
		codeToDescriptionMap.put("EXE000", "Exception Occurred : ");
				
		// For Personal Details and Business Details  Sign Up Process 
		codeToDescriptionMap.put("ASA001", "KYC status updated succefully.");
		
		
	}
	
	public static String getDescription(String code) 
	{
		String description = codeToDescriptionMap.get(code);
		if (description == null) 
		{
			description = "Invalid Error Code!";
		}
		return description;
	}

}

