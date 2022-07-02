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
        .antMatchers("/api/contact/getallcontact","/api/news/getallnews","/api/news/getnewsbyid/**","/api/rule/getallrule","/api/semester/currentsemester","/api/semester/getTop3Semesters","/api/commonschedule/**","/api/event/geteventsbyname","/api/event/geteventsbydate","/api/event/geteventsbysemester","/api/eventschedule/geteventschedule","/api/eventschedule/geteventsessionbydate","/api/eventschedule/geteventschedulebyevent/**").permitAll()
        .antMatchers("/api/login","/api/admin/hr/**","/api/rule/**").hasAnyRole("ViceHeadClub","HeadClub")
        .antMatchers("/api/login","/api/admin/treasure/membership/**","/api/admin/treasurer/getclubfund","/api/facility/treasurer/**","/api/tournament/headclub/tournament/getall","/api/tournament/treasurer/getalltournamentorganizingcommitteepaymentstatus/**","/api/tournament/headclub/tournament/**","/api/tournament/treasurer/updatetournamentorganizingcommitteepaymentstatus/**","/api/tournament/treasurer/getalltournamentorganizingcommitteepaymentstatusreport/**").hasAnyRole("Treasurer","HeadClub")
        .antMatchers("/api/login","/api/admin/headtechnique/checkattendance/report").hasAnyRole("HeadCulture","ViceHeadCulture","HeadClub")
        .antMatchers("/api/login","/api/contact/**","/api/news/**").hasAnyRole("HeadCommunication","ViceHeadCommunication","HeadClub")
        .antMatchers("/api/login","/api/facilitycategory/**","/api/facility/**","/api/trainingschedule/**").hasAnyRole("HeadTechnique","ViceHeadTechnique","HeadClub")
        .antMatchers("/api/login").hasAnyRole("MemberCommnication","MemberCulture","MemberTechnique","CollaboratorCommnunication","CollaboratorCulture","CollaboratorTechnique","HeadClub")
        .anyRequest().authenticated()
        ;
    }
}
