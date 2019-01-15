package com.excilys.ui.cli;

import java.time.LocalDateTime;
import java.util.InputMismatchException;
import java.util.Scanner;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.excilys.config.PersistenceSpringConfiguration;
import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.ui.config.JaxRsService;
import com.excilys.validator.ComputerValidator;
import com.excilys.validator.ParseValidator;

public class DisplayCreate {


	static ApplicationContext context = new AnnotationConfigApplicationContext(PersistenceSpringConfiguration.class);
	
	private static JaxRsService jaxService = (JaxRsService) context.getBean(JaxRsService.class);
	
	private static ComputerValidator computerValidator = (ComputerValidator) context.getBean(ComputerValidator.class);
	
	private static ParseValidator parseValidator = (ParseValidator) context.getBean(ParseValidator.class);

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

		System.out.println(jaxService.createComputer(computer));
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
