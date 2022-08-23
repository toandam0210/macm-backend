package com.fpt.macm.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import com.fpt.macm.model.entity.ClubFund;
import com.fpt.macm.model.entity.ClubFundReport;
import com.fpt.macm.model.entity.Role;
import com.fpt.macm.model.entity.User;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.repository.ClubFundReportRepository;
import com.fpt.macm.repository.ClubFundRepository;
import com.fpt.macm.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class ClubFundServiceTest {

	@InjectMocks
	ClubFundService clubFundService = new ClubFundServiceImpl();

	@Mock
	ClubFundRepository clubFundRepository;

	@Mock
	ClubFundReportRepository clubFundReportRepository;

	@Mock
	UserRepository userRepository;

	private User user() {
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

	private ClubFund clubFund() {
		ClubFund clubFund = new ClubFund();
		clubFund.setId(1);
		clubFund.setFundAmount(1000000);
		return clubFund;
	}

	private ClubFundReport clubFundReport() {
		ClubFundReport clubFundReport = new ClubFundReport();
		clubFundReport.setId(1);
		clubFundReport.setNote("Nạp lần đầu");
		clubFundReport.setFundChange(100000);
		clubFundReport.setFundBalance(100000);
		clubFundReport.setCreatedOn(LocalDateTime.now());
		return clubFundReport;
	}

	@Test
	public void getClubFundCaseSuccess() {
		when(clubFundRepository.findById(anyInt())).thenReturn(Optional.of(clubFund()));

		ResponseMessage responseMessage = clubFundService.getClubFund();
		assertEquals(responseMessage.getData().size(), 1);
	}

	@Test
	public void getClubFundCaseException() {
		when(clubFundRepository.findById(anyInt())).thenReturn(null);

		ResponseMessage responseMessage = clubFundService.getClubFund();
		assertEquals(responseMessage.getData().size(), 0);
	}

	@Test
	public void depositToClubFundCaseSuccess() {
		when(clubFundRepository.findById(anyInt())).thenReturn(Optional.of(clubFund()));
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));

		ResponseMessage responseMessage = clubFundService.depositToClubFund(user().getStudentId(), 10000,
				"Nạp lần đầu");
		assertEquals(responseMessage.getData().size(), 1);
	}

	@Test
	public void depositToClubFundCaseNoteEmpty() {
		ResponseMessage responseMessage = clubFundService.depositToClubFund(user().getStudentId(), 10000, "");
		assertEquals(responseMessage.getData().size(), 0);
	}

	@Test
	public void depositToClubFundCaseException() {
		when(clubFundRepository.findById(anyInt())).thenReturn(null);
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));

		ResponseMessage responseMessage = clubFundService.depositToClubFund(user().getStudentId(), 10000,
				"Nạp lần đầu");
		assertEquals(responseMessage.getData().size(), 0);
	}

	@Test
	public void withdrawFromClubFundCaseSuccess() {
		when(clubFundRepository.findById(anyInt())).thenReturn(Optional.of(clubFund()));
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));

		ResponseMessage responseMessage = clubFundService.withdrawFromClubFund(user().getStudentId(), 10000,
				"Nạp lần đầu");
		assertEquals(responseMessage.getData().size(), 1);
	}

	@Test
	public void withdrawFromClubFundCaseNotEnoughMoney() {
		when(clubFundRepository.findById(anyInt())).thenReturn(Optional.of(clubFund()));
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));

		ResponseMessage responseMessage = clubFundService.withdrawFromClubFund(user().getStudentId(), 1000000000,
				"Nạp lần đầu");
		assertEquals(responseMessage.getData().size(), 0);
	}

	@Test
	public void withdrawFromClubFundCaseNoteEmpty() {
		ResponseMessage responseMessage = clubFundService.withdrawFromClubFund(user().getStudentId(), 100000, "");
		assertEquals(responseMessage.getData().size(), 0);
	}

	@Test
	public void withdrawFromClubFundCaseException() {
		when(clubFundRepository.findById(anyInt())).thenReturn(null);
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));

		ResponseMessage responseMessage = clubFundService.withdrawFromClubFund(user().getStudentId(), 1000000,
				"Nạp lần đầu");
		assertEquals(responseMessage.getData().size(), 0);
	}

	@Test
	public void getClubFundReportCaseSuccess() {
		when(clubFundReportRepository.findAll(any(Sort.class))).thenReturn(Arrays.asList(clubFundReport()));

		ResponseMessage responseMessage = clubFundService.getClubFundReport();
		assertEquals(responseMessage.getData().size(), 1);
	}

	@Test
	public void getClubFundReportCaseListEmpty() {
		when(clubFundReportRepository.findAll(any(Sort.class))).thenReturn(new ArrayList<ClubFundReport>());

		ResponseMessage responseMessage = clubFundService.getClubFundReport();
		assertEquals(responseMessage.getData().size(), 0);
	}

	@Test
	public void getClubFundReportCaseException() {
		when(clubFundReportRepository.findAll(any(Sort.class))).thenReturn(null);

		ResponseMessage responseMessage = clubFundService.getClubFundReport();
		assertEquals(responseMessage.getData().size(), 0);
	}
}
