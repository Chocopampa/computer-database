package com.excilys.validator;

import java.time.LocalDateTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.exception.CompanyException;
import com.excilys.exception.DatesException;
import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.service.CompanyService;

@Component
public class ComputerValidator {

	private static final Logger LOG4J = LogManager.getLogger(ComputerValidator.class.getName());
	
	private final CompanyService companyService;

	@Autowired
	public ComputerValidator(CompanyService companyService) {
		this.companyService = companyService;
	}

	public boolean correctComputer(Computer computer) {
		LOG4J.info("Checking dates and associated company...");
		boolean isCorrect = true;
		try {
			isCorrect = correctDates(computer.getIntroduced(),computer.getDiscontinued()) && companyExists(computer.getCompany());
		} catch (DatesException e) {
			LOG4J.error("The entered dates are not correct.");
			isCorrect = false;
		} catch (CompanyException e) {
			LOG4J.error("The entered company does not exists.");
			isCorrect = false;
		}
		return isCorrect;
	}
	
	private boolean correctDates(LocalDateTime introduced, LocalDateTime discontinued) throws DatesException{
		boolean isCorrect = true;
		if (introduced == null && discontinued != null) {
			isCorrect = false;
			throw new DatesException("The introduced date is null while there is a discontinued date.");
		} else if (discontinued != null && introduced.isAfter(discontinued)){
			isCorrect = false;
			throw new DatesException("The introduced date is after the discontinued date.");
		}
		return isCorrect;
	}
	
	private boolean companyExists(Company company) throws CompanyException{
		boolean isPresent = true;
		if (company != null && !companyService.getCompanyById(company.getId()).isPresent()) {
			isPresent = false;
		}
		if (!isPresent) {
			throw new CompanyException("Company does not exist.");
		}
		return isPresent;
	}
}
