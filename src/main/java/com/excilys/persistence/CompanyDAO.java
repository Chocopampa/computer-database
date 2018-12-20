package com.excilys.persistence;

import java.util.List;
import java.util.Optional;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.model.Company;
import com.excilys.model.Page;
import com.excilys.model.QCompany;
import com.querydsl.jpa.hibernate.HibernateQuery;
import com.querydsl.jpa.hibernate.HibernateQueryFactory;

@Repository
public class CompanyDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private SessionFactory sessionFactory;

	private static final String DELETE_COMPANY = "DELETE FROM company WHERE id=?;";

	private static final Logger LOG4J = LogManager.getLogger(CompanyDAO.class.getName());

	@Autowired
	public CompanyDAO() {
	}

	/**
	 * Get all the database companies.
	 * 
	 * @return the ResultSet
	 */
	public List<Company> getCompanies() {
		LOG4J.info("Acquiring companies...");
		Session session = sessionFactory.openSession();
		HibernateQueryFactory query = new HibernateQueryFactory(session);
		try {
			HibernateQuery<Company> hCompanies = query.selectFrom(QCompany.company);
			return hCompanies.fetch();
		} finally {
			session.close();
		}
	}

	/**
	 * Get a company from database with its id.
	 * 
	 * @param idCompany
	 * @return
	 */
	public Optional<Company> getCompanyById(long idCompany) {
		LOG4J.info("Acquiring company by id :" + idCompany);
		QCompany qcompany = QCompany.company;
		Session session = sessionFactory.openSession();
		HibernateQueryFactory query = new HibernateQueryFactory(session);
		try {
			HibernateQuery<Company> hCompany = query.selectFrom(qcompany).where(qcompany.id.eq(idCompany));
			return Optional.ofNullable(hCompany.fetchOne());
		} finally {
			session.close();
		}
	}

	/**
	 * Delete a company from its id.
	 * 
	 * @param idCompany
	 * @return
	 */
	@Transactional(rollbackFor = DataAccessException.class)
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
		Session session = sessionFactory.openSession();
		HibernateQueryFactory query = new HibernateQueryFactory(session);
		try {
			HibernateQuery<Company> hCompanies = query.selectFrom(QCompany.company).limit(page.getFirstId()).offset(page.getOffset());
			return hCompanies.fetch();
		} finally {
			session.close();
		}
	}
}
