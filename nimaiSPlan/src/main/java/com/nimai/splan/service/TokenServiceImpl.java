package com.nimai.splan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nimai.splan.model.NimaiToken;
import com.nimai.splan.repository.UserTokenRepository;

@Service
public class TokenServiceImpl implements TokenService
{

	@Autowired
	private UserTokenRepository userTokenRepository;
	
	@Override
	public boolean validateToken(String userId, String token) {
		// TODO Auto-generated method stub
		
		NimaiToken nt=userTokenRepository.isTokenExists(userId, token);
		if(nt!=null)
			return true;
		else
			return false;
	}

}
