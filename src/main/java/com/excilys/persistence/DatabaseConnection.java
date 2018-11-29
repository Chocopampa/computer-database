package com.excilys.persistence;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;

public class DatabaseConnection {

	private Connection connection;
	private static HikariDataSource ds;

	private static final Logger LOG4J = LogManager.getLogger(DatabaseConnection.class.getName());

	static {
		InputStream input = DatabaseConnection.class.getClassLoader().getResourceAsStream("dbConfig.properties");

		Properties properties = new Properties();
		try {
			properties.load(input);
		} catch (IOException e) {
			LOG4J.error("Error occured while loading db properties file.");
		}
		
		HikariConfig config = new HikariConfig();
		config.setJdbcUrl(properties.getProperty("jdbcUrl"));
		config.setUsername(properties.getProperty("dataSource.user"));
		config.setPassword(properties.getProperty("dataSource.password"));
		config.setDriverClassName(properties.getProperty("driverClassName"));
		config.addDataSourceProperty("cachePrepStmts", properties.getProperty("dataSource.cachePrepStmts"));
		config.addDataSourceProperty("prepStmtCacheSize", properties.getProperty("dataSource.prepStmtCacheSize"));
		config.addDataSourceProperty("prepStmtCacheSqlLimit", properties.getProperty("dataSource.prepStmtCacheSqlLimit"));
		
		ds = new HikariDataSource(config);

	}

	private DatabaseConnection() {
	}

	private static final DatabaseConnection INSTANCE = new DatabaseConnection();

	public static DatabaseConnection getInstance() {
		return INSTANCE;
	}
	
	public Connection connect() {
		if (connection == null) {
			try {
				connection = ds.getConnection();
				LOG4J.debug("Connected to database!");
			} catch (SQLException e) {
				LOG4J.error("Connection failed", e);
			}
		}
		return connection;
	}

	public void disconnect() {
		if (connection != null) {
			try {
				connection.close();
				connection = null;
				LOG4J.debug("Disconnected !");
			} catch (SQLException e) {
				LOG4J.error("Disconnection failed", e);
			}
		}
	}

}
