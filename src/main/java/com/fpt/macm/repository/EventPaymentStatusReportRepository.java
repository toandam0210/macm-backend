package com.fpt.macm.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.fpt.macm.model.EventPaymentStatusReport;

@Repository
public interface EventPaymentStatusReportRepository extends PagingAndSortingRepository<EventPaymentStatusReport, Integer>{

	Page<EventPaymentStatusReport> findByEventId(int eventId, Pageable pageable);
	
	@Query(value = "SELECT * FROM event_payment_status_report WHERE created_on BETWEEN ?1 AND ?2", nativeQuery = true)
	List<EventPaymentStatusReport> findAllFundChange(LocalDateTime startDate, LocalDateTime endDate);
}
