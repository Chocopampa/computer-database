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
	
	public void showComputers() {
		List<Computer> computersList = computerDAO.getComputers();
		for (Computer computer : computersList) {
			System.out.println(computer);
		}
	}
	
	public void showComputerDetails(long idComputer) {
		Computer computer = computerDAO.getComputer(idComputer);
		if (computer != null) {
			System.out.println(computer);
		} else {
			System.out.println("This computer does not exist.");
		}
	}
	
	public void deleteComputer(long idComputer) {
		computerDAO.deleteComputerFromId(idComputer);
		System.out.println("The computer with id " + idComputer + " has been deleted.");
	}
	
	public void createComputer(String name, String introduced, String discontinued, String companyId) {
		long idCompany = -1;
		if (!"null".equalsIgnoreCase(companyId)) {
			idCompany = Long.parseLong(companyId);
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
		
		long idCompany = -1;
		if (!"null".equalsIgnoreCase(companyId)) {
			idCompany = Long.parseLong(companyId);
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

	private boolean companyIdExists(long idCompany) {
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
		LocalDateTime introducedDateTime = null;
		LocalDateTime discontinuedDateTime = null;
		Company company = null;
		
		
		
		if (!"null".equalsIgnoreCase(introduced)) {
			introducedDateTime = LocalDateTime.parse(introduced);
		} 
		
		if (!"null".equalsIgnoreCase(discontinued)) {
			discontinuedDateTime = LocalDateTime.parse(discontinued);
		}
		
		if (!"null".equalsIgnoreCase(companyId)) {
			company = new Company.Builder(Long.parseLong(companyId))
					.build();
		}

		
		Computer computer = new Computer.Builder(name)
				.withIntroduced(introducedDateTime)
				.withDiscontinued(discontinuedDateTime)
				.withCompany(company)
				.build();
		
		
		if (computer.getIntroduced() != null && computer.getDiscontinued() != null) {
			if (computer.getIntroduced().isBefore(computer.getDiscontinued())) {
				computerDAO.addComputer(computer);
			} else {
				System.out.println("The introduced date must be before the discontinued date.");
			}
		} else if (computer.getIntroduced() == null && computer.getDiscontinued() != null){
			System.out.println("You cannot input a discontinued datetime without an introduced datetime.");
		}
		else {
			computerDAO.addComputer(computer);
		}
	}
	
	private void computerUpdate(String computerId, String name, String introduced, String discontinued, String companyId) {
		LocalDateTime introducedDateTime = null;
		LocalDateTime discontinuedDateTime = null;
		Company company = null;
		
		if (!"null".equalsIgnoreCase(introduced)) {
			introducedDateTime = LocalDateTime.parse(introduced);
		} 
		
		if (!"null".equalsIgnoreCase(discontinued)) {
			discontinuedDateTime = LocalDateTime.parse(discontinued);
		}
		
		if (!"null".equalsIgnoreCase(companyId)) {
			company = new Company.Builder(Integer.parseInt(companyId))
					.build();
		}

		
		Computer computer = new Computer.Builder(name)
				.withId(Long.parseLong(computerId))
				.withIntroduced(introducedDateTime)
				.withDiscontinued(discontinuedDateTime)
				.withCompany(company)
				.build();
		
		if (computer.getIntroduced() != null && computer.getDiscontinued() != null) {
			if (computer.getIntroduced().isBefore(computer.getDiscontinued())) {
				computerDAO.updateComputer(computer);
			} else {
				System.out.println("The introduced date must be before the discontinued date.");
			}
		} else if (computer.getIntroduced() == null && computer.getDiscontinued() != null){
			System.out.println("You cannot input a discontinued datetime without an introduced datetime.");
		}
		else {
			computerDAO.updateComputer(computer);
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
