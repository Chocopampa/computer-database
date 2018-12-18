package com.excilys.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Company {

	public static class Builder {

		private long id;
		private String name;

		public Builder withName(String name) {
			this.name = name;
			return this;
		}

		public Builder(long id) {
			this.id = id;
		}

		public Company build() {
			Company company = new Company();
			company.id = this.id;
			company.name = this.name;

			return company;
		}
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column
	private String name;

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

	private Company() {
	}

	@Override
	public String toString() {
		if (this.getName() == null) {
			return "" + this.getId();
		} else {
			return (this.id + "		" + this.name);
		}
	}
}
