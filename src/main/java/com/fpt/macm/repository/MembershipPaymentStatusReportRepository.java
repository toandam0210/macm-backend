package com.fpt.macm.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.fpt.macm.model.MembershipPaymentStatusReport;

@Repository
public interface MembershipPaymentStatusReportRepository extends PagingAndSortingRepository<MembershipPaymentStatusReport, Integer>{
	Page<MembershipPaymentStatusReport> findByMembershipInfoId(int membershipInfoId, Pageable pageable);
}
