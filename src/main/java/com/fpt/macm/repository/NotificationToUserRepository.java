package com.fpt.macm.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.fpt.macm.model.entity.NotificationToUser;

@Repository
public interface NotificationToUserRepository extends PagingAndSortingRepository<NotificationToUser, Integer>{

	Page<NotificationToUser> findByUserId(int userId, Pageable pageable);
	
	List<NotificationToUser> findAllByUserId(int userId);
	
}
