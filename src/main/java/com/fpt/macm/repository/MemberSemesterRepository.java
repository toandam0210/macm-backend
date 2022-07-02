package com.fpt.macm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fpt.macm.model.MemberSemester;

@Repository
public interface MemberSemesterRepository extends JpaRepository<MemberSemester, Integer> {
	
	List<MemberSemester> findBySemester(String semester);
	
	@Query(value = "select * from member_semester where user_id = ?1 and semester like CONCAT('%', ?2, '%')", nativeQuery = true)
	Optional<MemberSemester> findByUserIdAndSemester(int userId, String semester);
}
