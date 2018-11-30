package com.excilys.mapper;

import java.util.ArrayList;
import java.util.List;

import com.excilys.dto.CompanyDTO;
import com.excilys.model.Company;
import com.excilys.model.Computer;

public class CompanyDTOMapper {

	private CompanyDTOMapper() {
	}

	private static final CompanyDTOMapper INSTANCE = new CompanyDTOMapper();

	public static CompanyDTOMapper getInstance() {
		return INSTANCE;
	}
	
	public CompanyDTO map(Company company, List<Computer> associatedComputers) {
		CompanyDTO companyDTO = new CompanyDTO();
		companyDTO.setId(company.getId());
		companyDTO.setName(company.getName());
		List<Long> computerIds = new ArrayList<>();
		associatedComputers.stream().forEach(computer -> computerIds.add(computer.getId()));
		companyDTO.setComputerIds(computerIds);
		return companyDTO;
	}
	
}
