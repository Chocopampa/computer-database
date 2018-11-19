package com.excilys.service;

import java.util.List;

import com.excilys.model.Company;
import com.excilys.persistence.CompanyDAO;

public class CompanyServices {
	
	private CompanyDAO companyDAO = CompanyDAO.getInstance();
	
	private CompanyServices() {};

	private static CompanyServices INSTANCE = new CompanyServices();

	public static CompanyServices getInstance() {
		return INSTANCE;
	}
	
	public void showCompanies() {
		List<Company> companiesList = companyDAO.getCompanies();
		for (Company company : companiesList) {
			System.out.println(company);
		}
	}

}
