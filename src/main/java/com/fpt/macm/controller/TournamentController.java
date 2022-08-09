package com.fpt.macm.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fpt.macm.model.dto.ActiveUserDto;
import com.fpt.macm.model.dto.TournamentCreateDto;
import com.fpt.macm.model.dto.TournamentDto;
import com.fpt.macm.model.dto.TournamentOrganizingCommitteeDto;
import com.fpt.macm.model.dto.UserTournamentOrganizingCommitteeDto;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.service.CompetitiveTypeService;
import com.fpt.macm.service.TournamentService;

@RestController
@RequestMapping("/api/tournament")
public class TournamentController {
	@Autowired
	TournamentService tournamentService;

	@Autowired
	CompetitiveTypeService competitiveTypeService;

	@PostMapping("/headclub/createtournament")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_HeadTechnique','ROLE_ViceHeadTechnique')")
	ResponseEntity<ResponseMessage> createTournament(@RequestBody TournamentCreateDto tournamentCreateDto,
			@RequestParam boolean isOverwritten) {
		return new ResponseEntity<ResponseMessage>(
				tournamentService.createTournament(tournamentCreateDto, isOverwritten), HttpStatus.OK);
	}

	@GetMapping("/headclub/getalltournamentorganizingcommittee/{tournamentId}")
	ResponseEntity<ResponseMessage> getAllTournamentOrganizingCommittee(
			@PathVariable(name = "tournamentId") int tournamentId) {
		return new ResponseEntity<ResponseMessage>(
				tournamentService.getAllTournamentOrganizingCommitteeByTournamentId(tournamentId), HttpStatus.OK);
	}

	@PutMapping("/headclub/updatetournamentorganizingcommitteerole")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_HeadTechnique','ROLE_ViceHeadTechnique')")
	ResponseEntity<ResponseMessage> updateTournamentOrganizingCommitteeRole(
			@RequestBody List<TournamentOrganizingCommitteeDto> tournamentOrganizingCommitteesDto) {
		return new ResponseEntity<ResponseMessage>(
				tournamentService.updateTournamentOrganizingCommitteeRole(tournamentOrganizingCommitteesDto),
				HttpStatus.OK);
	}

	@PutMapping("/headclub/update/{tournamentId}")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_HeadTechnique','ROLE_ViceHeadTechnique')")
	ResponseEntity<ResponseMessage> updateTournament(@PathVariable(name = "tournamentId") int tournamentId,
			@RequestBody TournamentDto tournamentDto) {
		return new ResponseEntity<ResponseMessage>(tournamentService.updateTournament(tournamentId, tournamentDto),
				HttpStatus.OK);
	}

	@DeleteMapping("/headclub/delete/{tournamentId}")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_HeadTechnique','ROLE_ViceHeadTechnique')")
	ResponseEntity<ResponseMessage> delete(@PathVariable(name = "tournamentId") int tournamentId) {
		return new ResponseEntity<ResponseMessage>(tournamentService.deleteTournamentById(tournamentId), HttpStatus.OK);
	}

	@GetMapping("/headclub/tournament/{tournamentId}")
	ResponseEntity<ResponseMessage> getTournamentById(@PathVariable(name = "tournamentId") int tournamentId) {
		return new ResponseEntity<ResponseMessage>(tournamentService.getTournamentById(tournamentId), HttpStatus.OK);
	}

	@GetMapping("/headclub/tournament/getall")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_HeadTechnique','ROLE_ViceHeadTechnique')")
	ResponseEntity<ResponseMessage> getAllTournamentBySemester(@RequestParam String semester,
			@RequestParam int status) {
		return new ResponseEntity<ResponseMessage>(tournamentService.getAllTournamentBySemester(semester, status),
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

	@GetMapping("/headclub/getallorganizingcommitteerole/{tournamentId}")
	ResponseEntity<ResponseMessage> getAllOrganizingCommitteeRoleByTournamentId(@PathVariable(name = "tournamentId") int tournamentId) {
		return new ResponseEntity<ResponseMessage>(tournamentService.getAllOrganizingCommitteeRoleByTournamentId(tournamentId), HttpStatus.OK);
	}

	@GetMapping("/headclub/getallexhibitiontype/{tournamentId}")
	ResponseEntity<ResponseMessage> getAllExhibitionType(@PathVariable(name = "tournamentId") int tournamentId) {
		return new ResponseEntity<ResponseMessage>(tournamentService.getAllExhibitionType(tournamentId), HttpStatus.OK);
	}

	@GetMapping("/treasurer/getalltournamentplayerpaymentstatus/{tournamentId}")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_Treasure')")
	ResponseEntity<ResponseMessage> getAllTournamentPlayerPaymentStatus(
			@PathVariable(name = "tournamentId") int tournamentId) {
		return new ResponseEntity<ResponseMessage>(tournamentService.getAllTournamentPlayerPaymentStatus(tournamentId),
				HttpStatus.OK);
	}

	@GetMapping("/treasurer/getalltournamentorganizingcommitteepaymentstatus/{tournamentId}")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_Treasure')")
	ResponseEntity<ResponseMessage> getAllTournamentOrganizingCommitteePaymentStatus(
			@PathVariable(name = "tournamentId") int tournamentId) {
		return new ResponseEntity<ResponseMessage>(
				tournamentService.getAllTournamentOrganizingCommitteePaymentStatus(tournamentId), HttpStatus.OK);
	}

	@PutMapping("/treasurer/updatetournamentorganizingcommitteepaymentstatus/{tournamentOrganizingCommitteeId}")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_Treasure')")
	ResponseEntity<ResponseMessage> updateTournamentOrganizingCommitteePaymentStatus(
			@PathVariable(name = "tournamentOrganizingCommitteeId") int tournamentOrganizingCommitteeId) {
		return new ResponseEntity<ResponseMessage>(
				tournamentService.updateTournamentOrganizingCommitteePaymentStatus(tournamentOrganizingCommitteeId),
				HttpStatus.OK);
	}

	@GetMapping("/treasurer/getalltournamentorganizingcommitteepaymentstatusreport/{tournamentId}")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_Treasure')")
	ResponseEntity<ResponseMessage> getAllTournamentOrganizingCommitteePaymentStatusReport(
			@PathVariable(name = "tournamentId") int tournamentId) {
		return new ResponseEntity<ResponseMessage>(
				tournamentService.getAllTournamentOrganizingCommitteePaymentStatusReport(tournamentId), HttpStatus.OK);
	}

	@PutMapping("/treasurer/updatetournamentplayerpaymentstatus/{tournamentPlayerId}")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_Treasure')")
	ResponseEntity<ResponseMessage> updateTournamentPlayerPaymentStatus(
			@PathVariable(name = "tournamentPlayerId") int tournamentPlayerId) {
		return new ResponseEntity<ResponseMessage>(
				tournamentService.updateTournamentPlayerPaymentStatus(tournamentPlayerId), HttpStatus.OK);
	}

	@GetMapping("/treasurer/getalltournamentplayerpaymentstatusreport/{tournamentId}")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_Treasure')")
	ResponseEntity<ResponseMessage> getAllTournamentPlayerPaymentStatusReport(
			@PathVariable(name = "tournamentId") int tournamentId) {
		return new ResponseEntity<ResponseMessage>(
				tournamentService.getAllTournamentPlayerPaymentStatusReport(tournamentId), HttpStatus.OK);
	}

	@GetMapping("/treasurer/getallcompetitivetype/{tournamentId}")
	ResponseEntity<ResponseMessage> getAllCompetitiveType(@PathVariable(name = "tournamentId") int tournamentId) {
		return new ResponseEntity<ResponseMessage>(competitiveTypeService.getAllType(tournamentId), HttpStatus.OK);
	}

	@PostMapping("/registertojoinorganizingcommittee/{tournamentId}/{studentId}/{roleId}")
	ResponseEntity<ResponseMessage> registerToJoinOrganizingCommittee(
			@PathVariable(name = "tournamentId") int tournamentId, @PathVariable(name = "studentId") String studentId,
			@PathVariable(name = "roleId") int roleId) {
		return new ResponseEntity<ResponseMessage>(
				tournamentService.registerToJoinTournamentOrganizingComittee(tournamentId, studentId, roleId),
				HttpStatus.OK);
	}

	@PostMapping("/registertojointournamentcompetitivetype/{tournamentId}/{studentId}")
	ResponseEntity<ResponseMessage> registerToJoinTournamentCompetitveType(
			@PathVariable(name = "tournamentId") int tournamentId, @PathVariable(name = "studentId") String studentId,
			@RequestParam(defaultValue = "0") double weight, @RequestParam(defaultValue = "0") int competitiveTypeId) {
		return new ResponseEntity<ResponseMessage>(tournamentService.registerToJoinTournamentCompetitiveType(
				tournamentId, studentId, weight, competitiveTypeId), HttpStatus.OK);
	}

	@PostMapping("/registertojointournamentexhibitiontype/{tournamentId}/{studentId}")
	ResponseEntity<ResponseMessage> registerToJoinTournamentExhibitionType(
			@PathVariable(name = "tournamentId") int tournamentId, @PathVariable(name = "studentId") String studentId,
			@RequestParam(defaultValue = "0") int exhibitionTypeId, @RequestParam(defaultValue = "0") String teamName,
			@RequestBody List<ActiveUserDto> teamMember) {
		return new ResponseEntity<ResponseMessage>(tournamentService.registerToJoinTournamentExhibitionType(
				tournamentId, studentId, exhibitionTypeId, teamName, teamMember), HttpStatus.OK);
	}

	@GetMapping("/getallusercompetitiveplayer/{tournamentId}/{studentId}")
	ResponseEntity<ResponseMessage> getAllUserCompetitivePlayer(@PathVariable(name = "tournamentId") int tournamentId,
			@PathVariable(name = "studentId") String studentId) {
		return new ResponseEntity<ResponseMessage>(
				tournamentService.getAllUserCompetitivePlayer(tournamentId, studentId), HttpStatus.OK);
	}

	@GetMapping("/getalluserexhibitionplayer/{tournamentId}/{studentId}")
	ResponseEntity<ResponseMessage> getAllUserExhibitionPlayer(@PathVariable(name = "tournamentId") int tournamentId,
			@PathVariable(name = "studentId") String studentId) {
		return new ResponseEntity<ResponseMessage>(
				tournamentService.getAllUserExhibitionPlayer(tournamentId, studentId), HttpStatus.OK);
	}

	@GetMapping("/getalluserorganizingcommittee/{tournamentId}/{studentId}")
	ResponseEntity<ResponseMessage> getAllUserOrganizingCommittee(@PathVariable(name = "tournamentId") int tournamentId,
			@PathVariable(name = "studentId") String studentId) {
		return new ResponseEntity<ResponseMessage>(
				tournamentService.getAllUserOrganizingCommittee(tournamentId, studentId), HttpStatus.OK);
	}

	@GetMapping("/getalltournamentbystudentid/{studentId}")
	ResponseEntity<ResponseMessage> getAllTournamentByStudentId(@PathVariable(name = "studentId") String studentId,
			@RequestParam String semester, @RequestParam int status) {
		return new ResponseEntity<ResponseMessage>(
				tournamentService.getAllTournamentByStudentId(studentId, semester, status), HttpStatus.OK);
	}

	@PostMapping("/headclub/addlistorganizingcommittee/{studentId}/{tournamentId}")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_HeadTechnique','ROLE_ViceHeadTechnique')")
	ResponseEntity<ResponseMessage> addListOrganizingCommittee(@PathVariable(name = "studentId") String studentId,
			@PathVariable(name = "tournamentId") int tournamentId,
			@RequestBody List<UserTournamentOrganizingCommitteeDto> userTournamentOrganizingCommitteesDto) {
		return new ResponseEntity<ResponseMessage>(tournamentService.addListTournamentOrganizingCommittee(studentId,
				userTournamentOrganizingCommitteesDto, tournamentId), HttpStatus.OK);
	}

	@GetMapping("headclub/getallusernotjointournament/{tournamentId}")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_HeadTechnique','ROLE_ViceHeadTechnique')")
	ResponseEntity<ResponseMessage> getAllUserNotJoinTournament(@PathVariable(name = "tournamentId") int tournamentId) {
		return new ResponseEntity<ResponseMessage>(tournamentService.getAllUserNotJoinTournament(tournamentId),
				HttpStatus.OK);
	}
}
