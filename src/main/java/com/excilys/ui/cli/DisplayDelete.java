package com.excilys.ui.cli;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;

import com.excilys.model.Computer;
import com.excilys.service.CompanyService;
import com.excilys.service.ComputerService;

public class DisplayDelete {

	@Autowired
	private static ComputerService computerServices;
	@Autowired
	private static CompanyService companyServices;

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
			List<Computer> computersLinked = computerServices.getComputersFromCompanyId(idParsed);
			int nbRowDeleted = 0;
			for (Computer computer : computersLinked) {
				nbRowDeleted += computerServices.deleteComputer(computer.getId());
			}
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
			int nbRowDeleted = computerServices.deleteComputer(idParsed);
			if (nbRowDeleted > 0) {
				System.out.println("The computer with id " + idParsed
						+ " has been deleted. (Number of computers deleted : " + nbRowDeleted + ")");
			} else {
				System.out.println("The computer with id " + idParsed + " has not been deleted.");
			}
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
