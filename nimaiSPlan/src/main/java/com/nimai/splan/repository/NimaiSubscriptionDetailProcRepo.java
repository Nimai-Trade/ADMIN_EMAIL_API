package com.nimai.splan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nimai.splan.model.NimaiMCustomer;

@Repository
public interface NimaiSubscriptionDetailProcRepo extends JpaRepository<NimaiMCustomer, String> {
	//userid,emailAddress
	@Procedure("GET_DETAILS_FOR_DASHBOARD")
    //void getCount(String userid,String emailAddress);
	public void getCount(@Param("i_user_id") String userid,@Param("i_email_id") String emailAddress);
}
