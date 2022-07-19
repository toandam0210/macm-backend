package com.fpt.macm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fpt.macm.model.entity.User;
import com.fpt.macm.repository.UserRepository;
import com.fpt.macm.security.CurrentUser;
import com.fpt.macm.security.UserPrincipal;
import com.fpt.macm.security.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/api")
public class LoginController {
	
	@Autowired
	UserRepository userRepository;
	
	@GetMapping("/login")
	ResponseEntity<String> getUserLogin(@CurrentUser UserPrincipal userPrincipal){
		return new ResponseEntity<String>("Oke roi day", HttpStatus.OK);
	}
	
	@GetMapping("/profile")
    public User getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        return userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
    }
	
	
}
