package com.excilys.persistence;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.model.QUser;
import com.excilys.model.User;
import com.querydsl.jpa.hibernate.HibernateQuery;
import com.querydsl.jpa.hibernate.HibernateQueryFactory;

@Repository
public class UserDAO {

	@Autowired
	private SessionFactory sessionFactory;

	private static final Logger LOG4J = LogManager.getLogger(ComputerDAO.class.getName());

	@Autowired
	public UserDAO() {
	}

	public Optional<User> getUserByName(String name) {
		QUser quser = QUser.user;
		Session session = sessionFactory.openSession();
		HibernateQueryFactory query = new HibernateQueryFactory(session);
		try {
			HibernateQuery<User> hComputer = query.selectFrom(quser).where(quser.name.eq(name));
			return Optional.ofNullable(hComputer.fetchOne());
		} finally {
			session.close();
		}
	}
}
