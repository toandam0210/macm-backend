package com.fpt.macm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.fpt.macm.model.Constant;
import com.fpt.macm.model.MemberEvent;

@Repository
public interface MemberEventRepository extends PagingAndSortingRepository<MemberEvent, Integer> {

	@Query(value = "SELECT * FROM member_event WHERE event_id = ?1 AND attendance_status = false", nativeQuery = true)
	Page<MemberEvent> findAllMemberCancelJoinEventByEventId(int eventId, Pageable pageable);

	@Query(value = "select * from member_event where event_id = ?1", nativeQuery = true)
	List<MemberEvent> findByEventId(int eventId);

	@Query(value = "select * from member_event where event_id = ?1 and attendance_status = true", nativeQuery = true)
	Page<MemberEvent> findAllMemberEventByEventId(int eventId, Pageable pageable);

	@Query(value = "SELECT m.id, attendance_status, created_by, created_on, payment_status, updated_by, updated_on, event_id, role_in_event, user_id FROM member_event m join role_event e on m.role_in_event = e.id where m.event_id = ?1 and e.\"name\" = '"
			+ Constant.ROLE_EVENT_MEMBER + "' and attendance_status = true", nativeQuery = true)
	Page<MemberEvent> findAllMemberJoinEvent(int eventId, Pageable pageable);

	@Query(value = "SELECT m.id, attendance_status, created_by, created_on, payment_status, updated_by, updated_on, event_id, role_in_event, user_id FROM member_event m join role_event e on m.role_in_event = e.id where m.event_id = ?1 and e.\"name\" != '"
			+ Constant.ROLE_EVENT_MEMBER + "' and attendance_status = true", nativeQuery = true)
	Page<MemberEvent> findAllEventMemberOrganizingCommittee(int eventId, Pageable pageable);
	
	@Query(value = "select * from member_event where event_id = ?1 and attendance_status = true", nativeQuery = true)
	List<MemberEvent> findMemberEventByEventId(int eventId);
	
	@Query(value = "select * from member_event where event_id = ?1 and attendance_status = true", nativeQuery = true)
	List<MemberEvent> findAllMemberEventByEventId(int eventId);
	
	@Query(value = "select * from member_event where event_id = ?1 and user_id = ?2", nativeQuery = true)
	Optional<MemberEvent> findMemberEventByEventAndUser(int eventId, int userId);
}
