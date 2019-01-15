package com.excilys.ui.cli;

import java.util.InputMismatchException;
import java.util.Scanner;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.excilys.config.PersistenceSpringConfiguration;
import com.excilys.service.CompanyService;
import com.excilys.ui.config.JaxRsService;

public class DisplayDelete {


	static ApplicationContext context = new AnnotationConfigApplicationContext(PersistenceSpringConfiguration.class);
	
	private static JaxRsService jaxService = (JaxRsService) context.getBean(JaxRsService.class);
	
	private static CompanyService companyServices = (CompanyService) context.getBean(CompanyService.class);

	protected static void deleteItem(Scanner sc) {
		String str = "";
		while (!"exit".equals(str)) {
			System.out.println("Delete : Input a command (type help for help) :");
			str = sc.nextLine();
			switch (str) {
			case "company":
				deleteCompany(sc);
				break;
			case "computer":
				deleteComputer(sc);
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
	
	private static void deleteCompany(Scanner sc) {
		long idParsed = -1;
		System.out.println("Please enter the id of the company you want to delete :");
		try {
			idParsed = sc.nextLong();
			sc.nextLine();
			int nbRowDeleted = 0;
			companyServices.deleteCompany(idParsed);
			System.out.println("Number of rows deleted : " + nbRowDeleted);
			if (nbRowDeleted > 0) {
				System.out.println("The company with id " + idParsed
						+ " has been deleted. (Number of computers deleted : " + nbRowDeleted + ")");
			} else {
				System.out.println("The company with id " + idParsed + " has not been deleted.");
			}
		} catch (InputMismatchException e) {
			System.out.println("Please input a number.");
		}
	}

	
	private static void deleteComputer(Scanner sc) {
		long idParsed = -1;
		System.out.println("Please enter the id of the computer you want to delete :");
		try {
			idParsed = sc.nextLong();
			sc.nextLine();
			System.out.println(jaxService.deleteComputer(idParsed));
		} catch (InputMismatchException e) {
			System.out.println("Please input a number.");
		}
	}
	
	private static void displayHelp() {
		System.out.println("Command list :");
		System.out.println("	computer : delete a computer in database");
		System.out.println("	company : delete a company in database");
		System.out.println("	exit : quit delete mode");
	}

}
