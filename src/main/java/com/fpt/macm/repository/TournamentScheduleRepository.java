package com.fpt.macm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fpt.macm.model.TournamentSchedule;

@Repository
public interface TournamentScheduleRepository extends JpaRepository<TournamentSchedule, Integer> {
	@Query(value = "select * from tournament_schedule where event_id = ?1 order by date", nativeQuery = true)
	List<TournamentSchedule> findByTournamentId(int tournamentId);
}
