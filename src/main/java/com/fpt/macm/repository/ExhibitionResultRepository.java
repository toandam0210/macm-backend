package com.fpt.macm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fpt.macm.model.entity.ExhibitionResult;

@Repository
public interface ExhibitionResultRepository extends JpaRepository<ExhibitionResult, Integer>{
	
	@Query(value = "select * from exhibition_result where team_id = ?1", nativeQuery = true)
	Optional<ExhibitionResult> findByTeam(int teamId);
	
	@Query(value = "select * from exhibition_result where area_id = ?1 and date_part('doy', time) = ?2 and date_part('year', time) = ?3 order by time", nativeQuery = true)
	List<ExhibitionResult> listExhibitionResultByAreaOrderTime(int areaId, int dayOfYear, int year);
}
