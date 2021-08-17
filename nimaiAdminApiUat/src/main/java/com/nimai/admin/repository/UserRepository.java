package com.nimai.admin.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nimai.admin.model.NimaiMLogin;

/**
 * Created by sahadeo
 */
@Repository
public interface UserRepository extends JpaRepository<NimaiMLogin, Long> {

	@Query("FROM NimaiMLogin r where r.empCode.empCode = :empCode")
	Optional<NimaiMLogin> findByEmpCode(@Param("empCode") String empCode);

	@Query("FROM NimaiMLogin r where r.empCode.empCode = :empCode")
	Boolean existsByUsername(String empCode);

	@Query("FROM NimaiMLogin r where r.empCode.empEmail = :email")
	Boolean existsByEmail(String email);
	
	@Query("FROM NimaiMLogin r where r.empCode.empEmail = :email")
	Optional<NimaiMLogin> existsByEmailId(String email);
	
	@Query("FROM NimaiMLogin r where r.userid.userid = :userid")
	NimaiMLogin findByUserId(@Param("userid") String userid);

	@Query("FROM NimaiMLogin r where r.empCode.empCode = :empCode")
	NimaiMLogin existsByEmpCode(String empCode);

}
