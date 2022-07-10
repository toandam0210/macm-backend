package com.fpt.macm.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fpt.macm.model.TournamentOrganizingCommitteePaymentStatusReport;

@Repository
public interface TournamentOrganizingCommitteePaymentStatusReportRepository
		extends JpaRepository<TournamentOrganizingCommitteePaymentStatusReport, Integer> {

	List<TournamentOrganizingCommitteePaymentStatusReport> findByTournamentId(int tournamentId);
	
	@Query(value = "SELECT * FROM tournament_organizing_committee_payment_status_report WHERE created_on BETWEEN ?1 AND ?2", nativeQuery = true)
	List<TournamentOrganizingCommitteePaymentStatusReport> findAllFundChange(LocalDateTime startDate, LocalDateTime endDate);
	
}
