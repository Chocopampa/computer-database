package com.excilys.mapper;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.dto.ComputerDTO;
import com.excilys.model.Company;
import com.excilys.service.CompanyService;
import com.excilys.validator.ParseValidator;

@Component
public class ComputerDTOMapper {

	@Autowired
	private ParseValidator parseValidator;
	@Autowired
	private CompanyService companyService;

	@Autowired
	public ComputerDTOMapper() {

	}

	public ComputerDTO map(long id, String name, String introduced, String discontinued, String companyNumber) {
		LocalDateTime introducedLocalDateTime = null;
		if (parseValidator.isParsableLocalDateTime(introduced + "T00:00:00")) {
			introducedLocalDateTime = LocalDateTime.parse(introduced + "T00:00:00");
		}

		LocalDateTime discontinuedLocalDateTime = null;
		if (parseValidator.isParsableLocalDateTime(discontinued + "T00:00:00")) {
			discontinuedLocalDateTime = LocalDateTime.parse(discontinued + "T00:00:00");
		}
		long companyId = Long.parseLong(companyNumber);

		Optional<Company> company = companyService.getCompanyById(companyId);

		if (company.get() != null) {
			return new ComputerDTO(id, name, introducedLocalDateTime, discontinuedLocalDateTime, company.get().getId(),
					company.get().getName());
		} else {
			return new ComputerDTO(id, name, introducedLocalDateTime, discontinuedLocalDateTime, companyId, null);
		}
	}

}
