package com.excilys.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.excilys.model.User;
import com.excilys.persistence.UserDAO;

@Service
public class UserService implements UserDetailsService {

	private final UserDAO userDAO;

	@Autowired
	public UserService(UserDAO userDAO) {
		this.userDAO = userDAO;
	};

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = userDAO.getUserByName(username);
		UserBuilder userBuilder = null;
        if (!user.isPresent()) {
            throw new UsernameNotFoundException(username);
        }
        userBuilder = org.springframework.security.core.userdetails.User.withUsername(username);
        userBuilder.password(user.get().getPassword());
        userBuilder.roles(user.get().getRole().getName().toUpperCase());
        return userBuilder.build();
	}
}
