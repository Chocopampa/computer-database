package com.excilys.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.mapper.ComputerMapper;
import com.excilys.model.Computer;
import com.excilys.model.Page;

@Repository
public class ComputerDAO {

	@Autowired
	private ComputerMapper computerMapper;
	@Autowired
	private DatabaseConnection dbConnection;

	private static final String REQUEST_COMPUTERS = "SELECT id,name,introduced,discontinued,company_id FROM computer;";
	private static final String REQUEST_COMPUTERS_SEARCH_NAME_AND_COMPANY = "SELECT id,name,introduced,discontinued,company_id FROM computer "
			+ "WHERE name LIKE ? OR company_id IN (SELECT id FROM company "
				+ "WHERE name LIKE ?);";
	private static final String REQUEST_COMPUTERS_LIMIT = "SELECT id,name,introduced,discontinued,company_id FROM computer LIMIT ?, ?;";
	private static final String REQUEST_DETAILED_COMPUTER = "SELECT id,name,introduced,discontinued,company_id FROM computer WHERE id = ?;";
	private static final String REQUEST_COMPUTER_FROM_COMPANY_ID = "SELECT id,name,introduced,discontinued,company_id FROM computer WHERE company_id = ?;";
	private static final String INSERT_COMPUTER = "INSERT INTO computer(name,introduced,discontinued,company_id) VALUES (?,?,?,?);";
	private static final String INSERT_COMPUTER_WITHOUT_COMPANY = "INSERT INTO computer(name,introduced,discontinued) VALUES (?,?,?);";
	private static final String DELETE_COMPUTER = "DELETE FROM computer WHERE id=?;";
	private static final String UPDATE_COMPUTER = "UPDATE computer SET name=?,introduced=?,discontinued=?,company_id=? WHERE id=?;";

	private static final Logger LOG4J = LogManager.getLogger(ComputerDAO.class.getName());

	private ComputerDAO() {
	};

	private static final ComputerDAO INSTANCE = new ComputerDAO();

	public static ComputerDAO getInstance() {
		return INSTANCE;
	}

	/**
	 * Get all the database computers.
	 * 
	 * @return the ResultSet
	 */
	public List<Computer> getComputers() {
		List<Computer> computers = new ArrayList<>();
		ResultSet rs = null;
		try (PreparedStatement statement = dbConnection.connect().prepareStatement(REQUEST_COMPUTERS)) {
			LOG4J.info("Acquiring all the computers in database...");
			LOG4J.debug(statement.toString());
			rs = statement.executeQuery();
			computers = computerMapper.mapList(rs);
			computers.stream().forEach(o -> LOG4J.debug(o));
		} catch (SQLException e) {
			LOG4J.error("Erreur lors de l'execution de la requête. (Requête : '" + REQUEST_COMPUTERS + "')", e);
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				LOG4J.error("ResultStatement did not close successfully.", e);
			}
			dbConnection.disconnect();
		}
		return computers;
	}

	/**
	 * Get the database computer with specified id.
	 * 
	 * @param idComputer
	 * @return
	 */
	public Optional<Computer> getComputerById(long idComputer) {
		Computer computer = null;
		ResultSet rs = null;
		try (PreparedStatement statement = dbConnection.connect().prepareStatement(REQUEST_DETAILED_COMPUTER)) {
			statement.setLong(1, idComputer);
			rs = statement.executeQuery();
			rs.next();
			computer = computerMapper.mapUnique(rs);
		} catch (SQLException e) {
			LOG4J.error("Erreur lors de l'execution de la requête. (Requête : '" + REQUEST_DETAILED_COMPUTER + "')", e);
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				LOG4J.error("ResultStatement did not close successfully.", e);
			}
			dbConnection.disconnect();
		}
		return Optional.ofNullable(computer);
	}
	
	/**
	 * Get computers by searching for a chain of characters in their name or in the name of their associated company.
	 * 
	 * @param search
	 * @return
	 */
	public List<Computer> getComputersWithSearch(String search) {
		List<Computer> computers = new ArrayList<>();
		ResultSet rs = null;
		try (PreparedStatement statement = dbConnection.connect().prepareStatement(REQUEST_COMPUTERS_SEARCH_NAME_AND_COMPANY)) {
			statement.setString(1, "%" + search + "%");
			statement.setString(2, "%" + search + "%");
			LOG4J.info(statement.toString());
			rs = statement.executeQuery();
			computers = computerMapper.mapList(rs);
		} catch (SQLException e) {
			LOG4J.error("Erreur lors de l'execution de la requête. (Requête : '" + REQUEST_COMPUTERS_SEARCH_NAME_AND_COMPANY + "')", e);
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				LOG4J.error("ResultStatement did not close successfully.", e);
			}
			dbConnection.disconnect();
		}
		return computers;
	}
	
	public List<Computer> getComputersFromCompanyId(long idCompany) {
		List<Computer> computers = new ArrayList<>();
		ResultSet rs = null;
		try (PreparedStatement statement = dbConnection.connect().prepareStatement(REQUEST_COMPUTER_FROM_COMPANY_ID)) {
			statement.setLong(1, idCompany);
			LOG4J.info(statement.toString());
			rs = statement.executeQuery();
			computers = computerMapper.mapList(rs);
		} catch (SQLException e) {
			LOG4J.error("Erreur lors de l'execution de la requête. (Requête : '" + REQUEST_COMPUTER_FROM_COMPANY_ID + "')", e);
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				LOG4J.error("ResultStatement did not close successfully.", e);
			}
			dbConnection.disconnect();
		}
		return computers;
	}

	/**
	 * Return database computers from first id to last id.
	 * 
	 * @param idFirstComputer
	 * @param idLastComputer
	 * @return
	 */
	public List<Computer> getListComputers(Page page) {
		List<Computer> computers = new ArrayList<>();
		ResultSet rs = null;
		try (PreparedStatement statement = dbConnection.connect().prepareStatement(REQUEST_COMPUTERS_LIMIT)) {
			LOG4J.info("Acquiring computers in database...");
			statement.setLong(1, page.getFirstId());
			statement.setInt(2, page.getOffset());
			rs = statement.executeQuery();
			computers = computerMapper.mapList(rs);
		} catch (SQLException e) {
			LOG4J.error("Erreur lors de l'execution de la requête. (Requête : '" + REQUEST_COMPUTERS_LIMIT + "')", e);
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				LOG4J.error("ResultStatement did not close successfully.", e);
			}
			dbConnection.disconnect();
		}
		return computers;
	}

	/**
	 * Create a computer in database.
	 * 
	 * @param name
	 * @param introduced
	 * @param discontinued
	 * @param company_id
	 */
	public int addComputer(Computer computer) {
		int nbRowAffected = 0;
		try (PreparedStatement statement = dbConnection.connect().prepareStatement(INSERT_COMPUTER)) {
			LOG4J.info("Adding a computer to database...");
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

			if (computer.getCompany() != null) {
				statement.setLong(4, computer.getCompany().getId());
			} else {
				statement.setString(4, null);
			}
			nbRowAffected = statement.executeUpdate();
		} catch (SQLException e) {
			LOG4J.error("Erreur lors de l'execution de la requête. (Requête : '" + INSERT_COMPUTER + "')", e);
		} finally {
			dbConnection.disconnect();
		}
		return nbRowAffected;
	}
	
	/**
	 * Create a computer in database.
	 * 
	 * @param name
	 * @param introduced
	 * @param discontinued
	 * @param company_id
	 */
	public int addComputerWithoutCompany(Computer computer) {
		int nbRowAffected = 0;
		try (PreparedStatement statement = dbConnection.connect().prepareStatement(INSERT_COMPUTER_WITHOUT_COMPANY)) {
			LOG4J.info("Adding a computer to database...");
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
			
			nbRowAffected = statement.executeUpdate();
		} catch (SQLException e) {
			LOG4J.error("Erreur lors de l'execution de la requête. (Requête : '" + INSERT_COMPUTER_WITHOUT_COMPANY + "')", e);
		} finally {
			dbConnection.disconnect();
		}
		return nbRowAffected;
	}

	/**
	 * Delete a computer in database.
	 * 
	 * @param idComputer
	 */
	public int deleteComputerFromId(long idComputer) {
		int nbRowAffected = 0;
		try (PreparedStatement statement = dbConnection.connect().prepareStatement(DELETE_COMPUTER)) {
			statement.setLong(1, idComputer);
			nbRowAffected = statement.executeUpdate();
			statement.close();
		} catch (SQLException e) {
			LOG4J.error("Erreur lors de l'execution de la requête. (Requête : '" + DELETE_COMPUTER + "')", e);
		} finally {
			dbConnection.disconnect();
		}
		return nbRowAffected;
	}

	/**
	 * Update a computer in database.
	 * 
	 * @param name
	 * @param introduced
	 * @param discontinued
	 * @param company_id
	 */
	public int updateComputer(Computer computer) {
		int nbRowAffected = 0;
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

			if (computer.getCompany() != null && computer.getCompany().getId() != -1) {
				statement.setLong(4, computer.getCompany().getId());
			} else {
				statement.setString(4, null);
			}
			statement.setLong(5, computer.getId());
			nbRowAffected = statement.executeUpdate();
			statement.close();
		} catch (SQLException e) {
			LOG4J.error("Erreur lors de l'execution de la requête. (Requête : '" + UPDATE_COMPUTER + "')", e);
		} finally {
			dbConnection.disconnect();
		}
		return nbRowAffected;
	}

}
