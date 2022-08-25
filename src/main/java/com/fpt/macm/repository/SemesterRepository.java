package com.fpt.macm.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fpt.macm.model.entity.Semester;

@Repository
public interface SemesterRepository extends JpaRepository<Semester, Integer>{
	@Query(value = "select * from semester order by start_date desc limit 3", nativeQuery = true)
	List<Semester> findTop3Semester();

	Optional<Semester> findByName(String name);

}
