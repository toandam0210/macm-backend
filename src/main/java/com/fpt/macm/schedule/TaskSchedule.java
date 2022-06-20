package com.fpt.macm.schedule;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fpt.macm.model.Role;
import com.fpt.macm.model.Semester;
import com.fpt.macm.model.TrainingSchedule;
import com.fpt.macm.model.AdminSemester;
import com.fpt.macm.model.AttendanceStatus;
import com.fpt.macm.model.MemberSemester;
import com.fpt.macm.model.MembershipInfo;
import com.fpt.macm.model.MembershipStatus;
import com.fpt.macm.model.User;
import com.fpt.macm.repository.AdminSemesterRepository;
import com.fpt.macm.repository.AttendanceStatusRepository;
import com.fpt.macm.repository.MembershipShipInforRepository;
import com.fpt.macm.repository.MembershipStatusRepository;
import com.fpt.macm.repository.SemesterRepository;
import com.fpt.macm.repository.StatusSemesterRepository;
import com.fpt.macm.repository.UserRepository;
import com.fpt.macm.service.TrainingScheduleServiceImpl;

@Component
public class TaskSchedule {

	@Autowired
	UserRepository userRepository;

	@Autowired
	StatusSemesterRepository stautsSemesterRepository;

	@Autowired
	AdminSemesterRepository adminSemesterRepository;

	@Autowired
	AttendanceStatusRepository attendanceStatusRepository;

	@Autowired
	TrainingScheduleServiceImpl trainingScheduleServiceImpl;

	@Autowired
	MembershipStatusRepository membershipStatusRepository;

	@Autowired
	SemesterRepository semesterRepository;

	@Autowired
	MembershipShipInforRepository membershipShipInforRepository;
	Logger logger = LoggerFactory.getLogger(TaskSchedule.class);

	@Scheduled(cron = "* 0 0 * * *")
	public void updateCollaboratorRole() {
		List<User> listCollaborator = userRepository.findCollaborator();
		Role role = new Role();
		for (User collaborator : listCollaborator) {
			if (LocalDate.now().compareTo(collaborator.getCreatedOn().plusMonths(1).plusDays(1)) == 0) {
				if (collaborator.isActive()) {
					role.setId(collaborator.getRole().getId() - 3);
					collaborator.setRole(role);
					userRepository.save(collaborator);
				} else {
					userRepository.delete(collaborator);
				}
			}
		}
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
		}
	}

	@Scheduled(cron = "1 2 0 * * *")
	public void addListAttendanceStatus() {
		TrainingSchedule trainingSchedule = (TrainingSchedule) trainingScheduleServiceImpl.getTrainingSessionByDate(LocalDate.now()).getData().get(0);
		if (trainingSchedule != null) {
			List<User> users = (List<User>) userRepository.findAll();
			for (User user : users) {
				AttendanceStatus attendanceStatus = new AttendanceStatus();
				attendanceStatus.setUser(user);
				attendanceStatus.setTrainingSchedule(trainingSchedule);
				attendanceStatus.setCreatedOn(LocalDateTime.now());
				attendanceStatus.setCreatedBy("toandv");
				attendanceStatus.setStatus(false);
				attendanceStatusRepository.save(attendanceStatus);
			}
		}
	}

	@Scheduled(cron = "1 3 0 * * *")
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

	@Scheduled(cron = "1 4 0 * * *")
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
}
