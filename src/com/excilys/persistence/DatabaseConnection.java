package com.excilys.persistence;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.mysql.jdbc.Connection;

public class DatabaseConnection {
    // init connection object
    private Connection connection;
    // init properties object
    private Properties properties;

    private DatabaseConnection() {}
    
    private static final DatabaseConnection INSTANCE = new DatabaseConnection();
    
    public static DatabaseConnection getInstance() {
    	return INSTANCE;
    }
    
    // create properties
    private Properties getProperties() {
    	try {
			InputStream input = new FileInputStream("resources/dbConfig.properties");
			properties = new Properties();
			properties.load(input);
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("");
			e.printStackTrace();
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
                e.printStackTrace();
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
                e.printStackTrace();
            }
        }
    }

}
