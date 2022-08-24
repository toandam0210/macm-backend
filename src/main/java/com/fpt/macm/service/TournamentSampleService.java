package com.fpt.macm.service;

import com.fpt.macm.model.entity.CompetitiveTypeSample;
import com.fpt.macm.model.entity.ExhibitionTypeSample;
import com.fpt.macm.model.response.ResponseMessage;

public interface TournamentSampleService {

	ResponseMessage getAllSuggestType();
	ResponseMessage addCompetitiveTypeSample(CompetitiveTypeSample competitiveTypeSample);
	ResponseMessage updateCompetitiveTypeSample(int competitiveTypeSampleId, CompetitiveTypeSample newCompetitiveTypeSample);
	ResponseMessage deleteCompetitiveTypeSample(int competitiveTypeSampleId);
	ResponseMessage addExhibitionTypeSample(ExhibitionTypeSample exhibitionTypeSample);
	ResponseMessage updateExhibitionTypeSample(int exhibitionTypeSampleId, ExhibitionTypeSample newExhibitionTypeSample);
	ResponseMessage deleteExhibitionTypeSample(int exhibitionTypeSampleId);
	
}
