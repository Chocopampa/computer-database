package com.excilys.ui.cli;


import java.util.InputMismatchException;
import java.util.Scanner;

import com.excilys.service.ComputerServices;

public class DisplayDelete {

	private static ComputerServices computerServices = ComputerServices.getInstance();
	
	protected static void deleteComputer(Scanner sc) {
		long idParsed = -1;
		System.out.println("Please enter the id of the computer you want to delete :");
		try {
			idParsed = sc.nextLong();
			sc.nextLine();
			int nbRowDeleted = computerServices.deleteComputer(idParsed);
			if (nbRowDeleted > 0) {
				System.out.println("The computer with id " + idParsed + " has been deleted. (Number of computers deleted : " + nbRowDeleted + ")");
			} else {
				System.out.println("The computer with id " + idParsed + " has not been deleted.");
			}
		} catch (InputMismatchException e) {
			System.out.println("Please input a number.");
		}
	}

}
