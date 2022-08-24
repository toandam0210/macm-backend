package com.fpt.macm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fpt.macm.model.entity.CompetitiveTypeSample;
import com.fpt.macm.model.entity.ExhibitionTypeSample;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.service.TournamentSampleService;

@RestController
@RequestMapping("/api/tournament")
public class TournamentSampleController {

	@Autowired
	TournamentSampleService tournamentSampleService;

	@GetMapping("/headclub/getallsuggesttype")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_HeadTechnique','ROLE_ViceHeadTechnique')")
	ResponseEntity<ResponseMessage> getAllSuggestType() {
		return new ResponseEntity<ResponseMessage>(tournamentSampleService.getAllSuggestType(), HttpStatus.OK);
	}

	@PostMapping("/headclub/addcompetitivetypesample")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_HeadTechnique','ROLE_ViceHeadTechnique')")
	ResponseEntity<ResponseMessage> addCompetitiveTypeSample(@RequestBody CompetitiveTypeSample competitiveTypeSample) {
		return new ResponseEntity<ResponseMessage>(
				tournamentSampleService.addCompetitiveTypeSample(competitiveTypeSample), HttpStatus.OK);
	}

	@PutMapping("/headclub/updatecompetitivetypesample/{competitiveTypeSampleId}")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_HeadTechnique','ROLE_ViceHeadTechnique')")
	ResponseEntity<ResponseMessage> updateCompetitiveTypeSample(
			@PathVariable(name = "competitiveTypeSampleId") int competitiveTypeSampleId,
			@RequestBody CompetitiveTypeSample competitiveTypeSample) {
		return new ResponseEntity<ResponseMessage>(
				tournamentSampleService.updateCompetitiveTypeSample(competitiveTypeSampleId, competitiveTypeSample),
				HttpStatus.OK);
	}

	@DeleteMapping("/headclub/deletecompetitivetypesample/{competitiveTypeSampleId}")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_HeadTechnique','ROLE_ViceHeadTechnique')")
	ResponseEntity<ResponseMessage> deleteCompetitiveTypeSample(
			@PathVariable(name = "competitiveTypeSampleId") int competitiveTypeSampleId) {
		return new ResponseEntity<ResponseMessage>(
				tournamentSampleService.deleteCompetitiveTypeSample(competitiveTypeSampleId), HttpStatus.OK);
	}

	@PostMapping("/headclub/addexhibitiontypesample")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_HeadTechnique','ROLE_ViceHeadTechnique')")
	ResponseEntity<ResponseMessage> addExhibitionTypeSample(@RequestBody ExhibitionTypeSample exhibitionTypeSample) {
		return new ResponseEntity<ResponseMessage>(
				tournamentSampleService.addExhibitionTypeSample(exhibitionTypeSample), HttpStatus.OK);
	}

	@PutMapping("/headclub/updateexhibitiontypesample/{exhibitionTypeSampleId}")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_HeadTechnique','ROLE_ViceHeadTechnique')")
	ResponseEntity<ResponseMessage> updateExhibitionTypeSample(
			@PathVariable(name = "exhibitionTypeSampleId") int exhibitionTypeSampleId,
			@RequestBody ExhibitionTypeSample exhibitionTypeSample) {
		return new ResponseEntity<ResponseMessage>(
				tournamentSampleService.updateExhibitionTypeSample(exhibitionTypeSampleId, exhibitionTypeSample),
				HttpStatus.OK);
	}

	@DeleteMapping("/headclub/deleteexhibitiontypesample/{exhibitionTypeSampleId}")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_HeadTechnique','ROLE_ViceHeadTechnique')")
	ResponseEntity<ResponseMessage> deleteExhibitionTypeSample(
			@PathVariable(name = "exhibitionTypeSampleId") int exhibitionTypeSampleId) {
		return new ResponseEntity<ResponseMessage>(
				tournamentSampleService.deleteExhibitionTypeSample(exhibitionTypeSampleId), HttpStatus.OK);
	}

}
