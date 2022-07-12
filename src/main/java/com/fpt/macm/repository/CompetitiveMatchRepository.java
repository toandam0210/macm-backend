package com.fpt.macm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fpt.macm.model.CompetitiveMatch;

@Repository
public interface CompetitiveMatchRepository extends JpaRepository<CompetitiveMatch, Integer>{
	@Query(value = "select * from competitive_match where type_id = ?1", nativeQuery = true)
	List<CompetitiveMatch> listMatchsByType(int competitiveTypeId);
	
	@Query(value = "select * from competitive_match where type_id = ?1 and round = ?2 order by created_on", nativeQuery = true)
	List<CompetitiveMatch> listMatchsByTypeAndRound(int competitiveTypeId, int round);
	
	@Query(value = "select * from competitive_match where type_id = ?1 order by created_on desc", nativeQuery = true)
	List<CompetitiveMatch> listMatchsByTypeDesc(int competitiveTypeId);
}
