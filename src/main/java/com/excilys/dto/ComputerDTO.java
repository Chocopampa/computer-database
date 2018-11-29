package com.excilys.dto;

import java.time.LocalDateTime;

public class ComputerDTO {
	
	private long id;
	private String name;
	private LocalDateTime introduced;
	private LocalDateTime discontinued;
	private String companyName;

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
	

	@Override
	public String toString() {
		return (this.id + "	" + this.name + "		" + this.introduced + "	"
				+ this.discontinued + "	" + this.companyName);
	}
}
