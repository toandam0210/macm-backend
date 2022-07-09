package com.fpt.macm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fpt.macm.dto.TournamentDto;
import com.fpt.macm.dto.TournamentOrganizingCommitteeDto;
import com.fpt.macm.model.ResponseMessage;
import com.fpt.macm.model.Tournament;
import com.fpt.macm.service.TournamentService;

@RestController
@RequestMapping("/api/tournament")
public class TournamentController {
	@Autowired
	TournamentService tournamentService;

	@PostMapping("/headclub/createtournament")
	ResponseEntity<ResponseMessage> createTournament(@RequestBody Tournament tournament) {
		return new ResponseEntity<ResponseMessage>(tournamentService.createTournament(tournament), HttpStatus.OK);
	}

	@GetMapping("/headclub/getalltournamentorganizingcommittee/{tournamentId}")
	ResponseEntity<ResponseMessage> getAllTournamentOrganizingCommittee(
			@PathVariable(name = "tournamentId") int tournamentId) {
		return new ResponseEntity<ResponseMessage>(
				tournamentService.getAllTournamentOrganizingCommitteeByTournamentId(tournamentId), HttpStatus.OK);
	}

	@PutMapping("/headclub/updatetournamentorganizingcommitteerole")
	ResponseEntity<ResponseMessage> updateTournamentOrganizingCommitteeRole(
			@RequestBody List<TournamentOrganizingCommitteeDto> tournamentOrganizingCommitteesDto) {
		return new ResponseEntity<ResponseMessage>(
				tournamentService.updateTournamentOrganizingCommitteeRole(tournamentOrganizingCommitteesDto),
				HttpStatus.OK);
	}

	@PutMapping("/headclub/update/{tournamentId}")
	ResponseEntity<ResponseMessage> updateTournament(@PathVariable(name = "tournamentId") int tournamentId,
			@RequestBody TournamentDto tournamentDto) {
		return new ResponseEntity<ResponseMessage>(tournamentService.updateTournament(tournamentId, tournamentDto),
				HttpStatus.OK);
	}

	@DeleteMapping("/headclub/delete/{tournamentId}")
	ResponseEntity<ResponseMessage> delete(@PathVariable(name = "tournamentId") int tournamentId) {
		return new ResponseEntity<ResponseMessage>(tournamentService.deleteTournamentById(tournamentId), HttpStatus.OK);
	}

	@GetMapping("/headclub/tournament/{tournamentId}")
	ResponseEntity<ResponseMessage> getTournamentById(@PathVariable(name = "tournamentId") int tournamentId) {
		return new ResponseEntity<ResponseMessage>(tournamentService.getTournamentById(tournamentId), HttpStatus.OK);
	}

	@GetMapping("/headclub/tournament/getall")
	ResponseEntity<ResponseMessage> getAllTournamentBySemester(@RequestParam String semester) {
		return new ResponseEntity<ResponseMessage>(tournamentService.getAllTournamentBySemester(semester),
				HttpStatus.OK);
	}

	@GetMapping("/headclub/getallcompetitiveplayer/{tournamentId}")
	ResponseEntity<ResponseMessage> getAllCompetitivePlayer(@PathVariable(name = "tournamentId") int tournamentId,
			@RequestParam(defaultValue = "0") double weightMin, @RequestParam(defaultValue = "0") double weightMax) {
		return new ResponseEntity<ResponseMessage>(
				tournamentService.getAllCompetitivePlayer(tournamentId, weightMin, weightMax), HttpStatus.OK);
	}

	@GetMapping("/headclub/getallcompetitiveplayerbytype/{tournamentId}")
	ResponseEntity<ResponseMessage> getAllCompetitivePlayerByType(@PathVariable(name = "tournamentId") int tournamentId,
			@RequestParam(defaultValue = "0") int competitiveTypeId) {
		return new ResponseEntity<ResponseMessage>(
				tournamentService.getAllCompetitivePlayerByType(tournamentId, competitiveTypeId), HttpStatus.OK);
	}
	
	@GetMapping("/headclub/getallexhibitionteam/{tournamentId}")
	ResponseEntity<ResponseMessage> getAllExhibitionTeam(@PathVariable(name = "tournamentId") int tournamentId,
			@RequestParam(defaultValue = "0") int exhibitionType) {
		return new ResponseEntity<ResponseMessage>(tournamentService.getAllExhibitionTeam(tournamentId, exhibitionType),
				HttpStatus.OK);
	}

	@GetMapping("/headclub/getallorganizingcommitteerole")
	ResponseEntity<ResponseMessage> getAllOrginizingCommitteeRole() {
		return new ResponseEntity<ResponseMessage>(tournamentService.getAllOrganizingCommitteeRole(), HttpStatus.OK);
	}

	@GetMapping("/headclub/getallexhibitiontype/{tournamentId}")
	ResponseEntity<ResponseMessage> getAllExhibitionType(@PathVariable(name = "tournamentId") int tournamentId) {
		return new ResponseEntity<ResponseMessage>(tournamentService.getAllExhibitionType(tournamentId), HttpStatus.OK);
	}

	@PutMapping("/headclub/acceptrequesttojoinorganizingcommittee/{organizingCommitteeId}")
	ResponseEntity<ResponseMessage> acceptRequestToJoinOrganizingCommittee(
			@PathVariable(name = "organizingCommitteeId") int organizingCommitteeId) {
		return new ResponseEntity<ResponseMessage>(
				tournamentService.acceptRequestOrganizingCommittee(organizingCommitteeId), HttpStatus.OK);
	}

	@PutMapping("/headclub/declinerequesttojoinorganizingcommittee/{organizingCommitteeId}")
	ResponseEntity<ResponseMessage> declineRequestToJoinOrganizingCommittee(
			@PathVariable(name = "organizingCommitteeId") int organizingCommitteeId) {
		return new ResponseEntity<ResponseMessage>(
				tournamentService.declineRequestOrganizingCommittee(organizingCommitteeId), HttpStatus.OK);
	}

	@GetMapping("/treasurer/getalltournamentplayerpaymentstatus/{tournamentId}")
	ResponseEntity<ResponseMessage> getAllTournamentPlayerPaymentStatus(
			@PathVariable(name = "tournamentId") int tournamentId) {
		return new ResponseEntity<ResponseMessage>(tournamentService.getAllTournamentPlayerPaymentStatus(tournamentId),
				HttpStatus.OK);
	}

	@GetMapping("/treasurer/getalltournamentorganizingcommitteepaymentstatus/{tournamentId}")
	ResponseEntity<ResponseMessage> getAllTournamentOrganizingCommitteePaymentStatus(
			@PathVariable(name = "tournamentId") int tournamentId) {
		return new ResponseEntity<ResponseMessage>(
				tournamentService.getAllTournamentOrganizingCommitteePaymentStatus(tournamentId), HttpStatus.OK);
	}

	@PutMapping("/treasurer/updatetournamentorganizingcommitteepaymentstatus/{tournamentOrganizingCommitteeId}")
	ResponseEntity<ResponseMessage> updateTournamentOrganizingCommitteePaymentStatus(
			@PathVariable(name = "tournamentOrganizingCommitteeId") int tournamentOrganizingCommitteeId) {
		return new ResponseEntity<ResponseMessage>(
				tournamentService.updateTournamentOrganizingCommitteePaymentStatus(tournamentOrganizingCommitteeId),
				HttpStatus.OK);
	}

	@GetMapping("/treasurer/getalltournamentorganizingcommitteepaymentstatusreport/{tournamentId}")
	ResponseEntity<ResponseMessage> getAllTournamentOrganizingCommitteePaymentStatusReport(
			@PathVariable(name = "tournamentId") int tournamentId) {
		return new ResponseEntity<ResponseMessage>(
				tournamentService.getAllTournamentOrganizingCommitteePaymentStatusReport(tournamentId), HttpStatus.OK);
	}

	@PutMapping("/treasurer/updatetournamentplayerpaymentstatus/{tournamentPlayerId}")
	ResponseEntity<ResponseMessage> updateTournamentPlayerPaymentStatus(
			@PathVariable(name = "tournamentPlayerId") int tournamentPlayerId) {
		return new ResponseEntity<ResponseMessage>(
				tournamentService.updateTournamentPlayerPaymentStatus(tournamentPlayerId), HttpStatus.OK);
	}

	@GetMapping("/treasurer/getalltournamentplayerpaymentstatusreport/{tournamentId}")
	ResponseEntity<ResponseMessage> getAllTournamentPlayerPaymentStatusReport(
			@PathVariable(name = "tournamentId") int tournamentId) {
		return new ResponseEntity<ResponseMessage>(
				tournamentService.getAllTournamentPlayerPaymentStatusReport(tournamentId), HttpStatus.OK);
	}
	
	
}
