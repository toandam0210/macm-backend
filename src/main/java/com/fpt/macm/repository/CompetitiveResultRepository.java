package com.fpt.macm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fpt.macm.model.entity.CompetitiveResult;

@Repository
public interface CompetitiveResultRepository extends JpaRepository<CompetitiveResult, Integer>{
	@Query(value = "select * from competitive_result where match_id = ?1", nativeQuery = true)
	Optional<CompetitiveResult> findByMatchId(int competitiveMatchId);
	
	@Query(value = "select * from competitive_result where area_id = ?1 order by time", nativeQuery = true)
	List<CompetitiveResult> listResultByAreaOrderTime(int areaId);
}
