package com.nimai.admin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nimai.admin.model.NimaiMDisplayFeatures;

@Repository
public interface DisplayFeatureRepository
		extends JpaRepository<NimaiMDisplayFeatures, Integer>, JpaSpecificationExecutor<NimaiMDisplayFeatures> {

	@Query("SELECT DISTINCT country FROM NimaiMDisplayFeatures")
	List<String> findNonReferencedCountry();

	@Query(value = "SELECT count(*) FROM nimai_m_display_features nd where nd.country= :country and nd.ccy= :ccy and nd.status='Active'", nativeQuery = true)
	int getCountryCcy(@Param("country") String country, @Param("ccy") String ccy);

	@Modifying
	@Query(value = "UPDATE nimai_m_display_features c SET c.status = :status WHERE c.country= :country and c.status='Active'", nativeQuery = true)
	int updateDispFeature(@Param("status") String status, @Param("country") String country);
}
