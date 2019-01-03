package com.excilys.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "company", schema = "computer-database-db")
public class Company implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -104454351165168235L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	@Column(name = "name")
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
	
	public Company() {
		
	}
	
	public Company(long id, String name) {
		this.id = id;
		this.name = name;
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
