package com.fpt.macm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fpt.macm.model.entity.AttendanceEvent;

@Repository
public interface AttendanceEventRepository extends JpaRepository<AttendanceEvent, Integer>{

	List<AttendanceEvent> findByEventId(int eventId);
	
	@Query(value = "SELECT * FROM attendance_event WHERE event_id = ?1 AND member_event_id = ?2", nativeQuery = true)
	Optional<AttendanceEvent> findByEventIdAndMemberEventId(int eventId, int memberEventId);
	
}
