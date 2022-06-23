package com.fpt.macm.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.fpt.macm.dto.MembershipPaymentStatusReportDto;
import com.fpt.macm.dto.MembershipStatusDto;
import com.fpt.macm.model.ClubFund;
import com.fpt.macm.model.Constant;
import com.fpt.macm.model.MembershipInfo;
import com.fpt.macm.model.MembershipPaymentStatusReport;
import com.fpt.macm.model.MembershipStatus;
import com.fpt.macm.model.ResponseMessage;
import com.fpt.macm.repository.ClubFundRepository;
import com.fpt.macm.repository.MembershipPaymentStatusReportRepository;
import com.fpt.macm.repository.MembershipShipInforRepository;
import com.fpt.macm.repository.MembershipStatusRepository;
import com.fpt.macm.utils.Utils;

@Service
public class MembershipServiceImpl implements MembershipService {
	@Autowired
	MembershipStatusRepository membershipStatusRepository;

	@Autowired
	MembershipShipInforRepository membershipShipInforRepository;

	@Autowired
	ClubFundRepository clubFundRepository;

	@Autowired
	MembershipPaymentStatusReportRepository membershipPaymentStatusReportRepository;

	@Override
	public ResponseMessage getListMemberPayMembershipBySemester(int membershipInfoId) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<MembershipStatus> listMembershipStatus = membershipStatusRepository
					.findByMembershipInfoId(membershipInfoId);
			List<MembershipStatusDto> membershipStatusDtos = new ArrayList<MembershipStatusDto>();
			if (listMembershipStatus.size() > 0) {
				for (MembershipStatus membershipStatus : listMembershipStatus) {
					MembershipStatusDto membershipStatusDto = new MembershipStatusDto();
					membershipStatusDto.setId(membershipStatus.getId());
					membershipStatusDto.setStudentId(membershipStatus.getUser().getStudentId());
					membershipStatusDto.setStudentName(membershipStatus.getUser().getName());
					membershipStatusDto.setStatus(membershipStatus.isStatus());
					membershipStatusDto.setRole(Utils.convertRoleFromDbToExcel(membershipStatus.getUser().getRole()));
					membershipStatusDto.setSemester(membershipStatus.getMembershipInfo().getSemester());
					membershipStatusDtos.add(membershipStatusDto);

				}
				responseMessage.setData(membershipStatusDtos);
				responseMessage.setMessage(Constant.MSG_001);
				responseMessage.setTotalResult(membershipStatusDtos.size());
			} else {
				responseMessage.setMessage(Constant.MSG_074);
			}
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage updateStatusPaymenMembershipById(int id) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<MembershipStatus> membershipOp = membershipStatusRepository.findById(id);
			MembershipStatus membershipStatus = membershipOp.get();

			List<ClubFund> clubFunds = clubFundRepository.findAll();
			ClubFund clubFund = clubFunds.get(0);
			double fundAmount = clubFund.getFundAmount();

			double membershipFee = membershipStatus.getMembershipInfo().getAmount();

			clubFund.setFundAmount(
					membershipStatus.isStatus() ? (fundAmount - membershipFee) : (fundAmount + membershipFee));
			clubFundRepository.save(clubFund);

			MembershipPaymentStatusReport membershipPaymentStatusReport = new MembershipPaymentStatusReport();
			membershipPaymentStatusReport.setMembershipInfo(membershipStatus.getMembershipInfo());
			membershipPaymentStatusReport.setUser(membershipStatus.getUser());
			membershipPaymentStatusReport.setPaymentStatus(!membershipStatus.isStatus());
			membershipPaymentStatusReport.setFundChange(membershipStatus.isStatus() ? -membershipFee : membershipFee);
			membershipPaymentStatusReport.setCreatedBy("toandv");
			membershipPaymentStatusReport.setCreatedOn(LocalDateTime.now());
			membershipPaymentStatusReportRepository.save(membershipPaymentStatusReport);

			membershipStatus.setStatus(!membershipStatus.isStatus());
			membershipStatus.setUpdatedBy("toandv");
			membershipStatus.setUpdatedOn(LocalDateTime.now());
			membershipStatusRepository.save(membershipStatus);
			responseMessage.setData(Arrays.asList(membershipStatus));
			responseMessage.setMessage("Cập nhật membership status thành công");
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage updateMembershipBySemester(double amount, String semester) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<MembershipInfo> membershipInfoOp = membershipShipInforRepository.findBySemester(semester);
			if (membershipInfoOp.isPresent()) {
				MembershipInfo membershipInfo = membershipInfoOp.get();
				membershipInfo.setAmount(amount);
				membershipShipInforRepository.save(membershipInfo);
				responseMessage.setData(Arrays.asList(membershipInfo));
				responseMessage.setMessage(Constant.MSG_005);
			}
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage getMembershipInfoBySemester(String semester) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<MembershipInfo> membershipInfoOp = membershipShipInforRepository.findBySemester(semester);
			if (membershipInfoOp.isPresent()) {
				MembershipInfo membershipInfo = membershipInfoOp.get();
				responseMessage.setData(Arrays.asList(membershipInfo));
				responseMessage.setMessage(Constant.MSG_005);
			}
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage getReportMembershipPaymentStatus(int membershipInfoId, int pageNo, int pageSize,
			String sortBy) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
			Page<MembershipPaymentStatusReport> pageResponse = membershipPaymentStatusReportRepository
					.findByMembershipInfoId(membershipInfoId, paging);
			List<MembershipPaymentStatusReport> membershipPaymentStatusReports = new ArrayList<MembershipPaymentStatusReport>();
			if (pageResponse != null && pageResponse.hasContent()) {
				membershipPaymentStatusReports = pageResponse.getContent();
			}

			if (!membershipPaymentStatusReports.isEmpty()) {
				List<MembershipPaymentStatusReportDto> membershipPaymentStatusReportsDto = new ArrayList<MembershipPaymentStatusReportDto>();
				for (MembershipPaymentStatusReport membershipPaymentStatusReport : membershipPaymentStatusReports) {
					MembershipPaymentStatusReportDto membershipPaymentStatusReportDto = new MembershipPaymentStatusReportDto();
					membershipPaymentStatusReportDto.setId(membershipPaymentStatusReport.getId());
					membershipPaymentStatusReportDto.setSemester(membershipPaymentStatusReport.getMembershipInfo().getSemester());
					membershipPaymentStatusReportDto.setUserName(membershipPaymentStatusReport.getUser().getName());
					membershipPaymentStatusReportDto.setUserStudentId(membershipPaymentStatusReport.getUser().getStudentId());
					membershipPaymentStatusReportDto.setPaymentStatus(membershipPaymentStatusReport.isPaymentStatus());
					membershipPaymentStatusReportDto.setFundChange(membershipPaymentStatusReport.getFundChange());
					membershipPaymentStatusReportDto.setCreatedBy(membershipPaymentStatusReport.getCreatedBy());
					membershipPaymentStatusReportDto.setCreatedOn(membershipPaymentStatusReport.getCreatedOn());
					membershipPaymentStatusReportsDto.add(membershipPaymentStatusReportDto);
				}
				responseMessage.setData(membershipPaymentStatusReportsDto);
				responseMessage.setMessage(Constant.MSG_087);
			} else {
				responseMessage.setMessage(Constant.MSG_086);
			}

		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

}
