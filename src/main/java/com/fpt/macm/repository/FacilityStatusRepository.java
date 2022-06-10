package com.fpt.macm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fpt.macm.model.FacilityStatus;

@Repository
public interface FacilityStatusRepository extends JpaRepository<FacilityStatus, Integer>{

	@Query(value = "SELECT * FROM facility_status WHERE facility_id = ?1", nativeQuery = true)
	List<FacilityStatus> findByFacilityId(int facilityId);
	
	@Query(value = "SELECT * FROM facility_status WHERE facility_id = ?1 AND status = ?2", nativeQuery = true)
	Optional<FacilityStatus> findByFacilityIdAndStatus(int facilityId, boolean status);
}
