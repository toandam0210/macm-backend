package com.fpt.macm.schedule;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.collections4.IterableUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fpt.macm.constant.Constant;
import com.fpt.macm.model.entity.AdminSemester;
import com.fpt.macm.model.entity.AttendanceEvent;
import com.fpt.macm.model.entity.AttendanceStatus;
import com.fpt.macm.model.entity.CollaboratorReport;
import com.fpt.macm.model.entity.CompetitiveType;
import com.fpt.macm.model.entity.Event;
import com.fpt.macm.model.entity.EventSchedule;
import com.fpt.macm.model.entity.ExhibitionType;
import com.fpt.macm.model.entity.MemberEvent;
import com.fpt.macm.model.entity.MemberSemester;
import com.fpt.macm.model.entity.MembershipInfo;
import com.fpt.macm.model.entity.Notification;
import com.fpt.macm.model.entity.NotificationToUser;
import com.fpt.macm.model.entity.Role;
import com.fpt.macm.model.entity.Semester;
import com.fpt.macm.model.entity.Tournament;
import com.fpt.macm.model.entity.TournamentOrganizingCommittee;
import com.fpt.macm.model.entity.TournamentPlayer;
import com.fpt.macm.model.entity.TournamentSchedule;
import com.fpt.macm.model.entity.TrainingSchedule;
import com.fpt.macm.model.entity.User;
import com.fpt.macm.model.entity.UserStatusReport;
import com.fpt.macm.repository.AdminSemesterRepository;
import com.fpt.macm.repository.AttendanceEventRepository;
import com.fpt.macm.repository.AttendanceStatusRepository;
import com.fpt.macm.repository.CollaboratorReportRepository;
import com.fpt.macm.repository.CompetitiveMatchRepository;
import com.fpt.macm.repository.EventRepository;
import com.fpt.macm.repository.EventScheduleRepository;
import com.fpt.macm.repository.MemberEventRepository;
import com.fpt.macm.repository.MemberSemesterRepository;
import com.fpt.macm.repository.MembershipShipInforRepository;
import com.fpt.macm.repository.MembershipStatusRepository;
import com.fpt.macm.repository.NotificationRepository;
import com.fpt.macm.repository.NotificationToUserRepository;
import com.fpt.macm.repository.SemesterRepository;
import com.fpt.macm.repository.TournamentOrganizingCommitteeRepository;
import com.fpt.macm.repository.TournamentRepository;
import com.fpt.macm.repository.TrainingScheduleRepository;
import com.fpt.macm.repository.UserRepository;
import com.fpt.macm.repository.UserStatusReportRepository;
import com.fpt.macm.service.EventService;
import com.fpt.macm.service.NotificationService;
import com.fpt.macm.service.SemesterService;
import com.fpt.macm.service.TournamentScheduleService;
import com.fpt.macm.service.TournamentService;
import com.fpt.macm.service.TrainingScheduleService;

@Component
public class TaskSchedule {

	@Autowired
	UserRepository userRepository;

	@Autowired
	MemberEventRepository memberEventRepository;

	@Autowired
	MemberSemesterRepository memberSemesterRepository;

	@Autowired
	AdminSemesterRepository adminSemesterRepository;

	@Autowired
	AttendanceStatusRepository attendanceStatusRepository;

	@Autowired
	AttendanceEventRepository attendanceEventRepository;

	@Autowired
	EventRepository eventRepository;

	@Autowired
	TrainingScheduleService trainingScheduleService;

	@Autowired
	EventService eventService;

	@Autowired
	MembershipStatusRepository membershipStatusRepository;

	@Autowired
	SemesterRepository semesterRepository;

	@Autowired
	CollaboratorReportRepository collaboratorReportRepository;

	@Autowired
	MembershipShipInforRepository membershipShipInforRepository;

	@Autowired
	NotificationService notificationService;

	@Autowired
	UserStatusReportRepository userStatusReportRepository;

	@Autowired
	SemesterService semesterService;

	@Autowired
	TournamentService tournamentService;

	@Autowired
	CompetitiveMatchRepository competitiveMatchRepository;

	@Autowired
	EventScheduleRepository eventScheduleRepository;

	@Autowired
	TournamentScheduleService tournamentScheduleService;

	@Autowired
	TournamentRepository tournamentRepository;

	@Autowired
	NotificationRepository notificationRepository;

	@Autowired
	TournamentOrganizingCommitteeRepository tournamentOrganizingCommitteeRepository;

	@Autowired
	TrainingScheduleRepository trainingScheduleRepository;

	@Autowired
	NotificationToUserRepository notificationToUserRepository;

	Logger logger = LoggerFactory.getLogger(TaskSchedule.class);

	@Scheduled(cron = "1 0 0 * * *")
	public void updateCollaboratorRole() {
		List<User> listCollaborator = userRepository.findCollaborator();
		Role role = new Role();
		int countPassed = 0;
		int countNotPassed = 0;
		int countMale = 0;
		int countFemale = 0;
		Semester semester = (Semester) semesterService.getCurrentSemester().getData().get(0);
		for (User collaborator : listCollaborator) {
			if (LocalDate.now().compareTo(collaborator.getCreatedOn().plusMonths(1).plusDays(1)) == 0) {
				if (collaborator.isGender()) {
					countMale++;
				} else {
					countFemale++;
				}
				if (collaborator.isActive()) {
					countPassed++;
					role.setId(collaborator.getRole().getId() - 3);
					collaborator.setRole(role);
					collaborator.setUpdatedBy("Hệ Thống");
					collaborator.setUpdatedOn(LocalDateTime.now());
					userRepository.save(collaborator);
					MemberSemester statusSemester = new MemberSemester();
					statusSemester.setUser(collaborator);
					statusSemester.setSemester(semester.getName());
					statusSemester.setStatus(collaborator.isActive());
					memberSemesterRepository.save(statusSemester);
				} else {
					countNotPassed++;
				}
			}
		}
		Optional<UserStatusReport> userStatusReportOp = userStatusReportRepository.findBySemester(semester.getName());
		if (userStatusReportOp.isPresent()) {
			UserStatusReport userStatusReport = userStatusReportOp.get();
			userStatusReport.setNumberActiveInSemester(userStatusReport.getNumberActiveInSemester() + countPassed);
			userStatusReport
					.setTotalNumberUserInSemester(userStatusReport.getTotalNumberUserInSemester() + countPassed);
			userStatusReportRepository.save(userStatusReport);
		}

		Optional<CollaboratorReport> colOptional = collaboratorReportRepository.findBySemester(semester.getName());
		if (colOptional.isPresent()) {
			CollaboratorReport collaboratorReport = colOptional.get();
			collaboratorReport.setNumberJoin(listCollaborator.size());
			collaboratorReport.setSemester(semester.getName());
			collaboratorReport = colOptional.get();
			collaboratorReport.setNumberPassed(countPassed);
			collaboratorReport.setNumberNotPassed(countNotPassed);
			collaboratorReport.setNumberMale(countMale);
			collaboratorReport.setNumberFemale(countFemale);
			collaboratorReportRepository.save(collaboratorReport);
		} else {
			CollaboratorReport collaboratorReportNew = new CollaboratorReport();
			collaboratorReportNew.setNumberJoin(listCollaborator.size());
			collaboratorReportNew.setSemester(semester.getName());
			collaboratorReportNew.setNumberPassed(countPassed);
			collaboratorReportNew.setNumberNotPassed(countNotPassed);
			collaboratorReportNew.setNumberMale(countMale);
			collaboratorReportNew.setNumberFemale(countFemale);
			collaboratorReportNew.setNumberJoin(countFemale);
			collaboratorReportRepository.save(collaboratorReportNew);
		}
		logger.info("report oke");
	}
	
	@Scheduled(cron = "0 0 1 * * *")
	public void updateStatusMemberToDeactive() {
		Semester currentSemester = (Semester) semesterService.getCurrentSemester().getData().get(0);
		if(LocalDate.now().isEqual(currentSemester.getStartDate())) {
			List<User> users = userRepository.findMembersAndAdmin();
			for (User user : users) {
				if (user.getRole().getId() > 9 && user.getRole().getId() < 13) {
					user.setActive(false);
					userRepository.save(user);
					if (user.getRole().getId() > 9 && user.getRole().getId() < 13) {
						MemberSemester memberSemester = new MemberSemester();
						memberSemester.setSemester(currentSemester.getName());
						memberSemester.setStatus(false);
						memberSemester.setClicked(false);
						memberSemester.setUser(user);
						memberSemesterRepository.save(memberSemester);
						logger.info("add member oke");
					}
				}
				if (user.getRole().getId() > 0 && user.getRole().getId() < 10) {
					AdminSemester adminSemester = new AdminSemester();
					adminSemester.setRole(user.getRole());
					adminSemester.setSemester(currentSemester.getName());
					adminSemester.setUser(user);
					adminSemesterRepository.save(adminSemester);
					logger.info("add admin oke");
				}
			}
		}
		logger.info("oke roi");
	}
	

//	@Scheduled(cron = "1 1 0 * * *")
//	public void addUserBySemester() {
//		if (LocalDate.now().getDayOfMonth() > 7 && LocalDate.now().getDayOfMonth() <= 14
//				&& LocalDate.now().getMonthValue() % 4 == 1
//				&& LocalDate.now().getDayOfWeek().toString().compareTo("MONDAY") == 0) {
//			List<User> members = userRepository.findMemberWithoutPaging();
//			List<User> admins = userRepository.findAllAdmin();
//			Semester semester = (Semester) semesterService.getCurrentSemester().getData().get(0);
//			UserStatusReport userStatusReport = new UserStatusReport();
//			userStatusReport.setSemester(semester.getName());
//			int numberUserActive = 0;
//			int numberUserDeactive = 0;
//			for (User user : members) {
//				if (user.isActive()) {
//					numberUserActive++;
//					Optional<MemberSemester> memberSemesterOp = memberSemesterRepository.findByUserIdAndSemester(user.getId(), semester.getName());
//					if (!memberSemesterOp.isPresent()) {
//						MemberSemester statusSemester = new MemberSemester();
//						statusSemester.setUser(user);
//						statusSemester.setSemester(semester.getName());
//						statusSemester.setStatus(user.isActive());
//						memberSemesterRepository.save(statusSemester);
//						logger.info("add member oke");
//					}
//				} else {
//					numberUserDeactive++;
//				}
//			}
//			for (User user : admins) {
//				numberUserActive++;
//				Optional<AdminSemester> adminSemesterOp = adminSemesterRepository.findByUserId(user.getId(), semester.getName());
//				if (!adminSemesterOp.isPresent()) {
//					AdminSemester adminSemester = new AdminSemester();
//					adminSemester.setUser(user);
//					adminSemester.setSemester(semester.getName());
//					adminSemester.setRole(user.getRole());
//					adminSemesterRepository.save(adminSemester);
//					logger.info("add admin oke");
//				}
//			}
//			
//			Optional<UserStatusReport> userStatusReportOp = userStatusReportRepository.findBySemester(semester.getName());
//			if (userStatusReportOp.isPresent()) {
//				userStatusReport = userStatusReportOp.get();
//			}
//			
//			userStatusReport.setNumberActiveInSemester(numberUserActive);
//			userStatusReport.setNumberDeactiveInSemester(numberUserDeactive);
//			userStatusReport.setTotalNumberUserInSemester(numberUserActive + numberUserDeactive);
//			userStatusReportRepository.save(userStatusReport);
//			
//			logger.info("loi roi");
//		}
//	}

//	@Scheduled(cron = "1 2 0 * * *")
//	public void addListAttendanceStatus() {
//		logger.info("bat dau chay");
//		TrainingSchedule trainingSchedule = trainingScheduleService.getTrainingScheduleByDate(LocalDate.now());
//		if (trainingSchedule != null) {
//			logger.info("Khac null");
//			List<User> users = (List<User>) userRepository.findAll();
//			for (User user : users) {
//				logger.info("vao for");
//				if (user.isActive()) {
//					AttendanceStatus attendanceStatus = new AttendanceStatus();
//					attendanceStatus.setUser(user);
//					attendanceStatus.setTrainingSchedule(trainingSchedule);
//					attendanceStatus.setCreatedOn(LocalDateTime.now());
//					attendanceStatus.setCreatedBy("toandv");
//					attendanceStatus.setStatus(2);
//					attendanceStatusRepository.save(attendanceStatus);
//					logger.info("atten oke");
//				}
//			}
//		}
//		logger.info("Chay xong");
//	}

//	@Scheduled(cron = "1 2 0 * * *")
//	public void addListMemberEventAttendanceStatus() {
//		EventSchedule eventSchedule = eventScheduleService.getEventScheduleByDate(LocalDate.now());
//		if (eventSchedule != null) {
//			Event event = eventSchedule.getEvent();
//			LocalDate startDate = (LocalDate) eventService.getStartDateOfEvent(event.getId()).getData().get(0);
//			if (startDate.compareTo(LocalDate.now()) == 0) {
//				List<MemberEvent> membersEvent = (List<MemberEvent>) memberEventRepository
//						.findByEventIdOrderByIdAsc(event.getId());
//				for (MemberEvent memberEvent : membersEvent) {
//					if (memberEvent.isRegisterStatus()) {
//						AttendanceEvent attendanceEvent = new AttendanceEvent();
//						attendanceEvent.setMemberEvent(memberEvent);
//						attendanceEvent.setEvent(event);
//						attendanceEvent.setCreatedOn(LocalDateTime.now());
//						attendanceEvent.setCreatedBy("toandv");
//						attendanceEvent.setStatus(2);
//						attendanceEventRepository.save(attendanceEvent);
//						logger.info("atten oke");
//					}
//				}
//			}
//		}
//	}

	@Scheduled(cron = "0 0 1 * * *")
	public void addListMembershipStatus() {
		Semester currentSemester = (Semester) semesterService.getCurrentSemester().getData().get(0);
		if (LocalDate.now().isEqual(currentSemester.getStartDate())) {
			MembershipInfo membershipInfo = new MembershipInfo();
			membershipInfo.setAmount(50000);
			membershipInfo.setSemester(currentSemester.getName());
			membershipShipInforRepository.save(membershipInfo);
			logger.info("ok roi day");
		}
	}

	@Scheduled(cron = "1 0 0 * * *")
	public void addSemester() {
		if (LocalDate.now().getDayOfMonth() <= 7 && LocalDate.now().getMonthValue() % 4 == 1
				&& LocalDate.now().getDayOfWeek().toString().compareTo("MONDAY") == 0) {
			Semester semester = new Semester();
			if (LocalDate.now().getMonthValue() == 1) {
				semester.setName("Spring" + LocalDate.now().getYear());
			}
			if (LocalDate.now().getMonthValue() == 5) {
				semester.setName("Summer" + LocalDate.now().getYear());
			}
			if (LocalDate.now().getMonthValue() == 9) {
				semester.setName("Fall" + LocalDate.now().getYear());
			}
			semester.setStartDate(LocalDate.now());
			LocalDate endDate = LocalDate.now().plusMonths(4).minusDays(LocalDate.now().getDayOfMonth());
			while (endDate.getDayOfWeek().toString().compareTo("SUNDAY") != 0) {
				endDate = endDate.plusDays(1);
			}
			semester.setEndDate(endDate);
			semesterRepository.save(semester);
		}
	}

	@Scheduled(cron = "1 2 0 * * *")
	public void pushNotificationBeforeEvent() {
		List<Event> listEvent = eventRepository.findAll();
		for (Event event : listEvent) {
			LocalDate getStartDate = eventService.getStartDate(event.getId());
			if (getStartDate != null) {
				if (LocalDate.now().plusDays(1).isEqual(getStartDate)) {
					List<MemberEvent> membersEvent = (List<MemberEvent>) memberEventRepository
							.findByEventIdOrderByIdAsc(event.getId());
					String message = Constant.messageEvent(event);
					Notification notification = new Notification();
					notification.setMessage(message);
					notification.setCreatedOn(LocalDateTime.now());
					notification.setNotificationType(1);
					notification.setNotificationTypeId(event.getId());
					notificationRepository.save(notification);

					Iterable<Notification> notificationIterable = notificationRepository
							.findAll(Sort.by("id").descending());
					List<Notification> notifications = IterableUtils.toList(notificationIterable);
					Notification newNotification = notifications.get(0);

					for (MemberEvent member : membersEvent) {
						if (member.getRegisterStatus().equals(Constant.REQUEST_STATUS_APPROVED)) {
							User user = member.getUser();
							notificationService.sendNotificationToAnUser(user, newNotification);
						}
					}
					break;
				}
			}
		}

	}

	@Scheduled(cron = "1 2 0 * * *")
	public void pushNotificationBeforeTournament() {
		List<Tournament> tournaments = tournamentRepository.findAll();
		for (Tournament tournament : tournaments) {
			LocalDate getStartDate = tournamentService.getStartDate(tournament.getId());

			if (getStartDate != null) {
				logger.info(getStartDate.toString());
				if (LocalDate.now().plusDays(1).isEqual(getStartDate)) {
					Notification notification = new Notification();
					notification.setMessage(
							"Giải đấu " + tournament.getName() + " sẽ bắt đầu sau 1 ngày nữa, chuẩn bị chiến nào !!");
					notification.setCreatedOn(LocalDateTime.now());
					notification.setNotificationType(0);
					notification.setNotificationTypeId(tournament.getId());
					notificationRepository.save(notification);

					Iterable<Notification> notificationIterable = notificationRepository
							.findAll(Sort.by("id").descending());
					List<Notification> notifications = IterableUtils.toList(notificationIterable);
					Notification newNotification = notifications.get(0);

					List<TournamentOrganizingCommittee> tournamentOrganizingCommittees = tournamentOrganizingCommitteeRepository
							.findByTournamentId(tournament.getId());
					for (TournamentOrganizingCommittee tournamentOrganizingCommittee : tournamentOrganizingCommittees) {
						User user = tournamentOrganizingCommittee.getUser();

						notificationService.sendNotificationToAnUser(user, newNotification);
					}

					Set<TournamentPlayer> tournamentPlayers = tournament.getTournamentPlayers();
					for (TournamentPlayer tournamentPlayer : tournamentPlayers) {
						User user = tournamentPlayer.getUser();

						notificationService.sendNotificationToAnUser(user, newNotification);
					}

					logger.info("okeeeeee");

					break;
				}
			}
		}
	}

	@Scheduled(cron = "1 2 0 * * *")
	public void pushNotificationWarningAbsent() {
		List<User> users = userRepository.findAllActiveUser();
		Semester semester = (Semester) semesterService.getCurrentSemester().getData().get(0);
		for (User user : users) {
			if (hasSentNotificationWarningAbsent(user, semester)) {
				continue;
			}

			List<TrainingSchedule> trainingSchedules = trainingScheduleRepository
					.listTrainingScheduleByTime(semester.getStartDate(), semester.getEndDate());
			int totalAbsent = 0;
			for (TrainingSchedule trainingSchedule : trainingSchedules) {
				AttendanceStatus attendanceStatus = attendanceStatusRepository
						.findByUserIdAndTrainingScheduleId(user.getId(), trainingSchedule.getId());
				if (attendanceStatus != null && attendanceStatus.getStatus() == 0) {
					totalAbsent++;
				}
			}
			double percentAbsent = Math.ceil(((double) totalAbsent / (double) trainingSchedules.size()) * 100);

			if (percentAbsent >= 40) {
				Notification notification = new Notification();
				notification.setMessage("Cảnh báo: bạn đã nghỉ " + totalAbsent + "/" + trainingSchedules.size() + " ("
						+ percentAbsent + "%) số buổi tập kỳ " + semester.getName());
				notification.setCreatedOn(LocalDateTime.now());
				notification.setNotificationType(3);
				notification.setNotificationTypeId(0);
				notificationRepository.save(notification);

				Iterable<Notification> notificationIterable = notificationRepository
						.findAll(Sort.by("id").descending());
				List<Notification> notifications = IterableUtils.toList(notificationIterable);
				Notification newNotification = notifications.get(0);

				notificationService.sendNotificationToAnUser(user, newNotification);
			}

		}
	}

	public boolean hasSentNotificationWarningAbsent(User user, Semester semester) {
		List<NotificationToUser> notificationsToUser = notificationToUserRepository.findAllByUserId(user.getId());
		for (NotificationToUser notificationToUser : notificationsToUser) {
			if (notificationToUser.getNotification().getNotificationType() == 3) {
				LocalDate createdDate = notificationToUser.getNotification().getCreatedOn().toLocalDate();
				if (createdDate.isAfter(semester.getStartDate()) && createdDate.isBefore(semester.getEndDate())) {
					return true;
				}
			}
		}
		return false;
	}

	@Scheduled(cron = "1 59 23 * * *")
	public void changeStatusAttendanceTraining() {
		TrainingSchedule trainingSchedule = trainingScheduleService.getTrainingScheduleByDate(LocalDate.now());
		if (trainingSchedule != null) {
			List<AttendanceStatus> listAttendanceStatus = attendanceStatusRepository
					.findByTrainingScheduleIdOrderByIdAsc(trainingSchedule.getId());
			for (AttendanceStatus attendanceStatus : listAttendanceStatus) {
				if (attendanceStatus.getStatus() == 2) {
					attendanceStatus.setStatus(0);
					attendanceStatusRepository.save(attendanceStatus);
				}
			}
		}
	}

	@Scheduled(cron = "1 59 23 * * *")
	public void changeStatusAttendanceEvent() {
		Optional<EventSchedule> eventScheduleOp = eventScheduleRepository.findByDate(LocalDate.now());
		if (eventScheduleOp.isPresent()) {
			EventSchedule eventSchedule = eventScheduleOp.get();
			List<AttendanceEvent> listAttendanceEvent = attendanceEventRepository
					.findByEventId(eventSchedule.getEvent().getId());
			for (AttendanceEvent attendanceEvent : listAttendanceEvent) {
				if (attendanceEvent.getStatus() == 2) {
					attendanceEvent.setStatus(0);
					attendanceEventRepository.save(attendanceEvent);
				}
			}
		}
	}

	@Scheduled(cron = "1 0 0 * * *")
	public void changeStatusTournamentForUpdatePlayer() {
		
		List<Tournament> tournaments = tournamentRepository.findAll();
		List<Tournament> listTournaments = new ArrayList<Tournament>();
		for (Tournament tournament : tournaments) {
			if (tournament.getRegistrationPlayerDeadline().toLocalDate().isEqual(LocalDateTime.now().toLocalDate())
					&& tournament.getRegistrationPlayerDeadline().getHour() == LocalDateTime.now().getHour()) {
				listTournaments.add(tournament);
			}
		}
		
		if (!listTournaments.isEmpty()) {
			for (Tournament tournament : listTournaments) {
				logger.info("Thay đổi trạng thái của giải đấu " + tournament.getName());
				tournament.setStage(1);
				Set<CompetitiveType> listCompetitiveTypes = tournament.getCompetitiveTypes();
				for (CompetitiveType competitiveType : listCompetitiveTypes) {
					if (competitiveType.getStatus() == 0) {
						competitiveType.setStatus(1);
						logger.info("Trạng thái của " + (competitiveType.isGender() ? "Nam " : "Nữ ")
								+ competitiveType.getWeightMin() + " - " + competitiveType.getWeightMax()
								+ " thay đổi từ 0 thành 1");
					}
				}
				tournament.setCompetitiveTypes(listCompetitiveTypes);
				Set<ExhibitionType> listExhibitionTypes = tournament.getExhibitionTypes();
				for (ExhibitionType exhibitionType : listExhibitionTypes) {
					if (exhibitionType.getStatus() == 0) {
						exhibitionType.setStatus(1);
						logger.info("Trạng thái của " + exhibitionType.getName() + " thay đổi từ 0 thành 1");
					}
				}
				tournament.setExhibitionTypes(listExhibitionTypes);

				logger.info("Dừng thay đổi trạng thái của giải đấu " + tournament.getName());
				tournamentRepository.save(tournament);
			}
		} else {
			logger.info("Không có giải đấu");
		}
	}

	@Scheduled(cron = "1 0 0 * * *")
	public void changeStatusTournamentForUpdateResult() {
		TournamentSchedule tournamentSchedule = tournamentScheduleService.getTournamentSessionByDate(LocalDate.now());
		if (tournamentSchedule != null) {
			Tournament tournament = tournamentSchedule.getTournament();
			tournament.setStage(3);
			logger.info("Thay đổi trạng thái của giải đấu " + tournament.getName());
			Set<CompetitiveType> listCompetitiveTypes = tournament.getCompetitiveTypes();
			for (CompetitiveType competitiveType : listCompetitiveTypes) {
				if (competitiveType.getStatus() == 2) {
					competitiveType.setStatus(3);
					logger.info("Trạng thái của " + (competitiveType.isGender() ? "Nam " : "Nữ ")
							+ competitiveType.getWeightMin() + " - " + competitiveType.getWeightMax()
							+ " thay đổi từ 2 thành 3");
				}
			}
			tournament.setCompetitiveTypes(listCompetitiveTypes);
			Set<ExhibitionType> listExhibitionTypes = tournament.getExhibitionTypes();
			for (ExhibitionType exhibitionType : listExhibitionTypes) {
				if (exhibitionType.getStatus() == 0) {
					exhibitionType.setStatus(1);
					logger.info("Trạng thái của " + exhibitionType.getName() + " thay đổi từ 2 thành 3");
				}
			}
			tournament.setExhibitionTypes(listExhibitionTypes);

			logger.info("Dừng thay đổi trạng thái của giải đấu " + tournament.getName());
			tournamentRepository.save(tournament);
		} else {
			logger.info("Không có giải đấu");
		}
	}
}
