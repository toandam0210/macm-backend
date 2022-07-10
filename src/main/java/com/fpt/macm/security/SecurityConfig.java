package com.fpt.macm.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.fpt.macm.security.user.CustomOAuth2UserService;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable().authorizeHttpRequests()
				.anyRequest().authenticated()
				.and().oauth2Login()
				.userInfoEndpoint().userService(oAuth2UserService);
	}
	
	@Autowired
	private CustomOAuth2UserService oAuth2UserService;
}
