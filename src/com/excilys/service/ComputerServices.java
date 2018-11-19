package com.excilys.service;

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
	
	public void showComputers() {
		List<Computer> computersList = computerDAO.getComputers();
		for (Computer computer : computersList) {
			System.out.println(computer);
		}
	}
	
	public void showComputerDetails(int idComputer) {
		Computer computer = computerDAO.getComputer(idComputer);
		if (computer != null) {
			System.out.println(computer);
		} else {
			System.out.println("This computer does not exist.");
		}
	}
	
	public void deleteComputer(int idComputer) {
		computerDAO.deleteComputerFromId(idComputer);
		System.out.println("The computer with id " + idComputer + " has been deleted.");
	}
	
	public void createComputer(String name, String introduced, String discontinued, String companyId) {
		int idCompany = -1;
		if (!"null".equalsIgnoreCase(companyId)) {
			idCompany = Integer.parseInt(companyId);
		}
		if (name.isEmpty()) {
			System.out.println("Empty computer name.");
		} else if ("null".equalsIgnoreCase(companyId)){
			computerCreation(name, introduced, discontinued, companyId);
		} else if (!companyIdExists(idCompany)) {
			System.out.println("Invalid company id.");
		} else {
			computerCreation(name, introduced, discontinued, companyId);
		}
	}
	
	public void updateComputer(String computerId, String name, String introduced, String discontinued, String companyId) {
		
		int idCompany = -1;
		if (!"null".equalsIgnoreCase(companyId)) {
			idCompany = Integer.parseInt(companyId);
		}
		if (name.isEmpty()) {
			System.out.println("Empty computer name.");
		} else if ("null".equalsIgnoreCase(companyId)){
			computerUpdate(computerId,name,introduced,discontinued,companyId);
		} else if (!companyIdExists(idCompany)) {
			System.out.println("Invalid company id.");
		} else {
			computerUpdate(computerId,name,introduced,discontinued,companyId);
		}
	}
	
	public void showComputersPaginated(int indexToShow) {
		Map<Integer,List<Computer>> paginatedComputers = getComputersPaginated();
		List<Computer> computerPage =  paginatedComputers.get(indexToShow);
		for (Computer computer : computerPage) {
			System.out.println(computer);
		}
	}

	private boolean companyIdExists(int idCompany) {
		List<Company> companies = companyDAO.getCompanies();
		boolean companyIdExists = false;
		
		for (Company company : companies) {
			if (idCompany == company.getId()) {
				companyIdExists = true;
			}
		}
		return companyIdExists;
	}
	
	private void computerCreation(String name, String introduced, String discontinued, String companyId) {
		Computer computer = new Computer(name, introduced, discontinued, companyId);
		if (computer.getIntroduced() != null && computer.getDiscontinued() != null) {
			if (computer.getIntroduced().isBefore(computer.getDiscontinued())) {
				computerDAO.insertComputer(computer);
			} else {
				System.out.println("The introduced date must be before the discontinued date.");
			}
		} else if (computer.getIntroduced() == null && computer.getDiscontinued() != null){
			System.out.println("You cannot input a discontinued datetime without an introduced datetime.");
		}
		else {
			computerDAO.insertComputer(computer);
		}
	}
	
	private void computerUpdate(String computerId, String name, String introduced, String discontinued, String companyId) {
		int idComputer = Integer.parseInt(computerId);
		Computer computer = new Computer(name, introduced, discontinued, companyId);
		if (computer.getIntroduced() != null && computer.getDiscontinued() != null) {
			if (computer.getIntroduced().isBefore(computer.getDiscontinued())) {
				computerDAO.updateComputer(idComputer, computer);
			} else {
				System.out.println("The introduced date must be before the discontinued date.");
			}
		} else if (computer.getIntroduced() == null && computer.getDiscontinued() != null){
			System.out.println("You cannot input a discontinued datetime without an introduced datetime.");
		}
		else {
			computerDAO.updateComputer(idComputer, computer);
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
