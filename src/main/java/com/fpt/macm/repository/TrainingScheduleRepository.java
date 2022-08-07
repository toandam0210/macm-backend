package com.fpt.macm.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fpt.macm.model.entity.TrainingSchedule;

@Repository
public interface TrainingScheduleRepository extends JpaRepository<TrainingSchedule, Integer>{
	Optional<TrainingSchedule> findByDate(LocalDate date);
	
	@Query(value = "select * from training_schedule where date between ?1 and ?2 order by date", nativeQuery = true)
	List<TrainingSchedule> listTrainingScheduleByTime(LocalDate startDate, LocalDate finishDate);
	
	@Query(value = "select * from training_schedule where date >= ?1", nativeQuery = true)
	List<TrainingSchedule> findAllFutureTrainingSchedule(LocalDate date);
}
