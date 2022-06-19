package com.fpt.macm.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpt.macm.dto.MembershipStatusDto;
import com.fpt.macm.model.Constant;
import com.fpt.macm.model.MembershipStatus;
import com.fpt.macm.model.ResponseMessage;
import com.fpt.macm.repository.MembershipStatusRepository;
import com.fpt.macm.utils.Utils;

@Service
public class MembershipServiceImpl implements MembershipService{
	@Autowired
	MembershipStatusRepository membershipStatusRepository;

	@Override
	public ResponseMessage getListMemberPayMembershipBySemester(int membershipInfoId) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<MembershipStatus> listMembershipStatus = membershipStatusRepository.findByMembershipInfoId(membershipInfoId);
			List<MembershipStatusDto> membershipStatusDtos = new ArrayList<MembershipStatusDto>();
			if(listMembershipStatus.size() > 0) {
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
			}else {
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

}
