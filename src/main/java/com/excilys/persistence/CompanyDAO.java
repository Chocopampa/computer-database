package com.excilys.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.excilys.mapper.CompanyMapper;
import com.excilys.model.Company;
import com.excilys.model.Page;

@Repository
public class CompanyDAO {

	private static final String REQUEST_COMPANIES = "SELECT id, name FROM company;";
	private static final String REQUEST_COMPANY_BY_ID = "SELECT id, name FROM company WHERE id=?;";
	private static final String REQUEST_COMPANIES_LIMIT = "SELECT id, name FROM company LIMIT ?, ?;";
	private static final String DELETE_COMPANY = "DELETE FROM company WHERE id=?;";
	private CompanyMapper companyMapper = CompanyMapper.getInstance();
	private DatabaseConnection dbConnection = DatabaseConnection.getInstance();
	private static final Logger LOG4J = LogManager.getLogger(CompanyDAO.class.getName());

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
			LOG4J.info("Acquiring companies in database...");
			rs = statement.executeQuery();
			companies = companyMapper.mapList(rs);
		} catch (SQLException e) {
			LOG4J.error("Erreur lors de l'execution de la requête. (Requête : '" + REQUEST_COMPANIES + "')", e);
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				LOG4J.error("ResultSet did not close successfully", e);
			}
			dbConnection.disconnect();
		}
		return companies;
	}
	
	/**
	 * Get a company from database with its id.
	 * @param idCompany
	 * @return
	 */
	public Optional<Company> getCompanyById(long idCompany) {
		Company company = null;
		ResultSet rs = null;
		try (PreparedStatement statement = dbConnection.connect().prepareStatement(REQUEST_COMPANY_BY_ID)) {
			statement.setLong(1, idCompany);
			rs = statement.executeQuery();
			rs.next();
			company = companyMapper.mapUnique(rs);
		} catch (SQLException e) {
			LOG4J.error("Erreur lors de l'execution de la requête. (Requête : '" + REQUEST_COMPANY_BY_ID + "')", e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException e) {
				LOG4J.error("ResultStatement did not close successfully.", e);
			}
			dbConnection.disconnect();
		}
		return Optional.ofNullable(company);
	}
	
	/**
	 * Delete a company from its id.
	 * @param idCompany
	 * @return
	 */
	public int deleteCompany(long idCompany) {
		int nbRowAffected = 0;
		try (PreparedStatement statement = dbConnection.connect().prepareStatement(DELETE_COMPANY)) {
			statement.setLong(1, idCompany);
			nbRowAffected = statement.executeUpdate();
			statement.close();
		} catch (SQLException e) {
			LOG4J.error("Erreur lors de l'execution de la requête. (Requête : '" + DELETE_COMPANY + "')", e);
		} finally {
			dbConnection.disconnect();
		}
		return nbRowAffected;
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
			LOG4J.info("Acquiring companies in database...");
			statement.setLong(1, page.getFirstId());
			statement.setInt(2, page.getOffset());
			rs = statement.executeQuery();
			companies = companyMapper.mapList(rs);
		} catch (SQLException e) {
			LOG4J.error("Erreur lors de l'execution de la requête. (Requête : '" + REQUEST_COMPANIES_LIMIT + "')", e);
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				LOG4J.error("ResultStatement did not close successfully.", e);
			}
			dbConnection.disconnect();
		}
		return companies;
	}
}
