package com.fpt.macm.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
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

import com.fpt.macm.model.dto.CompetitiveMatchDto;
import com.fpt.macm.model.dto.PlayerMatchDto;
import com.fpt.macm.model.entity.Area;
import com.fpt.macm.model.entity.CompetitiveMatch;
import com.fpt.macm.model.entity.CompetitivePlayer;
import com.fpt.macm.model.entity.CompetitiveResult;
import com.fpt.macm.model.entity.CompetitiveType;
import com.fpt.macm.model.entity.ExhibitionPlayer;
import com.fpt.macm.model.entity.ExhibitionTeam;
import com.fpt.macm.model.entity.ExhibitionType;
import com.fpt.macm.model.entity.Role;
import com.fpt.macm.model.entity.Tournament;
import com.fpt.macm.model.entity.TournamentPlayer;
import com.fpt.macm.model.entity.User;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.repository.CompetitiveMatchRepository;
import com.fpt.macm.repository.CompetitivePlayerRepository;
import com.fpt.macm.repository.CompetitiveResultRepository;
import com.fpt.macm.repository.CompetitiveTypeRepository;
import com.fpt.macm.repository.TournamentRepository;
import com.fpt.macm.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class CompetititveMatchServiceTest {
	
	@InjectMocks
	CompetitiveMatchService competitiveMatchService = new CompetitiveMatchServiceImpl();
	
	@Mock
	CompetitiveMatchRepository competitiveMatchRepository;

//	@Mock
//	CompetitivePlayerBracketRepository competitivePlayerBracketRepository;

	@Mock
	CompetitiveTypeRepository competitiveTypeRepository;

	@Mock
	CompetitiveResultRepository competitiveResultRepository;

	@Mock
	TournamentRepository tournamentRepository;

	@Mock
	UserRepository userRepository; 
	
	@Mock
	CompetitivePlayerRepository competitivePlayerRepository;
	
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
		//tournament.setStatus(1);
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
	
//	private CompetitivePlayerBracket competitivePlayerBracket() {
//		Set<CompetitiveType> competitiveTypes = competitiveTypes();
//		List<CompetitiveType> listCompetitive = new ArrayList<CompetitiveType>(competitiveTypes);
//		CompetitiveType competitiveType = listCompetitive.get(0);
//		CompetitivePlayerBracket competitivePlayerBracket = new CompetitivePlayerBracket();
//		competitivePlayerBracket.setCompetitivePlayer(competitivePlayer());
//		competitivePlayerBracket.setCompetitiveType(competitiveType);
//		competitivePlayerBracket.setId(1);
//		competitivePlayerBracket.setNumericalOrderId(1);
//		return competitivePlayerBracket;
//	}
//	
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
	
	@Test
	public void testListMatchsCaseStatusEq1() {
		Set<CompetitiveType> competitiveTypes = competitiveTypes();
		List<CompetitiveType> listCompetitive = new ArrayList<CompetitiveType>(competitiveTypes);
		CompetitiveType competitiveType = listCompetitive.get(0);
		competitiveType.setStatus(1);
		when(competitiveTypeRepository.findById(anyInt())).thenReturn(Optional.of(competitiveType));
		when(competitivePlayerRepository.findEligibleByCompetitiveTypeId(anyInt())).thenReturn(Arrays.asList(competitivePlayer()));
		ResponseMessage response = competitiveMatchService.listMatchs(1);
		assertEquals(response.getData().size(), 0);
	}
	
	@Test
	public void testListMatchsCaseListPlayerNull() {
		Set<CompetitiveType> competitiveTypes = competitiveTypes();
		List<CompetitiveType> listCompetitive = new ArrayList<CompetitiveType>(competitiveTypes);
		CompetitiveType competitiveType = listCompetitive.get(0);
		competitiveType.setStatus(1);
		when(competitiveTypeRepository.findById(anyInt())).thenReturn(Optional.of(competitiveType));
		when(competitivePlayerRepository.findEligibleByCompetitiveTypeId(anyInt())).thenReturn(Arrays.asList());
		ResponseMessage response = competitiveMatchService.listMatchs(1);
		assertEquals(response.getData().size(), 0);
	}
	
	@Test
	public void testListMatchsCaseList() {
		List<CompetitiveMatch> competitiveMatchs = new ArrayList<CompetitiveMatch>();
		CompetitiveMatch competitiveMatch = competitiveMatch();
		competitiveMatchs.add(competitiveMatch);
		CompetitiveMatch competitiveMatch1 = competitiveMatch();
		competitiveMatch1.setId(2);
		competitiveMatchs.add(competitiveMatch1);
		CompetitiveMatch competitiveMatch2 = competitiveMatch();
		competitiveMatch2.setId(3);
		competitiveMatchs.add(competitiveMatch2);
		CompetitiveMatch competitiveMatch3 = competitiveMatch();
		competitiveMatch3.setId(4);
		competitiveMatchs.add(competitiveMatch3);
		Set<CompetitiveType> competitiveTypes = competitiveTypes();
		List<CompetitiveType> listCompetitive = new ArrayList<CompetitiveType>(competitiveTypes);
		CompetitiveType competitiveType = listCompetitive.get(0);
		competitiveType.setStatus(1);
		when(competitiveTypeRepository.findById(anyInt())).thenReturn(Optional.of(competitiveType));
		when(competitivePlayerRepository.findEligibleByCompetitiveTypeId(anyInt())).thenReturn(Arrays.asList(competitivePlayer()));
		when(competitiveMatchRepository.listMatchsByType(anyInt())).thenReturn(competitiveMatchs);
		when(userRepository.getByStudentId(anyString())).thenReturn(createUser());
		when(competitiveResultRepository.findByMatchId(anyInt())).thenReturn(Optional.of(competitiveResult()));
		ResponseMessage response = competitiveMatchService.listMatchs(1);
		assertEquals(response.getData().size(), 4);
	}
	
	@Test
	public void testListMatchsCaseListCaseStausGreaterThan1() {
		List<CompetitiveMatch> competitiveMatchs = new ArrayList<CompetitiveMatch>();
		CompetitiveMatch competitiveMatch = competitiveMatch();
		competitiveMatchs.add(competitiveMatch);
		CompetitiveMatch competitiveMatch1 = competitiveMatch();
		competitiveMatch1.setId(2);
		competitiveMatchs.add(competitiveMatch1);
		CompetitiveMatch competitiveMatch2 = competitiveMatch();
		competitiveMatch2.setId(3);
		competitiveMatchs.add(competitiveMatch2);
		CompetitiveMatch competitiveMatch3 = competitiveMatch();
		competitiveMatch3.setId(4);
		competitiveMatchs.add(competitiveMatch3);
		Set<CompetitiveType> competitiveTypes = competitiveTypes();
		List<CompetitiveType> listCompetitive = new ArrayList<CompetitiveType>(competitiveTypes);
		CompetitiveType competitiveType = listCompetitive.get(0);
		competitiveType.setStatus(2);
		when(competitiveTypeRepository.findById(anyInt())).thenReturn(Optional.of(competitiveType));
		when(competitivePlayerRepository.findEligibleByCompetitiveTypeId(anyInt())).thenReturn(Arrays.asList(competitivePlayer()));
		when(competitiveMatchRepository.listMatchsByType(anyInt())).thenReturn(competitiveMatchs);
		when(userRepository.getByStudentId(anyString())).thenReturn(createUser());
		when(competitiveResultRepository.findByMatchId(anyInt())).thenReturn(Optional.of(competitiveResult()));
		ResponseMessage response = competitiveMatchService.listMatchs(1);
		assertEquals(response.getData().size(), 4);
	}
	
	@Test
	public void testListMatchsCaseListCaseException() {
		when(competitiveTypeRepository.findById(anyInt())).thenReturn(null);
		ResponseMessage response = competitiveMatchService.listMatchs(1);
		assertEquals(response.getData().size(), 0);
	}
	
	@Test
	public void testConfirmListMatchsPlayer() {
		Set<CompetitiveType> competitiveTypes = competitiveTypes();
		List<CompetitiveType> listCompetitive = new ArrayList<CompetitiveType>(competitiveTypes);
		CompetitiveType competitiveType = listCompetitive.get(0);
		competitiveType.setStatus(1);
		when(competitiveTypeRepository.findById(anyInt())).thenReturn(Optional.of(competitiveType));
		ResponseMessage response = competitiveMatchService.confirmListMatchsPlayer(1);
		assertEquals(response.getData().size(), 1);
	}
	
	@Test
	public void testConfirmListMatchsPlayerCaseFail() {
		Set<CompetitiveType> competitiveTypes = competitiveTypes();
		List<CompetitiveType> listCompetitive = new ArrayList<CompetitiveType>(competitiveTypes);
		CompetitiveType competitiveType = listCompetitive.get(0);
		competitiveType.setStatus(1);
		when(competitiveTypeRepository.findById(anyInt())).thenReturn(Optional.empty());
		ResponseMessage response = competitiveMatchService.confirmListMatchsPlayer(1);
		assertEquals(response.getData().size(), 0);
	}
	
	@Test
	public void testConfirmListMatchsPlayerCaseException() {
		when(competitiveTypeRepository.findById(anyInt())).thenReturn(null);
		ResponseMessage response = competitiveMatchService.confirmListMatchsPlayer(1);
		assertEquals(response.getData().size(), 0);
	}
	
	@Test
	public void testSpawnMatchsCaseNotEnoughPlyer() {
		Set<CompetitiveType> competitiveTypes = competitiveTypes();
		List<CompetitiveType> listCompetitive = new ArrayList<CompetitiveType>(competitiveTypes);
		CompetitiveType competitiveType = listCompetitive.get(0);
		competitiveType.setStatus(1);
		when(competitiveTypeRepository.findById(anyInt())).thenReturn(Optional.of(competitiveType));
		ResponseMessage response = competitiveMatchService.spawnMatchs(1);
		assertEquals(response.getData().size(), 0);
	}
	
	@Test
	public void testSpawnMatchsCaseNotEnoughPlyerAndDelete() {
		List<CompetitiveMatch> competitiveMatchs = new ArrayList<CompetitiveMatch>();
		CompetitiveMatch competitiveMatch = competitiveMatch();
		competitiveMatchs.add(competitiveMatch);
		CompetitiveMatch competitiveMatch1 = competitiveMatch();
		competitiveMatch1.setId(2);
		competitiveMatchs.add(competitiveMatch1);
		CompetitiveMatch competitiveMatch2 = competitiveMatch();
		competitiveMatch2.setId(3);
		competitiveMatchs.add(competitiveMatch2);
		CompetitiveMatch competitiveMatch3 = competitiveMatch();
		competitiveMatch3.setId(4);
		competitiveMatchs.add(competitiveMatch3);
		Set<CompetitiveType> competitiveTypes = competitiveTypes();
		List<CompetitiveType> listCompetitive = new ArrayList<CompetitiveType>(competitiveTypes);
		CompetitiveType competitiveType = listCompetitive.get(0);
		competitiveType.setStatus(1);
		when(competitiveTypeRepository.findById(anyInt())).thenReturn(Optional.of(competitiveType));
		when(competitiveMatchRepository.listMatchsByType(anyInt())).thenReturn(competitiveMatchs);
		ResponseMessage response = competitiveMatchService.spawnMatchs(1);
		assertEquals(response.getData().size(), 0);
	}
	
	@Test
	public void testSpawnMatchsCaseEnoughPlyer() {
		Set<CompetitiveType> competitiveTypes = competitiveTypes();
		List<CompetitiveType> listCompetitive = new ArrayList<CompetitiveType>(competitiveTypes);
		CompetitiveType competitiveType = listCompetitive.get(0);
		competitiveType.setStatus(1);
		List<CompetitivePlayer> competitivePlayers = new ArrayList<CompetitivePlayer>();
		CompetitivePlayer competitivePlayer = competitivePlayer();
		competitivePlayer.setId(1);
		competitivePlayers.add(competitivePlayer);
		CompetitivePlayer competitivePlayer2 = competitivePlayer();
		competitivePlayer.setId(2);
		competitivePlayers.add(competitivePlayer2);
		CompetitivePlayer competitivePlayer3 = competitivePlayer();
		competitivePlayer.setId(3);
		competitivePlayers.add(competitivePlayer3);
		CompetitivePlayer competitivePlayer4 = competitivePlayer();
		competitivePlayer.setId(4);
		competitivePlayers.add(competitivePlayer4);
		List<CompetitiveMatch> competitiveMatchs = new ArrayList<CompetitiveMatch>();
		CompetitiveMatch competitiveMatch = competitiveMatch();
		competitiveMatchs.add(competitiveMatch);
		CompetitiveMatch competitiveMatch1 = competitiveMatch();
		competitiveMatch1.setId(2);
		competitiveMatchs.add(competitiveMatch1);
		CompetitiveMatch competitiveMatch2 = competitiveMatch();
		competitiveMatch2.setId(3);
		competitiveMatchs.add(competitiveMatch2);
		CompetitiveMatch competitiveMatch3 = competitiveMatch();
		competitiveMatch3.setId(4);
		competitiveMatchs.add(competitiveMatch3);
		when(competitiveTypeRepository.findById(anyInt())).thenReturn(Optional.of(competitiveType));
		when(competitiveMatchRepository.listMatchsByType(anyInt())).thenReturn(competitiveMatchs);
		when(competitivePlayerRepository.findEligibleByCompetitiveTypeId(anyInt())).thenReturn(competitivePlayers);
		when(competitiveMatchRepository.listMatchsByTypeDesc(anyInt())).thenReturn(competitiveMatchs);
		when(competitiveMatchRepository.listMatchsByTypeAsc(anyInt())).thenReturn(competitiveMatchs);
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(createUser()));
		ResponseMessage response = competitiveMatchService.spawnMatchs(1);
		assertEquals(response.getData().size(), 4);
	}
	
	@Test
	public void testSpawnMatchsCaseEnoughPlyer2() {
		Set<CompetitiveType> competitiveTypes = competitiveTypes();
		List<CompetitiveType> listCompetitive = new ArrayList<CompetitiveType>(competitiveTypes);
		CompetitiveType competitiveType = listCompetitive.get(0);
		competitiveType.setStatus(1);
		List<CompetitivePlayer> competitivePlayers = new ArrayList<CompetitivePlayer>();
		CompetitivePlayer competitivePlayer = competitivePlayer();
		competitivePlayer.setId(1);
		competitivePlayers.add(competitivePlayer);
		CompetitivePlayer competitivePlayer2 = competitivePlayer();
		competitivePlayer.setId(2);
		competitivePlayers.add(competitivePlayer2);
		CompetitivePlayer competitivePlayer3 = competitivePlayer();
		competitivePlayer.setId(3);
		competitivePlayers.add(competitivePlayer3);
		CompetitivePlayer competitivePlayer4 = competitivePlayer();
		competitivePlayer.setId(4);
		competitivePlayers.add(competitivePlayer4);
		CompetitivePlayer competitivePlayer5 = competitivePlayer();
		competitivePlayer.setId(5);
		competitivePlayers.add(competitivePlayer5);
		List<CompetitiveMatch> competitiveMatchs = new ArrayList<CompetitiveMatch>();
		CompetitiveMatch competitiveMatch = competitiveMatch();
		competitiveMatchs.add(competitiveMatch);
		CompetitiveMatch competitiveMatch1 = competitiveMatch();
		competitiveMatch1.setId(2);
		competitiveMatchs.add(competitiveMatch1);
		CompetitiveMatch competitiveMatch2 = competitiveMatch();
		competitiveMatch2.setId(3);
		competitiveMatchs.add(competitiveMatch2);
		CompetitiveMatch competitiveMatch3 = competitiveMatch();
		competitiveMatch3.setId(4);
		competitiveMatchs.add(competitiveMatch3);
		when(competitiveTypeRepository.findById(anyInt())).thenReturn(Optional.of(competitiveType));
		when(competitiveMatchRepository.listMatchsByType(anyInt())).thenReturn(competitiveMatchs);
		when(competitivePlayerRepository.findEligibleByCompetitiveTypeId(anyInt())).thenReturn(competitivePlayers);
		when(competitiveMatchRepository.listMatchsByTypeDesc(anyInt())).thenReturn(competitiveMatchs);
		when(competitiveMatchRepository.listMatchsByTypeAsc(anyInt())).thenReturn(competitiveMatchs);
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(createUser()));
		when(competitiveMatchRepository.findById(anyInt())).thenReturn(Optional.of(competitiveMatch()));
		ResponseMessage response = competitiveMatchService.spawnMatchs(1);
		assertEquals(response.getData().size(), 4);
	}
	
	@Test
	public void testSpawnMatchsCaseStatusEq2() {
		Set<CompetitiveType> competitiveTypes = competitiveTypes();
		List<CompetitiveType> listCompetitive = new ArrayList<CompetitiveType>(competitiveTypes);
		CompetitiveType competitiveType = listCompetitive.get(0);
		competitiveType.setStatus(2);
		when(competitiveTypeRepository.findById(anyInt())).thenReturn(Optional.of(competitiveType));
		ResponseMessage response = competitiveMatchService.spawnMatchs(1);
		assertEquals(response.getData().size(), 0);
	}
	
	@Test
	public void testSpawnMatchsCaseTypeEmpty() {
		Set<CompetitiveType> competitiveTypes = competitiveTypes();
		List<CompetitiveType> listCompetitive = new ArrayList<CompetitiveType>(competitiveTypes);
		CompetitiveType competitiveType = listCompetitive.get(0);
		competitiveType.setStatus(2);
		when(competitiveTypeRepository.findById(anyInt())).thenReturn(Optional.empty());
		ResponseMessage response = competitiveMatchService.spawnMatchs(1);
		assertEquals(response.getData().size(), 0);
	}
	
	
	@Test
	public void testSpawnMatchsCaseException() {
		when(competitiveTypeRepository.findById(anyInt())).thenReturn(null);
		ResponseMessage response = competitiveMatchService.spawnMatchs(1);
		assertEquals(response.getData().size(), 0);
	}
		
	
	@Test
	public void testUpdateListMatchsPlayer() {
		when(competitiveMatchRepository.findById(anyInt())).thenReturn(Optional.of(competitiveMatch()));
		ResponseMessage response = competitiveMatchService.updateListMatchsPlayer(Arrays.asList(competitiveMatchDto()));
		assertEquals(response.getData().size(), 1);
	}
	
	@Test
	public void testUpdateListMatchsPlayerCaseException() {
		when(competitiveMatchRepository.findById(anyInt())).thenReturn(null);
		ResponseMessage response = competitiveMatchService.updateListMatchsPlayer(Arrays.asList(competitiveMatchDto()));
		assertEquals(response.getData().size(), 0);
	}
	
}
