package com.excilys.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.model.Company;
import com.excilys.model.Page;
import com.excilys.persistence.CompanyDAO;

@Service
public class CompanyService {

	@Autowired
	private CompanyDAO companyDAO;

	private CompanyService() {
	};

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
	
	public int deleteCompany(long idCompany) {
		return companyDAO.deleteCompany(idCompany);
	}
	
	public Optional<Company> getCompanyById(long idCompany) {
		return companyDAO.getCompanyById(idCompany);
	}

}
