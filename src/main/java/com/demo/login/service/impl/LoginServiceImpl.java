package com.demo.login.service.impl;

import java.util.Optional;
import java.util.UUID;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.demo.login.repository.LoginRepository;
import com.demo.login.service.LoginService;
import com.demo.login.vo.LoginVo;
import com.demo.util.MailUtil;

@Service("loginService")
public class LoginServiceImpl implements LoginService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginServiceImpl.class);
	
	@Resource(name="mailUtil")
	private MailUtil mailUtil;
	
	@Resource(name="loginRepository")
	private LoginRepository loginRepository;

	@Override
	public void saveInitPwd(HttpServletRequest request) throws AddressException, MessagingException, Exception {
		String userId = request.getSession().getAttribute("userId").toString();
		
		Optional<LoginVo> loginVo = loginRepository.findById(userId);
		String recvEmail = loginVo.get().getEmail();

		String tmpSuject = userId.concat(" 비밀번호 초기화");
		String tmpPwd = UUID.randomUUID().toString();
		
		mailUtil.sendTextEmail(recvEmail, tmpSuject, tmpPwd);
		
		LOGGER.debug(userId + "'s password is inited.");
	}
}
