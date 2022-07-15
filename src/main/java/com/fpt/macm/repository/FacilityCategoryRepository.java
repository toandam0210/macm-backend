package com.fpt.macm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fpt.macm.model.entity.FacilityCategory;

@Repository
public interface FacilityCategoryRepository extends JpaRepository<FacilityCategory, Integer> {
	@Query(value = "select * from facility_category where status = true", nativeQuery = true)
	List<FacilityCategory> findAllFacilityCategory();
}
