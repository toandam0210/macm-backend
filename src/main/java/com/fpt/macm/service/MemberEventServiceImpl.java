package com.fpt.macm.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.fpt.macm.constant.Constant;
import com.fpt.macm.model.dto.EventPaymentStatusReportDto;
import com.fpt.macm.model.dto.MemberEventDto;
import com.fpt.macm.model.dto.MemberNotJoinEventDto;
import com.fpt.macm.model.dto.RoleEventDto;
import com.fpt.macm.model.dto.UserEventDto;
import com.fpt.macm.model.entity.AttendanceEvent;
import com.fpt.macm.model.entity.ClubFund;
import com.fpt.macm.model.entity.Event;
import com.fpt.macm.model.entity.EventPaymentStatusReport;
import com.fpt.macm.model.entity.EventRole;
import com.fpt.macm.model.entity.MemberEvent;
import com.fpt.macm.model.entity.RoleEvent;
import com.fpt.macm.model.entity.User;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.repository.AttendanceEventRepository;
import com.fpt.macm.repository.ClubFundRepository;
import com.fpt.macm.repository.EventPaymentStatusReportRepository;
import com.fpt.macm.repository.EventRepository;
import com.fpt.macm.repository.EventRoleRepository;
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

	@Autowired
	AttendanceEventRepository attendanceEventRepository;

	@Autowired
	EventRoleRepository eventRoleRepository;

	@Autowired
	ClubFundService clubFundService;

	@Override
	public ResponseMessage updateListMemberEventRole(List<MemberEventDto> membersEventDto) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<MemberEvent> listUpdatedRoleEvent = new ArrayList<MemberEvent>();
			for (MemberEventDto memberEventDto : membersEventDto) {
				Optional<MemberEvent> memberEventOp = memberEventRepository.findById(memberEventDto.getId());
				MemberEvent memberEvent = memberEventOp.get();
				if (memberEventDto.getRoleEventDto().getId() != memberEvent.getRoleEvent().getId()) {
					RoleEvent roleEvent = new RoleEvent();
					roleEvent.setId(memberEventDto.getRoleEventDto().getId());
					roleEvent.setName(memberEventDto.getRoleEventDto().getName());
					memberEvent.setRoleEvent(roleEvent);
					memberEvent.setUpdatedBy("toandv");
					memberEvent.setUpdatedOn(LocalDateTime.now());
					listUpdatedRoleEvent.add(memberEvent);
				}
			}
			if (!listUpdatedRoleEvent.isEmpty()) {
				memberEventRepository.saveAll(listUpdatedRoleEvent);
				responseMessage.setData(listUpdatedRoleEvent);
				responseMessage.setMessage(Constant.MSG_061);
			} else {
				responseMessage.setMessage("Không có trường nào thay đổi");
			}
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage getAllMemberCancelJoinEvent(int eventId) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<MemberEvent> membersEvent = memberEventRepository.findByEventIdAndRegisterStatus(eventId, false);
			List<MemberEventDto> membersEventDto = new ArrayList<MemberEventDto>();
			if (!membersEvent.isEmpty()) {
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
					memberEventDto.setRoleInClub(Utils.convertRoleFromDbToExcel(memberEvent.getUser().getRole()));
					memberEventDto.setPaymentValue(memberEvent.getPaymentValue());
					memberEventDto.setAmountPerRegisterEstimate(memberEvent.getEvent().getAmountPerRegisterEstimated());
					memberEventDto.setAmountPerRegisterActual(memberEvent.getEvent().getAmountPerRegisterActual());
					membersEventDto.add(memberEventDto);
				}
				Collections.sort(membersEventDto);
				responseMessage.setData(membersEventDto);
				responseMessage.setMessage(Constant.MSG_058);
			} else {
				responseMessage.setMessage("Không có thành viên nào hủy đăng ký tham gia");
			}
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage updateMemberEventPaymentStatus(int memberEventId) {
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

			if (fundChange > 0) {
				clubFundService.depositToClubFund(fundChange,
						"Cập nhật trạng thái đóng phí tham gia sự kiện " + event.getName() + " của "
								+ memberEvent.getUser().getName() + " - " + memberEvent.getUser().getStudentId()
								+ " thành đã đóng");
			} else if (fundChange < 0) {
				clubFundService.withdrawFromClubFund(-fundChange,
						"Cập nhật trạng thái đóng phí tham gia sự kiện " + event.getName() + " của "
								+ memberEvent.getUser().getName() + " - " + memberEvent.getUser().getStudentId()
								+ " thành chưa đóng");
			}

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
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage getReportPaymentStatusByEventId(int eventId) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<EventPaymentStatusReport> eventPaymentStatusReports = eventPaymentStatusReportRepository
					.findByEventId(eventId);
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
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage getAllMemberJoinEventByRoleEventId(int eventId, int filterIndex) {
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
					if (memberEvent.isRegisterStatus() && memberEvent.getRoleEvent().getId() == 1) {
						membersEventFilter.add(memberEvent);
					}
				}
				break;
			case 2:
				// filter thành viên ban tổ chức
				for (MemberEvent memberEvent : membersEvent) {
					if (memberEvent.isRegisterStatus() && memberEvent.getRoleEvent().getId() != 1) {
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

			if (!membersEventFilter.isEmpty()) {
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
					memberEventDto.setRoleInClub(Utils.convertRoleFromDbToExcel(memberEvent.getUser().getRole()));
					memberEventDto.setPaymentValue(memberEvent.getPaymentValue());
					memberEventDto.setAmountPerRegisterEstimate(memberEvent.getEvent().getAmountPerRegisterEstimated());
					memberEventDto.setAmountPerRegisterActual(memberEvent.getEvent().getAmountPerRegisterActual());
					membersEventDto.add(memberEventDto);
				}
				responseMessage.setData(membersEventDto);
				responseMessage.setMessage(Constant.MSG_058);
			} else {
				responseMessage.setMessage("Không có thành viên nào");
			}
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage getAllRoleByEventId(int eventId) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<EventRole> eventRoles = eventRoleRepository.findByEventId(eventId);
			if (!eventRoles.isEmpty()) {
				List<RoleEventDto> rolesEventDto = new ArrayList<RoleEventDto>();
				for (EventRole eventRole : eventRoles) {
					RoleEventDto roleEventDto = new RoleEventDto();
					roleEventDto.setId(eventRole.getRoleEvent().getId());
					roleEventDto.setName(eventRole.getRoleEvent().getName());
					rolesEventDto.add(roleEventDto);
				}
				responseMessage.setData(rolesEventDto);
				responseMessage.setMessage(Constant.MSG_088);
			} else {
				responseMessage.setMessage("Sự kiện này chưa có vai trò nào");
			}
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage getAllSuggestionRole() {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<RoleEvent> rolesEvent = roleEventRepository.findAll(Sort.by("id").ascending());
			if (!rolesEvent.isEmpty()) {
				rolesEvent.remove(0);
				responseMessage.setData(rolesEvent);
				responseMessage.setMessage("Lấy tất cả vai trò gợi ý thành công");
			} else {
				responseMessage.setMessage("Không có vai trò gợi ý nào");
			}
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage getAllOrganizingCommitteeRoleByEventId(int eventId) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<EventRole> eventRoles = eventRoleRepository.findByEventId(eventId);
			if (!eventRoles.isEmpty()) {
				List<RoleEventDto> rolesEventDto = new ArrayList<RoleEventDto>();
				for (EventRole eventRole : eventRoles) {
					if (eventRole.getRoleEvent().getId() != 1) {
						RoleEventDto roleEventDto = new RoleEventDto();
						roleEventDto.setId(eventRole.getRoleEvent().getId());
						roleEventDto.setName(eventRole.getRoleEvent().getName());
						roleEventDto.setAvailableQuantity(getAvailableQuantity(eventId, eventRole));
						roleEventDto.setMaxQuantity(eventRole.getQuantity());
						rolesEventDto.add(roleEventDto);
					}
				}
				responseMessage.setData(rolesEventDto);
				responseMessage.setMessage(Constant.MSG_088);
			} else {
				responseMessage.setMessage("Sự kiện này chưa có vai trò ban tổ chức nào");
			}
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	private int getAvailableQuantity(int eventId, EventRole eventRole) {
		List<MemberEvent> organizingCommittees = memberEventRepository.findOrganizingCommitteeByEventId(eventId,
				eventRole.getRoleEvent().getId());
		if (!organizingCommittees.isEmpty()) {
			if (eventRole.getQuantity() - organizingCommittees.size() < 0) {
				return 0;
			} else {
				return eventRole.getQuantity() - organizingCommittees.size();
			}
		} else {
			return eventRole.getQuantity();
		}
	}

	@Override
	public ResponseMessage getListMemberEventToUpdateRole(int eventId) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<MemberEvent> membersEvent = memberEventRepository.findByEventIdOrderByIdAsc(eventId);
			if (!membersEvent.isEmpty()) {
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
						memberEventDto.setRoleInClub(Utils.convertRoleFromDbToExcel(memberEvent.getUser().getRole()));
						memberEventDto.setPaymentValue(memberEvent.getPaymentValue());
						memberEventDto
								.setAmountPerRegisterEstimate(memberEvent.getEvent().getAmountPerRegisterEstimated());
						memberEventDto.setAmountPerRegisterActual(memberEvent.getEvent().getAmountPerRegisterActual());
						membersEventDto.add(memberEventDto);
					}
				}
				responseMessage.setData(membersEventDto);
				responseMessage.setMessage(Constant.MSG_089);
			} else {
				responseMessage.setMessage("Không có thành viên nào");
			}
		} catch (Exception e) {
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
			Event event = eventRepository.findById(eventId).get();
			List<MemberEvent> listJoinEvent = new ArrayList<MemberEvent>();
			List<AttendanceEvent> attendancesEvent = new ArrayList<AttendanceEvent>();
			for (MemberNotJoinEventDto memberNotJoinEventDto : listToJoin) {
				User user = userRepository.findById(memberNotJoinEventDto.getUserId()).get();
				MemberEvent memberEvent = new MemberEvent();
				if (memberNotJoinEventDto.isRegisteredStatus()) {
					memberEvent = memberEventRepository
							.findMemberEventByEventAndUser(eventId, memberNotJoinEventDto.getUserId()).get();
					memberEvent.setUpdatedBy("LinhLHN");
					memberEvent.setUpdatedOn(LocalDateTime.now());
				} else {
					memberEvent.setEvent(event);
					memberEvent.setUser(user);
					memberEvent.setPaymentValue(0);
					memberEvent.setPaidBeforeClosing(false);
					memberEvent.setCreatedBy("LinhLHN");
					memberEvent.setCreatedOn(LocalDateTime.now());
				}
				memberEvent.setRegisterStatus(true);
				Optional<RoleEvent> roleEventOp = roleEventRepository.findById(1);
				RoleEvent roleEvent = roleEventOp.get();
				memberEvent.setRoleEvent(roleEvent);
				listJoinEvent.add(memberEvent);

				AttendanceEvent attendanceEvent = new AttendanceEvent();
				attendanceEvent.setEvent(event);
				attendanceEvent.setStatus(2);
				attendanceEvent.setUser(user);
				attendanceEvent.setCreatedBy("toandv");
				attendanceEvent.setCreatedOn(LocalDateTime.now());
				attendancesEvent.add(attendanceEvent);
			}
			if (listJoinEvent.size() == 0) {
				responseMessage.setMessage("Không có thành viên nào được thêm");
			} else {
				memberEventRepository.saveAll(listJoinEvent);
				attendanceEventRepository.saveAll(attendancesEvent);
				List<MemberEventDto> membersEventDto = new ArrayList<MemberEventDto>();
				for (MemberEvent memberEvent : listJoinEvent) {
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
					memberEventDto.setRoleInClub(Utils.convertRoleFromDbToExcel(memberEvent.getUser().getRole()));
					memberEventDto.setPaymentValue(memberEvent.getPaymentValue());
					memberEventDto.setAmountPerRegisterEstimate(memberEvent.getEvent().getAmountPerRegisterEstimated());
					memberEventDto.setAmountPerRegisterActual(memberEvent.getEvent().getAmountPerRegisterActual());
					membersEventDto.add(memberEventDto);
				}
				responseMessage.setData(membersEventDto);
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
			Event event = eventRepository.findById(eventId).get();

			if (LocalDateTime.now().isBefore(event.getRegistrationMemberDeadline())) {
				User user = userRepository.findByStudentId(studentId).get();

				MemberEvent memberEvent = new MemberEvent();
				Optional<MemberEvent> memberEventOp = memberEventRepository.findMemberEventByEventAndUser(event.getId(),
						user.getId());
				if (memberEventOp.isPresent()) {
					memberEvent = memberEventOp.get();
					if (memberEvent.isRegisterStatus()) {
						responseMessage.setMessage("Bạn đã đăng ký tham gia sự kiện này rồi");
						return responseMessage;
					} else {
						memberEvent.setRegisterStatus(true);
						memberEvent.setUpdatedOn(LocalDateTime.now());
						memberEvent.setUpdatedBy(user.getName() + " - " + user.getStudentId());
					}
				} else {
					memberEvent.setEvent(event);
					memberEvent.setUser(user);
					memberEvent.setRegisterStatus(true);
					memberEvent.setPaymentValue(0);
					memberEvent.setPaidBeforeClosing(false);
					Optional<RoleEvent> roleEventOp = roleEventRepository.findById(1);
					RoleEvent roleEvent = roleEventOp.get();
					memberEvent.setRoleEvent(roleEvent);
					memberEvent.setCreatedBy(user.getName() + " - " + user.getStudentId());
					memberEvent.setCreatedOn(LocalDateTime.now());
				}

				memberEventRepository.save(memberEvent);

				AttendanceEvent attendanceEvent = new AttendanceEvent();
				attendanceEvent.setEvent(event);
				attendanceEvent.setStatus(2);
				attendanceEvent.setUser(user);
				attendanceEvent.setCreatedBy(user.getName() + " - " + user.getStudentId());
				attendanceEvent.setCreatedOn(LocalDateTime.now());
				attendanceEventRepository.save(attendanceEvent);

				UserEventDto userEventDto = new UserEventDto();
				userEventDto.setEventId(memberEvent.getEvent().getId());
				userEventDto.setEventName(memberEvent.getEvent().getName());
				userEventDto.setUserName(user.getName());
				userEventDto.setUserStudentId(user.getStudentId());
				RoleEventDto roleEventDto = new RoleEventDto();
				roleEventDto.setId(memberEvent.getRoleEvent().getId());
				roleEventDto.setName(memberEvent.getRoleEvent().getName());
				userEventDto.setRoleEventDto(roleEventDto);

				responseMessage.setData(Arrays.asList(userEventDto));
				responseMessage.setMessage("Đăng ký tham gia sự kiện thành công");
			} else {
				responseMessage.setMessage(Constant.MSG_131);
			}
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage registerToJoinOrganizingCommittee(int eventId, String studentId, int roleEventId) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Event event = eventRepository.findById(eventId).get();
			if (LocalDateTime.now().isBefore(event.getRegistrationOrganizingCommitteeDeadline())) {
				RoleEvent roleEvent = roleEventRepository.findById(roleEventId).get();
				EventRole eventRole = eventRoleRepository.findByRoleEventIdAndEventId(roleEvent.getId(), event.getId())
						.get();
				if (roleEvent.getId() != 1) {
					if (getAvailableQuantity(event.getId(), eventRole) > 0) {
						User user = userRepository.findByStudentId(studentId).get();

						MemberEvent memberEvent = new MemberEvent();

						Optional<MemberEvent> memberEventOp = memberEventRepository
								.findMemberEventByEventAndUser(event.getId(), user.getId());
						if (memberEventOp.isPresent()) {
							memberEvent = memberEventOp.get();
							if (memberEvent.isRegisterStatus()) {
								responseMessage.setMessage("Bạn đã đăng ký tham gia sự kiện này rồi");
								return responseMessage;
							} else {
								memberEvent.setUpdatedBy(user.getName() + " - " + user.getStudentId());
								memberEvent.setUpdatedOn(LocalDateTime.now());
							}
						} else {
							memberEvent.setEvent(event);
							memberEvent.setUser(user);
							memberEvent.setPaymentValue(0);
							memberEvent.setPaidBeforeClosing(false);
							memberEvent.setCreatedBy(user.getName() + " - " + user.getStudentId());
							memberEvent.setCreatedOn(LocalDateTime.now());
						}

						memberEvent.setRegisterStatus(true);
						memberEvent.setRoleEvent(roleEvent);
						memberEventRepository.save(memberEvent);

						AttendanceEvent attendanceEvent = new AttendanceEvent();
						attendanceEvent.setEvent(event);
						attendanceEvent.setStatus(2);
						attendanceEvent.setUser(user);
						attendanceEvent.setCreatedBy(user.getName() + " - " + user.getStudentId());
						attendanceEvent.setCreatedOn(LocalDateTime.now());
						attendanceEventRepository.save(attendanceEvent);

						UserEventDto userEventDto = new UserEventDto();
						userEventDto.setEventId(memberEvent.getEvent().getId());
						userEventDto.setEventName(memberEvent.getEvent().getName());
						userEventDto.setUserName(user.getName());
						userEventDto.setUserStudentId(user.getStudentId());
						RoleEventDto roleEventDto = new RoleEventDto();
						roleEventDto.setId(memberEvent.getRoleEvent().getId());
						roleEventDto.setName(memberEvent.getRoleEvent().getName());
						userEventDto.setRoleEventDto(roleEventDto);

						responseMessage.setData(Arrays.asList(userEventDto));
						responseMessage.setMessage("Đăng ký tham gia ban tổ chức sự kiện thành công");
					} else {
						responseMessage.setMessage("Sự kiện này đã đủ số lượng ban tổ chức");
					}
				} else {
					responseMessage.setMessage("Vai trò không hợp lệ");
				}
			} else {
				responseMessage.setMessage(Constant.MSG_131);
			}
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage cancelToJoinEvent(int eventId, String studentId) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Event event = eventRepository.findById(eventId).get();
			if (LocalDateTime.now().isBefore(event.getRegistrationMemberDeadline())) {
				User user = userRepository.findByStudentId(studentId).get();
				Optional<MemberEvent> memberEventOp = memberEventRepository.findMemberEventByEventAndUser(eventId,
						user.getId());
				if (memberEventOp.isPresent()) {
					MemberEvent memberEvent = memberEventOp.get();
					if (memberEvent.getRoleEvent().getId() != 1) {
						responseMessage.setMessage("Thành viên ban tổ chức không thể hủy tham gia");
					} else {
						memberEvent.setRegisterStatus(false);
						memberEvent.setUpdatedBy(user.getName() + " - " + user.getStudentId());
						memberEvent.setUpdatedOn(LocalDateTime.now());
						memberEventRepository.save(memberEvent);

						Optional<AttendanceEvent> attendanceEventOp = attendanceEventRepository
								.findByEventIdAndUserId(event.getId(), user.getId());
						if (attendanceEventOp.isPresent()) {
							AttendanceEvent attendanceEvent = attendanceEventOp.get();
							attendanceEventRepository.delete(attendanceEvent);
						}

						responseMessage.setData(Arrays.asList(memberEvent));
						responseMessage.setMessage("Hủy đăng ký tham gia sự kiện thành công");
					}
				}
			} else {
				responseMessage.setMessage("Đã hết hạn hủy đăng ký");
			}
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage getAllEventByStudentId(String studentId) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			User user = userRepository.findByStudentId(studentId).get();
			List<MemberEvent> membersEvent = memberEventRepository.findByUserId(user.getId());
			if (!membersEvent.isEmpty()) {
				List<UserEventDto> userEventsDto = new ArrayList<UserEventDto>();
				for (MemberEvent memberEvent : membersEvent) {
					if (memberEvent.isRegisterStatus()) {
						UserEventDto userEventDto = new UserEventDto();
						userEventDto.setEventId(memberEvent.getEvent().getId());
						userEventDto.setEventName(memberEvent.getEvent().getName());
						userEventDto.setUserName(user.getName());
						userEventDto.setUserStudentId(user.getStudentId());
						RoleEventDto roleEventDto = new RoleEventDto();
						roleEventDto.setId(memberEvent.getRoleEvent().getId());
						roleEventDto.setName(memberEvent.getRoleEvent().getName());
						userEventDto.setRoleEventDto(roleEventDto);
						userEventsDto.add(userEventDto);
					}
				}
				responseMessage.setData(userEventsDto);
				responseMessage.setMessage("Lấy danh sách sự kiện đã tham gia của " + user.getName() + " - "
						+ user.getStudentId() + " thành công");
			}

		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}
}
