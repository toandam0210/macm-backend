package com.fpt.macm.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.fpt.macm.model.entity.Rule;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.repository.RuleRepository;

@ExtendWith(MockitoExtension.class)
public class RuleServiceTest {

	@InjectMocks
	RuleService ruleService = new RuleServiceImpl();
	
	@Mock
	RuleRepository ruleRepository;
	
	private Rule rule() {
		Rule rule = new Rule();
		rule.setId(1);
		rule.setDescription("Đi tập đúng giờ");
		return rule;
	}
	
	@Test
	public void getAllRuleCaseSuccess() {
		when(ruleRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(Arrays.asList(rule())));
		
		ResponseMessage responseMessage = ruleService.getAllRule(0, 1000, "id");
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void getAllRuleCasePageEmpty() {
		when(ruleRepository.findAll(any(Pageable.class))).thenReturn(Page.empty());
		
		ResponseMessage responseMessage = ruleService.getAllRule(0, 1000, "id");
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void getAllRuleCasePageNull() {
		when(ruleRepository.findAll(any(Pageable.class))).thenReturn(null);
		
		ResponseMessage responseMessage = ruleService.getAllRule(0, 1000, "id");
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void addRuleCaseSuccess() {
		ResponseMessage responseMessage = ruleService.addRule(rule());
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void editRuleCaseSuccess() {
		when(ruleRepository.findById(anyInt())).thenReturn(Optional.of(rule()));
		
		ResponseMessage responseMessage = ruleService.editRule(1, rule());
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void editRuleCaseException() {
		when(ruleRepository.findById(anyInt())).thenReturn(null);
		
		ResponseMessage responseMessage = ruleService.editRule(1, rule());
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void deleteRuleCaseSuccess() {
		when(ruleRepository.findById(anyInt())).thenReturn(Optional.of(rule()));
		
		ResponseMessage responseMessage = ruleService.deleteRule(1);
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void deleteRuleCaseException() {
		when(ruleRepository.findById(anyInt())).thenReturn(null);
		
		ResponseMessage responseMessage = ruleService.deleteRule(1);
		assertEquals(responseMessage.getData().size(), 0);
	}
	
}
