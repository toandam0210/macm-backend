package com.fpt.macm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fpt.macm.model.entity.MemberEvent;

@Repository
public interface MemberEventRepository extends JpaRepository<MemberEvent, Integer> {
	List<MemberEvent> findByEventIdAndRegisterStatus(int eventId, String registerStatus);

	List<MemberEvent> findByEventIdOrderByIdAsc(int eventId);
	
	Optional<MemberEvent> findByEventIdAndUserId(int eventId, int userId);
	
	List<MemberEvent> findByUserId(int userId);
	
}
