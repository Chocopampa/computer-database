package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import mappers.ComputerMapper;
import model.Computer;

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
		try {
			PreparedStatement statement = dbConnection.connect().prepareStatement(REQUEST_COMPUTERS);
			ResultSet rs = statement.executeQuery();
			computers = computerMapper.map(rs);
			rs.close();
			statement.close();
		} catch (SQLException e) {
			System.out.println("Erreur lors de l'execution de la requête. (Requête : '" + REQUEST_COMPUTERS + "')");
		    e.printStackTrace();
		} finally {
			dbConnection.disconnect();
		}
		return computers;
	}
	
	/**
	 * Get the database computer with specified id.
	 * @param idComputer
	 * @return
	 */
	public Computer getComputer(int idComputer) {
		DatabaseConnection dbConnection = new DatabaseConnection();
		Computer computer = new Computer();
		try {
			PreparedStatement statement = dbConnection.connect().prepareStatement(REQUEST_DETAILED_COMPUTER);
			statement.setInt(1, idComputer);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				rs.previous();
				computer = computerMapper.map(rs).get(0);
			} else {
				computer = null;
			}
			rs.close();
			statement.close();
		} catch (SQLException e) {
			System.out.println("Erreur lors de l'execution de la requête. (Requête : '" + REQUEST_DETAILED_COMPUTER + "')");
		    e.printStackTrace();
		} finally {
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
	public void insertComputer(Computer computer) {
		DatabaseConnection dbConnection = new DatabaseConnection();

		try {
			PreparedStatement statement = dbConnection.connect().prepareStatement(INSERT_COMPUTER);
			
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
			
			if (computer.getCompanyId() != -1) {
				statement.setInt(4, computer.getCompanyId());
			} else {
				statement.setString(4, null);
			}
			
			statement.executeUpdate();
			statement.close();
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
	public void deleteComputerFromId(int idComputer) {
		DatabaseConnection dbConnection = new DatabaseConnection();

		try {
			PreparedStatement statement = dbConnection.connect().prepareStatement(DELETE_COMPUTER);
			statement.setInt(1, idComputer);
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
	public void updateComputer(int idComputer, Computer computer) {
		DatabaseConnection dbConnection = new DatabaseConnection();

		try {
			PreparedStatement statement = dbConnection.connect().prepareStatement(UPDATE_COMPUTER);
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
			
			if (computer.getCompanyId() != -1) {
				statement.setInt(4, computer.getCompanyId());
			} else {
				statement.setString(4, null);
			}
			statement.setInt(5, idComputer);
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
