package com.excilys.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.model.Computer;
import com.excilys.model.Page;
import com.excilys.persistence.ComputerDAO;

@Service
public class ComputerService {

	private final ComputerDAO computerDAO;

	@Autowired
	public ComputerService(ComputerDAO computerDAO) {
		this.computerDAO = computerDAO;
	}
	
	public List<Computer> getComputers() {
		return computerDAO.getComputers();
	}

	public List<Computer> getPagedComputers(Page page) {
		return computerDAO.getListComputers(page);
	}

	public List<Computer> getPagedComputersOrdered(Page page, String orderType, String orderDirection) {
		switch (orderType) {
		case "name":
			return computerDAO.getListComputersOrderByName(page, orderDirection);
		case "introduced":
			return computerDAO.getListComputersOrderByIntroduced(page, orderDirection);
		case "discontinued":
			return computerDAO.getListComputersOrderByDiscontinued(page, orderDirection);
		case "company":
			return computerDAO.getListComputersOrderByCompany(page, orderDirection);
		default:
			return computerDAO.getListComputers(page);
		}

	}

	public Optional<Computer> getComputerById(long idComputer) {
		return computerDAO.getComputerById(idComputer);
	}

	public List<Computer> getComputersWithSearch(String search) {
		return computerDAO.getComputersWithSearch(search);
	}

	public List<Computer> getComputersFromCompanyId(long idCompany) {
		return computerDAO.getComputersFromCompanyId(idCompany);
	}

	public int deleteComputer(long idComputer) {
		return computerDAO.deleteComputerFromId(idComputer);
	}

	public int createComputer(Computer computer) {
		if (computer.getCompany() != null) {
			return computerDAO.addComputer(computer);
		} else {
			return computerDAO.addComputerWithoutCompany(computer);
		}
	}

	public int updateComputer(Computer computer) {
		return computerDAO.updateComputer(computer);
	}

}
