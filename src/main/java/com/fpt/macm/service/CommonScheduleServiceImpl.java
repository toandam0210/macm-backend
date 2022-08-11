package com.fpt.macm.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpt.macm.constant.Constant;
import com.fpt.macm.model.dto.UpcomingActivityDto;
import com.fpt.macm.model.entity.CommonSchedule;
import com.fpt.macm.model.entity.Event;
import com.fpt.macm.model.entity.Semester;
import com.fpt.macm.model.entity.Tournament;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.repository.CommonScheduleRepository;
import com.fpt.macm.repository.EventRepository;
import com.fpt.macm.repository.SemesterRepository;
import com.fpt.macm.repository.TournamentRepository;
import com.fpt.macm.utils.Utils;

@Service
public class CommonScheduleServiceImpl implements CommonScheduleService {

	@Autowired
	CommonScheduleRepository commonScheduleRepository;

	@Autowired
	SemesterRepository semesterRepository;

	@Autowired
	TournamentRepository tournamentRepository;

	@Autowired
	TournamentService tournamentService;
	
	@Autowired
	EventRepository eventRepository;
	
	@Autowired
	EventService eventService;

	@Override
	public CommonSchedule getCommonSessionByDate(LocalDate date) {
		// TODO Auto-generated method stub
		try {
			Optional<CommonSchedule> getSessionOp = commonScheduleRepository.findByDate(date);
			if (getSessionOp.isPresent()) {
				return getSessionOp.get();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	@Override
	public ResponseMessage getCommonSchedule() {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		List<CommonSchedule> getAllCommonSchedule = commonScheduleRepository.listAll();
		responseMessage.setData(getAllCommonSchedule);
		responseMessage.setMessage(Constant.MSG_105);
		return responseMessage;
	}

	@Override
	public ResponseMessage getCommonScheduleByDate(String date) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			LocalDate getDate = Utils.ConvertStringToLocalDate(date);
			Optional<CommonSchedule> getSessionOp = commonScheduleRepository.findByDate(getDate);
			if (getSessionOp != null) {
				responseMessage.setData(Arrays.asList(getSessionOp.get()));
				responseMessage.setMessage(Constant.MSG_103);
			} else {
				responseMessage.setMessage(Constant.MSG_104);
			}
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage getCommonScheduleBySemester(int semesterId) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Semester getSemester = semesterRepository.findById(semesterId).get();
			List<CommonSchedule> getCommonScheduleBySemester = commonScheduleRepository
					.listCommonScheduleByTime(getSemester.getStartDate(), getSemester.getEndDate());
			responseMessage.setData(getCommonScheduleBySemester);
			responseMessage.setMessage(Constant.MSG_130 + getSemester.getName());
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage getAllUpcomingActivities(String semesterName) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<UpcomingActivityDto> upcomingActivitiesDto = new ArrayList<UpcomingActivityDto>();
			List<Event> events = eventRepository.findBySemesterOrderByIdAsc(semesterName);
			if (!events.isEmpty()) {
				for (Event event : events) {
					LocalDate startDate = eventService.getStartDate(event.getId());
					if (startDate != null && startDate.isAfter(LocalDate.now())) {
						UpcomingActivityDto upcomingActivityDto = new UpcomingActivityDto();
						upcomingActivityDto.setId(event.getId());
						upcomingActivityDto.setName(event.getName());
						upcomingActivityDto.setDate(startDate);
						upcomingActivityDto.setType(1);
						upcomingActivitiesDto.add(upcomingActivityDto);
					}
				}
			}
			
			List<Tournament> tournaments = tournamentRepository.findBySemester(semesterName);
			if (!tournaments.isEmpty()) {
				for (Tournament tournament : tournaments) {
					LocalDate startDate = tournamentService.getStartDate(tournament.getId());
					if (startDate != null && startDate.isAfter(LocalDate.now())) {
						UpcomingActivityDto upcomingActivityDto = new UpcomingActivityDto();
						upcomingActivityDto.setId(tournament.getId());
						upcomingActivityDto.setName(tournament.getName());
						upcomingActivityDto.setDate(startDate);
						upcomingActivityDto.setType(2);
						upcomingActivitiesDto.add(upcomingActivityDto);
					}
				}
			}
			
			if (!upcomingActivitiesDto.isEmpty()) {
				Collections.sort(upcomingActivitiesDto);
				responseMessage.setData(upcomingActivitiesDto);
				responseMessage.setMessage("Lấy hoạt động sắp tới thành công");
			} else {
				responseMessage.setMessage("Sắp tới không có hoạt động nào");
			}
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}
}
