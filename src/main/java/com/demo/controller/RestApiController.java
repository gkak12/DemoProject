package com.demo.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.util.RestUtil;

@RestController
public class RestApiController {

	@Value("${api.url}")
	private String restGetUrl;

	@GetMapping(value="/getRestApi.json")
	public Map<String, Object> testRestApi(@RequestParam Map<String, Object> params){
		Map<String, Object> res = new HashMap<String, Object>();
		
		try {
			RestUtil.getHttpsRestApi(restGetUrl, params);
			res.put("Msg", "SUCCESS");
		} catch (MalformedURLException e) {
			e.printStackTrace();
			res.put("Msg", "FAIL");
		} catch (IOException e) {
			e.printStackTrace();
			res.put("Msg", "FAIL");
		} catch (Exception e) {
			e.printStackTrace();
			res.put("Msg", "FAIL");
		}
		
		return res;
	}
}
