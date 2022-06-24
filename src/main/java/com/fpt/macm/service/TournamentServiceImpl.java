package com.fpt.macm.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpt.macm.dto.RoleEventDto;
import com.fpt.macm.dto.TournamentOrganizingCommitteeDto;
import com.fpt.macm.model.Constant;
import com.fpt.macm.model.ResponseMessage;
import com.fpt.macm.model.RoleEvent;
import com.fpt.macm.model.TournamentOrganizingCommittee;
import com.fpt.macm.repository.TournamentOrganizingCommitteeRepository;
import com.fpt.macm.utils.Utils;

@Service
public class TournamentServiceImpl implements TournamentService {

	@Autowired
	TournamentOrganizingCommitteeRepository tournamentOrganizingCommitteeRepository;

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

}
