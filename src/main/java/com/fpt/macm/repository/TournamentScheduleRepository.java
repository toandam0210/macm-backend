package com.fpt.macm.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fpt.macm.model.entity.TournamentSchedule;

@Repository
public interface TournamentScheduleRepository extends JpaRepository<TournamentSchedule, Integer> {
	@Query(value = "select * from tournament_schedule where tournament_id = ?1 order by date",nativeQuery = true)
	List<TournamentSchedule> findByTournamentId(int tournamentId);
	
	Optional<TournamentSchedule> findByDate(LocalDate date);
}
