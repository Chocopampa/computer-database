package com.excilys.model;

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

	private long id;
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
