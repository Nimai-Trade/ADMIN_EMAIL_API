package com.nimai.splan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nimai.splan.model.NimaiToken;


@Repository
public interface UserTokenRepository extends JpaRepository<NimaiToken, String>
{
	 @Query("from NimaiToken where userId= :userId and token= :token")
	 NimaiToken isTokenExists(String userId,String token);
}
