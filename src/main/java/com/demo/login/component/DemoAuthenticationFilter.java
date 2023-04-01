package com.demo.login.component;

import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;

import javax.security.sasl.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import com.demo.login.util.RsaUtil;

public class DemoAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	private static final Logger LOGGER = LoggerFactory.getLogger(DemoAuthenticationFilter.class);
	
	public DemoAuthenticationFilter(String processesUrl) {
		super(processesUrl);
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		String username = request.getParameter("userId");
		String password = request.getParameter("userPwd");
		LOGGER.debug("============== " + username + ", " + password + " ==============");

//		try {
//			realPass = decodePassword(request);
//		} catch (NoSuchAlgorithmException e) {
//			LOGGER.debug(e.toString());
//		}
		
		return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(username, password));
	}
	
	private String decodePassword(HttpServletRequest request) throws NoSuchAlgorithmException {
		String password = request.getParameter("userPwd");	// form에서 전달하는 비밀번호 파라미터 명
		
		HttpSession session = request.getSession();
		PrivateKey privateKey = (PrivateKey) session.getAttribute("rsaKey");
		String realPass = RsaUtil.decryptRsa(privateKey, password);
		session.removeAttribute("rsaKey");
	
		return realPass;
	}
}
