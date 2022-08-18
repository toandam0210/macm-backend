package com.fpt.macm.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.fpt.macm.constant.Constant;
import com.fpt.macm.model.dto.ActiveUserDto;
import com.fpt.macm.model.dto.CompetitivePlayerDto;
import com.fpt.macm.model.dto.CompetitiveTypeDto;
import com.fpt.macm.model.dto.ExhibitionPlayerDto;
import com.fpt.macm.model.dto.ExhibitionTeamDto;
import com.fpt.macm.model.dto.ExhibitionTypeDto;
import com.fpt.macm.model.dto.RoleEventDto;
import com.fpt.macm.model.dto.ScheduleDto;
import com.fpt.macm.model.dto.TournamentCreateDto;
import com.fpt.macm.model.dto.TournamentDto;
import com.fpt.macm.model.dto.TournamentOrganizingCommitteeDto;
import com.fpt.macm.model.dto.TournamentOrganizingCommitteePaymentStatusReportDto;
import com.fpt.macm.model.dto.TournamentPlayerDto;
import com.fpt.macm.model.dto.TournamentPlayerPaymentStatusReportDto;
import com.fpt.macm.model.dto.TournamentResultDto;
import com.fpt.macm.model.dto.TournamentSampleTypeDto;
import com.fpt.macm.model.dto.UserTournamentDto;
import com.fpt.macm.model.dto.UserTournamentOrganizingCommitteeDto;
import com.fpt.macm.model.entity.Area;
import com.fpt.macm.model.entity.AttendanceStatus;
import com.fpt.macm.model.entity.ClubFund;
import com.fpt.macm.model.entity.CommonSchedule;
import com.fpt.macm.model.entity.CompetitiveMatch;
import com.fpt.macm.model.entity.CompetitivePlayer;
import com.fpt.macm.model.entity.CompetitiveResult;
import com.fpt.macm.model.entity.CompetitiveType;
import com.fpt.macm.model.entity.CompetitiveTypeSample;
import com.fpt.macm.model.entity.ExhibitionPlayer;
import com.fpt.macm.model.entity.ExhibitionResult;
import com.fpt.macm.model.entity.ExhibitionTeam;
import com.fpt.macm.model.entity.ExhibitionType;
import com.fpt.macm.model.entity.ExhibitionTypeSample;
import com.fpt.macm.model.entity.RoleEvent;
import com.fpt.macm.model.entity.Semester;
import com.fpt.macm.model.entity.Tournament;
import com.fpt.macm.model.entity.TournamentOrganizingCommittee;
import com.fpt.macm.model.entity.TournamentOrganizingCommitteePaymentStatusReport;
import com.fpt.macm.model.entity.TournamentPlayer;
import com.fpt.macm.model.entity.TournamentPlayerPaymentStatusReport;
import com.fpt.macm.model.entity.TournamentRole;
import com.fpt.macm.model.entity.TournamentSchedule;
import com.fpt.macm.model.entity.TrainingSchedule;
import com.fpt.macm.model.entity.User;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.repository.AreaRepository;
import com.fpt.macm.repository.AttendanceStatusRepository;
import com.fpt.macm.repository.ClubFundRepository;
import com.fpt.macm.repository.CommonScheduleRepository;
import com.fpt.macm.repository.CompetitiveMatchRepository;
import com.fpt.macm.repository.CompetitivePlayerRepository;
import com.fpt.macm.repository.CompetitiveResultRepository;
import com.fpt.macm.repository.CompetitiveTypeRepository;
import com.fpt.macm.repository.CompetitiveTypeSampleRepository;
import com.fpt.macm.repository.ExhibitionPlayerRepository;
import com.fpt.macm.repository.ExhibitionResultRepository;
import com.fpt.macm.repository.ExhibitionTeamRepository;
import com.fpt.macm.repository.ExhibitionTypeRepository;
import com.fpt.macm.repository.ExhibitionTypeSampleRepository;
import com.fpt.macm.repository.NotificationRepository;
import com.fpt.macm.repository.RoleEventRepository;
import com.fpt.macm.repository.SemesterRepository;
import com.fpt.macm.repository.TournamentOrganizingCommitteePaymentStatusReportRepository;
import com.fpt.macm.repository.TournamentOrganizingCommitteeRepository;
import com.fpt.macm.repository.TournamentPlayerPaymentStatusReportRepository;
import com.fpt.macm.repository.TournamentPlayerRepository;
import com.fpt.macm.repository.TournamentRepository;
import com.fpt.macm.repository.TournamentRoleRepository;
import com.fpt.macm.repository.TournamentScheduleRepository;
import com.fpt.macm.repository.TrainingScheduleRepository;
import com.fpt.macm.repository.UserRepository;
import com.fpt.macm.utils.Utils;

@Service
public class TournamentServiceImpl implements TournamentService {

	@Autowired
	TournamentRepository tournamentRepository;

	@Autowired
	TournamentOrganizingCommitteeRepository tournamentOrganizingCommitteeRepository;

	@Autowired
	CompetitiveTypeRepository competitiveTypeRepository;

	@Autowired
	ExhibitionTypeRepository exhibitionTypeRepository;

	@Autowired
	SemesterRepository semesterRepository;

	@Autowired
	TournamentScheduleRepository tournamentScheduleRepository;

	@Autowired
	TournamentPlayerRepository tournamentPlayerRepository;

	@Autowired
	CompetitivePlayerRepository competitivePlayerRepository;

	@Autowired
	ExhibitionPlayerRepository exhibitionPlayerRepository;

	@Autowired
	ExhibitionTeamRepository exhibitionTeamRepository;

	@Autowired
	RoleEventRepository roleEventRepository;

	@Autowired
	ClubFundRepository clubFundRepository;

	@Autowired
	CompetitiveMatchRepository competitiveMatchRepository;

	@Autowired
	TournamentOrganizingCommitteePaymentStatusReportRepository tournamentOrganizingCommitteePaymentStatusReportRepository;

	@Autowired
	TournamentPlayerPaymentStatusReportRepository tournamentPlayerPaymentStatusReportRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	NotificationRepository notificationRepository;

	@Autowired
	TournamentRoleRepository tournamentRoleRepository;

	@Autowired
	AttendanceStatusRepository attendanceStatusRepository;

	@Autowired
	TrainingScheduleRepository trainingScheduleRepository;

	@Autowired
	CommonScheduleRepository commonScheduleRepository;

	@Autowired
	CompetitiveResultRepository competitiveResultRepository;

	@Autowired
	AreaRepository areaRepository;

	@Autowired
	CompetitiveTypeSampleRepository competitiveTypeSampleRepository;

	@Autowired
	ExhibitionTypeSampleRepository exhibitionTypeSampleRepository;

	@Autowired
	ExhibitionResultRepository exhibitionResultRepository;

	@Autowired
	ExhibitionService exhibitionService;

	@Autowired
	CompetitiveService competitiveService;

	@Autowired
	ClubFundService clubFundService;

	@Autowired
	NotificationService notificationService;

	@Autowired
	SemesterService semesterService;

	@Autowired
	CommonScheduleService commonScheduleService;

	@Autowired
	TrainingScheduleService trainingScheduleService;

	@Override
	public ResponseMessage createTournament(String studentId, TournamentCreateDto tournamentCreateDto,
			boolean isOverwritten) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Tournament tournament = tournamentCreateDto.getTournament();
			List<RoleEventDto> rolesEventDto = tournamentCreateDto.getRolesEventDto();
			List<ScheduleDto> listPreview = tournamentCreateDto.getListPreview();

			if (tournament == null || listPreview == null || listPreview.isEmpty()) {
				responseMessage.setMessage("Không đc null");
				return responseMessage;
			}

			if (isAvailableToCreateTournament(listPreview, isOverwritten)) {
				User user = userRepository.findByStudentId(studentId).get();

				Semester semester = (Semester) semesterService.getCurrentSemester().getData().get(0);

				List<TournamentSchedule> listTournamentSchedule = new ArrayList<TournamentSchedule>();
				List<CommonSchedule> listCommon = new ArrayList<CommonSchedule>();
				List<CommonSchedule> listCommonOverwritten = new ArrayList<CommonSchedule>();
				List<TrainingSchedule> listTrainingOverwritten = new ArrayList<TrainingSchedule>();
				List<AttendanceStatus> listAttendanceStatusOverwritten = new ArrayList<AttendanceStatus>();

				tournament.setSemester(semester.getName());
				tournament.setStatus(true);
				tournament.setCreatedBy(user.getName() + " - " + user.getStudentId());
				tournament.setCreatedOn(LocalDateTime.now());
				tournament.setTotalAmount(0);
				tournament.setTotalAmountFromClubActual(0);
				tournament.setStage(0);
				Set<CompetitiveType> competitiveTypes = tournament.getCompetitiveTypes();
				Set<ExhibitionType> exhibitionTypes = tournament.getExhibitionTypes();
				for (CompetitiveType competitiveType : competitiveTypes) {
					competitiveType.setStatus(0);
					competitiveType.setChanged(false);
					competitiveType.setCanDelete(true);
					competitiveType.setCreatedBy(user.getName() + " - " + user.getStudentId());
					competitiveType.setCreatedOn(LocalDateTime.now());
				}
				for (ExhibitionType exhibitionType : exhibitionTypes) {
					exhibitionType.setStatus(0);
					exhibitionType.setCreatedBy(user.getName() + " - " + user.getStudentId());
					exhibitionType.setCreatedOn(LocalDateTime.now());
				}
				tournamentRepository.save(tournament);

				// Trừ tiền từ clb
				if (tournament.getTotalAmountFromClubEstimate() > 0) {
					clubFundService.withdrawFromClubFund(user.getStudentId(),
							tournament.getTotalAmountFromClubEstimate(),
							("Rút tiền để tổ chức giải đấu " + tournament.getName()));
				}

				Tournament newTournament = tournamentRepository.findAll(Sort.by("id").descending()).get(0);

				// Gửi thông báo đến user
				notificationService.createTournamentCreateNotification(newTournament.getId(), newTournament.getName());

				// Tạo role ban tổ chức cho giải đấu vs số lượng của từng role
				for (RoleEventDto roleEventDto : rolesEventDto) {
					Optional<RoleEvent> roleEventOp = roleEventRepository.findByName(roleEventDto.getName());
					if (roleEventOp.isPresent()) {
						RoleEvent roleEvent = roleEventOp.get();

						TournamentRole tournamentRole = new TournamentRole();
						tournamentRole.setTournament(newTournament);
						tournamentRole.setQuantity(roleEventDto.getMaxQuantity());
						tournamentRole.setRoleEvent(roleEvent);
						tournamentRoleRepository.save(tournamentRole);
					} else {
						RoleEvent roleEvent = new RoleEvent();
						roleEvent.setName(roleEventDto.getName());
						roleEventRepository.save(roleEvent);

						List<RoleEvent> roleEvents = roleEventRepository.findAll(Sort.by("id").descending());
						RoleEvent newRoleEvent = roleEvents.get(0);

						TournamentRole tournamentRole = new TournamentRole();
						tournamentRole.setTournament(newTournament);
						tournamentRole.setQuantity(roleEventDto.getMaxQuantity());
						tournamentRole.setRoleEvent(newRoleEvent);
						tournamentRoleRepository.save(tournamentRole);
					}
				}

				for (ScheduleDto scheduleDto : listPreview) {
					TournamentSchedule tournamentSchedule = new TournamentSchedule();
					tournamentSchedule.setTournament(newTournament);
					tournamentSchedule.setDate(scheduleDto.getDate());
					tournamentSchedule.setStartTime(scheduleDto.getStartTime());
					tournamentSchedule.setFinishTime(scheduleDto.getFinishTime());
					tournamentSchedule.setCreatedBy(user.getName() + " - " + user.getStudentId());
					tournamentSchedule.setCreatedOn(LocalDateTime.now());
					listTournamentSchedule.add(tournamentSchedule);

					CommonSchedule commonSession = new CommonSchedule();
					commonSession.setTitle(tournamentSchedule.getTournament().getName());
					commonSession.setDate(scheduleDto.getDate());
					commonSession.setStartTime(scheduleDto.getStartTime());
					commonSession.setFinishTime(scheduleDto.getFinishTime());
					commonSession.setCreatedOn(LocalDateTime.now());
					commonSession.setUpdatedOn(LocalDateTime.now());
					commonSession.setType(2);
					listCommon.add(commonSession);

					CommonSchedule oldCommonSchedule = commonScheduleService
							.getCommonSessionByDate(scheduleDto.getDate());
					if (oldCommonSchedule != null) {
						listCommonOverwritten.add(oldCommonSchedule);
					}

					TrainingSchedule oldTrainingSchedule = trainingScheduleService
							.getTrainingScheduleByDate(scheduleDto.getDate());
					if (oldTrainingSchedule != null) {
						listTrainingOverwritten.add(oldTrainingSchedule);
						List<AttendanceStatus> listAttendanceStatus = attendanceStatusRepository
								.findByTrainingScheduleIdOrderByIdAsc(oldTrainingSchedule.getId());
						for (AttendanceStatus attendanceStatus : listAttendanceStatus) {
							listAttendanceStatusOverwritten.add(attendanceStatus);
						}
					}
				}

				tournamentScheduleRepository.saveAll(listTournamentSchedule);

				if (!listCommonOverwritten.isEmpty()) {
					commonScheduleRepository.deleteAll(listCommonOverwritten);
				}
				if (!listAttendanceStatusOverwritten.isEmpty()) {
					attendanceStatusRepository.deleteAll(listAttendanceStatusOverwritten);
				}
				if (!listTrainingOverwritten.isEmpty()) {
					trainingScheduleRepository.deleteAll(listTrainingOverwritten);
				}

				commonScheduleRepository.saveAll(listCommon);

				responseMessage.setData(Arrays.asList(newTournament));
				responseMessage.setCode(200);
				responseMessage.setMessage(Constant.MSG_099);
			} else {
				responseMessage.setMessage("Không thể tạo giải đấu vì lịch đang bị trùng");
			}
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	private boolean isAvailableToCreateTournament(List<ScheduleDto> listPreview, boolean isOverwritten) {
		for (ScheduleDto scheduleDto : listPreview) {
			if (scheduleDto.getExisted()) {
				if (!scheduleDto.getTitle().toString().equals("Trùng với Lịch tập")) {
					return false;
				}
				if (!isOverwritten) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public ResponseMessage getAllTournamentOrganizingCommitteeByTournamentId(int tournamentId) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<TournamentOrganizingCommittee> tournamentOrganizingCommittees = tournamentOrganizingCommitteeRepository
					.findByTournamentId(tournamentId);
			if (!tournamentOrganizingCommittees.isEmpty()) {
				List<TournamentOrganizingCommitteeDto> tournamentOrganizingCommitteesDto = new ArrayList<TournamentOrganizingCommitteeDto>();
				for (TournamentOrganizingCommittee tournamentOrganizingCommittee : tournamentOrganizingCommittees) {
					TournamentOrganizingCommitteeDto tournamentOrganizingCommitteeDto = convertToTournamentOrganizingCommitteeDto(
							tournamentOrganizingCommittee);
					tournamentOrganizingCommitteesDto.add(tournamentOrganizingCommitteeDto);
				}
				responseMessage.setData(tournamentOrganizingCommitteesDto);
				responseMessage.setTotalResult(tournamentOrganizingCommitteesDto.size());
				responseMessage.setMessage(Constant.MSG_097);
			} else {
				responseMessage.setMessage("Chưa có thành viên ban tổ chức giải đấu");
			}
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage updateTournamentOrganizingCommitteeRole(
			List<TournamentOrganizingCommitteeDto> tournamentOrganizingCommitteesDto) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<TournamentOrganizingCommitteeDto> updatedTournamentOrganizingCommitteesDto = new ArrayList<TournamentOrganizingCommitteeDto>();
			for (TournamentOrganizingCommitteeDto tournamentOrganizingCommitteeDto : tournamentOrganizingCommitteesDto) {
				Optional<TournamentOrganizingCommittee> tournamentOrganizingCommitteeOp = tournamentOrganizingCommitteeRepository
						.findById(tournamentOrganizingCommitteeDto.getId());
				TournamentOrganizingCommittee tournamentOrganizingCommittee = tournamentOrganizingCommitteeOp.get();
				if (tournamentOrganizingCommittee.getRoleEvent().getId() != tournamentOrganizingCommitteeDto
						.getRoleTournamentDto().getId()) {
					RoleEvent roleEvent = new RoleEvent();
					roleEvent.setId(tournamentOrganizingCommitteeDto.getRoleTournamentDto().getId());
					roleEvent.setName(tournamentOrganizingCommitteeDto.getRoleTournamentDto().getName());
					tournamentOrganizingCommittee.setRoleEvent(roleEvent);
					tournamentOrganizingCommittee.setUpdatedBy("toandv");
					tournamentOrganizingCommittee.setUpdatedOn(LocalDateTime.now());
					tournamentOrganizingCommitteeRepository.save(tournamentOrganizingCommittee);
					TournamentOrganizingCommitteeDto updatedTournamentOrganizingCommitteeDto = convertToTournamentOrganizingCommitteeDto(
							tournamentOrganizingCommittee);
					updatedTournamentOrganizingCommitteesDto.add(updatedTournamentOrganizingCommitteeDto);
				}
			}
			if (!updatedTournamentOrganizingCommitteesDto.isEmpty()) {
				responseMessage.setData(updatedTournamentOrganizingCommitteesDto);
				responseMessage.setMessage(Constant.MSG_098);
			} else {
				responseMessage.setMessage("Không có trường nào thay đổi");
			}
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	private TournamentOrganizingCommitteeDto convertToTournamentOrganizingCommitteeDto(
			TournamentOrganizingCommittee tournamentOrganizingCommittee) {
		TournamentOrganizingCommitteeDto tournamentOrganizingCommitteeDto = new TournamentOrganizingCommitteeDto();
		tournamentOrganizingCommitteeDto.setId(tournamentOrganizingCommittee.getId());
		tournamentOrganizingCommitteeDto.setUserName(tournamentOrganizingCommittee.getUser().getName());
		tournamentOrganizingCommitteeDto.setUserStudentId(tournamentOrganizingCommittee.getUser().getStudentId());
		tournamentOrganizingCommitteeDto.setPaymentStatus(tournamentOrganizingCommittee.isPaymentStatus());

		RoleEventDto roleEventDto = new RoleEventDto();
		roleEventDto.setId(tournamentOrganizingCommittee.getRoleEvent().getId());
		roleEventDto.setName(tournamentOrganizingCommittee.getRoleEvent().getName());
		tournamentOrganizingCommitteeDto.setRoleTournamentDto(roleEventDto);
		return tournamentOrganizingCommitteeDto;
	}

	@Override
	public ResponseMessage updateTournament(int tournamentId, TournamentDto tournamentDto) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<Tournament> tournamentOp = tournamentRepository.findById(tournamentId);
			if (tournamentOp.isPresent()) {
				Tournament tournament = tournamentOp.get();
				if (tournament.getStage() == 0) {
					tournament.setName(tournamentDto.getName());
					tournament.setDescription(tournamentDto.getDescription());
					tournament.setRegistrationPlayerDeadline(tournamentDto.getRegistrationPlayerDeadline());
					tournament.setRegistrationOrganizingCommitteeDeadline(
							tournamentDto.getRegistrationOrganizingCommitteeDeadline());
					Set<CompetitiveTypeDto> competitiveTypeDtos = tournamentDto.getCompetitiveTypesDto();
					Set<CompetitiveType> competitiveTypes = tournament.getCompetitiveTypes();
					Set<ExhibitionTypeDto> exhibitionTypeDtos = tournamentDto.getExhibitionTypesDto();
					Set<ExhibitionType> exhibitionTypes = tournament.getExhibitionTypes();
					Set<CompetitiveType> competitiveTypesNew = new HashSet<CompetitiveType>();

					for (CompetitiveType competitiveType : competitiveTypes) {
						boolean isExist = false;
						for(CompetitiveTypeDto competitiveTypeDto : competitiveTypeDtos) {
							if(competitiveTypeDto.getWeightMin() == competitiveType.getWeightMin() && competitiveTypeDto.getWeightMax() == competitiveType.getWeightMax() && (competitiveTypeDto.isGender() == competitiveType.isGender())) {
								isExist = true;
								break;
							}
						}
						if(isExist == false) {
							List<CompetitiveMatch> competitiveMatchs = competitiveMatchRepository.listMatchsByType(competitiveType.getId());
							competitiveMatchRepository.deleteAll(competitiveMatchs);
							List<CompetitivePlayer> competitivePlayers = competitivePlayerRepository.findByCompetitiveTypeId(competitiveType.getId());
							competitivePlayerRepository.deleteAll(competitivePlayers);
							List<TournamentPlayer> tournamentPlayers = new ArrayList<TournamentPlayer>();
							for (CompetitivePlayer competitivePlayer : competitivePlayers) {
								tournamentPlayers.add(competitivePlayer.getTournamentPlayer());
							}
							for (TournamentPlayer tournamentPlayer : tournamentPlayers) {
								List<ExhibitionPlayer> exhibitionPlayers = exhibitionPlayerRepository.findAllByPlayerId(tournamentPlayer.getId());
								if(exhibitionPlayers.size() == 0) {
									tournamentPlayerRepository.delete(tournamentPlayer);
								}
							}
							
							competitiveTypes.remove(competitiveType);
						}
					}
					for (CompetitiveTypeDto competitiveTypeDto : competitiveTypeDtos) {
						boolean isExist = false;
						for (CompetitiveType competitiveType : competitiveTypes) {
							if (competitiveTypeDto.getWeightMin() == competitiveType.getWeightMin()
									&& competitiveTypeDto.getWeightMax() == competitiveType.getWeightMax()
									&& (competitiveTypeDto.isGender() == competitiveType.isGender())) {
								isExist = true;
								break;
							}
						}
						if (isExist == false) {
							competitiveTypes.add(convertCompetitiveTypeDto(competitiveTypeDto));
							for (CompetitiveType competitiveType : competitiveTypes) {
								competitiveType.setStatus(0);
								competitiveTypesNew.add(competitiveType);
							}
						}
					}

					for (ExhibitionType exhibitionType : exhibitionTypes) {
						boolean isExist = false;
						for (ExhibitionTypeDto exhibitionTypeDto : exhibitionTypeDtos) {
							if (exhibitionTypeDto.getName().equals(exhibitionType.getName())
									&& exhibitionTypeDto.getNumberMale() == exhibitionTypeDto.getNumberMale()
									&& exhibitionType.getNumberFemale() == exhibitionTypeDto.getNumberFemale()) {
								isExist = true;
								break;
							}
						}
						if (isExist == false) {
							exhibitionTypes.remove(exhibitionType);
						}
					}
					for (ExhibitionTypeDto exhibitionTypeDto : exhibitionTypeDtos) {
						boolean isExist = false;
						for (ExhibitionType exhibitionType : exhibitionTypes) {
							if (exhibitionTypeDto.getName().equals(exhibitionType.getName())
									&& exhibitionTypeDto.getNumberMale() == exhibitionTypeDto.getNumberMale()
									&& exhibitionType.getNumberFemale() == exhibitionTypeDto.getNumberFemale()) {
								isExist = true;
								break;
							}
						}
						if (isExist == false) {
							exhibitionTypes.add(convertExhibitionTypeDto(exhibitionTypeDto));
						}
					}
					tournament.setCompetitiveTypes(competitiveTypesNew);
					tournament.setExhibitionTypes(exhibitionTypes);
					tournament.setCompetitiveTypes(competitiveTypes);
					tournamentRepository.save(tournament);
					responseMessage.setData(Arrays.asList(tournament));
					responseMessage.setCode(200);
					responseMessage.setMessage(Constant.MSG_101);
				} else {
					responseMessage.setMessage("Đã qua giai đoạn chỉnh sửa giải đấu");
				}
			}
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	private CompetitiveType convertCompetitiveTypeDto(CompetitiveTypeDto competitiveTypeDto) {
		CompetitiveType competitiveType = new CompetitiveType();
		competitiveType.setId(competitiveTypeDto.getId());
		competitiveType.setWeightMin(competitiveTypeDto.getWeightMin());
		competitiveType.setWeightMax(competitiveTypeDto.getWeightMax());
		competitiveType.setGender(competitiveTypeDto.isGender());
		return competitiveType;
	}

	private ExhibitionType convertExhibitionTypeDto(ExhibitionTypeDto exhibitionTypeDto) {
		ExhibitionType exhibitionType = new ExhibitionType();
		exhibitionType.setId(exhibitionTypeDto.getId());
		exhibitionType.setName(exhibitionTypeDto.getName());
		exhibitionType.setNumberFemale(exhibitionTypeDto.getNumberFemale());
		exhibitionType.setNumberMale(exhibitionTypeDto.getNumberMale());
		return exhibitionType;
	}

	@Override
	public ResponseMessage deleteTournamentById(String studentId, int tournamentId) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<Tournament> tournamentOp = tournamentRepository.findById(tournamentId);
			if (tournamentOp.isPresent()) {
				Tournament tournament = tournamentOp.get();
				LocalDate startDate = getStartDate(tournamentId);
				if (startDate == null || LocalDate.now().isBefore(startDate)) {
					User user = userRepository.findByStudentId(studentId).get();

					List<TournamentSchedule> tournamentSchedules = tournamentScheduleRepository
							.findByTournamentId(tournamentId);
					if (!tournamentSchedules.isEmpty()) {
						for (TournamentSchedule tournamentSchedule : tournamentSchedules) {
							CommonSchedule commonSchedule = commonScheduleRepository
									.findByDate(tournamentSchedule.getDate()).get();
							commonScheduleRepository.delete(commonSchedule);
						}
						tournamentScheduleRepository.deleteAll(tournamentSchedules);
					}

					Set<CompetitiveType> competitiveTypes = tournament.getCompetitiveTypes();
					for (CompetitiveType competitiveType : competitiveTypes) {
						List<CompetitiveMatch> competitiveMatchs = competitiveMatchRepository
								.listMatchsByType(competitiveType.getId());
						for (CompetitiveMatch competitiveMatch : competitiveMatchs) {
							Optional<CompetitiveResult> competitiveResultOp = competitiveResultRepository
									.findResultByMatchId(competitiveMatch.getId());
							if (competitiveResultOp.isPresent()) {
								CompetitiveResult competitiveResult = competitiveResultOp.get();
								competitiveResultRepository.delete(competitiveResult);
							}
						}
					}

					Set<ExhibitionType> exhibitionTypes = tournament.getExhibitionTypes();
					for (ExhibitionType exhibitionType : exhibitionTypes) {
						Set<ExhibitionTeam> exhibitionTeams = exhibitionType.getExhibitionTeams();
						for (ExhibitionTeam exhibitionTeam : exhibitionTeams) {
							Optional<ExhibitionResult> exhibitionResultOp = exhibitionResultRepository
									.findByTeam(exhibitionTeam.getId());
							if (exhibitionResultOp.isPresent()) {
								ExhibitionResult exhibitionResult = exhibitionResultOp.get();
								exhibitionResultRepository.delete(exhibitionResult);
							}
						}
					}

					tournament.setStatus(false);
					tournament.setUpdatedBy(user.getName() + " - " + user.getStudentId());
					tournament.setUpdatedOn(LocalDateTime.now());
					tournamentRepository.save(tournament);

					notificationService.createTournamentDeleteNotification(tournament.getId(), tournament.getName());

					if (tournament.getTotalAmountFromClubEstimate() > 0) {
						clubFundService.depositToClubFund(user.getStudentId(),
								tournament.getTotalAmountFromClubEstimate(),
								("Hoàn tiền tổ chức giải đấu " + tournament.getName()));
					}

					responseMessage.setData(Arrays.asList(tournament));
					responseMessage.setMessage(Constant.MSG_102);
				} else {
					responseMessage.setMessage("Không thể xóa vì giải đấu đã bắt đầu");
				}
			} else {
				responseMessage.setMessage("Không có giải đấu này");
			}
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	public ResponseMessage getTournamentById(int tournamentId) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<Tournament> tournamentOp = tournamentRepository.findById(tournamentId);
			if (tournamentOp.isPresent()) {
				Tournament tournament = tournamentOp.get();
				if (tournament.isStatus()) {
					responseMessage.setData(Arrays.asList(tournament));
					responseMessage.setMessage(Constant.MSG_103);
				} else {
					responseMessage.setMessage("Giải đấu này đã hủy");
				}
			} else {
				responseMessage.setMessage("Không có giải đấu này");
			}
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage getAllTournamentBySemester(String semester, int status) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			if (semester == "") {
				semester = semesterRepository.findTop3Semester().get(0).getName();
			}
			List<Tournament> tournaments = tournamentRepository.findBySemester(semester);
			List<TournamentDto> tournamentDtos = new ArrayList<TournamentDto>();
			List<TournamentDto> listResult = new ArrayList<TournamentDto>();
			for (Tournament tournament : tournaments) {
				LocalDate startDate = getStartDate(tournament.getId());
				TournamentDto tournamentDto = new TournamentDto();
				if (startDate != null) {
					LocalDate endDate = getEndDate(tournament.getId());
					if (LocalDate.now().isBefore(startDate)) {
						tournamentDto.setStatus(3); // chua dien ra
					} else if (LocalDate.now().isAfter(endDate)) {
						tournamentDto.setStatus(1); // da ket thuc
					} else {
						tournamentDto.setStatus(2);// dang dien ra
					}
				} else {
					tournamentDto.setStatus(3);// chua dien ra
				}
				Set<CompetitiveTypeDto> competitiveTypeDtos = new HashSet<CompetitiveTypeDto>();
				Set<ExhibitionTypeDto> exhibitionTypeDtos = new HashSet<ExhibitionTypeDto>();
				for (CompetitiveType competitiveType : tournament.getCompetitiveTypes()) {
					CompetitiveTypeDto competitiveTypeDto = convertCompetitiveTypeToDto(competitiveType);
					competitiveTypeDtos.add(competitiveTypeDto);
				}
				for (ExhibitionType exhibitionType : tournament.getExhibitionTypes()) {
					ExhibitionTypeDto exhibitionTypeDto = convertExhibitionToDto(exhibitionType);
					exhibitionTypeDtos.add(exhibitionTypeDto);
				}
				tournamentDto.setCompetitiveTypesDto(competitiveTypeDtos);
				tournamentDto.setExhibitionTypesDto(exhibitionTypeDtos);
				tournamentDto.setFeeOrganizingCommiteePay(tournament.getFeeOrganizingCommiteePay());
				tournamentDto.setFeePlayerPay(tournament.getFeePlayerPay());
				tournamentDto.setStartDate(startDate);
				tournamentDto.setTotalAmount(tournament.getTotalAmount());
				tournamentDto.setName(tournament.getName());
				tournamentDto.setId(tournament.getId());
				tournamentDto.setTotalAmountEstimate(tournament.getTotalAmountEstimate());
				tournamentDto.setTotalAmountFromClubActual(tournament.getTotalAmountFromClubActual());
				tournamentDto.setTotalAmountFromClubEstimate(tournament.getTotalAmountFromClubEstimate());
				tournamentDto.setRegistrationPlayerDeadline(tournament.getRegistrationPlayerDeadline());
				tournamentDto.setRegistrationOrganizingCommitteeDeadline(
						tournament.getRegistrationOrganizingCommitteeDeadline());
				tournamentDtos.add(tournamentDto);
			}
			if (status == 0) {
				listResult.addAll(tournamentDtos);
			} else if (status == 1) {
				for (TournamentDto tournamentDto : tournamentDtos) {
					if (tournamentDto.getStatus() == 1) {
						listResult.add(tournamentDto);
					}
				}
			} else if (status == 2) {
				for (TournamentDto tournamentDto : tournamentDtos) {
					if (tournamentDto.getStatus() == 2) {
						listResult.add(tournamentDto);
					}
				}
			} else {
				for (TournamentDto tournamentDto : tournamentDtos) {
					if (tournamentDto.getStatus() == 3) {
						listResult.add(tournamentDto);
					}
				}
			}
			Collections.sort(listResult);
			responseMessage.setData(listResult);
			responseMessage.setMessage("Lấy danh sách giải đấu thành công" + semester);
			responseMessage.setTotalResult(listResult.size());

		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public LocalDate getStartDate(int tournamentId) {
		try {
			List<TournamentSchedule> listSchedule = tournamentScheduleRepository.findByTournamentId(tournamentId);
			if (listSchedule.size() > 0) {
				return listSchedule.get(0).getDate();
			}
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
		return null;
	}

	@Override
	public LocalDate getEndDate(int tournamentId) {
		List<TournamentSchedule> listSchedule = tournamentScheduleRepository.findByTournamentId(tournamentId);
		if (listSchedule.size() > 0) {
			return listSchedule.get(listSchedule.size() - 1).getDate();
		}
		return null;
	}

	public CompetitiveTypeDto convertCompetitiveTypeToDto(CompetitiveType competitiveType) {
		CompetitiveTypeDto competitiveTypeDto = new CompetitiveTypeDto();
		competitiveTypeDto.setId(competitiveType.getId());
		competitiveTypeDto.setGender(competitiveType.isGender());
		competitiveTypeDto.setWeightMin(competitiveType.getWeightMin());
		competitiveTypeDto.setWeightMax(competitiveType.getWeightMax());
		return competitiveTypeDto;
	}

	public ExhibitionTypeDto convertExhibitionToDto(ExhibitionType exhibitionType) {
		ExhibitionTypeDto exhibitionTypeDto = new ExhibitionTypeDto();
		exhibitionTypeDto.setId(exhibitionType.getId());
		exhibitionTypeDto.setName(exhibitionType.getName());
		exhibitionTypeDto.setNumberFemale(exhibitionType.getNumberFemale());
		exhibitionTypeDto.setNumberMale(exhibitionType.getNumberMale());
		return exhibitionTypeDto;
	}

	@Override
	public ResponseMessage getAllCompetitivePlayer(int tournamentId, double weightMin, double weightMax) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<Tournament> tournamentOp = tournamentRepository.findById(tournamentId);
			Tournament tournament = tournamentOp.get();
			Set<TournamentPlayer> tournamentPlayers = tournament.getTournamentPlayers();
			List<CompetitivePlayerDto> competitivePlayersDto = new ArrayList<CompetitivePlayerDto>();

			for (TournamentPlayer tournamentPlayer : tournamentPlayers) {
				Optional<CompetitivePlayer> competitivePlayerOp = competitivePlayerRepository
						.findByTournamentPlayerId(tournamentPlayer.getId());
				if (competitivePlayerOp.isPresent()) {
					CompetitivePlayer competitivePlayer = competitivePlayerOp.get();
					if (weightMin != 0 && weightMax != 0) {
						if (competitivePlayer.getWeight() <= weightMax && competitivePlayer.getWeight() >= weightMin) {
							CompetitivePlayerDto competitivePlayerDto = convertToCompetitivePlayerDto(
									competitivePlayer);
							competitivePlayersDto.add(competitivePlayerDto);
						}
					} else {
						CompetitivePlayerDto competitivePlayerDto = convertToCompetitivePlayerDto(competitivePlayer);
						competitivePlayersDto.add(competitivePlayerDto);
					}
				}
			}

			Set<CompetitiveType> competitiveTypes = tournament.getCompetitiveTypes();
			for (CompetitivePlayerDto competitivePlayerDto : competitivePlayersDto) {
				for (CompetitiveType competitiveType : competitiveTypes) {
					if (competitivePlayerDto.getWeight() >= competitiveType.getWeightMin()
							&& competitivePlayerDto.getWeight() <= competitiveType.getWeightMax()) {
						competitivePlayerDto.setWeightMax(competitiveType.getWeightMax());
						competitivePlayerDto.setWeightMin(competitiveType.getWeightMin());
						break;
					}
				}
			}

			responseMessage.setData(competitivePlayersDto);
			responseMessage.setMessage(Constant.MSG_114);
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	private CompetitivePlayerDto convertToCompetitivePlayerDto(CompetitivePlayer competitivePlayer) {
		CompetitivePlayerDto competitivePlayerDto = new CompetitivePlayerDto();
		competitivePlayerDto.setId(competitivePlayer.getId());
		competitivePlayerDto.setPlayerName(competitivePlayer.getTournamentPlayer().getUser().getName());
		competitivePlayerDto.setPlayerStudentId(competitivePlayer.getTournamentPlayer().getUser().getStudentId());
		competitivePlayerDto.setPlayerGender(competitivePlayer.getTournamentPlayer().getUser().isGender());
		competitivePlayerDto.setWeight(competitivePlayer.getWeight());
		return competitivePlayerDto;
	}

	@Override
	public ResponseMessage getAllExhibitionTeam(int tournamentId, int exhibitionTypeId) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<Tournament> tournamentOp = tournamentRepository.findById(tournamentId);
			Tournament tournament = tournamentOp.get();
			List<ExhibitionTeamDto> exhibitionTeamsDto = new ArrayList<ExhibitionTeamDto>();
			Set<ExhibitionType> exhibitionTypes = tournament.getExhibitionTypes();
			for (ExhibitionType exhibitionType : exhibitionTypes) {
				if (exhibitionTypeId == 0) {
					// fiter all
					Set<ExhibitionTeam> exhibitionTeams = exhibitionType.getExhibitionTeams();
					for (ExhibitionTeam exhibitionTeam : exhibitionTeams) {
						exhibitionTeamsDto.add(convertToExhibitionTeamDto(exhibitionTeam, exhibitionType.getName(),
								exhibitionType.getId()));
					}
				} else {
					// filter theo hạng mục thi đấu
					if (exhibitionType.getId() == exhibitionTypeId) {
						Set<ExhibitionTeam> exhibitionTeams = exhibitionType.getExhibitionTeams();
						for (ExhibitionTeam exhibitionTeam : exhibitionTeams) {
							exhibitionTeamsDto.add(convertToExhibitionTeamDto(exhibitionTeam, exhibitionType.getName(),
									exhibitionType.getId()));
						}
						break;
					}
				}
			}

			for (ExhibitionTeamDto exhibitionTeamDto : exhibitionTeamsDto) {
				Set<ExhibitionPlayerDto> exhibitionPlayersDto = exhibitionTeamDto.getExhibitionPlayersDto();
				for (ExhibitionPlayerDto exhibitionPlayerDto : exhibitionPlayersDto) {
					Set<TournamentPlayer> tournamentPlayers = tournament.getTournamentPlayers();
					for (TournamentPlayer tournamentPlayer : tournamentPlayers) {
						if (tournamentPlayer.getId() == exhibitionPlayerDto.getPlayerId()) {
							exhibitionPlayerDto.setPlayerName(tournamentPlayer.getUser().getName());
							exhibitionPlayerDto.setPlayerStudentId(tournamentPlayer.getUser().getStudentId());
							exhibitionPlayerDto.setPlayerGender(tournamentPlayer.getUser().isGender());
							break;
						}
					}
				}
			}

			responseMessage.setData(exhibitionTeamsDto);
			responseMessage.setMessage(Constant.MSG_115);
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	private ExhibitionTeamDto convertToExhibitionTeamDto(ExhibitionTeam exhibitionTeam, String exhibitionTypeName,
			int exhibitionTypeId) {
		ExhibitionTeamDto exhibitionTeamDto = new ExhibitionTeamDto();
		exhibitionTeamDto.setId(exhibitionTeam.getId());
		exhibitionTeamDto.setTeamName(exhibitionTeam.getTeamName());
		Set<ExhibitionPlayer> exhibitionPlayers = exhibitionTeam.getExhibitionPlayers();
		Set<ExhibitionPlayerDto> exhibitionPlayersDto = new HashSet<ExhibitionPlayerDto>();
		for (ExhibitionPlayer exhibitionPlayer : exhibitionPlayers) {
			exhibitionPlayersDto.add(convertToExhibitionPlayerDto(exhibitionPlayer));
		}
		exhibitionTeamDto.setExhibitionPlayersDto(exhibitionPlayersDto);
		exhibitionTeamDto.setExhibitionTypeName(exhibitionTypeName);
		exhibitionTeamDto.setExhibitionTypeId(exhibitionTypeId);
		return exhibitionTeamDto;
	}

	private ExhibitionPlayerDto convertToExhibitionPlayerDto(ExhibitionPlayer exhibitionPlayer) {
		ExhibitionPlayerDto exhibitionPlayerDto = new ExhibitionPlayerDto();
		exhibitionPlayerDto.setId(exhibitionPlayer.getId());
		exhibitionPlayerDto.setPlayerId(exhibitionPlayer.getTournamentPlayer().getId());
		exhibitionPlayerDto.setPlayerName(exhibitionPlayer.getTournamentPlayer().getUser().getName());
		exhibitionPlayerDto.setPlayerStudentId(exhibitionPlayer.getTournamentPlayer().getUser().getStudentId());
		exhibitionPlayerDto.setPlayerGender(exhibitionPlayer.getTournamentPlayer().getUser().isGender());
		exhibitionPlayerDto.setRoleInTeam(exhibitionPlayer.isRoleInTeam());
		return exhibitionPlayerDto;
	}

	@Override
	public ResponseMessage getAllOrganizingCommitteeRoleByTournamentId(int tournamentId) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<TournamentRole> tournamentRoles = tournamentRoleRepository.findByTournamentId(tournamentId);
			if (!tournamentRoles.isEmpty()) {
				List<RoleEventDto> rolesEventDto = new ArrayList<RoleEventDto>();
				for (TournamentRole tournamentRole : tournamentRoles) {
					if (tournamentRole.getRoleEvent().getId() != 1) {
						RoleEventDto roleEventDto = new RoleEventDto();
						roleEventDto.setId(tournamentRole.getRoleEvent().getId());
						roleEventDto.setName(tournamentRole.getRoleEvent().getName());
						roleEventDto.setAvailableQuantity(getAvailableQuantity(tournamentId, tournamentRole));
						roleEventDto.setMaxQuantity(tournamentRole.getQuantity());
						rolesEventDto.add(roleEventDto);
					}
				}
				responseMessage.setData(rolesEventDto);
				responseMessage.setMessage(Constant.MSG_116);
			} else {
				responseMessage.setMessage("Giải đấu này chưa có vai trò ban tổ chức nào");
			}
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	private int getAvailableQuantity(int tournamentId, TournamentRole tournamentRole) {
		List<TournamentOrganizingCommittee> organizingCommittees = tournamentOrganizingCommitteeRepository
				.findByTournamentIdAndRoleInTournament(tournamentId, tournamentRole.getRoleEvent().getId());
		if (!organizingCommittees.isEmpty()) {
			if (tournamentRole.getQuantity() - organizingCommittees.size() < 0) {
				return 0;
			} else {
				return tournamentRole.getQuantity() - organizingCommittees.size();
			}
		} else {
			return tournamentRole.getQuantity();
		}
	}

	@Override
	public ResponseMessage getAllExhibitionType(int tournamentId) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<Tournament> tournamentOp = tournamentRepository.findById(tournamentId);
			Tournament tournament = tournamentOp.get();
			Set<ExhibitionType> exhibitionTypes = tournament.getExhibitionTypes();
			List<ExhibitionTypeDto> exhibitionTypesDto = new ArrayList<ExhibitionTypeDto>();
			for (ExhibitionType exhibitionType : exhibitionTypes) {
				ExhibitionTypeDto exhibitionTypeDto = new ExhibitionTypeDto();
				exhibitionTypeDto.setId(exhibitionType.getId());
				exhibitionTypeDto.setName(exhibitionType.getName());
				exhibitionTypeDto.setNumberFemale(exhibitionType.getNumberFemale());
				exhibitionTypeDto.setNumberMale(exhibitionType.getNumberMale());
				exhibitionTypesDto.add(exhibitionTypeDto);
			}
			responseMessage.setData(exhibitionTypesDto);
			responseMessage.setMessage(Constant.MSG_117);
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage getAllTournamentPlayerPaymentStatus(int tournamentId) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<Tournament> tournamentOp = tournamentRepository.findById(tournamentId);
			if (tournamentOp.isPresent()) {
				Tournament tournament = tournamentOp.get();
				if (tournament.getFeePlayerPay() > 0) {
					Set<TournamentPlayer> tournamentPlayers = tournament.getTournamentPlayers();
					List<TournamentPlayerDto> tournamentPlayersDto = new ArrayList<TournamentPlayerDto>();
					for (TournamentPlayer tournamentPlayer : tournamentPlayers) {
						TournamentPlayerDto tournamentPlayerDto = convertTournamentPlayer(tournamentPlayer);
						tournamentPlayersDto.add(tournamentPlayerDto);
					}
					if (!tournamentPlayersDto.isEmpty()) {
						Collections.sort(tournamentPlayersDto);
						responseMessage.setData(tournamentPlayersDto);
						responseMessage.setMessage(Constant.MSG_121);
					} else {
						responseMessage.setMessage("Chưa có danh sách đóng tiền của người chơi");
					}
				} else {
					responseMessage.setMessage("Giải đấu không yêu cầu người chơi đóng phí tham gia");
				}
			} else {
				responseMessage.setMessage("Không có giải đấu này");
			}
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	private TournamentPlayerDto convertTournamentPlayer(TournamentPlayer tournamentPlayer) {
		TournamentPlayerDto tournamentPlayerDto = new TournamentPlayerDto();
		tournamentPlayerDto.setId(tournamentPlayer.getId());
		tournamentPlayerDto.setUserName(tournamentPlayer.getUser().getName());
		tournamentPlayerDto.setUserStudentId(tournamentPlayer.getUser().getStudentId());
		tournamentPlayerDto.setPaymentStatus(tournamentPlayer.isPaymentStatus());
		return tournamentPlayerDto;
	}

	@Override
	public ResponseMessage getAllTournamentOrganizingCommitteePaymentStatus(int tournamentId) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<Tournament> tournamentOp = tournamentRepository.findById(tournamentId);
			if (tournamentOp.isPresent()) {
				Tournament tournament = tournamentOp.get();
				if (tournament.getFeeOrganizingCommiteePay() > 0) {
					List<TournamentOrganizingCommittee> tournamentOrganizingCommittees = tournamentOrganizingCommitteeRepository
							.findByTournamentId(tournamentId);
					if (!tournamentOrganizingCommittees.isEmpty()) {
						List<TournamentOrganizingCommitteeDto> tournamentOrganizingCommitteesDto = new ArrayList<TournamentOrganizingCommitteeDto>();
						for (TournamentOrganizingCommittee tournamentOrganizingCommittee : tournamentOrganizingCommittees) {
							TournamentOrganizingCommitteeDto tournamentOrganizingCommitteeDto = convertToTournamentOrganizingCommitteeDto(
									tournamentOrganizingCommittee);
							tournamentOrganizingCommitteesDto.add(tournamentOrganizingCommitteeDto);
						}
						Collections.sort(tournamentOrganizingCommitteesDto);
						responseMessage.setData(tournamentOrganizingCommitteesDto);
						responseMessage.setMessage(Constant.MSG_122);
					} else {
						responseMessage.setMessage("Chưa có danh sách đóng tiền của thành viên ban tổ chức giải đấu");
					}
				} else {
					responseMessage.setMessage("Giải đấu không yêu cầu ban tổ chức đóng phí tham gia");
				}
			} else {
				responseMessage.setMessage("Không có giải đấu này");
			}
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage updateTournamentOrganizingCommitteePaymentStatus(String studentId,
			int tournamentOrganizingCommitteeId) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			User user = userRepository.findByStudentId(studentId).get();

			Optional<TournamentOrganizingCommittee> tournamentOrganizingCommitteeOp = tournamentOrganizingCommitteeRepository
					.findById(tournamentOrganizingCommitteeId);
			TournamentOrganizingCommittee tournamentOrganizingCommittee = tournamentOrganizingCommitteeOp.get();

			Tournament tournament = tournamentRepository.findById(tournamentOrganizingCommittee.getTournament().getId())
					.get();

			List<ClubFund> clubFunds = clubFundRepository.findAll();
			ClubFund clubFund = clubFunds.get(0);
			double fundAmount = clubFund.getFundAmount();

			double tournamentFee = tournament.getFeeOrganizingCommiteePay();

			double fundBalance = tournamentOrganizingCommittee.isPaymentStatus() ? (fundAmount - tournamentFee)
					: (fundAmount + tournamentFee);

			if (tournamentOrganizingCommittee.isPaymentStatus()) {
				clubFundService.withdrawFromClubFund(user.getStudentId(), tournamentFee,
						"Cập nhật trạng thái đóng phí tham gia giải đấu " + tournament.getName() + " của "
								+ tournamentOrganizingCommittee.getUser().getName() + " - "
								+ tournamentOrganizingCommittee.getUser().getStudentId() + " thành chưa đóng");
			} else {
				clubFundService.depositToClubFund(user.getStudentId(), tournamentFee,
						"Cập nhật trạng thái đóng phí tham gia giải đấu " + tournament.getName() + " của "
								+ tournamentOrganizingCommittee.getUser().getName() + " - "
								+ tournamentOrganizingCommittee.getUser().getStudentId() + " thành đã đóng");
			}

			TournamentOrganizingCommitteePaymentStatusReport tournamentOrganizingCommitteePaymentStatusReport = new TournamentOrganizingCommitteePaymentStatusReport();
			tournamentOrganizingCommitteePaymentStatusReport
					.setTournament(tournamentOrganizingCommittee.getTournament());
			tournamentOrganizingCommitteePaymentStatusReport.setUser(tournamentOrganizingCommittee.getUser());
			tournamentOrganizingCommitteePaymentStatusReport
					.setPaymentStatus(!tournamentOrganizingCommittee.isPaymentStatus());
			tournamentOrganizingCommitteePaymentStatusReport
					.setFundChange(tournamentOrganizingCommittee.isPaymentStatus() ? -tournamentFee : tournamentFee);
			tournamentOrganizingCommitteePaymentStatusReport.setFundBalance(fundBalance);
			tournamentOrganizingCommitteePaymentStatusReport.setCreatedBy(user.getName() + " - " + user.getStudentId());
			tournamentOrganizingCommitteePaymentStatusReport.setCreatedOn(LocalDateTime.now());
			tournamentOrganizingCommitteePaymentStatusReportRepository
					.save(tournamentOrganizingCommitteePaymentStatusReport);

			tournamentOrganizingCommittee.setPaymentStatus(!tournamentOrganizingCommittee.isPaymentStatus());
			tournamentOrganizingCommittee.setUpdatedBy(user.getName() + " - " + user.getStudentId());
			tournamentOrganizingCommittee.setUpdatedOn(LocalDateTime.now());
			tournamentOrganizingCommitteeRepository.save(tournamentOrganizingCommittee);

			TournamentOrganizingCommitteeDto tournamentOrganizingCommitteeDto = convertToTournamentOrganizingCommitteeDto(
					tournamentOrganizingCommittee);

			responseMessage.setData(Arrays.asList(tournamentOrganizingCommitteeDto));
			responseMessage.setMessage(Constant.MSG_123);
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage getAllTournamentOrganizingCommitteePaymentStatusReport(int tournamentId) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<TournamentOrganizingCommitteePaymentStatusReport> tournamentOrganizingCommitteePaymentStatusReports = tournamentOrganizingCommitteePaymentStatusReportRepository
					.findByTournamentId(tournamentId);
			List<TournamentOrganizingCommitteePaymentStatusReportDto> tournamentOrganizingCommitteePaymentStatusReportsDto = new ArrayList<TournamentOrganizingCommitteePaymentStatusReportDto>();
			for (TournamentOrganizingCommitteePaymentStatusReport tournamentOrganizingCommitteePaymentStatusReport : tournamentOrganizingCommitteePaymentStatusReports) {
				TournamentOrganizingCommitteePaymentStatusReportDto tournamentOrganizingCommitteePaymentStatusReportDto = new TournamentOrganizingCommitteePaymentStatusReportDto();
				tournamentOrganizingCommitteePaymentStatusReportDto
						.setId(tournamentOrganizingCommitteePaymentStatusReport.getId());
				tournamentOrganizingCommitteePaymentStatusReportDto
						.setTournamentId(tournamentOrganizingCommitteePaymentStatusReport.getTournament().getId());
				tournamentOrganizingCommitteePaymentStatusReportDto
						.setUserName(tournamentOrganizingCommitteePaymentStatusReport.getUser().getName());
				tournamentOrganizingCommitteePaymentStatusReportDto
						.setUserStudentId(tournamentOrganizingCommitteePaymentStatusReport.getUser().getStudentId());
				tournamentOrganizingCommitteePaymentStatusReportDto
						.setPaymentStatus(tournamentOrganizingCommitteePaymentStatusReport.isPaymentStatus());
				tournamentOrganizingCommitteePaymentStatusReportDto
						.setFundChange(tournamentOrganizingCommitteePaymentStatusReport.getFundChange());
				tournamentOrganizingCommitteePaymentStatusReportDto
						.setFundBalance(tournamentOrganizingCommitteePaymentStatusReport.getFundBalance());
				tournamentOrganizingCommitteePaymentStatusReportDto
						.setCreatedBy(tournamentOrganizingCommitteePaymentStatusReport.getCreatedBy());
				tournamentOrganizingCommitteePaymentStatusReportDto
						.setCreatedOn(tournamentOrganizingCommitteePaymentStatusReport.getCreatedOn());
				tournamentOrganizingCommitteePaymentStatusReportsDto
						.add(tournamentOrganizingCommitteePaymentStatusReportDto);
			}
			Collections.sort(tournamentOrganizingCommitteePaymentStatusReportsDto);
			responseMessage.setData(tournamentOrganizingCommitteePaymentStatusReportsDto);
			responseMessage.setMessage(Constant.MSG_124);
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage updateTournamentPlayerPaymentStatus(String studentId, int tournamentPlayerId) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			User user = userRepository.findByStudentId(studentId).get();

			Optional<TournamentPlayer> tournamentPlayerOp = tournamentPlayerRepository.findById(tournamentPlayerId);
			TournamentPlayer tournamentPlayer = tournamentPlayerOp.get();

			Tournament tournament = tournamentRepository.findByTournamentPlayers(tournamentPlayer).get();

			List<ClubFund> clubFunds = clubFundRepository.findAll();
			ClubFund clubFund = clubFunds.get(0);
			double fundAmount = clubFund.getFundAmount();

			double tournamentFee = tournament.getFeePlayerPay();

			double fundBalance = tournamentPlayer.isPaymentStatus() ? (fundAmount - tournamentFee)
					: (fundAmount + tournamentFee);

			if (tournamentPlayer.isPaymentStatus()) {
				clubFundService.withdrawFromClubFund(user.getStudentId(), tournamentFee,
						"Cập nhật trạng thái đóng phí tham gia giải đấu " + tournament.getName() + " của "
								+ tournamentPlayer.getUser().getName() + " - "
								+ tournamentPlayer.getUser().getStudentId() + " thành chưa đóng");
			} else {
				clubFundService.depositToClubFund(user.getStudentId(), tournamentFee,
						"Cập nhật trạng thái đóng phí tham gia giải đấu " + tournament.getName() + " của "
								+ tournamentPlayer.getUser().getName() + " - "
								+ tournamentPlayer.getUser().getStudentId() + " thành đã đóng");
			}

			TournamentPlayerPaymentStatusReport tournamentPlayerPaymentStatusReport = new TournamentPlayerPaymentStatusReport();
			tournamentPlayerPaymentStatusReport.setTournament(tournament);
			tournamentPlayerPaymentStatusReport.setUser(tournamentPlayer.getUser());
			tournamentPlayerPaymentStatusReport.setPaymentStatus(!tournamentPlayer.isPaymentStatus());
			tournamentPlayerPaymentStatusReport
					.setFundChange(tournamentPlayer.isPaymentStatus() ? -tournamentFee : tournamentFee);
			tournamentPlayerPaymentStatusReport.setFundBalance(fundBalance);
			tournamentPlayerPaymentStatusReport.setCreatedBy(user.getName() + " - " + user.getStudentId());
			tournamentPlayerPaymentStatusReport.setCreatedOn(LocalDateTime.now());
			tournamentPlayerPaymentStatusReportRepository.save(tournamentPlayerPaymentStatusReport);

			tournamentPlayer.setPaymentStatus(!tournamentPlayer.isPaymentStatus());
			tournamentPlayer.setUpdatedBy(user.getName() + " - " + user.getStudentId());
			tournamentPlayer.setUpdatedOn(LocalDateTime.now());
			tournamentPlayerRepository.save(tournamentPlayer);

			TournamentPlayerDto tournamentPlayerDto = convertTournamentPlayer(tournamentPlayer);

			responseMessage.setData(Arrays.asList(tournamentPlayerDto));
			responseMessage.setMessage(Constant.MSG_125);
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage getAllTournamentPlayerPaymentStatusReport(int tournamentId) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<TournamentPlayerPaymentStatusReport> tournamentPlayerPaymentStatusReports = tournamentPlayerPaymentStatusReportRepository
					.findByTournamentId(tournamentId);
			List<TournamentPlayerPaymentStatusReportDto> tournamentPlayerPaymentStatusReportsDto = new ArrayList<TournamentPlayerPaymentStatusReportDto>();
			for (TournamentPlayerPaymentStatusReport tournamentPlayerPaymentStatusReport : tournamentPlayerPaymentStatusReports) {
				TournamentPlayerPaymentStatusReportDto tournamentPlayerPaymentStatusReportDto = new TournamentPlayerPaymentStatusReportDto();
				tournamentPlayerPaymentStatusReportDto.setId(tournamentPlayerPaymentStatusReport.getId());
				tournamentPlayerPaymentStatusReportDto
						.setTournamentId(tournamentPlayerPaymentStatusReport.getTournament().getId());
				tournamentPlayerPaymentStatusReportDto
						.setUserName(tournamentPlayerPaymentStatusReport.getUser().getName());
				tournamentPlayerPaymentStatusReportDto
						.setUserStudentId(tournamentPlayerPaymentStatusReport.getUser().getStudentId());
				tournamentPlayerPaymentStatusReportDto
						.setPaymentStatus(tournamentPlayerPaymentStatusReport.isPaymentStatus());
				tournamentPlayerPaymentStatusReportDto
						.setFundChange(tournamentPlayerPaymentStatusReport.getFundChange());
				tournamentPlayerPaymentStatusReportDto
						.setFundBalance(tournamentPlayerPaymentStatusReport.getFundBalance());
				tournamentPlayerPaymentStatusReportDto.setCreatedBy(tournamentPlayerPaymentStatusReport.getCreatedBy());
				tournamentPlayerPaymentStatusReportDto.setCreatedOn(tournamentPlayerPaymentStatusReport.getCreatedOn());
				tournamentPlayerPaymentStatusReportsDto.add(tournamentPlayerPaymentStatusReportDto);
			}
			Collections.sort(tournamentPlayerPaymentStatusReportsDto);
			responseMessage.setData(tournamentPlayerPaymentStatusReportsDto);
			responseMessage.setMessage(Constant.MSG_126);
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage getAllCompetitivePlayerByType(int tournamentId, int competitiveTypeId) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<Tournament> tournamentOp = tournamentRepository.findById(tournamentId);
			Tournament tournament = tournamentOp.get();
			Set<TournamentPlayer> tournamentPlayers = tournament.getTournamentPlayers();
			List<CompetitivePlayerDto> competitivePlayersDto = new ArrayList<CompetitivePlayerDto>();
			Optional<CompetitiveType> competitiveTypeOp = competitiveTypeRepository.findById(competitiveTypeId);
			CompetitiveType getCompetitiveType = competitiveTypeOp.get();
			double weightMin = getCompetitiveType.getWeightMin();
			double weightMax = getCompetitiveType.getWeightMax();
			boolean gender = getCompetitiveType.isGender();
			for (TournamentPlayer tournamentPlayer : tournamentPlayers) {
				Optional<CompetitivePlayer> competitivePlayerOp = competitivePlayerRepository
						.findByTournamentPlayerId(tournamentPlayer.getId());
				if (competitivePlayerOp.isPresent()) {
					CompetitivePlayer competitivePlayer = competitivePlayerOp.get();
					if (competitivePlayer.getWeight() <= weightMax && competitivePlayer.getWeight() >= weightMin
							&& competitivePlayer.getTournamentPlayer().getUser().isGender() == gender) {
						CompetitivePlayerDto competitivePlayerDto = convertToCompetitivePlayerDto(competitivePlayer);
						competitivePlayersDto.add(competitivePlayerDto);
					}
				}
			}

			Set<CompetitiveType> competitiveTypes = tournament.getCompetitiveTypes();
			for (CompetitivePlayerDto competitivePlayerDto : competitivePlayersDto) {
				for (CompetitiveType competitiveType : competitiveTypes) {
					if (competitivePlayerDto.getWeight() >= competitiveType.getWeightMin()
							&& competitivePlayerDto.getWeight() <= competitiveType.getWeightMax()) {
						competitivePlayerDto.setWeightMax(competitiveType.getWeightMax());
						competitivePlayerDto.setWeightMin(competitiveType.getWeightMin());
						break;
					}
				}
			}
			responseMessage.setData(competitivePlayersDto);
			responseMessage.setMessage(Constant.MSG_114);
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage registerToJoinTournamentOrganizingComittee(int tournamentId, String studentId, int roleId) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Tournament tournament = tournamentRepository.findById(tournamentId).get();
			if (LocalDateTime.now().isBefore(tournament.getRegistrationOrganizingCommitteeDeadline())) {
				User user = userRepository.findByStudentId(studentId).get();
				RoleEvent roleEvent = roleEventRepository.findById(roleId).get();
				TournamentRole tournamentRole = tournamentRoleRepository
						.findByRoleEventIdAndTournamentId(roleEvent.getId(), tournament.getId()).get();

				Optional<TournamentOrganizingCommittee> tournamentOrganizingCommitteeOp = tournamentOrganizingCommitteeRepository
						.findByTournamentIdAndUserId(tournament.getId(), user.getId());
				if (tournamentOrganizingCommitteeOp.isPresent()) {
					responseMessage.setMessage("Bạn đã tham gia ban tổ chức giải đấu rồi");
					return responseMessage;
				}

				Optional<TournamentPlayer> tournamentPlayerOp = tournamentPlayerRepository
						.getPlayerByUserIdAndTournamentId(user.getId(), tournament.getId());
				if (tournamentPlayerOp.isPresent()) {
					responseMessage.setMessage("Bạn đã đăng ký tham gia giải đấu rồi");
					return responseMessage;
				}

				if (roleEvent.getId() != 1) {
					if (getAvailableQuantity(tournament.getId(), tournamentRole) > 0) {
						TournamentOrganizingCommittee tournamentOrganizingCommittee = new TournamentOrganizingCommittee();
						tournamentOrganizingCommittee.setTournament(tournament);
						tournamentOrganizingCommittee.setUser(user);
						tournamentOrganizingCommittee.setRoleEvent(roleEvent);
						tournamentOrganizingCommittee.setPaymentStatus(false);
						tournamentOrganizingCommittee.setCreatedBy(user.getName() + " - " + user.getStudentId());
						tournamentOrganizingCommittee.setCreatedOn(LocalDateTime.now());
						tournamentOrganizingCommitteeRepository.save(tournamentOrganizingCommittee);
						responseMessage.setData(Arrays.asList(tournamentOrganizingCommittee));
						responseMessage.setMessage("Đăng ký thành công");
					} else {
						responseMessage.setMessage("Đã đủ số lượng ban tổ chức");
					}
				} else {
					responseMessage.setMessage("Vai trò không hợp lệ");
				}
			} else {
				responseMessage.setMessage(Constant.MSG_131);
			}

		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage registerToJoinTournamentCompetitiveType(int tournamentId, String studentId, double weight,
			int competitiveTypeId) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Tournament tournament = tournamentRepository.findById(tournamentId).get();

			if (LocalDateTime.now().isBefore(tournament.getRegistrationPlayerDeadline())) {
				User user = userRepository.findByStudentId(studentId).get();

				Optional<TournamentOrganizingCommittee> tournamentOrganizingCommitteeOp = tournamentOrganizingCommitteeRepository
						.findByTournamentIdAndUserId(tournament.getId(), user.getId());
				if (tournamentOrganizingCommitteeOp.isPresent()) {
					responseMessage.setMessage("Bạn đã tham gia ban tổ chức giải đấu rồi");
					return responseMessage;
				}

				Optional<CompetitiveType> competitveTypeOp = competitiveTypeRepository.findById(competitiveTypeId);
				CompetitiveType competitiveType = new CompetitiveType();
				if (competitveTypeOp.isPresent()) {
					competitiveType = competitveTypeOp.get();
					if (user.isGender() != competitiveType.isGender()) {
						responseMessage.setMessage("Giới tính của bạn không phù hợp cho hạng cân này");
						return responseMessage;
					}
					if (weight < competitiveType.getWeightMin() || weight > competitiveType.getWeightMax()) {
						responseMessage.setMessage("Cân nặng của bạn không phù hợp cho hạng cân này");
						return responseMessage;
					}
				} else {
					responseMessage.setMessage("Không có hạng cân này");
					return responseMessage;
				}

				Optional<TournamentPlayer> tournamentPlayerOp = tournamentPlayerRepository
						.getPlayerByUserIdAndTournamentId(user.getId(), tournament.getId());

				if (!tournamentPlayerOp.isPresent()) {
					createTournamentPlayer(tournament, user);
					tournamentPlayerOp = tournamentPlayerRepository.findPlayerByUserIdAndTournamentId(user.getId(),
							tournament.getId());
				}

				TournamentPlayer tournamentPlayer = tournamentPlayerOp.get();
				Optional<CompetitivePlayer> competitivePlayerOp = competitivePlayerRepository
						.findByTournamentPlayerId(tournamentPlayer.getId());
				if (!competitivePlayerOp.isPresent()) {
					CompetitivePlayer competitivePlayer = new CompetitivePlayer();
					competitivePlayer.setTournamentPlayer(tournamentPlayer);
					competitivePlayer.setWeight(weight);
					competitivePlayer.setCompetitiveType(competitiveType);
					competitivePlayer.setIsEligible(true);
					competitivePlayer.setCreatedBy(user.getName() + " - " + user.getStudentId());
					competitivePlayer.setCreatedOn(LocalDateTime.now());
					competitivePlayerRepository.save(competitivePlayer);
					competitiveType.setCanDelete(false);
					competitiveTypeRepository.save(competitiveType);
					responseMessage.setData(Arrays.asList(competitivePlayer));
					responseMessage.setMessage("Đăng ký thành công");
					competitiveService.autoSpawnMatchs(competitiveTypeId);
				} else {
					responseMessage.setMessage("Bạn đã đăng ký vào giải này rồi");
				}
			} else {
				responseMessage.setMessage(Constant.MSG_131);
			}
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	private void createTournamentPlayer(Tournament tournament, User user) {
		Set<TournamentPlayer> tournamentPlayers = tournament.getTournamentPlayers();
		TournamentPlayer tournamentPlayer = new TournamentPlayer();
		tournamentPlayer.setUser(user);
		tournamentPlayer.setPaymentStatus(false);
		tournamentPlayer.setCreatedBy(user.getName() + " - " + user.getStudentId());
		tournamentPlayer.setCreatedOn(LocalDateTime.now());
		tournamentPlayers.add(tournamentPlayer);
		tournament.setTournamentPlayers(tournamentPlayers);
		tournamentRepository.save(tournament);
	}

	public ResponseMessage registerToJoinTournamentExhibitionType(int tournamentId, String studentId,
			int exhibitionTypeId, String teamName, List<ActiveUserDto> activeUsersDto) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Tournament tournament = tournamentRepository.findById(tournamentId).get();
			if (LocalDateTime.now().isBefore(tournament.getRegistrationPlayerDeadline())) {
				User user = userRepository.findByStudentId(studentId).get();
				ExhibitionType exhibitionType = exhibitionTypeRepository.findById(exhibitionTypeId).get();

				int countMale = 0;
				int countFemale = 0;

				List<User> members = new ArrayList<User>();

				for (ActiveUserDto activeUserDto : activeUsersDto) {
					User member = userRepository.findByStudentId(activeUserDto.getStudentId()).get();
					members.add(member);
					Optional<TournamentPlayer> tournamentPlayerOp = tournamentPlayerRepository
							.getPlayerByUserIdAndTournamentId(member.getId(), tournament.getId());
					if (tournamentPlayerOp.isPresent()) {
						TournamentPlayer tournamentPlayer = tournamentPlayerOp.get();
						Optional<ExhibitionPlayer> exhibitionPlayerOp = exhibitionPlayerRepository
								.findByTournamentPlayerAndType(tournamentPlayer.getId(), exhibitionType.getId());
						if (exhibitionPlayerOp.isPresent()) {
							responseMessage.setMessage("Thành viên " + member.getName() + " - " + member.getStudentId()
									+ " đã đăng ký nội dung này");
							return responseMessage;
						}
					}
					if (member.isGender()) {
						countMale++;
					} else {
						countFemale++;
					}
				}

				if (countMale != exhibitionType.getNumberMale() || countFemale != exhibitionType.getNumberFemale()) {
					responseMessage.setMessage("Số lượng thành viên không hợp lệ");
					return responseMessage;
				}

				Set<ExhibitionPlayer> exhibitionPlayers = new HashSet<ExhibitionPlayer>();
				for (User member : members) {
					Optional<TournamentPlayer> tournamentPlayerOp = tournamentPlayerRepository
							.findPlayerByUserIdAndTournamentId(member.getId(), tournament.getId());
					if (!tournamentPlayerOp.isPresent()) {
						createTournamentPlayer(tournament, member);
						tournamentPlayerOp = tournamentPlayerRepository.getPlayerByUserIdAndTournamentId(member.getId(),
								tournament.getId());
					}
					TournamentPlayer tournamentPlayer = tournamentPlayerOp.get();
					ExhibitionPlayer exhibitionPlayer = new ExhibitionPlayer();
					exhibitionPlayer.setTournamentPlayer(tournamentPlayer);
					if (member.getStudentId().equals(studentId)) {
						exhibitionPlayer.setRoleInTeam(true);
					} else {
						exhibitionPlayer.setRoleInTeam(false);
					}
					exhibitionPlayer.setCreatedBy(user.getName() + " - " + user.getStudentId());
					exhibitionPlayer.setCreatedOn(LocalDateTime.now());
					exhibitionPlayers.add(exhibitionPlayer);
				}

				ExhibitionTeam exhibitionTeam = new ExhibitionTeam();
				exhibitionTeam.setTeamName(teamName);
				exhibitionTeam.setExhibitionPlayers(exhibitionPlayers);
				exhibitionTeam.setCreatedBy(user.getName() + " - " + user.getStudentId());
				exhibitionTeam.setCreatedOn(LocalDateTime.now());
				ExhibitionTeamDto exhibitionTeamDto = convertToExhibitionTeamDto(exhibitionTeam,
						exhibitionType.getName(), exhibitionType.getId());

				Set<ExhibitionType> exhibitionTypes = tournament.getExhibitionTypes();
				for (ExhibitionType oldExhibitionType : exhibitionTypes) {
					if (oldExhibitionType.getId() == exhibitionType.getId()) {
						Set<ExhibitionTeam> exhibitionTeams = oldExhibitionType.getExhibitionTeams();
						exhibitionTeams.add(exhibitionTeam);
					}
				}

				tournament.setExhibitionTypes(exhibitionTypes);
				tournamentRepository.save(tournament);
				responseMessage.setData(Arrays.asList(exhibitionTeamDto));
				responseMessage.setMessage("Đăng ký thành công");
			} else {
				responseMessage.setMessage(Constant.MSG_131);
			}
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage getAllUserCompetitivePlayer(int tournamentId, String studentId) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Tournament tournament = tournamentRepository.findById(tournamentId).get();
			User user = userRepository.findByStudentId(studentId).get();
			Optional<TournamentPlayer> tournamentPlayerOp = tournamentPlayerRepository
					.getPlayerByUserIdAndTournamentId(user.getId(), tournament.getId());
			if (tournamentPlayerOp.isPresent()) {
				TournamentPlayer tournamentPlayer = tournamentPlayerOp.get();
				Optional<CompetitivePlayer> competitivePlayerOp = competitivePlayerRepository
						.findByTournamentPlayerId(tournamentPlayer.getId());
				if (competitivePlayerOp.isPresent()) {
					List<CompetitivePlayerDto> competitivePlayersDto = new ArrayList<CompetitivePlayerDto>();
					CompetitivePlayer getCompetitivePlayer = competitivePlayerOp.get();
					CompetitiveType competitiveType = getCompetitivePlayer.getCompetitiveType();
					List<CompetitivePlayer> competitivePlayers = competitivePlayerRepository
							.findByCompetitiveTypeId(competitiveType.getId());
					for (CompetitivePlayer competitivePlayer : competitivePlayers) {
						CompetitivePlayerDto competitivePlayerDto = convertToCompetitivePlayerDto(competitivePlayer);
						competitivePlayerDto.setWeightMin(competitiveType.getWeightMin());
						competitivePlayerDto.setWeightMax(competitiveType.getWeightMax());
						competitivePlayerDto.setCompetitiveTypeId(competitiveType.getId());
						competitivePlayersDto.add(competitivePlayerDto);
					}
					Collections.sort(competitivePlayersDto);
					responseMessage.setData(competitivePlayersDto);
					responseMessage.setMessage("Lấy danh sách người chơi tham gia cùng hạng cân thành công");
				}
			}
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage getAllUserExhibitionPlayer(int tournamentId, String studentId) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Tournament tournament = tournamentRepository.findById(tournamentId).get();
			User user = userRepository.findByStudentId(studentId).get();
			Optional<TournamentPlayer> tournamentPlayerOp = tournamentPlayerRepository
					.getPlayerByUserIdAndTournamentId(user.getId(), tournament.getId());
			if (tournamentPlayerOp.isPresent()) {
				TournamentPlayer tournamentPlayer = tournamentPlayerOp.get();
				List<ExhibitionPlayer> exhibitionPlayers = exhibitionPlayerRepository
						.findAllByPlayerId(tournamentPlayer.getId());
				List<ExhibitionTeamDto> exhibitionTeamsDto = new ArrayList<ExhibitionTeamDto>();
				Set<ExhibitionType> exhibitionTypes = tournament.getExhibitionTypes();
				for (ExhibitionType exhibitionType : exhibitionTypes) {
					Set<ExhibitionTeam> exhibitionTeams = exhibitionType.getExhibitionTeams();
					for (ExhibitionTeam exhibitionTeam : exhibitionTeams) {
						Set<ExhibitionPlayer> setExhibitionPlayers = exhibitionTeam.getExhibitionPlayers();
						for (ExhibitionPlayer exhibitionPlayer : setExhibitionPlayers) {
							for (ExhibitionPlayer exhibitionPlayerUser : exhibitionPlayers) {
								if (exhibitionPlayerUser.getId() == exhibitionPlayer.getId()) {
									ExhibitionTeamDto exhibitionTeamDto = convertToExhibitionTeamDto(exhibitionTeam,
											exhibitionType.getName(), exhibitionType.getId());
									exhibitionTeamsDto.add(exhibitionTeamDto);
								}
							}
						}
					}
				}
				Collections.sort(exhibitionTeamsDto, new Comparator<ExhibitionTeamDto>() {
					@Override
					public int compare(ExhibitionTeamDto o1, ExhibitionTeamDto o2) {
						return o2.getId() - o1.getId();
					}
				});
				responseMessage.setData(exhibitionTeamsDto);
				responseMessage.setMessage("Lấy danh sách đội đã đăng ký tham gia thi đấu biểu diễn thành công");
			}
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage getAllUserOrganizingCommittee(int tournamentId, String studentId) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<Tournament> tournamentOp = tournamentRepository.findById(tournamentId);
			Tournament tournament = tournamentOp.get();
			User user = userRepository.findByStudentId(studentId).get();
			Optional<TournamentOrganizingCommittee> tournamentOrganizingCommitteeOp = tournamentOrganizingCommitteeRepository
					.findByTournamentIdAndUserId(tournament.getId(), user.getId());
			if (tournamentOrganizingCommitteeOp.isPresent()) {
				List<TournamentOrganizingCommittee> tournamentOrganizingCommittees = tournamentOrganizingCommitteeRepository
						.findByTournamentId(tournamentId);
				List<TournamentOrganizingCommitteeDto> tournamentOrganizingCommitteesDto = new ArrayList<TournamentOrganizingCommitteeDto>();
				for (TournamentOrganizingCommittee tournamentOrganizingCommittee : tournamentOrganizingCommittees) {
					TournamentOrganizingCommitteeDto tournamentOrganizingCommitteeDto = new TournamentOrganizingCommitteeDto();
					tournamentOrganizingCommitteeDto.setUserName(tournamentOrganizingCommittee.getUser().getName());
					tournamentOrganizingCommitteeDto
							.setUserStudentId(tournamentOrganizingCommittee.getUser().getStudentId());
					RoleEventDto roleEventDto = new RoleEventDto();
					roleEventDto.setId(tournamentOrganizingCommittee.getRoleEvent().getId());
					roleEventDto.setName(tournamentOrganizingCommittee.getRoleEvent().getName());
					tournamentOrganizingCommitteeDto.setRoleTournamentDto(roleEventDto);
					tournamentOrganizingCommitteesDto.add(tournamentOrganizingCommitteeDto);
				}
				Collections.sort(tournamentOrganizingCommitteesDto);
				responseMessage.setData(tournamentOrganizingCommitteesDto);
				responseMessage.setMessage("Lấy danh sách ban tổ chức giải đấu cho người dùng thành công");
			} else {
				responseMessage.setMessage("Bạn chưa tham gia ban tổ chức giải đấu");
			}
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage getAllTournamentByStudentId(String studentId, String semester, int status) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			User user = userRepository.findByStudentId(studentId).get();

			if (semester == "") {
				semester = semesterRepository.findTop3Semester().get(0).getName();
			}
			List<Tournament> tournaments = tournamentRepository.findBySemester(semester);
			List<UserTournamentDto> userTournamentsDto = new ArrayList<UserTournamentDto>();
			List<UserTournamentDto> listResult = new ArrayList<UserTournamentDto>();
			for (Tournament tournament : tournaments) {
				if (tournament.isStatus()) {
					LocalDate startDate = getStartDate(tournament.getId());
					UserTournamentDto userTournamentDto = new UserTournamentDto();
					if (startDate != null) {
						LocalDate endDate = getEndDate(tournament.getId());
						if (LocalDate.now().isBefore(startDate)) {
							userTournamentDto.setStatus(3); // chua dien ra
						} else if (LocalDate.now().isAfter(endDate)) {
							userTournamentDto.setStatus(1); // da ket thuc
						} else {
							userTournamentDto.setStatus(2);// dang dien ra
						}
					} else {
						userTournamentDto.setStatus(3);// chua dien ra
					}

					Optional<TournamentOrganizingCommittee> tournamentOrganizingCommitteeOp = tournamentOrganizingCommitteeRepository
							.findByTournamentIdAndUserId(tournament.getId(), user.getId());
					if (tournamentOrganizingCommitteeOp.isPresent()) {
						userTournamentDto.setJoined(true);
					} else {
						Optional<TournamentPlayer> tournamentPlayerOp = tournamentPlayerRepository
								.findPlayerByUserIdAndTournamentId(user.getId(), tournament.getId());
						if (tournamentPlayerOp.isPresent()) {
							userTournamentDto.setJoined(true);
						} else {
							userTournamentDto.setJoined(false);
						}
					}

					userTournamentDto.setFeeOrganizingCommiteePay(tournament.getFeeOrganizingCommiteePay());
					userTournamentDto.setFeePlayerPay(tournament.getFeePlayerPay());
					userTournamentDto.setStartDate(startDate);
					userTournamentDto.setTotalAmount(tournament.getTotalAmount());
					userTournamentDto.setName(tournament.getName());
					userTournamentDto.setId(tournament.getId());
					userTournamentDto.setTotalAmountEstimate(tournament.getTotalAmountEstimate());
					userTournamentDto.setTotalAmountFromClubActual(tournament.getTotalAmountFromClubActual());
					userTournamentDto.setTotalAmountFromClubEstimate(tournament.getTotalAmountFromClubEstimate());
					userTournamentDto.setRegistrationPlayerDeadline(tournament.getRegistrationPlayerDeadline());
					userTournamentDto.setRegistrationOrganizingCommitteeDeadline(
							tournament.getRegistrationOrganizingCommitteeDeadline());
					userTournamentsDto.add(userTournamentDto);
				}
			}
			if (status == 0) {
				listResult.addAll(userTournamentsDto);
			} else if (status == 1) {
				for (UserTournamentDto userTournamentDto : userTournamentsDto) {
					if (userTournamentDto.getStatus() == 1) {
						listResult.add(userTournamentDto);
					}
				}
			} else if (status == 2) {
				for (UserTournamentDto userTournamentDto : userTournamentsDto) {
					if (userTournamentDto.getStatus() == 2) {
						listResult.add(userTournamentDto);
					}
				}
			} else {
				for (UserTournamentDto userTournamentDto : userTournamentsDto) {
					if (userTournamentDto.getStatus() == 3) {
						listResult.add(userTournamentDto);
					}
				}
			}
			Collections.sort(listResult);
			responseMessage.setData(listResult);
			responseMessage.setMessage("Lấy danh sách giải đấu thành công" + semester);
			responseMessage.setTotalResult(listResult.size());

		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage addListTournamentOrganizingCommittee(String studentId,
			List<UserTournamentOrganizingCommitteeDto> users, int tournamentId) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			User user = userRepository.findByStudentId(studentId).get();
			Tournament tournament = tournamentRepository.findById(tournamentId).get();

			List<UserTournamentOrganizingCommitteeDto> usersNotAdd = new ArrayList<UserTournamentOrganizingCommitteeDto>();

			for (UserTournamentOrganizingCommitteeDto userToJoin : users) {
				if (!isJoinTournament(userToJoin.getUser().getId(), tournamentId)) {
					TournamentOrganizingCommittee tournamentOrganizingCommittee = new TournamentOrganizingCommittee();
					tournamentOrganizingCommittee.setUser(userToJoin.getUser());
					RoleEvent roleEvent = roleEventRepository.findById(userToJoin.getRoleId()).get();
					tournamentOrganizingCommittee.setRoleEvent(roleEvent);
					tournamentOrganizingCommittee.setTournament(tournament);
					tournamentOrganizingCommittee.setPaymentStatus(false);
					tournamentOrganizingCommittee.setCreatedBy(user.getName() + " - " + user.getStudentId());
					tournamentOrganizingCommittee.setCreatedOn(LocalDateTime.now());
					tournamentOrganizingCommitteeRepository.save(tournamentOrganizingCommittee);
				} else {
					usersNotAdd.add(userToJoin);
				}
			}
			responseMessage.setData(usersNotAdd);
			responseMessage.setMessage("Thêm thành viên vào ban tổ chức thành công");
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	private boolean isJoinTournament(int userId, int tournamentId) {
		Optional<TournamentPlayer> tournamentPlayerOp = tournamentPlayerRepository
				.findPlayerByUserIdAndTournamentId(userId, tournamentId);
		if (tournamentPlayerOp.isPresent()) {
			return true;
		}

		Optional<TournamentOrganizingCommittee> tournamentOrganizingCommitteeOp = tournamentOrganizingCommitteeRepository
				.findByTournamentIdAndUserId(tournamentId, userId);
		if (tournamentOrganizingCommitteeOp.isPresent()) {
			return true;
		}

		return false;
	}

	@Override
	public ResponseMessage getAllUserNotJoinTournament(int tournamentId) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Tournament tournament = tournamentRepository.findById(tournamentId).get();
			List<User> users = userRepository.findAllActiveUser();
			List<User> userNotJoin = new ArrayList<User>();
			for (User user : users) {
				if (!isJoinTournament(user.getId(), tournament.getId())) {
					userNotJoin.add(user);
				}
			}
			responseMessage.setData(userNotJoin);
			responseMessage.setMessage("Lấy danh sách thành viên chưa tham gia giải đấu thành công");
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage deleteTournamentOrganizingCommittee(int tournamentOrganizingCommitteeId) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<TournamentOrganizingCommittee> tournamentOrganizingCommitteeOp = tournamentOrganizingCommitteeRepository
					.findById(tournamentOrganizingCommitteeId);
			if (tournamentOrganizingCommitteeOp.isPresent()) {
				TournamentOrganizingCommittee tournamentOrganizingCommittee = tournamentOrganizingCommitteeOp.get();
				if (!tournamentOrganizingCommittee.isPaymentStatus()) {
					tournamentOrganizingCommitteeRepository.delete(tournamentOrganizingCommittee);
					responseMessage.setData(Arrays.asList(tournamentOrganizingCommittee));
					responseMessage.setMessage("Xóa thành viên ban tổ chức thành công");
				} else {
					responseMessage.setMessage("Thành viên này đã đóng phí tham gia, không thể xóa");
				}
			} else {
				responseMessage.setMessage("Không có thành viên này");
			}
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage getResultOfTournament(int tournamentId) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<Tournament> getTournamentOp = tournamentRepository.findById(tournamentId);
			if (getTournamentOp.isPresent()) {
				Tournament getTournament = getTournamentOp.get();
				TournamentResultDto tournamentResultDto = new TournamentResultDto();
				Set<CompetitiveType> getCompetitiveTypes = getTournament.getCompetitiveTypes();
				List<ResponseMessage> listCompetitiveResult = new ArrayList<ResponseMessage>();
				for (CompetitiveType competitiveType : getCompetitiveTypes) {
					listCompetitiveResult.add(competitiveService.getResultByType(competitiveType.getId()));
				}
				Set<ExhibitionType> getExhibitionTypes = getTournament.getExhibitionTypes();
				List<ResponseMessage> listExhibitionResult = new ArrayList<ResponseMessage>();
				for (ExhibitionType exhibitionType : getExhibitionTypes) {
					listExhibitionResult.add(exhibitionService.getExhibitionResultByType(exhibitionType.getId()));
				}
				tournamentResultDto.setListCompetitiveResult(listCompetitiveResult);
				tournamentResultDto.setListExhibitionResult(listExhibitionResult);
				responseMessage.setData(Arrays.asList(tournamentResultDto));
				responseMessage.setMessage("Kết quả giải đấu");
			} else {
				responseMessage.setMessage("Không tìm thấy giải đấu");
			}
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage spawnTimeAndArea(int tournamentId) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<Tournament> getTournamentOp = tournamentRepository.findById(tournamentId);
			if (getTournamentOp.isPresent()) {
				Tournament getTournament = getTournamentOp.get();

				if (getTournament.getStage() <= 1) {
					List<CompetitiveMatch> listCompetitiveMatchs = new ArrayList<CompetitiveMatch>();
					Set<CompetitiveType> listCompetitiveTypes = getTournament.getCompetitiveTypes();
					List<Area> listArea = areaRepository.findAll();
					for (CompetitiveType competitiveType : listCompetitiveTypes) {
						competitiveType.setStatus(2);
						List<CompetitiveMatch> listMatchByType = competitiveMatchRepository
								.listMatchsByType(competitiveType.getId());
						for (CompetitiveMatch competitiveMatch : listMatchByType) {
							if (competitiveMatch.getRound() == 1 && (competitiveMatch.getFirstStudentId() == null
									|| competitiveMatch.getSecondStudentId() == null)) {
								continue;
							}
							listCompetitiveMatchs.add(competitiveMatch);
						}
					}
					Collections.sort(listCompetitiveMatchs);

					List<ExhibitionTeam> listExhibitionTeams = new ArrayList<ExhibitionTeam>();
					Set<ExhibitionType> listExhibitionTypes = getTournament.getExhibitionTypes();
					for (ExhibitionType exhibitionType : listExhibitionTypes) {
						exhibitionType.setStatus(2);
						List<ExhibitionTeam> getTeams = new ArrayList<ExhibitionTeam>(
								exhibitionType.getExhibitionTeams());
						listExhibitionTeams.addAll(getTeams);
					}

					tournamentRepository.save(getTournament);

					List<TournamentSchedule> listTournamentSchedules = tournamentScheduleRepository
							.findByTournamentId(tournamentId);

					int timeMatchNeedHeld = listCompetitiveMatchs.size() * 10 + listExhibitionTeams.size() * 5;
					int timeMatchCanHeld = 0;
					for (TournamentSchedule tournamentSchedule : listTournamentSchedules) {
						LocalTime startTime = tournamentSchedule.getStartTime();
						LocalTime finishTime = tournamentSchedule.getFinishTime();
						timeMatchCanHeld += ((finishTime.getHour() - startTime.getHour()) * 60 + finishTime.getMinute()
								- startTime.getMinute());
					}
					LocalDate doneSpawnCompetitiveDate = listTournamentSchedules.get(0).getDate();
					LocalTime doneSpawnCompetitiveTime = listTournamentSchedules.get(0).getStartTime();
					if (timeMatchCanHeld >= timeMatchNeedHeld) {
						boolean continueSpawnCompetitive = true;
						int index = 0;
						CompetitiveResult oldResult = new CompetitiveResult();

						boolean continueSpawnExhibition = true;
						List<ExhibitionType> listTypeNeedHeld = new ArrayList<ExhibitionType>();
						for (ExhibitionType exhibitionType : listExhibitionTypes) {
							if (exhibitionType.getExhibitionTeams().size() > 2) {
								listTypeNeedHeld.add(exhibitionType);
							}
						}
						Area getArea = listArea.get(0);
						for (int i = 0; i < listTournamentSchedules.size(); i++) {
							if (continueSpawnCompetitive) {
								LocalDate date = listTournamentSchedules.get(i).getDate();
								LocalTime startTime = listTournamentSchedules.get(i).getStartTime();
								LocalTime finishTime = listTournamentSchedules.get(i).getFinishTime();
								while (startTime.isBefore(finishTime)) {
									if (continueSpawnCompetitive) {
										for (Area area : listArea) {
											LocalDateTime timeMatch = LocalDateTime.of(date, startTime);
											if (continueSpawnCompetitive) {
												CompetitiveResult newResult = new CompetitiveResult();
												Optional<CompetitiveResult> getResultOp = competitiveResultRepository
														.findByMatchId(listCompetitiveMatchs.get(index).getId());
												if (getResultOp.isPresent()) {
													newResult = getResultOp.get();
												}
												newResult.setMatch(listCompetitiveMatchs.get(index));
												if (index > 0 && oldResult.getMatch().getRound() < newResult.getMatch()
														.getRound() && oldResult.getTime().equals(timeMatch)) {
													startTime = startTime.plusMinutes(10);
													timeMatch = LocalDateTime.of(date, startTime);
												}
												newResult.setTime(timeMatch);
												newResult.setArea(area);
												newResult.setCreatedBy("LinhLHN");
												newResult.setCreatedOn(LocalDateTime.now());
												newResult.setUpdatedBy("LinhLHN");
												newResult.setUpdatedOn(LocalDateTime.now());
												competitiveResultRepository.save(newResult);
												oldResult = newResult;
												index++;
												if (index == listCompetitiveMatchs.size()) {
													continueSpawnCompetitive = false;
													doneSpawnCompetitiveDate = date;
													if (startTime.plusMinutes(10).compareTo(finishTime) <= 0) {
														doneSpawnCompetitiveTime = startTime.plusMinutes(10);
													} else {
														doneSpawnCompetitiveDate = listTournamentSchedules.get(i + 1)
																.getDate();
													}
												}
											} else {
												break;
											}
										}
										startTime = startTime.plusMinutes(10);
									} else {
										break;
									}
								}
							}
							if (!continueSpawnCompetitive) {
								index = 0;
								LocalDate getDate = listTournamentSchedules.get(i).getDate();
								LocalTime startTime = listTournamentSchedules.get(i).getStartTime();
								LocalTime finishTime = listTournamentSchedules.get(i).getFinishTime();
								if (getDate.isEqual(doneSpawnCompetitiveDate)) {
									while (startTime.isBefore(doneSpawnCompetitiveTime)) {
										startTime = startTime.plusMinutes(10);
									}
								}
								if (startTime.compareTo(finishTime) >= 0) {
									continue;
								}
								while (true) {
									int countMatchCanHeld = ((listTournamentSchedules.get(i).getFinishTime().getHour()
											- startTime.getHour()) * 60
											+ listTournamentSchedules.get(i).getFinishTime().getMinute()
											- startTime.getMinute()) / 5;
									if (listTypeNeedHeld.get(index).getExhibitionTeams().size() > countMatchCanHeld) {
										break;
									}
									ExhibitionType getType = listTypeNeedHeld.get(index);
									Set<ExhibitionTeam> getTeamsByType = getType.getExhibitionTeams();
									List<ExhibitionTeam> getTeams = new ArrayList<ExhibitionTeam>();
									for (ExhibitionTeam exhibitionTeam : getTeamsByType) {
										getTeams.add(exhibitionTeam);
									}
									for (ExhibitionTeam exhibitionTeam : getTeams) {
										ExhibitionResult newResult = new ExhibitionResult();
										Optional<ExhibitionResult> getResultOp = exhibitionResultRepository
												.findByTeam(exhibitionTeam.getId());
										if (getResultOp.isPresent()) {
											newResult = getResultOp.get();
										}
										newResult.setTeam(exhibitionTeam);
										newResult.setArea(getArea);
										LocalDateTime getTime = LocalDateTime.of(getDate, startTime);
										newResult.setTime(getTime);
										newResult.setCreatedBy("LinhLHN");
										newResult.setCreatedOn(LocalDateTime.now());
										newResult.setUpdatedBy("LinhLHN");
										newResult.setUpdatedOn(LocalDateTime.now());
										exhibitionResultRepository.save(newResult);
										startTime = startTime.plusMinutes(5);
									}
									index++;

									if (index == listTypeNeedHeld.size()) {
										continueSpawnExhibition = false;
									} else {
										continue;
									}
									if (!continueSpawnExhibition) {
										break;
									}
								}
								if (!continueSpawnExhibition) {
									break;
								}
							}
						}
						responseMessage.setMessage("Phân chia lịch cho tất cả thể thức thành công");
						for (CompetitiveType competitiveType : listCompetitiveTypes) {
							competitiveType.setStatus(3);
						}
						for (ExhibitionType exhibitionType : listExhibitionTypes) {
							exhibitionType.setStatus(3);
						}
						getTournament.setStage(2);
						tournamentRepository.save(getTournament);
					} else {
						responseMessage.setMessage("Không đủ thời gian xếp lịch trận đấu. Vui lòng tạo thêm "
								+ (timeMatchNeedHeld - timeMatchCanHeld) + "phút để xếp lịch trận đấu");
					}
				} else {
					responseMessage.setMessage("Đã qua giai đoạn khởi tạo thời gian đia điểm");
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage updateAfterTournament(String studentId, int tournamentId, double totalAmountActual) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<Tournament> tournamentOp = tournamentRepository.findById(tournamentId);
			if (tournamentOp.isPresent()) {
				Tournament getTournament = tournamentOp.get();
				if (getTournament.getStage() == 3) {
					Set<CompetitiveType> listCompetitiveTypes = getTournament.getCompetitiveTypes();
					for (CompetitiveType competitiveType : listCompetitiveTypes) {
						List<CompetitiveMatch> listMatchs = competitiveMatchRepository
								.listMatchsByType(competitiveType.getId());
						for (CompetitiveMatch competitiveMatch : listMatchs) {
							Optional<CompetitiveResult> getResultOp = competitiveResultRepository
									.findByMatchId(competitiveMatch.getId());
							if (getResultOp.isPresent()) {
								CompetitiveResult getResult = getResultOp.get();
								if (getResult.getFirstPoint() == null || getResult.getSecondPoint() == null) {
									responseMessage.setMessage("Không thể tổng kết. Thể thức "
											+ (competitiveType.isGender() ? "Nam " : "Nữ ")
											+ competitiveType.getWeightMin() + " - " + competitiveType.getWeightMax()
											+ " vẫn còn trận đấu đối kháng chưa kết thúc");
									return responseMessage;
								}
							} else {
								responseMessage.setMessage("Không thể tổng kết. Thể thức "
										+ (competitiveType.isGender() ? "Nam " : "Nữ ") + competitiveType.getWeightMin()
										+ " - " + competitiveType.getWeightMax()
										+ " vẫn còn trận đấu đối kháng chưa kết thúc");
								return responseMessage;
							}
						}
					}
					Set<ExhibitionType> listExhibitionTypes = getTournament.getExhibitionTypes();
					for (ExhibitionType exhibitionType : listExhibitionTypes) {
						Set<ExhibitionTeam> listTeams = exhibitionType.getExhibitionTeams();
						for (ExhibitionTeam exhibitionTeam : listTeams) {
							Optional<ExhibitionResult> getResultOp = exhibitionResultRepository
									.findByTeam(exhibitionTeam.getId());
							if (getResultOp.isPresent()) {
								ExhibitionResult getResult = getResultOp.get();
								if (getResult.getScore() == null) {
									responseMessage.setMessage("Không thành công. Thể thức " + exhibitionType.getName()
											+ " vẫn còn đội chưa biểu diễn");
									return responseMessage;
								}
							} else {
								responseMessage.setMessage("Không thành công. Thể thức " + exhibitionType.getName()
										+ " vẫn còn đội chưa biểu diễn");
								return responseMessage;
							}
						}
					}

					User user = userRepository.findByStudentId(studentId).get();

					int countPlayer = getTournament.getTournamentPlayers().size();
					List<TournamentOrganizingCommittee> listCommittee = tournamentOrganizingCommitteeRepository
							.findByTournamentId(tournamentId);
					double totalProceedsActual = countPlayer * getTournament.getFeePlayerPay()
							+ listCommittee.size() * getTournament.getFeeOrganizingCommiteePay();

					getTournament.setTotalAmount(totalAmountActual);

					getTournament.setTotalAmountFromClubActual(
							(totalAmountActual > totalProceedsActual) ? (totalAmountActual - totalProceedsActual) : 0);

					if (totalAmountActual > totalProceedsActual) {
						clubFundService.withdrawFromClubFund(user.getStudentId(),
								(getTournament.getTotalAmountFromClubActual() - getTournament.getTotalAmountEstimate()),
								"Phát sinh thêm từ giải đấu " + getTournament.getName());
					} else if (getTournament.getTotalAmount() < getTournament.getTotalAmountEstimate()) {
						clubFundService.depositToClubFund(user.getStudentId(),
								(getTournament.getTotalAmountEstimate() - getTournament.getTotalAmountFromClubActual()),
								"Tiền dư từ giải đấu " + getTournament.getName());
					}

					getTournament.setUpdatedBy(user.getName() + " - " + user.getStudentId());
					getTournament.setUpdatedOn(LocalDateTime.now());
					tournamentRepository.save(getTournament);
					responseMessage.setData(Arrays.asList(getTournament));
					responseMessage.setMessage(Constant.MSG_129);
				} else {
					responseMessage.setMessage("Giải đấu chưa bắt đầu không thể tổng kết");
				}
			} else {
				responseMessage.setMessage("Không tìm thấy giải đấu");
			}
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage updateTimeAndAreaCompetitive(int matchId, CompetitiveResult newResult) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			LocalDate getDate = LocalDate.of(newResult.getTime().getYear(), newResult.getTime().getMonthValue(),
					newResult.getTime().getDayOfMonth());

			Optional<CompetitiveMatch> getMatchOp = competitiveMatchRepository.findById(matchId);
			if (getMatchOp.isPresent()) {
				CompetitiveType getType = getMatchOp.get().getCompetitiveType();

				Tournament getTournament = tournamentRepository
						.findById(competitiveTypeRepository.findTournamentOfType(getType.getId())).get();

				if (getTournament.getStage() == 2) {
					List<TournamentSchedule> getListSchedules = tournamentScheduleRepository
							.findByTournamentId(getTournament.getId());
					for (TournamentSchedule tournamentSchedule : getListSchedules) {
						if (tournamentSchedule.getDate().equals(getDate)) {
							LocalDateTime getDateTimeStart = LocalDateTime.of(getDate,
									tournamentSchedule.getStartTime());
							if (newResult.getTime().compareTo(getDateTimeStart) < 0) {
								responseMessage
										.setMessage("Quá sớm. Thời gian tổ chức giải đấu trong ngày đã chưa bắt đầu");
								return responseMessage;
							}
							LocalDateTime getDateTimeFinish = LocalDateTime.of(getDate,
									tournamentSchedule.getFinishTime());
							if (newResult.getTime().plusMinutes(10).compareTo(getDateTimeFinish) > 0) {
								responseMessage
										.setMessage("Quá muộn. Thời gian tổ chức giải đấu trong ngày đã trôi qua");
								return responseMessage;
							}
							break;
						}
					}

					List<CompetitiveResult> listCompetitiveResults = competitiveResultRepository
							.listCompetitiveResultByAreaOrderTime(newResult.getArea().getId(), getDate.getDayOfYear(),
									getDate.getYear());

					int checkExisted = -1;
					for (int i = 0; i < listCompetitiveResults.size(); i++) {
						if (listCompetitiveResults.get(i).getMatch().getId() == matchId) {
							checkExisted = i;
							break;
						}
					}

					if (checkExisted != -1) {
						listCompetitiveResults.remove(checkExisted);
					}

					for (int i = 0; i < listCompetitiveResults.size(); i++) {
						if (listCompetitiveResults.get(i).getTime().compareTo(newResult.getTime()) == 0) {
							responseMessage.setMessage("Bị trùng với trận đối kháng khác diễn ra trên cùng sân vào lúc "
									+ Utils.ConvertLocalDateTimeToString(listCompetitiveResults.get(i).getTime()));
							return responseMessage;
						} else if (listCompetitiveResults.get(i).getTime().compareTo(newResult.getTime()) < 0) {
							if (listCompetitiveResults.get(i).getTime().plusMinutes(10)
									.compareTo(newResult.getTime()) <= 0) {
								continue;
							} else {
								responseMessage.setMessage(
										"Bị trùng với trận đối kháng khác diễn ra trên cùng sân vào lúc " + Utils
												.ConvertLocalDateTimeToString(listCompetitiveResults.get(i).getTime()));
								return responseMessage;
							}
						} else {
							if (listCompetitiveResults.get(i).getTime()
									.compareTo(newResult.getTime().plusMinutes(10)) >= 0) {
								break;
							} else {
								responseMessage.setMessage(
										"Bị trùng với trận đối kháng khác diễn ra trên cùng sân vào lúc " + Utils
												.ConvertLocalDateTimeToString(listCompetitiveResults.get(i).getTime()));
								return responseMessage;
							}
						}
					}

					List<ExhibitionResult> listExhibitionResults = exhibitionResultRepository
							.listExhibitionResultByAreaOrderTime(newResult.getArea().getId(), getDate.getDayOfYear(),
									getDate.getYear());

					for (int i = 0; i < listExhibitionResults.size(); i++) {
						if (listExhibitionResults.get(i).getTime().compareTo(newResult.getTime()) == 0) {
							responseMessage.setMessage("Bị trùng với trận biểu diễn khác diễn ra trên cùng sân vào lúc "
									+ Utils.ConvertLocalDateTimeToString(listExhibitionResults.get(i).getTime()));
							return responseMessage;
						} else if (listExhibitionResults.get(i).getTime().compareTo(newResult.getTime()) < 0) {
							if (listExhibitionResults.get(i).getTime().plusMinutes(5)
									.compareTo(newResult.getTime()) <= 0) {
								continue;
							} else {
								responseMessage.setMessage(
										"Bị trùng với trận biểu diễn khác diễn ra trên cùng sân vào lúc " + Utils
												.ConvertLocalDateTimeToString(listExhibitionResults.get(i).getTime()));
								return responseMessage;
							}
						} else {
							if (listExhibitionResults.get(i).getTime()
									.compareTo(newResult.getTime().plusMinutes(10)) >= 0) {
								break;
							} else {
								responseMessage.setMessage(
										"Bị trùng với trận biểu diễn khác diễn ra trên cùng sân vào lúc " + Utils
												.ConvertLocalDateTimeToString(listExhibitionResults.get(i).getTime()));
								return responseMessage;
							}
						}
					}

					CompetitiveResult getResult = competitiveResultRepository.findResultByMatchId(matchId).get();
					getResult.setArea(newResult.getArea());
					getResult.setTime(newResult.getTime());
					getResult.setUpdatedBy("LinhLHN");
					getResult.setUpdatedOn(LocalDateTime.now());
					competitiveResultRepository.save(getResult);
					responseMessage.setData(Arrays.asList(getResult));
					responseMessage.setMessage("Cập nhật thời gian và địa điểm thành công");
				} else {
					responseMessage.setMessage("Giai đoạn này không thể cập nhật thời gian địa điểm cho trận đấu");
				}
			} else {
				responseMessage.setMessage("Không tìm thấy trận đấu");
			}
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage updateTimeAndAreaExhibition(int teamId, ExhibitionResult newResult) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			LocalDate getDate = LocalDate.of(newResult.getTime().getYear(), newResult.getTime().getMonthValue(),
					newResult.getTime().getDayOfMonth());

			Optional<ExhibitionTeam> getTeamOp = exhibitionTeamRepository.findById(teamId);
			if (getTeamOp.isPresent()) {
				Tournament getTournament = tournamentRepository.findById(exhibitionTypeRepository
						.findTournamentOfType(exhibitionTeamRepository.findTypeOfExhibitionTeam(teamId))).get();
				
				if(getTournament.getStage() == 2) {
					List<TournamentSchedule> getListSchedules = tournamentScheduleRepository.findByTournamentId(getTournament.getId());

					for (TournamentSchedule tournamentSchedule : getListSchedules) {
						if (tournamentSchedule.getDate().equals(getDate)) {
							LocalDateTime getDateTimeStart = LocalDateTime.of(getDate, tournamentSchedule.getStartTime());
							if (newResult.getTime().compareTo(getDateTimeStart) < 0) {
								responseMessage
										.setMessage("Quá sớm. Thời gian tổ chức giải đấu trong ngày đã chưa bắt đầu");
								return responseMessage;
							}
							LocalDateTime getDateTimeFinish = LocalDateTime.of(getDate, tournamentSchedule.getFinishTime());
							if (newResult.getTime().plusMinutes(5).compareTo(getDateTimeFinish) > 0) {
								responseMessage.setMessage("Quá muộn. Thời gian tổ chức giải đấu trong ngày đã trôi qua");
								return responseMessage;
							}
							break;
						}
					}

					List<CompetitiveResult> listCompetitiveResults = competitiveResultRepository
							.listCompetitiveResultByAreaOrderTime(newResult.getArea().getId(), getDate.getDayOfYear(),
									getDate.getYear());

					for (int i = 0; i < listCompetitiveResults.size(); i++) {
						if (listCompetitiveResults.get(i).getTime().compareTo(newResult.getTime()) == 0) {
							responseMessage.setMessage("Bị trùng với trận đối kháng khác diễn ra trên cùng sân vào lúc "
									+ Utils.ConvertLocalDateTimeToString(listCompetitiveResults.get(i).getTime()));
							return responseMessage;
						} else if (listCompetitiveResults.get(i).getTime().compareTo(newResult.getTime()) < 0) {
							if (listCompetitiveResults.get(i).getTime().plusMinutes(10)
									.compareTo(newResult.getTime()) <= 0) {
								continue;
							} else {
								responseMessage.setMessage("Bị trùng với trận đối kháng khác diễn ra trên cùng sân vào lúc "
										+ Utils.ConvertLocalDateTimeToString(listCompetitiveResults.get(i).getTime()));
								return responseMessage;
							}
						} else {
							if (listCompetitiveResults.get(i).getTime()
									.compareTo(newResult.getTime().plusMinutes(5)) >= 0) {
								break;
							} else {
								responseMessage.setMessage("Bị trùng với trận đối kháng khác diễn ra trên cùng sân vào lúc "
										+ Utils.ConvertLocalDateTimeToString(listCompetitiveResults.get(i).getTime()));
								return responseMessage;
							}
						}
					}

					List<ExhibitionResult> listExhibitionResults = exhibitionResultRepository
							.listExhibitionResultByAreaOrderTime(newResult.getArea().getId(), getDate.getDayOfYear(),
									getDate.getYear());

					int checkExisted = -1;
					for (int i = 0; i < listExhibitionResults.size(); i++) {
						if (listExhibitionResults.get(i).getTeam().getId() == teamId) {
							checkExisted = i;
							break;
						}
					}

					if (checkExisted != -1) {
						listExhibitionResults.remove(checkExisted);
					}

					for (int i = 0; i < listExhibitionResults.size(); i++) {
						if (listExhibitionResults.get(i).getTime().compareTo(newResult.getTime()) == 0) {
							responseMessage.setMessage("Bị trùng với trận biểu diễn khác diễn ra trên cùng sân vào lúc "
									+ Utils.ConvertLocalDateTimeToString(listExhibitionResults.get(i).getTime()));
							return responseMessage;
						} else if (listExhibitionResults.get(i).getTime().compareTo(newResult.getTime()) < 0) {
							if (listExhibitionResults.get(i).getTime().plusMinutes(5).compareTo(newResult.getTime()) <= 0) {
								continue;
							} else {
								responseMessage.setMessage("Bị trùng với trận biểu diễn khác diễn ra trên cùng sân vào lúc "
										+ Utils.ConvertLocalDateTimeToString(listExhibitionResults.get(i).getTime()));
								return responseMessage;
							}
						} else {
							if (listExhibitionResults.get(i).getTime().compareTo(newResult.getTime().plusMinutes(5)) >= 0) {
								break;
							} else {
								responseMessage.setMessage("Bị trùng với trận biểu diễn khác diễn ra trên cùng sân vào lúc "
										+ Utils.ConvertLocalDateTimeToString(listExhibitionResults.get(i).getTime()));
								return responseMessage;
							}
						}
					}

					ExhibitionResult getResult = exhibitionResultRepository.findByTeam(teamId).get();
					getResult.setArea(newResult.getArea());
					getResult.setTime(newResult.getTime());
					getResult.setUpdatedBy("LinhLHN");
					getResult.setUpdatedOn(LocalDateTime.now());
					exhibitionResultRepository.save(getResult);
					responseMessage.setData(Arrays.asList(getResult));
					responseMessage.setMessage("Cập nhật thời gian và địa điểm thành công");
				}
				else {
					responseMessage.setMessage("Giai đoạn này không thể cập nhật thời gian địa điểm cho đội biểu diễn");
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage getAllSuggestType() {
		ResponseMessage responseMessage = new ResponseMessage();
		List<CompetitiveTypeSample> competitiveTypeSamples = competitiveTypeSampleRepository
				.findAll(Sort.by("id").ascending());
		List<ExhibitionTypeSample> exhibitionTypeSamples = exhibitionTypeSampleRepository
				.findAll(Sort.by("id").ascending());

		TournamentSampleTypeDto tournamentSampleTypeDto = new TournamentSampleTypeDto();
		tournamentSampleTypeDto.setCompetitiveTypeSamples(competitiveTypeSamples);
		tournamentSampleTypeDto.setExhibitionTypeSamples(exhibitionTypeSamples);

		responseMessage.setData(Arrays.asList(tournamentSampleTypeDto));
		responseMessage.setMessage("Lấy danh sách các thể thức thi đấu thành công");
		return responseMessage;
	}

	@Override
	public ResponseMessage editRoleTournament(int tournamentId, List<RoleEventDto> rolesEventDto) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Tournament tournament = tournamentRepository.findById(tournamentId).get();

			List<TournamentRole> tournamentRoles = tournamentRoleRepository.findByTournamentId(tournamentId);
			for (TournamentRole tournamentRole : tournamentRoles) {
				boolean isExist = false;
				for (RoleEventDto roleEventDto : rolesEventDto) {
					if (roleEventDto.getName().equals(tournamentRole.getRoleEvent().getName())) {
						isExist = true;
						break;
					}
				}
				if (!isExist) {
					tournamentRoleRepository.delete(tournamentRole);
				}
			}

			for (RoleEventDto roleEventDto : rolesEventDto) {
				Optional<TournamentRole> tournamentRoleOp = tournamentRoleRepository
						.findByRoleEventIdAndTournamentId(roleEventDto.getId(), tournamentId);
				if (tournamentRoleOp.isPresent()) {
					TournamentRole tournamentRole = tournamentRoleOp.get();
					tournamentRole.setQuantity(roleEventDto.getMaxQuantity());
					tournamentRoleRepository.save(tournamentRole);
				} else {
					Optional<RoleEvent> roleEventOp = roleEventRepository.findByName(roleEventDto.getName());
					if (roleEventOp.isPresent()) {
						RoleEvent roleEvent = roleEventOp.get();
						TournamentRole tournamentRole = new TournamentRole();
						tournamentRole.setTournament(tournament);
						tournamentRole.setQuantity(roleEventDto.getMaxQuantity());
						tournamentRole.setRoleEvent(roleEvent);
						tournamentRoleRepository.save(tournamentRole);
					} else {
						RoleEvent roleEvent = new RoleEvent();
						roleEvent.setName(roleEventDto.getName());
						roleEventRepository.save(roleEvent);

						RoleEvent newRoleEvent = roleEventRepository.findAll(Sort.by("id").descending()).get(0);
						TournamentRole tournamentRole = new TournamentRole();
						tournamentRole.setTournament(tournament);
						tournamentRole.setRoleEvent(newRoleEvent);
						tournamentRole.setQuantity(roleEventDto.getMaxQuantity());
						tournamentRoleRepository.save(tournamentRole);
					}
				}
			}
			responseMessage.setData(rolesEventDto);
			responseMessage.setMessage("Chỉnh sửa vai trò BTC trong giải đấu thành công");
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}
}
