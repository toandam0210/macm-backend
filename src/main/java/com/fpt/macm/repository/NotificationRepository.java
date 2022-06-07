package com.fpt.macm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fpt.macm.model.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer>{

}
