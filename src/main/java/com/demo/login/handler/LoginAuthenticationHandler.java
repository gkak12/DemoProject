package com.demo.login.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.demo.login.repository.LoginRepository;
import com.demo.login.vo.LoginVo;

public class LoginAuthenticationHandler implements UserDetailsService, AuthenticationManager {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginAuthenticationHandler.class);

	@Resource(name = "passwordEncoder")
	private BCryptPasswordEncoder encoder;
	
	@Resource(name = "loginRepository")
	private LoginRepository loginRepository;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String userId = authentication.getName();
		String userPwd = authentication.getCredentials().toString();
		
		User user = (User) this.loadUserByUsername(userId);
		
		if(!encoder.matches(userPwd, user.getPassword())) {
			throw new BadCredentialsException("Error log: pwd is not matched.");
		}
		
		return new UsernamePasswordAuthenticationToken(user, userPwd);
	}

	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		Optional<LoginVo> loginOptional = loginRepository.findByUserId(userId);
		
		if(!loginOptional.isPresent()) {
			String errorMsg = "User is not found OR Database connection is falied.";
			LOGGER.debug(errorMsg);
			
			throw new UsernameNotFoundException(errorMsg);
		}

		LoginVo loginVo = loginOptional.get();
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		
		User user = new User(loginVo.getUserId(), loginVo.getUserPwd(), authorities);
		
		return user;
	}
}
