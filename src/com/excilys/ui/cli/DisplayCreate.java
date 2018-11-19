package com.excilys.ui.cli;

import java.util.Arrays;

import com.excilys.service.ComputerServices;
import com.excilys.validator.ParseValidator;

public class DisplayCreate {
	

	private static ParseValidator parseValidator = ParseValidator.getInstance();
	private static ComputerServices computerServices = ComputerServices.getInstance();

	protected static void displayCreate(String[] commands) {
		if (commands.length >= 3 && "computer".equalsIgnoreCase(commands[1])) {
			String[] commandsForCreation = Arrays.copyOfRange(commands, 2, commands.length);
			computerCreation(commandsForCreation);
		} else {
			System.out.println("Can not create anything but computer, and a computer needs at least a name.");
		}
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
			if (!parseValidator.isParsableLong(commandsForCreation[3])) {
				System.out.println("The company id must be a number.");
			} else if (!parseValidator.isParsableLocalDateTime(commandsForCreation[1])) {
				System.out.println("Invalid format for the introduced date time. (format : yyyy-mm-ddThh:mm:ss)");
			} else if (!parseValidator.isParsableLocalDateTime(commandsForCreation[2])) {
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
	
}
