package com.LoginPage.LoginPage.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

		user.setPassword(encryptPassword(user.getPassword()));
		return Optional.of(userRepository.save(user));
	}

	public Optional<User> authenticateUser(Optional<UserLogin> userLogin) {

		Optional<User> user = userRepository.findByEmail(userLogin.get().getEmail());

		if (user.isPresent()) {
			if (comparePasswords(userLogin.get().getPassword(),user.get().getPassword())) {
				return user;
			}
		}

		return Optional.empty();
	}

	private String encryptPassword(String password) {

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		return encoder.encode(password);

	}

	private boolean comparePasswords(String passwordTyped, String passwordDatabase) {

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		return encoder.matches(passwordTyped, passwordDatabase);

	}


}
