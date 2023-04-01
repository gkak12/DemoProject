package com.demo.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.service.DemoService;
import com.demo.vo.DemoRelation;

@RestController
public class DemoController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DemoController.class);
	
	@Resource
	private DemoService demoService;
	
	@GetMapping(value="/selectAll.json")
	public Map<String, Object> selectAll(){
		Map<String, Object> res = new HashMap<String, Object>();
		
		try {
			res.put("List", demoService.findAll());
			res.put("Msg", "SUCCESS");
		} catch (Exception e) {
			LOGGER.debug(e.toString());
			res.put("Msg", "FAIL");
		}
		
		return res;
	}
	
	@GetMapping(value="/selectById.json")
	public Map<String, Object> selectById(@RequestParam(value="id", required=true) String id){
		Map<String, Object> res = new HashMap<String, Object>();
		
		try {
			res.put("List", demoService.findById(id));
			res.put("Msg", "SUCCESS");
		} catch (Exception e) {
			LOGGER.debug(e.toString());
			res.put("Msg", "FAIL");
		}
		
		return res;
	}
	
	@PostMapping(value="/save.json")
	public Map<String, Object> save(DemoRelation d){
		Map<String, Object> res = new HashMap<String, Object>();
		
		try {
			demoService.save(d);
			res.put("Msg", "SUCCESS");
		} catch (Exception e) {
			LOGGER.debug(e.toString());
			res.put("Msg", "FAIL");
		}
		
		return res;
	}
	
	@DeleteMapping(value="/deleteById.json")
	public Map<String, Object> deleteById(@RequestParam(value="id", required=true) String id){
		Map<String, Object> res = new HashMap<String, Object>();
		
		try {
			demoService.deleteById(id);
			res.put("Msg", "SUCCESS");
		} catch (Exception e) {
			LOGGER.debug(e.toString());
			res.put("Msg", "FAIL");
		}
		
		return res;
	}
}
