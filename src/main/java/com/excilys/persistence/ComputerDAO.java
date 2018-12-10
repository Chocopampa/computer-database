package com.excilys.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameterValue;
import org.springframework.stereotype.Repository;

import com.excilys.mapper.ComputerMapper;
import com.excilys.model.Computer;
import com.excilys.model.Page;

@Repository
public class ComputerDAO {

	@Autowired
	private ComputerMapper computerMapper;

	private RowMapper<Computer> rowMapper = new RowMapper<Computer>() {
		public Computer mapRow(ResultSet rs, int rowNum) throws SQLException {
			return computerMapper.mapUnique(rs);
		}
	};

	private JdbcTemplate jdbcTemplate = new JdbcTemplate(DatabaseConnection.getDs());

	private static final String REQUEST_COMPUTERS = "SELECT id,name,introduced,discontinued,company_id FROM computer;";
	private static final String REQUEST_COMPUTERS_SEARCH_NAME_AND_COMPANY = "SELECT id,name,introduced,discontinued,company_id FROM computer "
			+ "WHERE name LIKE ? OR company_id IN (SELECT id FROM company " + "WHERE name LIKE ?);";
	private static final String REQUEST_COMPUTERS_LIMIT = "SELECT id,name,introduced,discontinued,company_id FROM computer LIMIT ?, ?;";
	private static final String REQUEST_DETAILED_COMPUTER = "SELECT id,name,introduced,discontinued,company_id FROM computer WHERE id = ?;";
	private static final String REQUEST_COMPUTER_FROM_COMPANY_ID = "SELECT id,name,introduced,discontinued,company_id FROM computer WHERE company_id = ?;";

	private static final String REQUEST_COMPUTERS_ORDER_BY_NAME = "(SELECT id,name,introduced,discontinued,company_id FROM computer LIMIT ?,?) ORDER BY name ASC;";
	private static final String REQUEST_COMPUTERS_ORDER_BY_INTRODUCED = "(SELECT id,name,introduced,discontinued,company_id FROM computer LIMIT ?,?) ORDER BY introduced ASC;";
	private static final String REQUEST_COMPUTERS_ORDER_BY_DISCONTINUED = "(SELECT id,name,introduced,discontinued,company_id FROM computer LIMIT ?,?) ORDER BY discontinued ASC;";
	private static final String REQUEST_COMPUTERS_ORDER_BY_COMPANY = "(SELECT id,name,introduced,discontinued,company_id FROM computer LIMIT ?,?) ORDER BY company_id ASC;";

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
		try {
			computers = jdbcTemplate.query(REQUEST_COMPUTERS, new Object[] {}, rowMapper);
		} catch (DataAccessException e) {
			LOG4J.error("Error accessing the database for request : " + REQUEST_COMPUTERS, e);
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
		try {
			computer = jdbcTemplate.queryForObject(REQUEST_DETAILED_COMPUTER, new Object[] { idComputer }, rowMapper);
		} catch (DataAccessException e) {
			LOG4J.error("Error accessing the database for request : " + REQUEST_DETAILED_COMPUTER, e);
		}
		return Optional.ofNullable(computer);
	}

	/**
	 * Get computers by searching for a chain of characters in their name or in the
	 * name of their associated company.
	 * 
	 * @param search
	 * @return
	 */
	public List<Computer> getComputersWithSearch(String search) {
		List<Computer> computers = new ArrayList<>();
		try {
			computers = jdbcTemplate.query(REQUEST_COMPUTERS_SEARCH_NAME_AND_COMPANY,
					new Object[] { "%" + search + "%", "%" + search + "%" }, rowMapper);
		} catch (DataAccessException e) {
			LOG4J.error("Error accessing the database for request : " + REQUEST_COMPUTERS_SEARCH_NAME_AND_COMPANY, e);
		}
		return computers;
	}

	public List<Computer> getComputersFromCompanyId(long idCompany) {
		List<Computer> computers = new ArrayList<>();
		try {
			computers = jdbcTemplate.query(REQUEST_COMPUTER_FROM_COMPANY_ID, new Object[] { idCompany }, rowMapper);
		} catch (DataAccessException e) {
			LOG4J.error("Error accessing the database for request : " + REQUEST_COMPUTER_FROM_COMPANY_ID, e);
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
		try {
			computers = jdbcTemplate.query(REQUEST_COMPUTERS_LIMIT,
					new Object[] { page.getFirstId(), page.getOffset() }, rowMapper);
		} catch (DataAccessException e) {
			LOG4J.error("Error accessing the database for request : " + REQUEST_COMPUTERS_LIMIT, e);
		}
		return computers;
	}

	/**
	 * Return database computers from first id to last id order by name.
	 * 
	 * @param idFirstComputer
	 * @param idLastComputer
	 * @return
	 */
	public List<Computer> getListComputersOrderByName(Page page) {
		List<Computer> computers = new ArrayList<>();
		try {
			computers = jdbcTemplate.query(REQUEST_COMPUTERS_ORDER_BY_NAME,
					new Object[] { page.getFirstId(), page.getOffset() }, rowMapper);
		} catch (DataAccessException e) {
			LOG4J.error("Error accessing the database for request : " + REQUEST_COMPUTERS_ORDER_BY_NAME, e);
		}
		return computers;
	}

	/**
	 * Return database computers from first id to last id order by name.
	 * 
	 * @param idFirstComputer
	 * @param idLastComputer
	 * @return
	 */
	public List<Computer> getListComputersOrderByIntroduced(Page page) {
		List<Computer> computers = new ArrayList<>();
		try {
			computers = jdbcTemplate.query(REQUEST_COMPUTERS_ORDER_BY_INTRODUCED,
					new Object[] { page.getFirstId(), page.getOffset() }, rowMapper);
		} catch (DataAccessException e) {
			LOG4J.error("Error accessing the database for request : " + REQUEST_COMPUTERS_ORDER_BY_INTRODUCED, e);
		}
		return computers;
	}

	/**
	 * Return database computers from first id to last id order by name.
	 * 
	 * @param idFirstComputer
	 * @param idLastComputer
	 * @return
	 */
	public List<Computer> getListComputersOrderByDiscontinued(Page page) {
		List<Computer> computers = new ArrayList<>();
		try {
			computers = jdbcTemplate.query(REQUEST_COMPUTERS_ORDER_BY_DISCONTINUED,
					new Object[] { page.getFirstId(), page.getOffset() }, rowMapper);
		} catch (DataAccessException e) {
			LOG4J.error("Error accessing the database for request : " + REQUEST_COMPUTERS_ORDER_BY_DISCONTINUED, e);
		}
		return computers;
	}

	/**
	 * Return database computers from first id to last id order by name.
	 * 
	 * @param idFirstComputer
	 * @param idLastComputer
	 * @return
	 */
	public List<Computer> getListComputersOrderByCompany(Page page) {
		List<Computer> computers = new ArrayList<>();
		try {
			computers = jdbcTemplate.query(REQUEST_COMPUTERS_ORDER_BY_COMPANY,
					new Object[] { page.getFirstId(), page.getOffset() }, rowMapper);
		} catch (DataAccessException e) {
			LOG4J.error("Error accessing the database for request : " + REQUEST_COMPUTERS_ORDER_BY_COMPANY, e);
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
		List<Object> params = new ArrayList<>();

		params.add(new SqlParameterValue(Types.VARCHAR, computer.getName()));

		if (computer.getIntroduced() != null) {
			params.add(new SqlParameterValue(Types.VARCHAR, computer.getIntroduced().toString()));
		} else {
			params.add(new SqlParameterValue(Types.VARCHAR, null));
		}

		if (computer.getDiscontinued() != null) {
			params.add(new SqlParameterValue(Types.VARCHAR, computer.getDiscontinued().toString()));
		} else {
			params.add(new SqlParameterValue(Types.VARCHAR, null));
		}

		if (computer.getCompany() != null) {
			params.add(new SqlParameterValue(Types.BIGINT, computer.getCompany().getId()));
		} else {
			params.add(new SqlParameterValue(Types.BIGINT, null));
		}

		Object[] vParams = params.toArray();

		int nbRowAffected = 0;
		try {
			nbRowAffected = jdbcTemplate.update(INSERT_COMPUTER, vParams);
		} catch (DataAccessException e) {
			LOG4J.error("Error accessing the database for request : " + INSERT_COMPUTER, e);
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
		List<Object> params = new ArrayList<>();

		params.add(new SqlParameterValue(Types.VARCHAR, computer.getName()));

		if (computer.getIntroduced() != null) {
			params.add(new SqlParameterValue(Types.VARCHAR, computer.getIntroduced().toString()));
		} else {
			params.add(new SqlParameterValue(Types.VARCHAR, null));
		}

		if (computer.getDiscontinued() != null) {
			params.add(new SqlParameterValue(Types.VARCHAR, computer.getDiscontinued().toString()));
		} else {
			params.add(new SqlParameterValue(Types.VARCHAR, null));
		}

		Object[] vParams = params.toArray();

		int nbRowAffected = 0;
		try {
			nbRowAffected = jdbcTemplate.update(INSERT_COMPUTER_WITHOUT_COMPANY, vParams);
		} catch (DataAccessException e) {
			LOG4J.error("Error accessing the database for request : " + INSERT_COMPUTER_WITHOUT_COMPANY, e);
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
		try {
			nbRowAffected = jdbcTemplate.update(DELETE_COMPUTER, new Object[] { idComputer });
		} catch (DataAccessException e) {
			LOG4J.error("Error accessing the database for request : " + DELETE_COMPUTER, e);
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
		List<Object> params = new ArrayList<>();

		params.add(new SqlParameterValue(Types.VARCHAR, computer.getName()));

		if (computer.getIntroduced() != null) {
			params.add(new SqlParameterValue(Types.VARCHAR, computer.getIntroduced().toString()));
		} else {
			params.add(new SqlParameterValue(Types.VARCHAR, null));
		}

		if (computer.getDiscontinued() != null) {
			params.add(new SqlParameterValue(Types.VARCHAR, computer.getDiscontinued().toString()));
		} else {
			params.add(new SqlParameterValue(Types.VARCHAR, null));
		}

		if (computer.getCompany() != null) {
			params.add(new SqlParameterValue(Types.BIGINT, computer.getCompany().getId()));
		} else {
			params.add(new SqlParameterValue(Types.BIGINT, null));
		}

		params.add(new SqlParameterValue(Types.BIGINT, computer.getId()));

		Object[] vParams = params.toArray();

		int nbRowAffected = 0;
		try {
			nbRowAffected = jdbcTemplate.update(UPDATE_COMPUTER, vParams);
		} catch (DataAccessException e) {
			LOG4J.error("Error accessing the database for request : " + UPDATE_COMPUTER, e);
		}

		return nbRowAffected;
	}

}
