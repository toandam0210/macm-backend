package com.fpt.macm.controller;

import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
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
import com.fpt.macm.model.dto.ExhibitionTeamDto;
import com.fpt.macm.model.entity.Area;
import com.fpt.macm.model.entity.CompetitiveType;
import com.fpt.macm.model.entity.ExhibitionPlayer;
import com.fpt.macm.model.entity.ExhibitionResult;
import com.fpt.macm.model.entity.ExhibitionTeam;
import com.fpt.macm.model.entity.ExhibitionType;
import com.fpt.macm.model.entity.Role;
import com.fpt.macm.model.entity.Tournament;
import com.fpt.macm.model.entity.TournamentPlayer;
import com.fpt.macm.model.entity.User;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.service.ExhibitionResultService;
import com.fpt.macm.service.ExhibitionTeamService;
import com.fpt.macm.service.ExhibitionTypeService;

@SpringBootTest
public class ExhibitionControllerTest {

	@MockBean
	ExhibitionTeamService exhibitionTeamService;
	
	@MockBean
	ExhibitionResultService exhibitionResultService;
	
	@MockBean
	ExhibitionTypeService exhibitionTypeService;
	
	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;
	
	@InjectMocks
	ExhibitionController exhibitionController;
	
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
		tournamentPlayer.setUser(user());
		return tournamentPlayer;
	}

	private Set<TournamentPlayer> tournamentPlayers() {
		Set<TournamentPlayer> tournamentPlayers = new HashSet<TournamentPlayer>();
		TournamentPlayer tournamentPlayer = new TournamentPlayer();
		tournamentPlayer.setId(1);
		tournamentPlayer.setPaymentStatus(true);
		tournamentPlayer.setUser(user());
		tournamentPlayers.add(tournamentPlayer);
		return tournamentPlayers;
	}

	private User user() {
		User user = new User();
		user.setId(1);
		user.setStudentId("HE140855");
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
		tournament.setName("FNC");
		tournament.setRegistrationOrganizingCommitteeDeadline(LocalDateTime.of(2022, 8, 1, 18, 0));
		tournament.setRegistrationPlayerDeadline(LocalDateTime.of(2022, 8, 1, 18, 0));
		tournament.setSemester("Summer2022");
		tournament.setStatus(true);
		tournament.setTournamentPlayers(tournamentPlayers());
		return tournament;
	}
	
	private List<ExhibitionTeamDto> exhibitionTeamsDto() {
		List<ExhibitionTeamDto> exhibitionTeamDtos = new ArrayList<ExhibitionTeamDto>();
		for (ExhibitionTeam exhibitionTeam : exhibitionTeams()) {
			ExhibitionTeamDto exhibitionTeamDto = new ExhibitionTeamDto();
			exhibitionTeamDto.setId(exhibitionTeam.getId());
			exhibitionTeamDto.setTeamName(exhibitionTeam.getTeamName());
			exhibitionTeamDto.setScore(exhibitionResult().getScore());
			exhibitionTeamDtos.add(exhibitionTeamDto);
		}
		return exhibitionTeamDtos;
	}
	
	private ExhibitionResult exhibitionResult() {
		ExhibitionResult exhibitionResult = new ExhibitionResult();
		for (ExhibitionTeam exhibitionTeam : exhibitionTeams()) {
			exhibitionResult.setId(1);
			exhibitionResult.setTeam(exhibitionTeam);
			exhibitionResult.setScore(0.0);
			exhibitionResult.setTime(LocalDateTime.now());
			exhibitionResult.setArea(area());
		}
		return exhibitionResult;
	}
	
	private Area area() {
		Area area = new Area();
		area.setId(1);
		area.setName("Sân 1");
		return area;
	}
	
	@Test
	public void registerTeamSuccess() throws Exception {
		List<String> listStudentId = new ArrayList<String>();
		listStudentId.add("HE140855");
		
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(Arrays.asList(exhibitionTeams()));
		
		when(exhibitionTeamService.registerTeam(anyInt(), anyString(), anyList())).thenReturn(responseMessage);
		
		this.mockMvc.perform(post("/api/exhibition/headclub/registerexhibitionteam/{exhibitionTypeId}", "1")
				.param("name", "Team 1")
				.content(asJsonString(listStudentId)).contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value("1"));
	}
	
	@Test
	public void getTeamByTypeSuccess() throws Exception {
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(Arrays.asList(exhibitionTeams()));
		
		when(exhibitionTeamService.getTeamByType(anyInt())).thenReturn(responseMessage);
		
		this.mockMvc.perform(get("/api/exhibition/headclub/getteambytype/{exhibitionTypeId}", "1"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value("1"));
	}
	
	@Test
	public void getTop3TeamByTypeSuccess() throws Exception {
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(Arrays.asList(exhibitionTeamsDto()));
		
		when(exhibitionTeamService.getTop3TeamByType(anyInt())).thenReturn(responseMessage);
		
		this.mockMvc.perform(get("/api/exhibition/headclub/gettop3teambytype/{exhibitionTypeId}", "1"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value("1"));
	}
	
	@Test
	public void spawnTimeAndAreaSuccess() throws Exception {
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(Arrays.asList(exhibitionResult()));
		
		when(exhibitionResultService.spawnTimeAndArea(anyInt())).thenReturn(responseMessage);
		
		this.mockMvc.perform(post("/api/exhibition/headclub/spawntimeandarea/{tournamentId}", "1"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value("1"));
	}
	
	@Test
	public void getListExhibitionTypeSuccess() throws Exception {
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(Arrays.asList(exhibitionTypes()));
		
		when(exhibitionTypeService.getAllExhibitionType(anyInt())).thenReturn(responseMessage);
		
		this.mockMvc.perform(get("/api/exhibition/getlistexhibitiontype/{tournamentId}", "1"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value("1"));
	}
	
	@Test
	public void getExhibitionResultSuccess() throws Exception {
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(Arrays.asList(exhibitionResult()));
		
		when(exhibitionResultService.getListExhibitionResult(anyInt(), anyString())).thenReturn(responseMessage);
		
		this.mockMvc.perform(get("/api/exhibition/getlistexhibitionresult")
				.param("exhibitionTypeId", "1")
				.param("date", "29/08/2022"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value("1"));
	}
	
	@Test
	public void updateExhibitionResultSuccess() throws Exception {
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(Arrays.asList(exhibitionResult()));
		
		when(exhibitionResultService.updateExhibitionResult(anyInt(), anyDouble())).thenReturn(responseMessage);
		
		this.mockMvc.perform(put("/api/exhibition/headclub/updateexhibitionresult/{exhibitionTeamId}", "1")
				.param("score", "100"))
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
