package com.fpt.macm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fpt.macm.model.entity.MembershipStatus;

@Repository
public interface MembershipStatusRepository extends JpaRepository<MembershipStatus, Integer> {
	List<MembershipStatus> findByMembershipInfoId(int membershipInfoId); 
}
