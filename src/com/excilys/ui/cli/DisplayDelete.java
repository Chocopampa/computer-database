package com.excilys.ui.cli;


import com.excilys.service.ComputerServices;
import com.excilys.validator.ParseValidator;

public class DisplayDelete {
	

	private static ParseValidator parseValidator = ParseValidator.getInstance();
	private static ComputerServices computerServices = ComputerServices.getInstance();
	
	protected static void displayDelete(String[] commands) {
		if (commands.length >= 3 && "computer".equalsIgnoreCase(commands[1])) {
			if (parseValidator.isParsableLong(commands[2])) {
				computerServices.deleteComputer(Long.parseLong(commands[2]));
			} else {
				System.out.println("Veuillez entrer un nombre.");
			}
		} else {
			System.out.println("Can not delete anything but computer with its id. (delete computer [id])");
		}
	}

}
