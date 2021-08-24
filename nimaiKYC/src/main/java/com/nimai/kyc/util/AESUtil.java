package com.nimai.kyc.util;




import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.tomcat.util.codec.binary.Base64;


public class AESUtil {


	public static final String ALGORITHM = "AES";
	public static final String SECRET_KEY = "OVynafBWAupXfA276piYqVcfUTuWQWdn4FRWkC7PQFY=";


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

}
	

