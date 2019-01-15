package com.excilys.service;

import java.util.List;
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

	private final UserDAO userDao;

	@Autowired
	public UserService(UserDAO userDAO) {
		this.userDao = userDAO;
	};

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = userDao.getUserByName(username);
		UserBuilder userBuilder = null;
        if (!user.isPresent()) {
            throw new UsernameNotFoundException(username);
        }
        userBuilder = org.springframework.security.core.userdetails.User.withUsername(username);
        userBuilder.password(user.get().getPassword());
        userBuilder.roles(user.get().getRole().getName().toUpperCase());
        return userBuilder.build();
	}
	
	public List<User> getUsers() {
		return userDao.getUsers();
	}
	
	public Optional<User> getUserByName(String userName) {
		return userDao.getUserByName(userName);
	}
	
	public int createUser(User user) {
		return userDao.addUser(user);
	}
}
