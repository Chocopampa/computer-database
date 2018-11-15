package cli;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.List;


import dao.CompanyDAO;
import dao.ComputerDAO;
import model.Company;
import model.Computer;
import services.CompanyServices;
import services.ComputerServices;

public class CommandLineInterface {
	
	private static CompanyServices companyServices = CompanyServices.getInstance();
	private static ComputerServices computerServices = ComputerServices.getInstance();

	public static void main(String[] args) {
		switch(args[0]) {
			case "get" :
				if ("companies".equalsIgnoreCase(args[1])) {
					companyServices.showCompanies();
				} else if ("computers".equalsIgnoreCase(args[1])) {
					computerServices.showComputers();
				}
				break;
			case "delete" :
				break;
			case "create" :
				break;
			case "update" :
				break;
			default:
				break;
		
		}
//		CompanyDAO companyDAO = CompanyDAO.getInstance();
//		ComputerDAO computerDAO = ComputerDAO.getInstance();
//		ComputerServices computerServices = ComputerServices.getInstance();
		
//		Computer computer = new Computer();
//		LocalDateTime time1 = LocalDateTime.now();		
//		LocalDateTime time2 = LocalDateTime.now().plusHours(1);
//		
//		computer.setName("billyModified");
//		computer.setIntroduced(time1);
//		computer.setDiscontinued(time2);
//		computer.setCompanyId(1);
		
//		computerServices.showComputerDetails(236);
		
//		computerDAO.insertComputer(computer);
//		computerDAO.updateComputer(576, computer);
//		
//		List<Computer> computers = computerDAO.getComputers();
//		List<Company> companies = companyDAO.getCompanies();

//		for (Company company : companies) {
//			System.out.println(company.getName());
//		}
//				
//		for (Computer computerInDB : computers) {
//			System.out.print(computerInDB.getName());
//			System.out.print("	" + computerInDB.getIntroduced());
//			System.out.print("	" + computerInDB.getDiscontinued());
//			System.out.println("	" + computerInDB.getCompanyId());
//		}
//		
	}

}
