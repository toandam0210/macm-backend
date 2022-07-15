package com.fpt.macm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fpt.macm.model.entity.CompetitiveMatch;

@Repository
public interface CompetitiveMatchRepository extends JpaRepository<CompetitiveMatch, Integer>{
	@Query(value = "select * from competitive_match where type_id = ?1", nativeQuery = true)
	List<CompetitiveMatch> listMatchsByType(int competitiveTypeId);
	
	@Query(value = "select * from competitive_match where type_id = ?1 and order by created_on", nativeQuery = true)
	List<CompetitiveMatch> listMatchsByTypeAndRound(int competitiveTypeId);
	
	@Query(value = "select * from competitive_match where type_id = ?1 order by created_on desc", nativeQuery = true)
	List<CompetitiveMatch> listMatchsByTypeDesc(int competitiveTypeId);
	
	@Query(value = "select * from competitive_match where type_id = ?1 order by created_on asc", nativeQuery = true)
	List<CompetitiveMatch> listMatchsByTypeAsc(int competitiveTypeId);
	
//	@Query(value = "select competitive_match.id, competitive_match.type_id , round, first_student_id, second_student_id, next_is_first, next_match_id, lose_match_id,\r\n"
//			+ "competitive_match.created_by, competitive_match.created_on, competitive_match.updated_by, competitive_match.updated_on\r\n"
//			+ "from competitive_match inner join competitive_type on competitive_match.type_id = competitive_type.id\r\n"
//			+ "where tournament_id = ?1 order by round, type_id", nativeQuery = true)
//	List<CompetitiveMatch> listMatchsByTournament(int tournamentId);
}
