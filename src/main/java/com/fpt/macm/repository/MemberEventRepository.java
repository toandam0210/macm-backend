package com.fpt.macm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fpt.macm.constant.Constant;
import com.fpt.macm.model.entity.MemberEvent;

@Repository
public interface MemberEventRepository extends JpaRepository<MemberEvent, Integer> {
	List<MemberEvent> findByEventIdAndRegisterStatus(int eventId, String registerStatus);

	List<MemberEvent> findByEventIdOrderByIdAsc(int eventId);
	
	@Query(value = "select * from member_event where event_id = ?1 and register_status = " + Constant.REQUEST_STATUS_APPROVED, nativeQuery = true)
	List<MemberEvent> findMemberEventByEventId(int eventId);
	
	@Query(value = "select * from member_event where event_id = ?1 and user_id = ?2", nativeQuery = true)
	Optional<MemberEvent> findMemberEventByEventAndUser(int eventId, int userId);
	
	List<MemberEvent> findByUserId(int userId);
	
	@Query(value = "select * from member_event where event_id = ?1 and role_in_event = ?2 and register_status = " + Constant.REQUEST_STATUS_APPROVED, nativeQuery = true)
	List<MemberEvent> findOrganizingCommitteeByEventId(int eventId, int roleEventId);
}
