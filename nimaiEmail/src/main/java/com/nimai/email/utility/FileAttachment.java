package com.nimai.email.utility;

import java.io.File;


import com.nimai.email.constants.Constants;
import com.nimai.email.emailproperties.MasterFilePath;
import com.nimai.email.emailproperties.PropertyHandler;
import com.nimai.email.entity.EmailComponentMaster;


public class FileAttachment extends InlineImage {
	public void process(EmailComponentMaster mailcomponentmaster){
		
        PropertyHandler propertyHandler= new PropertyHandler(MasterFilePath.getMasterfilepath());
	File baseDir=new File(propertyHandler.getProperty("basePathForEmailAttachment"));  
	if(getFileType().equalsIgnoreCase(Constants.COMMON_IMAGE_FILE)){
		setFileObject(new File(baseDir, getFileName()));
	}else if(getFileType().equalsIgnoreCase(Constants.PRODUCT_SPECIFIC_IMAGE)){
		long productid=1l;
		File baseDir1=new File(baseDir,productid+"");
		setFileObject(new File(baseDir1, getFileName()));
	} 
}
}
