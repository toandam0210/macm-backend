package com.fpt.macm.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fpt.macm.model.entity.CompetitivePlayer;

@Repository
public interface CompetitivePlayerRepository extends JpaRepository<CompetitivePlayer, Integer>{
	Optional<CompetitivePlayer> findByTournamentPlayerId(int tournamentPlayerId);
	
	@Query(value = "select * from competitive_player where player_id = ?1", nativeQuery = true)
	Optional<CompetitivePlayer> findCompetitivePlayerByTournamentPlayerId(int tournamentPlayerId);
}
