package com.excilys.validator;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.excilys.exception.RoleDoesNotExistException;
import com.excilys.exception.UserExistsException;
import com.excilys.model.Role;
import com.excilys.model.User;
import com.excilys.service.RoleService;
import com.excilys.service.UserService;

@Component
public class UserValidator {

	private static final Logger LOG4J = LogManager.getLogger(UserValidator.class.getName());
	
	private final RoleService roleService;
	private final UserService userService;
	
	public UserValidator(RoleService roleService, UserService userService) {
		this.roleService = roleService;
		this.userService = userService;
	}
	
	public boolean validate(User user) throws UserExistsException, RoleDoesNotExistException {
		Optional<User> opUser = userService.getUserByName(user.getName());
		if (opUser.isPresent()) {
			LOG4J.info("Username already in use.");
			throw new UserExistsException();
		}
		Optional<Role> opRole = roleService.getRoleByName(user.getRole().getName());
		if (!opRole.isPresent()) {
			LOG4J.info("Role does not exist.");
			throw new RoleDoesNotExistException();
		}
		return true;
	}
	
}
