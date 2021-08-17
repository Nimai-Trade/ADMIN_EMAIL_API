package com.nimai.email.constants;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



/**
 *
 * @author sahadeo.naik
 */
public class Constants {

    public static final int VALUE_STRING = 0;
    public static final int VALUE_NUMBER = 1;
    public static final int VALUE_ENCRYPTED = 2;

    public static final int CONFIGURATION_RELOAD = 0;
    public static final int CONFIGURATION_NOTRELOAD = 1;

    //sms
    public static final int SMS_DISABLED = 0;
    public static final int SMS_ENABLED = 1;

    public static final int EMAIL_DISABLED = 0;
    public static final int EMAIL_ENABLED = 1;

    //email event starts
    public static final byte EMAIL_OTPMAIL_TYPE = 1;
    public static final byte EMAIL_WECOMEMAIL_TYPE = 2;

    public static final String EMAIL_OTPMAIL = "OTPMAIL";

    public static final int EMAIL_NOT_SEND_FLG = 0;
    public static final int EMAIL_SEND_FLG = 1;
    public static final byte EMAIL_INSERT_INTO_DB = 0;
    public static final String COMMON_IMAGE_FILE = "0";
    public static final String PRODUCT_SPECIFIC_IMAGE = "1";
    
    public static final int LOGIN_ATTEMPTS = 4;
    public static final int FIRST_TIME_LOGIN = -1;
    public static final int PWD_EXPIRY_PERIOD_IN_DAYS = 30;
    public static final int USER_ACCOUNT_LOCK = 0;
    public static final int USER_ACCOUNT_UNLOCK = 1;
    public static final int PASSWORD_CHECK = 4;
    
    public static final String CONST_USER ="NIMAI";
    
    public static final String ACCOUNT_LOCK = "Your Account has been locked ";
    public static final String F_CHANGE_PASSWORD = "New User, Please set your password";
    public static final String E_CHANGE_PASSWORD = "Your Password has expired, Please set New Password";
    public static final String INVALID = "Invalid Username and Password";
    public static final String NOT_EXISTS = "User does not exits, Please contact to Admin";
       
}



