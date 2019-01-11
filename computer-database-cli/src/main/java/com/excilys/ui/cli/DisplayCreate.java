package com.excilys.ui.cli;

import java.time.LocalDateTime;
import java.util.InputMismatchException;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;

import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.service.ComputerService;
import com.excilys.validator.ComputerValidator;
import com.excilys.validator.ParseValidator;

public class DisplayCreate {

	@Autowired
	private static ComputerService computerServices;
	@Autowired
	private static ComputerValidator computerValidator;
	@Autowired
	private static ParseValidator parseValidator;

	protected static void displayCreate(Scanner sc) {
		System.out.println("Please enter the name of the computer you want to create :");
		String name = sc.nextLine();
		if (name.isEmpty()) {
			System.out.println("A name is mandatory.");
		}

		System.out.println(
				"Please enter the introduced date of the computer you want to create (format : yyyy-mm-ddThh:mm:ss) (null if unwanted):");
		String introducedString = sc.nextLine();
		LocalDateTime introduced = checkDateTimeForCreation(introducedString);

		LocalDateTime discontinued = null;
		if (introduced != null) {
			System.out.println(
					"Please enter the discontinued date of the computer you want to create (format : yyyy-mm-ddThh:mm:ss) (null if unwanted):");
			String discontinuedString = sc.nextLine();
			discontinued = checkDateTimeForCreation(discontinuedString);
			if (discontinued != null && introduced.isAfter(discontinued)) {
				discontinued = null;
				System.out.println("An introduced date and time must be anterior to a discontinued date and time.");
			}
		}

		Company company = null;
		System.out.println("Please enter the company id of the computer you want to create (-1 if unwanted):");
		try {
			long idCompany = sc.nextLong();
			if (idCompany == -1) {
				// Valid statement
			} else {
				company = new Company(idCompany,null);
			}
		} catch (InputMismatchException e) {
			System.out.println("Please input a number.");
		}

		Computer computer = new Computer(name,introduced,discontinued,company);
		computerValidator.correctComputer(computer);

		int nbRowAffected = computerServices.createComputer(computer);
		System.out.println("Number of row affected : " + nbRowAffected);
	}

	/**
	 * Return the parsed LocalDateTime
	 * 
	 * @param toCheck
	 * @return
	 */
	private static LocalDateTime checkDateTimeForCreation(String toCheck) {
		LocalDateTime dateTime = null;
		if (parseValidator.isParsableLocalDateTime(toCheck)) {
			dateTime = LocalDateTime.parse(toCheck);
		} else if (toCheck.isEmpty()) {
			// Keep dateTime to null
		} else {
			System.out.println("Wrong date and time format (format : yyyy-mm-ddThh:mm:ss)");
		}
		return dateTime;
	}

}
