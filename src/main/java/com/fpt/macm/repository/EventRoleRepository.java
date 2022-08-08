package com.fpt.macm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fpt.macm.model.entity.EventRole;

@Repository
public interface EventRoleRepository extends JpaRepository<EventRole, Integer>{

	List<EventRole> findByEventId(int eventId);
	
	Optional<EventRole> findByRoleEventIdAndEventId(int roleEventId, int eventId);
}
