package com.nimai.admin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nimai.admin.model.NimaiMRole;

public interface RoleRepository extends JpaRepository<NimaiMRole, Integer>{
	
	@Query("FROM NimaiMRole r where r.roleName = :roleName")
	List<NimaiMRole> findRightsByRoleName( String roleName);

	boolean existsByRoleShortName(String roleShortName);

	@Query("FROM NimaiMRole r where r.roleName = :role")
	NimaiMRole getBankRoleId(String role);
	
	@Query(value = "SELECT * FROM nimai_m_role nr WHERE nr.role_id IN (\n" + 
			"SELECT ur.role_id FROM nimai_mp_user_role ur WHERE ur.EMP_CODE=:empCode)", nativeQuery = true)
	List<NimaiMRole> getDataByEmpCode(String empCode);

}
