package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

public class ComputerDAO {

	private static final String REQUEST_COMPUTERS = "SELECT * FROM computer;";
	private static final String INSERT_COMPUTER = "INSERT INTO computer(name,introduced,discontinued,company_id) VALUES (?,?,?,?);";
	private static final String UPDATE_COMPUTER = "UPDATE computer SET name=?,introduced=?,discontinued=?,company_id=? WHERE id=?;";
	private static final String DELETE_COMPUTER = "DELETE FROM computer WHERE id=?;";

	
	/**
	 * Get all the database computers.
	 * @return the ResultSet
	 */
	public ResultSet getComputers() {
		DatabaseConnection dbConnection = new DatabaseConnection();
		ResultSet rs = null;
		try {
			PreparedStatement statement = dbConnection.connect().prepareStatement(REQUEST_COMPUTERS);
			rs = statement.executeQuery();
		} catch (SQLException e) {
			System.out.println("Erreur lors de l'execution de la requête. (Requête : '" + REQUEST_COMPUTERS + "')");
		    e.printStackTrace();
		} finally {
			dbConnection.disconnect();
		}
		return rs;
	}
	
	/**
	 * Create a computer in database.
	 * @param name
	 * @param introduced
	 * @param discontinued
	 * @param company_id
	 */
	public void createComputer(String name, Date introduced, Date discontinued, int companyId) {
		DatabaseConnection dbConnection = new DatabaseConnection();

		try {
			PreparedStatement statement = dbConnection.connect().prepareStatement(INSERT_COMPUTER);
			statement.setString(1, name);
			statement.setString(2, introduced.toString());
			statement.setString(3, discontinued.toString());
			statement.setInt(4, companyId);
		} catch (SQLException e) {
			System.out.println("Erreur lors de l'execution de la requête. (Requête : '" + INSERT_COMPUTER + "')");
		    e.printStackTrace();
		} finally {
			dbConnection.disconnect();
		}
	}
	
	/**
	 * Update a computer in database.
	 * @param name
	 * @param introduced
	 * @param discontinued
	 * @param company_id
	 */
	public void updateComputer(int idComputer, String name, Date introduced, Date discontinued, int companyId) {
		DatabaseConnection dbConnection = new DatabaseConnection();

		try {
			PreparedStatement statement = dbConnection.connect().prepareStatement(UPDATE_COMPUTER);
			statement.setString(1, name);
			statement.setString(2, introduced.toString());
			statement.setString(3, discontinued.toString());
			statement.setInt(4, companyId);
			statement.setInt(5, idComputer);
		} catch (SQLException e) {
			System.out.println("Erreur lors de l'execution de la requête. (Requête : '" + UPDATE_COMPUTER + "')");
		    e.printStackTrace();
		} finally {
			dbConnection.disconnect();
		}
	}
	
	/**
	 * Delete a computer in database.
	 * @param idComputer
	 */
	public void deleteComputerFromId(int idComputer) {
		DatabaseConnection dbConnection = new DatabaseConnection();

		try {
			PreparedStatement statement = dbConnection.connect().prepareStatement(DELETE_COMPUTER);
			statement.setInt(1, idComputer);
		} catch (SQLException e) {
			System.out.println("Erreur lors de l'execution de la requête. (Requête : '" + DELETE_COMPUTER + "')");
		    e.printStackTrace();
		} finally {
			dbConnection.disconnect();
		}
	}

}
