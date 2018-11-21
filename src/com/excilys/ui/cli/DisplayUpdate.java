package com.excilys.ui.cli;

import java.time.LocalDateTime;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.service.ComputerServices;
import com.excilys.validator.CompanyValidator;
import com.excilys.validator.DateTimeValidator;
import com.excilys.validator.ParseValidator;

public class DisplayUpdate {
	
	private static DateTimeValidator dateTimeValidator = DateTimeValidator.getInstance();
	private static ParseValidator parseValidator = ParseValidator.getInstance();
	private static ComputerServices computerServices = ComputerServices.getInstance();
	private static CompanyValidator companyValidator =CompanyValidator.getInstance();

	protected static void displayUpdate(Scanner sc) {

		System.out.println("Please enter the id of the computer you want to update :");
		long id = sc.nextLong();
		sc.nextLine();
		Computer computer = computerServices.getComputerById(id);
		if (computer == null) {
			System.out.println("The computer does not exist");
		} else {
		
			System.out.println("Please enter the new name (nothing if no update wanted) :");
			String name = sc.nextLine();
			if (name.isEmpty()) {
				name = computer.getName();
			}
			
			System.out.println("Please enter the new introduced date (format : yyyy-mm-ddThh:mm:ss) (null if unwanted) (nothing if no update wanted):");
			String introducedString = sc.nextLine();
			LocalDateTime introduced = null;
			if(introducedString.isEmpty() || !parseValidator.isParsableLocalDateTime(introducedString)) {
				introduced = computer.getIntroduced();
			} else {
				introduced = dateTimeValidator.checkDateTime(introducedString);
			}
			
			System.out.println("Please enter the discontinued date of the computer you want to update (format : yyyy-mm-ddThh:mm:ss) (null if unwanted) (nothing if no update wanted):");
			String discontinuedString = sc.nextLine();
			LocalDateTime discontinued = null;
			if (discontinuedString.isEmpty() || !parseValidator.isParsableLocalDateTime(introducedString)) {
				discontinued = computer.getDiscontinued();
			} else {
				discontinued = dateTimeValidator.checkDateTime(discontinuedString);
			}
			
			if (introduced != null && discontinued != null && introduced.isAfter(discontinued)) {
				System.out.println("An introduced date and time must be anterior to a discontinued date and time.");
			}
			
			Company company = null; 
			System.out.println("Please enter the company id of the computer you want to update (-1 if unwanted)(-2 if no update wanted) :");
			try {
				long idCompany = sc.nextLong();
				sc.nextLine();
				if (idCompany == -2) {
					idCompany = computer.getCompany().getId();
					company = new Company.Builder(idCompany).build();
				} else if (companyValidator.companyExists(idCompany)) {
					company = new Company.Builder(idCompany).build();
				} else if (idCompany == -1) {
					// Valid statement
				}
			} catch (InputMismatchException e) {
				System.out.println("Please input a number.");
			}
			
			computer = new Computer.Builder(name)
					.withId(id)
					.withIntroduced(introduced)
					.withDiscontinued(discontinued)
					.withCompany(company)
					.build();
			
			computerServices.updateComputer(computer);
		}
	}
	

}
