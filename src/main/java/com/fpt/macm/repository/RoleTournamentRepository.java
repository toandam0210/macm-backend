package com.fpt.macm.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fpt.macm.model.entity.RoleTournament;

@Repository
public interface RoleTournamentRepository extends JpaRepository<RoleTournament, Integer>{

	Optional<RoleTournament> findByName(String name);
	
//	List<RoleTournament> findByIsActiveOrderByIdAsc(boolean isActive);
	
}
