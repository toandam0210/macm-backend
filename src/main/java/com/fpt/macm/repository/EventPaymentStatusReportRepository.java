package com.fpt.macm.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fpt.macm.model.entity.EventPaymentStatusReport;

@Repository
public interface EventPaymentStatusReportRepository extends JpaRepository<EventPaymentStatusReport, Integer>{

	List<EventPaymentStatusReport> findByEventId(int eventId);
	
	@Query(value = "SELECT * FROM event_payment_status_report WHERE created_on BETWEEN ?1 AND ?2", nativeQuery = true)
	List<EventPaymentStatusReport> findAllFundChange(LocalDateTime startDate, LocalDateTime endDate);
}
