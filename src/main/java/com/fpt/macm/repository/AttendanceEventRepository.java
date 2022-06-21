package com.fpt.macm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fpt.macm.model.AttendanceEvent;

@Repository
public interface AttendanceEventRepository extends JpaRepository<AttendanceEvent, Integer>{

	List<AttendanceEvent> findByEventScheduleId(int eventScheduleId);
	
}
