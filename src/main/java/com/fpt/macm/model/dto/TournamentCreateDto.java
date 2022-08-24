package com.fpt.macm.model.dto;

import java.util.ArrayList;
import java.util.List;

import com.fpt.macm.model.entity.Tournament;

public class TournamentCreateDto {

	private Tournament tournament;
	private List<TournamentRoleDto> tournamentRolesDto = new ArrayList<TournamentRoleDto>();
	private List<ScheduleDto> listPreview = new ArrayList<ScheduleDto>();

	public Tournament getTournament() {
		return tournament;
	}

	public void setTournament(Tournament tournament) {
		this.tournament = tournament;
	}

	public List<TournamentRoleDto> getTournamentRolesDto() {
		return tournamentRolesDto;
	}

	public void setTournamentRolesDto(List<TournamentRoleDto> tournamentRolesDto) {
		this.tournamentRolesDto = tournamentRolesDto;
	}

	public List<ScheduleDto> getListPreview() {
		return listPreview;
	}

	public void setListPreview(List<ScheduleDto> listPreview) {
		this.listPreview = listPreview;
	}

}
