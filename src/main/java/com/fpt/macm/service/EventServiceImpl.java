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

import com.fpt.macm.dto.EventDto;
import com.fpt.macm.model.Constant;
import com.fpt.macm.model.Event;
import com.fpt.macm.model.EventSchedule;
import com.fpt.macm.model.MemberEvent;
import com.fpt.macm.model.ResponseMessage;
import com.fpt.macm.model.Semester;
import com.fpt.macm.repository.EventRepository;
import com.fpt.macm.repository.EventScheduleRepository;
import com.fpt.macm.repository.MemberEventRepository;
import com.fpt.macm.repository.SemesterRepository;

@Service
public class EventServiceImpl implements EventService{

	@Autowired
	EventRepository eventRepository;
	
	@Autowired
	EventScheduleRepository eventScheduleRepository;
	
	@Autowired
	MemberEventRepository memberEventRepository;
	
	@Autowired
	SemesterRepository semesterRepository;
	
	@Autowired
	EventScheduleService eventScheduleService;
	
	@Autowired
	SemesterService semesterService;
	
	@Override
	public ResponseMessage createEvent(Event event) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Semester semester = (Semester) semesterService.getCurrentSemester().getData().get(0);
			event.setCreatedBy("LinhLHN");
			event.setCreatedOn(LocalDateTime.now());
			event.setSemester(semester.getName());
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
				responseMessage.setMessage(Constant.MSG_069);
			}
			else {
				Optional<Event> eventOp = eventRepository.findById(id);
				Event getEvent = eventOp.get();
				getEvent.setName(event.getName());
				getEvent.setDescription(event.getDescription());
				getEvent.setMaxQuantityComitee(event.getMaxQuantityComitee());
				getEvent.setTotalAmount(event.getTotalAmount());
				getEvent.setAmount_per_register(event.getAmount_per_register());
				getEvent.setDescription(event.getDescription());
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
				eventScheduleRepository.deleteAll(listSchedule);
				memberEventRepository.deleteAll(memberEventRepository.findByEventId(event.getId()));
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
			Page<Event> pageResponse = eventRepository.findByName(name, paging);
			List<Event> eventList = new ArrayList<Event>();
			if (pageResponse != null && pageResponse.hasContent()) {
				eventList = pageResponse.getContent();
			}
			List<EventDto> eventDtos = new ArrayList<EventDto>();
			for (Event event : eventList) {
				LocalDate startDate = getStartDate(event.getId());
				EventDto eventDto = new EventDto();
				if(startDate != null) {
					LocalDate endDate = getEndDate(event.getId());
					if(LocalDate.now().isBefore(startDate)) {
						eventDto.setStatus("Chưa diễn ra");
					}else if(LocalDate.now().isAfter(endDate)) {
						eventDto.setStatus("Đã kết thúc");
					}else {
						eventDto.setStatus("Đang diễn ra");
					}
				} else {
					eventDto.setStatus("Chưa diễn ra");
				}
				eventDto.setAmountPerMemberRegister(event.getAmount_per_register());
				eventDto.setMaxQuantityComitee(event.getMaxQuantityComitee());
				eventDto.setTotalAmount(event.getTotalAmount());
				eventDto.setStartDate(startDate);
				eventDto.setName(event.getName());
				eventDto.setId(event.getId());
				eventDtos.add(eventDto);
				
			}
			responseMessage.setData(eventDtos);
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
				List<EventDto> eventDtos = new ArrayList<EventDto>();
				List<Event> eventList = new ArrayList<Event>();
				while(startDate.compareTo(finishDate) <= 0) {
					List<EventSchedule> getEventSession = (List<EventSchedule>) eventScheduleService.getEventSessionByDate(startDate).getData();
					if(getEventSession.size() > 0) {
						Event getEvent = getEventSession.get(0).getEvent();
						if(!eventList.contains(getEvent)) {
							eventList.add(getEvent);
						}
					}
					startDate.plusDays(1);
				}
				if(eventList.size() > 0) {
					for (Event event : eventList) {
						LocalDate startDateEvent = (LocalDate) getStartDateOfEvent(event.getId()).getData().get(0);
						LocalDate endDate = getEndDate(event.getId());
						EventDto eventDto = new EventDto();
						if(LocalDate.now().isBefore(startDateEvent)) {
							eventDto.setStatus("Chưa diễn ra");
						}else if(LocalDate.now().isAfter(endDate)) {
							eventDto.setStatus("Đã kết thúc");
						}else {
							eventDto.setStatus("Đang diễn ra");
						}
						eventDto.setAmountPerMemberRegister(event.getAmount_per_register());
						eventDto.setMaxQuantityComitee(event.getMaxQuantityComitee());
						eventDto.setTotalAmount(event.getTotalAmount());
						eventDto.setStartDate(startDate);
						eventDto.setName(event.getName());
						eventDto.setId(event.getId());
						eventDtos.add(eventDto);
						
					}
					responseMessage.setData(eventDtos);
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
	
	public LocalDate getEndDate(int eventId) {
		List<EventSchedule> listSchedule = eventScheduleRepository.findByEventId(eventId);
		if(listSchedule.size() > 0) {
			return listSchedule.get(listSchedule.size()-1).getDate();
		}
		return null;
	}

	@Override
	public ResponseMessage getEventsBySemester(String semester) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			if(semester == "") {
				semester = semesterRepository.findTop3Semester().get(0).getName();
			}
			List<Event> events = eventRepository.findBySemester(semester);
			List<EventDto> eventDtos = new ArrayList<EventDto>();
			for (Event event : events) {
				LocalDate startDate = getStartDate(event.getId());
				EventDto eventDto = new EventDto();
				if(startDate != null) {
					LocalDate endDate = getEndDate(event.getId());
					if(LocalDate.now().isBefore(startDate)) {
						eventDto.setStatus("Chưa diễn ra");
					}else if(LocalDate.now().isAfter(endDate)) {
						eventDto.setStatus("Đã kết thúc");
					}else {
						eventDto.setStatus("Đang diễn ra");
					}
				}
				else {
					eventDto.setStatus("Chưa diễn ra");
				}
				eventDto.setAmountPerMemberRegister(event.getAmount_per_register());
				eventDto.setMaxQuantityComitee(event.getMaxQuantityComitee());
				eventDto.setTotalAmount(event.getTotalAmount());
				eventDto.setStartDate(startDate);
				eventDto.setName(event.getName());
				eventDto.setId(event.getId());
				eventDtos.add(eventDto);
				
			}
			responseMessage.setData(eventDtos);
			responseMessage.setMessage("Lấy danh sách event thành công" + semester);
			responseMessage.setTotalResult(eventDtos.size());

		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}
	
	public LocalDate getStartDate(int eventId) {
		// TODO Auto-generated method stub
		try {
			List<EventSchedule> listSchedule = eventScheduleRepository.findByEventId(eventId);
			if(listSchedule.size() > 0) {
				return listSchedule.get(0).getDate();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
}
