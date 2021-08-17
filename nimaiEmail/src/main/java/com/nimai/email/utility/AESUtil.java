package com.nimai.email.utility;




import java.util.Scanner;


import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.Logger;


public class AESUtil {
//	static String str ="";
	//final static Logger logger = Logger.getLogger(ASEUtil.class);

	public static final String ALGORITHM = "AES";
	public static final String SECRET_KEY = "XMzDdG4D03CKm2IxIWQw7g==";
//	public static final String str = "UwVOdLG1OKs+9D1Hh9XPuBmys+7hbdPFCchBG9rjZU4";
//	int numberde =21345678;

	public String encrypt(String text) {
	//	logger.debug("IN DASEUtil -- Encryption starts : " + text);
		byte[] raw;
		String encryptedString;
		SecretKeySpec skeySpec;
		byte[] encryptText = text.getBytes();
		Cipher cipher;
		try {
			raw = Base64.decodeBase64(SECRET_KEY);

			skeySpec = new SecretKeySpec(raw, ALGORITHM);
			cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
			encryptedString = Base64.encodeBase64String(cipher.doFinal(encryptText));

	//		logger.debug("IN ASEUtil -- Encrypted string : " + encryptedString);
		} catch (Exception e) {
	//		logger.debug("IN ASEUtil -- encryption catch block : " + e.getMessage());
			System.out.println("encrypted catch block : " + e.getMessage());
			e.printStackTrace(System.out);
			return text;
		}
		return encryptedString;
	}

	public String decrypt(String text) {
	//	logger.debug("IN ASEUtil -- decryption starts : " + text);
// do some decryption
		Cipher cipher;
		String decryptString;
		byte[] encryptText = null;
		byte[] raw;
		SecretKeySpec skeySpec;
		try {
			raw = Base64.decodeBase64(SECRET_KEY);

			skeySpec = new SecretKeySpec(raw, ALGORITHM);
			encryptText = Base64.decodeBase64(text);
			
			cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			decryptString = new String(cipher.doFinal(encryptText));

	//		logger.debug("IN ASEUtil decrypt -- decrypted string : " + decryptString);
		} catch (Exception e) {
			System.out.println("decrypted catch block : " + e.getMessage());
	//		logger.debug("IN ASEUtil decrypt -- decryption catch block : " + e.getMessage());
			e.printStackTrace(System.out);
			return text;
		}
		return decryptString;
	}

	private static AESUtil aSEUtil = null;

	public static AESUtil getInstance() {
		if (aSEUtil == null) {
			aSEUtil = new AESUtil();
		}
		return aSEUtil;
	}

	
	public String method(String url) {
		int index=url.indexOf("@");
		String id=url.substring(index+1, url.length());
		
		return id;
		
	}
	
	public static void main(String[] args) {
		AESUtil aesUtil=new AESUtil();
		
		
		System.out.println(aesUtil.method("qewrt@654"));
//		
//		System.out.println(aesUtil.encrypt("{\r\n" + 
//				"\r\n" + 
//				"    \"empCode\": \"CLOVER\",\r\n" + 
//				"\"event\": \"ACCOUNT_ACTIVATE\",\r\n" + 
//				"\"link\": \"http://136.232.244.190:8081/nimai_admin/#/change-password?userId=NIMAI&token=41970\",\r\n" + 
//				"\"userId\": \"CLOVER\",\r\n" + 
//				"\"userName\": \"Nimai\"\r\n" + 
//				"}"));
//		System.out.println(aesUtil.decrypt(aesUtil.encrypt("{\r\n" + 
//				"\r\n" + 
//				"    \"empCode\": \"CLOVER\",\r\n" + 
//				"\"event\": \"ACCOUNT_ACTIVATE\",\r\n" + 
//				"\"link\": \"http://136.232.244.190:8081/nimai_admin/#/change-password?userId=NIMAI&token=41970\",\r\n" + 
//				"\"userId\": \"CLOVER\",\r\n" + 
//				"\"userName\": \"Nimai\"\r\n" + 
//				"}")));
//	}
	}
}