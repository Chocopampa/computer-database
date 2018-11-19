package com.excilys.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.model.Company;

public class CompanyMapper {

	private CompanyMapper(){}
	
	private static final CompanyMapper INSTANCE = new CompanyMapper();

	public static CompanyMapper getInstance() {
		return INSTANCE;
	}
	
	public List<Company> map(ResultSet listCompaniesDb) throws SQLException {
		List<Company> companies = new ArrayList<>();
		
		while(listCompaniesDb.next()) {
			Company company = new Company();
			company.setId(listCompaniesDb.getInt(1));
			company.setName(listCompaniesDb.getString(2));
			companies.add(company);
		}
		
		return companies;
	}
}
