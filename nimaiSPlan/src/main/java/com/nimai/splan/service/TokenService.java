package com.nimai.splan.service;

public interface TokenService 
{
	public boolean validateToken(String userId,String token);
}
