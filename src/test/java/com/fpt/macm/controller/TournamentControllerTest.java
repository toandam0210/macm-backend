package com.fpt.macm.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashSet;
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
import com.fpt.macm.dto.CompetitiveTypeDto;
import com.fpt.macm.dto.ExhibitionTypeDto;
import com.fpt.macm.dto.TournamentDto;
import com.fpt.macm.model.CompetitiveType;
import com.fpt.macm.model.ExhibitionType;
import com.fpt.macm.model.Tournament;

@SpringBootTest
public class TournamentControllerTest {
	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;

	@InjectMocks
	TournamentController tournamentController;

	@BeforeEach
	public void setup() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
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
	
	public TournamentDto tournamentDto() {
		TournamentDto tournamentDto = new TournamentDto();
		tournamentDto.setName("Giải đấu FNC Summer2022 update");
		tournamentDto.setDescription("Giải đấu cho thành viên FNC Summer2022");
		tournamentDto.setMaxQuantityComitee(15);
		tournamentDto.setFeeOrganizingCommiteePay(100000);
		Set<CompetitiveTypeDto> competitiveTypeDtos = new HashSet<CompetitiveTypeDto>();
		CompetitiveTypeDto competitiveTypeDto1 = new CompetitiveTypeDto();
		competitiveTypeDto1.setGender(true);
		competitiveTypeDto1.setWeightMax(80);
		competitiveTypeDto1.setWeightMin(77);
		CompetitiveTypeDto competitiveTypeDto = new CompetitiveTypeDto();
		competitiveTypeDto.setGender(true);
		competitiveTypeDto.setId(25);
		competitiveTypeDto.setWeightMax(76);
		competitiveTypeDto.setWeightMin(73);
		competitiveTypeDtos.add(competitiveTypeDto);
		competitiveTypeDtos.add(competitiveTypeDto1);
		Set<ExhibitionTypeDto> exhibitionTypeDtos = new HashSet<ExhibitionTypeDto>();
		ExhibitionTypeDto exhibitionTypeDto = new ExhibitionTypeDto();
		exhibitionTypeDto.setId(19);
		exhibitionTypeDto.setName("Khai nguyên");
		exhibitionTypeDto.setNumberMale(3);
		exhibitionTypeDto.setNumberFemale(3);
		
		ExhibitionTypeDto exhibitionTypeDto1 = new ExhibitionTypeDto();
		exhibitionTypeDto1.setName("Khai nguyên 1");
		exhibitionTypeDto1.setNumberMale(3);
		exhibitionTypeDto1.setNumberFemale(3);
		exhibitionTypeDtos.add(exhibitionTypeDto);
		exhibitionTypeDtos.add(exhibitionTypeDto1);
		tournamentDto.setCompetitiveTypesDto(competitiveTypeDtos);
		tournamentDto.setExhibitionTypesDto(exhibitionTypeDtos);
		tournamentDto.setTotalAmount(500000);
		tournamentDto.setFeePlayerPay(20000);
		return tournamentDto;
	}
	
//	@Test
//	public void createTournamentSuccessTest() throws Exception {
//		Tournament tournament = tournament();
//		this.mockMvc.perform(post("/api/tournament/headclub/createtournament").content(asJsonString(tournament))
//		.contentType(MediaType.APPLICATION_JSON))
//		.andExpect(status().isOk())
//		.andExpect(content()
//				.contentType(MediaType.APPLICATION_JSON))
//		.andExpect(jsonPath("$.data.size()").value("1"));
//	}
	
	@Test
	public void updateTournamentSuccessTest() throws Exception {
		TournamentDto tournamentDto = tournamentDto();
		this.mockMvc.perform(put("/api/tournament/headclub/update/{tournamentId}", "3").content(asJsonString(tournamentDto))
		.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content()
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value("1"));
	}
	
	@Test
	public void getTournamentByIdSuccessTest() throws Exception {
		this.mockMvc.perform(get("/api/tournament/headclub/tournament/{tournamentId}", "3")
		.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content()
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value("1"));
	}
	
	@Test
	public void getAllTournamentBySemesterSuccessTest() throws Exception {
		this.mockMvc.perform(get("/api/tournament/headclub/tournament/getall").param("semester", "Summer2022")
		.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content()
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value("3"));
	}
	
	public static String asJsonString(final Object obj) {
	    try {
	      return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	      throw new RuntimeException(e);
	    }
	  }
}
