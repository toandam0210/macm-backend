package com.fpt.macm.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.fpt.macm.constant.Constant;
import com.fpt.macm.model.dto.UserNotificationDto;
import com.fpt.macm.model.entity.Event;
import com.fpt.macm.model.entity.MemberEvent;
import com.fpt.macm.model.entity.MembershipInfo;
import com.fpt.macm.model.entity.MembershipStatus;
import com.fpt.macm.model.entity.Notification;
import com.fpt.macm.model.entity.NotificationToUser;
import com.fpt.macm.model.entity.Semester;
import com.fpt.macm.model.entity.Tournament;
import com.fpt.macm.model.entity.TournamentOrganizingCommittee;
import com.fpt.macm.model.entity.TournamentPlayer;
import com.fpt.macm.model.entity.User;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.repository.MemberEventRepository;
import com.fpt.macm.repository.MembershipShipInforRepository;
import com.fpt.macm.repository.MembershipStatusRepository;
import com.fpt.macm.repository.NotificationRepository;
import com.fpt.macm.repository.NotificationToUserRepository;
import com.fpt.macm.repository.TournamentOrganizingCommitteeRepository;
import com.fpt.macm.repository.TournamentRepository;
import com.fpt.macm.repository.UserRepository;

@Service
public class NotificationServiceImpl implements NotificationService {

	@Autowired
	NotificationRepository notificationRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	NotificationToUserRepository notificationToUserRepository;

	@Autowired
	TournamentRepository tournamentRepository;

	@Autowired
	MembershipStatusRepository membershipStatusRepository;

	@Autowired
	MembershipShipInforRepository membershipShipInforRepository;

	@Autowired
	SemesterService semesterService;

	@Autowired
	MemberEventRepository memberEventRepository;

	@Autowired
	TournamentOrganizingCommitteeRepository tournamentOrganizingCommitteeRepository;
	
	@Override
	public ResponseMessage getAllNotificationByStudentId(String studentId, int pageNo, int pageSize, String sortBy) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			User user = userRepository.findByStudentId(studentId).get();
			
			Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
			Page<NotificationToUser> pageResponse = notificationToUserRepository.findByUserId(user.getId(), paging);
			List<NotificationToUser> notificationsToUser = new ArrayList<NotificationToUser>();
			List<UserNotificationDto> userNotificationsDto = new ArrayList<UserNotificationDto>();
			List<NotificationToUser> unreadNotifications = notificationToUserRepository.findAllUnreadNotificationByUser(user.getId());
			if (pageResponse != null && pageResponse.hasContent()) {
				notificationsToUser = pageResponse.getContent();
				for (NotificationToUser notificationToUser : notificationsToUser) {
					UserNotificationDto userNotificationDto = convertToUserNotificationDto(notificationToUser);
					userNotificationsDto.add(userNotificationDto);
				}
				responseMessage.setData(userNotificationsDto);
				responseMessage.setMessage(Constant.MSG_016);
				responseMessage.setTotalResult(userNotificationsDto.size());
				responseMessage.setPageNo(pageNo);
				responseMessage.setPageSize(pageSize);
				responseMessage.setTotalPage(pageResponse.getTotalPages());
				// count số lượng chưa đọc
				responseMessage.setTotalDeactive(unreadNotifications.size());
			} else {
				responseMessage.setMessage("Không có thông báo nào!");
			}
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}
	
	private UserNotificationDto convertToUserNotificationDto(NotificationToUser notificationToUser) {
		UserNotificationDto userNotificationDto = new UserNotificationDto();
		userNotificationDto.setId(notificationToUser.getNotification().getId());
		userNotificationDto.setMessage(notificationToUser.getNotification().getMessage());
		userNotificationDto.setNotificationType(notificationToUser.getNotification().getNotificationType());
		userNotificationDto.setNotificationTypeId(notificationToUser.getNotification().getNotificationTypeId());
		userNotificationDto.setCreatedOn(notificationToUser.getNotification().getCreatedOn());
		userNotificationDto.setRead(notificationToUser.isRead());
		userNotificationDto.setUserName(notificationToUser.getUser().getName());
		userNotificationDto.setStudentId(notificationToUser.getUser().getStudentId());
		return userNotificationDto;
	}

	@Override
	public ResponseMessage createNotification(Notification notification) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			notification.setCreatedOn(LocalDateTime.now());

			notificationRepository.save(notification);
			responseMessage.setData(Arrays.asList(notification));
			responseMessage.setMessage(Constant.MSG_017);
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public void sendNotificationToAllUser(Notification notification) {
		// TODO Auto-generated method stub
		try {
			List<User> users = (List<User>) userRepository.findAll();

			List<NotificationToUser> notificationToUsers = new ArrayList<NotificationToUser>();

			for (User user : users) {
				NotificationToUser notificationToUser = new NotificationToUser();

				notificationToUser.setNotification(notification);
				notificationToUser.setUser(user);
				notificationToUser.setRead(false);
				notificationToUser.setCreatedOn(LocalDateTime.now());

				notificationToUsers.add(notificationToUser);

				notificationToUserRepository.save(notificationToUser);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	public void sendNotificationToAnUser(User user, Notification notification) {
		// TODO Auto-generated method stub
		try {
			NotificationToUser notificationToUser = new NotificationToUser();

			notificationToUser.setNotification(notification);
			notificationToUser.setUser(user);
			notificationToUser.setRead(false);
			notificationToUser.setCreatedOn(LocalDateTime.now());
			notificationToUserRepository.save(notificationToUser);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void createTournamentNotification(int tournamentId, String tournamentName) {
		try {
			Notification notification = new Notification();
			notification.setMessage("Sắp tới có giải đấu " + tournamentName + ".");
			notification.setNotificationType(0);
			notification.setNotificationTypeId(tournamentId);
			notification.setCreatedOn(LocalDateTime.now());
			notificationRepository.save(notification);
			
			Iterable<Notification> notificationIterable = notificationRepository.findAll(Sort.by("id").descending());
			List<Notification> notifications = IterableUtils.toList(notificationIterable);
			Notification newNotification = notifications.get(0);
			
			sendNotificationToAllUser(newNotification);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
	}

	public void createEventNotification(int eventId, String eventName) {
		try {
			Notification notification = new Notification();
			notification.setMessage("Sắp tới có sự kiện " + eventName + ".");
			notification.setNotificationType(1);
			notification.setNotificationTypeId(eventId);
			notification.setCreatedOn(LocalDateTime.now());
			notificationRepository.save(notification);
			
			Iterable<Notification> notificationIterable = notificationRepository.findAll(Sort.by("id").descending());
			List<Notification> notifications = IterableUtils.toList(notificationIterable);
			Notification newNotification = notifications.get(0);
			
			sendNotificationToAllUser(newNotification);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
	}

	public void createTrainingSessionCreateNotification(LocalDate date) {
		Notification notification = new Notification();
		notification.setMessage("Thông báo, có buổi tập mới vào ngày " + date.getDayOfMonth() + "/" + date.getMonthValue() + "/" + date.getYear() + ".");
		notification.setNotificationType(2);
		notification.setNotificationTypeId(0);
		notification.setCreatedOn(LocalDateTime.now());
		notificationRepository.save(notification);
		
		Iterable<Notification> notificationIterable = notificationRepository.findAll(Sort.by("id").descending());
		List<Notification> notifications = IterableUtils.toList(notificationIterable);
		Notification newNotification = notifications.get(0);
		
		sendNotificationToAllUser(newNotification);
	}

	public void createTrainingSessionUpdateNotification(LocalDate date, LocalTime newStartTime, LocalTime newEndTime) {
		Notification notification = new Notification();
		notification.setMessage(
				"Buổi tập ngày " + date.getDayOfMonth() + "/" + date.getMonthValue() + "/" + date.getYear() + " thay đổi thời gian tập thành: " + newStartTime + " - " + newEndTime + ".");
		notification.setNotificationType(2);
		notification.setNotificationTypeId(0);
		notification.setCreatedOn(LocalDateTime.now());
		notificationRepository.save(notification);
		
		Iterable<Notification> notificationIterable = notificationRepository.findAll(Sort.by("id").descending());
		List<Notification> notifications = IterableUtils.toList(notificationIterable);
		Notification newNotification = notifications.get(0);
		
		sendNotificationToAllUser(newNotification);
	}

	public void createTrainingSessionDeleteNotification(LocalDate date) {
		Notification notification = new Notification();
		notification.setMessage("Thông báo, nghỉ tập ngày " + date.getDayOfMonth() + "/" + date.getMonthValue() + "/" + date.getYear() + ".");
		notification.setNotificationType(2);
		notification.setNotificationTypeId(0);
		notification.setCreatedOn(LocalDateTime.now());
		notificationRepository.save(notification);
		
		Iterable<Notification> notificationIterable = notificationRepository.findAll(Sort.by("id").descending());
		List<Notification> notifications = IterableUtils.toList(notificationIterable);
		Notification newNotification = notifications.get(0);
		
		sendNotificationToAllUser(newNotification);
	}

	@Override
	public ResponseMessage checkPaymentStatus(String studentId) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<String> messages = new ArrayList<String>();
			User user = userRepository.findByStudentId(studentId).get();
			Semester semester = (Semester) semesterService.getCurrentSemester().getData().get(0);
			Optional<MembershipInfo> membershipInfoOp = membershipShipInforRepository
					.findBySemester(semester.getName());
			if (membershipInfoOp.isPresent()) {
				MembershipInfo membershipInfo = membershipInfoOp.get();
				MembershipStatus membershipStatus = membershipStatusRepository
						.findByMemberShipInfoIdAndUserId(membershipInfo.getId(), user.getId()).get();
				if (!membershipStatus.isStatus()) {
					String message = "Membership kỳ " + semester.getName() + ": "
							+ membershipInfo.getAmount() + " VND";
					messages.add(message);
				}
			}

			List<MemberEvent> membersEvent = memberEventRepository.findByUserId(user.getId());
			if (!membersEvent.isEmpty()) {
				for (MemberEvent memberEvent : membersEvent) {
					Event event = memberEvent.getEvent();
					double amountPerRegisterEstimate = event.getAmountPerRegisterEstimated();
					double amountPerRegisterActual = event.getAmountPerRegisterActual();

					if (amountPerRegisterEstimate != 0) {
						if (amountPerRegisterActual == 0) {
							if (memberEvent.getPaymentValue() == 0) {
								String message = "Sự kiện " + event.getName() + ": "
										+ amountPerRegisterEstimate + " VND";
								messages.add(message);
							}
						} else {
							if (memberEvent.getPaymentValue() == 0) {
								String message = "Sự kiện " + event.getName() + ": "
										+ amountPerRegisterActual + " VND";
								messages.add(message);
							} else if (amountPerRegisterActual > amountPerRegisterEstimate) {
								if (memberEvent.getPaymentValue() == amountPerRegisterEstimate) {
									String message = "Sự kiện " + event.getName()
											+ ": " + (amountPerRegisterActual - amountPerRegisterEstimate) + " VND";
									messages.add(message);
								}
							}
						}
					}
				}
			}

			List<TournamentOrganizingCommittee> tournamentOrganizingCommittees = tournamentOrganizingCommitteeRepository
					.findByUserId(user.getId());
			if (!tournamentOrganizingCommittees.isEmpty()) {
				for (TournamentOrganizingCommittee tournamentOrganizingCommittee : tournamentOrganizingCommittees) {
					if (!tournamentOrganizingCommittee.isPaymentStatus()) {
						String message = "Giải đấu "
								+ tournamentOrganizingCommittee.getTournament().getName() + ": "
								+ tournamentOrganizingCommittee.getTournament().getFeeOrganizingCommiteePay() + " VND";
						messages.add(message);
					}
				}
			}

			List<Tournament> tournaments = tournamentRepository.findAll();
			for (Tournament tournament : tournaments) {
				Set<TournamentPlayer> tournamentPlayers = tournament.getTournamentPlayers();
				for (TournamentPlayer tournamentPlayer : tournamentPlayers) {
					if (studentId.equals(tournamentPlayer.getUser().getStudentId())
							&& !tournamentPlayer.isPaymentStatus()) {
						String message = "Giải đấu " + tournament.getName() + ": "
								+ tournament.getFeePlayerPay() + " VND";
						messages.add(message);
					}
				}
			}

			responseMessage.setData(messages);
			responseMessage.setMessage(
					"Lấy trạng thái đóng tiền của " + user.getName() + " - " + user.getStudentId() + " thành công");
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage markNotificationAsRead(int notificationId, String studentId) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			User user = userRepository.findByStudentId(studentId).get();
			Optional<NotificationToUser> notificationToUserOp = notificationToUserRepository.findByUserIdAndNotificationId(user.getId(), notificationId);
			if (notificationToUserOp.isPresent()) {
				NotificationToUser notificationToUser = notificationToUserOp.get();
				notificationToUser.setRead(true);
				notificationToUserRepository.save(notificationToUser);
				UserNotificationDto userNotificationDto = convertToUserNotificationDto(notificationToUser);
				responseMessage.setData(Arrays.asList(userNotificationDto));
				responseMessage.setMessage("Đánh dấu là đã đọc thành công");
			}
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage markAllNotificationAsRead(String studentId) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			User user = userRepository.findByStudentId(studentId).get();
			List<NotificationToUser> notificationsToUser = notificationToUserRepository.findAllUnreadNotificationByUser(user.getId());
			List<UserNotificationDto> userNotificationsDto = new ArrayList<UserNotificationDto>();
			for (NotificationToUser notificationToUser : notificationsToUser) {
				notificationToUser.setRead(true);
				UserNotificationDto userNotificationDto = convertToUserNotificationDto(notificationToUser);
				userNotificationsDto.add(userNotificationDto);
			}
			notificationToUserRepository.saveAll(notificationsToUser);
			
			responseMessage.setData(userNotificationsDto);
			responseMessage.setMessage("Đánh dấu tất là đã đọc thành công");
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}
}
