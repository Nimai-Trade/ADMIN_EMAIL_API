package com.nimai.admin.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.nimai.admin.model.NimaiToken;
import com.nimai.admin.repository.UserTokenRepository;



@Service
public class TokenServiceImpl implements TokenService
{
	@Autowired
UserTokenRepository tokenRepo;
	
	
	@Override
	public boolean validateToken(String userId, String token) {
		// TODO Auto-generated method stub
		
		NimaiToken nt=tokenRepo.isTokenExists(userId, token);
		if(nt!=null)
			return true;
		else
			return false;
	}

}
