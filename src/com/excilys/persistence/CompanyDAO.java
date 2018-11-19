package com.excilys.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.mapper.CompanyMapper;
import com.excilys.model.Company;

public class CompanyDAO {
	
	private static final String REQUEST_COMPANIES = "SELECT * FROM company;";
	private CompanyMapper companyMapper = CompanyMapper.getInstance();
	
	private CompanyDAO() {};

	private static final CompanyDAO INSTANCE = new CompanyDAO();

	public static CompanyDAO getInstance() {
		return INSTANCE;
	}
	
	/**
	 * Get all the database companies.
	 * @return the ResultSet
	 */
	public List<Company> getCompanies() {
		DatabaseConnection dbConnection = new DatabaseConnection();
		List<Company> companies = new ArrayList<>();
		ResultSet rs = null; 
		try (PreparedStatement statement = dbConnection.connect().prepareStatement(REQUEST_COMPANIES)){
			rs = statement.executeQuery();
			companies = companyMapper.mapList(rs);
		} catch (SQLException e) {
			System.out.println("Erreur lors de l'execution de la requête. (Requête : '" + REQUEST_COMPANIES + "')");
		    e.printStackTrace();
		} finally {

			try {
				rs.close();
			} catch (SQLException e) {
				System.out.println("ResultSet did not close successfully");
			}

			dbConnection.disconnect();
		}
		return companies;
	}
}
