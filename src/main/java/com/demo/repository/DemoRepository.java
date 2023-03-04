package com.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.vo.DemoRelation;

@Repository("demoRepository")
public interface DemoRepository extends JpaRepository<DemoRelation, String>{

	public List<DemoRelation> findAll();
	
	public Optional<DemoRelation> findById(String id);
	
	@SuppressWarnings("unchecked")
	public DemoRelation save(DemoRelation d);
	
	public void deleteById(String id);
}
