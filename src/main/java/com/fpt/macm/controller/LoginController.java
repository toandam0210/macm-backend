package com.fpt.macm.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class LoginController {
	
	@GetMapping("/user")
	public Principal getUserLogin(Principal principal){
		return principal;
	}
}
