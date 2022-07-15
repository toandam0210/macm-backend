package com.fpt.macm.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import com.fpt.macm.model.dto.FacilityDto;
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
		facilityDto.setFacilityName("Giáp tay");
		facilityDto.setFacilityCategoryName("Giáp");
		facilityDto.setFacilityCategoryId(1);
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
	public void getAllFacilitySuccessByFacilityCategoryIdFail() throws Exception {
		this.mockMvc
				.perform(get("/api/facility/headtechnique/getallfacilitybyfacilitycategoryid")
						.param("facilityCategoryId", "100").param("pageSize", "100"))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.data.size()").value(0));
	}

	@Test
	public void createNewFacilitySuccess() throws Exception{
		FacilityDto facilityDto = createFacilityDto();
		facilityDto.setFacilityName("Dao găm");
		facilityDto.setFacilityCategoryId(2);
		facilityDto.setFacilityCategoryName("Vũ khí");
		this.mockMvc.perform(post("/api/facility/headtechnique/createnewfacility")
		.content(asJsonString(facilityDto))
		.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value(1));
	}
	
	@Test
	public void createNewFacilityFail() throws Exception{
		FacilityDto facilityDto = createFacilityDto();
		this.mockMvc.perform(post("/api/facility/headtechnique/createnewfacility")
		.content(asJsonString(facilityDto))
		.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value(0));
	}
	
	@Test
	public void updateFacilitySucessName() throws Exception{
		FacilityDto facilityDto = createFacilityDto();
		facilityDto.setFacilityName("Giáp tay 2");
		facilityDto.setFacilityCategoryName("Giáp");
		facilityDto.setFacilityCategoryId(1);
		facilityDto.setQuantityUsable(0);
		facilityDto.setQuantityBroken(0);
		this.mockMvc.perform(put("/api/facility/headtechnique/updatefacilitybyid/{facilityId}", "1")
		.content(asJsonString(facilityDto))
		.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value(1));
	}
	
	@Test
	public void updateFacilitySucessCategory() throws Exception{
		FacilityDto facilityDto = createFacilityDto();
		facilityDto.setFacilityName("Giáp tay 2");
		facilityDto.setFacilityCategoryName("Vũ khí");
		facilityDto.setFacilityCategoryId(2);
		facilityDto.setQuantityUsable(0);
		facilityDto.setQuantityBroken(0);
		this.mockMvc.perform(put("/api/facility/headtechnique/updatefacilitybyid/{facilityId}", "1")
		.content(asJsonString(facilityDto))
		.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value(1));
	}
	
	@Test
	public void updateFacilitySucessQuantity() throws Exception{
		FacilityDto facilityDto = createFacilityDto();
		facilityDto.setFacilityName("Giáp tay 2");
		facilityDto.setFacilityCategoryName("Vũ khí");
		facilityDto.setFacilityCategoryId(2);
		facilityDto.setQuantityUsable(10);
		facilityDto.setQuantityBroken(0);
		this.mockMvc.perform(put("/api/facility/headtechnique/updatefacilitybyid/{facilityId}", "1")
		.content(asJsonString(facilityDto))
		.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value(1));
	}
	
	@Test
	public void updateFacilityFailName() throws Exception{
		FacilityDto facilityDto = createFacilityDto();
		facilityDto.setFacilityName("Súng");
		facilityDto.setFacilityCategoryName("Vũ khí");
		facilityDto.setFacilityCategoryId(2);
		facilityDto.setQuantityUsable(10);
		facilityDto.setQuantityBroken(0);
		this.mockMvc.perform(put("/api/facility/headtechnique/updatefacilitybyid/{facilityId}", "1")
		.content(asJsonString(facilityDto))
		.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value(0));
	}
	
	@Test
	public void updateFacilityFailQuantity() throws Exception{
		FacilityDto facilityDto = createFacilityDto();
		facilityDto.setFacilityName("Giáp tay 2");
		facilityDto.setFacilityCategoryName("Vũ khí");
		facilityDto.setFacilityCategoryId(2);
		facilityDto.setQuantityUsable(10);
		facilityDto.setQuantityBroken(15);
		this.mockMvc.perform(put("/api/facility/headtechnique/updatefacilitybyid/{facilityId}", "1")
		.content(asJsonString(facilityDto))
		.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value(0));
	}
	
	@Test
	public void getAllFacilityReportSuccess() throws Exception {
		this.mockMvc.perform(get("/api/facility/headtechnique/getfacilityreport"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value(43));
	}
	
	@Test
	public void getAllFacilityReportIsAddSuccess() throws Exception {
		this.mockMvc.perform(get("/api/facility/headtechnique/getfacilityreport").param("filterIndex", "1"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value(23));
	}
	
	@Test
	public void getAllFacilityReportIsSubSuccess() throws Exception {
		this.mockMvc.perform(get("/api/facility/headtechnique/getfacilityreport").param("filterIndex", "2"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value(20));
	}


	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
