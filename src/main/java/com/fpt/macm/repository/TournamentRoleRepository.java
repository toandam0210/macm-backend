package com.fpt.macm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fpt.macm.model.entity.TournamentRole;

@Repository
public interface TournamentRoleRepository extends JpaRepository<TournamentRole, Integer>{

	List<TournamentRole> findByTournamentId(int tournamentId);
	
	Optional<TournamentRole> findByRoleEventIdAndTournamentId(int roleEventId, int tournamentId);
	
}
