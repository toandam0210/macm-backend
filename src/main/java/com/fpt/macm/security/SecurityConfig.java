package com.fpt.macm.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import com.fpt.macm.security.oauth2.OpenIdConnectFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
    private OAuth2RestTemplate restTemplate;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**");
    }

    @Bean
    public OpenIdConnectFilter myFilter() {
        final OpenIdConnectFilter filter = new OpenIdConnectFilter("/login/oauth2/code/google");
        filter.setRestTemplate(restTemplate);
        return filter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
        .addFilterAfter(new OAuth2ClientContextFilter(), AbstractPreAuthenticatedProcessingFilter.class)
        .addFilterAfter(myFilter(), OAuth2ClientContextFilter.class)
        .httpBasic().authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login/oauth2/code/google"))
        .and()
        .authorizeRequests()
        .antMatchers("/api/contact/getallcontact","/api/contact/getallsocialnetwork","/api/news/getallnews","/api/news/getnewsbyid/**","/api/rule/getallrule","/api/semester/currentsemester","/api/semester/getTop3Semesters","/api/commonschedule/**","/api/event/geteventsbyname","/api/event/geteventsbydate","/api/event/geteventsbysemester","/api/eventschedule/geteventschedule","/api/eventschedule/geteventsessionbydate","/api/eventschedule/geteventschedulebyevent/**","/api/role/getroles","/api/trainingschedule/gettrainingschedule","/api/trainingschedule/gettrainingschedulebysemester/**","/api/trainingschedule/gettrainingsesionbydate").permitAll()
        .antMatchers("/api/login","/api/tournament/headclub/**","/api/tournamentschedule/headclub/**").hasAnyRole("HeadClub")
        .antMatchers("/api/login","/api/admin/hr/**","/api/rule/**","/api/role/viceheadclub/**").hasAnyRole("ViceHeadClub","HeadClub")
        .antMatchers("/api/login","/api/admin/treasurer/**","/api/facility/treasurer/**","/api/event/treasurer/**","/api/tournament/treasurer/**","/api/admin/hr/getbystudentid/**","/api/admin/hr/updateuser/**","/api/admin/hr/users/search","/api/admin/hr/users/export").hasAnyRole("Treasurer","HeadClub")
        .antMatchers("/api/login","/api/event/headculture/**","/api/eventschedule/headculture/**","/api/admin/hr/getbystudentid/**","/api/admin/hr/updateuser/**","/api/admin/hr/users/search","/api/admin/hr/users/export").hasAnyRole("HeadCulture","ViceHeadCulture","HeadClub")
        .antMatchers("/api/login","/api/contact/**","/api/news/**","/api/admin/hr/getbystudentid/**","/api/admin/hr/updateuser/**","/api/admin/hr/users/search","/api/admin/hr/users/export").hasAnyRole("HeadCommunication","ViceHeadCommunication","HeadClub")
        .antMatchers("/api/login","/api/facilitycategory/headtechnique/**","/api/facility/headtechnique/**","/api/facility/treasurer/getallrequesttobuyfacility","/api/trainingschedule/**", "/api/admin/headtechnique/**","/api/admin/hr/getbystudentid/**","/api/admin/hr/updateuser/**","/api/admin/hr/users/search","/api/admin/hr/users/export").hasAnyRole("HeadTechnique","ViceHeadTechnique","HeadClub")
        .antMatchers("/api/login","/api/admin/hr/getbystudentid/**","/api/admin/hr/updateuser/**").hasAnyRole("MemberCommnication","MemberCulture","MemberTechnique","CollaboratorCommnunication","CollaboratorCulture","CollaboratorTechnique","HeadClub")
        .anyRequest().authenticated()
        ;
    }
}
