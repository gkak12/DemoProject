package com.demo.service.impl;

import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.demo.repository.DemoRepository;
import com.demo.service.DemoService;
import com.demo.vo.DemoRelation;

@Service("demoService")
public class DemoServiceImpl implements DemoService{

	@Resource(name="demoRepository")
	private DemoRepository demoRepository;
	
	@Override
	public List<DemoRelation> findAll() throws Exception {
		return demoRepository.findAll();
	}

	@Override
	public Optional<DemoRelation> findById(String id) throws Exception {
		return demoRepository.findById(id);
	}

	@Override
	public void save(DemoRelation d) throws Exception {
		demoRepository.save(d);
	}

	@Override
	public void deleteById(String id) throws Exception {
		demoRepository.deleteById(id);
	}

}
