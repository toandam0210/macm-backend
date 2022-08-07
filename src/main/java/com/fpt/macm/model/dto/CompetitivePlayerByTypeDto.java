package com.fpt.macm.model.dto;

import java.util.List;

import com.fpt.macm.model.entity.CompetitivePlayer;

public class CompetitivePlayerByTypeDto {
	private String name;
	
	private Integer status;
	
	private Boolean changed;
	
	List<CompetitivePlayer> listPlayers;

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

	public List<CompetitivePlayer> getListPlayers() {
		return listPlayers;
	}

	public void setListPlayers(List<CompetitivePlayer> listPlayers) {
		this.listPlayers = listPlayers;
	}
	
	
}
