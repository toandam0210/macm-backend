package com.fpt.macm.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fpt.macm.model.entity.Area;
import com.fpt.macm.model.entity.CompetitiveType;
import com.fpt.macm.model.entity.ExhibitionPlayer;
import com.fpt.macm.model.entity.ExhibitionResult;
import com.fpt.macm.model.entity.ExhibitionTeam;
import com.fpt.macm.model.entity.ExhibitionType;
import com.fpt.macm.model.entity.Role;
import com.fpt.macm.model.entity.Tournament;
import com.fpt.macm.model.entity.TournamentPlayer;
import com.fpt.macm.model.entity.User;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.repository.ExhibitionPlayerRepository;
import com.fpt.macm.repository.ExhibitionResultRepository;
import com.fpt.macm.repository.ExhibitionTeamRepository;
import com.fpt.macm.repository.ExhibitionTypeRepository;
import com.fpt.macm.repository.TournamentPlayerRepository;
import com.fpt.macm.repository.TournamentRepository;
import com.fpt.macm.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class ExhibitionTeamServiceTest {
	
	@InjectMocks
	ExhibitionTeamService exhibitionTeamService = new ExhibitionTeamServiceImpl();
	
	@Mock
	ExhibitionTypeRepository exhibitionTypeRepository;
	
	@Mock
	ExhibitionTeamRepository exhibitionTeamRepository;
	
	@Mock
	ExhibitionPlayerRepository exhibitionPlayerRepository;
	
	@Mock
	ExhibitionResultRepository exhibitionResultRepository;
	
	@Mock
	UserRepository userRepository;
	
	@Mock
	TournamentPlayerRepository tournamentPlayerRepository;
	
	@Mock
	TournamentRepository tournamentRepository;

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

	private Set<ExhibitionTeam> exhibitionTeams() {
		Set<ExhibitionTeam> exhibitionTeams = new HashSet<ExhibitionTeam>();
		exhibitionTeams.add(exhibitionTeam());
		return exhibitionTeams;
	}
	
	private ExhibitionTeam exhibitionTeam() {
		ExhibitionTeam exhibitionTeam = new ExhibitionTeam();
		exhibitionTeam.setExhibitionPlayers(exhibitionPlayers());
		exhibitionTeam.setId(1);
		exhibitionTeam.setTeamName("Team 1");
		return exhibitionTeam;
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

	private TournamentPlayer tournamentPlayer() {
		TournamentPlayer tournamentPlayer = new TournamentPlayer();
		tournamentPlayer.setId(1);
		tournamentPlayer.setPaymentStatus(true);
		tournamentPlayer.setUser(user());
		return tournamentPlayer;
	}

	private Set<TournamentPlayer> tournamentPlayers() {
		Set<TournamentPlayer> tournamentPlayers = new HashSet<TournamentPlayer>();
		TournamentPlayer tournamentPlayer = new TournamentPlayer();
		tournamentPlayer.setId(1);
		tournamentPlayer.setPaymentStatus(true);
		tournamentPlayer.setUser(user());
		tournamentPlayers.add(tournamentPlayer);
		return tournamentPlayers;
	}

	private User user() {
		User user = new User();
		user.setId(1);
		user.setStudentId("HE140855");
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
	
	private ExhibitionResult exhibitionResult() {
		ExhibitionResult exhibitionResult = new ExhibitionResult();
		for (ExhibitionTeam exhibitionTeam : exhibitionTeams()) {
			exhibitionResult.setId(1);
			exhibitionResult.setTeam(exhibitionTeam);
			exhibitionResult.setScore(0.0);
			exhibitionResult.setTime(LocalDateTime.now());
			exhibitionResult.setArea(area());
		}
		return exhibitionResult;
	}
	
	private Area area() {
		Area area = new Area();
		area.setId(1);
		area.setName("Sân 1");
		return area;
	}
	
	private ExhibitionType exhibitionType() {
		List<ExhibitionType> exhibitionTypes = new ArrayList<>(exhibitionTypes());
		return exhibitionTypes.get(0);
	}
	
	private ExhibitionPlayer exhibitionPlayer() {
		List<ExhibitionPlayer> exhibitionPlayers = new ArrayList<>(exhibitionPlayers());
		return exhibitionPlayers.get(0);
	}
	
	@Test
	public void registerTeamCaseSuccessMale() {
		List<String> listStudentId = new ArrayList<>();
		listStudentId.add("HE140855");
		
		when(exhibitionTypeRepository.findById(anyInt())).thenReturn(Optional.of(exhibitionType()));
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
		when(tournamentRepository.getById(anyInt())).thenReturn(tournament());
		when(tournamentPlayerRepository.findPlayerByUserIdAndTournamentId(anyInt(), anyInt())).thenReturn(Optional.of(tournamentPlayer()));
		
		ResponseMessage responseMessage = exhibitionTeamService.registerTeam(1, "Team 1", listStudentId);
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void registerTeamCaseSuccessFemale() {
		List<String> listStudentId = new ArrayList<>();
		listStudentId.add("HE140855");
		
		ExhibitionType exhibitionType = exhibitionType();
		exhibitionType.setNumberFemale(1);
		exhibitionType.setNumberMale(0);
		
		User user = user();
		user.setGender(false);
		
		when(exhibitionTypeRepository.findById(anyInt())).thenReturn(Optional.of(exhibitionType));
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user));
		when(tournamentRepository.getById(anyInt())).thenReturn(tournament());
		when(tournamentPlayerRepository.findPlayerByUserIdAndTournamentId(anyInt(), anyInt())).thenReturn(Optional.of(tournamentPlayer()));
		
		ResponseMessage responseMessage = exhibitionTeamService.registerTeam(1, "Team 1", listStudentId);
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void registerTeamCaseTournamentPlayerAndExhibitionPlayerNotNull() {
		List<String> listStudentId = new ArrayList<>();
		listStudentId.add("HE140855");
		
		when(exhibitionTypeRepository.findById(anyInt())).thenReturn(Optional.of(exhibitionType()));
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
		when(tournamentPlayerRepository.getPlayerByUserIdAndTournamentId(anyInt(), anyInt())).thenReturn(Optional.of(tournamentPlayer()));
		when(exhibitionPlayerRepository.findByTournamentPlayerAndType(anyInt(), anyInt())).thenReturn(Optional.of(exhibitionPlayer()));
		
		ResponseMessage responseMessage = exhibitionTeamService.registerTeam(1, "Team 1", listStudentId);
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void registerTeamCaseTournamentPlayerNotNullAndExhibitionPlayerNull() {
		List<String> listStudentId = new ArrayList<>();
		listStudentId.add("HE140855");
		
		when(exhibitionTypeRepository.findById(anyInt())).thenReturn(Optional.of(exhibitionType()));
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
		when(tournamentPlayerRepository.getPlayerByUserIdAndTournamentId(anyInt(), anyInt())).thenReturn(Optional.of(tournamentPlayer()));
		
		ResponseMessage responseMessage = exhibitionTeamService.registerTeam(1, "Team 1", listStudentId);
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void registerTeamCaseInvalidNumberOfMale() {
		List<String> listStudentId = new ArrayList<>();
		listStudentId.add("HE140855");
		listStudentId.add("HE140856");
		
		when(exhibitionTypeRepository.findById(anyInt())).thenReturn(Optional.of(exhibitionType()));
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
		when(tournamentPlayerRepository.getPlayerByUserIdAndTournamentId(anyInt(), anyInt())).thenReturn(Optional.of(tournamentPlayer()));
		
		ResponseMessage responseMessage = exhibitionTeamService.registerTeam(1, "Team 1", listStudentId);
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void registerTeamCaseSuccessTeam2Member() {
		List<String> listStudentId = new ArrayList<>();
		listStudentId.add("HE140855");
		listStudentId.add("HE140856");
		
		ExhibitionType exhibitionType = exhibitionType();
		exhibitionType.setNumberFemale(0);
		exhibitionType.setNumberMale(2);
		
		when(exhibitionTypeRepository.findById(anyInt())).thenReturn(Optional.of(exhibitionType));
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
		when(tournamentRepository.getById(anyInt())).thenReturn(tournament());
		when(tournamentPlayerRepository.findPlayerByUserIdAndTournamentId(anyInt(), anyInt())).thenReturn(Optional.of(tournamentPlayer()));
		
		ResponseMessage responseMessage = exhibitionTeamService.registerTeam(1, "Team 1", listStudentId);
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void registerTeamCaseException() {
		List<String> listStudentId = new ArrayList<>();
		listStudentId.add("HE140855");
		
		when(exhibitionTypeRepository.findById(anyInt())).thenReturn(null);
		
		ResponseMessage responseMessage = exhibitionTeamService.registerTeam(1, "Team 1", listStudentId);
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void getTeamByTypeCaseSuccess() {
		when(exhibitionTypeRepository.findById(anyInt())).thenReturn(Optional.of(exhibitionType()));
		
		ResponseMessage responseMessage = exhibitionTeamService.getTeamByType(1);
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void getTeamByTypeCaseException() {
		when(exhibitionTypeRepository.findById(anyInt())).thenReturn(null);
		
		ResponseMessage responseMessage = exhibitionTeamService.getTeamByType(1);
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void getTop3TeamByTypeCaseExhibitionResultNull() {
		when(exhibitionTypeRepository.findById(anyInt())).thenReturn(Optional.of(exhibitionType()));
		
		ResponseMessage responseMessage = exhibitionTeamService.getTop3TeamByType(1);
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void getTop3TeamByTypeCaseExhibitionScoreNull() {
		ExhibitionResult exhibitionResult = exhibitionResult();
		exhibitionResult.setScore(null);
		
		when(exhibitionTypeRepository.findById(anyInt())).thenReturn(Optional.of(exhibitionType()));
		when(exhibitionResultRepository.findByTeam(anyInt())).thenReturn(Optional.of(exhibitionResult));
		
		ResponseMessage responseMessage = exhibitionTeamService.getTop3TeamByType(1);
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void getTop3TeamByTypeCaseExhibitionScoreNotNull() {
		when(exhibitionTypeRepository.findById(anyInt())).thenReturn(Optional.of(exhibitionType()));
		when(exhibitionResultRepository.findByTeam(anyInt())).thenReturn(Optional.of(exhibitionResult()));
		
		ResponseMessage responseMessage = exhibitionTeamService.getTop3TeamByType(1);
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void getTop3TeamByTypeCaseExhibitionTeamMemberCountGreaterThan3() {
		ExhibitionType exhibitionType = exhibitionType();
		Set<ExhibitionTeam> exhibitionTeams = exhibitionType.getExhibitionTeams();
		exhibitionTeams.add(exhibitionTeam());
		exhibitionTeams.add(exhibitionTeam());
		exhibitionTeams.add(exhibitionTeam());
		exhibitionTeams.add(exhibitionTeam());
		
		when(exhibitionTypeRepository.findById(anyInt())).thenReturn(Optional.of(exhibitionType));
		when(exhibitionResultRepository.findByTeam(anyInt())).thenReturn(Optional.of(exhibitionResult()));
		
		ResponseMessage responseMessage = exhibitionTeamService.getTop3TeamByType(1);
		assertEquals(responseMessage.getData().size(), 2);
	}
	
	@Test
	public void getTop3TeamByTypeCaseException() {
		when(exhibitionTypeRepository.findById(anyInt())).thenReturn(null);
		
		ResponseMessage responseMessage = exhibitionTeamService.getTop3TeamByType(1);
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	
}
