package model;

import java.time.LocalDateTime;

public class Computer {
	
	private int id;
	private String name;
	private LocalDateTime introduced;
	private LocalDateTime discontinued;
	private int companyId;
	
	public Computer() {}
	
	public Computer (String name, String introduced, String discontinued, String companyId) {
		this.setName(name);
		
		if (!"null".equalsIgnoreCase(introduced)) {
			LocalDateTime introducedDateTime = LocalDateTime.parse(introduced);
			this.setIntroduced(introducedDateTime);
		} else {
			this.setIntroduced(null);
		}
		
		if (!"null".equalsIgnoreCase(discontinued)) {
			LocalDateTime discontinuedDateTime = LocalDateTime.parse(discontinued);
			this.setDiscontinued(discontinuedDateTime);
		} else {
			this.setDiscontinued(null);
		}
		
		if (!"null".equalsIgnoreCase(companyId)) {
			this.setCompanyId(Integer.parseInt(companyId));
		} else {
			this.setCompanyId(-1);
		}
	}
	
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
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	
	@Override
	public String toString() {
		return (this.getId() + "	" + this.getName() + "		" + this.getIntroduced() + "	" + this.getDiscontinued() + "	" + this.getCompanyId());
	}
}
