package com.excilys.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.excilys.model.Company;
import com.excilys.model.Computer;

public class ComputerMapper {

	private ComputerMapper() {
	}

	private static final ComputerMapper INSTANCE = new ComputerMapper();

	public static ComputerMapper getInstance() {
		return INSTANCE;
	}

	public List<Computer> mapList(ResultSet listComputerDb) throws SQLException {
		List<Computer> computers = new ArrayList<>();

		while (listComputerDb.next()) {
			Computer computer = mapUnique(listComputerDb);
			computers.add(computer);
		}

		return computers;
	}

	public Computer mapUnique(ResultSet computerDb) throws SQLException {
		LocalDateTime timeIntroduced = null;
		LocalDateTime timeDiscontinued = null;

		String introduced = computerDb.getString("introduced");
		if (introduced != null) {
			introduced = introduced.replace(' ', 'T');
			timeIntroduced = LocalDateTime.parse(introduced, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
		}

		String discontinued = computerDb.getString("discontinued");
		if (discontinued != null) {
			discontinued = discontinued.replace(' ', 'T');
			timeDiscontinued = LocalDateTime.parse(discontinued, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
		}

		Company company = new Company.Builder(computerDb.getLong("company_id")).build();

		return new Computer.Builder(computerDb.getString("name")).withId(computerDb.getInt("id"))
				.withIntroduced(timeIntroduced).withDiscontinued(timeDiscontinued).withCompany(company).build();
	}

	public Computer mapUnique(String name, String introduced, String discontinued, String companyNumber) {
		Computer computer = null;
		LocalDateTime timeIntroduced = null;
		LocalDateTime timeDiscontinued = null;

		if (!introduced.isEmpty()) {
			introduced = introduced + "T00:00:00";
			timeIntroduced = LocalDateTime.parse(introduced, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
		}

		if (!discontinued.isEmpty()) {
			discontinued = discontinued + "T00:00:00";
			timeDiscontinued = LocalDateTime.parse(discontinued, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
		}

		Company company = null;

		if (!companyNumber.isEmpty()) {
			company =  new Company.Builder(Long.parseLong(companyNumber)).build();
		}

		computer = new Computer.Builder(name).withIntroduced(timeIntroduced).withDiscontinued(timeDiscontinued)
				.withCompany(company).build();
		return computer;
	}

}
