package com.fpt.macm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fpt.macm.model.TournamentPlayer;

@Repository
public interface TournamentPlayerRepository extends JpaRepository<TournamentPlayer, Integer> {

}
