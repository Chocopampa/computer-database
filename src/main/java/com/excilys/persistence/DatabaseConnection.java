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
    // init connection object
    private Connection connection;
    // init properties object
    private Properties properties;

	private static final Logger log4j = LogManager.getLogger(DatabaseConnection.class.getName());
    
    private DatabaseConnection() {}
    
    private static final DatabaseConnection INSTANCE = new DatabaseConnection();
    
    public static DatabaseConnection getInstance() {
    	return INSTANCE;
    }
    
    // create properties
    private Properties getProperties() {
    	try {
			InputStream input = this.getClass().getClassLoader().getResourceAsStream("dbConfig.properties");
			properties = new Properties();
			properties.load(input);
		} catch (FileNotFoundException e) {
			log4j.error("File not found",e);
		} catch (IOException e) {
			log4j.error(e);
		}
        return properties;
    }

    // connect database
    public Connection connect() {
        if (connection == null) {
            try {
            	getProperties();
                Class.forName(properties.getProperty("dbDriver"));
                connection = (Connection) DriverManager.getConnection(properties.getProperty("dbURL"), properties);
            } catch (ClassNotFoundException | SQLException e) {
                log4j.error(e);
            }
        }
        return connection;
    }

    // disconnect database
    public void disconnect() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                log4j.error(e);
            }
        }
    }

}
