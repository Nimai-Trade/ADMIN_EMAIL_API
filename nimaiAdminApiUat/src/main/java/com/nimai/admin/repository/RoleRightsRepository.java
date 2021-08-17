package com.nimai.admin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.nimai.admin.model.NimaiMpRoleRights;

public interface RoleRightsRepository
		extends JpaRepository<NimaiMpRoleRights, Integer>, JpaSpecificationExecutor<NimaiMpRoleRights> {

	@Query("FROM NimaiMpRoleRights r where r.roleId.roleName = :roleName and status = 'ACTIVE'")
	List<NimaiMpRoleRights> findRightsByRoleName(String roleName);

	@Query("FROM NimaiMpRoleRights r where r.roleId.roleId = :roleId and status = 'ACTIVE' ")
	List<NimaiMpRoleRights> findRightsByRoleId(Integer roleId);
	
	@Query("select rightId.rightId FROM NimaiMpRoleRights r where r.roleId.roleId = :roleId and status = 'ACTIVE' ")
	List<Integer> findRightsIdByRoleId(Integer roleId);

	
	
	
	
	@Query("FROM NimaiMpRoleRights r where r.roleId.roleId = :roleId and r.rightId.rightId =:rightId and status = 'ACTIVE'")
	NimaiMpRoleRights getByRoleAndRight(Integer roleId, Integer rightId);

}
