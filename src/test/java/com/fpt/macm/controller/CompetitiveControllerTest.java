package com.fpt.macm.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
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
import com.fpt.macm.model.dto.CompetitiveMatchDto;
import com.fpt.macm.model.dto.PlayerMatchDto;
import com.fpt.macm.model.entity.Area;
import com.fpt.macm.model.entity.CompetitiveMatch;
import com.fpt.macm.model.entity.CompetitivePlayer;
import com.fpt.macm.model.entity.CompetitivePlayerBracket;
import com.fpt.macm.model.entity.CompetitiveResult;
import com.fpt.macm.model.entity.CompetitiveType;
import com.fpt.macm.model.entity.Role;
import com.fpt.macm.model.entity.TournamentPlayer;
import com.fpt.macm.model.entity.User;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.service.CompetitiveMatchService;
import com.fpt.macm.service.CompetitivePlayerBracketService;
import com.fpt.macm.service.CompetitivePlayerService;
import com.fpt.macm.service.CompetitiveResultService;

@SpringBootTest
public class CompetitiveControllerTest {

	@MockBean
	CompetitivePlayerService competitivePlayerService;

	@MockBean
	CompetitiveMatchService competitiveMatchService;

	@MockBean
	CompetitivePlayerBracketService competitivePlayerBracketService;

	@MockBean
	CompetitiveResultService competitiveResultService;

	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;

	@InjectMocks
	CompetitiveControllerTest competitiveController;

	@BeforeEach
	public void setup() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
	}

	private CompetitivePlayer competitivePlayer() {
		CompetitivePlayer competitivePlayer = new CompetitivePlayer();
		competitivePlayer.setId(1);
		competitivePlayer.setTournamentPlayer(tournamentPlayer());
		competitivePlayer.setWeight(50);
		return competitivePlayer;
	}

	private TournamentPlayer tournamentPlayer() {
		TournamentPlayer tournamentPlayer = new TournamentPlayer();
		tournamentPlayer.setId(1);
		tournamentPlayer.setPaymentStatus(true);
		tournamentPlayer.setUser(createUser());
		return tournamentPlayer;
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
	
	private CompetitivePlayerBracket competitivePlayerBracket() {
		Set<CompetitiveType> competitiveTypes = competitiveTypes();
		List<CompetitiveType> listCompetitive = new ArrayList<CompetitiveType>(competitiveTypes);
		CompetitiveType competitiveType = listCompetitive.get(0);
		CompetitivePlayerBracket competitivePlayerBracket = new CompetitivePlayerBracket();
		competitivePlayerBracket.setCompetitivePlayer(competitivePlayer());
		competitivePlayerBracket.setCompetitiveType(competitiveType);
		competitivePlayerBracket.setId(1);
		competitivePlayerBracket.setNumericalOrderId(1);
		return competitivePlayerBracket;
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
	
	private CompetitiveMatchDto competitiveMatchDto() {
		CompetitiveMatchDto competitiveMatchDto = new CompetitiveMatchDto();
		competitiveMatchDto.setArea("San A");
		competitiveMatchDto.setFirstPlayer(playerMatchDto());
		competitiveMatchDto.setId(1);
		competitiveMatchDto.setRound(1);
		competitiveMatchDto.setSecondPlayer(playerMatchDto());
		competitiveMatchDto.setStatus(true);
		competitiveMatchDto.setTime(LocalDateTime.now());
		return competitiveMatchDto;
	}
	
	private PlayerMatchDto playerMatchDto() {
		PlayerMatchDto playerMatchDto = new PlayerMatchDto();
		playerMatchDto.setPoint(5);
		playerMatchDto.setStudentId("HE140855");
		playerMatchDto.setStudentName("toan");
		return playerMatchDto;
	}
	
	private CompetitiveResult competitiveResult() {
		Area area = new Area();
		area.setId(1);
		area.setName("San B");
		CompetitiveResult competitiveResult = new CompetitiveResult();
		competitiveResult.setArea(area);
		competitiveResult.setFirstPoint(3);
		competitiveResult.setId(1);
		competitiveResult.setMatch(competitiveMatch());
		competitiveResult.setSecondPoint(5);
		competitiveResult.setTime(LocalDateTime.now());
		return competitiveResult;
	}
	
	private CompetitiveMatch competitiveMatch() {
		Set<CompetitiveType> competitiveTypes = competitiveTypes();
		List<CompetitiveType> listCompetitive = new ArrayList<CompetitiveType>(competitiveTypes);
		CompetitiveType competitiveType = listCompetitive.get(0);
		CompetitiveMatch competitiveMatch = new CompetitiveMatch();
		competitiveMatch.setCompetitiveType(competitiveType);
		competitiveMatch.setFirstStudentId("HE140855");
		competitiveMatch.setId(1);
		competitiveMatch.setLoseMatchId(1);
		competitiveMatch.setNextIsFirst(true);
		competitiveMatch.setNextMatchId(1);
		competitiveMatch.setRound(1);
		competitiveMatch.setSecondStudentId("HE140856");
		competitiveMatch.setStatus(true);
		return competitiveMatch;
	}

//	@Test
//	public void testAddNewCompetitivePlayer() throws Exception {
//		ResponseMessage responseMessage = new ResponseMessage();
//		responseMessage.setData(Arrays.asList(competitivePlayer()));
//		when(competitivePlayerService.addNewCompetitivePlayer(anyInt(), anyInt(), anyDouble()))
//				.thenReturn(responseMessage);
//		this.mockMvc
//				.perform(post("/api/competitive/headclub/addnewcompetitiveplayer/{tournamentId}", 1).param("userId", "1")
//						.param("weight", "50"))
//				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
//				.andExpect(jsonPath("$.data.size()").value("1"));
//
//	}
	
	@Test
	public void testUpdateWeightForCompetitivePlayer() throws Exception{
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(Arrays.asList(competitivePlayer()));
		when(competitivePlayerService.updateWeightForCompetitivePlayer(anyInt(), anyDouble()))
		.thenReturn(responseMessage);
		this.mockMvc
		.perform(put("/api/competitive/headclub/updateweightplayer/{competitivePlayerId}", 1).param("weight", "50"))
		.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value("1"));
	}
	
	@Test
	public void testdDeleteCompetitivePlayer() throws Exception{
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(Arrays.asList(competitivePlayer()));
		when(competitivePlayerService.deleteCompetitivePlayer(anyInt()))
		.thenReturn(responseMessage);
		this.mockMvc
		.perform(put("/api/competitive/headclub/deletecompetitiveplayer/{competitivePlayerId}", 1))
		.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value("1"));
	}
	
	@Test
	public void testGetListPlayerBracket() throws Exception {
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(Arrays.asList(competitivePlayerBracket()));
		when(competitivePlayerBracketService.getListPlayerBracket(anyInt())).thenReturn(responseMessage);
		this.mockMvc
		.perform(get("/api/competitive/headclub/getlistplayerbracket/{competitivePlayerId}", 1))
		.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value("1"));
	}
	
	@Test 
	public void testListUserNotJoinCompetitive() throws Exception {
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(Arrays.asList(createUser()));
		when(competitivePlayerService.listUserNotJoinCompetitive(anyInt())).thenReturn(responseMessage);
		this.mockMvc.perform(get("/api/competitive/headclub/listusernotjoincompetitive/{tournamentId}", 1))
		.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value("1"));
	}
	
//	@Test
//	public void testSpawnMatchs() throws Exception {
//		ResponseMessage responseMessage = new ResponseMessage();
//		responseMessage.setData(Arrays.asList(competitiveMatchDto()));
//		when(competitiveMatchService.spawnMatchs(anyInt())).thenReturn(responseMessage);
//		this.mockMvc.perform(post("/api/competitive/headclub/spawnmatchs/{competitiveTypeId}", 1))
//		.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
//		.andExpect(jsonPath("$.data.size()").value("1"));
//	}
	
	@Test
	public void testUpdateTimeAndPlaceMatch() throws Exception{
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(Arrays.asList(competitiveResult()));
		when(competitiveResultService.updateTimeAndArea(anyInt(), any())).thenReturn(responseMessage);
		this.mockMvc.perform(put("/api/competitive/headclub/updatetimeandplacematch/{matchId}", 1).content(asJsonString(competitiveResult()))
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value("1"));
	}
	
	@Test
	public void testUpdateResultMatch() throws Exception {
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setMessage("Đã cập nhật tỉ số trận đấu");
		when(competitiveResultService.updateResultMatch(anyInt(), anyInt(),anyInt())).thenReturn(responseMessage);
		this.mockMvc.perform(put("/api/competitive/headclub/updateresultmatch/{matchId}", 1))
		.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.message").value("Đã cập nhật tỉ số trận đấu"));
	}
	
	@Test
	public void testListMatchs() throws Exception {
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(Arrays.asList(competitiveMatchDto()));
		when(competitiveMatchService.listMatchs(anyInt())).thenReturn(responseMessage);
		this.mockMvc.perform(get("/api/competitive/headclub/listmatchs/{competitiveTypeId}", 1))
		.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value("1"));
	}
	
	@Test
	public void testUpdateListMatchsPlayer() throws Exception {
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(Arrays.asList(competitiveMatch()));
		when(competitiveMatchService.updateListMatchsPlayer(any())).thenReturn(responseMessage);
		this.mockMvc.perform(put("/api/competitive/headclub/updatelistmatchsplayer").content(asJsonString(Arrays.asList(competitiveMatchDto())))
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value("1"));
	}
	
//	@Test
//	public void testConfirmListMatchsPlayer() throws Exception {
//		ResponseMessage responseMessage = new ResponseMessage();
//		responseMessage.setData(Arrays.asList(competitiveMatch()));
//		when(competitiveMatchService.confirmListMatchsPlayer(anyInt())).thenReturn(responseMessage);
//		this.mockMvc.perform(put("/api/competitive/headclub/confirmlistmatchsplayer/{tournamentId}", 1))
//		.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
//		.andExpect(jsonPath("$.data.size()").value("1"));
//	}
	
	@Test
	public void testSpawnTimeAndArea() throws Exception {
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(Arrays.asList(competitiveResult()));
		when(competitiveResultService.spawnTimeAndArea(anyInt())).thenReturn(responseMessage);
		this.mockMvc.perform(post("/api/competitive/headclub/spawntimeandarea/{tournamentId}", 1))
		.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value("1"));
	}
	
	public static String asJsonString(final Object obj) {
	    try {
	    	ObjectMapper mapper = JsonMapper.builder()
	    			   .addModule(new JavaTimeModule())
	    			   .build();
	    	String result = mapper.writeValueAsString(obj);

	      return result;
	    } catch (Exception e) {
	      throw new RuntimeException(e);
	    }
	  }
}
