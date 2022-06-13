package com.fpt.macm.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fpt.macm.model.TrainingSchedule;

@Repository
public interface TrainingScheduleRepository extends JpaRepository<TrainingSchedule, Integer>{
	@Query(value = "SELECT * FROM training_schedule t WHERE t.date = ?1", nativeQuery = true)
	List<TrainingSchedule> getTrainingSchedule(LocalDate getDay);
}
