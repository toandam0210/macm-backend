package com.fpt.macm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.fpt.macm.model.entity.NotificationToUser;

@Repository
public interface NotificationToUserRepository extends PagingAndSortingRepository<NotificationToUser, Integer> {

	Page<NotificationToUser> findByUserId(int userId, Pageable pageable);

	List<NotificationToUser> findAllByUserId(int userId);

	Optional<NotificationToUser> findByUserIdAndNotificationId(int userId, int notificationId);
	
	@Query(value = "Select * from notification_to_user where is_read = false and user_id = ?1", nativeQuery = true)
	List<NotificationToUser> findAllUnreadNotificationByUser(int userId);
	
	@Query(value = "Select * from notification_to_user where is_read = false and user_id = ?1", nativeQuery = true)
	Page<NotificationToUser> findUnreadNotificationByUserId(int userId, Pageable pageable);
}
