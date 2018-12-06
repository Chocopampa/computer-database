package com.excilys.validator;

import java.time.LocalDateTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.excilys.exception.CompanyException;
import com.excilys.exception.DatesException;
import com.excilys.model.Computer;
import com.excilys.service.CompanyService;

public class ComputerValidator {

	private static final Logger LOG4J = LogManager.getLogger(ComputerValidator.class.getName());
	@Autowired
	private static CompanyService companyServices;

	private ComputerValidator() {
	}

	private static final ComputerValidator INSTANCE = new ComputerValidator();

	public static ComputerValidator getInstance() {
		return INSTANCE;
	}

	public boolean correctComputer(Computer computer) throws DatesException, CompanyException {
		LOG4J.info("Checking dates and associated company...");
		return (correctDates(computer.getIntroduced(),computer.getDiscontinued()) && companyExists(computer.getCompany().getId()));
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
	
	private boolean companyExists(long idCompany) throws CompanyException{
		boolean isPresent = companyServices.getCompanyById(idCompany).isPresent();
		if (!isPresent) {
			throw new CompanyException("Company does not exist.");
		}
		return isPresent;
	}
}
