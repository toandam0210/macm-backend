package com.fpt.macm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fpt.macm.model.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
	@Query(value = "select * from role where id > 2", nativeQuery = true)
	List<Role> findRoleForViceHead();
}
