package com.nimai.email.repository;



import org.springframework.data.jpa.repository.JpaRepository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nimai.email.entity.NimaiToken;

@Repository
public interface NimaiTokenRepository extends JpaRepository<NimaiToken, String> {

	@Query(value="from NimaiToken nm WHERE nm.userId= :userId AND nm.token=:token")
	NimaiToken isTokenExists(@Param("userId")String userId,@Param("token") String token);

	
	

	@Query(value="UPDATE nimai_m_token nb set nb.isInvalidCaptcha= :flag where nb.user_id= :userId",nativeQuery = true)
	void updateInvalCaptcha(@Param("userId")String userId,@Param("flag") String flag);

}
