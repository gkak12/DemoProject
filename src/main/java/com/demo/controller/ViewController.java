package com.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

	@GetMapping(value="/main.do")
	public String getMainPage() {
		return "main";
	}
	
	@GetMapping(value="/login.do")
	public String getLoginPage() {
		return "login";
	}
}
