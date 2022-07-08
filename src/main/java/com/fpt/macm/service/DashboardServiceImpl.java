package com.fpt.macm.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpt.macm.dto.AttendanceReportDto;
import com.fpt.macm.dto.EventDashboardDto;
import com.fpt.macm.model.AttendanceStatus;
import com.fpt.macm.model.CollaboratorReport;
import com.fpt.macm.model.Event;
import com.fpt.macm.model.MemberEvent;
import com.fpt.macm.model.ResponseMessage;
import com.fpt.macm.model.Semester;
import com.fpt.macm.model.TrainingSchedule;
import com.fpt.macm.model.UserStatusReport;
import com.fpt.macm.repository.AttendanceStatusRepository;
import com.fpt.macm.repository.CollaboratorReportRepository;
import com.fpt.macm.repository.EventRepository;
import com.fpt.macm.repository.MemberEventRepository;
import com.fpt.macm.repository.SemesterRepository;
import com.fpt.macm.repository.TournamentRepository;
import com.fpt.macm.repository.TrainingScheduleRepository;
import com.fpt.macm.repository.UserStatusReportRepository;

@Service
public class DashboardServiceImpl implements DashboardService {
	
	@Autowired
	CollaboratorReportRepository collaboratorReportRepository;
	
	@Autowired
	SemesterRepository semesterRepository;
	
	@Autowired
	AttendanceStatusRepository attendanceStatusRepository;
	
	@Autowired
	TrainingScheduleRepository trainingScheduleRepository;
	
	@Autowired
	EventRepository eventRepository;
	
	@Autowired
	TournamentRepository tournamentRepository;
	
	@Autowired
	MemberEventRepository memberEventRepository;
	
	@Autowired
	EventService eventService;
	
	@Autowired
	UserStatusReportRepository userStatusReportRepository;
	
	
	@Override
	public ResponseMessage getCollaboratorReport() {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<CollaboratorReport> collaboratorReports = collaboratorReportRepository.findAll();
			responseMessage.setData(collaboratorReports);
			responseMessage.setMessage("Lấy dữ liệu báo cáo CTV thành công");
			responseMessage.setTotalResult(collaboratorReports.size());
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}
	
	@Override
	public ResponseMessage attendanceReport(String semesterName) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<Semester> semesterOp = semesterRepository.findByName(semesterName);
			List<AttendanceStatus> listAttendanceStatus = attendanceStatusRepository.findAll();
			List<AttendanceReportDto> attendanceReportDtos = new ArrayList<AttendanceReportDto>();
			if (semesterOp.isPresent()) {
				Semester semester = semesterOp.get();
				List<TrainingSchedule> trainingSchedulesBySemester = trainingScheduleRepository
						.listTrainingScheduleByTime(semester.getStartDate(), semester.getEndDate());
				for (TrainingSchedule trainingSchedule : trainingSchedulesBySemester) {
					int totalUserJoinTrainingSession = 0;
					int totalUserAttentInTrainingSession = 0;
					int totalUserAbsentInTrainingSession = 0;
					AttendanceReportDto attendanceReportDto = new AttendanceReportDto();
					attendanceReportDto.setDate(trainingSchedule.getDate());
					for (AttendanceStatus attendanceStatus : listAttendanceStatus) {
						if (trainingSchedule.getId() == attendanceStatus.getTrainingSchedule().getId()) {
							totalUserJoinTrainingSession++;
							if(attendanceStatus.getStatus() == 1) {
								totalUserAttentInTrainingSession++;
							}
							if(attendanceStatus.getStatus() == 0) {
								totalUserAbsentInTrainingSession++;
							}
						}
					}
					attendanceReportDto.setTotalAttendInTrainingSession(totalUserAttentInTrainingSession);
					attendanceReportDto.setTotalAbsentInTrainingSession(totalUserAbsentInTrainingSession);
					attendanceReportDto.setTotalUserJoin(totalUserJoinTrainingSession);
					attendanceReportDtos.add(attendanceReportDto);
				}
				List<AttendanceReportDto> listRemove = new ArrayList<AttendanceReportDto>();
				for (AttendanceReportDto attendanceReportDto : attendanceReportDtos) {
					if(attendanceReportDto.getDate().isAfter(LocalDate.now())) {
						listRemove.add(attendanceReportDto);
					}
				}
				attendanceReportDtos.removeAll(listRemove);
				responseMessage.setData(attendanceReportDtos);
				responseMessage.setMessage("Lấy dữ liệu điểm danh thành công");
				responseMessage.setTotalResult(attendanceReportDtos.size());
			}
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage eventReport() {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<Event> events = eventRepository.findAll();
			List<EventDashboardDto> eventDashboardDtos = new ArrayList<EventDashboardDto>();
			for (Event event : events) {
				LocalDate startDate = eventService.getStartDate(event.getId());
				if(startDate.isBefore(LocalDate.now())) {
				List<MemberEvent> memberEvents = memberEventRepository.findByEventIdOrderByIdAsc(event.getId());
				EventDashboardDto eventDashboardDto = new EventDashboardDto();
				eventDashboardDto.setTotalMemberJoin(memberEvents.size());
				eventDashboardDto.setTotalRevenueInEvent(event.getTotalAmountActual());
				eventDashboardDto.setTotalExpenditure(event.getAmountPerRegisterActual() * memberEvents.size());
				eventDashboardDto.setEventName(event.getName());
				eventDashboardDtos.add(eventDashboardDto);
				}
			}
			responseMessage.setData(eventDashboardDtos);
			responseMessage.setMessage("Lấy dữ liệu sự kiện thành công");
			responseMessage.setTotalResult(eventDashboardDtos.size());
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage statusMemberReport() {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<UserStatusReport> userStatusReports = userStatusReportRepository.findAll();
			if(userStatusReports.size() > 0) {
				responseMessage.setData(userStatusReports);
				responseMessage.setMessage("Lấy dữ liệu report thành công");
				responseMessage.setTotalResult(userStatusReports.size());
			}else {
				responseMessage.setMessage("Không có dữ liệu");
			}
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}
}
