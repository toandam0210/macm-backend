package com.fpt.macm.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
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
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashSet;
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
import com.fpt.macm.model.dto.ScheduleDto;
import com.fpt.macm.model.entity.CompetitiveType;
import com.fpt.macm.model.entity.ExhibitionType;
import com.fpt.macm.model.entity.Tournament;
import com.fpt.macm.model.entity.TournamentSchedule;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.service.TournamentScheduleService;

@SpringBootTest
public class TournamentScheduleControllerTest {
	
	@MockBean
	TournamentScheduleService tournamentScheduleService;
	
	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;

	@InjectMocks
	TournamentScheduleController tournamentScheduleController;

	@BeforeEach
	public void setup() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
	}
	
	private Tournament tournament() {
		Tournament tournament = new Tournament();
		tournament.setId(1);
		tournament.setName("Giải đấu FNC Summer2022");
		tournament.setDescription("Giải đấu cho thành viên FNC Summer2022");
		tournament.setFeeOrganizingCommiteePay(100000);
		tournament.setSemester("Summer2022");
		Set<CompetitiveType> competitiveTypes = new HashSet<CompetitiveType>();
		CompetitiveType competitiveType = new CompetitiveType();
		competitiveType.setGender(true);
		competitiveType.setWeightMax(80);
		competitiveType.setWeightMin(77);
		competitiveTypes.add(competitiveType);
		Set<ExhibitionType> exhibitionTypes = new HashSet<ExhibitionType>();
		ExhibitionType exhibitionType = new ExhibitionType();
		exhibitionType.setName("Khai nguyên");
		exhibitionType.setNumberMale(3);
		exhibitionType.setNumberFemale(3);
		exhibitionTypes.add(exhibitionType);
		tournament.setCompetitiveTypes(competitiveTypes);
		tournament.setExhibitionTypes(exhibitionTypes);
		tournament.setTotalAmount(500000);
		tournament.setFeePlayerPay(20000);
		tournament.setStatus(true);
		return tournament;
	}
	
	private ScheduleDto scheduleDto() {
		ScheduleDto scheduleDto = new ScheduleDto();
		scheduleDto.setDate(LocalDate.now().plusMonths(1));
		scheduleDto.setStartTime(LocalTime.of(18, 0));
		scheduleDto.setFinishTime(LocalTime.of(20, 0));
		scheduleDto.setExisted(false);
		scheduleDto.setTitle("Giải đấu FNC Summer2022 update");
		return scheduleDto;
	}
	
	private TournamentSchedule tournamentSchedule() {
		TournamentSchedule tournamentSchedule = new TournamentSchedule();
		tournamentSchedule.setId(1);
		tournamentSchedule.setDate(LocalDate.now().plusMonths(1));
		tournamentSchedule.setStartTime(LocalTime.of(18, 0));
		tournamentSchedule.setFinishTime(LocalTime.of(20, 0));
		tournamentSchedule.setTournament(tournament());
		return tournamentSchedule;
	}
	
	@Test
	public void createTournamentSchedulePreviewSuccessTest() throws Exception {
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(Arrays.asList(scheduleDto()));
		
		when(tournamentScheduleService.createPreviewTournamentSchedule(anyString(), anyString(), anyString(), anyString(), anyString())).thenReturn(responseMessage);
		
		this.mockMvc.perform(post("/api/tournamentschedule/headclub/createpreview")
				.param("tournamentName", "Giải đấu cho thành viên FNC Summer2022")
				.param("startDate", "01/08/2022")
				.param("finishDate", "01/08/2022")
				.param("startTime", "18:00:00")
				.param("finishTime", "20:00:00"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value("1"));
	}
	
//	@Test
//	public void createTournamentScheduleSuccessTest() throws Exception {
//		ResponseMessage responseMessage = new ResponseMessage();
//		responseMessage.setData(Arrays.asList(tournamentSchedule()));
//		
//		when(tournamentScheduleService.createTournamentSchedule(anyInt(), anyList(), anyBoolean())).thenReturn(responseMessage);
//		
//		this.mockMvc.perform(post("/api/tournamentschedule/headclub/addnewschedule/{tournamentId}", "1")
//				.param("isOverwritten", "false")
//				.content(asJsonString(Arrays.asList(scheduleDto()))).contentType(MediaType.APPLICATION_JSON))
//		.andExpect(status().isOk())
//		.andExpect(content()
//				.contentType(MediaType.APPLICATION_JSON))
//		.andExpect(jsonPath("$.data.size()").value("1"));
//	}
	
	@Test
	public void createTournamentScheduleSessionSuccessTest() throws Exception {
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(Arrays.asList(tournamentSchedule()));
		
		when(tournamentScheduleService.createTournamentSession(anyInt(), any())).thenReturn(responseMessage);
		
		this.mockMvc.perform(post("/api/tournamentschedule/headclub/tournamentschedule/create/{tournamentId}", "1")
				.param("isOverwritten", "false")
				.content(asJsonString(tournamentSchedule())).contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value("1"));
	}
	
	@Test
	public void updateTournamentScheduleSessionSuccessTest() throws Exception {
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(Arrays.asList(tournamentSchedule()));
		
		when(tournamentScheduleService.updateTournamentSession(anyInt(), any())).thenReturn(responseMessage);
		
		this.mockMvc.perform(put("/api/tournamentschedule/headclub/tournamentschedule/update/{tournamentSessionId}", "1")
				.content(asJsonString(tournamentSchedule())).contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value("1"));
	}
	
	@Test
	public void deleteTournamentScheduleSessionSuccessTest() throws Exception {
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(Arrays.asList(tournamentSchedule()));
		
		when(tournamentScheduleService.deleteTournamentSession(anyInt())).thenReturn(responseMessage);
		
		this.mockMvc.perform(delete("/api/tournamentschedule/headclub/tournamentschedule/delete/{tournamentSessionId}", "1"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value("1"));
	}
	
	@Test
	public void getTournamentScheduleSessionSuccessTest() throws Exception {
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(Arrays.asList(tournamentSchedule()));
		
		when(tournamentScheduleService.getListTournamentScheduleByTournament(anyInt())).thenReturn(responseMessage);
		
		this.mockMvc.perform(get("/api/tournamentschedule/headclub/tournamentschedule/{tournamentId}", "1"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
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
