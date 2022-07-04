package com.fpt.macm.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.LocalTime;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fpt.macm.dto.ScheduleDto;
import com.fpt.macm.model.CompetitiveType;
import com.fpt.macm.model.ExhibitionType;
import com.fpt.macm.model.Tournament;
import com.fpt.macm.model.TournamentSchedule;

@SpringBootTest
public class TournamentScheduleControllerTest {
	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;

	@InjectMocks
	TournamentScheduleController tournamentScheduleController;

	@BeforeEach
	public void setup() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
	}
	
	@Test
	public void createTournamentSchedulePreviewSuccessTest() throws Exception {
		this.mockMvc.perform(post("/api/tournamentschedule/headclub/createpreview").param("tournamentName", "Giải đấu cho thành viên FNC Summer2022")
		.param("startDate", "01/08/2022").param("finishDate", "01/08/2022").param("startTime", "18:00:00").param("finishTime", "20:00:00"))
		.andExpect(status().isOk())
		.andExpect(content()
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value("1"));
	}
	
	@Test
	public void createTournamentSchedulePreviewFailTest() throws Exception {
		this.mockMvc.perform(post("/api/tournamentschedule/headclub/createpreview").param("tournamentName", "Giải đấu cho thành viên FNC Summer2022")
		.param("startDate", "02/08/2022").param("finishDate", "01/08/2022").param("startTime", "18:00:00").param("finishTime", "20:00:00"))
		.andExpect(status().isOk())
		.andExpect(content()
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value("0"));
	}
	
	@Test
	public void createTournamentSchedulePreviewFailTest1() throws Exception {
		this.mockMvc.perform(post("/api/tournamentschedule/headclub/createpreview").param("tournamentName", "Giải đấu cho thành viên FNC Summer2022")
		.param("startDate", "01/08/2022").param("finishDate", "01/08/2022").param("startTime", "18:00:00").param("finishTime", "15:00:00"))
		.andExpect(status().isOk())
		.andExpect(content()
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value("0"));
	}
	@Test
	public void createTournamentSchedulePreviewFailTest2() throws Exception {
		this.mockMvc.perform(post("/api/tournamentschedule/headclub/createpreview").param("tournamentName", "Giải đấu cho thành viên FNC Summer2022")
		.param("startDate", "05/06/2022").param("finishDate", "01/08/2022").param("startTime", "18:00:00").param("finishTime", "20:00:00"))
		.andExpect(status().isOk())
		.andExpect(content()
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value("0"));
	}
	@Test
	public void createTournamentSchedulePreviewFailTest3() throws Exception {
		this.mockMvc.perform(post("/api/tournamentschedule/headclub/createpreview").param("tournamentName", "Giải đấu cho thành viên FNC Summer2022")
		.param("startDate", "10/10/2022").param("finishDate", "10/10/2022").param("startTime", "18:00:00").param("finishTime", "20:00:00"))
		.andExpect(status().isOk())
		.andExpect(content()
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value("0"));
	}
	
	@Test
	public void createTournamentSchedulePreviewFailTest4() throws Exception {
		this.mockMvc.perform(post("/api/tournamentschedule/headclub/createpreview").param("tournamentName", "Giải đấu cho thành viên FNC Summer2022")
		.param("startDate", "05/07/2022").param("finishDate", "05/07/2022").param("startTime", "18:00:00").param("finishTime", "20:00:00"))
		.andExpect(status().isOk())
		.andExpect(content()
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value("1"));
	}
	
	@Test
	public void createTournamentScheduleSuccessTest() throws Exception {
		List<ScheduleDto> scheduleDtos = new ArrayList<>();
		ScheduleDto scheduleDto = new ScheduleDto();
		String localDate = "2022-08-03";
		LocalDate date = LocalDate.parse(localDate);
		scheduleDto.setDate(date);
		scheduleDto.setStartTime(LocalTime.parse("18:00:01"));
		scheduleDto.setFinishTime(LocalTime.parse("20:00:01"));
		scheduleDto.setExisted(false);
		scheduleDto.setTitle("Giải đấu FNC Summer2022 update");
		scheduleDtos.add(scheduleDto);
		this.mockMvc.perform(post("/api/tournamentschedule/headclub/addnewschedule/{tournamentId}", 3).param("isOverwritten", "false").content(asJsonString(Arrays.asList(scheduleDto))).contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content()
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value("1"));
	}
	
	@Test
	public void createTournamentScheduleSuccessTest1() throws Exception {
		List<ScheduleDto> scheduleDtos = new ArrayList<>();
		ScheduleDto scheduleDto = new ScheduleDto();
		String localDate = "2022-08-28";
		LocalDate date = LocalDate.parse(localDate);
		scheduleDto.setDate(date);
		scheduleDto.setStartTime(LocalTime.parse("18:00:01"));
		scheduleDto.setFinishTime(LocalTime.parse("20:00:01"));
		scheduleDto.setExisted(true);
		scheduleDto.setTitle("Trùng với Lịch tập");
		scheduleDtos.add(scheduleDto);
		this.mockMvc.perform(post("/api/tournamentschedule/headclub/addnewschedule/{tournamentId}", 3).param("isOverwritten", "true").content(asJsonString(Arrays.asList(scheduleDto))).contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content()
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value("1"));
	}
	
	@Test
	public void createTournamentScheduleSuccessTest2() throws Exception {
		List<ScheduleDto> scheduleDtos = new ArrayList<>();
		ScheduleDto scheduleDto = new ScheduleDto();
		String localDate = "2022-07-05";
		LocalDate date = LocalDate.parse(localDate);
		scheduleDto.setDate(date);
		scheduleDto.setStartTime(LocalTime.parse("18:00:01"));
		scheduleDto.setFinishTime(LocalTime.parse("20:00:01"));
		scheduleDto.setExisted(true);
		scheduleDto.setTitle("Trùng với Giải đấu FNC mở rộng");
		scheduleDtos.add(scheduleDto);
		this.mockMvc.perform(post("/api/tournamentschedule/headclub/addnewschedule/{tournamentId}", 3).param("isOverwritten", "false").content(asJsonString(Arrays.asList(scheduleDto))).contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content()
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value("0"));
	}
	
	@Test
	public void createTournamentScheduleSessionSuccessTest() throws Exception {
		Tournament tournament = tournament();
		TournamentSchedule tournamentSchedule = new TournamentSchedule();
		String localDate = "2022-08-04";
		LocalDate date = LocalDate.parse(localDate);
		tournamentSchedule.setDate(date);
		tournamentSchedule.setStartTime(LocalTime.parse("18:00:01"));
		tournamentSchedule.setFinishTime(LocalTime.parse("20:00:01"));
		tournamentSchedule.setTournament(tournament);
		this.mockMvc.perform(post("/api/tournamentschedule/headclub/tournamentschedule/create/{tournamentId}", 3).param("isOverwritten", "false").content(asJsonString(tournamentSchedule)).contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content()
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value("1"));
	}
	
	@Test
	public void createTournamentScheduleSessionFailTest() throws Exception {
		Tournament tournament = tournament();
		TournamentSchedule tournamentSchedule = new TournamentSchedule();
		String localDate = "2022-06-02";
		LocalDate date = LocalDate.parse(localDate);
		tournamentSchedule.setDate(date);
		tournamentSchedule.setStartTime(LocalTime.parse("18:00:01"));
		tournamentSchedule.setFinishTime(LocalTime.parse("20:00:01"));
		tournamentSchedule.setTournament(tournament);
		this.mockMvc.perform(post("/api/tournamentschedule/headclub/tournamentschedule/create/{tournamentId}", 3).param("isOverwritten", "false").content(asJsonString(tournamentSchedule)).contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content()
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value("0"));
	}
	
	@Test
	public void createTournamentScheduleSessionFailTest1() throws Exception {
		Tournament tournament = tournament();
		TournamentSchedule tournamentSchedule = new TournamentSchedule();
		String localDate = "2022-08-28";
		LocalDate date = LocalDate.parse(localDate);
		tournamentSchedule.setDate(date);
		tournamentSchedule.setStartTime(LocalTime.parse("18:00:01"));
		tournamentSchedule.setFinishTime(LocalTime.parse("20:00:01"));
		tournamentSchedule.setTournament(tournament);
		this.mockMvc.perform(post("/api/tournamentschedule/headclub/tournamentschedule/create/{tournamentId}", 3).param("isOverwritten", "false").content(asJsonString(tournamentSchedule)).contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content()
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value("0"));
	}
	
	@Test
	public void updateTournamentScheduleSessionSuccessTest1() throws Exception {
		Tournament tournament = tournament();
		TournamentSchedule tournamentSchedule = new TournamentSchedule();
		String localDate = "2022-08-01";
		LocalDate date = LocalDate.parse(localDate);
		tournamentSchedule.setDate(date);
		tournamentSchedule.setStartTime(LocalTime.parse("18:30:01"));
		tournamentSchedule.setFinishTime(LocalTime.parse("20:30:01"));
		tournamentSchedule.setTournament(tournament);
		this.mockMvc.perform(put("/api/tournamentschedule/headclub/tournamentschedule/update/{tournamentSessionId}", 19).content(asJsonString(tournamentSchedule)).contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content()
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value("1"));
	}
	
	@Test
	public void deleteTournamentScheduleSessionSuccessTest() throws Exception {
		this.mockMvc.perform(delete("/api/tournamentschedule/headclub/tournamentschedule/delete/{tournamentSessionId}", 21))
		.andExpect(status().isOk())
		.andExpect(content()
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value("1"));
	}
	
	@Test
	public void getTournamentScheduleSessionSuccessTest() throws Exception {
		this.mockMvc.perform(get("/api/tournamentschedule/headclub/tournamentschedule/{tournamentId}", 3))
		.andExpect(status().isOk())
		.andExpect(content()
				.contentType(MediaType.APPLICATION_JSON))
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
	public Tournament tournament() {
		Tournament tournament = new Tournament();
		tournament.setName("Giải đấu FNC Summer2022");
		tournament.setDescription("Giải đấu cho thành viên FNC Summer2022");
		tournament.setMaxQuantityComitee(10);
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
		return tournament;
	}
}
