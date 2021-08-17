package com.nimai.email.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nimai.email.entity.NimaiToken;
import com.nimai.email.entity.OnlinePayment;

@Repository
public interface OnlinePaymentRepository  extends JpaRepository<OnlinePayment, Integer> {

	@Query(value="SELECT * From nimai_m_online_payment b WHERE b.user_id=:userid AND \r\n" + 
			"			(b.`status`=:status or b.`status`=:appStatus)  \r\n" + 
			"			order by b.inserted_date DESC LIMIT 1",nativeQuery = true)
	OnlinePayment findByuserId(@Param("userid")String userid,@Param("status")String status,@Param("appStatus")String appStatus);

	
	
	
	
	
	@Query("From OnlinePayment b WHERE b.userId=:userid order by b.insertedDate desc")
	OnlinePayment findByUId(@Param("userid")String userid);

}
