package com.fpt.macm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fpt.macm.model.entity.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer>{
	@Query(value = "select * from event where name like %?1%", nativeQuery = true)
	List<Event> findByName(String name);
	
	@Query(value = "select * from event where semester like %?1%", nativeQuery = true)
	List<Event> findBySemester(String semester);
	
	@Query(value = "select * from event where name like ?1", nativeQuery = true)
	Optional<Event> findByExactName(String name);
	
}
