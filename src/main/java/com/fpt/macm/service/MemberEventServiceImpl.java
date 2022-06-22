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

import com.fpt.macm.dto.EventPaymentStatusReportDto;
import com.fpt.macm.dto.MemberEventDto;
import com.fpt.macm.dto.RoleEventDto;
import com.fpt.macm.model.ClubFund;
import com.fpt.macm.model.Constant;
import com.fpt.macm.model.EventPaymentStatusReport;
import com.fpt.macm.model.MemberEvent;
import com.fpt.macm.model.ResponseMessage;
import com.fpt.macm.model.RoleEvent;
import com.fpt.macm.repository.ClubFundRepository;
import com.fpt.macm.repository.EventPaymentStatusReportRepository;
import com.fpt.macm.repository.EventRepository;
import com.fpt.macm.repository.MemberEventRepository;
import com.fpt.macm.utils.Utils;

@Service
public class MemberEventServiceImpl implements MemberEventService {

	@Autowired
	MemberEventRepository memberEventRepository;

	@Autowired
	ClubFundRepository clubFundRepository;

	@Autowired
	EventPaymentStatusReportRepository eventPaymentStatusReportRepository;

	@Autowired
	EventRepository eventRepository;

	@Override
	public ResponseMessage getAllUserOfEventByEventId(int eventId, int pageNo, int pageSize, String sortBy) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
			Page<MemberEvent> pageResponse = memberEventRepository.findAllUserEventByEventId(eventId, paging);
			List<MemberEvent> membersEvent = new ArrayList<MemberEvent>();
			List<MemberEventDto> membersEventDto = new ArrayList<MemberEventDto>();
			if (pageResponse != null && pageResponse.hasContent()) {
				membersEvent = pageResponse.getContent();
			}

			for (MemberEvent memberEvent : membersEvent) {
				MemberEventDto memberEventDto = new MemberEventDto();
				memberEventDto.setId(memberEvent.getId());
				memberEventDto.setUserName(memberEvent.getUser().getName());
				memberEventDto.setUserMail(memberEvent.getUser().getEmail());
				memberEventDto.setUserStudentId(memberEvent.getUser().getStudentId());
				memberEventDto.setAttendanceStatus(memberEvent.getAttendanceStatus());
				RoleEventDto roleEventDto = new RoleEventDto();
				roleEventDto.setId(memberEvent.getRoleEvent().getId());
				roleEventDto.setName(memberEvent.getRoleEvent().getName());
				memberEventDto.setRoleEventDto(roleEventDto);
				Utils.convertNameOfEventRole(memberEvent.getRoleEvent(), memberEventDto.getRoleEventDto());
				memberEventDto.setPaymentStatus(memberEvent.getPaymentStatus());
				membersEventDto.add(memberEventDto);
			}

			responseMessage.setData(membersEventDto);
			responseMessage.setPageNo(pageNo);
			responseMessage.setPageSize(pageSize);
			responseMessage.setMessage(Constant.MSG_058);
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage getAllOrganizingCommitteeOfEventByEventId(int eventId, int pageNo, int pageSize,
			String sortBy) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
			Page<MemberEvent> pageResponse = memberEventRepository.findAllOrganizingCommitteeEventByEventId(eventId,
					paging);
			List<MemberEvent> membersEvent = new ArrayList<MemberEvent>();
			List<MemberEventDto> membersEventDto = new ArrayList<MemberEventDto>();
			if (pageResponse != null && pageResponse.hasContent()) {
				membersEvent = pageResponse.getContent();
			}

			for (MemberEvent memberEvent : membersEvent) {
				MemberEventDto memberEventDto = new MemberEventDto();
				memberEventDto.setId(memberEvent.getId());
				memberEventDto.setUserName(memberEvent.getUser().getName());
				memberEventDto.setUserMail(memberEvent.getUser().getEmail());
				memberEventDto.setUserStudentId(memberEvent.getUser().getStudentId());
				memberEventDto.setAttendanceStatus(memberEvent.getAttendanceStatus());
				RoleEventDto roleEventDto = new RoleEventDto();
				roleEventDto.setId(memberEvent.getRoleEvent().getId());
				roleEventDto.setName(memberEvent.getRoleEvent().getName());
				memberEventDto.setRoleEventDto(roleEventDto);
				Utils.convertNameOfEventRole(memberEvent.getRoleEvent(), memberEventDto.getRoleEventDto());
				memberEventDto.setPaymentStatus(memberEvent.getPaymentStatus());
				membersEventDto.add(memberEventDto);
			}

			responseMessage.setData(membersEventDto);
			responseMessage.setPageNo(pageNo);
			responseMessage.setPageSize(pageSize);
			responseMessage.setMessage(Constant.MSG_059);
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage getAllMemberOfEventByEventId(int eventId, int pageNo, int pageSize, String sortBy) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
			Page<MemberEvent> pageResponse = memberEventRepository.findAllMemberEventByEventId(eventId, paging);
			List<MemberEvent> membersEvent = new ArrayList<MemberEvent>();
			List<MemberEventDto> membersEventDto = new ArrayList<MemberEventDto>();
			if (pageResponse != null && pageResponse.hasContent()) {
				membersEvent = pageResponse.getContent();
			}

			for (MemberEvent memberEvent : membersEvent) {
				MemberEventDto memberEventDto = new MemberEventDto();
				memberEventDto.setId(memberEvent.getId());
				memberEventDto.setUserName(memberEvent.getUser().getName());
				memberEventDto.setUserMail(memberEvent.getUser().getEmail());
				memberEventDto.setUserStudentId(memberEvent.getUser().getStudentId());
				memberEventDto.setAttendanceStatus(memberEvent.getAttendanceStatus());
				RoleEventDto roleEventDto = new RoleEventDto();
				roleEventDto.setId(memberEvent.getRoleEvent().getId());
				roleEventDto.setName(memberEvent.getRoleEvent().getName());
				memberEventDto.setRoleEventDto(roleEventDto);
				Utils.convertNameOfEventRole(memberEvent.getRoleEvent(), memberEventDto.getRoleEventDto());
				memberEventDto.setPaymentStatus(memberEvent.getPaymentStatus());
				membersEventDto.add(memberEventDto);
			}

			responseMessage.setData(membersEventDto);
			responseMessage.setPageNo(pageNo);
			responseMessage.setPageSize(pageSize);
			responseMessage.setMessage(Constant.MSG_060);
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage updateUserRoleInEvent(List<MemberEventDto> membersEventDto) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<MemberEvent> listUpdatedRoleEvent = new ArrayList<MemberEvent>();
			for (MemberEventDto memberEventDto : membersEventDto) {
				Optional<MemberEvent> memberEventOp = memberEventRepository.findById(memberEventDto.getId());
				MemberEvent newMemberEvent = memberEventOp.get();
				if (memberEventDto.getRoleEventDto().getId() != newMemberEvent.getRoleEvent().getId()) {
					RoleEvent roleEvent = new RoleEvent();
					roleEvent.setId(memberEventDto.getRoleEventDto().getId());
					roleEvent.setName(memberEventDto.getRoleEventDto().getName());
					newMemberEvent.setRoleEvent(roleEvent);
					newMemberEvent.setUpdatedBy("toandv");
					newMemberEvent.setUpdatedOn(LocalDateTime.now());
					listUpdatedRoleEvent.add(newMemberEvent);
					memberEventRepository.save(newMemberEvent);
				}
			}
			responseMessage.setData(listUpdatedRoleEvent);
			responseMessage.setMessage(Constant.MSG_061);
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage getAllUserCancelJoinEvent(int eventId, int pageNo, int pageSize, String sortBy) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
			Page<MemberEvent> pageResponse = memberEventRepository.findAllUserCancelJoinEventByEventId(eventId, paging);
			List<MemberEvent> membersEvent = new ArrayList<MemberEvent>();
			List<MemberEventDto> membersEventDto = new ArrayList<MemberEventDto>();
			if (pageResponse != null && pageResponse.hasContent()) {
				membersEvent = pageResponse.getContent();
			}

			for (MemberEvent memberEvent : membersEvent) {
				MemberEventDto memberEventDto = new MemberEventDto();
				memberEventDto.setId(memberEvent.getId());
				memberEventDto.setUserName(memberEvent.getUser().getName());
				memberEventDto.setUserMail(memberEvent.getUser().getEmail());
				memberEventDto.setUserStudentId(memberEvent.getUser().getStudentId());
				memberEventDto.setAttendanceStatus(memberEvent.getAttendanceStatus());
				RoleEventDto roleEventDto = new RoleEventDto();
				roleEventDto.setId(memberEvent.getRoleEvent().getId());
				roleEventDto.setName(memberEvent.getRoleEvent().getName());
				memberEventDto.setRoleEventDto(roleEventDto);
				Utils.convertNameOfEventRole(memberEvent.getRoleEvent(), memberEventDto.getRoleEventDto());
				memberEventDto.setPaymentStatus(memberEvent.getPaymentStatus());
				membersEventDto.add(memberEventDto);
			}

			responseMessage.setData(membersEventDto);
			responseMessage.setPageNo(pageNo);
			responseMessage.setPageSize(pageSize);
			responseMessage.setMessage(Constant.MSG_058);
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage updateMemberEventPaymentStatus(int memberEventId) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<MemberEvent> memberEventOp = memberEventRepository.findById(memberEventId);
			MemberEvent memberEvent = memberEventOp.get();

			List<ClubFund> clubFunds = clubFundRepository.findAll();
			ClubFund clubFund = clubFunds.get(0);
			double fundAmount = clubFund.getFundAmount();

			double eventFee = memberEvent.getEvent().getAmount_per_register();

			clubFund.setFundAmount(fundAmount + eventFee);
			clubFundRepository.save(clubFund);

			EventPaymentStatusReport eventPaymentStatusReport = new EventPaymentStatusReport();
			eventPaymentStatusReport.setEvent(memberEvent.getEvent());
			eventPaymentStatusReport.setUser(memberEvent.getUser());
			eventPaymentStatusReport.setPaymentStatus(!memberEvent.getPaymentStatus());
			eventPaymentStatusReport.setFundChange(memberEvent.getPaymentStatus() ? -eventFee : eventFee);
			eventPaymentStatusReport.setCreatedBy("toandv");
			eventPaymentStatusReport.setCreatedOn(LocalDateTime.now());
			eventPaymentStatusReportRepository.save(eventPaymentStatusReport);

			memberEvent.setPaymentStatus(!memberEvent.getPaymentStatus());
			memberEvent.setUpdatedBy("toandv");
			memberEvent.setUpdatedOn(LocalDateTime.now());
			memberEventRepository.save(memberEvent);
			responseMessage.setData(Arrays.asList(memberEvent));
			responseMessage.setMessage(Constant.MSG_062);
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage getReportPaymentStatusByEventId(int eventId, int pageNo, int pageSize, String sortBy) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
			Page<EventPaymentStatusReport> pageResponse = eventPaymentStatusReportRepository.findByEventId(eventId,
					paging);
			List<EventPaymentStatusReport> eventPaymentStatusReports = new ArrayList<EventPaymentStatusReport>();
			if (pageResponse != null && pageResponse.hasContent()) {
				eventPaymentStatusReports = pageResponse.getContent();
			}

			if (!eventPaymentStatusReports.isEmpty()) {
				List<EventPaymentStatusReportDto> eventPaymentStatusReportsDto = new ArrayList<EventPaymentStatusReportDto>();
				for (EventPaymentStatusReport eventPaymentStatusReport : eventPaymentStatusReports) {
					EventPaymentStatusReportDto eventPaymentStatusReportDto = new EventPaymentStatusReportDto();
					eventPaymentStatusReportDto.setId(eventPaymentStatusReport.getId());
					eventPaymentStatusReportDto.setEventId(eventPaymentStatusReport.getEvent().getId());
					eventPaymentStatusReportDto.setUserName(eventPaymentStatusReport.getUser().getName());
					eventPaymentStatusReportDto.setUserStudentId(eventPaymentStatusReport.getUser().getStudentId());
					eventPaymentStatusReportDto.setPaymentStatus(eventPaymentStatusReport.isPaymentStatus());
					eventPaymentStatusReportDto.setFundChange(eventPaymentStatusReport.getFundChange());
					eventPaymentStatusReportDto.setCreatedBy(eventPaymentStatusReport.getCreatedBy());
					eventPaymentStatusReportDto.setCreatedOn(eventPaymentStatusReport.getCreatedOn());
					eventPaymentStatusReportsDto.add(eventPaymentStatusReportDto);
				}
				responseMessage.setData(eventPaymentStatusReportsDto);
				responseMessage.setMessage(Constant.MSG_079);
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
