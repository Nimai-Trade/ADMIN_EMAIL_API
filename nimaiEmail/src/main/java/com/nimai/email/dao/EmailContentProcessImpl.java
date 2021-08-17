package com.nimai.email.dao;

import java.io.File;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nimai.email.entity.EmailComponentMaster;
import com.nimai.email.utility.EmailConversionUtil;
import com.nimai.email.utility.FileAttachment;
import com.nimai.email.utility.InlineImage;

@Component
public class EmailContentProcessImpl {

	@Autowired
	EmailConfigurationdaoImpl emailConfigurationDAOImpl;

	private static Logger logger = LoggerFactory.getLogger(EmailContentProcessImpl.class);

//@Override
	/*
	 * **************************************************************** PARAMETER
	 * STRUTURE TO BE FOLLOWED for ARRAYLIST to be passed in METHOD process
	 * ****************************************************************** input
	 * ArrayList 0 - eventId 1 - to email addresss 2 - Arraylist of parameters for
	 * subject 3 - Arraylist of parameters for body 4 - Arraylist of files for
	 * attachement
	 * ******************************************************************
	 */
	/*
	 * **************************************************************** PARAMETER
	 * STRUTURE TO BE FOLLOWED for ARRAYLIST to be returned by METHOD process
	 * ****************************************************************** input
	 * ArrayList 0 - eventId 1 - from email address 2 - Arraylist of TO email
	 * addresses 3 - Arraylist of CC email addresses 4 - Arraylist of BCC email
	 * addresses 5 - String of Subject 6 - String of Body 7 - ArrayList of File
	 * attachements 8 - ArrayList of Inline Images
	 * ******************************************************************
	 */
	public ArrayList process(ArrayList input) {

		ArrayList<Object> output = null;
		if (input != null) {
			Integer eventId = (Integer) input.get(0);
			String toEmailId = (String) input.get(1);

//			System.out.println("@#####eventId= " + eventId);
//			System.out.println("@#####input.get(0)= " + input.get(0));
//			System.out.println("@#####input.get(1)= " + input.get(1));
//			System.out.println("@#####input.get(2)= " + input.get(2));
//			System.out.println("@#####input.get(3)= " + input.get(3));
//			System.out.println("@#####input.get(4)= " + input.get(4));

			logger.debug("@#####eventId= " + eventId);
			logger.debug("@#####input.get(0)= " + input.get(0));
			logger.debug("@#####input.get(1)= " + input.get(1));
			logger.debug("@#####input.get(2)= " + input.get(2));
			logger.debug("@#####input.get(3)= " + input.get(3));
			logger.debug("@#####input.get(4)= " + input.get(4));

			// String ccEmailId = (String) input.get(5);
			// System.out.println("@#####input.get(5)= " + input.get(5));
			// System.out.println("@#####input.get(5)= "+input.get(5));
//                  System.out.println("@#####input.get(6)= "+input.get(6));

			// EmailCompMapHandler hEmailComponent=EmailCompMapHandler.getInstance();
			// EmailCompMap emailCompMap=hEmailComponent.get(eventId);
			EmailComponentMaster emailConfigurationBean = null;
			// EmailConfigurationDAO emailConfigurationDAO=new EmailConfigurationDAOImpl();

			// EmailConfigurationDAOImpl emailDaoimpl= new EmailConfigurationDAOImpl();

			emailConfigurationBean = (EmailComponentMaster) emailConfigurationDAOImpl.findByID(eventId.longValue());
			// emailConfigurationBean= (FxEmailcomponentmaster) emailInsert.findByID(100L);

			/* hhh* HibernateUtil.commitTransaction();/* */

			// System.out.println(emailCompMap.getEmailFrom());
			// System.out.println(emailCompMap.getEventId());
			/// System.out.println(getSubject(emailCompMap,input));
			// System.out.println(getBody(emailCompMap,input));
			// System.out.println(input.get(1));
			output = new ArrayList<>();
			output.add(eventId);
			output.add(emailConfigurationBean.getEmailFrom());

			if (toEmailId != null && toEmailId.trim().length() != 0) {
				ArrayList<String> toList = new ArrayList<>(Arrays.asList(toEmailId.split("\\s*,\\s*")));
				logger.debug(">>>>>>>>>>>>>" + toList.size());
				output.add(toList);
			} else {
				output.add(null);
			}
//			if (ccEmailId != null && ccEmailId.trim().length() != 0) {
//				ArrayList<String> toList = new ArrayList(Arrays.asList(toEmailId.split("\\s*,\\s*")));
//				System.out.println(">>>>>>>>>>>>>" + toList.size());
//				output.add(toList);
//			} else {
//				output.add(null);
//			}

			if (emailConfigurationBean.getCc() != null && emailConfigurationBean.getCc().trim().length() != 0) {
				ArrayList<String> ccList = new ArrayList<>(
						Arrays.asList(emailConfigurationBean.getCc().split("\\s*,\\s*")));
				output.add(ccList);
			} else {
				output.add(null);
			}

			if (emailConfigurationBean.getBcc() != null && emailConfigurationBean.getBcc().trim().length() != 0) {
				ArrayList<String> bccList = new ArrayList<>(
						Arrays.asList(emailConfigurationBean.getBcc().split("\\s*,\\s*")));
				output.add(bccList);
			} else {
				output.add(null);
			}

			output.add(getSubject(emailConfigurationBean, input));

			output.add(getBody(emailConfigurationBean, input));

			// output.add(input.get(2));
			// output.add(input.get());

//		output.add(getInlineImages(emailConfigurationBean));
//                
//                String javaCodeImage ="0:image2:ruppee_symbol.JPG";
//                
//                javaCodeImage = "" + javaCodeImage +","+ emailConfigurationBean.getInlineimage();
//                output.add(javaCodeImage);
//                //output.add(emailConfigurationBean.getBodyencrypt());
			if (input.get(4) != null) {
				output.add(getTotalAttachementList(emailConfigurationBean, (ArrayList) input.get(4)));
			} else {
				output.add(null);
			}
			/*
			 * try { System.out.println("insideeeeeeee:input.size()=" + input.size()); if
			 * (input.size() >= 5) { System.out.println("====" + input.get(4));
			 * output.add(input.get(4));
			 * 
			 * } } catch (Exception e) { e.printStackTrace(System.out); }
			 */

		}

		return output;
	}

	private ArrayList<File> getTotalAttachementList(EmailComponentMaster emailConfigurationBean,
			ArrayList originalFileList) {
		ArrayList<File> finalFileList = null;

		if (originalFileList != null && originalFileList.size() != 0) {
			finalFileList = new ArrayList<File>();
			finalFileList.addAll(originalFileList);
		}

		String fileListFromDB = emailConfigurationBean.getAttachment();
		if (fileListFromDB != null && fileListFromDB.trim().length() != 0) {

			if (finalFileList == null) {
				finalFileList = new ArrayList<File>();
			}

			String arrayFiles[] = fileListFromDB.split(",");
			for (String filedetails : arrayFiles) {
				String details[] = filedetails.split(":");
				FileAttachment fileAttachment = new FileAttachment();
				fileAttachment.setFileType(details[0]);
				fileAttachment.setFileName(details[1]);
				fileAttachment.process(emailConfigurationBean);

				finalFileList.add(fileAttachment.getFileObject());
			}

		}

		return finalFileList;
	}

	private ArrayList<InlineImage> getInlineImages(EmailComponentMaster emailConfigurationBean) {
		ArrayList<InlineImage> listOfInlineImages = null;

		String inlineImages = emailConfigurationBean.getInlineimage();
		if (inlineImages != null && inlineImages.trim().length() > 0) {
			System.out.println("inlineImages=" + inlineImages);
			listOfInlineImages = new ArrayList<InlineImage>();
			String array[] = inlineImages.split(",");
			for (String inlineImageDetails : array) {
				String imageDetails[] = inlineImageDetails.split(":");
				InlineImage inlineImage = new InlineImage();
				inlineImage.setFileType(imageDetails[0]);
				inlineImage.setTagName(imageDetails[1]);
				inlineImage.setFileName(imageDetails[2]);
//			inlineImage.process(emailConfigurationBean);
				inlineImage.process(emailConfigurationBean);
				listOfInlineImages.add(inlineImage);
			}

		} else {
			listOfInlineImages = null;
		}

		return listOfInlineImages;
	}

	private String getSubject(EmailComponentMaster emailConfigurationBean, ArrayList input) {

		String subjectTemplate = emailConfigurationBean.getSubject();
		logger.debug("@@@" + input.get(2));
		HashMap<String, String> hmSubjectn = new HashMap<>();
		hmSubjectn.put(subjectTemplate, subjectTemplate);
		return replaceAll(hmSubjectn, subjectTemplate);
	}

	private String getBody(EmailComponentMaster emailConfigurationBean, ArrayList input) {
		String bodyTemplate = emailConfigurationBean.getBody();
		HashMap hmBody = (HashMap) input.get(3);
		return replaceAll(hmBody, bodyTemplate);
	}

	private String replaceAll(HashMap hm, String str) {

		logger.debug("--------------------------------------");
		logger.debug(str);
		logger.debug("--------------------------------------");
		Set<String> keys = hm.keySet();

		for (String key : keys) {

			String value = (String) hm.get(key);

			if (key.equalsIgnoreCase("username") && str.contains("${username}")) {
				str = value != null ? str.replace("${" + key + "}", value) : str.replace("${" + key + "}", value = "");
			} else if (key.equalsIgnoreCase("userId") && str.contains("${userId}")) {
				// str = str.replace("${" + key + "}", value);
				str = value != null ? str.replace("${" + key + "}", value) : str.replace("${" + key + "}", value = "");
			} else if (key.equalsIgnoreCase("link") && str.contains("${link}")) {
				str = value != null ? str.replace("${" + key + "}", value) : str.replace("${" + key + "}", value = "");
				// str = str.replace("${" + key + "}", value);
			} else if (key.equalsIgnoreCase("passcode") && str.contains("${passcode}")) {
				str = value != null ? str.replace("${" + key + "}", value)
						: str.replace("${" + key + "}", value = "NA");
				// str = str.replace("${" + key + "}", value);
			} else if (key.equalsIgnoreCase("transactionId") && str.contains("${transactionId}")) {
				str = value != null ? str.replace("${" + key + "}", value)
						: str.replace("${" + key + "}", value = "NA");
				// str = str.replace("${" + key + "}", value);
			} else if (key.equalsIgnoreCase("quotationId") && str.contains("${quotationId}")) {
				str = value != null ? str.replace("${" + key + "}", value)
						: str.replace("${" + key + "}", value = "NA");
				// str = str.replace("${" + key + "}", value);
			} else if (key.equalsIgnoreCase("reason") && str.contains("${reason}")) {
				str = value != null ? str.replace("${" + key + "}", value)
						: str.replace("${" + key + "}", value = "NA");
				// str = str.replace("${" + key + "}", value);
			} else if (key.equalsIgnoreCase("username") && str.contains("${username}")) {
				str = value != null ? str.replace("${" + key + "}", value) : str.replace("${" + key + "}", value = "");
				// str = str.replace("${" + key + "}", value);
			} else if (key.equalsIgnoreCase("lcIssuingValue") && str.contains("${lcIssuingValue}")) {
				str = value != null ? str.replace("${" + key + "}", value)
						: str.replace("${" + key + "}", value = "NA");
				// str = str.replace("${" + key + "}", value);
			} else if (key.equalsIgnoreCase("lcIssuingDate") && str.contains("${lcIssuingDate}")) {
				str = value != null ? str.replace("${" + key + "}", value)
						: str.replace("${" + key + "}", value = "NA");
				// str = str.replace("${" + key + "}", value);
			} else if (key.equalsIgnoreCase("lcExpiryDate") && str.contains("${lcExpiryDate}")) {
				// str = str.replace("${" + key + "}", value);
				str = value != null ? str.replace("${" + key + "}", value)
						: str.replace("${" + key + "}", value = "NA");
			} else if (key.equalsIgnoreCase("customerName") && str.contains("${customerName}")) {
				str = value != null ? str.replace("${" + key + "}", value) : str.replace("${" + key + "}", value = "");
				// str = str.replace("${" + key + "}", value);
			} else if (key.equalsIgnoreCase("totalQuoteValue") && str.contains("${totalQuoteValue}")) {
				str = value != null ? str.replace("${" + key + "}", value)
						: str.replace("${" + key + "}", value = "NA");
				// str = str.replace("${" + key + "}", value);
			} else if (key.equalsIgnoreCase("amount") && str.contains("${amount}")) {
				str = value != null ? str.replace("${" + key + "}", value)
						: str.replace("${" + key + "}", value = "NA");
				// str = str.replace("${" + key + "}", value);
			} else if (key.equalsIgnoreCase("validatyDate") && str.contains("${validatyDate}")) {
				str = value != null ? str.replace("${" + key + "}", value)
						: str.replace("${" + key + "}", value = "NA");
				// str = str.replace("${" + key + "}", value);
			} else if (key.equalsIgnoreCase("htmlBody") && str.contains("${htmlBody}")) {
				str = value != null ? str.replace("${" + key + "}", value) : str.replace("${" + key + "}", value = " ");
				// str = str.replace("${" + key + "}", value);
			} else if (key.equalsIgnoreCase("branchName") && str.contains("${branchName}")) {
				str = value != null ? str.replace("${" + key + "}", value)
						: str.replace("${" + key + "}", value = "NA");
				// str = str.replace("${" + key + "}", value);
			} else if (key.equalsIgnoreCase("bankName") && str.contains("${bankName}")) {
				str = value != null ? str.replace("${" + key + "}", value)
						: str.replace("${" + key + "}", value = "NA");
				// str = str.replace("${" + key + "}", value);
			} else if (key.equalsIgnoreCase("suscriptionId") && str.contains("${suscriptionId}")) {
				str = value != null ? str.replace("${" + key + "}", value)
						: str.replace("${" + key + "}", value = "NA");
				// str = str.replace("${" + key + "}", value);
			} else if (key.equalsIgnoreCase("subscriptionName") && str.contains("${subscriptionName}")) {
				str = value != null ? str.replace("${" + key + "}", value)
						: str.replace("${" + key + "}", value = "NA");
				// str = str.replace("${" + key + "}", value);
			} else if (key.equalsIgnoreCase("relationshipManager") && str.contains("${relationshipManager}")) {
				str = value != null ? str.replace("${" + key + "}", value)
						: str.replace("${" + key + "}", value = "NA");
				// str = str.replace("${" + key + "}", value);
			} else if (key.equalsIgnoreCase("customerSupport") && str.contains("${customerSupport}")) {
				str = value != null ? str.replace("${" + key + "}", value)
						: str.replace("${" + key + "}", value = "NA");
				// str = str.replace("${" + key + "}", value);
			} else if (key.equalsIgnoreCase("splanStartDate") && str.contains("${splanStartDate}")) {
				str = value != null ? str.replace("${" + key + "}", value)
						: str.replace("${" + key + "}", value = "NA");
				// str = str.replace("${" + key + "}", value);
			} else if (key.equalsIgnoreCase("splanEndDate") && str.contains("${splanEndDate}")) {
				str = value != null ? str.replace("${" + key + "}", value)
						: str.replace("${" + key + "}", value = "NA");
				// str = str.replace("${" + key + "}", value);
			} else if (key.equalsIgnoreCase("splanValidity") && str.contains("${splanValidity}")) {
				str = value != null ? str.replace("${" + key + "}", value)
						: str.replace("${" + key + "}", value = "NA");
				// str = str.replace("${" + key + "}", value);
			} else if (key.equalsIgnoreCase("splanAmount") && str.contains("${splanAmount}")) {
				str = value != null ? str.replace("${" + key + "}", value)
						: str.replace("${" + key + "}", value = "NA");
				// str = str.replace("${" + key + "}", value);
			} else if (key.equalsIgnoreCase("referUserId") && str.contains("${referUserId}")) {
				str = value != null ? str.replace("${" + key + "}", value)
						: str.replace("${" + key + "}", value = "NA");
				// str = str.replace("${" + key + "}", value);
			} else if (key.equalsIgnoreCase("subsUserId") && str.contains("${subsUserId}")) {
				str = value != null ? str.replace("${" + key + "}", value)
						: str.replace("${" + key + "}", value = "NA");
				// str = str.replace("${" + key + "}", value);
			}

			else if (key.equalsIgnoreCase("acceptedDate") && str.contains("${acceptedDate}")) {
				str = value != null ? str.replace("${" + key + "}", value)
						: str.replace("${" + key + "}", value = "NA");
				// str = str.replace("${" + key + "}", value);
			} else if (key.equalsIgnoreCase("productRequirementName") && str.contains("${productRequirementName}")) {
				str = value != null ? str.replace("${" + key + "}", value)
						: str.replace("${" + key + "}", value = "NA");
				// str = str.replace("${" + key + "}", value);
			} else if (key.equalsIgnoreCase("currency") && str.contains("${currency}")) {
				str = value != null ? str.replace("${" + key + "}", value)
						: str.replace("${" + key + "}", value = "NA");
				// str = str.replace("${" + key + "}", value);
			} else if (key.equalsIgnoreCase("accountnumber") && str.contains("${accountnumber}")) {
				str = value != null ? str.replace("${" + key + "}", value)
						: str.replace("${" + key + "}", value = "NA");
			}

			else if (key.equalsIgnoreCase("ifsccode") && str.contains("${ifsccode}")) {
				str = value != null ? str.replace("${" + key + "}", value)
						: str.replace("${" + key + "}", value = "NA");
			} else if (key.equalsIgnoreCase("bankname") && str.contains("${bankname}")) {
				str = value != null ? str.replace("${" + key + "}", value)
						: str.replace("${" + key + "}", value = "NA");
			} else if (key.equalsIgnoreCase("firstname") && str.contains("${firstname}")) {
				str = value != null ? str.replace("${" + key + "}", value)
						: str.replace("${" + key + "}", value = "NA");
			} else if (key.equalsIgnoreCase("lastname") && str.contains("${lastname}")) {
				str = value != null ? str.replace("${" + key + "}", value)
						: str.replace("${" + key + "}", value = "NA");
			} else if (key.equalsIgnoreCase("country") && str.contains("${country}")) {
				str = value != null ? str.replace("${" + key + "}", value)
						: str.replace("${" + key + "}", value = "NA");
			} else if (key.equalsIgnoreCase("mobileNo") && str.contains("${mobileNo}")) {
				str = value != null ? str.replace("${" + key + "}", value)
						: str.replace("${" + key + "}", value = "NA");
			} else if (key.equalsIgnoreCase("landline") && str.contains("${landline}")) {
				str = value != null ? str.replace("${" + key + "}", value)
						: str.replace("${" + key + "}", value = "NA");
			} else if (key.equalsIgnoreCase("subEmail") && str.contains("${subEmail}")) {
				str = value != null ? str.replace("${" + key + "}", value)
						: str.replace("${" + key + "}", value = "NA");
			} else if (key.equalsIgnoreCase("companyname") && str.contains("${companyname}")) {
				str = value != null ? str.replace("${" + key + "}", value)
						: str.replace("${" + key + "}", value = "NA");
			} else if (key.equalsIgnoreCase("SPLANNAme") && str.contains("${SPLANNAme}")) {
				str = value != null ? str.replace("${" + key + "}", value)
						: str.replace("${" + key + "}", value = "NA");
			} else if (key.equalsIgnoreCase("Credits") && str.contains("${Credits}")) {
				str = value != null ? str.replace("${" + key + "}", value)
						: str.replace("${" + key + "}", value = "NA");
			} else if (key.equalsIgnoreCase("Subsidiaries") && str.contains("${Subsidiaries}")) {
				str = value != null ? str.replace("${" + key + "}", value)
						: str.replace("${" + key + "}", value = "NA");
			} else if (key.equalsIgnoreCase("RM") && str.contains("${RM}")) {
				str = value != null ? str.replace("${" + key + "}", value)
						: str.replace("${" + key + "}", value = "NA");
			} else if (key.equalsIgnoreCase("Price") && str.contains("${Price}")) {
				str = value != null ? str.replace("${" + key + "}", value)
						: str.replace("${" + key + "}", value = "NA");
			} else if (key.equalsIgnoreCase("Validity") && str.contains("${Validity}")) {
				str = value != null ? str.replace("${" + key + "}", value)
						: str.replace("${" + key + "}", value = "NA");
			}

			else if (key.equalsIgnoreCase("currency") && str.contains("${currency}")) {
				str = value != null ? str.replace("${" + key + "}", value)
						: str.replace("${" + key + "}", value = "NA");
			} else if (key.equalsIgnoreCase("couponCode") && str.contains("${couponCode}")) {
				str = value != null ? str.replace("${" + key + "}", value)
						: str.replace("${" + key + "}", value = "NA");
			} else if (key.equalsIgnoreCase("discountType") && str.contains("${discountType}")) {
				str = value != null ? str.replace("${" + key + "}", value)
						: str.replace("${" + key + "}", value = "NA");
			} else if (key.equalsIgnoreCase("startdate") && str.contains("${startdate}")) {
				str = value != null ? str.replace("${" + key + "}", value)
						: str.replace("${" + key + "}", value = "NA");
			} else if (key.equalsIgnoreCase("startTime") && str.contains("${startTime}")) {
				str = value != null ? str.replace("${" + key + "}", value)
						: str.replace("${" + key + "}", value = "NA");
			} else if (key.equalsIgnoreCase("endDate") && str.contains("${endDate}")) {
				str = value != null ? str.replace("${" + key + "}", value)
						: str.replace("${" + key + "}", value = "NA");
			} else if (key.equalsIgnoreCase("endTime") && str.contains("${endTime}")) {
				str = value != null ? str.replace("${" + key + "}", value)
						: str.replace("${" + key + "}", value = "NA");
			} else if (key.equalsIgnoreCase("quantity") && str.contains("${quantity}")) {
				str = value != null ? str.replace("${" + key + "}", value)
						: str.replace("${" + key + "}", value = "NA");
			} else if (key.equalsIgnoreCase("customerType") && str.contains("${customerType}")) {
				str = value != null ? str.replace("${" + key + "}", value)
						: str.replace("${" + key + "}", value = "NA");
			}

			else if (key.equalsIgnoreCase("discountPercentage") && str.contains("${discountPercentage}")) {
				str = value != null ? str.replace("${" + key + "}", value)
						: str.replace("${" + key + "}", value = "NA");
			} else if (key.equalsIgnoreCase("description1") && str.contains("${description1}")) {
				str = value != null ? str.replace("${" + key + "}", value)
						: str.replace("${" + key + "}", value = "NA");
			} else if (key.equalsIgnoreCase("description2") && str.contains("${description2}")) {
				str = value != null ? str.replace("${" + key + "}", value)
						: str.replace("${" + key + "}", value = "NA");
			} else if (key.equalsIgnoreCase("description3") && str.contains("${description3}")) {
				str = value != null ? str.replace("${" + key + "}", value)
						: str.replace("${" + key + "}", value = "NA");
			} else if (key.equalsIgnoreCase("description4") && str.contains("${description4}")) {
				str = value != null ? str.replace("${" + key + "}", value)
						: str.replace("${" + key + "}", value = "NA");
			} else if (key.equalsIgnoreCase("description5") && str.contains("${description5}")) {
				str = value != null ? str.replace("${" + key + "}", value)
						: str.replace("${" + key + "}", value = "NA");
			} else if (key.equalsIgnoreCase("description5") && str.contains("${description5}")) {
				str = value != null ? str.replace("${" + key + "}", value)
						: str.replace("${" + key + "}", value = "NA");
			} else if (key.equalsIgnoreCase("rmName") && str.contains("${rmName}")) {
				str = value != null ? str.replace("${" + key + "}", value)
						: str.replace("${" + key + "}", value = "NA");
			} else if (key.equalsIgnoreCase("rmContactNumber") && str.contains("${rmContactNumber}")) {
				str = value != null ? str.replace("${" + key + "}", value)
						: str.replace("${" + key + "}", value = "NA");
			} else if (key.equalsIgnoreCase("rmEmailId") && str.contains("${rmEmailId}")) {
				str = value != null ? str.replace("${" + key + "}", value)
						: str.replace("${" + key + "}", value = "NA");
			} else if (key.equalsIgnoreCase("customerSupportNumber") && str.contains("${customerSupportNumber}")) {
				str = value != null ? str.replace("${" + key + "}", value)
						: str.replace("${" + key + "}", value = "NA");
			} else if (key.equalsIgnoreCase("accountName") && str.contains("${accountName}")) {
				str = value != null ? str.replace("${" + key + "}", value)
						: str.replace("${" + key + "}", value = "NA");
			} else if (key.equalsIgnoreCase("CompanyAddress") && str.contains("${CompanyAddress}")) {
				str = value != null ? str.replace("${" + key + "}", value)
						: str.replace("${" + key + "}", value = "NA");
			} else if (key.equalsIgnoreCase("bankAccNumber") && str.contains("${bankAccNumber}")) {
				str = value != null ? str.replace("${" + key + "}", value)
						: str.replace("${" + key + "}", value = "NA");
			} else if (key.equalsIgnoreCase("bankName") && str.contains("${bankName}")) {
				str = value != null ? str.replace("${" + key + "}", value)
						: str.replace("${" + key + "}", value = "NA");
			} else if (key.equalsIgnoreCase("bankAddress") && str.contains("${bankAddress}")) {
				str = value != null ? str.replace("${" + key + "}", value)
						: str.replace("${" + key + "}", value = "NA");
			} else if (key.equalsIgnoreCase("bankSwiftCode") && str.contains("${bankSwiftCode}")) {
				str = value != null ? str.replace("${" + key + "}", value)
						: str.replace("${" + key + "}", value = "NA");
			} else if (key.equalsIgnoreCase("intermeditaryBankName") && str.contains("${intermeditaryBankName}")) {
				str = value != null ? str.replace("${" + key + "}", value)
						: str.replace("${" + key + "}", value = "NA");
			} else if (key.equalsIgnoreCase("CompanyAddress2") && str.contains("${CompanyAddress2}")) {
				str = value != null ? str.replace("${" + key + "}", value)
						: str.replace("${" + key + "}", value = "NA");
			} else if (key.equalsIgnoreCase("CompanyAddress3") && str.contains("${CompanyAddress3}")) {
				str = value != null ? str.replace("${" + key + "}", value)
						: str.replace("${" + key + "}", value = "NA");
			} else if (key.equalsIgnoreCase("bankAddress2") && str.contains("${bankAddress2}")) {
				str = value != null ? str.replace("${" + key + "}", value)
						: str.replace("${" + key + "}", value = "NA");
			} else if (key.equalsIgnoreCase("bankAddress3") && str.contains("${bankAddress3}")) {
				str = value != null ? str.replace("${" + key + "}", value)
						: str.replace("${" + key + "}", value = "NA");
			} else if (key.equalsIgnoreCase("businessName") && str.contains("${businessName}")) {
				str = value != null ? str.replace("${" + key + "}", value)
						: str.replace("${" + key + "}", value = "NA");
			} else if (key.equalsIgnoreCase("kycType") && str.contains("${kycType}")) {
				str = value != null ? str.replace("${" + key + "}", value)
						: str.replace("${" + key + "}", value = "NA");
			} else if (key.equalsIgnoreCase("passcodeUserEmail") && str.contains("${passcodeUserEmail}")) {
				str = value != null ? str.replace("${" + key + "}", value)
						: str.replace("${" + key + "}", value = "NA");
			}
			else if (key.equalsIgnoreCase("customerEmailId") && str.contains("${customerEmailId}")) {
				str = value != null ? str.replace("${" + key + "}", value)
					: str.replace("${" + key + "}", value = "NA");
			}

			else if (key.equalsIgnoreCase("orderId") && str.contains("${orderId}")) {
				str = value != null ? str.replace("${" + key + "}", value)
						: str.replace("${" + key + "}", value = "NA");
			}

			else
				str = str.replaceAll("\\$" + key + "\\$", value);
		}

		// System.out.println("body=" + str);

		logger.debug("body=" + str);

		return str;
	}

//	private static String replaceAll1(HashMap hm, String str) {
//
//		Set<String> keys = hm.keySet();
//		// Iterator<String> itr = keys.iterator();
//		for (String key : keys) {
//			// while (itr.hasNext()) {
//			// String key = itr.next();
//			String value = (String) hm.get(key);
//			str = str.replaceAll("\\$" + key + "\\$", value);
//		}
//		// System.out.println("body=" + str);
//		logger.debug("body=" + str);
//
//		return str;
//	}

}
