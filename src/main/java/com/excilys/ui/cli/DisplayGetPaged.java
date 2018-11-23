package com.excilys.ui.cli;

import java.util.List;
import java.util.Scanner;

import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.model.Page;
import com.excilys.service.CompanyService;
import com.excilys.service.ComputerService;

public class DisplayGetPaged {

	private static CompanyService companyServices = CompanyService.getInstance();
	private static ComputerService computerServices = ComputerService.getInstance();

	/**
	 * Paginated computers function.
	 * 
	 * @param sc
	 */
	protected static void getPagedItems(Scanner sc) {
		System.out.println("computers or companies ?");
		String type = sc.nextLine();
		int offset = 0;
		long idFirstComputer = 0;
		System.out.println("Input the id of the first computer of the list :");

		idFirstComputer = sc.nextLong();
		sc.nextLine();
		System.out.println("Input the number of items per page :");
		offset = sc.nextInt();
		sc.nextLine();

		Page page = new Page(idFirstComputer, offset);

		switch (type) {
		case "companies":
			getCompanies(sc, page);
		case "computers":
			getComputers(sc, page);
		default:
			break;
		}
	}

	private static void getComputers(Scanner sc, Page page) {
		displayPagedComputers(computerServices.getPagedComputers(page));

		String str = "";
		while (!"exit".equals(str)) {
			System.out.println("Navigate (next, previous, exit) :");
			str = sc.nextLine();
			switch (str) {
			case "next":
				page = page.next();
				displayPagedComputers(computerServices.getPagedComputers(page));
				break;
			case "previous":
				page = page.previous();
				displayPagedComputers(computerServices.getPagedComputers(page));
				break;
			case "number":
				System.out.println("Input the page number :");
				int nb = sc.nextInt();
				page = page.pageNumber(nb);
				displayPagedComputers(computerServices.getPagedComputers(page));
				break;
			case "offset":
				System.out.println("Input the new number of items per page :");
				int newOffset = sc.nextInt();
				page.setOffset(newOffset);
				displayPagedComputers(computerServices.getPagedComputers(page));
				sc.nextLine();
				break;
			case "exit":
				break;
			default:
				System.out.println("Wrong command");
			}
		}
	}

	private static void getCompanies(Scanner sc, Page page) {
		displayPagedCompanies(companyServices.getPagedCompanies(page));

		String str = "";
		while (!"exit".equals(str)) {
			System.out.println("Navigate (next, previous, exit) :");
			str = sc.nextLine();
			switch (str) {
			case "next":
				page = page.next();
				displayPagedCompanies(companyServices.getPagedCompanies(page));
				break;
			case "previous":
				page = page.previous();
				displayPagedCompanies(companyServices.getPagedCompanies(page));
				break;
			case "number":
				System.out.println("Input the page number :");
				int nb = sc.nextInt();
				page = page.pageNumber(nb);
				displayPagedCompanies(companyServices.getPagedCompanies(page));
				break;
			case "offset":
				System.out.println("Input the new number of items per page :");
				int newOffset = sc.nextInt();
				page.setOffset(newOffset);
				displayPagedCompanies(companyServices.getPagedCompanies(page));
				sc.nextLine();
				break;
			case "exit":
				break;
			default:
				System.out.println("Wrong command");
			}
		}
	}

	private static void displayPagedComputers(List<Computer> computers) {
		for (Computer computer : computers) {
			System.out.println(computer);
		}
	}

	private static void displayPagedCompanies(List<Company> companies) {
		for (Company companie : companies) {
			System.out.println(companie);
		}
	}
}
