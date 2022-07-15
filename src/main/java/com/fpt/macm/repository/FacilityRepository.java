package com.fpt.macm.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.fpt.macm.model.entity.Facility;

public interface FacilityRepository extends PagingAndSortingRepository<Facility, Integer>{

	@Query(value = "SELECT * FROM facility WHERE \"name\" = ?1 AND facility_category_id = ?2", nativeQuery = true)
	Optional<Facility> findFacilityByFacilityNameAndFacilityCategoryId(String facilityName, int facilityCategoryId);
	
//	@Query(value = "SELECT * FROM facility WHERE facility_category_id = ?1", nativeQuery = true)
	Page<Facility> findByFacilityCategoryId(int facilityCategoryId, Pageable pageable);
}
