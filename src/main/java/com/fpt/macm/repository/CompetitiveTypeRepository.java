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
}
