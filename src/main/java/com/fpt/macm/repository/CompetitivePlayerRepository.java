package com.fpt.macm.repository;

import java.util.List;
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
	
	@Query(value = "select * from competitive_player where competitive_type_id = ?1", nativeQuery = true)
	List<CompetitivePlayer> findByCompetitiveTypeId(int competitiveTypeId);
	
	@Query(value = "select * from competitive_player where is_eligible = true and competitive_type_id = ?1", nativeQuery = true)
	List<CompetitivePlayer> findEligibleByCompetitiveTypeId(int competitiveTypeId);
}
