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
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.demo.login.repository.LoginRepository;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@DisplayName("LoginServiceImpl 테스트 클래스 ")
class LoginServiceImplTest {

	private static final Logger logger = LoggerFactory.getLogger(LoginServiceImplTest.class);
	
	@MockBean
	private LoginRepository loginRepository;
	
	@Test
	public void 비밀번호_초기화_테스트() {
		logger.debug("비밀번호_초기화_테스트");
		
        try {
        	String senderId = "aaa@naver.com";
    		String senderPwd = "qwe123!@#";
    		String recvId = "bbb@naver.com";
    		String tmpPwd = UUID.randomUUID().toString();
    		
    		Properties prop = new Properties();
    		prop.put("mail.smtp.host", "smtp.naver.com");
            prop.put("mail.smtp.port", 465);
            prop.put("mail.smtp.auth", "true");
            
            Session session = Session.getDefaultInstance(prop, new Authenticator() {
    			@Override
    			protected PasswordAuthentication getPasswordAuthentication() {
    				return new PasswordAuthentication(senderId, senderPwd);
    			}
    		});
    		
            MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(senderId));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(recvId));
			message.setSubject("이메일 테스트", "UTF-8");
			message.setText(tmpPwd);
			
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
