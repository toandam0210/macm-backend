package com.fpt.macm.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpt.macm.dto.RoleEventDto;
import com.fpt.macm.dto.TournamentOrganizingCommitteeDto;
import com.fpt.macm.model.Constant;
import com.fpt.macm.model.ResponseMessage;
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
				TournamentOrganizingCommitteeDto tournamentOrganizingCommitteeDto = new TournamentOrganizingCommitteeDto();
				tournamentOrganizingCommitteeDto.setId(tournamentOrganizingCommittee.getId());
				tournamentOrganizingCommitteeDto.setUserName(tournamentOrganizingCommittee.getUser().getName());
				tournamentOrganizingCommitteeDto
						.setUserStudentId(tournamentOrganizingCommittee.getUser().getStudentId());
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

}
