package com.fpt.macm.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.fpt.macm.model.User;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Integer>{
	Optional<User> findByStudentId(String studentId);
	
	Page<User> findByIsAdmin(boolean isAdmin, Pageable pageable);
}
