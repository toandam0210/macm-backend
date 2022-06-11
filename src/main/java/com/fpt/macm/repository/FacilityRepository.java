package com.fpt.macm.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.fpt.macm.model.Facility;

public interface FacilityRepository extends PagingAndSortingRepository<Facility, Integer>{

	@Query(value = "SELECT * FROM facility WHERE \"name\" = ?1 AND facility_category_id = ?2", nativeQuery = true)
	Optional<Facility> findFacilityByFacilityNameAndFacilityCategoryId(String facilityName, int facilityCategoryId);
	
}