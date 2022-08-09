package com.fpt.macm.model.dto;

import java.util.ArrayList;
import java.util.List;

import com.fpt.macm.model.entity.Tournament;

public class TournamentCreateDto {

	private Tournament tournament;
	private List<RoleEventDto> rolesEventDto = new ArrayList<RoleEventDto>();
	private List<ScheduleDto> listPreview = new ArrayList<ScheduleDto>();

	public Tournament getTournament() {
		return tournament;
	}

	public void setTournament(Tournament tournament) {
		this.tournament = tournament;
	}

	public List<RoleEventDto> getRolesEventDto() {
		return rolesEventDto;
	}

	public void setRolesEventDto(List<RoleEventDto> rolesEventDto) {
		this.rolesEventDto = rolesEventDto;
	}

	public List<ScheduleDto> getListPreview() {
		return listPreview;
	}

	public void setListPreview(List<ScheduleDto> listPreview) {
		this.listPreview = listPreview;
	}

}
