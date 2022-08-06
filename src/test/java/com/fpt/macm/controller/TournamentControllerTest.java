package com.fpt.macm.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fpt.macm.constant.Constant;
import com.fpt.macm.model.dto.ActiveUserDto;
import com.fpt.macm.model.dto.CompetitivePlayerDto;
import com.fpt.macm.model.dto.CompetitiveTypeDto;
import com.fpt.macm.model.dto.ExhibitionTypeDto;
import com.fpt.macm.model.dto.RoleEventDto;
import com.fpt.macm.model.dto.TournamentDto;
import com.fpt.macm.model.dto.TournamentOrganizingCommitteeDto;
import com.fpt.macm.model.entity.CompetitivePlayer;
import com.fpt.macm.model.entity.CompetitiveType;
import com.fpt.macm.model.entity.ExhibitionPlayer;
import com.fpt.macm.model.entity.ExhibitionTeam;
import com.fpt.macm.model.entity.ExhibitionType;
import com.fpt.macm.model.entity.Role;
import com.fpt.macm.model.entity.RoleEvent;
import com.fpt.macm.model.entity.Tournament;
import com.fpt.macm.model.entity.TournamentOrganizingCommittee;
import com.fpt.macm.model.entity.TournamentOrganizingCommitteePaymentStatusReport;
import com.fpt.macm.model.entity.TournamentPlayer;
import com.fpt.macm.model.entity.TournamentPlayerPaymentStatusReport;
import com.fpt.macm.model.entity.User;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.service.CompetitiveTypeService;
import com.fpt.macm.service.TournamentService;

@SpringBootTest
public class TournamentControllerTest {
	
	@MockBean
	TournamentService tournamentService;
	
	@MockBean
	CompetitiveTypeService competitiveTypeService;

	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;

	@InjectMocks
	TournamentController tournamentController;

	@BeforeEach
	public void setup() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
	}

	private Set<CompetitiveType> competitiveTypes() {
		Set<CompetitiveType> competitiveTypes = new HashSet<CompetitiveType>();
		CompetitiveType competitiveType = new CompetitiveType();
		competitiveType.setId(1);
		competitiveType.setGender(true);
		competitiveType.setWeightMax(60);
		competitiveType.setWeightMin(57);
		competitiveTypes.add(competitiveType);
		return competitiveTypes;
	}

	private Set<ExhibitionType> exhibitionTypes() {
		Set<ExhibitionType> exhibitionTypes = new HashSet<ExhibitionType>();
		ExhibitionType exhibitionType = new ExhibitionType();
		exhibitionType.setExhibitionTeams(exhibitionTeams());
		exhibitionType.setId(1);
		exhibitionType.setName("Long ho quyen");
		exhibitionType.setNumberFemale(0);
		exhibitionType.setNumberMale(1);
		exhibitionTypes.add(exhibitionType);
		return exhibitionTypes;
	}

	private Set<ExhibitionTeam> exhibitionTeams() {
		Set<ExhibitionTeam> exhibitionTeams = new HashSet<ExhibitionTeam>();
		ExhibitionTeam exhibitionTeam = new ExhibitionTeam();
		exhibitionTeam.setExhibitionPlayers(exhibitionPlayers());
		exhibitionTeam.setId(1);
		exhibitionTeam.setTeamName("Team 1");
		exhibitionTeams.add(exhibitionTeam);
		return exhibitionTeams;
	}

	private Set<ExhibitionPlayer> exhibitionPlayers() {
		Set<ExhibitionPlayer> exhibitionPlayers = new HashSet<ExhibitionPlayer>();
		ExhibitionPlayer exhibitionPlayer = new ExhibitionPlayer();
		exhibitionPlayer.setId(1);
		exhibitionPlayer.setRoleInTeam(true);
		exhibitionPlayer.setTournamentPlayer(tournamentPlayer());
		exhibitionPlayers.add(exhibitionPlayer);
		return exhibitionPlayers;
	}

	private TournamentPlayer tournamentPlayer() {
		TournamentPlayer tournamentPlayer = new TournamentPlayer();
		tournamentPlayer.setId(1);
		tournamentPlayer.setPaymentStatus(true);
		tournamentPlayer.setUser(createUser());
		return tournamentPlayer;
	}

	private Set<TournamentPlayer> tournamentPlayers() {
		Set<TournamentPlayer> tournamentPlayers = new HashSet<TournamentPlayer>();
		TournamentPlayer tournamentPlayer = new TournamentPlayer();
		tournamentPlayer.setId(1);
		tournamentPlayer.setPaymentStatus(true);
		tournamentPlayer.setUser(createUser());
		tournamentPlayers.add(tournamentPlayer);
		return tournamentPlayers;
	}

	private User createUser() {
		User user = new User();
		user.setStudentId("HE140856");
		user.setId(1);
		user.setName("Dam Van Toan");
		user.setGender(true);
		LocalDate localDate = LocalDate.of(2000, 02, 10);
		user.setDateOfBirth(localDate);
		user.setEmail("toandvhe140856@fpt.edu.vn");
		user.setImage(null);
		user.setPhone("0982102000");
		user.setActive(true);
		user.setCurrentAddress("Dom A");
		Role role = new Role();
		role.setId(1);
		role.setName("ROLE_HeadClub");
		user.setRole(role);
		user.setCreatedOn(LocalDate.now());
		user.setCreatedBy("toandv");
		return user;
	}

	private Tournament tournament() {
		Tournament tournament = new Tournament();
		tournament.setCompetitiveTypes(competitiveTypes());
		tournament.setDescription("abc");
		tournament.setExhibitionTypes(exhibitionTypes());
		tournament.setFeeOrganizingCommiteePay(100000);
		tournament.setFeePlayerPay(100000);
		tournament.setId(1);
		tournament.setMaxQuantityComitee(10);
		tournament.setName("FNC");
		tournament.setRegistrationOrganizingCommitteeDeadline(LocalDateTime.of(2022, 8, 1, 18, 0));
		tournament.setRegistrationPlayerDeadline(LocalDateTime.of(2022, 8, 1, 18, 0));
		tournament.setSemester("Summer2022");
		tournament.setStatus(1);
		tournament.setTournamentPlayers(tournamentPlayers());
		return tournament;
	}

	private TournamentOrganizingCommittee tournamentOrganizingCommittee() {
		TournamentOrganizingCommittee tournamentOrganizingCommittee = new TournamentOrganizingCommittee();
		tournamentOrganizingCommittee.setId(1);
		tournamentOrganizingCommittee.setPaymentStatus(true);
		tournamentOrganizingCommittee.setRegisterStatus(Constant.REQUEST_STATUS_APPROVED);
		RoleEvent roleEvent = new RoleEvent();
		roleEvent.setId(1);
		roleEvent.setName("Ban truyen thong");
		tournamentOrganizingCommittee.setRoleEvent(roleEvent);
		tournamentOrganizingCommittee.setTournament(tournament());
		tournamentOrganizingCommittee.setUser(createUser());
		tournamentOrganizingCommittee.setCreatedBy("toandv");
		tournamentOrganizingCommittee.setCreatedOn(LocalDateTime.now());
		tournamentOrganizingCommittee.setUpdatedBy("toandv");
		tournamentOrganizingCommittee.setUpdatedOn(LocalDateTime.now());
		return tournamentOrganizingCommittee;
	}

	private RoleEvent roleEvent() {
		RoleEvent roleEvent = new RoleEvent();
		roleEvent.setId(2);
		roleEvent.setName("Ban van hoa");
		return roleEvent;
	}

	private TournamentOrganizingCommitteeDto tournamentOrganizingCommitteeDto() {
		TournamentOrganizingCommitteeDto tournamentOrganizingCommitteeDto = new TournamentOrganizingCommitteeDto();
		tournamentOrganizingCommitteeDto.setId(1);
		tournamentOrganizingCommitteeDto.setPaymentStatus(true);
		tournamentOrganizingCommitteeDto.setRegisterStatus(Constant.REQUEST_STATUS_APPROVED);
		RoleEventDto roleEventDto = new RoleEventDto();
		roleEventDto.setId(2);
		roleEventDto.setName("Ban van hoa");
		tournamentOrganizingCommitteeDto.setRoleTournamentDto(roleEventDto);
		tournamentOrganizingCommitteeDto.setUserName("toan");
		tournamentOrganizingCommitteeDto.setUserStudentId("HE140855");
		return tournamentOrganizingCommitteeDto;
	}

	private TournamentDto tournamentDto() {
		TournamentDto tournamentDto = new TournamentDto();
		tournamentDto.setCompetitiveTypesDto(competitiveTypesDto());
		tournamentDto.setDescription("abc");
		tournamentDto.setExhibitionTypesDto(exhibitionTypesDtos());
		tournamentDto.setFeeOrganizingCommiteePay(100000);
		tournamentDto.setFeePlayerPay(100000);
		tournamentDto.setId(1);
		tournamentDto.setMaxQuantityComitee(10);
		tournamentDto.setName("FNC");
		tournamentDto.setRegistrationOrganizingCommitteeDeadline(LocalDateTime.of(2022, 8, 1, 18, 0));
		tournamentDto.setRegistrationPlayerDeadline(LocalDateTime.of(2022, 8, 1, 18, 0));
		tournamentDto.setStatus(1);
		tournamentDto.setTournamentPlayers(tournamentPlayers());
		return tournamentDto;
	}

	private Set<CompetitiveTypeDto> competitiveTypesDto() {
		Set<CompetitiveTypeDto> competitiveTypesDto = new HashSet<CompetitiveTypeDto>();
		CompetitiveTypeDto competitiveTypeDto = new CompetitiveTypeDto();
		competitiveTypeDto.setId(1);
		competitiveTypeDto.setGender(true);
		competitiveTypeDto.setWeightMax(60);
		competitiveTypeDto.setWeightMin(57);
		competitiveTypesDto.add(competitiveTypeDto);
		return competitiveTypesDto;
	}

	private Set<ExhibitionTypeDto> exhibitionTypesDtos() {
		Set<ExhibitionTypeDto> exhibitionTypes = new HashSet<ExhibitionTypeDto>();
		ExhibitionTypeDto exhibitionType = new ExhibitionTypeDto();
		exhibitionType.setId(1);
		exhibitionType.setName("Long ho quyen");
		exhibitionType.setNumberFemale(0);
		exhibitionType.setNumberMale(1);
		exhibitionTypes.add(exhibitionType);
		return exhibitionTypes;
	}

	private CompetitivePlayer competitivePlayer() {
		CompetitivePlayer competitivePlayer = new CompetitivePlayer();
		competitivePlayer.setId(1);
		competitivePlayer.setTournamentPlayer(tournamentPlayer());
		competitivePlayer.setWeight(50);
		return competitivePlayer;
	}
	
	private CompetitivePlayerDto competitivePlayerDto() {
		CompetitivePlayerDto competitivePlayerDto = new CompetitivePlayerDto();
		competitivePlayerDto.setId(competitivePlayer().getId());
		competitivePlayerDto.setWeight(competitivePlayer().getWeight());
		return competitivePlayerDto;
	}

	private TournamentOrganizingCommitteePaymentStatusReport tournamentOrganizingCommitteePaymentStatusReport() {
		TournamentOrganizingCommitteePaymentStatusReport tournamentOrganizingCommitteePaymentStatusReport = new TournamentOrganizingCommitteePaymentStatusReport();
		tournamentOrganizingCommitteePaymentStatusReport.setFundBalance(1000000);
		tournamentOrganizingCommitteePaymentStatusReport.setFundChange(500000);
		tournamentOrganizingCommitteePaymentStatusReport.setId(1);
		tournamentOrganizingCommitteePaymentStatusReport.setPaymentStatus(true);
		tournamentOrganizingCommitteePaymentStatusReport.setTournament(tournament());
		tournamentOrganizingCommitteePaymentStatusReport.setUser(createUser());
		return tournamentOrganizingCommitteePaymentStatusReport;
	}

	private TournamentPlayerPaymentStatusReport tournamentPlayerPaymentStatusReport() {
		TournamentPlayerPaymentStatusReport tournamentPlayerPaymentStatusReport = new TournamentPlayerPaymentStatusReport();
		tournamentPlayerPaymentStatusReport.setFundBalance(1000000);
		tournamentPlayerPaymentStatusReport.setFundChange(500000);
		tournamentPlayerPaymentStatusReport.setId(1);
		tournamentPlayerPaymentStatusReport.setPaymentStatus(true);
		tournamentPlayerPaymentStatusReport.setTournament(tournament());
		tournamentPlayerPaymentStatusReport.setUser(createUser());
		return tournamentPlayerPaymentStatusReport;
	}
	
	private ExhibitionPlayer exhibitionPlayer() {
		ExhibitionPlayer exhibitionPlayer = new ExhibitionPlayer();
		exhibitionPlayer.setId(1);
		exhibitionPlayer.setRoleInTeam(false);
		exhibitionPlayer.setTournamentPlayer(tournamentPlayer());
		return exhibitionPlayer;
	}
	
	private List<ActiveUserDto> activeUserDtos(){
		List<ActiveUserDto> activeUserDtos = new ArrayList<ActiveUserDto>();
		ActiveUserDto activeUserDto = new ActiveUserDto();
		activeUserDto.setGender(true);
		activeUserDto.setStudentId("HE140140");
		activeUserDto.setStudentName("toan");
		activeUserDtos.add(activeUserDto);
		return activeUserDtos;
		
	}

	@Test
	public void createTournamentSuccessTest() throws Exception {
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(Arrays.asList(tournament()));

		when(tournamentService.createTournament(any())).thenReturn(responseMessage);
		
		this.mockMvc.perform(post("/api/tournament/headclub/createtournament")
				.content(asJsonString(tournament())).contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value("1"));
	}

	@Test
	public void updateTournamentSuccessTest() throws Exception {
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(Arrays.asList(tournament()));
		
		when(tournamentService.updateTournament(anyInt(), any())).thenReturn(responseMessage);
		
		this.mockMvc.perform(put("/api/tournament/headclub/update/{tournamentId}", "1")
				.content(asJsonString(tournamentDto())).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.data.size()").value("1"));
	}

	@Test
	public void getTournamentByIdSuccessTest() throws Exception {
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(Arrays.asList(tournament()));
		
		when(tournamentService.getTournamentById(anyInt())).thenReturn(responseMessage);
		
		this.mockMvc.perform(get("/api/tournament/headclub/tournament/{tournamentId}", "1"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.data.size()").value("1"));
	}

	@Test
	public void getAllTournamentBySemesterSuccessTest() throws Exception {
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(Arrays.asList(tournamentDto()));
		
		when(tournamentService.getAllTournamentBySemester(anyString(), anyInt())).thenReturn(responseMessage);
		
		this.mockMvc
				.perform(get("/api/tournament/headclub/tournament/getall")
						.param("semester", "Summer2022")
						.param("status", "1"))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.data.size()").value("1"));
	}
	
	@Test
	public void getAllTournamentOrganizingCommitteeSuccess() throws Exception {
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(Arrays.asList(tournamentOrganizingCommitteeDto()));
		
		when(tournamentService.getAllTournamentOrganizingCommitteeByTournamentId(anyInt())).thenReturn(responseMessage);
		
		this.mockMvc
		.perform(get("/api/tournament/headclub/getalltournamentorganizingcommittee/{tournamentId}", "1"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value(1));
	}
	
	@Test
	public void updateTournamentOrganizingCommitteeRoleSuccess() throws Exception {
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(Arrays.asList(tournamentOrganizingCommitteeDto()));
		
		when(tournamentService.updateTournamentOrganizingCommitteeRole(any())).thenReturn(responseMessage);
		
		this.mockMvc
		.perform(put("/api/tournament/headclub/updatetournamentorganizingcommitteerole")
				.content(asJsonString(Arrays.asList(tournamentOrganizingCommitteeDto()))).contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value(1));
	}
	
	@Test
	public void deleteTournamentSuccessTest() throws Exception {
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(Arrays.asList(tournament()));

		when(tournamentService.deleteTournamentById(anyInt())).thenReturn(responseMessage);
		
		this.mockMvc.perform(delete("/api/tournament/headclub/delete/{tournamentId}", "1"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value("1"));
	}
	
	@Test
	public void getAllCompetitivePlayerSuccess() throws Exception {
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(Arrays.asList(competitivePlayerDto()));
		
		when(tournamentService.getAllCompetitivePlayer(anyInt(), anyDouble(), anyDouble())).thenReturn(responseMessage);
		
		this.mockMvc.perform(get("/api/tournament/headclub/getallcompetitiveplayer/{tournamentId}", "1")
				.param("weightMin", "0")
				.param("weightMax", "100"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value("1"));
	}

	@Test
	public void getAllCompetitivePlayerByTypeSuccess() throws Exception {
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(Arrays.asList(competitivePlayerDto()));
		
		when(tournamentService.getAllCompetitivePlayerByType(anyInt(), anyInt())).thenReturn(responseMessage);
		
		this.mockMvc.perform(get("/api/tournament/headclub/getallcompetitiveplayerbytype/{tournamentId}", "1")
				.param("competitiveTypeId", "1"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value("1"));
	}
	
	@Test
	public void getAllExhibitionTeamSuccess() throws Exception {
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(Arrays.asList(exhibitionTeams()));
		
		when(tournamentService.getAllExhibitionTeam(anyInt(), anyInt())).thenReturn(responseMessage);
		
		this.mockMvc.perform(get("/api/tournament/headclub/getallexhibitionteam/{tournamentId}", "1")
				.param("exhibitionType", "1"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value("1"));
	}
	
	@Test
	public void getAllOrginizingCommitteeRoleSuccess() throws Exception {
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(Arrays.asList(roleEvent()));
		
		when(tournamentService.getAllOrganizingCommitteeRole()).thenReturn(responseMessage);
		
		this.mockMvc.perform(get("/api/tournament/headclub/getallorganizingcommitteerole"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value("1"));
	}
	
	@Test
	public void getAllExhibitionTypeSuccess() throws Exception {
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(Arrays.asList(exhibitionTypes()));
		
		when(tournamentService.getAllExhibitionType(anyInt())).thenReturn(responseMessage);
		
		this.mockMvc.perform(get("/api/tournament/headclub/getallexhibitiontype/{tournamentId}", "1"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value("1"));
	}
	
	@Test
	public void acceptRequestToJoinOrganizingCommitteeSuccess() throws Exception {
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(Arrays.asList(tournamentOrganizingCommitteeDto()));
		
		when(tournamentService.acceptRequestOrganizingCommittee(anyInt())).thenReturn(responseMessage);
		
		this.mockMvc.perform(put("/api/tournament/headclub/acceptrequesttojoinorganizingcommittee/{organizingCommitteeId}", "1"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value("1"));
	}
	
	@Test
	public void declineRequestToJoinOrganizingCommitteeSuccess() throws Exception {
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(Arrays.asList(tournamentOrganizingCommitteeDto()));
		
		when(tournamentService.declineRequestOrganizingCommittee(anyInt())).thenReturn(responseMessage);
		
		this.mockMvc.perform(put("/api/tournament/headclub/declinerequesttojoinorganizingcommittee/{organizingCommitteeId}", "1"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value("1"));
	}
	
	@Test
	public void getAllTournamentPlayerPaymentStatusSuccess() throws Exception {
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(Arrays.asList(tournamentPlayer()));
		
		when(tournamentService.getAllTournamentPlayerPaymentStatus(anyInt())).thenReturn(responseMessage);
		
		this.mockMvc.perform(get("/api/tournament/treasurer/getalltournamentplayerpaymentstatus/{tournamentId}", "1"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value("1"));
	}
	
	@Test
	public void getAllTournamentOrganizingCommitteePaymentStatusSuccess() throws Exception {
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(Arrays.asList(tournamentOrganizingCommitteeDto()));
		
		when(tournamentService.getAllTournamentOrganizingCommitteePaymentStatus(anyInt())).thenReturn(responseMessage);
		
		this.mockMvc.perform(get("/api/tournament/treasurer/getalltournamentorganizingcommitteepaymentstatus/{tournamentId}", "1"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value("1"));
	}
	
	@Test
	public void updateTournamentOrganizingCommitteePaymentStatusSuccess() throws Exception {
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(Arrays.asList(tournamentOrganizingCommitteeDto()));
		
		when(tournamentService.updateTournamentOrganizingCommitteePaymentStatus(anyInt())).thenReturn(responseMessage);
		
		this.mockMvc.perform(put("/api/tournament/treasurer/updatetournamentorganizingcommitteepaymentstatus/{tournamentOrganizingCommitteeId}", "1"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value("1"));
	}
	
	@Test
	public void getAllTournamentOrganizingCommitteePaymentStatusReportSuccess() throws Exception {
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(Arrays.asList(tournamentOrganizingCommitteePaymentStatusReport()));
		
		when(tournamentService.getAllTournamentOrganizingCommitteePaymentStatusReport(anyInt())).thenReturn(responseMessage);
		
		this.mockMvc.perform(get("/api/tournament/treasurer/getalltournamentorganizingcommitteepaymentstatusreport/{tournamentId}", "1"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value("1"));
	}
	
	@Test
	public void updateTournamentPlayerPaymentStatusSuccess() throws Exception {
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(Arrays.asList(tournamentPlayer()));
		
		when(tournamentService.updateTournamentPlayerPaymentStatus(anyInt())).thenReturn(responseMessage);
		
		this.mockMvc.perform(put("/api/tournament/treasurer/updatetournamentplayerpaymentstatus/{tournamentPlayerId}", "1"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value("1"));
	}
	
	@Test
	public void getAllTournamentPlayerPaymentStatusReportSuccess() throws Exception {
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(Arrays.asList(tournamentPlayerPaymentStatusReport()));
		
		when(tournamentService.getAllTournamentPlayerPaymentStatusReport(anyInt())).thenReturn(responseMessage);
		
		this.mockMvc.perform(get("/api/tournament/treasurer/getalltournamentplayerpaymentstatusreport/{tournamentId}", "1"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value("1"));
	}
	
	@Test
	public void getAllCompetitiveTypeSuccess() throws Exception {
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(Arrays.asList(competitiveTypes()));
		
		when(competitiveTypeService.getAllType(anyInt())).thenReturn(responseMessage);
		
		this.mockMvc.perform(get("/api/tournament/treasurer/getallcompetitivetype/{tournamentId}", "1"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value("1"));
	}
	
	@Test
	public void registerToJoinOrganizingCommitteeSuccess() throws Exception {
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(Arrays.asList(tournamentOrganizingCommittee()));
		
		when(tournamentService.registerToJoinTournamentOrganizingComittee(anyInt(), anyString(), anyInt())).thenReturn(responseMessage);
		
		this.mockMvc.perform(post("/api/tournament/registertojoinorganizingcommittee/{tournamentId}/{studentId}/{roleId}", "1", "HE140855", "2"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value("1"));
	}
	
	@Test
	public void registerToJoinTournamentCompetitveTypeSuccess() throws Exception {
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(Arrays.asList(competitivePlayer()));
		
		when(tournamentService.registerToJoinTournamentCompetitiveType(anyInt(), anyString(), anyDouble(), anyInt())).thenReturn(responseMessage);
		
		this.mockMvc.perform(post("/api/tournament/registertojointournamentcompetitivetype/{tournamentId}/{studentId}", "1", "HE140855")
				.param("weight", "55")
				.param("competitiveTypeId", "1"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value("1"));
	}
	
	@Test
	public void registerToJoinTournamentExhibitionTypeSuccess() throws Exception {
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(Arrays.asList(exhibitionTeams()));
		
		when(tournamentService.registerToJoinTournamentExhibitionType(anyInt(), anyString(), anyInt(), anyString(), anyList())).thenReturn(responseMessage);
		
		this.mockMvc.perform(post("/api/tournament/registertojointournamentexhibitiontype/{tournamentId}/{studentId}", "1", "HE140855")
				.param("exhibitionTypeId", "1")
				.param("teamName", "Team 1")
				.content(asJsonString(activeUserDtos())).contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value("1"));
	}
	
	@Test
	public void getAllUserCompetitivePlayerSuccess() throws Exception {
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(Arrays.asList(competitivePlayer()));
		
		when(tournamentService.getAllUserCompetitivePlayer(anyInt(), anyString())).thenReturn(responseMessage);
		
		this.mockMvc.perform(get("/api/tournament/getallusercompetitiveplayer/{tournamentId}/{studentId}", "1", "HE140855"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value("1"));
	}
	
	@Test
	public void getAllUserExhibitionPlayerSuccess() throws Exception {
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(Arrays.asList(exhibitionPlayer()));
		
		when(tournamentService.getAllUserExhibitionPlayer(anyInt(), anyString())).thenReturn(responseMessage);
		
		this.mockMvc.perform(get("/api/tournament/getalluserexhibitionplayer/{tournamentId}/{studentId}", "1", "HE140855"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value("1"));
	}
	
	@Test
	public void getAllUserOrganizingCommitteeSuccess() throws Exception {
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(Arrays.asList(tournamentOrganizingCommittee()));
		
		when(tournamentService.getAllUserOrganizingCommittee(anyInt(), anyString())).thenReturn(responseMessage);
		
		this.mockMvc.perform(get("/api/tournament/getalluserorganizingcommittee/{tournamentId}/{studentId}", "1", "HE140855"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value("1"));
	}
	
	public static String asJsonString(final Object obj) {
		try {
			ObjectMapper mapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();
			String result = mapper.writeValueAsString(obj);

			return result;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
