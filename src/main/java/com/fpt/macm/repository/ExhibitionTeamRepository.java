package com.fpt.macm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fpt.macm.model.entity.ExhibitionTeam;

@Repository
public interface ExhibitionTeamRepository extends JpaRepository<ExhibitionTeam, Integer> {
	@Query(value = "select exhibition_type_id from exhibition_team where id = ?1", nativeQuery = true)
	int findTypeOfExhibitionTeam(int exhibitionTypeId);
}
