package com.excilys.dto;

import java.time.LocalDateTime;

import com.excilys.model.Computer;

public class ComputerDTO {

	private long id;
	private String name;
	private LocalDateTime introduced;
	private LocalDateTime discontinued;
	private long companyId;
	private String companyName;

	public ComputerDTO(Computer computer) {
		this.id = computer.getId();
		this.introduced = computer.getIntroduced();
		this.discontinued = computer.getDiscontinued();
		this.name = computer.getName();
		if (computer.getCompany() != null) {
			this.companyId = computer.getCompany().getId();
			this.companyName = computer.getCompany().getName();
		}
	}

	public ComputerDTO(long id, String name, LocalDateTime introduced, LocalDateTime discontinued, long companyId,
			String companyName) {
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.companyId = companyId;
		this.companyName = companyName;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDateTime getIntroduced() {
		return introduced;
	}

	public void setIntroduced(LocalDateTime introduced) {
		this.introduced = introduced;
	}

	public LocalDateTime getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(LocalDateTime discontinued) {
		this.discontinued = discontinued;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	@Override
	public String toString() {
		return (this.id + "	" + this.name + "		" + this.introduced + "	" + this.discontinued + "	"
				+ this.companyName);
	}
}
