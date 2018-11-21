package com.excilys.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.persistence.CompanyDAO;
import com.excilys.persistence.ComputerDAO;

public class ComputerServices {
	
	private ComputerDAO computerDAO = ComputerDAO.getInstance();
	private CompanyDAO companyDAO = CompanyDAO.getInstance();

	private ComputerServices(){}
	
	private static final ComputerServices INSTANCE = new ComputerServices();

	public static ComputerServices getInstance() {
		return INSTANCE;
	}
	
	public List<Computer> getComputers() {
		return computerDAO.getComputers();
	}
	
	public Computer getComputerById(long idComputer) {
		return computerDAO.getComputerById(idComputer);
	}
	
	public int deleteComputer(long idComputer) {
		return computerDAO.deleteComputerFromId(idComputer);
	}
	
	public void createComputer(Computer computer) {
		computerDAO.addComputer(computer);
	}
	
	public void updateComputer(Computer computer) {
		computerDAO.updateComputer(computer);
	}
	
	public void showComputersPaginated(int indexToShow) {
		Map<Integer,List<Computer>> paginatedComputers = getComputersPaginated();
		List<Computer> computerPage =  paginatedComputers.get(indexToShow);
		for (Computer computer : computerPage) {
			System.out.println(computer);
		}
	}
	
	private Map<Integer,List<Computer>> getComputersPaginated() {
		Map<Integer , List<Computer>> computerMap = new HashMap<>();
		List<Computer> computersList = computerDAO.getComputers();
		int indexKey = 0;
		while (computersList.size() > 0) {
			List<Computer> computerPage = new ArrayList<>();
			for (int i = 0; i<10; i++) {
				if (!computersList.isEmpty()) {
					computerPage.add(computersList.remove(0));
				} else {
					break;
				}
			}
			computerMap.put(indexKey, computerPage);
			indexKey++;
		}
		return computerMap;
	}
	
}
