package com.fpt.macm.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fpt.macm.model.CompetitivePlayer;

@Repository
public interface CompetitivePlayerRepository extends JpaRepository<CompetitivePlayer, Integer>{

	Optional<CompetitivePlayer> findByTournamentPlayerId(int tournamentPlayerId);
	
}
