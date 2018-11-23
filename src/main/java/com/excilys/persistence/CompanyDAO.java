package com.excilys.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.excilys.mapper.CompanyMapper;
import com.excilys.model.Company;
import com.excilys.model.Page;

public class CompanyDAO {

	private static final String REQUEST_COMPANIES = "SELECT * FROM company;";
	private static final String REQUEST_COMPANIES_LIMIT = "SELECT * FROM company LIMIT ?, ?;";
	private CompanyMapper companyMapper = CompanyMapper.getInstance();
	private DatabaseConnection dbConnection = DatabaseConnection.getInstance();
	private static final Logger log4j = LogManager.getLogger(CompanyDAO.class.getName());

	private CompanyDAO() {
	};

	private static final CompanyDAO INSTANCE = new CompanyDAO();

	public static CompanyDAO getInstance() {
		return INSTANCE;
	}

	/**
	 * Get all the database companies.
	 * 
	 * @return the ResultSet
	 */
	public List<Company> getCompanies() {
		List<Company> companies = new ArrayList<>();
		ResultSet rs = null;
		try (PreparedStatement statement = dbConnection.connect().prepareStatement(REQUEST_COMPANIES)) {
			rs = statement.executeQuery();
			companies = companyMapper.mapList(rs);
		} catch (SQLException e) {
			log4j.error("Erreur lors de l'execution de la requête. (Requête : '" + REQUEST_COMPANIES + "')", e);
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				log4j.error("ResultSet did not close successfully", e);
			}
			dbConnection.disconnect();
		}
		return companies;
	}

	/**
	 * Return database companies from first id to last id.
	 * 
	 * @param idFirstComputer
	 * @param idLastComputer
	 * @return
	 */
	public List<Company> getListCompanies(Page page) {
		List<Company> companies = new ArrayList<>();
		ResultSet rs = null;
		try (PreparedStatement statement = dbConnection.connect().prepareStatement(REQUEST_COMPANIES_LIMIT)) {
			log4j.info("Acquiring companies in database...");
			statement.setLong(1, page.getFirstId());
			statement.setInt(2, page.getOffset());
			rs = statement.executeQuery();
			companies = companyMapper.mapList(rs);
		} catch (SQLException e) {
			log4j.error("Erreur lors de l'execution de la requête. (Requête : '" + REQUEST_COMPANIES_LIMIT + "')", e);
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				log4j.error("ResultStatement did not close successfully.", e);
			}
			dbConnection.disconnect();
		}
		return companies;
	}
}
