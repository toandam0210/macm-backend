package com.fpt.macm.service;

import java.time.LocalDate;

import com.fpt.macm.model.Event;
import com.fpt.macm.model.ResponseMessage;

public interface EventService {
	ResponseMessage createEvent(Event event);
	ResponseMessage updateBeforeEvent(int id, Event event);
	ResponseMessage deleteEvent(int id);
	ResponseMessage getEventsByName(String name, int pageNo, int pageSize, String sortBy);
	ResponseMessage getEventById(int id);
	ResponseMessage getEventsByDate(LocalDate startDate, LocalDate finishDate, int pageNo, int pageSize);
	ResponseMessage getStartDateOfEvent(int eventId);
	ResponseMessage getEventsBySemester(String semester, int month, int pageNo, int pageSize);
	ResponseMessage updateAfterEvent(int id, Event event);
}
