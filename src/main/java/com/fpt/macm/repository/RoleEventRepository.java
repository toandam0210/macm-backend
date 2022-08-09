package com.fpt.macm.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fpt.macm.model.entity.RoleEvent;

@Repository
public interface RoleEventRepository extends JpaRepository<RoleEvent, Integer>{
	
	Optional<RoleEvent> findByName(String name);

//	@Query(value = "SELECT * FROM role_event where id != 1", nativeQuery = true)
//	List<RoleEvent> findAllOrganizingCommitteeRole();
	
}
