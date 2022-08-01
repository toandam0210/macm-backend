package com.fpt.macm.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fpt.macm.model.entity.ClubFund;
import com.fpt.macm.model.entity.ClubFundReport;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.repository.ClubFundReportRepository;
import com.fpt.macm.repository.ClubFundRepository;

@ExtendWith(MockitoExtension.class)
public class ClubFundServiceTest {

	@InjectMocks
	ClubFundService clubFundService = new ClubFundServiceImpl();
	
	@Mock
	ClubFundRepository clubFundRepository;

	@Mock
	ClubFundReportRepository clubFundReportRepository;
	
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
		
		ResponseMessage responseMessage = clubFundService.depositToClubFund(10000, "Nạp lần đầu");
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void depositToClubFundCaseNoteEmpty() {
		ResponseMessage responseMessage = clubFundService.depositToClubFund(10000, "");
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void depositToClubFundCaseException() {
		when(clubFundRepository.findById(anyInt())).thenReturn(null);
		
		ResponseMessage responseMessage = clubFundService.depositToClubFund(10000, "Nạp lần đầu");
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void withdrawFromClubFundCaseSuccess() {
		when(clubFundRepository.findById(anyInt())).thenReturn(Optional.of(clubFund()));
		
		ResponseMessage responseMessage = clubFundService.withdrawFromClubFund(10000, "Nạp lần đầu");
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void withdrawFromClubFundCaseNotEnoughMoney() {
		when(clubFundRepository.findById(anyInt())).thenReturn(Optional.of(clubFund()));
		
		ResponseMessage responseMessage = clubFundService.withdrawFromClubFund(1000000000, "Nạp lần đầu");
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void withdrawFromClubFundCaseNoteEmpty() {
		ResponseMessage responseMessage = clubFundService.withdrawFromClubFund(100000, "");
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void withdrawFromClubFundCaseException() {
		when(clubFundRepository.findById(anyInt())).thenReturn(null);
		
		ResponseMessage responseMessage = clubFundService.withdrawFromClubFund(1000000, "Nạp lần đầu");
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void getClubFundReportCaseSuccess() {
		when(clubFundReportRepository.findAll()).thenReturn(Arrays.asList(clubFundReport()));
		
		ResponseMessage responseMessage = clubFundService.getClubFundReport();
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void getClubFundReportCaseListEmpty() {
		when(clubFundReportRepository.findAll()).thenReturn(new ArrayList<ClubFundReport>());
		
		ResponseMessage responseMessage = clubFundService.getClubFundReport();
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void getClubFundReportCaseException() {
		when(clubFundReportRepository.findAll()).thenReturn(null);
		
		ResponseMessage responseMessage = clubFundService.getClubFundReport();
		assertEquals(responseMessage.getData().size(), 0);
	}
}