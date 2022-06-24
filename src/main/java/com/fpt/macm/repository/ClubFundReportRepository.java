package com.fpt.macm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fpt.macm.model.ClubFundReport;

@Repository
public interface ClubFundReportRepository extends JpaRepository<ClubFundReport, Integer>{

}
