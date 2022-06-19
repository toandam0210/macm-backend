package com.fpt.macm.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fpt.macm.model.EventSchedule;

@Repository
public interface EventScheduleRepository extends JpaRepository<EventSchedule, Integer>{
	Optional<EventSchedule> findByDate(LocalDate date);
	
	@Query(value = "select * from event_schedule where eventId like ?1 order by startDate", nativeQuery = true)
	List<EventSchedule> findByEventId(int eventId);
}
