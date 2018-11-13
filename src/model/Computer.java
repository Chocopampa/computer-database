package model;

import java.sql.Time;

public class Computer {
	
	private int id;
	private String name;
	private Time introduced;
	private Time discontinued;
	private int companyId;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Time getIntroduced() {
		return introduced;
	}
	public void setIntroduced(Time introduced) {
		this.introduced = introduced;
	}
	public Time getDiscontinued() {
		return discontinued;
	}
	public void setDiscontinued(Time discontinued) {
		this.discontinued = discontinued;
	}
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
}
