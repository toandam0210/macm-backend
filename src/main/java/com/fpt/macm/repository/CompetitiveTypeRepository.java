package com.fpt.macm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fpt.macm.model.entity.CompetitiveType;

@Repository
public interface CompetitiveTypeRepository extends JpaRepository<CompetitiveType, Integer> {
	
	@Query(value = "select * from competitive_type where tournament_id = ?1 and gender = ?2 order by weight_min asc", nativeQuery = true)
	List<CompetitiveType> findByTournamentAndGender(int tournamentId, boolean gender);
	
	@Query(value = "select tournament_id from competitive_type where id = ?1", nativeQuery = true)
	int findTournamentOfType(int competitiveTypeId);
	
	@Query(value = "select ct.tournament_id from \r\n"
			+ "competitive_player cp inner join competitive_player_bracket cpb on cp.id = cpb.player_id \r\n"
			+ "                      inner join competitive_type ct on cpb.competitive_type_id = ct.id\r\n"
			+ "where cp.id = ?1", nativeQuery =  true)
	int findTournamentByCompetitivePlayerId(int competitivePlayerId);
}
