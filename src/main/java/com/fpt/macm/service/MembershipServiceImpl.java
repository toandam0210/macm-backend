package com.fpt.macm.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.fpt.macm.constant.Constant;
import com.fpt.macm.model.dto.MembershipPaymentStatusReportDto;
import com.fpt.macm.model.dto.MembershipStatusDto;
import com.fpt.macm.model.entity.ClubFund;
import com.fpt.macm.model.entity.MembershipInfo;
import com.fpt.macm.model.entity.MembershipPaymentStatusReport;
import com.fpt.macm.model.entity.MembershipStatus;
import com.fpt.macm.model.entity.User;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.repository.ClubFundRepository;
import com.fpt.macm.repository.MembershipPaymentStatusReportRepository;
import com.fpt.macm.repository.MembershipShipInforRepository;
import com.fpt.macm.repository.MembershipStatusRepository;
import com.fpt.macm.repository.UserRepository;
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

	@Autowired
	ClubFundService clubFundService;

	@Autowired
	UserRepository userRepository;

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
				Collections.sort(membershipStatusDtos);
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
	public ResponseMessage updateStatusPaymenMembershipById(String studentId, int id) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			User user = userRepository.findByStudentId(studentId).get();

			Optional<MembershipStatus> membershipOp = membershipStatusRepository.findById(id);
			MembershipStatus membershipStatus = membershipOp.get();

			List<ClubFund> clubFunds = clubFundRepository.findAll();
			ClubFund clubFund = clubFunds.get(0);
			double fundAmount = clubFund.getFundAmount();

			double membershipFee = membershipStatus.getMembershipInfo().getAmount();

			double fundBalance = membershipStatus.isStatus() ? (fundAmount - membershipFee)
					: (fundAmount + membershipFee);

			if (membershipStatus.isStatus()) {
				clubFundService.withdrawFromClubFund(user.getStudentId(), membershipFee,
						"Cập nhật trạng thái đóng phí duy trì CLB kỳ "
								+ membershipStatus.getMembershipInfo().getSemester() + " của "
								+ membershipStatus.getUser().getName() + " - "
								+ membershipStatus.getUser().getStudentId() + " thành chưa đóng");
			} else {
				clubFundService.depositToClubFund(user.getStudentId(), membershipFee,
						"Cập nhật trạng thái đóng phí duy trì CLB kỳ "
								+ membershipStatus.getMembershipInfo().getSemester() + " của "
								+ membershipStatus.getUser().getName() + " - "
								+ membershipStatus.getUser().getStudentId() + " thành đã đóng");
			}

			MembershipPaymentStatusReport membershipPaymentStatusReport = new MembershipPaymentStatusReport();
			membershipPaymentStatusReport.setMembershipInfo(membershipStatus.getMembershipInfo());
			membershipPaymentStatusReport.setUser(membershipStatus.getUser());
			membershipPaymentStatusReport.setPaymentStatus(!membershipStatus.isStatus());
			membershipPaymentStatusReport.setFundChange(membershipStatus.isStatus() ? -membershipFee : membershipFee);
			membershipPaymentStatusReport.setFundBalance(fundBalance);
			membershipPaymentStatusReport.setCreatedBy(user.getName() + " - " + user.getStudentId());
			membershipPaymentStatusReport.setCreatedOn(LocalDateTime.now());
			membershipPaymentStatusReportRepository.save(membershipPaymentStatusReport);

			membershipStatus.setStatus(!membershipStatus.isStatus());
			membershipStatus.setUpdatedBy(user.getName() + " - " + user.getStudentId());
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
					membershipPaymentStatusReportDto
							.setSemester(membershipPaymentStatusReport.getMembershipInfo().getSemester());
					membershipPaymentStatusReportDto.setUserName(membershipPaymentStatusReport.getUser().getName());
					membershipPaymentStatusReportDto
							.setUserStudentId(membershipPaymentStatusReport.getUser().getStudentId());
					membershipPaymentStatusReportDto.setPaymentStatus(membershipPaymentStatusReport.isPaymentStatus());
					membershipPaymentStatusReportDto.setFundChange(membershipPaymentStatusReport.getFundChange());
					membershipPaymentStatusReportDto.setFundBalance(membershipPaymentStatusReport.getFundBalance());
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
