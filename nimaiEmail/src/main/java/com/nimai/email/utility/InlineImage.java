package com.nimai.email.utility;

import java.io.File;

import com.nimai.email.constants.Constants;
import com.nimai.email.emailproperties.MasterFilePath;
import com.nimai.email.emailproperties.PropertyHandler;
import com.nimai.email.entity.EmailComponentMaster;

public class InlineImage {
	private String tagName;
	private String fileName;
	private String fileType;
	private File fileObject;

	public File getFileObject() {
		return fileObject;
	}

	public void setFileObject(File fileObject) {
		this.fileObject = fileObject;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public void process(EmailComponentMaster emailConfigurationBean) {

		PropertyHandler propertyHandler = new PropertyHandler(MasterFilePath.getMasterfilepath());
		File baseDir = new File(propertyHandler.getProperty("basePathForEmailInlineImages"));
		if (fileType.equalsIgnoreCase(com.nimai.email.constants.Constants.COMMON_IMAGE_FILE)) {
			fileObject = new File(baseDir, fileName);
		} else if (fileType.equalsIgnoreCase(Constants.PRODUCT_SPECIFIC_IMAGE)) {
			System.out.println("InlineImage-Reached here...");
			long productid = 1l;// emailConfigurationBean.getProductMasterBean().getProduct_id();
			System.out.println("InlineImage-Reached here-" + productid);
			File baseDir1 = new File(baseDir, productid + "");
			fileObject = new File(baseDir1, fileName);
		}
	}

	public void process() {

		PropertyHandler propertyHandler = new PropertyHandler(MasterFilePath.getMasterfilepath());
		File baseDir = new File(propertyHandler.getProperty("basePathForEmailInlineImages"));
		if (fileType.equalsIgnoreCase(Constants.COMMON_IMAGE_FILE)) {
			fileObject = new File(baseDir, fileName);
		} else if (fileType.equalsIgnoreCase(Constants.PRODUCT_SPECIFIC_IMAGE)) {
			System.out.println("InlineImage-Reached here...");
			long productid = 1l;// emailConfigurationBean.getProductMasterBean().getProduct_id();
			System.out.println("InlineImage-Reached here-" + productid);
			File baseDir1 = new File(baseDir, productid + "");
			fileObject = new File(baseDir1, fileName);
		}
	}

}
