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
		LOGGER.debug(username);

		String realPass = null;
		
		try {
			realPass = decodePassword(request);
		} catch (NoSuchAlgorithmException e) {
			LOGGER.debug(e.toString());
			throw new AuthenticationException(e.toString());
		}
		
		if(realPass == null || "".equals(realPass)) {
			String errMsg = "decoding password is failed.";
			
			LOGGER.debug(errMsg);
			throw new AuthenticationException(errMsg);
		}
		
		return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(username, realPass));
	}
	
	private String decodePassword(HttpServletRequest request) throws NoSuchAlgorithmException {
		String enUserPwd = request.getParameter("enUserPwd");	// form에서 전달하는 encode 비밀번호 파라미터 명
		LOGGER.debug(enUserPwd);
		
		HttpSession session = request.getSession();
		PrivateKey privateKey = (PrivateKey) session.getAttribute("rsaKey");
		String realPass = RsaUtil.decryptRsa(privateKey, enUserPwd);
		session.removeAttribute("rsaKey");
	
		return realPass;
	}
}
