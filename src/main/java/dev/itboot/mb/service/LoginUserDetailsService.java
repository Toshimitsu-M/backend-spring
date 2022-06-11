package dev.itboot.mb.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.itboot.mb.mapper.LoginUserMapper;
import dev.itboot.mb.model.LoginUser;
import lombok.RequiredArgsConstructor;



@RequiredArgsConstructor
@Transactional
@Service
public class LoginUserDetailsService implements UserDetailsService {
	private final LoginUserMapper loginUserMapper;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<LoginUser> loginUserOptional = loginUserMapper.selectByEmail(email);
		return loginUserOptional.map(loginUser -> new LoginUserDetails(loginUser))
				.orElseThrow(() -> new UsernameNotFoundException("not found"));
	}
}