package com.LoginPage.LoginPage.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.LoginPage.LoginPage.model.User;
import com.LoginPage.LoginPage.model.UserLogin;
import com.LoginPage.LoginPage.repository.UserRepository;
import com.LoginPage.LoginPage.service.UserService;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserService userService;
	
	@GetMapping
	public ResponseEntity<List<User>> getAll(){
		return ResponseEntity.ok(userRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity <User> getById(@PathVariable long id){
		return userRepository.findById(id).map(resp -> ResponseEntity.ok(resp)).orElse(ResponseEntity.notFound().build());
	}
	
	@PostMapping("/register")
	public ResponseEntity<User> userRegister (@Valid @RequestBody User user){
		return userService.userRegister(user)
		.map(response -> ResponseEntity.status(HttpStatus.CREATED).body(response))
		.orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
	}
	
	@PostMapping("/login")
	public ResponseEntity<User> login(@RequestBody Optional<UserLogin> user) {
		return userService.authenticateUser(user).map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
	}
	

	
}
