package com.fpt.macm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fpt.macm.model.entity.Tournament;
import com.fpt.macm.model.entity.TournamentPlayer;

@Repository
public interface TournamentRepository extends JpaRepository<Tournament, Integer>{
	@Query(value = "select * from tournament where name like ?1", nativeQuery = true)
	Optional<Tournament> findByExactName(String name);
	
	@Query(value = "select * from tournament where semester like ?1", nativeQuery = true)
	List<Tournament> findBySemester(String semester);
	
	Optional<Tournament> findByTournamentPlayers(TournamentPlayer tournamentPlayer);
}
