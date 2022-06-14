package com.fpt.macm.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fpt.macm.model.TrainingSchedule;

@Repository
public interface TrainingScheduleRepository extends JpaRepository<TrainingSchedule, Integer>{
	Optional<TrainingSchedule> findByDate(LocalDate date);
}
