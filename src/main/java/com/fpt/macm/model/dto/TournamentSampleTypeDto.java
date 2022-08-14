package com.fpt.macm.model.dto;

import java.util.ArrayList;
import java.util.List;

import com.fpt.macm.model.entity.CompetitiveTypeSample;
import com.fpt.macm.model.entity.ExhibitionTypeSample;

public class TournamentSampleTypeDto {

	private List<CompetitiveTypeSample> competitiveTypeSamples = new ArrayList<CompetitiveTypeSample>();
	private List<ExhibitionTypeSample> exhibitionTypeSamples = new ArrayList<ExhibitionTypeSample>();

	public List<CompetitiveTypeSample> getCompetitiveTypeSamples() {
		return competitiveTypeSamples;
	}

	public void setCompetitiveTypeSamples(List<CompetitiveTypeSample> competitiveTypeSamples) {
		this.competitiveTypeSamples = competitiveTypeSamples;
	}

	public List<ExhibitionTypeSample> getExhibitionTypeSamples() {
		return exhibitionTypeSamples;
	}

	public void setExhibitionTypeSamples(List<ExhibitionTypeSample> exhibitionTypeSamples) {
		this.exhibitionTypeSamples = exhibitionTypeSamples;
	}

}
