package com.demo.login.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.login.service.LoginService;
import com.demo.login.util.RsaUtil;

@RestController
public class LoginController {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
	
	@Resource(name="loginService")
	private LoginService loginService;
	
	@PostMapping(value="/login/encodePwd.json")
	public Map<String, Object> encodePassword(HttpServletRequest request){
		Map<String, Object> res = new HashMap<String, Object>();
		String[] rsaObj = RsaUtil.generateRsa(request);
		
		if(rsaObj != null) {
			res.put("Msg", "SUCCESS");
			res.put("pModules", rsaObj[0]);
			res.put("pExponent", rsaObj[1]);
		} else {
			res.put("Msg", "FAIL");
		}
		
		return res;
	}
	
	@GetMapping(value="/login/initPwd.do")
	public Map<String, String> initPwd(HttpServletRequest request){
		Map<String, String> res = new HashMap<String, String>();

		try {
			loginService.saveInitPwd(request);
			res.put("Msg", "SUCCESS");
		} catch (AddressException e) {
			LOGGER.debug(e.toString());
			res.put("Msg", "FAIL");
		} catch (MessagingException e) {
			LOGGER.debug(e.toString());
			res.put("Msg", "FAIL");
		} catch (Exception e) {
			LOGGER.debug(e.toString());
			res.put("Msg", "FAIL");
		}
		
		return res;
	}
}
