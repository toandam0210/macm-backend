package com.fpt.macm.service;

import java.time.LocalDate;
import java.util.List;

import com.fpt.macm.model.dto.EventCreateDto;
import com.fpt.macm.model.dto.RoleEventDto;
import com.fpt.macm.model.entity.Event;
import com.fpt.macm.model.response.ResponseMessage;

public interface EventService {
	ResponseMessage createEvent(String studentId, EventCreateDto eventCreateDto, boolean isOverwritten);
	ResponseMessage updateBeforeEvent(int id, Event event);
	ResponseMessage deleteEvent(String studentId, int eventId);
	ResponseMessage getEventsByName(String name, int pageNo, int pageSize, String sortBy);
	ResponseMessage getEventById(int id);
	ResponseMessage getEventsByDate(LocalDate startDate, LocalDate finishDate, int pageNo, int pageSize);
	ResponseMessage getEventsBySemester(String semester, int month, int pageNo, int pageSize);
	ResponseMessage updateAfterEvent(String studentId, int eventId, double money, boolean isIncurred, boolean isUseClubFund);
	LocalDate getStartDate(int eventId);
	LocalDate getEndDate(int eventId);
	ResponseMessage getEventsBySemesterAndStudentId(String semester, String studentId, int month, int pageNo, int pageSize);
	ResponseMessage getAllEventHasJoinedByStudentId(String studentId, int pageNo, int pageSize);
	ResponseMessage getAllUpcomingEvent(int pageNo, int pageSize);
	ResponseMessage getAllOngoingEvent(int pageNo, int pageSize);
	ResponseMessage getAllClosedEvent(int pageNo, int pageSize);
	ResponseMessage editRoleEvent(int eventId, List<RoleEventDto> rolesEventDto);
}
