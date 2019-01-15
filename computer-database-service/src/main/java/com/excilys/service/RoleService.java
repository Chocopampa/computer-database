package com.excilys.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.model.Role;
import com.excilys.persistence.RoleDAO;

@Service
public class RoleService {

	private final RoleDAO roleDao;
	
	@Autowired
	public RoleService(RoleDAO roleDao) {
		this.roleDao = roleDao;
	}
	
	public Optional<Role> getRoleByName(String roleName) {
		return roleDao.getRoleByName(roleName);
	}
}
