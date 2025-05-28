package com.example.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.mapper.LoginUserMapper;
import com.example.model.LoginUser;

import lombok.RequiredArgsConstructor;



@RequiredArgsConstructor
@Transactional
@Service
public class LoginUserDetailsService implements UserDetailsService {
	private final LoginUserMapper loginUserMapper;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<LoginUser> loginUserOptional = loginUserMapper.selectByEmail(username);
		System.out.println(loginUserMapper.selectByEmail(username));
		
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		System.out.println("general");
		System.out.println(encoder.encode("general"));
		
		return loginUserOptional.map(loginUser -> new LoginUserDetails(loginUser))
				.orElseThrow(() -> new UsernameNotFoundException("not found"));
	}
}