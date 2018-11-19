package com.excilys.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.excilys.model.Computer;

public class ComputerMapper {
	
	private ComputerMapper(){}
	
	private static final ComputerMapper INSTANCE = new ComputerMapper();

	public static ComputerMapper getInstance() {
		return INSTANCE;
	}
	
	public List<Computer> map(ResultSet listComputerDb) throws SQLException {
		List<Computer> computers = new ArrayList<>();
		
		while(listComputerDb.next()) {
			Computer computer = new Computer();

			LocalDateTime timeIntroduced = null;
			LocalDateTime timeDiscontinued = null;
			
			String introduced = listComputerDb.getString(3);
			if (introduced != null) {
				introduced = introduced.replace(' ','T');
				timeIntroduced = LocalDateTime.parse(introduced);
			}
			
			String discontinued = listComputerDb.getString(4);
			if (discontinued != null) {
				discontinued = discontinued.replace(' ','T');
				timeDiscontinued = LocalDateTime.parse(discontinued);
			}
			
			computer.setId(listComputerDb.getInt(1));
			computer.setName(listComputerDb.getString(2));
			computer.setIntroduced(timeIntroduced);
			computer.setDiscontinued(timeDiscontinued);
			computer.setCompanyId(listComputerDb.getInt(5));
			computers.add(computer);
		}
		
		return computers;
	}
}
