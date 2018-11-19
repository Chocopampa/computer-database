package com.excilys.ui.cli;

import java.util.Scanner;

public class CommandLineInterface {
	
	private static Scanner sc = new Scanner(System.in);

	public static void run() {
		String str = "";
		while (!"exit".equals(str)) {
			System.out.println("Input a command (type help for help) :");
			str = sc.nextLine();
			String[] commands = str.split(" ");
			switch(commands[0]) {
				case "get" :
					DisplayGet.getResults(commands);
					break;
				case "delete" :
					DisplayDelete.displayDelete(commands);
					break;
				case "create" :
					DisplayCreate.displayCreate(commands);
					break;
				case "update" :
					DisplayUpdate.displayUpdate(commands);
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
	
}