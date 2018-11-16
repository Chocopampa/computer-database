package cli;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Scanner;

import services.CompanyServices;
import services.ComputerServices;

public class CommandLineInterface {
	
	private static CompanyServices companyServices = CompanyServices.getInstance();
	private static ComputerServices computerServices = ComputerServices.getInstance();

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
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
						computerServices.showComputers();
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
						String[] commands2 = Arrays.copyOfRange(commands, 2, commands.length);
						computerCreation(commands2);
					} else {
						System.out.println("Can not create anything but computer, and a computer needs at least a name.");
					}
					break;
				case "update" :
					if (commands.length >= 4 && "computer".equalsIgnoreCase(commands[1])) {
						if (!commands[2].isEmpty() && isParsableInt(commands[2])) {
							String[] commands2 = Arrays.copyOfRange(commands, 3, commands.length);
							computerServices.deleteComputer(Integer.parseInt(commands[2]));
						} else {
							System.out.println("Veuillez entrer un nombre.");
						}
					} else {
						System.out.println("Can not update anything but computer.");
					}
					break;
				case "exit" :
					System.out.println("Goodbye!");
					break;
				case "help" :
					System.out.println("Command list :");
					System.out.println("	get computers : return list of computers in database.");
					System.out.println("	get companies : return list of companies in database.");
					System.out.println("	get computer [id] : return the chosen computer from its id.");
					System.out.println("	create computer [name] : create a new computer in database with only a name.");
					System.out.println("	create computer [name] [date] [date] [companyId] : create a new computer in database with all its attributes.");
					System.out.println("	update computer [id] [name] [date] [date] [companyId] : update all the attributes of the chosen computer from its id.");
					System.out.println("	delete computer [id] : delete the chosen computer from its id.");
					System.out.println("	exit : quit the program.");
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
	 * @param commands
	 */
	private static void computerCreation(String[] commands) {
		switch (commands.length) {
		case 1 :
			computerServices.createComputer(commands[0], "null", "null", "null");
			break;
		case 4 :
			if (!isParsableInt(commands[3])) {
				System.out.println("The company id must be a number.");
			} else if (!isParsableLocalDateTime(commands[1])) {
				System.out.println("Invalid format for the introduced date time. (format : yyyy-mm-ddThh:mm:ss)");
			} else if (!isParsableLocalDateTime(commands[2])) {
				System.out.println("Invalid format for the discontinued date time. (format : yyyy-mm-ddThh:mm:ss)");
			} else {
				computerServices.createComputer(commands[0], commands[1], commands[2], commands[3]);
			}
			break;
		default :
			System.out.println("Invalid command.");
			break;
		}
	}
}
