package com.fpt.macm.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpt.macm.constant.Constant;
import com.fpt.macm.model.dto.EventDto;
import com.fpt.macm.model.dto.UserEventSemesterDto;
import com.fpt.macm.model.entity.ClubFund;
import com.fpt.macm.model.entity.CommonSchedule;
import com.fpt.macm.model.entity.Event;
import com.fpt.macm.model.entity.EventSchedule;
import com.fpt.macm.model.entity.MemberEvent;
import com.fpt.macm.model.entity.Semester;
import com.fpt.macm.model.entity.User;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.repository.ClubFundRepository;
import com.fpt.macm.repository.CommonScheduleRepository;
import com.fpt.macm.repository.EventRepository;
import com.fpt.macm.repository.EventScheduleRepository;
import com.fpt.macm.repository.MemberEventRepository;
import com.fpt.macm.repository.SemesterRepository;
import com.fpt.macm.repository.UserRepository;

@Service
public class EventServiceImpl implements EventService {

	@Autowired
	EventRepository eventRepository;

	@Autowired
	EventScheduleRepository eventScheduleRepository;

	@Autowired
	CommonScheduleRepository commonScheduleRepository;

	@Autowired
	MemberEventRepository memberEventRepository;

	@Autowired
	SemesterRepository semesterRepository;

	@Autowired
	ClubFundRepository clubFundRepository;

	@Autowired
	EventScheduleService eventScheduleService;

	@Autowired
	CommonScheduleService commonScheduleService;

	@Autowired
	SemesterService semesterService;

	@Autowired
	MemberEventService memberEventService;

	@Autowired
	UserRepository userRepository;

	@Override
	public ResponseMessage createEvent(Event event) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Semester semester = (Semester) semesterService.getCurrentSemester().getData().get(0);
			event.setAmountPerRegisterActual(0);
			event.setTotalAmountActual(0);
			event.setCreatedBy("LinhLHN");
			event.setCreatedOn(LocalDateTime.now());
			event.setSemester(semester.getName());
			eventRepository.save(event);
			// trừ tiền từ clb
			List<ClubFund> clubFunds = clubFundRepository.findAll();
			ClubFund clubFund = clubFunds.get(0);
			clubFund.setFundAmount(clubFund.getFundAmount() - event.getAmountFromClub());
			clubFundRepository.save(clubFund);
			responseMessage.setData(Arrays.asList(event));
			responseMessage.setMessage(Constant.MSG_052);
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage updateBeforeEvent(int id, Event event) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<EventSchedule> listSchedule = eventScheduleRepository.findByEventId(id);
			if (listSchedule.size() > 0 && listSchedule.get(0).getDate().compareTo(LocalDate.now()) <= 0) {
				responseMessage.setMessage(Constant.MSG_127);
			} else {
				Optional<Event> eventOp = eventRepository.findById(id);
				Event getEvent = eventOp.get();
				getEvent.setName(event.getName());
				getEvent.setDescription(event.getDescription());
				getEvent.setMaxQuantityComitee(event.getMaxQuantityComitee());
				getEvent.setRegistrationMemberDeadline(event.getRegistrationMemberDeadline());
				getEvent.setRegistrationOrganizingCommitteeDeadline(event.getRegistrationOrganizingCommitteeDeadline());
				getEvent.setUpdatedBy("LinhLHN");
				getEvent.setUpdatedOn(LocalDateTime.now());
				List<EventSchedule> getEventSchedules = eventScheduleRepository.findByEventId(id);
				for (EventSchedule eventSchedule : getEventSchedules) {
					CommonSchedule getCommonSchedule = commonScheduleService
							.getCommonSessionByDate(eventSchedule.getDate());
					getCommonSchedule.setTitle(event.getName());
					getCommonSchedule.setUpdatedOn(LocalDateTime.now());
					commonScheduleRepository.save(getCommonSchedule);
				}
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
			if (listSchedule.size() > 0 && listSchedule.get(0).getDate().compareTo(LocalDate.now()) <= 0) {
				responseMessage.setMessage(Constant.MSG_064);
			} else {
				for (EventSchedule eventSchedule : listSchedule) {
					CommonSchedule getCommonSession = commonScheduleService
							.getCommonSessionByDate(eventSchedule.getDate());
					commonScheduleRepository.delete(getCommonSession);
				}
				Optional<Event> eventOp = eventRepository.findById(id);
				Event event = eventOp.get();
				eventScheduleRepository.deleteAll(listSchedule);
				memberEventRepository.deleteAll(memberEventRepository.findByEventIdOrderByIdAsc(event.getId()));
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
			List<Event> eventList = eventRepository.findByName(name);
			List<EventDto> eventDtos = new ArrayList<EventDto>();
			for (Event event : eventList) {
				LocalDate startDate = getStartDate(event.getId());
				EventDto eventDto = new EventDto();
				if (startDate != null) {
					LocalDate endDate = getEndDate(event.getId());
					if (LocalDate.now().isBefore(startDate)) {
						eventDto.setStatus("Chưa diễn ra");
						eventDto.setAmountPerMemberRegister(event.getAmountPerRegisterEstimated());
						eventDto.setTotalAmount(event.getTotalAmountEstimated());
					} else if (LocalDate.now().isAfter(endDate)) {
						eventDto.setStatus("Đã kết thúc");
						eventDto.setAmountPerMemberRegister(event.getAmountPerRegisterActual());
						eventDto.setTotalAmount(event.getTotalAmountActual());
					} else {
						eventDto.setStatus("Đang diễn ra");
						eventDto.setAmountPerMemberRegister(event.getAmountPerRegisterEstimated());
						eventDto.setTotalAmount(event.getTotalAmountEstimated());
					}
				} else {
					eventDto.setStatus("Chưa diễn ra");
				}
				eventDto.setMaxQuantityComitee(event.getMaxQuantityComitee());
				eventDto.setAmountFromClub(event.getAmountFromClub());
				eventDto.setStartDate(startDate);
				eventDto.setName(event.getName());
				eventDto.setId(event.getId());
				eventDto.setDescription(event.getDescription());
				eventDto.setRegistrationMemberDeadline(event.getRegistrationMemberDeadline());
				eventDto.setRegistrationOrganizingCommitteeDeadline(event.getRegistrationOrganizingCommitteeDeadline());
				eventDtos.add(eventDto);
			}
			switch (sortBy) {
			case "id":
				Collections.sort(eventDtos, new Comparator<EventDto>() {
					@Override
					public int compare(EventDto o1, EventDto o2) {
						// TODO Auto-generated method stub
						return o2.getId() - o1.getId();
					}
				});
				break;
			case "name":
				Collections.sort(eventDtos, new Comparator<EventDto>() {
					@Override
					public int compare(EventDto o1, EventDto o2) {
						// TODO Auto-generated method stub
						return o1.getName().compareTo(o2.getName());
					}
				});
				break;
			default:
				Collections.sort(eventDtos, new Comparator<EventDto>() {
					@Override
					public int compare(EventDto o1, EventDto o2) {
						// TODO Auto-generated method stub
						return o2.getStartDate().compareTo(o1.getStartDate());
					}
				});
				break;
			}
			List<EventDto> listEventPaging = pageableEvent(eventDtos, pageNo, pageSize);
			responseMessage.setData(listEventPaging);
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
	public ResponseMessage getEventsByDate(LocalDate startDate, LocalDate finishDate, int pageNo, int pageSize) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			if (finishDate.compareTo(startDate) < 0) {
				responseMessage.setMessage(Constant.MSG_072);
			} else {
				List<EventDto> eventDtos = new ArrayList<EventDto>();
				List<Event> eventList = new ArrayList<Event>();
				while (startDate.compareTo(finishDate) <= 0) {
					EventSchedule getEventSession = eventScheduleService.getEventScheduleByDate(startDate);
					if (getEventSession != null) {
						Event getEvent = getEventSession.getEvent();
						if (!eventList.contains(getEvent)) {
							eventList.add(getEvent);
						}
					}
					startDate = startDate.plusDays(1);
				}
				if (eventList.size() > 0) {
					for (Event event : eventList) {
						LocalDate startDateEvent = (LocalDate) getStartDateOfEvent(event.getId()).getData().get(0);
						LocalDate endDate = getEndDate(event.getId());
						EventDto eventDto = new EventDto();
						if (LocalDate.now().isBefore(startDateEvent)) {
							eventDto.setStatus("Chưa diễn ra");
							eventDto.setAmountPerMemberRegister(event.getAmountPerRegisterEstimated());
							eventDto.setTotalAmount(event.getTotalAmountEstimated());
						} else if (LocalDate.now().isAfter(endDate)) {
							eventDto.setStatus("Đã kết thúc");
							eventDto.setAmountPerMemberRegister(event.getAmountPerRegisterActual());
							eventDto.setTotalAmount(event.getTotalAmountActual());
						} else {
							eventDto.setStatus("Đang diễn ra");
							eventDto.setAmountPerMemberRegister(event.getAmountPerRegisterEstimated());
							eventDto.setTotalAmount(event.getTotalAmountEstimated());
						}
						eventDto.setMaxQuantityComitee(event.getMaxQuantityComitee());
						eventDto.setAmountFromClub(event.getAmountFromClub());
						eventDto.setStartDate(startDate);
						eventDto.setName(event.getName());
						eventDto.setId(event.getId());
						eventDto.setDescription(event.getDescription());
						eventDto.setRegistrationMemberDeadline(event.getRegistrationMemberDeadline());
						eventDto.setRegistrationOrganizingCommitteeDeadline(
								event.getRegistrationOrganizingCommitteeDeadline());
						eventDtos.add(eventDto);
					}
					Collections.sort(eventDtos);
					List<EventDto> getEventPageable = pageableEvent(eventDtos, pageNo, pageSize);
					responseMessage.setData(getEventPageable);
					responseMessage.setMessage(
							Constant.MSG_063 + " từ ngày " + startDate.getDayOfMonth() + "/" + startDate.getMonthValue()
									+ "/" + startDate.getYear() + " đến ngày " + finishDate.getDayOfMonth() + "/"
									+ finishDate.getMonthValue() + "/" + finishDate.getYear());
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
			if (listSchedule.size() > 0) {
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
		if (listSchedule.size() > 0) {
			return listSchedule.get(listSchedule.size() - 1).getDate();
		}
		return null;
	}

	@Override
	public ResponseMessage getEventsBySemester(String semester, int month, int pageNo, int pageSize) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			if (semester == "") {
				semester = semesterRepository.findTop3Semester().get(0).getName();
			}
			List<Event> events = eventRepository.findBySemester(semester);
			List<EventDto> eventDtos = new ArrayList<EventDto>();
			for (Event event : events) {
				LocalDate startDate = getStartDate(event.getId());
				if (startDate != null) {
					if (month == 0 || (month != 0 && getStartDate(event.getId()).getMonthValue() == month)) {
						EventDto eventDto = new EventDto();
						LocalDate endDate = getEndDate(event.getId());
						if (LocalDate.now().isBefore(startDate)) {
							eventDto.setStatus("Chưa diễn ra");
							eventDto.setAmountPerMemberRegister(event.getAmountPerRegisterEstimated());
							eventDto.setTotalAmount(event.getTotalAmountEstimated());
						} else if (LocalDate.now().isAfter(endDate)) {
							eventDto.setStatus("Đã kết thúc");
							eventDto.setAmountPerMemberRegister(event.getAmountPerRegisterActual());
							eventDto.setTotalAmount(event.getTotalAmountActual());
						} else {
							eventDto.setStatus("Đang diễn ra");
							eventDto.setAmountPerMemberRegister(event.getAmountPerRegisterEstimated());
							eventDto.setTotalAmount(event.getTotalAmountEstimated());
						}
						eventDto.setMaxQuantityComitee(event.getMaxQuantityComitee());
						eventDto.setAmountFromClub(event.getAmountFromClub());
						eventDto.setStartDate(startDate);
						eventDto.setName(event.getName());
						eventDto.setId(event.getId());
						eventDto.setDescription(event.getDescription());
						eventDto.setRegistrationMemberDeadline(event.getRegistrationMemberDeadline());
						eventDto.setRegistrationOrganizingCommitteeDeadline(
								event.getRegistrationOrganizingCommitteeDeadline());
						eventDtos.add(eventDto);
					}
				}
			}
			Collections.sort(eventDtos);
			List<EventDto> getEventPageable = pageableEvent(eventDtos, pageNo, pageSize);
			responseMessage.setData(getEventPageable);
			responseMessage.setMessage(Constant.MSG_063 + " tháng " + month + " kỳ " + semester);
			responseMessage.setTotalResult(eventDtos.size());
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public LocalDate getStartDate(int eventId) {
		// TODO Auto-generated method stub
		try {
			List<EventSchedule> listSchedule = eventScheduleRepository.findByEventId(eventId);
			if (listSchedule.size() > 0) {
				return listSchedule.get(0).getDate();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	@Override
	public ResponseMessage updateAfterEvent(int eventId, double money, boolean isIncurred, boolean isUseClubFund) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<EventSchedule> listSchedule = eventScheduleRepository.findByEventId(eventId);
			if (listSchedule.size() > 0
					&& listSchedule.get(listSchedule.size() - 1).getDate().compareTo(LocalDate.now()) > 0) {
				responseMessage.setMessage(Constant.MSG_128);
			} else {
				Optional<Event> eventOp = eventRepository.findById(eventId);
				Event getEvent = eventOp.get();
				if (isIncurred) {
					int countMemberEvent = memberEventRepository.findMemberEventByEventId(eventId).size();
					double totalProceedsActual = countMemberEvent * getEvent.getAmountPerRegisterEstimated();
					double totalAmountActual = totalProceedsActual + money + getEvent.getAmountFromClub();
					getEvent.setTotalAmountActual(totalAmountActual);
					if (isUseClubFund) {
						getEvent.setAmountFromClub(getEvent.getAmountFromClub() + money);
						getEvent.setAmountPerRegisterActual(getEvent.getAmountPerRegisterEstimated());
						// trừ tiền từ clb
						List<ClubFund> clubFunds = clubFundRepository.findAll();
						ClubFund clubFund = clubFunds.get(0);
						clubFund.setFundAmount(clubFund.getFundAmount() - money);
						clubFundRepository.save(clubFund);
					} else {
						double amountPerMore = money / countMemberEvent;
						getEvent.setAmountPerRegisterActual(getEvent.getAmountPerRegisterEstimated() + amountPerMore);
					}
				} else {
					// cộng tiền vào clb
					List<ClubFund> clubFunds = clubFundRepository.findAll();
					ClubFund clubFund = clubFunds.get(0);
					clubFund.setFundAmount(clubFund.getFundAmount() + money);
					clubFundRepository.save(clubFund);
					getEvent.setAmountFromClub(
							getEvent.getAmountFromClub() - money >= 0 ? getEvent.getAmountFromClub() - money : 0);
					getEvent.setAmountPerRegisterActual(getEvent.getAmountPerRegisterEstimated());
					getEvent.setTotalAmountActual(getEvent.getTotalAmountEstimated());
				}
				getEvent.setUpdatedBy("LinhLHN");
				getEvent.setUpdatedOn(LocalDateTime.now());
				eventRepository.save(getEvent);
				responseMessage.setData(Arrays.asList(getEvent));
				responseMessage.setMessage(Constant.MSG_129);
			}
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	public List<EventDto> pageableEvent(List<EventDto> currentList, int pageNo, int pageSize) {
		List<EventDto> result = new ArrayList<EventDto>();
		for (int i = pageNo * pageSize; i < (pageNo + 1) * pageSize && i < currentList.size(); i++) {
			result.add(currentList.get(i));
		}
		return result;
	}

	@Override
	public ResponseMessage getEventsBySemesterAndStudentId(String semester, String studentId, int month, int pageNo,
			int pageSize) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			User user = userRepository.findByStudentId(studentId).get();

			if (semester == "") {
				semester = semesterRepository.findTop3Semester().get(0).getName();
			}
			List<Event> events = eventRepository.findBySemester(semester);

			List<UserEventSemesterDto> userEventsSemesterDto = new ArrayList<UserEventSemesterDto>();

			for (Event event : events) {
				LocalDate startDate = getStartDate(event.getId());
				if (startDate != null) {
					if (month == 0 || (month != 0 && getStartDate(event.getId()).getMonthValue() == month)) {
						UserEventSemesterDto userEventSemesterDto = new UserEventSemesterDto();
						userEventSemesterDto.setUserName(user.getName());
						userEventSemesterDto.setStudentId(user.getStudentId());

						Optional<MemberEvent> memberEventOp = memberEventRepository
								.findMemberEventByEventAndUser(event.getId(), user.getId());
						if (memberEventOp.isPresent()) {
							MemberEvent memberEvent = memberEventOp.get();
							if (memberEvent.isRegisterStatus()) {
								userEventSemesterDto.setJoin(true);
							} else {
								userEventSemesterDto.setJoin(false);
							}
						} else {
							userEventSemesterDto.setJoin(false);
						}

						EventDto eventDto = new EventDto();
						LocalDate endDate = getEndDate(event.getId());
						if (LocalDate.now().isBefore(startDate)) {
							eventDto.setStatus("Chưa diễn ra");
							eventDto.setAmountPerMemberRegister(event.getAmountPerRegisterEstimated());
							eventDto.setTotalAmount(event.getTotalAmountEstimated());
						} else if (LocalDate.now().isAfter(endDate)) {
							eventDto.setStatus("Đã kết thúc");
							eventDto.setAmountPerMemberRegister(event.getAmountPerRegisterActual());
							eventDto.setTotalAmount(event.getTotalAmountActual());
						} else {
							eventDto.setStatus("Đang diễn ra");
							eventDto.setAmountPerMemberRegister(event.getAmountPerRegisterEstimated());
							eventDto.setTotalAmount(event.getTotalAmountEstimated());
						}
						eventDto.setMaxQuantityComitee(event.getMaxQuantityComitee());
						eventDto.setAmountFromClub(event.getAmountFromClub());
						eventDto.setStartDate(startDate);
						eventDto.setName(event.getName());
						eventDto.setId(event.getId());
						eventDto.setDescription(event.getDescription());
						eventDto.setRegistrationMemberDeadline(event.getRegistrationMemberDeadline());
						eventDto.setRegistrationOrganizingCommitteeDeadline(
								event.getRegistrationOrganizingCommitteeDeadline());

						userEventSemesterDto.setEventDto(eventDto);
						userEventsSemesterDto.add(userEventSemesterDto);
					}
				}
			}
			Collections.sort(userEventsSemesterDto);
			List<UserEventSemesterDto> getUserEventPageable = pageableUserEvent(userEventsSemesterDto, pageNo,
					pageSize);
			responseMessage.setData(getUserEventPageable);
			responseMessage.setMessage(Constant.MSG_063 + " tháng " + month + " kỳ " + semester);
			responseMessage.setTotalResult(userEventsSemesterDto.size());
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	public List<UserEventSemesterDto> pageableUserEvent(List<UserEventSemesterDto> currentList, int pageNo,
			int pageSize) {
		List<UserEventSemesterDto> result = new ArrayList<UserEventSemesterDto>();
		for (int i = pageNo * pageSize; i < (pageNo + 1) * pageSize && i < currentList.size(); i++) {
			result.add(currentList.get(i));
		}
		return result;
	}

	@Override
	public ResponseMessage getAllEventHasJoinedByStudentId(String studentId, int pageNo, int pageSize) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			User user = userRepository.findByStudentId(studentId).get();
			List<MemberEvent> memberEvents = memberEventRepository.findByUserId(user.getId());
			if (!memberEvents.isEmpty()) {
				List<EventDto> eventsDto = new ArrayList<EventDto>();
				for (MemberEvent memberEvent : memberEvents) {
					if (memberEvent.isRegisterStatus()) {
						Event event = memberEvent.getEvent();
						eventsDto.add(convertToEventDto(event));
					}
				}
				Collections.sort(eventsDto);
				List<EventDto> getEventPageable = pageableEvent(eventsDto, pageNo, pageSize);
				responseMessage.setData(getEventPageable);
				responseMessage.setMessage("Lấy danh sách sự kiện đã tham gia của " + user.getName() + " - "
						+ user.getStudentId() + " thành công");
				responseMessage.setTotalResult(eventsDto.size());
				responseMessage.setPageNo(pageNo);
				responseMessage.setPageSize(pageSize);
			} else {
				responseMessage.setMessage("Bạn chưa tham gia sự kiện nào");
			}
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	private EventDto convertToEventDto(Event event) {
		LocalDate startDate = getStartDate(event.getId());
		LocalDate endDate = getEndDate(event.getId());
		EventDto eventDto = new EventDto();
		eventDto.setId(event.getId());
		eventDto.setName(event.getName());
		eventDto.setDescription(event.getDescription());
		eventDto.setStartDate(startDate);
		if (LocalDate.now().isBefore(startDate)) {
			eventDto.setStatus("Chưa diễn ra");
		} else if (LocalDate.now().isAfter(endDate)) {
			eventDto.setStatus("Đã kết thúc");
		} else {
			eventDto.setStatus("Đang diễn ra");
		}
		if (event.getAmountPerRegisterActual() == 0) {
			eventDto.setAmountPerMemberRegister(event.getAmountPerRegisterEstimated());
		} else {
			eventDto.setAmountPerMemberRegister(event.getAmountPerRegisterActual());
		}
		eventDto.setRegistrationMemberDeadline(event.getRegistrationMemberDeadline());
		eventDto.setRegistrationOrganizingCommitteeDeadline(event.getRegistrationOrganizingCommitteeDeadline());
		eventDto.setMaxQuantityComitee(event.getMaxQuantityComitee());
		return eventDto;
	}

	@Override
	public ResponseMessage getAllUpcomingEvent(int pageNo, int pageSize) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<Event> events = eventRepository.findAll();
			if (!events.isEmpty()) {
				List<EventDto> eventsDto = new ArrayList<EventDto>();
				for (Event event : events) {
					LocalDate startDate = getStartDate(event.getId());
					if (LocalDate.now().isBefore(startDate)) {
						eventsDto.add(convertToEventDto(event));
					}
				}
				if (!eventsDto.isEmpty()) {
					Collections.sort(eventsDto);
					List<EventDto> getEventPageable = pageableEvent(eventsDto, pageNo, pageSize);
					responseMessage.setData(getEventPageable);
					responseMessage.setMessage("Lấy danh sách sự kiện sắp tới thành công");
					responseMessage.setTotalResult(eventsDto.size());
					responseMessage.setPageNo(pageNo);
					responseMessage.setPageSize(pageSize);
				} else {
					responseMessage.setMessage("Sắp tới không có sự kiện nào");
				}
			} else {
				responseMessage.setMessage("Sắp tới không có sự kiện nào");
			}
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage getAllOngoingEvent(int pageNo, int pageSize) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<Event> events = eventRepository.findAll();
			if (!events.isEmpty()) {
				List<EventDto> eventsDto = new ArrayList<EventDto>();
				for (Event event : events) {
					List<EventSchedule> eventSchedules = eventScheduleRepository.findByEventId(event.getId());
					for (EventSchedule eventSchedule : eventSchedules) {
						if (LocalDate.now().isEqual(eventSchedule.getDate())) {
							eventsDto.add(convertToEventDto(event));
						}
					}
				}
				if (!eventsDto.isEmpty()) {
					Collections.sort(eventsDto);
					List<EventDto> getEventPageable = pageableEvent(eventsDto, pageNo, pageSize);
					responseMessage.setData(getEventPageable);
					responseMessage.setMessage("Lấy danh sách sự kiện đang diễn ra thành công");
					responseMessage.setTotalResult(eventsDto.size());
					responseMessage.setPageNo(pageNo);
					responseMessage.setPageSize(pageSize);
				} else {
					responseMessage.setMessage("Hiện tại không có sự kiện nào");
				}
			} else {
				responseMessage.setMessage("Hiện tại không có sự kiện nào");
			}
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage getAllClosedEvent(int pageNo, int pageSize) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<Event> events = eventRepository.findAll();
			if (!events.isEmpty()) {
				List<EventDto> eventsDto = new ArrayList<EventDto>();
				for (Event event : events) {
					LocalDate endDate = getEndDate(event.getId());
					if (LocalDate.now().isAfter(endDate)) {
						eventsDto.add(convertToEventDto(event));
					}
				}
				if (!eventsDto.isEmpty()) {
					Collections.sort(eventsDto);
					List<EventDto> getEventPageable = pageableEvent(eventsDto, pageNo, pageSize);
					responseMessage.setData(getEventPageable);
					responseMessage.setMessage("Lấy danh sách sự kiện đã kết thúc thành công");
					responseMessage.setTotalResult(eventsDto.size());
					responseMessage.setPageNo(pageNo);
					responseMessage.setPageSize(pageSize);
				} else {
					responseMessage.setMessage("Chưa có sự kiện nào đã kết thúc");
				}
			} else {
				responseMessage.setMessage("Chưa có sự kiện nào đã kết thúc");
			}
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}
}
