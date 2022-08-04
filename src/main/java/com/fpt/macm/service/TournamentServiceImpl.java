package com.fpt.macm.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.collections4.IterableUtils;
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
import com.fpt.macm.model.dto.TournamentDto;
import com.fpt.macm.model.dto.TournamentOrganizingCommitteeDto;
import com.fpt.macm.model.dto.TournamentOrganizingCommitteePaymentStatusReportDto;
import com.fpt.macm.model.dto.TournamentPlayerDto;
import com.fpt.macm.model.dto.TournamentPlayerPaymentStatusReportDto;
import com.fpt.macm.model.dto.UserTournamentDto;
import com.fpt.macm.model.dto.UserTournamentOrganizingCommitteeDto;
import com.fpt.macm.model.entity.ClubFund;
import com.fpt.macm.model.entity.CompetitivePlayer;
import com.fpt.macm.model.entity.CompetitivePlayerBracket;
import com.fpt.macm.model.entity.CompetitiveType;
import com.fpt.macm.model.entity.ExhibitionPlayer;
import com.fpt.macm.model.entity.ExhibitionTeam;
import com.fpt.macm.model.entity.ExhibitionType;
import com.fpt.macm.model.entity.Notification;
import com.fpt.macm.model.entity.RoleEvent;
import com.fpt.macm.model.entity.Semester;
import com.fpt.macm.model.entity.Tournament;
import com.fpt.macm.model.entity.TournamentOrganizingCommittee;
import com.fpt.macm.model.entity.TournamentOrganizingCommitteePaymentStatusReport;
import com.fpt.macm.model.entity.TournamentPlayer;
import com.fpt.macm.model.entity.TournamentPlayerPaymentStatusReport;
import com.fpt.macm.model.entity.TournamentSchedule;
import com.fpt.macm.model.entity.User;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.repository.ClubFundRepository;
import com.fpt.macm.repository.CompetitiveMatchRepository;
import com.fpt.macm.repository.CompetitivePlayerBracketRepository;
import com.fpt.macm.repository.CompetitivePlayerRepository;
import com.fpt.macm.repository.CompetitiveTypeRepository;
import com.fpt.macm.repository.ExhibitionPlayerRepository;
import com.fpt.macm.repository.ExhibitionTeamRepository;
import com.fpt.macm.repository.ExhibitionTypeRepository;
import com.fpt.macm.repository.NotificationRepository;
import com.fpt.macm.repository.RoleEventRepository;
import com.fpt.macm.repository.SemesterRepository;
import com.fpt.macm.repository.TournamentOrganizingCommitteePaymentStatusReportRepository;
import com.fpt.macm.repository.TournamentOrganizingCommitteeRepository;
import com.fpt.macm.repository.TournamentPlayerPaymentStatusReportRepository;
import com.fpt.macm.repository.TournamentPlayerRepository;
import com.fpt.macm.repository.TournamentRepository;
import com.fpt.macm.repository.TournamentScheduleRepository;
import com.fpt.macm.repository.UserRepository;
import com.fpt.macm.utils.Utils;

@Service
public class TournamentServiceImpl implements TournamentService {

	@Autowired
	TournamentRepository tournamentRepository;

	@Autowired
	SemesterService semesterService;

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
	CompetitivePlayerBracketRepository competitivePlayerBracketRepository;
	
	@Autowired
	NotificationRepository notificationRepository;
	
	@Autowired
	NotificationService notificationService;

	@Override
	public ResponseMessage createTournament(Tournament tournament) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Semester semester = (Semester) semesterService.getCurrentSemester().getData().get(0);
			tournament.setSemester(semester.getName());
			tournament.setStatus(0);
			tournament.setCreatedBy("toandv");
			tournament.setCreatedOn(LocalDateTime.now());
			tournament.setTotalAmount(0);
			tournament.setTotalAmountFromClubActual(0);
			Set<CompetitiveType> competitiveTypes = tournament.getCompetitiveTypes();
			Set<ExhibitionType> exhibitionTypes = tournament.getExhibitionTypes();
			for (CompetitiveType competitiveType : competitiveTypes) {
				competitiveType.setCreatedBy("toandv");
				competitiveType.setCreatedOn(LocalDateTime.now());
			}
			for (ExhibitionType exhibitionType : exhibitionTypes) {
				exhibitionType.setCreatedBy("toandv");
				exhibitionType.setCreatedOn(LocalDateTime.now());
			}
			tournamentRepository.save(tournament);
			responseMessage.setData(Arrays.asList(tournament));
			responseMessage.setCode(200);
			responseMessage.setMessage(Constant.MSG_099);

		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage getAllTournamentOrganizingCommitteeByTournamentId(int tournamentId) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<Tournament> tournamentOp = tournamentRepository.findById(tournamentId);
			Tournament tournament = tournamentOp.get();
			List<TournamentOrganizingCommittee> tournamentOrganizingCommittees = tournamentOrganizingCommitteeRepository
					.findByTournamentId(tournamentId);
			List<TournamentOrganizingCommitteeDto> tournamentOrganizingCommitteesDto = new ArrayList<TournamentOrganizingCommitteeDto>();
			int totalAccept = 0;
			for (TournamentOrganizingCommittee tournamentOrganizingCommittee : tournamentOrganizingCommittees) {
				TournamentOrganizingCommitteeDto tournamentOrganizingCommitteeDto = convertTournamentOrganizingCommitteeToTournamentOrganizingCommitteeDto(
						tournamentOrganizingCommittee);
				tournamentOrganizingCommitteesDto.add(tournamentOrganizingCommitteeDto);
				if (tournamentOrganizingCommittee.getRegisterStatus().equals(Constant.REQUEST_STATUS_APPROVED)) {
					totalAccept++;
				}
			}
			responseMessage.setData(tournamentOrganizingCommitteesDto);
			responseMessage.setTotalActive(totalAccept);
			responseMessage.setTotalResult(tournament.getMaxQuantityComitee());
			responseMessage.setMessage(Constant.MSG_097);
		} catch (Exception e) {
			// TODO: handle exception
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
					TournamentOrganizingCommitteeDto updatedTournamentOrganizingCommitteeDto = convertTournamentOrganizingCommitteeToTournamentOrganizingCommitteeDto(
							tournamentOrganizingCommittee);
					updatedTournamentOrganizingCommitteesDto.add(updatedTournamentOrganizingCommitteeDto);
				}
			}
			responseMessage.setData(updatedTournamentOrganizingCommitteesDto);
			responseMessage.setMessage(Constant.MSG_098);
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	private TournamentOrganizingCommitteeDto convertTournamentOrganizingCommitteeToTournamentOrganizingCommitteeDto(
			TournamentOrganizingCommittee tournamentOrganizingCommittee) {
		TournamentOrganizingCommitteeDto tournamentOrganizingCommitteeDto = new TournamentOrganizingCommitteeDto();
		tournamentOrganizingCommitteeDto.setId(tournamentOrganizingCommittee.getId());
		tournamentOrganizingCommitteeDto.setUserName(tournamentOrganizingCommittee.getUser().getName());
		tournamentOrganizingCommitteeDto.setUserStudentId(tournamentOrganizingCommittee.getUser().getStudentId());
		tournamentOrganizingCommitteeDto.setRegisterStatus(tournamentOrganizingCommittee.getRegisterStatus());
		tournamentOrganizingCommitteeDto.setPaymentStatus(tournamentOrganizingCommittee.isPaymentStatus());

		RoleEventDto roleEventDto = new RoleEventDto();
		roleEventDto.setId(tournamentOrganizingCommittee.getRoleEvent().getId());
		roleEventDto.setName(tournamentOrganizingCommittee.getRoleEvent().getName());
		tournamentOrganizingCommitteeDto.setRoleTournamentDto(roleEventDto);
		Utils.convertNameOfEventRole(tournamentOrganizingCommittee.getRoleEvent(),
				tournamentOrganizingCommitteeDto.getRoleTournamentDto());

		tournamentOrganizingCommitteeDto.setCreatedBy(tournamentOrganizingCommittee.getCreatedBy());
		tournamentOrganizingCommitteeDto.setCreatedOn(tournamentOrganizingCommittee.getCreatedOn());
		tournamentOrganizingCommitteeDto.setUpdatedBy(tournamentOrganizingCommittee.getUpdatedBy());
		tournamentOrganizingCommitteeDto.setUpdatedOn(tournamentOrganizingCommittee.getUpdatedOn());
		return tournamentOrganizingCommitteeDto;
	}

	@Override
	public ResponseMessage updateTournament(int id, TournamentDto tournamentDto) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<Tournament> tournamentOp = tournamentRepository.findById(id);
			if (tournamentOp.isPresent()) {
				Tournament tournament = tournamentOp.get();
				tournament.setName(tournamentDto.getName());
				tournament.setDescription(tournamentDto.getDescription());
				tournament.setRegistrationPlayerDeadline(tournamentDto.getRegistrationPlayerDeadline());
				tournament.setRegistrationOrganizingCommitteeDeadline(
						tournamentDto.getRegistrationOrganizingCommitteeDeadline());
				Set<CompetitiveTypeDto> competitiveTypeDtos = tournamentDto.getCompetitiveTypesDto();
				Set<CompetitiveType> competitiveTypes = tournament.getCompetitiveTypes();
				Set<ExhibitionTypeDto> exhibitionTypeDtos = tournamentDto.getExhibitionTypesDto();
				Set<ExhibitionType> exhibitionTypes = tournament.getExhibitionTypes();
				Set<CompetitiveTypeDto> competitiveTypeDtosRemove = new HashSet<CompetitiveTypeDto>();
				Set<ExhibitionTypeDto> exhibitionTypeDtosRemove = new HashSet<ExhibitionTypeDto>();
				competitiveTypes.removeIf(
						a -> !competitiveTypeDtos.stream().anyMatch(b -> Objects.equals(a.getId(), b.getId())));
				for (CompetitiveTypeDto competitiveTypeDto : competitiveTypeDtos) {
					for (CompetitiveType competitiveType : competitiveTypes) {
						if (competitiveTypeDto.getId() == competitiveType.getId()) {
							competitiveType = convertCompetitiveTypeDto(competitiveTypeDto);
							competitiveType.setUpdatedBy("toandv");
							competitiveType.setUpdatedOn(LocalDateTime.now());
							competitiveTypeRepository.save(competitiveType);
							competitiveTypeDtosRemove.add(competitiveTypeDto);
						}
					}
				}
				competitiveTypeDtos.removeAll(competitiveTypeDtosRemove);
				for (CompetitiveTypeDto competitiveTypeDto : competitiveTypeDtos) {
					CompetitiveType competitiveType = convertCompetitiveTypeDto(competitiveTypeDto);
					competitiveType.setUpdatedBy("toandv");
					competitiveType.setUpdatedOn(LocalDateTime.now());
					competitiveTypes.add(competitiveType);
				}

				exhibitionTypes.removeIf(
						a -> !exhibitionTypeDtos.stream().anyMatch(b -> Objects.equals(a.getId(), b.getId())));
				for (ExhibitionTypeDto exhibitionTypeDto : exhibitionTypeDtos) {
					for (ExhibitionType exhibitionType : exhibitionTypes) {
						if (exhibitionTypeDto.getId() == exhibitionType.getId()) {
							exhibitionType = convertExhibitionTypeDto(exhibitionTypeDto);
							exhibitionType.setUpdatedBy("toandv");
							exhibitionType.setUpdatedOn(LocalDateTime.now());
							exhibitionTypeRepository.save(exhibitionType);
							exhibitionTypeDtosRemove.add(exhibitionTypeDto);
						}
					}
				}
				exhibitionTypeDtos.removeAll(exhibitionTypeDtosRemove);
				for (ExhibitionTypeDto exhibitionTypeDto : exhibitionTypeDtos) {
					ExhibitionType exhibitionType = convertExhibitionTypeDto(exhibitionTypeDto);
					exhibitionType.setUpdatedBy("toandv");
					exhibitionType.setUpdatedOn(LocalDateTime.now());
					exhibitionTypes.add(exhibitionType);
				}
				tournament.setCompetitiveTypes(competitiveTypes);
				tournament.setExhibitionTypes(exhibitionTypes);
				tournamentRepository.save(tournament);
				responseMessage.setData(Arrays.asList(tournament));
				responseMessage.setCode(200);
				responseMessage.setMessage(Constant.MSG_101);
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
	public ResponseMessage deleteTournamentById(int id) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<Tournament> tournamentOp = tournamentRepository.findById(id);
			if (tournamentOp.isPresent()) {
				Tournament tournament = tournamentOp.get();
				tournamentRepository.delete(tournament);
				responseMessage.setMessage(Constant.MSG_102);
			}
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	public ResponseMessage getTournamentById(int id) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<Tournament> tournamentOp = tournamentRepository.findById(id);
			if (tournamentOp.isPresent()) {
				Tournament tournament = tournamentOp.get();
				responseMessage.setData(Arrays.asList(tournament));
				responseMessage.setMessage(Constant.MSG_103);
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
				tournamentDto.setMaxQuantityComitee(tournament.getMaxQuantityComitee());
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
		// TODO Auto-generated method stub
		try {
			List<TournamentSchedule> listSchedule = tournamentScheduleRepository.findByTournamentId(tournamentId);
			if (listSchedule.size() > 0) {
				return listSchedule.get(0).getDate();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

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
		// TODO Auto-generated method stub
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
			// TODO: handle exception
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
		// TODO Auto-generated method stub
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
						exhibitionTeamsDto.add(convertToExhibitionTeamDto(exhibitionTeam, exhibitionType.getName(), exhibitionType.getId()));
					}
				} else {
					// filter theo hạng mục thi đấu
					if (exhibitionType.getId() == exhibitionTypeId) {
						Set<ExhibitionTeam> exhibitionTeams = exhibitionType.getExhibitionTeams();
						for (ExhibitionTeam exhibitionTeam : exhibitionTeams) {
							exhibitionTeamsDto
									.add(convertToExhibitionTeamDto(exhibitionTeam, exhibitionType.getName(), exhibitionType.getId()));
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
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	private ExhibitionTeamDto convertToExhibitionTeamDto(ExhibitionTeam exhibitionTeam, String exhibitionTypeName, int exhibitionTypeId) {
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
	public ResponseMessage getAllOrganizingCommitteeRole() {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<RoleEvent> rolesEvent = roleEventRepository.findAllOrganizingCommitteeRole();
			List<RoleEventDto> rolesEventDto = new ArrayList<RoleEventDto>();
			for (RoleEvent roleEvent : rolesEvent) {
				RoleEventDto roleEventDto = new RoleEventDto();
				roleEventDto.setId(roleEvent.getId());
				roleEventDto.setName(roleEvent.getName());
				Utils.convertNameOfEventRole(roleEvent, roleEventDto);
				rolesEventDto.add(roleEventDto);
			}
			responseMessage.setData(rolesEventDto);
			responseMessage.setMessage(Constant.MSG_116);
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage getAllExhibitionType(int tournamentId) {
		// TODO Auto-generated method stub
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
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage acceptRequestOrganizingCommittee(int tournamentOrganizingCommitteeId) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {

			Optional<TournamentOrganizingCommittee> tournamentOrganizingCommitteeOp = tournamentOrganizingCommitteeRepository
					.findById(tournamentOrganizingCommitteeId);
			TournamentOrganizingCommittee tournamentOrganizingCommittee = tournamentOrganizingCommitteeOp.get();

			Optional<Tournament> tournamentOp = tournamentRepository
					.findById(tournamentOrganizingCommittee.getTournament().getId());
			Tournament tournament = tournamentOp.get();

			List<TournamentOrganizingCommittee> tournamentOrganizingCommittees = tournamentOrganizingCommitteeRepository
					.findByTournamentId(tournamentOrganizingCommittee.getTournament().getId());
			int totalAccept = 0;
			for (TournamentOrganizingCommittee tournamentOrganizingCommittee2 : tournamentOrganizingCommittees) {
				if (tournamentOrganizingCommittee2.getRegisterStatus().equals(Constant.REQUEST_STATUS_APPROVED)) {
					totalAccept++;
				}
			}

			if (totalAccept < tournament.getMaxQuantityComitee()) {
				if (tournamentOrganizingCommittee.getRegisterStatus().equals(Constant.REQUEST_STATUS_PENDING)) {
					tournamentOrganizingCommittee.setRegisterStatus(Constant.REQUEST_STATUS_APPROVED);
					tournamentOrganizingCommittee.setUpdatedBy("toandv");
					tournamentOrganizingCommittee.setUpdatedOn(LocalDateTime.now());
					tournamentOrganizingCommitteeRepository.save(tournamentOrganizingCommittee);
					TournamentOrganizingCommitteeDto tournamentOrganizingCommitteeDto = convertTournamentOrganizingCommitteeToTournamentOrganizingCommitteeDto(
							tournamentOrganizingCommittee);
					
					RoleEventDto roleEventDto = new RoleEventDto();
					roleEventDto.setId(tournamentOrganizingCommittee.getRoleEvent().getId());
					roleEventDto.setName(tournamentOrganizingCommittee.getRoleEvent().getName());
					Utils.convertNameOfEventRole(tournamentOrganizingCommittee.getRoleEvent(), roleEventDto);
					
					Notification notification = new Notification();
					notification.setMessage("Bạn đã được chấp nhận trở thành " + roleEventDto.getName() + " của giải đấu " + tournament.getName());
					notification.setCreatedOn(LocalDateTime.now());
					notification.setNotificationType(0);
					notification.setNotificationTypeId(tournament.getId());
					notificationRepository.save(notification);

					Iterable<Notification> notificationIterable = notificationRepository
							.findAll(Sort.by("id").descending());
					List<Notification> notifications = IterableUtils.toList(notificationIterable);
					Notification newNotification = notifications.get(0);
					
					notificationService.sendNotificationToAnUser(tournamentOrganizingCommittee.getUser(), newNotification);

					responseMessage.setData(Arrays.asList(tournamentOrganizingCommitteeDto));
					responseMessage.setMessage(Constant.MSG_118);
				}
			} else {
				responseMessage.setMessage(Constant.MSG_120);
			}
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage declineRequestOrganizingCommittee(int tournamentOrganizingCommitteeId) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<TournamentOrganizingCommittee> tournamentOrganizingCommitteeOp = tournamentOrganizingCommitteeRepository
					.findById(tournamentOrganizingCommitteeId);
			TournamentOrganizingCommittee tournamentOrganizingCommittee = tournamentOrganizingCommitteeOp.get();
			if (tournamentOrganizingCommittee.getRegisterStatus().equals(Constant.REQUEST_STATUS_PENDING)) {
				tournamentOrganizingCommittee.setRegisterStatus(Constant.REQUEST_STATUS_DECLINED);
				tournamentOrganizingCommittee.setUpdatedBy("toandv");
				tournamentOrganizingCommittee.setUpdatedOn(LocalDateTime.now());
				tournamentOrganizingCommitteeRepository.save(tournamentOrganizingCommittee);
				TournamentOrganizingCommitteeDto tournamentOrganizingCommitteeDto = convertTournamentOrganizingCommitteeToTournamentOrganizingCommitteeDto(
						tournamentOrganizingCommittee);
				
				RoleEventDto roleEventDto = new RoleEventDto();
				roleEventDto.setId(tournamentOrganizingCommittee.getRoleEvent().getId());
				roleEventDto.setName(tournamentOrganizingCommittee.getRoleEvent().getName());
				Utils.convertNameOfEventRole(tournamentOrganizingCommittee.getRoleEvent(), roleEventDto);
				
				Notification notification = new Notification();
				notification.setMessage("Bạn không được chấp nhận trở thành " + roleEventDto.getName() + " của giải đấu " + tournamentOrganizingCommittee.getTournament().getName());
				notification.setCreatedOn(LocalDateTime.now());
				notification.setNotificationType(0);
				notification.setNotificationTypeId(tournamentOrganizingCommittee.getTournament().getId());
				notificationRepository.save(notification);

				Iterable<Notification> notificationIterable = notificationRepository
						.findAll(Sort.by("id").descending());
				List<Notification> notifications = IterableUtils.toList(notificationIterable);
				Notification newNotification = notifications.get(0);
				
				notificationService.sendNotificationToAnUser(tournamentOrganizingCommittee.getUser(), newNotification);
				
				responseMessage.setData(Arrays.asList(tournamentOrganizingCommitteeDto));
				responseMessage.setMessage(Constant.MSG_119);
			}
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage getAllTournamentPlayerPaymentStatus(int tournamentId) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<Tournament> tournamentOp = tournamentRepository.findById(tournamentId);
			Tournament tournament = tournamentOp.get();
			Set<TournamentPlayer> tournamentPlayers = tournament.getTournamentPlayers();
			List<TournamentPlayerDto> tournamentPlayersDto = new ArrayList<TournamentPlayerDto>();
			for (TournamentPlayer tournamentPlayer : tournamentPlayers) {
				TournamentPlayerDto tournamentPlayerDto = convertTournamentPlayer(tournamentPlayer);
				tournamentPlayersDto.add(tournamentPlayerDto);
			}
			responseMessage.setData(tournamentPlayersDto);
			responseMessage.setMessage(Constant.MSG_121);
		} catch (Exception e) {
			// TODO: handle exception
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
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<TournamentOrganizingCommittee> tournamentOrganizingCommittees = tournamentOrganizingCommitteeRepository
					.findByTournamentId(tournamentId);
			List<TournamentOrganizingCommitteeDto> tournamentOrganizingCommitteesDto = new ArrayList<TournamentOrganizingCommitteeDto>();
			for (TournamentOrganizingCommittee tournamentOrganizingCommittee : tournamentOrganizingCommittees) {
				if (tournamentOrganizingCommittee.getRegisterStatus().equals(Constant.REQUEST_STATUS_APPROVED)) {
					TournamentOrganizingCommitteeDto tournamentOrganizingCommitteeDto = convertTournamentOrganizingCommitteeToTournamentOrganizingCommitteeDto(
							tournamentOrganizingCommittee);
					tournamentOrganizingCommitteesDto.add(tournamentOrganizingCommitteeDto);
				}
			}
			responseMessage.setData(tournamentOrganizingCommitteesDto);
			responseMessage.setMessage(Constant.MSG_122);
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage updateTournamentOrganizingCommitteePaymentStatus(int tournamentOrganizingCommitteeId) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
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

			clubFund.setFundAmount(fundBalance);
			clubFundRepository.save(clubFund);

			TournamentOrganizingCommitteePaymentStatusReport tournamentOrganizingCommitteePaymentStatusReport = new TournamentOrganizingCommitteePaymentStatusReport();
			tournamentOrganizingCommitteePaymentStatusReport
					.setTournament(tournamentOrganizingCommittee.getTournament());
			tournamentOrganizingCommitteePaymentStatusReport.setUser(tournamentOrganizingCommittee.getUser());
			tournamentOrganizingCommitteePaymentStatusReport
					.setPaymentStatus(!tournamentOrganizingCommittee.isPaymentStatus());
			tournamentOrganizingCommitteePaymentStatusReport
					.setFundChange(tournamentOrganizingCommittee.isPaymentStatus() ? -tournamentFee : tournamentFee);
			tournamentOrganizingCommitteePaymentStatusReport.setFundBalance(fundBalance);
			tournamentOrganizingCommitteePaymentStatusReport.setCreatedBy("toandv");
			tournamentOrganizingCommitteePaymentStatusReport.setCreatedOn(LocalDateTime.now());
			tournamentOrganizingCommitteePaymentStatusReportRepository
					.save(tournamentOrganizingCommitteePaymentStatusReport);

			tournamentOrganizingCommittee.setPaymentStatus(!tournamentOrganizingCommittee.isPaymentStatus());
			tournamentOrganizingCommittee.setUpdatedBy("toandv");
			tournamentOrganizingCommittee.setUpdatedOn(LocalDateTime.now());
			tournamentOrganizingCommitteeRepository.save(tournamentOrganizingCommittee);

			TournamentOrganizingCommitteeDto tournamentOrganizingCommitteeDto = convertTournamentOrganizingCommitteeToTournamentOrganizingCommitteeDto(
					tournamentOrganizingCommittee);

			responseMessage.setData(Arrays.asList(tournamentOrganizingCommitteeDto));
			responseMessage.setMessage(Constant.MSG_123);
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage getAllTournamentOrganizingCommitteePaymentStatusReport(int tournamentId) {
		// TODO Auto-generated method stub
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

			responseMessage.setData(tournamentOrganizingCommitteePaymentStatusReportsDto);
			responseMessage.setMessage(Constant.MSG_124);
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage updateTournamentPlayerPaymentStatus(int tournamentPlayerId) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<TournamentPlayer> tournamentPlayerOp = tournamentPlayerRepository.findById(tournamentPlayerId);
			TournamentPlayer tournamentPlayer = tournamentPlayerOp.get();

			Tournament tournament = tournamentRepository.findByTournamentPlayers(tournamentPlayer).get();

			List<ClubFund> clubFunds = clubFundRepository.findAll();
			ClubFund clubFund = clubFunds.get(0);
			double fundAmount = clubFund.getFundAmount();

			double tournamentFee = tournament.getFeePlayerPay();

			double fundBalance = tournamentPlayer.isPaymentStatus() ? (fundAmount - tournamentFee)
					: (fundAmount + tournamentFee);

			clubFund.setFundAmount(fundBalance);
			clubFundRepository.save(clubFund);

			TournamentPlayerPaymentStatusReport tournamentPlayerPaymentStatusReport = new TournamentPlayerPaymentStatusReport();
			tournamentPlayerPaymentStatusReport.setTournament(tournament);
			tournamentPlayerPaymentStatusReport.setUser(tournamentPlayer.getUser());
			tournamentPlayerPaymentStatusReport.setPaymentStatus(!tournamentPlayer.isPaymentStatus());
			tournamentPlayerPaymentStatusReport
					.setFundChange(tournamentPlayer.isPaymentStatus() ? -tournamentFee : tournamentFee);
			tournamentPlayerPaymentStatusReport.setFundBalance(fundBalance);
			tournamentPlayerPaymentStatusReport.setCreatedBy("toandv");
			tournamentPlayerPaymentStatusReport.setCreatedOn(LocalDateTime.now());
			tournamentPlayerPaymentStatusReportRepository.save(tournamentPlayerPaymentStatusReport);

			tournamentPlayer.setPaymentStatus(!tournamentPlayer.isPaymentStatus());
			tournamentPlayer.setUpdatedBy("toandv");
			tournamentPlayer.setUpdatedOn(LocalDateTime.now());
			tournamentPlayerRepository.save(tournamentPlayer);

			TournamentPlayerDto tournamentPlayerDto = convertTournamentPlayer(tournamentPlayer);

			responseMessage.setData(Arrays.asList(tournamentPlayerDto));
			responseMessage.setMessage(Constant.MSG_125);
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage getAllTournamentPlayerPaymentStatusReport(int tournamentId) {
		// TODO Auto-generated method stub
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

			responseMessage.setData(tournamentPlayerPaymentStatusReportsDto);
			responseMessage.setMessage(Constant.MSG_126);
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage getAllCompetitivePlayerByType(int tournamentId, int competitiveTypeId) {
		// TODO Auto-generated method stub
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
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage registerToJoinTournamentOrganizingComittee(int tournamentId, String studentId, int roleId) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Tournament tournament = tournamentRepository.findById(tournamentId).get();
			if (LocalDateTime.now().isBefore(tournament.getRegistrationOrganizingCommitteeDeadline())) {
				User user = userRepository.findByStudentId(studentId).get();
				RoleEvent roleEvent = roleEventRepository.findById(roleId).get();

				List<TournamentOrganizingCommittee> tournamentOrganizingCommittees = tournamentOrganizingCommitteeRepository
						.findByTournamentId(tournament.getId());
				int countTournamentOrganizingCommittee = 0;
				for (TournamentOrganizingCommittee tournamentOrganizingCommittee : tournamentOrganizingCommittees) {
					if (user.getId() == tournamentOrganizingCommittee.getUser().getId()) {
						if (tournamentOrganizingCommittee.getRegisterStatus()
								.equals(Constant.REQUEST_STATUS_DECLINED)) {
							responseMessage
									.setMessage("Yêu cầu đăng kí tham gia vào ban tổ chức của bạn đã bị từ chối");
							return responseMessage;
						}
						responseMessage.setMessage("Bạn đã đăng ký tham gia ban tổ chức giải đấu rồi");
						return responseMessage;
					}
					if (tournamentOrganizingCommittee.getRegisterStatus().equals(Constant.REQUEST_STATUS_APPROVED)) {
						countTournamentOrganizingCommittee++;
					}
				}

				Optional<TournamentPlayer> tournamentPlayerOp = tournamentPlayerRepository
						.getPlayerByUserIdAndTournamentId(user.getId(), tournament.getId());
				if (tournamentPlayerOp.isPresent()) {
					responseMessage.setMessage("Bạn đã đăng ký tham gia giải đấu rồi");
					return responseMessage;
				}

				if (!roleEvent.getName().equals(Constant.ROLE_EVENT_MEMBER)) {
					if (countTournamentOrganizingCommittee < tournament.getMaxQuantityComitee()) {
						TournamentOrganizingCommittee tournamentOrganizingCommittee = new TournamentOrganizingCommittee();
						tournamentOrganizingCommittee.setTournament(tournament);
						tournamentOrganizingCommittee.setUser(user);
						tournamentOrganizingCommittee.setRoleEvent(roleEvent);
						tournamentOrganizingCommittee.setRegisterStatus(Constant.REQUEST_STATUS_PENDING);
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
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage registerToJoinTournamentCompetitiveType(int tournamentId, String studentId, double weight, int competitiveTypeId) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Tournament tournament = tournamentRepository.findById(tournamentId).get();
			if (LocalDateTime.now().isBefore(tournament.getRegistrationPlayerDeadline())) {
				User user = userRepository.findByStudentId(studentId).get();

				List<TournamentOrganizingCommittee> tournamentOrganizingCommittees = tournamentOrganizingCommitteeRepository
						.findByTournamentId(tournament.getId());
				for (TournamentOrganizingCommittee tournamentOrganizingCommittee : tournamentOrganizingCommittees) {
					if (user.getId() == tournamentOrganizingCommittee.getUser().getId()) {
						if (!tournamentOrganizingCommittee.getRegisterStatus()
								.equals(Constant.REQUEST_STATUS_DECLINED)) {
							responseMessage.setMessage("Bạn đã đăng ký tham gia ban tổ chức giải đấu rồi");
							return responseMessage;
						}
					}
				}

				Optional<CompetitiveType> competitveTypeOp = competitiveTypeRepository.findById(competitiveTypeId);
				CompetitiveType competitiveType = new CompetitiveType();
				if (competitveTypeOp.isPresent()) {
					competitiveType = competitveTypeOp.get();
					if (user.isGender() != competitiveType.isGender()){
						responseMessage.setMessage("Giới tính của bạn không phù hợp cho hạng cân này");
						return responseMessage;
					}
					if (weight < competitiveType.getWeightMin() || weight > competitiveType.getWeightMax()) {
						responseMessage.setMessage("Cân nặng của bạn không phù hợp cho hạng cân này");
						return responseMessage;
					}
				}
				else {
					responseMessage.setMessage("Không có hạng cân này");
					return responseMessage;
				}

				Optional<TournamentPlayer> tournamentPlayerOp = tournamentPlayerRepository
						.getPlayerByUserIdAndTournamentId(user.getId(), tournament.getId());

				if (!tournamentPlayerOp.isPresent()) {
					createTournamentPlayer(tournament, user);
					tournamentPlayerOp = tournamentPlayerRepository.getPlayerByUserIdAndTournamentId(user.getId(),
							tournament.getId());
				}

				TournamentPlayer tournamentPlayer = tournamentPlayerOp.get();
				Optional<CompetitivePlayer> competitivePlayerOp = competitivePlayerRepository
						.findByTournamentPlayerId(tournamentPlayer.getId());
				if (!competitivePlayerOp.isPresent()) {
					CompetitivePlayer competitivePlayer = new CompetitivePlayer();
					competitivePlayer.setTournamentPlayer(tournamentPlayer);

					competitivePlayer.setWeight(weight);

					competitivePlayer.setCreatedBy(user.getName() + " - " + user.getStudentId());
					competitivePlayer.setCreatedOn(LocalDateTime.now());
					competitivePlayerRepository.save(competitivePlayer);
					responseMessage.setData(Arrays.asList(competitivePlayer));
					responseMessage.setMessage("Đăng ký thành công");

					CompetitivePlayer getCompetitivePlayer = competitivePlayerRepository
							.findCompetitivePlayerByTournamentPlayerId(tournamentPlayer.getId()).get();
					CompetitivePlayerBracket newCompetitivePlayerBracket = new CompetitivePlayerBracket();
					newCompetitivePlayerBracket.setCompetitiveType(competitiveType);
					newCompetitivePlayerBracket.setCompetitivePlayer(getCompetitivePlayer);
					newCompetitivePlayerBracket.setCreatedBy(user.getName() + " - " + user.getStudentId());
					newCompetitivePlayerBracket.setCreatedOn(LocalDateTime.now());
					competitivePlayerBracketRepository.save(newCompetitivePlayerBracket);

				} else {
					responseMessage.setMessage("Bạn đã đăng ký vào giải này rồi");
				}
			} else {
				responseMessage.setMessage(Constant.MSG_131);
			}
		} catch (Exception e) {
			// TODO: handle exception
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

	@Override
	public List<Tournament> listTournamentsByRegistrationPlayerDeadline(LocalDateTime playerDeadline) {
		// TODO Auto-generated method stub
		try {
			List<Tournament> listTournaments = tournamentRepository.findAll();
			List<Tournament> listResult = new ArrayList<Tournament>();
			for (Tournament tournament : listTournaments) {
				if (tournament.getRegistrationPlayerDeadline().toLocalDate().isEqual(playerDeadline.toLocalDate())
						&& tournament.getRegistrationPlayerDeadline().getHour() == playerDeadline.getHour()) {
					listResult.add(tournament);
				}
			}
			return listResult;
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}

	public ResponseMessage registerToJoinTournamentExhibitionType(int tournamentId, String studentId,
			int exhibitionTypeId, String teamName, List<ActiveUserDto> activeUsersDto) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Tournament tournament = tournamentRepository.findById(tournamentId).get();
			if (LocalDateTime.now().isBefore(tournament.getRegistrationPlayerDeadline())) {
				User user = userRepository.findByStudentId(studentId).get();
				ExhibitionType exhibitionType = exhibitionTypeRepository.findById(exhibitionTypeId).get();

				int countMale = 0;
				int countFemale = 0;
				for (ActiveUserDto activeUserDto : activeUsersDto) {
					User member = userRepository.findByStudentId(activeUserDto.getStudentId()).get();
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
				for (ActiveUserDto activeUserDto : activeUsersDto) {
					User member = userRepository.findByStudentId(activeUserDto.getStudentId()).get();
					Optional<TournamentPlayer> tournamentPlayerOp = tournamentPlayerRepository
							.getPlayerByUserIdAndTournamentId(member.getId(), tournament.getId());
					if (!tournamentPlayerOp.isPresent()) {
						createTournamentPlayer(tournament, member);
						tournamentPlayerOp = tournamentPlayerRepository.getPlayerByUserIdAndTournamentId(member.getId(),
								tournament.getId());
					}
					TournamentPlayer tournamentPlayer = tournamentPlayerOp.get();
					ExhibitionPlayer exhibitionPlayer = new ExhibitionPlayer();
					exhibitionPlayer.setTournamentPlayer(tournamentPlayer);
					if (member.getStudentId().equals(user.getStudentId())) {
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
				ExhibitionTeamDto exhibitionTeamDto = convertToExhibitionTeamDto(exhibitionTeam, exhibitionType.getName(), exhibitionType.getId());

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
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage getAllUserCompetitivePlayer(int tournamentId, String studentId) {
		// TODO Auto-generated method stub
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
					CompetitivePlayerBracket competitivePlayerBracket = competitivePlayerBracketRepository
							.findByPlayerId(competitivePlayerOp.get().getId()).get();
					CompetitiveType competitiveType = competitivePlayerBracket.getCompetitiveType();
					List<CompetitivePlayerBracket> competitivePlayersBracket = competitivePlayerBracketRepository
							.listPlayersByType(competitiveType.getId());
					for (CompetitivePlayerBracket competitivePlayerBracket2 : competitivePlayersBracket) {
						CompetitivePlayerDto competitivePlayerDto = convertToCompetitivePlayerDto(
								competitivePlayerBracket2.getCompetitivePlayer());
						competitivePlayerDto.setWeightMin(competitiveType.getWeightMin());
						competitivePlayerDto.setWeightMax(competitiveType.getWeightMax());
						competitivePlayerDto.setCompetitiveTypeId(competitiveType.getId());
						competitivePlayersDto.add(competitivePlayerDto);
					}
					responseMessage.setData(competitivePlayersDto);
					responseMessage.setMessage("Lấy danh sách người chơi tham gia cùng hạng cân thành công");
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage getAllUserExhibitionPlayer(int tournamentId, String studentId) {
		// TODO Auto-generated method stub
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
									ExhibitionTeamDto exhibitionTeamDto = convertToExhibitionTeamDto(exhibitionTeam, exhibitionType.getName(), exhibitionType.getId());
									exhibitionTeamsDto.add(exhibitionTeamDto);
								}
							}
						}
					}
				}
				
				responseMessage.setData(exhibitionTeamsDto);
				responseMessage.setMessage("Lấy danh sách đội đã đăng ký tham gia thi đấu biểu diễn thành công");
			}
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage getAllUserOrganizingCommittee(int tournamentId, String studentId) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<Tournament> tournamentOp = tournamentRepository.findById(tournamentId);
			Tournament tournament = tournamentOp.get();
			User user = userRepository.findByStudentId(studentId).get();
			Optional<TournamentOrganizingCommittee> tournamentOrganizingCommitteeOp = tournamentOrganizingCommitteeRepository.findByTournamentIdAndUserId(tournament.getId(), user.getId());
			if (tournamentOrganizingCommitteeOp.isPresent()) {
				TournamentOrganizingCommittee userTournamentOrganizingCommittee = tournamentOrganizingCommitteeOp.get();
				if (userTournamentOrganizingCommittee.getRegisterStatus().equals(Constant.REQUEST_STATUS_APPROVED)){
					List<TournamentOrganizingCommittee> tournamentOrganizingCommittees = tournamentOrganizingCommitteeRepository
							.findByTournamentId(tournamentId);
					List<TournamentOrganizingCommitteeDto> tournamentOrganizingCommitteesDto = new ArrayList<TournamentOrganizingCommitteeDto>();
					for (TournamentOrganizingCommittee tournamentOrganizingCommittee : tournamentOrganizingCommittees) {
						if (tournamentOrganizingCommittee.getRegisterStatus().equals(Constant.REQUEST_STATUS_APPROVED)) {
							TournamentOrganizingCommitteeDto tournamentOrganizingCommitteeDto = new TournamentOrganizingCommitteeDto();
							tournamentOrganizingCommitteeDto.setUserName(tournamentOrganizingCommittee.getUser().getName());
							tournamentOrganizingCommitteeDto.setUserStudentId(tournamentOrganizingCommittee.getUser().getStudentId());
							RoleEventDto roleEventDto = new RoleEventDto();
							roleEventDto.setId(tournamentOrganizingCommittee.getRoleEvent().getId());
							roleEventDto.setName(tournamentOrganizingCommittee.getRoleEvent().getName());
							tournamentOrganizingCommitteeDto.setRoleTournamentDto(roleEventDto);
							Utils.convertNameOfEventRole(tournamentOrganizingCommittee.getRoleEvent(),
									tournamentOrganizingCommitteeDto.getRoleTournamentDto());
							tournamentOrganizingCommitteesDto.add(tournamentOrganizingCommitteeDto);
						}
					}
					responseMessage.setData(tournamentOrganizingCommitteesDto);
					responseMessage.setMessage("Lấy danh sách ban tổ chức giải đấu cho người dùng thành công");
				} else if (userTournamentOrganizingCommittee.getRegisterStatus().equals(Constant.REQUEST_STATUS_PENDING)) {
					responseMessage.setMessage("Đang chờ duyệt");
				} else {
					responseMessage.setMessage("Đã bị từ chối");
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
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
				
				Optional<TournamentOrganizingCommittee> tournamentOrganizingCommitteeOp = tournamentOrganizingCommitteeRepository.findByTournamentIdAndUserId(tournament.getId(), user.getId());
				if (tournamentOrganizingCommitteeOp.isPresent()) {
					TournamentOrganizingCommittee tournamentOrganizingCommittee = tournamentOrganizingCommitteeOp.get();
					if (tournamentOrganizingCommittee.getRegisterStatus().equals(Constant.REQUEST_STATUS_APPROVED)) {
						userTournamentDto.setJoined(true);
					}
					else {
						Optional<TournamentPlayer> tournamentPlayerOp = tournamentPlayerRepository.findPlayerByUserIdAndTournamentId(user.getId(), tournament.getId());
						if (tournamentPlayerOp.isPresent()) {
							userTournamentDto.setJoined(true);
						}
						else {
							userTournamentDto.setJoined(false);
						}
					}
				} else {
					Optional<TournamentPlayer> tournamentPlayerOp = tournamentPlayerRepository.findPlayerByUserIdAndTournamentId(user.getId(), tournament.getId());
					if (tournamentPlayerOp.isPresent()) {
						userTournamentDto.setJoined(true);
					}
					else {
						userTournamentDto.setJoined(false);
					}
				}
				
				userTournamentDto.setFeeOrganizingCommiteePay(tournament.getFeeOrganizingCommiteePay());
				userTournamentDto.setFeePlayerPay(tournament.getFeePlayerPay());
				userTournamentDto.setMaxQuantityComitee(tournament.getMaxQuantityComitee());
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
	public ResponseMessage addListTournamentOrganizingCommittee(String studentId, List<UserTournamentOrganizingCommitteeDto> users, int tournamentId) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			User user = userRepository.findByStudentId(studentId).get();
			Tournament tournament = tournamentRepository.findById(tournamentId).get();
			
			List<UserTournamentOrganizingCommitteeDto> usersNotAdd = new ArrayList<UserTournamentOrganizingCommitteeDto>();
			
			for (UserTournamentOrganizingCommitteeDto userToJoin : users) {
				if (!isJoinTournament(userToJoin.getUser().getId(), tournamentId)) {
					TournamentOrganizingCommittee tournamentOrganizingCommittee = new TournamentOrganizingCommittee();
					
					Optional<TournamentOrganizingCommittee> tournamentOrganizingCommitteeOp = tournamentOrganizingCommitteeRepository.findByTournamentIdAndUserId(tournamentId, userToJoin.getUser().getId());
					if(tournamentOrganizingCommitteeOp.isPresent()) {
						tournamentOrganizingCommittee = tournamentOrganizingCommitteeOp.get();
						if (!tournamentOrganizingCommittee.getRegisterStatus().equals(Constant.REQUEST_STATUS_APPROVED)) {
							RoleEvent roleEvent = roleEventRepository.findById(userToJoin.getRoleId()).get();
							tournamentOrganizingCommittee.setRoleEvent(roleEvent);
							tournamentOrganizingCommittee.setUpdatedBy(user.getName() + " - " + user.getStudentId());
							tournamentOrganizingCommittee.setUpdatedOn(LocalDateTime.now());
						}
					} else {
						tournamentOrganizingCommittee.setUser(userToJoin.getUser());
						RoleEvent roleEvent = roleEventRepository.findById(userToJoin.getRoleId()).get();
						tournamentOrganizingCommittee.setRoleEvent(roleEvent);
						tournamentOrganizingCommittee.setTournament(tournament);
						tournamentOrganizingCommittee.setRegisterStatus(Constant.REQUEST_STATUS_APPROVED);
						tournamentOrganizingCommittee.setPaymentStatus(false);
						tournamentOrganizingCommittee.setCreatedBy(user.getName() + " - " + user.getStudentId());
						tournamentOrganizingCommittee.setCreatedOn(LocalDateTime.now());
					}
					tournamentOrganizingCommitteeRepository.save(tournamentOrganizingCommittee);
				}
				else {
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
		Optional<TournamentPlayer> tournamentPlayerOp = tournamentPlayerRepository.findPlayerByUserIdAndTournamentId(userId, tournamentId);
		if (tournamentPlayerOp.isPresent()) {
			return true;
		}
		
		Optional<TournamentOrganizingCommittee> tournamentOrganizingCommitteeOp = tournamentOrganizingCommitteeRepository.findByTournamentIdAndUserId(tournamentId, userId);
		if(tournamentOrganizingCommitteeOp.isPresent()) {
			TournamentOrganizingCommittee tournamentOrganizingCommittee = tournamentOrganizingCommitteeOp.get();
			if (tournamentOrganizingCommittee.getRegisterStatus().equals(Constant.REQUEST_STATUS_APPROVED)) {
				return true;
			}
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
}
