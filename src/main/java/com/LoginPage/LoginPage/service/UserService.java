package com.LoginPage.LoginPage.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.LoginPage.LoginPage.model.User;
import com.LoginPage.LoginPage.model.UserLogin;
import com.LoginPage.LoginPage.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public Optional<User> userRegister(User user) {
		if (userRepository.findByEmail(user.getEmail()).isPresent())
			return Optional.empty();
		

		return Optional.of(userRepository.save(user));
	}

	public Optional<User> authenticateUser(Optional<UserLogin> userLogin) {

		Optional<User> user = userRepository.findByEmail(userLogin.get().getEmail());

		if (user.isPresent()) {
			if (userLogin.get().getPassword().equals(user.get().getPassword())) {
				return user;
			}
		}

		return Optional.empty();
	}

}
