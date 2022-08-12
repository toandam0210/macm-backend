package com.fpt.macm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fpt.macm.security.RestAuthenticationEntryPoint;
import com.fpt.macm.security.TokenAuthenticationFilter;
import com.fpt.macm.security.oauth2.CustomOAuth2UserService;
import com.fpt.macm.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository;
import com.fpt.macm.security.oauth2.OAuth2AuthenticationFailureHandler;
import com.fpt.macm.security.oauth2.OAuth2AuthenticationSuccessHandler;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomOAuth2UserService customOAuth2UserService;

	@Autowired
	private OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

	@Autowired
	private OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

	@Autowired
	private HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

	@Bean
	public TokenAuthenticationFilter tokenAuthenticationFilter() {
		return new TokenAuthenticationFilter();
	}

	/*
	 * By default, Spring OAuth2 uses
	 * HttpSessionOAuth2AuthorizationRequestRepository to save the authorization
	 * request. But, since our service is stateless, we can't save it in the
	 * session. We'll save the request in a Base64 encoded cookie instead.
	 */
	@Bean
	public HttpCookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository() {
		return new HttpCookieOAuth2AuthorizationRequestRepository();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**",
				"/configuration/security", "/swagger-ui/**", "/webjars/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().csrf()
				.disable().formLogin().disable().httpBasic().disable().exceptionHandling()
				.authenticationEntryPoint(new RestAuthenticationEntryPoint()).and().authorizeRequests()
				.antMatchers("/", "/error", "/favicon.ico", "/**/*.png", "/**/*.gif", "/**/*.svg", "/**/*.jpg",
						"/**/*.html", "/**/*.css", "/**/*.js")
				.permitAll().antMatchers("/auth/**", "/oauth2/**").permitAll()
				// permit all
				.antMatchers("/api/tournamentschedule/getalltournamentschedule","/api/admin/dashboard/getallupcomingactivities", "/api/contact/getallcontact",
						"/api/contact/getallsocialnetwork", "/api/rule/getallrule", "/api/semester/currentsemester",
						"/api/semester/getTop3Semesters", "/api/semester/getlistmonths", "/api/commonschedule/**",
						"/api/event/geteventbyid/**", "/api/event/geteventsbyname", "/api/event/geteventsbydate",
						"/api/event/geteventsbysemester", "/api/event/geteventsbysemesterandstudentid/**",
						"/api/event/getalleventhasjoinedbystudentid/**", "/api/event/getallupcomingevent",
						"/api/event/getallongoingevent", "/api/event/getallorganizingcommitteerolebyeventid/**",
						"/api/event/getallrolebyeventid/**", "/api/event/getallclosedevent",
						"/api/eventschedule/geteventschedule", "/api/eventschedule/geteventsessionbydate",
						"/api/eventschedule/geteventschedulebyevent/**", "/api/role/getroles",
						"/api/trainingschedule/gettrainingschedule",
						"/api/trainingschedule/gettrainingschedulebysemester/**",
						"/api/trainingschedule/gettrainingsesionbydate", "/api/profile",
						"/api/event/registertojoinevent/**", "/api/event/registertojoinorganizingcommittee/**",
						"/api/event/canceltojoinevent/**", "/api/notification/getallnotificationbystudentid/**",
						"/api/notification/getallunreadnotificationbystudentid/**",
						"/api/notification/marknotificationasread/**", "/api/notification/markallnotificationasread/**",
						"/api/notification/checkpaymentstatus/**", "/api/tournament/headclub/tournament/**",
						"/api/tournament/registertojoinorganizingcommittee/**",
						"/api/tournament/registertojointournamentcompetitivetype/**",
						"/api/tournament/registertojointournamentexhibitiontype/**",
						"/api/tournament/getallusercompetitiveplayer/**",
						"/api/tournament/getalluserexhibitionplayer/**",
						"/api/tournament/getalluserorganizingcommittee/**",
						"/api/tournament/getalltournamentbystudentid/**",
						"/api/tournament/getallcompetitiveplayerbytype/**",
						"/api/tournament/treasurer/getallcompetitivetype/**",
						"/api/tournament/headclub/getallexhibitiontype/**",
						"/api/tournament/headclub/getalltournamentorganizingcommittee/**",
						"/api/tournament/headclub/getallorganizingcommitteerole",
						"/api/tournamentschedule/headclub/tournamentschedule/**", "/api/admin/hr/member/qrcode/create",
						"/api/admin/hr/getallattendancestatusofuser/**", "/api/event/getalleventbystudentid/**",
						"/api/tournament/headclub/getallorganizingcommitteerole",
						"/api/tournament/headclub/getallcompetitiveplayer/**",
						"/api/tournament/headclub/getallexhibitionteam/**",
						"/api/admin/headtechnique/getallattendancestatusbystudentidandsemester/**",
						"/api/exhibition/getlistexhibitiontype/**", "/api/exhibition/getlistexhibitionresult",
						"/api/admin/hr/getbystudentid/**", "/api/admin/hr/getallactivememberandcollaborator",
						"/api/admin/hr/updateuser/**", "/api/competitive/headclub/listmatchs/**",
						"/api/tournament/gettournamentresult/**")
				.permitAll()
//                // HeadClub
//                .antMatchers("/api/tournament/headclub/**","/api/tournamentschedule/headclub/**","/api/area/headclub/**","/api/competitive/headclub/**","/api/exhibition/headclub/**","/api/admin/dashboard/**").hasAnyRole("HeadClub")
//                // ViceHead
//                .antMatchers("/api/admin/hr/**","/api/rule/**","/api/role/viceheadclub/**","/api/admin/dashboard/**").hasAnyRole("ViceHeadClub","HeadClub")
//                // Treasurer
//                .antMatchers("/api/admin/treasurer/**","/api/event/treasurer/**","/api/tournament/treasurer/**","/api/admin/hr/getbystudentid/**","/api/admin/hr/updateuser/**","/api/admin/hr/users/search","/api/admin/hr/users/export","/api/admin/dashboard/**").hasAnyRole("Treasurer","HeadClub")
//                // HeadCulture
//                .antMatchers("/api/event/headculture/**","/api/eventschedule/headculture/**","/api/admin/hr/getbystudentid/**","/api/admin/hr/updateuser/**","/api/admin/hr/users/search","/api/admin/hr/users/export","/api/admin/dashboard/**").hasAnyRole("HeadCulture","HeadClub","ViceHeadCulture")
//                // HeadCommunication
//                .antMatchers("/api/contact/**","/api/admin/hr/getbystudentid/**","/api/admin/hr/updateuser/**","/api/admin/hr/users/search","/api/admin/hr/users/export","/api/admin/dashboard/**").hasAnyRole("HeadCommunication","ViceHeadCommunication","HeadClub")
//                // HeadTechnique
//                .antMatchers("/api/trainingschedule/**", "/api/admin/headtechnique/**","/api/admin/hr/getbystudentid/**","/api/admin/hr/users/search","/api/admin/hr/users/export","/api/admin/dashboard/**").hasAnyRole("HeadTechnique","ViceHeadTechnique","HeadClub")
//                // Member & Collaborator
//                .antMatchers("/api/admin/hr/getbystudentid/**","/api/admin/hr/updateuser/**","/api/admin/hr/getallactivememberandcollaborator").hasAnyRole("MemberCommnication","MemberCulture","MemberTechnique","CollaboratorCommnunication","CollaboratorCulture","CollaboratorTechnique","HeadClub")
//                .antMatchers("/api/admin/headtechnique/takeattendance/**","/api/admin/headtechnique/checkattendance/**","/api/event/headculture/takeattendanceevent/**","/api/event/headculture/checkattendance/**","/api/admin/hr/viceheadclub/getmembers/semester","/api/admin/hr/viceheadclub/getadmins/**","/api/admin/hr/viceheadclub/member/search").hasAnyRole("HeadCommunication","HeadClub","ViceHeadClub","Treasurer","HeadCulture","ViceHeadCulture","ViceHeadCommunication","HeadTechnique","ViceHeadTechnique")
				.anyRequest().authenticated().and().oauth2Login().authorizationEndpoint().baseUri("/oauth2/authorize")
				.authorizationRequestRepository(cookieAuthorizationRequestRepository()).and().redirectionEndpoint()
				.baseUri("/oauth2/callback/*").and().userInfoEndpoint().userService(customOAuth2UserService).and()
				.successHandler(oAuth2AuthenticationSuccessHandler).failureHandler(oAuth2AuthenticationFailureHandler);

		// Add our custom Token based authentication filter
		http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
	}
}
