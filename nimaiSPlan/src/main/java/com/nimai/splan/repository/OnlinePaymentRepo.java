package com.nimai.splan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nimai.splan.model.OnlinePayment;

@Transactional
@Repository
public interface OnlinePaymentRepo extends JpaRepository<OnlinePayment, Integer> {

	@Query(value="SELECT * from nimai_m_online_payment where user_id=(:userId) order by id desc limit 1", nativeQuery = true )
	OnlinePayment getDetailsByUserId(String userId);
	
}
