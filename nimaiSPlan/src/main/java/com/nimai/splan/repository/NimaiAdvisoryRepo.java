package com.nimai.splan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nimai.splan.model.NimaiAdvisory;
import com.nimai.splan.model.NimaiSubscriptionVas;

@Transactional
@Repository
public interface NimaiAdvisoryRepo extends JpaRepository<NimaiAdvisory, String> {

	@Query(value="SELECT * FROM nimai_m_vas na INNER JOIN nimai_m_vascoutry vas ON na.VAS_ID=vas.vasid WHERE na.CUSTOMER_TYPE=(:customerType) "
			+ " and vas.vas_country = (:country_name) and na.status='Active'",nativeQuery = true)
	List<NimaiAdvisory> findByCountryName(@Param("country_name") String country_name, @Param("customerType") String customerType);

	@Query("SELECT na FROM NimaiAdvisory na WHERE na.vas_id = (:vasId) and na.status='Active'")
	NimaiAdvisory getDataByVasId(int vasId);
	
	@Query("SELECT na FROM NimaiAdvisory na WHERE na.vas_id = (:vasId)")
	NimaiAdvisory getVASDetByVasId(int vasId);
	
	@Query(value = "select pricing from nimai_m_vas where vas_id=(:vasId)", nativeQuery = true)
	Double findPricingByVASId(Integer vasId);
	
	@Query(value="SELECT system_config_entity_value from nimai_system_config where system_config_entity='invoice_gst'", nativeQuery = true )
	Double getGSTValue();
	
	@Modifying
	@Query(value = "update nimai_subscription_vas set spl_serial_number=:spNo where userid=:userId and status='ACTIVE'", nativeQuery = true)
	void updateSplSerialNo(String userId,Integer spNo);
}
