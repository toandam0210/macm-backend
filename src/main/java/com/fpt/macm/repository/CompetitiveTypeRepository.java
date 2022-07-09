package com.fpt.macm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fpt.macm.model.CompetitiveType;

@Repository
public interface CompetitiveTypeRepository extends JpaRepository<CompetitiveType, Integer> {
	
	List<CompetitiveType> findByGender(boolean gender);
}
