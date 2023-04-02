package com.demo.login.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.login.util.RsaUtil;

@RestController
public class LoginController {

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
}
