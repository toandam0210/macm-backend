package com.fpt.macm.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fpt.macm.dto.FacilityDto;
import com.fpt.macm.service.FacilityCategoryService;
import com.fpt.macm.service.FacilityService;

@SpringBootTest
public class FacilityControllerTest {

	@Mock
	private FacilityService facilityService;

	@Mock
	private FacilityCategoryService facilityCategoryService;

	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;

	@InjectMocks
	FacilityController facilityController;

	@InjectMocks
	FacilityCategoryController facilityCategoryController;

	@BeforeEach
	public void setup() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
	}

	private FacilityDto createFacilityDto() {
		FacilityDto facilityDto = new FacilityDto();
		facilityDto.setFacilityName("Dao găm");
		facilityDto.setFacilityCategoryName("Vũ khí");
		facilityDto.setFacilityCategoryId(2);
		facilityDto.setQuantityUsable(10);
		facilityDto.setQuantityBroken(0);
		return facilityDto;
	}

	@Test
	public void getAllFacilitySuccess() throws Exception {
		this.mockMvc
				.perform(get("/api/facility/headtechnique/getallfacilitybyfacilitycategoryid").param("pageSize", "100"))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.data.size()").value(39));
	}

	@Test
	public void getAllFacilitySuccessByFacilityCategoryIdSuccess() throws Exception {
		this.mockMvc
				.perform(get("/api/facility/headtechnique/getallfacilitybyfacilitycategoryid")
						.param("facilityCategoryId", "1").param("pageSize", "100"))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.data.size()").value(22));
	}

	@Test
	public void getAllFacilitySuccessByFacilityCategoryIdFailure() throws Exception {
		this.mockMvc
				.perform(get("/api/facility/headtechnique/getallfacilitybyfacilitycategoryid")
						.param("facilityCategoryId", "100").param("pageSize", "100"))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.data.size()").value(0));
	}

	@Test
	public void createNewFacilitySuccess() throws Exception{
		FacilityDto facilityDto = createFacilityDto();
		this.mockMvc.perform(post("/api/facility/headtechnique/createnewfacility")
		.content(asJsonString(facilityDto))
		.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value(1));
	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
