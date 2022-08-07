package com.fpt.macm.model.dto;

import java.util.List;

public class CompetitiveMatchByTypeDto {
	private String name;
	
	private Integer status;
	
	private Boolean changed;
	
	List<CompetitiveMatchDto> listMatchDto;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Boolean getChanged() {
		return changed;
	}

	public void setChanged(Boolean changed) {
		this.changed = changed;
	}

	public List<CompetitiveMatchDto> getListMatchDto() {
		return listMatchDto;
	}

	public void setListMatchDto(List<CompetitiveMatchDto> listMatchDto) {
		this.listMatchDto = listMatchDto;
	}
	
	
}
