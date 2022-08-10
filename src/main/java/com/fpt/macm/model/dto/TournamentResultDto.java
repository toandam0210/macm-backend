package com.fpt.macm.model.dto;

import java.util.List;

import com.fpt.macm.model.response.ResponseMessage;

public class TournamentResultDto {
	private List<ResponseMessage> listCompetitiveResult;
	private List<ResponseMessage> listExhibitionResult;
	
	public List<ResponseMessage> getListCompetitiveResult() {
		return listCompetitiveResult;
	}
	public void setListCompetitiveResult(List<ResponseMessage> listCompetitiveResult) {
		this.listCompetitiveResult = listCompetitiveResult;
	}
	public List<ResponseMessage> getListExhibitionResult() {
		return listExhibitionResult;
	}
	public void setListExhibitionResult(List<ResponseMessage> listExhibitionResult) {
		this.listExhibitionResult = listExhibitionResult;
	}
}
