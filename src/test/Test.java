package test;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.List;


import dao.CompanyDAO;
import dao.ComputerDAO;
import model.Company;
import model.Computer;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CompanyDAO companyDAO = CompanyDAO.getInstance();
		ComputerDAO computerDAO = ComputerDAO.getInstance();
		
		Computer computer = new Computer();
		LocalDateTime time1 = LocalDateTime.now();		
		LocalDateTime time2 = LocalDateTime.now().plusHours(1);
		
		computer.setName("computerTestFinal");
		computer.setIntroduced(time1);
		computer.setDiscontinued(time2);
		computer.setCompanyId(1);
		
		computerDAO.insertComputer(computer);
		
		List<Computer> computers = computerDAO.getComputers();
		List<Company> companies = companyDAO.getCompanies();

//		for (Company company : companies) {
//			System.out.println(company.getName());
//		}
				
		for (Computer computerInDB : computers) {
			System.out.print(computerInDB.getName());
			System.out.print("	" + computerInDB.getIntroduced());
			System.out.print("	" + computerInDB.getDiscontinued());
			System.out.println("	" + computerInDB.getCompanyId());
		}
		
	}

}
