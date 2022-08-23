package com.fpt.macm.model.dto;

import com.fpt.macm.model.entity.CompetitiveType;
import com.fpt.macm.model.entity.User;

public class CompetitiveResultByTypeDto {
	private CompetitiveType competitiveType;
	private User[] listResult;
	public CompetitiveType getCompetitiveType() {
		return competitiveType;
	}
	public void setCompetitiveType(CompetitiveType competitiveType) {
		this.competitiveType = competitiveType;
	}
	public User[] getListResult() {
		return listResult;
	}
	public void setListResult(User[] listResult) {
		this.listResult = listResult;
	}
	
	
}
