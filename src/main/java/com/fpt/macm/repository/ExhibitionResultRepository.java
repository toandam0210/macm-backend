package com.fpt.macm.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fpt.macm.model.ExhibitionResult;

@Repository
public interface ExhibitionResultRepository extends JpaRepository<ExhibitionResult, Integer>{
	
	@Query(value = "select * from exhibition_result where team_id = ?1", nativeQuery = true)
	Optional<ExhibitionResult> findByTeam(int teamId);
}
