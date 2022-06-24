package com.fpt.macm.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fpt.macm.model.Tournament;

@Repository
public interface TournamentRepository extends JpaRepository<Tournament, Integer>{
	@Query(value = "select * from tournament where name like ?1", nativeQuery = true)
	Optional<Tournament> findByExactName(String name);
}
