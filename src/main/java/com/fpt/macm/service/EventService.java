package com.fpt.macm.service;

import com.fpt.macm.model.Event;
import com.fpt.macm.model.ResponseMessage;

public interface EventService {
	ResponseMessage createEvent(Event event);
	ResponseMessage updateEvent(int id, Event event);
	ResponseMessage deleteEvent(int id);
	ResponseMessage getEventsByName(String name, int pageNo, int pageSize, String sortBy);
	ResponseMessage getEventById(int id);
}
