package com.fpt.macm.model.dto;

import java.util.Set;

public class ExhibitionTeamDto implements Comparable<ExhibitionTeamDto>{

	private int id;
	private String teamName;
	private Set<ExhibitionPlayerDto> exhibitionPlayersDto;
	private String exhibitionTypeName;
	private Double score;

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

	public String getExhibitionTypeName() {
		return exhibitionTypeName;
	}

	public void setExhibitionTypeName(String exhibitionTypeName) {
		this.exhibitionTypeName = exhibitionTypeName;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	@Override
	public int compareTo(ExhibitionTeamDto o) {
		// TODO Auto-generated method stub
		if(o.getScore() - this.getScore() > 0) return 1;
		if(o.getScore() - this.getScore() < 0) return -1;
		return 0;
	}

}
