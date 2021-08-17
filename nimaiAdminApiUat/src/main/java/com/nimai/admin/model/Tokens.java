package com.nimai.admin.model;

import java.io.Serializable;



/* To Store Tokens by bashir */
public class Tokens implements Serializable {
	private static final long serialVersionUID = 1L;
	//@Indexed
	private String JWTtoken;

	public String getJWTtoken() {
		return JWTtoken;
	}

	public void setJWTtoken(String jWTtoken) {
		JWTtoken = jWTtoken;
	}

	public Tokens(String jWTtoken) {
		JWTtoken = jWTtoken;
	}

	public Tokens() {
		super();
		// TODO Auto-generated constructor stub
	}

}
