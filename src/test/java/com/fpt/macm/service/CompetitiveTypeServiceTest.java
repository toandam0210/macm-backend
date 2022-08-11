package com.fpt.macm.service;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
import com.fpt.macm.model.entity.CompetitiveType;
import com.fpt.macm.model.entity.ExhibitionPlayer;
import com.fpt.macm.model.entity.ExhibitionTeam;
import com.fpt.macm.model.entity.ExhibitionType;
import com.fpt.macm.model.entity.Role;
import com.fpt.macm.model.entity.Tournament;
import com.fpt.macm.model.entity.TournamentPlayer;
import com.fpt.macm.model.entity.User;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.repository.CompetitivePlayerRepository;
import com.fpt.macm.repository.CompetitiveTypeRepository;
import com.fpt.macm.repository.TournamentPlayerRepository;
import com.fpt.macm.repository.TournamentRepository;
import com.fpt.macm.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class CompetitiveTypeServiceTest {
	
	@InjectMocks
	CompetitiveTypeService competitiveTypeService = new CompetitiveTypeServiceImpl();
	
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
	
	@Test
	public void testGetAllType() {
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament()));
		ResponseMessage response = competitiveTypeService.getAllType(1);
		assertEquals(response.getData().size(), 1);
	}
	
	@Test
	public void testGetAllTypeCaseSort() {
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
		ResponseMessage response = competitiveTypeService.getAllType(1);
		assertEquals(response.getData().size(), 1);
	}
	
	@Test
	public void testGetAllTypeCaseSort2() {
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
		ResponseMessage response = competitiveTypeService.getAllType(1);
		assertEquals(response.getData().size(), 1);
	}

	@Test
	public void testGetAllTypeCaseSort3() {
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
		ResponseMessage response = competitiveTypeService.getAllType(1);
		assertEquals(response.getData().size(), 1);
	}
	
	@Test
	public void testGetAllTypeCaseException() {
		when(tournamentRepository.findById(anyInt())).thenReturn(null);
		ResponseMessage response = competitiveTypeService.getAllType(10);
		assertEquals(response.getData().size(), 0);
	}
	
	@Test
	public void testGetAllTypeByTournament() {
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament()));
		Set<CompetitiveType> competitiveTypes = competitiveTypeService.getAllTypeByTournament(1);
		assertEquals(competitiveTypes.size(), 1);
	}
	
	@Test
	public void testGetAllTypeByTournamentCaseException() {
		when(tournamentRepository.findById(anyInt())).thenReturn(null);
		Set<CompetitiveType> competitiveTypes = competitiveTypeService.getAllTypeByTournament(1);
		assertNull(competitiveTypes);
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
		ResponseMessage response = competitiveTypeService.getListNotJoinCompetitive(1);
		assertEquals(response.getData().size(), 1);
	}
	
	@Test
	public void testGetListNotJoinCompetitiveCaseException() {
		when(competitiveTypeRepository.findById(anyInt())).thenReturn(null);
		ResponseMessage response = competitiveTypeService.getListNotJoinCompetitive(1);
		assertEquals(response.getData().size(), 0);
	}
	
	
}
