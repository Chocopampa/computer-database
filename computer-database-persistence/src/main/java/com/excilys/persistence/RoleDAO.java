package com.excilys.persistence;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.model.QRole;
import com.excilys.model.Role;
import com.querydsl.jpa.hibernate.HibernateQuery;
import com.querydsl.jpa.hibernate.HibernateQueryFactory;

@Repository
public class RoleDAO {

	@Autowired
	private SessionFactory sessionFactory;

	private static final Logger LOG4J = LogManager.getLogger(ComputerDAO.class.getName());

	@Autowired
	public RoleDAO() {
	}
	
	public Optional<Role> getRoleByName(String roleName) {
		LOG4J.info("Acquiring role by name : " + roleName);
		QRole qrole = QRole.role;
		Session session = sessionFactory.openSession();
		HibernateQueryFactory query = new HibernateQueryFactory(session);
		try {
			HibernateQuery<Role> hRole = query.selectFrom(qrole).where(qrole.name.eq(roleName));
			return Optional.ofNullable(hRole.fetchOne());
		} finally {
			session.close();
		}
	}
}
