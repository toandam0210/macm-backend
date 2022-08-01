package com.fpt.macm.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fpt.macm.model.entity.CompetitivePlayer;
import com.fpt.macm.model.entity.CompetitivePlayerBracket;
import com.fpt.macm.model.entity.CompetitiveType;
import com.fpt.macm.model.entity.ExhibitionPlayer;
import com.fpt.macm.model.entity.ExhibitionTeam;
import com.fpt.macm.model.entity.ExhibitionType;
import com.fpt.macm.model.entity.Role;
import com.fpt.macm.model.entity.Tournament;
import com.fpt.macm.model.entity.TournamentPlayer;
import com.fpt.macm.model.entity.User;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.repository.CompetitivePlayerBracketRepository;
import com.fpt.macm.repository.CompetitivePlayerRepository;
import com.fpt.macm.repository.CompetitiveTypeRepository;
import com.fpt.macm.repository.TournamentPlayerRepository;
import com.fpt.macm.repository.TournamentRepository;
import com.fpt.macm.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class CompetitivePlayerServiceTest {
	@InjectMocks
	CompetitivePlayerService competitivePlayerService = new CompetitivePlayerServiceImpl();
	
	@Mock
	TournamentPlayerRepository tournamentPlayerRepository;
	
	@Mock
	UserRepository userRepository;
	
	@Mock
	TournamentRepository tournamentRepository;
	
	@Mock
	CompetitivePlayerRepository competitivePlayerRepository;
	
	@Mock
	CompetitiveTypeRepository competitiveTypeRepository;
	
	@Mock
	CompetitivePlayerBracketRepository competitivePlayerBracketRepository;
	
	private Tournament tournament() {
		Tournament tournament = new Tournament();
		tournament.setCompetitiveTypes(competitiveTypes());
		tournament.setDescription("abc");
		tournament.setExhibitionTypes(exhibitionTypes());
		tournament.setFeeOrganizingCommiteePay(100000);
		tournament.setFeePlayerPay(100000);
		tournament.setId(1);
		tournament.setMaxQuantityComitee(10);
		tournament.setName("FNC");
		tournament.setRegistrationOrganizingCommitteeDeadline(LocalDateTime.of(2022, 8, 1, 18, 0));
		tournament.setRegistrationPlayerDeadline(LocalDateTime.of(2022, 8, 1, 18, 0));
		tournament.setSemester("Summer2022");
		tournament.setStatus(1);
		tournament.setTournamentPlayers(tournamentPlayers());
		return tournament;
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
	
	private TournamentPlayer tournamentPlayer() {
		TournamentPlayer tournamentPlayer = new TournamentPlayer();
		tournamentPlayer.setId(1);
		tournamentPlayer.setPaymentStatus(true);
		tournamentPlayer.setUser(createUser());
		return tournamentPlayer;
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
	
	private Set<TournamentPlayer> tournamentPlayers() {
		Set<TournamentPlayer> tournamentPlayers = new HashSet<TournamentPlayer>();
		TournamentPlayer tournamentPlayer = new TournamentPlayer();
		tournamentPlayer.setId(1);
		tournamentPlayer.setPaymentStatus(true);
		tournamentPlayer.setUser(createUser());
		tournamentPlayers.add(tournamentPlayer);
		return tournamentPlayers;
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
	
	private Set<ExhibitionPlayer> exhibitionPlayers() {
		Set<ExhibitionPlayer> exhibitionPlayers = new HashSet<ExhibitionPlayer>();
		ExhibitionPlayer exhibitionPlayer = new ExhibitionPlayer();
		exhibitionPlayer.setId(1);
		exhibitionPlayer.setRoleInTeam(true);
		exhibitionPlayer.setTournamentPlayer(tournamentPlayer());
		exhibitionPlayers.add(exhibitionPlayer);
		return exhibitionPlayers;
	}
	
	private CompetitivePlayer competitivePlayer() {
		CompetitivePlayer competitivePlayer = new CompetitivePlayer();
		competitivePlayer.setId(1);
		competitivePlayer.setTournamentPlayer(tournamentPlayer());
		competitivePlayer.setWeight(50);
		return competitivePlayer;
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
	
	@Test
	public void testAddNewCompetitivePlayer() {
		Set<CompetitiveType> competitiveTypes = competitiveTypes();
		List<CompetitiveType> listCompetitive = new ArrayList<CompetitiveType>(competitiveTypes);
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament()));
		when(userRepository.findById(anyInt())).thenReturn(Optional.of(createUser()));
		when(tournamentPlayerRepository.findPlayerByUserIdAndTournamentId(anyInt(), anyInt())).thenReturn(Optional.of(tournamentPlayer()));
		when(competitiveTypeRepository.findByTournamentAndGender(anyInt(), anyBoolean())).thenReturn(listCompetitive);
		when(competitivePlayerRepository.findByTournamentPlayerId(anyInt()))
		.thenReturn(Optional.of(competitivePlayer()));
		ResponseMessage response = competitivePlayerService.addNewCompetitivePlayer(1, 1, 58);
		assertEquals(response.getData().size(), 1);
		
	}
	
	@Test
	public void testAddNewCompetitivePlayerCaseTournamentPlayerPresent() {
		Set<CompetitiveType> competitiveTypes = competitiveTypes();
		List<CompetitiveType> listCompetitive = new ArrayList<CompetitiveType>(competitiveTypes);
		when(tournamentPlayerRepository.getPlayerByUserIdAndTournamentId(anyInt(), anyInt())).thenReturn(Optional.of(tournamentPlayer()));
		when(competitiveTypeRepository.findByTournamentAndGender(anyInt(), anyBoolean())).thenReturn(listCompetitive);
		when(userRepository.findById(anyInt())).thenReturn(Optional.of(createUser()));
		when(competitivePlayerRepository.findCompetitivePlayerByTournamentPlayerId(anyInt())).thenReturn(Optional.of(competitivePlayer()));
		ResponseMessage response = competitivePlayerService.addNewCompetitivePlayer(1, 1, 58);
		assertEquals(response.getData().size(), 1);
	}
	
	@Test
	public void testAddNewCompetitivePlayerCaseTournamentPlayerPresentAndWeightInvalid() {
		Set<CompetitiveType> competitiveTypes = competitiveTypes();
		List<CompetitiveType> listCompetitive = new ArrayList<CompetitiveType>(competitiveTypes);
		when(tournamentPlayerRepository.getPlayerByUserIdAndTournamentId(anyInt(), anyInt())).thenReturn(Optional.of(tournamentPlayer()));
		when(competitiveTypeRepository.findByTournamentAndGender(anyInt(), anyBoolean())).thenReturn(listCompetitive);
		when(userRepository.findById(anyInt())).thenReturn(Optional.of(createUser()));
		ResponseMessage response = competitivePlayerService.addNewCompetitivePlayer(1, 1, 50);
		assertEquals(response.getData().size(), 1);
	}
	
	@Test
	public void testAddNewCompetitivePlayerCaseTournamentPlayerPresentAndWeightEq0() {
		when(tournamentPlayerRepository.getPlayerByUserIdAndTournamentId(anyInt(), anyInt())).thenReturn(Optional.of(tournamentPlayer()));
		ResponseMessage response = competitivePlayerService.addNewCompetitivePlayer(1, 1, 0);
		assertEquals(response.getData().size(), 1);
	}
	
	@Test
	public void testAddNewCompetitivePlayerCaseCompetitivePlayerPresent() {
		when(tournamentPlayerRepository.getPlayerByUserIdAndTournamentId(anyInt(), anyInt())).thenReturn(Optional.of(tournamentPlayer()));
		when(competitivePlayerRepository.findByTournamentPlayerId(anyInt())).thenReturn(Optional.of(competitivePlayer()));
		ResponseMessage response = competitivePlayerService.addNewCompetitivePlayer(1, 1, 58);
		assertEquals(response.getData().size(), 0);
	}
	
	@Test
	public void testAddNewCompetitivePlayerCaseException() {
		when(tournamentPlayerRepository.getPlayerByUserIdAndTournamentId(anyInt(), anyInt())).thenReturn(null);
		ResponseMessage response = competitivePlayerService.addNewCompetitivePlayer(1, 1, 58);
		assertEquals(response.getData().size(), 0);
	}
	
	@Test
	public void testAddNewCompetitivePlayerCaseWeightEq0() {
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament()));
		when(userRepository.findById(anyInt())).thenReturn(Optional.of(createUser()));
		when(tournamentPlayerRepository.findPlayerByUserIdAndTournamentId(anyInt(), anyInt())).thenReturn(Optional.of(tournamentPlayer()));
		ResponseMessage response = competitivePlayerService.addNewCompetitivePlayer(1, 1, 0);
		assertEquals(response.getData().size(), 1);
		
	}
	
	@Test
	public void testAddNewCompetitivePlayerCaseWeightInvalid() {
		Set<CompetitiveType> competitiveTypes = competitiveTypes();
		List<CompetitiveType> listCompetitive = new ArrayList<CompetitiveType>(competitiveTypes);
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament()));
		when(userRepository.findById(anyInt())).thenReturn(Optional.of(createUser()));
		when(tournamentPlayerRepository.findPlayerByUserIdAndTournamentId(anyInt(), anyInt())).thenReturn(Optional.of(tournamentPlayer()));
		when(competitiveTypeRepository.findByTournamentAndGender(anyInt(), anyBoolean())).thenReturn(listCompetitive);
		ResponseMessage response = competitivePlayerService.addNewCompetitivePlayer(1, 1, 50);
		assertEquals(response.getData().size(), 1);
		
	}	
	
	@Test 
	public void testUpdateWeightForCompetitivePlayerCaseWeightEq0() {
		Set<CompetitiveType> competitiveTypes = competitiveTypes();
		List<CompetitiveType> listCompetitive = new ArrayList<CompetitiveType>(competitiveTypes);
		CompetitivePlayer competitivePlayer = competitivePlayer();
		competitivePlayer.setWeight(0);
		when(competitivePlayerRepository.findById(anyInt())).thenReturn(Optional.of(competitivePlayer));
		when(competitiveTypeRepository.findByTournamentAndGender(anyInt(), anyBoolean())).thenReturn(listCompetitive);
		when(competitiveTypeRepository.findTournamentByCompetitivePlayerId(anyInt())).thenReturn(1);
		ResponseMessage response = competitivePlayerService.updateWeightForCompetitivePlayer(1, 58);
		assertEquals(response.getData().size(), 1);
	}
	
	@Test 
	public void testUpdateWeightForCompetitivePlayer() {
		when(competitivePlayerRepository.findById(anyInt())).thenReturn(Optional.of(competitivePlayer()));
		when(competitivePlayerBracketRepository.findByPlayerId(anyInt())).thenReturn(Optional.of(competitivePlayerBracket()));
		ResponseMessage response = competitivePlayerService.updateWeightForCompetitivePlayer(1, 50);
		assertEquals(response.getData().size(), 1);
	}
	
	@Test 
	public void testUpdateWeightForCompetitivePlayerCaseEmpty() {
		when(competitivePlayerRepository.findById(anyInt())).thenReturn(Optional.empty());
		ResponseMessage response = competitivePlayerService.updateWeightForCompetitivePlayer(1, 50);
		assertEquals(response.getData().size(), 0);
	}
	
	@Test 
	public void testUpdateWeightForCompetitivePlayerCaseException() {
		when(competitivePlayerRepository.findById(anyInt())).thenReturn(null);
		ResponseMessage response = competitivePlayerService.updateWeightForCompetitivePlayer(1, 50);
		assertEquals(response.getData().size(), 0);
	}
	
	@Test
	public void testDeleteCompetitivePlayer() {
		when(competitivePlayerRepository.findById(anyInt())).thenReturn(Optional.of(competitivePlayer()));
		when(competitivePlayerBracketRepository.findByPlayerId(anyInt())).thenReturn(Optional.of(competitivePlayerBracket()));
		ResponseMessage response = competitivePlayerService.deleteCompetitivePlayer(1);
		assertEquals(response.getData().size(), 1);
	}
	
	@Test
	public void testDeleteCompetitivePlayerCaseEmpty() {
		when(competitivePlayerRepository.findById(anyInt())).thenReturn(Optional.empty());
		ResponseMessage response = competitivePlayerService.deleteCompetitivePlayer(1);
		assertEquals(response.getData().size(), 0);
	}
	
	@Test
	public void testDeleteCompetitivePlayerCaseBracketEmpty() {
		when(competitivePlayerRepository.findById(anyInt())).thenReturn(Optional.of(competitivePlayer()));
		when(competitivePlayerBracketRepository.findByPlayerId(anyInt())).thenReturn(Optional.empty());
		ResponseMessage response = competitivePlayerService.deleteCompetitivePlayer(1);
		assertEquals(response.getData().size(), 1);
	}
	
	@Test
	public void testDeleteCompetitivePlayerCaseException() {
		when(competitivePlayerRepository.findById(anyInt())).thenReturn(null);
		ResponseMessage response = competitivePlayerService.deleteCompetitivePlayer(1);
		assertEquals(response.getData().size(), 0);
	}
	
	@Test
	public void testListUserNotJoinCompetitive() {
		when(userRepository.findAllActiveUser()).thenReturn(Arrays.asList(createUser()));
		when(tournamentPlayerRepository.getPlayerByTournamentId(anyInt())).thenReturn(Arrays.asList(tournamentPlayer()));
		when(competitivePlayerRepository.findByTournamentPlayerId(anyInt()))
		.thenReturn(Optional.of(competitivePlayer()));
		ResponseMessage response = competitivePlayerService.listUserNotJoinCompetitive(1);
		assertEquals(response.getData().size(), 1);
	}
	
	@Test
	public void testListUserNotJoinCompetitiveCaseCompetitivePlayerEmpty() {
		when(userRepository.findAllActiveUser()).thenReturn(Arrays.asList(createUser()));
		when(tournamentPlayerRepository.getPlayerByTournamentId(anyInt())).thenReturn(Arrays.asList(tournamentPlayer()));
		when(competitivePlayerRepository.findByTournamentPlayerId(anyInt()))
		.thenReturn(Optional.empty());
		ResponseMessage response = competitivePlayerService.listUserNotJoinCompetitive(1);
		assertEquals(response.getData().size(), 1);
	}
	
	@Test
	public void testListUserNotJoinCompetitiveCaseException() {
		when(userRepository.findAllActiveUser()).thenReturn(Arrays.asList(createUser()));
		when(tournamentPlayerRepository.getPlayerByTournamentId(anyInt())).thenReturn(Arrays.asList(tournamentPlayer()));
		when(competitivePlayerRepository.findByTournamentPlayerId(anyInt()))
		.thenReturn(null);
		ResponseMessage response = competitivePlayerService.listUserNotJoinCompetitive(1);
		assertEquals(response.getData().size(), 0);
	}
	
}
