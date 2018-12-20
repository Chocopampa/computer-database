package com.excilys.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.excilys.converter.LocalDateTimeConverter;

@Entity
@Table(name = "computer", schema = "computer-database-db")
public class Computer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	@Column(name = "name")
	private String name;
	@Column(name = "introduced")
	@Convert(converter = LocalDateTimeConverter.class)
	private LocalDateTime introduced;
	@Column(name = "discontinued")
	@Convert(converter = LocalDateTimeConverter.class)
	private LocalDateTime discontinued;
	@ManyToOne
	@JoinColumn(name = "company_id")
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

	public Computer(long id, String name, LocalDateTime introduced, LocalDateTime discontinued, Company company) {
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.company = company;
	}

	public Computer(String name, LocalDateTime introduced, LocalDateTime discontinued, Company company) {
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.company = company;
	}

	public Computer() {
	}

	@Override
	public String toString() {
		return (this.id + "	" + this.name + "		" + this.introduced + "	"
				+ this.discontinued + "	" + this.company);
	}
}
