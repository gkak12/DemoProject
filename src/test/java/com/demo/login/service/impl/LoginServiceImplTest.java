package com.demo.login.service.impl;

import java.util.Properties;
import java.util.UUID;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@DisplayName("LoginServiceImpl 테스트 클래스 ")
class LoginServiceImplTest {

	private static final Logger logger = LoggerFactory.getLogger(LoginServiceImplTest.class);
	
	@Test
	public void 비밀번호_초기화_이메일_테스트() {
		logger.debug("비밀번호_초기화_이메일_테스트");
		
        try {
        	String senderId = "aaa@naver.com";
    		String senderPwd = "qwe123!@#";
    		String recvId = "bbb@naver.com";
    		String tmpPwd = UUID.randomUUID().toString();
    		
    		Properties prop = new Properties();
    		prop.put("mail.smtp.host", "smtp.naver.com");
            prop.put("mail.smtp.port", 465);
            prop.put("mail.smtp.auth", "true");
            prop.put("mail.smtp.ssl.enable", "true");
            prop.put("mail.smtp.ssl.trust", "smtp.naver.com");
            
            Session session = Session.getDefaultInstance(prop, new Authenticator() {
    			@Override
    			protected PasswordAuthentication getPasswordAuthentication() {
    				return new PasswordAuthentication(senderId, senderPwd);
    			}
    		});
    		
            String encoding = "UTF-8";
            MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(senderId));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(recvId));
			message.setSubject("이메일 테스트", encoding);
			message.setText(tmpPwd, encoding);
			
			Transport.send(message);
		} catch (AddressException e) {
			logger.debug("비밀번호_초기화_테스트 에러 : "+e.toString());
		} catch (MessagingException e) {
			logger.debug("비밀번호_초기화_테스트 에러 : "+e.toString());
		} catch (Exception e) {
			logger.debug("비밀번호_초기화_테스트 에러 : "+e.toString());
		}
	}
}
