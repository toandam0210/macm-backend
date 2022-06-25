package com.fpt.macm.dto;

import java.time.LocalDate;
import java.util.Set;

public class TournamentDto {
	private int id;

	private String name;

	private String description;

	private int maxQuantityComitee;

	private double amount_per_register;

	private Set<CompetitiveTypeDto> competitiveTypesDto;
	
	private Set<ExhibitionTypeDto> exhibitionTypesDto;
	
	private String status;
	
	private LocalDate startDate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getMaxQuantityComitee() {
		return maxQuantityComitee;
	}

	public void setMaxQuantityComitee(int maxQuantityComitee) {
		this.maxQuantityComitee = maxQuantityComitee;
	}

	public double getAmount_per_register() {
		return amount_per_register;
	}

	public void setAmount_per_register(double amount_per_register) {
		this.amount_per_register = amount_per_register;
	}

	public Set<CompetitiveTypeDto> getCompetitiveTypesDto() {
		return competitiveTypesDto;
	}

	public void setCompetitiveTypesDto(Set<CompetitiveTypeDto> competitiveTypesDto) {
		this.competitiveTypesDto = competitiveTypesDto;
	}

	public Set<ExhibitionTypeDto> getExhibitionTypesDto() {
		return exhibitionTypesDto;
	}

	public void setExhibitionTypesDto(Set<ExhibitionTypeDto> exhibitionTypesDto) {
		this.exhibitionTypesDto = exhibitionTypesDto;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	
	
}
