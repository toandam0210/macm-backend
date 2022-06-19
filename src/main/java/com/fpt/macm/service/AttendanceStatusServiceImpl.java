package com.fpt.macm.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpt.macm.dto.AttendanceStatusDto;
import com.fpt.macm.model.AttendanceStatus;
import com.fpt.macm.model.Constant;
import com.fpt.macm.model.ResponseMessage;
import com.fpt.macm.model.TrainingSchedule;
import com.fpt.macm.model.User;
import com.fpt.macm.repository.AttendanceStatusRepository;
import com.fpt.macm.repository.UserRepository;

@Service
public class AttendanceStatusServiceImpl implements AttendanceStatusService {
	@Autowired
	TrainingScheduleServiceImpl trainingScheduleServiceImpl;

	@Autowired
	AttendanceStatusRepository attendanceStatusRepository;

	@Autowired
	UserRepository userRepository;

	@Override
	public ResponseMessage takeAttendanceByStudentId(String studentId) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			TrainingSchedule trainingSchedule = trainingScheduleServiceImpl.getTrainingSessionByDate(LocalDate.now());
			if (trainingSchedule != null) {
				Optional<User> userOp = userRepository.findByStudentId(studentId);
				User user = userOp.get();
				List<AttendanceStatus> attendancesStatus = attendanceStatusRepository
						.findByTrainingScheduleId(trainingSchedule.getId());
				for (AttendanceStatus attendanceStatus : attendancesStatus) {
					if (attendanceStatus.getUser().getId() == user.getId()) {
						attendanceStatus.setStatus(!attendanceStatus.isStatus());
						attendanceStatus.setUpdatedOn(LocalDateTime.now());
						attendanceStatus.setUpdatedBy("toandv");
						attendanceStatusRepository.save(attendanceStatus);
					}
				}
				responseMessage.setMessage(Constant.MSG_055);
			} else {
				responseMessage.setMessage(Constant.MSG_056);
			}
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage checkAttendanceStatusByTrainingSchedule(int trainingScheduleId) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<AttendanceStatus> attendancesStatus = attendanceStatusRepository.findByTrainingScheduleId(trainingScheduleId);
			List<AttendanceStatusDto> attendanceStatusDtos = new ArrayList<AttendanceStatusDto>();
			int attend = 0;
			for (AttendanceStatus attendanceStatus : attendancesStatus) {
				AttendanceStatusDto attendanceStatusDto = new AttendanceStatusDto();
				attendanceStatusDto.setName(attendanceStatus.getUser().getName());
				attendanceStatusDto.setStudentId(attendanceStatus.getUser().getStudentId());
				attendanceStatusDto.setStatus(attendanceStatus.isStatus());
				if(attendanceStatus.isStatus()) {
					attend++;
				}
				attendanceStatusDto.setDate(attendanceStatus.getTrainingSchedule().getDate());
				attendanceStatusDtos.add(attendanceStatusDto);
			}
			responseMessage.setData(attendanceStatusDtos);
			responseMessage.setMessage(Constant.MSG_057);
			responseMessage.setTotalActive(attend);
			responseMessage.setTotalDeactive(attendanceStatusDtos.size() - attend);
			responseMessage.setTotalResult(attendanceStatusDtos.size());
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

}
