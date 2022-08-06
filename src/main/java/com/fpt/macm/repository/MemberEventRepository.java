package com.fpt.macm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.fpt.macm.model.entity.MemberEvent;

@Repository
public interface MemberEventRepository extends PagingAndSortingRepository<MemberEvent, Integer> {
	Page<MemberEvent> findByEventIdAndRegisterStatus(int eventId, boolean registerStatus, Pageable pageable);

	List<MemberEvent> findByEventIdOrderByIdAsc(int eventId);
	
	@Query(value = "select * from member_event where event_id = ?1 and register_status = true", nativeQuery = true)
	List<MemberEvent> findMemberEventByEventId(int eventId);
	
	@Query(value = "select * from member_event where event_id = ?1 and user_id = ?2", nativeQuery = true)
	Optional<MemberEvent> findMemberEventByEventAndUser(int eventId, int userId);
	
	List<MemberEvent> findByUserId(int userId);
}
