package com.nimai.admin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nimai.admin.model.BankRatingModel;
import com.nimai.admin.model.MasterRating;

@Repository
public interface MasterRatingsRepository extends JpaRepository<MasterRating, Integer>{

	@Query("from MasterRating mr where mr.agency=(:agency) order by mr.agency,mr.scale")
	List<MasterRating> getRatings(String agency);
	
	@Query(value="SELECT DISTINCT agency FROM nimai_m_ratings ORDER BY agency",nativeQuery = true)
	List getAgency();

}
