package com.fpt.macm.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpt.macm.dto.AttendanceEventDto;
import com.fpt.macm.model.AttendanceEvent;
import com.fpt.macm.model.Constant;
import com.fpt.macm.model.Event;
import com.fpt.macm.model.EventSchedule;
import com.fpt.macm.model.MemberEvent;
import com.fpt.macm.model.ResponseMessage;
import com.fpt.macm.repository.AttendanceEventRepository;
import com.fpt.macm.repository.EventScheduleRepository;
import com.fpt.macm.repository.MemberEventRepository;

@Service
public class AttendanceEventServiceImpl implements AttendanceEventService {

	@Autowired
	EventScheduleServiceImpl eventScheduleServiceImpl;

	@Autowired
	AttendanceEventRepository attendanceEventRepository;

	@Autowired
	MemberEventRepository memberEventRepository;

	@Autowired
	EventScheduleRepository eventScheduleRepository;

	@Override
	public ResponseMessage takeAttendanceByMemberEventId(int memberEventId, int status) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<MemberEvent> memberEventOp = memberEventRepository.findById(memberEventId);
			MemberEvent memberEvent = memberEventOp.get();
			Event event = memberEvent.getEvent();
			AttendanceEvent attendanceEvent = attendanceEventRepository
					.findByEventIdAndMemberEventId(event.getId(), memberEventId).get();

			attendanceEvent.setStatus(status);
			attendanceEvent.setUpdatedBy("toandv");
			attendanceEvent.setUpdatedOn(LocalDateTime.now());
			attendanceEventRepository.save(attendanceEvent);

			responseMessage.setMessage(Constant.MSG_084);
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
				attendanceEventDto.setEventName(attendanceEvent.getEvent().getName());
				attendanceEventDto.setName(attendanceEvent.getMemberEvent().getUser().getName());
				attendanceEventDto.setStudentId(attendanceEvent.getMemberEvent().getUser().getStudentId());
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

}
