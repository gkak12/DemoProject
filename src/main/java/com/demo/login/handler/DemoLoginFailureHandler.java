package com.demo.login.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Component
public class DemoLoginFailureHandler implements AuthenticationFailureHandler{
	private static final Logger LOGGER = LoggerFactory.getLogger(DemoLoginFailureHandler.class);
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		LOGGER.debug(exception.getMessage());
		
		PrintWriter pw = response.getWriter();
		pw.println("<script>alert('로그인 실패했습니다.')</script>");
		pw.flush();
	}
}
