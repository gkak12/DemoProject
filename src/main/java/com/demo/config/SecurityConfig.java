package com.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.demo.login.handler.LoginAuthenticationHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private AuthenticationSuccessHandler successHandler;
	
	@Autowired
	private AuthenticationFailureHandler failureHandler;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SessionRegistry sessionRegistry() {
		return new SessionRegistryImpl();
	}

	@Bean
	public AuthenticationManager authenticationManager() {
		return new LoginAuthenticationHandler();
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable();
		
		http.authorizeRequests()
		.antMatchers("/login.do").permitAll();

//		http.sessionManagement()
//			.sessionFixation().changeSessionId()
//			.maximumSessions(1)
//			.expiredUrl("/login.do")
//			.sessionRegistry(sessionRegistry());
		
		http.formLogin()
		.usernameParameter("userId")
		.passwordParameter("userPwd")
		.loginPage("/login.do")
		.loginProcessingUrl("/loginProcess.do")
		.successHandler(successHandler)
		.failureHandler(failureHandler)
		.permitAll();
		
//		http.logout()
//		.logoutUrl("/logout.do")
//		.invalidateHttpSession(true)
//		.permitAll();
		
		return http.build();
	}
	
}
