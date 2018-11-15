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
	
	public void showComputerDetails(int computerId) {
		List<Computer> computersList = computerDAO.getComputers();
		Computer searchedComputer = null;
		for (Computer computer : computersList) {
			if (computerId == computer.getId()) {
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
	
	public void createComputer(String name, String introduced, String discontinued, String idCompany) {
		
		List<Company> companies = new ArrayList<>();
		companies = companyDAO.getCompanies();
		boolean companyIdExists = false;
		
		for (Company company : companies) {
			if (Integer.getInteger(idCompany) == company.getId()) {
				companyIdExists = true;
			}
		}
		
		if (name.isEmpty() || !companyIdExists) {
			System.out.println("Empty computer name, or invalid company identifier.");
		} else {
			Computer computer = new Computer();
			computer.setName(name);
			
			if (!"null".equals(introduced)) {
				LocalDateTime introducedDateTime = LocalDateTime.parse(introduced);
				computer.setIntroduced(introducedDateTime);
			} else {
				computer.setIntroduced(null);
			}
			
			if (!"null".equals(discontinued)) {
				LocalDateTime discontinuedDateTime = LocalDateTime.parse(discontinued);
				computer.setDiscontinued(discontinuedDateTime);
			} else {
				computer.setDiscontinued(null);
			}
			
			computer.setCompanyId(Integer.parseInt(idCompany));
			
			computerDAO.insertComputer(computer);
		}
	}

}
