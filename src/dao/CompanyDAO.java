package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CompanyDAO {
	
	private static final String REQUEST_COMPANIES = "SELECT * FROM company;";

	
	/**
	 * Get all the database companies.
	 * @return the ResultSet
	 */
	public ResultSet getCompanies() {
		DatabaseConnection dbConnection = new DatabaseConnection();
		ResultSet rs = null;
		try {
			PreparedStatement statement = dbConnection.connect().prepareStatement(REQUEST_COMPANIES);
			rs = statement.executeQuery();
		} catch (SQLException e) {
			System.out.println("Erreur lors de l'execution de la requête. (Requête : '" + REQUEST_COMPANIES + "')");
		    e.printStackTrace();
		} finally {
			dbConnection.disconnect();
		}
		return rs;
	}
	
}
