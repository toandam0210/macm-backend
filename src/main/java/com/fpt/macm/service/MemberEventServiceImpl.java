package com.fpt.macm.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.fpt.macm.constant.Constant;
import com.fpt.macm.model.dto.EventPaymentStatusReportDto;
import com.fpt.macm.model.dto.EventRoleDto;
import com.fpt.macm.model.dto.MemberEventDto;
import com.fpt.macm.model.dto.UserEventDto;
import com.fpt.macm.model.entity.AttendanceEvent;
import com.fpt.macm.model.entity.ClubFund;
import com.fpt.macm.model.entity.Event;
import com.fpt.macm.model.entity.EventPaymentStatusReport;
import com.fpt.macm.model.entity.EventRole;
import com.fpt.macm.model.entity.MemberEvent;
import com.fpt.macm.model.entity.Notification;
import com.fpt.macm.model.entity.User;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.repository.AttendanceEventRepository;
import com.fpt.macm.repository.ClubFundRepository;
import com.fpt.macm.repository.EventPaymentStatusReportRepository;
import com.fpt.macm.repository.EventRepository;
import com.fpt.macm.repository.EventRoleRepository;
import com.fpt.macm.repository.MemberEventRepository;
import com.fpt.macm.repository.NotificationRepository;
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

	@Autowired
	NotificationRepository notificationRepository;

	@Autowired
	NotificationService notificationService;

	@Override
	public ResponseMessage updateListMemberEventRole(List<MemberEventDto> membersEventDto) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<MemberEvent> listUpdatedRoleEvent = new ArrayList<MemberEvent>();
			for (MemberEventDto memberEventDto : membersEventDto) {
				Optional<MemberEvent> memberEventOp = memberEventRepository.findById(memberEventDto.getId());
				MemberEvent memberEvent = memberEventOp.get();
				if (memberEventDto.getEventRoleDto().getId() != memberEvent.getEventRole().getId()) {
					EventRole eventRole = new EventRole();
					eventRole.setId(memberEventDto.getEventRoleDto().getId());
					eventRole.setName(memberEventDto.getEventRoleDto().getName());
					eventRole.setQuantity(memberEventDto.getEventRoleDto().getMaxQuantity());
					eventRole.setEvent(memberEvent.getEvent());
					memberEvent.setEventRole(eventRole);
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
			List<MemberEvent> membersEvent = memberEventRepository.findByEventIdAndRegisterStatus(eventId,
					Constant.REQUEST_STATUS_DECLINED);
			List<MemberEventDto> membersEventDto = new ArrayList<MemberEventDto>();
			if (!membersEvent.isEmpty()) {
				for (MemberEvent memberEvent : membersEvent) {
					MemberEventDto memberEventDto = new MemberEventDto();
					memberEventDto.setId(memberEvent.getId());
					memberEventDto.setUserName(memberEvent.getUser().getName());
					memberEventDto.setUserMail(memberEvent.getUser().getEmail());
					memberEventDto.setUserStudentId(memberEvent.getUser().getStudentId());
					memberEventDto.setRegisterStatus(memberEvent.getRegisterStatus());
					EventRoleDto eventRoleDto = new EventRoleDto();
					eventRoleDto.setId(memberEvent.getEventRole().getId());
					eventRoleDto.setName(memberEvent.getEventRole().getName());
					memberEventDto.setEventRoleDto(eventRoleDto);
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
	public ResponseMessage updateMemberEventPaymentStatus(String studentId, int memberEventId) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			User user = userRepository.findByStudentId(studentId).get();

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
				clubFundService.depositToClubFund(user.getStudentId(), fundChange,
						"Cập nhật trạng thái đóng phí tham gia sự kiện " + event.getName() + " của "
								+ memberEvent.getUser().getName() + " - " + memberEvent.getUser().getStudentId()
								+ " thành đã đóng");
			} else if (fundChange < 0) {
				clubFundService.withdrawFromClubFund(user.getStudentId(), -fundChange,
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
			eventPaymentStatusReport.setCreatedBy(user.getName() + " - " + user.getStudentId());
			eventPaymentStatusReport.setCreatedOn(LocalDateTime.now());
			eventPaymentStatusReportRepository.save(eventPaymentStatusReport);

			memberEvent.setUpdatedBy(user.getName() + " - " + user.getStudentId());
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
					if (memberEvent.getRegisterStatus().equals(Constant.REQUEST_STATUS_APPROVED)) {
						membersEventFilter.add(memberEvent);
					}
				}
				break;
			case 1:
				// filter thành viên tham gia
				for (MemberEvent memberEvent : membersEvent) {
					if (memberEvent.getRegisterStatus().equals(Constant.REQUEST_STATUS_APPROVED)
							&& memberEvent.getEventRole().getName().equals(Constant.ROLE_EVENT_MEMBER_VN)) {
						membersEventFilter.add(memberEvent);
					}
				}
				break;
			case 2:
				// filter thành viên ban tổ chức
				for (MemberEvent memberEvent : membersEvent) {
					if (memberEvent.getRegisterStatus().equals(Constant.REQUEST_STATUS_APPROVED)
							&& !memberEvent.getEventRole().getName().equals(Constant.ROLE_EVENT_MEMBER_VN)) {
						membersEventFilter.add(memberEvent);
					}
				}
				break;
			default:
				for (MemberEvent memberEvent : membersEvent) {
					if (memberEvent.getRegisterStatus().equals(Constant.REQUEST_STATUS_APPROVED)) {
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
					memberEventDto.setRegisterStatus(memberEvent.getRegisterStatus());
					EventRoleDto eventRoleDto = new EventRoleDto();
					eventRoleDto.setId(memberEvent.getEventRole().getId());
					eventRoleDto.setName(memberEvent.getEventRole().getName());
					memberEventDto.setEventRoleDto(eventRoleDto);
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
				List<EventRoleDto> rolesEventDto = new ArrayList<EventRoleDto>();
				for (EventRole eventRole : eventRoles) {
					EventRoleDto roleEventDto = new EventRoleDto();
					roleEventDto.setId(eventRole.getId());
					roleEventDto.setName(eventRole.getName());
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
	public ResponseMessage getAllOrganizingCommitteeRoleByEventId(int eventId) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<EventRole> eventRoles = eventRoleRepository.findByEventId(eventId);
			if (!eventRoles.isEmpty()) {
				List<EventRoleDto> rolesEventDto = new ArrayList<EventRoleDto>();
				for (EventRole eventRole : eventRoles) {
					if (!eventRole.getName().equals(Constant.ROLE_EVENT_MEMBER_VN)) {
						EventRoleDto roleEventDto = new EventRoleDto();
						roleEventDto.setId(eventRole.getId());
						roleEventDto.setName(eventRole.getName());
						roleEventDto.setAvailableQuantity(getAvailableQuantity(eventRole));
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

	private int getAvailableQuantity(EventRole eventRole) {
		List<MemberEvent> organizingCommittees = memberEventRepository
				.findByEventIdOrderByIdAsc(eventRole.getEvent().getId());
		if (!organizingCommittees.isEmpty()) {
			int count = 0;
			for (MemberEvent memberEvent : organizingCommittees) {
				if (!memberEvent.getEventRole().getName().equals(Constant.ROLE_EVENT_MEMBER_VN)
						&& (memberEvent.getRegisterStatus().equals(Constant.REQUEST_STATUS_APPROVED)
								|| memberEvent.getRegisterStatus().equals(Constant.REQUEST_STATUS_PENDING))) {
					count++;
				}
			}

			if (eventRole.getQuantity() - count < 0) {
				return 0;
			} else {
				return eventRole.getQuantity() - count;
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
					if (memberEvent.getRegisterStatus().equals(Constant.REQUEST_STATUS_APPROVED)
							&& memberEvent.getUser().isActive()) {
						MemberEventDto memberEventDto = new MemberEventDto();
						memberEventDto.setId(memberEvent.getId());
						memberEventDto.setUserName(memberEvent.getUser().getName());
						memberEventDto.setUserMail(memberEvent.getUser().getEmail());
						memberEventDto.setUserStudentId(memberEvent.getUser().getStudentId());
						memberEventDto.setRegisterStatus(memberEvent.getRegisterStatus());
						EventRoleDto eventRoleDto = new EventRoleDto();
						eventRoleDto.setId(memberEvent.getEventRole().getId());
						eventRoleDto.setName(memberEvent.getEventRole().getName());
						eventRoleDto.setMaxQuantity(memberEvent.getEventRole().getQuantity());
						memberEventDto.setEventRoleDto(eventRoleDto);
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
	public ResponseMessage getListMemberNotJoinEvent(int eventId) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<User> listUser = (List<User>) userRepository.findAll();
			List<MemberEventDto> membersEventDto = new ArrayList<MemberEventDto>();
			for (User user : listUser) {
				Optional<MemberEvent> memberEventOp = memberEventRepository.findMemberEventByEventAndUser(eventId,
						user.getId());
				if (memberEventOp.isPresent()) {
					MemberEvent memberEvent = memberEventOp.get();
					if (!memberEvent.getRegisterStatus().equals(Constant.REQUEST_STATUS_APPROVED)
							&& !memberEvent.getRegisterStatus().equals(Constant.REQUEST_STATUS_PENDING)) {
						MemberEventDto memberEventDto = new MemberEventDto();
						memberEventDto.setUserId(user.getId());
						memberEventDto.setUserName(user.getName());
						memberEventDto.setUserMail(user.getEmail());
						memberEventDto.setUserStudentId(user.getStudentId());
						memberEventDto.setRoleInClub(Utils.convertRoleFromDbToExcel(user.getRole()));
						memberEventDto.setRegisterStatus(memberEvent.getRegisterStatus());
						membersEventDto.add(memberEventDto);
					}
				} else {
					MemberEventDto memberNotJoinEventDto = new MemberEventDto();
					memberNotJoinEventDto.setUserId(user.getId());
					memberNotJoinEventDto.setUserName(user.getName());
					memberNotJoinEventDto.setUserMail(user.getEmail());
					memberNotJoinEventDto.setUserStudentId(user.getStudentId());
					memberNotJoinEventDto.setRoleInClub(Utils.convertRoleFromDbToExcel(user.getRole()));
					membersEventDto.add(memberNotJoinEventDto);
				}
			}

			responseMessage.setData(membersEventDto);
			responseMessage.setMessage("Lấy danh sách thành viên chưa đăng ký tham gia sự kiện thành công");
			responseMessage.setTotalResult(membersEventDto.size());
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage addListMemberJoinEvent(int eventId, List<MemberEventDto> listToJoin) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Event event = eventRepository.findById(eventId).get();
			List<MemberEvent> listJoinEvent = new ArrayList<MemberEvent>();
			List<AttendanceEvent> attendancesEvent = new ArrayList<AttendanceEvent>();
			for (MemberEventDto memberEventDto : listToJoin) {
				User user = userRepository.findById(memberEventDto.getUserId()).get();
				MemberEvent memberEvent = new MemberEvent();
				if (memberEventDto.getRegisterStatus().equals(Constant.REQUEST_STATUS_DECLINED)) {
					memberEvent = memberEventRepository
							.findMemberEventByEventAndUser(eventId, memberEventDto.getUserId()).get();
					memberEvent.setUpdatedBy("toandv");
					memberEvent.setUpdatedOn(LocalDateTime.now());
				} else {
					memberEvent.setEvent(event);
					memberEvent.setUser(user);
					memberEvent.setPaymentValue(0);
					memberEvent.setPaidBeforeClosing(false);
					memberEvent.setCreatedBy("toandv");
					memberEvent.setCreatedOn(LocalDateTime.now());
				}
				memberEvent.setRegisterStatus(Constant.REQUEST_STATUS_APPROVED);
				Optional<EventRole> eventRoleOp = eventRoleRepository
						.findByNameAndEventId(Constant.ROLE_EVENT_MEMBER_VN, event.getId());
				;
				EventRole eventRole = eventRoleOp.get();
				memberEvent.setEventRole(eventRole);
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
					memberEventDto.setRegisterStatus(memberEvent.getRegisterStatus());
					EventRoleDto eventRoleDto = new EventRoleDto();
					eventRoleDto.setId(memberEvent.getEventRole().getId());
					eventRoleDto.setName(memberEvent.getEventRole().getName());
					memberEventDto.setEventRoleDto(eventRoleDto);
					memberEventDto.setRoleInClub(Utils.convertRoleFromDbToExcel(memberEvent.getUser().getRole()));
					memberEventDto.setPaymentValue(memberEvent.getPaymentValue());
					memberEventDto.setAmountPerRegisterEstimate(memberEvent.getEvent().getAmountPerRegisterEstimated());
					memberEventDto.setAmountPerRegisterActual(memberEvent.getEvent().getAmountPerRegisterActual());
					membersEventDto.add(memberEventDto);
				}
				responseMessage.setData(membersEventDto);
				responseMessage.setMessage("Thêm thành viên vào sự kiện thành công");
			}
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage registerToJoinEvent(int eventId, String studentId) {
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
					if (memberEvent.getRegisterStatus().equals(Constant.REQUEST_STATUS_APPROVED)
							|| memberEvent.getRegisterStatus().equals(Constant.REQUEST_STATUS_PENDING)) {
						responseMessage.setMessage("Bạn đã đăng ký tham gia sự kiện này rồi");
						return responseMessage;
					} else if (memberEvent.getRegisterStatus().equals(Constant.REQUEST_STATUS_DECLINED)) {
						responseMessage.setMessage("Bạn đã bị từ chối tham gia sự kiện này");
						return responseMessage;
					} else {
						memberEvent.setRegisterStatus(Constant.REQUEST_STATUS_PENDING);
						memberEvent.setUpdatedOn(LocalDateTime.now());
						memberEvent.setUpdatedBy(user.getName() + " - " + user.getStudentId());
					}
				} else {
					memberEvent.setEvent(event);
					memberEvent.setUser(user);
					memberEvent.setRegisterStatus(Constant.REQUEST_STATUS_PENDING);
					memberEvent.setPaymentValue(0);
					memberEvent.setPaidBeforeClosing(false);
					Optional<EventRole> eventRoleOp = eventRoleRepository
							.findByNameAndEventId(Constant.ROLE_EVENT_MEMBER_VN, event.getId());
					EventRole eventRole = eventRoleOp.get();
					memberEvent.setEventRole(eventRole);
					memberEvent.setCreatedBy(user.getName() + " - " + user.getStudentId());
					memberEvent.setCreatedOn(LocalDateTime.now());
				}

				memberEventRepository.save(memberEvent);

				UserEventDto userEventDto = new UserEventDto();
				userEventDto.setEventId(memberEvent.getEvent().getId());
				userEventDto.setEventName(memberEvent.getEvent().getName());
				userEventDto.setUserName(user.getName());
				userEventDto.setUserStudentId(user.getStudentId());
				EventRoleDto eventRoleDto = new EventRoleDto();
				eventRoleDto.setId(memberEvent.getEventRole().getId());
				eventRoleDto.setName(memberEvent.getEventRole().getName());
				userEventDto.setEventRoleDto(eventRoleDto);

				responseMessage.setData(Arrays.asList(userEventDto));
				responseMessage.setMessage("Đăng ký tham gia sự kiện thành công");
			} else {
				responseMessage.setMessage(Constant.MSG_131);
			}
		} catch (Exception e) {
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
				EventRole eventRole = eventRoleRepository.findById(roleEventId).get();
				if (!eventRole.getName().equals(Constant.ROLE_EVENT_MEMBER_VN)) {
					if (getAvailableQuantity(eventRole) > 0) {
						User user = userRepository.findByStudentId(studentId).get();

						MemberEvent memberEvent = new MemberEvent();

						Optional<MemberEvent> memberEventOp = memberEventRepository
								.findMemberEventByEventAndUser(event.getId(), user.getId());
						if (memberEventOp.isPresent()) {
							memberEvent = memberEventOp.get();
							if (memberEvent.getRegisterStatus().equals(Constant.REQUEST_STATUS_APPROVED)
									|| memberEvent.getRegisterStatus().equals(Constant.REQUEST_STATUS_PENDING)) {
								responseMessage.setMessage("Bạn đã đăng ký tham gia sự kiện này rồi");
								return responseMessage;
							} else if (memberEvent.getRegisterStatus().equals(Constant.REQUEST_STATUS_DECLINED)) {
								responseMessage.setMessage("Bạn đã bị từ chối tham gia sự kiện này");
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

						memberEvent.setRegisterStatus(Constant.REQUEST_STATUS_PENDING);
						memberEvent.setEventRole(eventRole);
						memberEventRepository.save(memberEvent);

						UserEventDto userEventDto = new UserEventDto();
						userEventDto.setEventId(memberEvent.getEvent().getId());
						userEventDto.setEventName(memberEvent.getEvent().getName());
						userEventDto.setUserName(user.getName());
						userEventDto.setUserStudentId(user.getStudentId());
						EventRoleDto eventRoleDto = new EventRoleDto();
						eventRoleDto.setId(memberEvent.getEventRole().getId());
						eventRoleDto.setName(memberEvent.getEventRole().getName());
						userEventDto.setEventRoleDto(eventRoleDto);

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
	public ResponseMessage acceptRequestToJoinEvent(int memberEventId) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<MemberEvent> memberEventOp = memberEventRepository.findById(memberEventId);
			if (memberEventOp.isPresent()) {
				MemberEvent memberEvent = memberEventOp.get();
				if (memberEvent.getRegisterStatus().equals(Constant.REQUEST_STATUS_PENDING)) {
					memberEvent.setRegisterStatus(Constant.REQUEST_STATUS_APPROVED);
					memberEvent.setUpdatedBy("toandv");
					memberEvent.setUpdatedOn(LocalDateTime.now());
					memberEventRepository.save(memberEvent);

					AttendanceEvent attendanceEvent = new AttendanceEvent();
					attendanceEvent.setEvent(memberEvent.getEvent());
					attendanceEvent.setStatus(2);
					attendanceEvent.setUser(memberEvent.getUser());
					attendanceEvent.setCreatedBy("toandv");
					attendanceEvent.setCreatedOn(LocalDateTime.now());
					attendanceEventRepository.save(attendanceEvent);

					Notification notification = new Notification();
					notification.setMessage("Yêu cầu đăng ký tham gia sự kiện " + memberEvent.getEvent().getName()
							+ " của bạn đã được chấp nhận");
					notification.setCreatedOn(LocalDateTime.now());
					notification.setNotificationType(1);
					notification.setNotificationTypeId(memberEvent.getEvent().getId());
					notificationRepository.save(notification);

					Iterable<Notification> notificationIterable = notificationRepository
							.findAll(Sort.by("id").descending());
					List<Notification> notifications = IterableUtils.toList(notificationIterable);
					Notification newNotification = notifications.get(0);

					notificationService.sendNotificationToAnUser(memberEvent.getUser(), newNotification);

					responseMessage.setData(Arrays.asList(memberEvent));
					responseMessage.setMessage("Từ chối đăng ký tham gia sự kiện của " + memberEvent.getUser().getName()
							+ " - " + memberEvent.getUser().getStudentId());
				} else {
					responseMessage.setMessage("Yêu cầu đăng ký không hợp lệ");
				}
			} else {
				responseMessage.setMessage("Sai memberEventId rồi");
			}
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage declineRequestToJoinEvent(int memberEventId) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<MemberEvent> memberEventOp = memberEventRepository.findById(memberEventId);
			if (memberEventOp.isPresent()) {
				MemberEvent memberEvent = memberEventOp.get();
				if (memberEvent.getRegisterStatus().equals(Constant.REQUEST_STATUS_PENDING)) {
					memberEvent.setRegisterStatus(Constant.REQUEST_STATUS_DECLINED);
					memberEvent.setUpdatedBy("toandv");
					memberEvent.setUpdatedOn(LocalDateTime.now());
					memberEventRepository.save(memberEvent);

					Optional<AttendanceEvent> attendanceEventOp = attendanceEventRepository
							.findByEventIdAndUserId(memberEvent.getEvent().getId(), memberEvent.getUser().getId());
					if (attendanceEventOp.isPresent()) {
						AttendanceEvent attendanceEvent = attendanceEventOp.get();
						attendanceEventRepository.delete(attendanceEvent);
					}

					Notification notification = new Notification();
					notification.setMessage("Yêu cầu đăng ký tham gia sự kiện " + memberEvent.getEvent().getName()
							+ " của bạn đã bị từ chối");
					notification.setCreatedOn(LocalDateTime.now());
					notification.setNotificationType(1);
					notification.setNotificationTypeId(memberEvent.getEvent().getId());
					notificationRepository.save(notification);

					Iterable<Notification> notificationIterable = notificationRepository
							.findAll(Sort.by("id").descending());
					List<Notification> notifications = IterableUtils.toList(notificationIterable);
					Notification newNotification = notifications.get(0);

					notificationService.sendNotificationToAnUser(memberEvent.getUser(), newNotification);

					responseMessage.setData(Arrays.asList(memberEvent));
					responseMessage.setMessage("Từ chối đăng ký tham gia sự kiện của " + memberEvent.getUser().getName()
							+ " - " + memberEvent.getUser().getStudentId());
				} else {
					responseMessage.setMessage("Yêu cầu đăng ký không hợp lệ");
				}
			} else {
				responseMessage.setMessage("Sai memberEventId rồi");
			}
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage deleteMemberEvent(int memberEventId) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<MemberEvent> memberEventOp = memberEventRepository.findById(memberEventId);
			if (memberEventOp.isPresent()) {
				MemberEvent memberEvent = memberEventOp.get();
				if (memberEvent.getPaymentValue() == 0) {
					memberEvent.setRegisterStatus(Constant.REQUEST_STATUS_DECLINED);
					memberEvent.setUpdatedBy("toandv");
					memberEvent.setUpdatedOn(LocalDateTime.now());
					memberEventRepository.save(memberEvent);

					Optional<AttendanceEvent> attendanceEventOp = attendanceEventRepository
							.findByEventIdAndUserId(memberEvent.getEvent().getId(), memberEvent.getUser().getId());
					if (attendanceEventOp.isPresent()) {
						AttendanceEvent attendanceEvent = attendanceEventOp.get();
						attendanceEventRepository.delete(attendanceEvent);
					}

					Notification notification = new Notification();
					notification.setMessage("Bạn đã bị xóa khỏi sự kiện " + memberEvent.getEvent().getName());
					notification.setCreatedOn(LocalDateTime.now());
					notification.setNotificationType(1);
					notification.setNotificationTypeId(memberEvent.getEvent().getId());
					notificationRepository.save(notification);

					Iterable<Notification> notificationIterable = notificationRepository
							.findAll(Sort.by("id").descending());
					List<Notification> notifications = IterableUtils.toList(notificationIterable);
					Notification newNotification = notifications.get(0);

					notificationService.sendNotificationToAnUser(memberEvent.getUser(), newNotification);

					responseMessage.setData(Arrays.asList(memberEvent));
					responseMessage.setMessage("Xóa " + memberEvent.getUser().getName() + " - "
							+ memberEvent.getUser().getStudentId() + " khỏi sự kiện thành công");
				} else {
					responseMessage.setMessage(
							"Thành viên này đã đóng phí tham gia. Vui lòng cập nhật lại trạng thái đóng tiền của "
									+ memberEvent.getUser().getName() + " - " + memberEvent.getUser().getStudentId());
				}
			} else {
				responseMessage.setMessage("Sai memberEventId rồi");
			}
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage getAllEventByStudentId(String studentId) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			User user = userRepository.findByStudentId(studentId).get();
			List<MemberEvent> membersEvent = memberEventRepository.findByUserId(user.getId());
			if (!membersEvent.isEmpty()) {
				List<UserEventDto> userEventsDto = new ArrayList<UserEventDto>();
				for (MemberEvent memberEvent : membersEvent) {
					UserEventDto userEventDto = new UserEventDto();
					userEventDto.setEventId(memberEvent.getEvent().getId());
					userEventDto.setEventName(memberEvent.getEvent().getName());
					userEventDto.setUserName(user.getName());
					userEventDto.setUserStudentId(user.getStudentId());
					EventRoleDto eventRoleDto = new EventRoleDto();
					eventRoleDto.setId(memberEvent.getEventRole().getId());
					eventRoleDto.setName(memberEvent.getEventRole().getName());
					userEventDto.setEventRoleDto(eventRoleDto);
					userEventDto.setRegisterStatus(memberEvent.getRegisterStatus());
					userEventsDto.add(userEventDto);
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
