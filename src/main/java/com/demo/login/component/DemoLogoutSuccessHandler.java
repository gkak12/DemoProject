package com.demo.login.component;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class DemoLogoutSuccessHandler implements LogoutSuccessHandler{

	private static final Logger LOGGER = LoggerFactory.getLogger(DemoLogoutSuccessHandler.class);
	
	@Value("${security.login.url}")
	String loginUrl;
	
	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		response.sendRedirect(loginUrl);
	}
}
