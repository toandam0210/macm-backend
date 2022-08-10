package com.fpt.macm.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fpt.macm.model.entity.Area;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.repository.AreaRepository;

@ExtendWith(MockitoExtension.class)
public class AreaServiceTest {

	@InjectMocks
	AreaService areaService = new AreaServiceImpl();
	
	@Mock
	AreaRepository areaRepository;
	
	private Area area() {
		Area area = new Area();
		area.setId(1);
		area.setName("Sân 1");
		return area;
	}
	
	@Test
	public void getAllAreaCaseSuccess() {
		when(areaRepository.findAll()).thenReturn(Arrays.asList(area()));
		
		ResponseMessage responseMessage = areaService.getAllArea();
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void getAllAreaCaseException() {
		when(areaRepository.findAll()).thenReturn(null);
		
		ResponseMessage responseMessage = areaService.getAllArea();
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	
	
	@Test
	public void addNewAreaCaseSuccess() {
		ResponseMessage responseMessage = areaService.addNewArea(area());
		assertEquals(responseMessage.getData().size(), 1);
	}
	
}
