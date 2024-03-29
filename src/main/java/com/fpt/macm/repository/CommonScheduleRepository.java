package com.fpt.macm.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fpt.macm.model.entity.CommonSchedule;

@Repository
public interface CommonScheduleRepository extends JpaRepository<CommonSchedule, Integer>{
	Optional<CommonSchedule> findByDate(LocalDate date);
	
	@Query(value = "select * from common_schedule order by date asc", nativeQuery = true)
	List<CommonSchedule> listAll();
	
	@Query(value = "select * from common_schedule where date between ?1 and ?2", nativeQuery = true)
	List<CommonSchedule> listCommonScheduleByTime(LocalDate startDate, LocalDate finishDate);
}
