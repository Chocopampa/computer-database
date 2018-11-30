package com.excilys.dto;

import java.util.List;

public class CompanyDTO {
	
	private long id;
	private List<Long> computerIds;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public List<Long> getComputerIds() {
		return computerIds;
	}
	public void setComputerIds(List<Long> computerIds) {
		this.computerIds = computerIds;
	}
}
