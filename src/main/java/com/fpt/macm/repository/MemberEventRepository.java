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
	Page<MemberEvent> findAllUserEventByEventId(int eventId, Pageable pageable);
	
	@Query(value = "SELECT m.id, attendance_status, created_by, created_on, payment_status, updated_by, updated_on, event_id, role_in_event, user_id FROM member_event m join role_event e on m.role_in_event = e.id where m.event_id = ?1 and e.\"name\" != 'ROLE_Member' and e.\"name\" != 'ROLE_Collaborator'", nativeQuery = true)
	Page<MemberEvent> findAllOrganizingCommitteeEventByEventId(int eventId, Pageable pageable);
	
	@Query(value = "SELECT m.id, attendance_status, created_by, created_on, payment_status, updated_by, updated_on, event_id, role_in_event, user_id FROM member_event m join role_event e on m.role_in_event = e.id where m.event_id = ?1 and e.\"name\" = 'ROLE_Member' or e.\"name\" = 'ROLE_Collaborator'", nativeQuery = true)
	Page<MemberEvent> findAllMemberEventByEventId(int eventId, Pageable pageable);
	
	@Query(value = "SELECT * FROM member_event WHERE event_id = ?1 AND attendance_status = false", nativeQuery = true)
	Page<MemberEvent> findAllUserCancelJoinEventByEventId(int eventId, Pageable pageable);
}
