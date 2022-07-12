package com.fpt.macm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fpt.macm.model.Area;

@Repository
public interface AreaRepository extends JpaRepository<Area, Integer>{

}
