package com.excilys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.exception.UserCreationException;
import com.excilys.model.User;
import com.excilys.service.UserService;
import com.excilys.validator.UserValidator;

@RestController
public class UserRestController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserValidator userValidator;
	
	@GetMapping("/getUsers")
	@ResponseStatus(HttpStatus.OK)
	public List<User> getUsers() {
		return userService.getUsers();
	}
	
	@PostMapping("/addUser-rest")
	@ResponseStatus(HttpStatus.CREATED)
	public int addUser(@RequestBody User user) throws Exception {
		if (userValidator.validate(user)) {
			return userService.createUser(user);
		}
		throw new UserCreationException();
	}


}
