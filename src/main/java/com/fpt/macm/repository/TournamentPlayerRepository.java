package com.fpt.macm.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fpt.macm.model.TournamentPlayer;

@Repository
public interface TournamentPlayerRepository extends JpaRepository<TournamentPlayer, Integer> {
	@Query(value = "select * from tournament_player where user_id = ?1 and tournament_id = ?2", nativeQuery = true)
	Optional<TournamentPlayer> getPlayerByUserIdAndTournamentId(int userId, int tournamentId);
}
