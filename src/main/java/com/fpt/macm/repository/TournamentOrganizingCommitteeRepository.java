package com.fpt.macm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fpt.macm.model.entity.TournamentOrganizingCommittee;

@Repository
public interface TournamentOrganizingCommitteeRepository extends JpaRepository<TournamentOrganizingCommittee, Integer>{

	List<TournamentOrganizingCommittee> findByTournamentId(int tournamentId);
	
	List<TournamentOrganizingCommittee> findByUserId(int userId);
	
	Optional<TournamentOrganizingCommittee> findByTournamentIdAndUserId(int tournamentId, int userId);
}
