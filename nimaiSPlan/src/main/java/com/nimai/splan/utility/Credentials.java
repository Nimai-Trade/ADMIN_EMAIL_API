package com.nimai.splan.utility;

import org.springframework.beans.factory.annotation.Value;

import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;

public class Credentials 
{
	
	//UAT
	static String clientId="AXb4EAPs5uvAkO4cAmmFK0GaBWCwuMw8ZoIcVNXFFC6sJaF3FsZsWRg7DgYo4PQ0omt9NgUgIMaASwao";
	static String secret="EB92cKwHyAhZZW-VyCE90FGyLgOUAz7lFyJYQnqaJ-3xgAqk_M-q_27tns8lgZr4_gTk0dwiCnSMiCkv";
	private static PayPalEnvironment environment = new PayPalEnvironment.Sandbox(clientId, secret);
	
	
	//Prod
	/*static String clientId = "ARakRQp7F_xil8H_kNWG8a7aOEf4ElCzumdY4vIGmbaRtQv8HKFLRw_pTLVKV4pKv4N8IbnjwDzyz5cP";
    static String secret = "EKdLnZRgT7LusUEPyUlAH0O5Fwv5xD1TiBTVhzF4vxO1PdoBnGQG2KJkMM7v-BwyXbs0XkeMpRTIMk_V";
    private static PayPalEnvironment environment = new PayPalEnvironment.Live(clientId, secret);
    */
    
    // Creating a client for the environment
    public static PayPalHttpClient client = new PayPalHttpClient(environment);
    
}