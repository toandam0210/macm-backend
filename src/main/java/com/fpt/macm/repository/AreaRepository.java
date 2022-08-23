package com.fpt.macm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fpt.macm.model.entity.Area;

@Repository
public interface AreaRepository extends JpaRepository<Area, Integer>{
	@Query(value = "select * from area where is_active = true", nativeQuery = true)
	List<Area> listActiveArea();
}
