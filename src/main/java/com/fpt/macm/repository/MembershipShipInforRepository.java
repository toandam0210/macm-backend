package com.fpt.macm.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fpt.macm.model.entity.MembershipInfo;

@Repository
public interface MembershipShipInforRepository extends JpaRepository<MembershipInfo, Integer> {
	Optional<MembershipInfo> findBySemester(String semester);
}
