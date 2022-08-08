package com.fpt.macm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fpt.macm.model.entity.AttendanceEvent;

@Repository
public interface AttendanceEventRepository extends JpaRepository<AttendanceEvent, Integer>{

	List<AttendanceEvent> findByEventId(int eventId);
	
	Optional<AttendanceEvent> findByEventIdAndUserId(int eventId, int userId);
	
}
