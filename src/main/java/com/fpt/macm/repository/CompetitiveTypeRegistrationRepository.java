package com.fpt.macm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fpt.macm.model.entity.CompetitiveTypeRegistration;

@Repository
public interface CompetitiveTypeRegistrationRepository extends JpaRepository<CompetitiveTypeRegistration, Integer> {

	Optional<CompetitiveTypeRegistration> findByCompetitiveTypeIdAndTournamentPlayerId(int competitiveTypeId,
			int tournamentPlayerId);
	
	Optional<CompetitiveTypeRegistration> findByTournamentPlayerId(int tournamentPlayerId);
	
	List<CompetitiveTypeRegistration> findByCompetitiveTypeId(int competitiveTypeId);

}
