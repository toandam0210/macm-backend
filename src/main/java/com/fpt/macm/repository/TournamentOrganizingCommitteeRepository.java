package com.fpt.macm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fpt.macm.model.TournamentOrganizingCommittee;

@Repository
public interface TournamentOrganizingCommitteeRepository extends JpaRepository<TournamentOrganizingCommittee, Integer>{

	List<TournamentOrganizingCommittee> findByTournamentId(int tournamentId);
	
}
