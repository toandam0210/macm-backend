package com.fpt.macm.service;

import java.time.LocalDate;
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
import com.fpt.macm.model.EventSchedule;
import com.fpt.macm.model.ResponseMessage;
import com.fpt.macm.model.Semester;
import com.fpt.macm.repository.EventRepository;
import com.fpt.macm.repository.EventScheduleRepository;
import com.fpt.macm.repository.SemesterRepository;

@Service
public class EventServiceImpl implements EventService{

	@Autowired
	EventRepository eventRepository;
	
	@Autowired
	EventScheduleRepository eventScheduleRepository;
	
	@Autowired
	SemesterRepository semesterRepository;
	
	@Autowired
	EventScheduleService eventScheduleService;
	
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
			List<EventSchedule> listSchedule = eventScheduleRepository.findByEventId(id);
			if(listSchedule.size() > 0 && listSchedule.get(0).getDate().compareTo(LocalDate.now()) <= 0) {
				responseMessage.setMessage(Constant.MSG_065);
			}
			else {
				Optional<Event> eventOp = eventRepository.findById(id);
				Event getEvent = eventOp.get();
				getEvent.setName(event.getName());
				getEvent.setDescription(event.getDescription());
				getEvent.setMaxQuantityComitee(event.getMaxQuantityComitee());
				getEvent.setTotalAmount(event.getTotalAmount());
				getEvent.setAmount_per_register(event.getAmount_per_register());
				getEvent.setUpdatedBy("LinhLHN");
				getEvent.setUpdatedOn(LocalDateTime.now());
				eventRepository.save(getEvent);
				responseMessage.setData(Arrays.asList(getEvent));
				responseMessage.setMessage(Constant.MSG_053);
			}
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
			List<EventSchedule> listSchedule = eventScheduleRepository.findByEventId(id);
			if(listSchedule.size() > 0 && listSchedule.get(0).getDate().compareTo(LocalDate.now()) <= 0) {
				responseMessage.setMessage(Constant.MSG_064);
			}
			else {
				Optional<Event> eventOp = eventRepository.findById(id);
				Event event = eventOp.get();
				eventRepository.delete(event);
				responseMessage.setData(Arrays.asList(event));
				responseMessage.setMessage(Constant.MSG_054);
			}
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

	@Override
	public ResponseMessage getEventsByDate(LocalDate startDate, LocalDate finishDate) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			if(finishDate.compareTo(LocalDate.now()) < 0) {
				responseMessage.setMessage(Constant.MSG_072);
			} else {
				List<Event> eventList = new ArrayList<Event>();
				while(startDate.compareTo(finishDate) <= 0) {
					EventSchedule getEventSession = (EventSchedule) eventScheduleService.getEventSessionByDate(startDate).getData().get(0);
					if(getEventSession != null) {
						Event getEvent = getEventSession.getEvent();
						if(!eventList.contains(getEvent)) {
							eventList.add(getEvent);
						}
					}
					startDate.plusDays(1);
				}
				if(eventList.size() > 0) {
					responseMessage.setData(eventList);
					responseMessage.setMessage(Constant.MSG_063);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage getStartDateOfEvent(int eventId) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<EventSchedule> listSchedule = eventScheduleRepository.findByEventId(eventId);
			if(listSchedule.size() > 0) {
				responseMessage.setData(Arrays.asList(listSchedule.get(0).getDate()));
			}
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage getEventsBySemester(int semesterId) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Semester getSemester = semesterRepository.findById(semesterId).get();
			responseMessage.setData(getEventsByDate(getSemester.getStartDate(), getSemester.getEndDate()).getData());
			responseMessage.setMessage(Constant.MSG_083 + getSemester.getName());
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}
	
}
