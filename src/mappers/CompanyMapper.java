package mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Company;

public class CompanyMapper {

	private CompanyMapper(){}
	
	private static CompanyMapper INSTANCE = new CompanyMapper();

	public static CompanyMapper getINSTANCE() {
		return INSTANCE;
	}
	
	public List<Company> map(ResultSet listCompaniesDb) {
		List<Company> companies = new ArrayList<>();
		
		try {
			while(listCompaniesDb.next()) {
				Company company = new Company();
				company.setId(listCompaniesDb.getInt(1));
				company.setName(listCompaniesDb.getString(2));
				companies.add(company);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return companies;
	}
}
