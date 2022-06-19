package com.fpt.macm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fpt.macm.model.ClubFund;

@Repository
public interface ClubFundRepository extends JpaRepository<ClubFund, Integer>{

}
