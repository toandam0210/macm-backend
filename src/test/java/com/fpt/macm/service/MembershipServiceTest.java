package com.fpt.macm.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import com.fpt.macm.model.entity.ClubFund;
import com.fpt.macm.model.entity.MembershipInfo;
import com.fpt.macm.model.entity.MembershipPaymentStatusReport;
import com.fpt.macm.model.entity.MembershipStatus;
import com.fpt.macm.model.entity.Role;
import com.fpt.macm.model.entity.User;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.repository.ClubFundRepository;
import com.fpt.macm.repository.MembershipPaymentStatusReportRepository;
import com.fpt.macm.repository.MembershipShipInforRepository;
import com.fpt.macm.repository.MembershipStatusRepository;

@ExtendWith(MockitoExtension.class)
public class MembershipServiceTest {
	@InjectMocks
	MembershipService membershipService = new MembershipServiceImpl();
	
	@Mock
	MembershipStatusRepository membershipStatusRepository;

	@Mock
	MembershipShipInforRepository membershipShipInforRepository;

	@Mock
	ClubFundRepository clubFundRepository;

	@Mock
	MembershipPaymentStatusReportRepository membershipPaymentStatusReportRepository;
	
	@Mock
	ClubFundService clubFundService;
	
	private MembershipStatus membershipStatus() {
		MembershipStatus membershipStatus = new MembershipStatus();
		membershipStatus.setId(1);
		membershipStatus.setMembershipInfo(membershipInfo());
		membershipStatus.setStatus(true);
		membershipStatus.setUser(createUser());
		return membershipStatus;
	}
	
	private MembershipInfo membershipInfo() {
		MembershipInfo membershipInfo = new MembershipInfo();
		membershipInfo.setAmount(100000);
		membershipInfo.setId(1);
		membershipInfo.setSemester("Summer2022");
		return membershipInfo;
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
	
	private ClubFund clubFund() {
		ClubFund clubFund = new ClubFund();
		clubFund.setFundAmount(10000000);
		clubFund.setId(1);
		return clubFund;
	}
	
	private MembershipPaymentStatusReport membershipPaymentStatusReport() {
		MembershipPaymentStatusReport membershipPaymentStatusReport = new MembershipPaymentStatusReport();
		membershipPaymentStatusReport.setFundBalance(15000000);
		membershipPaymentStatusReport.setFundChange(500000);
		membershipPaymentStatusReport.setId(1);
		membershipPaymentStatusReport.setMembershipInfo(membershipInfo());
		membershipPaymentStatusReport.setPaymentStatus(true);
		membershipPaymentStatusReport.setUser(createUser());
		return membershipPaymentStatusReport;
	}
	
	@Test
	public void testGetListMemberPayMembershipBySemester() {
		when(membershipStatusRepository.findByMembershipInfoId(anyInt())).thenReturn(Arrays.asList(membershipStatus()));
		ResponseMessage responseMessage = membershipService.getListMemberPayMembershipBySemester(1);
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void testGetListMemberPayMembershipBySemesterCaseSizeEq0() {
		when(membershipStatusRepository.findByMembershipInfoId(anyInt())).thenReturn(Arrays.asList());
		ResponseMessage responseMessage = membershipService.getListMemberPayMembershipBySemester(1);
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void testGetListMemberPayMembershipBySemesterCaseException() {
		when(membershipStatusRepository.findByMembershipInfoId(anyInt())).thenReturn(null);
		ResponseMessage responseMessage = membershipService.getListMemberPayMembershipBySemester(1);
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void testUpdateStatusPaymenMembershipById() {
		when(membershipStatusRepository.findById(anyInt())).thenReturn(Optional.of(membershipStatus()));
		when(clubFundRepository.findAll()).thenReturn(Arrays.asList(clubFund()));
		ResponseMessage responseMessage = membershipService.updateStatusPaymenMembershipById(1);
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void testUpdateStatusPaymenMembershipByIdCaseStatusFalse() {
		MembershipStatus membershipStatus = membershipStatus();
		membershipStatus.setStatus(false);
		when(membershipStatusRepository.findById(anyInt())).thenReturn(Optional.of(membershipStatus));
		when(clubFundRepository.findAll()).thenReturn(Arrays.asList(clubFund()));
		ResponseMessage responseMessage = membershipService.updateStatusPaymenMembershipById(1);
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void testUpdateStatusPaymenMembershipByIdCaseException() {
		when(membershipStatusRepository.findById(anyInt())).thenReturn(null);
		ResponseMessage responseMessage = membershipService.updateStatusPaymenMembershipById(1);
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void testUpdateMembershipBySemester() {
		when(membershipShipInforRepository.findBySemester(anyString())).thenReturn(Optional.of(membershipInfo()));
		ResponseMessage responseMessage = membershipService.updateMembershipBySemester(150000, "Summer2022");
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void testUpdateMembershipBySemesterCaseEmpty() {
		when(membershipShipInforRepository.findBySemester(anyString())).thenReturn(Optional.empty());
		ResponseMessage responseMessage = membershipService.updateMembershipBySemester(150000, "Summer2022");
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void testUpdateMembershipBySemesterCaseException() {
		when(membershipShipInforRepository.findBySemester(anyString())).thenReturn(null);
		ResponseMessage responseMessage = membershipService.updateMembershipBySemester(150000, "Summer2022");
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void testGetMembershipInfoBySemester() {
		when(membershipShipInforRepository.findBySemester(anyString())).thenReturn(Optional.of(membershipInfo()));
		ResponseMessage responseMessage = membershipService.getMembershipInfoBySemester("Summer2022");
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void testGetMembershipInfoBySemesterCaseEmpty() {
		when(membershipShipInforRepository.findBySemester(anyString())).thenReturn(Optional.empty());
		ResponseMessage responseMessage = membershipService.getMembershipInfoBySemester("Summer2022");
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void testGetMembershipInfoBySemesterCaseException() {
		when(membershipShipInforRepository.findBySemester(anyString())).thenReturn(null);
		ResponseMessage responseMessage = membershipService.getMembershipInfoBySemester("Summer2022");
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void testGetReportMembershipPaymentStatus() {
		List<MembershipPaymentStatusReport> membershipPaymentStatusReports = Arrays.asList(membershipPaymentStatusReport());
		Page<MembershipPaymentStatusReport> pages = new PageImpl<MembershipPaymentStatusReport>(membershipPaymentStatusReports);
		when(membershipPaymentStatusReportRepository.findByMembershipInfoId(anyInt(), any())).thenReturn(pages);
		ResponseMessage responseMessage = membershipService.getReportMembershipPaymentStatus(1,0,10,"id");
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void testGetReportMembershipPaymentStatusCaseEmpty() {
		List<MembershipPaymentStatusReport> membershipPaymentStatusReports = new ArrayList<MembershipPaymentStatusReport>();
		Page<MembershipPaymentStatusReport> pages = new PageImpl<MembershipPaymentStatusReport>(membershipPaymentStatusReports);
		when(membershipPaymentStatusReportRepository.findByMembershipInfoId(anyInt(), any())).thenReturn(pages);
		ResponseMessage responseMessage = membershipService.getReportMembershipPaymentStatus(1,0,10,"id");
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void testGetReportMembershipPaymentStatusCaseNull() {
		ResponseMessage responseMessage = membershipService.getReportMembershipPaymentStatus(0,0,10,"id");
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void testGetReportMembershipPaymentStatusCaseException() {
		ResponseMessage responseMessage = membershipService.getReportMembershipPaymentStatus(0,0,-10,"id");
		assertEquals(responseMessage.getData().size(), 0);
	}
}
