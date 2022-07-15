package com.fpt.macm.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.fpt.macm.model.entity.FacilityRequest;

@Repository
public interface FacilityRequestRepository extends PagingAndSortingRepository<FacilityRequest, Integer>{

}
