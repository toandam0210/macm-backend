package com.fpt.macm.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.fpt.macm.model.MemberEvent;

@Repository
public interface MemberEventRepository extends PagingAndSortingRepository<MemberEvent, Integer>{

	@Query(value = "SELECT * FROM member_event WHERE event_id = ?1", nativeQuery = true)
	Page<MemberEvent> findAllMemberEventByEventId(int eventId, Pageable pageable);
	
}
