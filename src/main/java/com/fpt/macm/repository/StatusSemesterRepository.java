package com.fpt.macm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fpt.macm.model.MemberSemester;

@Repository
public interface StatusSemesterRepository extends JpaRepository<MemberSemester, Integer> {
	List<MemberSemester> findBySemester(String semester);
}
