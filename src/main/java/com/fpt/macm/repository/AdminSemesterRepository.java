package com.fpt.macm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fpt.macm.model.entity.AdminSemester;

@Repository
public interface AdminSemesterRepository extends JpaRepository<AdminSemester, Integer> {
	List<AdminSemester> findBySemester(String semester);
	
	@Query(value = "select * from admin_semester where user_id = ?1 and semester like ?2", nativeQuery = true)
	Optional<AdminSemester> findByUserId(int userId, String semester);
}
