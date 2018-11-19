package com.excilys.cli;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Scanner;

import com.excilys.service.CompanyServices;
import com.excilys.service.ComputerServices;

public class CommandLineInterface {
	
	private static Scanner sc = new Scanner(System.in);

	private static CompanyServices companyServices = CompanyServices.getInstance();
	private static ComputerServices computerServices = ComputerServices.getInstance();

	public static void main(String[] args) {
		String str = "";
		while (!"exit".equals(str)) {
			System.out.println("Input a command (type help for help) :");
			str = sc.nextLine();
			String[] commands = str.split(" ");
			switch(commands[0]) {
				case "get" :
					if (!commands[1].isEmpty() && "companies".equalsIgnoreCase(commands[1])) {
						companyServices.showCompanies();
					} else if (!commands[1].isEmpty() && "computers".equalsIgnoreCase(commands[1])) {
						String[] commandsForListing = Arrays.copyOfRange(commands, 2, commands.length);
						displayComputers(commandsForListing);
					} else if (!commands[1].isEmpty() && "computer".equalsIgnoreCase(commands[1])) {
						if (commands.length >= 3 && isParsableInt(commands[2])) {
							computerServices.showComputerDetails(Integer.parseInt(commands[2]));
						} else {
							System.out.println("Please enter the id of the computer to show.");
						}
					} else {
						System.out.println("Invalid command. Please chose between : computers, companies, computer [id]");
					}
					break;
				case "delete" :
					if (commands.length >= 3 && "computer".equalsIgnoreCase(commands[1])) {
						if (isParsableInt(commands[2])) {
							computerServices.deleteComputer(Integer.parseInt(commands[2]));
						} else {
							System.out.println("Veuillez entrer un nombre.");
						}
					} else {
						System.out.println("Can not delete anything but computer with its id. (delete computer [id])");
					}
					break;
				case "create" :
					if (commands.length >= 3 && "computer".equalsIgnoreCase(commands[1])) {
						String[] commandsForCreation = Arrays.copyOfRange(commands, 2, commands.length);
						computerCreation(commandsForCreation);
					} else {
						System.out.println("Can not create anything but computer, and a computer needs at least a name.");
					}
					break;
				case "update" :
					if (commands.length >= 4 && "computer".equalsIgnoreCase(commands[1])) {
						if (!commands[2].isEmpty() && isParsableInt(commands[2])) {
							String[] commandsForUpdate = Arrays.copyOfRange(commands, 2, commands.length);
							computerUpdate(commandsForUpdate);
						} else {
							System.out.println("Please enter the id of the computer to update.");
						}
					} else {
						System.out.println("Can not update anything but computer.");
					}
					break;
				case "exit" :
					System.out.println("Goodbye!");
					break;
				case "help" :
					displayHelp();
					break;
				default:
					System.out.println("Invalid command.");
			}
		}
		sc.close();
	}
	
	/**
	 * Check if the String is an integer.
	 * @param toParse
	 * @return
	 */
	private static boolean isParsableInt(String toParse) {
		boolean parsable = true;
		try {
			Integer.parseInt(toParse);
		} catch (NumberFormatException e) {
			if (!"null".equalsIgnoreCase(toParse)) {
				parsable = false;
			}
		}
		return parsable;
	}
	
	/**
	 * Check if the String is a LocalDateTime
	 * @param toParse
	 * @return
	 */
	private static boolean isParsableLocalDateTime(String toParse) {
		boolean parsable = true;
		try {
			LocalDateTime.parse(toParse);
		} catch (DateTimeException e) {
			if (!"null".equalsIgnoreCase(toParse)) {
				parsable = false;
			}
		}
		return parsable;
	}

	/**
	 * Calls computer creation service based on the number of arguments given.
	 * @param commandsForCreation
	 */
	private static void computerCreation(String[] commandsForCreation) {
		switch (commandsForCreation.length) {
		case 1 :
			computerServices.createComputer(commandsForCreation[0], "null", "null", "null");
			break;
		case 4 :
			if (!isParsableInt(commandsForCreation[3])) {
				System.out.println("The company id must be a number.");
			} else if (!isParsableLocalDateTime(commandsForCreation[1])) {
				System.out.println("Invalid format for the introduced date time. (format : yyyy-mm-ddThh:mm:ss)");
			} else if (!isParsableLocalDateTime(commandsForCreation[2])) {
				System.out.println("Invalid format for the discontinued date time. (format : yyyy-mm-ddThh:mm:ss)");
			} else {
				computerServices.createComputer(commandsForCreation[0], commandsForCreation[1], commandsForCreation[2], commandsForCreation[3]);
			}
			break;
		default :
			System.out.println("Invalid command.");
			break;
		}
	}
	
	/**
	 * Calls computer update service based on the number of arguments given.
	 * @param commandsForUpdate
	 */
	private static void computerUpdate(String[] commandsForUpdate) {
		if (commandsForUpdate.length == 5) {
			if (!isParsableInt(commandsForUpdate[4])) {
				System.out.println("The company id must be a number.");
			} else if (!isParsableLocalDateTime(commandsForUpdate[2])) {
				System.out.println("Invalid format for the introduced date time. (format : yyyy-mm-ddThh:mm:ss)");
			} else if (!isParsableLocalDateTime(commandsForUpdate[3])) {
				System.out.println("Invalid format for the discontinued date time. (format : yyyy-mm-ddThh:mm:ss)");
			} else {
				computerServices.updateComputer(commandsForUpdate[0],commandsForUpdate[1], commandsForUpdate[2], commandsForUpdate[3], commandsForUpdate[4]);
			}
		} else {
			System.out.println("Wrong number of arguments for update.");
		}
	}
	
	/**
	 * Display the help.
	 */
	private static void displayHelp() {
		System.out.println("Command list :");
		System.out.println("	get computers : return list of computers in database.");
		System.out.println("	get computers paginated : enter list of computers paginated mode.");
		System.out.println("	get companies : return list of companies in database.");
		System.out.println("	get computer [id] : return the chosen computer from its id.");
		System.out.println("	create computer [name] : create a new computer in database with only a name.");
		System.out.println("	create computer [name] [date] [date] [companyId] : create a new computer in database with all its attributes.");
		System.out.println("	update computer [id] [name] [date] [date] [companyId] : update all the attributes of the chosen computer from its id.");
		System.out.println("	delete computer [id] : delete the chosen computer from its id.");
		System.out.println("	exit : quit the program.");
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
					
					if(!isParsableInt(paginationCommand)) {
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