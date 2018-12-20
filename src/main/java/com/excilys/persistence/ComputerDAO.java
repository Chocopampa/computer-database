package com.excilys.persistence;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameterValue;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.model.Computer;
import com.excilys.model.Page;
import com.excilys.model.QComputer;
import com.querydsl.jpa.hibernate.HibernateQuery;
import com.querydsl.jpa.hibernate.HibernateQueryFactory;

@Repository
public class ComputerDAO {


	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private SessionFactory sessionFactory;

	private static final String INSERT_COMPUTER = "INSERT INTO computer(name,introduced,discontinued,company_id) VALUES (?,?,?,?);";
	private static final String INSERT_COMPUTER_WITHOUT_COMPANY = "INSERT INTO computer(name,introduced,discontinued) VALUES (?,?,?);";
	private static final String DELETE_COMPUTER = "DELETE FROM computer WHERE id=?;";
	private static final String UPDATE_COMPUTER = "UPDATE computer SET name=?,introduced=?,discontinued=?,company_id=? WHERE id=?;";

	private static final Logger LOG4J = LogManager.getLogger(ComputerDAO.class.getName());

	@Autowired
	public ComputerDAO() {
	}

	/**
	 * Get all the database computers.
	 * 
	 * @return the ResultSet
	 */
	public List<Computer> getComputers() {
		LOG4J.info("Acquiring computers...");
		QComputer qcomputer = QComputer.computer;
		Session session = sessionFactory.openSession();
		HibernateQueryFactory query = new HibernateQueryFactory(session);
		try {
			HibernateQuery<Computer> hComputers = query.selectFrom(qcomputer);
			return hComputers.fetch();
		} finally {
			session.close();
		}
	}

	/**
	 * Get the database computer with specified id.
	 * 
	 * @param idComputer
	 * @return
	 */
	public Optional<Computer> getComputerById(long idComputer) {
		LOG4J.info("Acquiring computer by id : " + idComputer);
		QComputer qcomputer = QComputer.computer;
		Session session = sessionFactory.openSession();
		HibernateQueryFactory query = new HibernateQueryFactory(session);
		try {
			HibernateQuery<Computer> hComputer = query.selectFrom(qcomputer).where(qcomputer.id.eq(idComputer));
			return Optional.ofNullable(hComputer.fetchOne());
		} finally {
			session.close();
		}
	}

	/**
	 * Get computers by searching for a chain of characters in their name or in the
	 * name of their associated company.
	 * 
	 * @param search
	 * @return
	 */
	public List<Computer> getComputersWithSearch(String search) {
		LOG4J.info("Acquiring computers...");
		QComputer qcomputer = QComputer.computer;
		Session session = sessionFactory.openSession();
		HibernateQueryFactory query = new HibernateQueryFactory(session);
		try {
			HibernateQuery<Computer> hComputers = query.selectFrom(qcomputer).where(
					qcomputer.company().name.like("%" + search + "%").or(qcomputer.name.like("%" + search + "%")));
			return hComputers.fetch();
		} finally {
			session.close();
		}
	}

	public List<Computer> getComputersFromCompanyId(long idCompany) {
		LOG4J.info("Acquiring computers by id : " + idCompany);
		QComputer qcomputer = QComputer.computer;
		Session session = sessionFactory.openSession();
		HibernateQueryFactory query = new HibernateQueryFactory(session);
		try {
			HibernateQuery<Computer> hComputers = query.selectFrom(qcomputer)
					.where(qcomputer.company().id.eq(idCompany));
			return hComputers.fetch();
		} finally {
			session.close();
		}
	}

	/**
	 * Return database computers from first id to last id.
	 * 
	 * @param idFirstComputer
	 * @param idLastComputer
	 * @return
	 */
	public List<Computer> getListComputers(Page page) {
		LOG4J.info("Acquiring computers...");
		Session session = sessionFactory.openSession();
		HibernateQueryFactory query = new HibernateQueryFactory(session);
		try {
			HibernateQuery<Computer> hComputers = query.selectFrom(QComputer.computer).offset(page.getFirstId())
					.limit(page.getOffset());
			return hComputers.fetch();
		} finally {
			session.close();
		}
	}

	/**
	 * Return database computers from first id to last id order by name.
	 * 
	 * @param idFirstComputer
	 * @param idLastComputer
	 * @return
	 */
	public List<Computer> getListComputersOrderByName(Page page, String orderDirection) {
		LOG4J.info("Acquiring computers...");
		QComputer qcomputer = QComputer.computer;
		Session session = sessionFactory.openSession();
		HibernateQueryFactory query = new HibernateQueryFactory(session);
		try {
			HibernateQuery<Computer> hComputers = null;
			switch (orderDirection) {
			case "ASC":
				hComputers = query.selectFrom(qcomputer).offset(page.getFirstId()).limit(page.getOffset())
						.orderBy(qcomputer.name.asc());
				break;
			case "DESC":
				hComputers = query.selectFrom(qcomputer).offset(page.getFirstId()).limit(page.getOffset())
						.orderBy(qcomputer.name.desc());
				break;
			default:
				hComputers = query.selectFrom(qcomputer).offset(page.getFirstId()).limit(page.getOffset());
				break;
			}

			return hComputers.fetch();
		} finally {
			session.close();
		}
	}

	/**
	 * Return database computers from first id to last id order by name.
	 * 
	 * @param idFirstComputer
	 * @param idLastComputer
	 * @return
	 */
	public List<Computer> getListComputersOrderByIntroduced(Page page, String orderDirection) {
		LOG4J.info("Acquiring computers...");
		QComputer qcomputer = QComputer.computer;
		Session session = sessionFactory.openSession();
		HibernateQueryFactory query = new HibernateQueryFactory(session);
		try {
			HibernateQuery<Computer> hComputers = null;
			switch (orderDirection) {
			case "ASC":
				hComputers = query.selectFrom(qcomputer).offset(page.getFirstId()).limit(page.getOffset())
						.orderBy(qcomputer.introduced.asc());
				break;
			case "DESC":
				hComputers = query.selectFrom(qcomputer).offset(page.getFirstId()).limit(page.getOffset())
						.orderBy(qcomputer.introduced.desc());
				break;
			default:
				hComputers = query.selectFrom(qcomputer).offset(page.getFirstId()).limit(page.getOffset());
				break;
			}

			return hComputers.fetch();
		} finally {
			session.close();
		}
	}

	/**
	 * Return database computers from first id to last id order by name.
	 * 
	 * @param idFirstComputer
	 * @param idLastComputer
	 * @return
	 */
	public List<Computer> getListComputersOrderByDiscontinued(Page page, String orderDirection) {
		LOG4J.info("Acquiring computers...");
		QComputer qcomputer = QComputer.computer;
		Session session = sessionFactory.openSession();
		HibernateQueryFactory query = new HibernateQueryFactory(session);
		try {
			HibernateQuery<Computer> hComputers = null;
			switch (orderDirection) {
			case "ASC":
				hComputers = query.selectFrom(qcomputer).offset(page.getFirstId()).limit(page.getOffset())
						.orderBy(qcomputer.discontinued.asc());
				break;
			case "DESC":
				hComputers = query.selectFrom(qcomputer).offset(page.getFirstId()).limit(page.getOffset())
						.orderBy(qcomputer.discontinued.desc());
				break;
			default:
				hComputers = query.selectFrom(qcomputer).offset(page.getFirstId()).limit(page.getOffset());
				break;
			}

			return hComputers.fetch();
		} finally {
			session.close();
		}
	}

	/**
	 * Return database computers from first id to last id order by name.
	 * 
	 * @param idFirstComputer
	 * @param idLastComputer
	 * @return
	 */
	public List<Computer> getListComputersOrderByCompany(Page page, String orderDirection) {
		LOG4J.info("Acquiring computers...");
		QComputer qcomputer = QComputer.computer;
		Session session = sessionFactory.openSession();
		HibernateQueryFactory query = new HibernateQueryFactory(session);
		try {
			HibernateQuery<Computer> hComputers = null;
			switch (orderDirection) {
			case "ASC":
				hComputers = query.selectFrom(qcomputer).offset(page.getFirstId()).limit(page.getOffset())
						.orderBy(qcomputer.company().name.asc());
				break;
			case "DESC":
				hComputers = query.selectFrom(qcomputer).offset(page.getFirstId()).limit(page.getOffset())
						.orderBy(qcomputer.company().name.desc());
				break;
			default:
				hComputers = query.selectFrom(qcomputer).offset(page.getFirstId()).limit(page.getOffset());
				break;
			}

			return hComputers.fetch();
		} finally {
			session.close();
		}
	}

	/**
	 * Create a computer in database.
	 * 
	 * @param name
	 * @param introduced
	 * @param discontinued
	 * @param company_id
	 */
	@Transactional(rollbackFor = DataAccessException.class)
	public int addComputer(Computer computer) {
		List<Object> params = new ArrayList<>();

		params.add(new SqlParameterValue(Types.VARCHAR, computer.getName()));

		if (computer.getIntroduced() != null) {
			params.add(new SqlParameterValue(Types.VARCHAR, computer.getIntroduced().toString()));
		} else {
			params.add(new SqlParameterValue(Types.VARCHAR, null));
		}

		if (computer.getDiscontinued() != null) {
			params.add(new SqlParameterValue(Types.VARCHAR, computer.getDiscontinued().toString()));
		} else {
			params.add(new SqlParameterValue(Types.VARCHAR, null));
		}

		if (computer.getCompany() != null) {
			params.add(new SqlParameterValue(Types.BIGINT, computer.getCompany().getId()));
		} else {
			params.add(new SqlParameterValue(Types.BIGINT, null));
		}

		Object[] vParams = params.toArray();

		int nbRowAffected = 0;
		try {
			nbRowAffected = jdbcTemplate.update(INSERT_COMPUTER, vParams);
		} catch (DataAccessException e) {
			LOG4J.error("Error accessing the database for request : " + INSERT_COMPUTER, e);
		}

		return nbRowAffected;
	}

	/**
	 * Create a computer in database.
	 * 
	 * @param name
	 * @param introduced
	 * @param discontinued
	 * @param company_id
	 */
	@Transactional(rollbackFor = DataAccessException.class)
	public int addComputerWithoutCompany(Computer computer) {
		List<Object> params = new ArrayList<>();

		params.add(new SqlParameterValue(Types.VARCHAR, computer.getName()));

		if (computer.getIntroduced() != null) {
			params.add(new SqlParameterValue(Types.VARCHAR, computer.getIntroduced().toString()));
		} else {
			params.add(new SqlParameterValue(Types.VARCHAR, null));
		}

		if (computer.getDiscontinued() != null) {
			params.add(new SqlParameterValue(Types.VARCHAR, computer.getDiscontinued().toString()));
		} else {
			params.add(new SqlParameterValue(Types.VARCHAR, null));
		}

		Object[] vParams = params.toArray();

		int nbRowAffected = 0;
		try {
			nbRowAffected = jdbcTemplate.update(INSERT_COMPUTER_WITHOUT_COMPANY, vParams);
		} catch (DataAccessException e) {
			LOG4J.error("Error accessing the database for request : " + INSERT_COMPUTER_WITHOUT_COMPANY, e);
		}

		return nbRowAffected;
	}

	/**
	 * Delete a computer in database.
	 * 
	 * @param idComputer
	 */
	@Transactional(rollbackFor = DataAccessException.class)
	public int deleteComputerFromId(long idComputer) {
		int nbRowAffected = 0;
		try {
			nbRowAffected = jdbcTemplate.update(DELETE_COMPUTER, new Object[] { idComputer });
		} catch (DataAccessException e) {
			LOG4J.error("Error accessing the database for request : " + DELETE_COMPUTER, e);
		}

		return nbRowAffected;
	}

	/**
	 * Update a computer in database.
	 * 
	 * @param name
	 * @param introduced
	 * @param discontinued
	 * @param company_id
	 */
	@Transactional(rollbackFor = DataAccessException.class)
	public int updateComputer(Computer computer) {
		List<Object> params = new ArrayList<>();

		params.add(new SqlParameterValue(Types.VARCHAR, computer.getName()));

		if (computer.getIntroduced() != null) {
			params.add(new SqlParameterValue(Types.VARCHAR, computer.getIntroduced().toString()));
		} else {
			params.add(new SqlParameterValue(Types.VARCHAR, null));
		}

		if (computer.getDiscontinued() != null) {
			params.add(new SqlParameterValue(Types.VARCHAR, computer.getDiscontinued().toString()));
		} else {
			params.add(new SqlParameterValue(Types.VARCHAR, null));
		}

		if (computer.getCompany() != null) {
			params.add(new SqlParameterValue(Types.BIGINT, computer.getCompany().getId()));
		} else {
			params.add(new SqlParameterValue(Types.BIGINT, null));
		}

		params.add(new SqlParameterValue(Types.BIGINT, computer.getId()));

		Object[] vParams = params.toArray();

		int nbRowAffected = 0;
		try {
			nbRowAffected = jdbcTemplate.update(UPDATE_COMPUTER, vParams);
		} catch (DataAccessException e) {
			LOG4J.error("Error accessing the database for request : " + UPDATE_COMPUTER, e);
		}

		return nbRowAffected;
	}

}
