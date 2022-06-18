package com.fpt.macm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fpt.macm.model.StatusSemester;

@Repository
public interface StatusSemesterRepository extends JpaRepository<StatusSemester, Integer> {
	List<StatusSemester> findBySemester(String semester);
}
