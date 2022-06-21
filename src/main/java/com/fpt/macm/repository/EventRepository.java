package com.fpt.macm.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fpt.macm.model.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer>{
	@Query(value = "select * from event where name like %?1%", nativeQuery = true)
	Page<Event> findByName(Pageable pageable, String name);
	
	@Query(value = "select * from event where semester like %?1%", nativeQuery = true)
	List<Event> findBySemester(String semester);
	
	
}
