package com.excilys.ui.cli;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.service.CompanyServices;
import com.excilys.service.ComputerServices;

public class DisplayGet {

	private static CompanyServices companyServices = CompanyServices.getInstance();
	private static ComputerServices computerServices = ComputerServices.getInstance();

	protected static void getResults(Scanner sc) {
		String str = "";
		while (!"return".equals(str)) {
			System.out.println("Get : Input a command (type help for help) :");
			str = sc.nextLine();
			switch(str) {
				case "computers" :
					displayComputers();
					break;
				case "companies" :
					displayCompanies();
					break;
				case "computer" :
					displayComputerDetails(sc);
					break;
				case "help" :
					displayHelp();
					break;
				case "return" :
					break;
				default:
					System.out.println("Invalid command.");
			}
		}
	}

	/**
	 * Display all the computers in database.
	 */
	private static void displayComputers() {
		List<Computer> computersList = computerServices.getComputers();
		for (Computer computer : computersList) {
			System.out.println(computer);
		}
	}
	
	/**
	 * Display all the companies in database.
	 */
	private static void displayCompanies() {
		List<Company> companiesList = companyServices.getCompanies();
		for (Company company : companiesList) {
			System.out.println(company);
		}
	}
	
	/**
	 * Display the details of a computer.
	 * @param sc
	 */
	private static void displayComputerDetails(Scanner sc) {
		//TODO : régler bug double confirmation
		long idParsed = -1;
		System.out.println("Input the id of the computer you want to see :");
		try {
			idParsed = sc.nextLong();
			Computer computer = computerServices.getComputerDetails(idParsed);
			if (computer != null) {
				System.out.println(computer);
			} else {
				System.out.println("This computer does not exist.");
			}
		} catch (InputMismatchException e) {
			System.out.println("Please input a number.");
		}
	}
	
	private static void displayHelp() {
		System.out.println("Command list :");
		System.out.println("	computers : return the complete list of computers in database");
		System.out.println("	companies : return the complete list of companies in database");
		System.out.println("	computer : return the details of a specific computer by its id");
		System.out.println("	return : return to the previous command list");
	}
	
}
