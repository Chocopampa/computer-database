package com.excilys.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.mapper.ComputerMapper;
import com.excilys.model.Computer;

public class ComputerDAO {
	
	private ComputerMapper computerMapper = ComputerMapper.getInstance();

	private static final String REQUEST_COMPUTERS = "SELECT * FROM computer;";
	private static final String REQUEST_DETAILED_COMPUTER = "SELECT * FROM computer WHERE id = ?;";
	private static final String INSERT_COMPUTER = "INSERT INTO computer(name,introduced,discontinued,company_id) VALUES (?,?,?,?);";
	private static final String DELETE_COMPUTER = "DELETE FROM computer WHERE id=?;";
	private static final String UPDATE_COMPUTER = "UPDATE computer SET name=?,introduced=?,discontinued=?,company_id=? WHERE id=?;";

	private ComputerDAO() {};

	private static final ComputerDAO INSTANCE = new ComputerDAO();

	public static ComputerDAO getInstance() {
		return INSTANCE;
	}
	
	/**
	 * Get all the database computers.
	 * @return the ResultSet
	 */
	public List<Computer> getComputers() {
		DatabaseConnection dbConnection = new DatabaseConnection();
		List<Computer> computers = new ArrayList<>();
		ResultSet rs = null;
		try (PreparedStatement statement = dbConnection.connect().prepareStatement(REQUEST_COMPUTERS)){
			rs = statement.executeQuery();
			computers = computerMapper.mapList(rs);
		} catch (SQLException e) {
			System.out.println("Erreur lors de l'execution de la requête. (Requête : '" + REQUEST_COMPUTERS + "')");
		    e.printStackTrace();
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				System.out.println("ResultStatement did not cloe successfully.");
			}
			dbConnection.disconnect();
		}
		return computers;
	}
	
	/**
	 * Get the database computer with specified id.
	 * @param idComputer
	 * @return
	 */
	public Computer getComputer(long idComputer) {
		DatabaseConnection dbConnection = new DatabaseConnection();
		Computer computer = null;
		ResultSet rs = null;
		try (PreparedStatement statement = dbConnection.connect().prepareStatement(REQUEST_DETAILED_COMPUTER)) {
			statement.setLong(1, idComputer);
			rs = statement.executeQuery();
			computer = computerMapper.mapUnique(rs);
		} catch (SQLException e) {
			System.out.println("Erreur lors de l'execution de la requête. (Requête : '" + REQUEST_DETAILED_COMPUTER + "')");
		    e.printStackTrace();
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				System.out.println("ResultStatemnt did not close successfully.");
			}
			dbConnection.disconnect();
		}
		return computer;
	}
	
	/**
	 * Create a computer in database.
	 * @param name
	 * @param introduced
	 * @param discontinued
	 * @param company_id
	 */
	public void addComputer(Computer computer) {
		DatabaseConnection dbConnection = new DatabaseConnection();

		try (PreparedStatement statement = dbConnection.connect().prepareStatement(INSERT_COMPUTER)){
			
			statement.setString(1, computer.getName());
			
			if (computer.getIntroduced() != null) {
				statement.setString(2, computer.getIntroduced().toString());
			} else {
				statement.setString(2, null);
			}
			
			if (computer.getDiscontinued() != null) {
				statement.setString(3, computer.getDiscontinued().toString());
			} else {
				statement.setString(3, null);
			}
			
			if (computer.getCompany() != null && computer.getCompany().getId() != -1) {
				statement.setLong(4, computer.getCompany().getId());
			} else {
				statement.setString(4, null);
			}
			
			statement.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Erreur lors de l'execution de la requête. (Requête : '" + INSERT_COMPUTER + "')");
		    e.printStackTrace();
		} finally {
			dbConnection.disconnect();
		}
	}
	
	/**
	 * Delete a computer in database.
	 * @param idComputer
	 */
	public void deleteComputerFromId(long idComputer) {
		DatabaseConnection dbConnection = new DatabaseConnection();

		try (PreparedStatement statement = dbConnection.connect().prepareStatement(DELETE_COMPUTER)){
			
			statement.setLong(1, idComputer);
			statement.executeUpdate();
			statement.close();
		} catch (SQLException e) {
			System.out.println("Erreur lors de l'execution de la requête. (Requête : '" + DELETE_COMPUTER + "')");
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
	public void updateComputer(Computer computer) {
		DatabaseConnection dbConnection = new DatabaseConnection();

		try (PreparedStatement statement = dbConnection.connect().prepareStatement(UPDATE_COMPUTER)) {
			
			statement.setString(1, computer.getName());
			
			if (computer.getIntroduced() != null) {
				statement.setString(2, computer.getIntroduced().toString());
			} else {
				statement.setString(2, null);
			}
			
			if (computer.getDiscontinued() != null) {
				statement.setString(3, computer.getDiscontinued().toString());
			} else {
				statement.setString(3, null);
			}
			
			if (computer.getCompany().getId() != -1) {
				statement.setLong(4, computer.getCompany().getId());
			} else {
				statement.setString(4, null);
			}
			statement.setLong(5, computer.getId());
			statement.executeUpdate();
			statement.close();
		} catch (SQLException e) {
			System.out.println("Erreur lors de l'execution de la requête. (Requête : '" + UPDATE_COMPUTER + "')");
		    e.printStackTrace();
		} finally {
			dbConnection.disconnect();
		}
	}

}
