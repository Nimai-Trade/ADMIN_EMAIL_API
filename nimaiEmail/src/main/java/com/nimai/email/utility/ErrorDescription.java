package com.nimai.email.utility;

import java.util.HashMap;
import java.util.Map;

public class ErrorDescription {

	private static Map<String, String> codeToDescriptionMap = new HashMap<String, String>();

	static {
		codeToDescriptionMap.put("NIM000", "SUCCESS");
		codeToDescriptionMap.put("NIM001", "FAILURE");
		
		codeToDescriptionMap.put("EXE000", "Exception Occurred : ");
				
	
		codeToDescriptionMap.put("ASA001", "User id does not exist.");
		codeToDescriptionMap.put("ASA002", "Email Send Succefully");
		codeToDescriptionMap.put("ASA003","Password Already Reset!");
		codeToDescriptionMap.put("ASA004","Setting password link has expired!");
		codeToDescriptionMap.put("ASA005","Email Address is not registred!");
		codeToDescriptionMap.put("ASA006","Domain Name does not match!");
		codeToDescriptionMap.put("ASA007","Subsidiary Account Registration link expired!");
		codeToDescriptionMap.put("ASA008", "Refer Account Registration link expired!");
        codeToDescriptionMap.put("ASA009", "Branch user passcode expired!");
        codeToDescriptionMap.put("ASA010", "Employee Id not registred");
        codeToDescriptionMap.put("ASA011", "Email sent failed due to some technical Issue");
        codeToDescriptionMap.put("ASA012", "Event not present");
        codeToDescriptionMap.put("ASA013", "Kyc approval is pending");
        codeToDescriptionMap.put("ASA014", "Error occur while sending email");
        codeToDescriptionMap.put("ASA015", "Link Expired");
        codeToDescriptionMap.put("ASA016", "Token Invalid");
        codeToDescriptionMap.put("ASA017", "Token not available");
        codeToDescriptionMap.put("ASA018", "Forgot email not sent because of technical issue");
        codeToDescriptionMap.put("ASA019", "Something went wronng while sending password");

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

