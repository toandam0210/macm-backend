package com.fpt.macm.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpt.macm.model.AttendanceEvent;
import com.fpt.macm.model.Constant;
import com.fpt.macm.model.EventSchedule;
import com.fpt.macm.model.MemberEvent;
import com.fpt.macm.model.ResponseMessage;
import com.fpt.macm.repository.AttendanceEventRepository;
import com.fpt.macm.repository.MemberEventRepository;

@Service
public class AttendanceEventServiceImpl implements AttendanceEventService {

	@Autowired
	EventScheduleServiceImpl eventScheduleServiceImpl;

	@Autowired
	AttendanceEventRepository attendanceEventRepository;

	@Autowired
	MemberEventRepository memberEventRepository;

	@Override
	public ResponseMessage takeAttendanceByMemberEventId(int memberEventId) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			EventSchedule eventSchedule = eventScheduleServiceImpl.getEventSessionByDate(LocalDate.now());
			if (eventSchedule != null) {
				Optional<MemberEvent> memberEventOp = memberEventRepository.findById(memberEventId);
				MemberEvent memberEvent = memberEventOp.get();
				List<AttendanceEvent> attendancesEvent = attendanceEventRepository
						.findByEventScheduleId(eventSchedule.getId());
				for (AttendanceEvent attendanceEvent : attendancesEvent) {
					if (attendanceEvent.getMemberEvent().getId() == memberEvent.getId()) {
						attendanceEvent.setStatus(!attendanceEvent.getStatus());
						attendanceEvent.setUpdatedBy("toandv");
						attendanceEvent.setUpdatedOn(LocalDateTime.now());
						attendanceEventRepository.save(attendanceEvent);
					}
				}
				responseMessage.setMessage(Constant.MSG_084);
			} else {
				responseMessage.setMessage(Constant.MSG_085);
			}
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

}
