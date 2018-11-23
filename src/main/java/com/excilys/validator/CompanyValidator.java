package com.excilys.validator;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.excilys.model.Company;
import com.excilys.service.CompanyService;

public class CompanyValidator {

	private static final Logger log4j = LogManager.getLogger(CompanyValidator.class.getName());
	private static CompanyService companyServices = CompanyService.getInstance();

	private CompanyValidator() {
	}

	private static final CompanyValidator INSTANCE = new CompanyValidator();

	public static CompanyValidator getInstance() {
		return INSTANCE;
	}

	public boolean companyExists(long idCompany) {
		List<Company> companies = companyServices.getCompanies();
		boolean found = false;
		log4j.info("Checking company ids...");
		for (Company company : companies) {
			if (company.getId() == idCompany) {
				found = true;
				break;
			}
		}
		return found;
	}
}
