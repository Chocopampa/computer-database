package com.excilys.service;

import java.util.List;
import java.util.Optional;

import com.excilys.model.Computer;
import com.excilys.model.Page;
import com.excilys.persistence.ComputerDAO;

public class ComputerService {

	private ComputerDAO computerDAO = ComputerDAO.getInstance();

	private ComputerService() {
	}

	private static final ComputerService INSTANCE = new ComputerService();

	public static ComputerService getInstance() {
		return INSTANCE;
	}

	public List<Computer> getComputers() {
		return computerDAO.getComputers();
	}

	public List<Computer> getPagedComputers(Page page) {
		return computerDAO.getListComputers(page);
	}

	public Optional<Computer> getComputerById(long idComputer) {
		return computerDAO.getComputerById(idComputer);
	}
	
	public List<Computer> getComputersWithSearch(String search) {
		return computerDAO.getComputersWithSearch(search);
	}

	public int deleteComputer(long idComputer) {
		return computerDAO.deleteComputerFromId(idComputer);
	}

	public int createComputer(Computer computer) {
		return computerDAO.addComputer(computer);
	}

	public int updateComputer(Computer computer) {
		return computerDAO.updateComputer(computer);
	}

}
