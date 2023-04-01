package com.demo.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DemoErrorController implements ErrorController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DemoController.class);
	
	@RequestMapping(value = "/error")
	public String handleError(HttpServletRequest request) {
		String errorPage = "default";
		Integer errorCode = Integer.valueOf( request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE).toString() );
		
		LOGGER.debug(errorCode.toString());
		
		switch(errorCode) {
			case 400:
				errorPage = "error/400";
				break;
			case 401:
				errorPage = "error/401";
				break;
			case 403:
				errorPage = "error/403";
				break;
			case 404:
				errorPage = "error/404";
				break;
			case 405:
				errorPage = "error/405";
				break;
			case 429:
				errorPage = "error/429";
				break;
			case 500:
				errorPage = "error/500";
				break;
		}
		
		return errorPage;
	}
}
