//package com.fpt.macm.security;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.oauth2.client.OAuth2RestTemplate;
//import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
//import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
//import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
//
//import com.fpt.macm.security.oauth2.OpenIdConnectFilter;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//	@Autowired
//    private OAuth2RestTemplate restTemplate;
//
//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers("/resources/**");
//    }
//
//    @Bean
//    public OpenIdConnectFilter myFilter() {
//        final OpenIdConnectFilter filter = new OpenIdConnectFilter("/login/oauth2/code/google");
//        filter.setRestTemplate(restTemplate);
//        return filter;
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//        .addFilterAfter(new OAuth2ClientContextFilter(), AbstractPreAuthenticatedProcessingFilter.class)
//        .addFilterAfter(myFilter(), OAuth2ClientContextFilter.class)
//        .httpBasic().authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login/oauth2/code/google"))
//        .and()
//        .authorizeRequests()
//        .antMatchers("/api/**").hasRole("HeadClub")
//        .antMatchers("/api/login","/api/admin/hr/**","/api/rule/**").hasRole("ViceHeadClub")
//        .antMatchers("/api/login").hasRole("Treasurer")
//        .antMatchers("/api/login").hasRole("HeadCulture")
//        .antMatchers("/api/login").hasRole("ViceHeadCulture")
//        .antMatchers("/api/login","/api/contact/**","/api/news/**").hasRole("HeadCommunication")
//        .antMatchers("/api/login","/api/contact/**","/api/news/**").hasRole("ViceHeadCommunication")
//        .antMatchers("/api/login","/api/facilitycategory/**","/api/facility/**","/api/trainingschedule/**").hasRole("HeadTechnique")
//        .antMatchers("/api/login","/api/facilitycategory/**","/api/facility/**","/api/trainingschedule/**").hasRole("ViceHeadTechnique")
//        .antMatchers("/api/login").hasRole("MemberCommnication")
//        .antMatchers("/api/login").hasRole("MemberCulture")
//        .antMatchers("/api/login").hasRole("MemberTechnique")
//        .antMatchers("/api/login").hasRole("CollaboratorCommnunication")
//        .antMatchers("/api/login").hasRole("CollaboratorCulture")
//        .antMatchers("/api/login").hasRole("CollaboratjrTechnique")
//        .antMatchers("/api/login","/api/contact/getallcontact","/api/news/getallnews","/api/news/getnewsbyid/**","/api/rule/getallrule").permitAll()
//        .anyRequest().authenticated()
//        ;
//    }
//}