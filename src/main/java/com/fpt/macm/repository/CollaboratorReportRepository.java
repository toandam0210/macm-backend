package com.fpt.macm.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fpt.macm.model.entity.CollaboratorReport;

@Repository
public interface CollaboratorReportRepository extends JpaRepository<CollaboratorReport, Integer> {
	
	Optional<CollaboratorReport> findBySemester(String semester);

}
