package com.fpt.macm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fpt.macm.model.entity.ExhibitionType;

@Repository
public interface ExhibitionTypeRepository extends JpaRepository<ExhibitionType, Integer> {
	@Query(value = "select tournament_id from exhibition_type where id = ?1", nativeQuery = true)
	int findTournamentOfType(int exhibitionTypeId);
}
