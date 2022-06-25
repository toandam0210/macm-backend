package com.fpt.macm.service;

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

import com.fpt.macm.dto.CompetitiveTypeDto;
import com.fpt.macm.dto.ExhibitionTypeDto;
import com.fpt.macm.dto.RoleEventDto;
import com.fpt.macm.dto.TournamentDto;
import com.fpt.macm.dto.TournamentOrganizingCommitteeDto;
import com.fpt.macm.model.CompetitiveType;
import com.fpt.macm.model.Constant;
import com.fpt.macm.model.ExhibitionType;
import com.fpt.macm.model.ResponseMessage;
import com.fpt.macm.model.RoleEvent;
import com.fpt.macm.model.Semester;
import com.fpt.macm.model.Tournament;
import com.fpt.macm.model.TournamentOrganizingCommittee;
import com.fpt.macm.repository.CompetitiveTypeRepository;
import com.fpt.macm.repository.ExhibitionTypeRepository;
import com.fpt.macm.repository.TournamentOrganizingCommitteeRepository;
import com.fpt.macm.repository.TournamentRepository;
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

	@Override
	public ResponseMessage createTournament(Tournament tournament) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Semester semester = (Semester) semesterService.getCurrentSemester().getData().get(0);
			tournament.setSemester(semester.getName());
			tournament.setCreatedBy("toandv");
			tournament.setCreatedOn(LocalDateTime.now());
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
			List<TournamentOrganizingCommittee> tournamentOrganizingCommittees = tournamentOrganizingCommitteeRepository
					.findByTournamentId(tournamentId);
			List<TournamentOrganizingCommitteeDto> tournamentOrganizingCommitteesDto = new ArrayList<TournamentOrganizingCommitteeDto>();
			for (TournamentOrganizingCommittee tournamentOrganizingCommittee : tournamentOrganizingCommittees) {
				TournamentOrganizingCommitteeDto tournamentOrganizingCommitteeDto = convertTournamentOrganizingCommitteeToTournamentOrganizingCommitteeDto(
						tournamentOrganizingCommittee);
				tournamentOrganizingCommitteesDto.add(tournamentOrganizingCommitteeDto);
			}
			responseMessage.setData(tournamentOrganizingCommitteesDto);
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
				tournament.setAmount_per_register(tournamentDto.getAmount_per_register());
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
			if(tournamentOp.isPresent()) {
				Tournament tournament = tournamentOp.get();
				tournamentRepository.save(tournament);
				responseMessage.setMessage("Xóa giải đấu thành công");
			}
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

}
