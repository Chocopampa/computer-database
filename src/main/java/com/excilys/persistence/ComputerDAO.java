package com.excilys.persistence;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.model.Computer;
import com.excilys.model.Page;
import com.excilys.model.QComputer;
import com.querydsl.jpa.hibernate.HibernateQuery;
import com.querydsl.jpa.hibernate.HibernateQueryFactory;

@Repository
public class ComputerDAO {

	@Autowired
	private SessionFactory sessionFactory;

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
		Session session = sessionFactory.openSession();
		HibernateQueryFactory query = new HibernateQueryFactory(session);
		try {
			HibernateQuery<Computer> hComputers = query.selectFrom(QComputer.computer);
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
	 */
	public int addComputer(Computer computer) {
		LOG4J.info("Adding a computer...");
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		try {
			session.save(computer);
			session.getTransaction().commit();
		} finally {
			session.close();
		}

		return 1;
	}

	/**
	 * Create a computer in database.
	 */
	public int addComputerWithoutCompany(Computer computer) {
		LOG4J.info("Adding a computer without company...");
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		try {
			session.save(computer);
			session.getTransaction().commit();
		} finally {
			session.close();
		}

		return 1;
	}

	/**
	 * Delete a computer in database.
	 * 
	 * @param idComputer
	 */
	public int deleteComputerFromId(long idComputer) {
		LOG4J.info("Deleting computer...");
		Session session = sessionFactory.openSession();
		HibernateQueryFactory query = new HibernateQueryFactory(session);
		QComputer qcomputer = QComputer.computer;
		long deleted = 0;
		try { 
			deleted = query.delete(qcomputer).where(qcomputer.id.eq(idComputer)).execute();
		} finally {
			session.close();
		}
		if (deleted == 0) {
			return 0;
		} else {
			return 1;
		}
	}

	/**
	 * Update a computer in database.
	 * 
	 * @param name
	 * @param introduced
	 * @param discontinued
	 * @param company_id
	 */
	public int updateComputer(Computer computer) {
		LOG4J.info("Update of computer : " + computer);
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		try {
			session.update(computer);
			session.getTransaction().commit();
		} finally {
			session.close();
		}

		return 1;
	}

}
