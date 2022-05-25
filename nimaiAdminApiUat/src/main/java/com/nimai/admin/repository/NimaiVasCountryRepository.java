package com.nimai.admin.repository;

import com.nimai.admin.model.NimaiMVasCountry;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

@Transactional
public interface NimaiVasCountryRepository extends JpaRepository<NimaiMVasCountry, Integer> {
  @Query(value = "select nv.vas_country from nimai_m_vascoutry nv where nv.vas_id=:vasId ", nativeQuery = true)
  List<String> getVasCountryList(Integer vasId);
  
  @Modifying
  @Query(value = "delete from nimai_m_vascoutry where vas_id=:vasid", nativeQuery = true)
  void deleteSPLanById(int vasid);
}
