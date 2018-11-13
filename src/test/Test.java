package test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import persistence.DatabaseConnection;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DatabaseConnection dbConnection = new DatabaseConnection();
		
		String request = "SELECT * FROM computer;";
		
		try {
			PreparedStatement statement = dbConnection.connect().prepareStatement(request);
			ResultSet rs = statement.executeQuery();
			
			while (rs.next()) {
				String computerName = rs.getString("name");
				System.out.print(computerName);
				String computerId = rs.getString("company_id");
				System.out.println(" " + computerId);
			}
			
		} catch (SQLException e) {
		    e.printStackTrace();
		} finally {
			dbConnection.disconnect();
		}
	}

}
