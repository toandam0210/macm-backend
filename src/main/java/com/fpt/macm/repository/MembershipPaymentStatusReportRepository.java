package com.fpt.macm.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.fpt.macm.model.MembershipPaymentStatusReport;

@Repository
public interface MembershipPaymentStatusReportRepository extends PagingAndSortingRepository<MembershipPaymentStatusReport, Integer>{
	Page<MembershipPaymentStatusReport> findByMembershipInfoId(int membershipInfoId, Pageable pageable);
	
	@Query(value = "SELECT * FROM membership_payment_status_report WHERE created_on BETWEEN ?1 AND ?2", nativeQuery = true)
	List<MembershipPaymentStatusReport> findAllFundChange(LocalDateTime startDate, LocalDateTime endDate);
}
