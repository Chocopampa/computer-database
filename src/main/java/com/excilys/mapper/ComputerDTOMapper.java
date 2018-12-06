package com.excilys.mapper;

import org.springframework.stereotype.Component;

import com.excilys.dto.ComputerDTO;
import com.excilys.model.Computer;

@Component
public class ComputerDTOMapper {

	private ComputerDTOMapper() {
	}

	private static final ComputerDTOMapper INSTANCE = new ComputerDTOMapper();

	public static ComputerDTOMapper getInstance() {
		return INSTANCE;
	}
	
	public ComputerDTO map(Computer computer) {
		ComputerDTO computerDTO = new ComputerDTO();
		computerDTO.setId(computer.getId());
		computerDTO.setIntroduced(computer.getIntroduced());
		computerDTO.setDiscontinued(computer.getDiscontinued());
		computerDTO.setName(computer.getName());
		if (computer.getCompany() != null) {
			computerDTO.setCompanyName(computer.getCompany().getName());
		} 
		return computerDTO;
	}
}
