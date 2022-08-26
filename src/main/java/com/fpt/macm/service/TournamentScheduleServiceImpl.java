package com.fpt.macm.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.fpt.macm.constant.Constant;
import com.fpt.macm.model.dto.ScheduleDto;
import com.fpt.macm.model.entity.CommonSchedule;
import com.fpt.macm.model.entity.Semester;
import com.fpt.macm.model.entity.TournamentSchedule;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.repository.AttendanceStatusRepository;
import com.fpt.macm.repository.CommonScheduleRepository;
import com.fpt.macm.repository.SemesterRepository;
import com.fpt.macm.repository.TournamentRepository;
import com.fpt.macm.repository.TournamentScheduleRepository;
import com.fpt.macm.repository.TrainingScheduleRepository;
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

	@Autowired
	NotificationService notificationService;
	
	@Autowired
	AttendanceStatusRepository attendanceStatusRepository;
	
	@Autowired
	TrainingScheduleService trainingScheduleService;
	
	@Autowired
	TrainingScheduleRepository trainingScheduleRepository;
	
	@Autowired
	SemesterRepository semesterRepository;
	
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
				Semester lastSemester = semesterRepository.findAll(Sort.by("startDate").descending()).get(0);
				if (finishLocalDate.compareTo(lastSemester.getEndDate()) > 0) {
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
	public ResponseMessage createTournamentSession(int tournamentId, TournamentSchedule tournamentSchedule) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			if(tournamentSchedule.getStartTime().compareTo(tournamentSchedule.getFinishTime()) >= 0) {
				responseMessage.setMessage(Constant.MSG_038);
			} else {
				Semester lastSemester = semesterRepository.findAll(Sort.by("startDate").descending()).get(0);
				if (LocalDate.now().compareTo(lastSemester.getEndDate()) > 0) {
					responseMessage.setMessage(Constant.MSG_080);
				}
				else {
					if(tournamentSchedule.getDate().compareTo(LocalDate.now()) > 0) {
						if (commonScheduleService.getCommonSessionByDate(tournamentSchedule.getDate()) == null) {
							tournamentSchedule.setTournament(tournamentRepository.findById(tournamentId).get());
							tournamentSchedule.setCreatedBy("LinhLHN");
							tournamentSchedule.setCreatedOn(LocalDateTime.now());
							tournamentSchedule.setUpdatedBy("LinhLHN");
							tournamentSchedule.setUpdatedOn(LocalDateTime.now());
							tournamentScheduleRepository.save(tournamentSchedule);
							responseMessage.setData(Arrays.asList(tournamentSchedule));
							responseMessage.setMessage(Constant.MSG_106);
							CommonSchedule commonSession = new CommonSchedule();
							commonSession.setTitle(tournamentSchedule.getTournament().getName());
							commonSession.setDate(tournamentSchedule.getDate());
							commonSession.setStartTime(tournamentSchedule.getStartTime());
							commonSession.setFinishTime(tournamentSchedule.getFinishTime());
							commonSession.setCreatedOn(LocalDateTime.now());
							commonSession.setUpdatedOn(LocalDateTime.now());
							commonSession.setType(2);
							commonScheduleRepository.save(commonSession);
						}
						else {
							responseMessage.setMessage(Constant.MSG_104);
						}
					}
					else {
						responseMessage.setMessage(Constant.MSG_105);
					}
				}
			}
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage updateTournamentSession(int tournamentSessionId, TournamentSchedule tournamentSchedule) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<TournamentSchedule> currentSession = tournamentScheduleRepository.findById(tournamentSessionId);
			TournamentSchedule getTournamentSession = currentSession.get();
			if(getTournamentSession.getDate().compareTo(LocalDate.now()) > 0) {
				getTournamentSession.setStartTime(tournamentSchedule.getStartTime());
				getTournamentSession.setFinishTime(tournamentSchedule.getFinishTime());
				getTournamentSession.setUpdatedBy("LinhLHN");
				getTournamentSession.setUpdatedOn(LocalDateTime.now());
				tournamentScheduleRepository.save(getTournamentSession);
				responseMessage.setData(Arrays.asList(getTournamentSession));
				responseMessage.setMessage(Constant.MSG_107);
				CommonSchedule commonSession = commonScheduleService.getCommonSessionByDate(getTournamentSession.getDate());
				commonSession.setStartTime(tournamentSchedule.getStartTime());
				commonSession.setFinishTime(tournamentSchedule.getFinishTime());
				commonSession.setUpdatedOn(LocalDateTime.now());
				commonScheduleRepository.save(commonSession);
			}
			else {
				responseMessage.setMessage(Constant.MSG_108);
			}
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage deleteTournamentSession(int tournamentSessionId) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<TournamentSchedule> currentSession = tournamentScheduleRepository.findById(tournamentSessionId);
			TournamentSchedule getTournamentSession = currentSession.get();
			if(getTournamentSession.getDate().compareTo(LocalDate.now()) > 0) {
				tournamentScheduleRepository.delete(getTournamentSession);
				responseMessage.setData(Arrays.asList(getTournamentSession));
				responseMessage.setMessage(Constant.MSG_109);
				CommonSchedule commonSession = commonScheduleService.getCommonSessionByDate(getTournamentSession.getDate());
				commonScheduleRepository.delete(commonSession);
			}
			else {
				responseMessage.setMessage(Constant.MSG_108);
			}
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}
	
	@Override
	public TournamentSchedule getTournamentSessionByDate(LocalDate date) {
		// TODO Auto-generated method stub
		try {
			Optional<TournamentSchedule> getTournamentSessionOp = tournamentScheduleRepository.findByDate(date);
			if (getTournamentSessionOp.isPresent()) {
				TournamentSchedule getTournamentSession = getTournamentSessionOp.get();
				return getTournamentSession;
			} else {
				return null;
			}
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}

	@Override
	public ResponseMessage getAllTournamentSchedule() {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<TournamentSchedule> tournamentSchedules = tournamentScheduleRepository.findAll();
			if (!tournamentSchedules.isEmpty()) {
				responseMessage.setData(tournamentSchedules);
				responseMessage.setMessage("Lấy tất cả lịch của giải đấu thành công");
			} else {
				responseMessage.setMessage("Giải đấu chưa có lịch nào");
			}
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}
}
