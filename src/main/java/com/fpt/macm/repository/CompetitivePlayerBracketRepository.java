package com.fpt.macm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fpt.macm.model.CompetitivePlayerBracket;

@Repository
public interface CompetitivePlayerBracketRepository extends JpaRepository<CompetitivePlayerBracket, Integer>{
	
	@Query(value = "select * from competitive_player_bracket where player_id = ?1", nativeQuery = true)
	Optional<CompetitivePlayerBracket> findByPlayerId(int competitivePlayerId);
	
	@Query(value = "select * from competitive_player_bracket where competitive_type_id = ?1 order by numerical_order_id", nativeQuery = true)
	List<CompetitivePlayerBracket> listSortByNumerical(int competitiveTypeId);
	
	@Query(value = "select * from competitive_player_bracket where competitive_player_id = ?1 limit 1", nativeQuery = true)
	Optional<CompetitivePlayerBracket> listByPlayer(int competitivePlayId);
	
	@Query(value = "select * from competitive_player_bracket where competitive_type_id = ?1", nativeQuery = true)
	List<CompetitivePlayerBracket> listPlayersByType(int competitiveTypeId);
}
