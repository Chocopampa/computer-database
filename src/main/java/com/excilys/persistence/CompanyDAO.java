package com.excilys.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.mapper.CompanyMapper;
import com.excilys.model.Company;
import com.excilys.model.Page;

@Repository
public class CompanyDAO {
	
	private final CompanyMapper companyMapper;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static final String REQUEST_COMPANIES = "SELECT id, name FROM company;";
	private static final String REQUEST_COMPANY_BY_ID = "SELECT id, name FROM company WHERE id=?;";
	private static final String REQUEST_COMPANIES_LIMIT = "SELECT id, name FROM company LIMIT ?, ?;";
	private static final String DELETE_COMPANY = "DELETE FROM company WHERE id=?;";

	private final RowMapper<Company> rmCompany = new RowMapper<Company>() {
		public Company mapRow(ResultSet rs, int rowNum) throws SQLException {
			return companyMapper.mapUnique(rs);
		}
	};

	private static final Logger LOG4J = LogManager.getLogger(CompanyDAO.class.getName());

	@Autowired
	public CompanyDAO(CompanyMapper companyMapper) {
		this.companyMapper = companyMapper;
	}
	

	/**
	 * Get all the database companies.
	 * 
	 * @return the ResultSet
	 */
	public List<Company> getCompanies() {
		LOG4J.info("Acquiring companies...");
		List<Company> companies = new ArrayList<>();
		try {
			companies = jdbcTemplate.query(REQUEST_COMPANIES, new Object[] {}, rmCompany);
		} catch (DataAccessException e) {
			LOG4J.error("Error accessing the database for request : " + REQUEST_COMPANIES, e);
		}
		return companies;
	}

	/**
	 * Get a company from database with its id.
	 * 
	 * @param idCompany
	 * @return
	 */
	public Optional<Company> getCompanyById(long idCompany) {
		LOG4J.info("Acquiring company by id :" + idCompany);
		Company company = null;
		try {
			company = jdbcTemplate.queryForObject(REQUEST_COMPANY_BY_ID, new Object[] { idCompany }, rmCompany);
		} catch (DataAccessException e) {
			LOG4J.error("Error accessing the database for request : " + REQUEST_COMPANY_BY_ID, e);
		}
		return Optional.ofNullable(company);
	}

	/**
	 * Delete a company from its id.
	 * 
	 * @param idCompany
	 * @return
	 */
	@Transactional(rollbackFor=DataAccessException.class)
	public int deleteCompany(long idCompany) {
		int nbRowAffected = 0;
		try {
			nbRowAffected = jdbcTemplate.update(DELETE_COMPANY, new Object[] { idCompany });
		} catch (DataAccessException e) {
			LOG4J.error("Error accessing the database for request : " + DELETE_COMPANY, e);
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
		List<Company> computers = new ArrayList<>();
		try {
			computers = jdbcTemplate.query(REQUEST_COMPANIES_LIMIT,
					new Object[] { page.getFirstId(), page.getOffset() }, rmCompany);
		} catch (DataAccessException e) {
			LOG4J.error("Error accessing the database for request : " + REQUEST_COMPANIES_LIMIT, e);
		}
		return computers;
	}
}
