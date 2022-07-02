package com.fpt.macm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fpt.macm.model.ExhibitionPlayer;

@Repository
public interface ExhibitionPlayerRepository extends JpaRepository<ExhibitionPlayer, Integer>{
	
}
