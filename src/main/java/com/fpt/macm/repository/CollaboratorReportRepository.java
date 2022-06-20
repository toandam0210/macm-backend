package com.fpt.macm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fpt.macm.model.CollaboratorReport;

@Repository
public interface CollaboratorReportRepository extends JpaRepository<CollaboratorReport, Integer> {

}
