package com.LoginPage.LoginPage.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.LoginPage.LoginPage.model.User;
import com.LoginPage.LoginPage.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		
		Optional<User> user = userRepository.findByEmail(userName);
		user.orElseThrow(() -> new UsernameNotFoundException(userName + "not found."));

		return user.map(UserDetailsImpl::new).get();
	}
}
