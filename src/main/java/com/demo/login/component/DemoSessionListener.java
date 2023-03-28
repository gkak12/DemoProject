package com.demo.login.component;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DemoSessionListener implements HttpSessionListener{

	private static final Logger LOGGER = LoggerFactory.getLogger(DemoSessionListener.class);

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		LOGGER.debug(se.getSession().getAttribute("userId") + " is sessionDestroyed.");
		
		HttpSessionListener.super.sessionDestroyed(se);
	}
}
