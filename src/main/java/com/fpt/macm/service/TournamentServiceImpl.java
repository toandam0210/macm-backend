package com.fpt.macm.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpt.macm.constant.Constant;
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
import com.fpt.macm.model.entity.ClubFund;
import com.fpt.macm.model.entity.CompetitivePlayer;
import com.fpt.macm.model.entity.CompetitiveType;
import com.fpt.macm.model.entity.ExhibitionPlayer;
import com.fpt.macm.model.entity.ExhibitionTeam;
import com.fpt.macm.model.entity.ExhibitionType;
import com.fpt.macm.model.entity.RoleEvent;
import com.fpt.macm.model.entity.Semester;
import com.fpt.macm.model.entity.Tournament;
import com.fpt.macm.model.entity.TournamentOrganizingCommittee;
import com.fpt.macm.model.entity.TournamentOrganizingCommitteePaymentStatusReport;
import com.fpt.macm.model.entity.TournamentPlayer;
import com.fpt.macm.model.entity.TournamentPlayerPaymentStatusReport;
import com.fpt.macm.model.entity.TournamentSchedule;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.repository.ClubFundRepository;
import com.fpt.macm.repository.CompetitivePlayerRepository;
import com.fpt.macm.repository.CompetitiveTypeRepository;
import com.fpt.macm.repository.ExhibitionPlayerRepository;
import com.fpt.macm.repository.ExhibitionTeamRepository;
import com.fpt.macm.repository.ExhibitionTypeRepository;
import com.fpt.macm.repository.RoleEventRepository;
import com.fpt.macm.repository.SemesterRepository;
import com.fpt.macm.repository.TournamentOrganizingCommitteePaymentStatusReportRepository;
import com.fpt.macm.repository.TournamentOrganizingCommitteeRepository;
import com.fpt.macm.repository.TournamentPlayerPaymentStatusReportRepository;
import com.fpt.macm.repository.TournamentPlayerRepository;
import com.fpt.macm.repository.TournamentRepository;
import com.fpt.macm.repository.TournamentScheduleRepository;
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
	TournamentOrganizingCommitteePaymentStatusReportRepository tournamentOrganizingCommitteePaymentStatusReportRepository;

	@Autowired
	TournamentPlayerPaymentStatusReportRepository tournamentPlayerPaymentStatusReportRepository;

	@Override
	public ResponseMessage createTournament(Tournament tournament) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Semester semester = (Semester) semesterService.getCurrentSemester().getData().get(0);
			tournament.setSemester(semester.getName());
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
				tournament.setMaxQuantityComitee(tournamentDto.getMaxQuantityComitee());
				tournament.setDescription(tournamentDto.getDescription());
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
	public ResponseMessage getAllTournamentBySemester(String semester) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			if (semester == "") {
				semester = semesterRepository.findTop3Semester().get(0).getName();
			}
			List<Tournament> tournaments = tournamentRepository.findBySemester(semester);
			List<TournamentDto> tournamentDtos = new ArrayList<TournamentDto>();
			for (Tournament tournament : tournaments) {
				LocalDate startDate = getStartDate(tournament.getId());
				TournamentDto tournamentDto = new TournamentDto();
				if (startDate != null) {
					LocalDate endDate = getEndDate(tournament.getId());
					if (LocalDate.now().isBefore(startDate)) {
						tournamentDto.setStatus("Chưa diễn ra");
					} else if (LocalDate.now().isAfter(endDate)) {
						tournamentDto.setStatus("Đã kết thúc");
					} else {
						tournamentDto.setStatus("Đang diễn ra");
					}
				} else {
					tournamentDto.setStatus("Chưa diễn ra");
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
				tournamentDtos.add(tournamentDto);

			}
			responseMessage.setData(tournamentDtos);
			responseMessage.setMessage("Lấy danh sách giải đấu thành công" + semester);
			responseMessage.setTotalResult(tournamentDtos.size());

		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

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
				Optional<CompetitivePlayer> competitivePlayerOp = competitivePlayerRepository.findByTournamentPlayerId(tournamentPlayer.getId());
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
						exhibitionTeamsDto.add(convertToExhibitionTeamDto(exhibitionTeam, exhibitionType.getName()));
					}
				} else {
					// filter theo hạng mục thi đấu
					if (exhibitionType.getId() == exhibitionTypeId) {
						Set<ExhibitionTeam> exhibitionTeams = exhibitionType.getExhibitionTeams();
						for (ExhibitionTeam exhibitionTeam : exhibitionTeams) {
							exhibitionTeamsDto
									.add(convertToExhibitionTeamDto(exhibitionTeam, exhibitionType.getName()));
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

	private ExhibitionTeamDto convertToExhibitionTeamDto(ExhibitionTeam exhibitionTeam, String exhibitionTypeName) {
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
		return exhibitionTeamDto;
	}

	private ExhibitionPlayerDto convertToExhibitionPlayerDto(ExhibitionPlayer exhibitionPlayer) {
		ExhibitionPlayerDto exhibitionPlayerDto = new ExhibitionPlayerDto();
		exhibitionPlayerDto.setId(exhibitionPlayer.getId());
		exhibitionPlayerDto.setPlayerId(exhibitionPlayer.getTournamentPlayer().getId());
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
				Optional<CompetitivePlayer> competitivePlayerOp = competitivePlayerRepository.findByTournamentPlayerId(tournamentPlayer.getId());
				if (competitivePlayerOp.isPresent()) {
					CompetitivePlayer competitivePlayer = competitivePlayerOp.get();
					if (competitivePlayer.getWeight() <= weightMax && competitivePlayer.getWeight() >= weightMin && competitivePlayer.getTournamentPlayer().getUser().isGender() == gender) {
						CompetitivePlayerDto competitivePlayerDto = convertToCompetitivePlayerDto(
								competitivePlayer);
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

}
