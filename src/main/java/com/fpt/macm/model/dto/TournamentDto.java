package com.fpt.macm.model.dto;

import java.time.LocalDate;
import java.util.Set;

import com.fpt.macm.model.entity.TournamentPlayer;

public class TournamentDto {
	private int id;

	private String name;

	private String description;

	private int maxQuantityComitee;

	private double feeOrganizingCommiteePay;

	private Set<CompetitiveTypeDto> competitiveTypesDto;

	private Set<ExhibitionTypeDto> exhibitionTypesDto;

	private String status;

	private LocalDate startDate;

	private Set<TournamentPlayer> tournamentPlayers;
	
	private double totalAmount;
	
	private double feePlayerPay;
	
	private double totalAmountEstimate;
	
	private double totalAmountFromClubEstimate;
	
	private double totalAmountFromClubActual;

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

	public Set<TournamentPlayer> getTournamentPlayers() {
		return tournamentPlayers;
	}

	public void setTournamentPlayers(Set<TournamentPlayer> tournamentPlayers) {
		this.tournamentPlayers = tournamentPlayers;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public double getFeeOrganizingCommiteePay() {
		return feeOrganizingCommiteePay;
	}

	public void setFeeOrganizingCommiteePay(double feeOrganizingCommiteePay) {
		this.feeOrganizingCommiteePay = feeOrganizingCommiteePay;
	}

	public double getFeePlayerPay() {
		return feePlayerPay;
	}

	public void setFeePlayerPay(double feePlayerPay) {
		this.feePlayerPay = feePlayerPay;
	}

	public double getTotalAmountEstimate() {
		return totalAmountEstimate;
	}

	public void setTotalAmountEstimate(double totalAmountEstimate) {
		this.totalAmountEstimate = totalAmountEstimate;
	}

	public double getTotalAmountFromClubEstimate() {
		return totalAmountFromClubEstimate;
	}

	public void setTotalAmountFromClubEstimate(double totalAmountFromClubEstimate) {
		this.totalAmountFromClubEstimate = totalAmountFromClubEstimate;
	}

	public double getTotalAmountFromClubActual() {
		return totalAmountFromClubActual;
	}

	public void setTotalAmountFromClubActual(double totalAmountFromClubActual) {
		this.totalAmountFromClubActual = totalAmountFromClubActual;
	}
	
	

}
