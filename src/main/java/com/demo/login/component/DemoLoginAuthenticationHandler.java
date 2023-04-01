package com.demo.login.component;

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
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.demo.login.repository.LoginRepository;
import com.demo.login.vo.LoginVo;

public class DemoLoginAuthenticationHandler implements UserDetailsService, AuthenticationManager {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DemoLoginAuthenticationHandler.class);

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
			throw new BadCredentialsException("password is not matched.");
		}
		
		return new UsernamePasswordAuthenticationToken(user, userPwd, user.getAuthorities());
	}

	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		Optional<LoginVo> loginOptional = loginRepository.findById(userId);
		
		if(!loginOptional.isPresent()) {
			String errorMsg = "User is not found OR Database connection is falied.";
			LOGGER.debug(errorMsg);
			
			throw new UsernameNotFoundException(errorMsg);
		}

		LoginVo loginVo = loginOptional.get();
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add( new SimpleGrantedAuthority( loginVo.getAuth().toString() ) );
		User user = new User(loginVo.getId(), loginVo.getPwd(), authorities);
		
		return user;
	}
}
