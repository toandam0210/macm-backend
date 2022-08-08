package com.fpt.macm.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpt.macm.constant.Constant;
import com.fpt.macm.model.dto.AttendanceEventDto;
import com.fpt.macm.model.dto.EventDto;
import com.fpt.macm.model.entity.AttendanceEvent;
import com.fpt.macm.model.entity.Event;
import com.fpt.macm.model.entity.EventSchedule;
import com.fpt.macm.model.entity.Semester;
import com.fpt.macm.model.entity.User;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.repository.AttendanceEventRepository;
import com.fpt.macm.repository.EventRepository;
import com.fpt.macm.repository.EventScheduleRepository;
import com.fpt.macm.repository.MemberEventRepository;
import com.fpt.macm.repository.SemesterRepository;
import com.fpt.macm.repository.UserRepository;

@Service
public class AttendanceEventServiceImpl implements AttendanceEventService {

	@Autowired
	AttendanceEventRepository attendanceEventRepository;

	@Autowired
	MemberEventRepository memberEventRepository;

	@Autowired
	EventScheduleRepository eventScheduleRepository;

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	SemesterRepository semesterRepository;

	@Autowired
	SemesterService semesterService;
	
	@Autowired
	EventRepository eventRepository;

	@Override
	public ResponseMessage takeAttendanceByStudentId(String studentId, int status, int eventId) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<EventSchedule> listSchedule = eventScheduleRepository.findByEventId(eventId);
			if (!listSchedule.isEmpty()) {
				EventSchedule eventSchedule = listSchedule.get(0);
				Event event = eventSchedule.getEvent();
				LocalDate eventStartDate = eventSchedule.getDate();
				if (eventStartDate.isBefore(LocalDate.now()) || eventStartDate.isEqual(LocalDate.now())) {
					User user = userRepository.findByStudentId(studentId).get();
					Optional<AttendanceEvent> attendanceEventOp = attendanceEventRepository
							.findByEventIdAndUserId(event.getId(), user.getId());
					if (attendanceEventOp.isPresent()) {
						AttendanceEvent attendanceEvent = attendanceEventOp.get();
						attendanceEvent.setStatus(status);
						attendanceEvent.setUpdatedBy("toandv");
						attendanceEvent.setUpdatedOn(LocalDateTime.now());
						attendanceEventRepository.save(attendanceEvent);

						AttendanceEventDto attendanceEventDto = new AttendanceEventDto();
						attendanceEventDto.setId(attendanceEvent.getId());
						attendanceEventDto.setEventId(event.getId());
						attendanceEventDto.setEventName(event.getName());
						attendanceEventDto.setDate(eventStartDate);
						attendanceEventDto.setName(user.getName());
						attendanceEventDto.setStudentId(user.getStudentId());
						attendanceEventDto.setStatus(status);

						responseMessage.setData(Arrays.asList(attendanceEventDto));
						responseMessage.setMessage(Constant.MSG_084);
					} else {
						responseMessage.setMessage(
								"Không có thông tin điểm danh của " + user.getName() + " - " + user.getStudentId());
					}
				} else {
					responseMessage.setMessage("Không thành công vì chưa đến thời gian điểm danh");
				}
			} else {
				responseMessage.setMessage("Hôm nay không có sự kiện");
			}
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage checkAttendanceStatusByEventId(int eventId) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<AttendanceEvent> attendancesEvent = attendanceEventRepository.findByEventId(eventId);
			List<AttendanceEventDto> attendanceEventDtos = new ArrayList<AttendanceEventDto>();

			List<EventSchedule> listSchedule = eventScheduleRepository.findByEventId(eventId);
			LocalDate startDate = LocalDate.now();
			if (listSchedule.size() > 0) {
				startDate = listSchedule.get(0).getDate();
			}

			int attend = 0;
			int absent = 0;
			for (AttendanceEvent attendanceEvent : attendancesEvent) {
				AttendanceEventDto attendanceEventDto = new AttendanceEventDto();
				attendanceEventDto.setId(attendanceEvent.getId());
				attendanceEventDto.setEventId(attendanceEvent.getEvent().getId());
				attendanceEventDto.setEventName(attendanceEvent.getEvent().getName());
				attendanceEventDto.setName(attendanceEvent.getUser().getName());
				attendanceEventDto.setStudentId(attendanceEvent.getUser().getStudentId());
				attendanceEventDto.setStatus(attendanceEvent.getStatus());
				
				if (attendanceEvent.getStatus() == 1) {
					attend++;
				}
				if (attendanceEvent.getStatus() == 0) {
					absent++;
				}
				
				attendanceEventDto.setDate(startDate);
				attendanceEventDtos.add(attendanceEventDto);
			}
			
			Collections.sort(attendanceEventDtos);
			responseMessage.setData(attendanceEventDtos);
			responseMessage.setMessage(Constant.MSG_057);
			responseMessage.setTotalActive(attend);
			responseMessage.setTotalDeactive(absent);
			responseMessage.setTotalResult(attendanceEventDtos.size());
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage getListOldEventToTakeAttendanceBySemester(String semesterName) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Semester semester = new Semester();
			Optional<Semester> semesterOp = semesterRepository.findByName(semesterName);
			if (semesterOp.isPresent()) {
				semester = semesterOp.get();
			} else {
				semester = (Semester) semesterService.getCurrentSemester().getData().get(0);
			}
			
			List<EventDto> eventsDto = new ArrayList<EventDto>();
			List<Event> events = eventRepository.findBySemesterOrderByIdAsc(semester.getName());
			for (Event event : events) {
				List<EventSchedule> eventSchedules = eventScheduleRepository.findByEventId(event.getId());
				if (!eventSchedules.isEmpty()) {
					LocalDate eventStartDate = eventSchedules.get(0).getDate();
					if (eventStartDate.isBefore(LocalDate.now())) {
						EventDto eventDto = new EventDto();
						eventDto.setId(event.getId());
						eventDto.setName(event.getName());
						eventDto.setStartDate(eventStartDate);
						eventsDto.add(eventDto);
					}
				}
			}
			if (!eventsDto.isEmpty()) {
				responseMessage.setData(eventsDto);
				responseMessage.setMessage("Lấy danh sách các sự kiện đã qua của kỳ " + semester.getName() + " để điểm danh lại thành công");
			}
			else {
				responseMessage.setMessage("Không có sự kiện nào đã qua để điểm danh lại");
			}
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

}
