package com.excilys.ui.cli;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.excilys.config.PersistenceSpringConfiguration;
import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.service.CompanyService;
import com.excilys.ui.config.JaxRsService;

public class DisplayGet {

	
	static ApplicationContext context = new AnnotationConfigApplicationContext(PersistenceSpringConfiguration.class);
	
	private static CompanyService companyService = (CompanyService) context.getBean(CompanyService.class);
	private static JaxRsService jaxService = (JaxRsService) context.getBean(JaxRsService.class);

	protected static void getResults(Scanner sc) {
		String str = "";
		while (!"exit".equals(str)) {
			System.out.println("Get : Input a command (type help for help) :");
			str = sc.nextLine();
			switch (str) {
			case "computers":
				displayComputers();
				break;
			case "companies":
				displayCompanies();
				break;
			case "paged":
				DisplayGetPaged.getPagedItems(sc);
				break;
			case "computer":
				displayComputerDetails(sc);
				break;
			case "help":
				displayHelp();
				break;
			case "exit":
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
		System.out.println(jaxService.getJsonComputers());
	}

	/**
	 * Display all the companies in database.
	 */
	private static void displayCompanies() {
		List<Company> companiesList = companyService.getCompanies();
		for (Company company : companiesList) {
			System.out.println(company);
		}
	}

	/**
	 * Display the details of a computer.
	 * 
	 * @param sc
	 */
	private static void displayComputerDetails(Scanner sc) {
		long idParsed = -1;
		System.out.println("Input the id of the computer you want to see :");
		try {
			idParsed = sc.nextLong();
			sc.nextLine();
			Computer computer = jaxService.getJsonComputer(idParsed);
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
		System.out.println("	paged : return a list of items wanted");
		System.out.println("	computer : return the details of a specific computer by its id");
		System.out.println("	exit : return to the previous command list");
	}

}
