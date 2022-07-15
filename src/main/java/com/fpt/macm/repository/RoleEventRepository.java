package com.fpt.macm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fpt.macm.constant.Constant;
import com.fpt.macm.model.entity.RoleEvent;

@Repository
public interface RoleEventRepository extends JpaRepository<RoleEvent, Integer>{

	@Query(value = "SELECT * FROM role_event where \"name\" != '" + Constant.ROLE_EVENT_MEMBER + "'", nativeQuery = true)
	List<RoleEvent> findAllOrganizingCommitteeRole();
	
	@Query(value = "SELECT * FROM role_event where \"name\" = '" + Constant.ROLE_EVENT_MEMBER + "'", nativeQuery = true)
	Optional<RoleEvent> findMemberRole();
}
