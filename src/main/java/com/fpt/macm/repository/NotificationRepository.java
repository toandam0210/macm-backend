package com.fpt.macm.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.fpt.macm.model.Notification;

@Repository
public interface NotificationRepository extends PagingAndSortingRepository<Notification, Integer>{

}
