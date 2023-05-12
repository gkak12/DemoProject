package com.demo.login.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(schema="public", name="user_info")
@Data
@NoArgsConstructor(access=AccessLevel.PROTECTED)
public class LoginVo {
	@Id
	@Column(name="id", nullable=false)
	private String id;
	
	@Column(name="pwd")
	private String pwd;
	
	@Column(name="name")
	private String name;
	
	@Column(name="auth")
	private String auth;
	
	@Column(name="email")
	private String email;
	
	@Builder
	public LoginVo(String id, String pwd, String name, String auth, String email) {
		this.id = id;
		this.pwd = pwd;
		this.name = name;
		this.auth = auth;
		this.email = email;
	}
}
