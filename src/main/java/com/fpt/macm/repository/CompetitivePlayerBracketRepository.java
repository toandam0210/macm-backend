package com.fpt.macm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fpt.macm.model.CompetitivePlayerBracket;

public interface CompetitivePlayerBracketRepository extends JpaRepository<CompetitivePlayerBracket, Integer>{
	
	@Query(value = "select * from competitive_player_bracket where player_id = ?1", nativeQuery = true)
	Optional<CompetitivePlayerBracket> findByPlayerId(int competitivePlayerId);
	
	@Query(value = "select * from competitive_player_bracket where competitive_type_id = ?1 and round = ?2", nativeQuery = true)
	List<CompetitivePlayerBracket> listByTypeAndRound(int competitiveTypeId, int round);
	
	@Query(value = "select * from competitive_player_bracket where competitive_type_id = ?1 and round = ?2 order by numerical_order_id", nativeQuery = true)
	List<CompetitivePlayerBracket> listSortByNumerical(int competitiveTypeId, int round);
	
	@Query(value = "select * from competitive_player_bracket where competitive_player_id = ?1 limit 1", nativeQuery = true)
	Optional<CompetitivePlayerBracket> listByPlayer(int competitivePlayId);
}
