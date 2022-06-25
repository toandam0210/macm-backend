package com.fpt.macm.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpt.macm.dto.ScheduleDto;
import com.fpt.macm.model.CommonSchedule;
import com.fpt.macm.model.Constant;
import com.fpt.macm.model.ResponseMessage;
import com.fpt.macm.model.Semester;
import com.fpt.macm.model.TournamentSchedule;
import com.fpt.macm.repository.CommonScheduleRepository;
import com.fpt.macm.repository.TournamentRepository;
import com.fpt.macm.repository.TournamentScheduleRepository;
import com.fpt.macm.utils.Utils;

@Service
public class TournamentScheduleServiceImpl implements TournamentScheduleService {

	@Autowired
	TournamentScheduleRepository tournamentScheduleRepository;

	@Autowired
	TournamentRepository tournamentRepository;

	@Autowired
	SemesterService semesterService;

	@Autowired
	CommonScheduleService commonScheduleService;
	
	@Autowired
	CommonScheduleRepository commonScheduleRepository;

	@Override
	public ResponseMessage createPreviewTournamentSchedule(String tournamentName, String startDate, String finishDate,
			String startTime, String finishTime) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			LocalDate startLocalDate = Utils.ConvertStringToLocalDate(startDate);
			LocalDate finishLocalDate = Utils.ConvertStringToLocalDate(finishDate);
			LocalTime startLocalTime = LocalTime.parse(startTime);
			LocalTime finishLocalTime = LocalTime.parse(finishTime);

			if (startLocalDate.compareTo(finishLocalDate) > 0) {
				responseMessage.setMessage(Constant.MSG_081);
			} else if (startLocalTime.compareTo(finishLocalTime) >= 0) {
				responseMessage.setMessage(Constant.MSG_038);
			} else if (startLocalDate.compareTo(LocalDate.now()) <= 0) {
				responseMessage.setMessage(Constant.MSG_065);
			} else {
				Semester currentSemester = (Semester) semesterService.getCurrentSemester().getData().get(0);
				if (finishLocalDate.compareTo(currentSemester.getEndDate()) > 0) {
					responseMessage.setMessage(Constant.MSG_080);
				} else {
					List<ScheduleDto> listPreview = new ArrayList<ScheduleDto>();
					for (LocalDate currentLocalDate = startLocalDate; currentLocalDate
							.compareTo(finishLocalDate) <= 0; currentLocalDate = currentLocalDate.plusDays(1)) {
						if (currentLocalDate.compareTo(LocalDate.now()) > 0) {
							ScheduleDto tournamentScheduleDto = new ScheduleDto();
							tournamentScheduleDto.setDate(currentLocalDate);
							tournamentScheduleDto.setTitle(tournamentName);
							tournamentScheduleDto.setStartTime(startLocalTime);
							tournamentScheduleDto.setFinishTime(finishLocalTime);
							if (commonScheduleService.getCommonSessionByDate(currentLocalDate) == null) {
								tournamentScheduleDto.setExisted(false);
							} else {
								tournamentScheduleDto.setTitle("Trùng với "
										+ commonScheduleService.getCommonSessionByDate(currentLocalDate).getTitle());
								tournamentScheduleDto.setExisted(true);
							}
							listPreview.add(tournamentScheduleDto);
						}
					}
						responseMessage.setData(listPreview);
				}
			}
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage getListTournamentScheduleByTournament(int tournamentId) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<TournamentSchedule> listSchedule = tournamentScheduleRepository.findByTournamentId(tournamentId);
			responseMessage.setData(listSchedule);
			responseMessage.setMessage(
					"Danh sách lịch của giải đấu " + tournamentRepository.findById(tournamentId).get().getName());
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage createTournamenttSchedule(int tournamentId, List<ScheduleDto> listPreview,
			Boolean isOverwritten) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<TournamentSchedule> listTournamentSchedule = new ArrayList<TournamentSchedule>();
			List<CommonSchedule> listCommon = new ArrayList<CommonSchedule>();
			List<CommonSchedule> listCommonOverwritten = new ArrayList<CommonSchedule>();
			Boolean isInterrupted = false;
			String title = "";
			for (ScheduleDto scheduleDto : listPreview) {
				if (!scheduleDto.getExisted()) {
					TournamentSchedule tournamentSchedule = new TournamentSchedule();
					tournamentSchedule.setTournament(tournamentRepository.findById(tournamentId).get());
					tournamentSchedule.setDate(scheduleDto.getDate());
					tournamentSchedule.setStartTime(scheduleDto.getStartTime());
					tournamentSchedule.setFinishTime(scheduleDto.getFinishTime());
					tournamentSchedule.setCreatedBy("toandv");
					tournamentSchedule.setCreatedOn(LocalDateTime.now());
					listTournamentSchedule.add(tournamentSchedule);
					CommonSchedule commonSession = new CommonSchedule();
					commonSession.setTitle(tournamentSchedule.getTournament().getName());
					commonSession.setDate(scheduleDto.getDate());
					commonSession.setStartTime(scheduleDto.getStartTime());
					commonSession.setFinishTime(scheduleDto.getFinishTime());
					commonSession.setCreatedOn(LocalDateTime.now());
					commonSession.setUpdatedOn(LocalDateTime.now());
					listCommon.add(commonSession);
				} else {
					if (isOverwritten) {
						if (scheduleDto.getTitle().toString().equals("Trùng với Lịch tập")) {
							TournamentSchedule tournamentSchedule = new TournamentSchedule();
							tournamentSchedule.setTournament(tournamentRepository.findById(tournamentId).get());
							tournamentSchedule.setDate(scheduleDto.getDate());
							tournamentSchedule.setStartTime(scheduleDto.getStartTime());
							tournamentSchedule.setFinishTime(scheduleDto.getFinishTime());
							tournamentSchedule.setCreatedBy("toandv");
							tournamentSchedule.setCreatedOn(LocalDateTime.now());
							listTournamentSchedule.add(tournamentSchedule);
							CommonSchedule commonSession = new CommonSchedule();
							commonSession.setTitle(tournamentSchedule.getTournament().getName());
							commonSession.setDate(scheduleDto.getDate());
							commonSession.setStartTime(scheduleDto.getStartTime());
							commonSession.setFinishTime(scheduleDto.getFinishTime());
							commonSession.setCreatedOn(LocalDateTime.now());
							commonSession.setUpdatedOn(LocalDateTime.now());
							listCommon.add(commonSession);
							CommonSchedule getCommonSession = commonScheduleService
									.getCommonSessionByDate(scheduleDto.getDate());
							listCommonOverwritten.add(getCommonSession);
						} else {
							isInterrupted = true;
							title = scheduleDto.getTitle();
							break;
						}
					}
				}
			}
			if (isInterrupted) {
				responseMessage.setMessage(Constant.MSG_093 + title);
			} else {
				if (listTournamentSchedule.isEmpty()) {
					responseMessage.setMessage(Constant.MSG_040);
				} else {
					tournamentScheduleRepository.saveAll(listTournamentSchedule);
					commonScheduleRepository.deleteAll(listCommonOverwritten);
					commonScheduleRepository.saveAll(listCommon);
					responseMessage.setData(listTournamentSchedule);
					responseMessage.setMessage(Constant.MSG_100);
					responseMessage.setTotalResult(listTournamentSchedule.size());
				}
			}
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

}
