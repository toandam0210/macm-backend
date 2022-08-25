package com.fpt.macm.service;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
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
import com.fpt.macm.model.entity.CompetitiveType;
import com.fpt.macm.model.entity.CompetitiveTypeRegistration;
import com.fpt.macm.model.entity.Event;
import com.fpt.macm.model.entity.ExhibitionPlayer;
import com.fpt.macm.model.entity.ExhibitionPlayerRegistration;
import com.fpt.macm.model.entity.ExhibitionTeamRegistration;
import com.fpt.macm.model.entity.ExhibitionType;
import com.fpt.macm.model.entity.ExhibitionTypeRegistration;
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
import com.fpt.macm.repository.CompetitiveTypeRegistrationRepository;
import com.fpt.macm.repository.ExhibitionPlayerRepository;
import com.fpt.macm.repository.ExhibitionTypeRegistrationRepository;
import com.fpt.macm.repository.MemberEventRepository;
import com.fpt.macm.repository.MembershipShipInforRepository;
import com.fpt.macm.repository.MembershipStatusRepository;
import com.fpt.macm.repository.NotificationRepository;
import com.fpt.macm.repository.NotificationToUserRepository;
import com.fpt.macm.repository.SemesterRepository;
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
	SemesterRepository semesterRepository;

	@Autowired
	MemberEventRepository memberEventRepository;

	@Autowired
	TournamentOrganizingCommitteeRepository tournamentOrganizingCommitteeRepository;

	@Autowired
	CompetitiveTypeRegistrationRepository competitiveTypeRegistrationRepository;

	@Autowired
	ExhibitionTypeRegistrationRepository exhibitionTypeRegistrationRepository;

	@Autowired
	ExhibitionPlayerRepository exhibitionPlayerRepository;

	@Override
	public ResponseMessage getAllNotificationByStudentId(String studentId, int pageNo, int pageSize, String sortBy) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			User user = userRepository.findByStudentId(studentId).get();

			Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
			Page<NotificationToUser> pageResponse = notificationToUserRepository.findByUserId(user.getId(), paging);
			List<NotificationToUser> notificationsToUser = new ArrayList<NotificationToUser>();
			List<UserNotificationDto> userNotificationsDto = new ArrayList<UserNotificationDto>();
			List<NotificationToUser> unreadNotifications = notificationToUserRepository
					.findAllUnreadNotificationByUser(user.getId());
			if (pageResponse != null && pageResponse.hasContent()) {
				notificationsToUser = pageResponse.getContent();
				for (NotificationToUser notificationToUser : notificationsToUser) {
					UserNotificationDto userNotificationDto = convertToUserNotificationDto(notificationToUser);
					userNotificationsDto.add(userNotificationDto);
				}
				Collections.sort(userNotificationsDto);
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
	public ResponseMessage sendNotificationToAllUser(Notification notification) {
		ResponseMessage responseMessage = new ResponseMessage();
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

			responseMessage.setData(notificationToUsers);
			responseMessage.setMessage("Gửi thông báo đến tất cả người dùng thành công");
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage sendNotificationToAnUser(User user, Notification notification) {
		ResponseMessage responseMessage = new ResponseMessage();
		NotificationToUser notificationToUser = new NotificationToUser();

		notificationToUser.setNotification(notification);
		notificationToUser.setUser(user);
		notificationToUser.setRead(false);
		notificationToUser.setCreatedOn(LocalDateTime.now());
		notificationToUserRepository.save(notificationToUser);

		responseMessage.setData(Arrays.asList(notificationToUser));
		responseMessage.setMessage("Gửi thông báo đến người dùng thành công");
		return responseMessage;
	}

	@Override
	public ResponseMessage createTournamentCreateNotification(int tournamentId, String tournamentName) {
		ResponseMessage responseMessage = new ResponseMessage();
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

			responseMessage.setData(Arrays.asList(notification));
			responseMessage.setMessage("Tạo thông báo cho giải đấu thành công");
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage createTournamentDeleteNotification(int tournamentId, String tournamentName) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Notification notification = new Notification();
			notification.setMessage("Giải đấu " + tournamentName + " đã hủy.");
			notification.setNotificationType(0);
			notification.setNotificationTypeId(tournamentId);
			notification.setCreatedOn(LocalDateTime.now());
			notificationRepository.save(notification);

			Iterable<Notification> notificationIterable = notificationRepository.findAll(Sort.by("id").descending());
			List<Notification> notifications = IterableUtils.toList(notificationIterable);
			Notification newNotification = notifications.get(0);

			sendNotificationToAllUser(newNotification);

			responseMessage.setData(Arrays.asList(notification));
			responseMessage.setMessage("Tạo thông báo cho giải đấu thành công");
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage createEventCreateNotification(int eventId, String eventName) {
		ResponseMessage responseMessage = new ResponseMessage();
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

			responseMessage.setData(Arrays.asList(notification));
			responseMessage.setMessage("Tạo thông báo cho sự kiện thành công");
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage createEventDeleteNotification(int eventId, String eventName) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Notification notification = new Notification();
			notification.setMessage("Sự kiện " + eventName + " đã hủy.");
			notification.setNotificationType(1);
			notification.setNotificationTypeId(eventId);
			notification.setCreatedOn(LocalDateTime.now());
			notificationRepository.save(notification);

			Iterable<Notification> notificationIterable = notificationRepository.findAll(Sort.by("id").descending());
			List<Notification> notifications = IterableUtils.toList(notificationIterable);
			Notification newNotification = notifications.get(0);

			sendNotificationToAllUser(newNotification);

			responseMessage.setData(Arrays.asList(notification));
			responseMessage.setMessage("Tạo thông báo cho sự kiện thành công");
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage createTrainingSessionCreateNotification(LocalDate date) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Notification notification = new Notification();
			notification.setMessage("Thông báo, có buổi tập mới vào ngày " + date.getDayOfMonth() + "/"
					+ date.getMonthValue() + "/" + date.getYear() + ".");
			notification.setNotificationType(2);
			notification.setNotificationTypeId(0);
			notification.setCreatedOn(LocalDateTime.now());
			notificationRepository.save(notification);

			Iterable<Notification> notificationIterable = notificationRepository.findAll(Sort.by("id").descending());
			List<Notification> notifications = IterableUtils.toList(notificationIterable);
			Notification newNotification = notifications.get(0);

			sendNotificationToAllUser(newNotification);

			responseMessage.setData(Arrays.asList(notification));
			responseMessage.setMessage("Tạo thông báo cho lịch tập thành công");
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage createTrainingSessionUpdateNotification(LocalDate date, LocalTime newStartTime,
			LocalTime newEndTime) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Notification notification = new Notification();
			notification.setMessage("Buổi tập ngày " + date.getDayOfMonth() + "/" + date.getMonthValue() + "/"
					+ date.getYear() + " thay đổi thời gian tập thành: " + newStartTime + " - " + newEndTime + ".");
			notification.setNotificationType(2);
			notification.setNotificationTypeId(0);
			notification.setCreatedOn(LocalDateTime.now());
			notificationRepository.save(notification);

			Iterable<Notification> notificationIterable = notificationRepository.findAll(Sort.by("id").descending());
			List<Notification> notifications = IterableUtils.toList(notificationIterable);
			Notification newNotification = notifications.get(0);

			sendNotificationToAllUser(newNotification);

			responseMessage.setData(Arrays.asList(notification));
			responseMessage.setMessage("Tạo thông báo cho lịch tập thành công");
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage createTrainingSessionDeleteNotification(LocalDate date) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Notification notification = new Notification();
			notification.setMessage("Thông báo, nghỉ tập ngày " + date.getDayOfMonth() + "/" + date.getMonthValue()
					+ "/" + date.getYear() + ".");
			notification.setNotificationType(2);
			notification.setNotificationTypeId(0);
			notification.setCreatedOn(LocalDateTime.now());
			notificationRepository.save(notification);

			Iterable<Notification> notificationIterable = notificationRepository.findAll(Sort.by("id").descending());
			List<Notification> notifications = IterableUtils.toList(notificationIterable);
			Notification newNotification = notifications.get(0);

			sendNotificationToAllUser(newNotification);

			responseMessage.setData(Arrays.asList(notification));
			responseMessage.setMessage("Tạo thông báo cho lịch tập thành công");
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage checkPaymentStatus(String studentId) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			NumberFormat nf = NumberFormat.getInstance(new Locale("en", "US"));

			List<String> messages = new ArrayList<String>();
			User user = userRepository.findByStudentId(studentId).get();
			List<Semester> semesters = semesterRepository.findAll();
			for (Semester semester : semesters) {
				Optional<MembershipInfo> membershipInfoOp = membershipShipInforRepository
						.findBySemester(semester.getName());
				if (membershipInfoOp.isPresent()) {
					MembershipInfo membershipInfo = membershipInfoOp.get();
					Optional<MembershipStatus> membershipStatusOp = membershipStatusRepository
							.findByMemberShipInfoIdAndUserId(membershipInfo.getId(), user.getId());
					if (membershipStatusOp.isPresent()) {
						MembershipStatus membershipStatus = membershipStatusOp.get();
						if (!membershipStatus.isStatus()) {
							String message = "Membership kỳ " + semester.getName() + ": "
									+ nf.format(membershipInfo.getAmount()) + " VND";
							messages.add(message);
						}
					}

				}
			}

			List<MemberEvent> membersEvent = memberEventRepository.findByUserId(user.getId());
			if (!membersEvent.isEmpty()) {
				for (MemberEvent memberEvent : membersEvent) {
					if (memberEvent.getEvent().isStatus()) {
						if (memberEvent.getRegisterStatus().equals(Constant.REQUEST_STATUS_APPROVED)) {
							Event event = memberEvent.getEvent();
							double amountPerRegisterEstimate = event.getAmountPerRegisterEstimated();
							double amountPerRegisterActual = event.getAmountPerRegisterActual();

							if (amountPerRegisterEstimate != 0) {
								if (amountPerRegisterActual == 0) {
									if (memberEvent.getPaymentValue() == 0) {
										String message = "Sự kiện " + event.getName() + ": "
												+ nf.format(amountPerRegisterEstimate) + " VND";
										messages.add(message);
									}
								} else {
									if (memberEvent.getPaymentValue() == 0) {
										String message = "Sự kiện " + event.getName() + ": "
												+ nf.format(amountPerRegisterActual) + " VND";
										messages.add(message);
									} else if (amountPerRegisterActual > amountPerRegisterEstimate) {
										if (memberEvent.getPaymentValue() == amountPerRegisterEstimate) {
											String message = "Sự kiện " + event.getName() + ": "
													+ nf.format((amountPerRegisterActual - amountPerRegisterEstimate))
													+ " VND";
											messages.add(message);
										}
									}
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
					Tournament tournament = tournamentOrganizingCommittee.getTournament();
					if (tournament.isStatus() && tournament.getFeeOrganizingCommiteePay() > 0) {
						if (tournamentOrganizingCommittee.getRegisterStatus().equals(Constant.REQUEST_STATUS_APPROVED)
								&& !tournamentOrganizingCommittee.isPaymentStatus()) {
							String message = "Giải đấu " + tournamentOrganizingCommittee.getTournament().getName()
									+ ": "
									+ nf.format(
											tournamentOrganizingCommittee.getTournament().getFeeOrganizingCommiteePay())
									+ " VND";
							messages.add(message);
						}
					}
				}
			}

			List<Tournament> tournaments = tournamentRepository.findAll();
			for (Tournament tournament : tournaments) {
				if (tournament.isStatus() && tournament.getFeePlayerPay() > 0) {
					Set<TournamentPlayer> tournamentPlayers = tournament.getTournamentPlayers();
					for (TournamentPlayer tournamentPlayer : tournamentPlayers) {
						if (studentId.equals(tournamentPlayer.getUser().getStudentId())
								&& !tournamentPlayer.isPaymentStatus()) {
							boolean isContinue = false;
							Set<CompetitiveType> competitiveTypes = tournament.getCompetitiveTypes();
							for (CompetitiveType currentCompetitiveType : competitiveTypes) {
								Optional<CompetitiveTypeRegistration> competitiveTypeRegistrationOp = competitiveTypeRegistrationRepository
										.findByCompetitiveTypeIdAndTournamentPlayerId(currentCompetitiveType.getId(),
												tournamentPlayer.getId());
								if (competitiveTypeRegistrationOp.isPresent()) {
									CompetitiveTypeRegistration competitiveTypeRegistration = competitiveTypeRegistrationOp
											.get();
									if (competitiveTypeRegistration.getRegisterStatus()
											.equals(Constant.REQUEST_STATUS_APPROVED)
											|| competitiveTypeRegistration.getRegisterStatus()
													.equals(Constant.REQUEST_STATUS_PENDING)) {
										String message = "Giải đấu " + tournament.getName() + ": "
												+ nf.format(tournament.getFeePlayerPay()) + " VND";
										messages.add(message);
										isContinue = true;
										break;
									}
								}
							}

							if (isContinue) {
								continue;
							}

							Set<ExhibitionType> exhibitionTypes = tournament.getExhibitionTypes();
							for (ExhibitionType exhibitionType : exhibitionTypes) {
								List<ExhibitionTypeRegistration> exhibitionTypeRegistrations = exhibitionTypeRegistrationRepository
										.findByExhibitionTypeId(exhibitionType.getId());
								for (ExhibitionTypeRegistration exhibitionTypeRegistration : exhibitionTypeRegistrations) {
									if (exhibitionTypeRegistration.getRegisterStatus()
											.equals(Constant.REQUEST_STATUS_PENDING)) {
										ExhibitionTeamRegistration exhibitionTeamRegistration = exhibitionTypeRegistration
												.getExhibitionTeamRegistration();
										Set<ExhibitionPlayerRegistration> exhibitionPlayersRegistration = exhibitionTeamRegistration
												.getExhibitionPlayersRegistration();
										for (ExhibitionPlayerRegistration exhibitionPlayerRegistration : exhibitionPlayersRegistration) {
											if (tournamentPlayer.getId() == exhibitionPlayerRegistration
													.getTournamentPlayer().getId()) {
												String message = "Giải đấu " + tournament.getName() + ": "
														+ nf.format(tournament.getFeePlayerPay()) + " VND";
												messages.add(message);
												isContinue = true;
												break;
											}
										}
									}
									if (isContinue) {
										break;
									}
								}
								if (isContinue) {
									break;
								}
								Optional<ExhibitionPlayer> exhibitionPlayerOp = exhibitionPlayerRepository
										.findByTournamentPlayerAndType(tournamentPlayer.getId(),
												exhibitionType.getId());
								if (exhibitionPlayerOp.isPresent()) {
									String message = "Giải đấu " + tournament.getName() + ": "
											+ nf.format(tournament.getFeePlayerPay()) + " VND";
									messages.add(message);
								}
								break;
							}
						}
					}
				}
			}

			if (!messages.isEmpty()) {
				responseMessage.setData(messages);
				responseMessage.setMessage(
						"Lấy trạng thái đóng tiền của " + user.getName() + " - " + user.getStudentId() + " thành công");
			} else {
				responseMessage.setMessage("Không có khoản nào phải đóng");
			}
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
			Optional<NotificationToUser> notificationToUserOp = notificationToUserRepository
					.findByUserIdAndNotificationId(user.getId(), notificationId);
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
			List<NotificationToUser> notificationsToUser = notificationToUserRepository
					.findAllUnreadNotificationByUser(user.getId());
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

	@Override
	public ResponseMessage getAllUnreadNotificationByStudentId(String studentId, int pageNo, int pageSize,
			String sortBy) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			User user = userRepository.findByStudentId(studentId).get();

			Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
			Page<NotificationToUser> pageResponse = notificationToUserRepository.findByUserIdAndIsRead(user.getId(),
					false, paging);
			List<NotificationToUser> notificationsToUser = new ArrayList<NotificationToUser>();
			List<UserNotificationDto> userNotificationsDto = new ArrayList<UserNotificationDto>();
			if (pageResponse != null && pageResponse.hasContent()) {
				notificationsToUser = pageResponse.getContent();
				for (NotificationToUser notificationToUser : notificationsToUser) {
					UserNotificationDto userNotificationDto = convertToUserNotificationDto(notificationToUser);
					userNotificationsDto.add(userNotificationDto);
				}
				Collections.sort(userNotificationsDto);
				responseMessage.setData(userNotificationsDto);
				responseMessage.setMessage("Lấy tất cả thông báo chưa đọc thành công");
				responseMessage.setTotalResult(userNotificationsDto.size());
				responseMessage.setPageNo(pageNo);
				responseMessage.setPageSize(pageSize);
				responseMessage.setTotalPage(pageResponse.getTotalPages());
			} else {
				responseMessage.setMessage("Không có thông báo nào!");
			}
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}
}
