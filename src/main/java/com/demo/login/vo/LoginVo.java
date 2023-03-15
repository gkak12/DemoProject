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
@Table(schema="navy", name="user_info")
@Data
@NoArgsConstructor(access=AccessLevel.PROTECTED)
public class LoginVo {
	@Id
	@Column(name="id", nullable=false)
	private String userId;
	
	@Column(name="pwd")
	private String userPwd;
	
	@Builder
	public LoginVo(String userId, String userPwd) {
		this.userId = userId;
		this.userPwd = userPwd;
	}
}
