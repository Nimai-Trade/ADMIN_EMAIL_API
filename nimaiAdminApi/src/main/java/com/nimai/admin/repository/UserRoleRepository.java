package com.nimai.admin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nimai.admin.model.NimaiMpRoleRights;
import com.nimai.admin.model.NimaiMpUserRole;

public interface UserRoleRepository extends JpaRepository<NimaiMpUserRole, Integer>  {

	@Query("FROM NimaiMpUserRole r where r.empCode.empCode = :empCode and r.roleId.roleName = :roleName")
	List<NimaiMpUserRole> findRightsByRoleName(String empCode, String roleName);
	
	
	@Query("FROM NimaiMpUserRole r where r.empCode.empCode = :empCode and status = 'ACTIVE'")
	List<NimaiMpUserRole> findUserRoleId(String empCode);
	
//	@Query("FROM NimaiMpUserRole r where r.empCode.empCode = :empCode")
//	Optional<NimaiMpUserRole> findByEmpCode(@Param("empCode") String empCode);
	
	@Query("FROM NimaiMpUserRole r where r.empCode.empCode = :empCode and status = 'ACTIVE'")
	List<NimaiMpUserRole> findByEmpCode(@Param("empCode") String empCode);



	
//	@Query("FROM NimaiMpUserRole r where r.roleId.roleName like %RM% and r.status = 'ACTIVE'")
//	List<NimaiMpUserRole> findRightsByRmRoleName();

}
