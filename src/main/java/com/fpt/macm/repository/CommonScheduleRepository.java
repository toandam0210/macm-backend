package com.fpt.macm.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fpt.macm.model.CommonSchedule;

@Repository
public interface CommonScheduleRepository extends JpaRepository<CommonSchedule, Integer>{
	Optional<CommonSchedule> findByDate(LocalDate date);
}
