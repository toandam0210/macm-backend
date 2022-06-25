package com.fpt.macm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fpt.macm.model.TournamentSchedule;

@Repository
public interface TournamentScheduleRepository extends JpaRepository<TournamentSchedule, Integer> {
	List<TournamentSchedule> findByTournamentId(int tournamentId);
}
