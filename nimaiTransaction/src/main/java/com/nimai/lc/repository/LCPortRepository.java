package com.nimai.lc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nimai.lc.entity.NimaiLCPort;

@Repository
public interface LCPortRepository extends JpaRepository<NimaiLCPort, Integer>
{
	@Query(value="SELECT * from nimai_m_port where country=(:countryName)", nativeQuery = true )
	List<NimaiLCPort> getPort(String countryName);

}
