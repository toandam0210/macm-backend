package com.fpt.macm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fpt.macm.model.entity.MembershipStatus;

@Repository
public interface MembershipStatusRepository extends JpaRepository<MembershipStatus, Integer> {
	List<MembershipStatus> findByMembershipInfoId(int membershipInfoId); 
	
	@Query(value = "Select * From membership_status where membership_info_id = ?1 and user_id = ?2", nativeQuery = true)
	Optional<MembershipStatus> findByMemberShipInfoIdAndUserId(int membershipInfoId, int userId);
}
