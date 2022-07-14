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
import com.fpt.macm.dto.MemberNotJoinEventDto;
import com.fpt.macm.dto.RoleEventDto;
import com.fpt.macm.model.ClubFund;
import com.fpt.macm.model.Constant;
import com.fpt.macm.model.Event;
import com.fpt.macm.model.EventPaymentStatusReport;
import com.fpt.macm.model.MemberEvent;
import com.fpt.macm.model.ResponseMessage;
import com.fpt.macm.model.RoleEvent;
import com.fpt.macm.model.User;
import com.fpt.macm.repository.ClubFundRepository;
import com.fpt.macm.repository.EventPaymentStatusReportRepository;
import com.fpt.macm.repository.EventRepository;
import com.fpt.macm.repository.MemberEventRepository;
import com.fpt.macm.repository.RoleEventRepository;
import com.fpt.macm.repository.UserRepository;
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

	@Autowired
	RoleEventRepository roleEventRepository;

	@Autowired
	UserRepository userRepository;

	@Override
	public ResponseMessage updateListMemberEventRole(List<MemberEventDto> membersEventDto) {
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
	public ResponseMessage getAllMemberCancelJoinEvent(int eventId, int pageNo, int pageSize, String sortBy) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
			Page<MemberEvent> pageResponse = memberEventRepository.findAllMemberCancelJoinEventByEventId(eventId,
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
				memberEventDto.setRegisterStatus(memberEvent.isRegisterStatus());
				RoleEventDto roleEventDto = new RoleEventDto();
				roleEventDto.setId(memberEvent.getRoleEvent().getId());
				roleEventDto.setName(memberEvent.getRoleEvent().getName());
				memberEventDto.setRoleEventDto(roleEventDto);
				Utils.convertNameOfEventRole(memberEvent.getRoleEvent(), memberEventDto.getRoleEventDto());
				memberEventDto.setRoleInClub(Utils.convertRoleFromDbToExcel(memberEvent.getUser().getRole()));
				memberEventDto.setPaymentValue(memberEvent.getPaymentValue());
				memberEventDto.setAmountPerRegisterEstimate(memberEvent.getEvent().getAmountPerRegisterEstimated());
				memberEventDto.setAmountPerRegisterActual(memberEvent.getEvent().getAmountPerRegisterActual());
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

			Event event = memberEvent.getEvent();
			double amountPerRegisterEstimate = event.getAmountPerRegisterEstimated();
			double amountPerRegisterActual = event.getAmountPerRegisterActual();

			List<ClubFund> clubFunds = clubFundRepository.findAll();
			ClubFund clubFund = clubFunds.get(0);
			double fundAmount = clubFund.getFundAmount();
			double fundBalance = 0;
			double fundChange = 0;

			if (amountPerRegisterActual == 0) {
				fundChange = memberEvent.getPaymentValue() != 0 ? -amountPerRegisterEstimate
						: amountPerRegisterEstimate;
				fundBalance = memberEvent.getPaymentValue() != 0 ? (fundAmount - amountPerRegisterEstimate)
						: (fundAmount + amountPerRegisterEstimate);
				memberEvent.setPaidBeforeClosing(memberEvent.getPaymentValue() != 0 ? false : true);
				memberEvent.setPaymentValue(memberEvent.getPaymentValue() != 0 ? 0 : amountPerRegisterEstimate);
			} else {
				if (memberEvent.getPaymentValue() == 0) {
					fundChange = amountPerRegisterActual;
					fundBalance = fundAmount + fundChange;
					memberEvent.setPaymentValue(amountPerRegisterActual);
				} else if (amountPerRegisterActual > amountPerRegisterEstimate) {
					if (memberEvent.getPaymentValue() == amountPerRegisterEstimate) {
						fundChange = amountPerRegisterActual - amountPerRegisterEstimate;
						fundBalance = fundAmount + fundChange;
						memberEvent.setPaymentValue(amountPerRegisterActual);
					} else if (memberEvent.getPaymentValue() == amountPerRegisterActual) {
						if (memberEvent.isPaidBeforeClosing()) {
							fundChange = -(amountPerRegisterActual - amountPerRegisterEstimate);
							fundBalance = fundAmount - fundChange;
							memberEvent.setPaymentValue(amountPerRegisterEstimate);
						} else {
							fundChange = -amountPerRegisterActual;
							fundBalance = fundAmount - amountPerRegisterActual;
							memberEvent.setPaymentValue(0);
						}
					}
				} else if (amountPerRegisterActual == amountPerRegisterEstimate) {
					if (memberEvent.getPaymentValue() == amountPerRegisterActual) {
						fundChange = -amountPerRegisterActual;
						fundBalance = fundAmount - amountPerRegisterActual;
						memberEvent.setPaymentValue(0);
					}
				}
			}

			clubFund.setFundAmount(fundBalance);
			clubFundRepository.save(clubFund);

			EventPaymentStatusReport eventPaymentStatusReport = new EventPaymentStatusReport();
			eventPaymentStatusReport.setEvent(memberEvent.getEvent());
			eventPaymentStatusReport.setUser(memberEvent.getUser());
			eventPaymentStatusReport.setPaymentValue(memberEvent.getPaymentValue());
			eventPaymentStatusReport.setFundChange(fundChange);
			eventPaymentStatusReport.setFundBalance(fundBalance);
			eventPaymentStatusReport.setCreatedBy("toandv");
			eventPaymentStatusReport.setCreatedOn(LocalDateTime.now());
			eventPaymentStatusReportRepository.save(eventPaymentStatusReport);

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
					eventPaymentStatusReportDto.setPaymentValue(eventPaymentStatusReport.getPaymentValue());
					eventPaymentStatusReportDto.setFundChange(eventPaymentStatusReport.getFundChange());
					eventPaymentStatusReportDto.setFundBalance(eventPaymentStatusReport.getFundBalance());
					eventPaymentStatusReportDto.setCreatedBy(eventPaymentStatusReport.getCreatedBy());
					eventPaymentStatusReportDto.setCreatedOn(eventPaymentStatusReport.getCreatedOn());
					eventPaymentStatusReportDto.setAmountPerRegisterEstimate(
							eventPaymentStatusReport.getEvent().getAmountPerRegisterEstimated());
					eventPaymentStatusReportDto.setAmountPerRegisterActual(
							eventPaymentStatusReport.getEvent().getAmountPerRegisterActual());
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

	@Override
	public ResponseMessage getAllMemberJoinEventByRoleEventId(int eventId, int filterIndex) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<MemberEvent> membersEvent = memberEventRepository.findByEventIdOrderByIdAsc(eventId);
			List<MemberEvent> membersEventFilter = new ArrayList<MemberEvent>();
			switch (filterIndex) {
			case 0:
				// filter all
				for (MemberEvent memberEvent : membersEvent) {
					if (memberEvent.isRegisterStatus()) {
						membersEventFilter.add(memberEvent);
					}
				}
				break;
			case 1:
				// filter thành viên tham gia
				for (MemberEvent memberEvent : membersEvent) {
					if (memberEvent.isRegisterStatus()
							&& memberEvent.getRoleEvent().getName().equals(Constant.ROLE_EVENT_MEMBER)) {
						membersEventFilter.add(memberEvent);
					}
				}
				break;
			case 2:
				// filter thành viên ban tổ chức
				for (MemberEvent memberEvent : membersEvent) {
					if (memberEvent.isRegisterStatus()
							&& !memberEvent.getRoleEvent().getName().equals(Constant.ROLE_EVENT_MEMBER)) {
						membersEventFilter.add(memberEvent);
					}
				}
				break;
			default:
				for (MemberEvent memberEvent : membersEvent) {
					if (memberEvent.isRegisterStatus()) {
						membersEventFilter.add(memberEvent);
					}
				}
				break;
			}

			List<MemberEventDto> membersEventDto = new ArrayList<MemberEventDto>();

			for (MemberEvent memberEvent : membersEventFilter) {
				MemberEventDto memberEventDto = new MemberEventDto();
				memberEventDto.setId(memberEvent.getId());
				memberEventDto.setUserName(memberEvent.getUser().getName());
				memberEventDto.setUserMail(memberEvent.getUser().getEmail());
				memberEventDto.setUserStudentId(memberEvent.getUser().getStudentId());
				memberEventDto.setRegisterStatus(memberEvent.isRegisterStatus());
				RoleEventDto roleEventDto = new RoleEventDto();
				roleEventDto.setId(memberEvent.getRoleEvent().getId());
				roleEventDto.setName(memberEvent.getRoleEvent().getName());
				memberEventDto.setRoleEventDto(roleEventDto);
				Utils.convertNameOfEventRole(memberEvent.getRoleEvent(), memberEventDto.getRoleEventDto());
				memberEventDto.setRoleInClub(Utils.convertRoleFromDbToExcel(memberEvent.getUser().getRole()));
				memberEventDto.setPaymentValue(memberEvent.getPaymentValue());
				memberEventDto.setAmountPerRegisterEstimate(memberEvent.getEvent().getAmountPerRegisterEstimated());
				memberEventDto.setAmountPerRegisterActual(memberEvent.getEvent().getAmountPerRegisterActual());
				membersEventDto.add(memberEventDto);
			}

			responseMessage.setData(membersEventDto);
			responseMessage.setMessage(Constant.MSG_058);
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage getAllEventRole() {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<RoleEvent> rolesEvent = roleEventRepository.findAll();
			List<RoleEventDto> rolesEventDto = new ArrayList<RoleEventDto>();
			for (RoleEvent roleEvent : rolesEvent) {
				RoleEventDto roleEventDto = new RoleEventDto();
				roleEventDto.setId(roleEvent.getId());
				roleEventDto.setName(roleEvent.getName());
				Utils.convertNameOfEventRole(roleEvent, roleEventDto);
				rolesEventDto.add(roleEventDto);
			}
			responseMessage.setData(rolesEventDto);
			responseMessage.setMessage(Constant.MSG_088);
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage getListMemberEventToUpdateRole(int eventId) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<MemberEvent> membersEvent = memberEventRepository.findByEventIdOrderByIdAsc(eventId);
			List<MemberEventDto> membersEventDto = new ArrayList<MemberEventDto>();
			for (MemberEvent memberEvent : membersEvent) {
				if (memberEvent.isRegisterStatus() && memberEvent.getUser().isActive()) {
					MemberEventDto memberEventDto = new MemberEventDto();
					memberEventDto.setId(memberEvent.getId());
					memberEventDto.setUserName(memberEvent.getUser().getName());
					memberEventDto.setUserMail(memberEvent.getUser().getEmail());
					memberEventDto.setUserStudentId(memberEvent.getUser().getStudentId());
					memberEventDto.setRegisterStatus(memberEvent.isRegisterStatus());
					RoleEventDto roleEventDto = new RoleEventDto();
					roleEventDto.setId(memberEvent.getRoleEvent().getId());
					roleEventDto.setName(memberEvent.getRoleEvent().getName());
					memberEventDto.setRoleEventDto(roleEventDto);
					Utils.convertNameOfEventRole(memberEvent.getRoleEvent(), memberEventDto.getRoleEventDto());
					memberEventDto.setRoleInClub(Utils.convertRoleFromDbToExcel(memberEvent.getUser().getRole()));
					memberEventDto.setPaymentValue(memberEvent.getPaymentValue());
					memberEventDto.setAmountPerRegisterEstimate(memberEvent.getEvent().getAmountPerRegisterEstimated());
					memberEventDto.setAmountPerRegisterActual(memberEvent.getEvent().getAmountPerRegisterActual());
					membersEventDto.add(memberEventDto);
				}
			}
			responseMessage.setData(membersEventDto);
			responseMessage.setMessage(Constant.MSG_089);
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage getListMemberNotJoinEvent(int eventId, int pageNo, int pageSize) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<User> listUser = (List<User>) userRepository.findAll();
			List<MemberNotJoinEventDto> listNotJoin = new ArrayList<MemberNotJoinEventDto>();
			for (User user : listUser) {
				Optional<MemberEvent> getMemberEventOp = memberEventRepository.findMemberEventByEventAndUser(eventId,
						user.getId());
				if (getMemberEventOp.isPresent()) {
					MemberEvent getMemberEvent = getMemberEventOp.get();
					if (getMemberEvent.isRegisterStatus()) {
						continue;
					} else {
						MemberNotJoinEventDto memberNotJoinEventDto = new MemberNotJoinEventDto();
						memberNotJoinEventDto.setUserId(user.getId());
						memberNotJoinEventDto.setUserName(user.getName());
						memberNotJoinEventDto.setUserMail(user.getEmail());
						memberNotJoinEventDto.setUserStudentId(user.getStudentId());
						memberNotJoinEventDto.setRegisteredStatus(true);
						memberNotJoinEventDto.setRoleInClub(Utils.convertRoleFromDbToExcel(user.getRole()));
						listNotJoin.add(memberNotJoinEventDto);
					}
				} else {
					MemberNotJoinEventDto memberNotJoinEventDto = new MemberNotJoinEventDto();
					memberNotJoinEventDto.setUserId(user.getId());
					memberNotJoinEventDto.setUserName(user.getName());
					memberNotJoinEventDto.setUserMail(user.getEmail());
					memberNotJoinEventDto.setUserStudentId(user.getStudentId());
					memberNotJoinEventDto.setRegisteredStatus(false);
					memberNotJoinEventDto.setRoleInClub(Utils.convertRoleFromDbToExcel(user.getRole()));
					listNotJoin.add(memberNotJoinEventDto);
				}
			}
			List<MemberNotJoinEventDto> listNotJoinPageable = pageableMemberNotJoinEvent(listNotJoin, pageNo, pageSize);
			responseMessage.setData(listNotJoinPageable);
			responseMessage.setMessage("Danh sách chưa tham gia sự kiện" + listNotJoinPageable.size());
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage addListMemberJoinEvent(int eventId, List<MemberNotJoinEventDto> listToJoin) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<MemberEvent> listJoinEvent = new ArrayList<MemberEvent>();
			for (MemberNotJoinEventDto memberNotJoinEventDto : listToJoin) {
				if (memberNotJoinEventDto.isRegisteredStatus()) {
					MemberEvent memberEvent = memberEventRepository
							.findMemberEventByEventAndUser(eventId, memberNotJoinEventDto.getUserId()).get();
					Optional<RoleEvent> roleEventOp = roleEventRepository.findMemberRole();
					RoleEvent roleEvent = roleEventOp.get();
					memberEvent.setRoleEvent(roleEvent);
					memberEvent.setPaymentValue(0);
					memberEvent.setRegisterStatus(true);
					memberEvent.setPaidBeforeClosing(false);
					memberEvent.setCreatedBy("LinhLHN");
					memberEvent.setCreatedOn(LocalDateTime.now());
					memberEventRepository.save(memberEvent);
					listJoinEvent.add(memberEvent);
				} else {
					MemberEvent memberEvent = new MemberEvent();
					memberEvent.setEvent(eventRepository.findById(eventId).get());
					memberEvent.setUser(userRepository.findById(memberNotJoinEventDto.getUserId()).get());
					Optional<RoleEvent> roleEventOp = roleEventRepository.findMemberRole();
					RoleEvent roleEvent = roleEventOp.get();
					memberEvent.setRoleEvent(roleEvent);
					memberEvent.setPaymentValue(0);
					memberEvent.setRegisterStatus(true);
					memberEvent.setPaidBeforeClosing(false);
					memberEvent.setCreatedBy("LinhLHN");
					memberEvent.setCreatedOn(LocalDateTime.now());
					memberEventRepository.save(memberEvent);
					listJoinEvent.add(memberEvent);
				}
			}
			if (listJoinEvent.size() == 0) {
				responseMessage.setMessage("Không có thành viên nào được thêm");
			} else {
				responseMessage.setData(listJoinEvent);
				responseMessage.setMessage("Thêm thành viên thành công");
			}
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	public List<MemberNotJoinEventDto> pageableMemberNotJoinEvent(List<MemberNotJoinEventDto> currentList, int pageNo,
			int pageSize) {
		List<MemberNotJoinEventDto> result = new ArrayList<MemberNotJoinEventDto>();
		for (int i = pageNo * pageSize; i < (pageNo + 1) * pageSize && i < currentList.size(); i++) {
			result.add(currentList.get(i));
		}
		return result;
	}

	@Override
	public ResponseMessage registerToJoinEvent(int eventId, String studentId) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			User user = userRepository.findByStudentId(studentId).get();
			Event event = eventRepository.findById(eventId).get();
			List<MemberEvent> membersHasRegisteredToEvent = memberEventRepository.findByEventIdOrderByIdAsc(eventId);

			boolean isRegistered = false;

			for (MemberEvent memberHasRegisteredToEvent : membersHasRegisteredToEvent) {
				if (memberHasRegisteredToEvent.getUser().getId() == user.getId()) {
					isRegistered = true;
					if (memberHasRegisteredToEvent.isRegisterStatus()) {
						responseMessage.setMessage("Bạn đã đăng ký tham gia sự kiện này rồi");
					} else {
						Optional<RoleEvent> roleEventOp = roleEventRepository.findMemberRole();
						RoleEvent roleEvent = roleEventOp.get();
						memberHasRegisteredToEvent.setRoleEvent(roleEvent);
						memberHasRegisteredToEvent.setRegisterStatus(true);
						memberHasRegisteredToEvent.setUpdatedBy(user.getName() + " - " + user.getStudentId());
						memberHasRegisteredToEvent.setUpdatedOn(LocalDateTime.now());
						memberEventRepository.save(memberHasRegisteredToEvent);
						responseMessage.setData(Arrays.asList(memberHasRegisteredToEvent));
						responseMessage.setMessage("Đăng ký tham gia sự kiện thành công");
					}
					break;
				}
			}

			if (!isRegistered) {
				MemberEvent memberEvent = new MemberEvent();
				memberEvent.setEvent(event);
				memberEvent.setUser(user);
				memberEvent.setRegisterStatus(true);
				memberEvent.setPaymentValue(0);
				memberEvent.setPaidBeforeClosing(false);
				Optional<RoleEvent> roleEventOp = roleEventRepository.findMemberRole();
				RoleEvent roleEvent = roleEventOp.get();
				memberEvent.setRoleEvent(roleEvent);
				memberEvent.setCreatedBy(user.getName() + " - " + user.getStudentId());
				memberEvent.setCreatedOn(LocalDateTime.now());
				memberEventRepository.save(memberEvent);
				responseMessage.setData(Arrays.asList(memberEvent));
				responseMessage.setMessage("Đăng ký tham gia sự kiện thành công");
			}
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage registerToJoinOrganizingCommittee(int eventId, String studentId, int roleEventId) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			RoleEvent roleEvent = roleEventRepository.findById(roleEventId).get();

			if (!roleEvent.getName().equals(Constant.ROLE_EVENT_MEMBER)) {
				User user = userRepository.findByStudentId(studentId).get();
				Event event = eventRepository.findById(eventId).get();
				List<MemberEvent> membersHasRegisteredToEvent = memberEventRepository
						.findByEventIdOrderByIdAsc(eventId);
				int countOrganizingCommittee = 0;
				boolean isRegistered = false;
				for (MemberEvent memberHasRegisteredToEvent : membersHasRegisteredToEvent) {
					if (memberHasRegisteredToEvent.getUser().getId() == user.getId()
							&& memberHasRegisteredToEvent.isRegisterStatus()) {
						isRegistered = true;
						responseMessage.setMessage("Bạn đã đăng ký tham gia sự kiện này rồi");
						break;
					} else if (!memberHasRegisteredToEvent.getRoleEvent().getName()
							.equals(Constant.ROLE_EVENT_MEMBER)) {
						countOrganizingCommittee++;
					}
				}

				if (!isRegistered) {
					if (countOrganizingCommittee < event.getMaxQuantityComitee()) {
						MemberEvent memberEvent = new MemberEvent();
						memberEvent.setEvent(event);
						memberEvent.setUser(user);
						memberEvent.setRegisterStatus(true);
						memberEvent.setPaymentValue(0);
						memberEvent.setPaidBeforeClosing(false);
						memberEvent.setRoleEvent(roleEvent);
						memberEvent.setCreatedBy(user.getName() + " - " + user.getStudentId());
						memberEvent.setCreatedOn(LocalDateTime.now());
						memberEventRepository.save(memberEvent);
						responseMessage.setData(Arrays.asList(memberEvent));
						responseMessage.setMessage("Đăng ký tham gia ban tổ chức sự kiện thành công");
					} else {
						responseMessage.setMessage("Sự kiện này đã đủ số lượng ban tổ chức");
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage cancelToJoinEvent(int eventId, String studentId) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			User user = userRepository.findByStudentId(studentId).get();
			Optional<MemberEvent> memberEventOp = memberEventRepository.findMemberEventByEventAndUser(eventId,
					user.getId());
			if (memberEventOp.isPresent()) {
				MemberEvent memberEvent = memberEventOp.get();
				if (!memberEvent.getRoleEvent().getName().equals(Constant.ROLE_EVENT_MEMBER)) {
					responseMessage.setMessage("Thành viên ban tổ chức không thể hủy tham gia");
				} else {
					memberEvent.setRegisterStatus(false);
					memberEvent.setUpdatedBy(user.getName() + " - " + user.getStudentId());
					memberEvent.setUpdatedOn(LocalDateTime.now());
					memberEventRepository.save(memberEvent);
					responseMessage.setData(Arrays.asList(memberEvent));
					responseMessage.setMessage("Hủy đăng ký tham gia sự kiện thành công");
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}
}
