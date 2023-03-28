package com.demo.login.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.login.vo.LoginVo;

@Repository("loginRepository")
public interface LoginRepository extends JpaRepository<LoginVo, String>{

	public Optional<LoginVo> findById(String id);
}
