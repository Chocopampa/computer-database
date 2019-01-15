package com.excilys.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "role", schema = "computer-database-db")
public class Role implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4254736274072509983L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	@Column(name = "role_name")
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
	
	public Role() {
		
	}
	
	public Role(long id, String name) {
		this.id = id;
		this.name = name;
	}
}
