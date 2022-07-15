package com.fpt.macm.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fpt.macm.model.entity.TournamentPlayerPaymentStatusReport;

@Repository
public interface TournamentPlayerPaymentStatusReportRepository extends JpaRepository<TournamentPlayerPaymentStatusReport, Integer>{

	List<TournamentPlayerPaymentStatusReport> findByTournamentId(int tournamentId);
	
	@Query(value = "SELECT * FROM tournament_player_payment_status_report WHERE created_on BETWEEN ?1 AND ?2", nativeQuery = true)
	List<TournamentPlayerPaymentStatusReport> findAllFundChange(LocalDateTime startDate, LocalDateTime endDate);
}
