package com.excilys.ui.cli;

import java.util.Arrays;
import java.util.Scanner;

import com.excilys.service.CompanyServices;
import com.excilys.service.ComputerServices;
import com.excilys.validator.ParseValidator;

public class DisplayGet {
	
	private static Scanner sc = new Scanner(System.in);

	private static ParseValidator parseValidator = ParseValidator.getInstance();
	private static CompanyServices companyServices = CompanyServices.getInstance();
	private static ComputerServices computerServices = ComputerServices.getInstance();

	
	protected static void getResults(String[] commands) {
		if (!commands[1].isEmpty() && "companies".equalsIgnoreCase(commands[1])) {
			companyServices.showCompanies();
		} else if (!commands[1].isEmpty() && "computers".equalsIgnoreCase(commands[1])) {
			String[] commandsForListing = Arrays.copyOfRange(commands, 2, commands.length);
			displayComputers(commandsForListing);
		} else if (!commands[1].isEmpty() && "computer".equalsIgnoreCase(commands[1])) {
			if (commands.length >= 3 && parseValidator.isParsableLong(commands[2])) {
				computerServices.showComputerDetails(Long.parseLong(commands[2]));
			} else {
				System.out.println("Please enter the id of the computer to show.");
			}
		} else {
			System.out.println("Invalid command. Please chose between : computers, companies, computer [id]");
		}
	}


	/**
	 * Display the computers by page.
	 * @param commandsForListing
	 */
	private static void displayComputers(String[] commandsForListing) {
		if (commandsForListing.length > 0) {
			if (!commandsForListing[0].isEmpty() && "paginated".equalsIgnoreCase(commandsForListing[0])) {
				String paginationCommand = "";
				while (!"stop".equals(paginationCommand)) {			
					System.out.println("Enter the index number of the computers you want to display (10 per page) (type stop to quit pagination):");
					paginationCommand = sc.nextLine();
					
					if(!parseValidator.isParsableLong(paginationCommand)) {
						System.out.println("Please, enter a number.");
					} else {
						int index = Integer.parseInt(paginationCommand);
						computerServices.showComputersPaginated(index);
					}
				}
			} else {
				System.out.println("Wrong command.");
			}
		} else {
			computerServices.showComputers();
		}
	}
}
