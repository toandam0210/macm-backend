package com.fpt.macm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fpt.macm.model.AttendanceStatus;

@Repository
public interface AttendanceStatusRepository extends JpaRepository<AttendanceStatus, Integer> {
	List<AttendanceStatus> findByTrainingScheduleId(int trainingScheduleId);
}
