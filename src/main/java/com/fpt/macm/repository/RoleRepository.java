package com.fpt.macm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fpt.macm.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

}
