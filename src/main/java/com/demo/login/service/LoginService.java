package com.demo.login.service;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletRequest;

public interface LoginService {
	
	/**
	 * 사용자 비밀번호 초기화하고
	 * 사용자 이메일로 초기화 비밀번호 전송
	 * 
	 * @param request
	 * @throws AddressException
	 * @throws MessagingException
	 * @throws Exception
	 */
	public void saveInitPwd(HttpServletRequest request) throws AddressException, MessagingException, Exception;
}
