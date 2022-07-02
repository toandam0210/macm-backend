package com.fpt.macm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fpt.macm.model.TournamentPlayerPaymentStatusReport;

@Repository
public interface TournamentPlayerPaymentStatusReportRepository extends JpaRepository<TournamentPlayerPaymentStatusReport, Integer>{

	List<TournamentPlayerPaymentStatusReport> findByTournamentId(int tournamentId);
	
}
