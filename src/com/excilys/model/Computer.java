package com.excilys.model;

import java.time.LocalDateTime;

public class Computer {
	
	public static class Builder {
		
		private long id;
		private String name;
		private LocalDateTime introduced;
		private LocalDateTime discontinued;
		private Company company;
		
		public Builder(String name) {
			this.name = name;
		}
		
		public Builder withId(long id) {
			this.id = id;
			return this;
		}
		
		public Builder withIntroduced(LocalDateTime introduced) {
			this.introduced = introduced;
			return this;
		}
		
		public Builder withDiscontinued(LocalDateTime discontinued) {
			this.discontinued = discontinued;
			return this;
		}
		
		public Builder withCompany(Company company) {
			this.company = company;
			return this;
		}

		public Computer build() {
			Computer computer = new Computer();
			computer.id = this.id;
			computer.name = this.name;
			computer.introduced = this.introduced;
			computer.discontinued = this.discontinued;
			computer.company = this.company;
			
			return computer;
		}
	}
	
	private long id;

	private String name;
	private LocalDateTime introduced;
	private LocalDateTime discontinued;
	private Company company;
	

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

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
	
	private Computer() {}
	
	@Override
	public String toString() {
		return (this.getId() + "	" + this.getName() + "		" + this.getIntroduced() + "	" + this.getDiscontinued() + "	" + this.getCompany());
	}
}
