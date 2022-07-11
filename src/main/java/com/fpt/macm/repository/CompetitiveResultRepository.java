package com.fpt.macm.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fpt.macm.model.CompetitiveResult;

public interface CompetitiveResultRepository extends JpaRepository<CompetitiveResult, Integer>{
	@Query(value = "select * from competitive_result where match_id = ?1", nativeQuery = true)
	Optional<CompetitiveResult> findByMatchId(int competitiveMatchId);
}
