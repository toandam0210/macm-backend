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
import com.fpt.macm.model.entity.RoleEvent;
import com.fpt.macm.model.entity.Tournament;
import com.fpt.macm.model.entity.TournamentOrganizingCommittee;
import com.fpt.macm.model.entity.TournamentPlayer;
import com.fpt.macm.model.entity.User;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.repository.CompetitiveMatchRepository;
import com.fpt.macm.repository.CompetitivePlayerRepository;
import com.fpt.macm.repository.CompetitiveResultRepository;
import com.fpt.macm.repository.CompetitiveTypeRepository;
import com.fpt.macm.repository.ExhibitionPlayerRepository;
import com.fpt.macm.repository.TournamentOrganizingCommitteeRepository;
import com.fpt.macm.repository.TournamentPlayerRepository;
import com.fpt.macm.repository.TournamentRepository;
import com.fpt.macm.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class CompetitiveServiceTest {
	
	@InjectMocks
	CompetitiveService competitiveService = new CompetitiveServiceImpl();
	
	@Mock
	TournamentRepository tournamentRepository;

	@Mock
	CompetitiveTypeRepository competitiveTypeRepository;

	@Mock
	UserRepository userRepository;

	@Mock
	TournamentPlayerRepository tournamentPlayerRepository;

	@Mock
	CompetitivePlayerRepository competitivePlayerRepository;

	@Mock
	CompetitiveMatchRepository competitiveMatchRepository;

	@Mock
	CompetitiveResultRepository competitiveResultRepository;
	
	@Mock
	ExhibitionPlayerRepository exhibitionPlayerRepository;
	
	@Mock
	TournamentOrganizingCommitteeRepository tournamentOrganizingCommitteeRepository;
	
	
	private Tournament tournament() {
		Tournament tournament = new Tournament();
		tournament.setCompetitiveTypes(competitiveTypes());
		tournament.setDescription("abc");
		tournament.setExhibitionTypes(exhibitionTypes());
		tournament.setFeeOrganizingCommiteePay(100000);
		tournament.setFeePlayerPay(100000);
		tournament.setId(1);
		tournament.setName("FNC");
		tournament.setRegistrationOrganizingCommitteeDeadline(LocalDateTime.of(2022, 8, 1, 18, 0));
		tournament.setRegistrationPlayerDeadline(LocalDateTime.of(2022, 8, 1, 18, 0));
		tournament.setSemester("Summer2022");
		tournament.setStatus(true);
		tournament.setStage(3);
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
	
	private RoleEvent roleEvent() {
		RoleEvent roleEvent = new RoleEvent();
		roleEvent.setId(2);
		roleEvent.setName("Ban van hoa");
		return roleEvent;
	}
	
	private TournamentOrganizingCommittee tournamentOrganizingCommittee() {
		TournamentOrganizingCommittee tournamentOrganizingCommittee = new TournamentOrganizingCommittee();
		tournamentOrganizingCommittee.setId(1);
		tournamentOrganizingCommittee.setPaymentStatus(true);
		tournamentOrganizingCommittee.setRoleEvent(roleEvent());
		tournamentOrganizingCommittee.setTournament(tournament());
		tournamentOrganizingCommittee.setUser(createUser());
		return tournamentOrganizingCommittee;
	}
	
	@Test
	public void testGetAllType() {
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament()));
		ResponseMessage response = competitiveService.getAllCompetitiveType(1);
		assertEquals(response.getData().size(), 1);
	}
	
	@Test
	public void testgetAllCompetitiveTypeCaseSort() {
		List<CompetitiveType> competitiveTypes = new ArrayList<>(competitiveTypes());
		CompetitiveType competitiveType = new CompetitiveType();
		competitiveType.setId(1);
		competitiveType.setGender(false);
		competitiveType.setWeightMin(45);
		competitiveType.setWeightMax(50);
		competitiveTypes.add(competitiveType);
		
		Tournament tournament = tournament();
		tournament.setCompetitiveTypes(new HashSet<>(competitiveTypes));
		
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament));
		ResponseMessage response = competitiveService.getAllCompetitiveType(1);
		assertEquals(response.getData().size(), 1);
	}
	
	
	
	@Test
	public void testgetAllCompetitiveTypeCaseSort2() {
		List<CompetitiveType> competitiveTypes = new ArrayList<>(competitiveTypes());
		CompetitiveType competitiveType = new CompetitiveType();
		competitiveType.setId(1);
		competitiveType.setGender(true);
		competitiveType.setWeightMin(45);
		competitiveType.setWeightMax(50);
		competitiveTypes.add(competitiveType);
		
		Tournament tournament = tournament();
		tournament.setCompetitiveTypes(new HashSet<>(competitiveTypes));
		
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament));
		ResponseMessage response = competitiveService.getAllCompetitiveType(1);
		assertEquals(response.getData().size(), 1);
	}


	@Test
	public void testgetAllCompetitiveTypeCaseSort3() {
		List<CompetitiveType> competitiveTypes = new ArrayList<>();
		CompetitiveType competitiveType = new CompetitiveType();
		competitiveType.setId(1);
		competitiveType.setGender(false);
		competitiveType.setWeightMin(40);
		competitiveType.setWeightMax(45);
		competitiveTypes.add(competitiveType);
		CompetitiveType competitiveType2 = new CompetitiveType();
		competitiveType2.setId(3);
		competitiveType2.setGender(true);
		competitiveType2.setWeightMin(45);
		competitiveType2.setWeightMax(50);
		competitiveTypes.add(competitiveType2);
		CompetitiveType competitiveType3 = new CompetitiveType();
		competitiveType3.setId(2);
		competitiveType3.setGender(false);
		competitiveType3.setWeightMin(50);
		competitiveType3.setWeightMax(55);
		competitiveTypes.add(competitiveType3);
		CompetitiveType competitiveType4 = new CompetitiveType();
		competitiveType4.setId(4);
		competitiveType4.setGender(false);
		competitiveType4.setWeightMin(45);
		competitiveType4.setWeightMax(50);
		competitiveTypes.add(competitiveType4);
		CompetitiveType competitiveType5 = new CompetitiveType();
		competitiveType5.setId(5);
		competitiveType5.setGender(true);
		competitiveType5.setWeightMin(40);
		competitiveType5.setWeightMax(45);
		competitiveTypes.add(competitiveType5);
		CompetitiveType competitiveType6 = new CompetitiveType();
		competitiveType6.setId(6);
		competitiveType6.setGender(true);
		competitiveType6.setWeightMin(50);
		competitiveType6.setWeightMax(55);
		competitiveTypes.add(competitiveType6);
		
		Tournament tournament = tournament();
		tournament.setCompetitiveTypes(new HashSet<>(competitiveTypes));
		
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament));
		ResponseMessage response = competitiveService.getAllCompetitiveType(1);
		assertEquals(response.getData().size(), 1);
	}
	
	@Test
	public void testgetAllCompetitiveTypeCaseEmpty() {
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.empty());
		ResponseMessage response = competitiveService.getAllCompetitiveType(10);
		assertEquals(response.getData().size(), 0);
	}
	
	@Test
	public void testgetAllCompetitiveTypeCaseException() {
		when(tournamentRepository.findById(anyInt())).thenReturn(null);
		ResponseMessage response = competitiveService.getAllCompetitiveType(10);
		assertEquals(response.getData().size(), 0);
	}
	
	
	@Test
	public void testGetListNotJoinCompetitive() {
		Set<CompetitiveType> competitiveTypes = competitiveTypes();
		List<CompetitiveType> coms = new ArrayList<CompetitiveType>(competitiveTypes);
		List<TournamentPlayer> tournamentPlayers = new ArrayList<TournamentPlayer>(tournamentPlayers());
		when(competitiveTypeRepository.findById(anyInt())).thenReturn(Optional.of(coms.get(0)));
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament()));
		when(userRepository.findAllActiveUser()).thenReturn(Arrays.asList(createUser()));
		when(tournamentPlayerRepository.getPlayerByTournamentId(anyInt())).thenReturn(tournamentPlayers);
		when(competitivePlayerRepository.findByTournamentPlayerId(anyInt())).thenReturn(Optional.of(competitivePlayer()));
		when(tournamentOrganizingCommitteeRepository.findByTournamentId(anyInt())).thenReturn(Arrays.asList(tournamentOrganizingCommittee()));
		ResponseMessage response = competitiveService.getListNotJoinCompetitive(1);
		assertEquals(response.getData().size(), 1);
	}
	
	@Test
	public void testGetListNotJoinCompetitiveCaseEmpty() {
		when(competitiveTypeRepository.findById(anyInt())).thenReturn(Optional.empty());
		ResponseMessage response = competitiveService.getListNotJoinCompetitive(1);
		assertEquals(response.getData().size(), 0);
	}
	
	@Test
	public void testGetListNotJoinCompetitiveCaseException() {
		when(competitiveTypeRepository.findById(anyInt())).thenReturn(null);
		ResponseMessage response = competitiveService.getListNotJoinCompetitive(1);
		assertEquals(response.getData().size(), 0);
	}
	
	@Test
	public void testListMatchsCaseStatusEq1() {
		Set<CompetitiveType> competitiveTypes = competitiveTypes();
		List<CompetitiveType> listCompetitive = new ArrayList<CompetitiveType>(competitiveTypes);
		CompetitiveType competitiveType = listCompetitive.get(0);
		competitiveType.setStatus(1);
		when(competitiveTypeRepository.findById(anyInt())).thenReturn(Optional.of(competitiveType));
		when(competitivePlayerRepository.findEligibleByCompetitiveTypeId(anyInt())).thenReturn(Arrays.asList(competitivePlayer()));
		ResponseMessage response = competitiveService.listMatchs(1);
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
		ResponseMessage response = competitiveService.listMatchs(1);
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
		when(competitiveResultRepository.findResultByMatchId(anyInt())).thenReturn(Optional.of(competitiveResult()));
		ResponseMessage response = competitiveService.listMatchs(1);
		assertEquals(response.getData().size(), 1);
	}
	
	@Test
	public void testListMatchsCaseTypeFemale() {
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
		competitiveType.setGender(false);
		when(competitiveTypeRepository.findById(anyInt())).thenReturn(Optional.of(competitiveType));
		when(competitivePlayerRepository.findEligibleByCompetitiveTypeId(anyInt())).thenReturn(Arrays.asList(competitivePlayer()));
		when(competitiveMatchRepository.listMatchsByType(anyInt())).thenReturn(competitiveMatchs);
		when(userRepository.getByStudentId(anyString())).thenReturn(createUser());
		when(competitiveResultRepository.findResultByMatchId(anyInt())).thenReturn(Optional.of(competitiveResult()));
		ResponseMessage response = competitiveService.listMatchs(1);
		assertEquals(response.getData().size(), 1);
	}
	
	@Test
	public void testListMatchsCaseListPlayer() {
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
		
		List<CompetitivePlayer> competitivePlayers = new ArrayList<CompetitivePlayer>();
		competitivePlayers.add(competitivePlayer());
		competitivePlayers.add(competitivePlayer());
		competitivePlayers.add(competitivePlayer());
		competitivePlayers.add(competitivePlayer());
		
		when(competitiveTypeRepository.findById(anyInt())).thenReturn(Optional.of(competitiveType));
		when(competitivePlayerRepository.findEligibleByCompetitiveTypeId(anyInt())).thenReturn(competitivePlayers);
		when(competitiveMatchRepository.listMatchsByType(anyInt())).thenReturn(competitiveMatchs);
		when(userRepository.getByStudentId(anyString())).thenReturn(createUser());
		when(competitiveResultRepository.findResultByMatchId(anyInt())).thenReturn(Optional.of(competitiveResult()));
		ResponseMessage response = competitiveService.listMatchs(1);
		assertEquals(response.getData().size(), 1);
	}
	
	@Test
	public void testListMatchsCaseListStudentIdNull() {
		List<CompetitiveMatch> competitiveMatchs = new ArrayList<CompetitiveMatch>();
		CompetitiveMatch competitiveMatch = competitiveMatch();
		competitiveMatch.setFirstStudentId(null);
		competitiveMatch.setSecondStudentId(null);
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
		
		List<CompetitivePlayer> competitivePlayers = new ArrayList<CompetitivePlayer>();
		competitivePlayers.add(competitivePlayer());
		competitivePlayers.add(competitivePlayer());
		competitivePlayers.add(competitivePlayer());
		competitivePlayers.add(competitivePlayer());
		
		when(competitiveTypeRepository.findById(anyInt())).thenReturn(Optional.of(competitiveType));
		when(competitivePlayerRepository.findEligibleByCompetitiveTypeId(anyInt())).thenReturn(competitivePlayers);
		when(competitiveMatchRepository.listMatchsByType(anyInt())).thenReturn(competitiveMatchs);
		when(competitiveResultRepository.findResultByMatchId(anyInt())).thenReturn(Optional.of(competitiveResult()));
		ResponseMessage response = competitiveService.listMatchs(1);
		assertEquals(response.getData().size(), 0);
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
		when(competitiveResultRepository.findResultByMatchId(anyInt())).thenReturn(Optional.of(competitiveResult()));
		ResponseMessage response = competitiveService.listMatchs(1);
		assertEquals(response.getData().size(), 1);
	}
	
	@Test
	public void testListMatchsCaseListCaseException() {
		when(competitiveTypeRepository.findById(anyInt())).thenReturn(null);
		ResponseMessage response = competitiveService.listMatchs(1);
		assertEquals(response.getData().size(), 0);
	}
	
	@Test
	public void testSpawnMatchsAutoCaseNotEnoughPlyer() {
		Set<CompetitiveType> competitiveTypes = competitiveTypes();
		List<CompetitiveType> listCompetitive = new ArrayList<CompetitiveType>(competitiveTypes);
		CompetitiveType competitiveType = listCompetitive.get(0);
		competitiveType.setStatus(1);
		Tournament tournament = tournament();
		tournament.setStage(0);
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament));
		when(competitiveTypeRepository.findById(anyInt())).thenReturn(Optional.of(competitiveType));
		competitiveService.autoSpawnMatchs(1);
		
	}
	
	@Test
	public void testSpawnMatchsAutoCaseNotEnoughPlyerAndDelete() {
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
		competitiveType.setStatus(1);
		Tournament tournament = tournament();
		tournament.setStage(0);
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament));
		when(competitiveTypeRepository.findById(anyInt())).thenReturn(Optional.of(competitiveType));
		when(competitiveMatchRepository.listMatchsByType(anyInt())).thenReturn(competitiveMatchs);
		competitiveService.autoSpawnMatchs(1);
	}
	
	@Test
	public void testSpawnMatchsAutoCaseEnoughPlyer() {
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
		CompetitiveMatch competitiveMatch4 = competitiveMatch();
		competitiveMatch4.setId(5);
		competitiveMatchs.add(competitiveMatch4);
		competitiveType.setStatus(1);
		Tournament tournament = tournament();
		tournament.setStage(0);
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament));
		when(competitiveTypeRepository.findById(anyInt())).thenReturn(Optional.of(competitiveType));
		when(competitiveMatchRepository.listMatchsByType(anyInt())).thenReturn(competitiveMatchs);
		when(competitivePlayerRepository.findEligibleByCompetitiveTypeId(anyInt())).thenReturn(competitivePlayers);
		when(competitiveMatchRepository.listMatchsByTypeDesc(anyInt())).thenReturn(competitiveMatchs);
		when(competitiveMatchRepository.listMatchsByTypeAsc(anyInt())).thenReturn(competitiveMatchs);
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(createUser()));
		competitiveService.autoSpawnMatchs(1);
	}
	
	@Test
	public void testSpawnMatchsAutoCaseEnoughPlyer2() {
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
		Tournament tournament = tournament();
		tournament.setStage(0);
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament));
		when(competitiveTypeRepository.findById(anyInt())).thenReturn(Optional.of(competitiveType));
		when(competitiveMatchRepository.listMatchsByType(anyInt())).thenReturn(competitiveMatchs);
		when(competitivePlayerRepository.findEligibleByCompetitiveTypeId(anyInt())).thenReturn(competitivePlayers);
		when(competitiveMatchRepository.listMatchsByTypeDesc(anyInt())).thenReturn(competitiveMatchs);
		when(competitiveMatchRepository.listMatchsByTypeAsc(anyInt())).thenReturn(competitiveMatchs);
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(createUser()));
		when(competitiveMatchRepository.findById(anyInt())).thenReturn(Optional.of(competitiveMatch()));
		competitiveService.autoSpawnMatchs(1);
	}
	
	@Test
	public void testSpawnMatchsAutoCaseStatusEq2() {
		Set<CompetitiveType> competitiveTypes = competitiveTypes();
		List<CompetitiveType> listCompetitive = new ArrayList<CompetitiveType>(competitiveTypes);
		CompetitiveType competitiveType = listCompetitive.get(0);
		competitiveType.setStatus(2);
		when(competitiveTypeRepository.findById(anyInt())).thenReturn(Optional.of(competitiveType));
		competitiveService.autoSpawnMatchs(1);
	}
	
	@Test
	public void testSpawnMatchsAutoCaseTypeEmpty() {
		Set<CompetitiveType> competitiveTypes = competitiveTypes();
		List<CompetitiveType> listCompetitive = new ArrayList<CompetitiveType>(competitiveTypes);
		CompetitiveType competitiveType = listCompetitive.get(0);
		competitiveType.setStatus(2);
		when(competitiveTypeRepository.findById(anyInt())).thenReturn(Optional.empty());
		competitiveService.autoSpawnMatchs(1);
	}
	
	
	@Test
	public void testSpawnMatchsAutoCaseException() {
		when(competitiveTypeRepository.findById(anyInt())).thenReturn(null);
		competitiveService.autoSpawnMatchs(1);
	}
	
	@Test
	public void testUpdateResultMatchCaseAlreadyHasResult() {
		when(competitiveMatchRepository.findById(anyInt())).thenReturn(Optional.of(competitiveMatch()));
		ResponseMessage response = competitiveService.updateResultMatch(1,1,2);
		assertEquals(response.getData().size(), 0);
	}
	
	@Test
	public void testUpdateResultMatchCaseException() {
		ResponseMessage response = competitiveService.updateResultMatch(1,-1,2);
		assertEquals(response.getData().size(), 0);
	}
	
	@Test
	public void testUpdateResultMatch() {
		CompetitiveMatch competitiveMatch = competitiveMatch();
		competitiveMatch.setStatus(false);
		when(competitiveMatchRepository.findById(anyInt())).thenReturn(Optional.of(competitiveMatch));
		when(competitiveResultRepository.findResultByMatchId(anyInt())).thenReturn(Optional.of(competitiveResult()));
		when(competitiveMatchRepository.getById(anyInt())).thenReturn(competitiveMatch);
		ResponseMessage response = competitiveService.updateResultMatch(1,1,2);
		assertEquals(response.getData().size(), 0);
	}
	
	@Test
	public void testUpdateResultMatchCaseNotIsNextFirst() {
		CompetitiveMatch competitiveMatch = competitiveMatch();
		competitiveMatch.setStatus(false);
		competitiveMatch.setNextIsFirst(false);
		when(competitiveMatchRepository.findById(anyInt())).thenReturn(Optional.of(competitiveMatch));
		when(competitiveResultRepository.findResultByMatchId(anyInt())).thenReturn(Optional.of(competitiveResult()));
		when(competitiveMatchRepository.getById(anyInt())).thenReturn(competitiveMatch);
		ResponseMessage response = competitiveService.updateResultMatch(1,1,2);
		assertEquals(response.getData().size(), 0);
	}
	
	@Test
	public void testUpdateResultMatchCaseFirstPlayerWin() {
		CompetitiveMatch competitiveMatch = competitiveMatch();
		competitiveMatch.setStatus(false);
		competitiveMatch.setNextIsFirst(false);
		when(competitiveMatchRepository.findById(anyInt())).thenReturn(Optional.of(competitiveMatch));
		when(competitiveResultRepository.findResultByMatchId(anyInt())).thenReturn(Optional.of(competitiveResult()));
		when(competitiveMatchRepository.getById(anyInt())).thenReturn(competitiveMatch);
		ResponseMessage response = competitiveService.updateResultMatch(1,2,1);
		assertEquals(response.getData().size(), 0);
	}
	
	@Test
	public void testUpdateResultMatchCaseResultEmpty() {
		CompetitiveMatch competitiveMatch = competitiveMatch();
		competitiveMatch.setStatus(false);
		competitiveMatch.setNextIsFirst(false);
		when(competitiveMatchRepository.findById(anyInt())).thenReturn(Optional.of(competitiveMatch));
		when(competitiveResultRepository.findResultByMatchId(anyInt())).thenReturn(Optional.empty());
		ResponseMessage response = competitiveService.updateResultMatch(1,2,1);
		assertEquals(response.getData().size(), 0);
	}
	
	@Test
	public void getResultByTypeCaseThirdPlaceNotHappened() {
		List<CompetitiveType> competitiveTypes = new ArrayList<>(competitiveTypes());
		CompetitiveType competitiveType = competitiveTypes.get(0);
		competitiveType.setStatus(3);
		
		List<CompetitiveMatch> listMatchs = new ArrayList<CompetitiveMatch>();
		listMatchs.add(competitiveMatch());
		listMatchs.add(competitiveMatch());
		
		CompetitiveResult competitiveResult = competitiveResult();
		competitiveResult.setFirstPoint(null);
		competitiveResult.setSecondPoint(null);
		
		when(competitiveTypeRepository.findById(anyInt())).thenReturn(Optional.of(competitiveType));
		when(competitiveMatchRepository.listMatchsByTypeDesc(anyInt())).thenReturn(listMatchs);
		when(competitiveResultRepository.findResultByMatchId(anyInt())).thenReturn(Optional.of(competitiveResult));
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament()));
		//		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(createUser()));
		
		ResponseMessage responseMessage = competitiveService.getResultByType(competitiveType.getId());
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void getResultByTypeCaseThirdPlaceNotHappenedSecondPointNull() {
		List<CompetitiveType> competitiveTypes = new ArrayList<>(competitiveTypes());
		CompetitiveType competitiveType = competitiveTypes.get(0);
		competitiveType.setStatus(3);
		
		List<CompetitiveMatch> listMatchs = new ArrayList<CompetitiveMatch>();
		listMatchs.add(competitiveMatch());
		listMatchs.add(competitiveMatch());
		
		CompetitiveResult competitiveResult = competitiveResult();
		competitiveResult.setFirstPoint(1);
		competitiveResult.setSecondPoint(null);
		
		when(competitiveTypeRepository.findById(anyInt())).thenReturn(Optional.of(competitiveType));
		when(competitiveMatchRepository.listMatchsByTypeDesc(anyInt())).thenReturn(listMatchs);
		when(competitiveResultRepository.findResultByMatchId(anyInt())).thenReturn(Optional.of(competitiveResult));
//		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(createUser()));
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament()));
		ResponseMessage responseMessage = competitiveService.getResultByType(competitiveType.getId());
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void getResultByTypeCaseFinalMatch1() {
		List<CompetitiveType> competitiveTypes = new ArrayList<>(competitiveTypes());
		CompetitiveType competitiveType = competitiveTypes.get(0);
		competitiveType.setStatus(3);
		
		List<CompetitiveMatch> listMatchs = new ArrayList<CompetitiveMatch>();
		listMatchs.add(competitiveMatch());
		listMatchs.add(competitiveMatch());
		
		CompetitiveResult competitiveResult1 = competitiveResult();
		competitiveResult1.setFirstPoint(5);
		competitiveResult1.setSecondPoint(4);
		
		CompetitiveResult competitiveResult2 = competitiveResult();
		competitiveResult2.setFirstPoint(5);
		competitiveResult2.setSecondPoint(4);
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament()));
		when(competitiveTypeRepository.findById(anyInt())).thenReturn(Optional.of(competitiveType));
		when(competitiveMatchRepository.listMatchsByTypeDesc(anyInt())).thenReturn(listMatchs);
		when(competitiveResultRepository.findResultByMatchId(anyInt())).thenReturn(Optional.of(competitiveResult1));
		when(competitiveResultRepository.findByMatchId(anyInt())).thenReturn(Optional.of(competitiveResult2));
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(createUser()));
		
		ResponseMessage responseMessage = competitiveService.getResultByType(competitiveType.getId());
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void getResultByTypeCaseFinalMatch2() {
		List<CompetitiveType> competitiveTypes = new ArrayList<>(competitiveTypes());
		CompetitiveType competitiveType = competitiveTypes.get(0);
		competitiveType.setStatus(3);
		
		List<CompetitiveMatch> listMatchs = new ArrayList<CompetitiveMatch>();
		listMatchs.add(competitiveMatch());
		listMatchs.add(competitiveMatch());
		
		CompetitiveResult competitiveResult1 = competitiveResult();
		competitiveResult1.setFirstPoint(4);
		competitiveResult1.setSecondPoint(5);
		
		CompetitiveResult competitiveResult2 = competitiveResult();
		competitiveResult2.setFirstPoint(4);
		competitiveResult2.setSecondPoint(5);
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament()));
		when(competitiveTypeRepository.findById(anyInt())).thenReturn(Optional.of(competitiveType));
		when(competitiveMatchRepository.listMatchsByTypeDesc(anyInt())).thenReturn(listMatchs);
		when(competitiveResultRepository.findResultByMatchId(anyInt())).thenReturn(Optional.of(competitiveResult1));
		when(competitiveResultRepository.findByMatchId(anyInt())).thenReturn(Optional.of(competitiveResult2));
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(createUser()));
		
		ResponseMessage responseMessage = competitiveService.getResultByType(competitiveType.getId());
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void getResultByTypeCaseTypeFemale() {
		List<CompetitiveType> competitiveTypes = new ArrayList<>(competitiveTypes());
		CompetitiveType competitiveType = competitiveTypes.get(0);
		competitiveType.setGender(false);
		competitiveType.setStatus(3);
		
		List<CompetitiveMatch> listMatchs = new ArrayList<CompetitiveMatch>();
		listMatchs.add(competitiveMatch());
		listMatchs.add(competitiveMatch());
		
		CompetitiveResult competitiveResult1 = competitiveResult();
		competitiveResult1.setFirstPoint(4);
		competitiveResult1.setSecondPoint(5);
		
		CompetitiveResult competitiveResult2 = competitiveResult();
		competitiveResult2.setFirstPoint(4);
		competitiveResult2.setSecondPoint(5);
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament()));
		when(competitiveTypeRepository.findById(anyInt())).thenReturn(Optional.of(competitiveType));
		when(competitiveMatchRepository.listMatchsByTypeDesc(anyInt())).thenReturn(listMatchs);
		when(competitiveResultRepository.findResultByMatchId(anyInt())).thenReturn(Optional.of(competitiveResult1));
		when(competitiveResultRepository.findByMatchId(anyInt())).thenReturn(Optional.of(competitiveResult2));
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(createUser()));
		
		ResponseMessage responseMessage = competitiveService.getResultByType(competitiveType.getId());
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void getResultByTypeCaseResult2FirstPointNull() {
		List<CompetitiveType> competitiveTypes = new ArrayList<>(competitiveTypes());
		CompetitiveType competitiveType = competitiveTypes.get(0);
		competitiveType.setStatus(3);
		
		List<CompetitiveMatch> listMatchs = new ArrayList<CompetitiveMatch>();
		listMatchs.add(competitiveMatch());
		listMatchs.add(competitiveMatch());
		
		CompetitiveResult competitiveResult1 = competitiveResult();
		competitiveResult1.setFirstPoint(4);
		competitiveResult1.setSecondPoint(5);
		
		CompetitiveResult competitiveResult2 = competitiveResult();
		competitiveResult2.setFirstPoint(null);
		competitiveResult2.setSecondPoint(5);
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament()));
		when(competitiveTypeRepository.findById(anyInt())).thenReturn(Optional.of(competitiveType));
		when(competitiveMatchRepository.listMatchsByTypeDesc(anyInt())).thenReturn(listMatchs);
		when(competitiveResultRepository.findResultByMatchId(anyInt())).thenReturn(Optional.of(competitiveResult1));
		when(competitiveResultRepository.findByMatchId(anyInt())).thenReturn(Optional.of(competitiveResult2));
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(createUser()));
		
		ResponseMessage responseMessage = competitiveService.getResultByType(competitiveType.getId());
		assertEquals(responseMessage.getData().size(), 1);
	}
		
	@Test
	public void getResultByTypeCaseTypeNotStarted() {
		List<CompetitiveType> competitiveTypes = new ArrayList<>(competitiveTypes());
		CompetitiveType competitiveType = competitiveTypes.get(0);
		competitiveType.setStatus(1);
		Tournament tournament = tournament();
		tournament.setStage(1);
		when(competitiveTypeRepository.findById(anyInt())).thenReturn(Optional.of(competitiveType));
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament));
		ResponseMessage responseMessage = competitiveService.getResultByType(competitiveType.getId());
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void getResultByTypeCaseTypeEmpty() {
		when(competitiveTypeRepository.findById(anyInt())).thenReturn(Optional.empty());
		
		ResponseMessage responseMessage = competitiveService.getResultByType(1);
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void getResultByTypeCaseException() {
		when(competitiveTypeRepository.findById(anyInt())).thenReturn(null);
		
		ResponseMessage responseMessage = competitiveService.getResultByType(1);
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void testUpdateListMatchsPlayer() {
		when(competitiveMatchRepository.findById(anyInt())).thenReturn(Optional.of(competitiveMatch()));
		ResponseMessage response = competitiveService.updateListMatchsPlayer(Arrays.asList(competitiveMatchDto()));
		assertEquals(response.getData().size(), 1);
	}
	
	@Test
	public void testUpdateListMatchsPlayerCaseException() {
		when(competitiveMatchRepository.findById(anyInt())).thenReturn(null);
		ResponseMessage response = competitiveService.updateListMatchsPlayer(Arrays.asList(competitiveMatchDto()));
		assertEquals(response.getData().size(), 0);
	}
	
	@Test
	public void testAddNewCompetitivePlayerCaseStatusEq0() {
		Set<CompetitiveType> competitiveTypes = competitiveTypes();
		List<CompetitiveType> listCompetitive = new ArrayList<CompetitiveType>(competitiveTypes);
		listCompetitive.get(0).setStatus(0);
		Tournament tournament = tournament();
		tournament.setStage(0);
		when(competitiveTypeRepository.findById(anyInt())).thenReturn(Optional.of(listCompetitive.get(0)));
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament));
		when(userRepository.findById(anyInt())).thenReturn(Optional.of(createUser()));
		when(tournamentPlayerRepository.findPlayerByUserIdAndTournamentId(anyInt(), anyInt())).thenReturn(Optional.of(tournamentPlayer()));
		ResponseMessage response = competitiveService.addNewCompetitivePlayer(Arrays.asList(createUser()), 1);
		assertEquals(response.getData().size(), 1);
	}

	@Test
	public void testAddNewCompetitivePlayerCaseStatusEq0AndTypeFemale() {
		Set<CompetitiveType> competitiveTypes = competitiveTypes();
		List<CompetitiveType> listCompetitive = new ArrayList<CompetitiveType>(competitiveTypes);
		listCompetitive.get(0).setStatus(0);
		listCompetitive.get(0).setGender(false);
		Tournament tournament = tournament();
		tournament.setStage(0);
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament));
		when(competitiveTypeRepository.findById(anyInt())).thenReturn(Optional.of(listCompetitive.get(0)));
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament));
		
		ResponseMessage response = competitiveService.addNewCompetitivePlayer(Arrays.asList(createUser()), 1);
		assertEquals(response.getData().size(), 0);
	}
	
	@Test
	public void testAddNewCompetitivePlayerCaseStatusEq1() {
		Set<CompetitiveType> competitiveTypes = competitiveTypes();
		List<CompetitiveType> listCompetitive = new ArrayList<CompetitiveType>(competitiveTypes);
		listCompetitive.get(0).setStatus(1);
		Tournament tournament = tournament();
		tournament.setStage(1);
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament));
		when(competitiveTypeRepository.findById(anyInt())).thenReturn(Optional.of(listCompetitive.get(0)));
		ResponseMessage response = competitiveService.addNewCompetitivePlayer(Arrays.asList(createUser()), 1);
		assertEquals(response.getData().size(), 0);
		
	}
	
	@Test
	public void testAddNewCompetitivePlayerCaseTournamentPlayerIsPresent() {
		Set<CompetitiveType> competitiveTypes = competitiveTypes();
		List<CompetitiveType> listCompetitive = new ArrayList<CompetitiveType>(competitiveTypes);
		listCompetitive.get(0).setStatus(0);
		Tournament tournament = tournament();
		tournament.setStage(0);
		when(competitiveTypeRepository.findTournamentOfType(anyInt())).thenReturn(1);
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament));
		when(competitiveTypeRepository.findById(anyInt())).thenReturn(Optional.of(listCompetitive.get(0)));
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament));
		when(tournamentPlayerRepository.getPlayerByUserIdAndTournamentId(anyInt(), anyInt())).thenReturn(Optional.of(tournamentPlayer()));
		ResponseMessage response = competitiveService.addNewCompetitivePlayer(Arrays.asList(createUser()), 1);
		assertEquals(response.getData().size(), 1);
		
	}
	
	@Test
	public void testAddNewCompetitivePlayerCaseCompetitiveEmpty() {
		when(competitiveTypeRepository.findById(anyInt())).thenReturn(Optional.empty());
		ResponseMessage response = competitiveService.addNewCompetitivePlayer(Arrays.asList(createUser()), 1);
		assertEquals(response.getData().size(), 0);
		
	}
	
	@Test
	public void testAddNewCompetitivePlayerCaseException() {
		when(competitiveTypeRepository.findById(anyInt())).thenReturn(null);
		ResponseMessage response = competitiveService.addNewCompetitivePlayer(Arrays.asList(createUser()), 1);
		assertEquals(response.getData().size(), 0);
		
	}
	
	@Test 
	public void testUpdateWeightForCompetitivePlayerCaseStatusEq1() {
		Set<CompetitiveType> competitiveTypes = competitiveTypes();
		List<CompetitiveType> listCompetitive = new ArrayList<CompetitiveType>(competitiveTypes);
		listCompetitive.get(0).setStatus(1);
		CompetitivePlayer competitivePlayer = competitivePlayer();
		competitivePlayer.setCompetitiveType(listCompetitive.get(0));
		competitivePlayer.setIsEligible(true);
		Tournament tournament = tournament();
		tournament.setStage(1);
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament));
		when(competitivePlayerRepository.findById(anyInt())).thenReturn(Optional.of(competitivePlayer));
		ResponseMessage response = competitiveService.updateWeightForCompetitivePlayer(1, 58);
		assertEquals(response.getData().size(), 1);
	}
	
	@Test 
	public void testUpdateWeightForCompetitivePlayerCaseOverTime() {
		Set<CompetitiveType> competitiveTypes = competitiveTypes();
		List<CompetitiveType> listCompetitive = new ArrayList<CompetitiveType>(competitiveTypes);
		listCompetitive.get(0).setStatus(1);
		CompetitivePlayer competitivePlayer = competitivePlayer();
		competitivePlayer.setCompetitiveType(listCompetitive.get(0));
		competitivePlayer.setIsEligible(true);
		Tournament tournament = tournament();
		tournament.setStage(1);
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament));
		when(competitivePlayerRepository.findById(anyInt())).thenReturn(Optional.of(competitivePlayer));
		ResponseMessage response = competitiveService.updateWeightForCompetitivePlayer(1, 40);
		assertEquals(response.getData().size(), 1);
	}
	
	@Test 
	public void testUpdateWeightForCompetitivePlayerCaseOverTime1() {
		Set<CompetitiveType> competitiveTypes = competitiveTypes();
		List<CompetitiveType> listCompetitive = new ArrayList<CompetitiveType>(competitiveTypes);
		listCompetitive.get(0).setStatus(3);
		CompetitivePlayer competitivePlayer = competitivePlayer();
		competitivePlayer.setCompetitiveType(listCompetitive.get(0));
		competitivePlayer.setIsEligible(true);
		Tournament tournament = tournament();
		tournament.setStage(3);
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament));
		when(competitivePlayerRepository.findById(anyInt())).thenReturn(Optional.of(competitivePlayer));
		ResponseMessage response = competitiveService.updateWeightForCompetitivePlayer(1, 40);
		assertEquals(response.getData().size(), 0);
	}
	
	@Test 
	public void testUpdateWeightForCompetitivePlayerCaseStatusEq3AndIsEligibleTrue() {
		Set<CompetitiveType> competitiveTypes = competitiveTypes();
		List<CompetitiveType> listCompetitive = new ArrayList<CompetitiveType>(competitiveTypes);
		listCompetitive.get(0).setStatus(3);
		CompetitivePlayer competitivePlayer = competitivePlayer();
		competitivePlayer.setCompetitiveType(listCompetitive.get(0));
		competitivePlayer.setIsEligible(true);
		when(competitivePlayerRepository.findById(anyInt())).thenReturn(Optional.of(competitivePlayer));
		ResponseMessage response = competitiveService.updateWeightForCompetitivePlayer(1, 58);
		assertEquals(response.getData().size(), 0);
	}
	
	@Test 
	public void testUpdateWeightForCompetitivePlayerCaseEmpty() {
		when(competitivePlayerRepository.findById(anyInt())).thenReturn(Optional.empty());
		ResponseMessage response = competitiveService.updateWeightForCompetitivePlayer(1, 50);
		assertEquals(response.getData().size(), 0);
	}
	
	@Test 
	public void testUpdateWeightForCompetitivePlayerCaseException() {
		when(competitivePlayerRepository.findById(anyInt())).thenReturn(null);
		ResponseMessage response = competitiveService.updateWeightForCompetitivePlayer(1, 50);
		assertEquals(response.getData().size(), 0);
	}
	
	@Test
	public void testDeleteCompetitivePlayerCaseStatusEq1() {
		Set<CompetitiveType> competitiveTypes = competitiveTypes();
		List<CompetitiveType> listCompetitive = new ArrayList<CompetitiveType>(competitiveTypes);
		listCompetitive.get(0).setStatus(0);
		CompetitivePlayer competitivePlayer = competitivePlayer();
		competitivePlayer.setCompetitiveType(listCompetitive.get(0));
		Tournament tournament = tournament();
		tournament.setStage(1);
		List<ExhibitionPlayer> exhibitionPlayers = new ArrayList<ExhibitionPlayer>(exhibitionPlayers());
		when(exhibitionPlayerRepository.findAllByPlayerId(anyInt())).thenReturn(exhibitionPlayers);
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament));
		when(competitivePlayerRepository.findById(anyInt())).thenReturn(Optional.of(competitivePlayer));
		ResponseMessage response = competitiveService.deleteCompetitivePlayer(1);
		assertEquals(response.getData().size(), 1);
	}
	
	@Test
	public void testDeleteCompetitivePlayerCaseDeletePlayer() {
		Tournament tournament = tournament();
		tournament.setStage(1);
		Set<CompetitiveType> competitiveTypes = competitiveTypes();
		List<CompetitiveType> listCompetitive = new ArrayList<CompetitiveType>(competitiveTypes);
		listCompetitive.get(0).setStatus(0);
		CompetitivePlayer competitivePlayer = competitivePlayer();
		competitivePlayer.setCompetitiveType(listCompetitive.get(0));
		List<ExhibitionPlayer> exhibitionPlayers = new ArrayList<ExhibitionPlayer>();
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament));
		when(exhibitionPlayerRepository.findAllByPlayerId(anyInt())).thenReturn(exhibitionPlayers);
		when(competitivePlayerRepository.findById(anyInt())).thenReturn(Optional.of(competitivePlayer));
		when(tournamentPlayerRepository.findById(anyInt())).thenReturn(Optional.of(tournamentPlayer()));
		ResponseMessage response = competitiveService.deleteCompetitivePlayer(1);
		assertEquals(response.getData().size(), 1);
	}
	
	@Test
	public void testDeleteCompetitivePlayerCaseStatusEq3() {
		Set<CompetitiveType> competitiveTypes = competitiveTypes();
		List<CompetitiveType> listCompetitive = new ArrayList<CompetitiveType>(competitiveTypes);
		listCompetitive.get(0).setStatus(3);
		CompetitivePlayer competitivePlayer = competitivePlayer();
		competitivePlayer.setCompetitiveType(listCompetitive.get(0));
		competitivePlayer.setIsEligible(true);
		Tournament tournament = tournament();
		tournament.setStage(3);
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament));
		when(competitivePlayerRepository.findById(anyInt())).thenReturn(Optional.of(competitivePlayer));
		ResponseMessage response = competitiveService.deleteCompetitivePlayer(1);
		assertEquals(response.getData().size(), 0);
	}
	
	
	
	@Test
	public void testDeleteCompetitivePlayerCaseStatusEq3NotEligible() {
		Set<CompetitiveType> competitiveTypes = competitiveTypes();
		List<CompetitiveType> listCompetitive = new ArrayList<CompetitiveType>(competitiveTypes);
		listCompetitive.get(0).setStatus(3);
		CompetitivePlayer competitivePlayer = competitivePlayer();
		competitivePlayer.setCompetitiveType(listCompetitive.get(0));
		competitivePlayer.setIsEligible(false);
		Tournament tournament = tournament();
		tournament.setStage(3);
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament));
		List<ExhibitionPlayer> exhibitionPlayers = new ArrayList<ExhibitionPlayer>(exhibitionPlayers());
		when(competitivePlayerRepository.findById(anyInt())).thenReturn(Optional.of(competitivePlayer));
		when(exhibitionPlayerRepository.findAllByPlayerId(anyInt())).thenReturn(exhibitionPlayers);
		ResponseMessage response = competitiveService.deleteCompetitivePlayer(1);
		assertEquals(response.getData().size(), 1);
	}
	
	@Test
	public void testDeleteCompetitivePlayerCaseEmpty() {
		when(competitivePlayerRepository.findById(anyInt())).thenReturn(Optional.empty());
		ResponseMessage response = competitiveService.deleteCompetitivePlayer(1);
		assertEquals(response.getData().size(), 0);
	}
	
	
	@Test
	public void testDeleteCompetitivePlayerCaseException() {
		when(competitivePlayerRepository.findById(anyInt())).thenReturn(null);
		ResponseMessage response = competitiveService.deleteCompetitivePlayer(1);
		assertEquals(response.getData().size(), 0);
	}
	
	@Test
	public void testGetListPlayer() {
		Set<CompetitiveType> competitiveTypes = competitiveTypes();
		List<CompetitiveType> listCompetitive = new ArrayList<CompetitiveType>(competitiveTypes);
		listCompetitive.get(0).setStatus(0);
		when(competitiveTypeRepository.findById(anyInt())).thenReturn(Optional.of(listCompetitive.get(0)));
		when(competitivePlayerRepository.findByCompetitiveTypeId(anyInt())).thenReturn(Arrays.asList(competitivePlayer()));
		ResponseMessage response = competitiveService.getListPlayer(1);
		assertEquals(response.getData().size(), 1);
	}
	
	@Test
	public void testGetListPlayerCaseTypeFemale() {
		Set<CompetitiveType> competitiveTypes = competitiveTypes();
		List<CompetitiveType> listCompetitive = new ArrayList<CompetitiveType>(competitiveTypes);
		listCompetitive.get(0).setStatus(0);
		listCompetitive.get(0).setGender(false);
		when(competitiveTypeRepository.findById(anyInt())).thenReturn(Optional.of(listCompetitive.get(0)));
		when(competitivePlayerRepository.findByCompetitiveTypeId(anyInt())).thenReturn(Arrays.asList(competitivePlayer()));
		ResponseMessage response = competitiveService.getListPlayer(1);
		assertEquals(response.getData().size(), 1);
	}
	
	@Test
	public void testGetListPlayerCaseTypeEmpty() {
		Set<CompetitiveType> competitiveTypes = competitiveTypes();
		List<CompetitiveType> listCompetitive = new ArrayList<CompetitiveType>(competitiveTypes);
		listCompetitive.get(0).setStatus(0);
		when(competitiveTypeRepository.findById(anyInt())).thenReturn(Optional.empty());
		ResponseMessage response = competitiveService.getListPlayer(1);
		assertEquals(response.getData().size(), 0);
	}
	
	@Test
	public void testGetListPlayerCaseTypeException() {
		Set<CompetitiveType> competitiveTypes = competitiveTypes();
		List<CompetitiveType> listCompetitive = new ArrayList<CompetitiveType>(competitiveTypes);
		listCompetitive.get(0).setStatus(0);
		when(competitiveTypeRepository.findById(anyInt())).thenReturn(null);
		ResponseMessage response = competitiveService.getListPlayer(1);
		assertEquals(response.getData().size(), 0);
	}
}
