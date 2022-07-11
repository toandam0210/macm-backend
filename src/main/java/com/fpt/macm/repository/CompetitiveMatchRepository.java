package com.fpt.macm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fpt.macm.model.CompetitiveMatch;

public interface CompetitiveMatchRepository extends JpaRepository<CompetitiveMatch, Integer>{
	@Query(value = "select * from competitive_match where type_id = ?1", nativeQuery = true)
	List<CompetitiveMatch> listMatchsByType(int competitiveTypeId);
}
