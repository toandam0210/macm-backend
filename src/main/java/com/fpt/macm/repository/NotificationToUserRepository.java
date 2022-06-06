package com.fpt.macm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fpt.macm.model.NotificationToUser;

@Repository
public interface NotificationToUserRepository extends JpaRepository<NotificationToUser, Integer>{

}
