package com.fpt.macm.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
import com.fpt.macm.model.entity.CompetitivePlayerBracket;
import com.fpt.macm.model.entity.CompetitiveResult;
import com.fpt.macm.model.entity.CompetitiveType;
import com.fpt.macm.model.entity.ExhibitionPlayer;
import com.fpt.macm.model.entity.ExhibitionTeam;
import com.fpt.macm.model.entity.ExhibitionType;
import com.fpt.macm.model.entity.Role;
import com.fpt.macm.model.entity.Tournament;
import com.fpt.macm.model.entity.TournamentPlayer;
import com.fpt.macm.model.entity.TournamentSchedule;
import com.fpt.macm.model.entity.User;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.repository.AreaRepository;
import com.fpt.macm.repository.CompetitiveMatchRepository;
import com.fpt.macm.repository.CompetitivePlayerBracketRepository;
import com.fpt.macm.repository.CompetitivePlayerRepository;
import com.fpt.macm.repository.CompetitiveResultRepository;
import com.fpt.macm.repository.CompetitiveTypeRepository;
import com.fpt.macm.repository.TournamentPlayerRepository;
import com.fpt.macm.repository.TournamentScheduleRepository;
import com.fpt.macm.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class CompetitiveResultServiceTest {
	@InjectMocks
	CompetitiveResultService competitiveResultService = new CompetitiveResultServiceImpl();
	
	@Mock
	CompetitiveMatchRepository competitiveMatchRepository;

	@Mock
	CompetitiveResultRepository competitiveResultRepository;

	@Mock
	CompetitiveTypeRepository competitiveTypeRepository;

	@Mock
	AreaRepository areaRepository;

	@Mock
	UserRepository userRepository;

	@Mock
	TournamentPlayerRepository tournamentPlayerRepository;

	@Mock
	CompetitivePlayerRepository competitivePlayerRepository;

	@Mock
	CompetitivePlayerBracketRepository competitivePlayerBracketRepository;

	@Mock
	TournamentScheduleRepository tournamentScheduleRepository;

	@Mock
	CompetitiveTypeService competitiveTypeService;
	
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
	
	private Area area() {
		Area area = new Area();
		area.setId(1);
		area.setName("San B");
		return area;
	}
	
	private TournamentSchedule tournamentSchedule() {
		TournamentSchedule tournamentSchedule = new TournamentSchedule();
		tournamentSchedule.setId(1);
		tournamentSchedule.setDate(LocalDate.now().plusMonths(1));
		tournamentSchedule.setStartTime(LocalTime.of(18, 0));
		tournamentSchedule.setFinishTime(LocalTime.of(20, 0));
		tournamentSchedule.setTournament(tournament());
		return tournamentSchedule;
	}
	
	@Test
	public void testSpawnTimeAndArea() {
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
		when(competitiveTypeService.getAllTypeByTournament(anyInt())).thenReturn(competitiveTypes());
		when(areaRepository.findAll()).thenReturn(Arrays.asList(area()));
		when(competitiveMatchRepository.listMatchsByType(anyInt())).thenReturn(competitiveMatchs);
		when(tournamentScheduleRepository.findByTournamentId(anyInt())).thenReturn(Arrays.asList(tournamentSchedule()));
		ResponseMessage response = competitiveResultService.spawnTimeAndArea(1);
		assertEquals(response.getData().size(), 4);
	}
	
	@Test
	public void testSpawnTimeAndAreaCaseException() {
		when(competitiveTypeService.getAllTypeByTournament(anyInt())).thenReturn(null);
		ResponseMessage response = competitiveResultService.spawnTimeAndArea(1);
		assertEquals(response.getData().size(), 0);
	}
	
	@Test
	public void testUpdateResultMatchCaseAlreadyHasResult() {
		when(competitiveMatchRepository.findById(anyInt())).thenReturn(Optional.of(competitiveMatch()));
		ResponseMessage response = competitiveResultService.updateResultMatch(1,1,2);
		assertEquals(response.getData().size(), 0);
	}
	
	@Test
	public void testUpdateResultMatchCaseException() {
		ResponseMessage response = competitiveResultService.updateResultMatch(1,-1,2);
		assertEquals(response.getData().size(), 0);
	}
	
	@Test
	public void testUpdateResultMatch() {
		CompetitiveMatch competitiveMatch = competitiveMatch();
		competitiveMatch.setStatus(false);
		when(competitiveMatchRepository.findById(anyInt())).thenReturn(Optional.of(competitiveMatch));
		when(competitiveResultRepository.findByMatchId(anyInt())).thenReturn(Optional.of(competitiveResult()));
		when(competitiveMatchRepository.getById(anyInt())).thenReturn(competitiveMatch);
		ResponseMessage response = competitiveResultService.updateResultMatch(1,1,2);
		assertEquals(response.getData().size(), 0);
	}
	
	@Test
	public void testUpdateResultMatchCaseNotIsNextFirst() {
		CompetitiveMatch competitiveMatch = competitiveMatch();
		competitiveMatch.setStatus(false);
		competitiveMatch.setNextIsFirst(false);
		when(competitiveMatchRepository.findById(anyInt())).thenReturn(Optional.of(competitiveMatch));
		when(competitiveResultRepository.findByMatchId(anyInt())).thenReturn(Optional.of(competitiveResult()));
		when(competitiveMatchRepository.getById(anyInt())).thenReturn(competitiveMatch);
		ResponseMessage response = competitiveResultService.updateResultMatch(1,1,2);
		assertEquals(response.getData().size(), 0);
	}
	
	@Test
	public void testUpdateResultMatchCaseFirstPlayerWin() {
		CompetitiveMatch competitiveMatch = competitiveMatch();
		competitiveMatch.setStatus(false);
		competitiveMatch.setNextIsFirst(false);
		when(competitiveMatchRepository.findById(anyInt())).thenReturn(Optional.of(competitiveMatch));
		when(competitiveResultRepository.findByMatchId(anyInt())).thenReturn(Optional.of(competitiveResult()));
		when(competitiveMatchRepository.getById(anyInt())).thenReturn(competitiveMatch);
		ResponseMessage response = competitiveResultService.updateResultMatch(1,2,1);
		assertEquals(response.getData().size(), 0);
	}
	
	@Test
	public void testUpdateResultMatchCaseResultEmpty() {
		CompetitiveMatch competitiveMatch = competitiveMatch();
		competitiveMatch.setStatus(false);
		competitiveMatch.setNextIsFirst(false);
		when(competitiveMatchRepository.findById(anyInt())).thenReturn(Optional.of(competitiveMatch));
		when(competitiveResultRepository.findByMatchId(anyInt())).thenReturn(Optional.empty());
		ResponseMessage response = competitiveResultService.updateResultMatch(1,2,1);
		assertEquals(response.getData().size(), 0);
	}
	
	@Test
	public void testUpdateTimeAndAreaCaseExisted() {
		when(competitiveResultRepository.listResultByAreaOrderTime(anyInt())).thenReturn(Arrays.asList(competitiveResult()));
		ResponseMessage response = competitiveResultService.updateTimeAndArea(1,competitiveResult());
		assertEquals(response.getData().size(), 0);
	}
	
	@Test
	public void testUpdateTimeAndArea() {
		CompetitiveResult competitiveResult = competitiveResult();
		competitiveResult.setArea(area());
		competitiveResult.setTime(LocalDateTime.of(2022, 9, 1, 18, 0));
		when(competitiveResultRepository.listResultByAreaOrderTime(anyInt())).thenReturn(Arrays.asList(competitiveResult()));
		when(competitiveResultRepository.findByMatchId(anyInt())).thenReturn(Optional.of(competitiveResult()));
		ResponseMessage response = competitiveResultService.updateTimeAndArea(1,competitiveResult);
		assertEquals(response.getData().size(), 1 );
	}
	
	
}
