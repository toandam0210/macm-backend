package com.fpt.macm.dto;

import java.util.Set;

public class ExhibitionTeamDto {

	private int id;
	private String teamName;
	private Set<ExhibitionPlayerDto> exhibitionPlayersDto;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public Set<ExhibitionPlayerDto> getExhibitionPlayersDto() {
		return exhibitionPlayersDto;
	}

	public void setExhibitionPlayersDto(Set<ExhibitionPlayerDto> exhibitionPlayersDto) {
		this.exhibitionPlayersDto = exhibitionPlayersDto;
	}

}