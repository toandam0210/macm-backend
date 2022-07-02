package com.fpt.macm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fpt.macm.model.ExhibitionType;

@Repository
public interface ExhibitionTypeRepository extends JpaRepository<ExhibitionType, Integer> {

}
