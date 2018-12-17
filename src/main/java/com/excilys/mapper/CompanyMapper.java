package com.excilys.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.model.Company;

@Component
public class CompanyMapper {

	@Autowired
	public CompanyMapper() {
	}

	public List<Company> mapList(ResultSet listCompaniesDb) throws SQLException {
		List<Company> companies = new ArrayList<>();

		while (listCompaniesDb.next()) {
			Company company = mapUnique(listCompaniesDb);
			companies.add(company);
		}

		return companies;
	}
	
	public Company mapUnique(ResultSet companyDb) throws SQLException {
		return new Company.Builder(companyDb.getLong("id"))
				.withName(companyDb.getString("name")).build();
	}
}
