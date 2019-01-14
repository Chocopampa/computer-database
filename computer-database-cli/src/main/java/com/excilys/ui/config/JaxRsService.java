package com.excilys.ui.config;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.model.Computer;

@Service
public class JaxRsService {

	private static final String REST_COMPUTER_URI = "http://localhost:8080/computer-database-webapp/getComputer-rest/";
	private static final String REST_CREATE_COMPUTER_URI = "http://localhost:8080/computer-database-webapp/addComputer-rest/";
	private static final String REST_COMPUTERS_URI = "http://localhost:8080/computer-database-webapp/allComputers-rest";
	private static final String REST_DELETE_COMPUTER_URI = "http://localhost:8080/computer-database-webapp/deleteComputer-rest/";
	private static final String REST_UPDATE_COMPUTER_URI = "http://localhost:8080/computer-database-webapp/editComputer-rest/";

	@Autowired
	public JaxRsService() {
	}
	
	public Computer getJsonComputer(long id) {
		Client client = ClientBuilder.newClient().register(new Authenticator("admin", "network"));
		return client.target(REST_COMPUTER_URI + id).request(MediaType.APPLICATION_JSON).get(Computer.class);
	}

	public List<Computer> getJsonComputers() {
		Client client = ClientBuilder.newClient().register(new Authenticator("admin", "network"));
		return client.target(REST_COMPUTERS_URI).request(MediaType.APPLICATION_JSON).get(List.class);
	}

	public Response createComputer(Computer computer) {
		Client client = ClientBuilder.newClient().register(new Authenticator("admin", "network"));
		return client.target(REST_CREATE_COMPUTER_URI).request(MediaType.APPLICATION_JSON).put(Entity.json(computer));
	}
	
	public Response deleteComputer(long id) {
		Client client = ClientBuilder.newClient().register(new Authenticator("admin", "network"));
		return client.target(REST_DELETE_COMPUTER_URI + id).request(MediaType.APPLICATION_JSON).delete();
	}

	public Response updateComputer(Computer computer) {
		Client client = ClientBuilder.newClient().register(new Authenticator("admin", "network"));
		return client.target(REST_UPDATE_COMPUTER_URI + computer.getId()).request(MediaType.APPLICATION_JSON).put(Entity.json(computer));
	}
}
