package com.fpt.macm.schedule;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fpt.macm.model.AdminSemester;
import com.fpt.macm.model.AttendanceEvent;
import com.fpt.macm.model.AttendanceStatus;
import com.fpt.macm.model.CollaboratorReport;
import com.fpt.macm.model.Constant;
import com.fpt.macm.model.Event;
import com.fpt.macm.model.EventSchedule;
import com.fpt.macm.model.MemberEvent;
import com.fpt.macm.model.MemberSemester;
import com.fpt.macm.model.MembershipInfo;
import com.fpt.macm.model.MembershipStatus;
import com.fpt.macm.model.Notification;
import com.fpt.macm.model.Role;
import com.fpt.macm.model.Semester;
import com.fpt.macm.model.TrainingSchedule;
import com.fpt.macm.model.User;
import com.fpt.macm.repository.AdminSemesterRepository;
import com.fpt.macm.repository.AttendanceEventRepository;
import com.fpt.macm.repository.AttendanceStatusRepository;
import com.fpt.macm.repository.CollaboratorReportRepository;
import com.fpt.macm.repository.EventRepository;
import com.fpt.macm.repository.MemberEventRepository;
import com.fpt.macm.repository.MemberSemesterRepository;
import com.fpt.macm.repository.MembershipShipInforRepository;
import com.fpt.macm.repository.MembershipStatusRepository;
import com.fpt.macm.repository.SemesterRepository;
import com.fpt.macm.repository.UserRepository;
import com.fpt.macm.service.EventScheduleServiceImpl;
import com.fpt.macm.service.EventService;
import com.fpt.macm.service.NotificationService;
import com.fpt.macm.service.SemesterService;
import com.fpt.macm.service.TrainingScheduleServiceImpl;

@Component
public class TaskSchedule {

	@Autowired
	UserRepository userRepository;

	@Autowired
	MemberEventRepository memberEventRepository;

	@Autowired
	MemberSemesterRepository stautsSemesterRepository;

	@Autowired
	AdminSemesterRepository adminSemesterRepository;

	@Autowired
	AttendanceStatusRepository attendanceStatusRepository;

	@Autowired
	AttendanceEventRepository attendanceEventRepository;

	@Autowired
	EventRepository eventRepository;

	@Autowired
	TrainingScheduleServiceImpl trainingScheduleServiceImpl;

	@Autowired
	EventScheduleServiceImpl eventScheduleServiceImpl;

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
	SemesterService semesterService;
	Logger logger = LoggerFactory.getLogger(TaskSchedule.class);

	@Scheduled(cron = "1 0 0 * * *")
	public void updateCollaboratorRole() {
		List<User> listCollaborator = userRepository.findCollaborator();
		Role role = new Role();
		CollaboratorReport collaboratorReport = new CollaboratorReport();
		int countPassed = 0;
		int countNotPassed = 0;
		int countMale = 0;
		int countFemale = 0;
		Semester semester = (Semester) semesterService.getCurrentSemester().getData().get(0);
		collaboratorReport.setNumberJoin(listCollaborator.size());
		collaboratorReport.setSemester(semester.getName());
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
					stautsSemesterRepository.save(statusSemester);
				} else {
					countNotPassed++;
					List<AttendanceStatus> attendanceStatus = attendanceStatusRepository
							.findByUserId(collaborator.getId());
					for (AttendanceStatus user : attendanceStatus) {
						attendanceStatusRepository.delete(user);
					}
					userRepository.delete(collaborator);
				}
			}
		}
		collaboratorReport.setNumberPassed(countPassed);
		collaboratorReport.setNumberNotPassed(countNotPassed);
		collaboratorReport.setNumberMale(countMale);
		collaboratorReport.setNumberFemale(countFemale);
		collaboratorReportRepository.save(collaboratorReport);
		logger.info("report oke");
	}

	@Scheduled(cron = "1 1 0 * * *")
	public void addUserBySemester() {
		if (LocalDate.now().getDayOfMonth() > 7 && LocalDate.now().getDayOfMonth() <= 14
				&& LocalDate.now().getMonthValue() % 4 == 1
				&& LocalDate.now().getDayOfWeek().toString().compareTo("MONDAY") == 0) {
			List<User> members = userRepository.findMemberWithoutPaging();
			List<User> admins = userRepository.findAllAdmin();
			for (User user : members) {
					MemberSemester statusSemester = new MemberSemester();
					statusSemester.setUser(user);
					if (LocalDate.now().getMonthValue() == 1) {
						statusSemester.setSemester("Spring" + LocalDate.now().getYear());
					}
					if (LocalDate.now().getMonthValue() == 5) {
						statusSemester.setSemester("Summer" + LocalDate.now().getYear());
					}
					if (LocalDate.now().getMonthValue() == 9) {
						statusSemester.setSemester("Fall" + LocalDate.now().getYear());
					}
					statusSemester.setStatus(user.isActive());
					stautsSemesterRepository.save(statusSemester);
					logger.info("add member oke");
			}
			for (User user : admins) {
				AdminSemester adminSemester = new AdminSemester();
				adminSemester.setUser(user);
				if (LocalDate.now().getMonthValue() == 1) {
					adminSemester.setSemester("Spring" + LocalDate.now().getYear());
				}
				if (LocalDate.now().getMonthValue() == 5) {
					adminSemester.setSemester("Summer" + LocalDate.now().getYear());
				}
				if (LocalDate.now().getMonthValue() == 9) {
					adminSemester.setSemester("Fall" + LocalDate.now().getYear());
				}
				adminSemester.setRole(user.getRole());
				adminSemesterRepository.save(adminSemester);
				logger.info("add admin oke");
			}
			logger.info("loi roi");
		}
	}

	@Scheduled(cron = "1 2 0 * * *")
	public void addListAttendanceStatus() {
		TrainingSchedule trainingSchedule = trainingScheduleServiceImpl.getTrainingSessionByDate(LocalDate.now());
		if (trainingSchedule != null) {
			List<User> users = (List<User>) userRepository.findAll();
			for (User user : users) {
				if (user.isActive()) {
					AttendanceStatus attendanceStatus = new AttendanceStatus();
					attendanceStatus.setUser(user);
					attendanceStatus.setTrainingSchedule(trainingSchedule);
					attendanceStatus.setCreatedOn(LocalDateTime.now());
					attendanceStatus.setCreatedBy("toandv");
					attendanceStatus.setStatus(2);
					attendanceStatusRepository.save(attendanceStatus);
					logger.info("atten oke");
				}
			}
		}
	}

	@Scheduled(cron = "1 2 0 * * *")
	public void addListMemberEventAttendanceStatus() {
		EventSchedule eventSchedule = eventScheduleServiceImpl.getEventSessionByDate(LocalDate.now());
		if (eventSchedule != null) {
			Event event = eventSchedule.getEvent();
			LocalDate startDate = (LocalDate) eventService.getStartDateOfEvent(event.getId()).getData().get(0);
			if (startDate.compareTo(LocalDate.now()) == 0) {
				List<MemberEvent> membersEvent = (List<MemberEvent>) memberEventRepository
						.findByEventIdOrderByIdAsc(event.getId());
				for (MemberEvent memberEvent : membersEvent) {
					if (memberEvent.isRegisterStatus()) {
						AttendanceEvent attendanceEvent = new AttendanceEvent();
						attendanceEvent.setMemberEvent(memberEvent);
						attendanceEvent.setEvent(event);
						attendanceEvent.setCreatedOn(LocalDateTime.now());
						attendanceEvent.setCreatedBy("toandv");
						attendanceEvent.setStatus(2);
						attendanceEventRepository.save(attendanceEvent);
						logger.info("atten oke");
					}
				}
			}
		}
	}

	@Scheduled(cron = "1 0 0 * * *")
	public void addListMembershipStatus() {
		if (LocalDate.now().getDayOfMonth() > 17 && LocalDate.now().getDayOfMonth() <= 24
				&& LocalDate.now().getMonthValue() % 4 == 1
				&& LocalDate.now().getDayOfWeek().toString().compareTo("MONDAY") == 0) {
			MembershipInfo membershipInfo = new MembershipInfo();
			membershipInfo.setAmount(0);
			if (LocalDate.now().getMonthValue() == 1) {
				membershipInfo.setSemester("Spring" + LocalDate.now().getYear());
			}
			if (LocalDate.now().getMonthValue() == 5) {
				membershipInfo.setSemester("Summer" + LocalDate.now().getYear());
			}
			if (LocalDate.now().getMonthValue() == 9) {
				membershipInfo.setSemester("Fall" + LocalDate.now().getYear());
			}
			membershipShipInforRepository.save(membershipInfo);
			List<User> usersActive = userRepository.findMembersActive();
			for (User user : usersActive) {
				MembershipStatus membershipStatus = new MembershipStatus();
				membershipStatus.setMembershipInfo(membershipInfo);
				membershipStatus.setUser(user);
				membershipStatus.setStatus(false);
				membershipStatusRepository.save(membershipStatus);
			}
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

	@Scheduled(cron = "1 0 0 * * *")
	public void pushNotificationBeforeEvent() {
		List<Event> listEvent = eventRepository.findAll();
		for (Event event : listEvent) {
			LocalDate getStartDate = eventService.getStartDate(event.getId());
			if (LocalDate.now().plusDays(1).isEqual(getStartDate)) {
				List<MemberEvent> membersEvent = (List<MemberEvent>) memberEventRepository
						.findByEventIdOrderByIdAsc(event.getId());
				String message = Constant.messageEvent(event);
				Notification notification = new Notification();
				notification.setMessage(message);
				for (MemberEvent member : membersEvent) {
					User user = member.getUser();
					notificationService.sendNotificationToAnUser(user, notification);
				}
			}
			break;
		}
	}

	@Scheduled(cron = "1 59 23 * * *")
	public void changeStatusAttendanceTraining() {
		TrainingSchedule trainingSchedule = trainingScheduleServiceImpl.getTrainingSessionByDate(LocalDate.now());
		if (trainingSchedule != null) {
			List<AttendanceStatus> listAttendanceStatus = attendanceStatusRepository
					.findByTrainingScheduleId(trainingSchedule.getId());
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
		EventSchedule eventSchedule = eventScheduleServiceImpl.getEventSessionByDate(LocalDate.now());
		if (eventSchedule != null) {
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
}
