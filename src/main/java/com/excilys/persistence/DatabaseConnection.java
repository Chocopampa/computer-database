package com.excilys.persistence;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;

public class DatabaseConnection {
	
	private Connection connection;
	private Properties properties;

	private static final Logger LOG4J = LogManager.getLogger(DatabaseConnection.class.getName());

	private DatabaseConnection() {
	}

	private static final DatabaseConnection INSTANCE = new DatabaseConnection();

	public static DatabaseConnection getInstance() {
		return INSTANCE;
	}

	private Properties getProperties() {
		try {
			InputStream input = this.getClass().getClassLoader().getResourceAsStream("dbConfig.properties");
			properties = new Properties();
			properties.load(input);
		} catch (FileNotFoundException e) {
			LOG4J.error("File not found", e);
		} catch (IOException e) {
			LOG4J.error(e);
		}
		return properties;
	}

	public Connection connect() {
		if (connection == null) {
			try {
				getProperties();
				Class.forName(properties.getProperty("dbDriver"));
				connection = (Connection) DriverManager.getConnection(properties.getProperty("dbURL"), properties);
			} catch (ClassNotFoundException | SQLException e) {
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
			} catch (SQLException e) {
				LOG4J.error("Disconnection failed", e);
			}
		}
	}

}
