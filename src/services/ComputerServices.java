package services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import dao.CompanyDAO;
import dao.ComputerDAO;
import model.Company;
import model.Computer;

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
		List<Computer> computersList = computerDAO.getComputers();
		Computer searchedComputer = null;
		for (Computer computer : computersList) {
			if (idComputer == computer.getId()) {
				searchedComputer = computer;
			}
		}
		if (searchedComputer != null) {
			System.out.print(searchedComputer);
		} else {
			System.out.println("Cet ordinateur n'existe pas.");
		}
	}
	
	public void deleteComputer(int idComputer) {
		computerDAO.deleteComputerFromId(idComputer);
	}
	
	public void createComputer(String name, String introduced, String discontinued, String companyId) {
		int idCompany = Integer.parseInt(companyId);
		if (name.isEmpty() || !companyIdExists(idCompany)) {
			System.out.println("Empty computer name, or invalid company identifier.");
		} else {
			Computer computer = new Computer(name, introduced, discontinued, companyId);
			computerDAO.insertComputer(computer);
		}
	}
	
	public void updateComputer(String computerId, String name, String introduced, String discontinued, String companyId) {
		int idComputer = Integer.parseInt(computerId);
		if (name.isEmpty() || !companyIdExists(Integer.parseInt(companyId)) || !computerIdExists(idComputer)) {
			System.out.println("Empty computer name, or invalid company identifier, or invalid computer id.");
		} else {
			Computer computer = new Computer(name, introduced, discontinued, companyId);
			computerDAO.updateComputer(idComputer, computer);
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
	
	private boolean computerIdExists(int idComputer) {
		List<Computer> computers = computerDAO.getComputers();
		boolean computerIdExists = false;
		
		for (Computer computer : computers) {
			if (idComputer == computer.getId()) {
				computerIdExists = true;
			}
		}
		
		return computerIdExists;
	}
	
}
