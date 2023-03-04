package com.demo.service;

import java.util.List;
import java.util.Optional;

import com.demo.vo.DemoRelation;

public interface DemoService {

	public List<DemoRelation> findAll() throws Exception;

	public Optional<DemoRelation> findById(String id) throws Exception;
	
	public void save(DemoRelation d) throws Exception;
	
	public void deleteById(String id) throws Exception;
}
