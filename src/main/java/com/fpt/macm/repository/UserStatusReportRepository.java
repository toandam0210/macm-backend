package com.fpt.macm.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fpt.macm.model.UserStatusReport;

public interface UserStatusReportRepository extends JpaRepository<UserStatusReport, Integer>{
	Optional<UserStatusReport> findBySemester(String semester);
}
