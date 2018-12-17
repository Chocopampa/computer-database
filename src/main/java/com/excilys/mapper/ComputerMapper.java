package com.excilys.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.dto.ComputerDTO;
import com.excilys.model.Company;
import com.excilys.model.Computer;

@Component
public class ComputerMapper {

	@Autowired
	public ComputerMapper() {
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

		Company company = new Company.Builder(computerDb.getLong("company_id")).withName(computerDb.getString("companyName")).build();

		return new Computer.Builder(computerDb.getString("compuName")).withId(computerDb.getInt("compuId"))
				.withIntroduced(timeIntroduced).withDiscontinued(timeDiscontinued).withCompany(company).build();
	}

	public Computer mapUnique(ComputerDTO computerDTO) {
		Company company = new Company.Builder(computerDTO.getCompanyId()).withName(computerDTO.getCompanyName()).build();

		return new Computer.Builder(computerDTO.getName()).withIntroduced(computerDTO.getIntroduced()).withDiscontinued(computerDTO.getDiscontinued())
				.withCompany(company).build();
	}

}
