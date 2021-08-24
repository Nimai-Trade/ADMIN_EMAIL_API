package com.nimai.kyc.util;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.nimai.kyc.payload.BusinessKycList;
import com.nimai.kyc.payload.PersonalKycList;
import com.nimai.kyc.payload.kycBase64Request;

@Component
public class kycValidator {
	private static Logger logger = LoggerFactory.getLogger(kycValidator.class);



	public String kycRequestValidator(kycBase64Request kycDoc) {

		String returnString = null;
		try {
			if (kycDoc.getBusinessDocumentList().size() >= 0) {

				List<BusinessKycList> documentList = kycDoc.getBusinessDocumentList();
				for (BusinessKycList templist : documentList) {
					if ((kycDoc.getUserId() == null) || (kycDoc.getUserId().trim().isEmpty())) {
						return "UserId should not be empty";
					}
					if ((templist.getCountry()) == null || (templist.getCountry().trim().isEmpty())) {
						return "Business Country should not be empty";
					}
					if ((templist.getTitle()) == null || (templist.getTitle().trim().isEmpty())) {
						return "Business title should not be empty";
					}
					if ((templist.getDocumentName()) == null || (templist.getDocumentName().trim().isEmpty())) {
						return "Business document should not be empty";
					}
					 //pdf, jpeg, png, tiff
					String fileExt=templist.getEncodedFileContent().substring(templist.getEncodedFileContent().lastIndexOf(".") + 1).substring(0, 3);
						System.out.println(fileExt+" is extension.");	
						String splitted =templist.getEncodedFileContent().substring(0,82);
						System.out.println("BEfore |: "+splitted);
						String[] supportFormat= {"pdf", "jpg", "jpe", "png","tif"};
						String mimeType=templist.getEncodedFileContent().substring(templist.getEncodedFileContent().indexOf("data:")+5, templist.getEncodedFileContent().indexOf(";"));
						System.out.println("MIME Type: "+mimeType);
					if(!Arrays.asList(supportFormat).contains(fileExt) || splitted.substring(0, splitted.indexOf("data")).contains(":") 
							|| !stringContainsItemFromList(mimeType,supportFormat) || mimeType.contains(":"))
					{
						return "Invalid format. You can upload KYC of pdf, jpeg, png, tiff format";
					}
				}
			}

			if (kycDoc.getPersonalDocumentList().size() >= 0) {
				List<PersonalKycList> personalLKyc = kycDoc.getPersonalDocumentList();
				for (PersonalKycList templist : personalLKyc) {

					if ((templist.getCountry()) == null || (templist.getCountry().trim().isEmpty())) {
						return "Personal country should not be empty";
					}
					if ((templist.getTitle()) == null || (templist.getTitle().trim().isEmpty())) {
						return "Personal title should not be empty";
					}
					if ((templist.getDocumentName()) == null || (templist.getDocumentName().trim().isEmpty())) {
						return "Personal document name should not be empty";
					}
					String fileExt=templist.getEncodedFileContent().substring(templist.getEncodedFileContent().lastIndexOf(".") + 1).substring(0, 3);
					System.out.println(fileExt+" is extension.");	
					String splitted =templist.getEncodedFileContent().substring(0,82);
					System.out.println("BEfore |: "+splitted);
					String[] supportFormat= {"pdf", "jpg", "jpe", "png","tif"};
					String mimeType=templist.getEncodedFileContent().substring(templist.getEncodedFileContent().indexOf("data:")+5, templist.getEncodedFileContent().indexOf(";"));
					System.out.println("MIME Type: "+mimeType);
					System.out.println("Splitted Text: "+splitted.substring(0, splitted.indexOf("data")));
				if(!Arrays.asList(supportFormat).contains(fileExt) || splitted.substring(0, splitted.indexOf("data")).contains(":") 
						|| !stringContainsItemFromList(mimeType,supportFormat) || mimeType.contains(":"))
				{
					return "Invalid format. You can upload KYC of pdf, jpeg, png, tiff format";
				}
				}
			}

			returnString = "success";
		} catch (Exception exception) {
			exception.printStackTrace();
			returnString = "Error";
		}
		return returnString;
	}
	
	public static boolean stringContainsItemFromList(String inputStr, String[] items)
	{
	    for(int i =0; i < items.length; i++)
	    {
	        if(inputStr.contains(items[i]))
	        {
	            return true;
	        }
	    }
	    return false;
	}
}
