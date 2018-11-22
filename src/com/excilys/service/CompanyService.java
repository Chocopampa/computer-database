package com.excilys.service;

import java.util.List;

import com.excilys.model.Company;
import com.excilys.model.Page;
import com.excilys.persistence.CompanyDAO;

public class CompanyService {
	
	private CompanyDAO companyDAO = CompanyDAO.getInstance();
	
	private CompanyService() {};

	private static CompanyService INSTANCE = new CompanyService();

	public static CompanyService getInstance() {
		return INSTANCE;
	}
	
	public List<Company> getCompanies() {
		return companyDAO.getCompanies();
	}
	
	public List<Company> getPagedCompanies(Page page) {
		return companyDAO.getListCompanies(page);
	}

}
