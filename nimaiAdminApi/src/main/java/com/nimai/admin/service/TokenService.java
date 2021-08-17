package com.nimai.admin.service;

public interface TokenService 
{
	public boolean validateToken(String userId,String token);
}
