package com.excilys.validator;

import java.util.List;

import com.excilys.model.Company;
import com.excilys.service.CompanyServices;

public class CompanyValidator {

	private CompanyValidator() {}
	
	private static final CompanyValidator INSTANCE = new CompanyValidator();
	
	public static CompanyValidator getInstance() {
		return INSTANCE;
	}
	
	private static CompanyServices companyServices = CompanyServices.getInstance();
	
	public boolean companyExists(long idCompany) {
		List<Company> companies = companyServices.getCompanies();
		boolean found = false;
		for (Company company : companies) {
			if (company.getId() == idCompany) {
				found = true;
				break;
			}
		}
		return found;
	}
}
