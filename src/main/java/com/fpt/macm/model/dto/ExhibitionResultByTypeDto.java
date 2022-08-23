package com.fpt.macm.model.dto;

import java.util.List;

import com.fpt.macm.model.entity.ExhibitionType;

public class ExhibitionResultByTypeDto {
	private ExhibitionType exhibitionType;
	private List<ExhibitionTeamDto> listResult;
	
	public ExhibitionType getExhibitionType() {
		return exhibitionType;
	}
	public void setExhibitionType(ExhibitionType exhibitionType) {
		this.exhibitionType = exhibitionType;
	}
	public List<ExhibitionTeamDto> getListResult() {
		return listResult;
	}
	public void setListResult(List<ExhibitionTeamDto> listResult) {
		this.listResult = listResult;
	}
}
