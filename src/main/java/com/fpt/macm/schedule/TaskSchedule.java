package com.fpt.macm.schedule;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fpt.macm.model.Role;
import com.fpt.macm.model.User;
import com.fpt.macm.repository.UserRepository;

@Component
public class TaskSchedule {
	
	@Autowired 
	UserRepository userRepository;
	
	@Scheduled(cron = "* 0 0 * * *")
	public void updateCollaboratorRole() {
		List<User> listCollaborator = userRepository.findCollaborator();
		Role role = new Role();
		for (User collaborator : listCollaborator) {
			if(LocalDate.now().compareTo(collaborator.getCreatedOn().plusMonths(1).plusDays(1)) == 0) {
				if(collaborator.isActive()) {
					role.setId(collaborator.getRole().getId() - 3);
					collaborator.setRole(role);
					userRepository.save(collaborator);
				}else {
					userRepository.delete(collaborator);
				}
			}
		}
	}
}
