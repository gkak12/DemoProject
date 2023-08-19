package com.demo.webSocket;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component("webSocketHandler")
public class WebSocketHandler extends TextWebSocketHandler{

	private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketHandler.class);
	
	private final List<WebSocketSession> sessionList = new ArrayList<WebSocketSession>();
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		sessionList.add(session);
		LOGGER.debug(session.getId() + " is connected.");
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		if(message == null) {
			String errMsg = "webSocket message is null.";
			
			LOGGER.debug(errMsg);
			throw new Exception(errMsg);
		}
		
		for(WebSocketSession wSession : sessionList) {
			if(wSession.getId().equals(session.getId())) {
				LOGGER.debug(session.getId() + " sends " + message.getPayload());
				session.sendMessage(new TextMessage("reply-" + message.getPayload()));
				
				break;
			}
		}
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		sessionList.remove(session);
		LOGGER.debug(session.getId() + " is disconnected.");
	}
}
