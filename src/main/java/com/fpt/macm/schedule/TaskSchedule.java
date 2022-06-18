package com.fpt.macm.schedule;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fpt.macm.model.Role;
import com.fpt.macm.model.StatusSemester;
import com.fpt.macm.model.User;
import com.fpt.macm.repository.StatusSemesterRepository;
import com.fpt.macm.repository.UserRepository;

@Component
public class TaskSchedule {

	@Autowired
	UserRepository userRepository;

	@Autowired
	StatusSemesterRepository semesterRepository;
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
	@Scheduled(cron = "1 0 0 * * *")
	public void addMemberDeactiveBySemester() {
		List<User> users = userRepository.findMembersAndAdmin();
		if (LocalDate.now().getDayOfMonth() == 15 && LocalDate.now().getMonthValue() % 4 == 1) {
			for (User user : users) {
				StatusSemester statusSemester = new StatusSemester();
				if (!user.isActive()) {
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
					semesterRepository.save(statusSemester);
					logger.info("add oke");
				}
			}
		}
		logger.info("oke");
	}
}
