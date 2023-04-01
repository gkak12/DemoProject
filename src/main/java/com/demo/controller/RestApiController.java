package com.demo.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.util.RestUtil;

@RestController
public class RestApiController {

	private static final Logger LOGGER = LoggerFactory.getLogger(RestApiController.class);
	
	@Value("${api.get.url}")
	private String apiGetUrl;
	
	@Value("${api.post.url}")
	private String apiPostUrl;

	@GetMapping(value="/getRestApi.json")
	public Map<String, Object> testGetRestApi(@RequestParam Map<String, Object> params){
		Map<String, Object> res = new HashMap<String, Object>();
		
		try {
			res.put("Data", RestUtil.getHttpsRestApi(apiGetUrl, params));
			res.put("Msg", "SUCCESS");
		} catch (MalformedURLException e) {
			LOGGER.debug(e.toString());
			res.put("Msg", "FAIL");
		} catch (IOException e) {
			LOGGER.debug(e.toString());
			res.put("Msg", "FAIL");
		} catch (Exception e) {
			LOGGER.debug(e.toString());
			res.put("Msg", "FAIL");
		}
		
		return res;
	}
	
	@PostMapping(value="/postRestApi.json")
	public Map<String, Object> testPostRestApi(@RequestParam Map<String, Object> params, HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> res = new HashMap<String, Object>();
		
		try {
			res.put("Data", RestUtil.postHttpsRestApi(apiPostUrl, params));
			res.put("Msg", "SUCCESS");
		} catch (MalformedURLException e) {
			LOGGER.debug(e.toString());
			res.put("Msg", "FAIL");
		} catch (IOException e) {
			LOGGER.debug(e.toString());
			res.put("Msg", "FAIL");
		} catch (Exception e) {
			LOGGER.debug(e.toString());
			res.put("Msg", "FAIL");
		}
		
		return res;
	}
}
