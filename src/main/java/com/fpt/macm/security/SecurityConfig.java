package com.fpt.macm.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fpt.macm.security.oauth2.CustomOAuth2UserService;
import com.fpt.macm.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository;
import com.fpt.macm.security.oauth2.OAuth2AuthenticationFailureHandler;
import com.fpt.macm.security.oauth2.OAuth2AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;

    @Autowired
    private OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

    @Autowired
    private OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;


    @Bean
    public HttpCookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository() {
        return new HttpCookieOAuth2AuthorizationRequestRepository();
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                    .and()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                .csrf()
                    .disable()
                .formLogin()
                    .disable()
                .httpBasic()
                    .disable()
                .exceptionHandling()
                    .authenticationEntryPoint(new RestAuthenticationEntryPoint())
                    .and()
                .authorizeRequests()
                    .antMatchers("/",
                        "/error",
                        "/favicon.ico",
                        "/**/*.png",
                        "/**/*.gif",
                        "/**/*.svg",
                        "/**/*.jpg",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js")
                        .permitAll()
                        .antMatchers("/api/contact/getallcontact","/api/contact/getallsocialnetwork","/api/news/getallnews","/api/news/getnewsbyid/**","/api/rule/getallrule","/api/semester/currentsemester","/api/semester/getTop3Semesters","/api/commonschedule/**","/api/event/geteventsbyname","/api/event/geteventsbydate","/api/event/geteventsbysemester","/api/eventschedule/geteventschedule","/api/eventschedule/geteventsessionbydate","/api/eventschedule/geteventschedulebyevent/**","/api/role/getroles","/api/trainingschedule/gettrainingschedule","/api/trainingschedule/gettrainingschedulebysemester/**","/api/trainingschedule/gettrainingsesionbydate").permitAll()
                        .antMatchers("/api/login","/api/tournament/headclub/**","/api/tournamentschedule/headclub/**").hasAnyRole("HeadClub")
                        .antMatchers("/api/login","/api/admin/hr/**","/api/rule/**","/api/role/viceheadclub/**").hasAnyRole("ViceHeadClub","HeadClub")
                        .antMatchers("/api/login","/api/admin/treasurer/**","/api/facility/treasurer/**","/api/event/treasurer/**","/api/tournament/treasurer/**","/api/admin/hr/getbystudentid/**","/api/admin/hr/updateuser/**","/api/admin/hr/users/search","/api/admin/hr/users/export").hasAnyRole("Treasurer","HeadClub")
                        .antMatchers("/api/login","/api/event/headculture/**","/api/eventschedule/headculture/**","/api/admin/hr/getbystudentid/**","/api/admin/hr/updateuser/**","/api/admin/hr/users/search","/api/admin/hr/users/export").hasAnyRole("HeadCulture","ViceHeadCulture","HeadClub")
                        .antMatchers("/api/login","/api/contact/**","/api/news/**","/api/admin/hr/getbystudentid/**","/api/admin/hr/updateuser/**","/api/admin/hr/users/search","/api/admin/hr/users/export").hasAnyRole("HeadCommunication","ViceHeadCommunication","HeadClub")
                        .antMatchers("/api/login","/api/facilitycategory/headtechnique/**","/api/facility/headtechnique/**","/api/facility/treasurer/getallrequesttobuyfacility","/api/trainingschedule/**", "/api/admin/headtechnique/**","/api/admin/hr/getbystudentid/**","/api/admin/hr/updateuser/**","/api/admin/hr/users/search","/api/admin/hr/users/export").hasAnyRole("HeadTechnique","ViceHeadTechnique","HeadClub")
                        .antMatchers("/api/login","/api/admin/hr/getbystudentid/**","/api/admin/hr/updateuser/**").hasAnyRole("MemberCommnication","MemberCulture","MemberTechnique","CollaboratorCommnunication","CollaboratorCulture","CollaboratorTechnique","HeadClub")
                    .anyRequest()
                        .authenticated()
                    .and()
                .oauth2Login()
                    .authorizationEndpoint()
                        .baseUri("/oauth2/authorize")
                        .authorizationRequestRepository(cookieAuthorizationRequestRepository())
                        .and()
                    .redirectionEndpoint()
                        .baseUri("/oauth2/code/google")
                        .and()
                    .userInfoEndpoint()
                        .userService(customOAuth2UserService)
                        .and()
                    .successHandler(oAuth2AuthenticationSuccessHandler)
                    .failureHandler(oAuth2AuthenticationFailureHandler);

    }
}
