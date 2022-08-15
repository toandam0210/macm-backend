package com.fpt.macm.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fpt.macm.model.entity.ClubFundReport;

@Repository
public interface ClubFundReportRepository extends JpaRepository<ClubFundReport, Integer> {

	@Query(value = "SELECT * FROM club_fund_report WHERE created_on BETWEEN ?1 AND ?2 order by created_on asc", nativeQuery = true)
	List<ClubFundReport> findAllFundChange(LocalDateTime startDate, LocalDateTime endDate);

}
