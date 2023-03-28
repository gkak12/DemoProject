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
	
	@Column(name="auth")
	private String auth;
	
	@Builder
	public LoginVo(String id, String pwd, String auth) {
		this.id = id;
		this.pwd = pwd;
		this.auth = auth;
	}
}