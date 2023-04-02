package com.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.demo.login.component.DemoAuthenticationFilter;
import com.demo.login.component.DemoLoginAuthenticationHandler;
import com.navercorp.lucy.security.xss.servletfilter.XssEscapeServletFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Value("${security.login.url}")
	String loginUrl;
	
	@Value("${security.loginProc.url}")
	String loginProcUrl;
	
	@Value("${security.logout.url}")
	String logoutUrl;
	
	@Value("${page.root.url}")
	String rootPageUrl;
	
	@Value("${page.main.url}")
	String mainPageUrl;
	
	@Autowired
	private AuthenticationSuccessHandler successHandler;
	
	@Autowired
	private AuthenticationFailureHandler failureHandler;
	
	@Autowired
	private LogoutSuccessHandler logoutSuccessHandler;
	
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
		return new DemoLoginAuthenticationHandler();
	}
	
	@Bean
	public FilterRegistrationBean<XssEscapeServletFilter> filterRegistrationBean() {
		FilterRegistrationBean<XssEscapeServletFilter> filterRegistration = new FilterRegistrationBean<>();
		filterRegistration.setFilter(new XssEscapeServletFilter());
		filterRegistration.setOrder(1);
		filterRegistration.addUrlPatterns("/*");
		
		return filterRegistration;
	}
	
	@Bean
	public DemoAuthenticationFilter demoAuthenticationFilter() {
		DemoAuthenticationFilter filter = new DemoAuthenticationFilter("/loginProc.do");
		filter.setAuthenticationManager(authenticationManager());
        filter.setAuthenticationSuccessHandler(successHandler);
        filter.setAuthenticationFailureHandler(failureHandler);
        
        return filter;
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable();
		
		http.sessionManagement()
		.sessionFixation().changeSessionId()
		.maximumSessions(1)
		.expiredUrl(loginUrl)
		.sessionRegistry(sessionRegistry());
		
		http.authorizeRequests()
		.antMatchers(loginUrl).permitAll()
		.antMatchers("/lib/jquery/**", "/login/**").permitAll()
		.antMatchers("/webSocket**").access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
		.antMatchers(rootPageUrl, mainPageUrl).access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
		.anyRequest().authenticated();
		
		http.formLogin()
		.usernameParameter("userId")
		.passwordParameter("enPwd")
		.loginPage(loginUrl)
		.permitAll()
		.and()
		.addFilterBefore(demoAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
		
		http.logout()
		.logoutUrl(logoutUrl)
		.logoutSuccessHandler(logoutSuccessHandler)
		.invalidateHttpSession(true)
		.permitAll();
		
		return http.build();
	}
}
