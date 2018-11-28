package com.excilys.validator;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.service.CompanyService;

public class ComputerValidator {

	private static final Logger LOG4J = LogManager.getLogger(ComputerValidator.class.getName());
	private static CompanyService companyServices = CompanyService.getInstance();

	private ComputerValidator() {
	}

	private static final ComputerValidator INSTANCE = new ComputerValidator();

	public static ComputerValidator getInstance() {
		return INSTANCE;
	}

	public boolean correctComputer(Computer computer) {
		LOG4J.info("Checking dates and associated company...");
		return (correctDates(computer.getIntroduced(),computer.getDiscontinued()) && companyExists(computer.getCompany().getId()));
	}
	
	private boolean correctDates(LocalDateTime introduced, LocalDateTime discontinued) {
		boolean isCorrect = true;
		if (introduced == null && discontinued != null) {
			isCorrect = false;
		} else if (discontinued != null && introduced.isAfter(discontinued)){
			isCorrect = false;
		}
		return isCorrect;
	}
	
	private boolean companyExists(long idCompany) {
		return companyServices.getCompanyById(idCompany).isPresent();
	}
}
