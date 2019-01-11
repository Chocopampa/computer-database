package com.excilys.ui.cli;

import java.time.LocalDateTime;
import java.util.InputMismatchException;
import java.util.Optional;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;

import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.service.ComputerService;
import com.excilys.validator.ComputerValidator;
import com.excilys.validator.ParseValidator;

public class DisplayUpdate {

	@Autowired
	private static ParseValidator parseValidator;
	@Autowired
	private static ComputerService computerServices;
	@Autowired
	private static ComputerValidator computerValidator;

	protected static void displayUpdate(Scanner sc) {

		System.out.println("Please enter the id of the computer you want to update :");
		long id = sc.nextLong();
		sc.nextLine();
		Optional<Computer> opComputer = computerServices.getComputerById(id);
		if (!opComputer.isPresent()) {
			System.out.println("The computer does not exist");
		} else {
			Computer computer = opComputer.get();
			System.out.println("Please enter the new name (nothing if no update wanted) :");
			String name = sc.nextLine();
			if (name.isEmpty()) {
				name = computer.getName();
			}

			System.out.println(
					"Please enter the new introduced date (format : yyyy-mm-ddThh:mm:ss) (null if unwanted) (nothing if no update wanted):");
			String introducedString = sc.nextLine();
			LocalDateTime introduced = checkDateTimeForUpdate(introducedString, computer);

			System.out.println(
					"Please enter the discontinued date of the computer you want to update (format : yyyy-mm-ddThh:mm:ss) (null if unwanted) (nothing if no update wanted):");
			String discontinuedString = sc.nextLine();
			LocalDateTime discontinued = checkDateTimeForUpdate(discontinuedString, computer);

			if (introduced != null && discontinued != null && introduced.isAfter(discontinued)) {
				introduced = null;
				discontinued = null;
				System.out.println("An introduced date and time must be anterior to a discontinued date and time.");
			}

			Company company = null;
			System.out.println(
					"Please enter the company id of the computer you want to update (-1 if unwanted)(-2 if no update wanted) :");
			try {
				long idCompany = sc.nextLong();
				sc.nextLine();
				if (idCompany == -2) {
					idCompany = computer.getCompany().getId();
					company = new Company(idCompany,null);
				} else if (idCompany == -1) {
					// Valid statement
				} else {
					company = new Company(idCompany,null);
				}
			} catch (InputMismatchException e) {
				System.out.println("Please input a number.");
			}

			computer = new Computer(id,name,introduced,discontinued,company);
			computerValidator.correctComputer(computer);
			int nbRowAffected = computerServices.updateComputer(computer);
			System.out.print("Number of row affected : " + nbRowAffected);
		}
	}

	private static LocalDateTime checkDateTimeForUpdate(String toCheck, Computer computer) {
		LocalDateTime result = null;
		if (toCheck.isEmpty() || !parseValidator.isParsableLocalDateTime(toCheck)) {
			result = computer.getIntroduced();
		} else {
			result = LocalDateTime.parse(toCheck);
		}
		return result;
	}

}
