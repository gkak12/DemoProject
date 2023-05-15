package com.demo.util;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("mailUtil")
public class MailUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(MailUtil.class);
	
	@Value("${mail.sender.id}")
	private String senderId;
	
	@Value("${mail.sender.pwd}")
	private String senderPwd;
	
	@Value("${mail.sender.host}")
	private String senderHost;
	
	@Value("${mail.sender.port}")
	private int senderPort;
	
	@Value("${mail.sender.auth}")
	private String senderAuth;
	
	@Value("${mail.sender.ssl.enable}")
	private String senderSslEnable;
	
	@Value("${mail.sender.ssl.host}")
	private String senderSslHost;
	
	public void sendTextEmail(String recvEmail, String subject, String content) throws AddressException, MessagingException, Exception{
		LOGGER.debug(recvEmail + ", " + subject + ", " + content);
		
		Properties prop = new Properties();
		prop.put("mail.smtp.host", senderHost);
        prop.put("mail.smtp.port", senderPort);
        prop.put("mail.smtp.auth", senderAuth);
        prop.put("mail.smtp.ssl.enable", senderSslEnable);
        prop.put("mail.smtp.ssl.trust", senderSslHost);
        
        Session session = Session.getDefaultInstance(prop, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(senderId, senderPwd);
			}
		});
		
        MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(senderId));
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(recvEmail));
		message.setSubject(subject, "UTF-8");
		message.setText(content, "UTF-8");
		
		Transport.send(message);
	}
}
