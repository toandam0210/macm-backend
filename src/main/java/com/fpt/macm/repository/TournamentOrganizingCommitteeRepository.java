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
	
//	@Query(value = "Select * from tournament_organizing_committee where tournament_id = ?1 and role_in_tournament = ?2", nativeQuery = true)
//	List<TournamentOrganizingCommittee> findByTournamentIdAndRoleInTournament(int tournamentId, int roleInTournament);
	
	List<TournamentOrganizingCommittee> findByTournamentRoleId(int tournamentRoleId);
	
	List<TournamentOrganizingCommittee> findByTournamentIdAndRegisterStatus(int tournamentId, String registerStatus);
}
