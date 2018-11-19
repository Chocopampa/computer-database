package com.excilys.ui.cli;

import java.util.Arrays;

import com.excilys.service.ComputerServices;
import com.excilys.validator.ParseValidator;

public class DisplayUpdate {
	
	private static ParseValidator parseValidator = ParseValidator.getInstance();
	private static ComputerServices computerServices = ComputerServices.getInstance();
	
	protected static void displayUpdate(String[] commands) {
		if (commands.length >= 4 && "computer".equalsIgnoreCase(commands[1])) {
			if (!commands[2].isEmpty() && parseValidator.isParsableLong(commands[2])) {
				String[] commandsForUpdate = Arrays.copyOfRange(commands, 2, commands.length);
				computerUpdate(commandsForUpdate);
			} else {
				System.out.println("Please enter the id of the computer to update.");
			}
		} else {
			System.out.println("Can not update anything but computer.");
		}
	}
	
	/**
	 * Calls computer update service based on the number of arguments given.
	 * @param commandsForUpdate
	 */
	private static void computerUpdate(String[] commandsForUpdate) {
		if (commandsForUpdate.length == 5) {
			if (!parseValidator.isParsableLong(commandsForUpdate[4])) {
				System.out.println("The company id must be a number.");
			} else if (!parseValidator.isParsableLocalDateTime(commandsForUpdate[2])) {
				System.out.println("Invalid format for the introduced date time. (format : yyyy-mm-ddThh:mm:ss)");
			} else if (!parseValidator.isParsableLocalDateTime(commandsForUpdate[3])) {
				System.out.println("Invalid format for the discontinued date time. (format : yyyy-mm-ddThh:mm:ss)");
			} else {
				computerServices.updateComputer(commandsForUpdate[0],commandsForUpdate[1], commandsForUpdate[2], commandsForUpdate[3], commandsForUpdate[4]);
			}
		} else {
			System.out.println("Wrong number of arguments for update.");
		}
	}

}
