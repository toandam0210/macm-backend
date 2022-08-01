package com.fpt.macm.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fpt.macm.model.entity.CompetitivePlayer;
import com.fpt.macm.model.entity.CompetitivePlayerBracket;
import com.fpt.macm.model.entity.CompetitiveType;
import com.fpt.macm.model.entity.Role;
import com.fpt.macm.model.entity.TournamentPlayer;
import com.fpt.macm.model.entity.User;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.repository.CompetitivePlayerBracketRepository;

@ExtendWith(MockitoExtension.class)
public class CompetitivePlayerBracketServiceTest {
	
	@InjectMocks
	CompetitivePlayerBracketService competitivePlayerBracketService = new CompetitivePlayerBracketServiceImpl();
	
	@Mock
	CompetitivePlayerBracketRepository competitivePlayerBracketRepository;
	
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
	
	@Test
	public void testGetListPlayerBracket() {
		CompetitivePlayerBracket competitivePlayerBracket = competitivePlayerBracket();
		competitivePlayerBracket.setNumericalOrderId(null);
		when(competitivePlayerBracketRepository.listPlayersByType(anyInt())).thenReturn(Arrays.asList(competitivePlayerBracket));
		ResponseMessage response = competitivePlayerBracketService.getListPlayerBracket(1);
		assertEquals(response.getData().size(), 0);
	}
	
	@Test
	public void testGetListPlayerBracketCaseIsOrder() {
		when(competitivePlayerBracketRepository.listPlayersByType(anyInt())).thenReturn(Arrays.asList(competitivePlayerBracket()));
		ResponseMessage response = competitivePlayerBracketService.getListPlayerBracket(1);
		assertEquals(response.getData().size(), 0);
	}
	
	@Test
	public void testGetListPlayerBracketCaseException() {
		when(competitivePlayerBracketRepository.listPlayersByType(anyInt())).thenReturn(null);
		ResponseMessage response = competitivePlayerBracketService.getListPlayerBracket(1);
		assertEquals(response.getData().size(), 0);
	}
}
