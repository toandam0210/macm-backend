package com.fpt.macm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fpt.macm.model.FacilityCategory;

@Repository
public interface FacilityCategoryRepository extends JpaRepository<FacilityCategory, Integer> {
	
}
