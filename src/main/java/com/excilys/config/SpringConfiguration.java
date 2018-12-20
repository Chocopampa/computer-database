package com.excilys.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

//import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan(value = { "com.excilys.persistence", "com.excilys.service", "com.excilys.mapper",
		"com.excilys.validator", "com.excilys.converter" })
public class SpringConfiguration {

	@Bean
	public DataSource getDataSource() {

		InputStream input = this.getClass().getClassLoader().getResourceAsStream("dbConfig.properties");

		Properties properties = new Properties();
		try {
			properties.load(input);
		} catch (IOException e) {
//			LOG4J.error("Error occured while loading db properties file.");
		}

		HikariConfig config = new HikariConfig();
		config.setJdbcUrl(properties.getProperty("jdbcUrl"));
		config.setUsername(properties.getProperty("dataSource.user"));
		config.setPassword(properties.getProperty("dataSource.password"));
		config.setDriverClassName(properties.getProperty("driverClassName"));
		config.addDataSourceProperty("cachePrepStmts", properties.getProperty("dataSource.cachePrepStmts"));
		config.addDataSourceProperty("prepStmtCacheSize", properties.getProperty("dataSource.prepStmtCacheSize"));
		config.addDataSourceProperty("prepStmtCacheSqlLimit",
				properties.getProperty("dataSource.prepStmtCacheSqlLimit"));

		HikariDataSource ds = new HikariDataSource(config);

		return ds;
	}
	
	@Bean
	public JdbcTemplate getJdbcTemplate(DataSource ds) {
		return new JdbcTemplate(ds);
	}

	Properties hibernateProperties() {
		Properties properties = new Properties();
		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
		properties.setProperty("hibernate.show_sql", "true");
		return properties;
	}

	@Bean
	public LocalSessionFactoryBean sessionFactory(DataSource ds) {
		LocalSessionFactoryBean session = new LocalSessionFactoryBean();
		session.setDataSource(ds);
		session.setHibernateProperties(hibernateProperties());
		session.setPackagesToScan(new String[] { "com.excilys.model" });
		return session;
	}
}
