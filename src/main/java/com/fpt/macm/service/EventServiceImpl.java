package com.fpt.macm.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.fpt.macm.model.Constant;
import com.fpt.macm.model.Event;
import com.fpt.macm.model.ResponseMessage;
import com.fpt.macm.repository.EventRepository;

@Service
public class EventServiceImpl implements EventService{

	@Autowired
	EventRepository eventRepository;
	
	@Override
	public ResponseMessage createEvent(Event event) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			event.setCreatedBy("LinhLHN");
			event.setCreatedOn(LocalDateTime.now());
			eventRepository.save(event);
			responseMessage.setData(Arrays.asList(event));
			responseMessage.setMessage(Constant.MSG_052);
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage updateEvent(int id, Event event) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<Event> eventOp = eventRepository.findById(id);
			Event getEvent = eventOp.get();
			getEvent.setName(event.getName());
			getEvent.setDescription(event.getDescription());
			getEvent.setMaxQuantityComitee(event.getMaxQuantityComitee());
			getEvent.setTotalAmount(event.getTotalAmount());
			getEvent.setUpdatedBy("LinhLHN");
			getEvent.setUpdatedOn(LocalDateTime.now());
			eventRepository.save(getEvent);
			responseMessage.setData(Arrays.asList(getEvent));
			responseMessage.setMessage(Constant.MSG_053);
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage deleteEvent(int id) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<Event> eventOp = eventRepository.findById(id);
			Event event = eventOp.get();
			eventRepository.delete(event);
			responseMessage.setData(Arrays.asList(event));
			responseMessage.setMessage(Constant.MSG_054);
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage getEventsByName(String name, int pageNo, int pageSize, String sortBy) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
			Page<Event> pageResponse = eventRepository.findByName(paging, name);
			List<Event> eventList = new ArrayList<Event>();
			if (pageResponse != null && pageResponse.hasContent()) {
				eventList = pageResponse.getContent();
			}
			responseMessage.setData(eventList);
			responseMessage.setPageNo(pageNo);
			responseMessage.setPageSize(pageSize);
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage getEventById(int id) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<Event> eventOp = eventRepository.findById(id);
			Event getEvent = eventOp.get();
			responseMessage.setData(Arrays.asList(getEvent));
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}
	
	

}
