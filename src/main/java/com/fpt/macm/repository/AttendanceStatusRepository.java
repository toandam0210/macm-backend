package com.fpt.macm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fpt.macm.model.entity.AttendanceStatus;

@Repository
public interface AttendanceStatusRepository extends JpaRepository<AttendanceStatus, Integer> {
	List<AttendanceStatus> findByTrainingScheduleIdOrderByIdAsc(int trainingScheduleId);
	
	@Query(value = "select * from  attendance_status where user_id = ?1", nativeQuery = true)
	List<AttendanceStatus> findByUserId(int userId);
	
	@Query(value = "select * from  attendance_status where user_id = ?1 and traning_schedule_id = ?2", nativeQuery = true)
	AttendanceStatus findByUserIdAndTrainingScheduleId(int userId, int trainingScheduleId);
	
	List<AttendanceStatus> findByTrainingScheduleIdAndStatus(int trainingScheduleId, int status);
	
}
