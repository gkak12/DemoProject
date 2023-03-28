package com.demo.login.component;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class DemoLoginSuccessHandler implements AuthenticationSuccessHandler{
	private static final Logger LOGGER = LoggerFactory.getLogger(DemoLoginSuccessHandler.class);
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		HttpSession session = request.getSession();
		User user = (User) authentication.getPrincipal();
		String userId = user.getUsername();
		
		session.setAttribute("userId", userId);
		
		LOGGER.debug(userId + " is logined.");
		response.sendRedirect("/main.do");
	}
}
